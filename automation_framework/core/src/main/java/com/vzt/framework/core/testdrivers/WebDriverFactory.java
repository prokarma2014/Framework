/**
 * 
 */
package com.vzt.framework.core.testdrivers;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;

import  com.vzt.framework.core.exception.FrameworkException;
import  com.vzt.framework.core.util.HierarchialConfigDataParser;
import  com.vzt.framework.core.util.TestConfiguration;

/**
 * Represents a general all purpose driver factory capable of creating different type of selenium web drivers from the
 * provided test configurations. The factory looks up the test configuration data to check for web driver configuration
 * such as url and other similar desired capabilities properties. The expected format of the test properties are
 * webdriver.hub.xyz where xyz represents the platform type e.g. appium, selenium etc. The list of supported platforms
 * are available as part of the supported platforms.
 * 
 * @author prokarma
 * @verion 1.0
 */
public class WebDriverFactory<T extends WebDriver> implements DriverFactory<T> {

    private static final String DEFAULT = "default";

    private static final Logger logger = LoggerFactory.getLogger(WebDriverFactory.class);

    private static final String EMPTY = "";

    /**
     * Represents the platform name that can be specified as part of the test parameters which indicate the platform to
     * execute. Valid values are iPad , iPhone and android.
     */
    public static final String PLATFORM = "platform";

    /**
     * Represents the platform type that can be specified as part of the test parameters which indicate type of the
     * given platform. Valid values are mobile , desktop.
     */
    public static final String PLATFORM_TYPE = "platform-type";

    private final Map<String, Map<String, String>> driverConfigData;

    /**
     * Creates a instance of driver factory and configures with relevant data.
     * 
     * @param testProperties
     */
    public WebDriverFactory(TestConfiguration testProperties) {
        // initialize with default and empty configurations.
        driverConfigData = new HierarchialConfigDataParser(testProperties, "webdriver").getParsedConfig();
    }

    /**
     * Creates an appium driver for the given configuration and test parameters
     */
    public T create(Map<String, String> testParameters) {
        String platformType = Optional.fromNullable(testParameters.get(PLATFORM_TYPE)).or(EMPTY);
        Map<String, String> additionalProperties = new HashMap<String, String>(driverConfigData.get(DEFAULT));
        if (driverConfigData.containsKey(platformType)) {
            additionalProperties.putAll(driverConfigData.get(platformType));
        }
        // remove hub so as to not cause impact
        String hub = additionalProperties.remove("hub");
        SupportedPlatform platform = SupportedPlatform.findFor(platformType);
        return instantiateDriver(platform, hub,
                platform.createCapabilities().merge(new DesiredCapabilities(additionalProperties)));
    }

    @SuppressWarnings("unchecked")
    private T instantiateDriver(SupportedPlatform platform, String hubURL, DesiredCapabilities capabilities) {
        try {
            logger.debug("Desired capabilties {}", capabilities);
            if (isHubEnabled(hubURL)) {
                return (T) platform.getDriverClass(isHubEnabled(hubURL)).getConstructor(URL.class, Capabilities.class)
                        .newInstance(new URL(hubURL), capabilities);
            } else {
                return (T) platform.getDriverClass(isHubEnabled(hubURL)).getConstructor(Capabilities.class)
                        .newInstance(capabilities);
            }
        } catch (Exception excp) {
            logger.error("Error while initializing driver {} with {} and capabilities", hubURL, capabilities, excp);
            throw new FrameworkException("Error while intializing web driver", excp);
        }
    }

    private boolean isHubEnabled(String hubURL) {
        return hubURL != null;
    }
}
