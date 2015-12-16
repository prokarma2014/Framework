package com.vzt.framework.core.util;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.util.List;

import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.FieldDecorator;

/**
 * Represents a special field type decorator that needs to be extended by platform specific implementation to provide
 * customization. The decorator enables decoration of custom type objects e.g. Textbox, Link as opposed to
 * WebElement,MobileElement to provide more context senstive APIs.
 * 
 * @author prokarma
 * @verion 1.0
 */
public abstract class SpecialFieldTypeDecorator implements FieldDecorator{

    /**
     * Default Element Factory.
     */
    protected final ElementLocatorFactory factory;
    /**
     * Default web page.
     */
    protected final Object webPage;
    /**
     * Defaul field decorator
     */
    protected final FieldDecorator defaultFieldDecorator;
    protected final Class<?> pageType;

    /**
     * @param webPage
     * @param pageType
     */
    public SpecialFieldTypeDecorator(Object webPage, Class<?> pageType,ElementLocatorFactory factory,FieldDecorator decorator) {
        this.webPage = webPage;
        this.pageType = pageType;
        this.factory = factory;
        this.defaultFieldDecorator = decorator;
    }


    /**
     * Defines a special type decorator
     */
    public Object decorate(ClassLoader classLoader, Field field) {
        Object createdElement = null;
        ElementLocator elementLocator = factory.createLocator(field);
        if (isSupportedAnnotation(field) && canHandleType(field)) {
            if (isListType(field)) {
                createdElement = Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[] { List.class },
                        new ListPageElementsHandler(elementLocator, field, webPage, pageType));
            } else {
                createdElement = BaseElementTypeFactory.createInstanceOfType(field, new ElementLocatorWrapper(
                        elementLocator), webPage, pageType);
            }
        } else {
            createdElement = defaultFieldDecorator.decorate(classLoader, field);
        }
        return createdElement;
    }

    /**
     * Returns true if the annotation is supported.
     * @param field
     * @return
     */
    protected boolean isSupportedAnnotation(Field field) {
        // have to replicate logic to support custom types as default field locator.
        boolean isSupported = true;
        if (field.getAnnotation(FindBy.class) == null && field.getAnnotation(FindBys.class) == null
                && field.getAnnotation(FindAll.class) == null) {
            isSupported = false;
        }
        return isSupported;
    }

    private boolean canHandleType(Field field) {
        boolean supportedType = isSupportedType(field.getType());
        if (!supportedType && isListType(field)) {
            if (field.getGenericType() != null) {
                ParameterizedType typeOfList = (ParameterizedType) field.getGenericType();
                supportedType = isSupportedType((Class<?>) typeOfList.getActualTypeArguments()[0]);
            }
        }
        return supportedType;
    }

    private boolean isListType(Field field) {
        return List.class.equals(field.getType());
    }

    /**
     * Returns true if the specific type is supported.
     * @param type
     * @return
     */
    protected abstract boolean isSupportedType(Class<?> type);
}