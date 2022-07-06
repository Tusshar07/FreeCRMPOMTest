package com.demo.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.demo.qa.base.TestBase;

public class HomePage extends TestBase {

	@FindBy(xpath = "//span[contains(text(), 'Tushar P')]")
	@CacheLookup //to improve script performance we use this annotation
	WebElement userNameLabel;

	@FindBy(xpath = "//span[contains(text(), 'Contacts')]")
	WebElement contactsLink;

	@FindBy(xpath = "//span[contains(text(), 'Deals')]")
	WebElement dealsLink;

	@FindBy(xpath = "//span[contains(text(), 'Tasks')]")
	WebElement tasksLink;
	
	@FindBy(xpath = "//div[@class='ui basic icon left attached button']")
	WebElement contactsHoverButton;

	@FindBy(xpath = "//a[@href='/contacts/new']")
	WebElement newContactButton;
	
	// Initializing the Page Objects:
	public HomePage() {
		PageFactory.initElements(driver, this);
	}
	
	public String verifyHomePageTitle() {
		return driver.getTitle();
	}
	
	public boolean verifyCorrectUserName() {
		return userNameLabel.isDisplayed();
	}
	
	public ContactsPage clickOnContactsLink() {
		contactsLink.click();
		return new ContactsPage();
	}

	public DealsPage clickOnDealsLink() {
		dealsLink.click();
		return new DealsPage();
	}
	
	public TasksPage clickOnTasksLink() {
		tasksLink.click();
		return new TasksPage();
	}
	
	public void clickOnNewContactLink() throws InterruptedException {
		Actions action = new Actions(driver);
		contactsLink.click();
		Thread.sleep(2000);
		action.moveToElement(contactsHoverButton).build().perform();
		Thread.sleep(2000);
		newContactButton.click();
	}
}
