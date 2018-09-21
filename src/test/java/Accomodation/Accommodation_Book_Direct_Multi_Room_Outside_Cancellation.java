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

import ObjectRepository.Booking;
import ObjectRepository.LoginPage;
import ObjectRepository.Multi_Room;
import ObjectRepository.PaymentPage;
import ObjectRepository.Search;
import Utility.Configuration;
import lib.DriverAndObjectDetails;
import lib.ExcelDataConfig;
import lib.ExtentManager;
import lib.Takescreenshot;
import lib.DriverAndObjectDetails.DriverName;

/* #######  Test for Accommodation booking for Direct multiple room outside cancellation  #########
######  User Logs In, Searches for Direct multiple room outside cancellation, books the rooms for that hotel ##### */

public class Accommodation_Book_Direct_Multi_Room_Outside_Cancellation {
	public WebDriver driverqa;
	ExtentTest test;
	String errorpath;
	String Roomtype;
	ExcelDataConfig excel;
	Configuration Config = new Configuration();
	Takescreenshot obj = new Takescreenshot();
	ExtentReports rep = ExtentManager.getInstance();
	LoginPage login = new LoginPage();
	Logger logger = Logger.getLogger("Accommodation_Book_Direct_Multi_Room_Outside_Cancellation");

	/* ####### Passing browser as parameters in test ######### **/

