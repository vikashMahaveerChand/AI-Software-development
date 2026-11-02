package com.inventory.reports;

import java.util.ArrayList;
import java.util.List;

import com.inventory.dto.BillDTO;
import com.inventory.model.Bill;
import com.inventory.model.PawnBill;
import com.inventory.utility.Calculator;
import com.inventory.utility.StringUtils;

public class BillingReportBeanList {
	
	public ArrayList<BillingReportBean> getBillingReportList(BillDTO billDTO) {
	      ArrayList<BillingReportBean> dataBeanList = new ArrayList<BillingReportBean>();
	      
	      if(billDTO!=null && billDTO.getBillList()!=null && billDTO.getBillList().size()>0) {
	    	  for(Bill data:billDTO.getBillList()) {
	    		  dataBeanList.add(
		    			  new BillingReportBean(
		    					  data.getBillNumber(), 
		    					  StringUtils.getStrDate(data.getBillDate()), 
		    					  data.getContact().getContactName(), 
		    					  data.getWeight()!=null ? Calculator.formatThreeDecimal(data.getWeight()):"", 
		    					  data.getTotalAmount()!=null ? Calculator.formatThreeDecimal(data.getTotalAmount()):""
		    					  ));
	    	  }
	      }

	      return dataBeanList;
	   }

}
