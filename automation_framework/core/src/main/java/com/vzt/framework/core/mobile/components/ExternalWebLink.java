package com.vzt.framework.core.mobile.components;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.iOSFindBy;

import  com.vzt.framework.core.mobile.MobilePage;
import  com.vzt.framework.core.util.ElementLocatorWrapper;

/**
 * Represents a extenal link that on click opens a browser.
 * 
 * @author prokarma
 * @verion 1.0
 */
public class ExternalWebLink extends TapableContent {

    
    @iOSFindBy(name = "Address")
    private MobileElement urlAddress;

    /**
     * Initialize the mobile page and then lookup certain element.
     * 
     * @param page
     * @param wrapper
     */
    public ExternalWebLink(ElementLocatorWrapper wrapper,MobilePage page) {
        super(wrapper, page);
        initializeSubElements();
    }

    /**
     * Opens the browser url.
     */
    public ExternalWebLink open() {
        tap();
        return this;
    }

    /**
     * Clicks on the given element and gets the address. Ensure the click is not already called as the browser would be
     * open and the element will not be visible.
     * 
     * @return
     */
    public String clickAndGetAddress() {
        open();
        return urlAddress.getText();
    }
}