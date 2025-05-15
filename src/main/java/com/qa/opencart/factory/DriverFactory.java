package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.exception.FrameworkException;

public class DriverFactory {

	WebDriver driver;
	Properties prop;
	OptionsManager optionsManager;
	public static Boolean highlight;

	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();
	public static final Logger log = LogManager.getLogger(DriverFactory.class);
	
	
	public WebDriver initDriver(Properties prop) {

		String browserName = prop.getProperty("browser");
		//System.out.println("browser name is :" + browserName);
		log.info("browser name is: "+browserName);

		highlight = Boolean.parseBoolean(prop.getProperty("highlight"));
		optionsManager = new OptionsManager(prop);
		
		boolean remoteExecution= Boolean.parseBoolean(prop.getProperty("remote"));
		
		switch (browserName.trim().toLowerCase()) {
		case "chrome":
			if(remoteExecution) {
				initRemoteDriver(browserName);
			}else {
			tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
			// driver = new ChromeDriver(optionsManager.getChromeOption());
			}
			break;
		case "firefox":
			if(remoteExecution) {
				initRemoteDriver(browserName);
			}else {
			tlDriver.set(new FirefoxDriver(optionsManager.getFireFoxOptions()));
			// driver = new FirefoxDriver(optionsManager.getFireFoxOption());
			}
			break;
		case "edge":
			if(remoteExecution) {
				initRemoteDriver(browserName);
			}else {
			tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
			// driver = new EdgeDriver(optionsManager.getEdgeOption());
			}
			break;
		default:
			//System.out.println("Please pass valid browserName :" + browserName);
			log.error("Please pass valid browserName :" + browserName);
			throw new FrameworkException("====Invalid browser====");
		}

		// driver.manage().deleteAllCookies();
		// driver.manage().window().maximize();
		// driver.get(prop.getProperty("url"));

		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().get(prop.getProperty("url"));

		return getDriver();

	}
	
	@SuppressWarnings("deprecation")
	private void initRemoteDriver(String browserName) {
		System.out.println("Running tests on grid"+ browserName);
		
		try {
		switch (browserName.toLowerCase().trim()) {
		case "chrome":
			tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getChromeOptions()));
			break;
		case "firfox":
			tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getFireFoxOptions()));
			break;
		case "edge":
			tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getEdgeOptions()));
			break;

		default:
			System.out.println("browser is not supported on GRID... " + browserName);
			break;
		}}
		catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		//return driver;
	}

	public static WebDriver getDriver() {
		return tlDriver.get();
	}
	
	//supply the env from maven command line
	// mvn clean install -Denv="qa"

	public Properties initProp() {
		prop = new Properties();
		FileInputStream ip;
		String envName = System.getProperty("env");// This line takes input from command line
		log.info("Tests running on "+envName);
		
		try {
			if (envName == null) {
				log.warn("====no env is passed, so running suite on QA env");
				ip = new FileInputStream(AppConstants.CONFIG_PROP_FILE_PATH);
			} else {

				switch (envName.trim().toLowerCase()) {
				case "qa":
					ip = new FileInputStream(AppConstants.CONFIG_PROP_FILE_PATH);
					break;
				case "dev":
					ip = new FileInputStream(AppConstants.CONFIG_DEV_PROP_FILE_PATH);
					break;
				case "uat":
					ip = new FileInputStream(AppConstants.CONFIG_UAT_PROP_FILE_PATH);
					break;
				case "prod":
					ip = new FileInputStream(AppConstants.CONFIG_PROD_PROP_FILE_PATH);
					break;
				default:
					throw new FrameworkException("====Invalid Env====");
				}
			}
			prop.load(ip);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			log.info(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			log.info(e.getMessage());
		}
		return prop;
	}
	
	
	
	/**
	 * Takes Screenshots
	 */
	
	public static String getScreenshot() {
	    File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
	    String path = System.getProperty("user.dir") + "/target/chaintest/resources/" + System.currentTimeMillis() + ".png";
	    
	    File destination = new File(path);

	    try {
	        FileHandler.copy(srcFile,destination);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    return path;
	}

	

	public static File getScreenshotFile() {
		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);// temp dir
		return srcFile;
	}
	
	public static byte[] getScreenshotByte() {
		return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);// temp dir

	}

	public static String getScreenshotBase64() {
		return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BASE64);// temp dir

	}
}
