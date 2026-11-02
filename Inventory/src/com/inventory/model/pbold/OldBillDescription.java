package com.inventory.model.pbold;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "BILLDESCRIPTION")
public class OldBillDescription {
	@Id @GeneratedValue
	@Column(name = "ID")
	private Integer id;
	@Column(name = "loanno")
	private String loanno;
	@Column(name = "customerid")
	private Integer customerid;
	@Column(name = "sno")
	private String sno;
	@Column(name = "jeweltype")
	private String jeweltype;
	@Column(name = "description")
	private String description;
	@Column(name = "quantity")
	private String quantity;
	@Column(name = "weight")
	private String weight;
	@Column(name = "status")
	private String status;
	@Column(name = "date")
	private Date date;
	@Column(name = "redeemdate")
	private Date redeemdate;
	@Column(name = "amount")
	private String amount;
	@Column(name = "redeemamount")
	private String redeemamount;
	@Column(name = "redeemamountinternally")
	private String redeemamountinternally;
	@Column(name = "Grossweight")
	private String grossweight;
	@Column(name = "TotalDescription")
	private String totalDescription;
	@Column(name = "quantityinwords")
	private String quantityinwords;
	@Column(name = "facilityname")
	private String facilityname;
	@Column(name = "PurityPercentage")
	private String purityPercentage;
	@Column(name = "PureWeight")
	private String pureWeight;
	@Column(name = "IndivualRemarks")
	private String indivualRemarks;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLoanno() {
		return loanno;
	}
	public void setLoanno(String loanno) {
		this.loanno = loanno;
	}
	public Integer getCustomerid() {
		return customerid;
	}
	public void setCustomerid(Integer customerid) {
		this.customerid = customerid;
	}
	public String getSno() {
		return sno;
	}
	public void setSno(String sno) {
		this.sno = sno;
	}
	public String getJeweltype() {
		return jeweltype;
	}
	public void setJeweltype(String jeweltype) {
		this.jeweltype = jeweltype;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Date getRedeemdate() {
		return redeemdate;
	}
	public void setRedeemdate(Date redeemdate) {
		this.redeemdate = redeemdate;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getRedeemamount() {
		return redeemamount;
	}
	public void setRedeemamount(String redeemamount) {
		this.redeemamount = redeemamount;
	}
	public String getRedeemamountinternally() {
		return redeemamountinternally;
	}
	public void setRedeemamountinternally(String redeemamountinternally) {
		this.redeemamountinternally = redeemamountinternally;
	}
	public String getGrossweight() {
		return grossweight;
	}
	public void setGrossweight(String grossweight) {
		this.grossweight = grossweight;
	}
	public String getTotalDescription() {
		return totalDescription;
	}
	public void setTotalDescription(String totalDescription) {
		this.totalDescription = totalDescription;
	}
	public String getQuantityinwords() {
		return quantityinwords;
	}
	public void setQuantityinwords(String quantityinwords) {
		this.quantityinwords = quantityinwords;
	}
	public String getFacilityname() {
		return facilityname;
	}
	public void setFacilityname(String facilityname) {
		this.facilityname = facilityname;
	}
	public String getPurityPercentage() {
		return purityPercentage;
	}
	public void setPurityPercentage(String purityPercentage) {
		this.purityPercentage = purityPercentage;
	}
	public String getPureWeight() {
		return pureWeight;
	}
	public void setPureWeight(String pureWeight) {
		this.pureWeight = pureWeight;
	}
	public String getIndivualRemarks() {
		return indivualRemarks;
	}
	public void setIndivualRemarks(String indivualRemarks) {
		this.indivualRemarks = indivualRemarks;
	}

}
