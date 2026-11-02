package com.inventory.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "OLD_ITEM_DETAILS")
public class OldItemDetails {
	
	@Id @GeneratedValue
	@Column(name = "OLD_DETAILS_ID")
	private Integer oldDetailsId;

	@ManyToOne
	@JoinColumn(name = "BILL_ID")
    private Bill bill;
	
	@Column(name = "DELETED_FLAG")
	private String deletedFlag;
	
	@Column(name = "BARCODE_ID")
	private Integer barCode;
	
	@OneToOne
	@JoinColumn(name="ORNAMENT_ID")
	private Ornament ornament;
	
	@Column(name = "GROSS_WEIGHT")
	private Double grossWeight;
	
	@Column(name = "WEIGHT_LESS")
	private Double weightLess;
	
	@Column(name = "WEIGHT")
	private Double weight;
	
	@Column(name = "PURITY")
	private Double purity;
	
	@Column(name = "PURE_WEIGHT")
	private Double pureWeight;
	
	@Column(name = "WASTAGE")
	private Double wastage;
	
	@Column(name = "RATE")
	private Double rate;
	
	@Column(name = "TAX_PERCENTAGE")
	private Double taxPercentage;
	
	@Column(name = "ORNAMENT_AMOUNT")
	private Double ornamentAmount;
	
	@Column(name = "TAX_AMOUNT")
	private Double taxAmount;
	
	@Column(name = "MC_RATE")
	private Double mcRate;
	
	@Column(name = "MC_PER_PIECE")
	private Double mcPerPiece;
	
	@Column(name = "MC_AMOUNT")
	private Double mcAmount;
	
	@Column(name = "ROUND_OFF")
	private Double roundOff;
	
	
	@Column(name = "AMOUNT")
	private Double amount;
	
	@Column(name = "METAL_BALANCE")
	private Double metalBalance;
	
	@Column(name = "NO_OF_PIECE")
	private Integer noOfPiece;
	
	@Column(name = "DIRECT_AMOUNT")
	private Double directAmount;
	
	@Column(name = "SNO")
	private Integer sNo;

	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}

	public String getDeletedFlag() {
		return deletedFlag;
	}

	public void setDeletedFlag(String deletedFlag) {
		this.deletedFlag = deletedFlag;
	}

	public Integer getBarCode() {
		return barCode;
	}

	public void setBarCode(Integer barCode) {
		this.barCode = barCode;
	}

	public Ornament getOrnament() {
		return ornament;
	}

	public void setOrnament(Ornament ornament) {
		this.ornament = ornament;
	}

	public Double getGrossWeight() {
		return grossWeight;
	}

	public void setGrossWeight(Double grossWeight) {
		this.grossWeight = grossWeight;
	}

	public Double getWeightLess() {
		return weightLess;
	}

	public void setWeightLess(Double weightLess) {
		this.weightLess = weightLess;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Double getPurity() {
		return purity;
	}

	public void setPurity(Double purity) {
		this.purity = purity;
	}

	public Double getPureWeight() {
		return pureWeight;
	}

	public void setPureWeight(Double pureWeight) {
		this.pureWeight = pureWeight;
	}

	public Double getWastage() {
		return wastage;
	}

	public void setWastage(Double wastage) {
		this.wastage = wastage;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Double getTaxPercentage() {
		return taxPercentage;
	}

	public void setTaxPercentage(Double taxPercentage) {
		this.taxPercentage = taxPercentage;
	}

	public Double getMcRate() {
		return mcRate;
	}

	public void setMcRate(Double mcRate) {
		this.mcRate = mcRate;
	}

	public Double getMcPerPiece() {
		return mcPerPiece;
	}

	public void setMcPerPiece(Double mcPerPiece) {
		this.mcPerPiece = mcPerPiece;
	}

	public Double getMcAmount() {
		return mcAmount;
	}

	public void setMcAmount(Double mcAmount) {
		this.mcAmount = mcAmount;
	}

	public Double getRoundOff() {
		return roundOff;
	}

	public void setRoundOff(Double roundOff) {
		this.roundOff = roundOff;
	}

	public Integer getNoOfPiece() {
		return noOfPiece;
	}

	public void setNoOfPiece(Integer noOfPiece) {
		this.noOfPiece = noOfPiece;
	}

	public Double getDirectAmount() {
		return directAmount;
	}

	public void setDirectAmount(Double directAmount) {
		this.directAmount = directAmount;
	}

	public Integer getsNo() {
		return sNo;
	}

	public void setsNo(Integer sNo) {
		this.sNo = sNo;
	}

	public Double getOrnamentAmount() {
		return ornamentAmount;
	}

	public void setOrnamentAmount(Double ornamentAmount) {
		this.ornamentAmount = ornamentAmount;
	}

	public Double getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(Double taxAmount) {
		this.taxAmount = taxAmount;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getMetalBalance() {
		return metalBalance;
	}

	public void setMetalBalance(Double metalBalance) {
		this.metalBalance = metalBalance;
	}

	public Integer getOldDetailsId() {
		return oldDetailsId;
	}

	public void setOldDetailsId(Integer oldDetailsId) {
		this.oldDetailsId = oldDetailsId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(amount, barCode, bill, deletedFlag, directAmount, grossWeight, mcAmount, mcPerPiece, mcRate,
				metalBalance, noOfPiece, oldDetailsId, ornament, ornamentAmount, pureWeight, purity, rate, roundOff,
				sNo, taxAmount, taxPercentage, wastage, weight, weightLess);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OldItemDetails other = (OldItemDetails) obj;
		return Objects.equals(amount, other.amount) && Objects.equals(barCode, other.barCode)
				&& Objects.equals(bill, other.bill) && Objects.equals(deletedFlag, other.deletedFlag)
				&& Objects.equals(directAmount, other.directAmount) && Objects.equals(grossWeight, other.grossWeight)
				&& Objects.equals(mcAmount, other.mcAmount) && Objects.equals(mcPerPiece, other.mcPerPiece)
				&& Objects.equals(mcRate, other.mcRate) && Objects.equals(metalBalance, other.metalBalance)
				&& Objects.equals(noOfPiece, other.noOfPiece) && Objects.equals(oldDetailsId, other.oldDetailsId)
				&& Objects.equals(ornament, other.ornament) && Objects.equals(ornamentAmount, other.ornamentAmount)
				&& Objects.equals(pureWeight, other.pureWeight) && Objects.equals(purity, other.purity)
				&& Objects.equals(rate, other.rate) && Objects.equals(roundOff, other.roundOff)
				&& Objects.equals(sNo, other.sNo) && Objects.equals(taxAmount, other.taxAmount)
				&& Objects.equals(taxPercentage, other.taxPercentage) && Objects.equals(wastage, other.wastage)
				&& Objects.equals(weight, other.weight) && Objects.equals(weightLess, other.weightLess);
	}


	

}
