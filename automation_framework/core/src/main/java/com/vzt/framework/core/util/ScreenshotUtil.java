package com.vzt.framework.core.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility for creating screenshots using the web driver.
 * @author smahalingam@prokarma.com
 * @version 1.0
 */
public class ScreenshotUtil {

    private static final Logger logger = LoggerFactory.getLogger(ScreenshotUtil.class);
    
    private final WebDriver driver;
    private final File screenShotDir;
    
    /**
     * Initialize screen shot driver.
     * @param driver
     * @param screenShotDir
     */
    public ScreenshotUtil(WebDriver driver,File screenShotDir) {
        this.driver = driver;
        this.screenShotDir = screenShotDir;
    }
    
    /**
     * Captures the screenshot from mobile or web page.
     */
    public void captureScreenShot(String fileName) {
        try {
            File screenShot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenShot, new File(screenShotDir,fileName));
        } catch (IOException excp) {
            logger.warn("Screen shot retrieval failed due to ", excp);
        }
    }
}
