package com.vzt.framework.core.actions;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Provides ability to manage the test context associated with each test execution. Provides a way attaching state
 * information for executing test methods that can be referred and utilized in other parts of the framework.
 * 
 * @author prokarma
 * @version 1.0
 */
public class TestContextManager {

    private static final TestContextManager singleton = new TestContextManager();

    private Map<String, TestContext> contextMap = new ConcurrentHashMap<String, TestContext>();

    /**
     * Returns the singleton instance of the context manager.
     * 
     * @return
     */
    public static TestContextManager getInstance() {
        return singleton;
    }

    /**
     * Returns the context associated with the test method.
     * 
     * @param testMethod
     * @return
     */
    public TestContext getContext(Method testMethod) {
        TestContext context = contextMap.get(testMethod.getName());
        if (context == null) {
            //double checked locking
            synchronized (testMethod.getName().intern()) {
                if (context == null) {
                    context = new TestContext(testMethod);
                    contextMap.put(testMethod.getName(), context);
                }
            }
        }
        return context;
    }

}
