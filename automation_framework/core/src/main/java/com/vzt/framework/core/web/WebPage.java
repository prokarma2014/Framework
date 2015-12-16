package com.vzt.framework.core.web;

import java.util.concurrent.TimeUnit;

import com.vzt.framework.core.util.WebSpecialTypeFieldDecorator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

/**
 * Represents the base page object that is capable of initializing page elements and provider simple wrapper around
 * around actions.
 * 
 * @author prokarma
 * @verion 1.0
 */
public class WebPage {

    private final WebDriver webDriver;
    private final Actions actions;
    
    private final SoftAssert pageValidator;
    /**
     * @param webDriver
     */
    public WebPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.actions = new Actions(webDriver);
        this.pageValidator = new SoftAssert();
        initPage();
    }

    private void initPage() {
        WebSpecialTypeFieldDecorator decorator =  new WebSpecialTypeFieldDecorator(this);
        PageFactory.initElements(decorator, this);
        validate();
    }

    /**
     * Validate method is called once the page is initialized, sub classes need to override this method for verifying if
     * the page is valid.
     */
    protected void validate() {
        // no implementation.
    }

    /**
     * Returns actions class that will return the gestures that can be done on the web elements.
     * 
     * @return
     */
    protected Actions exec() {
        return actions;
    }

    /**
     * Performs mouse actions on the given element
     * 
     * @param element
     */
    protected void mouseOver(WebElement element) {
        actions.moveToElement(element).perform();
    }

    /**
     * Returns the page validator that can be used to vailade page details.
     * 
     * @return the pageValidator
     */
    public SoftAssert validator() {
        return pageValidator;
    }

    /**
     * @return the webDriver
     */
    public WebDriver getDriver() {
        return webDriver;
    }

    /**
     * Wait for condition usign the fluent driver
     * 
     * @param waitCondition
     */
    public void waitFor(ExpectedCondition<?> waitCondition, long timeout) {
        new WebDriverWait(webDriver, timeout).pollingEvery(500, TimeUnit.MILLISECONDS).until(waitCondition);
    }


    /**
     * Returns the title associated with web page.
     * 
     * @return
     */
    public String title() {
        return webDriver.getTitle();
    }

    /**
     * Verifies if the page is valid or not using the asserts.
     * 
     * @return
     */
    public boolean isPageValid() {
        boolean isValid = false;
        try {
            pageValidator.assertAll();
            isValid = true;
        } catch (AssertionError error) {
            isValid = false;
        }
        return isValid;
    }
}
