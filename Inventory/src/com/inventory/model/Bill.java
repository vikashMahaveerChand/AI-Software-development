package com.inventory.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "BILL")
public class Bill {
	
	@Id @GeneratedValue
	@Column(name = "BILL_ID")
	private Integer billId;

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
	
	@Column(name = "BILL_NUMBER")
	private String billNumber;
	
	@Column(name = "BILL_DATE")
	private Date billDate;
	
	@OneToOne
	@JoinColumn(name="CREATED_BY_USER_ID")
	private User user;
	
	@Column(name = "CREATED_DATE_TIME")
	private Date createdDateTime;

	@OneToOne
	@JoinColumn(name="UPDATED_BY_USER_ID")
	private User updatedUser;
	
	@Column(name = "LAST_UPDATED_DATE_TIME")
	private Date lastUpdatedDateTime;
	
	@OneToOne
	@JoinColumn(name="METAL_ID")
	private Metal metal;
	
	@Column(name = "BILL_GROSS_WEIGHT")
	private Double billGrossWeight;
	
	@Column(name = "BILL_WEIGHT_LESS")
	private Double billWeightLess;
	
	@Column(name = "BILL_WEIGHT")
	private Double billWeight;
	
	@Column(name = "BILL_PURE_WEIGHT")
	private Double billPureWeight;
	
	@Column(name = "RETURN_GROSS_WEIGHT")
	private Double returnGrossWeight;
	
	@Column(name = "RETURN_WEIGHT_LESS")
	private Double returnWeightLess;
	
	@Column(name = "RETURN_WEIGHT")
	private Double returnWeight;
	
	@Column(name = "RETURN_PURE_WEIGHT")
	private Double returnPureWeight;
	
	@Column(name = "GROSS_WEIGHT")
	private Double grossWeight;
	
	@Column(name = "WEIGHT_LESS")
	private Double weightLess;
	
	@Column(name = "WEIGHT")
	private Double weight;
	
	@Column(name = "PURE_WEIGHT")
	private Double pureWeight;
	
	@Column(name = "BILL_AMOUNT")
	private Double billAmount;
	
	@Column(name = "BILL_TAX_AMOUNT")
	private Double billTaxAmount;
	
	@Column(name = "BILL_ROUND_OFF")
	private Double billRoundOff;
	
	@Column(name = "BILL_TOTAL_AMOUNT")
	private Double billTotalAmount;
	
	@Column(name = "RETURN_AMOUNT")
	private Double returnAmount;
	
	@Column(name = "RETURN_ROUND_OFF")
	private Double returnRoundOff;
	
	@Column(name = "RETURN_TOTAL_AMOUNT")
	private Double returnTotalAmount;
	
	@Column(name = "AMOUNT")
	private Double amount;
	
	@Column(name = "TAX_AMOUNT")
	private Double taxAmount;
	
	@Column(name = "ROUND_OFF")
	private Double roundOff;
	
	@Column(name = "LESS_AMOUNT")
	private Double lessAmount;
	
	@Column(name = "TOTAL_AMOUNT")
	private Double totalAmount;
	
	@Column(name = "AMOUNT_IN_WORDS")
	private String amountInWords;
	
	@Column(name = "BILL_METAL_BALANCE")
	private Double billMetalBalance;
	
	@Column(name = "RETURN_METAL")
	private Double returnMetal;
	
	@Column(name = "METAL_BALANCE")
	private Double metalBalance;
	
	@Column(name = "NO_OF_PIECE")
	private Integer noOfPiece;
	
	@OneToOne
	@JoinColumn(name="BILL_SOURCE_ID")
	private BillSource billSource;

	
	@OneToMany(cascade=CascadeType.ALL,fetch = FetchType.LAZY,mappedBy="bill")
	@OrderBy("sNo ASC")
    private List<BillDetails> billDetailsList = new ArrayList<BillDetails>();
	
	
	@Transient
    private List<BillDetails> billDetails;
	
	
	@OneToMany(cascade=CascadeType.ALL,fetch = FetchType.LAZY,mappedBy="bill")
	@OrderBy("sNo ASC")
    private List<BillReturnDetails> billReturnDetailsList = new ArrayList<BillReturnDetails>();
	
