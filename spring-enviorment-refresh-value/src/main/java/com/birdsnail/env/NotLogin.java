package com.birdsnail.env;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NotLogin {

    /**
     * 指定白名单的path
     */
    String path() default "";

}
