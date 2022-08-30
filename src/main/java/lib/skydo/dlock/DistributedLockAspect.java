package lib.skydo.dlock;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.support.locks.LockRegistry;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

@Aspect
@Component
public class DistributedLockAspect {

    @Autowired
    @Qualifier("redis") LockRegistry lockRegistry;

    Logger log = LoggerFactory.getLogger(DistributedLockAspect.class);

    @Pointcut("within(@lib.skydo.dlock.DistributedLock *)")
    public void distributedLock() {}

    @Pointcut("execution(public * *(..))")
    public void publicMethod() {}

    @Around("publicMethod() && @within(distributedLock)")
    public Object doUnderLock(ProceedingJoinPoint pjp, DistributedLock distributedLock) throws Throwable {

        Lock lock = null;
        boolean lockAcquired = false;

        try {
            long waitingTime = getWaitingTime(pjp);
            String key = getKey(pjp);

            log.info("Trying to Acquire Lock for $key");
            lock = lockRegistry.obtain(key);
            lockAcquired = lock.tryLock(waitingTime, TimeUnit.SECONDS);
            if (!lockAcquired) {
                throw new Exception("Lock is not available for key:$key");
            }
            log.info("Successfully Acquired Lock");

            return runProceedingJoinPoint(pjp);

        } catch (DistributedProxyException distributedProxyException) {

            log.info("Error: PJP");
            throw distributedProxyException.getCause();

        } catch (Exception exception) {

            log.info(ExceptionUtils.getStackTrace(exception));
            throw new DistributedLockException("Failed to Acquire Lock ${e.message}");

        } finally {
            if (lockAcquired) {
                log.info("Releasing lock");
                lock.unlock();
            }
        }
    }

    private Object runProceedingJoinPoint(ProceedingJoinPoint pjp ) throws DistributedProxyException {
        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            throw new DistributedProxyException(throwable);
        }
    }


    private String getKey(ProceedingJoinPoint pjp) throws Exception {

        Object[] args = pjp.getArgs();

        Annotation[][] parameterAnnotations = ((MethodSignature) pjp.getSignature()).getMethod().getParameterAnnotations();

        assert(args.length == parameterAnnotations.length);

        for(int i = 0; i < args.length; i ++) {

            for(Annotation annotation : parameterAnnotations[i]) {

                if ((annotation instanceof LockKey) && (args[i] instanceof String) && !(args[i].toString()).isEmpty()) {

                    return args[i].toString();
                }
            }
        }

        throw new Exception("Invalid or Empty KeyVariable");
    }

    private long getWaitingTime(ProceedingJoinPoint pjp) {

        return 30L;

//        return ((MethodSignature) pjp.getSignature()).getMethod().getAnnotation(com.skydo.dlock.DistributedLock.class).timeout();
    }
}
