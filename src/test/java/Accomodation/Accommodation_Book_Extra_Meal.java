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
import ObjectRepository.Ci_Register;
import ObjectRepository.LoginPage;
import ObjectRepository.PaymentPage;
import ObjectRepository.Search;
import Utility.Configuration;
import lib.DriverAndObjectDetails;
import lib.ExcelDataConfig;
import lib.ExtentManager;
import lib.Takescreenshot;
import lib.DriverAndObjectDetails.DriverName;

/* #######  Test for Accommodation booking with extra meal  #########
######  User Logs In, Books accommodation with extra meal added ##### */

public class Accommodation_Book_Extra_Meal {
	public WebDriver driverqa;
	ExtentTest test;
	String errorpath;
	String Roomtype;
	ExcelDataConfig excel;
	Configuration Config = new Configuration();
	Takescreenshot obj = new Takescreenshot();
	ExtentReports rep = ExtentManager.getInstance();
	LoginPage login = new LoginPage();
	Logger logger = Logger.getLogger("Accommodation_Book_Extra_Meal");

	/* ####### Passing browser as parameters in test ######### **/

	@Test
	@Parameters({ "browsername" })
	public void AccommodationBookExtraMeal(String browsername) throws Exception {
		test = rep.startTest("Accommodation Book Extra Meal");
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
			obj.Takesnap(driverqa,
					Config.SnapShotPath() + "/Accomodation/Accommodation_Book_With_Extra_Meal/Log-In.jpg");

		} catch (Throwable e) {

			obj.Takesnap(driverqa,
					Config.SnapShotPath() + "/Accommodation/Error/Accommodation_Book_With_Extra_Meal/Log-In.jpg");
			test.log(LogStatus.FAIL, "Login");
			errorpath = Config.SnapShotPath() + "/Accommodation/Error/Accommodation_Book_With_Extra_Meal/Log-In.jpg";
			logger.info(e.getMessage());
			test.log(LogStatus.FAIL, e.getMessage());
			rep.endTest(test);
			rep.flush();
			Assert.assertTrue(false, e.getMessage());

		}

		/* ####### Applying filters and searching for filters ######### **/

