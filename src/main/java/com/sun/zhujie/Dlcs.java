package com.sun.zhujie;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(METHOD)
public @interface Dlcs {
	
	int seconds();
	int maxCount();
	boolean needLogin() default true;
}
