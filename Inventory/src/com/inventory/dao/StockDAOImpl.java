package com.inventory.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.inventory.dto.OpeningStockDetailsLicenceDTO;
import com.inventory.dto.StockDTO;
import com.inventory.dto.StockDTOList;
import com.inventory.utility.ConstantUtils;
import com.inventory.utility.DateUtils;
import com.inventory.utility.HibernateUtil;
import com.inventory.utility.StringUtils;

public class StockDAOImpl implements StockDAO {
	
	private static StockDAOImpl myInstance = null;
	SessionFactory factory = null;
	
	private StockDAOImpl(){
		try{
		factory = HibernateUtil.getSessionFactory();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public static StockDAOImpl getInstace(){
		if(myInstance == null){
			myInstance = new StockDAOImpl();
		}
		return myInstance;
	}

	@Override
	public List<StockDTO> getStockList(Date fromDate, Date toDate, Integer licenceId, String billSource) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		List<StockDTO> stockList = null;
		try {
			session = factory.openSession();
			tx = session.beginTransaction();
			String StockQuery="";
			String billType="";
			String billSourceNamePurchase="";
			List<Object[]> list = null;
			String billSourceStr1="",billSourceStr2="";
			String metal="";
			
			if(billSource.contains(ConstantUtils.GOLD)) {
				metal = ConstantUtils.GOLD;
			}else if(billSource.contains(ConstantUtils.SILVER)) {
				metal = ConstantUtils.SILVER;
			}
			
			if(billSource.equalsIgnoreCase(ConstantUtils.GOLD_RETAIL_GST)
					|| billSource.equalsIgnoreCase(ConstantUtils.SILVER_RETAIL_GST)
					|| billSource.equalsIgnoreCase(ConstantUtils.GOLD_WHOLESALE_GST)
					|| billSource.equalsIgnoreCase(ConstantUtils.SILVER_WHOLESALE_GST)
					|| billSource.equalsIgnoreCase(ConstantUtils.GOLD_SUPPLIER_GST)
					|| billSource.equalsIgnoreCase(ConstantUtils.SILVER_SUPPLIER_GST)
					) {
				billType = ConstantUtils.GST_ENTRY;
			}else {
				billType = ConstantUtils.NON_GST_ENTRY;
			}
			
			if(billSource.equalsIgnoreCase(ConstantUtils.GOLD_RETAIL_GST)
					|| billSource.equalsIgnoreCase(ConstantUtils.GOLD_WHOLESALE_GST)
					) {
				billSourceNamePurchase = ConstantUtils.GOLD_SUPPLIER_GST;
				billSourceStr1 = ConstantUtils.GOLD_RETAIL_GST;
				billSourceStr2 = ConstantUtils.GOLD_WHOLESALE_GST;
			}else if(billSource.equalsIgnoreCase(ConstantUtils.SILVER_RETAIL_GST)
					|| billSource.equalsIgnoreCase(ConstantUtils.SILVER_WHOLESALE_GST)
					) {
				billSourceNamePurchase = ConstantUtils.SILVER_SUPPLIER_GST;
				billSourceStr1 = ConstantUtils.SILVER_RETAIL_GST;
				billSourceStr2 = ConstantUtils.SILVER_WHOLESALE_GST;
			}else if(billSource.equalsIgnoreCase(ConstantUtils.GOLD_RETAIL_NONGST)
					|| billSource.equalsIgnoreCase(ConstantUtils.GOLD_WHOLESALE_NONGST)
					) {
				billSourceNamePurchase = ConstantUtils.GOLD_SUPPLIER_NONGST;
				billSourceStr1 = ConstantUtils.GOLD_RETAIL_NONGST;
				billSourceStr2 = ConstantUtils.GOLD_WHOLESALE_NONGST;
			}else if(billSource.equalsIgnoreCase(ConstantUtils.SILVER_RETAIL_NONGST)
					|| billSource.equalsIgnoreCase(ConstantUtils.SILVER_WHOLESALE_NONGST)
					) {
				billSourceNamePurchase = ConstantUtils.SILVER_SUPPLIER_NONGST;
				billSourceStr1 = ConstantUtils.SILVER_RETAIL_NONGST;
				billSourceStr2 = ConstantUtils.SILVER_WHOLESALE_NONGST;
			}
			
			boolean billTypeFlag = true;
			if(fromDate!=null && toDate!=null) {
				StockQuery = getStockQuery(billType, ConstantUtils.FETCHTYPE_USER_CRITERIA);
				//StockQuery = getStockQuery(billType, ConstantUtils.FETCHTYPE_OPENING_STOCK);
				billTypeFlag = false;
			}else {
				StockQuery = getStockQuery(billType, ConstantUtils.FETCHTYPE_OPENING_STOCK);
			}
			
			final Query query = session.createSQLQuery(StockQuery);
			
			query.setParameter("delFlag", ConstantUtils.CONSTANT_N);
			query.setParameter("licenceId", licenceId);
			
			//if(billTypeFlag) {
				//query.setParameter("billTypeName", billType);
			//}
			query.setParameter("billSourceNameSale1", billSourceStr1);
			query.setParameter("billSourceNameSale2", billSourceStr2);
			query.setParameter("billSourceNamePurchase", billSourceNamePurchase);
			
			if(billTypeFlag) {
				query.setParameter("billTypeName", billType);
				String sDate1="01/01/1991";				
				query.setParameter("fromDate", StringUtils.getStrDate(DateUtils.getDate(sDate1)));
				query.setParameter("toDate", StringUtils.getStrDate(toDate));
			}else {
				System.out.println(StringUtils.getStrDate(fromDate));
				System.out.println(StringUtils.getStrDate(toDate));
				query.setParameter("fromDate", StringUtils.getStrDate(fromDate));
				query.setParameter("toDate", StringUtils.getStrDate(toDate));
			}
			
			query.setParameter("metalName", metal);
			list = query.list();
			
			if(list != null && list.size()>0){
				stockList = new ArrayList<StockDTO>();
				
				for (Object[] obj : list) {
					StockDTO stock = new StockDTO();
					
					if(obj[0]!=null) {
						stock.setOrnamentId((Integer)obj[0]);
					}
					
					if(obj[1]!=null) {
						stock.setOrnamentName((String) obj[1]);
					}
					
					if(obj[2]!=null) {
						stock.setGstOpeningStock((Double)obj[2]);
					}
					
					if(obj[3]!=null) {
						stock.setNonGSTOpeningStock((Double)obj[3]);
					}
					
					if(obj[4]!=null) {
						stock.setGstSale((Double)obj[4]);
					}
					
					if(obj[5]!=null) {
						stock.setNonGstSale((Double)obj[5]);
					}
					
					if(obj[6]!=null) {
						stock.setGstPurchase((Double)obj[6]);
					}
					
					if(obj[7]!=null) {
						stock.setNonGstPurchase((Double)obj[7]);
					}
					
					if(obj[8]!=null) {
						stock.setGstStock((Double)obj[8]);
					}
					
					if(obj[9]!=null) {
						stock.setNonGstStock((Double)obj[9]);
					}
					
					stockList.add(stock);
				}
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}finally {
			try{
				session.close();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return stockList;
	}
	
	public String getStockQuery(String billType,String fetchType) { //Fetch Type -> Old or new - Old means include opening stock else dont include billType GST/NONGST
		StringBuffer query = new StringBuffer();
		String StockQuery ="";
		try {
			
			query.append("	SELECT ORNAMENT_ID ORN_ID,ORNAMENT_NAME ORN_NAME,	");
			query.append("	GST_OPENING_STOCK,NONGST_OPENING_STOCK,	");
			query.append("	GST_SALE,NONGST_SALE,	");
			query.append("	GST_PURCHASE,NONGST_PURCHASE,	");
			query.append("	(SUM(IFNULL(GST_OPENING_STOCK,0))+SUM(IFNULL(GST_PURCHASE,0))-SUM(IFNULL(GST_SALE,0))) GSTSTOCK,	");
			query.append("	(SUM(IFNULL(NONGST_OPENING_STOCK,0))+SUM(IFNULL(NONGST_PURCHASE,0))-SUM(IFNULL(NONGST_SALE,0))) NONGSTSTOCK	");
			query.append("	FROM(	");
			
			query.append("	SELECT ORN.ORNAMENT_ID,ORN.ORNAMENT_NAME,	");
			
			if(fetchType.equalsIgnoreCase(ConstantUtils.FETCHTYPE_OPENING_STOCK)) {
				if(billType.equalsIgnoreCase(ConstantUtils.GST_ENTRY)) {
					query.append("	GST_OPENING_STOCK.GRAMS GST_OPENING_STOCK,	");
					query.append("	NULL NONGST_OPENING_STOCK,	");
				}else if(billType.equalsIgnoreCase(ConstantUtils.NON_GST_ENTRY)) {
					query.append("	NULL GST_OPENING_STOCK,	");
					query.append("	NONGST_OPENING_STOCK.GRAMS NONGST_OPENING_STOCK,	");
				}
			}else {
				query.append("	NULL GST_OPENING_STOCK,	");
				query.append("	NULL NONGST_OPENING_STOCK,	");
			}
			
			if(billType.equalsIgnoreCase(ConstantUtils.GST_ENTRY)) {
				query.append("	GST_SALE.GRAMS GST_SALE,	");
				query.append("	NULL NONGST_SALE,	");
				
				query.append("	GST_PURCHASE.GRAMS GST_PURCHASE,	");
				query.append("	NULL NONGST_PURCHASE	");
			}else if(billType.equalsIgnoreCase(ConstantUtils.NON_GST_ENTRY)) {
				query.append("	NULL GST_SALE,	");
				query.append("	NONGST_SALE.GRAMS NONGST_SALE,	");
				
				query.append("	NULL GST_PURCHASE,	");
				query.append("	NONGST_PURCHASE.GRAMS NONGST_PURCHASE	");
			}
			
			
			query.append("	FROM ORNAMENT ORN	");
			query.append("	JOIN METAL METAL");
			query.append("	ON ORN.METAL_ID = METAL.METAL_ID");
			
			if(fetchType.equalsIgnoreCase(ConstantUtils.FETCHTYPE_OPENING_STOCK) && billType.equalsIgnoreCase(ConstantUtils.GST_ENTRY)) {
			//GST opening stock starts
				query.append("	LEFT JOIN(	");
				query.append("		");
				query.append("	SELECT ORN.ORNAMENT_ID,OPENING.GRAMS	");
				query.append("	FROM ORNAMENT ORN	");
				query.append("	JOIN OPENING_STOCK_DETAILS_LICENCE OPENING	");
				query.append("	ON ORN.ORNAMENT_ID = OPENING.ORNAMENT_ID	");				

				query.append("	JOIN METAL METAL");
				query.append("	ON ORN.METAL_ID = METAL.METAL_ID");
				
				query.append("	JOIN BILL_TYPE BT");
				query.append("	ON OPENING.BILL_TYPE_ID = BT.BILL_TYPE_ID");				
				query.append("	WHERE 0=0	");
				query.append("	AND ORN.DELETED_FLAG= :delFlag	");
				query.append("	AND ORN.LICENCE_ID = :licenceId	");
				query.append("	AND OPENING.DELETED_FLAG= :delFlag	");
				query.append("	AND OPENING.LICENCE_ID= :licenceId	");
				query.append("	AND BT.BILL_TYPE_NAME= :billTypeName	");
				
				query.append("	AND METAL.METAL_NAME= :metalName	");
				
				query.append("		");
				query.append("	)GST_OPENING_STOCK	");
				query.append("	ON ORN.ORNAMENT_ID = GST_OPENING_STOCK.ORNAMENT_ID	");
			//GST opening stock ends
			}
			//NONGST Opening stock starts
			if(fetchType.equalsIgnoreCase(ConstantUtils.FETCHTYPE_OPENING_STOCK) && billType.equalsIgnoreCase(ConstantUtils.NON_GST_ENTRY)) {
				query.append("		");
				query.append("	LEFT JOIN(	");
				query.append("		");
				query.append("	SELECT ORN.ORNAMENT_ID,OPENING.GRAMS	");
				query.append("	FROM ORNAMENT ORN	");
				query.append("	JOIN OPENING_STOCK_DETAILS_LICENCE OPENING	");
				query.append("	ON ORN.ORNAMENT_ID = OPENING.ORNAMENT_ID	");
				
				query.append("	JOIN METAL METAL");
				query.append("	ON ORN.METAL_ID = METAL.METAL_ID");
				
				query.append("	JOIN BILL_TYPE BT");
				query.append("	ON OPENING.BILL_TYPE_ID = BT.BILL_TYPE_ID");
				query.append("	WHERE 0=0	");
				query.append("	AND ORN.DELETED_FLAG= :delFlag	");
				query.append("	AND ORN.LICENCE_ID = :licenceId	");
				query.append("	AND OPENING.DELETED_FLAG= :delFlag	");
				query.append("	AND OPENING.LICENCE_ID= :licenceId	");
				query.append("	AND BT.BILL_TYPE_NAME= :billTypeName	");
				
				query.append("	AND METAL.METAL_NAME= :metalName	");
				
				query.append("		");
				query.append("	)NONGST_OPENING_STOCK	");
				query.append("	ON ORN.ORNAMENT_ID = NONGST_OPENING_STOCK.ORNAMENT_ID	");
			}
			//NONGST Opening stock ends
			
			query.append("		");
			//GST Sale starts
			if(billType.equalsIgnoreCase(ConstantUtils.GST_ENTRY)) {
				query.append("	LEFT JOIN(	");
				query.append("		");
				query.append("	SELECT BD.ORNAMENT_ID,SUM(BD.WEIGHT) GRAMS	");
				query.append("	FROM BILL_DETAILS BD	");
				query.append("	JOIN BILL BILL	");
				query.append("	ON BD.BILL_ID = BILL.BILL_ID	");
				query.append("	JOIN BILL_SOURCE BS");
				query.append("	ON BILL.BILL_SOURCE_ID = BS.BILL_SOURCE_ID");
				query.append("	WHERE 0=0	");
				query.append("	AND BILL.DELETED_FLAG= :delFlag	");
				query.append("	AND BILL.LICENCE_ID= :licenceId	");
				query.append("	AND BS.BILL_SOURCE_NAME IN (:billSourceNameSale1,:billSourceNameSale2)	");
				query.append("	AND (BILL.BILL_DATE BETWEEN STR_TO_DATE( :fromDate ,'%d/%m/%Y') AND STR_TO_DATE(:toDate,'%d/%m/%Y'))	");
				query.append("	GROUP BY BD.ORNAMENT_ID	");
				query.append("		");
				query.append("	)GST_SALE	");
				query.append("	ON ORN.ORNAMENT_ID = GST_SALE.ORNAMENT_ID	");
			}
			//GST Sale Ends
			query.append("		");
			
			//NONGST Sale starts
			if(billType.equalsIgnoreCase(ConstantUtils.NON_GST_ENTRY)) {
				query.append("	LEFT JOIN(	");
				query.append("		");
				query.append("	SELECT BD.ORNAMENT_ID,SUM(BD.WEIGHT) GRAMS	");
				query.append("	FROM BILL_DETAILS BD	");
				query.append("	JOIN BILL BILL	");
				query.append("	ON BD.BILL_ID = BILL.BILL_ID	");
				query.append("	JOIN BILL_SOURCE BS");
				query.append("	ON BILL.BILL_SOURCE_ID = BS.BILL_SOURCE_ID");
				query.append("	WHERE 0=0	");
				query.append("	AND BILL.DELETED_FLAG= :delFlag	");
				query.append("	AND BILL.LICENCE_ID= :licenceId	");
				query.append("	AND BS.BILL_SOURCE_NAME IN (:billSourceNameSale1,:billSourceNameSale2)	");
				query.append("	AND (BILL.BILL_DATE BETWEEN STR_TO_DATE( :fromDate ,'%d/%m/%Y') AND STR_TO_DATE(:toDate,'%d/%m/%Y'))	");
				query.append("	GROUP BY BD.ORNAMENT_ID	");
				query.append("		");
				query.append("	)NONGST_SALE	");
				query.append("	ON ORN.ORNAMENT_ID = NONGST_SALE.ORNAMENT_ID	");
			}
			//NONGST Sale Ends
			query.append("		");
			
			//GST Purchase Starts
			if(billType.equalsIgnoreCase(ConstantUtils.GST_ENTRY)) {
				query.append("	LEFT JOIN(	");
				query.append("		");
				query.append("	SELECT BD.ORNAMENT_ID,SUM(BD.WEIGHT) GRAMS	");
				query.append("	FROM BILL_DETAILS BD	");
				query.append("	JOIN BILL BILL	");
				query.append("	ON BD.BILL_ID = BILL.BILL_ID	");
				query.append("	JOIN BILL_SOURCE BS");
				query.append("	ON BILL.BILL_SOURCE_ID = BS.BILL_SOURCE_ID");
				query.append("	WHERE 0=0	");
				query.append("	AND BILL.DELETED_FLAG= :delFlag	");
				query.append("	AND BILL.LICENCE_ID= :licenceId	");
				query.append("	AND BS.BILL_SOURCE_NAME IN (:billSourceNamePurchase)	");
				query.append("	AND (BILL.BILL_DATE BETWEEN STR_TO_DATE( :fromDate ,'%d/%m/%Y') AND STR_TO_DATE(:toDate,'%d/%m/%Y'))	");
				query.append("	GROUP BY BD.ORNAMENT_ID	");
				query.append("		");
				query.append("	)GST_PURCHASE	");
				query.append("	ON ORN.ORNAMENT_ID = GST_PURCHASE.ORNAMENT_ID	");
			}
			//GST Purchase Ends
			query.append("		");
			
			//NONGST Purchase Starts
			if(billType.equalsIgnoreCase(ConstantUtils.NON_GST_ENTRY)) {
				query.append("	LEFT JOIN(	");
				query.append("		");
				query.append("	SELECT BD.ORNAMENT_ID,SUM(BD.WEIGHT) GRAMS	");
				query.append("	FROM BILL_DETAILS BD	");
				query.append("	JOIN BILL BILL	");
				query.append("	ON BD.BILL_ID = BILL.BILL_ID	");
				query.append("	JOIN BILL_SOURCE BS");
				query.append("	ON BILL.BILL_SOURCE_ID = BS.BILL_SOURCE_ID");
				query.append("	WHERE 0=0	");
				query.append("	AND BILL.DELETED_FLAG= :delFlag	");
				query.append("	AND BILL.LICENCE_ID= :licenceId	");
				query.append("	AND BS.BILL_SOURCE_NAME IN (:billSourceNamePurchase)	");
				query.append("	AND (BILL.BILL_DATE BETWEEN STR_TO_DATE( :fromDate ,'%d/%m/%Y') AND STR_TO_DATE(:toDate,'%d/%m/%Y'))	");
				query.append("	GROUP BY BD.ORNAMENT_ID	");
				query.append("		");
				query.append("	)NONGST_PURCHASE	");
				query.append("	ON ORN.ORNAMENT_ID = NONGST_PURCHASE.ORNAMENT_ID	");
			}
			//NONGST Purchase Ends
			
			query.append("	WHERE 0=0 AND METAL.METAL_NAME= :metalName	");
			
			query.append("	) ORNAMENT_STOCK	");
			query.append("	GROUP BY ORNAMENT_ID	");
			query.append("	ORDER BY ORN_NAME ASC	");
			query.append("		");
			StockQuery = query.toString();
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}finally {
			try {
				
			}catch(Exception ex) {
				ex.printStackTrace();
			}
		}
		return StockQuery;
	}

	@Override
	public StockDTO getStockListNew(Date fromDate, Date toDate, Integer licenceId, String billSource,
			String entryType) {
		// TODO Auto-generated method stub
		
		Session session = null;
		Transaction tx = null;
		StockDTO stockDTO = null;
		List<StockDTOList> stockDTOList = null;
		try {
			session = factory.openSession();
			tx = session.beginTransaction();
			
			StringBuffer query = new StringBuffer();
			stockDTOList = new ArrayList<StockDTOList>();
			stockDTO = new StockDTO();
			
			query.append("	SELECT ORNAMENT_STOCK.ORNAMENT_ID ORN_ID,ORNAMENT_STOCK.ORNAMENT_NAME ORN_NAME,	");
			
			//query.append("	IFNULL(ORNAMENT_STOCK.GST_OPENING_STOCK,0) GST_OPENING_STOCK,	");
			//query.append("	IFNULL(ORNAMENT_STOCK.NONGST_OPENING_STOCK,0) NONGST_OPENING_STOCK,	");
			
			
			query.append("	SUM(IFNULL(ORNAMENT_STOCK.GST_OPENING_STOCK,0)-IFNULL(ORNAMENT_STOCK.OLD_GST_SALE,0)+IFNULL(ORNAMENT_STOCK.OLD_GST_PURCHASE,0)+IFNULL(ORNAMENT_STOCK.OLD_GST_SALE_RETURN_GRAMS,0)-IFNULL(ORNAMENT_STOCK.OLD_GST_PURCHASE_RETURN_GRAMS,0)) GST_OPENING_STOCK,	");
			query.append("	SUM(IFNULL(ORNAMENT_STOCK.NONGST_OPENING_STOCK,0)-IFNULL(ORNAMENT_STOCK.OLD_NONGST_SALE,0)+IFNULL(ORNAMENT_STOCK.OLD_NONGST_PURCHASE,0)+IFNULL(ORNAMENT_STOCK.OLD_NONGST_SALE_RETURN_GRAMS,0)-IFNULL(ORNAMENT_STOCK.OLD_NONGST_PURCHASE_RETURN_GRAMS,0)) NONGST_OPENING_STOCK,	");
			
			//query.append("	(SUM(IFNULL(ORNAMENT_STOCK.GST_OPENING_STOCK,0))+SUM(IFNULL(ORNAMENT_STOCK.OLD_GST_PURCHASE,0))-SUM(IFNULL(ORNAMENT_STOCK.OLD_GST_SALE,0))+SUM(IFNULL(ORNAMENT_STOCK.OLD_GST_SALE_RETURN_GRAMS,0))-SUM(IFNULL(ORNAMENT_STOCK.OLD_GST_PURCHASE_RETURN_GRAMS,0))) GSTSTOCK,	");
			//query.append("	(SUM(IFNULL(ORNAMENT_STOCK.NONGST_OPENING_STOCK,0))+SUM(IFNULL(ORNAMENT_STOCK.OLD_NONGST_PURCHASE,0))-SUM(IFNULL(ORNAMENT_STOCK.OLD_NONGST_SALE,0))+SUM(IFNULL(ORNAMENT_STOCK.OLD_NONGST_SALE_RETURN_GRAMS,0))-SUM(IFNULL(ORNAMENT_STOCK.OLD_NONGST_PURCHASE_RETURN_GRAMS,0))) NONGSTSTOCK,	");
			
			query.append("	SUM(IFNULL(ORNAMENT_STOCK.GST_SALE,0)-IFNULL(ORNAMENT_STOCK.GST_SALE_RETURN_GRAMS,0)) AS GST_SALE,	");
			query.append("	SUM(IFNULL(ORNAMENT_STOCK.NONGST_SALE,0)-IFNULL(ORNAMENT_STOCK.NONGST_SALE_RETURN_GRAMS,0)) AS NONGST_SALE,");
			
			query.append("	SUM(IFNULL(ORNAMENT_STOCK.GST_PURCHASE,0)-IFNULL(ORNAMENT_STOCK.GST_PURCHASE_RETURN_GRAMS,0)) AS GST_PURCHASE,	");
			query.append("	SUM(IFNULL(ORNAMENT_STOCK.NONGST_PURCHASE,0)-IFNULL(ORNAMENT_STOCK.NONGST_PURCHASE_RETURN_GRAMS,0)) AS NONGST_PURCHASE,	");
			
			//query.append("	(SUM(IFNULL(ORNAMENT_STOCK.GST_OPENING_STOCK,0))+SUM(IFNULL(ORNAMENT_STOCK.GST_PURCHASE,0))-SUM(IFNULL(ORNAMENT_STOCK.GST_SALE,0))+SUM(IFNULL(ORNAMENT_STOCK.GST_SALE_RETURN_GRAMS,0))-SUM(IFNULL(ORNAMENT_STOCK.GST_PURCHASE_RETURN_GRAMS,0))) GSTSTOCK,	");
			//query.append("	(SUM(IFNULL(ORNAMENT_STOCK.NONGST_OPENING_STOCK,0))+SUM(IFNULL(ORNAMENT_STOCK.NONGST_PURCHASE,0))-SUM(IFNULL(ORNAMENT_STOCK.NONGST_SALE,0))+SUM(IFNULL(ORNAMENT_STOCK.NONGST_SALE_RETURN_GRAMS,0))-SUM(IFNULL(ORNAMENT_STOCK.NONGST_PURCHASE_RETURN_GRAMS,0))) NONGSTSTOCK,	");

			query.append("	SUM(IFNULL(ORNAMENT_STOCK.GST_OPENING_STOCK,0)-IFNULL(ORNAMENT_STOCK.OLD_GST_SALE,0)+IFNULL(ORNAMENT_STOCK.OLD_GST_PURCHASE,0)+IFNULL(ORNAMENT_STOCK.OLD_GST_SALE_RETURN_GRAMS,0)-IFNULL(ORNAMENT_STOCK.OLD_GST_PURCHASE_RETURN_GRAMS,0)+IFNULL(ORNAMENT_STOCK.GST_PURCHASE,0)-IFNULL(ORNAMENT_STOCK.GST_SALE,0)+IFNULL(ORNAMENT_STOCK.GST_SALE_RETURN_GRAMS,0)-IFNULL(ORNAMENT_STOCK.GST_PURCHASE_RETURN_GRAMS,0)) AS GSTSTOCK,	");
			query.append("	SUM(IFNULL(ORNAMENT_STOCK.NONGST_OPENING_STOCK,0)-IFNULL(ORNAMENT_STOCK.OLD_NONGST_SALE,0)+IFNULL(ORNAMENT_STOCK.OLD_NONGST_PURCHASE,0)+IFNULL(ORNAMENT_STOCK.OLD_NONGST_SALE_RETURN_GRAMS,0)-IFNULL(ORNAMENT_STOCK.OLD_NONGST_PURCHASE_RETURN_GRAMS,0)+IFNULL(ORNAMENT_STOCK.NONGST_PURCHASE,0)-IFNULL(ORNAMENT_STOCK.NONGST_SALE,0)+IFNULL(ORNAMENT_STOCK.NONGST_SALE_RETURN_GRAMS,0)-IFNULL(ORNAMENT_STOCK.NONGST_PURCHASE_RETURN_GRAMS,0)) AS NONGSTSTOCK,	");
			
			query.append("	METAL.METAL_NAME	");
			
			
			query.append("	FROM(	");
			query.append("	SELECT ORN.ORNAMENT_ID,ORN.ORNAMENT_NAME,	");
			
			
			query.append("	GST_OPENING_STOCK.GRAMS GST_OPENING_STOCK,	");
			query.append("	NONGST_OPENING_STOCK.GRAMS NONGST_OPENING_STOCK,	");
			
			query.append("	OLD_GST_SALE.GRAMS OLD_GST_SALE,	");
			query.append("	OLD_NONGST_SALE.GRAMS AS OLD_NONGST_SALE,	");
			
			query.append("	GST_SALE.GRAMS GST_SALE,	");
			query.append("	NONGST_SALE.GRAMS AS NONGST_SALE,	");
			
			query.append("	OLD_GST_PURCHASE.GRAMS OLD_GST_PURCHASE,	");
			query.append("	OLD_NONGST_PURCHASE.GRAMS OLD_NONGST_PURCHASE,	");
			
			query.append("	GST_PURCHASE.GRAMS GST_PURCHASE,	");
			query.append("	NONGST_PURCHASE.GRAMS NONGST_PURCHASE,	");
			
			query.append("	OLD_GST_SALE_RETURN.GRAMS AS OLD_GST_SALE_RETURN_GRAMS,	");
			query.append("	OLD_NONGST_SALE_RETURN.GRAMS AS OLD_NONGST_SALE_RETURN_GRAMS,	");
			
			query.append("	GST_SALE_RETURN.GRAMS AS GST_SALE_RETURN_GRAMS,	");
			query.append("	NONGST_SALE_RETURN.GRAMS AS NONGST_SALE_RETURN_GRAMS,	");
			
			query.append("	OLD_GST_PURCHASE_RETURN.GRAMS AS OLD_GST_PURCHASE_RETURN_GRAMS,	");
			query.append("	OLD_NONGST_PURCHASE_RETURN.GRAMS AS OLD_NONGST_PURCHASE_RETURN_GRAMS,	");
			
			query.append("	GST_PURCHASE_RETURN.GRAMS AS GST_PURCHASE_RETURN_GRAMS,	");
			query.append("	NONGST_PURCHASE_RETURN.GRAMS AS NONGST_PURCHASE_RETURN_GRAMS	");
			
			query.append("	FROM ORNAMENT ORN	");
			
			//GST_OPENING_STOCK Starts
			query.append("	LEFT JOIN(	");
			query.append("	SELECT ORN.ORNAMENT_ID,OPENING.GRAMS	");
			query.append("	FROM ORNAMENT ORN	");
			query.append("	JOIN OPENING_STOCK_DETAILS_LICENCE OPENING	");
			query.append("	ON ORN.ORNAMENT_ID = OPENING.ORNAMENT_ID	");
			query.append("	JOIN BILL_TYPE BT	");
			query.append("	ON OPENING.BILL_TYPE_ID = BT.BILL_TYPE_ID	");
			query.append("	WHERE 0=0	");
			query.append("	AND ORN.DELETED_FLAG= :deletedFlag	");
			query.append("	AND ORN.LICENCE_ID = :licenceId	");
			query.append("	AND OPENING.DELETED_FLAG= :deletedFlag	");
			query.append("	AND OPENING.LICENCE_ID= :licenceId	");
			query.append("	AND BT.BILL_TYPE_NAME='GST'	");
			query.append("	)GST_OPENING_STOCK	");
			query.append("	ON ORN.ORNAMENT_ID = GST_OPENING_STOCK.ORNAMENT_ID	");
			//GST_OPENING_STOCK Ends
			//NONGST_OPENING_STOCK Starts
			query.append("	LEFT JOIN(	");
			query.append("	SELECT ORN.ORNAMENT_ID,OPENING.GRAMS	");
			query.append("	FROM ORNAMENT ORN	");
			query.append("	JOIN OPENING_STOCK_DETAILS_LICENCE OPENING	");
			query.append("	ON ORN.ORNAMENT_ID = OPENING.ORNAMENT_ID	");
			query.append("	JOIN BILL_TYPE BT	");
			query.append("	ON OPENING.BILL_TYPE_ID = BT.BILL_TYPE_ID	");
			query.append("	WHERE 0=0	");
			query.append("	AND ORN.DELETED_FLAG= :deletedFlag	");
			query.append("	AND ORN.LICENCE_ID = :licenceId	");
			query.append("	AND OPENING.DELETED_FLAG= :deletedFlag	");
			query.append("	AND OPENING.LICENCE_ID= :licenceId	");
			query.append("	AND BT.BILL_TYPE_NAME='NON_GST'	");
			query.append("	)NONGST_OPENING_STOCK	");
			query.append("	ON ORN.ORNAMENT_ID = NONGST_OPENING_STOCK.ORNAMENT_ID	");
			//NONGST_OPENING_STOCK Ends
			
			//OLD_GST_SALE_STARTS
			query.append("	LEFT JOIN(	");
			query.append("	SELECT BD.ORNAMENT_ID,SUM(BD.WEIGHT) GRAMS	");
			query.append("	FROM BILL_DETAILS BD	");
			query.append("	JOIN BILL BILL	");
			query.append("	ON BD.BILL_ID = BILL.BILL_ID	");
			query.append("	JOIN BILL_SOURCE BS	");
			query.append("	ON BILL.BILL_SOURCE_ID = BS.BILL_SOURCE_ID	");
			query.append("	WHERE 0=0	");
			query.append("	AND BILL.DELETED_FLAG= :deletedFlag	");
			query.append("	AND BD.DELETED_FLAG= :deletedFlag	");
			query.append("	AND BILL.LICENCE_ID= :licenceId	");
			query.append("	AND BS.BILL_SOURCE_NAME IN ('WHOLESALE_GST','RETAIL_GST')	");
			query.append("	AND date(BILL.BILL_DATE) < date(:fromDate)	");
			query.append("	GROUP BY BD.ORNAMENT_ID	");
			query.append("	)OLD_GST_SALE	");
			query.append("	ON ORN.ORNAMENT_ID = OLD_GST_SALE.ORNAMENT_ID	");
			//OLD_GST_SALES_ENDS
			
			//GST_SALE Starts
			query.append("	LEFT JOIN(	");
			query.append("	SELECT BD.ORNAMENT_ID,SUM(BD.WEIGHT) GRAMS	");
			query.append("	FROM BILL_DETAILS BD	");
			query.append("	JOIN BILL BILL	");
			query.append("	ON BD.BILL_ID = BILL.BILL_ID	");
			query.append("	JOIN BILL_SOURCE BS	");
			query.append("	ON BILL.BILL_SOURCE_ID = BS.BILL_SOURCE_ID	");
			query.append("	WHERE 0=0	");
			query.append("	AND BILL.DELETED_FLAG= :deletedFlag	");
			query.append("	AND BD.DELETED_FLAG= :deletedFlag	");
			query.append("	AND BILL.LICENCE_ID= :licenceId	");
			query.append("	AND BS.BILL_SOURCE_NAME IN ('WHOLESALE_GST','RETAIL_GST')	");
			//query.append("	AND (BILL.BILL_DATE BETWEEN STR_TO_DATE(:fromDate,'%d/%m/%Y') AND STR_TO_DATE(:toDate,'%d/%m/%Y'))	");
			query.append("	AND (date(BILL.BILL_DATE) BETWEEN date(:fromDate) AND date(:toDate))	");
			query.append("	GROUP BY BD.ORNAMENT_ID	");
			query.append("	)GST_SALE	");
			query.append("	ON ORN.ORNAMENT_ID = GST_SALE.ORNAMENT_ID	");
			//GST_SALE Ends
			
			//OLD_GST_SALE_RETURN STATS
			query.append("	LEFT JOIN(	");
			query.append("	SELECT RBD.ORNAMENT_ID AS ORNAMENT_ID,SUM(RBD.WEIGHT) AS GRAMS	");
			query.append("	FROM BILL_RETURN_DETAILS RBD	");
			query.append("	JOIN BILL BILL	");
			query.append("	ON RBD.BILL_ID = BILL.BILL_ID	");
			query.append("	JOIN BILL_SOURCE BS	");
			query.append("	ON BILL.BILL_SOURCE_ID = BS.BILL_SOURCE_ID	");
			query.append("	WHERE 0=0	");
			query.append("	AND BILL.DELETED_FLAG= :deletedFlag	");
			query.append("	AND RBD.DELETED_FLAG= :deletedFlag	");
			query.append("	AND BILL.LICENCE_ID= :licenceId	");
			query.append("	AND BS.BILL_SOURCE_NAME IN ('WHOLESALE_GST','RETAIL_GST')	");
			query.append("	AND date(BILL.BILL_DATE) < date(:fromDate)	");
			query.append("	GROUP BY RBD.ORNAMENT_ID	");
			query.append("	)OLD_GST_SALE_RETURN	");
			query.append("	ON ORN.ORNAMENT_ID = OLD_GST_SALE_RETURN.ORNAMENT_ID	");
			//OLD_GST_SALE_RETURN ENDS

			//GST_SALE_RETURN Starts
			query.append("	LEFT JOIN(	");
			query.append("	SELECT RBD.ORNAMENT_ID AS ORNAMENT_ID,SUM(RBD.WEIGHT) AS GRAMS	");
			query.append("	FROM BILL_RETURN_DETAILS RBD	");
			query.append("	JOIN BILL BILL	");
			query.append("	ON RBD.BILL_ID = BILL.BILL_ID	");
			query.append("	JOIN BILL_SOURCE BS	");
			query.append("	ON BILL.BILL_SOURCE_ID = BS.BILL_SOURCE_ID	");
			query.append("	WHERE 0=0	");
			query.append("	AND BILL.DELETED_FLAG= :deletedFlag	");
			query.append("	AND RBD.DELETED_FLAG= :deletedFlag	");
			query.append("	AND BILL.LICENCE_ID= :licenceId	");
			query.append("	AND BS.BILL_SOURCE_NAME IN ('WHOLESALE_GST','RETAIL_GST')	");
			//query.append("	AND (BILL.BILL_DATE BETWEEN STR_TO_DATE(:fromDate,'%d/%m/%Y') AND STR_TO_DATE(:toDate,'%d/%m/%Y'))	");
			query.append("	AND (date(BILL.BILL_DATE) BETWEEN date(:fromDate) AND date(:toDate))	");
			query.append("	GROUP BY RBD.ORNAMENT_ID	");
			query.append("	)GST_SALE_RETURN	");
			query.append("	ON ORN.ORNAMENT_ID = GST_SALE_RETURN.ORNAMENT_ID	");
			//GST_SALE_RETURN Ends

			//OLD_NONGST_SALE_STARTS
			query.append("	LEFT JOIN(	");
			query.append("	SELECT BD.ORNAMENT_ID,SUM(BD.WEIGHT) GRAMS	");
			query.append("	FROM BILL_DETAILS BD	");
			query.append("	JOIN BILL BILL	");
			query.append("	ON BD.BILL_ID = BILL.BILL_ID	");
			query.append("	JOIN BILL_SOURCE BS	");
			query.append("	ON BILL.BILL_SOURCE_ID = BS.BILL_SOURCE_ID	");
			query.append("	WHERE 0=0	");
			query.append("	AND BILL.DELETED_FLAG= :deletedFlag	");
			query.append("	AND BD.DELETED_FLAG= :deletedFlag	");
			query.append("	AND BILL.LICENCE_ID= :licenceId	");
			query.append("	AND BS.BILL_SOURCE_NAME IN ('WHOLESALE_NONGST','RETAIL_NONGST')	");
			query.append("	AND date(BILL.BILL_DATE) < date(:fromDate)	");
			query.append("	GROUP BY BD.ORNAMENT_ID	");
			query.append("	)OLD_NONGST_SALE	");
			query.append("	ON ORN.ORNAMENT_ID = OLD_NONGST_SALE.ORNAMENT_ID	");
			//OLD_NONGST_SALE_ENDS
			
			//NONGST_SALE Starts
			query.append("	LEFT JOIN(	");
			query.append("	SELECT BD.ORNAMENT_ID,SUM(BD.WEIGHT) GRAMS	");
			query.append("	FROM BILL_DETAILS BD	");
			query.append("	JOIN BILL BILL	");
			query.append("	ON BD.BILL_ID = BILL.BILL_ID	");
			query.append("	JOIN BILL_SOURCE BS	");
			query.append("	ON BILL.BILL_SOURCE_ID = BS.BILL_SOURCE_ID	");
			query.append("	WHERE 0=0	");
			query.append("	AND BILL.DELETED_FLAG= :deletedFlag	");
			query.append("	AND BD.DELETED_FLAG= :deletedFlag	");
			query.append("	AND BILL.LICENCE_ID= :licenceId	");
			query.append("	AND BS.BILL_SOURCE_NAME IN ('WHOLESALE_NONGST','RETAIL_NONGST')	");
			query.append("	AND (date(BILL.BILL_DATE) BETWEEN date(:fromDate) AND date(:toDate))	");
			query.append("	GROUP BY BD.ORNAMENT_ID	");
			query.append("	)NONGST_SALE	");
			query.append("	ON ORN.ORNAMENT_ID = NONGST_SALE.ORNAMENT_ID	");
			//NONGST_SALE Ends
			
			//OLD_NONGST_SALE_RETURN STARTS
			query.append("	LEFT JOIN(	");
			query.append("	SELECT RBD.ORNAMENT_ID AS ORNAMENT_ID,SUM(RBD.WEIGHT) AS GRAMS	");
			query.append("	FROM BILL_RETURN_DETAILS RBD	");
			query.append("	JOIN BILL BILL	");
			query.append("	ON RBD.BILL_ID = BILL.BILL_ID	");
			query.append("	JOIN BILL_SOURCE BS	");
			query.append("	ON BILL.BILL_SOURCE_ID = BS.BILL_SOURCE_ID	");
			query.append("	WHERE 0=0	");
			query.append("	AND BILL.DELETED_FLAG= :deletedFlag	");
			query.append("	AND RBD.DELETED_FLAG= :deletedFlag	");
			query.append("	AND BILL.LICENCE_ID= :licenceId	");
			query.append("	AND BS.BILL_SOURCE_NAME IN ('WHOLESALE_NONGST','RETAIL_NONGST')	");
			query.append("	AND date(BILL.BILL_DATE) < date(:fromDate)	");
			query.append("	GROUP BY RBD.ORNAMENT_ID	");
			query.append("	)OLD_NONGST_SALE_RETURN	");
			query.append("	ON ORN.ORNAMENT_ID = OLD_NONGST_SALE_RETURN.ORNAMENT_ID	");
			//OLD_NONGST_SALE_RETURN ENDS
			
			//NONGST_SALE_RETURN STARTS
			query.append("	LEFT JOIN(	");
			query.append("	SELECT RBD.ORNAMENT_ID AS ORNAMENT_ID,SUM(RBD.WEIGHT) AS GRAMS	");
			query.append("	FROM BILL_RETURN_DETAILS RBD	");
			query.append("	JOIN BILL BILL	");
			query.append("	ON RBD.BILL_ID = BILL.BILL_ID	");
			query.append("	JOIN BILL_SOURCE BS	");
			query.append("	ON BILL.BILL_SOURCE_ID = BS.BILL_SOURCE_ID	");
			query.append("	WHERE 0=0	");
			query.append("	AND BILL.DELETED_FLAG= :deletedFlag	");
			query.append("	AND RBD.DELETED_FLAG= :deletedFlag	");
			query.append("	AND BILL.LICENCE_ID= :licenceId	");
			query.append("	AND BS.BILL_SOURCE_NAME IN ('WHOLESALE_NONGST','RETAIL_NONGST')	");
			query.append("	AND (date(BILL.BILL_DATE) BETWEEN date(:fromDate) AND date(:toDate))	");
			query.append("	GROUP BY RBD.ORNAMENT_ID	");
			query.append("	)NONGST_SALE_RETURN	");
			query.append("	ON ORN.ORNAMENT_ID = NONGST_SALE_RETURN.ORNAMENT_ID	");
			//NONGST_SALE_RETURN ENDS
			
			//OLD_GST_PURCHASE Starts
			query.append("	LEFT JOIN(	");
			query.append("	SELECT BD.ORNAMENT_ID,SUM(BD.WEIGHT) GRAMS	");
			query.append("	FROM BILL_DETAILS BD	");
			query.append("	JOIN BILL BILL	");
			query.append("	ON BD.BILL_ID = BILL.BILL_ID	");
			query.append("	JOIN BILL_SOURCE BS	");
			query.append("	ON BILL.BILL_SOURCE_ID = BS.BILL_SOURCE_ID	");
			query.append("	WHERE 0=0	");
			query.append("	AND BILL.DELETED_FLAG= :deletedFlag	");
			query.append("	AND BD.DELETED_FLAG= :deletedFlag	");
			query.append("	AND BILL.LICENCE_ID= :licenceId	");
			query.append("	AND BS.BILL_SOURCE_NAME IN ('PURCHASE_GST')	");
			query.append("	AND date(BILL.BILL_DATE) < date(:fromDate)	");
			query.append("	GROUP BY BD.ORNAMENT_ID	");
			query.append("	)OLD_GST_PURCHASE	");
			query.append("	ON ORN.ORNAMENT_ID = OLD_GST_PURCHASE.ORNAMENT_ID	");
			//OLD_GST_PURCHASE Ends
			
			//GST_PURCHASE Starts
			query.append("	LEFT JOIN(	");
			query.append("	SELECT BD.ORNAMENT_ID,SUM(BD.WEIGHT) GRAMS	");
			query.append("	FROM BILL_DETAILS BD	");
			query.append("	JOIN BILL BILL	");
			query.append("	ON BD.BILL_ID = BILL.BILL_ID	");
			query.append("	JOIN BILL_SOURCE BS	");
			query.append("	ON BILL.BILL_SOURCE_ID = BS.BILL_SOURCE_ID	");
			query.append("	WHERE 0=0	");
			query.append("	AND BILL.DELETED_FLAG= :deletedFlag	");
			query.append("	AND BD.DELETED_FLAG= :deletedFlag	");
			query.append("	AND BILL.LICENCE_ID= :licenceId	");
			query.append("	AND BS.BILL_SOURCE_NAME IN ('PURCHASE_GST')	");
			//query.append("	AND (BILL.BILL_DATE BETWEEN STR_TO_DATE(:fromDate,'%d/%m/%Y') AND STR_TO_DATE(:toDate,'%d/%m/%Y'))	");
			query.append("	AND (date(BILL.BILL_DATE) BETWEEN date(:fromDate) AND date(:toDate))	");
			query.append("	GROUP BY BD.ORNAMENT_ID	");
			query.append("	)GST_PURCHASE	");
			query.append("	ON ORN.ORNAMENT_ID = GST_PURCHASE.ORNAMENT_ID	");
			//GST_PURCHASE Ends
			
			//GST_PURCHASE_Return Starts
			query.append("	LEFT JOIN(	");
			query.append("	SELECT RBD.ORNAMENT_ID AS ORNAMENT_ID,SUM(RBD.WEIGHT) AS GRAMS	");
			query.append("	FROM BILL_RETURN_DETAILS RBD	");
			query.append("	JOIN BILL BILL	");
			query.append("	ON RBD.BILL_ID = BILL.BILL_ID	");
			query.append("	JOIN BILL_SOURCE BS	");
			query.append("	ON BILL.BILL_SOURCE_ID = BS.BILL_SOURCE_ID	");
			query.append("	WHERE 0=0	");
			query.append("	AND BILL.DELETED_FLAG= :deletedFlag	");
			query.append("	AND RBD.DELETED_FLAG= :deletedFlag	");
			query.append("	AND BILL.LICENCE_ID= :licenceId	");
			query.append("	AND BS.BILL_SOURCE_NAME IN ('PURCHASE_GST')	");
			query.append("	AND date(BILL.BILL_DATE) < date(:fromDate)	");
			query.append("	GROUP BY RBD.ORNAMENT_ID	");
			query.append("	)OLD_GST_PURCHASE_RETURN	");
			query.append("	ON ORN.ORNAMENT_ID = OLD_GST_PURCHASE_RETURN.ORNAMENT_ID	");
			//OLD_GST_PURCHASE_RETURN Ends

			//GST_PURCHASE_Return Starts
			query.append("	LEFT JOIN(	");
			query.append("	SELECT RBD.ORNAMENT_ID AS ORNAMENT_ID,SUM(RBD.WEIGHT) AS GRAMS	");
			query.append("	FROM BILL_RETURN_DETAILS RBD	");
			query.append("	JOIN BILL BILL	");
			query.append("	ON RBD.BILL_ID = BILL.BILL_ID	");
			query.append("	JOIN BILL_SOURCE BS	");
			query.append("	ON BILL.BILL_SOURCE_ID = BS.BILL_SOURCE_ID	");
			query.append("	WHERE 0=0	");
			query.append("	AND BILL.DELETED_FLAG= :deletedFlag	");
			query.append("	AND RBD.DELETED_FLAG= :deletedFlag	");
			query.append("	AND BILL.LICENCE_ID= :licenceId	");
			query.append("	AND BS.BILL_SOURCE_NAME IN ('PURCHASE_GST')	");
			//query.append("	AND (BILL.BILL_DATE BETWEEN STR_TO_DATE(:fromDate,'%d/%m/%Y') AND STR_TO_DATE(:toDate,'%d/%m/%Y'))	");
			query.append("	AND (date(BILL.BILL_DATE) BETWEEN date(:fromDate) AND date(:toDate))	");
			query.append("	GROUP BY RBD.ORNAMENT_ID	");
			query.append("	)GST_PURCHASE_RETURN	");
			query.append("	ON ORN.ORNAMENT_ID = GST_PURCHASE_RETURN.ORNAMENT_ID	");
			//GST_PURCHASE_RETURN Ends
			
			//OLD_NONGST_PURCHASE Starts
			query.append("	LEFT JOIN(	");
			query.append("	SELECT BD.ORNAMENT_ID,SUM(BD.WEIGHT) GRAMS	");
			query.append("	FROM BILL_DETAILS BD	");
			query.append("	JOIN BILL BILL	");
			query.append("	ON BD.BILL_ID = BILL.BILL_ID	");
			query.append("	JOIN BILL_SOURCE BS	");
			query.append("	ON BILL.BILL_SOURCE_ID = BS.BILL_SOURCE_ID	");
			query.append("	WHERE 0=0	");
			query.append("	AND BILL.DELETED_FLAG= :deletedFlag	");
			query.append("	AND BD.DELETED_FLAG= :deletedFlag	");
			query.append("	AND BILL.LICENCE_ID= :licenceId	");
			query.append("	AND BS.BILL_SOURCE_NAME IN ('PURCHASE_NONGST')	");
			//query.append("	AND (date(BILL.BILL_DATE) BETWEEN date(:fromDate) AND date(:toDate))	");
			query.append("	AND date(BILL.BILL_DATE) < date(:fromDate)	");
			query.append("	GROUP BY BD.ORNAMENT_ID	");
			query.append("	)OLD_NONGST_PURCHASE	");
			query.append("	ON ORN.ORNAMENT_ID = OLD_NONGST_PURCHASE.ORNAMENT_ID	");
			//OLD_NONGST_PURCHASE Ends
			
			//NONGST_PURCHASE Starts
			query.append("	LEFT JOIN(	");
			query.append("	SELECT BD.ORNAMENT_ID,SUM(BD.WEIGHT) GRAMS	");
			query.append("	FROM BILL_DETAILS BD	");
			query.append("	JOIN BILL BILL	");
			query.append("	ON BD.BILL_ID = BILL.BILL_ID	");
			query.append("	JOIN BILL_SOURCE BS	");
			query.append("	ON BILL.BILL_SOURCE_ID = BS.BILL_SOURCE_ID	");
			query.append("	WHERE 0=0	");
			query.append("	AND BILL.DELETED_FLAG= :deletedFlag	");
			query.append("	AND BD.DELETED_FLAG= :deletedFlag	");
			query.append("	AND BILL.LICENCE_ID= :licenceId	");
			query.append("	AND BS.BILL_SOURCE_NAME IN ('PURCHASE_NONGST')	");
			//query.append("	AND (BILL.BILL_DATE BETWEEN STR_TO_DATE(:fromDate,'%d/%m/%Y') AND STR_TO_DATE(:toDate,'%d/%m/%Y'))	");
			query.append("	AND (date(BILL.BILL_DATE) BETWEEN date(:fromDate) AND date(:toDate))	");
			query.append("	GROUP BY BD.ORNAMENT_ID	");
			query.append("	)NONGST_PURCHASE	");
			query.append("	ON ORN.ORNAMENT_ID = NONGST_PURCHASE.ORNAMENT_ID	");
			//NONGST_PURCHASE Ends
			
			//OLD_NONGST_PURCHASE_RETURN Starts
			query.append("	LEFT JOIN(	");
			query.append("	SELECT RBD.ORNAMENT_ID,SUM(RBD.WEIGHT) GRAMS	");
			query.append("	FROM BILL_RETURN_DETAILS RBD	");
			query.append("	JOIN BILL BILL	");
			query.append("	ON RBD.BILL_ID = BILL.BILL_ID	");
			query.append("	JOIN BILL_SOURCE BS	");
			query.append("	ON BILL.BILL_SOURCE_ID = BS.BILL_SOURCE_ID	");
			query.append("	WHERE 0=0	");
			query.append("	AND BILL.DELETED_FLAG= :deletedFlag	");
			query.append("	AND RBD.DELETED_FLAG= :deletedFlag	");
			query.append("	AND BILL.LICENCE_ID= :licenceId	");
			query.append("	AND BS.BILL_SOURCE_NAME IN ('PURCHASE_NONGST')	");
			query.append("	AND date(BILL.BILL_DATE) < date(:fromDate)	");
			query.append("	GROUP BY RBD.ORNAMENT_ID	");
			query.append("	)OLD_NONGST_PURCHASE_RETURN	");
			query.append("	ON ORN.ORNAMENT_ID = OLD_NONGST_PURCHASE_RETURN.ORNAMENT_ID	");
			//OLD_NONGST_PURCHASE_RETURN Ends
			
			//NONGST_PURCHASE_RETURN Starts
			query.append("	LEFT JOIN(	");
			query.append("	SELECT RBD.ORNAMENT_ID,SUM(RBD.WEIGHT) GRAMS	");
			query.append("	FROM BILL_RETURN_DETAILS RBD	");
			query.append("	JOIN BILL BILL	");
			query.append("	ON RBD.BILL_ID = BILL.BILL_ID	");
			query.append("	JOIN BILL_SOURCE BS	");
			query.append("	ON BILL.BILL_SOURCE_ID = BS.BILL_SOURCE_ID	");
			query.append("	WHERE 0=0	");
			query.append("	AND BILL.DELETED_FLAG= :deletedFlag	");
			query.append("	AND RBD.DELETED_FLAG= :deletedFlag	");
			query.append("	AND BILL.LICENCE_ID= :licenceId	");
			query.append("	AND BS.BILL_SOURCE_NAME IN ('PURCHASE_NONGST')	");
			//query.append("	AND (BILL.BILL_DATE BETWEEN STR_TO_DATE(:fromDate,'%d/%m/%Y') AND STR_TO_DATE(:toDate,'%d/%m/%Y'))	");
			query.append("	AND (date(BILL.BILL_DATE) BETWEEN date(:fromDate) AND date(:toDate))	");
			query.append("	GROUP BY RBD.ORNAMENT_ID	");
			query.append("	)NONGST_PURCHASE_RETURN	");
			query.append("	ON ORN.ORNAMENT_ID = NONGST_PURCHASE_RETURN.ORNAMENT_ID	");
			//NONGST_PURCHASE_RETURN Ends
			
			/*
			//OLD_GST_SALE Starts
			query.append("	LEFT JOIN(	");
			query.append("	SELECT BD.ORNAMENT_ID,SUM(BD.WEIGHT) GRAMS	");
			query.append("	FROM BILL_DETAILS BD	");
			query.append("	JOIN BILL BILL	");
			query.append("	ON BD.BILL_ID = BILL.BILL_ID	");
			query.append("	JOIN BILL_SOURCE BS	");
			query.append("	ON BILL.BILL_SOURCE_ID = BS.BILL_SOURCE_ID	");
			query.append("	WHERE 0=0	");
			query.append("	AND BILL.DELETED_FLAG= :deletedFlag	");
			query.append("	AND BILL.LICENCE_ID= :licenceId	");
			query.append("	AND BS.BILL_SOURCE_NAME IN ('WHOLESALE_GST','RETAIL_GST')	");
			query.append("	AND BILL.BILL_DATE < STR_TO_DATE(:fromDate,'%d/%m/%Y')	");
			query.append("	GROUP BY BD.ORNAMENT_ID	");
			query.append("	)OLD_GST_SALE	");
			query.append("	ON ORN.ORNAMENT_ID = GST_SALE.ORNAMENT_ID	");
			//OLD_GST_SALE Ends
			//OLD_NONGST_SALE Starts
			query.append("	LEFT JOIN(	");
			query.append("	SELECT BD.ORNAMENT_ID,SUM(BD.WEIGHT) GRAMS	");
			query.append("	FROM BILL_DETAILS BD	");
			query.append("	JOIN BILL BILL	");
			query.append("	ON BD.BILL_ID = BILL.BILL_ID	");
			query.append("	JOIN BILL_SOURCE BS	");
			query.append("	ON BILL.BILL_SOURCE_ID = BS.BILL_SOURCE_ID	");
			query.append("	WHERE 0=0	");
			query.append("	AND BILL.DELETED_FLAG= :deletedFlag	");
			query.append("	AND BILL.LICENCE_ID= :licenceId	");
			query.append("	AND BS.BILL_SOURCE_NAME IN ('WHOLESALE_NONGST','RETAIL_NONGST')	");
			query.append("	AND BILL.BILL_DATE < STR_TO_DATE(:fromDate,'%d/%m/%Y')	");
			query.append("	GROUP BY BD.ORNAMENT_ID	");
			query.append("	)OLD_NONGST_SALE	");
			query.append("	ON ORN.ORNAMENT_ID = NONGST_SALE.ORNAMENT_ID	");
			//OLD_NONGST_SALE Ends
			//OLD_GST_PURCHASE Starts
			query.append("	LEFT JOIN(	");
			query.append("	SELECT BD.ORNAMENT_ID,SUM(BD.WEIGHT) GRAMS	");
			query.append("	FROM BILL_DETAILS BD	");
			query.append("	JOIN BILL BILL	");
			query.append("	ON BD.BILL_ID = BILL.BILL_ID	");
			query.append("	JOIN BILL_SOURCE BS	");
			query.append("	ON BILL.BILL_SOURCE_ID = BS.BILL_SOURCE_ID	");
			query.append("	WHERE 0=0	");
			query.append("	AND BILL.DELETED_FLAG= :deletedFlag	");
			query.append("	AND BILL.LICENCE_ID= :licenceId	");
			query.append("	AND BS.BILL_SOURCE_NAME IN ('PURCHASE_GST')	");
			query.append("	AND BILL.BILL_DATE < STR_TO_DATE(:fromDate,'%d/%m/%Y')	");
			query.append("	GROUP BY BD.ORNAMENT_ID	");
			query.append("	)OLD_GST_PURCHASE	");
			query.append("	ON ORN.ORNAMENT_ID = GST_PURCHASE.ORNAMENT_ID	");
			//OLD_GST_PURCHASE Ends
			//OLD_NONGST_PURCHASE Starts
			query.append("	LEFT JOIN(	");
			query.append("	SELECT BD.ORNAMENT_ID,SUM(BD.WEIGHT) GRAMS	");
			query.append("	FROM BILL_DETAILS BD	");
			query.append("	JOIN BILL BILL	");
			query.append("	ON BD.BILL_ID = BILL.BILL_ID	");
			query.append("	JOIN BILL_SOURCE BS	");
			query.append("	ON BILL.BILL_SOURCE_ID = BS.BILL_SOURCE_ID	");
			query.append("	WHERE 0=0	");
			query.append("	AND BILL.DELETED_FLAG= :deletedFlag	");
			query.append("	AND BILL.LICENCE_ID= :licenceId	");
			query.append("	AND BS.BILL_SOURCE_NAME IN ('PURCHASE_NONGST')	");
			query.append("	AND BILL.BILL_DATE < STR_TO_DATE(:fromDate,'%d/%m/%Y')	");
			query.append("	GROUP BY BD.ORNAMENT_ID	");
			query.append("	)OLD_NONGST_PURCHASE	");
			query.append("	ON ORN.ORNAMENT_ID = NONGST_PURCHASE.ORNAMENT_ID	");
			//OLD_NONGST_PURCHASE Ends			
			*/
			query.append("	WHERE ORN.DELETED_FLAG='N'	");
			query.append("	) ORNAMENT_STOCK	");
			
			query.append("	JOIN ORNAMENT ORNAMENT");
			query.append("	ON ORNAMENT_STOCK.ORNAMENT_ID = ORNAMENT.ORNAMENT_ID	");
			
			query.append("	JOIN METAL METAL");
			query.append("	ON ORNAMENT.METAL_ID = METAL.METAL_ID	");
			
			query.append("	GROUP BY ORNAMENT_STOCK.ORNAMENT_ID	");
			query.append("	ORDER BY ORN_NAME ASC	");
			
			
			SQLQuery sqlquery = session.createSQLQuery(query.toString());
			System.out.println("Query Stock: "+query);
			sqlquery.setParameter("deletedFlag", ConstantUtils.CONSTANT_N);
			sqlquery.setParameter("licenceId", licenceId);
			sqlquery.setParameter("fromDate", fromDate);
			sqlquery.setParameter("toDate", toDate);
			/*query.setParameter("gstBillType", gstBillType);
			query.setParameter("nongstBillType", nongstBillType);*/
			List<Object[]> results = sqlquery.list();
			
			if(results!=null){
				for(Object[] obj:results){
					
					int i=-1;
					StockDTOList stock = new StockDTOList();
					
					if(obj[++i]!=null){
						stock.setOrnamentId((Integer)obj[i]);
					}
					
					if(obj[++i]!=null){
						stock.setOrnamentName((String)obj[i]);
					}
					
					if(obj[++i]!=null){
						stock.setGstOpeningStock((double)obj[i]);
					}
					
					if(obj[++i]!=null){
						stock.setNonGSTOpeningStock((double)obj[i]);
					}
					
					if(obj[++i]!=null){
						stock.setGstSale((double)obj[i]);
					}
					
					if(obj[++i]!=null){
						stock.setNonGstSale((double)obj[i]);
					}
					
					if(obj[++i]!=null){
						stock.setGstPurchase((double)obj[i]);
					}
					
					if(obj[++i]!=null){
						stock.setNonGstPurchase((double)obj[i]);
					}
					
					if(obj[++i]!=null){
						stock.setGstStock((double)obj[i]);
					}
					
					if(obj[++i]!=null){
						stock.setNonGstStock((double)obj[i]);
					}
					
					if(obj[++i]!=null){
						stock.setMetalName((String)obj[i]);
					}
					
					stockDTOList.add(stock);
					
				}
				
				
				
			}
			
			stockDTO.setStockDTOList(stockDTOList);
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}finally {
			try{
				session.close();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return stockDTO;
	}
}
