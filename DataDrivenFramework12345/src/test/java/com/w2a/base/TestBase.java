package com.w2a.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.w2a.utilities.ExcelReader;
import com.w2a.utilities.ExtentManager;

import okio.Timeout;
import sun.misc.Timeable;

public class TestBase {

	/*
	 * WebDriver Properties Logs ExtentReports Excel Mail
	 */

	public static WebDriver driver;
	public static Properties config = new Properties();
	public static Properties or = new Properties();
	public static FileInputStream fis;
	public static Logger log = Logger.getLogger("devpinoyLogger");
	public static ExcelReader excel = new ExcelReader(
			System.getProperty("user.dir") + "\\src\\test\\resources\\excel\\testdata.xlsx");
	public static WebDriverWait wait;
	public ExtentReports rep = ExtentManager.getInstance();
	public static ExtentTest test;
//Update Git
	@BeforeSuite
	public void setUp() throws IOException {

		if (driver == null) {
			fis = new FileInputStream(
					System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\Config.properties");
			config.load(fis);
			log.debug("Config file Load !!!");

			fis = new FileInputStream(
					System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\OR.properties");
			or.load(fis);
			log.debug("OR file Load !!!");
		}

		if (config.getProperty("browser").equals("firefox")) {
			System.out.println(config.getProperty("browser"));
			System.setProperty("webdriver.gecko.driver",
					System.getProperty("user.dir") + "\\src\\test\\resources\\executables\\geckodriver.exe");
			driver = new FirefoxDriver();

		} else if (config.getProperty("browser").equals("chrome")) {
			System.out.println(config.getProperty("browser"));
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + "\\src\\test\\resources\\executables\\chromedriver.exe");
			driver = new ChromeDriver();
			log.debug("Chrome Browser Launched !!!");

		} else if (config.getProperty("browser").equals("ie")) {
			System.out.println(config.getProperty("browser"));
			System.setProperty("webdriver.ie.driver",
					System.getProperty("user.dir") + "\\src\\test\\resources\\executables\\IEDriverServer.exe");
			driver = new InternetExplorerDriver();

		}

		driver.get(config.getProperty("testsiteurl"));
		log.debug("Navigate to =" + config.getProperty("testsiteurl"));
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicitlyWait")),
				TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, 10);
	}

	public void click(String locator) {
		if (locator.endsWith("_xpath")) {
			driver.findElement(By.xpath(or.getProperty(locator))).click();
		} else if (locator.endsWith("_css")) {
			driver.findElement(By.className(or.getProperty(locator))).click();
		} else if (locator.endsWith("_id")) {
			driver.findElement(By.id(or.getProperty(locator))).click();
		}

		test.log(LogStatus.INFO, "Clicking on:-" + locator);
	}

	public void clean(String locator) {
		if (locator.endsWith("_xpath")) {
			driver.findElement(By.xpath(or.getProperty(locator))).clear();
		} else if (locator.endsWith("_css")) {
			driver.findElement(By.className(or.getProperty(locator))).clear();
		} else if (locator.endsWith("_id")) {
			driver.findElement(By.id(or.getProperty(locator))).clear();
		}
		test.log(LogStatus.INFO, "Clearing on:-" + locator);
	}

	public void type(String locator, String value) {

		if (locator.endsWith("_xpath")) {
			driver.findElement(By.xpath(or.getProperty(locator))).sendKeys(value);
		} else if (locator.endsWith("_css")) {
			System.out.println("locator:-"+locator);
			driver.findElement(By.className(or.getProperty(locator))).sendKeys(value);
		} else if (locator.endsWith("_id")) {
			driver.findElement(By.id(or.getProperty(locator))).sendKeys(value);
		}

		test.log(LogStatus.INFO, "Typeing in : " + locator + " Entered value as " + value);
	}

	public boolean isElementPresent(By by) {
		try {

			driver.findElement(by);
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}

	}

	@AfterSuite
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
		log.debug("Test execution Succefully !!!");
	}

}
