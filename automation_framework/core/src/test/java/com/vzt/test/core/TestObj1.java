package com.vzt.test.core;

import  com.vzt.framework.core.annotations.Data;

/**
 * Sample test object
  * @author prokarma
 * @version 1.0
 */
public class TestObj1 {

    @Data(name="field1")
    private String stringField;
    @Data(name="field2")
    private int intField;
    @Data(name="field3")
    private Float floatField;
    /**
     * @return the stringField
     */
    public String getStringField() {
        return stringField;
    }
    /**
     * @return the intField
     */
    public int getIntField() {
        return intField;
    }
    /**
     * @return the floatField
     */
    public Float getFloatField() {
        return floatField;
    }
    
    
}
