package com.vzt.test.core;


import  com.vzt.framework.core.annotations.Data;

/**
 * Sample test object
 * @author prokarma
 * @version 1.0
 */
public class TestObj3 {

    @Data(name="field-obj3")
    private TestObj4 obj4;

    /**
     * @return the obj1
     */
    public TestObj4 getObj4() {
        return obj4;
    }
   
}
