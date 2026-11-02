package com.inventory.dto;

import java.util.Date;

public class ContactTransactionDTOList {
	
	//Common Starts
	private String tType;
	private Integer contactId;
	private String contactName;
	private String metalName;
 	//Common Ends
	
	//For Bill Fields Starts
	private Integer billId;
	private Date billDate;
	private String billNumber;
	private Double billWeight;
	private Double billMetalBalance;
	//private String billMetalName;
	private Double billAmount;
	//For Bill Fields Ends
	
	//For Payment Fields Starts
	private Integer paymentId;
	private Date paymentDate;
	private Double paymentMetalPaid;
	//private String paymentMetalName;
	private Double paymentAmountPaid;
	//For Payment Fields Ends
	
	//For Opening and old data fields Starts
	private Double oldAmountDue;
	private Double oldMetalDue;
	
	private Double oldAmountPaid;
	private Double oldMetalPaid;
	
	//private String oldMetalName;
	
	//For Opening and old data fields Ends
	
	
	public String gettType() {
		return tType;
	}
	public void settType(String tType) {
		this.tType = tType;
	}
	public Integer getBillId() {
		return billId;
	}
	public void setBillId(Integer billId) {
		this.billId = billId;
	}
	public Date getBillDate() {
		return billDate;
	}
	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}
	public String getBillNumber() {
		return billNumber;
	}
	public void setBillNumber(String billNumber) {
		this.billNumber = billNumber;
	}
	public Double getBillWeight() {
		return billWeight;
	}
	public void setBillWeight(Double billWeight) {
		this.billWeight = billWeight;
	}
	public Double getBillMetalBalance() {
		return billMetalBalance;
	}
	public void setBillMetalBalance(Double billMetalBalance) {
		this.billMetalBalance = billMetalBalance;
	}
	
	public Double getBillAmount() {
		return billAmount;
	}
	public void setBillAmount(Double billAmount) {
		this.billAmount = billAmount;
	}
	public Date getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}
	public Double getPaymentMetalPaid() {
		return paymentMetalPaid;
	}
	public void setPaymentMetalPaid(Double paymentMetalPaid) {
		this.paymentMetalPaid = paymentMetalPaid;
	}
	public Double getPaymentAmountPaid() {
		return paymentAmountPaid;
	}
	public void setPaymentAmountPaid(Double paymentAmountPaid) {
		this.paymentAmountPaid = paymentAmountPaid;
	}
	public Integer getContactId() {
		return contactId;
	}
	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public Integer getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(Integer paymentId) {
		this.paymentId = paymentId;
	}
	public Double getOldAmountDue() {
		return oldAmountDue;
	}
	public void setOldAmountDue(Double oldAmountDue) {
		this.oldAmountDue = oldAmountDue;
	}
	public Double getOldMetalDue() {
		return oldMetalDue;
	}
	public void setOldMetalDue(Double oldMetalDue) {
		this.oldMetalDue = oldMetalDue;
	}
	public Double getOldAmountPaid() {
		return oldAmountPaid;
	}
	public void setOldAmountPaid(Double oldAmountPaid) {
		this.oldAmountPaid = oldAmountPaid;
	}
	public Double getOldMetalPaid() {
		return oldMetalPaid;
	}
	public void setOldMetalPaid(Double oldMetalPaid) {
		this.oldMetalPaid = oldMetalPaid;
	}
	public String getMetalName() {
		return metalName;
	}
	public void setMetalName(String metalName) {
		this.metalName = metalName;
	}
	
	
	

}
