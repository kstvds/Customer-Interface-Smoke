package Others;

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

import ObjectRepository.Ci_Register;
import ObjectRepository.LoginPage;
import ObjectRepository.Others;
import Utility.Configuration;
import lib.DriverAndObjectDetails;
import lib.ExcelDataConfig;
import lib.ExtentManager;
import lib.Takescreenshot;
import lib.DriverAndObjectDetails.DriverName;

/* #######  Test for new credit customer registration  #########
######  User creates a new credit customer registration and then activates the customer ##### */

public class Register_Credit_Customer {
	public WebDriver driverqa;
	ExtentTest test;
	ExcelDataConfig excel;
	String errorpath;
	String actualresult;
	String expectedtitle;
	String atualtitle;
	String expectedresult;
	Configuration Config = new Configuration();
	Takescreenshot obj = new Takescreenshot();
	ExtentReports rep = ExtentManager.getInstance();
	LoginPage login = new LoginPage();
	Logger logger = Logger.getLogger("Register_Credit_Customer");

	/* ####### Passing browser as parameters in test ######### **/

	@Test
	@Parameters({ "browsername" })
	public void RegisterCreditCustomer(String browsername) throws Exception {
		test = rep.startTest("Register Credit Customer");
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

		/* ####### New customer registration fill up ######### **/

		try {
			logger.info("Browser Opened");

			// Opening URL

			String URL = excel.getData(0, 1, 3) + "/interface/en";
			driverqa.get(URL);
			logger.info("Test Case Started");
			logger.info("Starting Register Credit Customer");
			test.log(LogStatus.INFO, "Starting Register Credit Customer");

			// Navigating to customer registration tab

			logger.info("Navigating to registration page");
			test.log(LogStatus.INFO, "Navigating to registration page");
			driverqa.findElement(Ci_Register.Register_now).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(Ci_Register.Company_name));
			logger.info("Navigated to registration page");
			test.log(LogStatus.PASS, "Navigated to registration page");

			// Fill up Details

			logger.info("Filling up the details");
			test.log(LogStatus.INFO, "Filling up the details");
			driverqa.findElement(Ci_Register.Company_name).sendKeys("Anindya Company");
			driverqa.findElement(Ci_Register.Company_Address).sendKeys("Anindya Company");
			Select dropdown = new Select(driverqa.findElement(Ci_Register.Company_country));
			dropdown.selectByVisibleText("INDIA");
			Thread.sleep(2000);
			Select dropdown1 = new Select(driverqa.findElement(Ci_Register.Company_city));
			dropdown1.selectByVisibleText("CALICUT");
			Select dropdown2 = new Select(driverqa.findElement(Ci_Register.Preffered_Payment_Method));
			dropdown2.selectByVisibleText("Credit");
			Select dropdown3 = new Select(driverqa.findElement(Ci_Register.Preffered_Currency));
			dropdown3.selectByVisibleText("USD - US Dollars");
			driverqa.findElement(Ci_Register.Telephone_City_Code).sendKeys("033");
			driverqa.findElement(Ci_Register.Telephone_Number).sendKeys("1234567894");
			Select dropdown4 = new Select(driverqa.findElement(Ci_Register.Salutation));
			dropdown4.selectByIndex(1);
			driverqa.findElement(Ci_Register.FirstName).sendKeys("Kaustav");
			driverqa.findElement(Ci_Register.LastName).sendKeys("Anindya");
			driverqa.findElement(Ci_Register.Email_Address).sendKeys("kaustav.d@dotw.com");
			Thread.sleep(1000);
			driverqa.findElement(Ci_Register.Confirm_Email_Address).sendKeys("kaustav.d@dotw.com");
			driverqa.findElement(Ci_Register.Login_Id).sendKeys(excel.getData(0, 58, 5));
			Thread.sleep(1000);
			driverqa.findElement(Ci_Register.Password).sendKeys(excel.getData(0, 58, 6));
			driverqa.findElement(Ci_Register.Confirm_Password).sendKeys(excel.getData(0, 58, 6));
			driverqa.findElement(Ci_Register.FinanceEmail).sendKeys("kaustav.d@dotw.com");
			driverqa.findElement(Ci_Register.Terms_of_use).click();
			logger.info("Filled up the details");
			test.log(LogStatus.PASS, "Filled up the details");
			Thread.sleep(2000);
			obj.Takesnap(driverqa, Config.SnapShotPath() + "/Others/Register_Credit_Customer/Filled-Details.jpg");
			driverqa.findElement(Ci_Register.Register_button).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(Ci_Register.accept_continue));
			driverqa.findElement(Ci_Register.accept_continue).click();
			Thread.sleep(2000);
			obj.Takesnap(driverqa,
					Config.SnapShotPath() + "/Others/Register_Credit_Customer/Terms-and-conditions-page.jpg");
			driverqa.findElement(Ci_Register.continue1).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(Ci_Register.thank_you));
			Thread.sleep(2000);
			obj.Takesnap(driverqa, Config.SnapShotPath() + "/Others/Register_Credit_Customer/Customer-Created.jpg");
			String expectedtext = "Thank you for completing your registration.";
			String actualtext = driverqa.findElement(Ci_Register.thank_you).getText();
			Assert.assertEquals(actualtext, expectedtext);
			test.log(LogStatus.PASS, "Register Credit Customer");
			logger.info("Registered Credit Customer");
			driverqa.close();
		}

		// Catches throwable errors

		catch (Throwable e) {

			obj.Takesnap(driverqa,
					Config.SnapShotPath() + "/Others/Error/Register_Credit_Customer/Customer-Creation.jpg");
			test.log(LogStatus.FAIL, "Customer Creation");
			errorpath = Config.SnapShotPath() + "/Others/Error/Register_Credit_Customer/Customer-Creation.jpg";
			logger.info(e.getMessage());
			test.log(LogStatus.FAIL, e.getMessage());
			rep.endTest(test);
			rep.flush();
			Assert.assertTrue(false, e.getMessage());

		}

		/* ####### Initializing new driver ######### **/

		if (browsername.equalsIgnoreCase("CH")) {
			driverqa = new DriverAndObjectDetails(DriverName.CH).CreateDriver();
		} else if (browsername.equalsIgnoreCase("IE")) {
			driverqa = new DriverAndObjectDetails(DriverName.IE).CreateDriver();
		} else {
			driverqa = new DriverAndObjectDetails(DriverName.FF).CreateDriver();
		}
		WebDriverWait wait1 = new WebDriverWait(driverqa, 50);
		Actions action1 = new Actions(driverqa);

		/* ####### Login functionality MyAdmin ######### **/

		try {
			logger.info("Browser Opened for MyAdmin");
			String URL = excel.getData(0, 1, 3) + "/_myadmin";
			driverqa.get(URL);
			logger.info("Starting Login MyAdmin");
			test.log(LogStatus.INFO, "Starting Login MyAdmin");
			WebElement username = driverqa.findElement(LoginPage.uname);
			username.clear();
			username.sendKeys(excel.getData(0, 1, 0));
			WebElement password = driverqa.findElement(LoginPage.pwd);
			password.clear();
			password.sendKeys(excel.getData(0, 1, 1));
			driverqa.findElement(LoginPage.submit).click();
			Thread.sleep(1000);
			String expectedtitle = "DOTWconnect.com::DOTWconnect.com: My Admin";
			String atualtitle = driverqa.getTitle();
			Assert.assertEquals(atualtitle, expectedtitle);
			test.log(LogStatus.INFO, "Ending Login MyAdmin");
			test.log(LogStatus.PASS, "PASSED Login MyAdmin");
			logger.info("Login Successful");
			wait1.until(ExpectedConditions.visibilityOfElementLocated(Ci_Register.customers));
			Thread.sleep(2000);
			obj.Takesnap(driverqa, Config.SnapShotPath() + "/Others/Register_Credit_Customer/Log-In-MyAdmin.jpg");

		} catch (Throwable e) {
			obj.Takesnap(driverqa, Config.SnapShotPath() + "/Others/Error/Register_Credit_Customer/Log-In-MyAdmin.jpg");
			test.log(LogStatus.FAIL, "Login MyAdmin");
			errorpath = Config.SnapShotPath() + "/Others/Error/Register_Credit_Customer/Log-In-MyAdmin.jpg";
			logger.info(e.getMessage());
			test.log(LogStatus.FAIL, e.getMessage());
			rep.endTest(test);
			rep.flush();
			Assert.assertTrue(false, e.getMessage());

		}

		/* ####### Activating the created customer ######### **/

		try {
			test.log(LogStatus.INFO, "Activating new customer");
			logger.info("Activating new customer");
			logger.info("Navigating to customer filter page");
			test.log(LogStatus.INFO, "Navigating to Customer filter Page");
			driverqa.findElement(Ci_Register.customers).click();
			wait1.until(ExpectedConditions.visibilityOfElementLocated(Ci_Register.new_cust));
			driverqa.findElement(Ci_Register.new_cust).click();
			Thread.sleep(2000);
			String searchcustatualtitle = driverqa.getTitle();
			String searchcustexpectedtitle = "DOTWconnect.com::New customers";
			Assert.assertEquals(searchcustatualtitle, searchcustexpectedtitle);
			logger.info("Navigated to customer filter page");
			test.log(LogStatus.PASS, "Navigated to Customer filter Page");
			// driverqa.findElement(Ci_Register.company_name).sendKeys("Anindya
			// Company");
			Thread.sleep(2000);
			obj.Takesnap(driverqa, Config.SnapShotPath() + "/Others/Register_Credit_Customer/Customer-FiltersPage.jpg");
			driverqa.findElement(Ci_Register.filter_cus).click();
			wait1.until(ExpectedConditions.visibilityOfElementLocated(Ci_Register.edit_newcust));
			driverqa.findElement(Ci_Register.edit_newcust).click();
			Thread.sleep(2000);
			wait1.until(ExpectedConditions.visibilityOfElementLocated(Ci_Register.active_newcust));
			String searchcustatualtitle1 = driverqa.getTitle();
			String searchcustexpectedtitle1 = "DOTWconnect.com::Customer";
			Assert.assertEquals(searchcustatualtitle1, searchcustexpectedtitle1);
			driverqa.findElement(Ci_Register.active_newcust).click();
			Thread.sleep(2000);
			obj.Takesnap(driverqa, Config.SnapShotPath() + "/Others/Register_Credit_Customer/Activating customer.jpg");
			Select dropdown_manager = new Select(driverqa.findElement(Ci_Register.select_manager));
			dropdown_manager.selectByValue("7932");
			driverqa.findElement(Ci_Register.update_newcust).click();
			Thread.sleep(2000);
			obj.Takesnap(driverqa, Config.SnapShotPath() + "/Others/Register_Credit_Customer/Customer-Activated.jpg");
			logger.info("New customer activated successfully");
			test.log(LogStatus.PASS, "New customer activated successfully");

		} catch (Throwable e) {
			obj.Takesnap(driverqa,
					Config.SnapShotPath() + "/Others/Error/Register_Credit_Customer/Customer-Activation.jpg");
			test.log(LogStatus.FAIL, "Customer Activation");
			errorpath = Config.SnapShotPath() + "/Others/Error/Register_Credit_Customer/Customer-Activation.jpg";
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

	/* ####### Generating the Failure Reports and Screenshots ######### **/

	@AfterTest
	public void afterTest() {

		rep.endTest(test);
		rep.flush();
		driverqa.close();
	}
}
