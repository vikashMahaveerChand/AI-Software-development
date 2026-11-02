package com.inventory.dto;

import java.util.List;

public class ContactTransactionDTO {
	private Integer contactId;
	private String contactName;
	
	private String openingBalance;
	private String openingMetalBlance;
	
	private String totalBillAmountDue;
	private String totalBillMetalDue;
	
	private String totalAmountPaid;
	private String totalMetalPaid;
	
	private String currentAmountBalance;
	private String currentMetalBalance;
	
	
	
	private List<ContactTransactionDTOList> contactTransactionList;

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

	public String getTotalAmountPaid() {
		return totalAmountPaid;
	}

	public void setTotalAmountPaid(String totalAmountPaid) {
		this.totalAmountPaid = totalAmountPaid;
	}

	public String getTotalMetalPaid() {
		return totalMetalPaid;
	}

	public void setTotalMetalPaid(String totalMetalPaid) {
		this.totalMetalPaid = totalMetalPaid;
	}

	public String getCurrentAmountBalance() {
		return currentAmountBalance;
	}

	public void setCurrentAmountBalance(String currentAmountBalance) {
		this.currentAmountBalance = currentAmountBalance;
	}

	public String getCurrentMetalBalance() {
		return currentMetalBalance;
	}

	public void setCurrentMetalBalance(String currentMetalBalance) {
		this.currentMetalBalance = currentMetalBalance;
	}

	public List<ContactTransactionDTOList> getContactTransactionList() {
		return contactTransactionList;
	}

	public void setContactTransactionList(List<ContactTransactionDTOList> contactTransactionList) {
		this.contactTransactionList = contactTransactionList;
	}

	public String getOpeningBalance() {
		return openingBalance;
	}

	public void setOpeningBalance(String openingBalance) {
		this.openingBalance = openingBalance;
	}

	public String getOpeningMetalBlance() {
		return openingMetalBlance;
	}

	public void setOpeningMetalBlance(String openingMetalBlance) {
		this.openingMetalBlance = openingMetalBlance;
	}

	public String getTotalBillAmountDue() {
		return totalBillAmountDue;
	}

	public void setTotalBillAmountDue(String totalBillAmountDue) {
		this.totalBillAmountDue = totalBillAmountDue;
	}

	public String getTotalBillMetalDue() {
		return totalBillMetalDue;
	}

	public void setTotalBillMetalDue(String totalBillMetalDue) {
		this.totalBillMetalDue = totalBillMetalDue;
	}
	
	
	
	

}
