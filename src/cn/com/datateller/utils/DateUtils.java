package cn.com.datateller.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

	public static String getCurrentDay(){
		Calendar calendar=Calendar.getInstance();
		String year=String.valueOf(calendar.get(Calendar.YEAR));
		String month=String.valueOf(calendar.get(Calendar.MONTH)+1);
		String day=String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
		return year+"年"+month+"月"+day+"日";
	}
	
	public static String getStandardCurrentDay() {
		// TODO Auto-generated method stub
		Date date=new Date();
		SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");
		return format.format(date);
	}
	
	public static String CheckBabyBirthday(String year,String month,String day){
		int intyear=Integer.valueOf(year);
		int intmonth=Integer.valueOf(month);
		int intday=Integer.valueOf(day);
		
		if(intyear>2100&&intyear<1900) 
			return "输入非法的年份";
		if(intmonth>12||intmonth<0)
			return "输入非法的月份";
		if(intday>31||intday<0)
			return "输入非法日期";
		return "true";
		
	}
    
	public static int getCurrentYear(){
		Calendar calendar=Calendar.getInstance();
		return calendar.get(Calendar.YEAR);
	}
	
	public static int getCurrentMonth(){
		Calendar calendar=Calendar.getInstance();
		return calendar.get(Calendar.MONTH);
	}
	
    public static int getCurrentDayOfMonth(){
    	Calendar calendar=Calendar.getInstance();
		return calendar.get(Calendar.DAY_OF_MONTH);
    }
	
}
