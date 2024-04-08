package driverFactory;
import commonFunctions.functionLibrary;
import utilities.ExcelFileUtil;

import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class DriverScript {
	String inputpath ="./FileInput/DataEngine.xlsx";
	String outputpath ="./FileOutput/HybridResults.xlsx";
	ExtentReports report;
	ExtentTest logger;
	String Sheet ="MasterTestCases";
	WebDriver driver;
	public void startTest() throws Throwable
	{
	String Modulestatus="";
	//create object for excelfileutil class
	ExcelFileUtil xl = new ExcelFileUtil(inputpath);
	//iterate all in sheet 
	for(int i=1;i<=xl.rowCount(Sheet);i++)
	{
		if(xl.getCellData(Sheet, i, 2).equalsIgnoreCase("Y"))
		{
			//store all corresponding sheet into TCModule
			String TCModule =xl.getCellData(Sheet, i, 1);
			report=new ExtentReports("./target/Reports/"+TCModule+functionLibrary.generateDate()+".html");
			logger =report.startTest(TCModule);
			logger.assignAuthor("Ranga");
			//iterate all rows in TCModule
			for(int j=1;j<=xl.rowCount(TCModule);j++)
			{
				//read each cell from TCModule
				String Description =xl.getCellData(TCModule, j, 0);
				String Object_Type = xl.getCellData(TCModule, j, 1);
				String Locator_Type =xl.getCellData(TCModule, j, 2);
				String Locator_Value = xl.getCellData(TCModule, j, 3);
				String Test_Data = xl.getCellData(TCModule, j, 4);
				try {
					if(Object_Type.equalsIgnoreCase("startBrowser"))
					{
						driver = functionLibrary.startBrowser();
						logger.log(LogStatus.INFO, Description);
					}
					if(Object_Type.equalsIgnoreCase("openUrl"))
					{
						functionLibrary.openUrl();
						logger.log(LogStatus.INFO, Description);
					}
					if(Object_Type.equalsIgnoreCase("waitForElement"))
					{
						functionLibrary.waitForElement(Locator_Type, Locator_Value, Test_Data);
						logger.log(LogStatus.INFO, Description);
					}
					if(Object_Type.equalsIgnoreCase("typeAction"))
					{
						functionLibrary.typeAction(Locator_Type, Locator_Value, Test_Data);
						logger.log(LogStatus.INFO, Description);
					}
					if(Object_Type.equalsIgnoreCase("clickAction"))
					{
						functionLibrary.clickAction(Locator_Type, Locator_Value);
						logger.log(LogStatus.INFO, Description);
					}
					if(Object_Type.equalsIgnoreCase("validateTitle"))
					{
						functionLibrary.validateTitle(Test_Data);
						logger.log(LogStatus.INFO, Description);
					}
					if(Object_Type.equalsIgnoreCase("closeBrowser"))
					{
						functionLibrary.closeBrowser();
						logger.log(LogStatus.INFO, Description);
					}
					//write a s pass into status cell in TCModule
					xl.setCellData(TCModule, j, 5, "Pass", outputpath);
					logger.log(LogStatus.PASS, Description);
					Modulestatus="True";
				}catch(Exception e)
				{
				System.out.println(e.getMessage()); 
				//write a s Fail into status cell in TCModule
				xl.setCellData(TCModule, j, 5, "Fail", outputpath);
				logger.log(LogStatus.FAIL, Description);
				Modulestatus="False";
				}
				if(Modulestatus.equalsIgnoreCase("True"))
				{
					//write as pass into Sheet status cell
					xl.setCellData(Sheet, i, 3, "Pass", outputpath);
				}
				if(Modulestatus.equalsIgnoreCase("False"))
				{
					//write as Fail into Sheet status cell
					xl.setCellData(Sheet, i, 3, "Fail", outputpath);
				}
				report.endTest(logger);
				report.flush();
			}
		}
		else
		{
			//write as blocked into status cell for Testcases flag to N
			xl.setCellData(Sheet, i, 3, "Blocked", outputpath);
		}
}
	}
}

