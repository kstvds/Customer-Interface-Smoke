package Tour;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
import ObjectRepository.Search;
import ObjectRepository.Tour;
import Utility.Configuration;
import lib.DriverAndObjectDetails;
import lib.ExcelDataConfig;
import lib.ExtentManager;
import lib.Takescreenshot;
import lib.DriverAndObjectDetails.DriverName;

/* #######  Test for Tour Searching #########
######  Scenario Logs In, Searches for a specified tour and then verifies the search ##### */

public class Search_Tour {
	public WebDriver driverqa;
	String errorpath;
	ExtentTest test;
	ExcelDataConfig excel;
	Configuration Config = new Configuration();
	Takescreenshot obj = new Takescreenshot();
	ExtentReports rep = ExtentManager.getInstance();

	LoginPage login = new LoginPage();
	Logger logger = Logger.getLogger("Search_Tour");

	/* ####### Passing browser as parameters in test ######### **/

	@Test
	@Parameters({ "browsername" })
	public void SearchTour(String browsername) throws Exception {
		test = rep.startTest("Search Tour");

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
			String URL = excel.getData(0, 1, 3) + "/interface/en";
			driverqa.get(URL);
			logger.info("Test Case Started");
			test.log(LogStatus.INFO, "Starting Login");
			WebElement username = driverqa.findElement(LoginPage.LoginId);
			username.clear();
			username.sendKeys(excel.getData(0, 57, 1));

			// WebElement password = driverqa.findElement(LoginPage.password);
			// password.clear();
			wait.until(ExpectedConditions.visibilityOfElementLocated(LoginPage.password));
			driverqa.findElement(LoginPage.password).sendKeys(excel.getData(0, 57, 2));
			Thread.sleep(1000);
			WebElement company = driverqa.findElement(LoginPage.Companycode);
			company.clear();
			company.sendKeys(excel.getData(0, 57, 3));
			driverqa.findElement(LoginPage.Submit).click();
			Thread.sleep(2000);
			String expectedtitle = "DOTWconnect.com";
			String atualtitle = driverqa.getTitle();
			Assert.assertEquals(atualtitle, expectedtitle);
			test.log(LogStatus.INFO, "Ending Login");
			test.log(LogStatus.PASS, "PASSED Login");
			logger.info("Login Successful");
			// Thread.sleep(7000);
			ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver driver) {
					return ((JavascriptExecutor) driverqa).executeScript("return document.readyState")
							.equals("complete");
				}
			};

			wait.until(pageLoadCondition);

			action.sendKeys(Keys.ESCAPE).build().perform();
			Thread.sleep(2000);
			obj.Takesnap(driverqa, Config.SnapShotPath() + "/Tour/Search_Tour/Log-In.jpg");

		} catch (Throwable e) {

			obj.Takesnap(driverqa, Config.SnapShotPath() + "/Tour/Error/Search_Tour/Log-In.jpg");
			test.log(LogStatus.FAIL, "Login");
			errorpath = Config.SnapShotPath() + "/Tour/Error/Search_Tour/Log-In.jpg";
			logger.info(e.getMessage());
			test.log(LogStatus.FAIL, e.getMessage());
			rep.endTest(test);
			rep.flush();
			Assert.assertTrue(false, e.getMessage());

		}
		/* ####### Applying filters and searching for Tour ######### **/

		try {
			logger.info("Applying search Filters");
			logger.info("Starting Tour Search");
			test.log(LogStatus.INFO, "Starting Tour Search");
			driverqa.findElement(Tour.tour).click();
			Thread.sleep(2000);
			wait.until(ExpectedConditions.visibilityOfElementLocated(Tour.tourname));
			wait.until(ExpectedConditions.visibilityOfElementLocated(Tour.TourdestID));
			driverqa.findElement(Tour.TourdestID).sendKeys(excel.getData(0, 51, 4));
			Thread.sleep(3000);
			action.sendKeys(Keys.ARROW_DOWN).build().perform();
			action.sendKeys(Keys.ENTER).build().perform();
			driverqa.findElement(Tour.tourname).sendKeys(excel.getData(0, 51, 1));
			Thread.sleep(4000);
			action.sendKeys(Keys.ARROW_DOWN).build().perform();
			action.sendKeys(Keys.ENTER).build().perform();

			test.log(LogStatus.INFO, "Selecting dates");
			driverqa.findElement(Tour.tourdate).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(Search.CalenderIN));
			driverqa.findElement(Search.nextmnth).click();
			driverqa.findElement(Search.nextmnth).click();
			List<WebElement> allDates = driverqa.findElements(Search.CalenderIN);

			for (WebElement ele : allDates) {

				String date = ele.getText();

				if (date.equalsIgnoreCase(excel.getData(0, 65, 1))) {
					ele.click();
					break;
				}

			}
			/*
			 * wait.until(ExpectedConditions.visibilityOfElementLocated(Search.
			 * CalenderIN));
			 * 
			 * List<WebElement> allDates2 =
			 * driverqa.findElements(Search.CalenderIN);
			 * 
			 * for (WebElement ele : allDates2) {
			 * 
			 * String date = ele.getText();
			 * 
			 * if (date.equalsIgnoreCase(excel.getData(0, 65, 2))) {
			 * ele.click(); break; }
			 * 
			 * }
			 */
			test.log(LogStatus.PASS, "Selection of Dates");
			/*
			 * WebElement Noofchilds = driverqa.findElement(Search.NoOfChilds);
			 * Noofchilds.clear(); Noofchilds.sendKeys("1");
			 */
			Thread.sleep(2000);
			obj.Takesnap(driverqa, Config.SnapShotPath() + "/Tour/Search_Tour/Filters.jpg");
			String expectedresult = excel.getData(0, 51, 1);
			System.out.println(expectedresult);
			driverqa.findElement(Tour.searchtourbutton).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(Search.HotelTitle));
			Thread.sleep(2000);
			obj.Takesnap(driverqa, Config.SnapShotPath() + "/Tour/Search_Tour/Search-Result.jpg");
			String actualresult = driverqa.findElement(Search.HotelTitle).getText();
			System.out.println(actualresult);
			Assert.assertTrue(actualresult.equalsIgnoreCase(expectedresult));
			test.log(LogStatus.INFO, "Ending Tour Search");
			test.log(LogStatus.PASS, "PASSED Tour Search");
			logger.info("Tour Search Complete");
		} catch (Throwable e) {
			test.log(LogStatus.FAIL, "Tour Search");
			obj.Takesnap(driverqa, Config.SnapShotPath() + "/Tour/Error/Search_Tour/Search-Result.jpg");
			errorpath = Config.SnapShotPath() + "/Tour/Error/Search_Tour/Search-Result.jpg";
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

			test.log(LogStatus.FAIL, test.addScreenCapture(errorpath));
			test.log(LogStatus.FAIL, result.getThrowable());
		}
		rep.endTest(test);
	}

	/* ####### Ending Tests ######### **/

	@AfterTest
	public void afterTest() {

		rep.endTest(test);
		rep.flush();
		driverqa.close();
	}
}
