package com.inventory.controller;

import java.util.Date;
import java.util.List;

import com.inventory.dto.BankEntryDTO;
import com.inventory.model.Bank;
import com.inventory.model.BankEntry;
import com.inventory.service.BankService;
import com.inventory.service.BankServiceImpl;

public class BankController {
	
	private static BankController myInstance = null;
	BankService bankService = null;
	
	private BankController(){
		try{
			bankService = BankServiceImpl.getInstace();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public static BankController getInstace(){
		if(myInstance == null){
			myInstance = new BankController();
		}
		return myInstance;
	}
	
	public Bank saveOrUpdateBank(Bank bank) {
		return bankService.saveOrUpdateBank(bank);
	}
	public List<Bank> getBankList(){
		return bankService.getBankList();
	}
	public Bank getBank(Integer bankId) {
		return bankService.getBank(bankId);
	}
	
	public BankEntry saveOrUpdateBankEntry(BankEntry bankEntry) {
		return bankService.saveOrUpdateBankEntry(bankEntry);
	}
	public BankEntryDTO getBankEntryList(Date fromDate,Date toDate,Integer bankId){
		return bankService.getBankEntryList(fromDate, toDate, bankId);
	}

}
