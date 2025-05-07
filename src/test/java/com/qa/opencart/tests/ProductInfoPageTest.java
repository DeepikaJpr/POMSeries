package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.util.ExcelUtil;

public class ProductInfoPageTest extends BaseTest {

	@BeforeClass
	public void prodctInfoSetup() {
		homePage = loginPage.doLogin(prop.getProperty("username"),prop.getProperty("password"));
	}

	@DataProvider
	public Object[][] getProductData() {
		return new Object[][] { { "macbook", "MacBook Pro" }, { "macbook", "MacBook Air" }, { "imac", "iMac" },
				{ "samsung", "Samsung SyncMaster 941BW" }, { "samsung", "Samsung Galaxy Tab 10.1" }

		};

	}

	@Test(dataProvider = "getProductData")
	public void productSearchHeaderTest(String searchKey, String productName) {
		searchResultsPage = homePage.doSearch("macbook");
		productInfoPage = searchResultsPage.selectProduct("MacBook Pro");
		String actualProductHeader = productInfoPage.getProductHeader();
		Assert.assertEquals(actualProductHeader, "MacBook Pro", "=====Product Header is not matched=====");
	}

	@DataProvider
	public Object[][] getProductImagesData() {
		return new Object[][] { 
			{ "macbook", "MacBook Pro", 4 },
			{ "macbook", "MacBook Air", 4 },
			{ "imac", "iMac", 3 },
			{ "samsung", "Samsung SyncMaster 941BW", 1 },
			{ "samsung", "Samsung Galaxy Tab 10.1", 7 }
		};

	}

	@DataProvider
	public Object[][] getProductImagesSheetData() {

		return ExcelUtil.getTestData(AppConstants.PRODUCT_SHEET_NAME);

	}

	@Test(dataProvider = "getProductImagesSheetData")
	public void productImagesCountTest(String searchKey, String productName, String expectedImagesCount) {
		searchResultsPage = homePage.doSearch(searchKey);
		productInfoPage = searchResultsPage.selectProduct(productName);
		int actualProductImagesCount = productInfoPage.getProductImagesCount();
		Assert.assertEquals(actualProductImagesCount, Integer.parseInt(expectedImagesCount),
				"=====Images count is not matched=====");
	}

	@DataProvider
	public Object[][] getProductMataInfoData() {
		return new Object[][] { { "macbook", "MacBook Pro" },

		};

	}

	@Test(dataProvider = "getProductMataInfoData")
	public void productInfoTest(String searchKey, String productName) {
		searchResultsPage = homePage.doSearch(searchKey);
		productInfoPage = searchResultsPage.selectProduct(productName);

		Map<String, String> productInfoMap = productInfoPage.getProductInfo();

		// lambda functiona
		productInfoMap.forEach((k, v) -> System.out.println(k + " : " + v));

		SoftAssert softAssert = new SoftAssert();

		softAssert.assertEquals(productInfoMap.get("Header"), "MacBook Pro");
		softAssert.assertEquals(productInfoMap.get("ImagesCount"), "4");

		softAssert.assertEquals(productInfoMap.get("Product Code"), "Product 18");
		softAssert.assertEquals(productInfoMap.get("Reward Points"), "800");
		softAssert.assertEquals(productInfoMap.get("Brand"), "Apple");
		softAssert.assertEquals(productInfoMap.get("Availability"), "Out Of Stock");

		softAssert.assertEquals(productInfoMap.get("extax"), "$2,000.00");
		softAssert.assertEquals(productInfoMap.get("price"), "$2,000.00");

		softAssert.assertAll();
	}

}