	@Test
	@Parameters({ "browsername" })
	public void AccommodationBookDirectMultiRoomOutsideCancellation(String browsername) throws Exception {
		test = rep.startTest("Accommodation Book Direct Multi Room Outside Cancellation");
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
			// wait.until(ExpectedConditions.visibilityOfElementLocated(LoginPage.Closetuto));
			// driverqa.findElement(LoginPage.Closetuto).click();
			action.sendKeys(Keys.ESCAPE).build().perform();
			Thread.sleep(2000);
			obj.Takesnap(driverqa, Config.SnapShotPath()
					+ "/Accomodation/Accommodation_Book_Direct_Multi_Room_Outside_Cancellation/Log-In.jpg");

		} catch (Throwable e) {

			obj.Takesnap(driverqa, Config.SnapShotPath()
					+ "/Accomodation/Error/Accommodation_Book_Direct_Multi_Room_Outside_Cancellation/Log-In.jpg");
			test.log(LogStatus.FAIL, "Login");
			errorpath = Config.SnapShotPath()
					+ "/Accomodation/Error/Accommodation_Book_Direct_Multi_Room_Outside_Cancellation/Log-In.jpg";
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
			driverqa.findElement(Multi_Room.MultiRoomDropdown).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(Multi_Room.SelectTwooRooms));
			Thread.sleep(2000);
			WebElement element1 = driverqa.findElement(Multi_Room.SelectTwooRooms);
			Actions actions = new Actions(driverqa);
			actions.moveToElement(element1).click().perform();
			driverqa.findElement(Search.dest).sendKeys(excel.getData(0, 13, 1));
			Thread.sleep(6000);
			action.sendKeys(Keys.ARROW_DOWN).build().perform();
			//action.sendKeys(Keys.ARROW_DOWN).build().perform();
			action.sendKeys(Keys.ENTER).build().perform();
			test.log(LogStatus.INFO, "Selecting dates");
			driverqa.findElement(Search.InDate).click();
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
			/*
			 * driverqa.findElement(Search.PaymentOption).click();
			 * wait.until(ExpectedConditions.visibilityOfElementLocated(Search.
			 * NetPay)); Thread.sleep(1000); WebElement element =
			 * driverqa.findElement(Search.NetPay);
			 * 
			 * Actions actions = new Actions(driverqa);
			 * 
			 * actions.moveToElement(element).click().perform();
			 */
			Thread.sleep(2000);
			obj.Takesnap(driverqa, Config.SnapShotPath()
					+ "/Accomodation/Accommodation_Book_Direct_Multi_Room_Outside_Cancellation/Two-rooms.jpg");
			String expectedresult = excel.getData(0, 13, 1);
			driverqa.findElement(Search.SearchBtn).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(Search.HotelTitle));
			Thread.sleep(2000);
			obj.Takesnap(driverqa, Config.SnapShotPath()
					+ "/Accomodation/Accommodation_Book_Direct_Multi_Room_Outside_Cancellation/Search-Result.jpg");
			String actualresult = driverqa.findElement(Search.HotelTitle).getText();
			Assert.assertTrue(actualresult.contains(expectedresult));
			test.log(LogStatus.INFO, "Ending HotelSearch ");
			test.log(LogStatus.PASS, "PASSED HotelSearch ");
			logger.info("Hotel Search Complete");
		} catch (Throwable e) {
			test.log(LogStatus.FAIL, "Hotel Search");
			obj.Takesnap(driverqa, Config.SnapShotPath()
					+ "/Accomodation/Error/Accommodation_Book_Direct_Multi_Room_Outside_Cancellation/Search-Result.jpg");
			errorpath = Config.SnapShotPath()
					+ "/Accomodation/Error/Accommodation_Book_Direct_Multi_Room_Outside_Cancellation/Search-Result.jpg";
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
			wait.until(ExpectedConditions.visibilityOfElementLocated(Multi_Room.Selecttworoomsaftersearch));
			test.log(LogStatus.INFO, "Selecting Room");
			logger.info("Selecting Room");
			Select dropdown1 = new Select(driverqa.findElement(Multi_Room.Selecttworoomsaftersearch));
			dropdown1.selectByVisibleText("2");
			Thread.sleep(2000);
			obj.Takesnap(driverqa, Config.SnapShotPath()
					+ "/Accomodation/Accommodation_Book_Direct_Multi_Room_Outside_Cancellation/Room-Selected.jpg");
			/*WebElement Booknowbutton = driverqa.findElement(Multi_Room.BookNowMultiRoom); 
		    Actions actions = new Actions(driverqa);
            actions.moveToElement(Booknowbutton).click().perform();*/
			
			JavascriptExecutor js3 = (JavascriptExecutor) driverqa;
			WebElement ScrollElement = driverqa.findElement(Booking.ScrollElement);
			
			// This will scroll the web page till end.
			
			js3.executeScript("arguments[0].scrollIntoView();", ScrollElement);
			Thread.sleep(1000);
			driverqa.findElement(Multi_Room.BookNowMultiRoom).click();
			
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
			 * if (driverqa.findElements(Booking.TwoPaxFirstName).size() != 0) {
			 * driverqa.findElement(Booking.TwoPaxFirstName).sendKeys(excel.
			 * getData(0, 22, 1)); Thread.sleep(1000);
			 * driverqa.findElement(Booking.TwoPaxLastName).sendKeys(excel.
			 * getData(0, 22, 2)); Select passengertitle2 = new
			 * Select(driverqa.findElement(Booking.TwoPaxTitle));
			 * passengertitle2.selectByIndex(1); }
			 */
			driverqa.findElement(Multi_Room.FirstName1Room2).sendKeys(excel.getData(0, 21, 1));
			Thread.sleep(1000);
			driverqa.findElement(Multi_Room.LastName1Room2).sendKeys(excel.getData(0, 21, 2));
			Select passengertitle3 = new Select(driverqa.findElement(Multi_Room.Title1Room2));
			passengertitle3.selectByIndex(1);
			/*
			 * if (driverqa.findElements(Multi_Room.FirstName2Room2).size() !=
			 * 0) {
			 * driverqa.findElement(Multi_Room.FirstName2Room2).sendKeys(excel.
			 * getData(0, 22, 1)); Thread.sleep(1000);
			 * driverqa.findElement(Multi_Room.LastName2Room2).sendKeys(excel.
			 * getData(0, 22, 2)); Select passengertitle2 = new
			 * Select(driverqa.findElement(Multi_Room.Title2Room2));
			 * passengertitle2.selectByIndex(1); }
			 */
			driverqa.findElement(Booking.PrcdToBookChckBox).click();
			Thread.sleep(2000);
			obj.Takesnap(driverqa, Config.SnapShotPath()
					+ "/Accomodation/Accommodation_Book_Direct_Multi_Room_Outside_Cancellation/Passenger-Details.jpg");
			logger.info("Entered Passenger details");
			test.log(LogStatus.INFO, "Entered Passenger details");
			test.log(LogStatus.PASS, "Passenger details");
			JavascriptExecutor js1 = (JavascriptExecutor) driverqa;

			// This will scroll the web page till end.

			js1.executeScript("window.scrollTo(0, document.body.scrollHeight)");
			driverqa.findElement(Booking.ConfirmBook).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(Booking.ProccedToBook));
			Thread.sleep(2000);
			obj.Takesnap(driverqa, Config.SnapShotPath()
					+ "/Book/Accommodation_Book_Direct_Multi_Room_Outside_Cancellation/Confirm-Booking.jpg");
			driverqa.findElement(Booking.ProccedToBook).click();
			logger.info("Entering Payment details");
			test.log(LogStatus.INFO, "Entering Payment details");
			wait.until(ExpectedConditions.visibilityOfElementLocated(PaymentPage.FirstName));
			WebElement FirstName = driverqa.findElement(PaymentPage.FirstName);
			FirstName.clear();
			FirstName.sendKeys(excel.getData(0, 20, 1));
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
			obj.Takesnap(driverqa, Config.SnapShotPath()
					+ "/Book/Accommodation_Book_Direct_Multi_Room_Outside_Cancellation/Payment-Details.jpg");
			driverqa.findElement(PaymentPage.Acceptpayment).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(Multi_Room.Confirmedstatusroom1));
			String ExpectedStatus = "Confirmed";
			String ExpectedCheckiN = excel.getData(0, 65, 1);
			String ExpectedCheckout = excel.getData(0, 65, 2);
			// String ExpectedNoOfAdults = "2 Adults";
			// String ActualNoOfAdults =
			// driverqa.findElement(Booking.noOfAdultsPrepay).getText();
			String ActualStatusRoom1 = driverqa.findElement(Multi_Room.Confirmedstatusroom1).getText();
			String ActualStatusRoom2 = driverqa.findElement(Multi_Room.Confirmedstatusroom2).getText();
			String CheckinRoom1 = driverqa.findElement(Multi_Room.CheckinRoomroom1).getText();
			String CheckoutRoom1 = driverqa.findElement(Multi_Room.CheckoutRoomroom1).getText();
			String CheckinRoom2 = driverqa.findElement(Multi_Room.CheckinRoomroom2).getText();
			String CheckoutRoom2 = driverqa.findElement(Multi_Room.CheckoutRoomroom2).getText();
			// JavascriptExecutor js = (JavascriptExecutor) driverqa;
			Thread.sleep(2000);
			obj.Takesnap(driverqa, Config.SnapShotPath()
					+ "/Accomodation/Accommodation_Book_DOTW_Multi_Room_Outside_Cancellation/Booking-Details1.jpg");
			/*
			 * WebElement Element = driverqa.findElement(Booking.Invoice);
			 * 
			 * // This will scroll the page till the element is found
			 * 
			 * js.executeScript("arguments[0].scrollIntoView();", Element);
			 * Thread.sleep(2000); obj.Takesnap(driverqa, Config.SnapShotPath()
			 * +
			 * "/Accomodation/Accommodation_Book_DOTW_Multi_Room_Outside_Cancellation/Booking-Details2.jpg"
			 * );
			 */ Assert.assertTrue(ActualStatusRoom1.equalsIgnoreCase(ExpectedStatus));
			Assert.assertTrue(ActualStatusRoom2.equalsIgnoreCase(ExpectedStatus));
			Assert.assertTrue(CheckinRoom1.contains(ExpectedCheckiN));
			Assert.assertTrue(CheckinRoom2.contains(ExpectedCheckiN));
			Assert.assertTrue(CheckoutRoom1.contains(ExpectedCheckout));
			Assert.assertTrue(CheckoutRoom2.contains(ExpectedCheckout));
			// Assert.assertTrue(ActualNoOfAdults.equalsIgnoreCase(ExpectedNoOfAdults));
			test.log(LogStatus.INFO, "Ending Hotel Book");
			test.log(LogStatus.PASS, "Hotel Book");
			logger.info("Hotel Booked");

		} catch (Throwable e) {
			test.log(LogStatus.FAIL, "Hotel Book");
			obj.Takesnap(driverqa, Config.SnapShotPath()
					+ "/Accomodation/Error/Accommodation_Book_DOTW_Multi_Room_Outside_Cancellation/Booking.jpg");
			errorpath = Config.SnapShotPath()
					+ "/Accomodation/Error/Accommodation_Book_DOTW_Multi_Room_Outside_Cancellation/Booking.jpg";
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
