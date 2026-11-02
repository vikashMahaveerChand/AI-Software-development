package com.inventory.dto;

public class LicenceOpeningStockDetailsDTO {
	
	private Integer ornamentId;
	private String ornamentName;
	private String ornamentKeyCode;
	private Integer licenceOpeningStockDetailsId;
	private Double gstGrams;
	private Double nonGstGrams;
	
	public Integer getOrnamentId() {
		return ornamentId;
	}
	public void setOrnamentId(Integer ornamentId) {
		this.ornamentId = ornamentId;
	}
	public String getOrnamentName() {
		return ornamentName;
	}
	public void setOrnamentName(String ornamentName) {
		this.ornamentName = ornamentName;
	}
	public String getOrnamentKeyCode() {
		return ornamentKeyCode;
	}
	public void setOrnamentKeyCode(String ornamentKeyCode) {
		this.ornamentKeyCode = ornamentKeyCode;
	}
	
	public Integer getLicenceOpeningStockDetailsId() {
		return licenceOpeningStockDetailsId;
	}
	public void setLicenceOpeningStockDetailsId(Integer licenceOpeningStockDetailsId) {
		this.licenceOpeningStockDetailsId = licenceOpeningStockDetailsId;
	}
	public Double getGstGrams() {
		return gstGrams;
	}
	public void setGstGrams(Double gstGrams) {
		this.gstGrams = gstGrams;
	}
	public Double getNonGstGrams() {
		return nonGstGrams;
	}
	public void setNonGstGrams(Double nonGstGrams) {
		this.nonGstGrams = nonGstGrams;
	}
	
	

}
