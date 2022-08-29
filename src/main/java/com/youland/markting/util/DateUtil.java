package com.youland.markting.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtil {

	public static String yyyyMMdd = "yyyyMMdd";
	
	public static String format(LocalDateTime date) {
		return format(date, yyyyMMdd);
	}

	public static String format(LocalDateTime date, String format) {
		String result = "";
		try {
			DateTimeFormatter df = DateTimeFormatter.ofPattern(format);
			result = df.format(date);
		} catch (Exception e) {
			throw e;
		}
		return result;
	}
	
	public static LocalDateTime getDateAfterMinute(LocalDateTime d, int minute) {
		return d.plusMinutes(minute);
	}
	
	public static long getTimeMillis(LocalDateTime d) {
		return d.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
	}
	
	public static boolean compareTime(LocalDateTime bigger, LocalDateTime smaller) {
		return getTimeMillis(bigger) - getTimeMillis(smaller) > 0;
	}

	public static LocalDateTime converter(Date date) {
		Instant instant = date.toInstant();
		ZoneId zone = ZoneId.systemDefault();
		LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
		return localDateTime;
	}
}
