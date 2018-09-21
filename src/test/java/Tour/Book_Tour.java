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
import org.openqa.selenium.support.ui.Select;
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

import ObjectRepository.Booking;
import ObjectRepository.LoginPage;
import ObjectRepository.PaymentPage;
import ObjectRepository.Search;
import ObjectRepository.Tour;
import Utility.Configuration;
import lib.DriverAndObjectDetails;
import lib.ExcelDataConfig;
import lib.ExtentManager;
import lib.Takescreenshot;
import lib.DriverAndObjectDetails.DriverName;

/* #######  Test for Tour booking #########
######  Scenario Logs In, Books a specified Tour ##### */

public class Book_Tour {
	public WebDriver driverqa;
	ExtentTest test;
	String errorpath;
	String Roomtype;
	ExcelDataConfig excel;
	String ExpectedTourDate;
	String ActualTourDate;
	String ExpectedTourPickUp;
	String ActualTourPickUp;
	Configuration Config = new Configuration();
	Takescreenshot obj = new Takescreenshot();
	ExtentReports rep = ExtentManager.getInstance();
	LoginPage login = new LoginPage();
	Logger logger = Logger.getLogger("Book_Tour");

	/* ####### Passing browser as parameters in test ######### **/

	@Test
	@Parameters({ "browsername" })
	public void BookTour(String browsername) throws Exception {
		test = rep.startTest("Book Tour");
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
		WebDriverWait wait = new WebDriverWait(driverqa, 80);
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
			username.sendKeys(excel.getData(0, 58, 1));
			wait.until(ExpectedConditions.visibilityOfElementLocated(LoginPage.password));
			driverqa.findElement(LoginPage.password).sendKeys(excel.getData(0, 58, 2));
			Thread.sleep(1000);
			WebElement company = driverqa.findElement(LoginPage.Companycode);
			company.clear();
			company.sendKeys(excel.getData(0, 58, 3));
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
			// WebDriverWait waiting = new WebDriverWait(driverqa, 30);
			wait.until(pageLoadCondition);
			action.sendKeys(Keys.ESCAPE).build().perform();
			Thread.sleep(2000);
			obj.Takesnap(driverqa, Config.SnapShotPath() + "/Tour/Book_Tour/Log-In.jpg");

		} catch (Throwable e) {

			obj.Takesnap(driverqa, Config.SnapShotPath() + "/Tour/Error/Book_Tour/Log-In.jpg");
			test.log(LogStatus.FAIL, "Login");
			errorpath = Config.SnapShotPath() + "/Tour/Error/Book_Tour/Log-In.jpg";
			logger.info(e.getMessage());
			test.log(LogStatus.FAIL, e.getMessage());
			rep.endTest(test);
			rep.flush();
			Assert.assertTrue(false, e.getMessage());

		}

