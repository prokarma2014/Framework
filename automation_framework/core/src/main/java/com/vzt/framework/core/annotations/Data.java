package com.vzt.framework.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to specify test data to retrieved. The name attribute represents the logical name for test data. 
 * @author prokarma
 * @version 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.FIELD,ElementType.PARAMETER})
@Inherited
public @interface Data {
    
    /**
     * Represents the logical name of the test data. The name would be used to lookup in datasheet for matching column.
     * @return
     */
    public String name() default ""; 
    
    /**
     * Optional attribute to specify the format of the expected data, useful for specifying date formats.
     * Not implemented as of yet.
     * @return
     */
    public String format() default "";
    
    /**
     * Optional attribute used to specify additional context for retrieving the test data. Useful in scenarios where
     * the same type of test data is being requested twice as part of the test method, in such cases context can be
     * used to segregate the test data further. 
     * @return
     */
    public String contextPrefix() default "";
}
