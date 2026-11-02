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
@Table(name = "BILL_NUMBER")
public class BillNumber {

	@Id @GeneratedValue
	@Column(name = "BILL_NUMBER_ID")
	private Integer billNumberId;
	
	
	@OneToOne
	@JoinColumn(name="LICENCE_ID")
	private Licence licence;
	
	@OneToOne
	@JoinColumn(name="BILL_SOURCE_ID")
	private BillSource billSource;
	
	@Column(name = "DELETED_FLAG")
	private String deletedFlag;
	
	@Column(name = "BILL_NUMBER")
	private Integer billNumber;
	
	@Column(name = "BILL_LETTER")
	private String billLetter;

	public Integer getBillNumberId() {
		return billNumberId;
	}

	public void setBillNumberId(Integer billNumberId) {
		this.billNumberId = billNumberId;
	}

	public Licence getLicence() {
		return licence;
	}

	public void setLicence(Licence licence) {
		this.licence = licence;
	}

	public BillSource getBillSource() {
		return billSource;
	}

	public void setBillSource(BillSource billSource) {
		this.billSource = billSource;
	}

	public String getDeletedFlag() {
		return deletedFlag;
	}

	public void setDeletedFlag(String deletedFlag) {
		this.deletedFlag = deletedFlag;
	}

	public Integer getBillNumber() {
		return billNumber;
	}

	public void setBillNumber(Integer billNumber) {
		this.billNumber = billNumber;
	}

	public String getBillLetter() {
		return billLetter;
	}

	public void setBillLetter(String billLetter) {
		this.billLetter = billLetter;
	}

	@Override
	public int hashCode() {
		return Objects.hash(billLetter, billNumber, billNumberId, billSource, deletedFlag, licence);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BillNumber other = (BillNumber) obj;
		return Objects.equals(billLetter, other.billLetter) && Objects.equals(billNumber, other.billNumber)
				&& Objects.equals(billNumberId, other.billNumberId) && Objects.equals(billSource, other.billSource)
				&& Objects.equals(deletedFlag, other.deletedFlag) && Objects.equals(licence, other.licence);
	}
}
