package com.inventory.dto;

import java.util.Date;
import java.util.List;

public class BalanceSheetDTOList {
	
	private Integer contactId;
	private String contactName;
	private Double amountBalance;
	private Double metalBalance;
	private String metalName;
	
	private String metalBalanceStr;
	private String type;
	
	private Date tDate;
	private String tNumber;
	private String tID;
	private Double weight;
	
	private List<BalanceSheetDTOList> balanceSheetDTOList;
	
	/**
	 * @return the metalBalanceStr
	 */
	public String getMetalBalanceStr() {
		return metalBalanceStr;
	}
	/**
	 * @param metalBalanceStr the metalBalanceStr to set
	 */
	public void setMetalBalanceStr(String metalBalanceStr) {
		this.metalBalanceStr = metalBalanceStr;
	}
	public Integer getContactId() {
		return contactId;
	}
	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}
	public Double getAmountBalance() {
		return amountBalance;
	}
	public void setAmountBalance(Double amountBalance) {
		this.amountBalance = amountBalance;
	}
	public Double getMetalBalance() {
		return metalBalance;
	}
	public void setMetalBalance(Double metalBalance) {
		this.metalBalance = metalBalance;
	}
	public String getMetalName() {
		return metalName;
	}
	public void setMetalName(String metalName) {
		this.metalName = metalName;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	public Date gettDate() {
		return tDate;
	}
	public void settDate(Date tDate) {
		this.tDate = tDate;
	}
	public String gettNumber() {
		return tNumber;
	}
	public void settNumber(String tNumber) {
		this.tNumber = tNumber;
	}
	public String gettID() {
		return tID;
	}
	public void settID(String tID) {
		this.tID = tID;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	public List<BalanceSheetDTOList> getBalanceSheetDTOList() {
		return balanceSheetDTOList;
	}
	public void setBalanceSheetDTOList(List<BalanceSheetDTOList> balanceSheetDTOList) {
		this.balanceSheetDTOList = balanceSheetDTOList;
	}

}
