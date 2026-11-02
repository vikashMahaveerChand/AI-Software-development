package com.inventory.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.inventory.dto.LicenceOpeningStockDetailsDTO;
import com.inventory.dto.OpeningStockDetailsLicenceDTO;
import com.inventory.model.Area;
import com.inventory.model.Bank;
import com.inventory.model.BillResetMaster;
import com.inventory.model.BillSource;
import com.inventory.model.BillType;
import com.inventory.model.City;
import com.inventory.model.Contact;
import com.inventory.model.ContactType;
import com.inventory.model.CoverWeight;
import com.inventory.model.DayMaster;
import com.inventory.model.Employee;
import com.inventory.model.EntryType;
import com.inventory.model.Licence;
import com.inventory.model.LicenceOpeningStockDetails;
import com.inventory.model.LicenceType;
import com.inventory.model.Metal;
import com.inventory.model.MonthMaster;
import com.inventory.model.OpeningBalanceDetailsContact;
import com.inventory.model.OpeningBalanceDetailsLicence;
import com.inventory.model.OpeningStockDetailsLicence;
import com.inventory.model.Ornament;
import com.inventory.model.State;
import com.inventory.model.Street;
import com.inventory.model.User;
import com.inventory.model.UserType;
import com.inventory.model.WeekMaster;
import com.inventory.model.pbold.CustomerDetails;
import com.inventory.utility.ConstantUtils;
import com.inventory.utility.HibernateUtil;


public class MasterDAOImpl implements MasterDAO {
	
	private static MasterDAOImpl myInstance = null;
	SessionFactory factory = null;
	
