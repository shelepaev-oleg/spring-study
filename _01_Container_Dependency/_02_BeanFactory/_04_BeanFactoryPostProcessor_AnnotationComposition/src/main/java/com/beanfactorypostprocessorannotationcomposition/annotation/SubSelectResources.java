package com.beanfactorypostprocessorannotationcomposition.annotation;

import org.hibernate.annotations.Subselect;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Subselect("")
public @interface SubSelectResources {

    @AliasFor(annotation = Subselect.class, attribute = "value")
    String value();
}
