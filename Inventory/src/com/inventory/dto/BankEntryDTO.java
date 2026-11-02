package com.inventory.dto;

import java.util.List;

import com.inventory.model.BankEntry;

public class BankEntryDTO {
	
	private List<BankEntry> bankEntryList;
	private Double totalCredit;
	private Double totalDebit;
	private Double currentBalance;
	
	public List<BankEntry> getBankEntryList() {
		return bankEntryList;
	}
	public void setBankEntryList(List<BankEntry> bankEntryList) {
		this.bankEntryList = bankEntryList;
	}
	public Double getTotalCredit() {
		return totalCredit;
	}
	public void setTotalCredit(Double totalCredit) {
		this.totalCredit = totalCredit;
	}
	public Double getTotalDebit() {
		return totalDebit;
	}
	public void setTotalDebit(Double totalDebit) {
		this.totalDebit = totalDebit;
	}
	public Double getCurrentBalance() {
		return currentBalance;
	}
	public void setCurrentBalance(Double currentBalance) {
		this.currentBalance = currentBalance;
	}

}
