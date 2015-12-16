package com.vzt.framework.core.actions;

import java.lang.reflect.Method;

/**
 * Represents a test context class that will be used by the test setup actions to setup various test data.
 * @author prokarma
 * @verion 1.0
 */
public class TestContext {
    private final Method testMethod;
    private TestControl control;

    /**
     * Initialize with method being executed.
     * @param testMethod
     */
    public TestContext(Method testMethod) {
        this.testMethod = testMethod;
    }

    /**
     * Return test method.
     * @return
     */
    public Method getTestMethod() {
        return testMethod;
    }

    /**
     * @return the control
     */
    public TestControl getControl() {
        return control;
    }

    /**
     * @param control the control to set
     */
    public void setControl(TestControl control) {
        this.control = control;
    }
}