		/* ####### Applying filters and searching for filters ######### **/

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
			obj.Takesnap(driverqa, Config.SnapShotPath() + "/Tour/Book_Tour/Filters.jpg");
			String expectedresult = excel.getData(0, 51, 1);
			System.out.println(expectedresult);
			driverqa.findElement(Tour.searchtourbutton).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(Search.HotelTitle));
			Thread.sleep(2000);
			obj.Takesnap(driverqa, Config.SnapShotPath() + "/Tour/Book_Tour/Search-Result.jpg");
			String actualresult = driverqa.findElement(Search.HotelTitle).getText();
			System.out.println(actualresult);
			Assert.assertTrue(actualresult.equalsIgnoreCase(expectedresult));
			test.log(LogStatus.INFO, "Ending Tour Search");
			test.log(LogStatus.PASS, "PASSED Tour Search");
			logger.info("Tour Search Complete");
		} catch (Throwable e) {
			test.log(LogStatus.FAIL, "Tour Search");

			obj.Takesnap(driverqa, Config.SnapShotPath() + "/Tour/Error/Book_Tour/Search-Result.jpg");
			errorpath = Config.SnapShotPath() + "/Tour/Error/Book_Tour/Search-Result.jpg";
			logger.info(e.getMessage());
			test.log(LogStatus.FAIL, e.getMessage());
			rep.endTest(test);
			rep.flush();
			Assert.assertTrue(false, e.getMessage());
		}

		/* ####### Booking Tour for the specified date ######### **/

		try {
			test.log(LogStatus.INFO, "Starting Tour Book");
			logger.info("Starting Tour Book");
			wait.until(ExpectedConditions.visibilityOfElementLocated(Tour.Gettour));
			test.log(LogStatus.INFO, "Selecting Tour");
			logger.info("Selecting Tour");
			driverqa.findElement(Tour.Gettour).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(Tour.SelectTour));
			Thread.sleep(2000);
			obj.Takesnap(driverqa, Config.SnapShotPath() + "/Tour/Book_Tour/Avaailable_Tour_List.jpg");
			driverqa.findElement(Tour.SelectTour).click();
			test.log(LogStatus.INFO, "Tour Selected");
			logger.info("Tour Selected");
			logger.info("Entering Passenger details");
			test.log(LogStatus.INFO, "Entering Passenger details");
			wait.until(ExpectedConditions.visibilityOfElementLocated(Tour.BookTourFirstName));
			driverqa.findElement(Tour.BookTourFirstName).sendKeys(excel.getData(0, 21, 1));
			Thread.sleep(2000);
			driverqa.findElement(Tour.BookTourLastName).sendKeys(excel.getData(0, 21, 2));
			Select passengertitle = new Select(driverqa.findElement(Tour.BookTourpassengerTitle));
			passengertitle.selectByIndex(1);
			driverqa.findElement(Tour.Pickuplocation).sendKeys("Airport");
			driverqa.findElement(Booking.PrcdToBookChckBox).click();
			Thread.sleep(2000);
			obj.Takesnap(driverqa, Config.SnapShotPath() + "/Tour/Book_Tour/Passenger-Details.jpg");
			logger.info("Entered Passenger details");
			test.log(LogStatus.INFO, "Entered Passenger details");
			test.log(LogStatus.PASS, "Passenger details");
			driverqa.findElement(Tour.ContinueTourBook).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(Booking.ProccedToBook));
			Thread.sleep(2000);
			obj.Takesnap(driverqa, Config.SnapShotPath() + "/Tour/Book_Tour/Confirm-Tour-Booking.jpg");
			driverqa.findElement(Booking.ProccedToBook).click();
			logger.info("Entering Payment details");
			test.log(LogStatus.INFO, "Entering Payment details");
			wait.until(ExpectedConditions.visibilityOfElementLocated(PaymentPage.FirstName));
			WebElement FirstName = driverqa.findElement(PaymentPage.FirstName);
			FirstName.clear();
			FirstName.sendKeys(excel.getData(0, 22, 1));
			WebElement LastName = driverqa.findElement(PaymentPage.LastName);
			LastName.clear();
			LastName.sendKeys(excel.getData(0, 22, 2));
			WebElement Address = driverqa.findElement(PaymentPage.Address);
			Address.clear();
			Address.sendKeys("Kolkata1234");
			WebElement CardNo = driverqa.findElement(PaymentPage.CardNumber);
			CardNo.clear();
			CardNo.sendKeys(excel.getData(0, 21, 5));
			WebElement CVVNo = driverqa.findElement(PaymentPage.CVVNumber);
			CVVNo.clear();
			CVVNo.sendKeys(excel.getData(0, 22, 5));
			driverqa.findElement(PaymentPage.AcceptTerms).click();
			logger.info("Entered Payment details");
			test.log(LogStatus.INFO, "Entered Payment details");
			test.log(LogStatus.PASS, "Payment details");
			Thread.sleep(2000);
			obj.Takesnap(driverqa, Config.SnapShotPath() + "/Tour/Book_Tour/Payment-Details.jpg");
			driverqa.findElement(PaymentPage.Acceptpayment).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(Booking.ViewBooking));
			JavascriptExecutor js1 = (JavascriptExecutor) driverqa;
			Thread.sleep(2000);
			obj.Takesnap(driverqa, Config.SnapShotPath() + "/Tour/Book_Tour/Search-Booking-Page.jpg");
			// WebElement Element = driverqa.findElement(Booking.Invoice);
			// This will scroll the page till the element is found
			/*
			 * Assert.assertTrue(ActualStatus.equalsIgnoreCase(ExpectedStatus));
			 * Assert.assertTrue(ActualNoOfAdults.equalsIgnoreCase(
			 * ExpectedNoOfAdults));
			 */
			driverqa.findElement(Booking.ViewBooking).click();
			Thread.sleep(2000);
			obj.Takesnap(driverqa, Config.SnapShotPath() + "/Tour/Book_Tour/Booking-Details1.jpg");
			// This will scroll the page till the element is found
			js1.executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(2000);
			obj.Takesnap(driverqa, Config.SnapShotPath() + "/Tour/Book_Tour/Booking-Details2.jpg");
			wait.until(ExpectedConditions.visibilityOfElementLocated(Booking.BookingStatusPrepay));
			String ExpectedStatus = "CONFIRMED";
			String ExpectedTourName = excel.getData(0, 51, 1);
			ExpectedTourDate = excel.getData(0, 65, 1);
			ActualTourDate = driverqa.findElement(Tour.AfterBookingTourDate).getText();
			ExpectedTourPickUp = "Airport";
			ActualTourPickUp = driverqa.findElement(Tour.AfterBookingTourPickUp).getText();
			String ActualTourName = driverqa.findElement(Tour.AfterBookingTourName).getText();
			String ActualStatus = driverqa.findElement(Booking.BookingStatusPrepay).getText();
			System.out.println(ExpectedStatus);
			System.out.println(ExpectedTourName);
			System.out.println(ExpectedTourDate);
			System.out.println(ActualTourDate);
			System.out.println(ExpectedTourPickUp);
			System.out.println(ActualTourPickUp);
			System.out.println(ActualTourName);
			System.out.println(ActualStatus);
			Assert.assertTrue(ActualStatus.equalsIgnoreCase(ExpectedStatus));
			Assert.assertTrue(ActualTourName.equalsIgnoreCase(ExpectedTourName));
			Assert.assertTrue(ActualTourPickUp.equalsIgnoreCase(ExpectedTourPickUp));
			Assert.assertTrue(ActualTourDate.contains(ExpectedTourDate));
			test.log(LogStatus.INFO, "Ending Tour Book");
			test.log(LogStatus.PASS, "Tour Book");
			logger.info("Tour Booked");

		} catch (Throwable e) {
			test.log(LogStatus.FAIL, "Tour Book");
			obj.Takesnap(driverqa, Config.SnapShotPath() + "/Tour/Error/Book_Tour/Booking.jpg");
			errorpath = Config.SnapShotPath() + "/Tour/Error/Book_Tour/Booking.jpg";
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
		//driverqa.close();
	}
}
