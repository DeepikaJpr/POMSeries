package com.qa.opencart.tests;


import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.constants.AppError;

public class HomePageTest extends BaseTest {

	@BeforeTest
	public void homePageSetup() {
		homePage=loginPage.doLogin(prop.getProperty("username"),prop.getProperty("password"));
	}

	@Test
	public void homePageTitleTest() {
		Assert.assertEquals(homePage.getHomePageTitle(), AppConstants.HOME_PAGE_TITLE, AppError.TITLE_NOT_FOUND_ERROR);
	}
	
	@Test
	public void homePageURLTest() {
		Assert.assertTrue(homePage.getHomePageURL().contains(AppConstants.HOME_PAGE_URL_FRACTION), AppError.URL_NOT_FOUND_ERROR); 
	}
	
	@Test
	public void logoutLinkExistTest() {
		Assert.assertTrue(homePage.isLogoutLinkExist(),AppError.ELEMENT_NOT_FOUND_ERROR);
	}
	
	@Test
	public void headersTest() {
		List<String> actualHeaders = homePage.getHeaderList();
		System.out.println("home page headers:" +actualHeaders);
	}
	
	@DataProvider
	public Object[][] getSearchData(){
		return new Object[][] {
			{"macbook", 3},
			{"imac", 1},
			{"samsung",2},
			{"airtel",0}
		};
		
	}
	
	@Test(dataProvider = "getSearchData")
	public void searchTest(String searchKey, int resultCount) {
		searchResultsPage= homePage.doSearch(searchKey);
		Assert.assertEquals(searchResultsPage.getProductResultsCount(), resultCount);
		
	}

}
