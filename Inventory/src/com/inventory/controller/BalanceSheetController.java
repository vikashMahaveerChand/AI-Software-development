package com.inventory.controller;

import java.util.Date;
import java.util.List;

import com.inventory.dto.BalanceSheetDTO;
import com.inventory.service.BalanceSheetService;
import com.inventory.service.BalanceSheetServiceImpl;

public class BalanceSheetController {
	
	private static BalanceSheetController myInstance = null;
	BalanceSheetService balanceSheetService = null;
	
	private BalanceSheetController(){
		try{
			balanceSheetService = BalanceSheetServiceImpl.getInstace();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public static BalanceSheetController getInstace(){
		if(myInstance == null){
			myInstance = new BalanceSheetController();
		}
		return myInstance;
	}
	
	public BalanceSheetDTO getBalanceSheetList(Date toDate, Integer licenceId, String contactTypeName,
			String billSource,String billType,Integer contactId) {
		// TODO Auto-generated method stub
		return balanceSheetService.getBalanceSheetList(toDate, licenceId, contactTypeName, billSource,billType,contactId);
	}

}
