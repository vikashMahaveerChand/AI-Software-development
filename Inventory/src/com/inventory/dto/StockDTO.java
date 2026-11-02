package com.inventory.dto;

import java.util.List;

import com.inventory.model.Bill;

public class StockDTO {
	
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
	
	private List<StockDTOList> stockDTOList;
	
	List<Bill> billListCurrent;
	List<Bill> billListOld;
	List<Bill> purchaseBillListCurrent;
	List<Bill> purchaseBillListOld;
	
	
	private String totalGSTStockStr;
	private String totalNONGSTStockStr;
	
	
	private String gstStockStr;
	private String nonGstStockStr;
	
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
	public List<StockDTOList> getStockDTOList() {
		return stockDTOList;
	}
	public void setStockDTOList(List<StockDTOList> stockDTOList) {
		this.stockDTOList = stockDTOList;
	}
	public List<Bill> getBillListCurrent() {
		return billListCurrent;
	}
	public void setBillListCurrent(List<Bill> billListCurrent) {
		this.billListCurrent = billListCurrent;
	}
	public List<Bill> getBillListOld() {
		return billListOld;
	}
	public void setBillListOld(List<Bill> billListOld) {
		this.billListOld = billListOld;
	}
	public List<Bill> getPurchaseBillListCurrent() {
		return purchaseBillListCurrent;
	}
	public void setPurchaseBillListCurrent(List<Bill> purchaseBillListCurrent) {
		this.purchaseBillListCurrent = purchaseBillListCurrent;
	}
	public List<Bill> getPurchaseBillListOld() {
		return purchaseBillListOld;
	}
	public void setPurchaseBillListOld(List<Bill> purchaseBillListOld) {
		this.purchaseBillListOld = purchaseBillListOld;
	}
	public String getGstStockStr() {
		return gstStockStr;
	}
	public void setGstStockStr(String gstStockStr) {
		this.gstStockStr = gstStockStr;
	}
	public String getNonGstStockStr() {
		return nonGstStockStr;
	}
	public void setNonGstStockStr(String nonGstStockStr) {
		this.nonGstStockStr = nonGstStockStr;
	}
	public String getTotalGSTStockStr() {
		return totalGSTStockStr;
	}
	public void setTotalGSTStockStr(String totalGSTStockStr) {
		this.totalGSTStockStr = totalGSTStockStr;
	}
	public String getTotalNONGSTStockStr() {
		return totalNONGSTStockStr;
	}
	public void setTotalNONGSTStockStr(String totalNONGSTStockStr) {
		this.totalNONGSTStockStr = totalNONGSTStockStr;
	}
	
	

}
