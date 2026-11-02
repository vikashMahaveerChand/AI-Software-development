package com.inventory.dto;

import java.util.List;

public class CashReportDTO {
	
	private Double amountAvailable;
	private Double goldMetalAvailable;
	private Double silverMetalAvailable;
	
	private List<CashReportDTOList> cashReportDTOList;

	public List<CashReportDTOList> getCashReportDTOList() {
		return cashReportDTOList;
	}

	public void setCashReportDTOList(List<CashReportDTOList> cashReportDTOList) {
		this.cashReportDTOList = cashReportDTOList;
	}

	public Double getAmountAvailable() {
		return amountAvailable;
	}

	public void setAmountAvailable(Double amountAvailable) {
		this.amountAvailable = amountAvailable;
	}

	public Double getGoldMetalAvailable() {
		return goldMetalAvailable;
	}

	public void setGoldMetalAvailable(Double goldMetalAvailable) {
		this.goldMetalAvailable = goldMetalAvailable;
	}

	public Double getSilverMetalAvailable() {
		return silverMetalAvailable;
	}

	public void setSilverMetalAvailable(Double silverMetalAvailable) {
		this.silverMetalAvailable = silverMetalAvailable;
	}
	

}
