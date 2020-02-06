package com.library.library.annotation;

import java.lang.annotation.*;

/**
 * @Author: chenmingzhe
 * @Date: 2020/2/6 16:01
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Role {
    String value();
}
