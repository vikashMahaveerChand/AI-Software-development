package com.inventory.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.google.gson.GsonBuilder;
import com.inventory.controller.BalanceSheetController;
import com.inventory.dao.BillDAO;
import com.inventory.dao.BillDAOImpl;
import com.inventory.dto.BalanceSheetDTO;
import com.inventory.dto.BalanceSheetDTOList;
import com.inventory.dto.BillDTO;
import com.inventory.dto.ContactTransactionDTO;
import com.inventory.dto.ContactTransactionDTOList;
import com.inventory.dto.PaymentDTO;
import com.inventory.dto.PaymentDTOList;
import com.inventory.model.BankEntry;
import com.inventory.model.Bill;
import com.inventory.model.BillDetails;
import com.inventory.model.BillNumber;
import com.inventory.model.BillReturnDetails;
import com.inventory.model.Metal;
import com.inventory.model.PaymentDetails;
import com.inventory.utility.Calculator;
import com.inventory.utility.CommonComparator;
import com.inventory.utility.ConstantUtils;
import com.inventory.utility.StringUtils;

public class BillServiceImpl implements BillService{
	private static BillServiceImpl myInstance = null;
	BillDAO billDAO = null;
	MasterService masterService = null;
	
	
	private BillServiceImpl(){
		try{
			billDAO = BillDAOImpl.getInstace();
			masterService = MasterServiceImpl.getInstace();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public static BillServiceImpl getInstace(){
		if(myInstance == null){
			myInstance = new BillServiceImpl();
		}
		return myInstance;
	}

	@Override
	public Bill saveOrUpdateBill(Bill bill) {
		// TODO Auto-generated method stub
		Bill billObj=null;
		try{
			if(bill!=null){
				if(bill.getBillDetails()!=null){
					for(BillDetails obj : bill.getBillDetails()){
						bill.addBillDetailList(obj);
					}
				}
				
				if(bill.getBillReturnDetails()!=null){
					for(BillReturnDetails obj : bill.getBillReturnDetails()){
						bill.addBillReturnDetailList(obj);
					}
				}
				Boolean isInsertMode = true;
				if(bill.getBillId()!=null && bill.getBillId()>0) {
					isInsertMode = false;
				}
				billObj = billDAO.saveOrUpdateBill(bill);
				
				if(billObj!=null){
					
					//Update Closing Balance -Starts
					if(isInsertMode) {
						BalanceSheetDTO balanceSheetDTO = BalanceSheetController.getInstace().getBalanceSheetList(new Date(), bill.getLicence().getLicenceId(), bill.getContact().getContactType().getContactTypeName(), bill.getBillSource().getBillSourceName(),bill.getBillType().getBillTypeName(),bill.getContact().getContactId());
						String closingCashBalance = "";
						String closingMetalBalance = "";
						if(balanceSheetDTO.getTotalAmountBalance()!=null) {
							closingCashBalance = Double.toString(balanceSheetDTO.getTotalAmountBalance());
						}
						
						if(balanceSheetDTO.getTotalMetalBalanceStr()!=null) {
							closingMetalBalance = balanceSheetDTO.getTotalMetalBalanceStr();
						}
						billDAO.updateClosingBalance(closingCashBalance, closingMetalBalance, billObj.getBillId());
					}
					//Update Closing Balance Ends
					
					BillNumber billNumberObj = billDAO.getBillNumber(billObj.getLicence().getLicenceId(), billObj.getBillSource().getBillSourceId());
					
					if(billNumberObj!=null){
						String billNum = StringUtils.getBillNumber(billNumberObj.getBillLetter(), billNumberObj.getBillNumber());
						if(billObj.getBillNumber().equalsIgnoreCase(billNum)){
							//update bill number starts
							updateBillNumber(billNumberObj);
							//update billNumber ends
						}
					}
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return billObj;
	}

	@Override
	public BillDTO getBillList(Date fromDate,Date toDate,Integer licenceId,Integer billSourceId,Integer contactId,String entryType) {
		// TODO Auto-generated method stub
		BillDTO billDTO = null;
		List<Bill> billList = null;
		try {
			billDTO = new BillDTO();
			billList = billDAO.getBillList(fromDate, toDate, licenceId, billSourceId, contactId,entryType);
			
			Integer billCount;
			Double totalBillAmount;
			Double totalBillWeight;
			
			if(billList!=null && billList.size()>0) {
				billCount = billList.size();
				
				totalBillAmount = billList.stream().filter(o -> o.getTotalAmount()!=null).mapToDouble(Bill::getTotalAmount).sum();
				totalBillWeight = billList.stream().filter(o -> o.getWeight()!=null).mapToDouble(Bill::getWeight).sum();
				
				billDTO.setBillCount(billCount);
				billDTO.setTotalBillAmount(totalBillAmount);
				billDTO.setTotalBillWeight(totalBillWeight);
				
				Map<Metal, List<Bill>> rawMap = billList.stream().collect(Collectors.groupingBy(Bill::getMetal,Collectors.toList()));

				if(rawMap!=null) {
					HashMap<Metal, List<Bill>> map
		            = rawMap.entrySet()
		                  .stream()
		                  .sorted((i1, i2)
		                              -> i1.getKey().getMetalName().compareTo(
		                                  i2.getKey().getMetalName()))
		                  .collect(Collectors.toMap(
		                      Map.Entry::getKey,
		                      Map.Entry::getValue,
		                      (e1, e2) -> e1, LinkedHashMap::new));
					
					StringBuffer billCountStr = new StringBuffer();
					StringBuffer totalBillAmountStr = new StringBuffer();
					StringBuffer totalBillWeightStr = new StringBuffer();
					
					map.forEach((k,v)->{
						if(billCountStr.length()==0) {
							billCountStr.append(k).append(": ").append(v.size());
						}else {
							billCountStr.append(", ").append(k).append(" ").append(v.size());
						}
						
						if(totalBillAmountStr.length()==0) {
							totalBillAmountStr.append(k).append(": ").append(
								Calculator.formatTwoDecimal(v.stream().filter(o -> o.getTotalAmount()!=null).mapToDouble(Bill::getTotalAmount).sum()));
						}else {
							totalBillAmountStr.append(", ").append(k).append("-").append(
								Calculator.formatTwoDecimal(v.stream().filter(o -> o.getTotalAmount()!=null).mapToDouble(Bill::getTotalAmount).sum()));
						}
						
						if(totalBillWeightStr.length()==0) {
							totalBillWeightStr.append(k).append(": ").append(
								Calculator.formatThreeDecimal(v.stream().filter(o -> o.getWeight()!=null).mapToDouble(Bill::getWeight).sum()));
						}else {
							totalBillWeightStr.append(", ").append(k).append(": ").append(
								Calculator.formatThreeDecimal(v.stream().filter(o -> o.getWeight()!=null).mapToDouble(Bill::getWeight).sum()));
						}
					});
					
					billDTO.setBillCountStr(billCountStr.toString());
					billDTO.setTotalBillAmountStr(totalBillAmountStr.toString());
					billDTO.setTotalBillWeightStr(totalBillWeightStr.toString());
				}
			}
			billDTO.setBillList(billList);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return billDTO;
	}

	@Override
	public Bill getBill(Integer billId) {
		// TODO Auto-generated method stub
		Bill bill = null;
		try{
			bill = billDAO.getBill(billId);
			List<BillDetails> billDetails = new ArrayList<BillDetails>();
			List<BillReturnDetails> billReturnDetails = new ArrayList<BillReturnDetails>();
			if(bill!=null){
				if(bill.getBillDetailsList()!=null && bill.getBillDetailsList().size()>0){
					for(BillDetails obj:bill.getBillDetailsList()){
						if(obj.getDeletedFlag().equalsIgnoreCase(ConstantUtils.CONSTANT_N))
							billDetails.add(obj);
					}
					
					bill.setBillDetails(billDetails);
				}
				
				if(bill.getBillReturnDetailsList()!=null && bill.getBillReturnDetailsList().size()>0){
					for(BillReturnDetails obj:bill.getBillReturnDetailsList()){
						if(obj.getDeletedFlag().equalsIgnoreCase(ConstantUtils.CONSTANT_N))
							billReturnDetails.add(obj);
					}
					
					bill.setBillReturnDetails(billReturnDetails);
				}
				
				
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return bill;
	}

	@Override
	public BillNumber getBillNumber(Integer licenceId, Integer billSourceId) {
		// TODO Auto-generated method stub
		return billDAO.getBillNumber(licenceId, billSourceId);
	}

	@Override
	public BillNumber saveOrUpdateBillNumber(BillNumber billNumber) {
		// TODO Auto-generated method stub
		return billDAO.saveOrUpdateBillNumber(billNumber);
	}
	@Override
	public void updateBillNumber(BillNumber billNumber){
		try{
			
			Integer newBillNumber = billNumber.getBillNumber();
			newBillNumber = newBillNumber+1;
			String letter=billNumber.getBillLetter();
			
			if(letter!=null && newBillNumber>=10000){
				newBillNumber = 1;
				
				if(letter.length()==1 && !letter.equalsIgnoreCase("Z")){
					char ch = letter.charAt(0);
					ch = ch++;
					letter = ""+ch;
				}if(letter.length()==1 && letter.equalsIgnoreCase("Z")){
					letter ="AA";
				}else if(letter.length()==2){
					char ch = letter.charAt(1);
					if(ch!='Z'){
						ch = ch++;
						letter =letter.charAt(0)+""+ch;
					}else if(ch=='Z'){
						ch = letter.charAt(0);
						if(ch!='Z'){
							ch = ch++;
							letter = ch+"A";
						}
					}
				}
			}
			
			billNumber.setBillLetter(letter);
			billNumber.setBillNumber(newBillNumber);
			
			billDAO.saveOrUpdateBillNumber(billNumber);
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	@Override
	public PaymentDTO getPaymentDTODetails(Date fromDate, Date toDate, Integer billSourceId,Integer contactId, Integer licenceId,String entryType) {
		// TODO Auto-generated method stub
		PaymentDTO paymentDTO = null;
		try{
			paymentDTO = new PaymentDTO();
			List<PaymentDTOList> paymentDTOList = new ArrayList<PaymentDTOList>();
			
			List<Bill> billList = billDAO.getBillList(fromDate, toDate, licenceId, billSourceId, contactId,entryType);
			List<PaymentDetails> paymentDetailsList = billDAO.getPaymentDetailsList(fromDate, toDate, licenceId, billSourceId, contactId);
			
			
			List<Bill> billListOld = null;
			List<PaymentDetails> paymentDetailsListOld = null;
			
			List<PaymentDTOList> paymentDTOListOld = null;
			
			if(fromDate!=null && toDate!=null){
				billListOld = billDAO.getBillList(null, fromDate, licenceId, billSourceId, contactId,entryType);
				paymentDetailsListOld = billDAO.getPaymentDetailsList(null, fromDate, licenceId, billSourceId, contactId);
				paymentDTOListOld = new ArrayList<PaymentDTOList>();
			}
			
			paymentDTO.setBillList(billDAO.getBillList(null, null, licenceId, billSourceId, contactId,entryType));
			paymentDTO.setPaymentDetailsList(billDAO.getPaymentDetailsList(null, null, licenceId, billSourceId, contactId));
			//Fetch oldBill list if any and old payment list if any -Starts
			
			if(billListOld!=null){
				if(billListOld!=null && billListOld.size()>0){
					for(Bill obj:billListOld){
						PaymentDTOList list = new PaymentDTOList();
						
						list.setRecordId(obj.getBillId());
						list.setRecordType(ConstantUtils.BILL);
						
						if(obj.getBillDate()!=null){
							//list.setBillDate(StringUtils.getStrDate(obj.getBillDate()));
							list.setBillDate(obj.getBillDate());
							list.setDateForSort(obj.getBillDate());
						}
						list.setBillNumber(obj.getBillNumber());
						if(obj.getWeight()!=null){
							list.setWeight(obj.getWeight());
						}
						
						if(obj.getMetalBalance()!=null){
							list.setMetalBalance(obj.getMetalBalance());
						}
						
						if(obj.getAmount()!=null){
							list.setBillingAmount(obj.getTotalAmount());
						}
						if(obj.getMetal()!=null) {
							list.setMetal(obj.getMetal());
						}
						paymentDTOListOld.add(list);
					}
				}
			}
			if(paymentDTOListOld!=null){
				if(paymentDetailsListOld!=null && paymentDetailsListOld.size()>0){
					for(PaymentDetails obj:paymentDetailsListOld){
						PaymentDTOList list = new PaymentDTOList();
						
						if(obj.getAmountPaid()!=null){
							list.setPaymentAmount(obj.getAmountPaid());
						}
						
						if(obj.getPaymentDate()!=null){
							list.setPaymentDate(obj.getPaymentDate());
							list.setDateForSort(obj.getPaymentDate());
						}
						
						if(obj.getMetalPaid()!=null){
							list.setPaymentMetal(obj.getMetalPaid());
						}
						
						list.setRecordId(obj.getPaymentDetailsId());
						list.setRecordType(ConstantUtils.PAYMENT);
						
						if(obj.getMetal()!=null) {
							list.setMetal(obj.getMetal());
						}
						
						paymentDTOListOld.add(list);
						
					}
					
				}
			}
			
			double totalAmountBalanceOld = 0.0;
			double totalMetalBalanceOld = 0.0;
			double totalAmountPaidOld = 0.0;
			double totalMetalPaidOld = 0.0;
			
			if(paymentDTOListOld!=null && paymentDTOListOld.size()>0){
			totalAmountBalanceOld = paymentDTOListOld.stream().filter(o -> o.getBillingAmount()!=null).mapToDouble(PaymentDTOList::getBillingAmount).sum();
			totalMetalBalanceOld = paymentDTOListOld.stream().filter(o -> o.getMetalBalance()!=null).mapToDouble(PaymentDTOList::getMetalBalance).sum();
			
			totalAmountPaidOld = paymentDTOListOld.stream().filter(o -> o.getPaymentAmount()!=null).mapToDouble(PaymentDTOList::getPaymentAmount).sum();
			totalMetalPaidOld = paymentDTOListOld.stream().filter(o -> o.getPaymentMetal()!=null).mapToDouble(PaymentDTOList::getPaymentMetal).sum();
			
			PaymentDTOList oldList = new PaymentDTOList();
			
			oldList.setRecordId(-1);
			oldList.setRecordType(ConstantUtils.OPENING);
			oldList.setDateForSort(new SimpleDateFormat("dd/MM/yyyy").parse("01/01/1900"));
			oldList.setBillNumber(ConstantUtils.OPENING);
			
			oldList.setMetalBalance(totalMetalBalanceOld);
			oldList.setBillingAmount(totalAmountBalanceOld);
			oldList.setPaymentMetal(totalMetalPaidOld);
			oldList.setPaymentAmount(totalAmountPaidOld);
			
			paymentDTOList.add(oldList);
			}
			
			
			System.out.println("totalAmountBalanceOld = "+totalAmountBalanceOld);
			System.out.println("totalMetalBalanceOld = "+totalMetalBalanceOld);
			System.out.println("totalAmountPaidOld = "+totalAmountPaidOld);
			System.out.println("totalMetalPaidOld = "+totalMetalPaidOld);
			//Fetch oldBill list if any and old payment list if any -Ends

			if(billList!=null && billList.size()>0){
				for(Bill obj:billList){
					PaymentDTOList list = new PaymentDTOList();
					
					list.setRecordId(obj.getBillId());
					list.setRecordType(ConstantUtils.BILL);
					
					if(obj.getBillDate()!=null){
						//list.setBillDate(StringUtils.getStrDate(obj.getBillDate()));
						list.setBillDate(obj.getBillDate());
						list.setDateForSort(obj.getBillDate());
					}
					list.setBillNumber(obj.getBillNumber());
					if(obj.getWeight()!=null){
						list.setWeight(obj.getWeight());
					}
					
					if(obj.getMetalBalance()!=null){
						list.setMetalBalance(obj.getMetalBalance());
					}
					
					if(obj.getAmount()!=null){
						list.setBillingAmount(obj.getTotalAmount());
					}
					
					if(obj.getMetal()!=null) {
						list.setMetal(obj.getMetal());
					}
					
					paymentDTOList.add(list);
				}
			}
			
			
			//payment Starts
			if(paymentDetailsList!=null && paymentDetailsList.size()>0){
				for(PaymentDetails obj:paymentDetailsList){
					PaymentDTOList list = new PaymentDTOList();
					//list.setBillNumber("");
					if(obj.getAmountPaid()!=null){
						list.setPaymentAmount(obj.getAmountPaid());
					}
					
					if(obj.getPaymentDate()!=null){
						list.setPaymentDate(obj.getPaymentDate());
						list.setDateForSort(obj.getPaymentDate());
					}
					
					if(obj.getMetalPaid()!=null){
						list.setPaymentMetal(obj.getMetalPaid());
					}
					
					list.setRecordId(obj.getPaymentDetailsId());
					list.setRecordType(ConstantUtils.PAYMENT);
					
					if(obj.getMetal()!=null) {
						list.setMetal(obj.getMetal());
					}
					
					paymentDTOList.add(list);
					
				}
			}
			//Payment Ends
			
			//Implement collection sort based upon date
			
			//CommonComparator.paymentDateSort
			//Collections.sort(paymentDTOList,CommonComparator.paymentDateSort);
			//persons.sort(Comparator.comparing(Person::getName).thenComparing(Person::getAge));
			paymentDTOList.sort(Comparator.comparing(PaymentDTOList::getDateForSort).thenComparing(PaymentDTOList::getBillNumber,Comparator.nullsLast(Comparator.naturalOrder())));
			//Collections.sort
			/*
			Comparator<PaymentDTOList> byDate = new Comparator<PaymentDTOList>() {
			      public int compare(PaymentDTOList c1, PaymentDTOList c2) {
			        return (c1.getBillDate()).compareTo(c2.getPaymentDate());
			      }
			    };
			Collections.sort(paymentDTOList,byDate);
			*/
			//Collection sort ends
			
			//paymentDTO.setPaymentDTOList(paymentDTOList);
			
			
			
			//Calculation starts
			//.filter(o -> o.field > 10)
			
			if(paymentDTOList!=null){
				double totalAmountBalance = paymentDTOList.stream().filter(o -> o.getBillingAmount()!=null).mapToDouble(PaymentDTOList::getBillingAmount).sum();
				double totalMetalBalance = paymentDTOList.stream().filter(o -> o.getMetalBalance()!=null).mapToDouble(PaymentDTOList::getMetalBalance).sum();
				
				double totalAmountPaid = paymentDTOList.stream().filter(o -> o.getPaymentAmount()!=null).mapToDouble(PaymentDTOList::getPaymentAmount).sum();
				double totalMetalPaid = paymentDTOList.stream().filter(o -> o.getPaymentMetal()!=null).mapToDouble(PaymentDTOList::getPaymentMetal).sum();
				
				Double amountBalance = Calculator.getTwoDigitDecimal(totalAmountBalance-totalAmountPaid);
				Double metalBalance = Calculator.getThreeDigitDecimal(totalMetalBalance-totalMetalPaid);
	
				paymentDTO.setTotalAmountBalance(totalAmountBalance);
				paymentDTO.setTotalMetalBalance(totalMetalBalance);
				paymentDTO.setTotalAmountPaid(totalAmountPaid);
				paymentDTO.setTotalMetalPaid(totalMetalPaid);
				paymentDTO.setAmountBalance(amountBalance);
				paymentDTO.setMetalBalance(metalBalance);
				
				PaymentDTOList totalList = new PaymentDTOList();
				
				totalList.setRecordId(-1);
				totalList.setRecordType(ConstantUtils.TOTAL);
				totalList.setDateForSort(new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2100"));
				
				totalList.setMetalBalance(totalMetalBalance);
				totalList.setBillingAmount(totalAmountBalance);
				
				totalList.setPaymentMetal(totalMetalPaid);
				totalList.setPaymentAmount(totalAmountPaid);
				
				
				
				try {
				Map<Metal, List<PaymentDTOList>> rawMap = paymentDTOList.stream().filter(o-> o.getMetal()!=null).collect(Collectors.groupingBy(PaymentDTOList::getMetal,Collectors.toList()));
				
				if(rawMap!=null && rawMap.size()>0) {
					HashMap<Metal, List<PaymentDTOList>> map
		            = rawMap.entrySet()
		                  .stream()
		                  .sorted((i1, i2)
		                              -> i1.getKey().getMetalName().compareTo(
		                                  i2.getKey().getMetalName()))
		                  .collect(Collectors.toMap(
		                      Map.Entry::getKey,
		                      Map.Entry::getValue,
		                      (e1, e2) -> e1, LinkedHashMap::new));
					
					StringBuffer metalBalanceStr = new StringBuffer();
					StringBuffer totalMetalBalanceStr = new StringBuffer();
					StringBuffer totalMetalPaidStr = new StringBuffer();
					
					if(map!=null && map.size()>0) {
					map.forEach((k,v)->{
						List<PaymentDTOList> listObj = v;
						double totalMetalBalanceTemp = listObj.stream().filter(o -> o!=null && o.getMetalBalance()!=null).mapToDouble(PaymentDTOList::getMetalBalance).sum();
						double totalMetalPaidTemp = listObj.stream().filter(o -> o!=null && o.getPaymentMetal()!=null).mapToDouble(PaymentDTOList::getPaymentMetal).sum();
						
						Double metalBalanceTemp = Calculator.getThreeDigitDecimal(totalMetalBalanceTemp-totalMetalPaidTemp);
						
						if(metalBalanceStr.length()==0) {
							metalBalanceStr.append(k).append("-").append(
									Calculator.formatTwoDecimal(metalBalanceTemp));
						}else {
							metalBalanceStr.append(", ").append(k).append("-").append(
									Calculator.formatTwoDecimal(metalBalanceTemp));
						}
						
						if(totalMetalBalanceStr.length()==0) {
							totalMetalBalanceStr.append(k).append("-").append(
									Calculator.formatTwoDecimal(totalMetalBalanceTemp));
						}else {
							totalMetalBalanceStr.append(", ").append(k).append("-").append(
									Calculator.formatTwoDecimal(totalMetalBalanceTemp));
						}
						
						if(totalMetalPaidStr.length()==0) {
							totalMetalPaidStr.append(k).append("-").append(
									Calculator.formatTwoDecimal(totalMetalPaidTemp));
						}else {
							totalMetalPaidStr.append(", ").append(k).append("-").append(
									Calculator.formatTwoDecimal(totalMetalPaidTemp));
						}
						
						
					});
					
					paymentDTO.setMetalBalanceStr(metalBalanceStr.toString());
					//paymentDTO.setTotalMetalBalanceStr(totalMetalBalanceStr.toString());
					//paymentDTO.setTotalMetalPaidStr(totalMetalPaidStr.toString());
					
					totalList.setMetalBalanceStr(metalBalanceStr.toString());
					totalList.setTotalMetalBalanceStr(totalMetalBalanceStr.toString());
					totalList.setTotalMetalPaidStr(totalMetalPaidStr.toString());
					}
				}
			}catch(Exception ex) {
				ex.printStackTrace();
			}
				
			paymentDTOList.add(totalList);
			}
			
			
			
			
			//Calculation Ends
			
			
			paymentDTO.setPaymentDTOList(paymentDTOList);
			
			//Get the last billed date, payment date-Starts
			if(paymentDTOList!=null && paymentDTOList.size()>0) {
				Optional <Date> lastBilledDate =null;
				if(billList!=null && billList.size()>0) {
					lastBilledDate = Optional.ofNullable(paymentDTOList.stream().filter(payment -> payment.getRecordType().equals(ConstantUtils.BILL)).map(PaymentDTOList::getBillDate).max(Date::compareTo).get());
				}
				Optional <Date> lastPaymentDate = null;
				if(paymentDetailsList!=null && paymentDetailsList.size()>0) {
				lastPaymentDate = Optional.ofNullable(paymentDTOList.stream().filter(payment -> payment.getRecordType().equals(ConstantUtils.PAYMENT)).map(PaymentDTOList::getPaymentDate).max(Date::compareTo).get());
				}
				
				if(lastBilledDate!=null && lastBilledDate.isPresent()) {
					paymentDTO.setLastBilledDate(lastBilledDate.get());
				}
				if(lastPaymentDate!=null && lastPaymentDate.isPresent()) {
					paymentDTO.setLastPaymentDate(lastPaymentDate.get());
				}
			}
			//Get the last billed date, payment date-Ends
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return paymentDTO;
	}

	@Override
	public PaymentDetails saveOrUpdatePaymentDetails(PaymentDetails payment) {
		// TODO Auto-generated method stub
		return billDAO.saveOrUpdatePaymentDetails(payment);
	}

	@Override
	public List<PaymentDetails> getPaymentDetailsList(Date fromDate, Date toDate, Integer licenceId,
			Integer billSourceId, Integer contactId) {
		// TODO Auto-generated method stub
		/*List<PaymentDetails> paymentDetailsList = null;
		List<PaymentDetails> paymentDetailsCurrentList = null;
		List<PaymentDetails> paymentDetailsOldList = null;
		
		try {
			paymentDetailsList = new ArrayList<PaymentDetails>();
			
			if(fromDate!=null && toDate!=null) {
				paymentDetailsCurrentList = billDAO.getPaymentDetailsList(fromDate, toDate, licenceId, billSourceId, contactId);
				paymentDetailsOldList = billDAO.getPaymentDetailsList(null, fromDate, licenceId, billSourceId, contactId);
				
				if(paymentDetailsOldList!=null && paymentDetailsOldList.size()>0) {
					
				}
				
				paymentDetailsList.addAll(paymentDetailsCurrentList);
				
			}
			
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}*/
		return billDAO.getPaymentDetailsList(fromDate, toDate, licenceId, billSourceId, contactId);
	}

	@Override
	public PaymentDetails getPaymentDetails(Integer paymentDetailsId) {
		// TODO Auto-generated method stub
		return billDAO.getPaymentDetails(paymentDetailsId);
	}

	@Override
	public Integer deleteBill(Integer billId) {
		// TODO Auto-generated method stub
		Integer deletedFlag=0;
		try{
			Bill billObj=null;
			billObj = getBill(billId);
			
			
			if(billObj!=null){
				billObj.setDeletedFlag(ConstantUtils.CONSTANT_Y);
				if(billObj.getBillDetails()!=null){
					for(BillDetails obj : billObj.getBillDetails()){
						obj.setDeletedFlag(ConstantUtils.CONSTANT_Y);
						billObj.addBillDetailList(obj);
					}
				}
				
				billObj = billDAO.saveOrUpdateBill(billObj);
				
				if(billObj!=null && billObj.getDeletedFlag().equalsIgnoreCase(ConstantUtils.CONSTANT_Y)){
					deletedFlag=1;
				}
			}
			
		}catch(Exception ex){
			
		}
		return deletedFlag;
	}

	@Override
	public BillDTO getReturnBillItems(Date fromDate, Date toDate, Integer licenceId, String entryType) {
		// TODO Auto-generated method stub
		BillDTO billDTO = null;
		List<Bill> billListSales = null;
		List<Bill> billListPurchase = null;
		List<Bill> billList = null;
		try {
			billDTO = new BillDTO();
			Integer billSourceIdSales=0;
			Integer billSourceIdPurchase=0;
			
			if(entryType.equalsIgnoreCase(ConstantUtils.GST_ENTRY)) {
				billSourceIdSales = masterService.getBillSourceByCode(ConstantUtils.WHOLESALE_BILLING_GST).getBillSourceId();
				billSourceIdPurchase = masterService.getBillSourceByCode(ConstantUtils.PURCHASE_BILLING_GST).getBillSourceId();
			}else if(entryType.equalsIgnoreCase(ConstantUtils.NON_GST_ENTRY)) {
				billSourceIdSales = masterService.getBillSourceByCode(ConstantUtils.WHOLESALE_BILLING_NONGST).getBillSourceId();
				billSourceIdPurchase = masterService.getBillSourceByCode(ConstantUtils.PURCHASE_BILLING_NONGST).getBillSourceId();
			}
			
			
			billListSales = billDAO.getBillList(fromDate, toDate, licenceId, billSourceIdSales, null, null);
			billListPurchase = billDAO.getBillList(fromDate, toDate, licenceId, billSourceIdPurchase, null, null);

			
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return billDTO;
	}

	@Override
	public PaymentDTO getPaymentDTODetailsNew(Date fromDate, Date toDate, Integer billSourceId, Integer contactId,
			Integer licenceId, String entryType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ContactTransactionDTO getContactTransactionDetails(Date fromDate, Date toDate, String billSource,
			Integer contactId, Integer licenceId, String entryType) {
		// TODO Auto-generated method stub
		ContactTransactionDTO contactTransDTO =null;
		try {
			contactTransDTO = billDAO.getContactTransactionDetails(fromDate, toDate, billSource, contactId, licenceId, entryType);
			
			System.out.println("Contact Transaction Data : "+new GsonBuilder().serializeNulls().setPrettyPrinting().create().toJson(contactTransDTO));
			
			
			
			String openingBalanceStr="";			
			StringBuffer openingMetalBalanceStr= new StringBuffer();
			
			String totalBillAmountDueStr = "";
			StringBuffer totalBillMetalDueStr = new StringBuffer();
			
			String totalAmountPaidStr="";
			StringBuffer totalMetalPaidStr = new StringBuffer();
			
			String currentAmountBalanceStr="";
			StringBuffer currentMetalBalanceStr = new StringBuffer();
			
			
			if(contactTransDTO!=null && contactTransDTO.getContactTransactionList()!=null){
				double openingBalance = 0.0;
				double openingPaidAmount = 0.0;
				
				double totalBillAmountDue = 0.0;
				double totalAmountPaid = 0.0;
				
				//Calculate Opening Balance for the customer i.e. Opening balance configured while creating the contact plus previous balance before the search i.e carry forward amount - Starts
				// and subtract the previous payment amount i.e. before the from date of the search criteria
				openingBalance = contactTransDTO.getContactTransactionList().stream()
						.filter(s->s.getOldAmountDue()!=null && s.gettType().equalsIgnoreCase(ConstantUtils.OPENING) || s.gettType().equalsIgnoreCase(ConstantUtils.OLD_BILL))
						.mapToDouble(ContactTransactionDTOList::getOldAmountDue).sum();
				
				openingPaidAmount = contactTransDTO.getContactTransactionList().stream()
						.filter(s->s.getOldAmountPaid()!=null && s.gettType().equalsIgnoreCase(ConstantUtils.OLD_PAYMENT))
						.mapToDouble(ContactTransactionDTOList::getOldAmountPaid).sum();
				
				openingBalanceStr = Calculator.formatTwoDecimal(openingBalance-openingPaidAmount);
				//Calculate Opening Balance for the customer i.e. Opening balance configured while creating the contact plus previous balance before the search i.e carry forward amount - Ends
				//and subtract the previous payment amount i.e. before the from date of the search criteria - End
				
				//Calculate the total Bill amount for the given date range -Starts
				totalBillAmountDue = contactTransDTO.getContactTransactionList().stream()
						.filter(s->s.getBillAmount()!=null && s.gettType().equalsIgnoreCase(ConstantUtils.BILL))
						.mapToDouble(ContactTransactionDTOList::getBillAmount).sum();
				
				totalBillAmountDueStr = Calculator.formatTwoDecimal(totalBillAmountDue);
				//Calculate the total Bill amount for the given date range -Ends
				
				//Calculate the total payment amount for the given date range -Starts
				totalAmountPaid = contactTransDTO.getContactTransactionList().stream()
						.filter(s->s.getPaymentAmountPaid()!=null && s.gettType().equalsIgnoreCase(ConstantUtils.PAYMENT))
						.mapToDouble(ContactTransactionDTOList::getPaymentAmountPaid).sum();
				totalAmountPaidStr = Calculator.formatTwoDecimal(totalAmountPaid);
				//Calculate the total payment amount for the given date range -Ends
				
				//Calculate current balance by adding opening balance plus total due amount - total old payment amount - total current payment amount  for the given date range -Starts
				currentAmountBalanceStr = Calculator.formatTwoDecimal(openingBalance-openingPaidAmount+totalBillAmountDue-totalAmountPaid);
				//Calculate current balance by adding opening balance plus total due amount - total old payment amount - total current payment amount  for the given date range -Ends
				
				
				//Group the transaction data by Metal Names - Starts
						
				Map<String, List<ContactTransactionDTOList>> metalMap = contactTransDTO.getContactTransactionList().stream()
						.filter(f -> f.getMetalName()!=null && !f.getMetalName().equalsIgnoreCase(ConstantUtils.BLANK))
						.collect(Collectors.groupingBy(ContactTransactionDTOList::getMetalName,Collectors.toList()));
				//Group the transaction data by Metal Names - Ends
				
				metalMap.forEach((metal,transacationList)->{
					//Opening Starts
					double openingMetalBalanceDue = transacationList.stream()
							.filter(s->(s.getOldMetalDue()!=null) 
									&& (s.gettType().equalsIgnoreCase(ConstantUtils.OPENING) || s.gettType().equalsIgnoreCase(ConstantUtils.OLD_BILL)))
							.mapToDouble(ContactTransactionDTOList::getOldMetalDue).sum();
					
					
					double openingMetalBalancePaid = transacationList.stream()
							.filter(s->(s.getOldMetalPaid()!=null) 
									&& (s.gettType().equalsIgnoreCase(ConstantUtils.OLD_PAYMENT)))
							.mapToDouble(ContactTransactionDTOList::getOldMetalPaid).sum();
					
					
					
					if(openingMetalBalanceStr.length()==0) {
						openingMetalBalanceStr.append(metal).append(" : ").append(
							Calculator.formatTwoDecimal(openingMetalBalanceDue-openingMetalBalancePaid));
					}else {
						openingMetalBalanceStr.append(", ").append(metal).append(" : ").append(
							Calculator.formatTwoDecimal(openingMetalBalanceDue-openingMetalBalancePaid));
					}
					//Opening Ends
					
					//Billing Starts
					double totalBillMetalBalanceDue=transacationList.stream()
							.filter(s->(s.getBillMetalBalance()!=null)//&& s.getBillMetalBalance()>0.0 
									&& (s.gettType().equalsIgnoreCase(ConstantUtils.BILL)))
							.mapToDouble(ContactTransactionDTOList::getBillMetalBalance).sum();
					
					if(totalBillMetalDueStr.length()==0) {
						totalBillMetalDueStr.append(metal).append(" : ").append(
							Calculator.formatTwoDecimal(totalBillMetalBalanceDue));
					}else {
						totalBillMetalDueStr.append(", ").append(metal).append(" : ").append(
							Calculator.formatTwoDecimal(totalBillMetalBalanceDue));
					}
					//Billing Ends
					
					//Payment Starts
					double totalMetalPaid = transacationList.stream()
							.filter(s->(s.getPaymentMetalPaid()!=null)// && s.getPaymentMetalPaid()>0.0 
									&& (s.gettType().equalsIgnoreCase(ConstantUtils.PAYMENT)))
							.mapToDouble(ContactTransactionDTOList::getPaymentMetalPaid).sum();
					
					if(totalMetalPaidStr.length()==0) {
						totalMetalPaidStr.append(metal).append(" : ").append(
							Calculator.formatTwoDecimal(totalMetalPaid));
					}else {
						totalMetalPaidStr.append(", ").append(metal).append(" : ").append(
							Calculator.formatTwoDecimal(totalMetalPaid));
					}
					//Payment Ends
					//Current Balance Starts
					if(currentMetalBalanceStr.length()==0) {
						currentMetalBalanceStr.append(metal).append(" : ").append(
							Calculator.formatTwoDecimal(openingMetalBalanceDue-openingMetalBalancePaid+totalBillMetalBalanceDue-totalMetalPaid));
					}else {
						currentMetalBalanceStr.append(", ").append(metal).append(" : ").append(
							Calculator.formatTwoDecimal(openingMetalBalanceDue-openingMetalBalancePaid+totalBillMetalBalanceDue-totalMetalPaid));
					}
					//Current Balance Ends					
				});
			
				System.out.println("metalMap Data : "+new GsonBuilder().setPrettyPrinting().create().toJson(metalMap));
			}
			
			
			
			System.out.println("openingMetalBalanceStr : "+openingMetalBalanceStr+". openingBalanceStr "+openingBalanceStr);
			System.out.println("totalBillAmountDueStr : "+totalBillAmountDueStr+". totalAmountPaidStr "+totalAmountPaidStr);
			System.out.println("totalBillMetalDueStr : "+totalBillMetalDueStr+". totalMetalPaidStr "+totalMetalPaidStr);
			System.out.println("currentAmountBalanceStr : "+currentAmountBalanceStr+". currentMetalBalanceStr "+currentMetalBalanceStr);

			
			contactTransDTO.setContactId(contactId);
			contactTransDTO.setContactName("");
			contactTransDTO.setOpeningBalance(openingBalanceStr);
			contactTransDTO.setOpeningMetalBlance(openingMetalBalanceStr.toString());
			contactTransDTO.setTotalBillAmountDue(totalBillAmountDueStr);
			contactTransDTO.setTotalBillMetalDue(totalBillMetalDueStr.toString());
			contactTransDTO.setTotalAmountPaid(totalAmountPaidStr);
			contactTransDTO.setTotalMetalPaid(totalMetalPaidStr.toString());
			contactTransDTO.setCurrentAmountBalance(currentAmountBalanceStr);
			contactTransDTO.setCurrentMetalBalance(currentMetalBalanceStr.toString());
			
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		System.out.println("Response Data : contactTransDTO Data : "+new GsonBuilder().setPrettyPrinting().create().toJson(contactTransDTO));
		return contactTransDTO;
	}


}
