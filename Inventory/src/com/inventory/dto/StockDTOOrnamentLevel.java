package com.inventory.dto;

import java.util.List;

import com.inventory.model.BillDetails;

public class StockDTOOrnamentLevel {
	
	private Integer ornamentId;
	private String ornamentName;
	
	private List<BillDetails> billDetails;
	private List<BillDetails> purchaseDetails;
	
	
	
	private List<StockDTOOrnamentLevelDisplay> displayList;
	
	public Integer getOrnamentId() {
		return ornamentId;
	}
	public void setOrnamentId(Integer ornamentId) {
		this.ornamentId = ornamentId;
	}
	public String getOrnamentName() {
		return ornamentName;
	}
	public void setOrnamentName(String ornamentName) {
		this.ornamentName = ornamentName;
	}
	public List<BillDetails> getBillDetails() {
		return billDetails;
	}
	public void setBillDetails(List<BillDetails> billDetails) {
		this.billDetails = billDetails;
	}
	public List<BillDetails> getPurchaseDetails() {
		return purchaseDetails;
	}
	public void setPurchaseDetails(List<BillDetails> purchaseDetails) {
		this.purchaseDetails = purchaseDetails;
	}
	public List<StockDTOOrnamentLevelDisplay> getDisplayList() {
		return displayList;
	}
	public void setDisplayList(List<StockDTOOrnamentLevelDisplay> displayList) {
		this.displayList = displayList;
	}
	
	
	
	

}
