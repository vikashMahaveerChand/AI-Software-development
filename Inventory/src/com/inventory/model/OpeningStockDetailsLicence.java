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
@Table(name = "OPENING_STOCK_DETAILS_LICENCE")
public class OpeningStockDetailsLicence {
	
	@Id @GeneratedValue
	@Column(name = "OPENING_STOCK_DETAILS_LICENCE_ID")
	private Integer openingStockDetailsLicenceId;
	
	@ManyToOne
	@JoinColumn(name="ORNAMENT_ID")
	private Ornament ornament;
	
	@Column(name = "GRAMS")
	private Double grams;
	
	@OneToOne
	@JoinColumn(name="BILL_TYPE_ID")
	private BillType billType;
	
	@OneToOne
	@JoinColumn(name="LICENCE_ID")
	private Licence licence;
	
	@Column(name = "DELETED_FLAG")
	private String deletedFlag;

	public Integer getOpeningStockDetailsLicenceId() {
		return openingStockDetailsLicenceId;
	}

	public void setOpeningStockDetailsLicenceId(Integer openingStockDetailsLicenceId) {
		this.openingStockDetailsLicenceId = openingStockDetailsLicenceId;
	}

	public Ornament getOrnament() {
		return ornament;
	}

	public void setOrnament(Ornament ornament) {
		this.ornament = ornament;
	}

	public Double getGrams() {
		return grams;
	}

	public void setGrams(Double grams) {
		this.grams = grams;
	}

	public BillType getBillType() {
		return billType;
	}

	public void setBillType(BillType billType) {
		this.billType = billType;
	}

	public Licence getLicence() {
		return licence;
	}

	public void setLicence(Licence licence) {
		this.licence = licence;
	}

	public String getDeletedFlag() {
		return deletedFlag;
	}

	public void setDeletedFlag(String deletedFlag) {
		this.deletedFlag = deletedFlag;
	}

	@Override
	public int hashCode() {
		return Objects.hash(billType, deletedFlag, grams, licence, openingStockDetailsLicenceId, ornament);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OpeningStockDetailsLicence other = (OpeningStockDetailsLicence) obj;
		return Objects.equals(billType, other.billType) && Objects.equals(deletedFlag, other.deletedFlag)
				&& Objects.equals(grams, other.grams) && Objects.equals(licence, other.licence)
				&& Objects.equals(openingStockDetailsLicenceId, other.openingStockDetailsLicenceId)
				&& Objects.equals(ornament, other.ornament);
	}

}
