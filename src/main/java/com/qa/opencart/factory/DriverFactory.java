package com.qa.opencart.factory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverFactory {
	
	WebDriver driver;
	Properties prop;
	OptionsManager optionsManager;
	public static String highlight;
	
	public WebDriver initDriver(Properties prop) {
		//String browserName = prop.getProperty("browser");
		
		String browserName = System.getProperty("browser");
		
		if(browserName.toLowerCase().trim() == null) {
			browserName = prop.getProperty("browser");
		}
		
		System.out.println("Running on browser " + browserName);
		
		highlight = prop.getProperty("highlight");
		
		optionsManager = new OptionsManager(prop);
		
		switch (browserName.toLowerCase()) {
		case "chrome":
			driver=new ChromeDriver(optionsManager.getChromeOptions());
			break;
		case "firefox":
			driver=new FirefoxDriver(optionsManager.getFirefoxOptions());
			break;
		case "edge":
			driver=new EdgeDriver(optionsManager.getEdgeOptions());
			break;
		default:
			System.out.println("Please pass the right browser....");
			break;
		}
		
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.get(prop.getProperty("url"));
		
		return driver;
	}
	
	public Properties initProp() {
		
		//mvn clean install -Denv="qa"
		
		FileInputStream ip = null;  
		
		String envName = System.getProperty("env");
		System.out.println("env name is : "+envName);
		try {
		if(envName == null) {
			System.out.println("no env is given... hence tunning it on QA env..by default");
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
				ip = new FileInputStream("./src/test/resources/config/prod.config.properties");
				break;

			default:
				System.out.println("Please pass the right env name..." + envName);
				break;
			}
		}

		}catch (FileNotFoundException e){
			e.printStackTrace();
		}
		prop = new Properties();
		try {
			prop.load(ip);
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		return prop;
		
	}

}
