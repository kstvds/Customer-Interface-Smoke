package Transfer;

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
import ObjectRepository.Transfer;
import Utility.Configuration;
import lib.DriverAndObjectDetails;
import lib.ExcelDataConfig;
import lib.ExtentManager;
import lib.Takescreenshot;
import lib.DriverAndObjectDetails.DriverName;

/* #######  Test for Scheduled Transfer booking #########
######  Scenario Logs In, Books a specified Scheduled Transfer  ##### */

public class Transfer_Booking_Scheduled {
	public WebDriver driverqa;
	ExtentTest test;
	String errorpath;
	String Roomtype;
	ExcelDataConfig excel;
	String ExpectedTransferDate;
	String ActualTransferDate;
	String ExpectedTransferPickUp;
	String ActualTransferPickUp;
	Configuration Config = new Configuration();
	Takescreenshot obj = new Takescreenshot();
	ExtentReports rep = ExtentManager.getInstance();
	LoginPage login = new LoginPage();
	Logger logger = Logger.getLogger("Transfer_Booking_Scheduled");

	/* ####### Passing browser as parameters in test ######### **/

	@Test
	@Parameters({ "browsername" })
	public void TransferBookingScheduled(String browsername) throws Exception {
		test = rep.startTest("Transfer Booking Scheduled");
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
			username.sendKeys(excel.getData(0, 57, 1));
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
			// WebDriverWait waiting = new WebDriverWait(driverqa, 30);
			wait.until(pageLoadCondition);
			action.sendKeys(Keys.ESCAPE).build().perform();
			Thread.sleep(2000);
			obj.Takesnap(driverqa, Config.SnapShotPath() + "/Transfer/Transfer_Booking_Scheduled/Log-In.jpg");

		} catch (Throwable e) {

			obj.Takesnap(driverqa, Config.SnapShotPath() + "/Transfer/Error/Transfer_Booking_Scheduled/Log-In.jpg");
			test.log(LogStatus.FAIL, "Login");
			errorpath = Config.SnapShotPath() + "/Transfer/Error/Transfer_Booking_Scheduled/Log-In.jpg";
			logger.info(e.getMessage());
			test.log(LogStatus.FAIL, e.getMessage());
			rep.endTest(test);
			rep.flush();
			Assert.assertTrue(false, e.getMessage());

		}

		/* ####### Applying filters and searching for filters ######### **/

