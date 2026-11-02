package com.inventory.utility;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

public class Calculator {
	
	public static Integer getCostPrice(Double sellingPrice,Double gstPercentage){
		Double costPrice = 0.0;
		Integer costPriceReturn =0;
		try{
			if(sellingPrice>0.0 && gstPercentage>0.0){
				costPrice = (sellingPrice*100)/(gstPercentage+100);
				System.out.println("costPrice befor Round->"+costPrice);
				//costPrice = (double)Math.round(costPrice*100)/100;
				//System.out.println("costPrice After round->"+costPrice);
				costPriceReturn = (int)Math.round(costPrice);
				System.out.println("Round to int"+costPriceReturn);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return costPriceReturn;
		
	}
	
	public static Double getTwoDigitDecimal(Double input){
	    DecimalFormat twoDForm = new DecimalFormat("#.##");
	    return Double.valueOf(twoDForm.format(input));
	}
	
	public static Double getThreeDigitDecimal(Double input){
	    DecimalFormat twoDForm = new DecimalFormat("#.###");
	    return Double.valueOf(twoDForm.format(input));
	}
	
	
	public static String formatTwoDecimal(double value) {
		try {
		if(value==0) {
			return "";
		}
	    if(value < 1000) {
	        return format("###.##", value);
	    } else {
	        double hundreds = value % 1000;
	        int other = (int) (value / 1000);
	        return format(",##", other) + ',' + format("000.##", hundreds);
	    }		
		}catch(Exception ex) {
			return "";
		}
	}
	
	public static String formatThreeDecimal(double value) {
		try {
		if(value==0) {
			return "";
		}	
	    if(value < 1000) {
	        return format("###.###", value);
	    } else {
	        double hundreds = value % 1000;
	        int other = (int) (value / 1000);
	        return format(",##", other) + ',' + format("000.###", hundreds);
	    }		
		}catch(Exception ex) {
			return "";
		}
	}

	private static String format(String pattern, Object value) {
	    return new DecimalFormat(pattern).format(value);
	}
	
	private static final String[] specialNames = {
	        "",
	        " thousand",
	        " lakh",//" million",
	        " crore",//" billion",
	        " arab",//" trillion",
	        " karab",//" quadrillion",
	        "arabkarab"//" quintillion"
	    };
	    
	    private static final String[] tensNames = {
	        "",
	        " ten",
	        " twenty",
	        " thirty",
	        " forty",
	        " fifty",
	        " sixty",
	        " seventy",
	        " eighty",
	        " ninety"
	    };
	    
	    private static final String[] numNames = {
	        "",
	        " one",
	        " two",
	        " three",
	        " four",
	        " five",
	        " six",
	        " seven",
	        " eight",
	        " nine",
	        " ten",
	        " eleven",
	        " twelve",
	        " thirteen",
	        " fourteen",
	        " fifteen",
	        " sixteen",
	        " seventeen",
	        " eighteen",
	        " nineteen"
	    };
	    
	    private static String convertLessThanOneThousand(int number) {
	        String current;
	        
	        if (number % 100 < 20){
	            current = numNames[number % 100];
	            number /= 100;
	        }
	        else {
	            current = numNames[number % 10];
	            number /= 10;
	            
	            current = tensNames[number % 10] + current;
	            number /= 10;
	        }
	        if (number == 0) return current;
	        return numNames[number] + " hundred" + current;
	    }
	    
	    public static String convertOld(int number) {

	        if (number == 0) { return "zero"; }
	        
	        String prefix = "";
	        
	        if (number < 0) {
	            number = -number;
	            prefix = "negative";
	        }
	        
	        String current = "";
	        int place = 0;
	        
	        do {
	            int n = number % 1000;
	            if (n != 0){
	                String s = convertLessThanOneThousand(n);
	                current = s + specialNames[place] + current;
	            }
	            place++;
	            number /= 1000;
	        } while (number > 0);
	        
	        return (prefix + current).trim();
	    }
	    
	    
	    public static final String[] units = { "", "One", "Two", "Three", "Four",
				"Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve",
				"Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen",
				"Eighteen", "Nineteen" };

		public static final String[] tens = { 
				"", 		// 0
				"",		// 1
				"Twenty", 	// 2
				"Thirty", 	// 3
				"Forty", 	// 4
				"Fifty", 	// 5
				"Sixty", 	// 6
				"Seventy",	// 7
				"Eighty", 	// 8
				"Ninety" 	// 9
		};

		public static String convert(final int n) {
			if (n < 0) {
				return "Minus " + convert(-n);
			}

			if (n < 20) {
				return units[n];
			}

			if (n < 100) {
				return tens[n / 10] + ((n % 10 != 0) ? " " : "") + units[n % 10];
				//return tens[n / 10] + ((n % 10 != 0) ? " " : "") ;
			}

			if (n < 1000) {
				return units[n / 100] + " Hundred" + ((n % 100 != 0) ? " " : "") + convert(n % 100);
			}

			if (n < 100000) {
				return convert(n / 1000) + " Thousand" + ((n % 10000 != 0) ? " " : "") + convert(n % 1000);
			}

			if (n < 10000000) {
				return convert(n / 100000) + " Lakh" + ((n % 100000 != 0) ? " " : "") + convert(n % 100000);
			}

			return convert(n / 10000000) + " Crore" + ((n % 10000000 != 0) ? " " : "") + convert(n % 10000000);
		}
		
		public static Integer monthDifference(Date fromDate,Date toDate) {
			Integer diffMonth=null;
			try {
				LocalDate fDate = convertToLocalDateViaInstant(fromDate);
				LocalDate tDate = convertToLocalDateViaInstant(toDate);
				
				
				Period age = Period.between(fDate, tDate);
				
				
				int years = age.getYears(); 
				int months = age.getMonths(); 
				int days = age.getDays();
				//System.out.println("number of years: " + years); 
				//System.out.println("number of months: " + months);
				//System.out.println("number of days: " + days);
				
				if(years>0) {
					months = months + (years*12);
				}
				
				if(days!=0) {
					months = months+1;
				}
				//System.out.println("Updated number of months: " + months);
				diffMonth = months;
			}catch(Exception ex) {
				ex.printStackTrace();
			}
			
			return diffMonth;
		}
		
		public static LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
		    return dateToConvert.toInstant()
		      .atZone(ZoneId.systemDefault())
		      .toLocalDate();
		}
		
		public static double calculateCompoundInterest(Double principal,Double rate,int months,int number) {
			double totalAmount=0.0;
			
			int remainingMonth = months%12;
			int years = months/12;
			//System.out.println("remainingMonth - "+remainingMonth);
			//System.out.println("No of year - "+(months/12));
			Double rateOfInterestPerYear = getTwoDigitDecimal(rate*12);
			if(remainingMonth==0) {
				totalAmount = getCompoundInterest(principal,rateOfInterestPerYear,years,number);
			}else {
				totalAmount = getCompoundInterest(principal,rateOfInterestPerYear,years,number);
				Double ExtraInt = getTwoDigitDecimal((totalAmount*rate*remainingMonth)/100);
				
				totalAmount = getTwoDigitDecimal(totalAmount + ExtraInt);
				
				
			}
			//System.out.println("Total Amount ->"+totalAmount);
			//System.out.println("Total Amount round off->"+Math.round(totalAmount));
			totalAmount = Math.round(totalAmount);
			return totalAmount;
		}
		
		private static Double getCompoundInterest(Double principal,Double rate,int time,int number) {
			double interest = (principal * (Math.pow((1 + rate/100), (time * number))) - principal);
			
			//System.out.println("P+I"+principal+interest);
			//System.out.println(Calculator.formatTwoDecimal((principal+interest)));
			//double returnValue = Double.parseDouble(Calculator.formatTwoDecimal((principal+interest)).replaceAll(",", ""));
			double returnValue = getTwoDigitDecimal((principal+interest));
			return returnValue;
		}

}
