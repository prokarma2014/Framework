package com.vzt.framework.core.testdrivers;

import java.util.Map;

/**
 * The class responsible for creating and configuring appropriate driver object given the test properties and test parameters. 
 * The create method creates an instance of the driver using the test properties. Implementation can get access to the test properties
 * by declaring a constructor with parameter as test properties.
 * @author prokarma
 * @verion 1.0
 */
public interface DriverFactory<T> {

    /**
     * Creates a new instance of driver and initialized with relevant properties provided as part of the properties
     * object.
     * 
     * @param testProperties
     *            test properties
     * @return test driver.
     */
    public T create(Map<String, String> testParameters);
    
}
