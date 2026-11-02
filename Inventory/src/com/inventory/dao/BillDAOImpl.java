
package com.inventory.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.inventory.dto.ContactTransactionDTO;
import com.inventory.dto.ContactTransactionDTOList;
import com.inventory.model.Bill;
import com.inventory.model.BillDetails;
import com.inventory.model.BillNumber;
import com.inventory.model.BillReturnDetails;
import com.inventory.model.Conversion;
import com.inventory.model.PaymentDetails;
import com.inventory.utility.ConstantUtils;
import com.inventory.utility.HibernateUtil;
import com.inventory.utility.StringUtils;

public class BillDAOImpl implements BillDAO{
	
	private static BillDAOImpl myInstance = null;
	SessionFactory factory = null;
	
	private BillDAOImpl(){
		try{
		factory = HibernateUtil.getSessionFactory();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public static BillDAOImpl getInstace(){
		if(myInstance == null){
			myInstance = new BillDAOImpl();
		}
		return myInstance;
	}

	@Override
	public Bill saveOrUpdateBill(Bill bill) {
		// TODO Auto-generated method stub
		Session session = null;
		Bill billObj = null;
		try{
			session = factory.openSession();
			System.out.println("DAO customer id ->"+bill.getContact().getContactId());
			Transaction t = session.beginTransaction();   
		        
		    //session.saveOrUpdate(bill);
		    bill = (Bill)session.merge(bill);
		    
		    t.commit();  
		    System.out.println("successfully saved");    
		    
		    if(bill!=null){
		    	billObj = bill;
		    	
		    }
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try{
				session.close();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return bill;
	}

	@Override
	//@Transactional
	public List<Bill> getBillList(Date fromDate,Date toDate,Integer licenceId,Integer billSourceId,Integer contactId,String entryType) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		List<Bill> billList = null;
		List<Bill> billListNew = null;
		try{
			session = factory.openSession();
			tx = session.beginTransaction();
			
			Criteria criteria = session.createCriteria(Bill.class);
			criteria.add(Restrictions.eq("deletedFlag", ConstantUtils.CONSTANT_N));
			
			criteria.createAlias("billSource", "billSource");
			criteria.add(Restrictions.eq("billSource.billSourceId", billSourceId));
		    
			criteria.createAlias("licence", "licence");
			criteria.add(Restrictions.eq("licence.licenceId", licenceId));
			
			if(contactId!=null && contactId>0){
				criteria.createAlias("contact", "contact");
				criteria.add(Restrictions.eq("contact.contactId", contactId));
			}
			
			if(fromDate!=null && toDate!=null){
				criteria.add(Restrictions.ge("billDate", fromDate));
				criteria.add(Restrictions.le("billDate", toDate));
			}else if(fromDate!=null){
				criteria.add(Restrictions.gt("billDate", fromDate));
			}else if(toDate!=null){
				criteria.add(Restrictions.lt("billDate", toDate));
			}
			criteria.addOrder(Order.asc("billDate"));
			criteria.addOrder(Order.asc("billNumber"));
			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
            billList = (List<Bill>) criteria.list();
            
            if(billList!=null && billList.size()>0) {
            	billListNew = new ArrayList<Bill>();
            	for(Bill bill:billList) {
    				List<BillDetails> billDetails = new ArrayList<BillDetails>();
    				List<BillReturnDetails> billReturnDetails = new ArrayList<BillReturnDetails>();
    				
    				if(bill.getBillDetailsList()!=null && bill.getBillDetailsList().size()>0) {
    					bill.getBillDetailsList().forEach(obj -> billDetails.add(obj));
    					bill.setBillDetails(billDetails);
    				}
    				
    				if(bill.getBillReturnDetailsList()!=null && bill.getBillReturnDetailsList().size()>0) {
    					bill.getBillReturnDetailsList().forEach(obj -> billReturnDetails.add(obj));
    					bill.setBillReturnDetails(billReturnDetails);
    				}
    				
    				billListNew.add(bill);
    			}
            }
            tx.commit();
		}catch(Exception ex){
			ex.printStackTrace();
			tx.rollback();
		}finally{
			try{
				session.close();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		
		return billListNew;
	}

	@Override
	public Bill getBill(Integer billId) {
		// TODO Auto-generated method stub
		Bill bill = null;
		
		
		Session session = null;
		try{
			session = factory.openSession();
			
			bill = (Bill)session.get(Bill.class, billId);
			
			if(bill!=null) {
				List<BillDetails> billDetails = new ArrayList<BillDetails>();
				List<BillReturnDetails> billReturnDetails = new ArrayList<BillReturnDetails>();
				
				if(bill.getBillDetailsList()!=null && bill.getBillDetailsList().size()>0) {
					bill.getBillDetailsList().forEach(obj -> billDetails.add(obj));
					bill.setBillDetails(billDetails);
				}
				
				if(bill.getBillReturnDetailsList()!=null && bill.getBillReturnDetailsList().size()>0) {
					bill.getBillReturnDetailsList().forEach(obj -> billReturnDetails.add(obj));
					bill.setBillReturnDetails(billReturnDetails);
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try{
				session.close();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return bill;
	}

	@Override
	public BillNumber getBillNumber(Integer licenceId, Integer billSourceId) {
		// TODO Auto-generated method stub
		BillNumber billNumber = null;
		
		
		Session session = null;
		try{
			session = factory.openSession();
			
			//billNumber = (BillNumber)session.get(BillNumber.class, billId);
			
			
			
			Criteria criteria = session.createCriteria(BillNumber.class);
			criteria.add(Restrictions.eq("deletedFlag", ConstantUtils.CONSTANT_N));
			
			criteria.createAlias("billSource", "billSource");
			criteria.add(Restrictions.eq("billSource.billSourceId", billSourceId));
		    
			criteria.createAlias("licence", "licence");
			criteria.add(Restrictions.eq("licence.licenceId", licenceId));
		    
			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
            //billList = (List<Bill>) criteria.list();
            billNumber = (BillNumber) criteria.uniqueResult();
            
			
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try{
				session.close();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return billNumber;
	}

	@Override
	public BillNumber saveOrUpdateBillNumber(BillNumber billNumber) {
		// TODO Auto-generated method stub
		Session session = null;
		BillNumber obj = null;
		try{
			session = factory.openSession();
			
			Transaction t = session.beginTransaction();   
		        
		    session.saveOrUpdate(billNumber);
		    obj = (BillNumber)session.merge(billNumber);
		    
		    t.commit();  
		    System.out.println("successfully saved");    
		    
		    
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try{
				session.close();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return obj;
	}
	
	
	@Override
	public PaymentDetails saveOrUpdatePaymentDetails(PaymentDetails payment) {
		// TODO Auto-generated method stub
		Session session = null;
		PaymentDetails paymentObj = null;
		try{
			session = factory.openSession();
			Transaction t = session.beginTransaction();   
		        
		    //session.saveOrUpdate(bill);
			payment = (PaymentDetails)session.merge(payment);
		    
		    t.commit();  
		    System.out.println("successfully saved");    
		    
		    if(payment!=null){
		    	paymentObj = payment;
		    	
		    }
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try{
				session.close();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return paymentObj;
	}

	@Override
	public List<PaymentDetails> getPaymentDetailsList(Date fromDate,Date toDate,Integer licenceId,Integer billSourceId,Integer contactId) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		List<PaymentDetails> paymentDetailsList = null;
		try{
			session = factory.openSession();
			tx = session.beginTransaction();
			
			Criteria criteria = session.createCriteria(PaymentDetails.class);
			criteria.add(Restrictions.eq("deletedFlag", ConstantUtils.CONSTANT_N));
			
			criteria.createAlias("billSource", "billSource");
			criteria.add(Restrictions.eq("billSource.billSourceId", billSourceId));
		    
			criteria.createAlias("licence", "licence");
			criteria.add(Restrictions.eq("licence.licenceId", licenceId));
			
			if(contactId!=null && contactId>0){
				criteria.createAlias("contact", "contact");
				criteria.add(Restrictions.eq("contact.contactId", contactId));
			}

			if(fromDate!=null && toDate!=null){
				criteria.add(Restrictions.ge("paymentDate", fromDate));
				criteria.add(Restrictions.le("paymentDate", toDate));
			}else if(fromDate!=null){
				criteria.add(Restrictions.gt("paymentDate", fromDate));
			}else if(toDate!=null){
				criteria.add(Restrictions.lt("paymentDate", toDate));
			}
			
			criteria.addOrder(Order.asc("paymentDate"));
			criteria.addOrder(Order.asc("paymentDetailsId"));
			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			paymentDetailsList = (List<PaymentDetails>) criteria.list();
            tx.commit();
		}catch(Exception ex){
			ex.printStackTrace();
			tx.rollback();
		}finally{
			try{
				session.close();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return paymentDetailsList;
	}

	@Override
	public PaymentDetails getPaymentDetails(Integer paymentDetailsId) {
		// TODO Auto-generated method stub
		PaymentDetails paymentDetails = null;
		
		
		Session session = null;
		try{
			session = factory.openSession();
			
			paymentDetails = (PaymentDetails)session.get(PaymentDetails.class, paymentDetailsId);
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try{
				session.close();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return paymentDetails;
	}

	@Override
	public List<PaymentDetails> getBankPaymentDetails(Date fromDate, Date toDate, Integer bankId) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		List<PaymentDetails> paymentDetailsList = null;
		try{
			session = factory.openSession();
			tx = session.beginTransaction();
			
			Criteria criteria = session.createCriteria(PaymentDetails.class);
			criteria.add(Restrictions.eq("deletedFlag", ConstantUtils.CONSTANT_N));
			
			criteria.createAlias("bank", "bank");
			criteria.add(Restrictions.eq("bank.bankId", bankId));
			
			if(fromDate!=null && toDate!=null){
				criteria.add(Restrictions.ge("paymentDate", fromDate));
				criteria.add(Restrictions.le("paymentDate", toDate));
			}else if(fromDate!=null){
				criteria.add(Restrictions.gt("paymentDate", fromDate));
			}else if(toDate!=null){
				criteria.add(Restrictions.lt("paymentDate", toDate));
			}
			
			
			criteria.addOrder(Order.asc("paymentDate"));
			criteria.addOrder(Order.asc("paymentDetailsId"));
			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			paymentDetailsList = (List<PaymentDetails>) criteria.list();
            tx.commit();
		}catch(Exception ex){
			ex.printStackTrace();
			tx.rollback();
		}finally{
			try{
				session.close();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return paymentDetailsList;
	}

	@Override
	public Conversion saveOrUpdateConversion(Conversion conversion) {
		// TODO Auto-generated method stub
		Session session = null;
		Conversion conversionObj = null;
		try{
			session = factory.openSession();
			Transaction t = session.beginTransaction();   
		        
		    //session.saveOrUpdate(bill);
		    conversion = (Conversion)session.merge(conversion);
		    
		    t.commit();  
		    System.out.println("successfully saved");    
		    
		    if(conversion!=null){
		    	conversionObj = conversion;
		    	
		    }
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try{
				session.close();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return conversionObj;
	}

	@Override
	public Conversion getConversion(Integer conversionId) {
		// TODO Auto-generated method stub
		Conversion conversion = null;
		
		
		Session session = null;
		try{
			session = factory.openSession();
			
			conversion = (Conversion)session.get(Conversion.class, conversionId);
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try{
				session.close();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return conversion;
	}

	@Override
	public List<Conversion> getConversionList(Date fromDate, Date toDate, Integer licenceId, Integer billSourceId,
			Integer contactId) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		List<Conversion> conversionList = null;
		try{
			session = factory.openSession();
			tx = session.beginTransaction();
			
			Criteria criteria = session.createCriteria(Conversion.class);
			criteria.add(Restrictions.eq("deletedFlag", ConstantUtils.CONSTANT_N));
			
			criteria.createAlias("billSource", "billSource");
			criteria.add(Restrictions.eq("billSource.billSourceId", billSourceId));
		    
			criteria.createAlias("licence", "licence");
			criteria.add(Restrictions.eq("licence.licenceId", licenceId));
			
			if(contactId!=null && contactId>0){
				criteria.createAlias("contact", "contact");
				criteria.add(Restrictions.eq("contact.contactId", contactId));
			}
			
			if(fromDate!=null && toDate!=null){
				criteria.add(Restrictions.ge("conversionDate", fromDate));
				criteria.add(Restrictions.le("conversionDate", toDate));
			}else if(fromDate!=null){
				criteria.add(Restrictions.gt("conversionDate", fromDate));
			}else if(toDate!=null){
				criteria.add(Restrictions.lt("conversionDate", toDate));
			}
			
			
			
			
			criteria.addOrder(Order.asc("conversionDate"));
			criteria.addOrder(Order.asc("conversionId"));
			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			conversionList = (List<Conversion>) criteria.list();
            tx.commit();
		}catch(Exception ex){
			ex.printStackTrace();
			tx.rollback();
		}finally{
			try{
				session.close();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return conversionList;
	}

	@Override
	public ContactTransactionDTO getContactTransactionDetails(Date fromDate, Date toDate, String billSource,
			Integer contactId, Integer licenceId, String entryType) {
		// TODO Auto-generated method stub
		
		Session session = null;
		Transaction tx = null;
		ContactTransactionDTO contactTransDTO =null;
		List<ContactTransactionDTOList> contactTransDTOList = null;
		try {
			contactTransDTO = new ContactTransactionDTO();
			
			
			session = factory.openSession();
			tx = session.beginTransaction();
			List<Object[]> list = null;
			
			String transacationQuery = getContactTransactionQuery();
			//String transacationQuery = getBalanceSheetQuery(contactId);
			System.out.println("transacationQuery: "+transacationQuery);
			
			final SQLQuery query = session.createSQLQuery(transacationQuery);
			
			query.setParameter("delFlag", ConstantUtils.CONSTANT_N);
			query.setParameter("licenceId", licenceId);
			query.setParameter("billSourceName", billSource);//billSourceName
			query.setParameter("fromDate", StringUtils.getStrDate(fromDate));//toDate
			query.setParameter("toDate", StringUtils.getStrDate(toDate));//toDate
			query.setParameter("billType", entryType);
			query.setParameter("contactId", contactId);
			
			list = query.list();
			
			if(list != null && list.size()>0){
				contactTransDTOList = new ArrayList<ContactTransactionDTOList>();
				int index = -1;
				for (Object[] obj : list) {
					ContactTransactionDTOList data = new ContactTransactionDTOList();
					String type = "";
					index = -1;
					if(obj[++index]!=null) {
						//Disply Order
					}
					
					if(obj[++index]!=null) {
						data.settType((String)obj[index]);
						type = (String)obj[index];
					}
					
					if(obj[++index]!=null) {
						data.setContactId((Integer)obj[index]);
					}
					
					if(obj[++index]!=null) {
						data.setContactName((String)obj[index]);
					}
					
					if(obj[++index]!=null) {
						if(type.equalsIgnoreCase(ConstantUtils.BILL)) {
							data.setBillAmount((double)obj[index]);
						}else if(type.equalsIgnoreCase(ConstantUtils.PAYMENT)) {
							data.setPaymentAmountPaid((double)obj[index]);
						}else if(type.equalsIgnoreCase(ConstantUtils.OLD_BILL) || type.equalsIgnoreCase(ConstantUtils.OPENING)) {
							data.setOldAmountDue((double)obj[index]);
						}else if(type.equalsIgnoreCase(ConstantUtils.OLD_PAYMENT)) {
							data.setOldAmountPaid((double)obj[index]);
						}
					}
					
					
					if(obj[++index]!=null) {
						if(type.equalsIgnoreCase(ConstantUtils.BILL)) {
							data.setBillMetalBalance((double)obj[index]);
						}else if(type.equalsIgnoreCase(ConstantUtils.PAYMENT)) {
							data.setPaymentMetalPaid((double)obj[index]);
						}else if(type.equalsIgnoreCase(ConstantUtils.OLD_BILL) || type.equalsIgnoreCase(ConstantUtils.OPENING)) {
							data.setOldMetalDue((double)obj[index]);
						}else if(type.equalsIgnoreCase(ConstantUtils.OLD_PAYMENT)) {
							data.setOldMetalPaid((double)obj[index]);
						}
					}
					
					if(obj[++index]!=null) {
						data.setMetalName((String)obj[index]);
						/*if(type.equalsIgnoreCase(ConstantUtils.BILL)) {
							data.setMetalName((String)obj[index]);
						}else if(type.equalsIgnoreCase(ConstantUtils.PAYMENT)) {
							data.setMetalName((String)obj[index]);
						}else if(type.equalsIgnoreCase(ConstantUtils.OLD_BILL) || type.equalsIgnoreCase(ConstantUtils.OPENING)) {
							data.setMetalName((String)obj[index]);
						}else if(type.equalsIgnoreCase(ConstantUtils.OLD_PAYMENT)) {
							data.setMetalName((String)obj[index]);
						}*/
					}
					
					if(obj[++index]!=null) {
						if(type.equalsIgnoreCase(ConstantUtils.BILL)) {
							data.setBillDate((Date)obj[index]);
						}else if(type.equalsIgnoreCase(ConstantUtils.PAYMENT)) {
							data.setPaymentDate((Date)obj[index]);
						}
					}
					
					if(obj[++index]!=null) {
						if(type.equalsIgnoreCase(ConstantUtils.BILL)) {
							data.setBillNumber((String)obj[index]);
						}else if(type.equalsIgnoreCase(ConstantUtils.PAYMENT)) {
							//Data will be NULL of Payment
						}
					}
					
					if(obj[++index]!=null) {
						if(type.equalsIgnoreCase(ConstantUtils.BILL)) {
							data.setBillId((int)obj[index]);
						}else if(type.equalsIgnoreCase(ConstantUtils.PAYMENT)) {
							data.setPaymentId((int)obj[index]);
						}
					}
					
					
					if(obj[++index]!=null) {
						if(type.equalsIgnoreCase(ConstantUtils.BILL)) {
							data.setBillWeight((double)obj[index]);
						}else if(type.equalsIgnoreCase(ConstantUtils.PAYMENT)) {
							//Data will be NULL of Payment
						}
					}
				
					
					contactTransDTOList.add(data);
				}
			}
			
			contactTransDTO.setContactTransactionList(contactTransDTOList);
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}finally {
			try{
				session.close();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return contactTransDTO;
	}
	
	private String getContactTransactionQuery() {
		String contactQuery = "";
		StringBuffer query = new StringBuffer();
		try {
			query.append("     SELECT CONTACT_QUERY.DISPLAY_ORDER AS DISPLAY_ORDER, CONTACT_QUERY.DATA_TYPE,CONTACT_QUERY.CONTACT_ID,CONTACT.CONTACT_NAME,     ");
			query.append("     CONTACT_QUERY.AMOUNT,CONTACT_QUERY.METAL,CONTACT_QUERY.METAL_NAME,     ");
			query.append("	   CONTACT_QUERY.BILL_DATE AS BILL_DATE, CONTACT_QUERY.BILL_NUMBER AS BILL_NUMBER, CONTACT_QUERY.BILL_ID AS BILL_ID,CONTACT_QUERY.WEIGHT AS WEIGHT");
			query.append("     FROM CONTACT JOIN     ");
			query.append("     (     ");
			
			query.append("     SELECT 3 as DISPLAY_ORDER, 'BILL' AS DATA_TYPE,BILL.CONTACT_ID,     ");
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
			//query.append("     LEFT JOIN CONTACT_TYPE CT     ");
			//query.append("     ON CON.CONTACT_TYPE_ID = CT.CONTACT_TYPE_ID     ");

			query.append("     WHERE BILL.DELETED_FLAG=:delFlag     ");
			query.append("     AND BILL.LICENCE_ID=:licenceId     ");
			//query.append("     AND CT.CONTACT_TYPE_NAME = :contactTypeName     ");
			query.append(" AND CON.CONTACT_ID= :contactId");
			query.append("     AND BS.BILL_SOURCE_NAME IN (:billSourceName)     ");
			query.append("     AND (BILL.BILL_DATE BETWEEN STR_TO_DATE(:fromDate,'%d/%m/%Y') AND STR_TO_DATE(:toDate,'%d/%m/%Y'))	");

			query.append("     UNION ALL     ");
			
			query.append("     SELECT 2 as DISPLAY_ORDER, 'OLD_BILL' AS DATA_TYPE,BILL.CONTACT_ID,     ");
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
			//query.append("     LEFT JOIN CONTACT_TYPE CT     ");
			//query.append("     ON CON.CONTACT_TYPE_ID = CT.CONTACT_TYPE_ID     ");

			query.append("     WHERE BILL.DELETED_FLAG=:delFlag     ");
			query.append("     AND BILL.LICENCE_ID=:licenceId     ");
			//query.append("     AND CT.CONTACT_TYPE_NAME = :contactTypeName     ");
			query.append(" AND CON.CONTACT_ID= :contactId");
			query.append("     AND BS.BILL_SOURCE_NAME IN (:billSourceName)     ");
			query.append("     AND (BILL.BILL_DATE < STR_TO_DATE(:fromDate,'%d/%m/%Y'))     ");
			
			
			
			query.append("     UNION ALL     ");

			query.append("     SELECT 5 as DISPLAY_ORDER, 'PAYMENT' AS DATA_TYPE,PD.CONTACT_ID,     ");
			query.append("     PD.AMOUNT_PAID AMOUNT,     ");
			query.append("     PD.METAL_PAID METAL,     ");
			query.append("     METAL.METAL_NAME METAL_NAME,     ");
			query.append("     PD.PAYMENT_DATE_TIME AS BILL_DATE,NULL AS BILL_NUMBER,PD.PAYMENT_DETAILS_ID AS BILL_ID,NULL AS WEIGHT     ");
			query.append("     FROM PAYMENT_DETAILS PD     ");
			query.append("     LEFT JOIN BILL_SOURCE BS     ");
			query.append("     ON PD.BILL_SOURCE_ID = BS.BILL_SOURCE_ID     ");
			query.append("     LEFT JOIN METAL METAL     ");
			query.append("     ON PD.METAL_ID = METAL.METAL_ID     ");
			query.append("     LEFT JOIN CONTACT CON     ");
			query.append("     ON PD.CONTACT_ID = CON.CONTACT_ID     ");
			//query.append("     LEFT JOIN CONTACT_TYPE CT     ");
			//query.append("     ON CON.CONTACT_TYPE_ID = CT.CONTACT_TYPE_ID     ");

			query.append("     WHERE PD.DELETED_FLAG=:delFlag     ");
			query.append("     AND PD.LICENCE_ID=:licenceId     ");
			//query.append("     AND CT.CONTACT_TYPE_NAME = :contactTypeName     ");
			query.append(" AND CON.CONTACT_ID= :contactId");
			query.append("     AND BS.BILL_SOURCE_NAME IN (:billSourceName)     ");
			query.append("     AND (PD.PAYMENT_DATE_TIME BETWEEN STR_TO_DATE(:fromDate,'%d/%m/%Y') AND STR_TO_DATE(:toDate,'%d/%m/%Y'))	");
			
			query.append("     UNION ALL     ");

			query.append("     SELECT 4 as DISPLAY_ORDER, 'OLD_PAYMENT' AS DATA_TYPE,PD.CONTACT_ID,     ");
			query.append("     PD.AMOUNT_PAID AMOUNT,     ");
			query.append("     PD.METAL_PAID METAL,     ");
			query.append("     METAL.METAL_NAME METAL_NAME,     ");
			query.append("     PD.PAYMENT_DATE_TIME AS BILL_DATE,NULL AS BILL_NUMBER,PD.PAYMENT_DETAILS_ID AS BILL_ID,NULL AS WEIGHT     ");
			query.append("     FROM PAYMENT_DETAILS PD     ");
			query.append("     LEFT JOIN BILL_SOURCE BS     ");
			query.append("     ON PD.BILL_SOURCE_ID = BS.BILL_SOURCE_ID     ");
			query.append("     LEFT JOIN METAL METAL     ");
			query.append("     ON PD.METAL_ID = METAL.METAL_ID     ");
			query.append("     LEFT JOIN CONTACT CON     ");
			query.append("     ON PD.CONTACT_ID = CON.CONTACT_ID     ");
			//query.append("     LEFT JOIN CONTACT_TYPE CT     ");
			//query.append("     ON CON.CONTACT_TYPE_ID = CT.CONTACT_TYPE_ID     ");

			query.append("     WHERE PD.DELETED_FLAG=:delFlag     ");
			query.append("     AND PD.LICENCE_ID=:licenceId     ");
			//query.append("     AND CT.CONTACT_TYPE_NAME = :contactTypeName     ");
			query.append(" AND CON.CONTACT_ID= :contactId");
			query.append("     AND BS.BILL_SOURCE_NAME IN (:billSourceName)     ");
			query.append("     AND (PD.PAYMENT_DATE_TIME < STR_TO_DATE(:fromDate,'%d/%m/%Y'))     ");
			
			
			query.append("     UNION ALL     ");
			query.append("     SELECT 1 as DISPLAY_ORDER, 'OPENING' AS DATA_TYPE,OPENING.CONTACT_ID,     ");
			query.append("     IFNULL(OPENING.AMOUNT,0) AMOUNT,     ");
			query.append("     IFNULL(OPENING.GRAMS,0) METAL,     ");
			query.append("     IFNULL(METAL.METAL_NAME,'') METAL_NAME,     ");
			query.append("     NULL AS BILL_DATE,NULL AS BILL_NUMBER,NULL AS BILL_ID,NULL AS WEIGHT     ");
			query.append("     FROM OPENING_BALANCE_DETAILS_CONTACT OPENING     ");
			query.append("     LEFT JOIN BILL_TYPE BT     ");
			query.append("     ON OPENING.BILL_TYPE_ID = BT.BILL_TYPE_ID     ");
			query.append("     LEFT JOIN METAL METAL     ");
			query.append("     ON OPENING.METAL_ID = METAL.METAL_ID     ");
			query.append("     LEFT JOIN CONTACT CON     ");
			query.append("     ON OPENING.CONTACT_ID = CON.CONTACT_ID     ");
			//query.append("     LEFT JOIN CONTACT_TYPE CT     ");
			//query.append("     ON CON.CONTACT_TYPE_ID = CT.CONTACT_TYPE_ID     ");
			
			query.append("     WHERE OPENING.DELETED_FLAG=:delFlag     ");
			query.append("     AND OPENING.LICENCE_ID=:licenceId     ");
			//query.append("     AND CT.CONTACT_TYPE_NAME = :contactTypeName     ");
			query.append(" AND CON.CONTACT_ID= :contactId");
			query.append("     AND BT.BILL_TYPE_NAME IN (:billType)     ");
			

			
			query.append("     )CONTACT_QUERY	");
			query.append("     ON CONTACT.CONTACT_ID = CONTACT_QUERY.CONTACT_ID	");
			
			
			//query.append(" WHERE CONTACT.CONTACT_ID= :contactId");
			
			//query.append("	   ORDER BY CONTACT_QUERY.DISPLAY_ORDER	");
			query.append("	ORDER BY CONTACT_QUERY.BILL_DATE");
			
			
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		contactQuery = query.toString();
		return contactQuery;
	}
	
	public String getBalanceSheetQuery(Integer contactId) {
		String balanceSheetQuery = "";
		try {
			StringBuffer query = new StringBuffer();
			
			
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
			//query.append("     AND CT.CONTACT_TYPE_NAME = :contactTypeName     ");
			query.append(" AND CON.CONTACT_ID= :contactId");
			query.append("     AND BS.BILL_SOURCE_NAME IN (:billSourceName)     ");
			//query.append("     AND BILL.BILL_DATE <= STR_TO_DATE(:toDate,'%d/%m/%Y')     ");
			query.append("     AND (BILL.BILL_DATE BETWEEN STR_TO_DATE(:fromDate,'%d/%m/%Y') AND STR_TO_DATE(:toDate,'%d/%m/%Y'))	");
			

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
			//query.append("     AND CT.CONTACT_TYPE_NAME = :contactTypeName     ");
			query.append(" AND CON.CONTACT_ID= :contactId");
			query.append("     AND BS.BILL_SOURCE_NAME IN (:billSourceName)     ");
			//query.append("     AND PD.PAYMENT_DATE_TIME <= STR_TO_DATE(:toDate,'%d/%m/%Y')     ");
			query.append("     AND (PD.PAYMENT_DATE_TIME BETWEEN STR_TO_DATE(:fromDate,'%d/%m/%Y') AND STR_TO_DATE(:toDate,'%d/%m/%Y'))	");
			
			
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
			//query.append("     AND CT.CONTACT_TYPE_NAME = :contactTypeName     ");
			query.append(" AND CON.CONTACT_ID= :contactId");
			query.append("     AND BT.BILL_TYPE_NAME IN (:billType)     ");

			
			query.append("     )BALANCE_SHEET	");
			query.append("     ON CONTACT.CONTACT_ID = BALANCE_SHEET.CONTACT_ID	");
			
			
			//if(contactId!=null && contactId>0) {
				//query.append(" WHERE CONTACT.CONTACT_ID= :contactId");
			//}
			
			query.append("	   ORDER BY BALANCE_SHEET.DISPLAY_ORDER	");
			
			balanceSheetQuery = query.toString();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return balanceSheetQuery;
	}

	@Override
	public Boolean updateClosingBalance(String closingCashBalance, String closingMetalBalance, Integer billId) {
		// TODO Auto-generated method stub
		Boolean flag = false;
		Session session = null;
		Transaction tx= null;
		try {
			session = factory.openSession();
			tx=session.beginTransaction();
			
			final SQLQuery query = session.createSQLQuery("update Bill set CLOSING_CASH_BALANCE=:closeCash, CLOSING_METAL_BALANCE=:closeMetal where BILL_ID=:id");
			
			//Query q=session.createQuery("update Bill set closingCashBalance=:closeCash, closingMetalBalance=:closeMetal where billId=:id");
			
			query.setParameter("closeCash",closingCashBalance);
			query.setParameter("closeMetal",closingMetalBalance);
			query.setParameter("id",billId);
			
			int status=query.executeUpdate();  
			System.out.println(status);  
			tx.commit();  
			
			if(status>0) {
				flag = true;
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
		return false;
	}


}
