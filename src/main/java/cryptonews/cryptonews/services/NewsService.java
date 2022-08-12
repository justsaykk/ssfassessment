package cryptonews.cryptonews.services;

import java.io.Reader;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import cryptonews.cryptonews.models.Article;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Service
public class NewsService {

    private final String baseUrl = "https://min-api.cryptocompare.com/data/v2/news/?lang=EN";
    private final String app = "restfulApiPractice";

    @Value("${API_KEY}")
    private String apiKey;

    private ResponseEntity<String> fetch(String url) {
        RestTemplate template = new RestTemplate();
        RequestEntity<Void> req = RequestEntity.get(url).build();

        try {
            ResponseEntity<String> res = template.exchange(req, String.class);
            return res;
        } catch (Exception e) {
            System.err.print(e);
            return null;
        }
    }

    public List<Article> getArticles() {
        String url = UriComponentsBuilder.fromUriString(baseUrl)
                .queryParam("extraParams", app)
                .queryParam("api_key", apiKey).toUriString();

        // Fetch API
        ResponseEntity<String> res = fetch(url);

        // Get response:
        String payload = res.getBody();

        // Read response:
        Reader reader = new StringReader(payload);
        JsonReader jr = Json.createReader(reader);

        JsonObject jo = jr.readObject();
        JsonArray data = jo.getJsonArray("Data");

        // Manipulate response
        List<Article> resList = new LinkedList<>();

        for (int i = 0; i < data.size(); i++) {
            Article article = new Article();
            JsonObject dataEl = data.getJsonObject(i);
            article.setId(dataEl.getString("id"));
            article.setPublishedOn(Integer.toString(dataEl.getInt("published_on")));
            article.setTitle(dataEl.getString("title"));
            article.setUrl(dataEl.getString("url"));
            article.setImageUrl(dataEl.getString("imageurl"));
            article.setBody(dataEl.getString("body"));
            article.setTags(dataEl.getString("tags"));
            article.setCategories(dataEl.getString("categories"));
            resList.add(article);
        }
        return resList;
    }
}
