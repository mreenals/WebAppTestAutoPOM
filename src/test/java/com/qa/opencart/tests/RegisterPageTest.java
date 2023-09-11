package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ExcelUtil;

public class RegisterPageTest extends BaseTest{
	
	@BeforeClass
	public void setupRegistration() {
		rPage = loginPage.navigateToRegisterPage();
	}
	
	public String getRandomEmailId() {
//		String uniqEmailId = "mstest" + Integer.toString(rand.nextInt(1000)) + "@testautomation.com";
//		System.out.println("EmailId is: " + uniqEmailId);
//		return uniqEmailId;
		
		return "mstest"+System.currentTimeMillis()+"@open.com";
		
	}
	
	@DataProvider
	public Object[][] userRegistrationData(){
		return new Object [][]{
			{"Ravi", "Mohan", "+919922113300", "yes"}//,
			//{"Krishna", "Singh", "+918877113300", "no"},
			//{"Madhav", "Rathi", "+919900002100", "no"}
		};
		
	}
	
	@DataProvider
	public Object[][] getUserRegSheetData(){
		return ExcelUtil.getTestData(AppConstants.REGISTER_SHEET_NAME);
		
	}
	
	@Test(dataProvider = "userRegistrationData")
	public void userRegistrationTest(String firstName, String lastName, String telephoneNum, String newsLetter) {
		Assert.assertTrue(rPage.registerUser(firstName, lastName, getRandomEmailId(), telephoneNum, newsLetter));
	}
}
