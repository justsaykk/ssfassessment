package cryptonews.cryptonews.repository;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import cryptonews.cryptonews.models.Article;

@Repository
public class Repo {

    @Autowired
    @Qualifier("repository")
    private RedisTemplate<String, String> repo;

    public void save(Article article) {
        ValueOperations<String, String> ops = repo.opsForValue();
        String key = article.getId();
        Duration timeout = Duration.ofSeconds(120);
        String value = article.toValueString();
        ops.set(key, value, timeout);
        System.out.printf("Saved key: %s\n value: %s\n\n", key, value);
    }

    public String get(String id) {
        ValueOperations<String, String> ops = repo.opsForValue();
        String value = ops.get(id);

        if (null == value) {
            return null;
        } else {
            return value;
        }
    }
}
