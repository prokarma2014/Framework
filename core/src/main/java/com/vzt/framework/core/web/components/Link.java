package com.vzt.framework.core.web.components;

import com.vzt.framework.core.util.ElementLocatorWrapper;
import com.vzt.framework.core.web.WebPage;

/**
 * Represents a hyper link component 
 * @author prokarma
 * @verion 1.0
 */
public class Link extends WebBaseElement {
    /**
     * @param element
     */
    public Link(ElementLocatorWrapper wrapper,WebPage page) {
        super(wrapper,page,"a");
    }
    
    /**
     * Returns the hyperlink associated with element
     * @return
     */
    public String href() {
        return getElement().getAttribute("href");
    }
    
    /**
     * Returns the link text.
     * @return
     */
    public String desc() {
        return getElement().getText();
    }
}
