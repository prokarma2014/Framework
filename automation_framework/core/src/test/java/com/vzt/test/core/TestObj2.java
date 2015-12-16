package com.vzt.test.core;

import java.util.Calendar;

import  com.vzt.framework.core.annotations.Data;

/**
 * Sample test object
  @author prokarma
 * @version 1.0
 */
public class TestObj2 {

    @Data(name="field1")
    private String stringField;
    @Data(name="field2")
    private int intField;
    @Data(name="field3")
    private float floatField;
    @TestAnnotation1
    private Float floatField1;
    @TestAnnotation1
    @Data(name="field5")
    private long longField;
    @Data(name="field5")
    private Calendar cal;
    
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
    /**
     * @return the floatField1
     */
    public Float getFloatField1() {
        return floatField1;
    }
    /**
     * @return the longField
     */
    public long getLongField() {
        return longField;
    }
    /**
     * @return the cal
     */
    public Calendar getCal() {
        return cal;
    }
}
