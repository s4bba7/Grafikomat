package util;

public class CalendarDays {
	
	private int day;
	private String dayOfWeek;
	
	public CalendarDays(int day, String dayOfWeek) {
		this.day = day;
		this.dayOfWeek = dayOfWeek;
	}
	
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public String getDayOfWeek() {
		return dayOfWeek;
	}
	public void setDayOfWeek(String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

}
