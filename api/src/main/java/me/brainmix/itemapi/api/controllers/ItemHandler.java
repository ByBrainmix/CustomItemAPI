package me.brainmix.itemapi.api.controllers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ItemHandler {

    // if set to true, the events get handled if an delay is running
    boolean delay() default false;

}
