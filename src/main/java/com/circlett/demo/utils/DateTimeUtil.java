package com.circlett.demo.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DateTimeUtil {
	@Bean
	public static String getSysTime(){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Date date = new Date();
		String dateStr = sdf.format(date);
		
		return dateStr;
		
	}
	
}
