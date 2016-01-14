package com.frez.turretwars;

import java.util.Calendar;

public class TimeUtils {
	
	public static final String getTimeText(boolean withMillis) {
		Calendar c = Calendar.getInstance();
		
		int h = c.get(Calendar.HOUR_OF_DAY);
		int m = c.get(Calendar.MINUTE);
		int s = c.get(Calendar.SECOND);
		
		
		String sh = h < 10 ? "0" + h : "" + h;
		String sm = m < 10 ? "0" + m : "" + m;
		String ss = s < 10 ? "0" + s : "" + s;
		if (withMillis) {
			int ms = c.get(Calendar.MILLISECOND);
			String sms = (ms < 100 ? (ms < 10 ? "00" : "0") : "") + ms;
			return sh + ":" + sm + ":" + ss + "." + sms;
		}
		return sh + ":" + sm + ":" + ss;
	}
	
	public static final String getTimeText(long time, boolean withMillis) {
		
		int h = getHours(time, true);
		int m = getMinutes(time, true);
		int s = getSeconds(time, true);
		
		String sh = h < 10 ? "0" + h : "" + h;
		String sm = m < 10 ? "0" + m : "" + m;
		String ss = s < 10 ? "0" + s : "" + s;
		if (withMillis) {
			int ms = getMilliSeconds(time);
			String sms = (ms < 100 ? (ms < 10 ? "00" : "0") : "") + ms;
			return sh + ":" + sm + ":" + ss + "." + sms;
		}
		return sh + ":" + sm + ":" + ss;
	}
	
	public static final int getHours() {
		return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
	}
	
	public static final int getHours(long time, boolean wrap) {
		return (int) (wrap ? ((time / 3600000l) % 24) : (time / 3600000l));
	}
	
	public static final int getMinutes() {
		return Calendar.getInstance().get(Calendar.MINUTE);
	}
	
	public static final int getMinutes(long time, boolean wrap) {
		return (int) (wrap ? ((time / 60000l) % 60) : (time / 60000l));
	}
	
	public static final int getSeconds() {
		return Calendar.getInstance().get(Calendar.SECOND);
	}
	
	public static final int getSeconds(long time, boolean wrap) {
		return (int) (wrap ? ((time / 1000l) % 60) : (time / 1000l));
	}
	
	public static final int getMilliSeconds() {
		return Calendar.getInstance().get(Calendar.MILLISECOND);
	}
	
	public static final int getMilliSeconds(long time) {
		return (int) (time % 1000l);
	}
	
	public static final long getTime(int h, int m, int s, int ms) {
		return getRawHours(h) + getRawMinutes(m) + getRawSeconds(s) + ms;
	}
	
	public static final long getRawHours(int h) {
		return h * 3600000l;
	}
	
	public static final long getRawMinutes(int m) {
		return m * 60000l;
	}
	
	public static final long getRawSeconds(int s) {
		return s * 1000l;
	}
	
	
}
