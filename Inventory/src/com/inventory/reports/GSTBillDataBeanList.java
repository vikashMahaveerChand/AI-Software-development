package com.inventory.reports;

import java.util.ArrayList;
import java.util.List;

import com.inventory.model.BillDetails;
import com.inventory.model.BillReturnDetails;
import com.inventory.utility.Calculator;
import com.inventory.utility.StringUtils;

public class GSTBillDataBeanList {
	
	public ArrayList<GSTBillDataBean> getGSTBillDataBeanList(List<BillDetails> billDetails) {
	      ArrayList<GSTBillDataBean> dataBeanList = new ArrayList<GSTBillDataBean>();
	      
	      if(billDetails!=null && billDetails.size()>0) {
	    	  for(BillDetails bill:billDetails) {
	    		  dataBeanList.add(
		    			  addOrnament(
		    					  /*bill.getsNo()!=null ? bill.getsNo().toString():"",
		    					  bill.getOrnament()!=null?bill.getOrnament().getOrnamentName():"",
		    					  bill.getRate()!=null?bill.getRate().toString():"",
		    					  bill.getWeight()!=null?bill.getWeight().toString():"",
		    					  bill.getTaxPercentage()!=null?bill.getTaxPercentage().toString():"",
		    					  bill.getAmount()!=null?bill.getAmount().toString():"",
		    					  bill.getOrnament()!=null?bill.getOrnament().getHsncode():"",
		    					*/		  
		    							  
		    					bill.getOrnament()!=null ? StringUtils.capitalize(bill.getOrnament().getOrnamentName()):"",
		    					bill.getGrossWeight()!=null ? Calculator.formatThreeDecimal(bill.getGrossWeight()):"",
		    					bill.getWeightLess()!=null ? Calculator.formatThreeDecimal(bill.getWeightLess()):"",
		    					bill.getWeight()!=null ? Calculator.formatThreeDecimal(bill.getWeight()):"",
		    					bill.getPurity()!=null ? Calculator.formatThreeDecimal(bill.getPurity()):"",
		    					bill.getPureWeight()!=null ? Calculator.formatThreeDecimal(bill.getPureWeight()):"",
		    					bill.getWastage()!=null ? Calculator.formatThreeDecimal(bill.getWastage()):"",
		    					bill.getRate()!=null ? Calculator.formatThreeDecimal(bill.getRate()):"",
		    					bill.getTaxPercentage()!=null ? Calculator.formatThreeDecimal(bill.getTaxPercentage()):"",
		    					bill.getOrnamentAmount()!=null ? Calculator.formatThreeDecimal(bill.getOrnamentAmount()):"",
		    					bill.getTaxAmount()!=null ? Calculator.formatThreeDecimal(bill.getTaxAmount()):"",
		    					bill.getMcRate()!=null ? Calculator.formatThreeDecimal(bill.getMcRate()):"",
		    					bill.getMcPerPiece()!=null ? Calculator.formatThreeDecimal(bill.getMcPerPiece()):"",
		    					bill.getMcAmount()!=null ? Calculator.formatThreeDecimal(bill.getMcAmount()):"",
		    					bill.getRoundOff()!=null ? Calculator.formatThreeDecimal(bill.getRoundOff()):"",
		    					bill.getAmount()!=null ? Calculator.formatThreeDecimal(bill.getAmount()):"",
		    					bill.getNoOfPiece()!=null ? Calculator.formatThreeDecimal(bill.getNoOfPiece()):"",
		    					bill.getMetalBalance()!=null ? Calculator.formatThreeDecimal(bill.getMetalBalance()):"",
		    					bill.getsNo()!=null ? Calculator.formatThreeDecimal(bill.getsNo()) :"",
		    					bill.getOrnament()!=null && bill.getOrnament().getHsncode()!=null ? bill.getOrnament().getHsncode():""
		    								
		    					  ));
		    			  
	    	  }
	    	  
	      }

	      return dataBeanList;
	   }
	
