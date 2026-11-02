package com.inventory.model;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CONVERSION")
public class Conversion {
	
	@Id @GeneratedValue
	@Column(name = "CONVERSION_ID")
	private Integer conversionId;

	@Column(name = "DELETED_FLAG")
	private String deletedFlag;
	
	@OneToOne
	@JoinColumn(name="CONTACT_ID")
	private Contact contact;
	
	@OneToOne
	@JoinColumn(name="LICENCE_ID")
	private Licence licence;
	
	@OneToOne
	@JoinColumn(name="BILL_TYPE_ID")
	private BillType billType;
	
	@OneToOne
	@JoinColumn(name="METAL_ID")
	private Metal metal;
	
	@Column(name="CONVERSION_TYPE")
	private String conversionType;
	
	@Column(name="CONVERSION_DATE")
	private Date conversionDate;
	
	@Column(name="RATE")
	private Double rate;
	
	@Column(name="GRAMS")
	private Double grams;
	
	@Column(name="AMOUNT")
	private Double amount;
	
	@OneToOne
	@JoinColumn(name="BILL_SOURCE_ID")
	private BillSource billSource;
	

	public BillSource getBillSource() {
		return billSource;
	}

	public void setBillSource(BillSource billSource) {
		this.billSource = billSource;
	}

	public Integer getConversionId() {
		return conversionId;
	}

	public void setConversionId(Integer conversionId) {
		this.conversionId = conversionId;
	}

	public String getDeletedFlag() {
		return deletedFlag;
	}

	public void setDeletedFlag(String deletedFlag) {
		this.deletedFlag = deletedFlag;
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public Licence getLicence() {
		return licence;
	}

	public void setLicence(Licence licence) {
		this.licence = licence;
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

	public String getConversionType() {
		return conversionType;
	}

	public void setConversionType(String conversionType) {
		this.conversionType = conversionType;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Double getGrams() {
		return grams;
	}

	public Date getConversionDate() {
		return conversionDate;
	}

	public void setConversionDate(Date conversionDate) {
		this.conversionDate = conversionDate;
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

	@Override
	public int hashCode() {
		return Objects.hash(amount, billSource, billType, contact, conversionDate, conversionId, conversionType,
				deletedFlag, grams, licence, metal, rate);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Conversion other = (Conversion) obj;
		return Objects.equals(amount, other.amount) && Objects.equals(billSource, other.billSource)
				&& Objects.equals(billType, other.billType) && Objects.equals(contact, other.contact)
				&& Objects.equals(conversionDate, other.conversionDate)
				&& Objects.equals(conversionId, other.conversionId)
				&& Objects.equals(conversionType, other.conversionType)
				&& Objects.equals(deletedFlag, other.deletedFlag) && Objects.equals(grams, other.grams)
				&& Objects.equals(licence, other.licence) && Objects.equals(metal, other.metal)
				&& Objects.equals(rate, other.rate);
	}
	
	

}
