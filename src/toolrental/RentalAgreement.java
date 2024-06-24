package toolrental;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RentalAgreement {

	private String toolCode;
	private String toolType;
	private String toolBrand;
	private int rentalDays;
	private Date checkOutDate;
	private Date dueDate;
	private double dailyRentalCharge;
	private int chargeDays;
	private double preDiscountCharge;
	private int discountPercent;
	private double discountAmount;
	private double finalCharge;
	
	/**
	 * @param toolCode - code of tool that was rented
	 * @param toolType - type of tool that was rented
	 * @param toolBrand - brand of tool that was rented
	 * @param rentalDays - length of rental in days
	 * @param checkOutDate - date rental occurred
	 * @param dueDate - date tool is to be returned
	 * @param dailyRentalCharge - daily rate for rental of tool
	 * @param chargeDays - number of days being charged for in rental period
	 * @param discountPercent - discount to be applied to rental.
	 */
	public RentalAgreement(String toolCode, String toolType, String toolBrand, int rentalDays, Date checkOutDate, Date dueDate, double dailyRentalCharge, int chargeDays, int discountPercent) {
		this.toolCode = toolCode;
		this.toolType = toolType;
		this.toolBrand = toolBrand;
		this.rentalDays = rentalDays;
		this.checkOutDate = checkOutDate;
		this.dueDate = dueDate;
		this.dailyRentalCharge = dailyRentalCharge;
		this.chargeDays = chargeDays;
		this.discountPercent = discountPercent;
		this.preDiscountCharge = this.dailyRentalCharge * chargeDays;
		this.discountAmount = Math.round(this.preDiscountCharge * this.discountPercent) / 100.0; //constant 100 is to convert to percent from whole number.
		this.finalCharge = (this.preDiscountCharge - this.discountAmount);
	}
	/**
	 * generate string with all class elements formated for printing.
	 */
	public String toString() {
		NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(); //set up currency formating. Country can be changed and all prints update.
		String pattern = "MM/dd/yy";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern); // set up date formating 
		return "Tool code: "+ this.toolCode +
				"\nTool type: " + this.toolType + 
				"\nTool brand: " + this.toolBrand +
				"\nRental days: " + this.rentalDays  +
				"\nCheck out date: " + simpleDateFormat.format(this.checkOutDate) + 
				"\nDue date: " + simpleDateFormat.format(this.dueDate) + 
				"\nDaily rental charge: " + currencyFormat.format(this.dailyRentalCharge) + 
				"\nCharge days: " + this.chargeDays +
				"\nPre-discount charge: " + currencyFormat.format(this.preDiscountCharge) +
				"\nDiscount percent:  " + this.discountPercent +"%"  +
				"\nDiscount amount:  " +  currencyFormat.format(this.discountAmount) +
				"\nFinal charge: " +  currencyFormat.format(this.finalCharge);
	}

	/**
	 * print class contents to console
	 */
	public void printAgreement() {
		System.out.println(this.toString());
	}
	
	//Get functions for use in jUnit tests.
	public String getToolCode() {
		return toolCode;
	}
	public String getToolType() {
		return toolType;
	}
	public String getToolBrand() {
		return toolBrand;
	}
	public int getRentalDays() {
		return rentalDays;
	}
	public Date getCheckOutDate() {
		return checkOutDate;
	}
	public Date getDueDate() {
		return dueDate;
	}
	public double getDailyRentalCharge() {
		return dailyRentalCharge;
	}
	public int getChargeDays() {
		return chargeDays;
	}
	public double getPreDiscountCharge() {
		return preDiscountCharge;
	}
	public int getDiscountPercent() {
		return discountPercent;
	}
	public double getDiscountAmount() {
		return discountAmount;
	}
	public double getFinalCharge() {
		return finalCharge;
	}
	
}