	@Column(name = "OPENING_CASH_BALANCE")
	private String openingCashBalance;
	
	@Column(name = "OPENING_METAL_BALANCE")
	private String openingMetalBalance;
	
	@Column(name = "CLOSING_CASH_BALANCE")
	private String closingCashBalance;
	
	@Column(name = "CLOSING_METAL_BALANCE")
	private String closingMetalBalance;
	
	
	@Transient
    private List<BillReturnDetails> billReturnDetails;
	
	public Integer getBillId() {
		return billId;
	}

	public void setBillId(Integer billId) {
		this.billId = billId;
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

	public String getDeletedFlag() {
		return deletedFlag;
	}

	public void setDeletedFlag(String deletedFlag) {
		this.deletedFlag = deletedFlag;
	}

	

	public BillType getBillType() {
		return billType;
	}

	public void setBillType(BillType billType) {
		this.billType = billType;
	}

	public String getBillNumber() {
		return billNumber;
	}

	public void setBillNumber(String billNumber) {
		this.billNumber = billNumber;
	}

	public Date getBillDate() {
		return billDate;
	}

	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(Date createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	public User getUpdatedUser() {
		return updatedUser;
	}

	public void setUpdatedUser(User updatedUser) {
		this.updatedUser = updatedUser;
	}

	public Date getLastUpdatedDateTime() {
		return lastUpdatedDateTime;
	}

	public void setLastUpdatedDateTime(Date lastUpdatedDateTime) {
		this.lastUpdatedDateTime = lastUpdatedDateTime;
	}

	public Metal getMetal() {
		return metal;
	}

	public void setMetal(Metal metal) {
		this.metal = metal;
	}

	public Double getBillGrossWeight() {
		return billGrossWeight;
	}

	public void setBillGrossWeight(Double billGrossWeight) {
		this.billGrossWeight = billGrossWeight;
	}

	public Double getBillWeightLess() {
		return billWeightLess;
	}

	public void setBillWeightLess(Double billWeightLess) {
		this.billWeightLess = billWeightLess;
	}

	public Double getBillWeight() {
		return billWeight;
	}

	public void setBillWeight(Double billWeight) {
		this.billWeight = billWeight;
	}

	public Double getBillPureWeight() {
		return billPureWeight;
	}

	public void setBillPureWeight(Double billPureWeight) {
		this.billPureWeight = billPureWeight;
	}

	public Double getReturnGrossWeight() {
		return returnGrossWeight;
	}

	public void setReturnGrossWeight(Double returnGrossWeight) {
		this.returnGrossWeight = returnGrossWeight;
	}

	public Double getReturnWeightLess() {
		return returnWeightLess;
	}

	public void setReturnWeightLess(Double returnWeightLess) {
		this.returnWeightLess = returnWeightLess;
	}

	public Double getReturnWeight() {
		return returnWeight;
	}

	public void setReturnWeight(Double returnWeight) {
		this.returnWeight = returnWeight;
	}

	public Double getReturnPureWeight() {
		return returnPureWeight;
	}

	public void setReturnPureWeight(Double returnPureWeight) {
		this.returnPureWeight = returnPureWeight;
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

	public Double getPureWeight() {
		return pureWeight;
	}

	public void setPureWeight(Double pureWeight) {
		this.pureWeight = pureWeight;
	}

	public Double getBillAmount() {
		return billAmount;
	}

	public void setBillAmount(Double billAmount) {
		this.billAmount = billAmount;
	}

	public Double getBillTaxAmount() {
		return billTaxAmount;
	}

	public void setBillTaxAmount(Double billTaxAmount) {
		this.billTaxAmount = billTaxAmount;
	}

	public Double getBillRoundOff() {
		return billRoundOff;
	}

	public void setBillRoundOff(Double billRoundOff) {
		this.billRoundOff = billRoundOff;
	}

	public Double getBillTotalAmount() {
		return billTotalAmount;
	}

	public void setBillTotalAmount(Double billTotalAmount) {
		this.billTotalAmount = billTotalAmount;
	}

	public Double getReturnAmount() {
		return returnAmount;
	}

	public void setReturnAmount(Double returnAmount) {
		this.returnAmount = returnAmount;
	}

	public Double getReturnRoundOff() {
		return returnRoundOff;
	}

	public void setReturnRoundOff(Double returnRoundOff) {
		this.returnRoundOff = returnRoundOff;
	}

	public Double getReturnTotalAmount() {
		return returnTotalAmount;
	}

	public void setReturnTotalAmount(Double returnTotalAmount) {
		this.returnTotalAmount = returnTotalAmount;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(Double taxAmount) {
		this.taxAmount = taxAmount;
	}

	public Double getRoundOff() {
		return roundOff;
	}

	public void setRoundOff(Double roundOff) {
		this.roundOff = roundOff;
	}

	public Double getLessAmount() {
		return lessAmount;
	}

	public void setLessAmount(Double lessAmount) {
		this.lessAmount = lessAmount;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getAmountInWords() {
		return amountInWords;
	}

	public void setAmountInWords(String amountInWords) {
		this.amountInWords = amountInWords;
	}

	public Double getBillMetalBalance() {
		return billMetalBalance;
	}

	public void setBillMetalBalance(Double billMetalBalance) {
		this.billMetalBalance = billMetalBalance;
	}

	public Double getReturnMetal() {
		return returnMetal;
	}

	public void setReturnMetal(Double returnMetal) {
		this.returnMetal = returnMetal;
	}

	public Double getMetalBalance() {
		return metalBalance;
	}

	public void setMetalBalance(Double metalBalance) {
		this.metalBalance = metalBalance;
	}

	public Integer getNoOfPiece() {
		return noOfPiece;
	}

	public void setNoOfPiece(Integer noOfPiece) {
		this.noOfPiece = noOfPiece;
	}

	public BillSource getBillSource() {
		return billSource;
	}

	public void setBillSource(BillSource billSource) {
		this.billSource = billSource;
	}

	public List<BillDetails> getBillDetails() {
		return billDetails;
	}

	public void setBillDetails(List<BillDetails> billDetails) {
		this.billDetails = billDetails;
	}
	
	public void addBillDetailList(BillDetails billDetails){
		this.billDetailsList.add(billDetails);
		billDetails.setBill(this);
	}
	
	public List<BillDetails> getBillDetailsList(){
		return billDetailsList;
	}

	public void addBillReturnDetailList(BillReturnDetails billReturnDetails){
		this.billReturnDetailsList.add(billReturnDetails);
		billReturnDetails.setBill(this);
	}
	
	public List<BillReturnDetails> getBillReturnDetails() {
		return billReturnDetails;
	}

	public void setBillReturnDetails(List<BillReturnDetails> billReturnDetails) {
		this.billReturnDetails = billReturnDetails;
	}

	public List<BillReturnDetails> getBillReturnDetailsList() {
		return billReturnDetailsList;
	}

	@Override
	public int hashCode() {
		return Objects.hash(amount, amountInWords, billAmount, billDate, billDetails, billDetailsList, billGrossWeight,
				billId, billMetalBalance, billNumber, billPureWeight, billReturnDetails, billReturnDetailsList,
				billRoundOff, billSource, billTaxAmount, billTotalAmount, billType, billWeight, billWeightLess, contact,
				createdDateTime, deletedFlag, grossWeight, lastUpdatedDateTime, lessAmount, licence, metal,
				metalBalance, noOfPiece, pureWeight, returnAmount, returnGrossWeight, returnMetal, returnPureWeight,
				returnRoundOff, returnTotalAmount, returnWeight, returnWeightLess, roundOff, taxAmount, totalAmount,
				updatedUser, user, weight, weightLess);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bill other = (Bill) obj;
		return Objects.equals(amount, other.amount) && Objects.equals(amountInWords, other.amountInWords)
				&& Objects.equals(billAmount, other.billAmount) && Objects.equals(billDate, other.billDate)
				&& Objects.equals(billDetails, other.billDetails)
				&& Objects.equals(billDetailsList, other.billDetailsList)
				&& Objects.equals(billGrossWeight, other.billGrossWeight) && Objects.equals(billId, other.billId)
				&& Objects.equals(billMetalBalance, other.billMetalBalance)
				&& Objects.equals(billNumber, other.billNumber) && Objects.equals(billPureWeight, other.billPureWeight)
				&& Objects.equals(billReturnDetails, other.billReturnDetails)
				&& Objects.equals(billReturnDetailsList, other.billReturnDetailsList)
				&& Objects.equals(billRoundOff, other.billRoundOff) && Objects.equals(billSource, other.billSource)
				&& Objects.equals(billTaxAmount, other.billTaxAmount)
				&& Objects.equals(billTotalAmount, other.billTotalAmount) && Objects.equals(billType, other.billType)
				&& Objects.equals(billWeight, other.billWeight) && Objects.equals(billWeightLess, other.billWeightLess)
				&& Objects.equals(contact, other.contact) && Objects.equals(createdDateTime, other.createdDateTime)
				&& Objects.equals(deletedFlag, other.deletedFlag) && Objects.equals(grossWeight, other.grossWeight)
				&& Objects.equals(lastUpdatedDateTime, other.lastUpdatedDateTime)
				&& Objects.equals(lessAmount, other.lessAmount) && Objects.equals(licence, other.licence)
				&& Objects.equals(metal, other.metal) && Objects.equals(metalBalance, other.metalBalance)
				&& Objects.equals(noOfPiece, other.noOfPiece) && Objects.equals(pureWeight, other.pureWeight)
				&& Objects.equals(returnAmount, other.returnAmount)
				&& Objects.equals(returnGrossWeight, other.returnGrossWeight)
				&& Objects.equals(returnMetal, other.returnMetal)
				&& Objects.equals(returnPureWeight, other.returnPureWeight)
				&& Objects.equals(returnRoundOff, other.returnRoundOff)
				&& Objects.equals(returnTotalAmount, other.returnTotalAmount)
				&& Objects.equals(returnWeight, other.returnWeight)
				&& Objects.equals(returnWeightLess, other.returnWeightLess) && Objects.equals(roundOff, other.roundOff)
				&& Objects.equals(taxAmount, other.taxAmount) && Objects.equals(totalAmount, other.totalAmount)
				&& Objects.equals(updatedUser, other.updatedUser) && Objects.equals(user, other.user)
				&& Objects.equals(weight, other.weight) && Objects.equals(weightLess, other.weightLess);
	}

	public String getOpeningCashBalance() {
		return openingCashBalance;
	}

	public void setOpeningCashBalance(String openingCashBalance) {
		this.openingCashBalance = openingCashBalance;
	}

	public String getOpeningMetalBalance() {
		return openingMetalBalance;
	}

	public void setOpeningMetalBalance(String openingMetalBalance) {
		this.openingMetalBalance = openingMetalBalance;
	}

	public String getClosingCashBalance() {
		return closingCashBalance;
	}

	public void setClosingCashBalance(String closingCashBalance) {
		this.closingCashBalance = closingCashBalance;
	}

	public String getClosingMetalBalance() {
		return closingMetalBalance;
	}

	public void setClosingMetalBalance(String closingMetalBalance) {
		this.closingMetalBalance = closingMetalBalance;
	}

}
