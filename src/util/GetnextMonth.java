package util;

import java.util.GregorianCalendar;

public class GetnextMonth {

	 public static java.util.Date nextMonths(java.util.Date date,int addmonths) {

		GregorianCalendar cal = new GregorianCalendar();

		cal.setTime(date);

		cal.add(GregorianCalendar.MONTH, addmonths); // 在月份上加

		return cal.getTime();

	}

}
