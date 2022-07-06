package com.demo.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.demo.qa.base.TestBase;

public class ContactsPage extends TestBase {

	@FindBy(xpath = "//span[contains(text(), 'Contacts')]")
	WebElement contactsLabel;
	
	@FindBy(xpath = "//div[contains(text(), 'Contacts')]")
	WebElement contactsTableLabel;
	
	@FindBy(xpath = "//div[@class='ui basic icon left attached button']")
	WebElement contactsPageTableButton;
	
	@FindBy(name = "first_name")
	WebElement firstName;
	
	@FindBy(name = "last_name")
	WebElement lastName;
	
	@FindBy(xpath = "//button[contains(text(), 'Save')]")
	WebElement saveBtn;
	
	// Initializing the Page Objects:
	public ContactsPage() {
		PageFactory.initElements(driver, this);
	}
	
	public boolean verifyContactsLabel() throws InterruptedException {
		Actions action = new Actions(driver);
		contactsLabel.click();
		Thread.sleep(2000);
		action.moveToElement(driver.findElement(By.xpath("//div[@class='ui basic icon left attached button']"))).build().perform();
		Thread.sleep(2000);
		return contactsTableLabel.isDisplayed();
	}

	public void selectContactsByName(String name) throws InterruptedException {
		Actions action = new Actions(driver);
		Thread.sleep(2000);
		action.moveToElement(contactsPageTableButton).build().perform();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//a[contains(text(), '"+name+"')]/parent::td//preceding-sibling::td//input[@name='id']")).click();
	}
	
	public void createNewContact(String ftName, String ltname) throws InterruptedException {
		firstName.sendKeys(ftName);
		Thread.sleep(1000);
		lastName.sendKeys(ltname);
		Thread.sleep(1000);
		saveBtn.click();
		Thread.sleep(2000);
	}
}
