package com.vzt.framework.core.web;

import java.lang.reflect.Method;
import java.util.Date;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.vzt.framework.core.BaseTest;
import com.vzt.framework.core.testdrivers.DriverFactory;
import com.vzt.framework.core.testdrivers.WebDriverFactory;
import com.vzt.framework.core.util.ScreenshotUtil;

/**
 * A base test for web applications that are executed through selenium.
 * 
 * @author prokarma
 * @verion 1.0
 */
public class WebTest extends BaseTest<WebDriver> {

	private WebDriver driver;

	/**
	 * @see core.BaseTest#initPrimaryDriverFactory()
	 */
	@Override
	protected DriverFactory<WebDriver> initPrimaryDriverFactory() {
		return new WebDriverFactory<WebDriver>(getTestConfig());
	}

	/**
	 * Opens the browser as part of the test class.
	 */
	@BeforeMethod(alwaysRun = true)
	protected void openBrowser() {
		if (driver == null) {
			driver = createPrimaryDriver();
		}
	}

	/**
	 * Closes the given browser
	 */
	@AfterMethod(alwaysRun = true)
	protected void closeBrowser(ITestResult result, Method testMethod) {
		if (driver != null && !reset()) {
/*			if (result.getStatus() == ITestResult.FAILURE) {
				String fileName = new StringBuilder(testMethod.getName())
						.append(new Date().getTime()+".png").toString();
				ScreenshotUtil util = new ScreenshotUtil(getDriver(),
						getScreenShotDir());
				util.captureScreenShot(fileName);
*/			}
			if (!(driver instanceof InternetExplorerDriver)) {
				driver.close();
			}
			driver.quit();
			driver = null;
		}
	}

	/**
	 * Override to reset the browser state prior to calling each test method.
	 */
	protected boolean reset() {
		return false;
	}

	/**
	 * Returns an instance of the web driver.
	 * 
	 * @return
	 */
	public WebDriver getDriver() {
		return driver;
	}
}
