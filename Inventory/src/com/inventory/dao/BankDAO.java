package com.inventory.dao;

import java.util.Date;
import java.util.List;

import com.inventory.model.Bank;
import com.inventory.model.BankEntry;

public interface BankDAO {
	
	public Bank saveOrUpdateBank(Bank bank);
	public List<Bank> getBankList();
	public Bank getBank(Integer bankId);
	
	public BankEntry saveOrUpdateBankEntry(BankEntry bankEntry);
	public List<BankEntry> getBankEntryList(Date fromDate,Date toDate,Integer bankId);
	
	
}
