package com.vzt.framework.hpqc.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

public class InputDataLoader
{
    private InputDataLoader()
    {
 
    }
 
    public static Map<String, String> getRequiredFieldsMap(final int seq,
               final String entityType) throws IOException
    {
            InputStream systemResource = ClassLoader
                        .getSystemResourceAsStream(entityType);
 
            Properties myTestProperties = new Properties();
 
            myTestProperties.load(systemResource);
 
            // ResourceBundle anEntityResourceBundle = ResourceBundle
            // .getBundle(entityType);
 
            Map<String, String> fieldMap = new HashMap<String, String>();
            String value = myTestProperties.getProperty(String.valueOf(seq));
            String[] split = value.split(",");
            fieldMap.put(split[0], split[1]);
            return fieldMap;
    }
}
