package com.inventory.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.hibernate.SessionFactory;

import com.google.gson.GsonBuilder;
import com.inventory.controller.MasterController;
import com.inventory.dao.BillDAO;
import com.inventory.dao.BillDAOImpl;
import com.inventory.dao.MasterDAO;
import com.inventory.dao.MasterDAOImpl;
import com.inventory.dao.StockDAO;
import com.inventory.dao.StockDAOImpl;
import com.inventory.dto.BalanceSheetDTOList;
import com.inventory.dto.StockDTO;
import com.inventory.dto.StockDTOList;
import com.inventory.dto.StockDTOOrnamentLevel;
import com.inventory.dto.StockDTOOrnamentLevelDisplay;
import com.inventory.model.Bill;
import com.inventory.model.BillDetails;
import com.inventory.model.BillReturnDetails;
import com.inventory.model.BillSource;
import com.inventory.model.Metal;
import com.inventory.model.OpeningStockDetailsLicence;
import com.inventory.model.Ornament;
import com.inventory.utility.Calculator;
import com.inventory.utility.ConstantUtils;
import com.inventory.utility.HibernateUtil;

public class StockServiceImpl implements StockService {
	
	private static StockServiceImpl myInstance = null;
	SessionFactory factory = null;
	StockDAO stockDAO = null;
	MasterDAO masterDAO = null;
	BillDAO billDAO = null;
	private StockServiceImpl(){
		try{
		factory = HibernateUtil.getSessionFactory();
		stockDAO = StockDAOImpl.getInstace();
		masterDAO = MasterDAOImpl.getInstace();
		billDAO = BillDAOImpl.getInstace();
		
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public static StockServiceImpl getInstace(){
		if(myInstance == null){
			myInstance = new StockServiceImpl();
		}
		return myInstance;
	}

	@Override
	public StockDTO getStockList(Date fromDate, Date toDate, Integer licenceId, String billSource,String entryType) {
		// TODO Auto-generated method stub
		System.out.println("getStockList"+billSource);
		List<StockDTO> stockList = null;
		StockDTO stockDTO = null;
		try {
			stockDTO = new StockDTO();
			
			List<Ornament> ornamentList = masterDAO.getOrnamentList(licenceId);
			BillSource billSourceobj = null;
			List<StockDTOList> stockDTOList = new ArrayList<StockDTOList>();
			
			List<Bill> billListCurrent = new ArrayList<Bill>();
			List<Bill> billListOld = new ArrayList<Bill>();
			
			List<Bill> purchaseBillListCurrent =  new ArrayList<Bill>();
			List<Bill> purchaseBillListOld =  new ArrayList<Bill>();
			
			
			if(entryType.equalsIgnoreCase(ConstantUtils.GST_ENTRY)) {
				billSourceobj = masterDAO.getBillSourceByCode(ConstantUtils.RETAIL_BILLING_GST);
				List<Bill> retailListCurrent =null;
				if(billSourceobj!=null) {
					retailListCurrent = billDAO.getBillList(fromDate, toDate, licenceId, billSourceobj.getBillSourceId(), null,entryType);
				}
				if(retailListCurrent!=null && retailListCurrent.size()>0) {
					billListCurrent = retailListCurrent; 
				}
				List<Bill> retailListOld = null;
				if(billSourceobj!=null) {
					retailListOld = billDAO.getBillList(null, fromDate, licenceId, billSourceobj.getBillSourceId(), null,entryType);
				}
				if(retailListOld!=null && retailListOld.size()>0) {
					billListOld = retailListOld;  
				}
				
				billSourceobj = masterDAO.getBillSourceByCode(ConstantUtils.WHOLESALE_BILLING_GST);
				List<Bill> billListCurrentTemp = billDAO.getBillList(fromDate, toDate, licenceId, billSourceobj.getBillSourceId(), null,entryType);
				if(billListCurrentTemp!=null && billListCurrentTemp.size()>0) {
					billListCurrent.addAll(billListCurrentTemp);
				}
				
				List<Bill> billListOldTemp = billDAO.getBillList(null, fromDate, licenceId, billSourceobj.getBillSourceId(), null,entryType);
				if(billListOldTemp!=null && billListOldTemp.size()>0){
					billListOld.addAll(billListOldTemp);
				}
				
				BillSource purchaseBillSourceobj = masterDAO.getBillSourceByCode(ConstantUtils.PURCHASE_BILLING_GST);
				List<Bill> purchaseBillListCurrentTemp = billDAO.getBillList(fromDate, toDate, licenceId, purchaseBillSourceobj.getBillSourceId(), null,entryType);
				if(purchaseBillListCurrentTemp!=null && purchaseBillListCurrentTemp.size()>0) {
					purchaseBillListCurrent =  purchaseBillListCurrentTemp;
				}
				
				List<Bill> purchaseBillListOldTemp = billDAO.getBillList(null, fromDate, licenceId, purchaseBillSourceobj.getBillSourceId(), null,entryType);
				if(purchaseBillListOldTemp!=null && purchaseBillListOldTemp.size()>0) {
					purchaseBillListOld = purchaseBillListOldTemp;  
				}
			}else if(entryType.equalsIgnoreCase(ConstantUtils.NON_GST_ENTRY)) {
				billSourceobj = masterDAO.getBillSourceByCode(ConstantUtils.RETAIL_BILLING_NONGST);
				List<Bill> billListCurrentTemp = billDAO.getBillList(fromDate, toDate, licenceId, billSourceobj.getBillSourceId(), null,entryType);
				if(billListCurrentTemp!=null && billListCurrentTemp.size()>0) {
					billListCurrent = billListCurrentTemp;
				}
				List<Bill> billListOldTemp = billDAO.getBillList(null, fromDate, licenceId, billSourceobj.getBillSourceId(), null,entryType);
				if(billListOldTemp!=null && billListOldTemp.size()>0) {
					billListOld = billListOldTemp;  
				}
				billSourceobj = masterDAO.getBillSourceByCode(ConstantUtils.WHOLESALE_BILLING_NONGST);
				List<Bill> billListCurrentTemp2 = billDAO.getBillList(fromDate, toDate, licenceId, billSourceobj.getBillSourceId(), null,entryType);
				if(billListCurrentTemp2!=null && billListCurrentTemp2.size()>0) {
					billListCurrent.addAll(billListCurrentTemp2);
				}
				List<Bill> billListOldTemp2 = billDAO.getBillList(null, fromDate, licenceId, billSourceobj.getBillSourceId(), null,entryType);
				if(billListOldTemp2!=null && billListOldTemp2.size()>0) {
					billListOld.addAll(billListOldTemp2);
				}
				BillSource purchaseBillSourceobj = masterDAO.getBillSourceByCode(ConstantUtils.PURCHASE_BILLING_NONGST);
				
				List<Bill> purchaseBillListCurrentTemp = billDAO.getBillList(fromDate, toDate, licenceId, purchaseBillSourceobj.getBillSourceId(), null,entryType);
				if(purchaseBillListCurrentTemp!=null && purchaseBillListCurrentTemp.size()>0) {
					purchaseBillListCurrent =  purchaseBillListCurrentTemp;
				}
				
				List<Bill> purchaseBillListOldTemp = billDAO.getBillList(null, fromDate, licenceId, purchaseBillSourceobj.getBillSourceId(), null,entryType);
				if(purchaseBillListOldTemp!=null && purchaseBillListOldTemp.size()>0) {
					purchaseBillListOld = purchaseBillListOldTemp;  
				}
			}else {
				billListCurrent = new ArrayList<Bill>();
				billListOld = new ArrayList<Bill>();
				
				purchaseBillListCurrent =  new ArrayList<Bill>();
				purchaseBillListOld =  new ArrayList<Bill>();
			}
			
			
			stockDTO.setBillListCurrent(billListCurrent);
			stockDTO.setBillListOld(billListOld);
			stockDTO.setPurchaseBillListCurrent(purchaseBillListCurrent);
			stockDTO.setPurchaseBillListOld(purchaseBillListOld);
			
			
			
			for(Ornament orn: ornamentList) {
				double openingStock = orn.getOpeningStockDetailsList()
						.stream()
						.filter(ornStock -> ornStock.getBillType().getBillTypeName().equalsIgnoreCase(entryType))
						.mapToDouble(OpeningStockDetailsLicence::getGrams).sum();
				
				double billedGrossWeightOld = Calculator.getThreeDigitDecimal(getBillDetailsList(billListOld, orn).stream().mapToDouble(BillDetails::getWeight).sum());
				double billedGrossWeightCurrent = Calculator.getThreeDigitDecimal(getBillDetailsList(billListCurrent, orn).stream().mapToDouble(BillDetails::getWeight).sum());
				
				double billedReturnGrossWeightOld = Calculator.getThreeDigitDecimal(getBillReturnDetailsList(billListOld, orn).stream().mapToDouble(BillReturnDetails::getWeight).sum());
				double billedReturnGrossWeightCurrent = Calculator.getThreeDigitDecimal(getBillReturnDetailsList(billListCurrent, orn).stream().mapToDouble(BillReturnDetails::getWeight).sum());
				billedGrossWeightOld = billedGrossWeightOld-billedReturnGrossWeightOld;
				billedGrossWeightCurrent = billedGrossWeightCurrent-billedReturnGrossWeightCurrent;

				double purchasedGrossWeightOld = Calculator.getThreeDigitDecimal(getBillDetailsList(purchaseBillListOld, orn).stream().mapToDouble(BillDetails::getWeight).sum());
				double purchasedGrossWeightCurrent = Calculator.getThreeDigitDecimal(getBillDetailsList(purchaseBillListCurrent, orn).stream().mapToDouble(BillDetails::getWeight).sum());
				double purchasedReturnGrossWeightOld = Calculator.getThreeDigitDecimal(getBillReturnDetailsList(purchaseBillListOld, orn).stream().mapToDouble(BillReturnDetails::getWeight).sum());
				double purchasedReturnGrossWeightCurrent = Calculator.getThreeDigitDecimal(getBillReturnDetailsList(purchaseBillListCurrent, orn).stream().mapToDouble(BillReturnDetails::getWeight).sum());
				purchasedGrossWeightOld = purchasedGrossWeightOld-purchasedReturnGrossWeightOld;
				purchasedGrossWeightCurrent = purchasedGrossWeightCurrent-purchasedReturnGrossWeightCurrent;
				
				StockDTOList stockDTOListObj = new StockDTOList();
				
				stockDTOListObj.setOrnamentId(orn.getOrnamentId());
				stockDTOListObj.setOrnamentName(orn.getOrnamentName());
				
				if(entryType.equalsIgnoreCase(ConstantUtils.GST_ENTRY)) {
					double gstOpeningStock = Calculator.getThreeDigitDecimal(openingStock+purchasedGrossWeightOld-billedGrossWeightOld);					
					stockDTOListObj.setGstOpeningStock(gstOpeningStock);
					stockDTOListObj.setGstPurchase(purchasedGrossWeightCurrent);
					stockDTOListObj.setGstSale(billedGrossWeightCurrent);
					double gstStock = Calculator.getThreeDigitDecimal(gstOpeningStock+purchasedGrossWeightCurrent-billedGrossWeightCurrent);
					stockDTOListObj.setGstStock(gstStock);
					
				}else if(entryType.equalsIgnoreCase(ConstantUtils.NON_GST_ENTRY)) {
					double nonGSTOpeningStock = Calculator.getThreeDigitDecimal(openingStock+purchasedGrossWeightOld-billedGrossWeightOld);
					stockDTOListObj.setNonGSTOpeningStock(nonGSTOpeningStock);
					stockDTOListObj.setNonGstPurchase(purchasedGrossWeightCurrent);
					stockDTOListObj.setNonGstSale(billedGrossWeightCurrent);
					double nonGstStock = Calculator.getThreeDigitDecimal(nonGSTOpeningStock+purchasedGrossWeightCurrent-billedGrossWeightCurrent);
					stockDTOListObj.setNonGstStock(nonGstStock);
				}
				
				stockDTOListObj.setMetal(orn.getMetalId());
				
				stockDTOList.add(stockDTOListObj);
				
			}
			
			//Get total GST and NON GST stock -Starts
			if(stockDTOList!=null && stockDTOList.size()>0) {
				double gstStock = stockDTOList.stream().filter(s -> s.getGstStock()!=null).mapToDouble(StockDTOList::getGstOpeningStock).sum();
				double nonGstStock = stockDTOList.stream().filter(s->s.getNonGstStock()!=null).mapToDouble(StockDTOList::getNonGstStock).sum();
				
				stockDTO.setGstStock(gstStock);
				stockDTO.setNonGstStock(nonGstStock);
				
				
				Map<Metal, List<StockDTOList>> rawMap = stockDTOList.stream().collect(Collectors.groupingBy(StockDTOList::getMetal,Collectors.toList()));
				if(rawMap!=null) {
					HashMap<Metal, List<StockDTOList>> map
		            = rawMap.entrySet()
		                  .stream()
		                  .sorted((i1, i2)
		                              -> i1.getKey().getMetalName().compareTo(
		                                  i2.getKey().getMetalName()))
		                  .collect(Collectors.toMap(
		                      Map.Entry::getKey,
		                      Map.Entry::getValue,
		                      (e1, e2) -> e1, LinkedHashMap::new));
					
					
					StringBuffer gstStockStr = new StringBuffer();
					StringBuffer nonGstStockStr = new StringBuffer();
					
					map.forEach((k,v)->{
						if(gstStockStr.length()==0) {
							gstStockStr.append(k).append(" : ").append(
								Calculator.formatTwoDecimal(v.stream().filter(o -> o.getGstStock()!=null).mapToDouble(StockDTOList::getGstOpeningStock).sum()));
						}else {
							gstStockStr.append(", ").append(k).append(" : ").append(
								Calculator.formatTwoDecimal(v.stream().filter(o -> o.getGstStock()!=null).mapToDouble(StockDTOList::getGstOpeningStock).sum()));
						}
						
						if(nonGstStockStr.length()==0) {
							nonGstStockStr.append(k).append(" : ").append(
								Calculator.formatThreeDecimal(v.stream().filter(o -> o.getNonGstStock()!=null).mapToDouble(StockDTOList::getNonGstStock).sum()));
						}else {
							nonGstStockStr.append(", ").append(k).append(" : ").append(
								Calculator.formatThreeDecimal(v.stream().filter(o -> o.getNonGstStock()!=null).mapToDouble(StockDTOList::getNonGstStock).sum()));
						}
					});
					
					stockDTO.setGstStockStr(gstStockStr.toString());
					stockDTO.setNonGstStockStr(nonGstStockStr.toString());
				}
			}
			//Get total GST and NON GST stock -Ends
			
			stockDTO.setStockDTOList(stockDTOList);
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return stockDTO;
	}
	
	
	
	public static List<BillDetails> getBillDetailsList(List<Bill> bills,Ornament ornament){
		List<BillDetails> result = new ArrayList<BillDetails>();
		for(Bill bill:bills) {
			for(BillDetails billDetails:bill.getBillDetailsList()) {
				if(ornament.getOrnamentId().equals(billDetails.getOrnament().getOrnamentId())) {
					result.add(billDetails);
				}
			}
		}
		return result;
	}
	
	public static List<BillReturnDetails> getBillReturnDetailsList(List<Bill> bills,Ornament ornament){
		List<BillReturnDetails> result = new ArrayList<BillReturnDetails>();
		for(Bill bill:bills) {
			for(BillReturnDetails billDetails:bill.getBillReturnDetailsList()) {
				if(ornament.getOrnamentId().equals(billDetails.getOrnament().getOrnamentId())) {
					result.add(billDetails);
				}
			}
		}
		return result;
	}

	@Override
	public StockDTOOrnamentLevel getStockListOrnamentLevel(Date fromDate, Date toDate, Integer licenceId, String billSource,
			Integer ornamentId,String entryType) {
		// TODO Auto-generated method stub
		StockDTO stockDTO = null;
		StockDTOOrnamentLevel stockDTOOrnamentLevel = null;
		try {
			stockDTOOrnamentLevel = new StockDTOOrnamentLevel();
			
			stockDTO = getStockList(fromDate,toDate,licenceId,billSource,entryType);
			Ornament orn = MasterController.getInstace().getOrnament(ornamentId);
			List<StockDTOOrnamentLevelDisplay> displayList = new ArrayList<StockDTOOrnamentLevelDisplay>();
			//displayList = new ArrayList<StockDTOOrnamentLevelDisplay>();
			if(stockDTO!=null) {
				/*if(stockDTO.getStockDTOList()!=null && stockDTO.getStockDTOList().size()>0) {
					for(StockDTOList stock:stockDTO.getStockDTOList()) {
						if(stock.getOrnamentId().equals(ornamentId)) {
							stockDTONew.setStockDTOList(Arrays.asList(stock));
						}
					}
				}*/
				
				
				if(stockDTO.getBillListCurrent()!=null && stockDTO.getBillListCurrent().size()>0) {
					//stockDTOOrnamentLevel.setBillListCurrent(getBillDetailsList(stockDTO.getBillListCurrent(),orn));
					stockDTOOrnamentLevel.setBillDetails((getBillDetailsList(stockDTO.getBillListCurrent(),orn)));
					
					
					stockDTOOrnamentLevel.getBillDetails().stream().forEach(billDetails->{
						StockDTOOrnamentLevelDisplay display = new StockDTOOrnamentLevelDisplay();
						
						display.setDate(billDetails.getBill().getBillDate());
						display.setName(billDetails.getBill().getContact().getContactName());
						display.setGrams(-billDetails.getGrossWeight());
						
						
						displayList.add(display);
					});
					
					
					
					
				}
				
				if(stockDTO.getPurchaseBillListCurrent()!=null && stockDTO.getPurchaseBillListCurrent().size()>0) {
					stockDTOOrnamentLevel.setPurchaseDetails(getBillDetailsList(stockDTO.getPurchaseBillListCurrent(),orn));
					
					
					stockDTOOrnamentLevel.getPurchaseDetails().stream().forEach(billDetails->{
						StockDTOOrnamentLevelDisplay display = new StockDTOOrnamentLevelDisplay();
						
						display.setDate(billDetails.getBill().getBillDate());
						display.setName(billDetails.getBill().getContact().getContactName());
						display.setGrams(billDetails.getGrossWeight());
						
						
						displayList.add(display);
					});
					
				}
				
				
				
				stockDTOOrnamentLevel.setDisplayList(displayList);
				
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return stockDTOOrnamentLevel;
	}

	@Override
	public StockDTO getStockListNew(Date fromDate, Date toDate, Integer licenceId, String billSource,
			String entryType) {
		// TODO Auto-generated method stub
		StockDTO stockDTO = null;
		try {
			stockDTO = stockDAO.getStockListNew(fromDate, toDate, licenceId, billSource, entryType);
			
			if(stockDTO!=null && stockDTO.getStockDTOList()!=null && stockDTO.getStockDTOList().size()>0) {
				
				Map<String, List<StockDTOList>> metalMap = stockDTO.getStockDTOList().stream()
						.collect(Collectors.groupingBy(StockDTOList::getMetalName,Collectors.toList()));
				
				
				String totalGSTStockStr="";
				String totalNONGSTStockStr="";
				StringBuffer totalGSTStockSB = new StringBuffer();
				StringBuffer totalNONGSTStockSB = new StringBuffer();
				
				metalMap.forEach((metal,stockDTOList)->{
					
					double gstTotalStock = stockDTOList.stream().mapToDouble(StockDTOList::getGstStock).sum();
					
					if(gstTotalStock>0.0) {
						if(totalGSTStockSB.length()==0) {
							totalGSTStockSB.append(metal).append(": ").append(gstTotalStock);
						}else {
							totalGSTStockSB.append(", ").append(metal).append(": ").append(gstTotalStock);
						}
					}
					
					double nonGSTTotalStock = stockDTOList.stream().mapToDouble(StockDTOList::getNonGstStock).sum();
					
					if(nonGSTTotalStock>0.0) {
						if(totalNONGSTStockSB.length()==0) {
							totalNONGSTStockSB.append(metal).append(": ").append(nonGSTTotalStock);
						}else {
							totalNONGSTStockSB.append(", ").append(metal).append(": ").append(nonGSTTotalStock);
						}
					}
					
				});
				
				
				totalGSTStockStr = totalGSTStockSB.toString();
				totalNONGSTStockStr = totalNONGSTStockSB.toString();
				
				System.out.println("metalMap Data : "+new GsonBuilder().setPrettyPrinting().create().toJson(metalMap));
				
				stockDTO.setTotalGSTStockStr(totalGSTStockStr);
				stockDTO.setTotalNONGSTStockStr(totalNONGSTStockStr);
				
				
				
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return stockDTO;
	}
	
	

}
