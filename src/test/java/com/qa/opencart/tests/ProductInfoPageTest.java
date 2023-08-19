package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ExcelUtil;

public class ProductInfoPageTest extends BaseTest{
	
	@BeforeClass
	public void prodInfoSetup() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	@DataProvider
	public Object[][] productTestData() {
		return new Object[][] {
			{"macbook", "MacBook Pro"},
			{"macbook", "MacBook Air"},
			{"imac", "iMac"},
			{"samsung", "Samsung SyncMaster 941BW"},
			{"samsung", "Samsung Galaxy Tab 10.1"},
		};
	}
	
	@DataProvider
	public Object[][] productData() {
		return new Object[][] {
			{"macbook", "MacBook Pro", 4},
			{"macbook", "MacBook Air", 4},
			{"imac", "iMac", 3},
			{"samsung", "Samsung SyncMaster 941BW", 1},
			{"samsung", "Samsung Galaxy Tab 10.1", 7}
		};
	}
	
	@DataProvider
	public Object[][] getProductSheetData(){
		return ExcelUtil.getTestData(AppConstants.PRODUCT_SHEET_NAME);
		
	}
	
	@Test(dataProvider = "productTestData")
	public void productHeaderTest(String searchKey, String productName) {
		searchResPage = accPage.doSearch(searchKey);
		productInfoPage = searchResPage.selectProduct(productName);
		Assert.assertEquals(productInfoPage.getProductHeaderValue(), productName);
	}
	
	@Test(dataProvider = "getProductSheetData")
	public void productImagesCountTest(String searchKey, String productName, String expProductImgagesCount) {
		searchResPage = accPage.doSearch(searchKey);
		productInfoPage = searchResPage.selectProduct(productName);
		int actProductImgCount = productInfoPage.getProductImagesCount();
		System.out.println("Actual product image count is: " + actProductImgCount);
		Assert.assertEquals(actProductImgCount, Integer.parseInt(expProductImgagesCount));
	}
	
	@Test
	public void getProductInfoTest() {
		searchResPage = accPage.doSearch("macbook");
		productInfoPage = searchResPage.selectProduct("MacBook Pro");
		Map<String, String> productActualData = productInfoPage.getProductData();
		System.out.println(productActualData);
		softAssert.assertEquals(productActualData.get("Brand"), "Apple");
		softAssert.assertEquals(productActualData.get("Availability"), "In Stock");
		softAssert.assertEquals(productActualData.get("Ex Tax"), "$2,000.00");
		softAssert.assertEquals(productActualData.get("Product Code"), "Product 18");
		softAssert.assertEquals(productActualData.get("Reward Points"), "800");
		softAssert.assertAll();
	}
}
