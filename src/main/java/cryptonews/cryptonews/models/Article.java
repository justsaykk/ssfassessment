package cryptonews.cryptonews.models;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Article {
    private String id;
    private String publishedOn;
    private String title;
    private String url;
    private String imageUrl;
    private String body;
    private String tags;
    private String categories;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPublishedOn() {
        return publishedOn;
    }

    public void setPublishedOn(String publishedOn) {
        this.publishedOn = publishedOn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String toValueString() {
        StringBuilder stb = new StringBuilder();
        String valueString = stb
                .append("{ id:" + id + ",")
                .append("title:" + title + ",")
                .append("body:" + body + ",")
                .append("published_on:" + publishedOn + ",")
                .append("url" + url + ",")
                .append("imageurl:" + imageUrl + ",")
                .append("tags:" + tags + ",")
                .append("categories:" + categories + "}")
                .toString();
        System.out.printf(">> valueString >> %s \n\n\n", valueString);
        return valueString;
    }

    public JsonObject toJsonObject() {
        JsonObject jo = Json.createObjectBuilder()
                .add("id", id)
                .add("title", title)
                .add("body", body)
                .add("published_on", publishedOn)
                .add("url", url)
                .add("imageurl", imageUrl)
                .add("tags", tags)
                .add("categories", categories)
                .build();

        return jo;
    }
}
