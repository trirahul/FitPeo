package com.fitpeo.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

/**
 * Setup class for initializing and managing WebDriver instances.
 */
public class DriverSetup {
    protected WebDriver driver;

    /**
     * Sets up the WebDriver instance before the test class execution.
     * The browser type and URL are fetched from the properties file.
     *
     * @throws Exception if an invalid browser type is specified or an error occurs during setup.
     */
    @BeforeClass
    public void setup() throws Exception {
        ReadProperty propty = null;
        String browser;
        String uRL;
        String iEDriverPath;
        String geckoDriverPath;
        String chromeDriverPath;

        try {
            // Initialize property reader to fetch configuration details
            propty = new ReadProperty();
            browser = propty.getBrowser();
            uRL = propty.getURL();

            // Set paths for browser driver executables
            iEDriverPath = System.getProperty("user.dir") + "\\drivers\\IEDriverServer.exe";
            geckoDriverPath = System.getProperty("user.dir") + "\\drivers\\geckodriver.exe";
            chromeDriverPath = System.getProperty("user.dir") + "\\drivers\\chromedriver.exe";

            // Initialize the appropriate WebDriver based on the browser type
            if (browser.equalsIgnoreCase("explorer")) {
                System.setProperty("webdriver.ie.driver", iEDriverPath);
                driver = new InternetExplorerDriver();
            } else if (browser.equalsIgnoreCase("firefox")) {
                System.setProperty("webdriver.gecko.driver", geckoDriverPath);
                driver = new FirefoxDriver();
            } else if (browser.equalsIgnoreCase("Chrome")) {
                System.setProperty("webdriver.chrome.driver", chromeDriverPath);
                driver = new ChromeDriver();
            } else {
                throw new Exception("Illegal browser input given");
            }

            // Maximize the browser window and navigate to the specified URL
            driver.manage().window().maximize();
            driver.get(uRL);
        } catch (Exception e) {
            // Print the stack trace if an error occurs during setup
            e.printStackTrace();
        }
    }

    /**
     * Tears down the WebDriver instance after the test class execution.
     * Ensures the browser is closed and resources are released.
     */
    @AfterClass
    public void teardown() {
        if (driver != null) {
            driver.quit(); // Close the browser and release resources
        }
    }
}
