package ru.clevertec.tasks.olga.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.*;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static ru.clevertec.tasks.olga.util.Constant.BASE_PACKAGES_TO_SCAN;

@Configuration
@ComponentScan(
        basePackages = BASE_PACKAGES_TO_SCAN,
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ANNOTATION,
                value = EnableWebMvc.class
        ))
@PropertySource("classpath:application.yml")
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class AppConfig {

    @Bean
    public Gson gson() {
        return new GsonBuilder()
                .registerTypeAdapter(LocalDate.class,
                        (JsonSerializer<LocalDate>) (localDateTime, srcType, context) ->
                                new JsonPrimitive(DateTimeFormatter.ofPattern("dd-MM-yyyy").format(localDateTime)))
                .registerTypeAdapter(LocalDate.class,
                        (JsonDeserializer<LocalDate>) (json, typeOfT, context) ->
                                LocalDate.parse(json.getAsString(),
                                        DateTimeFormatter.ofPattern("dd-MM-yyyy").withLocale(Locale.ENGLISH)))
                .create();
    }

    @Bean
    public ObjectMapper mapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer configurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

//    @Bean
//    public Cache<Long, AbstractModel> cache(){
//        CacheStrategy alg = CacheStrategy.valueOf(env.getProperty("cache.algorithm").toUpperCase());
//        int capacity = Integer.parseInt(env.getProperty("cache.capacity"));
//
//        switch (alg) {
//            case LFU:
//                return new LfuCache<>(capacity);
//            case LRU:
//                return new LruCache<>(capacity);
//            default:
//                throw new UndefinedException("xd");
//        }
//    }

//    @Bean
//    public LruCache<Long, AbstractModel> lru(){
//        return new LruCache<>(10);
//    }
}