	private MasterDAOImpl(){
		try{
		factory = HibernateUtil.getSessionFactory();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public static MasterDAOImpl getInstace(){
		if(myInstance == null){
			myInstance = new MasterDAOImpl();
		}
		return myInstance;
	}

	@Override
	public LicenceType getLicenceType(Integer licenceTypeId) {
		// TODO Auto-generated method stub
		LicenceType licenceType = null;
		
		
		Session session = null;
		try{
			session = factory.openSession();
			
			licenceType = (LicenceType)session.get(LicenceType.class, licenceTypeId);
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try{
				session.close();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return licenceType;
	}

	@Override
	public Integer saveOrUpdateLicence(Licence licence) {
		// TODO Auto-generated method stub
		Session session = null;
		Integer licenceId = null;
		try{
			session = factory.openSession();
			
			Transaction t = session.beginTransaction();   
		        
		    session.saveOrUpdate(licence);
		    licence = (Licence)session.merge(licence);
		    
		    t.commit();  
		    System.out.println("successfully saved");    
		    
		    if(licence!=null){
		    	licenceId = licence.getLicenceId();
		    	
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
		return licenceId;
	}

	@Override
	public List<Licence> getLicenceList() {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		List<Licence> licence = null;
		try{
			session = factory.openSession();
			tx = session.beginTransaction();
			
			Criteria criteria = session.createCriteria(Licence.class);
			criteria.add(Restrictions.eq("deletedFlag", ConstantUtils.CONSTANT_N));
			criteria.addOrder(Order.asc("licenceName"));
            licence = (List<Licence>) criteria.list();
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
		return licence;
	}

	@Override
	public List<LicenceType> getLicenceTypeList() {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		List<LicenceType> licenceType = null;
		try{
			session = factory.openSession();
			tx = session.beginTransaction();
			
			Criteria criteria = session.createCriteria(LicenceType.class);
			criteria.add(Restrictions.eq("deletedFlag", ConstantUtils.CONSTANT_N));
             
            licenceType = (List<LicenceType>) criteria.list();
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
		return licenceType;
	}

	@Override
	public Integer saveOrUpdateState(State state) {
		// TODO Auto-generated method stub
		Session session = null;
		Integer stateId = null;
		try{
			session = factory.openSession();
			
			Transaction t = session.beginTransaction();   
		        
		    session.saveOrUpdate(state);
		    state = (State)session.merge(state);
		    
		    t.commit();  
		    System.out.println("successfully saved");    
		    
		    if(state!=null){
		    	stateId = state.getStateId();
		    	
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
		return stateId;
	}

	@Override
	public List<State> getStateList() {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		List<State> stateTypeList = null;
		try{
			session = factory.openSession();
			tx = session.beginTransaction();
			
			Criteria criteria = session.createCriteria(State.class);
			criteria.add(Restrictions.eq("deletedFlag", ConstantUtils.CONSTANT_N));
             
            stateTypeList = (List<State>) criteria.list();
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
		return stateTypeList;
	}

	@Override
	public State getState(Integer stateId) {
		// TODO Auto-generated method stub
		State state = null;
		
		
		Session session = null;
		try{
			session = factory.openSession();
			
			state = (State)session.get(State.class, stateId);
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try{
				session.close();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return state;
	}
	
	@Override
	public Integer saveOrUpdateCity(City city) {
		// TODO Auto-generated method stub
		Session session = null;
		Integer cityId = null;
		try{
			session = factory.openSession();
			
			Transaction t = session.beginTransaction();   
		        
		    session.saveOrUpdate(city);
		    city = (City)session.merge(city);
		    
		    t.commit();  
		    System.out.println("successfully saved");    
		    
		    if(city!=null){
		    	cityId = city.getCityId();
		    	
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
		return cityId;
	}

	@Override
	public List<City> getCityList() {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		List<City> cityList = null;
		try{
			session = factory.openSession();
			tx = session.beginTransaction();
			
			Criteria criteria = session.createCriteria(City.class);
			criteria.add(Restrictions.eq("deletedFlag", ConstantUtils.CONSTANT_N));
             
            cityList = (List<City>) criteria.list();
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
		return cityList;
	}

	@Override
	public City getCity(Integer cityId) {
		// TODO Auto-generated method stub
		City city = null;
		
		
		Session session = null;
		try{
			session = factory.openSession();
			
			city = (City)session.get(City.class, cityId);
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try{
				session.close();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return city;
	}
	
	@Override
	public Integer saveOrUpdateArea(Area area) {
		// TODO Auto-generated method stub
		Session session = null;
		Integer areaId = null;
		try{
			session = factory.openSession();
			
			Transaction t = session.beginTransaction();   
		        
		    session.saveOrUpdate(area);
		    area = (Area)session.merge(area);
		    
		    t.commit();  
		    System.out.println("successfully saved");    
		    
		    if(area!=null){
		    	areaId = area.getAreaId();
		    	
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
		return areaId;
	}

	@Override
	public List<Area> getAreaList() {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		List<Area> areaList = null;
		try{
			session = factory.openSession();
			tx = session.beginTransaction();
			
			Criteria criteria = session.createCriteria(Area.class);
			criteria.add(Restrictions.eq("deletedFlag", ConstantUtils.CONSTANT_N));
             
            areaList = (List<Area>) criteria.list();
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
		return areaList;
	}

	@Override
	public Area getArea(Integer areaId) {
		// TODO Auto-generated method stub
		Area area = null;
		
		
		Session session = null;
		try{
			session = factory.openSession();
			
			area = (Area)session.get(Area.class, areaId);
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try{
				session.close();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return area;
	}
	
	@Override
	public Integer saveOrUpdateStreet(Street street) {
		// TODO Auto-generated method stub
		Session session = null;
		Integer streetId = null;
		try{
			session = factory.openSession();
			
			Transaction t = session.beginTransaction();   
		        
		    session.saveOrUpdate(street);
		    street = (Street)session.merge(street);
		    
		    t.commit();  
		    System.out.println("successfully saved");    
		    
		    if(street!=null){
		    	streetId = street.getStreetId();
		    	
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
		return streetId;
	}

	@Override
	public List<Street> getStreetList() {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		List<Street> streetList = null;
		try{
			session = factory.openSession();
			tx = session.beginTransaction();
			
			Criteria criteria = session.createCriteria(Street.class);
			criteria.add(Restrictions.eq("deletedFlag", ConstantUtils.CONSTANT_N));
             
            streetList = (List<Street>) criteria.list();
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
		return streetList;
	}

	@Override
	public Street getStreet(Integer streetId) {
		// TODO Auto-generated method stub
		Street street = null;
		
		
		Session session = null;
		try{
			session = factory.openSession();
			
			street = (Street)session.get(Street.class, streetId);
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try{
				session.close();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return street;
	}

	@Override
	public Integer saveOrUpdateBank(Bank bank) {
		// TODO Auto-generated method stub
		Session session = null;
		Integer bankId = null;
		try{
			session = factory.openSession();
			
			Transaction t = session.beginTransaction();   
		        
		    session.saveOrUpdate(bank);
		    bank = (Bank)session.merge(bank);
		    
		    t.commit();  
		    System.out.println("successfully saved");    
		    
		    if(bank!=null){
		    	bankId = bank.getBankId();
		    	
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
		return bankId;
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
	public Integer saveOrUpdateUser(User user) {
		// TODO Auto-generated method stub
		Session session = null;
		Integer userId = null;
		try{
			session = factory.openSession();
			
			Transaction t = session.beginTransaction();   
		        
		    session.saveOrUpdate(user);
		    user = (User)session.merge(user);
		    
		    t.commit();  
		    System.out.println("successfully saved");    
		    
		    if(user!=null){
		    	userId = user.getUserId();
		    	
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
		return userId;
	}

	@Override
	public List<User> getUserList() {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		List<User> userList = null;
		try{
			session = factory.openSession();
			tx = session.beginTransaction();
			
			Criteria criteria = session.createCriteria(User.class);
			criteria.add(Restrictions.eq("deletedFlag", ConstantUtils.CONSTANT_N));
             
            userList = (List<User>) criteria.list();
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
		return userList;
	}

	@Override
	public User getUser(Integer userId) {
		// TODO Auto-generated method stub
		User user = null;
		
		
		Session session = null;
		try{
			session = factory.openSession();
			
			user = (User)session.get(User.class, userId);
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try{
				session.close();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return user;
	}

	@Override
	public Integer saveOrUpdateUserType(UserType userType) {
		// TODO Auto-generated method stub
		Session session = null;
		Integer userTypeId = null;
		try{
			session = factory.openSession();
			
			Transaction t = session.beginTransaction();   
		        
		    session.saveOrUpdate(userType);
		    userType = (UserType)session.merge(userType);
		    
		    t.commit();  
		    System.out.println("successfully saved");    
		    
		    if(userType!=null){
		    	userTypeId = userType.getUserTypeId();
		    	
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
		return userTypeId;
	}

	@Override
	public List<UserType> getUserTypeList() {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		List<UserType> userTypeList = null;
		try{
			session = factory.openSession();
			tx = session.beginTransaction();
			
			Criteria criteria = session.createCriteria(UserType.class);
			criteria.add(Restrictions.eq("deletedFlag", ConstantUtils.CONSTANT_N));
             
            userTypeList = (List<UserType>) criteria.list();
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
		return userTypeList;
	}

	@Override
	public UserType getUserType(Integer userTypeId) {
		// TODO Auto-generated method stub
		UserType userType = null;
		
		
		Session session = null;
		try{
			session = factory.openSession();
			
			userType = (UserType)session.get(UserType.class, userTypeId);
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try{
				session.close();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return userType;
	}

	@Override
	public Integer saveOrUpdateEmployee(Employee employee) {
		// TODO Auto-generated method stub
		Session session = null;
		Integer employeeId = null;
		try{
			session = factory.openSession();
			
			Transaction t = session.beginTransaction();   
		        
		    //session.saveOrUpdate(employee);
		    employee = (Employee)session.merge(employee);
		    
		    t.commit();  
		    System.out.println("successfully saved");    
		    
		    if(employee!=null){
		    	employeeId = employee.getEmployeeId();
		    	
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
		return employeeId;
	}

	@Override
	public List<Employee> getEmployeeList() {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		List<Employee> employeeList = null;
		try{
			session = factory.openSession();
			tx = session.beginTransaction();
			
			Criteria criteria = session.createCriteria(Employee.class);
			//criteria.add(Restrictions.eq("deletedFlag", ConstantUtils.CONSTANT_N));
             
            employeeList = (List<Employee>) criteria.list();
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
		return employeeList;
	}

	@Override
	public Employee getEmployee(Integer employeeId) {
		// TODO Auto-generated method stub
		Employee employee = null;
		
		
		Session session = null;
		try{
			session = factory.openSession();
			
			employee = (Employee)session.get(Employee.class, employeeId);
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try{
				session.close();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return employee;
	}

	@Override
	public Licence getLicence(Integer licenceId) {
		// TODO Auto-generated method stub
		Licence licence = null;
		
		
		Session session = null;
		try{
			session = factory.openSession();
			
			licence = (Licence)session.get(Licence.class, licenceId);
			
			if(licence!=null) {
				List<OpeningBalanceDetailsLicence> balance = new ArrayList<OpeningBalanceDetailsLicence>();
				
				if(licence.getOpeningBalanceDetailsLicenceList()!=null && licence.getOpeningBalanceDetailsLicenceList().size()>0) {
					for(OpeningBalanceDetailsLicence obj : licence.getOpeningBalanceDetailsLicenceList()) {
						balance.add(obj);
					}
					
					licence.setOpeningBalanceDetailsLicence(balance);
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
		return licence;
	}
	
	@Override
	public Integer saveOrUpdateMetal(Metal metal) {
		// TODO Auto-generated method stub
		Session session = null;
		Integer metalId = null;
		try{
			session = factory.openSession();
			
			Transaction t = session.beginTransaction();   
		        
		    session.saveOrUpdate(metal);
		    metal = (Metal)session.merge(metal);
		    
		    t.commit();  
		    System.out.println("successfully saved");    
		    
		    if(metal!=null){
		    	metalId = metal.getMetalId();
		    	
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
		return metalId;
	}

	@Override
	public List<Metal> getMetalList() {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		List<Metal> metalList = null;
		try{
			session = factory.openSession();
			tx = session.beginTransaction();
			
			Criteria criteria = session.createCriteria(Metal.class);
			criteria.add(Restrictions.eq("deletedFlag", ConstantUtils.CONSTANT_N));
             
            metalList = (List<Metal>) criteria.list();
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
		return metalList;
	}

	@Override
	public Metal getMetal(String metalCode) {
		// TODO Auto-generated method stub
		Metal metal = null;
		List<Metal> metalList = null;
		
		Session session = null;
		try{
			session = factory.openSession();
			
			//metal = (Metal)session.get(Metal.class, metalId);
			
			Criteria criteria = session.createCriteria(Metal.class);
			criteria.add(Restrictions.eq("metalCode", metalCode));
             
            metalList = (List<Metal>) criteria.list();
            
            if(metalList!=null && metalList.size()>0){
            	metal = metalList.get(0);
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
		return metal;
	}
	
	@Override
	public Integer saveOrUpdateOrnament(Ornament ornament) {
		// TODO Auto-generated method stub
		Session session = null;
		Integer ornamentId = null;
		try{
			session = factory.openSession();
			
			Transaction t = session.beginTransaction();   
		        
		    session.saveOrUpdate(ornament);
		    ornament = (Ornament)session.merge(ornament);
		    
		    t.commit();  
		    System.out.println("successfully saved");    
		    
		    if(ornament!=null){
		    	ornamentId = ornament.getOrnamentId();
		    	
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
		return ornamentId;
	}

	@Override
	public List<Ornament> getOrnamentList(Integer licenceId) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		List<Ornament> ornamentList = null;
		try{
			session = factory.openSession();
			tx = session.beginTransaction();
			
			Criteria criteria = session.createCriteria(Ornament.class);
			criteria.add(Restrictions.eq("deletedFlag", ConstantUtils.CONSTANT_N));
			criteria.add(Restrictions.eq("licenceId", licenceId));
			criteria.addOrder(Order.asc("ornamentName"));
			ornamentList = (List<Ornament>) criteria.list();
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
		return ornamentList;
	}
	
	@Override
	public List<Ornament> getOrnamentListByMetalCode(Integer licenceId,String metalCode) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		List<Ornament> ornamentList = null;
		try{
			session = factory.openSession();
			tx = session.beginTransaction();
			
			Criteria criteria = session.createCriteria(Ornament.class);
			
			criteria.createAlias("metalId", "metalId");
		    criteria.add(Restrictions.eq("metalId.metalCode", metalCode));
		    
			criteria.add(Restrictions.eq("deletedFlag", ConstantUtils.CONSTANT_N));
			criteria.add(Restrictions.eq("licenceId", licenceId));
			criteria.addOrder(Order.asc("ornamentName"));
			ornamentList = (List<Ornament>) criteria.list();
			//ornamentList = (List<Ornament>) criteria.uniqueResult();
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
		return ornamentList;
	}

	@Override
	public Ornament getOrnament(Integer ornamentId) {
		// TODO Auto-generated method stub
		Ornament ornament = null;
		Session session = null;
		try{
			session = factory.openSession();
			
			ornament = (Ornament)session.get(Ornament.class, ornamentId);
			
            
            
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try{
				session.close();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return ornament;
	}

	@Override
	public Integer saveOrUpdateEntryType(EntryType entryType) {
		// TODO Auto-generated method stub
		Session session = null;
		Integer entryTypeId = null;
		try{
			session = factory.openSession();
			
			Transaction t = session.beginTransaction();   
		        
		    session.saveOrUpdate(entryType);
		    entryType = (EntryType)session.merge(entryType);
		    
		    t.commit();  
		    System.out.println("successfully saved");    
		    
		    if(entryType!=null){
		    	entryTypeId = entryType.getEntryTypeId();
		    	
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
		return entryTypeId;
	}

	@Override
	public List<EntryType> getEntryTypeList() {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		List<EntryType> entryTypeList = null;
		try{
			session = factory.openSession();
			tx = session.beginTransaction();
			
			Criteria criteria = session.createCriteria(EntryType.class);
			criteria.add(Restrictions.eq("deletedFlag", ConstantUtils.CONSTANT_N));
             
			entryTypeList = (List<EntryType>) criteria.list();
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
		return entryTypeList;
	}

	@Override
	public EntryType getEntryType(String entryType) {
		// TODO Auto-generated method stub
		EntryType entryTypeObj = null;
		List<EntryType> entryTypeList = null;
		
		Session session = null;
		try{
			session = factory.openSession();
			
			Criteria criteria = session.createCriteria(EntryType.class);
			criteria.add(Restrictions.eq("entryType", entryType));
             
			entryTypeList = (List<EntryType>) criteria.list();
            
            if(entryTypeList!=null && entryTypeList.size()>0){
            	entryTypeObj = entryTypeList.get(0);
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
		return entryTypeObj;
	}

	@Override
	public List<LicenceOpeningStockDetailsDTO> getLicenceOpeningStockDetailsList(String entryTypeCode,Integer licenceId) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		List<Ornament> ornamentList = null;
		List<LicenceOpeningStockDetails> lornamentList = null;
		List<LicenceOpeningStockDetailsDTO> licenceOrnamentListDTO = null;
		try{
			session = factory.openSession();
			tx = session.beginTransaction();
			
//			Criteria criteria = session.createCriteria(Ornament.class,"ornament");
//			criteria.createAlias("ornament.licenceOpeningStockDetailsList", "licenceStock");
//			
//			criteria.add(Restrictions.eq("ornament.deletedFlag", ConstantUtils.CONSTANT_N));
//			criteria.add(Restrictions.eq("licenceStock.deletedFlag", ConstantUtils.CONSTANT_N));
//			
//			criteria.addOrder(Order.asc("ornament.ornamentName"));
//			ornamentList = (List<Ornament>) criteria.list();
//          tx.commit();
            
            
			Criteria criteria = session.createCriteria(Ornament.class,"ornament");
			criteria.createAlias("ornament.licenceOpeningStockDetailsList", "licenceStock");
			criteria.setFetchMode("issues.issuetracker", FetchMode.JOIN);
			criteria.add(Restrictions.eq("ornament.deletedFlag", ConstantUtils.CONSTANT_N));
			criteria.addOrder(Order.asc("ornament.ornamentName"));
			//ornamentList = (List<Ornament>) criteria.list();
			
//            Criteria c1 = session.createCriteria(Child.class);
//            c1.add(Restrictions.in("property", properties));
//            c1.setProjection( Projections.distinct( Projections.property( "parentId" ) ) );
//            List<Integer> parentIds = c1.list();
//
//            Criteria c2 = session.createCriteria(Parent.class);
//            c2.createAlias("children", "children");
//            c2.add(Restrictions.in("id", parentIds));
//            return c2.list();
            
				StringBuffer sqlQuery = new StringBuffer();
				/*sqlQuery.append("	");
				sqlQuery.append("	SELECT ORNAMENT.ORNAMENT_ID,ORNAMENT.ORNAMENT_NAME,ORNAMENT.ORNAMENT_KEY_CODE,	");
				//sqlQuery.append("	LICENCE_OPENING.LICENCE_OPENING_STOCK_DETAILS_ID,LICENCE_OPENING.GRAMS 	");
				sqlQuery.append("	CASE WHEN ENTRY.ENTRY_TYPE IS NULL THEN NULL	");
				sqlQuery.append("	WHEN ENTRY.ENTRY_TYPE = 'GST' THEN LICENCE_OPENING.GRAMS	");
				sqlQuery.append("	END GST_GRAMS,	");
				sqlQuery.append("	CASE WHEN ENTRY.ENTRY_TYPE IS NULL THEN NULL	");
				sqlQuery.append("	WHEN ENTRY.ENTRY_TYPE = 'GST' THEN LICENCE_OPENING.LICENCE_OPENING_STOCK_DETAILS_ID	");
				sqlQuery.append("	END GST_OPENING_STOCK_DETAILS_ID,	");
				sqlQuery.append("	CASE WHEN ENTRY.ENTRY_TYPE IS NULL THEN NULL	");
				sqlQuery.append("	WHEN ENTRY.ENTRY_TYPE = 'NON_GST' THEN LICENCE_OPENING.GRAMS	");
				sqlQuery.append("	END NONGST_GRAMS,	");
				sqlQuery.append("	CASE WHEN ENTRY.ENTRY_TYPE IS NULL THEN NULL	");
				sqlQuery.append("	WHEN ENTRY.ENTRY_TYPE = 'NON_GST' THEN LICENCE_OPENING.LICENCE_OPENING_STOCK_DETAILS_ID	");
				sqlQuery.append("	END NONGST_OPENING_STOCK_DETAILS_ID	");
				sqlQuery.append("	FROM ORNAMENT ORNAMENT LEFT JOIN  LICENCE_OPENING_STOCK_DETAILS LICENCE_OPENING	");
				sqlQuery.append("	ON LICENCE_OPENING.ORNAMENT_ID = ORNAMENT.ORNAMENT_ID	");
				sqlQuery.append("	LEFT JOIN ENTRY_TYPE ENTRY ON LICENCE_OPENING.ENTRY_TYPE_ID = ENTRY.ENTRY_TYPE_ID	");
				sqlQuery.append("	WHERE 0=0	");
				sqlQuery.append("	and (LICENCE_OPENING.DELETED_FLAG IS NULL OR LICENCE_OPENING.DELETED_FLAG= :deletedFlag1)	");
				sqlQuery.append("	AND (LICENCE_OPENING.LICENCE_ID IS NULL OR LICENCE_OPENING.LICENCE_ID= :licenceId1)	");
				sqlQuery.append("	AND ORNAMENT.DELETED_FLAG= :deletedFlag2 AND ORNAMENT.LICENCE_ID= :licenceId2	");
				sqlQuery.append("	ORDER BY ORNAMENT.ORNAMENT_NAME ASC	");
				
				SQLQuery query = session.createSQLQuery(sqlQuery.toString());
				System.out.println("getLicenceOpeningStockDetailsList sqlQuery - "+sqlQuery);
				query.setParameter("deletedFlag1", ConstantUtils.CONSTANT_N);
				query.setParameter("licenceId1", licenceId);
				query.setParameter("deletedFlag2", ConstantUtils.CONSTANT_N);
				query.setParameter("licenceId2", licenceId);
				List<Object[]> results = query.list();*/
				
				
				sqlQuery.append("	SELECT ORNAMENT.ORNAMENT_ID,ORNAMENT.ORNAMENT_NAME,ORNAMENT.ORNAMENT_KEY_CODE,	");
				sqlQuery.append("	LICENCE_OPENING.LICENCE_OPENING_STOCK_DETAILS_ID,	");
				sqlQuery.append("	LICENCE_OPENING.GST_GRAMS,LICENCE_OPENING.NONGST_GRAMS	");
				sqlQuery.append("	FROM ORNAMENT ORNAMENT LEFT OUTER JOIN  LICENCE_OPENING_STOCK_DETAILS LICENCE_OPENING	");
				sqlQuery.append("	ON LICENCE_OPENING.ORNAMENT_ID = ORNAMENT.ORNAMENT_ID	");
				sqlQuery.append("	WHERE 0=0	");
				sqlQuery.append("	and (LICENCE_OPENING.DELETED_FLAG IS NULL OR LICENCE_OPENING.DELETED_FLAG= :deletedFlag1)	");
				sqlQuery.append("	AND (LICENCE_OPENING.LICENCE_ID IS NULL OR LICENCE_OPENING.LICENCE_ID= :licenceId1)	");
				sqlQuery.append("		AND ORNAMENT.DELETED_FLAG= :deletedFlag2 AND ORNAMENT.LICENCE_ID= :licenceId2	");
				
				SQLQuery query = session.createSQLQuery(sqlQuery.toString());
				query.setParameter("deletedFlag1", ConstantUtils.CONSTANT_N);
				query.setParameter("licenceId1", licenceId);
				query.setParameter("deletedFlag2", ConstantUtils.CONSTANT_N);
				query.setParameter("licenceId2", licenceId);
				
				List<Object[]> results = query.list();
				
				if(results!=null){
					System.out.println(results.size());
					licenceOrnamentListDTO = new ArrayList<LicenceOpeningStockDetailsDTO>();
					for(Object[] obj:results){
						int i=-1;
						
						LicenceOpeningStockDetailsDTO dto = new LicenceOpeningStockDetailsDTO();
						
						if(obj[++i]!=null){
							dto.setOrnamentId((Integer)obj[i]);
						}
						if(obj[++i]!=null){
							dto.setOrnamentName((String)obj[i]);
						}
						
						if(obj[++i]!=null){
							dto.setOrnamentKeyCode((String)obj[i]);
						}
						
						if(obj[++i]!=null){
							//dto.setLicenceOpeningStockDetailsId(((BigInteger)obj[i]).intValue());
							dto.setLicenceOpeningStockDetailsId(((Integer)obj[i]));
						}
						
						if(obj[++i]!=null){
							dto.setGstGrams((Double)obj[i]);
						}
						
						if(obj[++i]!=null){
							dto.setNonGstGrams((Double)obj[i]);
						}
						
						
						licenceOrnamentListDTO.add(dto);
						
					}
				}
				
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
		return licenceOrnamentListDTO;
	}
	
	@Override
	public Integer saveOrUpdateLicenceOpeningStockDetails(LicenceOpeningStockDetails licenceOpeningStockDetails) {
		// TODO Auto-generated method stub
		Session session = null;
		Integer liceneOpeningStockDetailsId = null;
		try{
			session = factory.openSession();
			
			Transaction t = session.beginTransaction();   
		        
		    session.saveOrUpdate(licenceOpeningStockDetails);
		    licenceOpeningStockDetails = (LicenceOpeningStockDetails)session.merge(licenceOpeningStockDetails);
		    
 		    t.commit();  
		    System.out.println("successfully saved");    
		    
		    if(licenceOpeningStockDetails!=null){
		    	liceneOpeningStockDetailsId = licenceOpeningStockDetails.getLicenceOpeningStockDetailsId();
		    	
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
		return liceneOpeningStockDetailsId;
	}

	@Override
	public List<LicenceOpeningStockDetails> getLicenceOpeningStockDetailsList() {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		List<LicenceOpeningStockDetails> licenceOpeningStockList = null;
		try{
			session = factory.openSession();
			tx = session.beginTransaction();
			
			Criteria criteria = session.createCriteria(LicenceOpeningStockDetails.class);
			criteria.add(Restrictions.eq("deletedFlag", ConstantUtils.CONSTANT_N));
             
			licenceOpeningStockList = (List<LicenceOpeningStockDetails>) criteria.list();
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
		return licenceOpeningStockList;
	}

	@Override
	public LicenceOpeningStockDetails getLicenceOpeningStockDetails(Integer licenceOpeningStockDetailsId) {
		// TODO Auto-generated method stub
		LicenceOpeningStockDetails licenceOpeningStockDetailsObj = null;
		List<LicenceOpeningStockDetails> licenceOpeningStockDetailsList = null;
		
		Session session = null;
		try{
			session = factory.openSession();
			
			Criteria criteria = session.createCriteria(LicenceOpeningStockDetails.class);
			criteria.add(Restrictions.eq("licenceOpeningStockDetailsId", licenceOpeningStockDetailsId));
             
			licenceOpeningStockDetailsList = (List<LicenceOpeningStockDetails>) criteria.list();
            
            if(licenceOpeningStockDetailsList!=null && licenceOpeningStockDetailsList.size()>0){
            	licenceOpeningStockDetailsObj = licenceOpeningStockDetailsList.get(0);
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
		return licenceOpeningStockDetailsObj;
	}
	
	@Override
	public ContactType getContactType(Integer contactTypeId) {
		// TODO Auto-generated method stub
		ContactType contactType = null;
		Session session = null;
		try{
			session = factory.openSession();
			
			contactType = (ContactType)session.get(ContactType.class, contactTypeId);
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try{
				session.close();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return contactType;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ContactType> getContactTypeList() {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		List<ContactType> contactType = null;
		try{
			session = factory.openSession();
			tx = session.beginTransaction();
			
			Criteria criteria = session.createCriteria(ContactType.class);
			criteria.add(Restrictions.eq("deletedFlag", ConstantUtils.CONSTANT_N));
			criteria.addOrder(Order.asc("contactTypeName"));
			contactType = (List<ContactType>) criteria.list();
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
		return contactType;
	}

	@Override
	public Contact saveOrUpdateContact(Contact contact) {
		// TODO Auto-generated method stub
		Session session = null;
		Contact contactObj = null;
		try{
			session = factory.openSession();
			
			Transaction t = session.beginTransaction();   
		        
		    session.saveOrUpdate(contact);
		    contactObj = (Contact)session.merge(contact);
		    
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
		return contactObj;
	}

	@Override
	public Contact getContact(Integer contactId) {
		// TODO Auto-generated method stub
		Contact contact = null;
		
		
		Session session = null;
		try{
			session = factory.openSession();
			
			contact = (Contact)session.get(Contact.class, contactId);
			
			if(contact!=null && contact.getOpeningBalanceDetailsListDB()!=null && contact.getOpeningBalanceDetailsListDB().size()>0) {
				List<OpeningBalanceDetailsContact> balance = new ArrayList<OpeningBalanceDetailsContact>();
				for(OpeningBalanceDetailsContact obj : contact.getOpeningBalanceDetailsListDB()) {
					balance.add(obj);
				}
				
				contact.setOpeningBalanceDetailsList(balance);
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
		return contact;
	}

	@Override
	public List<Contact> getContactList(String contactType) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		List<Contact> contactList = null;
		try{
			session = factory.openSession();
			tx = session.beginTransaction();
			
			Criteria criteria = session.createCriteria(Contact.class);
			criteria.createAlias("contactType", "contactType");
		    criteria.add(Restrictions.eq("contactType.contactTypeName", contactType));
		    
			criteria.add(Restrictions.eq("deletedFlag", ConstantUtils.CONSTANT_N));
			criteria.addOrder(Order.desc("isEnabled"));
			criteria.addOrder(Order.asc("contactName"));
			
			
			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			
            contactList = (List<Contact>) criteria.list();
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
		return contactList;
	}

	@Override
	public List<Contact> getEnabledContactList(String contactType, String enableFlag) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		List<Contact> contactList = null;
		try{
			session = factory.openSession();
			tx = session.beginTransaction();
			
			Criteria criteria = session.createCriteria(Contact.class);
			criteria.createAlias("contactType", "contactType");
		    criteria.add(Restrictions.eq("contactType.contactTypeName", contactType));
		    
			criteria.add(Restrictions.eq("deletedFlag", ConstantUtils.CONSTANT_N));
			criteria.add(Restrictions.eq("isEnabled", enableFlag));
			criteria.addOrder(Order.desc("isEnabled"));
			criteria.addOrder(Order.asc("contactName"));
			
			
			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			
            contactList = (List<Contact>) criteria.list();
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
		return contactList;
	}
	
	
	@Override
	public BillSource saveOrUpdateBillSource(BillSource billSource) {
		// TODO Auto-generated method stub
		Session session = null;
		BillSource billSourceObj = null;
		try{
			session = factory.openSession();
			
			Transaction t = session.beginTransaction();   
		        
		    session.saveOrUpdate(billSource);
		    billSource = (BillSource)session.merge(billSource);
		    
		    t.commit();  
		    System.out.println("successfully saved");    
		    
		    if(billSource!=null){
		    	billSourceObj = billSource;
		    	
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
		return billSourceObj;
	}

	@Override
	public List<BillSource> getBillSourceList() {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		List<BillSource> billSourceList = null;
		try{
			session = factory.openSession();
			tx = session.beginTransaction();
			
			Criteria criteria = session.createCriteria(BillSource.class);
			criteria.add(Restrictions.eq("deletedFlag", ConstantUtils.CONSTANT_N));
             
            billSourceList = (List<BillSource>) criteria.list();
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
		return billSourceList;
	}

	@Override
	public BillSource getBillSource(Integer billSourceId) {
		// TODO Auto-generated method stub
		BillSource billSource = null;
		Session session = null;

		try{
			session = factory.openSession();
			billSource = (BillSource)session.get(BillSource.class, billSourceId);
            
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try{
				session.close();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return billSource;
	}
	
	@Override
	public BillSource getBillSourceByCode(String billSourceCode) {
		// TODO Auto-generated method stub
		BillSource billSource = null;
		List<BillSource> billSourceList = null;
		
		Session session = null;
		try{
			session = factory.openSession();
			
			//metal = (Metal)session.get(Metal.class, metalId);
			
			Criteria criteria = session.createCriteria(BillSource.class);
			criteria.add(Restrictions.eq("billSourceName", billSourceCode));
             
			billSourceList = (List<BillSource>) criteria.list();
            
            if(billSourceList!=null && billSourceList.size()>0){
            	billSource = billSourceList.get(0);
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
		return billSource;
	}
	
	
	@Override
	public Integer saveOrUpdateBillType(BillType billType) {
		// TODO Auto-generated method stub
		Session session = null;
		Integer billTypeId = null;
		try{
			session = factory.openSession();
			
			Transaction t = session.beginTransaction();   
		        
		    session.saveOrUpdate(billType);
		    billType = (BillType)session.merge(billType);
		    
		    t.commit();  
		    System.out.println("successfully saved");    
		    
		    if(billType!=null){
		    	billTypeId = billType.getBillTypeId();
		    	
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
		return billTypeId;
	}

	@Override
	public List<BillType> getBillTypeList() {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		List<BillType> billTypeList = null;
		try{
			session = factory.openSession();
			tx = session.beginTransaction();
			
			Criteria criteria = session.createCriteria(BillType.class);
			criteria.add(Restrictions.eq("deletedFlag", ConstantUtils.CONSTANT_N));
             
			billTypeList = (List<BillType>) criteria.list();
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
		return billTypeList;
	}

	@Override
	public BillType getBillType(String billType) {
		// TODO Auto-generated method stub
		BillType billTypeObj = null;
		List<BillType> billTypeList = null;
		
		Session session = null;
		try{
			session = factory.openSession();
			
			Criteria criteria = session.createCriteria(BillType.class);
			criteria.add(Restrictions.eq("billTypeName", billType));
             
			billTypeList = (List<BillType>) criteria.list();
            
            if(billTypeList!=null && billTypeList.size()>0){
            	billTypeObj = billTypeList.get(0);
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
		return billTypeObj;
	}

	@Override
	public List<OpeningStockDetailsLicenceDTO> getOpeningStockDetailsLicenceDTOList(Integer licenceId,
			String billTypeName) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		List<OpeningStockDetailsLicenceDTO> openingStockDetailsLicenceDTOList = null;
		try{
			session = factory.openSession();
			tx = session.beginTransaction();

			String gstBillType=ConstantUtils.GST_ENTRY;
			String nongstBillType=ConstantUtils.NON_GST_ENTRY;
			
			StringBuffer sqlQuery = new StringBuffer();
			
			sqlQuery.append("")
			.append("	SELECT ORN.ORNAMENT_ID,ORN.METAL_ID,ORN.ORNAMENT_NAME,ORN.ORNAMENT_KEY_CODE,ORN.GST_PERCENTAGE,ORN.PURITY,ORN.MC_RATE,ORN.MC_PER_PIECE,	")
			.append("	GST_STOCK.GST_STOCK_ID GST_STOCK_ID,GST_STOCK.GST_GRAMS GST_GRAMS,	")
			.append("	NONGST_STOCK.NON_GST_STOCK_ID NON_GST_STOCK_ID,NONGST_STOCK.NONGST_GRAMS NONGST_GRAMS	")
			.append("	FROM ORNAMENT ORN	")
			.append("	LEFT JOIN(	")
			.append("	SELECT ORN.ORNAMENT_ID,	")
			.append("	OPENING.OPENING_STOCK_DETAILS_LICENCE_ID GST_STOCK_ID,OPENING.GRAMS GST_GRAMS	")
			.append("	FROM ORNAMENT ORN	")
			.append("	LEFT OUTER JOIN OPENING_STOCK_DETAILS_LICENCE OPENING	")
			.append("	ON ORN.ORNAMENT_ID = OPENING.ORNAMENT_ID	")
			.append("	LEFT JOIN BILL_TYPE BT	")
			.append("	ON OPENING.BILL_TYPE_ID = BT.BILL_TYPE_ID	")
			.append("	WHERE 0=0	")
			.append("	AND ORN.DELETED_FLAG= :deletedFlag	")
			.append("	AND ORN.LICENCE_ID = :licenceId	")
			.append("	AND OPENING.DELETED_FLAG= :deletedFlag	")
			.append("	AND OPENING.LICENCE_ID= :licenceId	")
			.append("	AND BT.BILL_TYPE_NAME = :gstBillType	")
			.append("	)GST_STOCK	")
			.append("	ON ORN.ORNAMENT_ID = GST_STOCK.ORNAMENT_ID	")
			.append("	LEFT JOIN(	")
			.append("	SELECT ORN.ORNAMENT_ID,	")
			.append("	OPENING.OPENING_STOCK_DETAILS_LICENCE_ID NON_GST_STOCK_ID,OPENING.GRAMS NONGST_GRAMS	")
			.append("	FROM ORNAMENT ORN	")
			.append("	LEFT OUTER JOIN OPENING_STOCK_DETAILS_LICENCE OPENING	")
			.append("	ON ORN.ORNAMENT_ID = OPENING.ORNAMENT_ID	")
			.append("	LEFT JOIN BILL_TYPE BT	")
			.append("	ON OPENING.BILL_TYPE_ID = BT.BILL_TYPE_ID	")
			.append("	WHERE 0=0	")
			.append("	AND ORN.DELETED_FLAG= :deletedFlag	")
			.append("	AND ORN.LICENCE_ID = :licenceId	")
			.append("	AND OPENING.DELETED_FLAG= :deletedFlag	")
			.append("	AND OPENING.LICENCE_ID= :licenceId	")
			.append("	AND BT.BILL_TYPE_NAME = :nongstBillType	")
			.append("	)NONGST_STOCK	")
			.append("	ON ORN.ORNAMENT_ID = NONGST_STOCK.ORNAMENT_ID	")
			.append("	WHERE 0=0	")
			.append("	AND ORN.DELETED_FLAG= :deletedFlag	")
			.append("	AND ORN.LICENCE_ID = :licenceId	")
			;
			
			//sqlQuery.append("select OPENING.OPENING_STOCK_DETAILS_LICENCE_ID from( select OPENING.OPENING_STOCK_DETAILS_LICENCE_ID,OPENING.GRAMS from OPENING_STOCK_DETAILS_LICENCE OPENING) OPENING ");
			
			/*sqlQuery.append("	SELECT 	")//ORN.ORNAMENT_ID,
			.append("	OPENING.OPENING_STOCK_DETAILS_LICENCE_ID,OPENING.GRAMS,ORN.ORNAMENT_ID	")
			.append("	FROM ORNAMENT ORN	")
			.append("	LEFT OUTER JOIN OPENING_STOCK_DETAILS_LICENCE OPENING	")
			.append("	ON ORN.ORNAMENT_ID = OPENING.ORNAMENT_ID	")
			.append("	LEFT JOIN BILL_TYPE BT	")
			.append("	ON OPENING.BILL_TYPE_ID = BT.BILL_TYPE_ID	")
			.append("	WHERE 0=0	")
			.append("	AND ORN.DELETED_FLAG= :deletedFlag	")
			.append("	AND ORN.LICENCE_ID = :licenceId	")
			.append("	AND OPENING.DELETED_FLAG= :deletedFlag	")
			.append("	AND OPENING.LICENCE_ID= :licenceId	")
			.append("	AND BT.BILL_TYPE_NAME = :gstBillType	")
			
			;
			*/
			
			System.out.println("Query : "+sqlQuery);
			SQLQuery query = session.createSQLQuery(sqlQuery.toString());
			query.setParameter("deletedFlag", ConstantUtils.CONSTANT_N);
			query.setParameter("licenceId", licenceId);
			query.setParameter("gstBillType", gstBillType);
			query.setParameter("nongstBillType", nongstBillType);
			System.out.println("Query Object : "+query);
			List<Object[]> results = query.list();
			
			if(results!=null){
				System.out.println(results.size());
				openingStockDetailsLicenceDTOList = new ArrayList<OpeningStockDetailsLicenceDTO>();
				for(Object[] obj:results){
					int i=-1;
					
					OpeningStockDetailsLicenceDTO dto = new OpeningStockDetailsLicenceDTO();
					
					if(obj[++i]!=null){
						dto.setOrnamentId((Integer)obj[i]);
					}
					if(obj[++i]!=null){
						dto.setMetalId((Integer)obj[i]);
					}
					if(obj[++i]!=null){
						dto.setOrnamentName((String)obj[i]);
					}
					if(obj[++i]!=null){
						dto.setOrnamentCode((String)obj[i]);
					}
					if(obj[++i]!=null){
						dto.setGstPercentage((Double)obj[i]);
					}
					if(obj[++i]!=null){
						dto.setPurity((Double)obj[i]);
					}
					if(obj[++i]!=null){
						dto.setMcRate((Double)obj[i]);
					}
					if(obj[++i]!=null){
						dto.setMcPerPiece((Double)obj[i]);
					}
					if(obj[++i]!=null){
						dto.setGstStockId((Integer)obj[i]);
					}
					if(obj[++i]!=null){
						dto.setGstGrams((Double)obj[i]);
					}
					if(obj[++i]!=null){
						dto.setNongstStockId((Integer)obj[i]);
					}
					if(obj[++i]!=null){
						dto.setNongstGrams((Double)obj[i]);
					}

					openingStockDetailsLicenceDTOList.add(dto);
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
		return openingStockDetailsLicenceDTOList;
	}

	@Override
	public OpeningStockDetailsLicence saveOrUpdateOpeningStockDetailsLicence(
			OpeningStockDetailsLicence openingStockDetailsLicence) {
		// TODO Auto-generated method stub
		Session session = null;
		OpeningStockDetailsLicence openingStockDetailsLicenceObj = null;
		try{
			session = factory.openSession();
			
			Transaction t = session.beginTransaction();   
		        
		    session.saveOrUpdate(openingStockDetailsLicence);
		    openingStockDetailsLicenceObj = (OpeningStockDetailsLicence)session.merge(openingStockDetailsLicence);
		    
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
		return openingStockDetailsLicenceObj;
	}

	@Override
	public List<OpeningStockDetailsLicence> getOpeningStockDetailsLicenceList() {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		List<OpeningStockDetailsLicence> openingStockDetailsLicenceList = null;
		try{
			session = factory.openSession();
			tx = session.beginTransaction();
			
			Criteria criteria = session.createCriteria(OpeningStockDetailsLicence.class);
			criteria.add(Restrictions.eq("deletedFlag", ConstantUtils.CONSTANT_N));
             
			openingStockDetailsLicenceList = (List<OpeningStockDetailsLicence>) criteria.list();
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
		return openingStockDetailsLicenceList;
	}

	@Override
	public OpeningStockDetailsLicence getOpeningStockDetailsLicence(Integer openingStockDetailsLicenceId) {
		// TODO Auto-generated method stub
		OpeningStockDetailsLicence openingStockDetailsLicenceObj = null;
		List<OpeningStockDetailsLicence> openingStockDetailsLicenceList = null;
		
		Session session = null;
		try{
			session = factory.openSession();
			
			Criteria criteria = session.createCriteria(OpeningStockDetailsLicence.class);
			criteria.add(Restrictions.eq("openingStockDetailsLicenceId", openingStockDetailsLicenceId));
             
			openingStockDetailsLicenceList = (List<OpeningStockDetailsLicence>) criteria.list();
            
            if(openingStockDetailsLicenceList!=null && openingStockDetailsLicenceList.size()>0){
            	openingStockDetailsLicenceObj = openingStockDetailsLicenceList.get(0);
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
		return openingStockDetailsLicenceObj;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerDetails> getCustomerDetailsList() {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		List<CustomerDetails> customerDetailsList = null;
		try{
			session = factory.openSession();
			tx = session.beginTransaction();
			
			Criteria criteria = session.createCriteria(CustomerDetails.class);
		    
			//criteria.add(Restrictions.eq("deletedFlag", ConstantUtils.CONSTANT_N));
			//criteria.addOrder(Order.desc("isEnabled"));
			criteria.addOrder(Order.asc("id"));
			
			
			//criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			
			customerDetailsList = (List<CustomerDetails>) criteria.list();
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
		return customerDetailsList;
	}

	@Override
	public List<CoverWeight> coverWeightList() {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		List<CoverWeight> coverWeightList = null;
		try{
			session = factory.openSession();
			tx = session.beginTransaction();
			
			Criteria criteria = session.createCriteria(CoverWeight.class);
			criteria.add(Restrictions.eq("deletedFlag", ConstantUtils.CONSTANT_N));
             
			coverWeightList = (List<CoverWeight>) criteria.list();
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
		return coverWeightList;
	}

	@Override
	public List<BillResetMaster> getBillResetMaster() {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		List<BillResetMaster> billResetMasterList = null;
		try{
			session = factory.openSession();
			tx = session.beginTransaction();
			
			Criteria criteria = session.createCriteria(BillResetMaster.class);
			criteria.add(Restrictions.eq("deletedFlag", ConstantUtils.CONSTANT_N));
             
			billResetMasterList = (List<BillResetMaster>) criteria.list();
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
		return billResetMasterList;
	}

	@Override
	public List<DayMaster> getDayMasterList() {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		List<DayMaster> dayMasterList = null;
		try{
			session = factory.openSession();
			tx = session.beginTransaction();
			
			Criteria criteria = session.createCriteria(DayMaster.class);
			criteria.add(Restrictions.eq("deletedFlag", ConstantUtils.CONSTANT_N));
             
			dayMasterList = (List<DayMaster>) criteria.list();
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
		return dayMasterList;
	}

	@Override
	public List<WeekMaster> getWeekMasterList() {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		List<WeekMaster> weekMasterList = null;
		try{
			session = factory.openSession();
			tx = session.beginTransaction();
			
			Criteria criteria = session.createCriteria(WeekMaster.class);
			criteria.add(Restrictions.eq("deletedFlag", ConstantUtils.CONSTANT_N));
             
			weekMasterList = (List<WeekMaster>) criteria.list();
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
		return weekMasterList;
	}

	@Override
	public List<MonthMaster> getMonthMasterList() {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		List<MonthMaster> monthMasterList = null;
		try{
			session = factory.openSession();
			tx = session.beginTransaction();
			
			Criteria criteria = session.createCriteria(MonthMaster.class);
			criteria.add(Restrictions.eq("deletedFlag", ConstantUtils.CONSTANT_N));
             
			monthMasterList = (List<MonthMaster>) criteria.list();
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
		return monthMasterList;
	}
	
}


