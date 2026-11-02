package com.inventory.utility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringUtils {
	
	public static String capitalize(String str) {
	    if(str == null || str.isEmpty()) {
	        return str;
	    }else{
	    	String words[]=str.split("\\s");
	    	//System.out.println(words.toString());
	        String capitalizeWord="";  
	        for(String w:words){  
	        	//System.out.println("->"+w+"<-");
	        	if(!w.equals("")) {
	            String first=w.substring(0,1);  
	            String afterfirst=w.substring(1).toLowerCase();  
	            capitalizeWord+=first.toUpperCase()+afterfirst+" ";
	        	}
	        }
	        return capitalizeWord;
	    }

	    //return str.substring(0, 1).toUpperCase() + str.substring(1);
	}
	
	public static String getStrDate(Date inputDate){
		String strDate="";
		try{
			//DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");//yyyy-mm-dd  
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");//yyyy-mm-dd
            strDate = dateFormat.format(inputDate); 
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return strDate;
	}
	
	public static String getStrDateTime(Date inputDate){
		String strDate="";
		try{
			//DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");//yyyy-mm-dd  
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");//yyyy-mm-dd
            strDate = dateFormat.format(inputDate); 
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return strDate;
	}
	
	public static String getBillNumber(String letter,Integer billNumber){
		String billNum="";
		try{
			
			if(billNumber>0){
				billNum = billNumber.toString();
				
				if(billNum.length()==1){
					billNum = "000"+billNum;
				}else if(billNum.length()==2){
					billNum = "00"+billNum;
				}if(billNum.length()==3){
					billNum = "0"+billNum;
				}
			}
			if(letter!=null){
				if(!letter.equalsIgnoreCase(ConstantUtils.BLANK)){
					billNum = letter+billNum;
				}
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return billNum;
	}

}
