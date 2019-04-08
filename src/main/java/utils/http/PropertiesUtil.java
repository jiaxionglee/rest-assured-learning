package utils.http;

import java.io.InputStream;
import java.util.Properties;

/**
 * Created by jiaxiong on 2019-03-20 13:51
 */
public class PropertiesUtil {

    private static volatile Properties properties = null;

    private PropertiesUtil() {}

    private static Properties getProperty() {
        if (properties == null) {
            synchronized (PropertiesUtil.class) {
                if (properties == null) {
                    properties = new Properties();
                }
            }
        }
        return properties;
    }

    public static void loadProperties(String pathName) {
        InputStream inputStream = PropertiesUtil.class.getResourceAsStream(pathName);
        getProperty();
        try {
            properties.load(inputStream);
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getValue(String key) {
        if (properties == null) {
            return null;
        }
        return properties.getProperty(key);
    }

    public static String getValue(String pathName, String key) {
        loadProperties(pathName);
        return getValue(key);
    }
}
