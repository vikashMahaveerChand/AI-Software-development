package com.inventory.dao;

import java.util.Date;
import java.util.List;

import com.inventory.dto.StockDTO;

public interface StockDAO {
	
	public List<StockDTO> getStockList(Date fromDate,Date toDate,Integer licenceId,String billSource);
	public StockDTO getStockListNew(Date fromDate,Date toDate,Integer licenceId,String billSource,String entryType);

}
