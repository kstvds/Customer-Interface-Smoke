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

public class Add_Edit_Sub_Customer {
	public WebDriver driverqa;
	ExtentTest test;
	String errorpath;
	String Roomtype;
	String ExpectedSuccessMSG;
	String ActualSuccessMSG;
	String ExpectedAddsubcustomerpagetitle;
	String ActualAddsubcustomerpagetitle;
	ExcelDataConfig excel;
	Configuration Config = new Configuration();
	Takescreenshot obj = new Takescreenshot();
	ExtentReports rep = ExtentManager.getInstance();
	LoginPage login = new LoginPage();
	Logger logger = Logger.getLogger("Add_Edit_Sub_Customer");

	/* ####### Passing browser as parameters in test ######### **/

	@Test
	@Parameters({ "browsername" })
	public void AddEditSubCustomer(String browsername) throws Exception {
		test = rep.startTest("Add Edit Sub Customer");
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
			username.sendKeys(excel.getData(0, 62, 1));
			wait.until(ExpectedConditions.visibilityOfElementLocated(LoginPage.password));
			driverqa.findElement(LoginPage.password).sendKeys(excel.getData(0, 62, 2));
			Thread.sleep(1000);
			WebElement company = driverqa.findElement(LoginPage.Companycode);
			company.clear();
			company.sendKeys(excel.getData(0, 62, 3));
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
			obj.Takesnap(driverqa, Config.SnapShotPath() + "/Others/Add_Edit_Sub_Customer/Log-In.jpg");

		} catch (Throwable e) {

			obj.Takesnap(driverqa, Config.SnapShotPath() + "/Others/Error/Add_Edit_Sub_Customer/Log-In.jpg");
			test.log(LogStatus.FAIL, "Login");
			errorpath = Config.SnapShotPath() + "/Others/Error/Add_Edit_Sub_Customer/Log-In.jpg";
			logger.info(e.getMessage());
			test.log(LogStatus.FAIL, e.getMessage());
			rep.endTest(test);
			rep.flush();
			Assert.assertTrue(false, e.getMessage());

		}
		
		/* ####### Adding new Sub Customer ######### **/
		
