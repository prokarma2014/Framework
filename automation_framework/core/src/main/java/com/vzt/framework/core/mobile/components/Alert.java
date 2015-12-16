package com.vzt.framework.core.mobile.components;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindAll;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSFindAll;
import io.appium.java_client.pagefactory.iOSFindBy;

import  com.vzt.framework.core.mobile.MobilePage;

/**
 * Represents the common alert message shown both in iOS and Android. A composite control.
 * 
 * @author prokarma
 * @verion 1.0
 */
public class Alert extends MobilePage {

    // Alert text content
    @AndroidFindAll({ @AndroidFindBy(id = "android:id/parentPanel")})
    @iOSFindAll({ @iOSFindBy(className="UIAAlert")})
    protected TapableContent content ;

    @iOSFindBy(xpath = "//UIAApplication[1]/UIAWindow[3]/UIAAlert[1]/UIAScrollView[1]/UIAStaticText[2]")
    @AndroidFindBy(id = "android:id/message")
    private TapableContent message;

    @iOSFindBy(name = "OK")
    @AndroidFindBy(name = "OK")
    private TapableContent okButton;

    @iOSFindBy(name = "CANCEL")
    @AndroidFindBy(name = "CANCEL")
    private TapableContent cancelButton;

    /**
     * Initialize page.
     * 
     * @param driver
     */
    public Alert(AppiumDriver<?> driver) {
        super(driver);
    }

    /**
     * Returns the message associated with the alert box.
     * 
     * @return
     */
    public String message() {
        return message.content();
    }

    /**
     * Selects a specific actions for the alert.
     * 
     * @param action
     */
    public Alert select(String action) {
        return this;
    }

}
