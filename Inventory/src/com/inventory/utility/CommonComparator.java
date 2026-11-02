package com.inventory.utility;

import java.util.Comparator;
import java.util.Date;

import com.inventory.dto.PaymentDTOList;

public class CommonComparator {
	
	public static Comparator<PaymentDTOList> paymentDateSort = new Comparator<PaymentDTOList>() {

		public int compare(PaymentDTOList s1, PaymentDTOList s2) {

		   Date billDate = s1.getBillDate();
		   Date paymentDate = s2.getPaymentDate();

		   /*For ascending order*/
		   return s1.getDateForSort().compareTo( s2.getDateForSort() );

		   /*For descending order*/
		   //rollno2-rollno1;
	   }};

}
