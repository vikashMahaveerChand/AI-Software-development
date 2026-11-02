package com.inventory.reports;

import java.util.ArrayList;
import java.util.List;

import com.inventory.model.BillDetails;
import com.inventory.model.Contact;
import com.inventory.model.PawnBill;
import com.inventory.model.PawnBillDetails;
import com.inventory.utility.StringUtils;

public class PawnBillBeanList {
	
	public ArrayList<PawnBillBean> getPawnBillBeanList(List<PawnBill> pawnBillDetails) {
	      ArrayList<PawnBillBean> dataBeanList = new ArrayList<PawnBillBean>();
	      
	      if(pawnBillDetails!=null && pawnBillDetails.size()>0) {
	    	  for(PawnBill bill:pawnBillDetails) {
	    		  dataBeanList.add(
		    			  addObject(
		    					  bill.getContact().getContactId(),
		    					  bill.getBillNumber(),
		    					  bill.getContact().getContactName()+" "+bill.getContact().getSecondName(),
		    					  contactAddress(bill.getContact()),
		    					  bill.getContact().getMobile(),
		    					  StringUtils.getStrDate(bill.getBillDate()),
		    					  bill.getOrnamentDescription(),
		    					  bill.getTotalNetWeight(),
		    					  bill.getPledgeAmount()
		    					  ));
		    			  
	    	  }
	    	  
	      }

	      return dataBeanList;
	   }
	
	public ArrayList<PawnBillBean> getPawnBillPrintBeanList(List<PawnBillDetails> pawnBillDetails) {
	      ArrayList<PawnBillBean> dataBeanList = new ArrayList<PawnBillBean>();
	      Integer sNo=0;
	      if(pawnBillDetails!=null && pawnBillDetails.size()>0) {
	    	  for(PawnBillDetails bill:pawnBillDetails) {
	    		  dataBeanList.add(
		    			  addBillDetailsObject(
		    					  ++sNo,
		    					  bill.getOrnament().getOrnamentName()
		    					  ));
		    			  
	    	  }
	    	  
	      }

	      return dataBeanList;
	   }
	
	private PawnBillBean addObject(Integer contactId,String billNumber,String contactName,
			String contactAddress,String contactMobile,
			String billDate,String description,double weight,double amount) {
		PawnBillBean dataBean = new PawnBillBean();
	      dataBean.setContactId(contactId);
	      dataBean.setBillNumber(billNumber);
	      dataBean.setContactName(contactName);
	      dataBean.setContactAddress(contactAddress);
	      dataBean.setContactMobile(contactMobile);
	      dataBean.setBillDate(billDate);
	      dataBean.setDescription(description);
	      dataBean.setWeight(weight);
	      dataBean.setAmount(amount);
	      return dataBean;
	   }
	
	private PawnBillBean addBillDetailsObject(Integer sNo,String description) {
		PawnBillBean dataBean = new PawnBillBean();
	      dataBean.setsNo(sNo);
	      dataBean.setDescription(description);
	      return dataBean;
	   }
	
	
	
	private String contactAddress(Contact contact) {
		String address ="";
		try {
			StringBuffer addressBuffer = new StringBuffer();
			if(contact.getDoorNo()!=null) {
				//address = "# "+contact.getDoorNo()+",";
				addressBuffer.append("# "+contact.getDoorNo()).append(" ");
			}
			if(contact.getStreet()!=null) {
				addressBuffer.append(StringUtils.capitalize(contact.getStreet().getStreetName()));
				
				if(contact.getStreet().getArea()!=null) {
				addressBuffer.append("\n").append(StringUtils.capitalize(contact.getStreet().getArea().getAreaName()));
				
				if(contact.getStreet().getArea().getCity()!=null) {
				addressBuffer.append("\n")
				.append(StringUtils.capitalize(contact.getStreet().getArea().getCity().getCityName()))
				.append(" - ")
				.append(contact.getStreet().getArea().getPincode());
				}
				if(contact.getStreet().getArea().getCity().getState()!=null) {
				addressBuffer.append("\n").append(StringUtils.capitalize(contact.getStreet().getArea().getCity().getState().getStateName()))
				;
				}
				}
			}
			
			address = addressBuffer.toString();
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return address;
	}

}
