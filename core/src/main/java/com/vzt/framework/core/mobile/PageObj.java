package com.vzt.framework.core.mobile;

import  com.vzt.framework.core.exception.FrameworkException;
import  com.vzt.framework.core.util.ObjectCreatorUtil;
import org.openqa.selenium.WebDriver;

import com.google.common.base.Optional;

/**
 * Represents a generic page object. This class enables fluent api and page chaining allowing pages classes to return
 * page objects that can be bound to the actual page class at a later point in time.
 * 
 * @author prokarma
 * @version 1.0
 */
public class PageObj {

    private final WebDriver driver;

    /**
     * Initialize web driver.
     * 
     * @param driver
     */
    public PageObj(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Returns a instance of the given page class initialized with relevant parameters.
     * 
     * @param pageClass
     * @return
     */
    public <T> T get(Class<T> pageClass, Object... parameters) {
        if (parameters == null) {
            throw new IllegalArgumentException("Null parameters are provided to the page class");
        }
        Object[] params = new Object[parameters.length + 1];
        params[0] = driver;
        System.arraycopy(parameters, 0, params, 1, parameters.length);
        Optional<T> pageObj = ObjectCreatorUtil.selectConstructorAndInvoke(pageClass, params);
        if (!pageObj.isPresent()) {
            throw new FrameworkException("Expected page object is not found with required parameters in page class "
                    + pageClass);
        }
        return pageObj.get();
    }

}
