package com.birdsnail.springbootdemo.annotation;

import java.lang.annotation.*;

/**
 * 标记一个方法是yhdjob
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface YhdJob {
}
