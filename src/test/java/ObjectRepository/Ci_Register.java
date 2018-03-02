package ObjectRepository;

import org.openqa.selenium.By;

public class Ci_Register {
	
		public static final By Register_now = By.xpath("//*[@id='customer']/form/table/tbody/tr[2]/td[2]/a");
		
		public static final By Company_name = By.className("textfield");
		
		public static final By Company_Address = By.xpath("//*[@id='companyAddress']");
		
		public static final By Postal_Code=By.xpath(".//*[@id='companyZipCode']");
		
		public static final By Company_country=By.xpath(".//*[@id='companyCountry']");
		
		public static final By Company_city=By.id("companyCity");
		
		public static final By Preffered_Payment_Method=By.xpath("//*[@id='paymentType']");
		
		public static final By Preffered_Currency= By.xpath("//*[@id='currencyCreditGross']");
		
		public static final By Preffered_Currency_Credit_Card= By.xpath("//*[@id='currencyCreditCard']");
		
		public static final By Preffered_Currency_Prepay = By.xpath("//*[@id='currencyCreditCash']");
		
		public static final By Telephone_City_Code=By.xpath("//input[@name='PrefTel']");
		
		public static final By Telephone_Number=By.xpath("//input[@name='Tel']");
		
		public static final By Salutation=By.xpath("//*[@id='title']");
		
		public static final By FirstName=By.xpath("//*[@id='firstName']");
		
		public static final By LastName=By.id("lastName");
		
		public static final By Email_Address=By.id("emailAddress");
		
		public static final By Confirm_Email_Address=By.id("emailAddress_confirmation");
		
		public static final By Login_Id=By.id("loginId");
		
		public static final By Password=By.id("password");
		
		public static final By Confirm_Password=By.id("password_confirmation");
		
		public static final By Terms_of_use=By.id("termsAndCond");
		
		public static final By Register_button=By.xpath("//*[@id='registerForm']/div[2]/input");
		
		public static final By accept_continue=By.xpath("//*[@id='acceptAndContinue']");
		
		public static final By continue1=By.xpath("//*[@id='confirmForm']/div[2]/input");

		public static final By thank_you=By.xpath("//*[@id='bg-pattern']/div/div[3]/p[1]");

		public static final By FinanceEmail = By.xpath("//*[@id='CFDEmail']");
		
		public static final By customers=By.xpath("//*[@id='MenuBar_0']/div[2]/ul[1]/li[7]/a");
		
		 public static final By new_cust=By.xpath(".//*[@id='MenuBar_143']/div[2]/ul[1]/li[1]/a");
		 
		 public static final By simple_filter=By.xpath("//*[@id='AdvancedFilters']");
		 
		   public static final By company_name=By.xpath(" .//*[@id='companyName']");

		   public static final By filter_cus=By.xpath(".//*[@id='filter-button']");
		   
		   public static final By edit_newcust=By.xpath(".//*[@id='mainContainer']/form/div[1]/div/table/tbody/tr/td[10]/a[1]");
		   
		   public static final By active_newcust=By.xpath(".//*[@id='setup']/div[1]/div/div[1]/div[2]/div/div/span[2]");
		   
		   public static final By select_manager=By.xpath(".//*[@id='AccountManager']");
		   
		   public static final By update_newcust=By.xpath(" .//*[@id='myForm']/div[2]/div/button");
		
		
	
		
		
		
}
