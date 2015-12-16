package com.vzt.framework.core.exception;

/**
 * Thrown incase of page refresh failures and element refresh errors.
 * @author prokarma
 *
 */
public class PageRefreshException extends FrameworkException {

    /**
     * @param message
     */
    public PageRefreshException(Class<?> pageClass,String fieldName) {
        super("Error while refreshing the field " + fieldName + " in page " + pageClass.getName());
    }

}
