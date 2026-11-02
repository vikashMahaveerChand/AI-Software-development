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

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "PAWN_BILL")
public class PawnBill {
	
	@Id @GeneratedValue
	@Column(name = "PAWN_BILL_ID")
	private Integer pawnBillId;

	@Column(name = "DELETED_FLAG")
	private String deletedFlag;
	
	@OneToOne
	@JoinColumn(name="CONTACT_ID")
	private Contact contact;
	
	@OneToOne
	@JoinColumn(name="LICENCE_ID")
	private Licence licence;
	
	@Column(name = "BILL_NUMBER")
	private String billNumber;
	
	@Column(name = "BILL_DATE")
	private Date billDate;
	
	@OneToOne
	@JoinColumn(name="BILL_SOURCE_ID")
	private BillSource billSource;
	
	@Column(name = "TOTAL_GROSS_WEIGHT")
	private Double totalGrossWeight;
	
	@Column(name = "TOTAL_NET_WEIGHT")
	private Double totalNetWeight;
	
	@Column(name = "PLEDGE_AMOUNT")
	private Double pledgeAmount;
	
	@Column(name = "RATE_OF_INTEREST")
	private Double rateOfInterest;
	
	@Column(name = "BILL_CHARGES")
	private Double billCharges;
	
	@Column(name = "AMOUNT_TO_PAY")
	private Double amountToPay;
	
	@Column(name = "AMOUNT_IN_WORDS")
	private String amountInWords;
	
	@OneToOne
	@JoinColumn(name="PAWN_BILL_STATUS_ID")
	private PawnBillStatus pawnBillStatusId;
	
	@OneToMany(cascade=CascadeType.ALL,fetch = FetchType.EAGER,mappedBy="pawnBill")
	@OrderBy("sNo ASC")
    private List<PawnBillDetails> pawnBillDetailsList = new ArrayList<PawnBillDetails>();
	
	/*@OneToMany(cascade=CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name="BILL_ID")*/
	@Transient
    private List<PawnBillDetails> pawnBillDetails;
	
	//@OneToMany(cascade=CascadeType.ALL,fetch = FetchType.EAGER,mappedBy="pawnBill")
    //private List<PawnBillRedeemDetails> pawnBillRedeemDetailsList = new ArrayList<PawnBillRedeemDetails>();
	
	/*
	@OneToMany(mappedBy="pawnBill", fetch=FetchType.EAGER)
	@Where(clause = "DELETED_FLAG = 'N'")
	@Fetch(value = FetchMode.SUBSELECT)
	private List<PawnBillRedeemDetails> pawnBillRedeemDetailsList;
	*/
	@OneToOne(mappedBy="pawnBill", fetch=FetchType.EAGER)
	@Where(clause = "DELETED_FLAG = 'N'")
	//@Fetch(value = FetchMode.SUBSELECT)
	private PawnBillRedeemDetails pawnBillRedeemDetails;
	
	
	@OneToOne(mappedBy="pawnBill", fetch=FetchType.EAGER)
	@Where(clause = "DELETED_FLAG = 'N'")
	private PawnBillAuctionDetails pawnBillAuctionDetails;
	
	@Column(name="RATE_OF_INTEREST_PM")
	private Double rateOfInterestPerMonth;
	
	@Column(name="AUCTION_STATUS")
	private String auctionStatus;
	
	@Transient
	private String billStatusCode;
	
	@Transient
	private Date redeemDate;
	
	@Transient
	private Double redeemAmount;
	
	@Transient
	private Double compoundInterestAmount;
	
	@Transient
	private String deliveryNumber;
	
	@Transient
	private Integer noOfMonths;
	
	@Transient
	private String ornamentDescription;
	
	@Transient
	private Metal metal;
	
	
	
	public Integer getPawnBillId() {
		return pawnBillId;
	}

	public void setPawnBillId(Integer pawnBillId) {
		this.pawnBillId = pawnBillId;
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

	public String getBillNumber() {
		return billNumber;
	}

	public void setBillNumber(String billNumber) {
		this.billNumber = billNumber;
	}

	public BillSource getBillSource() {
		return billSource;
	}

	public void setBillSource(BillSource billSource) {
		this.billSource = billSource;
	}

	public Double getTotalGrossWeight() {
		return totalGrossWeight;
	}

	public void setTotalGrossWeight(Double totalGrossWeight) {
		this.totalGrossWeight = totalGrossWeight;
	}

	public Double getTotalNetWeight() {
		return totalNetWeight;
	}

	public void setTotalNetWeight(Double totalNetWeight) {
		this.totalNetWeight = totalNetWeight;
	}

	public Double getPledgeAmount() {
		return pledgeAmount;
	}

	public void setPledgeAmount(Double pledgeAmount) {
		this.pledgeAmount = pledgeAmount;
	}

	public Double getRateOfInterest() {
		return rateOfInterest;
	}

	public void setRateOfInterest(Double rateOfInterest) {
		this.rateOfInterest = rateOfInterest;
	}

	public Double getBillCharges() {
		return billCharges;
	}

	public void setBillCharges(Double billCharges) {
		this.billCharges = billCharges;
	}

	public Double getAmountToPay() {
		return amountToPay;
	}

	public void setAmountToPay(Double amountToPay) {
		this.amountToPay = amountToPay;
	}

	public String getAmountInWords() {
		return amountInWords;
	}

	public void setAmountInWords(String amountInWords) {
		this.amountInWords = amountInWords;
	}

	public PawnBillStatus getPawnBillStatusId() {
		return pawnBillStatusId;
	}

	public void setPawnBillStatusId(PawnBillStatus pawnBillStatusId) {
		this.pawnBillStatusId = pawnBillStatusId;
	}
	
	public List<PawnBillDetails> getPawnBillDetails() {
		return pawnBillDetails;
	}

	public void setPawnBillDetails(List<PawnBillDetails> pawnBillDetails) {
		this.pawnBillDetails = pawnBillDetails;
	}
	
	public void addPawnBillDetailList(PawnBillDetails pawnBillDetails){
		this.pawnBillDetailsList.add(pawnBillDetails);
		pawnBillDetails.setPawnBill(this);
	}
	
	public List<PawnBillDetails> getPawnBillDetailsList(){
		return pawnBillDetailsList;
	}

	public Date getBillDate() {
		return billDate;
	}

	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}



