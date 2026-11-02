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
@Table(name = "OPENING_BALANCE_DETAILS_CONTACT")
public class OpeningBalanceDetailsContact {
	
	@Id @GeneratedValue
	@Column(name = "OPENING_BALANCE_DETAILS_CONTACT_ID")
	private Integer OpeningBalanceDetailsContactId;
	
	@ManyToOne
	@JoinColumn(name="CONTACT_ID")
	private Contact contact;
	
	@OneToOne
	@JoinColumn(name="BILL_TYPE_ID")
	private BillType billType;
	
	@OneToOne
	@JoinColumn(name="METAL_ID")
	private Metal metal;
	
	@Column(name = "GRAMS")
	private Double grams;
	
	@Column(name = "AMOUNT")
	private Double amount;
	
	@Column(name = "DELETED_FLAG")
	private String deletedFlag;
	
	@OneToOne
	@JoinColumn(name="LICENCE_ID")
	private Licence licence;

	public Integer getOpeningBalanceDetailsContactId() {
		return OpeningBalanceDetailsContactId;
	}

	public void setOpeningBalanceDetailsContactId(Integer openingBalanceDetailsContactId) {
		OpeningBalanceDetailsContactId = openingBalanceDetailsContactId;
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public BillType getBillType() {
		return billType;
	}

	public void setBillType(BillType billType) {
		this.billType = billType;
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

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getDeletedFlag() {
		return deletedFlag;
	}

	public void setDeletedFlag(String deletedFlag) {
		this.deletedFlag = deletedFlag;
	}

	public Licence getLicence() {
		return licence;
	}

	public void setLicence(Licence licence) {
		this.licence = licence;
	}

	@Override
	public int hashCode() {
		return Objects.hash(OpeningBalanceDetailsContactId, amount, billType, contact, deletedFlag, grams, licence,
				metal);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OpeningBalanceDetailsContact other = (OpeningBalanceDetailsContact) obj;
		return Objects.equals(OpeningBalanceDetailsContactId, other.OpeningBalanceDetailsContactId)
				&& Objects.equals(amount, other.amount) && Objects.equals(billType, other.billType)
				&& Objects.equals(contact, other.contact) && Objects.equals(deletedFlag, other.deletedFlag)
				&& Objects.equals(grams, other.grams) && Objects.equals(licence, other.licence)
				&& Objects.equals(metal, other.metal);
	}

}
