package com.vzt.framework.core.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.support.pagefactory.ElementLocator;

/**
 * Represents a array list type page element handlers.
 * 
 * @author prokarma
 * @verion 1.0
 */
public final class ListPageElementsHandler implements InvocationHandler {

    private final List<Object> webElements = new ArrayList<Object>();
    private final ElementLocator locator;
    private final Field field;
    private final Object webPage;
    private final Class<?> pageType;
    private boolean firstTime = true;

    /**
     * Initialize with locator, field and web page.
     * 
     * @param locator
     * @param field
     * @param webPage
     */
    public ListPageElementsHandler(ElementLocator locator, Field field, Object webPage, Class<?> pageType) {
        this.locator = locator;
        this.field = field;
        this.webPage = webPage;
        this.pageType = pageType;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
     */
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (firstTime) {
            for (int count = 0; count < locator.findElements().size(); count++) {
                ElementLocatorWrapper wrapper = new ElementLocatorWrapper(locator, count);
                webElements.add(BaseElementTypeFactory.createInstanceOfType(field, wrapper, webPage, pageType));
            }
            firstTime = false;
        }
        return method.invoke(webElements, args);
    }
}