package com.lovehins.cache.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Properties;

public class PropertiesLoaderUtils {
    private static Logger logger = LoggerFactory.getLogger(PropertiesLoaderUtils.class);
    private static ResourceLoader resourceLoader = new DefaultResourceLoader();
    private final Properties properties;

    public PropertiesLoaderUtils(String... resourcesPaths) {
        this.properties = this.loadProperties(resourcesPaths);
    }

    public Properties getProperties() {
        return this.properties;
    }

    private String getValue(String key) {
        String systemProperty = System.getProperty(key);
        return systemProperty != null?systemProperty:(this.properties.containsKey(key)?this.properties.getProperty(key):"");
    }

    public String getProperty(String key) {
        String value = this.getValue(key);
        if(value == null) {
            throw new NoSuchElementException();
        } else {
            return value;
        }
    }

    public String getProperty(String key, String defaultValue) {
        String value = this.getValue(key);
        return value != null?value:defaultValue;
    }

    public Integer getInteger(String key) {
        String value = this.getValue(key);
        if(value == null) {
            throw new NoSuchElementException();
        } else {
            return Integer.valueOf(value);
        }
    }

    public Integer getInteger(String key, Integer defaultValue) {
        String value = this.getValue(key);
        return value != null?Integer.valueOf(value):defaultValue;
    }

    public Double getDouble(String key) {
        String value = this.getValue(key);
        if(value == null) {
            throw new NoSuchElementException();
        } else {
            return Double.valueOf(value);
        }
    }

    public Double getDouble(String key, Integer defaultValue) {
        String value = this.getValue(key);
        return Double.valueOf(value != null?Double.valueOf(value).doubleValue():(double)defaultValue.intValue());
    }

    public Boolean getBoolean(String key) {
        String value = this.getValue(key);
        if(value == null) {
            throw new NoSuchElementException();
        } else {
            return Boolean.valueOf(value);
        }
    }

    public Boolean getBoolean(String key, boolean defaultValue) {
        String value = this.getValue(key);
        return Boolean.valueOf(value != null?Boolean.valueOf(value).booleanValue():defaultValue);
    }

    private Properties loadProperties(String... resourcesPaths) {
        Properties props = new Properties();
        String[] arr$ = resourcesPaths;
        int len$ = resourcesPaths.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            String location = arr$[i$];
            InputStream is = null;

            try {
                Resource e = resourceLoader.getResource(location);
                is = e.getInputStream();
                props.load(is);
            } catch (IOException var17) {
                logger.info("Could not load properties from path:" + location + ", " + var17.getMessage());
            } finally {
                if(is != null) {
                    try {
                        is.close();
                    } catch (IOException var16) {
                        ;
                    }
                }

            }
        }

        return props;
    }
}