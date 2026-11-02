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

import org.hibernate.annotations.Where;

@Entity
@Table(name = "PAYMENT_DETAILS")
//@Where(clause = "deletedFlag = 'N'")
public class PaymentDetails {
	
	@Id @GeneratedValue
	@Column(name = "PAYMENT_DETAILS_ID")
	private Integer paymentDetailsId;
	
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
	
	@Column(name = "DELETED_FLAG")
	private String deletedFlag;
	
	@Column(name = "PAYMENT_DATE_TIME")
	private Date paymentDate;
	
	@OneToOne
	@JoinColumn(name="BILL_SOURCE_ID")
	private BillSource billSource;
	
	@Column(name = "METAL_PAID")
	private Double metalPaid;
	
	@Column(name = "AMOUNT_PAID")
	private Double amountPaid;

	@OneToOne
	@JoinColumn(name="BANK_ID")
	private Bank bank;


	public Integer getPaymentDetailsId() {
		return paymentDetailsId;
	}

	public void setPaymentDetailsId(Integer paymentDetailsId) {
		this.paymentDetailsId = paymentDetailsId;
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

	public String getDeletedFlag() {
		return deletedFlag;
	}

	public void setDeletedFlag(String deletedFlag) {
		this.deletedFlag = deletedFlag;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public BillSource getBillSource() {
		return billSource;
	}

	public void setBillSource(BillSource billSource) {
		this.billSource = billSource;
	}

	public Double getMetalPaid() {
		return metalPaid;
	}

	public void setMetalPaid(Double metalPaid) {
		this.metalPaid = metalPaid;
	}

	public Double getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(Double amountPaid) {
		this.amountPaid = amountPaid;
	}

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	@Override
	public int hashCode() {
		return Objects.hash(amountPaid, bank, billSource, billType, contact, deletedFlag, licence, metal, metalPaid,
				paymentDate, paymentDetailsId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PaymentDetails other = (PaymentDetails) obj;
		return Objects.equals(amountPaid, other.amountPaid) && Objects.equals(bank, other.bank)
				&& Objects.equals(billSource, other.billSource) && Objects.equals(billType, other.billType)
				&& Objects.equals(contact, other.contact) && Objects.equals(deletedFlag, other.deletedFlag)
				&& Objects.equals(licence, other.licence) && Objects.equals(metal, other.metal)
				&& Objects.equals(metalPaid, other.metalPaid) && Objects.equals(paymentDate, other.paymentDate)
				&& Objects.equals(paymentDetailsId, other.paymentDetailsId);
	}
	
	
	
}
