package Others;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import ObjectRepository.LoginPage;
import Utility.Configuration;
import lib.DriverAndObjectDetails;
import lib.ExcelDataConfig;
import lib.ExtentManager;
import lib.Takescreenshot;
import lib.DriverAndObjectDetails.DriverName;

/* #######  Test for Supplier Login  #########
######  User log's in as a supplier  ##### */

public class Supplier_Login {
	public WebDriver driverqa;
	ExtentTest test;
	ExcelDataConfig excel;
	String actualresult;
	String expectedtitle;
	String atualtitle;
	String expectedresult;
	Configuration Config = new Configuration();
	Takescreenshot obj = new Takescreenshot();
	ExtentReports rep = ExtentManager.getInstance();
	LoginPage login = new LoginPage();
	Logger logger = Logger.getLogger("Supplier_Login");

	/* ####### Passing browser as parameters in test ######### **/

	@Test
	@Parameters({ "browsername" })
	public void SupplierLogin(String browsername) throws Exception {
		test = rep.startTest("Supplier Login");
		excel = new ExcelDataConfig(Config.getExcelPathBook());
		PropertyConfigurator.configure("Log4j.properties");
		logger.info("Test Case Started");
		if (browsername.equalsIgnoreCase("CH")) {
			driverqa = new DriverAndObjectDetails(DriverName.CH).CreateDriver();
		} else if (browsername.equalsIgnoreCase("IE")) {
			driverqa = new DriverAndObjectDetails(DriverName.IE).CreateDriver();
		} else {
			driverqa = new DriverAndObjectDetails(DriverName.FF).CreateDriver();
		}
		WebDriverWait wait = new WebDriverWait(driverqa, 30);
		Actions action = new Actions(driverqa);

		/* ####### Login functionality ######### **/

		try {
			logger.info("Browser Opened");
			Actions action1 = new Actions(driverqa);

			// Opening URL

			String URL = excel.getData(0, 1, 3) + "/interface/en";
			driverqa.get(URL);
			logger.info("Test Case Started");
			test.log(LogStatus.INFO, "Starting Login");

			// Navigating to supplier login tab

			wait.until(ExpectedConditions.visibilityOfElementLocated(LoginPage.SupplierTab));
			driverqa.findElement(LoginPage.SupplierTab).click();

			// Entering login credentials

			wait.until(ExpectedConditions.visibilityOfElementLocated(LoginPage.SupplierUsername));
			WebElement username = driverqa.findElement(LoginPage.SupplierUsername);
			username.clear();
			username.sendKeys(excel.getData(0, 61, 1));
			driverqa.findElement(LoginPage.SupplierPassword).sendKeys(excel.getData(0, 61, 2));
			Thread.sleep(4000);
			obj.Takesnap(driverqa, Config.SnapShotPath() + "/Others/Supplier_Log_In/Log-In-Credentials.jpg");
			action.sendKeys(Keys.ENTER).build().perform();
			/* driverqa.findElement(LoginPage.Submit).click(); */
			Thread.sleep(2000);
			expectedtitle = "DOTWconnect.com::DOTWconnect.com: Extranet";
			atualtitle = driverqa.getTitle();
			Assert.assertEquals(atualtitle, expectedtitle);
			test.log(LogStatus.INFO, "Ending Login");
			test.log(LogStatus.PASS, "PASSED Login");
			logger.info("Login Successful");
			ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver driver) {
					return ((JavascriptExecutor) driverqa).executeScript("return document.readyState")
							.equals("complete");
				}
			};
			wait.until(pageLoadCondition);
			action.sendKeys(Keys.ESCAPE).build().perform();
			Thread.sleep(2000);
			obj.Takesnap(driverqa, Config.SnapShotPath() + "/Others/Supplier_Log_In/Log-In.jpg");

			// Catches throwable errors

		} catch (Throwable e) {

			obj.Takesnap(driverqa, Config.SnapShotPath() + "/Others/Error/Supplier_Log_In/Log-In.jpg");
			test.log(LogStatus.FAIL, "Login");
			logger.info(e.getMessage());
			test.log(LogStatus.FAIL, e.getMessage());
			rep.endTest(test);
			rep.flush();
			Assert.assertTrue(false, e.getMessage());

		}
	}

	/* ####### Generating the Failure Reports and Screenshots ######### **/

	@AfterMethod
	public void getResult(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {
			test.log(LogStatus.FAIL, result.getThrowable());
		}
		rep.endTest(test);
	}

	/* ####### Generating the Failure Reports and Screenshots ######### **/

	@AfterTest
	public void afterTest() {

		rep.endTest(test);
		rep.flush();
		driverqa.close();
	}
}
