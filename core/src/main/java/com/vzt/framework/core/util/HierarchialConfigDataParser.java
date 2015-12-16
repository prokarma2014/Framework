package com.vzt.framework.core.util;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Map.Entry;

/**
 * Class parses data in test configuration in hierachial format e.g. webdriver.url. The parser also supports suffix
 * along with a prefix. e.g. webdriver.url.ios prefix being webdriver, suffix being ios and properly being url.
 * 
 * @author prokarma
 * @verion 1.0
 */
public class HierarchialConfigDataParser {

    private static final HashMap<String, String> DEFAULT_PROPERTY_SET = new HashMap<String, String>();

    private static final String DEFAULT = "default";

    private final Map<String, Map<String, String>> parsedConfig = new HashMap<String, Map<String, String>>();

    /**
     * Initialize with test configuration and group prefix.
     * 
     * @param configuration
     * @param groupPrefix
     */
    public HierarchialConfigDataParser(TestConfiguration configuration, String groupPrefix) {
        parsedConfig.put(DEFAULT, DEFAULT_PROPERTY_SET);
        processTestProperties(configuration, groupPrefix);
    }

    private void processTestProperties(TestConfiguration configuration, String groupPrefix) {
        for (Entry<String, String> configData : configuration.getAllConfigData().entrySet()) {
            StringTokenizer tokenizer = new StringTokenizer(configData.getKey(), ".");
            String prefix = tokenizer.nextToken();
            String parameterName = tokenizer.nextToken();
            if (groupPrefix.equals(prefix)) {
                if (hasSuffix(tokenizer)) {
                    populatePlatformSpecificProps(configuration, configData, parameterName, tokenizer.nextToken());
                } else {
                    parsedConfig.get(DEFAULT).put(parameterName, configuration.getConfig(configData.getKey()));
                }
            }
        }
    }

    private void populatePlatformSpecificProps(TestConfiguration configuration, Entry<String, String> configData,
            String parameterName, String suffix) {
        if (!parsedConfig.containsKey(suffix)) {
            parsedConfig.put(suffix, new HashMap<String, String>());
        }
        parsedConfig.get(suffix).put(parameterName, configuration.getConfig(configData.getKey()));
    }

    private boolean hasSuffix(StringTokenizer tokenizer) {
        return tokenizer.hasMoreTokens();
    }

    /**
     * @return the parsedConfig
     */
    public Map<String, Map<String, String>> getParsedConfig() {
        return parsedConfig;
    }
}
