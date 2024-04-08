package commonFunctions;

import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

public class functionLibrary {
public static WebDriver driver;
public static Properties conpro ;
//Method for launching browser
public static WebDriver startBrowser() throws Throwable 
{
	conpro=new Properties();
	//load property file
conpro.load(new FileInputStream("./PropertyFiles\\Environment.properties"));
if(conpro.getProperty("Browser").equalsIgnoreCase("chrome"))
{
	driver=new ChromeDriver();
	driver.manage().window().maximize();
			
}
else if(conpro.getProperty("Browser").equalsIgnoreCase("Firefox"))
{
	driver=new FirefoxDriver();
}
else
{
	Reporter.log("Browser value is not matching",true);
}
return driver;	
}
//Method for Launching url

public static void openUrl()
{
	driver.get(conpro.getProperty("Url"));
}

public static void waitForElement(String LocatorType,String LocatorValue,String TestData)
{
	WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(Integer.parseInt(TestData)));
	if(LocatorType.equalsIgnoreCase("name"))
	{
		//wait untill element is visible
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(LocatorValue)));
	}
	if(LocatorType.equalsIgnoreCase("xpath"))
	{
		//wait untill element is visible
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LocatorValue)));
	}if(LocatorType.equalsIgnoreCase("id"))
	{
		//wait untill element is visible
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(LocatorValue)));
	}
	
}

//method for any Textboxes
public static void typeAction(String LocatorType,String LocatorValue,String TestData)
{
	if(LocatorType.equalsIgnoreCase("name"))
	{
		driver.findElement(By.name(LocatorValue)).clear();
		driver.findElement(By.name(LocatorValue)).sendKeys(TestData);
	}if(LocatorType.equalsIgnoreCase("id"))
	{
		driver.findElement(By.id(LocatorValue)).clear();
		driver.findElement(By.id(LocatorValue)).sendKeys(TestData);
     }
	if(LocatorType.equalsIgnoreCase("xpath"))
	{
		driver.findElement(By.xpath(LocatorValue)).clear();
		driver.findElement(By.xpath(LocatorValue)).sendKeys(TestData);
    }
}
////method for buttons,radiobuttons,checkboxes,links and images

public static void clickAction(String LocatorType,String LocatorValue)
{
	if(LocatorType.equalsIgnoreCase("xpath"))
	{
		driver.findElement(By.xpath(LocatorValue)).click();
		
	}
	if(LocatorType.equalsIgnoreCase("name"))
	{
		driver.findElement(By.name(LocatorValue)).click();
	}
	if(LocatorType.equalsIgnoreCase("id"))
	{
		driver.findElement(By.id(LocatorValue)).sendKeys(Keys.ENTER);
	}
}
//method for validate title
public static void validateTitle(String Expected_title)
{
	String Actual_title = driver.getTitle();
	try {
	Assert.assertEquals(Actual_title, Expected_title, "Title is Not Matching");
	}catch(AssertionError a)
	{
		System.out.println(a.getMessage());
	}
}
public static void closeBrowser()
{
	driver.quit();
}
//method for date generate
public static String generateDate()
{
	Date date = new Date();
	DateFormat df = new SimpleDateFormat("YYYY_MM_dd hh_mm");
	return df.format(date);
}


}

