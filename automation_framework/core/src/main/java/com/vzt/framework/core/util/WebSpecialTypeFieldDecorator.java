package com.vzt.framework.core.util;

import  com.vzt.framework.core.web.WebPage;
import  com.vzt.framework.core.web.components.WebBaseElement;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.DefaultFieldDecorator;

/**
 * Represents the web special type decorator that creates different types such as Link, TextBox etc.
 * @author prokarma
 * @verion 1.0
 */
public class WebSpecialTypeFieldDecorator extends SpecialFieldTypeDecorator {

    /**
     * @param driver
     */
    public WebSpecialTypeFieldDecorator(WebPage webPage) {
        super(webPage, WebPage.class, new DefaultElementLocatorFactory(webPage.getDriver()),
                new DefaultFieldDecorator(new DefaultElementLocatorFactory(webPage.getDriver())));
    }

    /**
     * Returns whether a specific type is supported or not. Subclass can override to support specific types.
     * 
     * @param type
     * @return
     */
    protected boolean isSupportedType(Class<?> type) {
        return WebBaseElement.class.isAssignableFrom(type);
    }
}