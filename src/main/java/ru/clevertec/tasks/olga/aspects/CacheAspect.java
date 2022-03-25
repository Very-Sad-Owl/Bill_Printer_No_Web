package ru.clevertec.tasks.olga.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import ru.clevertec.tasks.olga.cache.Cache;
import ru.clevertec.tasks.olga.cache.CacheFactory;
import ru.clevertec.tasks.olga.model.AbstractModel;

@Aspect
public class CacheAspect {
    private final Cache<Long, AbstractModel> cache = CacheFactory.getInstance().getCache();

    @Pointcut("@annotation(ru.clevertec.tasks.olga.annotation.UseCache)")
    public void useCache() {
    }

    @Around("useCache() && execution(* *..find*(..))")
    public Object getTriggered(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] arguments = joinPoint.getArgs();
        AbstractModel el = cache.get((Long)arguments[0]);
        if (el == null){
            el = (AbstractModel)joinPoint.proceed(arguments);
            cache.put(el.getId(), el);
        }
        return el;
    }

    @Around("useCache() && execution(* *..save*(..))")
    public void saveTriggered(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] arguments = joinPoint.getArgs();

        joinPoint.proceed(arguments);

        cache.put(((AbstractModel)arguments[0]).getId(), (AbstractModel) arguments[0]);
    }

    @Around("useCache() && execution(* *..delete*(..))")
    public Object deleteTriggered(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] arguments = joinPoint.getArgs();

        boolean result = (boolean) joinPoint.proceed(arguments);

        cache.remove(((AbstractModel)arguments[0]).getId());
        return result;
    }

    @Around("useCache() && execution(* *..update*(..))")
    public Object updateTriggered(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] arguments = joinPoint.getArgs();

        AbstractModel entity = (AbstractModel) joinPoint.proceed(arguments);

        cache.put(entity.getId(), entity);
        return entity;
    }

}
