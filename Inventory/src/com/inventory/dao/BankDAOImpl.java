package com.inventory.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.inventory.model.Bank;
import com.inventory.model.BankEntry;
import com.inventory.model.Bill;
import com.inventory.model.OpeningBalanceDetailsLicence;
import com.inventory.model.PaymentDetails;
import com.inventory.utility.ConstantUtils;
import com.inventory.utility.HibernateUtil;

public class BankDAOImpl implements BankDAO {
	
	private static BankDAOImpl myInstance = null;
	SessionFactory factory = null;
	
	private BankDAOImpl(){
		try{
		factory = HibernateUtil.getSessionFactory();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public static BankDAOImpl getInstace(){
		if(myInstance == null){
			myInstance = new BankDAOImpl();
		}
		return myInstance;
	}

	@Override
	public Bank saveOrUpdateBank(Bank bank) {
		// TODO Auto-generated method stub
		Session session = null;
		Bank bankObj = null;
		try{
			session = factory.openSession();
			Transaction t = session.beginTransaction();   
		        
		    //session.saveOrUpdate(bill);
		    bank = (Bank)session.merge(bank);
		    
		    t.commit();  
		    System.out.println("successfully saved");    
		    
		    if(bank!=null){
		    	bankObj = bank;
		    	
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
		return bankObj;
	}

	@Override
	public List<Bank> getBankList() {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		List<Bank> bankList = null;
		try{
			session = factory.openSession();
			tx = session.beginTransaction();
			
			Criteria criteria = session.createCriteria(Bank.class);
			criteria.add(Restrictions.eq("deletedFlag", ConstantUtils.CONSTANT_N));
			
			criteria.addOrder(Order.asc("bankName"));
			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			bankList = (List<Bank>) criteria.list();
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
		return bankList;
	}

	@Override
	public Bank getBank(Integer bankId) {
		// TODO Auto-generated method stub
		Bank bank = null;
		
		
		Session session = null;
		try{
			session = factory.openSession();
			
			bank = (Bank)session.get(Bank.class, bankId);
			
			if(bank!=null) {
				List<PaymentDetails> bankPaymentTransientList = new ArrayList<PaymentDetails>();
				
				if(bank.getBankPaymentList()!=null && bank.getBankPaymentList().size()>0) {
					for(PaymentDetails pd:bank.getBankPaymentList()) {
						if(pd.getBank()!=null) {
						bankPaymentTransientList.add(pd);
						}
					}
				}
				bank.setBankPaymentTransientList(bankPaymentTransientList);
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
		return bank;
	}

	@Override
	public BankEntry saveOrUpdateBankEntry(BankEntry bankEntry) {
		// TODO Auto-generated method stub
		Session session = null;
		BankEntry bankEntryObj = null;
		try{
			session = factory.openSession();
			Transaction t = session.beginTransaction();   
		        
		    //session.saveOrUpdate(bill);
		    bankEntry = (BankEntry)session.merge(bankEntry);
		    
		    t.commit();  
		    System.out.println("successfully saved");    
		    
		    if(bankEntry!=null){
		    	bankEntryObj = bankEntry;
		    	
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
		return bankEntryObj;
	}

	@Override
	public List<BankEntry> getBankEntryList(Date fromDate, Date toDate, Integer bankId) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		List<BankEntry> bankEntryList = null;
		try{
			session = factory.openSession();
			tx = session.beginTransaction();
			
			Criteria criteria = session.createCriteria(BankEntry.class);
			criteria.add(Restrictions.eq("deletedFlag", ConstantUtils.CONSTANT_N));
			
			criteria.createAlias("bank", "bank");
			criteria.add(Restrictions.eq("bank.bankId", bankId));
		    
			
			if(fromDate!=null && toDate!=null){
				criteria.add(Restrictions.ge("entryDate", fromDate));
				criteria.add(Restrictions.le("entryDate", toDate));
			}else if(fromDate!=null){
				criteria.add(Restrictions.gt("entryDate", fromDate));
			}else if(toDate!=null){
				criteria.add(Restrictions.lt("entryDate", toDate));
			}
			criteria.addOrder(Order.asc("entryDate"));
			criteria.addOrder(Order.asc("bankEntryId"));
			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			bankEntryList = (List<BankEntry>) criteria.list();
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
		return bankEntryList;
	}


}
