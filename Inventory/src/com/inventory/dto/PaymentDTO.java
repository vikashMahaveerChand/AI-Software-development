package com.inventory.dto;

import java.util.Date;
import java.util.List;

import com.inventory.model.Bill;
import com.inventory.model.Contact;
import com.inventory.model.PaymentDetails;

public class PaymentDTO {
	
	private Contact contact;
	private Double totalAmountBalance;
	private Double totalAmountPaid;
	private Double amountBalance;
	
	private Double totalMetalBalance;
	private Double totalMetalPaid;
	private Double metalBalance;
	
	private List<PaymentDTOList> paymentDTOList;
	
	private Date lastBilledDate;
	private Date lastPaymentDate;
	
	List<Bill> billList;
	List<PaymentDetails> paymentDetailsList;

	private String metalBalanceStr;
	private String totalMetalBalanceStr;
	private String totalMetalPaidStr;
	
	
	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public Double getTotalAmountBalance() {
		return totalAmountBalance;
	}

	public void setTotalAmountBalance(Double totalAmountBalance) {
		this.totalAmountBalance = totalAmountBalance;
	}

	public Double getTotalAmountPaid() {
		return totalAmountPaid;
	}

	public void setTotalAmountPaid(Double totalAmountPaid) {
		this.totalAmountPaid = totalAmountPaid;
	}

	public Double getAmountBalance() {
		return amountBalance;
	}

	public void setAmountBalance(Double amountBalance) {
		this.amountBalance = amountBalance;
	}

	public Double getTotalMetalBalance() {
		return totalMetalBalance;
	}

	public void setTotalMetalBalance(Double totalMetalBalance) {
		this.totalMetalBalance = totalMetalBalance;
	}

	public Double getTotalMetalPaid() {
		return totalMetalPaid;
	}

	public void setTotalMetalPaid(Double totalMetalPaid) {
		this.totalMetalPaid = totalMetalPaid;
	}

	public Double getMetalBalance() {
		return metalBalance;
	}

	public void setMetalBalance(Double metalBalance) {
		this.metalBalance = metalBalance;
	}

	public List<PaymentDTOList> getPaymentDTOList() {
		return paymentDTOList;
	}

	public void setPaymentDTOList(List<PaymentDTOList> paymentDTOList) {
		this.paymentDTOList = paymentDTOList;
	}

	public Date getLastBilledDate() {
		return lastBilledDate;
	}

	public void setLastBilledDate(Date lastBilledDate) {
		this.lastBilledDate = lastBilledDate;
	}

	public Date getLastPaymentDate() {
		return lastPaymentDate;
	}

	public void setLastPaymentDate(Date lastPaymentDate) {
		this.lastPaymentDate = lastPaymentDate;
	}

	public List<Bill> getBillList() {
		return billList;
	}

	public void setBillList(List<Bill> billList) {
		this.billList = billList;
	}

	public List<PaymentDetails> getPaymentDetailsList() {
		return paymentDetailsList;
	}

	public void setPaymentDetailsList(List<PaymentDetails> paymentDetailsList) {
		this.paymentDetailsList = paymentDetailsList;
	}

	public String getMetalBalanceStr() {
		return metalBalanceStr;
	}

	public void setMetalBalanceStr(String metalBalanceStr) {
		this.metalBalanceStr = metalBalanceStr;
	}

	public String getTotalMetalBalanceStr() {
		return totalMetalBalanceStr;
	}

	public void setTotalMetalBalanceStr(String totalMetalBalanceStr) {
		this.totalMetalBalanceStr = totalMetalBalanceStr;
	}

	public String getTotalMetalPaidStr() {
		return totalMetalPaidStr;
	}

	public void setTotalMetalPaidStr(String totalMetalPaidStr) {
		this.totalMetalPaidStr = totalMetalPaidStr;
	}
	
}
