package com.qa.opencart.pages;


import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.util.ElementUtil;

public class ProductInfoPage {

	private WebDriver driver;
	private ElementUtil eleUtil;
	private Map<String,String> productMap;
	public static final Logger log = LogManager.getLogger(ProductInfoPage.class);

	
	public ProductInfoPage(WebDriver driver) {
		this.driver=driver;
		eleUtil = new ElementUtil(driver);
	}
	
	private By productHeaderName = By.cssSelector("div#content h1");	
	private By productImages = By.xpath("//ul[@class='thumbnails']//img");
	private By productMataData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[1]/li");
	private By productPriceData= By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[2]/li");
	
	public String getProductHeader() {
		return eleUtil.doElementGetText(productHeaderName);
		
	}
	
	public int getProductImagesCount() {
		  int productCount= eleUtil.waitForElementsVisible(productImages, AppConstants.DEFAULT_TIMEOUT).size();
		  log.info(getProductHeader()+": Product images count is "+productCount);
		  return productCount;
	}
	
	
	/**
	 * get full product information : Header, Images count, Product mata data, Product price data
	 * @return
	 */
	
	public Map<String, String> getProductInfo() {
		// productMap = new HashMap<String, String>(); // HashMap do not follow order
		//productMap = new TreeMap<String, String>(); // TreeMap will follow sorted order
		productMap = new LinkedHashMap<String, String>(); // LinkedHashMap will follow order
		productMap.put("Header", getProductHeader());
		productMap.put("ImagesCount", getProductImagesCount()+"");
		getProductMataData();
		getProductPriceData();
		
		return productMap;
	}
	
	private void getProductMataData() {
		
		List<WebElement> mataList =eleUtil.waitForElementsVisible(productMataData, AppConstants.DEFAULT_TIMEOUT);
		for(WebElement e: mataList) {
		String mataText	=e.getText();
		String mata[] =mataText.split(":");
		String mataKey = mata[0].trim();
		String mataValue = mata[1].trim();
		productMap.put(mataKey, mataValue);
		} 
	}
	
	private void getProductPriceData() {
		
		List<WebElement> priceList =eleUtil.waitForElementsVisible(productPriceData, AppConstants.DEFAULT_TIMEOUT);
		String productPrice = priceList.get(0).getText().trim();
		String productExTax= priceList.get(1).getText().split(":")[1].trim();
		productMap.put("price", productPrice);
		productMap.put("extax", productExTax);
		} 
	
	
}
