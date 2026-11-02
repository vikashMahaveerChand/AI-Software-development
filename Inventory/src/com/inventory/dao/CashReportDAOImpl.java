package com.inventory.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.inventory.dto.CashReportDTOList;
import com.inventory.utility.ConstantUtils;
import com.inventory.utility.HibernateUtil;
import com.inventory.utility.StringUtils;

public class CashReportDAOImpl implements CashReportDAO {
	
	private static CashReportDAOImpl myInstance = null;
	SessionFactory factory = null;
	
	private CashReportDAOImpl(){
		try{
		factory = HibernateUtil.getSessionFactory();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public static CashReportDAOImpl getInstace(){
		if(myInstance == null){
			myInstance = new CashReportDAOImpl();
		}
		return myInstance;
	}

	@Override
	public List<CashReportDTOList> getCashReportList(Date fromDate, Date toDate, Integer licenceId, String billType) {
		// TODO Auto-generated method stub
		List<CashReportDTOList> cashReportList = null;
		Session session = null;
		Transaction tx = null;
		try {
			session = factory.openSession();
			tx = session.beginTransaction();
			List<Object[]> list = null;
			/*
			String cashReceivedBillSourceGST ="GOLD_WHOLESALE_GST,SILVER_WHOLESALE_GST,GOLD_RETAIL_GST,SILVER_RETAIL_GST";
			String cashPaidBillSourceGST="GOLD_SUPPLIER_GST,SILVER_SUPPLIER_GST";
			
			String cashReceivedBillSourceNONGST ="GOLD_WHOLESALE_NONGST,SILVER_WHOLESALE_NONGST,GOLD_RETAIL_NONGST,SILVER_RETAIL_NONGST";
			String cashPaidBillSourceNONGST="GOLD_SUPPLIER_NONGST,SILVER_SUPPLIER_NONGST";
			*/
			
			String cashReceivedBillSourceGST ="WHOLESALE_GST,RETAIL_GST,";
			String cashPaidBillSourceGST="PURCHASE_GST";
			
			String cashReceivedBillSourceNONGST ="WHOLESALE_NONGST,RETAIL_NONGST";
			String cashPaidBillSourceNONGST="PURCHASE_NONGST";
			
			
			String CashReportQuery="";
			if(billType.equalsIgnoreCase(ConstantUtils.GST_ENTRY)) {
				CashReportQuery=getCashReportQuery(cashReceivedBillSourceGST,cashPaidBillSourceGST);
			}else if(billType.equalsIgnoreCase(ConstantUtils.NON_GST_ENTRY)) {
				CashReportQuery=getCashReportQuery(cashReceivedBillSourceNONGST,cashPaidBillSourceNONGST);
			}
			
			final Query query = session.createSQLQuery(CashReportQuery);
			
			query.setParameter("delFlag", ConstantUtils.CONSTANT_N);
			query.setParameter("licenceId", licenceId);
			//query.setParameter("billType", billType);
			
			
			if(billType.equalsIgnoreCase(ConstantUtils.GST_ENTRY)) {				
				int index = 0;
				
				for(String billSource : cashReceivedBillSourceGST.split(",")){
					query.setParameter("cashReceivedBillSource"+(++index), billSource);										
				}
				
				index = 0;				
				for(String billSource : cashPaidBillSourceGST.split(",")){
					query.setParameter("cashPaidBillSource"+(++index), billSource);										
				}
				
			}else if(billType.equalsIgnoreCase(ConstantUtils.NON_GST_ENTRY)) {
				int index = 0;
				
				for(String billSource : cashReceivedBillSourceNONGST.split(",")){
					query.setParameter("cashReceivedBillSource"+(++index), billSource);										
				}
				
				index = 0;				
				for(String billSource : cashPaidBillSourceNONGST.split(",")){
					query.setParameter("cashPaidBillSource"+(++index), billSource);										
				}
			}
			
			
			query.setParameter("fromDateStr", StringUtils.getStrDate(fromDate));
			query.setParameter("toDateStr", StringUtils.getStrDate(toDate));
			
			list = query.list();
			
			if(list != null && list.size()>0){
				cashReportList = new ArrayList<CashReportDTOList>();
				int counter =-1;
				for (Object[] obj : list) {
					CashReportDTOList data = new CashReportDTOList();
					counter =-1;
					
					if(obj[++counter]!=null) {
						data.setRecordType((String) obj[counter]);
					}
					
					if(obj[++counter]!=null) {
						data.setPaymentDate((Date) obj[counter]);
					}
					
					if(obj[++counter]!=null) {
						data.setDescription((String) obj[counter]);
					}
					
					if(obj[++counter]!=null) {
						data.setMetalName((String) obj[counter]);
					}
					
					if(obj[++counter]!=null) {
						data.setAmountReceived((Double) obj[counter]);
					}
					/*
					if(obj[++counter]!=null) {
						data.setMetalReceived((Double) obj[counter]);
					}*/
					if(obj[++counter]!=null) {
						data.setGoldMetalReceived((Double) obj[counter]);
					}
					if(obj[++counter]!=null) {
						data.setSilverMetalReceived((Double) obj[counter]);
					}
					
					if(obj[++counter]!=null) {
						data.setAmountPaid((Double) obj[counter]);
					}
					
					/*if(obj[++counter]!=null) {
						data.setMetalPaid((Double) obj[counter]);
					}*/
					
					if(obj[++counter]!=null) {
						data.setGoldMetalPaid((Double) obj[counter]);
					}
					if(obj[++counter]!=null) {
						data.setSilverMetalPaid((Double) obj[counter]);
					}
					
					if(obj[++counter]!=null) {
						data.setBillSource((String) obj[counter]);
					}
					
					if(obj[++counter]!=null) {
						data.setCashReportId((Integer) obj[counter]);
					}

					cashReportList.add(data);
					
				}
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}finally {
			try {
				session.close();
			}catch(Exception ex) {
				ex.printStackTrace();
			}
		}
		return cashReportList;
	}
	
	public String getCashReportQuery(String receivedBillSource,String paidBillSource) {
		String query = "";
		try {
			StringBuffer cashQuery = new StringBuffer();
			StringBuffer receivedwhereQuery = new StringBuffer();
			StringBuffer paidwhereQuery = new StringBuffer();
			
			
			int index=0;
			receivedwhereQuery.append("     AND BS.BILL_SOURCE_NAME IN(     ");
			for(String billSource : receivedBillSource.split(",")){
				if(index==0){
					receivedwhereQuery.append(":cashReceivedBillSource"+(++index));
				}else{
					receivedwhereQuery.append(",:cashReceivedBillSource"+(++index));
				}
			}
			receivedwhereQuery.append("     )     ");
			
			index=0;
			paidwhereQuery.append("     AND BS.BILL_SOURCE_NAME IN(     ");
			for(String billSource : paidBillSource.split(",")){
				if(index==0){
					paidwhereQuery.append(":cashPaidBillSource"+(++index));
				}else{
					paidwhereQuery.append(",:cashPaidBillSource"+(++index));
				}
			}
			paidwhereQuery.append("     )     ");
			
			
			cashQuery.append("     SELECT RECORD_TYPE,PAYMENT_DATE_TIME,CONTACT_NAME,METAL_NAME,     ");
			cashQuery.append("     CASE WHEN RECORD_TYPE ='RECEIVED' THEN AMOUNT_PAID END AMOUNT_RECEIVED,     ");
			//cashQuery.append("     CASE WHEN RECORD_TYPE ='RECEIVED' THEN METAL_PAID END METAL_RECEIVED,     ");
			cashQuery.append("     CASE WHEN RECORD_TYPE ='RECEIVED' AND METAL_NAME='GOLD' THEN METAL_PAID END GOLD_METAL_RECEIVED,     ");
			cashQuery.append("     CASE WHEN RECORD_TYPE ='RECEIVED' AND METAL_NAME='SILVER' THEN METAL_PAID END SILVER_METAL_RECEIVED,     ");
			
			
			cashQuery.append("     CASE WHEN RECORD_TYPE ='PAID' THEN AMOUNT_PAID END AMOUNT_PAID,     ");
			//cashQuery.append("     CASE WHEN RECORD_TYPE ='PAID' THEN METAL_PAID END METAL_PAID,     ");
			cashQuery.append("     CASE WHEN RECORD_TYPE ='PAID' AND METAL_NAME='GOLD' THEN METAL_PAID END GOLD_METAL_PAID,     ");
			cashQuery.append("     CASE WHEN RECORD_TYPE ='PAID' AND METAL_NAME='SILVER' THEN METAL_PAID END SILVER_METAL_PAID,     ");

			cashQuery.append("	   BILL_SOURCE_NAME,PAYMENT_DETAILS_ID	");
			cashQuery.append("     FROM(     ");
			
			cashQuery.append("     SELECT * FROM(     ");
			cashQuery.append("     SELECT 'RECEIVED' AS RECORD_TYPE,PD.PAYMENT_DATE_TIME,     ");
			cashQuery.append("     CONTACT.CONTACT_NAME,     ");
			cashQuery.append("     PD.AMOUNT_PAID,     ");
			cashQuery.append("     PD.METAL_PAID,     ");
			cashQuery.append("     METAL.METAL_NAME,BS.BILL_SOURCE_NAME,PD.PAYMENT_DETAILS_ID     ");
			
			cashQuery.append("     FROM PAYMENT_DETAILS PD     ");
			cashQuery.append("     LEFT JOIN CONTACT CONTACT     ");
			cashQuery.append("     ON PD.CONTACT_ID = CONTACT.CONTACT_ID     ");
			cashQuery.append("     LEFT JOIN METAL METAL     ");
			cashQuery.append("     ON PD.METAL_ID = METAL.METAL_ID     ");
			cashQuery.append("     LEFT JOIN BILL_SOURCE BS     ");
			cashQuery.append("     ON PD.BILL_SOURCE_ID = BS.BILL_SOURCE_ID     ");
			cashQuery.append("     WHERE PD.DELETED_FLAG= :delFlag     ");
			cashQuery.append("     AND PD.LICENCE_ID= :licenceId     ");
			//cashQuery.append("     AND BS.BILL_SOURCE_NAME IN(:cashReceivedBillSource)     ");
			cashQuery.append(receivedwhereQuery);
			cashQuery.append("     AND (PD.PAYMENT_DATE_TIME BETWEEN STR_TO_DATE(:fromDateStr,'%d/%m/%Y') AND STR_TO_DATE(:toDateStr,'%d/%m/%Y'))     ");
			cashQuery.append("     ) CASH_RECEIVED     ");
			
			cashQuery.append("     UNION ALL     ");
			
			cashQuery.append("     SELECT * FROM(     ");
			cashQuery.append("     SELECT 'PAID' AS RECORD_TYPE,PD.PAYMENT_DATE_TIME,     ");
			cashQuery.append("     CONTACT.CONTACT_NAME,     ");
			cashQuery.append("     PD.AMOUNT_PAID,     ");
			cashQuery.append("     PD.METAL_PAID,     ");
			cashQuery.append("     METAL.METAL_NAME,BS.BILL_SOURCE_NAME,PD.PAYMENT_DETAILS_ID     ");
			
			cashQuery.append("     FROM PAYMENT_DETAILS PD     ");
			cashQuery.append("     LEFT JOIN CONTACT CONTACT     ");
			cashQuery.append("     ON PD.CONTACT_ID = CONTACT.CONTACT_ID     ");
			cashQuery.append("     LEFT JOIN METAL METAL     ");
			cashQuery.append("     ON PD.METAL_ID = METAL.METAL_ID     ");
			cashQuery.append("     LEFT JOIN BILL_SOURCE BS     ");
			cashQuery.append("     ON PD.BILL_SOURCE_ID = BS.BILL_SOURCE_ID     ");
			cashQuery.append("     WHERE PD.DELETED_FLAG='N'     ");
			cashQuery.append("     AND PD.LICENCE_ID=1     ");
			//cashQuery.append("     AND BS.BILL_SOURCE_NAME IN(:cashPaidBillSource)     ");
			cashQuery.append(paidwhereQuery);
			cashQuery.append("     AND (PD.PAYMENT_DATE_TIME BETWEEN STR_TO_DATE(:fromDateStr,'%d/%m/%Y') AND STR_TO_DATE(:toDateStr,'%d/%m/%Y'))     ");
			cashQuery.append("     )CASH_RECEIVED     ");
			cashQuery.append("     )CASH_REPORT     ");
			
			query = cashQuery.toString();
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return query;
	}

}
