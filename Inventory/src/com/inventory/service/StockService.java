package com.inventory.service;

import java.util.Date;
import java.util.List;

import com.inventory.dto.StockDTO;
import com.inventory.dto.StockDTOOrnamentLevel;

public interface StockService {
	
	public StockDTO getStockList(Date fromDate,Date toDate,Integer licenceId,String billSource,String entryType);
	public StockDTO getStockListNew(Date fromDate,Date toDate,Integer licenceId,String billSource,String entryType);
	public StockDTOOrnamentLevel getStockListOrnamentLevel(Date fromDate,Date toDate,Integer licenceId,String billSource,Integer ornamentId,String entryType);

}
