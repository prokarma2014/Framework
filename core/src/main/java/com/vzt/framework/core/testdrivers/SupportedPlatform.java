package com.vzt.framework.core.testdrivers;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

/**
 * Represents the list of platforms that are supported for testing. Represents all the platforms like iPhone, android
 * etc. and browser types like safari, firefox etc.
 * 
 * @author prokarma
 * @verion 1.0
 */
public enum SupportedPlatform {
    iPhone(DesiredCapabilities.iphone(), IOSDriver.class),
    iPad(DesiredCapabilities.ipad(), IOSDriver.class),
    android(DesiredCapabilities.android(), AndroidDriver.class),
    chrome(DesiredCapabilities.chrome(), ChromeDriver.class, RemoteWebDriver.class),
    firefox(DesiredCapabilities.firefox(), FirefoxDriver.class, RemoteWebDriver.class),
    ie(DesiredCapabilities.internetExplorer(), InternetExplorerDriver.class, RemoteWebDriver.class),
    operaBlink(DesiredCapabilities.operaBlink(), OperaDriver.class, RemoteWebDriver.class),
    safari(DesiredCapabilities.safari(), SafariDriver.class, RemoteWebDriver.class),
    defaultRemote(new DesiredCapabilities(), RemoteWebDriver.class);

    private final Class<? extends WebDriver> driverClass;
    private final Class<? extends WebDriver> hubEnabledDriver;
    private final String platformName;
    private final DesiredCapabilities capabilities;

    /**
     * Initialize with platform name.
     * 
     * @param platformName
     */
    private SupportedPlatform(DesiredCapabilities template, Class<? extends WebDriver> driverClass,
            Class<? extends WebDriver> hubEnabledDriverClass) {
        this.platformName = name();
        this.capabilities = template;
        this.driverClass = driverClass;
        this.hubEnabledDriver = hubEnabledDriverClass;

    }

    /**
     * Initialize with platform name.
     * 
     * @param platformName
     */
    private SupportedPlatform(DesiredCapabilities template, Class<? extends WebDriver> driverClass) {
        this(template, driverClass, driverClass);
    }

    /**
     * Initialize with platform name.
     * 
     * @param platformName
     */
    private SupportedPlatform(DesiredCapabilities template) {
        this(template, null);
    }

    /**
     * Returns the platform matching the platform name. If not driver of the specified platform name is found then
     * remote web driver is returned.
     * 
     * @param platformName
     *            name of the platform.
     * @return
     */
    public static SupportedPlatform findFor(String platformName) {
        SupportedPlatform retPlatform = SupportedPlatform.defaultRemote;
        for (SupportedPlatform platform : SupportedPlatform.values()) {
            if (platform.platformName.equals(platformName)) {
                retPlatform = platform;
                break;
            }
        }
        return retPlatform;
    }

    /**
     * Clones the desired capabilities from the template.
     * 
     * @return
     */
    public DesiredCapabilities createCapabilities() {
        return new DesiredCapabilities(capabilities.asMap());
    }

    /**
     * @return the driverClass
     */
    public Class<? extends WebDriver> getDriverClass(boolean hubEnabled) {
        return hubEnabled ? hubEnabledDriver : driverClass;
    }

    /**
     * @return the platformName
     */
    public String getPlatformName() {
        return platformName;
    }
}