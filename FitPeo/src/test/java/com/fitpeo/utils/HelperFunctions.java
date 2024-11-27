package com.fitpeo.utils;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HelperFunctions {

	private WebDriver driver = null;
	private WebDriverWait explicitWait = null;

	// Constructor to initialize the WebDriver instance
	public HelperFunctions(WebDriver driver) {
		this.driver = driver;
	}

	// Setter method to update the WebDriver instance
	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	// Method to set custom implicit wait duration for the WebDriver
	public void customImplicitWait(long seconds) {
		try {
			driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Custom explicit wait to check if an element becomes clickable within a specified timeout
	public <T> void customExplicitWaitTillElementBecomesClickable(T argument, double timeOutInSeconds,
			double pollFreqInSeconds) throws Exception {
		WebElement element = null;
		Exception error = null;
		long init = 0;
		long timeoutInMilliseconds = (long) (timeOutInSeconds * 1000.0);
		long pollFreqInMillis = (long) (pollFreqInSeconds * 1000.0);
		boolean result = false;

		// Determine the type of argument (String or WebElement) and find the element
		if (argument.getClass().toString().contains("String")) {
			element = driver.findElement(By.xpath((String) argument));
		} else if (argument.getClass().toString().contains("WebElement")) {
			element = (WebElement) argument;
		} else {
			throw new IllegalArgumentException("Argument must be of type String or WebElement");
		}

		// Poll for the element to become clickable within the specified timeout
		do {
			try {
				if (element.isDisplayed() && element.isEnabled()) {
					result = true;
					break;
				}
			} catch (Exception e) {
				error = e;
			} finally {
				Thread.sleep(pollFreqInMillis);
				init = pollFreqInMillis + init + 1;
			}
		} while (init < timeoutInMilliseconds);

		// Throw an exception if the element does not become clickable
		if (!result) {
			if (error == null) {
				throw new Exception("Element is unclickable");
			} else {
				throw error;
			}
		} else {
			System.out.println("It took " + init + "ms for element (" + element + ") to become clickable.");
		}
	}

	// Custom explicit wait to check if an element appears in the DOM
	public void customExplicitWaitTillElementAppearsInDom(String xpath, double timeOutInSeconds,
			double pollFreqInSeconds) throws Exception {
		WebElement element = null;
		Exception error = null;
		long init = 0;
		long timeoutInMilliseconds = (long) (timeOutInSeconds * 1000.0);
		long pollFreqInMillis = (long) (pollFreqInSeconds * 1000.0);
		boolean result = false;

		// Poll for the element to appear in the DOM within the specified timeout
		do {
			try {
				element = driver.findElement(By.xpath(xpath));
				result = true;
				break;
			} catch (Exception e) {
				error = e;
			} finally {
				Thread.sleep(pollFreqInMillis);
				init = pollFreqInMillis + init + 1;
			}
		} while (init < timeoutInMilliseconds);

		// Throw an exception if the element does not appear in the DOM
		if (!result) {
			throw error;
		} else {
			System.out.println("It took " + init + "ms for element (" + element + ") to come in DOM.");
		}
	}

	// Custom explicit wait to check if an element becomes active on the screen
	public void customExplicitWaitTillElementBecomesActive(String argument, double timeOutInSeconds,
			double pollFreqInSeconds) throws Exception {
		WebElement element = null;
		long init = 0;
		long timeoutInMilliseconds = (long) (timeOutInSeconds * 1000.0);
		long pollFreqInMillis = (long) (pollFreqInSeconds * 1000.0);
		boolean result = false;
		Exception error = null;

		// Poll for the element to become active within the specified timeout
		do {
			try {
				if (driver.findElement(By.xpath((String) argument)) != null) {
					result = true;
					element = driver.findElement(By.xpath((String) argument));
					break;
				}
			} catch (Exception e) {
				error = e;
			} finally {
				Thread.sleep(pollFreqInMillis);
				init = pollFreqInMillis + init + 1;
			}
		} while (init < timeoutInMilliseconds);

		// Throw an exception if the element does not become active
		if (!result) {
			throw error;
		} else {
			System.out.println("It took " + init + "ms for element (" + element + ") to be present on screen");
		}
	}

	// Explicit wait for an element to be clickable using its XPath
	public void explicitWaitForElementClick(String xpath, double timeOutInSeconds, double pollFreqInSeconds) {
		long timeOutInMillis = (long) (timeOutInSeconds * 1000.0);
		long pollFreqInMillis = (long) (pollFreqInSeconds * 1000.0);
		explicitWait = new WebDriverWait(driver, (long) timeOutInSeconds);
		explicitWait.withTimeout(Duration.ofMillis(timeOutInMillis)).pollingEvery(Duration.ofMillis(pollFreqInMillis))
				.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
	}

	// Explicit wait for an element to be clickable using a WebElement
	public void explicitWaitForElementClick(WebElement element, double timeOutInSeconds, double pollFreqInSeconds) {
		long timeOutInMillis = (long) (timeOutInSeconds * 1000.0);
		long pollFreqInMillis = (long) (pollFreqInSeconds * 1000.0);
		explicitWait = new WebDriverWait(driver, (long) timeOutInSeconds);
		explicitWait.withTimeout(Duration.ofMillis(timeOutInMillis)).pollingEvery(Duration.ofMillis(pollFreqInMillis))
				.until(ExpectedConditions.elementToBeClickable(element));
	}

	// Scrolls the viewport to bring an element into view
	public void scrollIntoView(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();", element);
	}

	// Scrolls the viewport to bring an element into view using its XPath
	public void scrollIntoView(String xpath) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();", driver.findElement(By.xpath(xpath)));
	}

	// Scrolls vertically by a specified number of pixels slowly
	public void scrollVerticallySlowly(int pixels) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		for (int i = 0; i <= pixels; i += 20) {
			js.executeScript("window.scrollBy(0, " + i + ");");
		}
	}

	// Clicks an element using JavaScript
	public void clickUsingJS(WebElement element) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", element);
	}

	// Clicks an element using JavaScript and its XPath
	public void clickUsingJS(String xpath) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		WebElement element = driver.findElement(By.xpath(xpath));
		executor.executeScript("arguments[0].click();", element);
	}

	// Prints the provided argument to the console
	public <T> void print(T argument) {
		System.out.println(argument);
	}

	// Waits for a specified amount of time in seconds
	public void waitALittle(double seconds) {
		try {
			long millis = (long) (seconds * 1000.0);
			Thread.sleep(millis);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Extracts a numerical value from a string containing mixed characters
	public double extractNumberFromString(String variedString) {
		String numberOnly = variedString.replaceAll("[^0-9]", "");
		return Integer.valueOf(numberOnly);
	}
}
