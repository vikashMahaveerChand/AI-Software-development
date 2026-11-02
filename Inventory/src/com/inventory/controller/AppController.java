package com.inventory.controller;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.inventory.model.Licence;
import com.inventory.model.User;
import com.inventory.utility.ConstantUtils;
import com.inventory.utility.Session;
import com.inventory.view.BackupForm;
import com.inventory.view.BalanceSheet;
import com.inventory.view.BankEntryForm;
import com.inventory.view.BankForm;
import com.inventory.view.BankReport;
import com.inventory.view.BillNumberConfigureForm;
import com.inventory.view.BillingForm;
import com.inventory.view.BillingReport;
import com.inventory.view.CashReport;
import com.inventory.view.ContactForm;
import com.inventory.view.ContactReport;
import com.inventory.view.FooterPanel;
import com.inventory.view.HandLoanDashboard;
import com.inventory.view.HeaderPanel;
import com.inventory.view.LicenceForm;
import com.inventory.view.LicenceReport;
import com.inventory.view.LoginPanel;
import com.inventory.view.OrnamentForm;
import com.inventory.view.OrnamentReport;
import com.inventory.view.PawnAuctionForm;
import com.inventory.view.PawnBillingForm;
import com.inventory.view.PawnBillingReport;
import com.inventory.view.PawnCustomerBillDetails;
import com.inventory.view.PawnDailyReport;
import com.inventory.view.PawnDeliveryForm;
import com.inventory.view.PaymentForm;
import com.inventory.view.ReturnItemReport;
import com.inventory.view.StockReport;
import com.inventory.view.UserForm;
import com.inventory.view.UserReport;

public class AppController {
	public JFrame frame;
    public JPanel header,center,footer;
    
    private static AppController myInstance;

    private AppController(){

    }
    public static AppController getInstance(){
        
        if(myInstance==null){
            myInstance =new AppController();
        }
        
        return myInstance;
    }
    
    public void start(){
        initcomponent();
    }
    
    public void initcomponent(){
    	try{
    		frame=new JFrame();
            frame.setLayout(new BorderLayout());
            //frame.setLayout(new MigLayout());
            
            header=new JPanel(new BorderLayout());
            center=new JPanel(new BorderLayout());
            footer=new JPanel(new BorderLayout());
            
            frame.setExtendedState(Frame.MAXIMIZED_BOTH);
            //frame.setSize(Toolkit.getDefaultToolkit().getScreenSize().width,Toolkit.getDefaultToolkit().getScreenSize().height);
            frame.setLocationRelativeTo(null);
            frame.setTitle("Dilkush Software - 994-133-6844");
            ImageIcon img = new ImageIcon("image/Logo.jpg");
            frame.setIconImage(img.getImage());
            
            frame.add(header,BorderLayout.NORTH);
            frame.add(center,BorderLayout.CENTER);
            frame.add(footer,BorderLayout.SOUTH);
            
            /*frame.add(header,"pushx,growx,pushy,growy,wrap");
            frame.add(center,"center,pushx,growx,pushy,growy,wrap");
            frame.add(footer,"pushx,growx,pushy,growy,wrap");*/
            
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
            addHeaderPanel(ConstantUtils.DEFAULT,ConstantUtils.DEFAULT);
            addLoginPage();
            addFooterPanel();
            //frame.setSize(Toolkit.getDefaultToolkit().getScreenSize().width,Toolkit.getDefaultToolkit().getScreenSize().height);
            //frame.pack();
    	}catch(Exception ex){
    		ex.printStackTrace();
    	}
    	
    }
    
    public void addToHeaderPanel(JPanel content){
        
        header.removeAll();
        header.add(content);
        header.revalidate();
        header.repaint();
        //page.asComponent().requestFocus();
    
    }
    public void addToFooterPanel(JPanel content){
        
    	footer.removeAll();
    	footer.add(content);
    	footer.revalidate();
    	footer.repaint();
        //page.asComponent().requestFocus();
    
    }
    public void addToCenterPanel(JPanel content){
    
        center.removeAll();
        center.add(content);
        center.revalidate();
        center.repaint();
    
    }
    
