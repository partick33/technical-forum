package com.partick.forum.category.annoation;

import java.lang.annotation.*;

/**
 * @author partick_peng
 */
@Target(ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface RedissionLock {

    int expireTime();

    String lockName();
}
