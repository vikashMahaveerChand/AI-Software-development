package com.inventory.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "BANK")
public class Bank {
	
	@Override
	public String toString(){
		return accountNumber+" - "+accountHolderName;
	}
	
	@Id @GeneratedValue
	@Column(name = "BANK_ID")
	private Integer bankId;
	
	@Column(name = "BANK_NAME")
	private String bankName;
	
	@Column(name = "BANK_BRANCH")
	private String bankBranch;
	
	@Column(name = "ACCOUNT_HOLDER_NAME")
	private String accountHolderName;
	
	@Column(name = "ACCOUNT_NUMBER")
	private String accountNumber;
	
	@Column(name = "ACCOUNT_TYPE")
	private String accountType;
	
	@Column(name = "IFSC_CODE")
	private String ifscCode;
	
	@Column(name = "DELETED_FLAG")
	private String deletedFlag;
	
	@OneToMany(cascade=CascadeType.ALL,fetch = FetchType.EAGER,mappedBy="bank")
	@OrderBy("entryDate ASC")
    private List<BankEntry> bankEntryList = new ArrayList<BankEntry>();

	@OneToMany(cascade=CascadeType.ALL,fetch = FetchType.LAZY,mappedBy="bank")
	@OrderBy("paymentDate ASC")
    private List<PaymentDetails> bankPaymentList = new ArrayList<PaymentDetails>();
	
	@Transient
	private List<PaymentDetails> bankPaymentTransientList;
	
	@Transient
	private Double totalDebitAmount;
	
	@Transient
	private Double totalCreditAmount;
	
	@Transient
	private Double currentBalance;
	
	
	public Integer getBankId() {
		return bankId;
	}

	public void setBankId(Integer bankId) {
		this.bankId = bankId;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankBranch() {
		return bankBranch;
	}

	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}

	public String getAccountHolderName() {
		return accountHolderName;
	}

	public void setAccountHolderName(String accountHolderName) {
		this.accountHolderName = accountHolderName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getIfscCode() {
		return ifscCode;
	}

	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

	public String getDeletedFlag() {
		return deletedFlag;
	}

	public void setDeletedFlag(String deletedFlag) {
		this.deletedFlag = deletedFlag;
	}

	public List<BankEntry> getBankEntryList() {
		return bankEntryList;
	}

	public Double getTotalDebitAmount() {
		return totalDebitAmount;
	}

	public void setTotalDebitAmount(Double totalDebitAmount) {
		this.totalDebitAmount = totalDebitAmount;
	}

	public Double getTotalCreditAmount() {
		return totalCreditAmount;
	}

	public void setTotalCreditAmount(Double totalCreditAmount) {
		this.totalCreditAmount = totalCreditAmount;
	}

	public Double getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(Double currentBalance) {
		this.currentBalance = currentBalance;
	}

	public List<PaymentDetails> getBankPaymentTransientList() {
		return bankPaymentTransientList;
	}

	public void setBankPaymentTransientList(List<PaymentDetails> bankPaymentTransientList) {
		this.bankPaymentTransientList = bankPaymentTransientList;
	}

	public List<PaymentDetails> getBankPaymentList() {
		return bankPaymentList;
	}

	
	
}
