package cryptonews.cryptonews.repository;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import cryptonews.cryptonews.models.Article;

@Repository
public class Repo {

    @Autowired
    @Qualifier("repository")
    private RedisTemplate<String, String> repo;

    @Value("${spring.redis.timeout}")
    private long cacheTime;

    public void save(Article article) {
        ValueOperations<String, String> ops = repo.opsForValue();
        String key = article.getId();

        // Find if article id exists:
        Boolean articleInDb = repo.hasKey(key);

        // Save article if do not exists
        if (!articleInDb) {
            String value = article.toJsonObject(article).toString();
            Duration timeout = Duration.ofMinutes(cacheTime);
            ops.set(key, value, timeout);
            System.out.printf("Saving article Id: %s\n", key);
        }
    }

    public void saveList(List<String> listOfIds) {
        ValueOperations<String, String> ops = repo.opsForValue();
        List<String> listToPersist = ops.multiGet(listOfIds);
        for (String key : listToPersist) {
            repo.persist(key);
        }
    }

    public Optional<String> get(String id) {
        ValueOperations<String, String> ops = repo.opsForValue();
        String value = ops.get(id);

        if (null == value) {
            return Optional.empty();
        } else {
            return Optional.of(value);
        }
    }
}
