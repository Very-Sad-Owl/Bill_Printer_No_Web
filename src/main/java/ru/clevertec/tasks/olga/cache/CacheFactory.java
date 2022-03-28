package ru.clevertec.tasks.olga.cache;

import lombok.Getter;
import org.yaml.snakeyaml.Yaml;
import ru.clevertec.tasks.olga.cache.impl.LfuCache;
import ru.clevertec.tasks.olga.cache.impl.LruCache;
import ru.clevertec.tasks.olga.entity.AbstractModel;

import java.io.InputStream;
import java.util.Map;
import java.util.ResourceBundle;

import static ru.clevertec.tasks.olga.util.Constant.*;

public class CacheFactory {

    private static final CacheFactory INSTANCE = new CacheFactory();
    @Getter
    private final Cache<Long, AbstractModel> cache;
    private final CacheDeletionStrategy strategy;
    private final int capacity;

    {
        Yaml yaml = new Yaml();
        InputStream inputStream = this.getClass().getClassLoader()
                .getResourceAsStream(
                        ResourceBundle.getBundle("db").getString("cache_data.source")
                );
        Map<String, Object> obj = yaml.load(inputStream);
        strategy = CacheDeletionStrategy.valueOf(obj.get(CACHE_ALG).toString());
        capacity = Integer.parseInt(obj.get(CACHE_CAPACITY).toString());
    }

    private CacheFactory() {
        switch (strategy) {
            case LFU:
                cache = new LfuCache<>(capacity);
                break;
            case LRU:
                cache = new LruCache<>(capacity);
                break;
            default:
                throw new IllegalArgumentException("error.illegal_strategy");
        }
    }

    public static CacheFactory getInstance(){
        return INSTANCE;
    }

}