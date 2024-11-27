package com.fitpeo.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Utility class to read properties from a configuration file.
 */
public class ReadProperty {

    // Path to the configuration file
    static String filePath = System.getProperty("user.dir") + "\\config.properties";

    // Variables to store property values
    private String username;
    private String password;
    private String URL;
    private String env;
    private String pcName;
    private String browser;

    /**
     * Constructor to initialize properties by reading from the configuration file.
     *
     * @throws IOException if there is an issue accessing or reading the file.
     */
    public ReadProperty() throws IOException {
        Properties prop = new Properties(); // Create a Properties object
        FileInputStream instream = new FileInputStream(filePath); // Open the properties file
        prop.load(instream); // Load properties into the object

        // Initialize variables with the values from the properties file
        this.username = prop.getProperty("username");
        this.password = prop.getProperty("password");
        this.URL = prop.getProperty("URL");
        this.env = prop.getProperty("enviroment");
        this.pcName = prop.getProperty("pcName");
        this.browser = prop.getProperty("browser");

        instream.close(); // Close the file input stream
    }

    /********************** GETTERS ***************************/

    /**
     * @return the username value from the properties file.
     */
    public String getUserName() {
        return this.username;
    }

    /**
     * @return the password value from the properties file.
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * @return the URL value from the properties file.
     */
    public String getURL() {
        return this.URL;
    }

    /**
     * @return the environment value from the properties file.
     */
    public String getEnviroment() {
        return this.env;
    }

    /**
     * @return the PC name value from the properties file.
     */
    public String getPcName() {
        return this.pcName;
    }

    /**
     * @return the browser value from the properties file.
     */
    public String getBrowser() {
        return this.browser;
    }
}
