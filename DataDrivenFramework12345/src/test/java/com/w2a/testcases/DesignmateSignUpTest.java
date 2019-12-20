package com.w2a.testcases;

import org.apache.poi.hssf.extractor.ExcelExtractor;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.w2a.base.TestBase;
import com.w2a.utilities.TestUtil;

public class DesignmateSignUpTest extends TestBase {

	@BeforeTest
	public void testbefor() {
		click("singUpAndLoginBtn_xpath");
	}

	@Test(dataProviderClass = TestUtil.class,dataProvider="dp")
	public void designmateSignUpTest(String firstname, String lastname, String email, String passWord, String configPwd)
			throws InterruptedException
//	,String alerttext)
	{
		if(!TestUtil.isTestRunable("designmateSignUpTest", excel)) {
			
			throw new SkipException("Skipping the test " + "  designmateSignUpTest".toUpperCase() + "  as the run mode is No");
		}
		
		click("loginSignUpBtn_xpath");
		
		
		clean("firstNameSignup_xpath");
		
		
		 type("firstNameSignup_xpath",firstname);

		clean("lastNameSignup_xpath");
		type("lastNameSignup_xpath",lastname);

		clean("emailIdSignup_xpath");
		type("emailIdSignup_xpath",email);

		clean("passWordSignup_xpath");
		type("passWordSignup_xpath",passWord);

		clean("confingPwdSignup_xpath");
		type("confingPwdSignup_xpath",configPwd);
		wait = new WebDriverWait(driver, 10);
		Thread.sleep(1000);
		click("signBtnSignup_xpath");

		Reporter.log("<b>Login Successfully Executed</b>");
		Reporter.log("<a href=\"\\C:\\Users\\Administrator\\eclipse-workspace\\DataDrivenFramework\\Screenshot\\error.jpg\"><a href=\\\"\\\\C:\\\\Users\\\\Administrator\\\\eclipse-workspace\\\\DataDrivenFramework\\\\Screenshot\\\\error.jpg\" height=200 width=200</a>\"");
		
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		Assert.assertTrue(alert.getText().contains("Already User Exsits"));
		 alert.accept();
		log.debug("Sign UP successfully executed");
		Assert.fail("Sign UP not successfully executed");
		wait = new WebDriverWait(driver, 10);
	}

	

}
