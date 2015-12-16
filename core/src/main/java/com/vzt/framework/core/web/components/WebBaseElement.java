package com.vzt.framework.core.web.components;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.vzt.framework.core.util.ElementLocatorWrapper;
import  com.vzt.framework.core.web.WebPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;

/**
 * This class represents base components that wrap around generic selenium web elements. The base element provides a
 * more context specific apis and validation.
 * 
 * @author prokarma
 * @verion 1.0
 */
public class WebBaseElement {

    private WebElement element;
    private final ElementLocatorWrapper elementLocatorWrapper;
    private final Actions actions;
    private final String tagName;
    private final Map<String, String> props;
    private final WebPage page;

    /**
     * Intialized with element locator, page and tag name of web element.
     * @param element
     */
    public WebBaseElement(ElementLocatorWrapper elementLocatorWrapper, WebPage page, String tagName, Map<String, String> props) {
        this.elementLocatorWrapper = elementLocatorWrapper;
        this.actions = new Actions(page.getDriver());
        this.tagName = tagName;
        this.props = props;
        this.page = page;
    }
    
    /**
     * Intialized with element locator, page and tag name of web element.
     * @param element
     */
    public WebBaseElement(ElementLocatorWrapper elementLocatorWrapper, WebPage page, String tagName) {
        this(elementLocatorWrapper,page,tagName,new HashMap<String, String>());
    }

    /**
     * Validates the when the elements are valid.
     */
    public void validate() {
        page.validator().assertEquals(element.getTagName(), tagName);
        if (props != null) {
            for (Entry<String, String> expectedProp : props.entrySet()) {
                page.validator().assertEquals(expectedProp.getValue(), element.getAttribute(expectedProp.getKey()));
            }
        }
    }

    /**
     * @return the element
     */
    protected WebElement getElement() {
        if(this.element == null) {
            this.element = elementLocatorWrapper.findElement();
        }
        validate();
        return element;
    }

    /**
     * Returns actions class that will return the gestures that can be done on the web elements.
     * 
     * @return
     */
    public Actions exec() {
        return actions;
    }

    /**
     * Performs mouse actions on the given element
     * 
     * @param element
     */
    public WebBaseElement mouseOver() {
        actions.moveToElement(getElement()).perform();
        return this;
    }

    /**
     * Click on the specific element
     */
    public WebBaseElement click() {
        getElement().click();
        return this;
    }
    
    /**
     * Refresh the given web element.
     */
    public void refresh() {
        element = elementLocatorWrapper.findElement();
    }
    
    /**
     * Waits until given condition and then refreshes.
     * @param condition
     * @param timeout
     */
    public void refresh(ExpectedCondition<?> condition,long timeout) {
        page.waitFor(condition, timeout);
        element = elementLocatorWrapper.findElement();
    }
}