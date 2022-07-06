package com.demo.qa.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.demo.qa.base.TestBase;

public class LoginPage extends TestBase{
	
	//Page Factory - OR(Object Repository):
	@FindBy(xpath = "//a[contains(text(), 'Login')]")
	WebElement loginLink;
	
	@FindBy(name = "email")
	WebElement emailAddress;
	
	@FindBy(name = "password")
	WebElement password;
	
	@FindBy(xpath = "//div[@class='ui fluid large blue submit button']")
	WebElement loginBtn;
	
	@FindBy(xpath = "//a[contains(text(), 'Sign Up')]")
	WebElement signUpLink;

	@FindBy(xpath = "//img[@class='img-responsive' and @src='https://freecrm.com/images/freecrm_logo.png']")
	WebElement crmLogo;
	
	//Initializing the Page Objects:
	public LoginPage() {
		PageFactory.initElements(driver, this);
	}
	
	//Actions:
	public String validateLoginPageTitle() {
		return driver.getTitle();
	}
	
	public boolean validateCRMImage() {
		return crmLogo.isDisplayed();
	}
	
	public HomePage login(String emailAdd, String pwd) {
		loginLink.click();
		emailAddress.sendKeys(emailAdd);
		password.sendKeys(pwd);
		loginBtn.click();
		
		return new HomePage();
	}
	
	
}
