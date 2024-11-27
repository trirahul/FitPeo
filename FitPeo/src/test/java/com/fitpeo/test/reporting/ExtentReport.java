package com.fitpeo.test.reporting;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.fitpeo.utils.ReadProperty;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

/**
 * Utility class for generating Extent Reports and capturing screenshots.
 * Extent Reports provide detailed test execution logs and visual evidence for debugging.
 */
public class ExtentReport {

    public ExtentReports report; // ExtentReports instance
    public ExtentTest test; // ExtentTest instance for logging individual test steps
    public WebDriver driver; // WebDriver instance

    // Paths for report files, screenshots, and configuration XML
    String reportPath = System.getProperty("user.dir") + "\\ExtentReports\\";
    String screenshotPath = System.getProperty("user.dir") + "\\Test_Screenshots\\";
    String extentReportXmlPath = System.getProperty("user.dir") + "\\XML\\extent-config.xml";

    ReadProperty prop; // Instance for reading properties
    public String testName; // Current test name for reporting and screenshots

    /**
     * Constructor to initialize ExtentReport with the WebDriver instance.
     *
     * @param driver WebDriver instance for the current test session.
     */
    public ExtentReport(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Starts a new Extent Report test and initializes reporting parameters.
     *
     * @param testName the name of the test being executed.
     * @param choice   flag to overwrite existing reports or append to them.
     */
    public void startTest(String testName, boolean choice) {
        try {
            prop = new ReadProperty(); // Read properties for report configuration
            report = new ExtentReports(
                    reportPath + testName + "_" + prop.getBrowser().toUpperCase() + ".html", choice);
            report.addSystemInfo("Environment", prop.getEnviroment())
                    .addSystemInfo("PC Name", prop.getPcName())
                    .addSystemInfo("Browser", prop.getBrowser());
            report.loadConfig(new File(extentReportXmlPath)); // Load XML configuration for Extent Reports
            test = report.startTest(testName); // Start a new test in the report
            this.testName = testName;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Logs messages and captures screenshots in the Extent Report based on test step status.
     *
     * @param msg    the message to log in the report.
     * @param status the status of the test step ("PASS" or "FAIL").
     * @param driver WebDriver instance for capturing screenshots.
     */
    public void testLogAndScr(String msg, String status, WebDriver driver) {
        try {
            if (status.equalsIgnoreCase("PASS")) {
                test.log(LogStatus.PASS, msg, test.addScreenCapture(capture(driver)));
            } else if (status.equalsIgnoreCase("FAIL")) {
                test.log(LogStatus.FAIL, msg, test.addScreenCapture(capture(driver)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Captures a screenshot of the current browser window and saves it with a unique name.
     *
     * @param driver WebDriver instance for capturing the screenshot.
     * @return the absolute path of the saved screenshot file.
     * @throws IOException if an error occurs while saving the screenshot.
     */
    public String capture(WebDriver driver) throws IOException {
        // Get the current method name from the call stack for unique naming
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        String methodName = stack[3].getMethodName();

        // Take a screenshot and save it to a temporary file
        File scrCapture = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        // Define the destination file for the screenshot
        File dest = new File(
                screenshotPath + testName + "_" + methodName + "_" + prop.getBrowser().toUpperCase() + ".png");
        String returnPath = dest.getAbsolutePath();

        // Copy the screenshot to the destination file
        FileUtils.copyFile(scrCapture, dest);

        // Return the absolute path of the saved screenshot
        return returnPath;
    }

    /**
     * Ends the test in the Extent Report and flushes the logs to the report file.
     */
    public void endReport() {
        report.endTest(test); // End the test in the report
        report.flush(); // Write all logs to the report file
    }
}
