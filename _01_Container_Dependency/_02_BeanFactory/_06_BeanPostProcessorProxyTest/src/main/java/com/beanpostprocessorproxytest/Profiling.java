package com.beanpostprocessorproxytest;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Аннотация, установив который Spring начнет профилировать вызовы методов.
 * Профилирование - синоним логирования.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Profiling {
}
