package com.demo.qa.testcases;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.bouncycastle.util.test.TestResult;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.demo.qa.ExtentReportListener.ExtentReporterNG;
import com.demo.qa.base.TestBase;
import com.demo.qa.pages.HomePage;
import com.demo.qa.pages.LoginPage;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;

public class LoginPageTest extends TestBase{
	
	LoginPage loginPage;
	HomePage homePage;
	Logger log = Logger.getLogger(LoginPageTest.class);
	
	public LoginPageTest() {
		super();
	}
	
	
	@BeforeTest
	public void setExtent() {
		 extent = new ExtentReports(System.getProperty("user.dir")+"/test-output/ExtentReport.html", true);
		 extent.addSystemInfo("Host Name", "Tushar Machine");
		 extent.addSystemInfo("User Name", "tusharp");
		 extent.addSystemInfo("Enviornment", "QA");
	}
	
	@AfterTest
	public void endReport() {
		extent.flush();
		extent.close();
	}
	
	public static String getScreenshot(WebDriver driver, String screeshotName) throws IOException {
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File Source = ts.getScreenshotAs(OutputType.FILE);
		String destination = System.getProperty("user.dir") + "/FailedTestsScreenshots/" + screeshotName + dateName
				+ ".png";
		File finalDestination = new File(destination);
		FileUtils.copyFile(Source, finalDestination);
		return destination;
	}
	
	@BeforeMethod
	public void setUp() {
		log.info("****************************** Starting test cases execution  *****************************************");
		initialization();
		log.info("launching chrome broswer");
		log.info("entering application URL");
		log.warn("Hey this just a warning message");
		log.fatal("hey this is just fatal error message");
		log.debug("this is debug message");
		loginPage = new LoginPage();
	}
	
	@Test(priority=1)
	public void loginPageTitleTest() {
		log.info("****************************** starting test case *****************************************");
		log.info("****************************** loginPageTitleTest *****************************************");
		extentTest = extent.startTest("loginPageTitleTest");
		String title = loginPage.validateLoginPageTitle();
		log.info("login page title is--->"+title);
		Assert.assertEquals(title, "Free CRM software for customer relationship management, sales, marketing campaigns and support.");
		log.info("****************************** ending test case *****************************************");
		log.info("****************************** loginPageTitleTest *****************************************");

	}
	
	@Test(priority=2)
	public void crmLogoImageTest() {
		extentTest = extent.startTest("crmLogoImageTest");
		boolean flag = loginPage.validateCRMImage();
		Assert.assertTrue(flag);
	}
	
	@Test(priority=3)
	public void loginTest() {
		extentTest = extent.startTest("loginTest");
		homePage = loginPage.login(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	@AfterMethod
	public void tearDown(ITestResult result) throws IOException {
		if(result.getStatus()==ITestResult.FAILURE) {
			extentTest.log(LogStatus.FAIL, "Test Case Failed is "+ result.getName()); //to add name in extent report
			extentTest.log(LogStatus.FAIL, "Test Case Failed is "+ result.getThrowable()); // to add error/exception in extent report
			
			String screenshotPath = getScreenshot(driver, result.getName());
			extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(screenshotPath));
			extentTest.log(LogStatus.FAIL, extentTest.addScreencast(screenshotPath)); //to add screenshot in extent report
		}
		else if(result.getStatus()==ITestResult.SKIP) {
			extentTest.log(LogStatus.SKIP, "Test Case Skipped is " + result.getName());
		}
		else if(result.getStatus()==ITestResult.SUCCESS) {
			extentTest.log(LogStatus.PASS, "Test Case Passed is " + result.getName());
		}
		
		extent.endTest(extentTest); // ending test and ends the current test and prepare to create html report 
		driver.quit();
		
		log.info("****************************** Browser is closed *****************************************");
	}
	
}