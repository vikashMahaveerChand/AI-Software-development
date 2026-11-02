package com.inventory.model.pbold;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "BILLDETAILS")
public class OldBillDetails {
	@Id @GeneratedValue
	@Column(name = "ID")
	private Integer id;
	@Column(name = "customerid")
	private Integer customerid;
	@Column(name = "loanno")
	private String loanno;
	@Column(name = "grams")
	private String grams;
	@Column(name = "amount")
	private String amount;
	@Column(name = "status")
	private String status;
	@Column(name = "redeemamount")
	private String redeemamount;
	@Column(name = "presentvalue")
	private String presentvalue;
	@Column(name = "amountinword")
	private String amountinword;
	@Column(name = "rateofinterest")
	private String rateofinterest;
	@Column(name = "deliveryno")
	private String deliveryno;
	@Column(name = "remark")
	private String remark;
	@Column(name = "newdate")
	private Date newdate;
	@Column(name = "newredeemdate")
	private Date newredeemdate;
	@Column(name = "redeemamountinternal")
	private String redeemamountinternal;
	@Column(name = "billedby")
	private String billedby;
	@Column(name = "cancelledby")
	private String cancelledby;
	@Column(name = "GrossWeight")
	private String GrossWeight;
	@Column(name = "slipamount")
	private String slipamount;
	@Column(name = "duplicateslipamount")
	private String duplicateslipamount;
	@Column(name = "amountpaidinhanddurningbilling")
	private String amountpaidinhanddurningbilling;
	@Column(name = "facilityname")
	private String facilityname;
	@Column(name = "PurityPercentage")
	private String PurityPercentage;
	@Column(name = "WeightAfterPurity")
	private String WeightAfterPurity;
	@Column(name = "jeweltype")
	private String jeweltype;
	@Column(name = "reamindeletter_YN")
	private String reamindeletter_YN;
	@Column(name = "redeemamountreceived")
	private String redeemamountreceived;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCustomerid() {
		return customerid;
	}
	public void setCustomerid(Integer customerid) {
		this.customerid = customerid;
	}
	public String getLoanno() {
		return loanno;
	}
	public void setLoanno(String loanno) {
		this.loanno = loanno;
	}
	public String getGrams() {
		return grams;
	}
	public void setGrams(String grams) {
		this.grams = grams;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRedeemamount() {
		return redeemamount;
	}
	public void setRedeemamount(String redeemamount) {
		this.redeemamount = redeemamount;
	}
	public String getPresentvalue() {
		return presentvalue;
	}
	public void setPresentvalue(String presentvalue) {
		this.presentvalue = presentvalue;
	}
	public String getAmountinword() {
		return amountinword;
	}
	public void setAmountinword(String amountinword) {
		this.amountinword = amountinword;
	}
	public String getRateofinterest() {
		return rateofinterest;
	}
	public void setRateofinterest(String rateofinterest) {
		this.rateofinterest = rateofinterest;
	}
	public String getDeliveryno() {
		return deliveryno;
	}
	public void setDeliveryno(String deliveryno) {
		this.deliveryno = deliveryno;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getNewdate() {
		return newdate;
	}
	public void setNewdate(Date newdate) {
		this.newdate = newdate;
	}
	public Date getNewredeemdate() {
		return newredeemdate;
	}
	public void setNewredeemdate(Date newredeemdate) {
		this.newredeemdate = newredeemdate;
	}
	public String getRedeemamountinternal() {
		return redeemamountinternal;
	}
	public void setRedeemamountinternal(String redeemamountinternal) {
		this.redeemamountinternal = redeemamountinternal;
	}
	public String getBilledby() {
		return billedby;
	}
	public void setBilledby(String billedby) {
		this.billedby = billedby;
	}
	public String getCancelledby() {
		return cancelledby;
	}
	public void setCancelledby(String cancelledby) {
		this.cancelledby = cancelledby;
	}
	public String getGrossWeight() {
		return GrossWeight;
	}
	public void setGrossWeight(String grossWeight) {
		GrossWeight = grossWeight;
	}
	public String getSlipamount() {
		return slipamount;
	}
	public void setSlipamount(String slipamount) {
		this.slipamount = slipamount;
	}
	public String getDuplicateslipamount() {
		return duplicateslipamount;
	}
	public void setDuplicateslipamount(String duplicateslipamount) {
		this.duplicateslipamount = duplicateslipamount;
	}
	public String getAmountpaidinhanddurningbilling() {
		return amountpaidinhanddurningbilling;
	}
	public void setAmountpaidinhanddurningbilling(String amountpaidinhanddurningbilling) {
		this.amountpaidinhanddurningbilling = amountpaidinhanddurningbilling;
	}
	public String getFacilityname() {
		return facilityname;
	}
	public void setFacilityname(String facilityname) {
		this.facilityname = facilityname;
	}
	public String getPurityPercentage() {
		return PurityPercentage;
	}
	public void setPurityPercentage(String purityPercentage) {
		PurityPercentage = purityPercentage;
	}
	public String getWeightAfterPurity() {
		return WeightAfterPurity;
	}
	public void setWeightAfterPurity(String weightAfterPurity) {
		WeightAfterPurity = weightAfterPurity;
	}
	public String getJeweltype() {
		return jeweltype;
	}
	public void setJeweltype(String jeweltype) {
		this.jeweltype = jeweltype;
	}
	public String getReamindeletter_YN() {
		return reamindeletter_YN;
	}
	public void setReamindeletter_YN(String reamindeletter_YN) {
		this.reamindeletter_YN = reamindeletter_YN;
	}
	public String getRedeemamountreceived() {
		return redeemamountreceived;
	}
	public void setRedeemamountreceived(String redeemamountreceived) {
		this.redeemamountreceived = redeemamountreceived;
	}

}
