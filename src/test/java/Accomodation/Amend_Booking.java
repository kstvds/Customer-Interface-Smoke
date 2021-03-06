package Accomodation;

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

import ObjectRepository.Amend;
import ObjectRepository.Booking;
import ObjectRepository.LoginPage;
import ObjectRepository.PaymentPage;
import ObjectRepository.Search;
import Utility.Configuration;
import lib.DriverAndObjectDetails;
import lib.ExcelDataConfig;
import lib.ExtentManager;
import lib.Takescreenshot;
import lib.DriverAndObjectDetails.DriverName;

/* #######  Test for accommodation booking and amend in Customer Interface #########
######  Scenario Logs In, Books a specified hotel and amends the booking  ##### */

public class Amend_Booking {
	public WebDriver driverqa;
	ExtentTest test;
	String errorpath;
	String Roomtype;
	ExcelDataConfig excel;
	String ActualAmendDateChkIn;
	String ExpectedAmendDateChkIn;
	String ActualAmendDateChkOut;
	String ExpectedAmendDateChkOut;
	String ActualConfirmedAmendTitle;
	String ExpectedConfirmedAmendTitle;
	String ActualAfterAmendDateChkIn;
	String ExpectedAfterAmendDateChkIn;
	String ActualAfterAmendDateChkOut;
	String ExpectedAfterAmendDateChkOut;
	String ExpectedNoOfAdults;
	String ActualNoOfAdults;
	Configuration Config = new Configuration();
	Takescreenshot obj = new Takescreenshot();
	ExtentReports rep = ExtentManager.getInstance();
	LoginPage login = new LoginPage();
	Logger logger = Logger.getLogger("Amend_Booking");

	/* ####### Passing browser as parameters in test ######### **/

	@Test
	@Parameters({ "browsername" })
	public void AmendBooking(String browsername) throws Exception {
		test = rep.startTest("Amend_Booking");
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
			wait.until(pageLoadCondition);
			action.sendKeys(Keys.ESCAPE).build().perform();
			Thread.sleep(2000);
			obj.Takesnap(driverqa, Config.SnapShotPath() + "/Accomodation/Accommodation_Amend/Log-In.jpg");

		} catch (Throwable e) {

			obj.Takesnap(driverqa, Config.SnapShotPath() + "/Accomodation/Error/Accommodation_Amend/Log-In.jpg");
			test.log(LogStatus.FAIL, "Login");
			errorpath = Config.SnapShotPath() + "/Accomodation/Error/Accommodation_Amend/Log-In.jpg";
			logger.info(e.getMessage());
			test.log(LogStatus.FAIL, e.getMessage());
			rep.endTest(test);
			rep.flush();
			Assert.assertTrue(false, e.getMessage());

		}

		/* ####### Applying filters and searching for filters ######### **/

		try {
			logger.info("Applying search Filters");
			logger.info("Starting HotelSearch");
			test.log(LogStatus.INFO, "Starting HotelSearch");
			Thread.sleep(2000);
			wait.until(ExpectedConditions.visibilityOfElementLocated(Search.dest));
			driverqa.findElement(Search.dest).sendKeys(excel.getData(0, 12, 1));
			Thread.sleep(6000);
			action.sendKeys(Keys.ARROW_DOWN).build().perform();
			// action.sendKeys(Keys.ARROW_DOWN).build().perform();
			action.sendKeys(Keys.ENTER).build().perform();
			test.log(LogStatus.INFO, "Selecting dates");
			driverqa.findElement(Search.InDate).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(Search.CalenderIN));
			driverqa.findElement(Search.nextmnth).click();
			//driverqa.findElement(Search.nextmnth).click();
			List<WebElement> allDates = driverqa.findElements(Search.CalenderIN);

			for (WebElement ele : allDates) {

				String date = ele.getText();

				if (date.equalsIgnoreCase(excel.getData(0, 65, 1))) {
					ele.click();
					break;
				}

			}
			wait.until(ExpectedConditions.visibilityOfElementLocated(Search.CalenderIN));
			// driverqa.findElement(Search.nextmnth).click();
			// driverqa.findElement(Search.nextmnth).click();
			List<WebElement> allDates2 = driverqa.findElements(Search.CalenderIN);

