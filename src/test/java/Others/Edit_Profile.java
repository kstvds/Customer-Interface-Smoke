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
import ObjectRepository.Others;
import Utility.Configuration;
import lib.DriverAndObjectDetails;
import lib.ExcelDataConfig;
import lib.ExtentManager;
import lib.Takescreenshot;
import lib.DriverAndObjectDetails.DriverName;

public class Edit_Profile {
	public WebDriver driverqa;
	ExtentTest test;
	String errorpath;
	String Roomtype;
	String ExpectedSuccessMSG;
	String ActualSuccessMSG;
	ExcelDataConfig excel;
	Configuration Config = new Configuration();
	Takescreenshot obj = new Takescreenshot();
	ExtentReports rep = ExtentManager.getInstance();
	LoginPage login = new LoginPage();
	Logger logger = Logger.getLogger("Edit_Profile");

	/* ####### Passing browser as parameters in test ######### **/

	@Test
	@Parameters({ "browsername" })
	public void PendingPayments(String browsername) throws Exception {
		test = rep.startTest("Pending Payments");
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
			obj.Takesnap(driverqa, Config.SnapShotPath() + "/Others/Edit_Profile/Log-In.jpg");

		} catch (Throwable e) {

			obj.Takesnap(driverqa, Config.SnapShotPath() + "/Others/Error/Edit_Profile/Log-In.jpg");
			test.log(LogStatus.FAIL, "Login");
			errorpath = Config.SnapShotPath() + "/Others/Error/Edit_Profile/Log-In.jpg";
			logger.info(e.getMessage());
			test.log(LogStatus.FAIL, e.getMessage());
			rep.endTest(test);
			rep.flush();
			Assert.assertTrue(false, e.getMessage());

		}

		/* ####### Editing User Profile ######### **/

		test.log(LogStatus.INFO, "Editing User Profile");
		logger.info("Editing User Profile");
		try {
			test.log(LogStatus.INFO, "Navigating to Edit Profile Page");
			logger.info("Navigating to Edit Profile Page");
			driverqa.findElement(Others.MyAccount).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(Others.EditProfile));
			driverqa.findElement(Others.EditProfile).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(Others.EditProfileChangeName));
			Thread.sleep(2000);
			obj.Takesnap(driverqa, Config.SnapShotPath() + "/Others/Edit_Profile/Edit-User-Profile-Page.jpg");

			test.log(LogStatus.PASS, "Navigating to Edit Profile Page");
			logger.info("Navigated to Edit Profile Page");
			test.log(LogStatus.INFO, "Editing Name");
			logger.info("Editing Name");
			WebElement Name = driverqa.findElement(Others.EditProfileChangeName);
			Name.clear();
			Name.sendKeys("Anindya");
			Thread.sleep(2000);
			obj.Takesnap(driverqa, Config.SnapShotPath() + "/Others/Edit_Profile/Edited-Name.jpg");
			driverqa.findElement(Others.ConfirmUpdateProfile).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(Others.ProfileUpdateMsg));
			Thread.sleep(2000);
			obj.Takesnap(driverqa, Config.SnapShotPath() + "/Others/Edit_Profile/Edit-Successful.jpg");
			ActualSuccessMSG = driverqa.findElement(Others.ProfileUpdateMsg).getText();
			ExpectedSuccessMSG = "Profile successfully updated.";
			Assert.assertTrue(ActualSuccessMSG.contains(ExpectedSuccessMSG));
			test.log(LogStatus.PASS, "Editing User Profile");
			logger.info("Edited User Profile");
		} catch (Throwable e) {
			obj.Takesnap(driverqa, Config.SnapShotPath() + "/Others/Error/Edit_Profile/Edit-User-Profile-Page.jpg");
			test.log(LogStatus.FAIL, "Edit User profile");
			errorpath = Config.SnapShotPath() + "/Others/Error/Edit_Profile/Edit-User-Profile-Page.jpg";
			logger.info(e.getMessage());
			test.log(LogStatus.FAIL, e.getMessage());
			rep.endTest(test);
			rep.flush();
			Assert.assertTrue(false, e.getMessage());
		}

		/* ####### Editing Company Profile ######### **/

		try {
			test.log(LogStatus.INFO, "Editing Company Profile");
			logger.info("Editing Company Profile");
			test.log(LogStatus.INFO, "Navigating to Edit Company Profile Page");
			logger.info("Navigating to Edit Company Profile Page");
			driverqa.findElement(Others.MyAccount).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(Others.EditCompanyProfile));
			driverqa.findElement(Others.EditCompanyProfile).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(Others.EditCompanyAdddress));
			Thread.sleep(2000);
			obj.Takesnap(driverqa, Config.SnapShotPath() + "/Others/Edit_Profile/Edit-Company-Profile-Page.jpg");

			test.log(LogStatus.PASS, "Navigating to Edit Profile Page");
			logger.info("Navigated to Edit Profile Page");
			test.log(LogStatus.INFO, "Editing Name");
			logger.info("Editing Name");
			WebElement Name = driverqa.findElement(Others.EditCompanyAdddress);
			Name.clear();
			Name.sendKeys("KolkataABC123");
			Thread.sleep(2000);
			obj.Takesnap(driverqa, Config.SnapShotPath() + "/Others/Edit_Profile/Edited-Address.jpg");
			driverqa.findElement(Others.ConfirmUpdateProfile).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(Others.ProfileUpdateMsg));
			Thread.sleep(2000);
			obj.Takesnap(driverqa, Config.SnapShotPath() + "/Others/Edit_Profile/Edit-Successful-Customer-Profile.jpg");
			ActualSuccessMSG = driverqa.findElement(Others.ProfileUpdateMsg).getText();
			ExpectedSuccessMSG = "Profile successfully updated.";
			Assert.assertTrue(ActualSuccessMSG.contains(ExpectedSuccessMSG));
			test.log(LogStatus.PASS, "Editing Company Profile");
			logger.info("Edited Company Profile");
		} catch (Throwable e) {
			obj.Takesnap(driverqa, Config.SnapShotPath() + "/Others/Error/Edit_Profile/Edit-Company-Profile-Page.jpg");
			test.log(LogStatus.FAIL, "Edit Company profile");
			errorpath = Config.SnapShotPath() + "/Others/Error/Edit_Profile/Edit-Company-Profile-Page.jpg";
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
