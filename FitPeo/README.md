# Automated Testing for E-commerce Website

## Introduction

This repository contains an automated testing strategy for the e-commerce website [SauceDemo](https://www.saucedemo.com/). The goal of this project is to ensure the quality and functionality of the website through systematic testing.

## Part 1: Test Planning

### Test Strategy Document

#### Objectives of Testing
- Ensure the website's functionality meets requirements.
- Verify that key features work as expected.
- Identify and report defects.
- Ensure a positive user experience.

#### Scope of Testing
- **In-Scope:** User registration, product search, adding items to the cart, checkout process, order management.
- **Out-of-Scope:** Non-functional requirements such as performance testing.

#### Testing Levels
- **Unit Testing:** Verify individual components.
- **Integration Testing:** Ensure that components work together.
- **System Testing:** Validate the entire system against requirements.
- **Acceptance Testing:** Ensure the system meets business requirements.

#### Testing Types
- **Functional Testing:** Verify functionality against requirements.
- **Usability Testing:** Ensure a positive user experience.
- **Regression Testing:** Ensure new changes do not break existing functionality.

#### Entry and Exit Criteria
- **Entry Criteria:** Test environment is set up, test data is prepared, all dependencies are resolved.
- **Exit Criteria:** All test cases executed, critical defects resolved, test summary report completed.

#### Test Environment and Tools
- **Environment:** Local machine with Chrome browser.
- **Tools:** Selenium WebDriver, TestNG, Maven, ExtentReports.

#### Risk Analysis
- **High Risk:** New feature implementations, third-party integrations.
- **Medium Risk:** Changes to existing features.
- **Low Risk:** Minor UI changes.

### Test Plan

#### Test Deliverables
- Test Strategy Document
- Test Plan
- Test Cases
- Test Scripts
- Test Summary Report

#### Test Schedule
- **Planning:** 2 days
- **Test Case Design:** 3 days
- **Test Automation:** 5 days
- **Test Execution:** 2 days
- **Reporting:** 1 day

#### Test Resources
- **Personnel:** 1 QA Engineer
- **Tools:** Selenium, TestNG, Maven, ExtentReports

#### Test Data and Environment Setup
- Test data stored in CSV files located in `src/test/resources/testdata/`
- Test environment set up using ChromeDriver

#### Test Execution and Reporting
- Test execution performed using TestNG
- Test results reported using ExtentReports

## Part 2: Test Case Design

### Functional Test Cases

1. **User Registration:**
   - Verify that a new user can register successfully.
   - Verify that registration with an existing email fails.

2. **Product Search:**
   - Verify that users can search for products using the search box.
   - Verify that relevant products are displayed based on the search query.

3. **Adding Items to Cart:**
   - Verify that users can add products to the cart.
   - Verify that the cart count increases when a product is added.

4. **Checkout Process:**
   - Verify that users can proceed to checkout.
   - Verify that users can complete the purchase.

5. **Order Management:**
   - Verify that users can view their order history.
   - Verify that order details are displayed correctly.

### Edge and Boundary Test Cases

1. **User Registration:**
   - Register with the maximum and minimum allowed characters in each field.
   - Register with special characters in fields.

2. **Product Search:**
   - Search with very long text.
   - Search with special characters.

## Part 3: Test Automation

### Test Automation Framework

**Framework:** Selenium WebDriver with TestNG
**Reason for Selection:** 
- Selenium is widely used for web application testing.
- TestNG provides powerful test configuration and reporting.

### Project Structure
selenium-test
│
├── src
│ ├── main
│ │ └── java
│ │ └── com
│ │ └── example
│ │ ├── base
│ │ │ └── TestBase.java
│ │ ├── pages
│ │ │ ├── LoginPage.java
│ │ │ ├── ProductPage.java
│ │ │ ├── CartPage.java
│ │ │ └── CheckoutPage.java
│ └── test
│ └── java
│ └── com
│ └── example
│ └── tests
│ └── FunctionalTests.java
│ └── resources
│ └── testdata
│ └── testdata.csv
└── ExtentReport
├── pom.xml
└── README.md
### Assumptions
1) It is assumed that a standard e-commerce website is to be used for testing purposes
2) User has 'Eclipse Java IDE for Java Developers 2024-06', Maven and TestNG plugins installed in Eclipse IDE
3) User is using Windows OS.
4) Java 8 (JDK and JRE) are installed and properly configured on the user's sytem.
5) Maven version 3.8.5 is installed and aptly configured on user's system.
### How To Run
1) Download the zip file for this project in an apt location on your system from Github
2) Unzip it and import it into Eclipse Java IDE
3) Right-click on the project in Project Explorer View and click on Maven->Update Project
4) Right-click on the project again in Project Explorer View and click on Run As->Maven Clean
5) Right-click on the project in Project Explorer View and click on Maven->Update Project
6) Right-click on the project again in Project Explorer View and click on Run As->Maven Test
7) Once all expected tests are done, check ExtentReports folder for customized report.

