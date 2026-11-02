package com.inventory.dto;

import java.util.Date;
import java.util.List;

import com.inventory.model.PawnBill;

public class PawnBillDTO {
	
	private List<PawnBill> pawnBillList;
	
	private Double totalAmount;
	private Integer billCount;
	
	
	private Integer pledgeBillCount;
	private Double pledgeBillTotalAmount;

	private Integer redeemedBillCount;
	private Double redemeedBillTotalAmount;

	private Integer oldPledgeBillCount;
	private Double oldPledgeBillTotalAmount;
	
	private Date lastBillDate;

	public List<PawnBill> getPawnBillList() {
		return pawnBillList;
	}

	public void setPawnBillList(List<PawnBill> pawnBillList) {
		this.pawnBillList = pawnBillList;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Integer getBillCount() {
		return billCount;
	}

	public void setBillCount(Integer billCount) {
		this.billCount = billCount;
	}

	public Integer getPledgeBillCount() {
		return pledgeBillCount;
	}

	public void setPledgeBillCount(Integer pledgeBillCount) {
		this.pledgeBillCount = pledgeBillCount;
	}

	public Double getPledgeBillTotalAmount() {
		return pledgeBillTotalAmount;
	}

	public void setPledgeBillTotalAmount(Double pledgeBillTotalAmount) {
		this.pledgeBillTotalAmount = pledgeBillTotalAmount;
	}

	public Integer getRedeemedBillCount() {
		return redeemedBillCount;
	}

	public void setRedeemedBillCount(Integer redeemedBillCount) {
		this.redeemedBillCount = redeemedBillCount;
	}

	public Double getRedemeedBillTotalAmount() {
		return redemeedBillTotalAmount;
	}

	public void setRedemeedBillTotalAmount(Double redemeedBillTotalAmount) {
		this.redemeedBillTotalAmount = redemeedBillTotalAmount;
	}

	public Integer getOldPledgeBillCount() {
		return oldPledgeBillCount;
	}

	public void setOldPledgeBillCount(Integer oldPledgeBillCount) {
		this.oldPledgeBillCount = oldPledgeBillCount;
	}

	public Double getOldPledgeBillTotalAmount() {
		return oldPledgeBillTotalAmount;
	}

	public void setOldPledgeBillTotalAmount(Double oldPledgeBillTotalAmount) {
		this.oldPledgeBillTotalAmount = oldPledgeBillTotalAmount;
	}

	public Date getLastBillDate() {
		return lastBillDate;
	}

	public void setLastBillDate(Date lastBillDate) {
		this.lastBillDate = lastBillDate;
	}

}
