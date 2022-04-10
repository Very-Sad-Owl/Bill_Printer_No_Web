package ru.clevertec.tasks.olga.annotation;

import ru.clevertec.tasks.olga.cache.CacheStrategy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CachingAlgorithm {
    CacheStrategy value() default CacheStrategy.LFU;
}
