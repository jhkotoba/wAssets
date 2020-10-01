package com.wAssets.common;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
	
	public static boolean isDate(String date, String pattern) {
		
		if(Objects.isNull(date)) {
			return false;
		}else if(date.length() != 8) {
			return false;
		}
		
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(date);
		
		if(m.find()) {
			
			int year = Integer.parseInt(date.substring(0, 4));
			int month = Integer.parseInt(date.substring(4, 6));
			int day = Integer.parseInt(date.substring(6, 8));
			
			if(month < 1 || month > 12 ) {
                return false;
            }

            int[] lastDays = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
            int maxDay = lastDays[month-1];
            
            if(month == 2 && (year % 4 == 0 && year % 100 != 0 || year % 400 == 0)){
                maxDay = 29;
            }
                
            if(day <= 0 || day > maxDay){
                return false;
            }
            return true;
		}else {
			return false;
		}
	}
	
	public static boolean isNotDate(String date, String pattern) {
		return !Utils.isDate(date, pattern);
	}
}
