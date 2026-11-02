package com.inventory.dao;

import java.util.Date;
import java.util.List;

import com.inventory.dto.CashReportDTOList;

public interface CashReportDAO {
	
	public List<CashReportDTOList> getCashReportList(Date fromDate,Date toDate,Integer licenceId,String billType);

}
