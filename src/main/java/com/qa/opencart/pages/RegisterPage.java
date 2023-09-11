package com.qa.opencart.pages;

import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class RegisterPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;

	public RegisterPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	private By first_name = By.id("input-firstname");
	private By last_name = By.id("input-lastname");
	private By email = By.id("input-email");
	private By telephone = By.id("input-telephone");
	private By password = By.id("input-password");
	private By pwdConfirm = By.id("input-confirm");
	private By subscribeYes = By.xpath("//label[normalize-space()='Yes']");
	private By subscribeNo = By.xpath("//label[normalize-space()='No']");
	private By privacyPolicy = By.xpath("//input[@type='checkbox' and @name='agree']");
	private By submitBtn = By.xpath("//input[@type='submit']");
	private By successMsg = By.xpath("//div[@id='content']//h1");
	private By logoutLink = By.linkText("Logout");
	private By registerLink = By.linkText("Register");
	Random rand = new Random();
	private String pwdString;
	
	private String uniqPwd() {
		String randGenPwd = "mstest@" + Integer.toString(rand.nextInt(1000));
		System.out.println("Password is: " + randGenPwd);
		return randGenPwd;
	}

	public boolean registerUser(String first_name, String last_name, String email, String telephone, String newsLetter) {
		
		pwdString = uniqPwd();
		
		eleUtil.waitForElementVisible(this.first_name, AppConstants.MEDIUM_TIME_OUT).sendKeys(first_name);
		eleUtil.doSendKeys(this.last_name, last_name);
		eleUtil.doSendKeys(this.email, email);
		System.out.println("Email of " + first_name + " is:" + email);
		eleUtil.doSendKeys(this.telephone, telephone);
		eleUtil.doSendKeys(password, pwdString);
		eleUtil.doSendKeys(pwdConfirm, pwdString);
		
		if(newsLetter.toLowerCase().equals("yes")) {
			eleUtil.doClick(subscribeYes);
		}else {
			eleUtil.doClick(subscribeNo);
		}
		
		eleUtil.doClick(privacyPolicy);
		eleUtil.doClick(submitBtn);	
		
		String successMsg = eleUtil.waitForElementVisible(this.successMsg, AppConstants.MEDIUM_TIME_OUT).getText();
		System.out.println(successMsg);
		
		if(successMsg.contains(AppConstants.USER_REGISTER_SUCCESS_MESSG)) {
			eleUtil.doClick(logoutLink);
			eleUtil.doActionsClick(registerLink);
			return true;
		}else {
			return false;
		} 
	}

}
