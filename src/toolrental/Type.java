package toolrental;

public class Type {

	public double dailyCharge;
	public boolean weekday;
	public boolean weekend;
	public boolean holiday;
	/**
	 * @param dailyCharge -  the daily charge for this type of tool
	 * @param weekday - boolean value of if the tool is charged for weekday rentals
	 * @param weekend - boolean value of if the tool is charged for weekend rentals
	 * @param holiday - boolean value of if the tool is charged when rented on holidays.
	 */
	public Type(double dailyCharge, boolean weekday, boolean weekend, boolean holiday) {
		this.dailyCharge= dailyCharge;
		this.weekday = weekday;
		this.weekend = weekend;
		this.holiday = holiday;
	}
	public double getDailyCharge() {
		return this.dailyCharge;
	}
	
	public boolean getWeekday() {
		return this.weekday;
	}
	
	public boolean getWeekend() {
		return this.weekend;
	}
	
	public boolean getHoliday() {
		return this.holiday;
	}
}
