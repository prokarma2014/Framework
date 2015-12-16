package com.vzt.framework.core.web.components;

import  com.vzt.framework.core.util.ElementLocatorWrapper;
import com.vzt.framework.core.web.WebPage;

/**
 * Represents a text box in web page.
 * 
 * @author prokarma
 * @verion 1.0
 */
public class TextBox extends WebBaseElement {

    /**
     * Intialize with web element and base web page.
     * 
     * @param element
     * @param page
     */
    public TextBox(ElementLocatorWrapper wrapper, WebPage page) {
        super(wrapper, page, "input");
    }
    
    /**
     * Enters the text in the text box
     */
    public TextBox enterText(String textBox) {
        getElement().sendKeys(textBox);
        return this;
    }
    
    /**
     * Clears the given text.
     */
    public TextBox clear(){
        getElement().clear();
        return this;
    }
}
