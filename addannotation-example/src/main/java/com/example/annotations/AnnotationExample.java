package com.example.annotations;

import java.lang.annotation.*;

/**
 * Created by h3nrique on 25/04/16.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.PACKAGE, ElementType.PARAMETER, ElementType.TYPE})
public @interface AnnotationExample {
    String value();
}