			for (WebElement ele : allDates2) {

				String date = ele.getText();

				if (date.equalsIgnoreCase(excel.getData(0, 65, 2))) {
					ele.click();
					break;
				}

			}
			test.log(LogStatus.PASS, "Selection of Dates");
			WebElement Noofchilds = driverqa.findElement(Search.NoOfChilds);
			Noofchilds.clear();
			Noofchilds.sendKeys("1");
			Thread.sleep(2000);
			obj.Takesnap(driverqa, Config.SnapShotPath() + "/Accomodation/Accommodation_Amend/Filters.jpg");
			String expectedresult = excel.getData(0, 12, 1);
			driverqa.findElement(Search.SearchBtn).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(Search.HotelTitle));
			Thread.sleep(2000);
			obj.Takesnap(driverqa, Config.SnapShotPath() + "/Accomodation/Accommodation_Amend/Search-Result.jpg");
			String actualresult = driverqa.findElement(Search.HotelTitle).getText();
			Assert.assertTrue(actualresult.equalsIgnoreCase(expectedresult));
			test.log(LogStatus.INFO, "Ending HotelSearch");
			test.log(LogStatus.PASS, "PASSED HotelSearch");
			logger.info("Hotel Search Complete");
		} catch (Throwable e) {
			test.log(LogStatus.FAIL, "Hotel Search ");
			obj.Takesnap(driverqa, Config.SnapShotPath() + "/Accomodation/Error/Accommodation_Amend/Search-Result.jpg");
			errorpath = Config.SnapShotPath() + "/Accomodation/Error/Accommodation_Amend/Search-Result.jpg";
			logger.info(e.getMessage());
			test.log(LogStatus.FAIL, e.getMessage());
			rep.endTest(test);
			rep.flush();
			Assert.assertTrue(false, e.getMessage());
		}

		/* ####### Booking Hotel for the specified date ######### **/

		try {
			test.log(LogStatus.INFO, "Starting Hotel Book");
			logger.info("Starting Hotel Book");
			wait.until(ExpectedConditions.visibilityOfElementLocated(Booking.Bookroom));
			test.log(LogStatus.INFO, "Selecting Room");
			logger.info("Selecting Room");
			driverqa.findElement(Booking.Bookroom).click();
			test.log(LogStatus.INFO, "Room Selected");
			logger.info("Room Selected");
			logger.info("Entering Passenger details");
			test.log(LogStatus.INFO, "Entering Passenger details");
			wait.until(ExpectedConditions.visibilityOfElementLocated(Booking.OnePaxFirstName));
			driverqa.findElement(Booking.OnePaxFirstName).sendKeys(excel.getData(0, 20, 1));
			Thread.sleep(2000);
			driverqa.findElement(Booking.OnePaxlastName).sendKeys(excel.getData(0, 20, 2));
			Select passengertitle = new Select(driverqa.findElement(Booking.OnePaxTitle));
			passengertitle.selectByIndex(1);
			/*
			 * if (!driverqa.findElements(Booking.TwoPaxFirstName).isEmpty()) {
			 * driverqa.findElement(Booking.TwoPaxFirstName).sendKeys(excel.
			 * getData(0, 22, 1)); Thread.sleep(2000);
			 * driverqa.findElement(Booking.TwoPaxLastName).sendKeys(excel.
			 * getData(0, 22, 2)); Select passengertitle2 = new
			 * Select(driverqa.findElement(Booking.TwoPaxTitle));
			 * passengertitle2.selectByIndex(1); }
			 */
			driverqa.findElement(Booking.PrcdToBookChckBox).click();
			Thread.sleep(2000);
			obj.Takesnap(driverqa, Config.SnapShotPath() + "/Accomodation/Accommodation_Amend/Passenger-Details.jpg");
			logger.info("Entered Passenger details");
			test.log(LogStatus.INFO, "Entered Passenger details");
			test.log(LogStatus.PASS, "Passenger details");
			driverqa.findElement(Booking.ConfirmBook).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(Booking.ProccedToBook));
			Thread.sleep(2000);
			obj.Takesnap(driverqa, Config.SnapShotPath() + "/Accomodation/Accommodation_Amend/Confirm-Booking.jpg");
			driverqa.findElement(Booking.ProccedToBook).click();
			logger.info("Entering Payment details");
			test.log(LogStatus.INFO, "Entering Payment details");
			wait.until(ExpectedConditions.visibilityOfElementLocated(PaymentPage.FirstName));
			WebElement FirstName = driverqa.findElement(PaymentPage.FirstName);
			FirstName.clear();
			FirstName.sendKeys(excel.getData(0, 21, 1));
			WebElement LastName = driverqa.findElement(PaymentPage.LastName);
			LastName.clear();
			LastName.sendKeys(excel.getData(0, 21, 2));
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
			obj.Takesnap(driverqa, Config.SnapShotPath() + "/Accomodation/Accommodation_Amend/Payment-Details.jpg");
			driverqa.findElement(PaymentPage.Acceptpayment).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(Booking.ViewBooking));
			JavascriptExecutor js = (JavascriptExecutor) driverqa;
			Thread.sleep(2000);
			obj.Takesnap(driverqa, Config.SnapShotPath() + "/Accomodation/Accommodation_Amend/Search-Booking-Page.jpg");
			// WebElement Element = driverqa.findElement(Booking.Invoice);
			// This will scroll the page till the element is found
			/*
			 * Assert.assertTrue(ActualStatus.equalsIgnoreCase(ExpectedStatus));
			 * Assert.assertTrue(ActualNoOfAdults.equalsIgnoreCase(
			 * ExpectedNoOfAdults));
			 */
			driverqa.findElement(Booking.ViewBooking).click();
			Thread.sleep(2000);
			obj.Takesnap(driverqa, Config.SnapShotPath() + "/Accomodation/Accommodation_Amend/Booking-Details1.jpg");

			// This will scroll the page till the element is found

			js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(2000);
			obj.Takesnap(driverqa, Config.SnapShotPath() + "/Accomodation/Accommodation_Amend/Booking-Details2.jpg");
			wait.until(ExpectedConditions.visibilityOfElementLocated(Booking.BookingStatusPrepay));
			String ExpectedStatus = "Confirmed";
			String ExpectedNoOfAdults = "2 Adults";
			String ActualNoOfAdults = driverqa.findElement(Booking.noOfAdultsPrepay).getText();
			String ActualStatus = driverqa.findElement(Booking.BookingStatusPrepay).getText();
			Assert.assertTrue(ActualStatus.equalsIgnoreCase(ExpectedStatus));
			Assert.assertTrue(ActualNoOfAdults.contains(ExpectedNoOfAdults));
			test.log(LogStatus.INFO, "Ending Hotel Book");
			test.log(LogStatus.PASS, "Hotel Book");
			logger.info("Hotel Booked");

		} catch (Throwable e) {
			test.log(LogStatus.FAIL, "Hotel Booking");
			obj.Takesnap(driverqa, Config.SnapShotPath() + "/Accomodation/Error/Accommodation_Amend/Booking.jpg");
			errorpath = Config.SnapShotPath() + "/Accomodation/Error/Accommodation_Amend/Booking.jpg";
			logger.info(e.getMessage());
			test.log(LogStatus.FAIL, e.getMessage());
			rep.endTest(test);
			rep.flush();
			Assert.assertTrue(false, e.getMessage());
		}
		/* ####### Amending the booking on a different Check Out ######### **/

		test.log(LogStatus.INFO, "Starting Hotel Amend");
		logger.info("Starting Hotel Amend");
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(Amend.AmendButton));
			driverqa.findElement(Amend.AmendButton).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(Amend.VerifyAmendPage));
			test.log(LogStatus.INFO, "Changing No Of Passengers");
			/*
			 * Select Adults = new Select(driverqa.findElement(Amend.Adults));
			 * Adults.selectByIndex(2);
			 */
			Select Child = new Select(driverqa.findElement(Amend.Children));
			Child.selectByIndex(0);
			test.log(LogStatus.PASS, "Changing No Of Passengers");
			test.log(LogStatus.INFO, "Selecting new dates");
			Thread.sleep(2000);
			driverqa.findElement(Amend.Checkin).click();
			// wait.until(ExpectedConditions.visibilityOfElementLocated(Search.CalenderIN));
			// driverqa.findElement(Search.nextmnth).click();
			// driverqa.findElement(Search.nextmnth).click();
			List<WebElement> allDates = driverqa.findElements(Search.CalenderIN);

			for (WebElement ele : allDates) {

				String date = ele.getText();

				if (date.equalsIgnoreCase(excel.getData(0, 66, 1))) {
					ele.click();
					break;
				}

			}
			wait.until(ExpectedConditions.visibilityOfElementLocated(Search.CalenderIN));
			// driverqa.findElement(Search.nextmnth).click();
			// driverqa.findElement(Search.nextmnth).click();
			List<WebElement> allDates2 = driverqa.findElements(Search.CalenderIN);

			for (WebElement ele : allDates2) {

				String date = ele.getText();

				if (date.equalsIgnoreCase(excel.getData(0, 66, 2))) {
					ele.click();
					break;
				}

			}
			test.log(LogStatus.PASS, "Selection of new Dates");

			Thread.sleep(2000);
			obj.Takesnap(driverqa, Config.SnapShotPath() + "/Accomodation/Accommodation_Amend/Changed-Dates.jpg");
			driverqa.findElement(Amend.AfterAmendButton).click();

			wait.until(ExpectedConditions.visibilityOfElementLocated(Amend.AmendConfirmPageCheckIn));
			ActualAmendDateChkIn = driverqa.findElement(Amend.AmendConfirmPageCheckIn).getText();
			ActualAmendDateChkOut = driverqa.findElement(Amend.AmendConfirmPageCheckOut).getText();
			ExpectedAmendDateChkIn = excel.getData(0, 66, 1);
			ExpectedAmendDateChkOut = excel.getData(0, 66, 2);
			Assert.assertTrue(ActualAmendDateChkIn.contains(ExpectedAmendDateChkIn));
			Assert.assertTrue(ActualAmendDateChkOut.contains(ExpectedAmendDateChkOut));
			Thread.sleep(2000);
			obj.Takesnap(driverqa,
					Config.SnapShotPath() + "/Accomodation/Accommodation_Amend/Amend-Confirmation-Page.jpg");
			test.log(LogStatus.INFO, "Confirming Changes");
			logger.info("Confirming Changes");
			driverqa.findElement(Amend.ConfirmChanges).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(Amend.AmendSuccessTitle));
			ActualConfirmedAmendTitle = driverqa.findElement(Amend.AmendSuccessTitle).getText();
			ActualAfterAmendDateChkIn = driverqa.findElement(Amend.AfterAmendChckIn).getText();
			ActualAfterAmendDateChkOut = driverqa.findElement(Amend.AfterAmendChckOut).getText();
			ExpectedNoOfAdults = "2 Adults";
			ActualNoOfAdults = driverqa.findElement(Amend.AfterAmendNoOfPassengers).getText();
			System.out.println(ActualConfirmedAmendTitle);
			ExpectedAfterAmendDateChkIn = excel.getData(0, 66, 1);
			ExpectedAfterAmendDateChkOut = excel.getData(0, 66, 2);
			ExpectedConfirmedAmendTitle = "Successfully amended";
			Assert.assertTrue(ActualConfirmedAmendTitle.contains(ExpectedConfirmedAmendTitle));
			Thread.sleep(2000);
			obj.Takesnap(driverqa, Config.SnapShotPath() + "/Accomodation/Accommodation_Amend/Amend-Confirmed1.jpg");
			JavascriptExecutor js = (JavascriptExecutor) driverqa;
			js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(2000);
			obj.Takesnap(driverqa, Config.SnapShotPath() + "/Accomodation/Accommodation_Amend/Amend-Confirmed2.jpg");
			Assert.assertTrue(ActualAfterAmendDateChkIn.contains(ExpectedAfterAmendDateChkIn));
			Assert.assertTrue(ActualAfterAmendDateChkOut.contains(ExpectedAfterAmendDateChkOut));
			Assert.assertTrue(ActualNoOfAdults.contains(ExpectedNoOfAdults));
			test.log(LogStatus.PASS, "Confirmed Changes");
			logger.info("Confirmed Changes");
			test.log(LogStatus.INFO, "Ending Hotel Amend");
			test.log(LogStatus.PASS, "Hotel Amend");
			logger.info("Hotel Amended");

		} catch (Throwable e) {
			test.log(LogStatus.FAIL, "Hotel Amend");
			obj.Takesnap(driverqa, Config.SnapShotPath() + "/Accomodation/Error/Accommodation_Amend/Amending.jpg");
			errorpath = Config.SnapShotPath() + "/Accomodation/Error/Accommodation_Amend/Amending.jpg";
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
