package com.vzt.test.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Calendar;
import java.util.Map;

import com.vzt.framework.core.BaseTest;
import  com.vzt.framework.core.annotations.Data;
import  com.vzt.framework.core.testdrivers.DriverFactory;
import com.vzt.test.core.TestDataObj1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

/**
 * Tests the basic data provider capabilities.
 * 
 * @author prokarma
 * @version 1.0
 */

public class BaseDataProviderTest extends BaseTest<Object> {

    private static final Logger logger = LoggerFactory.getLogger(BaseDataProviderTest.class);

    /**
     * Initialize with a dummy driver factory.
     */
    public BaseDataProviderTest() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see core.BaseTest#initPrimaryDriverFactory()
     */
    protected DriverFactory<Object> initPrimaryDriverFactory() {
        return new DriverFactory<Object>() {
            public Object create(Map<String, String> testParameters) {
                return new Object();
            };
        };
    }

    /**
     * Overloaded test data provider that can read the test data as long as sheet with the test name (testDataProvider)
     * is available as part of the excel sheet.
     * 
     * @param value
     * @param obj1
     */
    @Test(dataProvider = "default")
    public void testDataProvider1(@Data(name = "Val1") String value, @Data(name = "Val2") float value2,
            @Data(name = "Val3") double value3, @Data(name = "Val4") Calendar date) {
        logger.warn("Test warning!!!");
        assertNotNull(value);
        assertEquals("abcd", value);
        assertNotNull(value2);
        assertEquals(1212.01, value2, 0.001f);
        assertNotNull(value3);
        assertEquals(100.5, value3, 0.001f);
        assertNotNull(date);
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(2015, 10, 12);
        assertEquals(cal.getTime(), date.getTime());
    }

    /**
     * Overloaded test data provider that can read the test data as long as sheet with the test name (testDataProvider)
     * is available as part of the excel sheet.
     * 
     * @param value
     * @param obj1
     */
    @Test(dataProvider = "default")
    public void testDataProvider2(@Data(name = "Val1") String value, TestDataObj1 obj1) {
        assertNotNull(value);
        assertEquals("abcd", value);
        assertNotNull(obj1.getValue());
        assertEquals("abcd", obj1.getValue());
        assertNotNull(obj1.getValue2());
        assertEquals(1212.01, obj1.getValue2(), 0.001f);
        assertNotNull(obj1.getValue3());
        assertEquals(100.5, obj1.getValue3(), 0.001f);
        assertNotNull(obj1.getDate());
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(2015, 10, 12);
        assertEquals(cal.getTime(), obj1.getDate().getTime());
    }

}
