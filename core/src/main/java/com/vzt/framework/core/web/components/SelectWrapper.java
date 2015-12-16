package com.vzt.framework.core.web.components;

import org.openqa.selenium.support.ui.Select;

import com.vzt.framework.core.util.ElementLocatorWrapper;
import com.vzt.framework.core.web.WebPage;

/**
 * Represents a hyper link component 
 * @author prokarma
 * @verion 1.0
 */
public class SelectWrapper extends WebBaseElement {
    
	/**
     * @param element
     */
    public SelectWrapper(ElementLocatorWrapper wrapper,WebPage page) {
        super(wrapper,page,"select");
    }

    /**
     * Returns the select component for the given select box.
     * @return
     */
    public Select get() {
    	return new Select(getElement());
    }
}
