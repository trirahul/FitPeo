package com.fitpeo.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.fitpeo.test.reporting.ExtentReport;
import com.fitpeo.utils.HelperFunctions;

public class HomePage {
	private WebDriver driver;
	private HelperFunctions helper = null;
	// Locators
	private ExtentReport report;
	private WebElement element;

	public HomePage(WebDriver driver) {
		this.driver = driver;
		helper = new HelperFunctions(driver);
		report = new ExtentReport(driver);
	}

	String revenueCalcSectionXpath = "//div//div[contains(@class,'satoshi') and (contains(text(),'Revenue'))]";

	public void clickOnRevenueCalculatorSection() {
		try {
			helper.customExplicitWaitTillElementBecomesClickable(revenueCalcSectionXpath, 2, 0.5);
			element = driver.findElement(By.xpath(revenueCalcSectionXpath));
			element.click();
			report.testLogAndScr("Click on Revenue Section success", "PASS", driver);
		} catch (Exception e) {
			e.printStackTrace();
			report.testLogAndScr("Click on Revenue Section not a success", "FAIL", driver);
		}
	}

	String healthSliderXpath = "//*[contains(@class, 'MuiSlider-root')]";

	public void scrollUntilHealthSliderComesUp() {
		try {
			helper.customExplicitWaitTillElementBecomesActive(healthSliderXpath, 2, 0.5);
			element = driver.findElement(By.xpath(healthSliderXpath));
			helper.scrollIntoView(element);
			report.testLogAndScr("Successfully scrolled into Health Slider", "PASS", driver);
		} catch (Exception e) {
			e.printStackTrace();
			report.testLogAndScr("Successfully scrolled into Health Slider", "FAIL", driver);
		}
	}

	public void dragSliderToDesiredValue(String desiredValue) {
		try {
			String sliderBodyXpath = "//span[contains(@class, 'MuiSlider-rail')]";
			String sliderHandleXpath = "//span[contains(@class, 'MuiSlider-thumb')]";
			String sliderInputXpath = "//span[contains(@class, 'MuiSlider-thumb')]//input[@type='range']";

			WebElement sliderBody, sliderHandle, sliderInput;
			helper.waitALittle(5);

			// Scroll to make slider visible
			helper.scrollVerticallySlowly(-350);

			// Wait for slider elements
			helper.customExplicitWaitTillElementAppearsInDom(sliderBodyXpath, 3, 0.5);
			helper.customExplicitWaitTillElementBecomesClickable(sliderBodyXpath, 4, 0.5);

			// Click on the slider main body
			sliderBody = driver.findElement(By.xpath(sliderBodyXpath));
			sliderBody.click();

			// Wait for the slider handle and click on it
			helper.customExplicitWaitTillElementBecomesClickable(sliderHandleXpath, 4, 0.5);
			sliderHandle = driver.findElement(By.xpath(sliderHandleXpath));
			sliderHandle.click();

			// Fetch the slider input element
			sliderInput = driver.findElement(By.xpath(sliderInputXpath));

			// Fetch the current slider value and properties
			String currentValueStr = sliderInput.getAttribute("aria-valuenow");
			int currentValue = Integer.parseInt(currentValueStr);
			int targetValue = Integer.parseInt(desiredValue);
			int minValue = Integer.parseInt(sliderInput.getAttribute("aria-valuemin"));
			int maxValue = Integer.parseInt(sliderInput.getAttribute("aria-valuemax"));

			System.out.println("Current Slider Value: " + currentValue);
			System.out.println("Target Slider Value: " + targetValue);

			// Slider dimensions
			int sliderWidth = sliderBody.getSize().getWidth();
			System.out.println("Slider Width: " + sliderWidth);

			// Calculate the pixel offset for the target value
			int offsetX = (int) ((sliderWidth * (targetValue - minValue)) / (maxValue - minValue));
			int currentOffsetX = (int) ((sliderWidth * (currentValue - minValue)) / (maxValue - minValue));
			int requiredOffsetX = offsetX - currentOffsetX;

			System.out.println("Calculated Offset to Target: " + requiredOffsetX);

			// Use Actions class to move the slider by calculated offset
			Actions actions = new Actions(driver);
			actions.clickAndHold(sliderHandle).moveByOffset(requiredOffsetX, 0) // Move slider handle horizontally
					.release().perform();
			actions.sendKeys(Keys.PAGE_DOWN).perform();
			report.testLogAndScr("Able to movie slider as expected", "PASS", driver);
		} catch (Exception e) {
			e.printStackTrace();
			report.testLogAndScr("Unable to move slider to desired value", "FAIL", driver);
		}
	}

