package com.w2a.testcases;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.Test;

import com.w2a.base.TestBase;
import com.w2a.utilities.TestUtil;

public class DesignmateLoginTest extends TestBase {

	@Test(dataProviderClass=TestUtil.class,dataProvider="dp")
	public void designmateLogintest(String username,String password,String runmode) throws InterruptedException {

		if(!runmode.equals("Y")) {
			
			throw new SkipException("Skipped the test"+"DesignmateLoginTest".toUpperCase()+" As per rum mode of No.");
		}
		
		log.debug("Inside Login Test !!!");
		click("singUpAndLoginBtn_xpath");
		clean("username_xpath");
		type("username_xpath", username);
		clean("userpwd_xpath");
		type("userpwd_xpath",password);
		click("loginBtn_xpath");
		//Thread.sleep(5000);
		Assert.assertTrue(isElementPresent(By.xpath(or.getProperty("userloginDropdown_xpath"))),
				"Login Not Successfully Execution !!!");
		
		click("userloginDropdown_xpath");
		click("userlogoutLink_xpath");
		log.debug("Login successfully executed");
		
	}
}
