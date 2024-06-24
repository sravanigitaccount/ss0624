package toolrental;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Hashtable;
import java.util.Calendar;
import java.util.Date;


public class ToolRentalPOC {
    private Hashtable<String, Tool> tools;
    private Hashtable<String, Type> types;
    public ToolRentalPOC() {
        this.tools = new Hashtable<String, Tool>();
        this.loadTools(); // generate tool database
        this.types = new Hashtable<String, Type>();
        this.loadTypes(); //generate tool type database
    }

    /**
     * @param args - only for internal testing
     */
    public static void main(String[] args) {
        ToolRentalPOC testPOS = new ToolRentalPOC();
        String pattern = "MM/dd/yy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        try {
            System.out.print(testPOS.checkout("JAKR", 50000, simpleDateFormat.parse("09/03/15"), 0));
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    /**
     * @param toolCode - identifier code for the tool being rented.
     * @param rentalDays - number of days the tool will be rented for.
     * @param checkOutDate - Date the tool was rented
     * @param discountPercent - whole number percentage discount to be applied 0-100
     * @return - new instance of rental agreement class with all transaction data.
     * @throws Exception - possible output when input values are improper
     */
    public RentalAgreement checkout(String toolCode,  int rentalDays, Date checkOutDate, int discountPercent) throws Exception{
        if (rentalDays < 1) {
            throw new Exception("A tool can not be rented for less than 1 day.");
        }
        if (100 < discountPercent || discountPercent< 0) {
            throw new Exception("Discount percent must be between 0 and 100.");
        }
        Tool tool = this.tools.get(toolCode);
        if (tool == null) { //if database lookup failed raise exception
            throw new Exception("Tool Code is not Valid");
        }
        Type type = this.types.get(tool.getType());
        if (type == null) { //shouldn't happen but raise exception in event database has error.
            throw new Exception("Tool type \""+tool.getType()+"\" was not found.");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(checkOutDate);
        int chargeDays = countChargeDays(type, calendar, rentalDays);
        //System.out.println(checkOutDate);
        //System.out.println(calendar.getTime());
        return new RentalAgreement(toolCode, tool.getType(), tool.getBrand(), rentalDays, checkOutDate, calendar.getTime(), type.dailyCharge, chargeDays, discountPercent);
    }

    /**
     * Initializes tools dictionary
     */
    public void loadTools() {
        //tool load could be updated to load from a file or database using a loop
        Tool tool = new Tool("Ladder", "Werner","LADW"); // replaceable with a loop based load in the future
        this.tools.put(tool.getCode(), tool);
        tool = new Tool("Chainsaw", "Stihl","CHNS");
        this.tools.put(tool.getCode(), tool);
        tool = new Tool("Jackhammer", "Ridgid","JAKR");
        this.tools.put(tool.getCode(), tool);
        tool = new Tool("Jackhammer", "DeWalt","JAKD");
        this.tools.put(tool.getCode(), tool);
    }

    /**
     * Initializes toolTypes dictionary
     */
    public void loadTypes() {
        Type type = new Type(1.99, true, true, false); // as with tool load this could be looped to read from file or database
        this.types.put("Ladder", type);
        type = new Type(1.49, true, false, true);
        this.types.put("Chainsaw", type);
        type = new Type(2.99, true, false, false);
        this.types.put("Jackhammer", type); // type key might be more robust as a enum
    }

    /**
     * @param toolType - The type of tool being charged for
     * @param calendar -start of charge period date. returns as end of charge period date. (sideffect)
     * @param rentalDays - Number of days the rental is for
     * @return
     */
    public int countChargeDays(Type toolType, Calendar calendar, int rentalDays) {
        int count = 0;
        boolean chargeDay = true;
        for (int day = 0; day<rentalDays; day++) {
            chargeDay=true;
            // add day first as billing starts day after rental.
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            if (!toolType.getHoliday()) { //if not charging on holidays see if day is holiday
                chargeDay=chargeDay&&(!this.isDateHoliday(calendar)); //if holiday set chargeDay false
            }
            if (!toolType.getWeekday()) { // if not charging for weekdays see if day is weekday
                chargeDay=chargeDay&&(this.isDateWeekend(calendar)); //if day weekday set chargeDay false
            }
            if (!toolType.getWeekend()) {
                chargeDay=chargeDay&&(!this.isDateWeekend(calendar));
            }
            if (chargeDay) {
                count++;
            }
        }
        return count;
    }

    /**
     * @param calendar - calendar class instance referencing the day being tested.
     * @return - boolean value if the day is a holiday or not.
     */
    public boolean isDateHoliday(Calendar calendar) {
        if (calendar.get(Calendar.MONTH)== Calendar.JULY) {
            if (!this.isDateWeekend(calendar)) {
                if (calendar.get(Calendar.DAY_OF_MONTH) ==4) {
                    return true;
                }
                if (calendar.get(Calendar.DAY_OF_MONTH) ==5 && calendar.get(Calendar.DAY_OF_WEEK)== Calendar.MONDAY) {
                    return true;
                }
                if (calendar.get(Calendar.DAY_OF_MONTH) ==3 && calendar.get(Calendar.DAY_OF_WEEK)== Calendar.FRIDAY) {
                    return true;
                }
            }
        }
        else if (calendar.get(Calendar.MONTH)== Calendar.SEPTEMBER && calendar.get(Calendar.DAY_OF_WEEK)== Calendar.MONDAY && calendar.get(Calendar.DAY_OF_MONTH) < 8 ) {
            return true;
        }
        return false;
    }

    /**
     * @param calendar - calendar class instance referencing the day being tested.
     * @return - boolean value if the day is a weekend.
     */
    public boolean isDateWeekend(Calendar calendar) {
        return (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
                calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY);
    }

}
