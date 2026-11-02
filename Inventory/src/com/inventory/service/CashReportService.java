package com.inventory.service;

import java.util.Date;

import com.inventory.dto.CashReportDTO;

public interface CashReportService {
	public CashReportDTO getCashReportList(Date fromDate,Date toDate,Integer licenceId,String billType);
}
