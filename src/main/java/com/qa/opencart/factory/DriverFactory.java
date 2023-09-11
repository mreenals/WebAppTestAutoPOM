package com.qa.opencart.factory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

public class DriverFactory {
	
	WebDriver driver;
	Properties prop;
	OptionsManager optionsManager;
	public static String highlight;
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>(); //it is supported by jdk1.8 onwards
	
	public WebDriver initDriver(Properties prop) {
		
		/**
		 * This methos is used to initialize the driver 
		 * @param browserName
		 * @return it returns driver
		 */
		
		String browserName = prop.getProperty("browser");  //Properties prop (arg)
		
		//String browserName = System.getProperty("browser");
		
		System.out.println("Running on browser " + browserName);
		
		highlight = prop.getProperty("highlight");
		
		optionsManager = new OptionsManager(prop);
		
		switch (browserName.toLowerCase()) {
		case "chrome":
			//driver = new ChromeDriver(optionsManager.getChromeOptions());
			tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
			break;
		case "firefox":
			//driver = new FirefoxDriver(optionsManager.getFirefoxOptions());
			tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
			break;
		case "edge":
			//driver = new EdgeDriver(optionsManager.getEdgeOptions());
			tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
			break;
		case "safari":
			//driver = new SafariDriver();
			tlDriver.set(new SafariDriver());
			break;
		default:
			System.out.println("Please pass the right browser...." + browserName);
			break;
		}
		
		getDriver().manage().window().maximize();
		getDriver().manage().deleteAllCookies();
		getDriver().get(prop.getProperty("url"));
		
//		driver.manage().window().maximize();
//		driver.manage().deleteAllCookies();
//		driver.get(prop.getProperty("url"));
		
		return getDriver();
		//return driver;
	}
	
	public static WebDriver getDriver() {
		return tlDriver.get();
	}
	
	public Properties initProp() {
		
		//mvn clean install -Denv="qa"
		
		FileInputStream ip = null;
		prop = new Properties();
		String envName = System.getProperty("env");
		System.out.println("env name is : "+envName);
		try {
		if(envName == null) {
			System.out.println("no env is given... hence running it on QA env..by default");
			ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
		}
		else {
			switch (envName.toLowerCase().trim()) {
			case "qa":
				ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
				break;
			case "dev":
				ip = new FileInputStream("./src/test/resources/config/dev.config.properties");
				break;
			case "stage":
				ip = new FileInputStream("./src/test/resources/config/stage.config.properties");
				break;
			case "uat":
				ip = new FileInputStream("./src/test/resources/config/uat.config.properties");
				break;
			case "prod":
				ip = new FileInputStream("./src/test/resources/config/config.properties");
				break;

			default:
				System.out.println("Please pass the right env name..." + envName);
				break;
			}
		}

		}catch (FileNotFoundException e){
			e.printStackTrace();
		}
		try {
			prop.load(ip);
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		return prop;
		
	}

}
