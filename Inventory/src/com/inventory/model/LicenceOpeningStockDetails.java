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
@Table(name = "LICENCE_OPENING_STOCK_DETAILS")
public class LicenceOpeningStockDetails {
	
	
	@Id @GeneratedValue
	@Column(name = "LICENCE_OPENING_STOCK_DETAILS_ID")
	private Integer licenceOpeningStockDetailsId;
	
	@ManyToOne
	@JoinColumn(name="ORNAMENT_ID")
	private Ornament ornament;
	
	@Column(name = "GST_GRAMS")
	private Double gstGrams;
	
	@Column(name = "NONGST_GRAMS")
	private Double nonGSTgrams;
	
	@Column(name = "DELETED_FLAG")
	private String deletedFlag;
	
	@Column(name = "LICENCE_ID")
	private Integer licenceId;
	
	/*@OneToOne
	@JoinColumn(name="ENTRY_TYPE_ID")
	private EntryType entryType;*/

	public Integer getLicenceOpeningStockDetailsId() {
		return licenceOpeningStockDetailsId;
	}

	public void setLicenceOpeningStockDetailsId(Integer licenceOpeningStockDetailsId) {
		this.licenceOpeningStockDetailsId = licenceOpeningStockDetailsId;
	}

	public Ornament getOrnament() {
		return ornament;
	}

	public void setOrnament(Ornament ornament) {
		this.ornament = ornament;
	}


	public String getDeletedFlag() {
		return deletedFlag;
	}

	public void setDeletedFlag(String deletedFlag) {
		this.deletedFlag = deletedFlag;
	}

	public Integer getLicenceId() {
		return licenceId;
	}

	public void setLicenceId(Integer licenceId) {
		this.licenceId = licenceId;
	}

	/*public EntryType getEntryType() {
		return entryType;
	}

	public void setEntryType(EntryType entryType) {
		this.entryType = entryType;
	}*/

	public Double getGstGrams() {
		return gstGrams;
	}

	public void setGstGrams(Double gstGrams) {
		this.gstGrams = gstGrams;
	}

	public Double getNonGSTgrams() {
		return nonGSTgrams;
	}

	public void setNonGSTgrams(Double nonGSTgrams) {
		this.nonGSTgrams = nonGSTgrams;
	}

	@Override
	public int hashCode() {
		return Objects.hash(deletedFlag, gstGrams, licenceId, licenceOpeningStockDetailsId, nonGSTgrams, ornament);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LicenceOpeningStockDetails other = (LicenceOpeningStockDetails) obj;
		return Objects.equals(deletedFlag, other.deletedFlag) && Objects.equals(gstGrams, other.gstGrams)
				&& Objects.equals(licenceId, other.licenceId)
				&& Objects.equals(licenceOpeningStockDetailsId, other.licenceOpeningStockDetailsId)
				&& Objects.equals(nonGSTgrams, other.nonGSTgrams) && Objects.equals(ornament, other.ornament);
	}
	

}
