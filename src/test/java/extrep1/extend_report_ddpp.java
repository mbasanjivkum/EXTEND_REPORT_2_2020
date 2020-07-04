package extrep1;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.google.common.io.Files;

import org.testng.annotations.DataProvider;
import org.testng.annotations.BeforeTest;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class extend_report_ddpp 
{
	WebDriver wd;
	//====Extend Report reference==================
	ExtentHtmlReporter html;
	ExtentReports extent;
	ExtentTest test1 ;
	int i=1;


	@BeforeTest
	public void beforeTest() throws InterruptedException
	{
		//=====Extend report custumize======================

		html = new ExtentHtmlReporter("C:\\SEL_TEST\\Extent_Report.html");
		//html.setAppendExisting(true);
		extent = new ExtentReports();
		extent.attachReporter(html);
		test1 = extent.createTest("Oracle application login example ");
		test1.assignAuthor("Sanju");
		Thread.sleep(3000);

		//===========================================================
		System.setProperty("webdriver.gecko.driver", "C:\\Users\\dell\\Downloads\\chromedriver_win32_83.0.4103.39\\chromedriver.exe");
		wd= new FirefoxDriver();
		wd.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		test1.info("Opening url");

		wd.get("http://127.0.0.1:8081/apex/f?p=4550:11:1704015208386886::NO:::");
	}

	@Test(dataProvider = "dp")

	public void f(String user, String password) throws InterruptedException 
	{
		
		
        wd.findElement(By.id("P11_USERNAME")).clear();
		wd.findElement(By.id("P11_PASSWORD")).clear();
		test1.info("Typing UserName");
		wd.findElement(By.id("P11_USERNAME")).sendKeys(user);
		test1.info("Typing passwor");
		wd.findElement(By.id("P11_USERNAME")).sendKeys(password);
		test1.info("Click on login button");
		wd.findElement(By.id("LOGIN")).click();
		Thread.sleep(3000);
		System.out.println(wd.getTitle());
		test1.info("Verifying Login Title");
		Assert.assertEquals(wd.getTitle(), "oracle");
		wd.findElement(By.linkText("Logout")).click();
		//test1.pass("Test Passed ");
		i++;
	}

	@DataProvider
	public Object[][] dp() 
	{
		return new Object[][] 
				{
			new Object[] { "sys", "sys" },
			new Object[] { "#$^#$^", "SDG436" },
			new Object[] { "", "SDG436" },
			new Object[] { "sys", "sys" },
			new Object[] { "", "" },
			new Object[] { "$%46^", "%^756" },
				};
	}

	@AfterMethod
	public void getResult(ITestResult result) throws IOException 
	{
		if(result.getStatus() == ITestResult.FAILURE)
		{
			test1.log(Status.FAIL, MarkupHelper.createLabel(result.getName()+" FAILED ", ExtentColor.RED));
			test1.fail(result.getThrowable());

			File srcfile=((TakesScreenshot)wd).
					getScreenshotAs(OutputType.FILE);
			Files.copy(srcfile, new File
					("C:\\SEL_TEST\\fail\\login"+(i+1)+".png"));
			test1.fail("Test Failed *******");
		}
		else if(result.getStatus() == ITestResult.SUCCESS) 
		{
			test1.log(Status.PASS, MarkupHelper.createLabel(result.getName()+" PASSED ", ExtentColor.GREEN));

			File srcfile=((TakesScreenshot)wd).
					getScreenshotAs(OutputType.FILE);
			Files.copy(srcfile, new File
					("C:\\SEL_TEST\\pass\\ogin"+(i+1)+".png"));

			test1.pass("Test Passed iiiiiii");
		}
		else 
		{
			test1.log(Status.SKIP, MarkupHelper.createLabel(result.getName()+" SKIPPED ", ExtentColor.ORANGE));
			test1.skip(result.getThrowable());
		}
	}



	@AfterTest
	public void afterTest()
	{
		wd.quit();
		//test1.pass("Test completed ");

		extent.flush();
	}

}
