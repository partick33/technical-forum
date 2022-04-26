package com.partick.forum.category.aspect;

import com.partick.forum.category.annoation.RedissionLock;
import com.partick.forum.category.annoation.RedissionRepeatLock;
import com.partick.forum.category.config.RedissionConfig;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * 分布式锁切面
 * @author partick_peng
 */
@Aspect
@Component
@Slf4j
public class RedissionLockAspect {

    @Resource
    private RedissionConfig redissionConfig;

    @Around(value = "@annotation(com.partick.forum.category.annoation.RedissionRepeatLock)")
    public Object redissionRepeatLockAop(ProceedingJoinPoint joinPoint) throws Throwable{
        RedissionRepeatLock annotation = (RedissionRepeatLock) getMyAnnotation(joinPoint, RedissionRepeatLock.class);
        String lockName = annotation.lockName();
        int waitTime = annotation.waitTime();
        int expireTime = annotation.expireTime();
        RedissonClient client = redissionConfig.getRedissonClient();
        RLock lock = client.getLock(lockName);
        boolean tryLock = lock.tryLock(waitTime, expireTime, TimeUnit.SECONDS);
        if (!tryLock) {
            log.error("获取分布式锁{}失败", lockName);
            throw new RuntimeException("获取分布式锁失败");
        }
        return joinPoint.proceed();
    }

    @Around(value = "@annotation(com.partick.forum.category.annoation.RedissionLock)")
    public Object redissionLockAop(ProceedingJoinPoint joinPoint) throws Throwable{
        RedissionLock annotation = (RedissionLock) getMyAnnotation(joinPoint, RedissionLock.class);
        String lockName = annotation.lockName();
        int expireTime = annotation.expireTime();
        RedissonClient client = redissionConfig.getRedissonClient();
        RLock lock = client.getLock(lockName);
        boolean tryLock = lock.tryLock(30,expireTime, TimeUnit.SECONDS);
        int count = lock.getHoldCount();
        if (!tryLock || count > 1) {
            log.error("获取分布式锁{}失败", lockName);
            throw new RuntimeException("获取分布式锁失败");
        }
        return joinPoint.proceed();
    }

    private Annotation getMyAnnotation(ProceedingJoinPoint joinPoint, Class<? extends Annotation> annotationClass) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        return method.getAnnotation(annotationClass);
    }
}
