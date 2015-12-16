package com.vzt.framework.core.mobile.components;

import  com.vzt.framework.core.mobile.MobilePage;
import  com.vzt.framework.core.util.ElementLocatorWrapper;

/**
 * Class represents the page title, proper locators need to be provided.
 * @author prokarma
 * @verion 1.0
 */
public class PageTitle extends TapableContent {

    /**
     * Initialize with page and locator wrapper.
     * @param page
     * @param wrapper
     */
    public PageTitle(ElementLocatorWrapper wrapper,MobilePage page) {
        super(wrapper, page);
    }
    
    /**
     * Returns the title associated with the page. 
     * @return
     */
    public String getTitle() {
        return waitUntilVisible().content();
    }
}
