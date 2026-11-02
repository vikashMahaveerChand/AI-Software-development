package com.inventory;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.inventory.controller.AppController;
import com.inventory.dao.MasterDAO;
import com.inventory.dao.MasterDAOImpl;
import com.inventory.model.Employee;
import com.inventory.model.EmployeeDetails;
import com.inventory.utility.HibernateUtil;

public class InventoryMain {
	
	/*static {
		//JOptionPane.showMessageDialog(null, "Static Load");
		System.out.println("Static Load");
		File file = new File("lib/opencv_java341.dll");
		//JOptionPane.showMessageDialog(null, "file.getAbsolutePath() "+file.getAbsolutePath());
		System.out.println("Abs path "+file.getAbsolutePath());
		try {
		System.load(file.getAbsolutePath());
		}catch(Exception ex) {
			JOptionPane.showMessageDialog(null, "Static Load Exception system.load => "+ex);
		}
		//JOptionPane.showMessageDialog(null, "Static Load Ends");
		//System.load(Core.NATIVE_LIBRARY_NAME);
	}*/

	static MasterDAO masterDAO = null;
	static AppController controller;
	
	private InventoryMain(){
		masterDAO = MasterDAOImpl.getInstace();
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Main");
		
		try{
			HibernateUtil.getSessionFactory();
			//System.out.println(org.hibernate.cfg.Environment.VERSION);
		    SwingUtilities.invokeLater(new Runnable() {
	            @Override
	            public void run() {
	                controller =AppController.getInstance();
	                //controller.setSplash(splash);
	                controller.start();
	            }
	        });
			
			//employeeSaveUpdate();
			/*SessionFactory factory = HibernateUtil.getSessionFactory();
			
			Session session = factory.openSession(); 
			Transaction t = session.beginTransaction();   
            
			LicenceType e1=new LicenceType();    
			e1.setLicenceTypeId(1);
		    e1.setDeletedFlag("N");
		    e1.setLicenceTypeName("SILVER_WHOLESALE");
		        
		    session.saveOrUpdate(e1);  
		    t.commit();  
		    System.out.println("successfully saved");    
		    session.close();*/    
			/*masterDAO = MasterDAOImpl.getInstace();
		    LicenceType lt = masterDAO.getLicenceType(1);
		    
		    if(lt!=null){
		    	System.out.println("lt Name: "+lt.getLicenceTypeName());
		    }
		    
		    LicenceType lt2 = masterDAO.getLicenceType(2);
		    if(lt2!=null){
		    	System.out.println("lt Name: "+lt2.getLicenceTypeName());
		    }
		    
		    Licence l1 = new Licence();
		    l1.setLicenceName("Silver Shop");
		    l1.setLicenceTypeId(lt);
		    Integer licenceId1 = masterDAO.saveOrUpdateLicence(l1);
		    System.out.println("licenceId1 - "+licenceId1);
		    
		    Licence l2 = new Licence();
		    l2.setLicenceName("Gold Shop");
		    l2.setLicenceTypeId(lt2);
		    Integer licenceId2 = masterDAO.saveOrUpdateLicence(l2);
		    System.out.println("licenceId2 - "+licenceId2);*/
		    
		}catch(Exception ex){
			System.out.println("Exception"+ex);
		}
	}
	
	public static void employeeSaveUpdate(){
		try{
			masterDAO = MasterDAOImpl.getInstace();
			Employee emp1 = new Employee();
			emp1.setEmployeeName("Vikash");
			
			EmployeeDetails empDetail1 = new EmployeeDetails();
			empDetail1.setPhoneNumber("9941336844");
			
			EmployeeDetails empDetail2 = new EmployeeDetails();
			empDetail2.setPhoneNumber("9941336833");
			
			//Set<EmployeeDetails> empList1 = new HashSet<EmployeeDetails>();
			List<EmployeeDetails> empList1 = new ArrayList<EmployeeDetails>();
			empList1.add(empDetail1);
			empList1.add(empDetail2);
			
			emp1.setEmpDetails(empList1);
			Integer id = masterDAO.saveOrUpdateEmployee(emp1);
			
			masterDAO.getEmployeeList();
			masterDAO.getEmployee(id);
			
			
		}catch(Exception ex){
			System.out.println("Exception"+ex);
		}
	}

}
