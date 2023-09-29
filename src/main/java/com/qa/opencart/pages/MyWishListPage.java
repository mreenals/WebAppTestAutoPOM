package com.qa.opencart.pages;

import java.util.List;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class MyWishListPage {

	private WebDriver driver;
	private ElementUtil eleUtil;

	private By noWishList = By.xpath("//div[@id='content']//p[text()='Your wish list is empty.']");
	private By itemsInWishList = By.xpath("//div[@id=\"content\"]//td//a[contains(@href,\"product_id\") and text()]");

	public MyWishListPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	public String getTitleWishPage(String titleFraction) {
		return eleUtil.waitForTitleContains(titleFraction, AppConstants.MEDIUM_TIME_OUT);
	}

	public List<WebElement> getWishList() {
		return eleUtil.waitForElementsPresence(itemsInWishList, AppConstants.MEDIUM_TIME_OUT);
	}

	public Boolean emptyWishList() {
		try {
			return eleUtil.checkElementIsDisplayed(noWishList);
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public String emptyWishListMsg() {
		return eleUtil.doElementGetText(noWishList);
	}

}
