package com.inventory.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "BANK_ENTRY")
public class BankEntry {
	
	
	@Id @GeneratedValue
	@Column(name = "BANK_ENTRY_ID")
	private Integer bankEntryId;
	
	@OneToOne
	@JoinColumn(name="BANK_ID")
	private Bank bank;
	
	@Column(name = "ENTRY_DATE")
	private Date entryDate;
	
	@Column(name = "CREDIT_AMOUNT")
	private Double creditAmount;
	
	@Column(name = "DEBIT_AMOUNT")
	private Double debitAmount;

	@Column (name= "ENTRY_NOTES")
	private String entryNotes;
	
	@Column(name = "DELETED_FLAG")
	private String deletedFlag;

	public Integer getBankEntryId() {
		return bankEntryId;
	}

	public void setBankEntryId(Integer bankEntryId) {
		this.bankEntryId = bankEntryId;
	}

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	public Double getCreditAmount() {
		return creditAmount;
	}

	public void setCreditAmount(Double creditAmount) {
		this.creditAmount = creditAmount;
	}

	public Double getDebitAmount() {
		return debitAmount;
	}

	public void setDebitAmount(Double debitAmount) {
		this.debitAmount = debitAmount;
	}

	public String getEntryNotes() {
		return entryNotes;
	}

	public void setEntryNotes(String entryNotes) {
		this.entryNotes = entryNotes;
	}

	public String getDeletedFlag() {
		return deletedFlag;
	}

	public void setDeletedFlag(String deletedFlag) {
		this.deletedFlag = deletedFlag;
	}
}
