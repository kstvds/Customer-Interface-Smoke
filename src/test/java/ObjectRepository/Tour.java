package ObjectRepository;

import org.openqa.selenium.By;

public class Tour {
	public static final By tour = By.xpath("//*[@id='header']/div[2]/ul[2]/li[3]/a/div/img");
	public static final By tourname = By.xpath("//*[@id='tourId']");
	public static final By tourdate = By.xpath("//*[@id='travelDatePicker']");
	public static final By searchtourbutton = By.xpath("//*[@id='button']");
	public static final By TourdestID = By.xpath("//*[@id='destinationId']");
	public static final By Gettour = By.xpath("//input[@name='booknow']");
	public static final By SelectTour = By.xpath("//*[@class='search_btn' and @name = 'booknow2']");
	public static final By Pickuplocation = By.xpath("//*[@id='pickup']");
	public static final By AfterBookingTourName = By.xpath("//*[@id='bookingDetailsContainer']/div[1]/div[3]/table/tbody/tr[2]/td[2]");
	public static final By AfterBookingTourDate = By.xpath("//*[@id='bookingDetailsContainer']/div[1]/div[5]/table/tbody/tr[1]/td[2]");
	public static final By AfterBookingTourPickUp = By.xpath("//*[@id='bookingDetailsContainer']/div[1]/div[5]/table/tbody/tr[3]/td[2]");
	public static final By BookTourFirstName = By.xpath("//*[@id='firstName']");
	public static final By BookTourLastName = By.xpath("//*[@id='lastName']");
	public static final By BookTourpassengerTitle = By.xpath("//*[@name='title[]']");
	public static final By ContinueTourBook = By.xpath("//*[@id='continueToBook']");
}
