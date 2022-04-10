package ru.clevertec.tasks.olga.config;

import org.aspectj.lang.Aspects;
import org.springframework.context.annotation.*;
import ru.clevertec.tasks.olga.aspects.CacheAspect;

@Configuration
public class AspectConfig {

    @Bean
    public CacheAspect theAspect() {
        return Aspects.aspectOf(CacheAspect.class);
    }

}