	public void enterInputIntoPatientsField(int quantity) {
		String patientsFieldXpath = "//input[@type='number']";
		WebElement patientsField;
		try {
			helper.customExplicitWaitTillElementAppearsInDom(patientsFieldXpath, 4, 0.5);
			patientsField = driver.findElement(By.xpath(patientsFieldXpath));

			helper.customExplicitWaitTillElementBecomesClickable(patientsField, 5, 0.2);

			JavascriptExecutor js = (JavascriptExecutor) driver;
			try {
				js.executeScript("arguments[0].value = '';", patientsField);
			} catch (StaleElementReferenceException e) {

			} catch (Exception e) {
				e.printStackTrace();
			}
//			patientsField.clear();
			Thread.sleep(50);

			char[] charArray = String.valueOf(quantity).toCharArray();

			// Loop through each character and type it with a delay
			for (int i = 0; i < charArray.length; i++) {
				try {
					patientsField.sendKeys(Keys.BACK_SPACE);
					Thread.sleep(500); // 500 ms delay between each character
				} catch (StaleElementReferenceException e) {
					// Not important it's a trivial and common and doesn't involve any critical
					// issues
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			for (char ch : charArray) {
				try {
					patientsField.sendKeys(String.valueOf(ch)); // Send one character at a time
					helper.waitALittle(0.5);
					// 500 ms delay between each character
				} catch (StaleElementReferenceException e) {
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// click outside so value gets finalized

		} catch (Exception e) {
			report.testLogAndScr("Unable to put text in patientsField", "FAIL", driver);
			e.printStackTrace();
		}
	}

	public void checkIfInputtedValueIsOnSlider(String quantity) throws Exception {
		String patientsFieldXpath = "//input[@type='number']";
		try {
			WebElement patientsField = driver.findElement(By.xpath(patientsFieldXpath));

			helper.waitALittle(0.5);

			helper.scrollIntoView(patientsField);

			if (!(patientsField.getAttribute("value").equals(String.valueOf(quantity)))) {
				report.testLogAndScr("Slider and field value are different", "FAIL", driver);
				throw new Exception("Slider and field value are different");
			} else {
				report.testLogAndScr("Slider reflects the expected value: " + quantity, "PASS", driver);
			}
		} catch (Exception e) {
			e.printStackTrace();
			report.testLogAndScr("Error in checking inputted values", "FAIL", driver);
		}
	}

	public void checkInCPTCodes(String... cpts) {
		String cptsXpath = "//span[contains(@class,'PrivateSwitchBase')]/parent::*/parent::div/p[contains(@class,'body1')]";
		String innerText;
		int j = 0;
		try {
			List<WebElement> cptChecks = driver.findElements(By.xpath(cptsXpath));

			for (int i = 0; i < cptChecks.size() && j < cpts.length; i++) {
				innerText = cptChecks.get(i).getText();
				if (innerText.equals(cpts[j])) {
					j++;
					String checkBoxXpath = "("
							+ "//span[contains(@class,'PrivateSwitchBase')]/parent::*/parent::div/p[contains(@class,'body1')]/"
							+ "parent::div/child::label/descendant::input[@type='checkbox']" + ")[" + (i + 1) + "]";
					helper.print(checkBoxXpath);
					WebElement checkbox = driver.findElement(By.xpath(checkBoxXpath));
					helper.scrollIntoView(checkbox);
					helper.waitALittle(0.5);
					helper.clickUsingJS(checkbox);
				}
			}
			if (j == cpts.length) {
				String printResult = "CheckBoxOptions:";
				for (int i = 0; i < cpts.length; i++) {
					printResult += cpts[i];
				}
				report.testLogAndScr("Clicked all " + printResult, "PASS", driver);
			} else {
				report.testLogAndScr("Unable to click on all checks", "FAIL", driver);
				throw new Exception("Unable to click on all checks" + "\nTotalCheckClicked:" + j);
			}
		} catch (Exception e) {
			e.printStackTrace();
			report.testLogAndScr("Some critical error occured in accessing checkboxes", "FAIL", driver);
		}

	}

	public void checkTotalReimbursementValue(String expectedValue) {
		String reimburseSectionXpath = "//div[contains(@class,'Tool')]/ancestor::div/preceding-sibling::header/"
				+ "following-sibling::div/descendant::p[contains(text(),'Total Recurring Reimbursement "
				+ "for all')]/child::*[1]";
		try {
			helper.customExplicitWaitTillElementAppearsInDom(reimburseSectionXpath, 3, 0.5);

			WebElement reimburseSection = driver.findElement(By.xpath(reimburseSectionXpath));
			String actualReimburseValue = reimburseSection.getText();

			if (actualReimburseValue.equals(expectedValue)) {
				report.testLogAndScr("Able to match reimbursement values", "PASS", driver);
			} else {
				report.testLogAndScr("Wrong reimbursement values", "FAIL", driver);
				throw new Exception("Wrong Reimbursement Values\n" + "Actual value is:" + actualReimburseValue
						+ " and Expected value is " + expectedValue);
			}
		} catch (Exception e) {
			e.printStackTrace();
			report.testLogAndScr("Failed to verify reimbursement values", "FAIL", driver);
		}
	}

	public void scrollALittle() {
		helper.scrollVerticallySlowly(500);
	}

	// Reporting Code
	public void endReport() {
		report.endReport();
	}

	public void setReport(String reportName) {
		report.startTest(reportName, true);
	}

}