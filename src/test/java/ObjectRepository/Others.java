package ObjectRepository;

import org.openqa.selenium.By;

public class Others {
	public static final By settings = By.xpath(".//*[@id='MenuBar_0']/div[2]/ul[1]/li[3]/a");
    public static final By Framework = By.name("//a[@class='dropdown-toggle'][@data-toggle='dropdown']//following-sibling::b");
    public static final By BookingChannelSetup = By.xpath("//a[contains(text(),'Booking Channels Setup')]");
    public static final By SupplierChannel = By.xpath("//*[@id='supplier_1807']/td[10]/a");
  
    public static final By SetFlag = By.xpath("//*[contains(text(),'Disable Multiple Room Bookings Within CXL')]//following-sibling::div//following-sibling::div[1]");
    public static final By Homebutton = By.xpath("//*[@id='home-button']");
    public static final By Updatechannel = By.xpath("//button[@type='submit']");
    public static final By Unabletobook = By.xpath("//*[contains(text(),'Unable to block all rooms for this booking')]");
    public static final By BookingDocuments = By.xpath("//*[@id='booking_history']");
    public static final By BookingDocumentStatus = By.xpath("//span[@class='label label-success']");
    public static final By BookingDocumentCheckin = By.xpath("//*[@id='modalBody']/div[1]/div/table/tbody/tr[15]/td[2]");
    public static final By BookingDocumentCheckOut = By.xpath("//*[@id='modalBody']/div[1]/div/table/tbody/tr[16]/td[2]");
    public static final By BookingDocumentPassengerName = By.xpath("//*[@id='modalBody']/div[1]/div/table/tbody/tr[12]/td[2]");
    public static final By BookingDocumentNoOfAdults = By.xpath("//*[@id='modalBody']/div[1]/div/table/tbody/tr[18]/td[2]");
    public static final By BookingProforma = By.xpath("//*[@id='booking_proforma_invoice']");
    public static final By BookingProformaHotelName = By.xpath("//*[@id='availableForPrint']/tbody/tr[3]/td/table/tbody/tr[1]/td[2]");
    public static final By BookingProformaDate = By.xpath("//*[@id='availableForPrint']/tbody/tr[3]/td/table/tbody/tr[1]/td[4]");
    public static final By MyAccount = By.xpath("//*[@id='header']/div[2]/ul[1]/li[5]/div/button");
    public static final By EditProfile = By.xpath("//*[@id='header']/div[2]/ul[1]/li[5]/div/ul/li[10]/a");
    public static final By EditProfileChangeName = By.xpath("//*[@id='LastName']");
    public static final By ConfirmUpdateProfile = By.xpath("//*[@id='button3']");
    public static final By ProfileUpdateMsg = By.xpath("//*[contains(text(),'Profile successfully updated.')]");
    public static final By EditCompanyProfile = By.xpath("//*[@id='header']/div[2]/ul[1]/li[5]/div/ul/li[11]/a");
    public static final By EditCompanyAdddress = By.xpath("//*[@id='Address']");
    public static final By UserManagement = By.xpath("//*[@id='right_panel-menu']/div[1]/ul[3]/li[4]/a");
    public static final By AddSubCustomer = By.xpath("//*[contains(text(),'Add New User')]");
    public static final By AddSubCustomerSalutation = By.xpath("//*[@name='Salutation']");
    public static final By AddSubCustomerFirstName = By.xpath("//*[@name='FirstName']");
    public static final By AddSubCustomerLastName = By.xpath("//*[@name='LastName']");
    public static final By AddSubCustomerPrefixTelephone = By.xpath("//*[@id='PrefTel']");
    public static final By AddSubCustomerTelephoneno = By.xpath("//*[@id='Tel']");
    public static final By AddSubCustomerEmail = By.xpath("//*[@id='Email']");
    public static final By AddSubCustomerConfirmEmail = By.xpath("//*[@id='Email_confirmation']");
    public static final By AddSubCustomerLoginID = By.xpath("//*[@name='Login']");
    public static final By AddSubCustomerPassword = By.xpath("//*[@id='Password']");
    public static final By AddSubCustomerConfirmPassword = By.xpath("//*[@id='Password_confirmation']");
    public static final By AddSubCustomerCreatedSucceessfully = By.xpath("//*[contains(text(),'User created successfully.')]");
    public static final By SearchSubCustLoginID = By.xpath("//*[@name='LoginID']");
    public static final By SaerchSubcust = By.xpath("//*[@value='Search Users']");
    public static final By SubCustEditButton = By.xpath("//*[@class='edit']");
    public static final By AddSubCustomerUpdatedSucceessfully = By.xpath("//*[contains(text(),'User updated successfully.')]");
}
