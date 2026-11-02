package com.inventory.service;

import java.util.Date;
import java.util.List;

import com.inventory.dto.BankEntryDTO;
import com.inventory.model.Bank;
import com.inventory.model.BankEntry;

public interface BankService {
	
	public Bank saveOrUpdateBank(Bank bank);
	public List<Bank> getBankList();
	public Bank getBank(Integer bankId);
	
	public BankEntry saveOrUpdateBankEntry(BankEntry bankEntry);
	public BankEntryDTO getBankEntryList(Date fromDate,Date toDate,Integer bankId);

}
