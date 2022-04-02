package ru.clevertec.tasks.olga.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import ru.clevertec.tasks.olga.cache.Cache;
import ru.clevertec.tasks.olga.cache.CacheFactory;
import ru.clevertec.tasks.olga.entity.AbstractModel;

import java.util.Optional;

@Slf4j
@Aspect
public class CacheAspect {
    private final Cache<Long, AbstractModel> cache = CacheFactory.getInstance().getCache();

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
            return entity;
        }
    }

    @Around("useCache() && execution(* *..save*(..))")
    public Object saveTriggered(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] arguments = joinPoint.getArgs();
        AbstractModel res = (AbstractModel)joinPoint.proceed(arguments);
        cache.put(res.getId(), res);
        return res.getId();
    }

    @Around("useCache() && execution(* *..delete*(..))")
    public Object deleteTriggered(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] arguments = joinPoint.getArgs();

        boolean result = (boolean) joinPoint.proceed(arguments);

        cache.remove((Long)arguments[0]);
        return result;
    }

    @Around("useCache() && execution(* *..update*(..))")
    public Object updateTriggered(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] arguments = joinPoint.getArgs();
        AbstractModel entity = (AbstractModel)arguments[0];

        boolean res = (boolean) joinPoint.proceed(arguments);

        cache.put(entity.getId(), entity);
        return res;
    }

}
