package ObjectRepository;

import org.openqa.selenium.By;

public class Booking {
	public static final By Bookroom = By.xpath("//input[@class='roomBookNowButton']");
	public static final By OnePaxFirstName = By.xpath("//*[@id='pbcpFirstName1_0']");
	public static final By OnePaxlastName = By.xpath("//*[@id='pbcpLastName1_0']");
	public static final By OnePaxTitle = By.xpath("//*[@id='pbcpTitle1_0']");
	public static final By TwoPaxFirstName = By.xpath("//*[@id='pbcpFirstName1_1']");
	public static final By TwoPaxLastName = By.xpath("//*[@id='pbcpLastName1_1']");
	public static final By TwoPaxTitle = By.xpath("//*[@id='pbcpTitle1_1']");
	public static final By PrcdToBookChckBox = By.xpath("//*[@id='rateNoteCheck']");
	public static final By ConfirmBook = By.xpath("//*[@id='pbcpConfirmBooking']");
	public static final By Closeconfirmbook = By.xpath("//button[contains(text(),'Close')]");
	public static final By ItnCart = By.xpath("//a[@class='shoppingcart_btn shoppingCartBtn']");
	public static final By AfterSaveHotel = By.xpath("//*[@id='shoppingCartList']/table/tbody/tr[2]/td[2]");
	public static final By DeletefrmITN = By.xpath("//button[contains(text(),'Clear')]");
	public static final By CloseCart = By.xpath("//button[contains(text(),'Close')]");
	public static final By ConfirmDeletefrmITN = By.xpath("//button[contains(text(),'Yes')]");
	public static final By AfterSaveHotel2 = By.xpath("//*[@id='shoppingCartList']/table/tbody/tr[3]/td[2]");
	public static final By ClickDeadline = By.xpath("//span[contains(text(),'Click to view deadline')]");
	public static final By ThirdPaxFirstName = By.xpath("//*[@id='pbcpFirstName1_2']");
	public static final By ThirdPaxLastName = By.xpath("//*[@id='pbcpLastName1_2']");
	public static final By ThirdPaxTitle = By.xpath("//*[@id='pbcpTitle1_2']");
	public static final By BookingStatusPrepay = By.xpath("//*[contains(text(),'Confirmed')]");
	public static final By noOfAdultsPrepay = By.xpath("//*[@id='bookingDetailsContainer']/div[1]/div[5]/table/tbody/tr[3]/td[2]");
	public static final By Invoice = By.xpath("//*[@id='booking_proforma_invoice']");
	public static final By ProccedToBook = By.xpath("//button[contains(text(),'Confirm booking')]");
	public static final By ViewBooking = By.xpath("//*[@id='viewButton']");
	public static final By BookingCost = By.xpath("//*[@id='creditCardDetailsForm']/div[1]/div/input");
	public static final By CreditCost = By.xpath("//*[@name='useCredit']");
	public static final By AcceptpaymentCredit = By.xpath("//*[@id='creditCardDetailsForm']/div[6]/label/input");
	public static final By BookMultiRoom = By.xpath("//input[@value='Book Now']");
	public static final By Unabletoblockmultiroom = By.xpath("//*[@id='modalBody']/div/ul/li[1]");
	public static final By ExtraMealTab = By.xpath("//*[@id='pbcpRoom1']/div[3]/table/tbody/tr/td[7]/div/div/span[2]");
	public static final By MealGuestType = By.xpath("//*[starts-with(@id,'guestType_1')]"); 
	public static final By MealType = By.xpath("//*[starts-with(@id,'mealType_1')]");
	public static final By MealName = By.xpath("//*[starts-with(@id,'meal_1')]");
	public static final By MealGuests = By.xpath("//*[starts-with(@id,'guestNbr_1')]");
	public static final By SelectDate = By.xpath("//*[starts-with(@id,'dates_')]");
	public static final By BookingRefernceNo = By.xpath("//*[@id='bookingDetailsContainer']/div[1]/div[2]/div[1]/h4");
	public static final By FindBooking = By.xpath("//*[@id='right_panel-menu']/div[1]/ul[1]/li[2]/a");
	public static final By SearchRefernceNumber = By.xpath("//*[@name='referenceNumber']");
	public static final By ServiceType= By.xpath("//select[@class='form-control' and @name='productType']");
	public static final By SubmitAccomSearch = By.xpath("//*[@id='submitPartButton']");
	public static final By BookingStatus = By.xpath("//select[@class='form-control' and @name='bookingType']");
	public static final By FindBookingWithFirstName = By.xpath("//*[@id='bookingSearchForm']/table/tbody/tr[7]/td/table/tbody/tr/td[2]/div/input");
	public static final By FindBookingWithLastName = By.xpath("//*[@id='bookingSearchForm']/table/tbody/tr[7]/td/table/tbody/tr/td[5]/input");
	public static final By AfterBookingName = By.xpath("//*[@id='bookingDetailsContainer']/div[1]/div[4]/table/tbody/tr[2]/td[2]");
	public static final By PendingPayment = By.xpath("//*[@id='right_panel-menu']/div[1]/ul[1]/li[3]/a");
	public static final By RoomPriceChange = By.xpath("//li[contains(text(),'We are unable to proceed as the offer price has been revised.  Please select the desired room and resubmit your request.')]");
	public static final By ScrollElement = By.xpath("//*[@id='collapsiblePanelTabChain']");
}
