package com.vzt.framework.core;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Map;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.vzt.framework.core.actions.TestContextManager;
import com.vzt.framework.core.actions.TestDataProvider;
import com.vzt.framework.core.exception.FrameworkException;
import com.vzt.framework.core.reporting.ExtentReporterNG;
import com.vzt.framework.core.reporting.TestStatusLogger;
import com.vzt.framework.core.testdrivers.DriverFactory;
import com.vzt.framework.core.testdrivers.WebDriverFactory;
import com.vzt.framework.core.util.TestConfiguration;

/**
 * Represents the base testng class that provide common abilities such as
 * retrieving test data, logging capabilities, common initializations that needs
 * to be extended by all other test classes for reusing the capabilities.
 * 
 * @author prokarma
 * @verion 1.0
 */
@Listeners({ TestStatusLogger.class, ExtentReporterNG.class })
public abstract class BaseTest<T> {

	private static final Logger frameworkLogger = LoggerFactory
			.getLogger(BaseTest.class);

	private static TestConfiguration testProperties;
	private static TestDataProvider testDataMapper;

	private DriverFactory<T> primaryDriverFactory;

	private Map<String, String> testParameters;
	
	private static File screenShotDir;

	/**
	 * Initialize
	 */
	public BaseTest() {
		frameworkLogger.debug("Instance created " + this.getClass().getName());
	}

	/**
	 * Initialize the entire test suite with relevant test
	 */
	@BeforeSuite(alwaysRun = true)
	@Parameters({ "config-file" })
	public static void initializeConfig(
			@Optional("testconfig.properties") String configFile) {
		frameworkLogger.debug("initailizeConfig called");
		try {
			Properties properties = new Properties();
			properties.load(BaseTest.class.getClassLoader()
					.getResourceAsStream(configFile));
			testProperties = new TestConfiguration(properties);
			frameworkLogger.info(testProperties.toString());
			String screenShotDirName = testProperties.getConfig("test.screenshotdir");
            if (screenShotDirName != null) {
                URL resource = BaseTest.class.getClassLoader().getResource(screenShotDirName);
                if (resource == null) {
                    throw new FrameworkException("Error while initailizing, the screenshot dir not found in classpath "
                            + screenShotDirName);
                }
                screenShotDir = new File(resource.getFile());
            }
		} catch (IOException iexcp) {
			frameworkLogger
					.error("Error while initializing the test using the testconfig.properties",
							iexcp);
		}
		testDataMapper = new TestDataProvider(testProperties);
	}

	/**
	 * Initialize the entire test suite with relevant test
	 */
	@BeforeMethod(alwaysRun = true)
	public void initializeTest(ITestContext testContext) {
		testParameters = testContext.getCurrentXmlTest().getAllParameters();
		frameworkLogger.debug("Test parameter {} ", testParameters);
		if (primaryDriverFactory == null) {
			primaryDriverFactory = initPrimaryDriverFactory();
		}
		frameworkLogger.debug("Done initialize test @BeforeTest {} {} {}", this
				.getClass().getName(), testParameters, primaryDriverFactory);
	}

	/**
	 * Initializes and return the primary driver factory. Subclasses should
	 * return
	 * 
	 * @return
	 */
	protected abstract DriverFactory<T> initPrimaryDriverFactory();

	@DataProvider(name = "default")
	public Object[][] dataprovider(Method method, ITestContext context) {
		return testDataMapper.getTestData(TestContextManager.getInstance()
				.getContext(method), context);
	}

	/**
	 * Returns the test property file.
	 * 
	 * @return
	 */
	public TestConfiguration getTestConfig() {
		return testProperties;
	}

	/**
	 * Returns a instance of the primary test driver.
	 * 
	 * @return
	 */
	public T createPrimaryDriver() {
		return primaryDriverFactory.create(testParameters);
	}

	/**
	 * Returns the driver of specific requested instance.
	 * 
	 * @param driverType
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object createDriverOfType(Class<?> driverType) {

		if (WebDriver.class.isAssignableFrom(driverType)) {
			if (primaryDriverFactory instanceof WebDriver) {
				return primaryDriverFactory;
			} else {
				return new WebDriverFactory(testProperties)
						.create(testParameters);
			}
		}
		// default throw error
		throw new FrameworkException("Invalid driver type requested "
				+ driverType);
	}
	
	  /**
     * @return the screenShotDir
     */
    protected static File getScreenShotDir() {
        return screenShotDir;
    }

}