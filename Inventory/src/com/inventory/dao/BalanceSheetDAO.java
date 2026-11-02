package com.inventory.dao;

import java.util.Date;
import java.util.List;

import com.inventory.dto.BalanceSheetDTO;

public interface BalanceSheetDAO {
	public BalanceSheetDTO getBalanceSheetList(Date toDate,Integer licenceId,String contactTypeName,String billSource,String billType,Integer contactId);

}
