package com.qa.opencart.pages;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.util.ElementUtil;

public class LoginPage {

	private WebDriver driver;
	private ElementUtil eleUtil;
	
	public static final Logger log = LogManager.getLogger(LoginPage.class);

	
	//Encapsulation: private driver initialized with public layer constructor
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		eleUtil=new ElementUtil(driver);
	}

	// 1. By Locator: page Objects :OR Object Repository
	private By emailId = By.id("input-email");
	private By password = By.id("input-password");
	private By loginBtn = By.xpath("//input[@value='Login']");
	private By forgotPwdLink = By.linkText("Forgotten Password");

	// 2. Public page actions - methods(features)
	public String getLoginPageTitle() {
		String title=eleUtil.waitForTitleIs(AppConstants.LOGIN_PAGE_TITLE, AppConstants.DEFAULT_TIMEOUT);
		//String title = driver.getTitle();
		log.info("===Login Page Title====" + title);
		return title;
	}

	public String getLoginPageURL() {
		String url=eleUtil.waitForURLContains(AppConstants.LOGIN_PAGE_URL_FRACTION, AppConstants.DEFAULT_TIMEOUT);
		//String url = driver.getCurrentUrl();
		log.info("===Login Page URL" + url);
		return url;
	}
	
	public Boolean isForgotPwdLinkExist() {
		//Encapsulation is used here Public layer accessing private vars
		return eleUtil.doIsElementDisplayed(forgotPwdLink);
		//return driver.findElement(forgotPwdLink).isDisplayed();
	}
	
	public HomePage doLogin(String username, String pwd) {
		log.info("App creds are ===>"+username+" :"+pwd);
		/*
		 * driver.findElement(emailId).sendKeys(username);
		 * driver.findElement(password).sendKeys(pwd);
		 * driver.findElement(loginBtn).click();
		 */
		//eleUtil.doSendKeys(emailId, username);
		//eleUtil.doSendKeys(password, pwd);
		//eleUtil.doClick(loginBtn);
		//return next page object
		
		eleUtil.waitForElementVisible(emailId, AppConstants.DEFAULT_TIMEOUT).sendKeys(username);
		eleUtil.doSendKeys(password, pwd);
		eleUtil.doClick(loginBtn);
		return new HomePage(driver);
	}

}




