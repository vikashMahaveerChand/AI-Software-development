package com.inventory.reports;

public class GSTBillDataBean {
	
	//private String sNo;
	//private String description;
	//private String rate;
	//private String weight;
	//private String gstPercentage;
	//private String amount;
	private String hsncode;
	
	
	
	//private String grossWeightStr;
	//private String weightLessStr;
	//private String netWeightStr;
	//private String purity;
	//private String pureWeightStr;
	//private String mcRateStr;
	//private String mcAmount;
	
	
	String description;
	String grossWeight;
	String weightLess;
	String weight;
	String purity;
	String pureWeight;
	String wastage;
	String rate;
	String taxPercentage;
	String ornamentAmount;
	String taxAmount;
	String mcRate;
	String mcPerPiece;
	String mcAmount;
	String roundoff;
	String amount;
	String noopiece;
	String metalBalance;
	String sno;
	
	public GSTBillDataBean() {
		
	}
	public GSTBillDataBean(String hsncode, String description, String grossWeight, String weightLess, String weight,
			String purity, String pureWeight, String wastage, String rate, String taxPercentage, String ornamentAmount,
			String taxAmount, String mcRate, String mcPerPiece, String mcAmount, String roundoff, String amount,
			String noopiece, String metalBalance, String sno) {
		super();
		this.hsncode = hsncode;
		this.description = description;
		this.grossWeight = grossWeight;
		this.weightLess = weightLess;
		this.weight = weight;
		this.purity = purity;
		this.pureWeight = pureWeight;
		this.wastage = wastage;
		this.rate = rate;
		this.taxPercentage = taxPercentage;
		this.ornamentAmount = ornamentAmount;
		this.taxAmount = taxAmount;
		this.mcRate = mcRate;
		this.mcPerPiece = mcPerPiece;
		this.mcAmount = mcAmount;
		this.roundoff = roundoff;
		this.amount = amount;
		this.noopiece = noopiece;
		this.metalBalance = metalBalance;
		this.sno = sno;
	}
	public String getHsncode() {
		return hsncode;
	}
	public void setHsncode(String hsncode) {
		this.hsncode = hsncode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getGrossWeight() {
		return grossWeight;
	}
	public void setGrossWeight(String grossWeight) {
		this.grossWeight = grossWeight;
	}
	public String getWeightLess() {
		return weightLess;
	}
	public void setWeightLess(String weightLess) {
		this.weightLess = weightLess;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getPurity() {
		return purity;
	}
	public void setPurity(String purity) {
		this.purity = purity;
	}
	public String getPureWeight() {
		return pureWeight;
	}
	public void setPureWeight(String pureWeight) {
		this.pureWeight = pureWeight;
	}
	public String getWastage() {
		return wastage;
	}
	public void setWastage(String wastage) {
		this.wastage = wastage;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getTaxPercentage() {
		return taxPercentage;
	}
	public void setTaxPercentage(String taxPercentage) {
		this.taxPercentage = taxPercentage;
	}
	public String getOrnamentAmount() {
		return ornamentAmount;
	}
	public void setOrnamentAmount(String ornamentAmount) {
		this.ornamentAmount = ornamentAmount;
	}
	public String getTaxAmount() {
		return taxAmount;
	}
	public void setTaxAmount(String taxAmount) {
		this.taxAmount = taxAmount;
	}
	public String getMcRate() {
		return mcRate;
	}
	public void setMcRate(String mcRate) {
		this.mcRate = mcRate;
	}
	public String getMcPerPiece() {
		return mcPerPiece;
	}
	public void setMcPerPiece(String mcPerPiece) {
		this.mcPerPiece = mcPerPiece;
	}
	public String getMcAmount() {
		return mcAmount;
	}
	public void setMcAmount(String mcAmount) {
		this.mcAmount = mcAmount;
	}
	public String getRoundoff() {
		return roundoff;
	}
	public void setRoundoff(String roundoff) {
		this.roundoff = roundoff;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getNoopiece() {
		return noopiece;
	}
	public void setNoopiece(String noopiece) {
		this.noopiece = noopiece;
	}
	public String getMetalBalance() {
		return metalBalance;
	}
	public void setMetalBalance(String metalBalance) {
		this.metalBalance = metalBalance;
	}
	public String getSno() {
		return sno;
	}
	public void setSno(String sno) {
		this.sno = sno;
	}
	
	
}
