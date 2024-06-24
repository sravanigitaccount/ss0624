package toolrental;

import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.junit.jupiter.api.Test;
public class ToolRentalPOCTest {
    @Test
    void testCheckout() {
        System.out.println("\nTestcase 1");
        ToolRentalPOC testPOS = new ToolRentalPOC();
        String pattern = "MM/dd/yy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        try {
            RentalAgreement test1 = testPOS.checkout("JAKR", 5, simpleDateFormat.parse("09/03/15"), 101);
        } catch (ParseException e) {
            fail(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            assertEquals(e.getMessage(), "Discount percent must be between 0 and 100.");
        }

        System.out.println("\nTestcase 2");
        try {
            RentalAgreement test2 = testPOS.checkout("LADW", 3, simpleDateFormat.parse("07/02/20"), 10);
            System.out.println(test2);
            assertEquals(test2.getDueDate(),simpleDateFormat.parse("07/05/20"));
            assertEquals(test2.getDailyRentalCharge(), 1.99);
            assertEquals(test2.getChargeDays(), 2);
            assertEquals(test2.getPreDiscountCharge(),3.98, 0.001);
            assertEquals(test2.getDiscountAmount(),.40, 0.001);
            assertEquals(test2.getFinalCharge(),3.58, 0.001);
        } catch (ParseException e) {
            fail(e.getMessage());
        } catch (Exception e) {

            fail(e.getMessage());
        }

        System.out.println("\nTestcase 3");
        try {
            RentalAgreement test3 = testPOS.checkout("CHNS", 5, simpleDateFormat.parse("07/02/15"), 25);
            System.out.println(test3);
            assertEquals(test3.getDueDate(),simpleDateFormat.parse("07/07/15"));
            assertEquals(test3.getDailyRentalCharge(), 1.49);
            assertEquals(test3.getChargeDays(), 3);
            assertEquals(test3.getPreDiscountCharge(),4.47, 0.001);
            assertEquals(test3.getDiscountAmount(),1.12, 0.001);
            assertEquals(test3.getFinalCharge(),3.35, 0.001);
        } catch (ParseException e) {
            fail(e.getMessage());
        } catch (Exception e) {
            fail(e.getMessage());
        }

        System.out.println("\nTestcase 4");
        try {
            RentalAgreement test4 = testPOS.checkout("JAKD", 6, simpleDateFormat.parse("09/03/15"), 0);
            System.out.println(test4);
            assertEquals(test4.getDueDate(),simpleDateFormat.parse("09/09/15"));
            assertEquals(test4.getDailyRentalCharge(), 2.99);
            assertEquals(test4.getChargeDays(), 3);
            assertEquals(test4.getPreDiscountCharge(), 8.97, 0.001);
            assertEquals(test4.getDiscountAmount(), 0, 0.001);
            assertEquals(test4.getFinalCharge(), 8.97, 0.001);
        } catch (ParseException e) {
            fail(e.getMessage());
        } catch (Exception e) {
            fail(e.getMessage());
        }

        System.out.println("\nTestcase 5");
        try {
            RentalAgreement test5 = testPOS.checkout("JAKR", 9, simpleDateFormat.parse("07/02/15"), 0);
            System.out.println(test5);
            assertEquals(test5.getDueDate(),simpleDateFormat.parse("07/11/15"));
            assertEquals(test5.getDailyRentalCharge(), 2.99);
            assertEquals(test5.getChargeDays(), 5);
            assertEquals(test5.getPreDiscountCharge(), 14.95, 0.001);
            assertEquals(test5.getDiscountAmount(), 0, 0.001);
            assertEquals(test5.getFinalCharge(), 14.95, 0.001);
        } catch (ParseException e) {
            fail(e.getMessage());
        } catch (Exception e) {
            fail(e.getMessage());
        }

        System.out.println("\nTestcase 6");
        try {
            RentalAgreement test6 = testPOS.checkout("JAKR", 4, simpleDateFormat.parse("07/02/20"), 50);
            System.out.println(test6);
            assertEquals(test6.getDueDate(),simpleDateFormat.parse("07/6/20"));
            assertEquals(test6.getDailyRentalCharge(), 2.99);
            assertEquals(test6.getChargeDays(), 1);
            assertEquals(test6.getPreDiscountCharge(), 2.99, 0.001);
            assertEquals(test6.getDiscountAmount(), 1.50, 0.001);
            assertEquals(test6.getFinalCharge(), 1.49, 0.001);
        } catch (ParseException e) {
            fail(e.getMessage());
        } catch (Exception e) {
            fail(e.getMessage());
        }
        System.out.println("\nTestcase 7");
        try {
            RentalAgreement test7 = testPOS.checkout("JAKR", 3, simpleDateFormat.parse("07/02/21"), 50);
            System.out.println(test7);
            assertEquals(test7.getDueDate(),simpleDateFormat.parse("07/5/21"));
            assertEquals(test7.getDailyRentalCharge(), 2.99);
            assertEquals(test7.getChargeDays(), 0);
            assertEquals(test7.getPreDiscountCharge(), 0, 0.001);
            assertEquals(test7.getDiscountAmount(), 0, 0.001);
            assertEquals(test7.getFinalCharge(), 0, 0.001);
        } catch (ParseException e) {
            fail(e.getMessage());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }


    @Test
    void testIsDateHoliday() {
        ToolRentalPOC pos = new ToolRentalPOC();
        String pattern = "MM/dd/yy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(simpleDateFormat.parse("7/03/20"));
        } catch (ParseException e) {
            fail("date parse error");
        }
        if (!pos.isDateHoliday(calendar)) {
            fail("observed today instead of saturday");
        }
        try {
            calendar.setTime(simpleDateFormat.parse("7/04/20"));
        } catch (ParseException e) {
            fail("date parse error");
        }
        if (pos.isDateHoliday(calendar)) {
            fail("Observed on friday instead");
        }
        try {
            calendar.setTime(simpleDateFormat.parse("9/07/20"));
        } catch (ParseException e) {
            fail("date parse error");
        }
        if (!pos.isDateHoliday(calendar)) {
            fail("Labor day is holiday");
        }
        try {
            calendar.setTime(simpleDateFormat.parse("9/03/20"));
        } catch (ParseException e) {
            fail("date parse error");
        }
        if (pos.isDateHoliday(calendar)) {
            fail("Not a holiday");
        }
    }

    @Test
    void testIsDateWeekend() {
        ToolRentalPOC pos = new ToolRentalPOC();
        String pattern = "MM/dd/yy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(simpleDateFormat.parse("10/05/20"));
        } catch (ParseException e) {
            fail("date parse error");
        }
        if (pos.isDateWeekend(calendar)) {
            fail("Monday 10-5 is not a weekend");
        }
        try {
            calendar.setTime(simpleDateFormat.parse("10/04/20"));
        } catch (ParseException e) {
            fail("date parse error");
        }
        if (!pos.isDateWeekend(calendar)) {
            fail("Sunday 10-4 is a weekend");
        }

    }

}
