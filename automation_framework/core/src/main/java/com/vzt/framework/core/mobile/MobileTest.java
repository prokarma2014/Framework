package com.vzt.framework.core.mobile;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

import com.vzt.framework.core.BaseTest;
import com.vzt.framework.core.testdrivers.DriverFactory;
import com.vzt.framework.core.testdrivers.WebDriverFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/**
 * Mobile appium based base test class. Provides wrapper around common driver api's to control device app controls such
 * as openApp, closeApp, switchTo, moveTo etc.
 * 
 * @author prokarma
 * @verion 1.0
 */
public class MobileTest extends BaseTest<AppiumDriver<?>> {

    private AppiumDriver<?> primaryDriver;

    /**
     * Create and return an instance of webdriver factory that supports appium.
     */
    @Override
    protected DriverFactory<AppiumDriver<?>> initPrimaryDriverFactory() {
        return new WebDriverFactory<AppiumDriver<?>>(getTestConfig());
    }

    /**
     * Open and close app for each test method
     */
    @BeforeMethod(alwaysRun=true)
    protected void openApp() {
        if (primaryDriver == null) {
            primaryDriver = createPrimaryDriver();
        }
    }

    /**
     * Closes the given app.
     */
    @AfterMethod(alwaysRun=true)
    protected void closeApp() {
        cleanupResources();
    }

    /**
     * Closes the given app.
     */
    @AfterClass(alwaysRun=true)
    private void cleanupTest() {
        cleanupResources();
    }

    private void cleanupResources() {
        if (primaryDriver != null && !resetApp()) {
            primaryDriver.closeApp();
            primaryDriver.quit();
            primaryDriver = null;
        }
    }

    /**
     * Reset enables performance benefits of not closing and opening the app every time when running test. If the reset
     * method returns true, then driver are not open and closed for every test runs. Test cases should implement reset
     * such way that app is not in a inconsistent state for each test. Also reset scope is within the test class and
     * cannot be extended beyond it.
     * 
     * @return
     */
    protected boolean resetApp() {
        return false;
    }

    /**
     * @return the primaryDriver
     */
    public AppiumDriver<?> getDriver() {
        return primaryDriver;
    }
    
    /**
     * Selects relevant object based on iOS or android.
     * @param iOSObj
     * @param androidObj
     * @return
     */
    public <T> T selectPlatformSpecificObj(T iOSObj,T androidObj) {
        T endObj = iOSObj;
        if(primaryDriver instanceof AndroidDriver) {
            endObj = androidObj;
        }
        return endObj;
    }
}