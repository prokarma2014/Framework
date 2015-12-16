package com.vzt.test.core;

import java.util.Calendar;

import  com.vzt.framework.core.annotations.Data;

public class TestDataObj1 {

    @Data(name = "Val1")
    private String value;
    @Data(name = "Val2")
    private float value2;
    @Data(name = "Val3")
    private double value3;
    @Data(name = "Val4")
    private Calendar date;
    
    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }
    /**
     * @return the value2
     */
    public float getValue2() {
        return value2;
    }
    /**
     * @return the value3
     */
    public double getValue3() {
        return value3;
    }
    /**
     * @return the date
     */
    public Calendar getDate() {
        return date;
    }
    
    
}
