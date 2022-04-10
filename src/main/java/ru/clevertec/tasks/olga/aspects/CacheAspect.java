package ru.clevertec.tasks.olga.aspects;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;
import ru.clevertec.tasks.olga.cache.Cache;
import ru.clevertec.tasks.olga.entity.AbstractModel;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Aspect
@Component
public class CacheAspect {

    Cache<Long, AbstractModel> cache;

    @Autowired
    public void setCache(Cache<Long, AbstractModel> cache) {
        this.cache = cache;
    }

    @Pointcut("@annotation(ru.clevertec.tasks.olga.annotation.UseCache)")
    public void useCache() {
    }

    @SuppressWarnings("unchecked")
    @Around("useCache() && execution(* *..find*(..))")
    public Object getTriggered(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] arguments = joinPoint.getArgs();
        Optional<AbstractModel> el = Optional.ofNullable(cache.get((Long)arguments[0]));
        if (el.isPresent()){
            return el;
        } else {
            Optional<AbstractModel> entity = (Optional<AbstractModel>)joinPoint.proceed(arguments);
            entity.ifPresent(abstractModel -> cache.put(abstractModel.getId(), abstractModel));
            entity.ifPresent(abstractModel -> log.info(abstractModel + "put to cache"));
            return entity;
        }
    }

    @Around("useCache() && execution(* *..save*(..))")
    public Object saveTriggered(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] arguments = joinPoint.getArgs();
        long res = (Long) joinPoint.proceed(arguments);
        AbstractModel model = (AbstractModel) arguments[0];
        model.setId(res);
        cache.put(res, model);
        log.info(model + "put to cache");
        return res;
    }

    @Around("useCache() && execution(* *..delete*(..))")
    public Object deleteTriggered(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] arguments = joinPoint.getArgs();
        boolean result = (boolean) joinPoint.proceed(arguments);
        log.info(cache.get((Long)arguments[0]) + "removed from cache");
        cache.remove((Long)arguments[0]);
        return result;
    }

    @Around("useCache() && execution(* *..update*(..))")
    public Object updateTriggered(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] arguments = joinPoint.getArgs();
        AbstractModel entity = (AbstractModel)arguments[0];
        boolean res = (boolean) joinPoint.proceed(arguments);
        cache.put(entity.getId(), entity);
        log.info(entity + "put to cache");
        return res;
    }

}
