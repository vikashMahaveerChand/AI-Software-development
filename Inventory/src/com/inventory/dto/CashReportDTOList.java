package com.inventory.dto;

import java.util.Date;

public class CashReportDTOList {
	
	private Integer cashReportId;
	private String recordType;// paid or received
	private String paymentType;//cash or metal
	private String billSource;
	private String metalName;
	
	private Date paymentDate;
	private String description;
	
	private Double amountReceived;
	private Double metalReceived;
	
	private Double goldMetalReceived;
	private Double silverMetalReceived;
	
		
	private Double amountPaid;
	private Double metalPaid;
	
	private Double goldMetalPaid;
	private Double silverMetalPaid;
	
	public Integer getCashReportId() {
		return cashReportId;
	}
	public void setCashReportId(Integer cashReportId) {
		this.cashReportId = cashReportId;
	}
	public String getRecordType() {
		return recordType;
	}
	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public String getMetalName() {
		return metalName;
	}
	public void setMetalName(String metalName) {
		this.metalName = metalName;
	}
	public Date getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Double getAmountReceived() {
		return amountReceived;
	}
	public void setAmountReceived(Double amountReceived) {
		this.amountReceived = amountReceived;
	}
	public Double getMetalReceived() {
		return metalReceived;
	}
	public void setMetalReceived(Double metalReceived) {
		this.metalReceived = metalReceived;
	}
	public Double getAmountPaid() {
		return amountPaid;
	}
	public void setAmountPaid(Double amountPaid) {
		this.amountPaid = amountPaid;
	}
	public Double getMetalPaid() {
		return metalPaid;
	}
	public void setMetalPaid(Double metalPaid) {
		this.metalPaid = metalPaid;
	}
	public String getBillSource() {
		return billSource;
	}
	public void setBillSource(String billSource) {
		this.billSource = billSource;
	}
	public Double getGoldMetalReceived() {
		return goldMetalReceived;
	}
	public void setGoldMetalReceived(Double goldMetalReceived) {
		this.goldMetalReceived = goldMetalReceived;
	}
	public Double getSilverMetalReceived() {
		return silverMetalReceived;
	}
	public void setSilverMetalReceived(Double silverMetalReceived) {
		this.silverMetalReceived = silverMetalReceived;
	}
	public Double getGoldMetalPaid() {
		return goldMetalPaid;
	}
	public void setGoldMetalPaid(Double goldMetalPaid) {
		this.goldMetalPaid = goldMetalPaid;
	}
	public Double getSilverMetalPaid() {
		return silverMetalPaid;
	}
	public void setSilverMetalPaid(Double silverMetalPaid) {
		this.silverMetalPaid = silverMetalPaid;
	}
	
	
	

}
