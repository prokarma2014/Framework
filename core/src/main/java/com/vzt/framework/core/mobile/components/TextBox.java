package com.vzt.framework.core.mobile.components;

import  com.vzt.framework.core.mobile.MobilePage;
import  com.vzt.framework.core.util.ElementLocatorWrapper;

/**
 * Represents a text box which can be used to enter text details.
 * 
 * @author prokarma
 * @verion 1.0
 */
public class TextBox extends TapableContent {

    /**
     * Initialize the text box.
     * 
     * @param page
     * @param wrapper
     */
    public TextBox(ElementLocatorWrapper wrapper,MobilePage page) {
        super(wrapper,page);
    }

    /**
     * Clears the given text element.
     * 
     * @return
     */
    public TextBox clear() {
        getElement().clear();
        return this;
    }

    /**
     * Enters a text value.
     * 
     * @return
     */
    public TextBox enterText(String text) {
        getElement().sendKeys(text);
        return this;
    }
}
