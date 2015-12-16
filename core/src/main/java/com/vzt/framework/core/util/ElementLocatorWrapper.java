package com.vzt.framework.core.util;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

/**
 * A lighweight wrapper around element locator that provides a mapping between a element locator and the web element.
 * Each web element will a single wrapper locator. The locator wrapper handles the logic of using the list locator or
 * single element locator when looking up elements.
 * 
 * @author prokarma
 * @verion 1.0
 */
public class ElementLocatorWrapper {

    private final ElementLocator elementLocator;
    private final boolean isListMode;
    private int listIndex;

    /**
     * Initializw with element locator.
     * 
     * @param elementLocator
     */
    public ElementLocatorWrapper(ElementLocator elementLocator) {
        this.elementLocator = elementLocator;
        isListMode = false;
    }

    /**
     * Initializw with element locator.
     * 
     * @param elementLocator
     */
    public ElementLocatorWrapper(ElementLocator elementLocator, int listIndex) {
        this.elementLocator = elementLocator;
        isListMode = true;
        this.listIndex = listIndex;
    }

    /**
     * Finds the web element using the locator.
     * 
     * @return found web element.
     */
    public WebElement findElement() {
        WebElement element = null;
        if (isListMode) {
            element = elementLocator.findElements().get(listIndex);
        } else {
            element = elementLocator.findElement();
        }
        return element;
    }
}
