package ObjectRepository;

import org.openqa.selenium.By;

public class Transfer {
	public static final By transfer = By.xpath("//*[@id='header']/div[2]/ul[2]/li[2]/a");
	public static final By transfername = By.xpath("//*[@id='transferId']");
	public static final By ArrivingArlineDetails = By.xpath("//*[@id='A_Airline']");
	public static final By ArrivingFrom = By.xpath("//*[@id='Other_A_City_typeahead']");
	public static final By FlightNo = By.xpath("//*[@id='flightNo']");
	public static final By pickUp = By.xpath("//*[@id='pickup']");
	public static final By DropOff = By.xpath("//*[@id='dropoff']");
	public static final By TransferFirstName = By.xpath("//*[@id='firstName1']");
	public static final By TransferLastName = By.xpath("//*[@id='lastName1']");
	public static final By TransferPassemgerTitle = By.xpath("//select[@name='title[1]']");
	public static final By TransferPrcdToBookChckBox = By.xpath("//*[@id='rateNoteCheck']");
	public static final By AfterBookingTransferDate = By.xpath("//*[@id='bookingDetailsContainer']/div[1]/div[5]/table/tbody/tr[1]/td[2]");
	public static final By AfterBookingTransferPickUp = By.xpath("//*[@id='bookingDetailsContainer']/div[1]/div[5]/table/tbody/tr[4]/td[2]");
	public static final By AfterBookingTransferName = By.xpath("//*[@id='bookingDetailsContainer']/div[1]/div[3]/table/tbody/tr[2]/td[2]");
}
