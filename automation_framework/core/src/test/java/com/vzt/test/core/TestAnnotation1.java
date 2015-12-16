package com.vzt.test.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Dummy annotation to verify retrieval of data from the field annotation retriever.
 * * @author prokarma
 * @version 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value=ElementType.FIELD)
public @interface TestAnnotation1 {

}
