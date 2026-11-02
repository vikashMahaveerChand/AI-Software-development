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
@Table(name = "PAWN_BILL_AUCTION_DETAILS")
public class PawnBillAuctionDetails {
	
	@Id @GeneratedValue
	@Column(name = "PAWN_BILL_AUCTION_DETAILS_ID")
	private Integer pawnBillAuctionDetailsId;
	
	@OneToOne
	@JoinColumn(name="PAWN_BILL_ID")
	private PawnBill pawnBill;

	@Column(name = "DELETED_FLAG")
	private String deletedFlag;
	
	@Column(name = "AUCTION_NUMBER")
	private String auctionNumber;
	
	@Column(name = "AUCTION_DATE")
	private Date auctionDate;
	
	@Column(name = "TOTAL_NUMBER_OF_MONTHS")
	private Integer totalNumberOfMonths;
	
	@Column(name = "REDEEM_INTEREST")
	private Double redeemInterest;
	
	@Column(name = "REDEEM_TOTAL_AMOUNT")
	private Double redeemTotalAmount;
	
	@Column(name = "AUCTION_AMOUNT_RECEIVED")
	private Double auctionAmountReceived;
	
	@Column(name = "NARRATION")
	private String narration;

	public Integer getPawnBillAuctionDetailsId() {
		return pawnBillAuctionDetailsId;
	}

	public void setPawnBillAuctionDetailsId(Integer pawnBillAuctionDetailsId) {
		this.pawnBillAuctionDetailsId = pawnBillAuctionDetailsId;
	}

	public PawnBill getPawnBill() {
		return pawnBill;
	}

	public void setPawnBill(PawnBill pawnBill) {
		this.pawnBill = pawnBill;
	}

	public String getDeletedFlag() {
		return deletedFlag;
	}

	public void setDeletedFlag(String deletedFlag) {
		this.deletedFlag = deletedFlag;
	}

	public String getAuctionNumber() {
		return auctionNumber;
	}

	public void setAuctionNumber(String auctionNumber) {
		this.auctionNumber = auctionNumber;
	}

	public Date getAuctionDate() {
		return auctionDate;
	}

	public void setAuctionDate(Date auctionDate) {
		this.auctionDate = auctionDate;
	}

	public Integer getTotalNumberOfMonths() {
		return totalNumberOfMonths;
	}

	public void setTotalNumberOfMonths(Integer totalNumberOfMonths) {
		this.totalNumberOfMonths = totalNumberOfMonths;
	}

	public Double getRedeemInterest() {
		return redeemInterest;
	}

	public void setRedeemInterest(Double redeemInterest) {
		this.redeemInterest = redeemInterest;
	}

	public Double getRedeemTotalAmount() {
		return redeemTotalAmount;
	}

	public void setRedeemTotalAmount(Double redeemTotalAmount) {
		this.redeemTotalAmount = redeemTotalAmount;
	}

	public Double getAuctionAmountReceived() {
		return auctionAmountReceived;
	}

	public void setAuctionAmountReceived(Double auctionAmountReceived) {
		this.auctionAmountReceived = auctionAmountReceived;
	}

	public String getNarration() {
		return narration;
	}

	public void setNarration(String narration) {
		this.narration = narration;
	}

}
