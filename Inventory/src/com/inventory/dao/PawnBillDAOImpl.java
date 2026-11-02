package com.inventory.dao;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.inventory.model.Bill;
import com.inventory.model.BillNumber;
import com.inventory.model.BillSource;
import com.inventory.model.PawnBill;
import com.inventory.model.PawnBillAuctionDetails;
import com.inventory.model.PawnBillRedeemDetails;
import com.inventory.model.PawnBillStatus;
import com.inventory.model.State;
import com.inventory.model.pbold.OldBillDetails;
import com.inventory.model.pbold.CustomerDetails;
import com.inventory.model.pbold.OldBillDescription;
import com.inventory.utility.ConstantUtils;
import com.inventory.utility.HibernateUtil;

public class PawnBillDAOImpl implements PawnBillDAO {
	
	private static PawnBillDAOImpl myInstance = null;
	SessionFactory factory = null;
	
	private PawnBillDAOImpl(){
		try{
		factory = HibernateUtil.getSessionFactory();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public static PawnBillDAOImpl getInstace(){
		if(myInstance == null){
			myInstance = new PawnBillDAOImpl();
		}
		return myInstance;
	}

	@Override
	public PawnBill saveOrUpdateBill(PawnBill bill) {
		// TODO Auto-generated method stub
		Session session = null;
		PawnBill billObj = null;
		try{
			session = factory.openSession();
			Transaction t = session.beginTransaction();   
		        
		    //session.saveOrUpdate(bill);
		    bill = (PawnBill)session.merge(bill);
		    
		    t.commit();  
		    //System.out.println("successfully saved");    
		    
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
	public List<PawnBill> getPawnBillList(Date fromDate, Date toDate, Integer licenceId, Integer pawnBillStatusId,
			Integer contactId,String pawnBillSource,Integer metalId) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		List<PawnBill> billList = null;
		try{
			session = factory.openSession();
			tx = session.beginTransaction();
			
			String statusCode ="";
			
			Criteria criteria = session.createCriteria(PawnBill.class);
			criteria.add(Restrictions.eq("deletedFlag", ConstantUtils.CONSTANT_N));
			
			//criteria.createAlias("billSource", "billSource");
			//criteria.add(Restrictions.eq("billSource.billSourceId", billSourceId));
			
			if(pawnBillStatusId!=null && pawnBillStatusId>0) {
			criteria.createAlias("pawnBillStatusId", "pawnBillStatusId");
			criteria.add(Restrictions.eq("pawnBillStatusId.pawnBillStatusId", pawnBillStatusId));
			
			statusCode = getPawnBillStatusList()
			.stream()
			.filter(pawnStatus -> pawnStatus.getPawnBillStatusId().equals(pawnBillStatusId))
			.map(status -> status.getPawnBillStatus())
			.collect(Collectors.joining());
			/*.forEach(pawnStatus ->{
				if(pawnStatus.equals(pawnBillStatusId)) {
					
				}
				
			})*/
			//System.out.println("statusCode - "+statusCode);
			
			}		    
			
			
			
			criteria.createAlias("licence", "licence");
			criteria.add(Restrictions.eq("licence.licenceId", licenceId));
			
			if(contactId!=null && contactId>0){
				criteria.createAlias("contact", "contact");
				criteria.add(Restrictions.eq("contact.contactId", contactId));
			}
			if(statusCode.equalsIgnoreCase("REDEEM")) {
				
				criteria.createAlias("pawnBillRedeemDetails", "pawnBillRedeemDetails");
				
				if(fromDate!=null && toDate!=null){
					criteria.add(Restrictions.ge("pawnBillRedeemDetails.redeemDate", fromDate));
					criteria.add(Restrictions.le("pawnBillRedeemDetails.redeemDate", toDate));
				}else if(fromDate!=null){
					criteria.add(Restrictions.gt("pawnBillRedeemDetails.redeemDate", fromDate));
				}else if(toDate!=null){
					criteria.add(Restrictions.lt("pawnBillRedeemDetails.redeemDate", toDate));
				}
				criteria.addOrder(Order.asc("pawnBillRedeemDetails.deliveryNumber"));
			}else {
				if(fromDate!=null && toDate!=null){
					criteria.add(Restrictions.ge("billDate", fromDate));
					criteria.add(Restrictions.le("billDate", toDate));
				}else if(fromDate!=null){
					criteria.add(Restrictions.gt("billDate", fromDate));
				}else if(toDate!=null){
					criteria.add(Restrictions.lt("billDate", toDate));
				}
				criteria.addOrder(Order.asc("billNumber"));
			}
			
			
			
			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
            billList = (List<PawnBill>) criteria.list();
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
		return billList;
	}

	@Override
	public PawnBill getPawnBill(Integer pawnBillId) {
		// TODO Auto-generated method stub
		PawnBill bill = null;
		
		
		Session session = null;
		try{
			session = factory.openSession();
			
			bill = (PawnBill)session.get(PawnBill.class, pawnBillId);
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
	public PawnBillStatus getPawnBillStatusByCode(String pawnBillStatusCode) {
		// TODO Auto-generated method stub
		PawnBillStatus pawnBillStatus = null;
		List<PawnBillStatus> pawnBillStatusList = null;
		
		Session session = null;
		try{
			session = factory.openSession();
			
			//metal = (Metal)session.get(Metal.class, metalId);
			
			Criteria criteria = session.createCriteria(PawnBillStatus.class);
			criteria.add(Restrictions.eq("pawnBillStatus", pawnBillStatusCode));
             
			pawnBillStatusList = (List<PawnBillStatus>) criteria.list();
            
            if(pawnBillStatusList!=null && pawnBillStatusList.size()>0){
            	pawnBillStatus = pawnBillStatusList.get(0);
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
		return pawnBillStatus;
	}

	@Override
	public PawnBill getPawnBillByBillNumber(String billNumber, Integer licenceId, String pawnBillSource) {
		// TODO Auto-generated method stub
		PawnBill obj = null;
		Session session = null;
		try {
			session = factory.openSession();
			
			Criteria criteria = session.createCriteria(PawnBill.class);
			criteria.add(Restrictions.eq("deletedFlag", ConstantUtils.CONSTANT_N));
			criteria.add(Restrictions.eq("billNumber", billNumber));
			
			
			criteria.createAlias("billSource", "billSource");
			criteria.add(Restrictions.eq("billSource.billSourceName", pawnBillSource));
		    
			criteria.createAlias("licence", "licence");
			criteria.add(Restrictions.eq("licence.licenceId", licenceId));
		    
			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
            //billList = (List<Bill>) criteria.list();
			obj = (PawnBill) criteria.uniqueResult();
			
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
	public PawnBillRedeemDetails saveOrUpdatePawnBillRedeemDetails(PawnBillRedeemDetails pawnBillRedeemDetails) {
		// TODO Auto-generated method stub
		Session session = null;
		PawnBillRedeemDetails billObj = null;
		try{
			session = factory.openSession();
			Transaction t = session.beginTransaction();   
		        
		    //session.saveOrUpdate(bill);
			pawnBillRedeemDetails = (PawnBillRedeemDetails)session.merge(pawnBillRedeemDetails);
		    
		    t.commit();  
		    //System.out.println("successfully saved");    
		    
		    if(pawnBillRedeemDetails!=null){
		    	billObj = pawnBillRedeemDetails;
		    	
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
		return billObj;
	}
	

	@Override
	public PawnBillRedeemDetails getPawnBillRedeemDetails(Integer pawnBillRedeemDetailsId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OldBillDetails> getPawnOldBillDetails() {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		List<OldBillDetails> billDetailsList = null;
		try{
			session = factory.openSession();
			tx = session.beginTransaction();
			
			Criteria criteria = session.createCriteria(OldBillDetails.class);
		    
			//criteria.add(Restrictions.eq("deletedFlag", ConstantUtils.CONSTANT_N));
			//criteria.addOrder(Order.desc("isEnabled"));
			criteria.addOrder(Order.asc("id"));
			
			
			//criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			
			billDetailsList = (List<OldBillDetails>) criteria.list();
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
		return billDetailsList;
	}

	@Override
	public List<OldBillDescription> getPawnOldBillDescription() {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		List<OldBillDescription> billDescriptionList = null;
		try{
			session = factory.openSession();
			tx = session.beginTransaction();
			
			Criteria criteria = session.createCriteria(OldBillDescription.class);
		    
			//criteria.add(Restrictions.eq("deletedFlag", ConstantUtils.CONSTANT_N));
			//criteria.addOrder(Order.desc("isEnabled"));
			criteria.addOrder(Order.asc("id"));
			
			
			//criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			
			billDescriptionList = (List<OldBillDescription>) criteria.list();
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
		return billDescriptionList;
	}

	@Override
	public List<PawnBillStatus> getPawnBillStatusList() {
		Session session = null;
		Transaction tx = null;
		List<PawnBillStatus> pawnBillStatusList = null;
		try{
			session = factory.openSession();
			tx = session.beginTransaction();
			
			Criteria criteria = session.createCriteria(PawnBillStatus.class);
			criteria.add(Restrictions.eq("deletedFlag", ConstantUtils.CONSTANT_N));
	         
			pawnBillStatusList = (List<PawnBillStatus>) criteria.list();
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
		return pawnBillStatusList;
	}

	@Override
	public Integer updatePawnBillDetails(Integer pawnBillId, String pawnBillNumber, Date pawnBillDate) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		Integer result = null;
		try {
			session = factory.openSession();
			tx = session.beginTransaction();
			
			StringBuffer updateQuery = new StringBuffer();
			
			
			updateQuery.append("UPDATE PAWN_BILL SET BILL_NUMBER = :billNumber, BILL_DATE = :billDate where PAWN_BILL_ID = :pawnBillId ");	
						
			Query query = session.createSQLQuery(updateQuery.toString());
			
			query.setParameter("billNumber", pawnBillNumber);
			query.setParameter("billDate", pawnBillDate);
			query.setParameter("pawnBillId", pawnBillId);
			
			result = query.executeUpdate();
			
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
		return result;
	}
	
	@Override
	public Integer updatePawnBillRedeemDetails(Integer pawnBillId, String deliveryNumber, Date redeemDate) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		Integer result = null;
		try {
			session = factory.openSession();
			tx = session.beginTransaction();
			
			StringBuffer updateQuery = new StringBuffer();
			
			
			updateQuery.append("UPDATE PAWN_BILL_REDEEM_DETAILS SET DELIVERY_NUMBER = :deliveryNumber, REDEEM_DATE = :redeemDate where PAWN_BILL_ID = :pawnBillId ");	
						
			Query query = session.createSQLQuery(updateQuery.toString());
			
			query.setParameter("deliveryNumber", deliveryNumber);
			query.setParameter("redeemDate", redeemDate);
			query.setParameter("pawnBillId", pawnBillId);
			
			result = query.executeUpdate();
			
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
		return result;
	}

	@Override
	public Integer updatePawnBillAuctionStatus(Integer pawnBillId, String auctionStatus) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		Integer result = null;
		try {
			session = factory.openSession();
			tx = session.beginTransaction();
			
			StringBuffer updateQuery = new StringBuffer();
			
			
			updateQuery.append("UPDATE PAWN_BILL SET AUCTION_STATUS = :auctionStatus where PAWN_BILL_ID = :pawnBillId ");	
						
			Query query = session.createSQLQuery(updateQuery.toString());
			System.out.println("DAO->"+pawnBillId);
			
			query.setParameter("auctionStatus", auctionStatus);
			query.setParameter("pawnBillId", pawnBillId);
			
			result = query.executeUpdate();
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
		return result;
	}

	@Override
	public PawnBillAuctionDetails saveOrUpdatePawnBillAuctionDetails(PawnBillAuctionDetails pawnBillAuctionDetails) {
		// TODO Auto-generated method stub
		Session session = null;
		PawnBillAuctionDetails auctionObj = null;
		try{
			session = factory.openSession();
			Transaction t = session.beginTransaction();   
		        
		    //session.saveOrUpdate(bill);
			pawnBillAuctionDetails = (PawnBillAuctionDetails)session.merge(pawnBillAuctionDetails);
		    
		    t.commit();  
		    System.out.println("successfully saved");    
		    
		    if(pawnBillAuctionDetails!=null){
		    	auctionObj = pawnBillAuctionDetails;
		    	
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
		return auctionObj;
	}

	@Override
	public PawnBillAuctionDetails getPawnBillAuctionDetails(Integer pawnBillAuctionDetailsId) {
		// TODO Auto-generated method stub
		PawnBillAuctionDetails auction = null;
		
		
		Session session = null;
		try{
			session = factory.openSession();
			
			auction = (PawnBillAuctionDetails)session.get(PawnBillAuctionDetails.class, pawnBillAuctionDetailsId);
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try{
				session.close();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return auction;
	}

	@Override
	public Integer deletePawnBillEntry(Integer pawnBillId) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		Integer result = null;
		try {
			session = factory.openSession();
			tx = session.beginTransaction();
			
			StringBuffer updateQuery = new StringBuffer();
			
			
			updateQuery.append("UPDATE PAWN_BILL SET DELETED_FLAG = :deleteStatus where PAWN_BILL_ID = :pawnBillId ");	
						
			Query query = session.createSQLQuery(updateQuery.toString());
			System.out.println("DAO->"+pawnBillId);
			
			query.setParameter("deleteStatus", ConstantUtils.CONSTANT_Y);
			query.setParameter("pawnBillId", pawnBillId);
			
			result = query.executeUpdate();
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
		return result;
	}

	@Override
	public Integer deletePawnRedeemEntry(Integer pawnBillId) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		Integer result = null;
		try {
			session = factory.openSession();
			tx = session.beginTransaction();
			
			StringBuffer updateQuery = new StringBuffer();
			
			
			updateQuery.append("UPDATE PAWN_BILL_REDEEM_DETAILS SET DELETED_FLAG = :deleteStatus where PAWN_BILL_ID = :pawnBillId ");	
						
			Query query = session.createSQLQuery(updateQuery.toString());
			System.out.println("DAO->"+pawnBillId);
			
			query.setParameter("deleteStatus", ConstantUtils.CONSTANT_Y);
			query.setParameter("pawnBillId", pawnBillId);
			
			result = query.executeUpdate();
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
		return result;
	}

	@Override
	public Integer deletePawnAuctionEntry(Integer pawnBillId) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		Integer result = null;
		try {
			session = factory.openSession();
			tx = session.beginTransaction();
			
			StringBuffer updateQuery = new StringBuffer();
			
			
			updateQuery.append("UPDATE PAWN_BILL_AUCTION_DETAILS SET DELETED_FLAG = :deleteStatus where PAWN_BILL_ID = :pawnBillId ");	
						
			Query query = session.createSQLQuery(updateQuery.toString());
			System.out.println("DAO->"+pawnBillId);
			
			query.setParameter("deleteStatus", ConstantUtils.CONSTANT_Y);
			query.setParameter("pawnBillId", pawnBillId);
			
			result = query.executeUpdate();
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
		return result;
	}

	@Override
	public Integer updatePawnBillStatus(Integer pawnBillId, Integer pawnBillStatusId) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		Integer result = null;
		try {
			session = factory.openSession();
			tx = session.beginTransaction();
			
			StringBuffer updateQuery = new StringBuffer();
			
			
			updateQuery.append("UPDATE PAWN_BILL SET PAWN_BILL_STATUS_ID = :pawnBillStatusId where PAWN_BILL_ID = :pawnBillId ");	
						
			Query query = session.createSQLQuery(updateQuery.toString());
			System.out.println("DAO->"+pawnBillId);
			
			query.setParameter("pawnBillStatusId", pawnBillStatusId);
			query.setParameter("pawnBillId", pawnBillId);
			
			result = query.executeUpdate();
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
		return result;
	}
	
	
	

}
