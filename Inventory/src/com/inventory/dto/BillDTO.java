package com.inventory.dto;

import java.util.List;

import com.inventory.model.Bill;

public class BillDTO {
	
	private Integer billCount;
	private Double totalBillAmount;
	private Double totalBillWeight;
	private Double totalMetalBalance;
	
	
	private List<Bill> billList;
	
	private String billCountStr;
	private String totalBillAmountStr;
	private String totalBillWeightStr;
	private String totalMetalStr;
	
	private String reportName;
	private String reportStartDate;
	private String reportEndDate;

	public List<Bill> getBillList() {
		return billList;
	}

	public void setBillList(List<Bill> billList) {
		this.billList = billList;
	}

	public Integer getBillCount() {
		return billCount;
	}

	public void setBillCount(Integer billCount) {
		this.billCount = billCount;
	}

	public Double getTotalBillAmount() {
		return totalBillAmount;
	}

	public void setTotalBillAmount(Double totalBillAmount) {
		this.totalBillAmount = totalBillAmount;
	}

	public Double getTotalBillWeight() {
		return totalBillWeight;
	}

	public void setTotalBillWeight(Double totalBillWeight) {
		this.totalBillWeight = totalBillWeight;
	}

	public Double getTotalMetalBalance() {
		return totalMetalBalance;
	}

	public void setTotalMetalBalance(Double totalMetalBalance) {
		this.totalMetalBalance = totalMetalBalance;
	}

	public String getBillCountStr() {
		return billCountStr;
	}

	public void setBillCountStr(String billCountStr) {
		this.billCountStr = billCountStr;
	}

	public String getTotalBillAmountStr() {
		return totalBillAmountStr;
	}

	public void setTotalBillAmountStr(String totalBillAmountStr) {
		this.totalBillAmountStr = totalBillAmountStr;
	}

	public String getTotalBillWeightStr() {
		return totalBillWeightStr;
	}

	public void setTotalBillWeightStr(String totalBillWeightStr) {
		this.totalBillWeightStr = totalBillWeightStr;
	}

	public String getTotalMetalStr() {
		return totalMetalStr;
	}

	public void setTotalMetalStr(String totalMetalStr) {
		this.totalMetalStr = totalMetalStr;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getReportStartDate() {
		return reportStartDate;
	}

	public void setReportStartDate(String reportStartDate) {
		this.reportStartDate = reportStartDate;
	}

	public String getReportEndDate() {
		return reportEndDate;
	}

	public void setReportEndDate(String reportEndDate) {
		this.reportEndDate = reportEndDate;
	}

}