		try {
			test.log(LogStatus.INFO, "Adding Sub Customer");
			logger.info("Adding Sub Customer");
			driverqa.findElement(Others.UserManagement).click();
			test.log(LogStatus.INFO, "Navigating to add subcustomer Page");
			logger.info("Navigating to add subcustomer Page");
			wait.until(ExpectedConditions.visibilityOfElementLocated(Others.AddSubCustomer));
			driverqa.findElement(Others.AddSubCustomer).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(Others.AddSubCustomerSalutation));
			String ExpectedAddsubcustomerpagetitle = "DOTWconnect.com";
			String ActualAddsubcustomerpagetitle = driverqa.getTitle();
			Assert.assertEquals(ActualAddsubcustomerpagetitle, ExpectedAddsubcustomerpagetitle);
			test.log(LogStatus.PASS, "Navigating to add subcustomer Page");
			logger.info("Navigated to add subcustomer Page");
			test.log(LogStatus.INFO, "Entering subcustomer details");
			logger.info("Entering subcustomer details");
			Select dropdown_title_subcustomer = new Select(driverqa.findElement(Others.AddSubCustomerSalutation));
			dropdown_title_subcustomer.selectByValue("1328");
			WebElement subcustomer_firstname = driverqa.findElement(Others.AddSubCustomerFirstName);
			subcustomer_firstname.clear();
			subcustomer_firstname.sendKeys("Kaustav");
			WebElement subcustomer_lastname = driverqa.findElement(Others.AddSubCustomerLastName);
			subcustomer_lastname.clear();
			subcustomer_lastname.sendKeys("Das");
			WebElement subcustomer_Pretel = driverqa.findElement(Others.AddSubCustomerPrefixTelephone);
			subcustomer_Pretel.clear();
			subcustomer_Pretel.sendKeys("033");
			WebElement subcustomer_TelNo = driverqa.findElement(Others.AddSubCustomerTelephoneno);
			subcustomer_TelNo.clear();
			subcustomer_TelNo.sendKeys("98547456");
			Thread.sleep(2000);
			obj.Takesnap(driverqa, Config.SnapShotPath() + "/Others/Add_Edit_Sub_Customer/Adding-Sub-Customer-Details1.jpg");
			WebElement subcustomer_Email = driverqa.findElement(Others.AddSubCustomerEmail);
			subcustomer_Email.clear();
			subcustomer_Email.sendKeys("kaustav.d@dotw.com");
			WebElement subcustomer_Confirm_Email = driverqa.findElement(Others.AddSubCustomerConfirmEmail);
			subcustomer_Confirm_Email.clear();
			subcustomer_Confirm_Email.sendKeys("kaustav.d@dotw.com");
			WebElement subcustomer_Login_ID = driverqa.findElement(Others.AddSubCustomerLoginID);
			subcustomer_Login_ID.clear();
			subcustomer_Login_ID.sendKeys("kausdas009");
			WebElement subcustomer_Password= driverqa.findElement(Others.AddSubCustomerPassword);
			subcustomer_Password.clear();
			subcustomer_Password.sendKeys("P@ssw0rd");
			WebElement subcustomer_Confirm_Password= driverqa.findElement(Others.AddSubCustomerConfirmPassword);
			subcustomer_Confirm_Password.clear();
			subcustomer_Confirm_Password.sendKeys("P@ssw0rd");
			test.log(LogStatus.PASS, "Entering subcustomer details");
			logger.info("Entered subcustomer details");
			Thread.sleep(2000);
			obj.Takesnap(driverqa, Config.SnapShotPath() + "/Others/Add_Edit_Sub_Customer/Adding-Sub-Customer-Details2.jpg");
			driverqa.findElement(Others.ConfirmUpdateProfile).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(Others.AddSubCustomerCreatedSucceessfully));
			ExpectedSuccessMSG = "User created successfully.";
			ActualSuccessMSG = driverqa.findElement(Others.AddSubCustomerCreatedSucceessfully).getText();
			Thread.sleep(2000);
			obj.Takesnap(driverqa, Config.SnapShotPath() + "/Others/Add_Edit_Sub_Customer/Sub-Customer-Created.jpg");
			Assert.assertTrue(ActualSuccessMSG.contains(ExpectedSuccessMSG));
			test.log(LogStatus.PASS, "Adding Sub Customer");
			logger.info("Added Sub Customer");
		} 
		
		catch (Throwable e) {
			obj.Takesnap(driverqa, Config.SnapShotPath() + "/Others/Error/Add_Edit_Sub_Customer/Creating-SubCustomer.jpg");
			test.log(LogStatus.FAIL, "Adding Sub Customer");
			errorpath = Config.SnapShotPath() + "/Others/Error/Add_Edit_Sub_Customer/Creating-SubCustomer.jpg";
			logger.info(e.getMessage());
			test.log(LogStatus.FAIL, e.getMessage());
			rep.endTest(test);
			rep.flush();
			Assert.assertTrue(false, e.getMessage());
		}
		
		/* ####### Edit Sub Customer details ######### **/
		
		try {
			test.log(LogStatus.INFO, "Searching Sub Customer");
			logger.info("Searching Sub Customer");
			WebElement SearchLogIN = driverqa.findElement(Others.SearchSubCustLoginID);
			SearchLogIN.clear();
			SearchLogIN.sendKeys("kausdas0010");
			Thread.sleep(2000);
			obj.Takesnap(driverqa, Config.SnapShotPath() + "/Others/Add_Edit_Sub_Customer/Searched-Sub-Customer.jpg");
			driverqa.findElement(Others.SaerchSubcust).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(Others.SubCustEditButton));
			Thread.sleep(2000);
			obj.Takesnap(driverqa, Config.SnapShotPath() + "/Others/Add_Edit_Sub_Customer/Edited-Name.jpg");
			test.log(LogStatus.PASS, "Searching Sub Customer");
			logger.info("Searched Sub Customer");
			test.log(LogStatus.INFO, "Editing Sub Customer details");
			logger.info("Editing Sub Customer details");
			driverqa.findElement(Others.SubCustEditButton).click();
			WebElement subcustomer_firstname = driverqa.findElement(Others.AddSubCustomerFirstName);
			subcustomer_firstname.clear();
			subcustomer_firstname.sendKeys("kdas");
			Thread.sleep(2000);
			obj.Takesnap(driverqa, Config.SnapShotPath() + "/Others/Add_Edit_Sub_Customer/Edit-Successful.jpg");
			driverqa.findElement(Others.ConfirmUpdateProfile).click();
			ExpectedSuccessMSG = "User updated successfully.";
			ActualSuccessMSG = driverqa.findElement(Others.AddSubCustomerUpdatedSucceessfully).getText();
			Assert.assertTrue(ActualSuccessMSG.contains(ExpectedSuccessMSG));
			test.log(LogStatus.PASS, "Editing Sub Customer details");
			logger.info("Edited Sub Customer details");
		} catch (Throwable e) {
			obj.Takesnap(driverqa, Config.SnapShotPath() + "/Others/Error/Add_Edit_Sub_Customer/Edit-Successful.jpg");
			test.log(LogStatus.FAIL, "Editing Sub Customer");
			errorpath = Config.SnapShotPath() + "/Others/Error/Add_Edit_Sub_Customer/Edit-Successful.jpg";
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