    public void addHeaderPanel(String licenceType,String userType){
        addToHeaderPanel(HeaderPanel.getInstance().getPanel(licenceType,userType));
    }
    
    public void addFooterPanel(){
    	addToFooterPanel(FooterPanel.getInstance().getPanel());
    }
    
    public void addLoginPage(){
    	List<Licence> LicenceList = MasterController.getInstace().getLicenceList();
    	List<User> userList = MasterController.getInstace().getUserList();
    	
    	if((LicenceList!=null && LicenceList.size()>0) && (userList !=null && userList.size()>0)){
    		addToCenterPanel(LoginPanel.getInstance().getPanel());
    	}else if(userList==null || userList.size()<=0){
    		addUserFormPage(ConstantUtils.LOGIN_PAGE,0,ConstantUtils.BLANK);
    	}else if(LicenceList==null || LicenceList.size()<=0){
    		addLicenceFormPage(ConstantUtils.LOGIN_PAGE,0,ConstantUtils.BLANK);
    	}
    }
    
    public void addLicenceFormPage(String calledFrom,Integer licenceId,String userType){
    	addToCenterPanel(LicenceForm.getInstance().getPanel(calledFrom, licenceId,userType));
    }
    public void addLicenceReportPage(String userType){
    	addToCenterPanel(LicenceReport.getInstance().getPanel(userType));
    }
    
    public void addUserFormPage(String calledFrom,Integer userId,String userType){
    	addToCenterPanel(UserForm.getInstance().getPanel(calledFrom, userId,userType));
    }
    public void addUserReportPage(String userType){
    	addToCenterPanel(UserReport.getInstance().getPanel(userType));
    }
    
    public void addOrnamentFormPage(String metalCode,Integer ornamentId,String calledFrom,String userType,Integer licenceId){
    	addToCenterPanel(OrnamentForm.getInstance().getPanel(metalCode, ornamentId, calledFrom, userType, licenceId));
    }
    public void addOrnamentReportPage(String userType,String calledFrom){
    	addToCenterPanel(OrnamentReport.getInstance().getPanel(userType,calledFrom));
    }
    public void addContactFormPage(Integer contactId,String contactTypeName,String calledFrom,Integer licenceId){
    	addToCenterPanel(ContactForm.getInstance().getPanel(contactId, contactTypeName, calledFrom, licenceId));
    }
    public void addContactReportPage(String contactType,String calledFrom){
    	addToCenterPanel(ContactReport.getInstance().getPanel(contactType,calledFrom));
    }
    public void addBillingFormPage(String calledFrom,Integer billId,Integer customerId,String billType,String metal,String billSource){
    	addToCenterPanel(BillingForm.getInstance().getPanel(calledFrom, billId, customerId, billType, metal, billSource));
    }
    public void addRetailPawnBillingFormPage(String calledFrom,Integer billId,Integer customerId,String billSource){
    	addToCenterPanel(PawnBillingForm.getInstance().getPanel(calledFrom, billId, customerId, billSource));
    }
    public void addBillingReportPage(String calledFrom,String billType,String metal,String billSource){
    	addToCenterPanel(BillingReport.getInstance().getPanel(calledFrom, billType, metal, billSource));
    }
    
