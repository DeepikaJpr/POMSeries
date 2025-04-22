package com.qa.opencart.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.util.ElementUtil;

public class SearchResultsPage {

	private WebDriver driver;
	private ElementUtil eleUtil;
	public static final Logger log = LogManager.getLogger(SearchResultsPage.class);


	public SearchResultsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil= new ElementUtil(driver);
	}

	private By productResults = By.cssSelector("div.product-thumb");

	public int getProductResultsCount() {
		
		int resultCount=eleUtil.waitForElementsVisible(productResults, AppConstants.DEFAULT_TIMEOUT).size();
		//int resultCount = driver.findElements(productResults).size();
		log.info("product result count===>" + resultCount);
		return resultCount;
	}
	
	public ProductInfoPage selectProduct(String productName) {
		log.info("product name is:"+ productName);
		
		eleUtil.doClick(By.linkText(productName));
		//driver.findElement(By.linkText(productName)).click();
		
		return new ProductInfoPage(driver);
	}

}
