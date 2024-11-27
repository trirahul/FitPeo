package com.fitpeo.tests;

import com.fitpeo.pages.HomePage;
import com.fitpeo.utils.DriverSetup;
import org.testng.annotations.Test;

public class TotalReimbursementTest extends DriverSetup {
    private HomePage homePage;
    
    @Test
    public void testTotalReimbursementFunctionality() throws Exception {
        homePage = new HomePage(driver);
        homePage.setReport("Total Reimbursement Test");
        homePage.clickOnRevenueCalculatorSection();
        homePage.dragSliderToDesiredValue("820");
        homePage.enterInputIntoPatientsField(560);
        homePage.checkIfInputtedValueIsOnSlider("560");
        homePage.enterInputIntoPatientsField(820);
        homePage.checkInCPTCodes("CPT-99091", "CPT-99453", "CPT-99454", "CPT-99474");
        homePage.scrollALittle();
        homePage.checkTotalReimbursementValue("$110700");
        homePage.endReport();
    }
}
