package api.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ProjectUtils {
    private static final String PREFIX_REQRES_PROP = "reqres.";
    private static final Properties properties = new Properties();

    static  {
        try (FileInputStream fis = new FileInputStream("src/test/resources/.properties")) {
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String convertPropToEnvName(String propName) {
        return propName.replace('.', '_').toUpperCase();
    }

    private static String getValue(String name) {
        return properties.getProperty(name, System.getenv(convertPropToEnvName(name)));
    }

    public static String getLogin() {
        return getValue(PREFIX_REQRES_PROP + "login");
    }

    public static String getPassword() {
        return getValue(PREFIX_REQRES_PROP + "password");
    }
}
