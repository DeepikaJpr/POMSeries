package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.util.ElementUtil;

public class CommonsPage {
	private WebDriver driver;
	private ElementUtil eleUtil;

	
	public CommonsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	private By logo = By.className("img-responsive");
	private By footer = By.xpath("//footer//a");
	
	public boolean IsLogoDisplayed() {
		return eleUtil.isElementDisplayed(logo);
	}
	
	public List<String> getFooterList() {
		List<WebElement> footerList = eleUtil.waitForElementsPresence(footer, AppConstants.DEFAULT_TIMEOUT);
		List<String> footer= new ArrayList<String>();
		
		for (WebElement e : footerList) {
		String text= e.getText();
		footer.add(text);
			
		}
		return footer;
	}
	
	public boolean checkFooterLinks(String footerLinks) {
		 return getFooterList().contains(footerLinks);
	}

}
