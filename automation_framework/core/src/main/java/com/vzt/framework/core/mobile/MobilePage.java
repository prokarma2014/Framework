package com.vzt.framework.core.mobile;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;

import java.util.concurrent.TimeUnit;

import  com.vzt.framework.core.util.MobileSpecialTypeFieldDecorator;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.asserts.SoftAssert;

/**
 * Represents the abstract page containing key api like swipe, click which will be inherited by page classes.
 * 
 * @author prokarma
 * @verion 1.0
 */
public abstract class MobilePage {

    private static final Logger logger = LoggerFactory.getLogger(MobilePage.class);

    private final AppiumDriver<?> driver;
    private final TouchAction actions;
    private final SoftAssert pageValidator;
    private final FluentWait<WebDriver> wait;

    /**
     * Initialize appium driver
     * 
     * @param driver
     */
    public MobilePage(AppiumDriver<?> driver) {
        this.driver = driver;
        this.actions = new TouchAction(driver);
        this.pageValidator = new SoftAssert();
        wait = new WebDriverWait(driver, 130).pollingEvery(500, TimeUnit.MILLISECONDS);
        initPage();
        logger.debug("Creating element {} ", this);
    }

    private void initPage() {
        MobileSpecialTypeFieldDecorator decorator = new MobileSpecialTypeFieldDecorator(this);
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
     * @return the webDriver
     */
    public AppiumDriver<?> getDriver() {
        return driver;
    }

    /**
     * Hides keyboard.
     */
    public void hideKeyboard() {
        try {
            driver.hideKeyboard();
        } catch (Exception e) {
            System.out.println("Keypad not displayed");
        }
    }

    /**
     * Verifies if the page is valid or not using the asserts.
     * 
     * @return
     */
    public boolean isPageValid() {
        boolean isValid = false;
        try {
            validate();
            pageValidator.assertAll();
            isValid = true;
        } catch (AssertionError error) {
            isValid = false;
        } catch(Exception excp) {
            isValid = false;
        }
        return isValid;
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
     * Wait for condition usign the fluent driver
     * 
     * @param waitCondition
     */
    public FluentWait<WebDriver> waitFor(ExpectedCondition<?> waitCondition) {
        wait.until(waitCondition);
        return wait;
    }

    /**
     * Performs tap on a particular location using the co-ordinates. *
     * 
     * @param Xval
     *            co-ordinate specifying the X-point.
     * @param Yval
     *            co-ordinate specifying the Y-point.
     */
    public void tapAt(Point location) {
        actions.tap(location.getX(), location.getY()).perform();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "MobilePage [" + (driver != null ? "driver=" + driver + ", " : "")
                + (getClass() != null ? "getClass()=" + getClass() : "") + "]";
    }

    /**
     * Returns the executable action.
     * 
     * @return
     */
    public TouchAction exec() {
        return actions;
    }

    /**
     * Returns the platform specific implementation
     * 
     * @param commonBase
     * @param iOSImpl
     * @param androidImpl
     * @return
     */
    protected <T> T selectPlatformSpecificPage(Class<T> commonBase, T iOSImpl, T androidImpl) {
        T retObj = iOSImpl;
        if (getDriver() instanceof AndroidDriver) {
            retObj = androidImpl;
        }
        return retObj;
    }
    
    /**
     * Returns a new generic page object for the corresponding class.
     * @return
     */
    public <T extends MobilePage> T get(Class<T> pageClass,Object... args) {
        return new PageObj(getDriver()).get(pageClass, args);
    }
}