		try {
			logger.info("Applying search Filters");
			logger.info("Starting HotelSearch Credit Card");
			test.log(LogStatus.INFO, "Starting HotelSearch Credit Card");
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
			 * WebElement Noofadults = driverqa.findElement(Search.NoOfAdults);
			 * Noofadults.clear(); Noofadults.sendKeys("01");
			 */
			Thread.sleep(2000);
			obj.Takesnap(driverqa,
					Config.SnapShotPath() + "/Accomodation/Accommodation_Book_With_Extra_Meal/Filters.jpg");
			String expectedresult = excel.getData(0, 12, 1);
			driverqa.findElement(Search.SearchBtn).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(Search.HotelTitle));
			Thread.sleep(2000);
			obj.Takesnap(driverqa,
					Config.SnapShotPath() + "/Accomodation/Accommodation_Book_With_Extra_Meal/Search-Result.jpg");
			String actualresult = driverqa.findElement(Search.HotelTitle).getText();
			System.out.println(actualresult);
			System.out.println(expectedresult);
			Assert.assertTrue(actualresult.equalsIgnoreCase(expectedresult));
			test.log(LogStatus.INFO, "Ending HotelSearch Credit Card");
			test.log(LogStatus.PASS, "PASSED HotelSearch Credit Card");
			logger.info("Hotel Search Complete Credit Card");
		} catch (Throwable e) {
			test.log(LogStatus.FAIL, "Hotel Search Credit Card");

			obj.Takesnap(driverqa, Config.SnapShotPath()
					+ "/Accommodation/Error/Accommodation_Book_With_Extra_Meal/Search-Result.jpg");
			errorpath = Config.SnapShotPath()
					+ "/Accommodation/Error/Accommodation_Book_With_Extra_Meal/Search-Result.jpg";
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
			driverqa.findElement(Booking.OnePaxFirstName).sendKeys(excel.getData(0, 21, 1));
			Thread.sleep(2000);
			driverqa.findElement(Booking.OnePaxlastName).sendKeys(excel.getData(0, 21, 2));
			Select passengertitle = new Select(driverqa.findElement(Booking.OnePaxTitle));
			passengertitle.selectByIndex(1);
			/*
			 * if (driverqa.findElements(Booking.TwoPaxFirstName).size() != 0) {
			 * driverqa.findElement(Booking.TwoPaxFirstName).sendKeys(excel.
			 * getData(0, 22, 1)); Thread.sleep(2000);
			 * driverqa.findElement(Booking.TwoPaxLastName).sendKeys(excel.
			 * getData(0, 22, 2)); Select passengertitle2 = new
			 * Select(driverqa.findElement(Booking.TwoPaxTitle));
			 * passengertitle2.selectByIndex(1); }
			 */

			// ******* Selecting Meal Type *********//

			logger.info("Entering Extra Meal Details");
			test.log(LogStatus.INFO, "Entering Extra Meal Details");
			WebElement element = driverqa.findElement(Booking.ExtraMealTab);
			JavascriptExecutor js = (JavascriptExecutor) driverqa;
			js.executeScript("var evt = document.createEvent('MouseEvents');"
					+ "evt.initMouseEvent('click',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);"
					+ "arguments[0].dispatchEvent(evt);", element);
			driverqa.findElement(Booking.ExtraMealTab).click();
			// wait.until(ExpectedConditions.visibilityOfElementLocated(Booking.MealGuestType));
			Thread.sleep(2000);
			// Select dropdown = new
			// Select(driverqa.findElement(Booking.MealGuestType));
			Select dropdown = new Select(driverqa.findElement(Booking.MealGuestType));
			dropdown.selectByVisibleText("Adult");
			Thread.sleep(3000);
			Select dropdown1 = new Select(driverqa.findElement(Booking.MealType));
			dropdown1.selectByVisibleText("Lunch");
			Thread.sleep(2000);
			;
			Select dropdown3 = new Select(driverqa.findElement(Booking.MealName));
			dropdown3.selectByVisibleText("Superb Meal");
			Thread.sleep(2000);
			Select dropdown4 = new Select(driverqa.findElement(Booking.MealGuests));
			dropdown4.selectByVisibleText("1");
			Thread.sleep(2000);
			driverqa.findElement(Booking.SelectDate).click();
			test.log(LogStatus.INFO, "Entered Extra Meal Details");
			test.log(LogStatus.PASS, "Extra Meal Details");
			driverqa.findElement(Booking.PrcdToBookChckBox).click();
			Thread.sleep(2000);
			obj.Takesnap(driverqa,
					Config.SnapShotPath() + "/Accommodation/Accommodation_Book_With_Extra_Meal/Passenger-Details.jpg");
			logger.info("Entered Passenger details");
			test.log(LogStatus.INFO, "Entered Passenger details");
			test.log(LogStatus.PASS, "Passenger details");
			driverqa.findElement(Booking.ConfirmBook).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(Booking.ProccedToBook));
			Thread.sleep(2000);
			obj.Takesnap(driverqa,
					Config.SnapShotPath() + "/Accommodation/Accommodation_Book_With_Extra_Meal/Confirm-Booking.jpg");
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
			obj.Takesnap(driverqa,
					Config.SnapShotPath() + "/Accommodation/Accommodation_Book_With_Extra_Meal/Payment-Details.jpg");
			driverqa.findElement(PaymentPage.Acceptpayment).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(Booking.ViewBooking));
			JavascriptExecutor js1 = (JavascriptExecutor) driverqa;
			Thread.sleep(2000);
			obj.Takesnap(driverqa, Config.SnapShotPath()
					+ "/Accommodation/Accommodation_Book_With_Extra_Meal/Search-Booking-Page.jpg");
			// WebElement Element = driverqa.findElement(Booking.Invoice);
			// This will scroll the page till the element is found
			/*
			 * Assert.assertTrue(ActualStatus.equalsIgnoreCase(ExpectedStatus));
			 * Assert.assertTrue(ActualNoOfAdults.equalsIgnoreCase(
			 * ExpectedNoOfAdults));
			 */
			driverqa.findElement(Booking.ViewBooking).click();
			Thread.sleep(2000);
			obj.Takesnap(driverqa,
					Config.SnapShotPath() + "/Accommodation/Accommodation_Book_With_Extra_Meal/Booking-Details1.jpg");
			// This will scroll the page till the element is found
			js1.executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(2000);
			obj.Takesnap(driverqa,
					Config.SnapShotPath() + "/Accommodation/Accommodation_Book_With_Extra_Meal/Booking-Details2.jpg");
			wait.until(ExpectedConditions.visibilityOfElementLocated(Booking.BookingStatusPrepay));
			String ExpectedStatus = "Confirmed";
			String ExpectedNoOfAdults = "2 Adults";
			String ActualNoOfAdults = driverqa.findElement(Booking.noOfAdultsPrepay).getText();
			String ActualStatus = driverqa.findElement(Booking.BookingStatusPrepay).getText();
			Assert.assertTrue(ActualStatus.equalsIgnoreCase(ExpectedStatus));
			Assert.assertTrue(ActualNoOfAdults.equalsIgnoreCase(ExpectedNoOfAdults));
			test.log(LogStatus.INFO, "Ending Hotel Book");
			test.log(LogStatus.PASS, "Hotel Book");
			logger.info("Hotel Booked");

		} catch (Throwable e) {
			test.log(LogStatus.FAIL, "Hotel Book Credit Card");
			obj.Takesnap(driverqa,
					Config.SnapShotPath() + "/Accommodation/Error/Accommodation_Book_With_Extra_Meal/Booking.jpg");
			errorpath = Config.SnapShotPath() + "/Accommodation/Error/Accommodation_Book_With_Extra_Meal/Booking.jpg";
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
