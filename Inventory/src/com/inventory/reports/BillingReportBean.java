package com.inventory.reports;

public class BillingReportBean {
	
	private String billNo;
	private String billDate;
	private String name;
	private String weight;
	private String amount;
	public BillingReportBean() {}
	public BillingReportBean(String billNo, String billDate, String name, String weight, String amount) {
		super();
		this.billNo = billNo;
		this.billDate = billDate;
		this.name = name;
		this.weight = weight;
		this.amount = amount;
	}
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	public String getBillDate() {
		return billDate;
	}
	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}

}
