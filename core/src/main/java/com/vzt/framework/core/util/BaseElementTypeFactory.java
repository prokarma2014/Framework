package com.vzt.framework.core.util;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.openqa.selenium.NoSuchElementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Creates an instances of type base element.
 * 
 * @author prokarma
 * @verion 1.0
 */
public class BaseElementTypeFactory {

    private static final Logger logger = LoggerFactory.getLogger(BaseElementTypeFactory.class);

    /**
     * Utility class that creates an instance of any sub-type of BaseElement. The sub-type should support constructor of
     * elementlocatorwrapper and web page. Constructor(ElementLocatorWrapper wrapper, WebPage webPage).
     * 
     * @param field
     * @param elementLocator
     * @param pageObj
     * @return
     */
    public static Object createInstanceOfType(Field field, ElementLocatorWrapper wrapper, Object pageObj,
            Class<?> pageType) {
        try {
            logger.debug("Type is {}", field.getType());
            return getType(field).getConstructor(ElementLocatorWrapper.class, pageType).newInstance(wrapper, pageObj);
        } catch (Exception excp) {
            logger.error("Error while creating instance of a base element for field {} in class {}.", field.getName(),
                    field.getDeclaringClass().getName(), excp);
            throw new NoSuchElementException("Error while creating the wrapper type", excp);
        }
    }

    private static Class<?> getType(Field field) {
        Class<?> supportedType = null;
        if (isListType(field)) {
            if (field.getGenericType() != null) {
                ParameterizedType typeOfList = (ParameterizedType) field.getGenericType();
                supportedType = (Class<?>) typeOfList.getActualTypeArguments()[0];
            }
        } else {
            supportedType = field.getType();
        }
        return supportedType;
    }

    private static boolean isListType(Field field) {
        return List.class.equals(field.getType());
    }
}