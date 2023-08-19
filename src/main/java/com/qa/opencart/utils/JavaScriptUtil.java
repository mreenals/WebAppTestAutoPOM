package com.qa.opencart.utils;

import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JavaScriptUtil {
	
	private WebDriver driver;
	private JavascriptExecutor js;
	
	public JavaScriptUtil(WebDriver driver) {
		this.driver = driver;
		js= (JavascriptExecutor)this.driver;
	}
	
	public String getTitleByJs() {
		return js.executeScript("return document.title;").toString();
	}
	
	public void goBackWithJs() {
		js.executeScript("history.go(-1);");
	}
	public void goForwardWithJs() {
		js.executeScript("history.go(1);");
	}
	public void goRefreshWithJs() {
		js.executeScript("history.go(0);");
	}
	public void generateJSAlert(String alert) {
		js.executeScript("alert('"+alert+"')");
		try {
			Thread.sleep(500);
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		driver.switchTo().alert().accept();
	}
	
	public void generateJSConfirm(String mesg) {
		js.executeScript("confirm('"+mesg+"')");
		try {
			Thread.sleep(500);
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		driver.switchTo().alert().accept();
	}
	
	public void generateJSPrompt(String mesg, String value) {
		js.executeScript("prompt('"+mesg+"')");
		try {
			Thread.sleep(500);
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		Alert alert = driver.switchTo().alert();
		alert.sendKeys(value);
		alert.accept();
	}
	
	public String getPageInnerText() {
		return js.executeScript("return document.documentElement.innerText;").toString();
	}
	
	public void scrollPageUp() {
		js.executeScript("window.scrollTo(document.body.scrollHeight, 0)");
	}
	
	public void scrollPageDown() {
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}
	
	public void scrollPageDownMiddlePage() {
		js.executeScript("window.scrollTo(0, document.body.scrollHeight/2)");
	}
	
	public void scrollPageDown(String height) {
		js.executeScript("window.scrollTo(0, " + height + "\")");
	}
	
	public void scrollIntoView(WebElement element) {
		js.executeScript("arguments[0].scrollIntoView(true);", element );
	}
	
	public void zoomChromeEdgeSafari(String zoomPercentage) {
		js.executeScript("document.body.style.zoom = \'" + zoomPercentage + "%\'");
	}
	
	public void zoomFirefox(String zoomPercentage){
		js.executeScript("document.body.style.MozTransform=\'scale(" + zoomPercentage + "%)\'");
	}
	
	public void drawBorder(WebElement element) {
		js.executeScript("arguments[0].style.border=\'3px solid red\'", element);
	}
	
	public void flash(WebElement element) {
		String bgcolor = element.getCssValue("backgroundColor");
		for (int i = 0; i < 10; i++) {
			changeColor("rgb(0,200,0)", element);// 1
			changeColor(bgcolor, element);// 2
		}
	}

	private void changeColor(String color, WebElement element) {
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("arguments[0].style.backgroundColor = '" + color + "'", element);
		//G->P->G->P

		try {
			Thread.sleep(35);
		} catch (InterruptedException e) {
		}
	}

}
