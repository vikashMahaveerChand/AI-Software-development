package com.inventory.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.inventory.dto.BalanceSheetDTO;
import com.inventory.dto.BalanceSheetDTOList;
import com.inventory.dto.StockDTO;
import com.inventory.dto.StockDTOList;
import com.inventory.utility.ConstantUtils;
import com.inventory.utility.HibernateUtil;
import com.inventory.utility.StringUtils;

public class BalanceSheetDAOImpl implements BalanceSheetDAO {
	
	private static BalanceSheetDAOImpl myInstance = null;
	SessionFactory factory = null;
	
	private BalanceSheetDAOImpl(){
		try{
		factory = HibernateUtil.getSessionFactory();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public static BalanceSheetDAOImpl getInstace(){
		if(myInstance == null){
			myInstance = new BalanceSheetDAOImpl();
		}
		return myInstance;
	}

	@Override
	public BalanceSheetDTO getBalanceSheetList(Date toDate, Integer licenceId, String contactTypeName,
			String billSource,String billType,Integer contactId) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		BalanceSheetDTO balanceSheetDTO =null;
		List<BalanceSheetDTOList> balanceSheetList = null;
		try {
			balanceSheetDTO = new BalanceSheetDTO();
			
			List<Object[]> list = null;
			session = factory.openSession();
			tx = session.beginTransaction();
			
			String balanceSheetQuery = getBalanceSheetQuery(contactId);
			//System.out.println("balanceSheetQuery :"+balanceSheetQuery);
			
			final SQLQuery query = session.createSQLQuery(balanceSheetQuery);
			
			query.setParameter("delFlag", ConstantUtils.CONSTANT_N);
			query.setParameter("licenceId", licenceId);
			query.setParameter("contactTypeName", contactTypeName);//contactTypeName
			query.setParameter("billSourceName", billSource);//billSourceName
			query.setParameter("toDate", StringUtils.getStrDate(toDate));//toDate
			query.setParameter("billType", billType);
			
			if(contactId!=null && contactId>0) {
				query.setParameter("contactId", contactId);
			}
			//query.addScalar("METAL_NAME ", Hibernate.STRING);
			
			list = query.list();
			
			if(list != null && list.size()>0){
				balanceSheetList = new ArrayList<BalanceSheetDTOList>();
				int index = -1;
				for (Object[] obj : list) {
					BalanceSheetDTOList balanceObj = new BalanceSheetDTOList();
					index=-1;
					
					if(obj[++index]!=null) {
						//balanceObj.setType((String)obj[index]); Disply Order
					}
					
					if(obj[++index]!=null) {
						balanceObj.setType((String)obj[index]);
					}
					
					if(obj[++index]!=null) {
						balanceObj.setContactId((Integer)obj[index]);
					}
					
					if(obj[++index]!=null) {
						balanceObj.setContactName((String) obj[index]);
					}
					
					if(obj[++index]!=null) {
						balanceObj.setAmountBalance((Double) obj[index]);
					}
					
					if(obj[++index]!=null) {
						balanceObj.setMetalBalance((Double) obj[index]);
					}
					
					if(obj[++index]!=null) {
						balanceObj.setMetalName((String) obj[index]);
					}
					
					if(obj[++index]!=null) {
						balanceObj.settDate(((Date) obj[index]));
					}
					
					if(obj[++index]!=null) {
						balanceObj.settNumber((String) obj[index]);
					}
					
					if(obj[++index]!=null) {
						balanceObj.settID(((String) obj[index]));
					}
					
					if(obj[++index]!=null) {
						balanceObj.setWeight(((Double) obj[index]));
					}

					balanceSheetList.add(balanceObj);
				}
				
				balanceSheetDTO.setBalanceSheetDTOList(balanceSheetList);
				if(balanceSheetList!=null && balanceSheetList.size()>0) {
					double totalAmountBalance = balanceSheetList.stream().filter(s->s.getAmountBalance()!=null).mapToDouble(BalanceSheetDTOList::getAmountBalance).sum();
					double totalMetalBalance = balanceSheetList.stream().filter(s->s.getMetalBalance()!=null).mapToDouble(BalanceSheetDTOList::getMetalBalance).sum();
					
					balanceSheetDTO.setTotalAmountBalance(totalAmountBalance);
					balanceSheetDTO.setTotalMetalBalance(totalMetalBalance);
					
					
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
		return balanceSheetDTO;
	}
	
	public String getBalanceSheetQuery(Integer contactId) {
		String balanceSheetQuery = "";
		try {
			StringBuffer query = new StringBuffer();
			
			
			/*query.append("     SELECT      ");
			query.append("     BALANCE_SHEET.CONTACT_ID,BALANCE_SHEET.CONTACT_NAME,     ");
			query.append("     (BALANCE_SHEET.TOTAL_AMOUNT-BALANCE_SHEET.AMOUNT_PAID) AMOUNT_BALANCE,     ");
			query.append("     (BALANCE_SHEET.METAL_BALANCE-BALANCE_SHEET.METAL_PAID) METAL_BALANCE,     ");
			query.append("     BALANCE_SHEET.BILL_METAL_NAME     ");
			query.append("     FROM(     ");
			query.append("     SELECT CONTACT.CONTACT_ID,CONTACT.CONTACT_NAME,     ");
			query.append("     IFNULL(BILL_DETAILS.TOTAL_AMOUNT,'0') TOTAL_AMOUNT,     ");
			query.append("     IFNULL(BILL_DETAILS.METAL_BALANCE,'0') METAL_BALANCE,     ");
			query.append("     BILL_DETAILS.BILL_METAL_NAME,     ");
			query.append("     IFNULL(PAYMENT_DETAILS.AMOUNT_PAID,'0') AMOUNT_PAID,     ");
			query.append("     IFNULL(PAYMENT_DETAILS.METAL_PAID,'0') METAL_PAID,     ");
			query.append("     PAYMENT_DETAILS.METAL_NAME_PAID     ");

			query.append("     FROM CONTACT CONTACT     ");
			query.append("     LEFT JOIN(     ");
			
			query.append("     SELECT BILL.CONTACT_ID,     ");
			query.append("     SUM(BILL.TOTAL_AMOUNT) TOTAL_AMOUNT,     ");
			query.append("     SUM(BILL.METAL_BALANCE) METAL_BALANCE,     ");
			query.append("     METAL.METAL_NAME BILL_METAL_NAME     ");
			query.append("     FROM BILL BILL     ");
			query.append("     LEFT JOIN BILL_SOURCE BS     ");
			query.append("     ON BILL.BILL_SOURCE_ID = BS.BILL_SOURCE_ID     ");
			query.append("     LEFT JOIN METAL METAL     ");
			query.append("     ON BILL.METAL_ID = METAL.METAL_ID     ");
			query.append("     LEFT JOIN CONTACT CON     ");
			query.append("     ON BILL.CONTACT_ID = CON.CONTACT_ID     ");
			query.append("     LEFT JOIN CONTACT_TYPE CT     ");
			query.append("     ON CON.CONTACT_TYPE_ID = CT.CONTACT_TYPE_ID     ");
			
			
			query.append("     WHERE BILL.DELETED_FLAG= :delFlag     ");
			query.append("     AND BILL.LICENCE_ID= :licenceId     ");
			query.append("	   AND CT.CONTACT_TYPE_NAME = :contactTypeName	");
			query.append("     AND BS.BILL_SOURCE_NAME IN ( :billSourceName)     ");
			query.append("     AND BILL.BILL_DATE <= STR_TO_DATE(:toDate,'%d/%m/%Y')     ");
			query.append("     GROUP BY BILL.CONTACT_ID,METAL.METAL_NAME     ");
			query.append("     ) BILL_DETAILS     ");
			
			query.append("     ON CONTACT.CONTACT_ID = BILL_DETAILS.CONTACT_ID     ");
			query.append("     LEFT JOIN(     ");
			
			query.append("     SELECT PD.CONTACT_ID,     ");
			query.append("     SUM(PD.AMOUNT_PAID) AMOUNT_PAID,     ");
			query.append("     SUM(PD.METAL_PAID) METAL_PAID,     ");
			query.append("     METAL.METAL_NAME METAL_NAME_PAID     ");
			
			query.append("     FROM PAYMENT_DETAILS PD     ");
			query.append("     LEFT JOIN BILL_SOURCE BS     ");
			query.append("     ON PD.BILL_SOURCE_ID = BS.BILL_SOURCE_ID     ");
			query.append("     LEFT JOIN METAL METAL     ");
			query.append("     ON PD.METAL_ID = METAL.METAL_ID     ");
			query.append("     LEFT JOIN CONTACT CON     ");
			query.append("     ON PD.CONTACT_ID = CON.CONTACT_ID     ");
			query.append("     LEFT JOIN CONTACT_TYPE CT     ");
			query.append("     ON CON.CONTACT_TYPE_ID = CT.CONTACT_TYPE_ID     ");
			
			query.append("     WHERE PD.DELETED_FLAG= :delFlag     ");
			query.append("     AND PD.LICENCE_ID= :licenceId     ");
			query.append("	   AND CT.CONTACT_TYPE_NAME = :contactTypeName	");
			query.append("     AND BS.BILL_SOURCE_NAME IN (:billSourceName)     ");
			query.append("     AND PD.PAYMENT_DATE_TIME <= STR_TO_DATE(:toDate,'%d/%m/%Y')     ");
			query.append("     GROUP BY PD.CONTACT_ID,METAL.METAL_NAME     ");
			query.append("     ) PAYMENT_DETAILS     ");
			
			query.append("     ON CONTACT.CONTACT_ID = PAYMENT_DETAILS.CONTACT_ID     ");
			query.append("     JOIN CONTACT_TYPE CONTACT_TYPE     ");
			query.append("     ON CONTACT.CONTACT_TYPE_ID = CONTACT_TYPE.CONTACT_TYPE_ID     ");

			query.append("     WHERE CONTACT.DELETED_FLAG=:delFlag     ");
			query.append("     AND CONTACT_TYPE.CONTACT_TYPE_NAME= :contactTypeName     ");
			query.append("     )BALANCE_SHEET     ");
			query.append("     GROUP BY BALANCE_SHEET.CONTACT_NAME     ");
			query.append("     ORDER BY BALANCE_SHEET.CONTACT_NAME     ");*/
			
			
			query.append("     SELECT BALANCE_SHEET.DISPLAY_ORDER AS DISPLAY_ORDER, BALANCE_SHEET.DATA_TYPE,BALANCE_SHEET.CONTACT_ID,CONTACT.CONTACT_NAME,     ");
			query.append("     BALANCE_SHEET.AMOUNT,BALANCE_SHEET.METAL,BALANCE_SHEET.METAL_NAME,     ");
			query.append("	   BALANCE_SHEET.BILL_DATE AS BILL_DATE, BALANCE_SHEET.BILL_NUMBER AS BILL_NUMBER, BALANCE_SHEET.BILL_ID AS BILL_ID,BALANCE_SHEET.WEIGHT AS WEIGHT");
			query.append("     FROM CONTACT JOIN     ");
			query.append("     (     ");
			
			query.append("     SELECT 2 as DISPLAY_ORDER, 'BILL' AS DATA_TYPE,BILL.CONTACT_ID,     ");
			query.append("     BILL.TOTAL_AMOUNT AMOUNT,     ");
			query.append("     BILL.METAL_BALANCE METAL,     ");
			query.append("     METAL.METAL_NAME METAL_NAME,     ");
			query.append("     BILL.BILL_DATE AS BILL_DATE,BILL.BILL_NUMBER AS BILL_NUMBER,BILL.BILL_ID AS BILL_ID,BILL.WEIGHT AS WEIGHT     ");
			query.append("     FROM BILL BILL     ");
			query.append("     LEFT JOIN BILL_SOURCE BS     ");
			query.append("     ON BILL.BILL_SOURCE_ID = BS.BILL_SOURCE_ID     ");
			query.append("     LEFT JOIN METAL METAL     ");
			query.append("     ON BILL.METAL_ID = METAL.METAL_ID     ");
			query.append("     LEFT JOIN CONTACT CON     ");
			query.append("     ON BILL.CONTACT_ID = CON.CONTACT_ID     ");
			query.append("     LEFT JOIN CONTACT_TYPE CT     ");
			query.append("     ON CON.CONTACT_TYPE_ID = CT.CONTACT_TYPE_ID     ");

			query.append("     WHERE BILL.DELETED_FLAG=:delFlag     ");
			query.append("     AND BILL.LICENCE_ID=:licenceId     ");
			query.append("     AND CT.CONTACT_TYPE_NAME = :contactTypeName     ");
			query.append("     AND BS.BILL_SOURCE_NAME IN (:billSourceName)     ");
			query.append("     AND BILL.BILL_DATE <= STR_TO_DATE(:toDate,'%d/%m/%Y')     ");

			query.append("     UNION ALL     ");

			query.append("     SELECT 3 as DISPLAY_ORDER, 'PAYMENT' AS DATA_TYPE,PD.CONTACT_ID,     ");
			query.append("     PD.AMOUNT_PAID AMOUNT,     ");
			query.append("     PD.METAL_PAID METAL,     ");
			query.append("     METAL.METAL_NAME METAL_NAME,     ");
			query.append("     PD.PAYMENT_DATE_TIME AS BILL_DATE,'' AS BILL_NUMBER,PD.PAYMENT_DETAILS_ID AS BILL_ID,NULL AS WEIGHT     ");
			query.append("     FROM PAYMENT_DETAILS PD     ");
			query.append("     LEFT JOIN BILL_SOURCE BS     ");
			query.append("     ON PD.BILL_SOURCE_ID = BS.BILL_SOURCE_ID     ");
			query.append("     LEFT JOIN METAL METAL     ");
			query.append("     ON PD.METAL_ID = METAL.METAL_ID     ");
			query.append("     LEFT JOIN CONTACT CON     ");
			query.append("     ON PD.CONTACT_ID = CON.CONTACT_ID     ");
			query.append("     LEFT JOIN CONTACT_TYPE CT     ");
			query.append("     ON CON.CONTACT_TYPE_ID = CT.CONTACT_TYPE_ID     ");

			query.append("     WHERE PD.DELETED_FLAG=:delFlag     ");
			query.append("     AND PD.LICENCE_ID=:licenceId     ");
			query.append("     AND CT.CONTACT_TYPE_NAME = :contactTypeName     ");
			query.append("     AND BS.BILL_SOURCE_NAME IN (:billSourceName)     ");
			query.append("     AND PD.PAYMENT_DATE_TIME <= STR_TO_DATE(:toDate,'%d/%m/%Y')     ");
			
			
			query.append("     UNION ALL     ");
			query.append("     SELECT 1 as DISPLAY_ORDER, 'OPENING_BALANCE' AS DATA_TYPE,OPENING.CONTACT_ID,     ");
			query.append("     IFNULL(OPENING.AMOUNT,0) AMOUNT,     ");
			query.append("     IFNULL(OPENING.GRAMS,0) METAL,     ");
			query.append("     IFNULL(METAL.METAL_NAME,'') METAL_NAME,     ");
			query.append("     NULL AS BILL_DATE,'Opening' AS BILL_NUMBER,'' AS BILL_ID,NULL AS WEIGHT     ");
			query.append("     FROM OPENING_BALANCE_DETAILS_CONTACT OPENING     ");
			query.append("     LEFT JOIN BILL_TYPE BT     ");
			query.append("     ON OPENING.BILL_TYPE_ID = BT.BILL_TYPE_ID     ");
			query.append("     LEFT JOIN METAL METAL     ");
			query.append("     ON OPENING.METAL_ID = METAL.METAL_ID     ");
			query.append("     LEFT JOIN CONTACT CON     ");
			query.append("     ON OPENING.CONTACT_ID = CON.CONTACT_ID     ");
			query.append("     LEFT JOIN CONTACT_TYPE CT     ");
			query.append("     ON CON.CONTACT_TYPE_ID = CT.CONTACT_TYPE_ID     ");
			
			query.append("     WHERE OPENING.DELETED_FLAG=:delFlag     ");
			query.append("     AND OPENING.LICENCE_ID=:licenceId     ");
			query.append("     AND CT.CONTACT_TYPE_NAME = :contactTypeName     ");
			query.append("     AND BT.BILL_TYPE_NAME IN (:billType)     ");

			
			query.append("     )BALANCE_SHEET	");
			query.append("     ON CONTACT.CONTACT_ID = BALANCE_SHEET.CONTACT_ID	");
			
			
			if(contactId!=null && contactId>0) {
				query.append(" WHERE CONTACT.CONTACT_ID= :contactId");
			}
			
			query.append("	   ORDER BY BALANCE_SHEET.DISPLAY_ORDER	");
			
			balanceSheetQuery = query.toString();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return balanceSheetQuery;
	}

}
