package com.vzt.test.core;


import  com.vzt.framework.core.annotations.Data;

/**
 * Sample test object
 * @author prokarma
 * @version 1.0
 */
public class TestObj4 {
    
    @Data(name="field-obj2")
    private TestObj2 obj2;


    /**
     * @return the obj2
     */
    public TestObj2 getObj2() {
        return obj2;
    }
}
