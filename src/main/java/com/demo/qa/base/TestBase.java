package com.demo.qa.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.events.WebDriverListener;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.demo.qa.util.TestUtil;
import com.demo.qa.util.WebEventListener;

public class TestBase {

	public static WebDriver driver;
	public static Properties prop;
	public static EventFiringDecorator e_driver;
	public static WebEventListener eventListener;
	public static WebDriver e_decorated;


	public TestBase() {
		try {
			prop = new Properties();
			FileInputStream ip = new FileInputStream(
					"C:\\Users\\Tushar\\Automation\\JavaTraining\\POMTest\\src\\main\\java\\com\\demo\\"
							+ "qa\\config\\config.properties");
			prop.load(ip);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();

		}
	}
	
	public static void initialization() {
		String browsername = prop.getProperty("browser");
		if(browsername.equals("chrome")) {
			System.setProperty("webdriver.chrome.driver", "G:\\Selenium\\BrowserDrivers\\chromedriver.exe");
			driver = new ChromeDriver();
		} 
		else if(browsername.equals("firefox")) {
			System.setProperty("webdriver.gecko.driver", "G:\\\\Selenium\\\\BrowserDrivers\\\\geckodriver.exe");
			driver = new FirefoxDriver();
		}
		
		
		/*
		 * e_driver = new EventFiringWebDriver(driver); // Now create object of
		 * EventListerHandler to register it with EventFiringWebDriver eventListener =
		 * new WebEventListener(); e_driver.register(eventListener); driver = e_driver;
		 */
		
		//e_driver = new EventFiringDecorator(driver);
		/*
		 * eventListener = new WebEventListener(); e_driver = (EventFiringDecorator) new
		 * EventFiringDecorator(eventListener).decorate(driver); e_driver =
		 * (EventFiringDecorator) driver;
		 * 
		 * 
		 * eventListener = new WebEventListener();
		 */
		
		e_driver = new EventFiringDecorator(eventListener);
		WebDriverListener listener = new WebEventListener();
		e_decorated = new EventFiringDecorator(listener).decorate(driver);
		driver = e_decorated;
		
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		
		//driver.manage().timeouts().pageLoadTimeout(TestUtil.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
		//driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(TestUtil.PAGE_LOAD_TIMEOUT));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(TestUtil.IMPLICIT_WAIT));
		
		driver.get(prop.getProperty("url"));
	}
}
