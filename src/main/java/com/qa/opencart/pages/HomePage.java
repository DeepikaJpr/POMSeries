package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.util.ElementUtil;

public class HomePage {

	private WebDriver driver;
	private ElementUtil eleUtil;
	
	public static final Logger log = LogManager.getLogger(HomePage.class);


	
	public HomePage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	// 1. Main By locators
	private By logoutLink = By.xpath("//a[@class='list-group-item' and text()='Logout']");
	private By headers = By.cssSelector("#content > h2");
	private By search = By.name("search");
	private By searchIcon = By.cssSelector("div#search button");

	// 2. Public page actions
	public String getHomePageTitle() {
		String title= eleUtil.waitForTitleIs(AppConstants.HOME_PAGE_TITLE, AppConstants.DEFAULT_TIMEOUT);
		//String title = driver.getTitle();
		log.info("Home Page title " + title);
		return title;
	}

	public String getHomePageURL() {
		String url=eleUtil.waitForURLContains(AppConstants.HOME_PAGE_URL_FRACTION, AppConstants.DEFAULT_TIMEOUT);
		//String url = driver.getCurrentUrl();
		log.info("Home Page url " + url);
		return url;
	}

	public boolean isLogoutLinkExist() {
		//return driver.findElement(logoutLink).isDisplayed(); :: In case of invalid By locator,
		//it will NOT return boolean false instead it will throw NoSuchElementException as the execution did not reach to '.isDisplayed'. 
		//Hence to cater this we will use 'findElements' which will return empty list in case no element is present
		//return eleUtil.isElementDisplayed(logoutLink);
		
		
		//OR we can surround the driver.findElement() in Try-Catch to handle NSE. Check below implementation #useIsDisplayed()
		return eleUtil.doIsElementDisplayed(logoutLink);
		
	}

	public void logout() {
		if (isLogoutLinkExist()) {
			eleUtil.doClick(logoutLink);
		}//WIP Pending
	}

	public List<String> getHeaderList() {
		
		List<WebElement> headerList= eleUtil.waitForElementsVisible(headers, AppConstants.DEFAULT_TIMEOUT);
		//List<WebElement> headerList = eleUtil.getElements(headers);
		List<String> headerValList = new ArrayList<String>();
		for (WebElement e : headerList) {
			String text= e.getText();
			headerValList.add(text);
		}
		return headerValList;
	}
	
	public SearchResultsPage doSearch(String searchKey) {
		log.info("Search Key: "+searchKey);
//		driver.findElement(search).sendKeys(searchKey);
//		driver.findElement(searchIcon).click();
		//eleUtil.doSendKeys(search, searchKey);
	WebElement searchEle = eleUtil.waitForElementVisible(search,AppConstants.DEFAULT_TIMEOUT);
		eleUtil.doSendKeys(searchEle, searchKey);
		eleUtil.doClick(searchIcon);
		return new SearchResultsPage(driver);
	}

}
