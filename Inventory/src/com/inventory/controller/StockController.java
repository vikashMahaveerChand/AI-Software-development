package com.inventory.controller;

import java.util.Date;
import java.util.List;

import com.inventory.dto.StockDTO;
import com.inventory.dto.StockDTOOrnamentLevel;
import com.inventory.service.StockService;
import com.inventory.service.StockServiceImpl;

public class StockController {
	
	private static StockController myInstance = null;
	StockService stockService = null;
	
	private StockController(){
		try{
			stockService = StockServiceImpl.getInstace();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public static StockController getInstace(){
		if(myInstance == null){
			myInstance = new StockController();
		}
		return myInstance;
	}
	
	public StockDTO getStockList(Date fromDate,Date toDate,Integer licenceId,String billSource,String entryType){
		return stockService.getStockList(fromDate, toDate, licenceId, billSource,entryType);
	}
	
	public StockDTOOrnamentLevel getStockListOrnamentLevel(Date fromDate, Date toDate, Integer licenceId, String billSource,
			Integer ornamentId,String entryType) {
		return stockService.getStockListOrnamentLevel(fromDate, toDate, licenceId, billSource, ornamentId,entryType);
	}
	
	public StockDTO getStockListNew(Date fromDate,Date toDate,Integer licenceId,String billSource,String entryType) {
		return stockService.getStockListNew(fromDate, toDate, licenceId, billSource, entryType);
	}

}
