package com.inventory.reports;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

import com.inventory.controller.BalanceSheetController;
import com.inventory.controller.BillController;
import com.inventory.dto.BalanceSheetDTO;
import com.inventory.dto.BillDTO;
import com.inventory.dto.PaymentDTO;
import com.inventory.dto.PaymentDTOList;
import com.inventory.model.Bill;
import com.inventory.model.Contact;
import com.inventory.model.PawnBill;
import com.inventory.utility.Calculator;
import com.inventory.utility.Common;
import com.inventory.utility.ConstantUtils;
import com.inventory.utility.DateUtils;
import com.inventory.utility.Session;
import com.inventory.utility.StringUtils;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public class RunReport {
	
	/*public static void executeSampleReport() {
		try {
			String sourceFileName = 
			         "c://tools/jasperreports-5.0.1/test/jasper_report_template.jasper";
			
			sourceFileName = "C://121569/UserData/WIP/Works_New_After_VDI_Migration/Vikash/POC/Inventory/Inventory/reports/Demo.jasper";
			System.out.println("sourceFileName ->"+sourceFileName);
			      DataBeanList DataBeanList = new DataBeanList();
			      ArrayList<DataBean> dataList = DataBeanList.getDataBeanList();

			      JRBeanCollectionDataSource beanColDataSource = new 
			         JRBeanCollectionDataSource(dataList);
			      beanColDataSource =null;
			      Map parameters = new HashMap();
			      parameters.put("LicenceName","Vikash Jewellery");
			      parameters.put("TitleName","Vikash Jewellery");
			      try {
			    	  System.out.println("Calling"+parameters);
			         JasperFillManager.fillReportToFile( 
			            sourceFileName, parameters, beanColDataSource);
			         System.out.println("calling end");
			      } catch (JRException e) {
			         e.printStackTrace();
			      }
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}*/
	
	/*public static void executeDemoJasperReport() {
		try {
			JasperReport jasperReport = JasperCompileManager.compileReport("C://121569/UserData/WIP/Works_New_After_VDI_Migration/Vikash/POC/Inventory/Inventory/reports/Demo.jrxml");
		    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,new HashMap(), new JREmptyDataSource());
		    JasperExportManager.exportReportToPdfFile(jasperPrint, "sample.pdf");
		    
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}*/
	
	public static void callSample() {
		try {
			System.out.println("Call Sample11");
			 JasperDesign jd=null;
             JasperReport jr=null;
             JasperPrint jp2=null;
             jd=JRXmlLoader.load("reports/Blank_A4.jrxml");
             jr=JasperCompileManager.compileReport(jd);
             System.out.println("Compile Success");
             HashMap reportparam=new HashMap();
             ArrayList<String> result=new ArrayList<String>();
 			result.add("Vikash");
 			
 			DataBeanList DataBeanList = new DataBeanList();
 			ArrayList<DataBean> dataList = DataBeanList.getDataBeanList();
 			System.out.println("dataList->"+dataList);
 			//JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(result);
 			JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(dataList);
 			
             jp2=JasperFillManager.fillReport(jr, reportparam,beanColDataSource);
             JasperViewer.viewReport(jp2,false);
             //String sourceFile ="C:\\121569\\UserData\\WIP\\Works_New_After_VDI_Migration\\Vikash\\POC\\Inventory\\Inventory\\reports\\Blank_A4.jasper";
             //JasperFillManager.fillReport(sourceFile, reportparam,beanColDataSource);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void callSampleNew() {
		try {
			System.out.println("Call Sample New");
			 
             HashMap reportparam=new HashMap();
             ArrayList<String> result=new ArrayList<String>();
 			result.add("Vikash");
 			
 			DataBeanList DataBeanList = new DataBeanList();
 			ArrayList<DataBean> dataList = DataBeanList.getDataBeanList();
 			System.out.println("dataList->"+dataList);
 			JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(dataList);
 			
 			String sourceFile ="C:\\121569\\UserData\\WIP\\Works_New_After_VDI_Migration\\Vikash\\POC\\Inventory\\Inventory\\reports\\Blank_A4.jasper";
             
             JasperFillManager.fillReport(sourceFile, reportparam,beanColDataSource);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void printWholeSaleBill(Integer billId,String billSource,String entryType) {
		try {
			System.out.println("printGSTWholeSaleBill");
			JasperDesign jd=null;
            JasperReport jr=null;
            JasperPrint jp2=null;
            JRBeanCollectionDataSource beanColDataSource = null;
            String fileName="";
            if(billSource.equalsIgnoreCase(ConstantUtils.GOLD_WHOLESALE_GST) || billSource.equalsIgnoreCase(ConstantUtils.WHOLESALE_BILLING_GST)) {
            	fileName="reports/GST_WholesaleBill.jrxml";
            }else if(billSource.equalsIgnoreCase(ConstantUtils.GOLD_WHOLESALE_NONGST) || billSource.equalsIgnoreCase(ConstantUtils.WHOLESALE_BILLING_NONGST)) {
            	fileName="reports/WholesaleBillNONGST.jrxml";
            }else if(billSource.equalsIgnoreCase(ConstantUtils.GOLD_RETAIL_GST) || billSource.equalsIgnoreCase(ConstantUtils.SILVER_RETAIL_GST)) {
            	fileName="reports/GSTBill.jrxml";
            }
            jd=JRXmlLoader.load(fileName);
            jr=JasperCompileManager.compileReport(jd);           
            HashMap reportparam=new HashMap();
            
            
            //JasperReport jasperSubReport = JasperCompileManager.compileReport("reports/GSTBillSubreport.jrxml");

            
			if(billId>0) {
				Bill billObj = BillController.getInstace().getBill(billId);
				if(billObj!=null) {
					
					PaymentDTO paymentDTO = null;
					paymentDTO = BillController.getInstace().getPaymentDTODetails(null, null, billObj.getBillSource().getBillSourceId(), billObj.getContact().getContactId(), Session.licence.getLicenceId(),entryType);
					
					
					BalanceSheetDTO balanceSheetDTO = BalanceSheetController.getInstace().getBalanceSheetList(new Date(), Session.licence.getLicenceId(), billObj.getContact().getContactType().getContactTypeName(), billSource, entryType, billObj.getContact().getContactId());
					
					
					GSTBillDataBeanList DataBeanList = new GSTBillDataBeanList();
					ArrayList<GSTBillDataBean> dataList = DataBeanList.getGSTBillDataBeanList(billObj.getBillDetailsList());
					String rate="";
					for(GSTBillDataBean data:dataList) {
						if(data.getRate()!=null && !data.getRate().equals("")) {
							rate = data.getRate();
						}
					}
					
					if(billObj.getBillReturnDetailsList()!=null && billObj.getBillReturnDetailsList().size()>0) {
						String returnEntry = "<style isBold=\"true\" isItalic=\"true\" isUnderline=\"true\">static text</style>";
						returnEntry="**Return Entry**";
						dataList.add(new GSTBillDataBean("", "**"+returnEntry+"**", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""));
						dataList.addAll(DataBeanList.getGSTBillReturnDataBeanList(billObj.getBillReturnDetailsList()));
					}
					beanColDataSource = new JRBeanCollectionDataSource(dataList);
					String customerAddress = billObj.getContact().getDoorNo()+", "+billObj.getContact().getStreet().getStreetName()+" "+billObj.getContact().getStreet().getArea().getAreaName()
							+" "+billObj.getContact().getStreet().getArea().getCity()+" "+billObj.getContact().getStreet().getArea().getPincode();
					String customerState = billObj.getContact().getStreet().getArea().getCity().getState().getStateName();
					String licenceState = billObj.getLicence().getState().getStateName();
					
					
					//reportparam.put("subReport","reports/GSTBillSubreport.jasper");
					//reportparam.put("subreportParameter", jasperSubReport);
					
					reportparam.put("subReportData",dataList);
					reportparam.put("licenceName", billObj.getLicence().getLicenceName());
					reportparam.put("licenceAddress",billObj.getLicence().getAddress());
					reportparam.put("licenceContact",billObj.getLicence().getLandLine());
					reportparam.put("licenceNumber","GSTIN:"+billObj.getLicence().getGstNumber());
					
					reportparam.put("customerName",billObj.getContact().getContactName());
					
					if(billObj.getContact().getGstNumber()!=null && !billObj.getContact().getGstNumber().equalsIgnoreCase("")) {
						customerAddress = "GST #:"+billObj.getContact().getGstNumber()+"\n"+customerAddress;
					}
					reportparam.put("customerAddress",customerAddress+" \nState : "+customerState.trim());
					reportparam.put("billNo",billObj.getBillNumber());
					String[] billCreatedDateTimeArr = StringUtils.getStrDateTime(billObj.getCreatedDateTime()).split(" ");
					//System.out.println("bill date time "+StringUtils.getStrDate(billObj.getBillDate())+"\n"+billCreatedDateTimeArr[1]);
					reportparam.put("billDateTime", StringUtils.getStrDate(billObj.getBillDate())+" "+billCreatedDateTimeArr[1]);
					//reportparam.put("billDateTime", StringUtils.getStrDate(billObj.getBillDate()));
					reportparam.put("billDate", StringUtils.getStrDate(billObj.getBillDate()));
					//System.out.println("billTime ->"+(StringUtils.getStrDateTime(billObj.getCreatedDateTime())).split(" "));
					//System.out.println("Current DateTime ->"+(new Date()));
					
					reportparam.put("accountNumber",billObj.getLicence().getBank().getAccountNumber());
					reportparam.put("accountName",billObj.getLicence().getBank().getAccountHolderName());
					reportparam.put("ifscCode",billObj.getLicence().getBank().getIfscCode());
					reportparam.put("bankName",billObj.getLicence().getBank().getBankName());
					reportparam.put("bankBranch",billObj.getLicence().getBank().getBankBranch());
					
					reportparam.put("weight",Calculator.formatThreeDecimal(billObj.getWeight()));
					reportparam.put("pureWeight",Calculator.formatThreeDecimal(billObj.getPureWeight()));
					reportparam.put("amount",Calculator.formatTwoDecimal(billObj.getAmount()));
					
					//reportparam.put("totalAmount",Calculator.formatTwoDecimal(billObj.getTotalAmount()));
					reportparam.put("metalBalance",Calculator.formatTwoDecimal(billObj.getMetalBalance()));
					
					if(customerState.equalsIgnoreCase(licenceState)) {
						reportparam.put("gstText","1.5 % CGST :\n1.5 % SGST :");
						Double gstTaxAmount = billObj.getTaxAmount();
						Double cgstTaxAmount = Calculator.getTwoDigitDecimal(gstTaxAmount/2);
						Double sgstTaxAmount = Calculator.getTwoDigitDecimal(gstTaxAmount/2);
						
						String cgstTaxAmountStr = Calculator.formatTwoDecimal(cgstTaxAmount);
						String sgstTaxAmountStr = Calculator.formatTwoDecimal(sgstTaxAmount);
						String gstAmountStr = cgstTaxAmountStr+"\n"+sgstTaxAmountStr;
						reportparam.put("gstAmount",gstAmountStr);
						//reportparam.put("gstAmount",Calculator.formatTwoDecimal(billObj.getTaxAmount()));
					}else {
						reportparam.put("gstText","IGST :");
						reportparam.put("gstAmount",Calculator.formatTwoDecimal(billObj.getTaxAmount()));
					}
					
					
					reportparam.put("roundOff",Calculator.formatTwoDecimal(billObj.getRoundOff()));
					reportparam.put("totalAmount",Calculator.formatTwoDecimal(billObj.getTotalAmount()));
					reportparam.put("gstTotalAmount",Calculator.formatTwoDecimal(billObj.getTaxAmount()));
					if(billObj.getAmountInWords()!=null) {
					reportparam.put("amountinword", "Rupees "+billObj.getAmountInWords()+" only.");
					}else {
						reportparam.put("amountinword", " ");
					}
					/*if(paymentDTO.getMetalBalance()!=null){
						reportparam.put("totalMetalBalance",Calculator.formatTwoDecimal(paymentDTO.getMetalBalance()));
					}else {
						reportparam.put("totalMetalBalance","");
					}*/
					
					/*if(paymentDTO.getAmountBalance()!=null){
						reportparam.put("totalAmountBalance",Calculator.formatTwoDecimal(paymentDTO.getAmountBalance()));
					}else {
						reportparam.put("totalAmountBalance","");
					}*/
					
					//reportparam.put("totalAmountBalance", Calculator.formatTwoDecimal(balanceSheetDTO.getTotalAmountBalance()));
					//reportparam.put("totalMetalBalance", balanceSheetDTO.getTotalMetalBalanceStr());
					
					StringBuffer currentCashBalance = new StringBuffer();
					StringBuffer currentMetalBalance = new StringBuffer();
					
					if(billObj.getOpeningCashBalance()!=null && !billObj.getOpeningCashBalance().equalsIgnoreCase(ConstantUtils.BLANK)) {
						currentCashBalance.append("C/F: ").append(billObj.getOpeningCashBalance());
					}
					
					if(billObj.getClosingCashBalance()!=null && !billObj.getClosingCashBalance().equalsIgnoreCase(ConstantUtils.BLANK)) {
						currentCashBalance.append("  Cur: ").append(billObj.getClosingCashBalance());
					}
					
					
					if(billObj.getOpeningMetalBalance()!=null && !billObj.getOpeningMetalBalance().equalsIgnoreCase(ConstantUtils.BLANK)) {
						currentMetalBalance.append("C/F: ").append(billObj.getOpeningMetalBalance());
					}
					
					if(billObj.getClosingMetalBalance()!=null && !billObj.getClosingMetalBalance().equalsIgnoreCase(ConstantUtils.BLANK)) {
						currentMetalBalance.append("  Cur: ").append(billObj.getClosingMetalBalance());
					}
					
					reportparam.put("totalAmountBalance", currentCashBalance.toString());
					reportparam.put("totalMetalBalance", currentMetalBalance.toString());
					
					reportparam.put("rate", rate);
				}
			}else {
				DataBeanList DataBeanList = new DataBeanList();
				ArrayList<DataBean> dataList = DataBeanList.getDataBeanList();
				beanColDataSource = new JRBeanCollectionDataSource(dataList);
				
				reportparam.put("licenceName", "Vikash Jewels");
				reportparam.put("licenceAddress","# 28, Siva Sakthi Nagar, Madhavaram ,Chennai-600060");
				reportparam.put("licenceContact","9941336844/ 044-42321103");
				
				reportparam.put("customerName","Vikash Mahaveer Chand Jain Kunkulol");
				reportparam.put("customerAddress","GST: 1234567890123 \n # 28, Siva Sakthi Nagar, Madhavaram ,Chennai-600060 \n State Code:");
				reportparam.put("billNo","A8888");
				reportparam.put("billDateTime", "01/02/2021 \n 18:25:45");
				
				reportparam.put("accountNumber","547332442547332442");
				reportparam.put("accountName","Vikash Mahaveer Chand");
				reportparam.put("ifscCode","CITI0000003");
				
				reportparam.put("totalWeight","8888.888");
				reportparam.put("amount","10,00,000.00");
				reportparam.put("gstAmount","2,500.00");
				reportparam.put("totalAmount","12,50,000.00");
			}
			
			//String destFileName="D:/jasper.JRprint";
			/*JasperFillManager.fillReportToFile(jr, 
		            destFileName, reportparam, beanColDataSource);
			*/
			
            jp2=JasperFillManager.fillReport(jr, reportparam,beanColDataSource);
            JasperViewer.viewReport(jp2,false);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void printNONGSTGoldWholeSaleBill(Integer billId) {
		try {
			System.out.println("printGSTWholeSaleBill");
			JasperDesign jd=null;
            JasperReport jr=null;
            JasperPrint jp2=null;
            JRBeanCollectionDataSource beanColDataSource = null;
            jd=JRXmlLoader.load("reports/NONGST_GoldWholesaleBill.jrxml");
            jr=JasperCompileManager.compileReport(jd);           
            HashMap reportparam=new HashMap();
            //billId=-1;
			if(billId>0) {
				Bill billObj = BillController.getInstace().getBill(billId);
				if(billObj!=null) {
					
					GSTBillDataBeanList DataBeanList = new GSTBillDataBeanList();
					ArrayList<GSTBillDataBean> dataList = DataBeanList.getGSTBillDataBeanList(billObj.getBillDetailsList());
					beanColDataSource = new JRBeanCollectionDataSource(dataList);
					String customerAddress = billObj.getContact().getDoorNo()+", "+billObj.getContact().getStreet().getStreetName()+" "+billObj.getContact().getStreet().getArea().getAreaName()
							+" "+billObj.getContact().getStreet().getArea().getCity()+" "+billObj.getContact().getStreet().getArea().getPincode();
					String customerState = billObj.getContact().getStreet().getArea().getCity().getState().getStateName();
					String licenceState = billObj.getLicence().getState().getStateName();
					
					reportparam.put("licenceName", billObj.getLicence().getLicenceName());
					reportparam.put("licenceAddress",billObj.getLicence().getAddress());
					reportparam.put("licenceContact",billObj.getLicence().getLandLine());
					
					reportparam.put("customerName",billObj.getContact().getContactName());
					reportparam.put("customerAddress","GST #:"+billObj.getContact().getGstNumber()+" \n "+customerAddress+" \n State : "+customerState);
					reportparam.put("billNo",billObj.getBillNumber());
					String[] billCreatedDateTimeArr = StringUtils.getStrDateTime(billObj.getCreatedDateTime()).split(" ");
					
					//reportparam.put("billDateTime", StringUtils.getStrDate(billObj.getBillDate())+"\n"+billCreatedDateTimeArr[1]);
					reportparam.put("billDateTime", StringUtils.getStrDate(billObj.getBillDate()));
					//System.out.println("billTime ->"+(StringUtils.getStrDateTime(billObj.getCreatedDateTime())).split(" "));
					//System.out.println("Current DateTime ->"+(new Date()));
					
					reportparam.put("accountNumber",billObj.getLicence().getBank().getAccountNumber());
					reportparam.put("accountName",billObj.getLicence().getBank().getAccountHolderName());
					reportparam.put("ifscCode",billObj.getLicence().getBank().getIfscCode());
					reportparam.put("bankName",billObj.getLicence().getBank().getBankName());
					
					reportparam.put("totalWeight",Calculator.formatThreeDecimal(billObj.getWeight()));
					reportparam.put("amount",Calculator.formatTwoDecimal(billObj.getAmount()));
					
					if(customerState.equalsIgnoreCase(licenceState)) {
						reportparam.put("gstText","CGST :\nSGST :");
						Double gstTaxAmount = billObj.getTaxAmount();
						Double cgstTaxAmount = Calculator.getTwoDigitDecimal(gstTaxAmount/2);
						Double sgstTaxAmount = Calculator.getTwoDigitDecimal(gstTaxAmount/2);
						
						String cgstTaxAmountStr = Calculator.formatTwoDecimal(cgstTaxAmount);
						String sgstTaxAmountStr = Calculator.formatTwoDecimal(sgstTaxAmount);
						String gstAmountStr = cgstTaxAmountStr+"\n"+sgstTaxAmountStr;
						reportparam.put("gstAmount",gstAmountStr);
						//reportparam.put("gstAmount",Calculator.formatTwoDecimal(billObj.getTaxAmount()));
					}else {
						reportparam.put("gstText","IGST :");
						reportparam.put("gstAmount",Calculator.formatTwoDecimal(billObj.getTaxAmount()));
					}
					
					
					reportparam.put("roundOff",Calculator.formatTwoDecimal(billObj.getRoundOff()));
					reportparam.put("totalAmount",Calculator.formatTwoDecimal(billObj.getTotalAmount()));
				}
			}else {
				DataBeanList DataBeanList = new DataBeanList();
				ArrayList<DataBean> dataList = DataBeanList.getDataBeanList();
				beanColDataSource = new JRBeanCollectionDataSource(dataList);
				
				reportparam.put("licenceName", "Vikash Jewels");
				reportparam.put("licenceAddress","# 28, Siva Sakthi Nagar, Madhavaram ,Chennai-600060");
				reportparam.put("licenceContact","9941336844/ 044-42321103");
				
				reportparam.put("customerName","Vikash Mahaveer Chand Jain Kunkulol");
				reportparam.put("customerAddress","GST: 1234567890123 \n # 28, Siva Sakthi Nagar, Madhavaram ,Chennai-600060 \n State Code:");
				reportparam.put("billNo","A8888");
				reportparam.put("billDateTime", "01/02/2021 \n 18:25:45");
				
				reportparam.put("accountNumber","547332442547332442");
				reportparam.put("accountName","Vikash Mahaveer Chand");
				reportparam.put("ifscCode","CITI0000003");
				
				reportparam.put("totalWeight","8888.888");
				reportparam.put("amount","10,00,000.00");
				reportparam.put("gstAmount","2,500.00");
				reportparam.put("totalAmount","12,50,000.00");
			}
			
			
            jp2=JasperFillManager.fillReport(jr, reportparam,beanColDataSource);
            JasperViewer.viewReport(jp2,false);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void printAuctionReport() {
		
	}
	
	public static void printRemainderReport(List<PawnBill> pawnBillList) {
		try {
			
			System.out.println("printGSTWholeSaleBill");
			JasperDesign jd=null;
            JasperReport jr=null;
            JasperPrint jp2=null;
            JRBeanCollectionDataSource beanColDataSource = null;
            jd=JRXmlLoader.load("reports/RemainderReport.jrxml");
            jr=JasperCompileManager.compileReport(jd);           
            HashMap reportparam=new HashMap();
            
            reportparam.put("licenceName", Session.licence.getLicenceName());
            String licenceAddress = Session.licence.getAddress().replaceAll(",", "\n")+"\n"+StringUtils.capitalize(Session.licence.getState().getStateName());
            reportparam.put("licenceAddress", licenceAddress);
            reportparam.put("licenceContact", Session.licence.getLandLine());
            reportparam.put("printDate",StringUtils.getStrDate(new Date()));
            
            
            //pawnBillList.sort(Comparator.comparing(PawnBill::getContact));
            
            pawnBillList = pawnBillList.stream()
            	    .filter(p -> (p.getPawnBillStatusId().getPawnBillStatus().equalsIgnoreCase(ConstantUtils.PAWN_BILL_STATUS_PLEDGE)
            	    			&& p.getAuctionStatus().equalsIgnoreCase("Y")
            	    		))
            	    .collect(Collectors.toList());
            
            Comparator<PawnBill> compareByName = (PawnBill o1, PawnBill o2) -> o1.getContact().getContactId().compareTo( o2.getContact().getContactId() );
            
            Collections.sort(pawnBillList, compareByName);
            
            PawnBillBeanList DataBeanList = new PawnBillBeanList();
			ArrayList<PawnBillBean> dataList = DataBeanList.getPawnBillBeanList(pawnBillList);
			
			
            beanColDataSource = new JRBeanCollectionDataSource(dataList);
            
            
            jp2=JasperFillManager.fillReport(jr, reportparam,beanColDataSource);
            JasperViewer.viewReport(jp2,false);
			
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
	public static void printAuctionReport(List<PawnBill> pawnBillList,Date fromDate,Date toDate) {
		try {
			
			System.out.println("printAuctionReport");
			JasperDesign jd=null;
            JasperReport jr=null;
            JasperPrint jp2=null;
            JRBeanCollectionDataSource beanColDataSource = null;
            jd=JRXmlLoader.load("reports/AuctionReport.jrxml");
            jr=JasperCompileManager.compileReport(jd);           
            HashMap reportparam=new HashMap();
            
            String criteria = "From :"+StringUtils.getStrDate(fromDate)+" To:"+StringUtils.getStrDate(toDate);
            reportparam.put("licenceName", Session.licence.getLicenceName());
            String licenceAddress = Session.licence.getAddress().replaceAll(",", "\n")+"\n"+StringUtils.capitalize(Session.licence.getState().getStateName());
            reportparam.put("licenceAddress", licenceAddress);
            reportparam.put("licenceContact", Session.licence.getLandLine());
            reportparam.put("printDate",StringUtils.getStrDate(new Date()));
            reportparam.put("criteria",criteria);
            
            
            //pawnBillList.sort(Comparator.comparing(PawnBill::getContact));
            
            pawnBillList = pawnBillList.stream()
            	    .filter(p -> (p.getPawnBillStatusId().getPawnBillStatus().equalsIgnoreCase(ConstantUtils.PAWN_BILL_STATUS_PLEDGE)
            	    			&& p.getAuctionStatus().equalsIgnoreCase("Y")
            	    		))
            	    .collect(Collectors.toList());
            
            Comparator<PawnBill> compareByName = (PawnBill o1, PawnBill o2) -> o1.getContact().getContactId().compareTo( o2.getContact().getContactId() );
            
            Collections.sort(pawnBillList, compareByName);
            
            PawnBillBeanList DataBeanList = new PawnBillBeanList();
			ArrayList<PawnBillBean> dataList = DataBeanList.getPawnBillBeanList(pawnBillList);
			
			
            beanColDataSource = new JRBeanCollectionDataSource(dataList);
            
            System.out.println("List Size Auction:"+dataList.size());
            jp2=JasperFillManager.fillReport(jr, reportparam,beanColDataSource);
            JasperViewer.viewReport(jp2,false);
			
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void printPawnPledgeReport(List<PawnBill> pawnBillList,Date fromDate,Date toDate) {
		try {
			
			System.out.println("printPawnPledgeReport");
			JasperDesign jd=null;
            JasperReport jr=null;
            JasperPrint jp2=null;
            JRBeanCollectionDataSource beanColDataSource = null;
            jd=JRXmlLoader.load("reports/PawnPledgeReport.jrxml");
            jr=JasperCompileManager.compileReport(jd);           
            HashMap reportparam=new HashMap();
            
            String criteria = "From :"+StringUtils.getStrDate(fromDate)+" To:"+StringUtils.getStrDate(toDate);
            reportparam.put("licenceName", Session.licence.getLicenceName());
            String licenceAddress = Session.licence.getAddress().replaceAll(",", "\n")+"\n"+StringUtils.capitalize(Session.licence.getState().getStateName());
            reportparam.put("licenceAddress", licenceAddress);
            reportparam.put("licenceContact", Session.licence.getLandLine());
            reportparam.put("printDate",StringUtils.getStrDate(new Date()));
            reportparam.put("criteria",criteria);
            
            
            //pawnBillList.sort(Comparator.comparing(PawnBill::getContact));
            
            /*pawnBillList = pawnBillList.stream()
            	    .filter(p -> (p.getPawnBillStatusId().getPawnBillStatus().equalsIgnoreCase(ConstantUtils.PAWN_BILL_STATUS_PLEDGE)
            	    			&& p.getAuctionStatus().equalsIgnoreCase("Y")
            	    		))
            	    .collect(Collectors.toList());
            */
            //Comparator<PawnBill> compareByName = (PawnBill o1, PawnBill o2) -> o1.getContact().getContactId().compareTo( o2.getContact().getContactId() );
            
            //Collections.sort(pawnBillList, compareByName);
            
            PawnBillBeanList DataBeanList = new PawnBillBeanList();
			ArrayList<PawnBillBean> dataList = DataBeanList.getPawnBillBeanList(pawnBillList);
			
			
            beanColDataSource = new JRBeanCollectionDataSource(dataList);
            
            System.out.println("List Size Auction:"+dataList.size());
            jp2=JasperFillManager.fillReport(jr, reportparam,beanColDataSource);
            JasperViewer.viewReport(jp2,false);
			
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
	public static void tagPrintReport(List<PawnBill> pawnBillList) {
		try {
			System.out.println("tagPrintReport");
			JasperDesign jd=null;
            JasperReport jr=null;
            JasperPrint jp2=null;
            JRBeanCollectionDataSource beanColDataSource = null;
            jd=JRXmlLoader.load("reports/PawnBillTagPrint.jrxml");
            jr=JasperCompileManager.compileReport(jd);           
            HashMap reportparam=new HashMap();
            
            //String criteria = "From :"+StringUtils.getStrDate(fromDate)+" To:"+StringUtils.getStrDate(toDate);
            reportparam.put("licenceName", Session.licence.getLicenceName());
            String licenceAddress = Session.licence.getAddress().replaceAll(",", "\n")+"\n"+StringUtils.capitalize(Session.licence.getState().getStateName());
            reportparam.put("licenceAddress", licenceAddress);
            reportparam.put("licenceContact", Session.licence.getLandLine());
            reportparam.put("printDate",StringUtils.getStrDate(new Date()));
           //reportparam.put("criteria",criteria);
            
            
            //pawnBillList.sort(Comparator.comparing(PawnBill::getContact));
            
            pawnBillList = pawnBillList.stream()
            	    .filter(p -> (p.getPawnBillStatusId().getPawnBillStatus().equalsIgnoreCase(ConstantUtils.PAWN_BILL_STATUS_PLEDGE)            	    			
            	    		))
            	    .collect(Collectors.toList());
            
            Comparator<PawnBill> compareByBillNumber = (PawnBill o1, PawnBill o2) -> o1.getBillNumber().compareTo( o2.getBillNumber() );
            
            Collections.sort(pawnBillList, compareByBillNumber);
            
            PawnBillBeanList DataBeanList = new PawnBillBeanList();
			ArrayList<PawnBillBean> dataList = DataBeanList.getPawnBillBeanList(pawnBillList);
			
			
            beanColDataSource = new JRBeanCollectionDataSource(dataList);
            
            System.out.println("List Size Auction:"+dataList.size());
            jp2=JasperFillManager.fillReport(jr, reportparam,beanColDataSource);
            JasperViewer.viewReport(jp2,false);
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
	public static void printPawnBill(PawnBill pawnBill) {
		try {
			JasperDesign jd=null;
            JasperReport jr=null;
            JasperPrint jp2=null;
            JRBeanCollectionDataSource beanColDataSource = null;
            jd=JRXmlLoader.load("reports/PawnBillPrint.jrxml");
            jr=JasperCompileManager.compileReport(jd);           
            HashMap reportparam=new HashMap();
            
            //String criteria = "From :"+StringUtils.getStrDate(fromDate)+" To:"+StringUtils.getStrDate(toDate);
            reportparam.put("licenceName", Session.licence.getLicenceName());
            String licenceAddress = Session.licence.getAddress().replaceAll(",", "\n")+"\n"+StringUtils.capitalize(Session.licence.getState().getStateName());
            reportparam.put("licenceAddress", licenceAddress);
            reportparam.put("licenceContact", Session.licence.getLandLine());
            //reportparam.put("licenceContact", Session.licence.getLandLine());
            reportparam.put("printDate",StringUtils.getStrDate(new Date()));
           //reportparam.put("criteria",criteria);
            
            reportparam.put("contactName",StringUtils.capitalize(pawnBill.getContact().getContactName()+" "+pawnBill.getContact().getSecondName()));
            reportparam.put("contactAddress",Common.contactAddress(pawnBill.getContact()));
            reportparam.put("contactMobile",pawnBill.getContact().getMobile());
            reportparam.put("billNumber",pawnBill.getBillNumber());
            reportparam.put("billDate",StringUtils.getStrDate(pawnBill.getBillDate()));
            
            reportparam.put("weight",Calculator.formatThreeDecimal(pawnBill.getTotalNetWeight()));
            reportparam.put("pledgeAmount",Calculator.formatThreeDecimal(pawnBill.getPledgeAmount()));
            reportparam.put("amountInWords",pawnBill.getAmountInWords());
            
            PawnBillBeanList DataBeanList = new PawnBillBeanList();
			ArrayList<PawnBillBean> dataList = DataBeanList.getPawnBillPrintBeanList(pawnBill.getPawnBillDetails());
			
			
            beanColDataSource = new JRBeanCollectionDataSource(dataList);
            
            System.out.println("List Size printPawnBill:"+dataList.size());
            jp2=JasperFillManager.fillReport(jr, reportparam,beanColDataSource);
            JasperViewer.viewReport(jp2,false);
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void pawnBillTagPrintBarcode(List<PawnBill> pawnBillList) {
		try {
			System.out.println("pawnBillTagPrintBarcode");
			JasperDesign jd=null;
            JasperReport jr=null;
            JasperPrint jp2=null;
            JRBeanCollectionDataSource beanColDataSource = null;
            jd=JRXmlLoader.load("reports/PawnBillBarCode.jrxml");
            jr=JasperCompileManager.compileReport(jd);           
            HashMap reportparam=new HashMap();
            
            //String criteria = "From :"+StringUtils.getStrDate(fromDate)+" To:"+StringUtils.getStrDate(toDate);
            reportparam.put("licenceName", Session.licence.getLicenceName());
            String licenceAddress = Session.licence.getAddress().replaceAll(",", "\n")+"\n"+StringUtils.capitalize(Session.licence.getState().getStateName());
            reportparam.put("licenceAddress", licenceAddress);
            reportparam.put("licenceContact", Session.licence.getLandLine());
            reportparam.put("printDate",StringUtils.getStrDate(new Date()));
           //reportparam.put("criteria",criteria);
            
            
            //pawnBillList.sort(Comparator.comparing(PawnBill::getContact));
            
            pawnBillList = pawnBillList.stream()
            	    .filter(p -> (p.getPawnBillStatusId().getPawnBillStatus().equalsIgnoreCase(ConstantUtils.PAWN_BILL_STATUS_PLEDGE)            	    			
            	    		))
            	    .collect(Collectors.toList());
            
            Comparator<PawnBill> compareByBillNumber = (PawnBill o1, PawnBill o2) -> o1.getBillNumber().compareTo( o2.getBillNumber() );
            
            Collections.sort(pawnBillList, compareByBillNumber);
            
            PawnBillBeanList DataBeanList = new PawnBillBeanList();
			ArrayList<PawnBillBean> dataList = DataBeanList.getPawnBillBeanList(pawnBillList);
			
			
            beanColDataSource = new JRBeanCollectionDataSource(dataList);
            
            System.out.println("List Size Auction:"+dataList.size());
            jp2=JasperFillManager.fillReport(jr, reportparam,beanColDataSource);
            JasperViewer.viewReport(jp2,false);
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
	public static void printContactBillAndPayment(PaymentDTO paymentDTO,Contact contactObj) {
		try {
			System.out.println("printContactBillAndPayment");
			JasperDesign jd=null;
            JasperReport jr=null;
            JasperPrint jp2=null;
            JRBeanCollectionDataSource beanColDataSource = null;
            jd=JRXmlLoader.load("reports/Contact-Bill-Payment-Details.jrxml");
            jr=JasperCompileManager.compileReport(jd);           
            HashMap reportparam=new HashMap();
            
            //String criteria = "From :"+StringUtils.getStrDate(fromDate)+" To:"+StringUtils.getStrDate(toDate);
            reportparam.put("licenceName", Session.licence.getLicenceName());
            String licenceAddress = Session.licence.getAddress().replaceAll(",", "\n")+"\n"+StringUtils.capitalize(Session.licence.getState().getStateName());
            reportparam.put("licenceAddress", licenceAddress);
            reportparam.put("licenceContact", Session.licence.getLandLine());
            reportparam.put("contactName", contactObj.getContactName());
            reportparam.put("printDate",StringUtils.getStrDate(new Date()));
            
			ArrayList<PaymentDTOList> dataList = new ArrayList<PaymentDTOList>();
			
			
			
			if(paymentDTO!=null && paymentDTO.getPaymentDTOList()!=null && paymentDTO.getPaymentDTOList().size()>0){
				
				for(PaymentDTOList obj:paymentDTO.getPaymentDTOList()) {
					PaymentDTOList data = new PaymentDTOList();
					data = obj;
					data.setRecordType(obj.getRecordType());
					if(obj.getBillNumber()==null) {
						data.setBillNumber("");
					}
					if(obj.getBillDate()!=null) {
						data.setBillDateStr(StringUtils.getStrDate(data.getBillDate()));
					}else {
						data.setBillDateStr("");
					}
					if(obj.getWeight()!=null) {
						data.setWeightStr(Calculator.formatThreeDecimal(data.getWeight()));
					}else {
						data.setWeightStr("");
					}
					if(obj.getMetalBalance()!=null) {
						data.setMetalBalanceStr(Calculator.formatThreeDecimal(data.getMetalBalance()));
					}else {
						data.setMetalBalanceStr("");
					}
					if(obj.getBillingAmount()!=null) {
						data.setBillingAmountStr(Calculator.formatThreeDecimal(data.getBillingAmount()));
					}else {
						data.setBillingAmountStr("");
					}
					if(obj.getPaymentDate()!=null) {
						data.setPaymentDateStr(StringUtils.getStrDate(data.getPaymentDate()));
					}else {
						data.setPaymentDateStr("");
					}
					if(obj.getPaymentMetal()!=null) {
						data.setPaymentMetalStr(Calculator.formatThreeDecimal(data.getPaymentMetal()));
					}else {
						data.setPaymentMetalStr("");
					}
					if(obj.getPaymentAmount()!=null) {
						data.setPaymentAmountStr(Calculator.formatThreeDecimal(data.getPaymentAmount()));
					}else {
						data.setPaymentAmountStr("");
					}
					dataList.add(data);
				}
			}
			
			
            beanColDataSource = new JRBeanCollectionDataSource(dataList);
            
            System.out.println("List Size Auction:"+dataList.size());
            jp2=JasperFillManager.fillReport(jr, reportparam,beanColDataSource);
            JasperViewer.viewReport(jp2,false);
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void printBillingReport(BillDTO billDTO) {
		try {
			
			System.out.println("printBillingReport");
			JasperDesign jd=null;
            JasperReport jr=null;
            JasperPrint jp2=null;
            JRBeanCollectionDataSource beanColDataSource = null;
            String fileName="";
            
            fileName="reports/BillingReport.jrxml";
            
            jd=JRXmlLoader.load(fileName);
            jr=JasperCompileManager.compileReport(jd);           
            HashMap reportparam=new HashMap();
            
            BillingReportBeanList DataBeanList = new BillingReportBeanList();
			ArrayList<BillingReportBean> dataList = DataBeanList.getBillingReportList(billDTO);
			
			beanColDataSource = new JRBeanCollectionDataSource(dataList);
			
			reportparam.put("licenceName", Session.licence.getLicenceName());
			reportparam.put("licenceAddress",Session.licence.getAddress());
			
			reportparam.put("reportName",billDTO.getReportName());
			reportparam.put("reportStartDate",billDTO.getReportStartDate());
			reportparam.put("reportEndDate",billDTO.getReportEndDate());
			
			
			reportparam.put("billCountStr",billDTO.getBillCountStr());
			reportparam.put("totalBillAmountStr",billDTO.getTotalBillAmountStr());
			reportparam.put("totalBillWeightStr",billDTO.getTotalBillWeightStr());
			
			jp2=JasperFillManager.fillReport(jr, reportparam,beanColDataSource);
            JasperViewer.viewReport(jp2,false);
			
		}catch(Exception ex) {
			JOptionPane.showMessageDialog(null, "error : "+ex);
			ex.printStackTrace();
		}
		
	}

}
