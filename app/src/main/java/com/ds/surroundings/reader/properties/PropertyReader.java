package com.ds.surroundings.reader.properties;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class PropertyReader implements PropertyReadable {
    private static final String DEFAULT_PATH_TO_SETTINGS = "settings.properties";
    private final String pathToSettings;
    private Properties properties = new Properties();
    private InputStream inputStream;

    public PropertyReader(String path) {
        pathToSettings = path;
    }

    public PropertyReader() {
        this(DEFAULT_PATH_TO_SETTINGS);
    }

    @Override
    public List<String> getProperties() {
        List<String> loadedProperties = new ArrayList<>();
        try {
            inputStream = new FileInputStream(pathToSettings);
            properties.load(inputStream);
            Set<Object> propertiesKeys = properties.keySet();
            for (Object key : propertiesKeys) {
                loadedProperties.add((String) properties.get(key));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return loadedProperties;
    }

    private void closeConnection() {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
