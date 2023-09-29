package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

public class MyWishListPageTest extends BaseTest{
	
	@BeforeClass
	public void accPageSetup() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		wishListPage = accPage.doClickWishList();
	}
	
	@Test
	public void wishListPageTitleTest() {
		String title = wishListPage.getTitleWishPage("My Wish");
		Assert.assertEquals(title, "My Wish List");
	}
	
	@Test
	public void validateWishList() {
		if(wishListPage.emptyWishList()) {
			String msg = wishListPage.emptyWishListMsg();
			Assert.assertEquals(msg, "Your wish list is empty.");
		}else {
			Assert.assertEquals(wishListPage.getWishList().size() > 0, true);
			System.out.println(": Wish List has product..");
		}
	}

}
