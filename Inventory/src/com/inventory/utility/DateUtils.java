package com.inventory.utility;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DateUtils {
	
	public static Date geReducedDate(Date date,Integer minusDate) {
		Date rDate = null;
		try {
			//LocalDate dateBeforeXDays = LocalDate.now(ZoneId.of("Europe/Paris")).minusDays(30);
			LocalDate dateBeforeXDays = convertToLocalDateViaInstant(date).minusDays(minusDate);
			rDate = Date.from(dateBeforeXDays.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return rDate;
	}
	
	public static Date getDate(String dateStr) {
		Date date = null;
		try {
			date=new SimpleDateFormat("dd/MM/yyyy").parse(dateStr);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return date;
	}
	
	
	public static LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
	    return dateToConvert.toInstant()
	      .atZone(ZoneId.systemDefault())
	      .toLocalDate();
	}

}
