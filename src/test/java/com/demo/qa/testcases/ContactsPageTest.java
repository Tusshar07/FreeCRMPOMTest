package com.demo.qa.testcases;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.demo.qa.base.TestBase;
import com.demo.qa.pages.ContactsPage;
import com.demo.qa.pages.HomePage;
import com.demo.qa.pages.LoginPage;
import com.demo.qa.util.TestUtil;

public class ContactsPageTest extends TestBase{
	
	LoginPage loginPage;
	HomePage homePage;
	TestUtil testUtil;
	ContactsPage contactsPage;
	
	String sheetName = "contacts";
	
	public ContactsPageTest() {
		super();
	}
	

	@BeforeMethod
	public void setUp() {
		initialization();
		testUtil = new TestUtil();
		loginPage = new LoginPage();
		homePage = loginPage.login(prop.getProperty("username"), prop.getProperty("password"));
		contactsPage = homePage.clickOnContactsLink();
		contactsPage = new ContactsPage();
	}
	
	@Test(priority=1)
	public void verifyContactsPageLabel() throws InterruptedException {
		Assert.assertTrue(contactsPage.verifyContactsLabel(), "Contacts label is missing on the page");
	}
	
	@Test(priority=2)
	public void selectSingleContactsTest() throws InterruptedException {
		contactsPage.selectContactsByName("test2 test2");
	}
	
	@Test(priority=3)
	public void selectMultipleContactsTest() throws InterruptedException {
		contactsPage.selectContactsByName("test2 test2");
		contactsPage.selectContactsByName("ui uii");
	}
	
	
	//DataProvider is used to provide the data from excel
	@DataProvider
	public Object[][] getCRMTestData() {
		Object data[][] = TestUtil.getTestData(sheetName);
		return data;
	}
	
	@Test(priority=4, dataProvider = "getCRMTestData")
	public void validateCreateNewContact(String firstName, String lastName) throws InterruptedException {
		homePage.clickOnNewContactLink();
		contactsPage.createNewContact(firstName, lastName);
	}
	
	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

}
