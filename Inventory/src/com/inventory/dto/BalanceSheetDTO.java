package com.inventory.dto;

import java.util.List;

public class BalanceSheetDTO {
	
	private List<BalanceSheetDTOList> balanceSheetDTOList;
	
	private Double totalAmountBalance;
	private Double totalMetalBalance;
	private String totalMetalBalanceStr;

	public List<BalanceSheetDTOList> getBalanceSheetDTOList() {
		return balanceSheetDTOList;
	}

	public void setBalanceSheetDTOList(List<BalanceSheetDTOList> balanceSheetDTOList) {
		this.balanceSheetDTOList = balanceSheetDTOList;
	}

	public Double getTotalAmountBalance() {
		return totalAmountBalance;
	}

	public void setTotalAmountBalance(Double totalAmountBalance) {
		this.totalAmountBalance = totalAmountBalance;
	}

	public Double getTotalMetalBalance() {
		return totalMetalBalance;
	}

	public void setTotalMetalBalance(Double totalMetalBalance) {
		this.totalMetalBalance = totalMetalBalance;
	}

	/**
	 * @return the totalMetalBalanceStr
	 */
	public String getTotalMetalBalanceStr() {
		return totalMetalBalanceStr;
	}

	/**
	 * @param totalMetalBalanceStr the totalMetalBalanceStr to set
	 */
	public void setTotalMetalBalanceStr(String totalMetalBalanceStr) {
		this.totalMetalBalanceStr = totalMetalBalanceStr;
	}

	@Override
	public String toString() {
		return "BalanceSheetDTO [totalAmountBalance=" + totalAmountBalance + ", totalMetalBalance=" + totalMetalBalance
				+ ", totalMetalBalanceStr=" + totalMetalBalanceStr + "]";
	}

	
}
