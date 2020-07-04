package extrep1;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.google.common.io.Files;

public class ExtendReport_ITestListenerDemo implements ITestListener
{
	WebDriver wd;
	//====Extend Report reference==================
	ExtentHtmlReporter html;
	ExtentReports extent;
	ExtentTest test1 ;
	int i=0;
	
	 @BeforeTest
	  public void beforeTest() throws InterruptedException
	 {
		 
		 html = new ExtentHtmlReporter("/home/luser/Downloads/sDocs/testReport.html");
			//html.setAppendExisting(true);
			extent = new ExtentReports();
			extent.attachReporter(html);
			test1 = extent.createTest("php admin Example");
			test1.assignAuthor("Sanju");
			Thread.sleep(3000);
	        //===========================================================
			System.setProperty("webdriver.gecko.driver", "/home/luser/Desktop/New_Tools/Selenium/browser_extensions/geckodriver");
			wd= new FirefoxDriver();
			wd.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			test1.info("Opening url"); 
			wd.get("http://localhost/phpmyadmin/");
	  }
	 
	 @Test(dataProvider = "dp")
  public void f(String user, String password) throws InterruptedException 
  {
		 //try
		// {
		wd.findElement(By.name("pma_username")).clear();

		wd.findElement(By.name("pma_password")).clear();
		test1.info("Typing UserName");

		wd.findElement(By.name("pma_username")).sendKeys(user);
		test1.info("Typing UserName");

		wd.findElement(By.name("pma_password")).sendKeys(password);
		test1.info("Click on login button");

		wd.findElement(By.id("input_go")).click();
		Thread.sleep(3000);
      System.out.println(wd.getTitle());
      
      test1.info("verify title");
      Assert.assertEquals(wd.getTitle(), "localhost / localhost / dbuser | phpMyAdmin 4.6.6deb5");
     
      test1.info("Click on logout link");
      wd.findElement(By.cssSelector("img[title=\"Log out\"]")).click();
      
		 //}
		// catch(Exception e)
		// {
			// System.out.println(e.getMessage());
		// }
     
  }
  @DataProvider
	public Object[][] dp() 
	{
		return new Object[][] 
				{
			new Object[] { "dbuser", "dbuser" },
			new Object[] { "dbuser", "dbuser" },
			new Object[] { "dbuser", "dbuser" },
			new Object[] { "dbuser", "dbuser" },
			new Object[] { "dbuser", "dbuser" },
			new Object[] { "dbuser", "dbuser" },
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
					("/home/luser/Downloads/sDocs/fail/login"+(i+1)+".png"));
			test1.fail("Test Failed *******");
			 i++;
		}
		else if(result.getStatus() == ITestResult.SUCCESS) 
		{
			test1.log(Status.PASS, MarkupHelper.createLabel(result.getName()+" PASSED ", ExtentColor.GREEN));

			File srcfile=((TakesScreenshot)wd).
					getScreenshotAs(OutputType.FILE);
			Files.copy(srcfile, new File
					("/home/luser/Downloads/sDocs/pass/login"+(i+1)+".png"));

			test1.pass("Test Passed iiiiiii");
			 i++;
		}
		else 
		{
			test1.log(Status.SKIP, MarkupHelper.createLabel(result.getName()+" SKIPPED ", ExtentColor.ORANGE));
			test1.skip(result.getThrowable());
		}
	}

  
public void onTestStart(ITestResult result)
{
	System.out.println(result.getName()+"test started");
	
}

public void onTestSuccess(ITestResult result)
{
	System.out.println(result.getStatus() + " status");
	//System.out.println(result.getName() + " Test Failed");
//	if (result.getStatus() == ITestResult.SUCCESS) 
//	{
//		File src=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
//		try {
//			Files.copy(src, new File("/home/luser/Desktop/Student Work/AdvAut/SeleniumGrid/test-output/click.png"));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			//e.printStackTrace();
//		}
//	}
	
	 //if(result.getStatus() == ITestResult.SUCCESS) 
	//{
		 System.out.println("===========SUCCESS part====================");
		//test1.log(Status.PASS, MarkupHelper.createLabel(result.getName()+" PASSED ", ExtentColor.GREEN));

		File srcfile=((TakesScreenshot)wd).
				getScreenshotAs(OutputType.FILE);
		try {
			Files.copy(srcfile, new File("/home/luser/Downloads/sDocs/pass/login"+(i+1)+".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//test1.pass("Test Passed");
		i++;
	//}
	
}

public void onTestFailure(ITestResult result)
{
	System.out.println("===========FAILURE part====================");
	//if(result.getStatus() == ITestResult.FAILURE)
	//{
		//test1.log(Status.FAIL, MarkupHelper.createLabel(result.getName()+" FAILED ", ExtentColor.RED));
		

		File srcfile=((TakesScreenshot)wd).
				getScreenshotAs(OutputType.FILE);
		try {
			Files.copy(srcfile, new File("/home/luser/Downloads/sDocs/fail/login"+(i+1)+".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		i++;
		//test1.fail("Test Failed");
		//test1.fail(result.getThrowable());
	//}
	
}


public void onTestSkipped(ITestResult result)
{
	// TODO Auto-generated method stub
			System.out.println(result.getStatus() + " status");
			//System.out.println(result.getName() + " Test Failed");
			if (result.getStatus() == ITestResult.SKIP) 
			{
				
			}
	
}


public void onTestFailedButWithinSuccessPercentage(ITestResult result) 
{
	// TODO Auto-generated method stub
	
}


public void onStart(ITestContext context) 
{
	// TODO Auto-generated method stub
	
}


public void onFinish(ITestContext context)
{
	// TODO Auto-generated method stub
	
}

@AfterTest
public void afterTest()
{
	wd.quit();
}


}
