package com.inventory.controller;

import java.util.Date;
import java.util.List;

import com.inventory.dto.CashReportDTO;
import com.inventory.dto.CashReportDTOList;
import com.inventory.dto.StockDTO;
import com.inventory.service.CashReportService;
import com.inventory.service.CashReportServiceImpl;

public class CashReportController {
	
	private static CashReportController myInstance = null;
	CashReportService cashReportService = null;
	
	private CashReportController(){
		try{
			cashReportService = CashReportServiceImpl.getInstace();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public static CashReportController getInstace(){
		if(myInstance == null){
			myInstance = new CashReportController();
		}
		return myInstance;
	}
	
	public CashReportDTO getCashReportList(Date fromDate, Date toDate, Integer licenceId, String billType) {
		// TODO Auto-generated method stub
		return cashReportService.getCashReportList(fromDate, toDate, licenceId, billType);
	}

}

