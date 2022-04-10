package ru.clevertec.tasks.olga.config;

import lombok.SneakyThrows;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.MultiValueMap;
import ru.clevertec.tasks.olga.cache.CacheStrategy;
import java.util.Objects;

import static ru.clevertec.tasks.olga.util.Constant.CACHING_ALG_ANNOTATION_PACKAGE;

public class CacheConditional implements Condition {
    @SneakyThrows
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        String algorithmProperty = context.getEnvironment().getProperty("cache.algorithm");
        MultiValueMap<String, Object> attributes = metadata.getAllAnnotationAttributes(
                CACHING_ALG_ANNOTATION_PACKAGE);
        if (Objects.nonNull(attributes)) {
            return attributes.get("value").stream()
                    .anyMatch(it -> it.equals(CacheStrategy.valueOf(algorithmProperty)));
        }
        return false;
    }
}
