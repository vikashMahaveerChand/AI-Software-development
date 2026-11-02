package com.inventory.service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.inventory.controller.MasterController;
import com.inventory.dao.CashReportDAOImpl;
import com.inventory.dto.CashReportDTO;
import com.inventory.dto.CashReportDTOList;
import com.inventory.dto.PaymentDTOList;
import com.inventory.model.Licence;
import com.inventory.model.OpeningBalanceDetailsLicence;
import com.inventory.utility.ConstantUtils;
import com.inventory.utility.Session;

public class CashReportServiceImpl implements CashReportService {
	
	private static CashReportServiceImpl myInstance = null;
	CashReportDAOImpl cashDAO = null;
	
	private CashReportServiceImpl(){
		try{
			cashDAO = CashReportDAOImpl.getInstace();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public static CashReportServiceImpl getInstace(){
		if(myInstance == null){
			myInstance = new CashReportServiceImpl();
		}
		return myInstance;
	}

	@Override
	public CashReportDTO getCashReportList(Date fromDate, Date toDate, Integer licenceId, String billType) {
		// TODO Auto-generated method stub
		CashReportDTO cashReportDTO = null;
		List<CashReportDTOList> cashReportList = null;
		List<CashReportDTOList> oldReportList = null;
		List<CashReportDTOList> toReturnCashReportList = null;
		try {
			cashReportDTO = new CashReportDTO();
			System.out.println("getCashReportList billType :: "+billType);
			cashReportList = cashDAO.getCashReportList(fromDate, toDate, licenceId, billType);
			toReturnCashReportList = new ArrayList<CashReportDTOList>();
			
			Double totalOldAmountReceived = 0.0;
			Double totalOldGoldMetalReceived = 0.0;
			Double totalOldSilverMetalReceived = 0.0;
			Double totalOldAmountPaid = 0.0;
			Double toatlOldGoldMetalPaid = 0.0;
			Double toatlOldSilverMetalPaid = 0.0;
			boolean isOldDataAvailable =false;
			
			Double licenceGSTAmount=0.0;
			Double licenceNONGSTAmount = 0.0;
			Double licenceNONGSTGold = 0.0;
			Double licenceNONGSTSilver = 0.0;
			
			Licence licenceObj = MasterController.getInstace().getLicence(Session.licence.getLicenceId()) ;
			List<OpeningBalanceDetailsLicence> licenceOpeningBalance = licenceObj.getOpeningBalanceDetailsLicence();
			
			if(licenceOpeningBalance!=null){
				for(OpeningBalanceDetailsLicence licenceOpening: licenceOpeningBalance) {
					if(licenceOpening.getBillType().getBillTypeName().equalsIgnoreCase(ConstantUtils.GST_ENTRY) 
							&& licenceOpening.getAmount()!=null
							) {
						licenceGSTAmount = licenceOpening.getAmount();
					}
					if(licenceOpening.getBillType().getBillTypeName().equalsIgnoreCase(ConstantUtils.NON_GST_ENTRY)
							&& licenceOpening.getAmount()!=null
							) {
						licenceNONGSTAmount = licenceOpening.getAmount();
					}
					
					if(licenceOpening.getBillType().getBillTypeName().equalsIgnoreCase(ConstantUtils.NON_GST_ENTRY)
							&& licenceOpening.getGrams()!=null
							) {
						if(licenceOpening.getMetal().getMetalCode().equalsIgnoreCase(ConstantUtils.GOLD)) {
							licenceNONGSTGold = licenceOpening.getGrams();
						}
						
						if(licenceOpening.getMetal().getMetalCode().equalsIgnoreCase(ConstantUtils.SILVER)) {
							licenceNONGSTSilver = licenceOpening.getGrams();
						}
					}
				}
				
				System.out.println("licenceGSTAmount "+licenceGSTAmount);
				System.out.println("*licenceNONGSTAmount "+licenceNONGSTAmount);
			}
			
			
			if(fromDate!=null && toDate!=null) {
				String sDate1="01/01/1970";  
				Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
				LocalDate localDate = fromDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().minusDays(1);
				Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
				
				oldReportList = cashDAO.getCashReportList(date1, date, licenceId, billType);
				
				if(oldReportList!=null && oldReportList.size()>0) {
					isOldDataAvailable = true;
					totalOldAmountReceived = oldReportList.stream().filter(o -> o.getAmountReceived()!=null).mapToDouble(CashReportDTOList::getAmountReceived).sum();
					totalOldGoldMetalReceived = oldReportList.stream().filter(o -> o.getGoldMetalReceived()!=null).mapToDouble(CashReportDTOList::getGoldMetalReceived).sum();
					totalOldSilverMetalReceived = oldReportList.stream().filter(o -> o.getSilverMetalReceived()!=null).mapToDouble(CashReportDTOList::getSilverMetalReceived).sum();
					
					totalOldAmountPaid = oldReportList.stream().filter(o -> o.getAmountPaid()!=null).mapToDouble(CashReportDTOList::getAmountPaid).sum();
					toatlOldGoldMetalPaid = oldReportList.stream().filter(o -> o.getGoldMetalPaid()!=null).mapToDouble(CashReportDTOList::getGoldMetalPaid).sum();
					toatlOldSilverMetalPaid = oldReportList.stream().filter(o -> o.getSilverMetalPaid()!=null).mapToDouble(CashReportDTOList::getSilverMetalPaid).sum();
				}
			}
			
			if(isOldDataAvailable) {
				CashReportDTOList openingData = new CashReportDTOList();
				
				openingData.setCashReportId(-1);
				openingData.setRecordType(ConstantUtils.OPENING);
				openingData.setPaymentType(ConstantUtils.OPENING);
				
				openingData.setDescription(ConstantUtils.OPENING);
				
				if(billType.equalsIgnoreCase(ConstantUtils.GST_ENTRY)) {
					totalOldAmountReceived = totalOldAmountReceived+licenceGSTAmount;
				}else if(billType.equalsIgnoreCase(ConstantUtils.NON_GST_ENTRY)) {
					totalOldAmountReceived = totalOldAmountReceived+licenceNONGSTAmount;
					totalOldGoldMetalReceived = totalOldGoldMetalReceived+licenceNONGSTGold;
					totalOldSilverMetalReceived = totalOldSilverMetalReceived+licenceNONGSTSilver;
				}
				
				openingData.setAmountReceived(totalOldAmountReceived);
				openingData.setGoldMetalReceived(totalOldGoldMetalReceived);
				openingData.setSilverMetalReceived(totalOldSilverMetalReceived);
				
				openingData.setAmountPaid(totalOldAmountPaid);
				openingData.setGoldMetalPaid(toatlOldGoldMetalPaid);
				openingData.setSilverMetalPaid(toatlOldSilverMetalPaid);
				
				toReturnCashReportList.add(openingData);
			}else {
				CashReportDTOList openingData = new CashReportDTOList();
				openingData.setCashReportId(-1);
				openingData.setRecordType(ConstantUtils.OPENING);
				openingData.setPaymentType(ConstantUtils.OPENING);
				
				openingData.setDescription(ConstantUtils.OPENING);
				
				if(billType.equalsIgnoreCase(ConstantUtils.GST_ENTRY)) {
					openingData.setAmountReceived(licenceGSTAmount);
				}else if(billType.equalsIgnoreCase(ConstantUtils.NON_GST_ENTRY)) {
					openingData.setAmountReceived(licenceNONGSTAmount);
					openingData.setGoldMetalReceived(licenceNONGSTGold);
					openingData.setSilverMetalReceived(licenceNONGSTSilver);
				}
				
				
				toReturnCashReportList.add(openingData);
				
				
			}
			if(cashReportList!=null) {
			toReturnCashReportList.addAll(cashReportList);
			}
			
			
			//Calculation starts
			Double totalAmountReceived =0.0;
			Double totalAmountPaid =0.0;
			Double currentAmount =0.0;
			
			Double goldMetalReceived =0.0;
			Double goldMetalPaid =0.0;
			Double currentGoldMetal =0.0;
			
			Double silverMetalReceived =0.0;
			Double silverMetalPaid =0.0;
			Double currentSilverMetal =0.0;
			
			
			totalAmountReceived = toReturnCashReportList.stream().filter(o -> o.getAmountReceived()!=null).mapToDouble(CashReportDTOList::getAmountReceived).sum();
			totalAmountPaid = toReturnCashReportList.stream().filter(o -> o.getAmountPaid()!=null).mapToDouble(CashReportDTOList::getAmountPaid).sum();
			currentAmount = totalAmountReceived - totalAmountPaid;
			
			goldMetalReceived = toReturnCashReportList.stream().filter(o -> o.getGoldMetalReceived()!=null).mapToDouble(CashReportDTOList::getGoldMetalReceived).sum();
			goldMetalPaid = toReturnCashReportList.stream().filter(o -> o.getGoldMetalPaid()!=null).mapToDouble(CashReportDTOList::getGoldMetalPaid).sum();
			currentGoldMetal = goldMetalReceived - goldMetalPaid;
			
			
			silverMetalReceived = toReturnCashReportList.stream().filter(o -> o.getSilverMetalReceived()!=null).mapToDouble(CashReportDTOList::getSilverMetalReceived).sum();
			silverMetalPaid = toReturnCashReportList.stream().filter(o -> o.getSilverMetalPaid()!=null).mapToDouble(CashReportDTOList::getSilverMetalPaid).sum();
			currentSilverMetal = silverMetalReceived - silverMetalPaid;
			//Calculation Ends
			cashReportDTO.setAmountAvailable(currentAmount);
			cashReportDTO.setGoldMetalAvailable(currentGoldMetal);
			cashReportDTO.setSilverMetalAvailable(currentSilverMetal);
			cashReportDTO.setCashReportDTOList(toReturnCashReportList);
			
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return cashReportDTO;
	}

}