    public void addStockReport(String calledFrom,String billType,String metal,String billSource) {
    	addToCenterPanel(StockReport.getInstance().getPanel(calledFrom, billType, metal, billSource));
    }
    public void addPaymentFormPage(String calledFrom, Integer customerId,String billType,String metal,String billSource){
    	addToCenterPanel(PaymentForm.getInstance().getPanel(calledFrom, customerId,billType, metal, billSource));
    }
    public void addPawnBillingReportPage(String calledFrom, String pawnBillSource){
    	addToCenterPanel(PawnBillingReport.getInstance().getPanel(calledFrom, pawnBillSource));
    }
    public void addRetailPawnDeliveryFormPage(String calledFrom,Integer billId,Integer customerId,String billSource){
    	addToCenterPanel(PawnDeliveryForm.getInstance().getPanel(calledFrom, billId, customerId, billSource));
    }
    public void addRetailPawnAuctionFormPage(String calledFrom,Integer billId,Integer customerId,String billSource){
    	addToCenterPanel(PawnAuctionForm.getInstance().getPanel(calledFrom, billId, customerId, billSource));
    }    
    public void addRetailPawnCustomerDetails(String calledFrom, Integer customerId,String billSource){
    	addToCenterPanel(PawnCustomerBillDetails.getInstance().getPanel(calledFrom, customerId, billSource));
    }
    public void addCashReport(String calledFrom,String billType,String metal,String billSource) {
    	addToCenterPanel(CashReport.getInstance().getPanel(calledFrom, billType, metal, billSource));
    }
    public void addBalanceReport(String calledFrom,String billType,String contactType) {
    	addToCenterPanel(BalanceSheet.getInstance().getPanel(calledFrom, billType, contactType));
    }
    public void addBankForm(String calledFrom,Integer bankId) {
    	addToCenterPanel(BankForm.getInstance().getPanel(calledFrom, bankId));
    }
    public void addBankEntryForm(String calledFrom,Integer bankId) {
    	addToCenterPanel(BankEntryForm.getInstance().getPanel(calledFrom, bankId));
    }
    public void addBankReport(String calledFrom,Integer bankId) {
    	addToCenterPanel(BankReport.getInstance().getPanel(calledFrom, bankId));
    }
    
    public void addReturnOrnamentReport(String calledFrom,String billType) {
    	addToCenterPanel(ReturnItemReport.getInstance().getPanel(calledFrom, billType));
    }
    
    public void addBackupForm() {
    	addToCenterPanel(BackupForm.getInstance().getPanel());
    }
    public void addBillNumberForm() {
    	addToCenterPanel(BillNumberConfigureForm.getInstance().getPanel());
    }
    public void addPawnDailyReport(String calledFrom,String pawnBillSource) {
    	addToCenterPanel(PawnDailyReport.getInstance().getPanel(calledFrom, pawnBillSource));
    }
    
    public void addHandloanDashboard() {
    	addToCenterPanel(HandLoanDashboard.getInstance().getPanel());
    }
    
    public void configureMenu(){
    	try{
    		User user = Session.user;
    		Licence licence = Session.licence;
    		String userType ="";
    		String licenceType ="";
    		String licenceName="";
    		
    		if(user!=null){
    			userType = user.getUserType().getUserType();
    		}

    		if(licence!=null){
    			licenceType = licence.getLicenceType().getLicenceTypeName();
    			licenceName = licence.getLicenceName();
    		}
    		addHeaderPanel(licenceType,userType);
    		frame.setTitle(licenceName + " - "+userType);
    		
    		
    		if(licenceType.equalsIgnoreCase(ConstantUtils.LICENCE_TYPE_PAWNBROKER) && userType.equalsIgnoreCase(ConstantUtils.USER_TYPE_ADMIN)) {
    			//AppController.getInstance().addContactFormPage(0, ConstantUtils.PAWNCUSTOMER_CONTACT_TYPE, ConstantUtils.MENU, Session.licence.getLicenceId());
    			AppController.getInstance().addRetailPawnBillingFormPage(ConstantUtils.MENU, 0, 0, ConstantUtils.PAWN_RETAIL);
    		}else if(licenceType.equalsIgnoreCase(ConstantUtils.HANDLOAN)) {
    			AppController.getInstance().addHandloanDashboard();
    		}
    		
    	}catch(Exception ex){
    		ex.printStackTrace();
    	}
    }

}