	public PawnBillRedeemDetails getPawnBillRedeemDetails() {
		return pawnBillRedeemDetails;
	}

	public void setPawnBillRedeemDetails(PawnBillRedeemDetails pawnBillRedeemDetails) {
		this.pawnBillRedeemDetails = pawnBillRedeemDetails;
	}

	public String getBillStatusCode() {
		return billStatusCode;
	}

	public void setBillStatusCode(String billStatusCode) {
		this.billStatusCode = billStatusCode;
	}

	public Date getRedeemDate() {
		return redeemDate;
	}

	public void setRedeemDate(Date redeemDate) {
		this.redeemDate = redeemDate;
	}

	public Double getRedeemAmount() {
		return redeemAmount;
	}

	public void setRedeemAmount(Double redeemAmount) {
		this.redeemAmount = redeemAmount;
	}

	public String getDeliveryNumber() {
		return deliveryNumber;
	}

	public void setDeliveryNumber(String deliveryNumber) {
		this.deliveryNumber = deliveryNumber;
	}

	public Double getRateOfInterestPerMonth() {
		return rateOfInterestPerMonth;
	}

	public void setRateOfInterestPerMonth(Double rateOfInterestPerMonth) {
		this.rateOfInterestPerMonth = rateOfInterestPerMonth;
	}

	public Integer getNoOfMonths() {
		return noOfMonths;
	}

	public void setNoOfMonths(Integer noOfMonths) {
		this.noOfMonths = noOfMonths;
	}

	public Double getCompoundInterestAmount() {
		return compoundInterestAmount;
	}

	public void setCompoundInterestAmount(Double compoundInterestAmount) {
		this.compoundInterestAmount = compoundInterestAmount;
	}

	public String getOrnamentDescription() {
		return ornamentDescription;
	}

	public void setOrnamentDescription(String ornamentDescription) {
		this.ornamentDescription = ornamentDescription;
	}

	public String getAuctionStatus() {
		return auctionStatus;
	}

	public void setAuctionStatus(String auctionStatus) {
		this.auctionStatus = auctionStatus;
	}

	public Metal getMetal() {
		return metal;
	}

	public void setMetal(Metal metal) {
		this.metal = metal;
	}

	public PawnBillAuctionDetails getPawnBillAuctionDetails() {
		return pawnBillAuctionDetails;
	}

	public void setPawnBillAuctionDetails(PawnBillAuctionDetails pawnBillAuctionDetails) {
		this.pawnBillAuctionDetails = pawnBillAuctionDetails;
	}

	@Override
	public int hashCode() {
		return Objects.hash(amountInWords, amountToPay, auctionStatus, billCharges, billDate, billNumber, billSource,
				billStatusCode, compoundInterestAmount, contact, deletedFlag, deliveryNumber, licence, metal,
				noOfMonths, ornamentDescription, pawnBillAuctionDetails, pawnBillDetails, pawnBillDetailsList,
				pawnBillId, pawnBillRedeemDetails, pawnBillStatusId, pledgeAmount, rateOfInterest,
				rateOfInterestPerMonth, redeemAmount, redeemDate, totalGrossWeight, totalNetWeight);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PawnBill other = (PawnBill) obj;
		return Objects.equals(amountInWords, other.amountInWords) && Objects.equals(amountToPay, other.amountToPay)
				&& Objects.equals(auctionStatus, other.auctionStatus) && Objects.equals(billCharges, other.billCharges)
				&& Objects.equals(billDate, other.billDate) && Objects.equals(billNumber, other.billNumber)
				&& Objects.equals(billSource, other.billSource) && Objects.equals(billStatusCode, other.billStatusCode)
				&& Objects.equals(compoundInterestAmount, other.compoundInterestAmount)
				&& Objects.equals(contact, other.contact) && Objects.equals(deletedFlag, other.deletedFlag)
				&& Objects.equals(deliveryNumber, other.deliveryNumber) && Objects.equals(licence, other.licence)
				&& Objects.equals(metal, other.metal) && Objects.equals(noOfMonths, other.noOfMonths)
				&& Objects.equals(ornamentDescription, other.ornamentDescription)
				&& Objects.equals(pawnBillAuctionDetails, other.pawnBillAuctionDetails)
				&& Objects.equals(pawnBillDetails, other.pawnBillDetails)
				&& Objects.equals(pawnBillDetailsList, other.pawnBillDetailsList)
				&& Objects.equals(pawnBillId, other.pawnBillId)
				&& Objects.equals(pawnBillRedeemDetails, other.pawnBillRedeemDetails)
				&& Objects.equals(pawnBillStatusId, other.pawnBillStatusId)
				&& Objects.equals(pledgeAmount, other.pledgeAmount)
				&& Objects.equals(rateOfInterest, other.rateOfInterest)
				&& Objects.equals(rateOfInterestPerMonth, other.rateOfInterestPerMonth)
				&& Objects.equals(redeemAmount, other.redeemAmount) && Objects.equals(redeemDate, other.redeemDate)
				&& Objects.equals(totalGrossWeight, other.totalGrossWeight)
				&& Objects.equals(totalNetWeight, other.totalNetWeight);
	}

}
