package com.hanyi.daily.lambda.stream;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatThreadLocal {
	
	private static final ThreadLocal<DateFormat> df = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyyMMdd"));
	
	public static Date convert(String source) throws ParseException{
		return df.get().parse(source);
	}

}
