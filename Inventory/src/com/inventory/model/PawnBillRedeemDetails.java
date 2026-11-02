package com.inventory.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PAWN_BILL_REDEEM_DETAILS")
public class PawnBillRedeemDetails {
	
	@Id @GeneratedValue
	@Column(name = "PAWN_BILL_REDEEM_DETAILS_ID")
	private Integer pawnBillRedeemDetailsId;
	
	@OneToOne
	@JoinColumn(name="PAWN_BILL_ID")
	private PawnBill pawnBill;

	@Column(name = "DELETED_FLAG")
	private String deletedFlag;
	
	@Column(name = "DELIVERY_NUMBER")
	private String deliveryNumber;
	
	@Column(name = "REDEEM_DATE")
	private Date redeemDate;
	
	@Column(name = "TOTAL_NUMBER_OF_MONTHS")
	private Integer totalNumberOfMonths;
	
	@Column(name = "REDEEM_INTEREST")
	private Double redeemInterest;
	
	@Column(name = "REDEEM_TOTAL_AMOUNT")
	private Double redeemTotalAmount;
	
	@Column(name = "REDEEM_AMOUNT_RECEIVED")
	private Double redeemAmountReceived;
	
	@Column(name = "NARRATION")
	private String narration;

	public Integer getPawnBillRedeemDetailsId() {
		return pawnBillRedeemDetailsId;
	}

	public void setPawnBillRedeemDetailsId(Integer pawnBillRedeemDetailsId) {
		this.pawnBillRedeemDetailsId = pawnBillRedeemDetailsId;
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
	
	public String getDeliveryNumber() {
		return deliveryNumber;
	}

	public void setDeliveryNumber(String deliveryNumber) {
		this.deliveryNumber = deliveryNumber;
	}

	public Date getRedeemDate() {
		return redeemDate;
	}

	public void setRedeemDate(Date redeemDate) {
		this.redeemDate = redeemDate;
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

	public Double getRedeemAmountReceived() {
		return redeemAmountReceived;
	}

	public void setRedeemAmountReceived(Double redeemAmountReceived) {
		this.redeemAmountReceived = redeemAmountReceived;
	}

	public String getNarration() {
		return narration;
	}

	public void setNarration(String narration) {
		this.narration = narration;
	}

}
