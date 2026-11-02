package com.inventory.service;

import java.util.Date;
import java.util.List;

import com.inventory.dto.BalanceSheetDTO;

public interface BalanceSheetService {
	
	public BalanceSheetDTO getBalanceSheetList(Date toDate,Integer licenceId,String contactTypeName,String billSource,String billType,Integer contactId);

}
