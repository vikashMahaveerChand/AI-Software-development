package com.inventory.dto;

import com.inventory.model.Metal;

public class StockDTOList {
	
	private Integer ornamentId;
	private String ornamentName;
	
	private Double gstOpeningStock;
	private Double nonGSTOpeningStock;
	
	private Double gstSale;
	private Double nonGstSale;
	
	private Double gstPurchase;
	private Double nonGstPurchase;
	
	private Double gstStock;
	private Double nonGstStock;
	
	private Metal metal;
	
	private String metalName;
	
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
	public Double getGstOpeningStock() {
		return gstOpeningStock;
	}
	public void setGstOpeningStock(Double gstOpeningStock) {
		this.gstOpeningStock = gstOpeningStock;
	}
	public Double getNonGSTOpeningStock() {
		return nonGSTOpeningStock;
	}
	public void setNonGSTOpeningStock(Double nonGSTOpeningStock) {
		this.nonGSTOpeningStock = nonGSTOpeningStock;
	}
	public Double getGstSale() {
		return gstSale;
	}
	public void setGstSale(Double gstSale) {
		this.gstSale = gstSale;
	}
	public Double getNonGstSale() {
		return nonGstSale;
	}
	public void setNonGstSale(Double nonGstSale) {
		this.nonGstSale = nonGstSale;
	}
	public Double getGstPurchase() {
		return gstPurchase;
	}
	public void setGstPurchase(Double gstPurchase) {
		this.gstPurchase = gstPurchase;
	}
	public Double getNonGstPurchase() {
		return nonGstPurchase;
	}
	public void setNonGstPurchase(Double nonGstPurchase) {
		this.nonGstPurchase = nonGstPurchase;
	}
	public Double getGstStock() {
		return gstStock;
	}
	public void setGstStock(Double gstStock) {
		this.gstStock = gstStock;
	}
	public Double getNonGstStock() {
		return nonGstStock;
	}
	public void setNonGstStock(Double nonGstStock) {
		this.nonGstStock = nonGstStock;
	}
	public Metal getMetal() {
		return metal;
	}
	public void setMetal(Metal metal) {
		this.metal = metal;
	}
	public String getMetalName() {
		return metalName;
	}
	public void setMetalName(String metalName) {
		this.metalName = metalName;
	}
	
	

}
