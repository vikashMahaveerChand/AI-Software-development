package com.inventory.service;

import java.util.Date;
import java.util.List;

import com.inventory.dto.BillDTO;
import com.inventory.dto.ContactTransactionDTO;
import com.inventory.dto.PaymentDTO;
import com.inventory.model.Bill;
import com.inventory.model.BillNumber;
import com.inventory.model.PaymentDetails;

public interface BillService {

	public Bill saveOrUpdateBill(Bill bill);
	public BillDTO getBillList(Date fromDate,Date toDate,Integer licenceId,Integer billSourceId,Integer contactId,String entryType);
	public Bill getBill(Integer billId);
	public Integer deleteBill(Integer billId);
	
	public BillNumber getBillNumber(Integer licenceId,Integer billSourceId);
	public BillNumber saveOrUpdateBillNumber(BillNumber billNumber);
	
	public PaymentDTO getPaymentDTODetails(Date fromDate,Date toDate,Integer billSourceId,Integer contactId,Integer licenceId,String entryType);
	public PaymentDTO getPaymentDTODetailsNew(Date fromDate,Date toDate,Integer billSourceId,Integer contactId,Integer licenceId,String entryType);
	
	public PaymentDetails saveOrUpdatePaymentDetails(PaymentDetails payment);
	public List<PaymentDetails> getPaymentDetailsList(Date fromDate,Date toDate,Integer licenceId,Integer billSourceId,Integer contactId);
	public PaymentDetails getPaymentDetails(Integer paymentDetailsId);
	
	public void updateBillNumber(BillNumber billNumber);
	
	public BillDTO getReturnBillItems(Date fromDate,Date toDate,Integer licenceId,String entryType);
	public ContactTransactionDTO getContactTransactionDetails(Date fromDate,Date toDate,String billSource,Integer contactId,Integer licenceId,String entryType);
}