		try {
			logger.info("Applying search Filters");
			logger.info("Starting Transfer Search");
			test.log(LogStatus.INFO, "Starting Transfer Search");
			driverqa.findElement(Transfer.transfer).click();
			Thread.sleep(2000);
			wait.until(ExpectedConditions.visibilityOfElementLocated(Transfer.transfername));
			wait.until(ExpectedConditions.visibilityOfElementLocated(Tour.TourdestID));
			driverqa.findElement(Tour.TourdestID).sendKeys(excel.getData(0, 32, 3));
			Thread.sleep(3000);
			action.sendKeys(Keys.ARROW_DOWN).build().perform();
			action.sendKeys(Keys.ENTER).build().perform();
			driverqa.findElement(Transfer.transfername).sendKeys(excel.getData(0, 32, 1));
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
			obj.Takesnap(driverqa, Config.SnapShotPath() + "/Transfer/Transfer_Booking_Scheduled/Filters.jpg");
			String expectedresult = excel.getData(0, 32, 1);
			System.out.println(expectedresult);
			driverqa.findElement(Tour.searchtourbutton).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(Search.HotelTitle));
			Thread.sleep(2000);
			obj.Takesnap(driverqa, Config.SnapShotPath() + "/Transfer/Transfer_Booking_Scheduled/Search-Result.jpg");
			String actualresult = driverqa.findElement(Search.HotelTitle).getText();
			System.out.println(actualresult);
			Assert.assertTrue(actualresult.contains(expectedresult));
			test.log(LogStatus.INFO, "Ending Transfer Search");
			test.log(LogStatus.PASS, "PASSED Transfer Search");
			logger.info("Transfer Search Complete");
		} catch (Throwable e) {
			test.log(LogStatus.FAIL, "Transfer Search");

			obj.Takesnap(driverqa,
					Config.SnapShotPath() + "/Transfer/Error/Transfer_Booking_Scheduled/Search-Result.jpg");
			errorpath = Config.SnapShotPath() + "/Transfer/Error/Transfer_Booking_Scheduled/Search-Result.jpg";
			logger.info(e.getMessage());
			test.log(LogStatus.FAIL, e.getMessage());
			rep.endTest(test);
			rep.flush();
			Assert.assertTrue(false, e.getMessage());
		}

		/* ####### Booking Tour for the specified date ######### **/

		try {
			test.log(LogStatus.INFO, "Starting Transfer Book");
			logger.info("Starting Transfer Book");
			wait.until(ExpectedConditions.visibilityOfElementLocated(Tour.Gettour));
			test.log(LogStatus.INFO, "Selecting Transfer");
			logger.info("Selecting Transfer");
			driverqa.findElement(Tour.Gettour).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(Tour.SelectTour));
			Thread.sleep(2000);
			obj.Takesnap(driverqa,
					Config.SnapShotPath() + "/Transfer/Transfer_Booking_Scheduled/Available_Transfer_List.jpg");
			driverqa.findElement(Tour.SelectTour).click();
			test.log(LogStatus.INFO, "Transfer Selected");
			logger.info("Transfer Selected");
			logger.info("Entering Passenger details");
			test.log(LogStatus.INFO, "Entering Passenger details");
			wait.until(ExpectedConditions.visibilityOfElementLocated(Transfer.TransferFirstName));
			driverqa.findElement(Transfer.TransferFirstName).sendKeys(excel.getData(0, 21, 1));
			Thread.sleep(2000);
			driverqa.findElement(Transfer.TransferLastName).sendKeys(excel.getData(0, 21, 2));
			Select passengertitle = new Select(driverqa.findElement(Transfer.TransferPassemgerTitle));
			passengertitle.selectByIndex(1);
			Select dropdown_Airline_Details = new Select(driverqa.findElement(Transfer.ArrivingArlineDetails));
			dropdown_Airline_Details.selectByValue("5133");
			driverqa.findElement(Transfer.ArrivingFrom).sendKeys("Dubai");
			Thread.sleep(3000);
			action.sendKeys(Keys.ARROW_DOWN).build().perform();
			action.sendKeys(Keys.ENTER).build().perform();
			driverqa.findElement(Transfer.FlightNo).sendKeys("007");
			driverqa.findElement(Transfer.pickUp).sendKeys("Airport");
			driverqa.findElement(Transfer.DropOff).sendKeys("Hotel");
			Thread.sleep(2000);
			obj.Takesnap(driverqa,
					Config.SnapShotPath() + "/Transfer/Transfer_Booking_Scheduled/Passenger-Details.jpg");
			driverqa.findElement(Transfer.TransferPrcdToBookChckBox).click();
			logger.info("Entered Passenger details");
			test.log(LogStatus.INFO, "Entered Passenger details");
			test.log(LogStatus.PASS, "Passenger details");
			driverqa.findElement(Tour.ContinueTourBook).click();
			/*
			 * wait.until(ExpectedConditions.visibilityOfElementLocated(Booking.
			 * ProccedToBook));
			 */
			/*
			 * Thread.sleep(2000); obj.Takesnap(driverqa, Config.SnapShotPath()
			 * +
			 * "/Transfer/Transfer_Booking_Private/Confirm-Transfer-Booking.jpg"
			 * ); driverqa.findElement(Booking.ProccedToBook).click();
			 * logger.info("Entering Payment details"); test.log(LogStatus.INFO,
			 * "Entering Payment details");
			 * wait.until(ExpectedConditions.visibilityOfElementLocated(
			 * PaymentPage.FirstName));
			 * 
			 * WebElement FirstName =
			 * driverqa.findElement(PaymentPage.FirstName); FirstName.clear();
			 * FirstName.sendKeys(excel.getData(0, 21, 1));
			 * 
			 * WebElement LastName = driverqa.findElement(PaymentPage.LastName);
			 * LastName.clear(); LastName.sendKeys(excel.getData(0, 22, 2));
			 * WebElement Address = driverqa.findElement(PaymentPage.Address);
			 * Address.clear(); Address.sendKeys("Kolkata1234"); WebElement
			 * CardNo = driverqa.findElement(PaymentPage.CardNumber);
			 * CardNo.clear(); CardNo.sendKeys(excel.getData(0, 21, 5));
			 * WebElement CVVNo = driverqa.findElement(PaymentPage.CVVNumber);
			 * CVVNo.clear(); CVVNo.sendKeys(excel.getData(0, 22, 5));
			 * driverqa.findElement(PaymentPage.AcceptTerms).click();
			 * logger.info("Entered Payment details"); test.log(LogStatus.INFO,
			 * "Entered Payment details"); test.log(LogStatus.PASS,
			 * "Payment details"); Thread.sleep(2000); obj.Takesnap(driverqa,
			 * Config.SnapShotPath() +
			 * "/Transfer/Transfer_Booking_Private/Payment-Details.jpg");
			 * driverqa.findElement(PaymentPage.Acceptpayment).click();
			 */
			wait.until(ExpectedConditions.visibilityOfElementLocated(Booking.BookingStatusPrepay));
			JavascriptExecutor js1 = (JavascriptExecutor) driverqa;
			/*
			 * Thread.sleep(2000); obj.Takesnap(driverqa, Config.SnapShotPath()
			 * + "/Transfer/Transfer_Booking_Private/Search-Booking-Page.jpg");
			 */
			// WebElement Element = driverqa.findElement(Booking.Invoice);
			// This will scroll the page till the element is found
			/*
			 * Assert.assertTrue(ActualStatus.equalsIgnoreCase(ExpectedStatus));
			 * Assert.assertTrue(ActualNoOfAdults.equalsIgnoreCase(
			 * ExpectedNoOfAdults));
			 */
			/* driverqa.findElement(Booking.ViewBooking).click(); */
			Thread.sleep(2000);
			obj.Takesnap(driverqa, Config.SnapShotPath() + "/Transfer/Transfer_Booking_Scheduled/Booking-Details1.jpg");
			// This will scroll the page till the element is found
			js1.executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(2000);
			obj.Takesnap(driverqa, Config.SnapShotPath() + "/Transfer/Transfer_Booking_Scheduled/Booking-Details2.jpg");
			wait.until(ExpectedConditions.visibilityOfElementLocated(Booking.BookingStatusPrepay));
			String ExpectedStatus = "Confirmed";
			String ExpectedTransferName = excel.getData(0, 32, 1);
			ExpectedTransferDate = excel.getData(0, 65, 1);
			ActualTransferDate = driverqa.findElement(Transfer.AfterBookingTransferDate).getText();
			ExpectedTransferPickUp = "Airport";
			ActualTransferPickUp = driverqa.findElement(Transfer.AfterBookingTransferPickUp).getText();
			String ActualTransferName = driverqa.findElement(Transfer.AfterBookingTransferName).getText();
			String ActualStatus = driverqa.findElement(Booking.BookingStatusPrepay).getText();
			System.out.println(ActualTransferName);
			System.out.println(ExpectedTransferName);
			Assert.assertTrue(ActualStatus.equalsIgnoreCase(ExpectedStatus));
			Assert.assertTrue(ActualTransferName.contains(ExpectedTransferName));
			Assert.assertTrue(ActualTransferPickUp.equalsIgnoreCase(ExpectedTransferPickUp));
			Assert.assertTrue(ActualTransferDate.contains(ExpectedTransferDate));
			test.log(LogStatus.INFO, "Ending Transfer Book");
			test.log(LogStatus.PASS, "Transfer Book");
			logger.info("Transfer Booked");

		} catch (Throwable e) {
			test.log(LogStatus.FAIL, "Transfer Book");
			obj.Takesnap(driverqa, Config.SnapShotPath() + "/Transfer/Error/Transfer_Booking_Scheduled/Booking.jpg");
			errorpath = Config.SnapShotPath() + "/Transfer/Error/Transfer_Booking_Scheduled/Booking.jpg";
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
