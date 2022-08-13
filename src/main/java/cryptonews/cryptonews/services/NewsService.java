package cryptonews.cryptonews.services;

import java.io.Reader;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import cryptonews.cryptonews.models.Article;
import cryptonews.cryptonews.repository.Repo;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Service
public class NewsService {

    private final String baseUrl = "https://min-api.cryptocompare.com/data/v2/news/?lang=EN";

    @Value("${API_KEY}")
    private String apiKey;

    @Autowired
    private Repo repo;

    public ResponseEntity<String> fetch(String url) {
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

    public void saveArticles(List<Article> list) {
        // System.out.printf("Saving >> %s\n", list.toString());
        for (Article article : list) {
            repo.save(article);
        }
    }

    public void save(List<String> listOfIds) {
        repo.saveList(listOfIds);
    }

    public List<Article> getArticles() {
        String url = UriComponentsBuilder.fromUriString(baseUrl)
                // .queryParam("categories", "ALL_NEWS_CATEGORIES")
                // .queryParam("api_key", apiKey)
                .toUriString();

        // Fetch API
        ResponseEntity<String> res = fetch(url);
        // Get response:
        String payload = res.getBody();
        // Read response:
        Reader reader = new StringReader(payload);
        JsonReader jr = Json.createReader(reader);
        JsonObject jo = jr.readObject();
        System.out.println(jo.getString("Message"));
        JsonArray data = jo.getJsonArray("Data");

        // Manipulate response
        List<Article> resList = new LinkedList<>();
        for (int i = 0; i < data.size(); i++) {
            Article article = new Article();
            JsonObject dataJo = data.getJsonObject(i);
            resList.add(article.createFromJsonObj(dataJo));
        }

        // Save initial list of all the articles
        saveArticles(resList);

        return resList;
    }

    public JsonObject getArticles(String id) {
        Optional<String> article = repo.get(id);
        if (article.isEmpty()) {
            String value = "Cannot find news article " + id;
            JsonObject serverResponse = Json.createObjectBuilder()
                    .add("error", value)
                    .build();
            return serverResponse;
        } else {
            String payload = article.get();
            Reader reader = new StringReader(payload);
            JsonReader jr = Json.createReader(reader);
            JsonObject response = jr.readObject();
            return response;
        }
    }
}