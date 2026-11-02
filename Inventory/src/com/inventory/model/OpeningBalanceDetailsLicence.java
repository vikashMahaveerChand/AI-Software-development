package com.inventory.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "OPENING_BALANCE_DETAILS_LICENCE")
public class OpeningBalanceDetailsLicence {
	
	@Id @GeneratedValue
	@Column(name = "OPENING_BALANCE_DETAILS_LICENCE_ID")
	private Integer OpeningBalanceDetailsLicenceId;
	
	@OneToOne
	@JoinColumn(name="LICENCE_ID")
	private Licence licence;
	
	@Column(name = "AMOUNT")
	private Double amount;
	
	@OneToOne
	@JoinColumn(name="BILL_TYPE_ID")
	private BillType billType;
	
	@Column(name = "DELETED_FLAG")
	private String deletedFlag;
	
	@OneToOne
	@JoinColumn(name = "METAL_ID")
	private Metal metal;
	
	@Column(name = "GRAMS")
	private Double grams;

	public Integer getOpeningBalanceDetailsLicenceId() {
		return OpeningBalanceDetailsLicenceId;
	}

	public void setOpeningBalanceDetailsLicenceId(Integer openingBalanceDetailsLicenceId) {
		OpeningBalanceDetailsLicenceId = openingBalanceDetailsLicenceId;
	}

	public Licence getLicence() {
		return licence;
	}

	public void setLicence(Licence licence) {
		this.licence = licence;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public BillType getBillType() {
		return billType;
	}

	public void setBillType(BillType billType) {
		this.billType = billType;
	}

	public String getDeletedFlag() {
		return deletedFlag;
	}

	public void setDeletedFlag(String deletedFlag) {
		this.deletedFlag = deletedFlag;
	}

	public Metal getMetal() {
		return metal;
	}

	public void setMetal(Metal metal) {
		this.metal = metal;
	}

	public Double getGrams() {
		return grams;
	}

	public void setGrams(Double grams) {
		this.grams = grams;
	}

	@Override
	public int hashCode() {
		return Objects.hash(OpeningBalanceDetailsLicenceId, amount, billType, deletedFlag, grams, licence, metal);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OpeningBalanceDetailsLicence other = (OpeningBalanceDetailsLicence) obj;
		return Objects.equals(OpeningBalanceDetailsLicenceId, other.OpeningBalanceDetailsLicenceId)
				&& Objects.equals(amount, other.amount) && Objects.equals(billType, other.billType)
				&& Objects.equals(deletedFlag, other.deletedFlag) && Objects.equals(grams, other.grams)
				&& Objects.equals(licence, other.licence) && Objects.equals(metal, other.metal);
	}
	
}
