package ru.clevertec.tasks.olga.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import ru.clevertec.tasks.olga.cache.Cache;
import ru.clevertec.tasks.olga.cache.CacheStrategy;
import ru.clevertec.tasks.olga.cache.impl.LfuCache;
import ru.clevertec.tasks.olga.cache.impl.LruCache;
import ru.clevertec.tasks.olga.entity.AbstractModel;

@Configuration
@ConditionalOnMissingBean({LfuCache.class, LfuCache.class, Cache.class})
public class CacheConfig {

    @Autowired
    Environment environment;

    @Bean
    public Cache<Long, AbstractModel> cacheStrategy() {
        String strategy = environment.getProperty("cache.algorithm");
        int capacity = Integer.parseInt(environment.getProperty("cache.capacity"));
        switch (CacheStrategy.valueOf(strategy)) {
            case LFU:
                return new LfuCache<>(capacity);
            case LRU:
                return new LruCache<>(capacity);
            default:
                throw new IllegalArgumentException();
        }
    }
}
