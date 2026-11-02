package com.inventory.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PAWN_BILL_DETAILS")
public class PawnBillDetails {
	
	@Id @GeneratedValue
	@Column(name = "PAWN_BILL_DETAILS_ID")
	private Integer pawnBillDetailsId;

	@ManyToOne
	@JoinColumn(name="PAWN_BILL_ID")
	private PawnBill pawnBill;
	
	@Column(name = "DELETED_FLAG")
	private String deletedFlag;
	
	@OneToOne
	@JoinColumn(name="ORNAMENT_ID")
	private Ornament ornament;

	@Column(name = "NO_OF_PIECE")
	private Integer noOfPiece;
	
	@Column(name = "GROSS_WEIGHT")
	private Double grossWeight;
	
	@Column(name = "NET_WEIGHT")
	private Double netWeight;
	
	@Column(name = "NARRATION")
	private String narration;
	
	@Column(name = "SNO")
	private Integer sNo;

	public Integer getsNo() {
		return sNo;
	}

	public void setsNo(Integer sNo) {
		this.sNo = sNo;
	}

	public Integer getPawnBillDetailsId() {
		return pawnBillDetailsId;
	}

	public void setPawnBillDetailsId(Integer pawnBillDetailsId) {
		this.pawnBillDetailsId = pawnBillDetailsId;
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

	public Ornament getOrnament() {
		return ornament;
	}

	public void setOrnament(Ornament ornament) {
		this.ornament = ornament;
	}

	public Integer getNoOfPiece() {
		return noOfPiece;
	}

	public void setNoOfPiece(Integer noOfPiece) {
		this.noOfPiece = noOfPiece;
	}

	public Double getGrossWeight() {
		return grossWeight;
	}

	public void setGrossWeight(Double grossWeight) {
		this.grossWeight = grossWeight;
	}

	public Double getNetWeight() {
		return netWeight;
	}

	public void setNetWeight(Double netWeight) {
		this.netWeight = netWeight;
	}

	public String getNarration() {
		return narration;
	}

	public void setNarration(String narration) {
		this.narration = narration;
	}
	
	
	
}
