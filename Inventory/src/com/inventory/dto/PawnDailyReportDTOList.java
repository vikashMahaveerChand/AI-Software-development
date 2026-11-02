package com.inventory.dto;

import java.util.Date;

public class PawnDailyReportDTOList {
	
	private Integer pawnBillId;
	private String description;
	private Date date;
	
	private double pledgeAmount;
	private double billCharges;
	private double interest;
	
	private double redeemPledgeAmount;
	private double redeemInterestAmount;
	private double redeemAmountReceived;
	public Integer getPawnBillId() {
		return pawnBillId;
	}
	public void setPawnBillId(Integer pawnBillId) {
		this.pawnBillId = pawnBillId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getPledgeAmount() {
		return pledgeAmount;
	}
	public void setPledgeAmount(double pledgeAmount) {
		this.pledgeAmount = pledgeAmount;
	}
	public double getBillCharges() {
		return billCharges;
	}
	public void setBillCharges(double billCharges) {
		this.billCharges = billCharges;
	}
	public double getInterest() {
		return interest;
	}
	public void setInterest(double interest) {
		this.interest = interest;
	}
	public double getRedeemPledgeAmount() {
		return redeemPledgeAmount;
	}
	public void setRedeemPledgeAmount(double redeemPledgeAmount) {
		this.redeemPledgeAmount = redeemPledgeAmount;
	}
	public double getRedeemInterestAmount() {
		return redeemInterestAmount;
	}
	public void setRedeemInterestAmount(double redeemInterestAmount) {
		this.redeemInterestAmount = redeemInterestAmount;
	}
	public double getRedeemAmountReceived() {
		return redeemAmountReceived;
	}
	public void setRedeemAmountReceived(double redeemAmountReceived) {
		this.redeemAmountReceived = redeemAmountReceived;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	

}
