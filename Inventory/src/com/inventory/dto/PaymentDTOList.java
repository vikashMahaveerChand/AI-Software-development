package com.inventory.dto;

import java.util.Date;

import com.inventory.model.Metal;

public class PaymentDTOList {
	
	private Integer recordId;
	private String recordType;
	private Date dateForSort;
	
	private Date billDate;String billDateStr;
	private String billNumber;
	private Double weight;String weightStr;
	private Double metalBalance;String metalBalanceStr;
	private Double billingAmount;String billingAmountStr;
	
	private Date paymentDate;String paymentDateStr;
	private Double paymentMetal;String paymentMetalStr;
	private Double paymentAmount;String paymentAmountStr;
	
	private Metal metal;
	private String totalMetalBalanceStr;
	private String totalMetalPaidStr;

	public Integer getRecordId() {
		return recordId;
	}
	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}
	public String getRecordType() {
		return recordType;
	}
	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}
	public Date getBillDate() {
		return billDate;
	}
	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	public Double getBillingAmount() {
		return billingAmount;
	}
	public void setBillingAmount(Double billingAmount) {
		this.billingAmount = billingAmount;
	}
	public Date getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}
	public Double getPaymentMetal() {
		return paymentMetal;
	}
	public void setPaymentMetal(Double paymentMetal) {
		this.paymentMetal = paymentMetal;
	}
	public Double getPaymentAmount() {
		return paymentAmount;
	}
	public void setPaymentAmount(Double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	public Double getMetalBalance() {
		return metalBalance;
	}
	public void setMetalBalance(Double metalBalance) {
		this.metalBalance = metalBalance;
	}
	public String getBillNumber() {
		return billNumber;
	}
	public void setBillNumber(String billNumber) {
		this.billNumber = billNumber;
	}
	public Date getDateForSort() {
		return dateForSort;
	}
	public void setDateForSort(Date dateForSort) {
		this.dateForSort = dateForSort;
	}
	public String getBillDateStr() {
		return billDateStr;
	}
	public void setBillDateStr(String billDateStr) {
		this.billDateStr = billDateStr;
	}
	public String getWeightStr() {
		return weightStr;
	}
	public void setWeightStr(String weightStr) {
		this.weightStr = weightStr;
	}
	public String getMetalBalanceStr() {
		return metalBalanceStr;
	}
	public void setMetalBalanceStr(String metalBalanceStr) {
		this.metalBalanceStr = metalBalanceStr;
	}
	public String getBillingAmountStr() {
		return billingAmountStr;
	}
	public void setBillingAmountStr(String billingAmountStr) {
		this.billingAmountStr = billingAmountStr;
	}
	public String getPaymentDateStr() {
		return paymentDateStr;
	}
	public void setPaymentDateStr(String paymentDateStr) {
		this.paymentDateStr = paymentDateStr;
	}
	public String getPaymentMetalStr() {
		return paymentMetalStr;
	}
	public void setPaymentMetalStr(String paymentMetalStr) {
		this.paymentMetalStr = paymentMetalStr;
	}
	public String getPaymentAmountStr() {
		return paymentAmountStr;
	}
	public void setPaymentAmountStr(String paymentAmountStr) {
		this.paymentAmountStr = paymentAmountStr;
	}
	public Metal getMetal() {
		return metal;
	}
	public void setMetal(Metal metal) {
		this.metal = metal;
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
