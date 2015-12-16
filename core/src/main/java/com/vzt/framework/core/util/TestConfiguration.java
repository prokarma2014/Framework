package com.vzt.framework.core.util;

import java.util.Collections;
import java.util.Map;
import java.util.Properties;

import com.google.common.base.Optional;
import com.google.common.collect.Maps;

/**
 * Test configuration containing all the configuration data for test as map.
 * 
 * @author prokarma
 * @verion 1.0
 */
public class TestConfiguration {

    private final Map<String, String> configData;

    /**
     * Initialize with the test properties
     * 
     * @param configurationName
     */
    public TestConfiguration(Properties properties) {
        configData = Maps.fromProperties(properties);
    }

    /**
     * Returns value of the given key or a default value.
     * @param key
     * @param defaultValue
     * @return
     */
    public String getConfig(String key, String defaultValue) {
        return Optional.fromNullable(configData.get(key)).or(defaultValue);
    }
    
    /**
     * Returns value of the given key value, null if not found.
     * @param key
     * @param defaultValue
     * @return
     */
    public String getConfig(String key) {
        return configData.get(key);
    }

    /**
     * Returns the all of the test properties.
     * @return the configData
     */
    public Map<String, String> getAllConfigData() {
        return Collections.unmodifiableMap(configData);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "TestConfiguration [" + (configData != null ? "configData=" + configData : "") + "]";
    }
}