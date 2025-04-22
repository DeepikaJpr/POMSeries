package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.constants.AppError;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

public class LoginPageTest extends BaseTest {

	@Test
	public void loginPageTitleTest() {
		ChainTestListener.log("===Login Page Title Test===");
		String actTitle = loginPage.getLoginPageTitle();
		Assert.assertEquals(actTitle, AppConstants.LOGIN_PAGE_TITLE, AppError.TITLE_NOT_FOUND_ERROR);
		
	}

	@Test
	public void loginPageUrlTest() {
		String actURL = loginPage.getLoginPageURL();
		Assert.assertTrue(actURL.contains(AppConstants.LOGIN_PAGE_URL_FRACTION), AppError.URL_NOT_FOUND_ERROR);

	}

	@Test
	public void forgotPwdLinkTest() {
		Assert.assertTrue(loginPage.isForgotPwdLinkExist(), AppError.ELEMENT_NOT_FOUND_ERROR);
	}

	
	@Test(priority = Integer.MAX_VALUE)
	@Description("Verify login with valid credentials")
	@Severity(SeverityLevel.CRITICAL)
	public void loginTest() {
		ChainTestListener.log("===Login Test===");
		Allure.step("Verifying login funtionality");
		homePage = loginPage.doLogin(prop.getProperty("username"),prop.getProperty("password"));
		Assert.assertEquals(homePage.getHomePageTitle(), AppConstants.HOME_PAGE_TITLE, AppError.TITLE_NOT_FOUND_ERROR);
	}
	
	@Test
	public void logoTest() {
		Assert.assertTrue(commonsPage.IsLogoDisplayed(),AppError.lOGO_NOT_FOUND);
	}
	
	@DataProvider
	public Object[][] getFooterTestData() {
		 return new Object[][] {
			{"About Us"},
			{"Contact US"},
			{"Specials"},
			{"Order History"}
		};
	}
	
	@Test(dataProvider = "getFooterTestData")
	public void footerTest(String footerLink) {
		commonsPage.checkFooterLinks(footerLink);
	}

}