	public ArrayList<GSTBillDataBean> getGSTBillReturnDataBeanList(List<BillReturnDetails> billDetails) {
	      ArrayList<GSTBillDataBean> dataBeanList = new ArrayList<GSTBillDataBean>();
	      
	      if(billDetails!=null && billDetails.size()>0) {
	    	  for(BillReturnDetails bill:billDetails) {
	    		  dataBeanList.add(
		    			  addOrnament(
		    					  /*bill.getsNo()!=null ? bill.getsNo().toString():"",
		    					  bill.getOrnament()!=null?bill.getOrnament().getOrnamentName():"",
		    					  bill.getRate()!=null?bill.getRate().toString():"",
		    					  bill.getWeight()!=null?bill.getWeight().toString():"",
		    					  bill.getTaxPercentage()!=null?bill.getTaxPercentage().toString():"",
		    					  bill.getAmount()!=null?bill.getAmount().toString():"",
		    					  bill.getOrnament()!=null?bill.getOrnament().getHsncode():"",
		    					*/		  
		    							  
		    					bill.getOrnament()!=null ? StringUtils.capitalize(bill.getOrnament().getOrnamentName()):"",
		    					bill.getGrossWeight()!=null ? Calculator.formatThreeDecimal(bill.getGrossWeight()):"",
		    					bill.getWeightLess()!=null ? Calculator.formatThreeDecimal(bill.getWeightLess()):"",
		    					bill.getWeight()!=null ? Calculator.formatThreeDecimal(bill.getWeight()):"",
		    					bill.getPurity()!=null ? Calculator.formatThreeDecimal(bill.getPurity()):"",
		    					bill.getPureWeight()!=null ? Calculator.formatThreeDecimal(bill.getPureWeight()):"",
		    					bill.getWastage()!=null ? Calculator.formatThreeDecimal(bill.getWastage()):"",
		    					bill.getRate()!=null ? Calculator.formatThreeDecimal(bill.getRate()):"",
		    					bill.getTaxPercentage()!=null ? Calculator.formatThreeDecimal(bill.getTaxPercentage()):"",
		    					bill.getOrnamentAmount()!=null ? Calculator.formatThreeDecimal(bill.getOrnamentAmount()):"",
		    					bill.getTaxAmount()!=null ? Calculator.formatThreeDecimal(bill.getTaxAmount()):"",
		    					bill.getMcRate()!=null ? Calculator.formatThreeDecimal(bill.getMcRate()):"",
		    					bill.getMcPerPiece()!=null ? Calculator.formatThreeDecimal(bill.getMcPerPiece()):"",
		    					bill.getMcAmount()!=null ? Calculator.formatThreeDecimal(bill.getMcAmount()):"",
		    					bill.getRoundOff()!=null ? Calculator.formatThreeDecimal(bill.getRoundOff()):"",
		    					bill.getAmount()!=null ? Calculator.formatThreeDecimal(bill.getAmount()):"",
		    					bill.getNoOfPiece()!=null ? Calculator.formatThreeDecimal(bill.getNoOfPiece()):"",
		    					bill.getMetalBalance()!=null ? Calculator.formatThreeDecimal(bill.getMetalBalance()):"",
		    					bill.getsNo()!=null ? Calculator.formatThreeDecimal(bill.getsNo()) :"",
		    					bill.getOrnament()!=null && bill.getOrnament().getHsncode()!=null ? bill.getOrnament().getHsncode():""
		    								
		    					  ));
		    			  
	    	  }
	    	  
	      }

	      return dataBeanList;
	   }
	
	private GSTBillDataBean addOrnament(String description,String grossWeight,String weightLess,
			String weight,String purity,String pureWeight,String wastage,String rate,String taxPercentage,
			String ornamentAmount,String taxAmount,String mcRate,String mcPerPiece,String mcAmount,
			String roundoff,String amount,String noopiece,String metalBalance,String sno,String hsncode) {
		GSTBillDataBean dataBean = new GSTBillDataBean();
		
		dataBean.setDescription(description);
		dataBean.setGrossWeight(grossWeight);
		dataBean.setWeightLess(weightLess);
		dataBean.setWeight(weight);
		dataBean.setPurity(purity);
		dataBean.setPureWeight(pureWeight);
		dataBean.setWastage(wastage);
		dataBean.setRate(rate);
		dataBean.setTaxPercentage(taxPercentage);
		dataBean.setOrnamentAmount(ornamentAmount);
		dataBean.setTaxAmount(taxAmount);
		dataBean.setMcRate(mcRate);
		dataBean.setMcPerPiece(mcPerPiece);
		dataBean.setMcAmount(mcAmount);
		dataBean.setRoundoff(roundoff);
		dataBean.setAmount(amount);
		dataBean.setNoopiece(noopiece);
		dataBean.setMetalBalance(metalBalance);
		dataBean.setSno(sno);
		dataBean.setHsncode(hsncode);
		
		return dataBean;
	}
	
	/*private GSTBillDataBean addOrnamentOld(String sNo, String description,String rate,
	String weight,String gstPercentage,String amount,String hsncode) {
GSTBillDataBean dataBean = new GSTBillDataBean();
  dataBean.setsNo(sNo);
  dataBean.setDescription(description);
  dataBean.setRate(rate);
  dataBean.setWeight(weight);
  dataBean.setGstPercentage(gstPercentage);
  dataBean.setAmount(amount);
  dataBean.setHsncode(hsncode);
  return dataBean;
}*/


}
