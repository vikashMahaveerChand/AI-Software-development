package com.inventory.dao;

import java.util.Date;
import java.util.List;

import com.inventory.dto.ContactTransactionDTO;
import com.inventory.model.Bill;
import com.inventory.model.BillNumber;
import com.inventory.model.Conversion;
import com.inventory.model.PaymentDetails;

public interface BillDAO {
	
	public Bill saveOrUpdateBill(Bill bill);
	public List<Bill> getBillList(Date fromDate,Date toDate,Integer licenceId,Integer billSourceId,Integer contactId,String entryType);
	public Bill getBill(Integer billId);
	public Boolean updateClosingBalance(String closingCashBalance,String closingMetalBalance,Integer billId);
	
	public BillNumber getBillNumber(Integer licenceId,Integer billSourceId);
	public BillNumber saveOrUpdateBillNumber(BillNumber billNumber);

	public PaymentDetails saveOrUpdatePaymentDetails(PaymentDetails payment);
	public List<PaymentDetails> getPaymentDetailsList(Date fromDate,Date toDate,Integer licenceId,Integer billSourceId,Integer contactId);
	public PaymentDetails getPaymentDetails(Integer paymentDetailsId);
	
	public List<PaymentDetails> getBankPaymentDetails(Date fromDate,Date toDate,Integer bankId);
	
	public Conversion saveOrUpdateConversion(Conversion conversion);
	public Conversion getConversion(Integer conversionId);
	public List<Conversion> getConversionList(Date fromDate,Date toDate,Integer licenceId,Integer billSourceId,Integer contactId);
	
	public ContactTransactionDTO getContactTransactionDetails(Date fromDate,Date toDate,String billSource,Integer contactId,Integer licenceId,String entryType);
	
}
