public class CalendarPrinter {

	private final static String[] DAYS_OF_WEEK = { "MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN" };
	private final static String[] MONTHS_OF_YEAR = { "JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP",
			"OCT", "NOV", "DEC" };

	public static int getCentury(String year) {

		int theYear = Math.floorDiv(Integer.parseInt(year), 100);
		return theYear;
	}

	public static int getYearWithinCentury(String year) {
		int theCalc = Integer.parseInt(year) % 100;

		return theCalc;
	}
    
	public static boolean getIsLeapYear(String yearString) {
		// Note implementation tips in Appendix I below.
		int calcYear = Integer.parseInt(yearString);
		if (calcYear % 4 != 0) {
			return false;
		} else if (calcYear % 100 != 0) {
			return true;
		} else if (calcYear % 400 != 0) {
			return false;
		}

		return true;
	}

	public static int getMonthIndex(String month) {
		month = month.toUpperCase().substring(0, 3);
		int index = -1;
		for (int i = 0; i < MONTHS_OF_YEAR.length; i++) {
			if (MONTHS_OF_YEAR[i].equals(month)) {
				index = i;
			}
		}

		return index;

	}
	public static int getNumberOfDaysInMonth(String month, String year) {
		int theMonth = getMonthIndex(month);

		if (getIsLeapYear(year)) {
			if (theMonth == 1) {
				return 29;
			} else if (theMonth == 3 || theMonth == 5 || theMonth == 8 || theMonth == 10) {
				return 30;
			} else {
				return 31;
			}

		} else {
			if (theMonth == 1) {
				return 28;
			} else if (theMonth == 3 || theMonth == 5 || theMonth == 8 || theMonth == 10) {
				return 30;
			} else {
				return 31;
			}
		}

	}

	/**
	 * Calculates the index of the first day of the week in a specified month. The
	 * index returned corresponds to position of this first day of the week within
	 * the DAYS_OF_WEEK class field.
	 * 
	 * @param month which may or may not be abbreviated to 3 or more characters
	 * @param year  of month to determine the first day from (Gregorian Calendar AD)
	 *              * String must contain the digits of a single non-negative int
	 *              for year.
	 * @return index within DAYS_OF_WEEK of specified month's first day
	 */
	public static int getFirstDayOfWeekInMonth(String month, String year) {
		int theMonth = getMonthIndex(month);// m
		int yearInCent = getYearWithinCentury(year);// k
		int century = getCentury(year);// j
		int days = 0;
		
		for (int i = 0; i < Integer.parseInt(year); i++) {
			if (getIsLeapYear(year)) {
				days += 366;
			} else {
				days += 365;
			}
		}
		for (int j = 0; j < getMonthIndex(month); j++) {
			days += getNumberOfDaysInMonth(month, year);
		}

		int q = getNumberOfDaysInMonth(month, year);//q

		days = (((13 * (theMonth + 1)) / 5) + yearInCent + (yearInCent / 4) + (century / 4) + 5 * century) % 7;

		return days;

	}
