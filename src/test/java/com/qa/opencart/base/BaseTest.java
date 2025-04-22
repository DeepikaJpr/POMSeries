package com.qa.opencart.base;


import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.aventstack.chaintest.service.ChainPluginService;
import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.pages.CommonsPage;
import com.qa.opencart.pages.HomePage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.ProductInfoPage;
import com.qa.opencart.pages.SearchResultsPage;

//@Listeners(ChainTestListener.class)
public class BaseTest {

	// Note: we must not extend DriverFactory to get the driver, as BaseTest is not
	// logically child of DriverFactory
	// Instead we will create object of the class and use the driver available.

	WebDriver driver;
	DriverFactory df;
	protected Properties prop;
	protected LoginPage loginPage; // we are trying to access the LoginPage variable outside of package
									// i.e. LoginPageTest[under tests] hence 'Protected' is the right access
									// modifier
	protected HomePage homePage;
	protected SearchResultsPage searchResultsPage;
	protected ProductInfoPage productInfoPage;
	protected CommonsPage commonsPage;

	@Parameters({"browser"})
	@BeforeTest
	public void setup(String browserName) {
		df = new DriverFactory();
		prop = df.initProp();
		
		if(browserName !=null) {
			prop.setProperty("browser", browserName);
		}
		
		driver = df.initDriver(prop);
		loginPage = new LoginPage(driver);
		// loginPage.doLogin("POMSeries@gmail.com", "POMSeries") :: We can't setup Login
		// functionality here
		// as it will affect the loginPage tests which do not require login
		// To setup the Login in all Test classes @BeforeClass will be used in .
		commonsPage = new CommonsPage(driver);
		
		ChainPluginService.getInstance().addSystemInfo("Owner", "Deepika S");
		ChainPluginService.getInstance().addSystemInfo("Headless", prop.getProperty("headless"));
		ChainPluginService.getInstance().addSystemInfo("Incognito", prop.getProperty("incognito"));
	}

	@AfterMethod
	public void attachScreenshot(ITestResult result) {
	    if (!result.isSuccess()) {
	        ChainTestListener.embed(DriverFactory.getScreenshotFile(), "image/png");
	    }
	}


	@AfterTest
	public void tearDown() {
		driver.quit();
	}

}
