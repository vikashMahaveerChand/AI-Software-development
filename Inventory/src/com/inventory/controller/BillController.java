package com.inventory.controller;

import java.util.Date;
import java.util.List;

import com.inventory.dto.BillDTO;
import com.inventory.dto.ContactTransactionDTO;
import com.inventory.dto.PaymentDTO;
import com.inventory.model.Bill;
import com.inventory.model.BillNumber;
import com.inventory.model.PaymentDetails;
import com.inventory.service.BillService;
import com.inventory.service.BillServiceImpl;

public class BillController {

	private static BillController myInstance = null;
	BillService billService = null;
	
	private BillController(){
		try{
			billService = BillServiceImpl.getInstace();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public static BillController getInstace(){
		if(myInstance == null){
			myInstance = new BillController();
		}
		return myInstance;
	}
	
	public Bill saveOrUpdateBill(Bill bill){
		return billService.saveOrUpdateBill(bill);
	}
	public BillDTO getBillList(Date fromDate,Date toDate,Integer licenceId,Integer billSourceId,Integer contactId,String entryType){
		return billService.getBillList(fromDate, toDate, licenceId, billSourceId, contactId,entryType);
	}
	public Bill getBill(Integer billId){
		return billService.getBill(billId);
	}
	
	public BillNumber getBillNumber(Integer licenceId,Integer billSourceId){
		return billService.getBillNumber(licenceId, billSourceId);
	}
	public BillNumber saveOrUpdateBillNumber(BillNumber billNumber){
		return billService.saveOrUpdateBillNumber(billNumber);
	}
	
	public PaymentDTO getPaymentDTODetails(Date fromDate,Date toDate,Integer billSourceId,Integer contactId,Integer licenceId,String entryType){
		return billService.getPaymentDTODetails(fromDate, toDate, billSourceId, contactId, licenceId,entryType);
	}
	
	public PaymentDTO getPaymentDTODetailsNew(Date fromDate,Date toDate,Integer billSourceId,Integer contactId,Integer licenceId,String entryType){
		return billService.getPaymentDTODetailsNew(fromDate, toDate, billSourceId, contactId, licenceId,entryType);
	}
	
	public PaymentDetails saveOrUpdatePaymentDetails(PaymentDetails payment){
		return billService.saveOrUpdatePaymentDetails(payment);
	}
	public List<PaymentDetails> getPaymentDetailsList(Date fromDate,Date toDate,Integer licenceId,Integer billSourceId,Integer contactId){
		return billService.getPaymentDetailsList(fromDate, toDate, licenceId, billSourceId, contactId);
	}
	
	public PaymentDetails getPaymentDetails(Integer paymentDetailsId){
		return billService.getPaymentDetails(paymentDetailsId);
	}
	
	public Integer deleteBill(Integer billId){
		return billService.deleteBill(billId);
	}
	
	public ContactTransactionDTO getContactTransactionDetails(Date fromDate, Date toDate, String billSource,
			Integer contactId, Integer licenceId, String entryType) {
		// TODO Auto-generated method stub
		return billService.getContactTransactionDetails(fromDate, toDate, billSource, contactId, licenceId, entryType);
	}
}
