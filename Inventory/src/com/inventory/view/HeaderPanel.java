package com.inventory.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import com.inventory.components.MyLabel;
import com.inventory.components.MyPanel;
import com.inventory.controller.AppController;
import com.inventory.utility.ConstantUtils;
import com.inventory.utility.Session;

import net.miginfocom.swing.MigLayout;

public class HeaderPanel implements ActionListener,MouseListener{
	private static HeaderPanel myInstance=null;
    
    private HeaderPanel(){};
    
    public static HeaderPanel getInstance(){
        if(myInstance==null){
            myInstance = new HeaderPanel();
        }
        
        return myInstance;
    }
    
    JPanel panel;
    String licenceType;
    String userType;
    JMenuBar menuBar;
    JMenu retailBilling,wholesaleBilling,purchaseBilling,reports,payment,bank,master,handloan;
    
    //Pawnbroker Related Menu Starts
    JMenu pawnCustomerMenu,pawnBillingMenu,pawnReportMenu;
    JMenuItem pawnCustomer,pawnCustomerReport,pawnBill,pawnDelivery,pawnReports,pawnCustomerDetails,pawnAuction,pawnDailyReport;
    //Pawnbroker Releated Menu End
    
    //Billing Related Menu Items Starts
    JMenuItem gstRetailBillGold,gstRetailBillSilver,nonGstRetailBillGold,nonGstRetailBillSilver,retailCustomer,gstRetailBilling,nonGstRetailBilling;
    JMenuItem gstWholesaleBillingGold,gstWholesaleBillingSilver,nonGstWholesaleBillingGold,nonGstWholesaleBillingSilver,wholesaleCustomer,gstWholeSaleBilling,nonGstWholeSaleBilling;
    JMenuItem gstPurchaseBillingGold,gstPurchaseBillingSilver,nonGstPurchaseBillingGold,nonGstPurchaseBillingSilver,purchaseCustomer,gstPurchaseBilling,nonGstPurchaseBilling;
    //Billing Related Menu Items Ends
    
    //Report Related Menu Items Starts
    JMenuItem gstRetailBillGoldReport,gstRetailBillSilverReport,nonGstRetailBillGoldReport,nonGstRetailBillSilverReport,retailCustomerReport,gstRetailReport,nonGstRetailReport;
    JMenuItem gstWholesaleBillingGoldReport,gstWholesaleBillingSilverReport,nonGstWholesaleBillingGoldReport,nonGstWholesaleBillingSilverReport,wholesaleCustomerReport,gstWholeSaleReport,nonGstWholeSaleReport;
    JMenuItem gstPurchaseBillingGoldReport,gstPurchaseBillingSilverReport,nonGstPurchaseBillingGoldReport,nonGstPurchaseBillingSilverReport,purchaseCustomerReport,gstPurchaseReport,nonGstPurchaseReport;
    
    JMenuItem gstGoldStockReport,gstSilverStockReport,nonGstGoldStockReport,nonGstSilverStockReport,stockReport,roughStockReport;
    JMenuItem gstCashReport,nonGstCashReport;
    JMenuItem gstGoldBalanceSheet,nonGstGoldBalanceSheet,gstSilverBalanceSheet,nonGstSilverBalanceSheet,balanceSheet,roughBalanceSheet;
    JMenuItem returnOrnament;
    //Report Related Menu Items Ends
    
    //Payment Menu Items Starts
    JMenuItem gstRetailGoldPayment,gstRetailSilverPayment,nonGstRetailGoldPayment,nonGstRetailSilverPayment,gstRetailPayment,nonGstRetailPayment;
    JMenuItem gstWholesaleGoldPayment,gstWholesaleSilverPayment,nonGstGoldWholesalePayment,nonGstSilverWholesalePayment,gstWholeSalePayment,nonGstWholeSalePayment;
    JMenuItem gstGoldPurchasePayment,gstSilverPurchasePayment,nonGstGoldPurchasePayment,nonGstSilverPurchasePayment,gstPurchasePayment,nonGstPurchasePayment;
    //Payment Menu Items Ends
    
    //Bank Menu Items Starts
    JMenuItem bankAccount,bankAccountEntry,bankAccountStatement;
    //Bank Menu Items Ends
    
    //Master Menu Starts
    JMenuItem productMaster,backup,licence,licenceListView,user,userList,goldOrnamentMaster,silverOrnamentMaster,ornamentList,billnumber;
    //Master Menu Ends
    
    //Handloan Menu Starts
    JMenuItem handLoanCustomer,handLoanCustomerReport,handLoanCustomerAccount,handLoanEntry,handLoanPayment,handloanReport,handLoanDailyReport,handLoanDashBoard;
    //Handloan Menu Ends
    public JPanel getPanel(String licenceType,String userType){
    	this.licenceType = licenceType;
    	this.userType = userType;
        initcomponent();
        constructMenu();
        return panel;
    }
    
    private void initcomponent() {
    	try{
    		panel=new JPanel(new MigLayout());
    		MyPanel.setBackground(panel);
    		
    		menuBar =new JMenuBar();  
    	    retailBilling = new JMenu();    	    
    	    wholesaleBilling = new JMenu();
    	    purchaseBilling = new JMenu();
    	    reports = new JMenu();
    	    payment = new JMenu();
    	    bank = new JMenu();
    	    master = new JMenu();
    	    handloan = new JMenu();
    	    
    	    //Pawn Starts
    	    pawnCustomerMenu = new JMenu();
    	    pawnBillingMenu = new JMenu();
    	    pawnReportMenu = new JMenu();
    	    pawnCustomer=new JMenuItem();
    	    pawnCustomerReport=new JMenuItem();
    	    pawnBill=new JMenuItem();
    	    pawnDelivery=new JMenuItem();
    	    pawnReports=new JMenuItem();
    	    pawnCustomerDetails=new JMenuItem();
    	    pawnAuction = new JMenuItem();
    	    pawnDailyReport = new JMenuItem();
    	    //Pawn Ends
    	    
    	    //Billing Related Menu Items Starts
    	    gstRetailBillGold=new JMenuItem();
    	    gstRetailBillSilver=new JMenuItem();
    	    nonGstRetailBillGold=new JMenuItem();
    	    nonGstRetailBillSilver=new JMenuItem();
    	    gstRetailBilling=new JMenuItem();
    	    nonGstRetailBilling=new JMenuItem();
    	    retailCustomer=new JMenuItem();
    	    
    	    gstWholesaleBillingGold=new JMenuItem();
    	    gstWholesaleBillingSilver=new JMenuItem();
    	    nonGstWholesaleBillingGold=new JMenuItem();
    	    nonGstWholesaleBillingSilver=new JMenuItem();
    	    gstWholeSaleBilling=new JMenuItem();
    	    nonGstWholeSaleBilling=new JMenuItem();
    	    wholesaleCustomer=new JMenuItem();
    	    
    	    gstPurchaseBillingGold=new JMenuItem();
    	    gstPurchaseBillingSilver=new JMenuItem();
    	    nonGstPurchaseBillingGold=new JMenuItem();
    	    nonGstPurchaseBillingSilver=new JMenuItem();
    	    gstPurchaseBilling=new JMenuItem();
    	    nonGstPurchaseBilling=new JMenuItem();
    	    purchaseCustomer=new JMenuItem();
    	    //Billing Related Menu Items Ends
    	    
    	    //Report Related Menu Items Starts
    	    gstRetailBillGoldReport=new JMenuItem();
    	    gstRetailBillSilverReport=new JMenuItem();
    	    nonGstRetailBillGoldReport=new JMenuItem();
    	    nonGstRetailBillSilverReport=new JMenuItem();
    	    gstRetailReport=new JMenuItem();
    	    nonGstRetailReport=new JMenuItem();
    	    retailCustomerReport=new JMenuItem();
    	    
    	    gstWholesaleBillingGoldReport=new JMenuItem();
    	    gstWholesaleBillingSilverReport=new JMenuItem();
    	    nonGstWholesaleBillingGoldReport=new JMenuItem();
    	    nonGstWholesaleBillingSilverReport=new JMenuItem();
    	    gstWholeSaleReport=new JMenuItem();
    	    nonGstWholeSaleReport=new JMenuItem();
    	    wholesaleCustomerReport=new JMenuItem();
    	    
    	    gstPurchaseBillingGoldReport=new JMenuItem();
    	    gstPurchaseBillingSilverReport=new JMenuItem();
    	    nonGstPurchaseBillingGoldReport=new JMenuItem();
    	    nonGstPurchaseBillingSilverReport=new JMenuItem();
    	    gstPurchaseReport=new JMenuItem();
    	    nonGstPurchaseReport=new JMenuItem();
    	    purchaseCustomerReport=new JMenuItem();
    	    
    	    
    	    gstGoldStockReport=new JMenuItem();
    	    gstSilverStockReport=new JMenuItem();
    	    nonGstGoldStockReport=new JMenuItem();
    	    nonGstSilverStockReport=new JMenuItem();
    	    stockReport=new JMenuItem();
    	    roughStockReport=new JMenuItem();
    	    
    	    gstCashReport=new JMenuItem();
    	    nonGstCashReport=new JMenuItem();
    	    
    	    gstGoldBalanceSheet=new JMenuItem();
    	    nonGstGoldBalanceSheet=new JMenuItem();
    	    gstSilverBalanceSheet=new JMenuItem();
    	    nonGstSilverBalanceSheet=new JMenuItem();
    	    balanceSheet=new JMenuItem();
    	    roughBalanceSheet=new JMenuItem();
    	    
    	    returnOrnament = new JMenuItem();
    	    //Report Related Menu Items Ends
    	    
    	    //Payment Menu Items Starts
    	    gstRetailGoldPayment=new JMenuItem();
    	    gstRetailSilverPayment=new JMenuItem();
    	    nonGstRetailGoldPayment=new JMenuItem();
    	    nonGstRetailSilverPayment=new JMenuItem();
    	    gstRetailPayment=new JMenuItem();
    	    nonGstRetailPayment=new JMenuItem();
    	    
    	    gstWholesaleGoldPayment=new JMenuItem();
    	    gstWholesaleSilverPayment=new JMenuItem();
    	    nonGstGoldWholesalePayment=new JMenuItem();
    	    nonGstSilverWholesalePayment=new JMenuItem();
    	    gstWholeSalePayment=new JMenuItem();
    	    nonGstWholeSalePayment=new JMenuItem();
    	    
    	    gstGoldPurchasePayment=new JMenuItem();
    	    gstSilverPurchasePayment=new JMenuItem();
    	    nonGstGoldPurchasePayment=new JMenuItem();
    	    nonGstSilverPurchasePayment=new JMenuItem();
    	    gstPurchasePayment=new JMenuItem();
    	    nonGstPurchasePayment=new JMenuItem();
    	    //Payment Menu Items Ends
    	    
    	    //Bank Menu Items Starts
    	    bankAccount=new JMenuItem();
    	    bankAccountEntry=new JMenuItem();
    	    bankAccountStatement=new JMenuItem();
    	    
    	    //Bank Menu Items Ends
    	    
    	    //Master Menu Starts
    	    productMaster=new JMenuItem();
    	    backup=new JMenuItem();
    	    billnumber = new JMenuItem();
    	    licence=new JMenuItem();
    	    licenceListView = new JMenuItem();
    	    user = new JMenuItem();
    	    userList = new JMenuItem();
    	    goldOrnamentMaster = new JMenuItem();
    	    silverOrnamentMaster = new JMenuItem();
    	    ornamentList = new JMenuItem();
    	    //Master Menu Ends
    	    
    	    //Handloan Menu Starts
    	    handLoanCustomer = new JMenuItem();
    	    handLoanCustomerReport = new JMenuItem();
    	    handLoanCustomerAccount = new JMenuItem();
    	    handLoanEntry = new JMenuItem();
    	    handLoanPayment = new JMenuItem();
    	    handloanReport = new JMenuItem();
    	    handLoanDailyReport = new JMenuItem();
    	    handLoanDashBoard = new JMenuItem();
    	    //Handloan Menu Ends
    	    
    	    gstRetailBillGold.addActionListener(this);
    	    gstRetailBillSilver.addActionListener(this);
    	    nonGstRetailBillGold.addActionListener(this);
    	    nonGstRetailBillSilver.addActionListener(this);
    	    retailCustomer.addActionListener(this);
    	    gstWholesaleBillingGold.addActionListener(this);
    	    gstWholesaleBillingSilver.addActionListener(this);
    	    nonGstWholesaleBillingGold.addActionListener(this);
    	    nonGstWholesaleBillingSilver.addActionListener(this);
    	    wholesaleCustomer.addActionListener(this);
    	    gstPurchaseBillingGold.addActionListener(this);
    	    gstPurchaseBillingSilver.addActionListener(this);
    	    nonGstPurchaseBillingGold.addActionListener(this);
    	    nonGstPurchaseBillingSilver.addActionListener(this);
    	    purchaseCustomer.addActionListener(this);
    	    gstRetailBillGoldReport.addActionListener(this);
    	    gstRetailBillSilverReport.addActionListener(this);
    	    nonGstRetailBillGoldReport.addActionListener(this);
    	    nonGstRetailBillSilverReport.addActionListener(this);
    	    retailCustomerReport.addActionListener(this);
    	    gstWholesaleBillingGoldReport.addActionListener(this);
    	    gstWholesaleBillingSilverReport.addActionListener(this);
    	    nonGstWholesaleBillingGoldReport.addActionListener(this);
    	    nonGstWholesaleBillingSilverReport.addActionListener(this);
    	    wholesaleCustomerReport.addActionListener(this);
    	    gstPurchaseBillingGoldReport.addActionListener(this);
    	    gstPurchaseBillingSilverReport.addActionListener(this);
    	    nonGstPurchaseBillingGoldReport.addActionListener(this);
    	    nonGstPurchaseBillingSilverReport.addActionListener(this);
    	    purchaseCustomerReport.addActionListener(this);
    	    gstGoldStockReport.addActionListener(this);
    	    gstSilverStockReport.addActionListener(this);
    	    nonGstGoldStockReport.addActionListener(this);
    	    nonGstSilverStockReport.addActionListener(this);
    	    gstCashReport.addActionListener(this);
    	    nonGstCashReport.addActionListener(this);
    	    gstGoldBalanceSheet.addActionListener(this);
    	    nonGstGoldBalanceSheet.addActionListener(this);
    	    gstSilverBalanceSheet.addActionListener(this);
    	    nonGstSilverBalanceSheet.addActionListener(this);
    	    gstRetailGoldPayment.addActionListener(this);
    	    gstRetailSilverPayment.addActionListener(this);
    	    nonGstRetailGoldPayment.addActionListener(this);
    	    nonGstRetailSilverPayment.addActionListener(this);
    	    gstWholesaleGoldPayment.addActionListener(this);
    	    gstWholesaleSilverPayment.addActionListener(this);
    	    nonGstGoldWholesalePayment.addActionListener(this);
    	    nonGstSilverWholesalePayment.addActionListener(this);
    	    gstGoldPurchasePayment.addActionListener(this);
    	    gstSilverPurchasePayment.addActionListener(this);
    	    nonGstGoldPurchasePayment.addActionListener(this);
    	    nonGstSilverPurchasePayment.addActionListener(this);
    	    bankAccount.addActionListener(this);
    	    bankAccountEntry.addActionListener(this);
    	    bankAccountStatement.addActionListener(this);
    	    productMaster.addActionListener(this);
    	    backup.addActionListener(this);
    	    billnumber.addActionListener(this);
    	    licence.addActionListener(this);
    	    licenceListView.addActionListener(this);
    	    user.addActionListener(this);
    	    userList.addActionListener(this);
    	    goldOrnamentMaster.addActionListener(this);
    	    silverOrnamentMaster.addActionListener(this);
    	    ornamentList.addActionListener(this);
    	    //Pawn Starts
    	    pawnCustomer.addActionListener(this);
    	    pawnCustomerReport.addActionListener(this);
    	    pawnBill.addActionListener(this);
    	    pawnDelivery.addActionListener(this);
    	    pawnReports.addActionListener(this);
    	    pawnCustomerDetails.addActionListener(this);
    	    pawnAuction.addActionListener(this);
    	    pawnDailyReport.addActionListener(this);
    	    //Pawn Ends
    	    handLoanCustomer.addActionListener(this);
    	    handLoanCustomerReport.addActionListener(this);
    	    handLoanCustomerAccount.addActionListener(this);
    	    handLoanEntry.addActionListener(this);
    	    handLoanPayment.addActionListener(this);
    	    handloanReport.addActionListener(this);
    	    handLoanDailyReport.addActionListener(this);
    	    handLoanDashBoard.addActionListener(this);
    	    
    	    
    	    gstRetailBilling.addActionListener(this);
    	    nonGstRetailBilling.addActionListener(this);
    	    gstWholeSaleBilling.addActionListener(this);
    	    nonGstWholeSaleBilling.addActionListener(this);
    	    gstPurchaseBilling.addActionListener(this);
    	    nonGstPurchaseBilling.addActionListener(this);
    	    gstRetailReport.addActionListener(this);
    	    nonGstRetailReport.addActionListener(this);
    	    gstWholeSaleReport.addActionListener(this);
    	    nonGstWholeSaleReport.addActionListener(this);
    	    gstPurchaseReport.addActionListener(this);
    	    nonGstPurchaseReport.addActionListener(this);
    	    gstRetailPayment.addActionListener(this);
    	    nonGstRetailPayment.addActionListener(this);
    	    gstWholeSalePayment.addActionListener(this);
    	    nonGstWholeSalePayment.addActionListener(this);
    	    gstPurchasePayment.addActionListener(this);
    	    nonGstPurchasePayment.addActionListener(this);
    	    stockReport.addActionListener(this);
    	    roughStockReport.addActionListener(this);
    	    balanceSheet.addActionListener(this);
    	    roughBalanceSheet.addActionListener(this);
    	    returnOrnament.addActionListener(this);
    	    
    	}catch(Exception ex){
    		ex.printStackTrace();
    	}
    }
    private void constructMenu(){
    	try{
    		if(licenceType.equalsIgnoreCase(ConstantUtils.DEFAULT) && userType.equalsIgnoreCase(ConstantUtils.DEFAULT)){
    			constructDefaultMenu();
    		}else if(licenceType.equalsIgnoreCase(ConstantUtils.LICENCE_TYPE_SILVER_WHOLESALE) && userType.equalsIgnoreCase(ConstantUtils.USER_TYPE_ADMIN)){
    			constructSilverAdminMenu();
    		}else if(licenceType.equalsIgnoreCase(ConstantUtils.LICENCE_TYPE_SILVER_WHOLESALE) && userType.equalsIgnoreCase(ConstantUtils.USER_TYPE_GST)){
    			constructSilverGSTMenu();
    		}else if(licenceType.equalsIgnoreCase(ConstantUtils.LICENCE_TYPE_SILVER_WHOLESALE) && userType.equalsIgnoreCase(ConstantUtils.USER_TYPE_NON_GST)){
    			constructSilverNONGSTMenu();
    		}else if(licenceType.equalsIgnoreCase(ConstantUtils.LICENCE_TYPE_GOLD_WHOLESALE) && userType.equalsIgnoreCase(ConstantUtils.USER_TYPE_ADMIN)){
    			constructGoldAdminMenu();
    		}else if(licenceType.equalsIgnoreCase(ConstantUtils.LICENCE_TYPE_GOLD_WHOLESALE) && userType.equalsIgnoreCase(ConstantUtils.USER_TYPE_GST)){
    			constructGoldGSTMenu();
    		}else if(licenceType.equalsIgnoreCase(ConstantUtils.LICENCE_TYPE_GOLD_WHOLESALE) && userType.equalsIgnoreCase(ConstantUtils.USER_TYPE_NON_GST)){
    			constructGoldNONGSTMenu();
    		}
    		
    		else if(licenceType.equalsIgnoreCase(ConstantUtils.LICENCE_TYPE_JEWELLERY) && userType.equalsIgnoreCase(ConstantUtils.USER_TYPE_ADMIN)){
    			constructJewelleryAdminMenu();
    		}else if(licenceType.equalsIgnoreCase(ConstantUtils.LICENCE_TYPE_JEWELLERY) && userType.equalsIgnoreCase(ConstantUtils.USER_TYPE_GST)){
    			constructJewelleryGSTMenu();
    		}else if(licenceType.equalsIgnoreCase(ConstantUtils.LICENCE_TYPE_JEWELLERY) && userType.equalsIgnoreCase(ConstantUtils.USER_TYPE_NON_GST)){
    			constructJewelleryNONGSTMenu();
    		}else  if(licenceType.equalsIgnoreCase(ConstantUtils.LICENCE_TYPE_PAWNBROKER) && userType.equalsIgnoreCase(ConstantUtils.USER_TYPE_ADMIN)){
    			constructPawnbrokerAdminMenu();
    		}else if(licenceType.equalsIgnoreCase(ConstantUtils.HANDLOAN) && userType.equalsIgnoreCase(ConstantUtils.USER_TYPE_ADMIN)) {
    			constructHandLoanAdminMenu();
    		}else if(licenceType.equalsIgnoreCase(ConstantUtils.LICENCE_TYPE_WHOLESALE)) {
    			constructWholesaleMenu(userType);
    		}
    		
    		if(!licenceType.equalsIgnoreCase(ConstantUtils.DEFAULT) && !userType.equalsIgnoreCase(ConstantUtils.DEFAULT)){
    			panel.add(menuBar,"pushx,growx,wrap");
    		}
    	}catch(Exception ex){
    		ex.printStackTrace();
    	}
    }
    
    private void constructDefaultMenu(){
    	try{
    		panel.add(new MyLabel("Welcome"),"center,pushx,wrap,top");
    	}catch(Exception ex){
    		ex.printStackTrace();
    	}
    }

    private void constructSilverAdminMenu(){
    	try{
    		wholesaleBilling.setText("Sales");
			purchaseBilling.setText("Purchase");
			reports.setText("Reports");
			payment.setText("Payment");
			bank.setText("Bank");
			master.setText("Master");
			
			//Sales Starts 
			gstWholesaleBillingSilver.setText("GST Billing");
			nonGstWholesaleBillingSilver.setText("NON GST Billing");
			wholesaleCustomer.setText("Customer");
			wholesaleCustomerReport.setText("Customer List");
			
			wholesaleBilling.add(gstWholesaleBillingSilver);
			wholesaleBilling.add(nonGstWholesaleBillingSilver);
			wholesaleBilling.add(wholesaleCustomer);
			wholesaleBilling.add(wholesaleCustomerReport);
			//Sales Ends
			
			//purchase Starts
			gstPurchaseBillingSilver.setText("GST Purchase");
			nonGstPurchaseBillingSilver.setText("NON GST Purchase");
			purchaseCustomer.setText("Supplier");
			
			purchaseBilling.add(gstPurchaseBillingSilver);
			purchaseBilling.add(nonGstPurchaseBillingSilver);
			purchaseBilling.add(purchaseCustomer);
			//Purchase Ends
			
			//Payment Starts
			gstWholesaleSilverPayment.setText("GST Sales Payment");
			nonGstSilverWholesalePayment.setText("NON GST Sales Payments");
			gstSilverPurchasePayment.setText("GST Purchase Payment");
			nonGstSilverPurchasePayment.setText("NON GST Purchase Payment");
			
			payment.add(gstWholesaleSilverPayment);
			payment.add(nonGstSilverWholesalePayment);
			payment.add(gstSilverPurchasePayment);
			payment.add(nonGstSilverPurchasePayment);
			//Payment Ends
			
			//Reports Starts
			gstWholesaleBillingSilverReport.setText("GST Sales Report");
			nonGstWholesaleBillingSilverReport.setText("NONGST Sales Report");
			gstPurchaseBillingSilverReport.setText("GST Purchase Report");
			nonGstPurchaseBillingSilverReport.setText("NON GST Purchase Report");
			gstSilverStockReport.setText("GST Stock Report");
			
			
			
			reports.add(gstWholesaleBillingSilverReport);
			reports.add(nonGstWholesaleBillingSilverReport);
			reports.add(gstPurchaseBillingSilverReport);
			reports.add(nonGstPurchaseBillingSilverReport);
			//Reports Ends
			
			//Bank Starts
			bankAccount.setText("Account");
		    bankAccountEntry.setText("Account Entry");
		    bankAccountStatement.setText("Statement");
		    
		    bank.add(bankAccount);
		    bank.add(bankAccountEntry);
		    bank.add(bankAccountStatement);
			//Bank Ends
		    
		    //Master Starts
		    //productMaster.setText("Ornament");
		    backup.setText("Backup");
		    billnumber.setText("Bill Number");
		    licence.setText("Licence");
		    licenceListView.setText("Licence View");
		    user.setText("User");
		    userList.setText("User Report");
		    silverOrnamentMaster.setText("Silver Ornament");
		    ornamentList.setText("Ornament Report");
		    
		    //master.add(productMaster);
		    master.add(silverOrnamentMaster);
		    master.add(ornamentList);
		    master.add(backup);
		    master.add(licence);
		    master.add(licenceListView);
		    master.add(user);
		    master.add(userList);
		    master.add(billnumber);
		    //Master Ends
		    
			//Menu Starts
			menuBar.add(wholesaleBilling);
			menuBar.add(purchaseBilling);
			menuBar.add(reports);
			menuBar.add(payment);
			menuBar.add(bank);
			menuBar.add(master);
			//Menu Ends
			
    	}catch(Exception ex){
    		ex.printStackTrace();
    	}
    }
    private void constructSilverGSTMenu(){
    	try{
    		wholesaleBilling = new JMenu("Billing");
			purchaseBilling = new JMenu("Purchase");
			reports = new JMenu("Reports");
			payment = new JMenu("Payment");
			bank = new JMenu("Bank");
			master = new JMenu("Master");
			
			//Billing starts
			gstWholesaleBillingSilver.setText("Sales Bill");
			wholesaleCustomer.setText("Customer");
			
			//Billing Ends
			
			//Purchase Start
		    gstPurchaseBillingSilver.setText("Purchase Bill");    		    
		    purchaseCustomer.setText("Supplier");    		    
		    
		    //Purchase Ends
		    
		    //Report Start
		    gstWholesaleBillingSilverReport.setText("Sales Report");  
		    gstPurchaseBillingSilverReport.setText("Purchase Report");
		    gstSilverStockReport.setText("Stock Report");    		    
		    gstCashReport.setText("Cash Report");
		    gstSilverBalanceSheet.setText("Balance Sheet");
		    
		    reports.add(gstWholesaleBillingSilverReport);
		    reports.add(gstPurchaseBillingSilverReport);
		    reports.add(gstSilverStockReport);
		    reports.add(gstCashReport);
		    reports.add(gstSilverBalanceSheet);
		    
		    //Report Ends
		    
		    //Payment Starts
		    gstWholesaleSilverPayment.setText("Sales Payment");    		    
		    
		    gstSilverPurchasePayment.setText("Purchase Payment");    		    
		    
		    //Payment Ends
		    
		    //Bank Starts
		    bankAccount.setText("Account");
		    bankAccountEntry.setText("Account Entry");
		    bankAccountStatement.setText("Statement");
		    
		    bank.add(bankAccount);
		    bank.add(bankAccountEntry);
		    bank.add(bankAccountStatement);
		    
		    //Bank Ends
		    
		    //Master Starts
		    productMaster.setText("Ornament");
		    backup.setText("Backup");
		    licence.setText("Licence");
		    licenceListView.setText("Licence View");
		    user.setText("User");
		    userList.setText("User Report");
		    billnumber.setText("Bill Number");
		    
		    master.add(productMaster);
		    master.add(backup);
		    master.add(licence);
		    master.add(licenceListView);
		    master.add(user);
		    master.add(userList);
		    master.add(billnumber);
		    //Master Ends
		    
		    
		    menuBar.add(wholesaleCustomer);
			menuBar.add(gstWholesaleBillingSilver);
			menuBar.add(gstWholesaleSilverPayment);
			
			menuBar.add(purchaseCustomer);
			menuBar.add(gstPurchaseBillingSilver);
			menuBar.add(gstSilverPurchasePayment);
		    menuBar.add(reports);    		    
		    menuBar.add(bank);
		    menuBar.add(master);
    	}catch(Exception ex){
    		ex.printStackTrace();
    	}
    }
    private void constructSilverNONGSTMenu(){
    	try{
    		wholesaleBilling = new JMenu("Billing");
			purchaseBilling = new JMenu("Purchase");
			reports = new JMenu("Reports");
			payment = new JMenu("Payment");
			bank = new JMenu("Bank");
			master = new JMenu("Master");
			
			
			//Billing starts
			nonGstWholesaleBillingSilver.setText("Rough Estimate");
			wholesaleCustomer.setText("Customer");
			
			//Billing Ends
			
			//Purchase Start    		        		    
		    nonGstPurchaseBillingSilver.setText("Rough Estimate Purchase");    		    
		    purchaseCustomer.setText("Supplier");
		    
		    //Purchase Ends
		    
		    //Report Start
		    nonGstWholesaleBillingSilverReport.setText("Rough Estimate Report");    		    
		    nonGstPurchaseBillingSilverReport.setText("Rough Estimate Purchase Report");   		        		    
		    nonGstSilverStockReport.setText("Rough Stock Report");    		    
		    nonGstCashReport.setText("Rough Cash Report");    		    
		    nonGstSilverBalanceSheet.setText("Rough Balance Sheet");
		    
		    reports.add(nonGstWholesaleBillingSilverReport);
		    reports.add(nonGstPurchaseBillingSilverReport);
		    reports.add(nonGstSilverStockReport);
		    reports.add(nonGstCashReport);
		    reports.add(nonGstSilverBalanceSheet);
		    
		    //Report Ends
		    
		    //Payment Starts
		    nonGstSilverWholesalePayment.setText("Rough Payment");
		    
		    nonGstSilverPurchasePayment.setText("Rough Purchase Payment");
		    
		    //Payment Ends
		    
		    //Bank Starts
		    bankAccount.setText("Account");
		    bankAccountEntry.setText("Account Entry");
		    bankAccountStatement.setText("Statement");
		    
		    bank.add(bankAccount);
		    bank.add(bankAccountEntry);
		    bank.add(bankAccountStatement);
		    
		    //Bank Ends
		    
		    //Master Starts
		    productMaster.setText("Ornament");
		    backup.setText("Backup");
		    licence.setText("Licence");
		    licenceListView.setText("Licence View");
		    user.setText("User");
		    userList.setText("User Report");
		    billnumber.setText("Bill Number");
		    
		    master.add(productMaster);
		    master.add(backup);
		    master.add(licence);
		    master.add(licenceListView);
		    master.add(user);
		    master.add(userList);
		    master.add(billnumber);
		    //Master Ends
		    
		    
		    menuBar.add(wholesaleCustomer);
		    menuBar.add(nonGstWholesaleBillingSilver);
		    menuBar.add(nonGstSilverWholesalePayment);    		    
		    menuBar.add(purchaseCustomer);
		    menuBar.add(nonGstPurchaseBillingSilver);    		        		    
		    menuBar.add(nonGstSilverPurchasePayment);
		    menuBar.add(reports);    		    
		    menuBar.add(master);
    	}catch(Exception ex){
    		ex.printStackTrace();
    	}
    }
    private void constructGoldAdminMenu(){
    	try{
    		wholesaleBilling.setText("Billing");
			purchaseBilling.setText("Purchase");
			reports.setText("Reports");
			payment.setText("Payment");
			bank.setText("Bank");
			master.setText("Master");   			
			
			//Billing starts
			gstWholesaleBillingGold.setText("Sales Bill");
			nonGstWholesaleBillingGold.setText("Rough Estimate");
			wholesaleCustomer.setText("Customer");
			wholesaleCustomerReport.setText("Customer List");
			
			wholesaleBilling.add(gstWholesaleBillingGold);
			wholesaleBilling.add(nonGstWholesaleBillingGold);
			wholesaleBilling.add(wholesaleCustomer);
			wholesaleBilling.add(wholesaleCustomerReport);
			//Billing Ends
			
			//Purchase Start
		    gstPurchaseBillingGold.setText("Purchase Bill");    		    
		    nonGstPurchaseBillingGold.setText("Rough Estimate Purchase");    		    
		    purchaseCustomer.setText("Supplier");    		    
		    purchaseCustomerReport.setText("Supplier List");
		    
		    purchaseBilling.add(gstPurchaseBillingGold);
		    purchaseBilling.add(nonGstPurchaseBillingGold);
		    purchaseBilling.add(purchaseCustomer);
		    purchaseBilling.add(purchaseCustomerReport);
		    //Purchase Ends
		    
		    //Report Start
		    gstWholesaleBillingGoldReport.setText("Sales Report");    		    
		    nonGstWholesaleBillingGoldReport.setText("Rough Estimate Report");
		    
		    gstPurchaseBillingGoldReport.setText("Purchase Report");    		    
		    nonGstPurchaseBillingGoldReport.setText("Rough Estimate Purchase Report");    		    
		    
		    gstGoldStockReport.setText("Stock Report");    		    
		    nonGstGoldStockReport.setText("Rough Stock Report");
		    
		    gstCashReport.setText("Cash Report");
		    nonGstCashReport.setText("Rough Cash Report");
		    
		    gstGoldBalanceSheet.setText("Balance Sheet");
		    nonGstGoldBalanceSheet.setText("Rough Balance Sheet");
		    
		    reports.add(gstWholesaleBillingGoldReport);
		    reports.add(nonGstWholesaleBillingGoldReport);
		    reports.add(gstPurchaseBillingGoldReport);
		    reports.add(nonGstPurchaseBillingGoldReport);
		    reports.add(gstGoldStockReport);
		    reports.add(nonGstGoldStockReport);
		    reports.add(gstCashReport);
		    reports.add(nonGstCashReport);
		    reports.add(gstGoldBalanceSheet);
		    reports.add(nonGstGoldBalanceSheet);
		    
		    //Report Ends
		    
		    //Payment Starts
		    gstWholesaleGoldPayment.setText("Sales Payment");
		    nonGstGoldWholesalePayment.setText("Rough Payment");
		    
		    gstGoldPurchasePayment.setText("Purchase Payment");
		    nonGstGoldPurchasePayment.setText("Rough Purchase Payment");
		    
		    payment.add(gstWholesaleGoldPayment);
		    payment.add(nonGstGoldWholesalePayment);
		    payment.add(gstGoldPurchasePayment);
		    payment.add(nonGstGoldPurchasePayment);
		    
		    //Payment Ends
		    
		    //Bank Starts
		    bankAccount.setText("Account");
		    bankAccountEntry.setText("Account Entry");
		    bankAccountStatement.setText("Statement");
		    
		    bank.add(bankAccount);
		    bank.add(bankAccountEntry);
		    bank.add(bankAccountStatement);
		    
		    //Bank Ends
		    
		    //Master Starts
		    //productMaster.setText("Product");
		    goldOrnamentMaster.setText("Gold Ornament");
		    ornamentList.setText("Ornament Report");
		    backup.setText("Backup");
		    billnumber.setText("Bill number");
		    licence.setText("Licence");
		    licenceListView.setText("Licence View");
		    user.setText("User");
		    userList.setText("User Report");
		    
		    productMaster.setText("Ornament");
		    
		    //master.add(productMaster);
		    master.add(goldOrnamentMaster);
		    master.add(ornamentList);
		    master.add(backup);
		    master.add(licence);
		    master.add(licenceListView);
		    master.add(user);
		    master.add(userList);
		    master.add(billnumber);
		    //Master Ends
		    
		    
		    menuBar.add(wholesaleBilling);
		    menuBar.add(purchaseBilling);
		    menuBar.add(reports);
		    menuBar.add(payment);
		    menuBar.add(bank);
		    menuBar.add(master);
    	}catch(Exception ex){
    		ex.printStackTrace();
    	}
    }
    private void constructGoldGSTMenu(){
    	try{
    		wholesaleBilling = new JMenu("Billing");
			purchaseBilling = new JMenu("Purchase");
			reports = new JMenu("Reports");
			payment = new JMenu("Payment");
			bank = new JMenu("Bank");
			master = new JMenu("Master");
			
			//Billing starts
			gstWholesaleBillingGold.setText("Sales Bill");
			wholesaleCustomer.setText("Customer");
			
			//Billing Ends
			
			//Purchase Start
		    gstPurchaseBillingGold.setText("Purchase Bill");    		    
		    purchaseCustomer.setText("Supplier");    		    
		    
		    //Purchase Ends
		    
		    //Report Start
		    gstWholesaleBillingGoldReport.setText("Sales Report");  
		    gstPurchaseBillingGoldReport.setText("Purchase Report");
		    gstGoldStockReport.setText("Stock Report");    		    
		    gstCashReport.setText("Cash Report");
		    gstGoldBalanceSheet.setText("Balance Sheet");
		    
		    reports.add(gstWholesaleBillingGoldReport);
		    reports.add(gstPurchaseBillingGoldReport);
		    reports.add(gstGoldStockReport);
		    reports.add(gstCashReport);
		    reports.add(gstGoldBalanceSheet);
		    
		    //Report Ends
		    
		    //Payment Starts
		    gstWholesaleGoldPayment.setText("Sales Payment");    		    
		    
		    gstGoldPurchasePayment.setText("Purchase Payment");    		    
		    
		    //Payment Ends
		    
		    //Bank Starts
		    bankAccount.setText("Account");
		    bankAccountEntry.setText("Account Entry");
		    bankAccountStatement.setText("Statement");
		    
		    bank.add(bankAccount);
		    bank.add(bankAccountEntry);
		    bank.add(bankAccountStatement);
		    
		    //Bank Ends
		    
		    //Master Starts
		    productMaster.setText("Ornament");
		    backup.setText("Backup");
		    billnumber.setText("Bill number");
		    licence.setText("Licence");
		    licenceListView.setText("Licence View");
		    user.setText("User");
		    userList.setText("User Report");
		    
		    master.add(productMaster);
		    master.add(backup);
		    master.add(licence);
		    master.add(licenceListView);
		    master.add(user);
		    master.add(userList);
		    master.add(billnumber);
		    //Master Ends
		    
		    
		    menuBar.add(wholesaleCustomer);
			menuBar.add(gstWholesaleBillingGold);
			//menuBar.add(gstWholesaleBillingGoldReport);
			menuBar.add(gstWholesaleGoldPayment);
			
			menuBar.add(purchaseCustomer);
			menuBar.add(gstPurchaseBillingGold);
			//menuBar.add(gstPurchaseBillingGoldReport);
			menuBar.add(gstGoldPurchasePayment);
		    menuBar.add(reports);    		    
		    menuBar.add(bank);
		    menuBar.add(master);
    		
    	}catch(Exception ex){
    		ex.printStackTrace();
    	}
    }
    private void constructGoldNONGSTMenu(){
    	try{
    		wholesaleBilling = new JMenu("Billing");
			purchaseBilling = new JMenu("Purchase");
			reports = new JMenu("Reports");
			payment = new JMenu("Payment");
			bank = new JMenu("Bank");
			master = new JMenu("Master");
			
			
			//Billing starts
			nonGstWholesaleBillingGold.setText("Rough Estimate");
			wholesaleCustomer.setText("Customer");
			wholesaleCustomerReport.setText("Customer List");
			
			//Billing Ends
			
			//Purchase Start    		        		    
		    nonGstPurchaseBillingGold.setText("Rough Estimate Purchase");    		    
		    purchaseCustomer.setText("Supplier");
		    purchaseCustomerReport.setText("Supplier List");
		    
		    //Purchase Ends
		    
		    //Report Start
		    nonGstWholesaleBillingGoldReport.setText("Rough Estimate Report");    		    
		    nonGstPurchaseBillingGoldReport.setText("Rough Estimate Purchase Report");   		        		    
		    nonGstGoldStockReport.setText("Rough Stock Report");    		    
		    nonGstCashReport.setText("Rough Cash Report");    		    
		    nonGstGoldBalanceSheet.setText("Rough Balance Sheet");
		    
		    reports.add(nonGstWholesaleBillingGoldReport);
		    reports.add(nonGstPurchaseBillingGoldReport);
		    reports.add(nonGstGoldStockReport);
		    reports.add(nonGstCashReport);
		    reports.add(nonGstGoldBalanceSheet);
		    
		    //Report Ends
		    
		    //Payment Starts
		    nonGstGoldWholesalePayment.setText("Rough Payment");
		    
		    nonGstGoldPurchasePayment.setText("Rough Purchase Payment");
		    
		    //Payment Ends
		    
		    //Bank Starts
		    bankAccount.setText("Account");
		    bankAccountEntry.setText("Account Entry");
		    bankAccountStatement.setText("Statement");
		    
		    bank.add(bankAccount);
		    bank.add(bankAccountEntry);
		    bank.add(bankAccountStatement);
		    
		    //Bank Ends
		    
		    //Master Starts
		    //productMaster.setText("Ornament");
		    goldOrnamentMaster.setText("Gold Ornament");
		    ornamentList.setText("Ornament Report");
		    backup.setText("Backup");
		    billnumber.setText("Bull number");
		    licence.setText("Licence");
		    licenceListView.setText("Licence View");
		    user.setText("User");
		    userList.setText("User Report");
		    
		    //master.add(productMaster);
		    master.add(goldOrnamentMaster);
		    master.add(ornamentList);
		    master.add(backup);
		    master.add(licence);
		    master.add(licenceListView);
		    master.add(user);
		    master.add(userList);
		    master.add(billnumber);
		    master.add(wholesaleCustomerReport);
		    master.add(purchaseCustomerReport);
		    //Master Ends
		    
		    
		    menuBar.add(wholesaleCustomer);
		    menuBar.add(nonGstWholesaleBillingGold);
		    menuBar.add(nonGstGoldWholesalePayment);    		    
		    menuBar.add(purchaseCustomer);
		    menuBar.add(nonGstPurchaseBillingGold);    		        		    
		    menuBar.add(nonGstGoldPurchasePayment);
		    menuBar.add(reports);    		    
		    //menuBar.add(bank);
		    menuBar.add(master);
    	}catch(Exception ex){
    		ex.printStackTrace();
    	}
    }
    private void constructJewelleryAdminMenu(){
    	try{
    		retailBilling.setText("Retail");
			purchaseBilling.setText("Purchase");
			reports.setText("Reports");
			payment.setText("Payment");
			bank.setText("Bank");
			master.setText("Master");
			
		    gstRetailBillGold.setText("Gold Billing");
		    gstRetailBillSilver.setText("Silver Billing");
		    nonGstRetailBillGold.setText("Gold Rough Estimate");
		    nonGstRetailBillSilver.setText("Silver Rough Estimate");
		    gstRetailBilling.setText("Billing");
		    nonGstRetailBilling.setText("Rough Estimate");
		    retailCustomer.setText("Customer");
		    retailCustomerReport.setText("Customer Report");
		    
		    /*retailBilling.add(gstRetailBillGold);
		    retailBilling.add(gstRetailBillSilver);
		    retailBilling.add(nonGstRetailBillGold);
		    retailBilling.add(nonGstRetailBillSilver);*/
		    retailBilling.add(gstRetailBilling);
		    retailBilling.add(nonGstRetailBilling);
		    retailBilling.add(retailCustomer);
		    retailBilling.add(retailCustomerReport);
		    menuBar.add(retailBilling);
		    
		    gstPurchaseBillingGold.setText("Gold Purchase");
		    gstPurchaseBillingSilver.setText("Silver Purchase");
		    nonGstPurchaseBillingGold.setText("Gold Rough Estimate");
		    nonGstPurchaseBillingSilver.setText("Silver Rough Estimate");
		    gstPurchaseBilling.setText("Billing");   
		    nonGstPurchaseBilling.setText("Rough Estimate");
		    purchaseCustomer.setText("Supplier");
		    purchaseCustomerReport.setText("Supplier Report");
		    
		    /*purchaseBilling.add(gstPurchaseBillingGold);
		    purchaseBilling.add(gstPurchaseBillingSilver);
		    purchaseBilling.add(nonGstPurchaseBillingGold);
		    purchaseBilling.add(nonGstPurchaseBillingSilver);*/
		    purchaseBilling.add(gstPurchaseBilling);
		    purchaseBilling.add(nonGstPurchaseBilling);
		    purchaseBilling.add(purchaseCustomer);
		    purchaseBilling.add(purchaseCustomerReport);
		    menuBar.add(purchaseBilling);
		    
		    
		    gstRetailBillGoldReport.setText("Gold Billing Report");
		    gstRetailBillSilverReport.setText("Silver Billing Report");
		    nonGstRetailBillGoldReport.setText("Gold Rough Estimate Report");
		    nonGstRetailBillSilverReport.setText("Silver Rough Estimate Report");
		    gstPurchaseBillingGoldReport.setText("Gold Purchase Report");
		    gstPurchaseBillingSilverReport.setText("Silver Purchase Report");
		    nonGstPurchaseBillingGoldReport.setText("Gold Rough Estimate Purchase Report");
		    nonGstPurchaseBillingSilverReport.setText("Silver Rough Estimate PurchaseReport");
		    gstRetailReport.setText("Billing Report"); 
		    nonGstRetailReport.setText("Rough Estimate Report");
		    gstPurchaseReport.setText("Purchase Report");
		    nonGstPurchaseReport.setText("Purchase Rough Estimate Report");
		    
		    
		    gstGoldStockReport.setText("Gold Stock");
		    gstSilverStockReport.setText("Silver Stock");
		    nonGstGoldStockReport.setText("Gold Rough Stock");
		    nonGstSilverStockReport.setText("Silver Rough Stock");
		    stockReport.setText("Stock");
		    roughStockReport.setText("Rough Stock");
		    
		    gstCashReport.setText("Cash Report");
		    nonGstCashReport.setText("Rough Cash Report");
		    
		    gstGoldBalanceSheet.setText("Gold Balance Sheet");
		    gstSilverBalanceSheet.setText("Silver Balance Sheet");
		    nonGstGoldBalanceSheet.setText("Gold Rough Balance Sheet");
		    nonGstSilverBalanceSheet.setText("Silver Rough Balance Sheet");
		    balanceSheet.setText("Balance Sheet");
		    roughBalanceSheet.setText("Rough Balance Sheet");
		    
		    /*reports.add(gstRetailBillGoldReport);
		    reports.add(gstRetailBillSilverReport);
		    reports.add(nonGstRetailBillGoldReport);
		    reports.add(nonGstRetailBillSilverReport);
		    reports.add(gstPurchaseBillingGoldReport);
		    reports.add(gstPurchaseBillingSilverReport);
		    reports.add(nonGstPurchaseBillingGoldReport);
		    reports.add(nonGstPurchaseBillingSilverReport);*/
		    reports.add(gstRetailReport);
		    reports.add(nonGstRetailReport);
		    reports.add(gstPurchaseReport);
		    reports.add(nonGstPurchaseReport);
		    /*reports.add(gstGoldStockReport);
		    reports.add(gstSilverStockReport);
		    reports.add(nonGstGoldStockReport);
		    reports.add(nonGstSilverStockReport);*/
		    reports.add(stockReport);
		    reports.add(roughStockReport);
		    reports.add(balanceSheet);
		    reports.add(roughBalanceSheet);
		    reports.add(gstCashReport);
		    reports.add(nonGstCashReport);
		    /*reports.add(gstGoldBalanceSheet);
		    reports.add(gstSilverBalanceSheet);
		    reports.add(nonGstGoldBalanceSheet);
		    reports.add(nonGstSilverBalanceSheet);*/
		    
		    menuBar.add(reports);
		    //Report Related Menu Items Ends
		    
		    //Payment Menu Items Starts
		    gstRetailGoldPayment.setText("Gold Sales Payment");
		    gstRetailSilverPayment.setText("Silver Sales Payment");
		    nonGstRetailGoldPayment.setText("Gold Rough Payment");
		    nonGstRetailSilverPayment.setText("Silver Rough Payment");
		    gstGoldPurchasePayment.setText("Gold Purchase Payment");
		    gstSilverPurchasePayment.setText("Silver Purchase Payment");
		    nonGstGoldPurchasePayment.setText("Gold Purchase Rough Payment");
		    nonGstSilverPurchasePayment.setText("Silver Purchase Rough Payment");
		    gstRetailPayment.setText("Sales Payment");
		    nonGstRetailPayment.setText("Rough Sale Payment");
		    gstPurchasePayment.setText("Purchase Payment");
		    nonGstPurchasePayment.setText("Purchase Rough Payment");
		    /*payment.add(gstRetailGoldPayment);
		    payment.add(gstRetailSilverPayment);
		    payment.add(nonGstRetailGoldPayment);
		    payment.add(nonGstRetailSilverPayment);
		    payment.add(gstGoldPurchasePayment);
		    payment.add(gstSilverPurchasePayment);
		    payment.add(nonGstGoldPurchasePayment);
		    payment.add(nonGstSilverPurchasePayment);*/
		    payment.add(gstRetailPayment);
		    payment.add(nonGstRetailPayment);
		    payment.add(gstPurchasePayment);
		    payment.add(nonGstPurchasePayment);
		    menuBar.add(payment);
		    
		    //Payment Menu Items Ends
		    
		    //Bank Menu Items Starts
		    bankAccount.setText("Account");
		    bankAccountEntry.setText("Account Entry");
		    bankAccountStatement.setText("Statement");
		    bank.add(bankAccount);
		    bank.add(bankAccountEntry);
		    bank.add(bankAccountStatement);
		    menuBar.add(bank);
		    //Bank Menu Items Ends
		    
		    //Master Menu Starts
		    //JMenuItem productMaster,backup,licence;
		    //productMaster.setText("Ornament");
		    goldOrnamentMaster.setText("Gold Ornament");
		    silverOrnamentMaster.setText("Silver Ornament");
		    ornamentList.setText("Ornament Report");
		    backup.setText("Backup");
		    billnumber.setText("Bill Number");
		    licence.setText("Licence");
		    licenceListView.setText("Licence View");
		    user.setText("User");
		    userList.setText("User Report");
		    
		    //master.add(productMaster);
		    master.add(goldOrnamentMaster);
		    master.add(silverOrnamentMaster);
		    master.add(ornamentList);
		    master.add(backup);
		    master.add(licence);
		    master.add(licenceListView);
		    master.add(user);
		    master.add(userList);
		    master.add(billnumber);
		    
		    menuBar.add(master);
		    //Master Menu Ends
    	}catch(Exception ex){
    		ex.printStackTrace();
    	}
    }
    private void constructJewelleryGSTMenu(){
    	try{
    		retailBilling.setText("Retail");
			purchaseBilling.setText("Purchase");
			reports.setText("Reports");
			payment.setText("Payment");
			bank.setText("Bank");
			master.setText("Master");
			
		    gstRetailBillGold.setText("Gold Billing");
		    gstRetailBillSilver.setText("Silver Billing");    		    
		    retailCustomer.setText("Customer");    		    
		    
		    menuBar.add(retailCustomer);
		    menuBar.add(gstRetailBillGold);
		    menuBar.add(gstRetailBillSilver);
		    
		    
		    gstPurchaseBillingGold.setText("Gold Purchase");
		    gstPurchaseBillingSilver.setText("Silver Purchase");
		    purchaseCustomer.setText("Supplier");
		    
		    menuBar.add(gstPurchaseBillingGold);
		    menuBar.add(gstPurchaseBillingSilver);
		    menuBar.add(purchaseCustomer);
		    
		    gstRetailBillGoldReport.setText("Gold Billing Report");
		    gstRetailBillSilverReport.setText("Silver Billing Report");
		    gstPurchaseBillingGoldReport.setText("Gold Purchase Report");
		    gstPurchaseBillingSilverReport.setText("Silver Purchase Report");
		    
		    gstGoldStockReport.setText("Gold Stock");
		    gstSilverStockReport.setText("Silver Stock");
		    
		    gstCashReport.setText("Cash Report");
		    
		    gstGoldBalanceSheet.setText("Gold Balance Sheet");
		    gstSilverBalanceSheet.setText("Silver Balance Sheet");
		    
		    reports.add(gstRetailBillGoldReport);
		    reports.add(gstRetailBillSilverReport);
		    reports.add(gstPurchaseBillingGoldReport);
		    reports.add(gstPurchaseBillingSilverReport);
		    reports.add(gstGoldStockReport);
		    reports.add(gstSilverStockReport);
		    reports.add(gstCashReport);
		    reports.add(gstGoldBalanceSheet);
		    reports.add(gstSilverBalanceSheet);
		    
		    menuBar.add(reports);
		    //Report Related Menu Items Ends
		    
		    //Payment Menu Items Starts
		    gstRetailGoldPayment.setText("Gold Sales Payment");
		    gstRetailSilverPayment.setText("Silver Sales Payment");
		    gstGoldPurchasePayment.setText("Gold Purchase Payment");
		    gstSilverPurchasePayment.setText("Silver Purchase Payment");
		    payment.add(gstRetailGoldPayment);
		    payment.add(gstRetailSilverPayment);
		    payment.add(gstGoldPurchasePayment);
		    payment.add(gstSilverPurchasePayment);
		    menuBar.add(payment);
		    
		    //Payment Menu Items Ends
		    
		    //Bank Menu Items Starts
		    bankAccount.setText("Account");
		    bankAccountEntry.setText("Account Entry");
		    bankAccountStatement.setText("Statement");
		    bank.add(bankAccount);
		    bank.add(bankAccountEntry);
		    bank.add(bankAccountStatement);
		    menuBar.add(bank);
		    //Bank Menu Items Ends
		    
		    //Master Menu Starts
		    //JMenuItem productMaster,backup,licence;
		    productMaster.setText("Ornament");
		    backup.setText("Backup");
		    billnumber.setText("Bill Number");
		    licence.setText("Licence");
		    licenceListView.setText("Licence View");
		    user.setText("User");
		    userList.setText("User Report");
		    
		    master.add(productMaster);
		    master.add(backup);
		    master.add(licence);
		    master.add(licenceListView);
		    master.add(user);
		    master.add(userList);
		    master.add(billnumber);
		    menuBar.add(master);
		    //Master Menu Ends
    	}catch(Exception ex){
    		ex.printStackTrace();
    	}
    }
    private void constructJewelleryNONGSTMenu(){
    	try{
    		retailBilling.setText("Retail");
			purchaseBilling.setText("Purchase");
			reports.setText("Reports");
			payment.setText("Payment");
			bank.setText("Bank");
			master.setText("Master");
			
		    nonGstRetailBillGold.setText("Gold Rough Estimate");
		    nonGstRetailBillSilver.setText("Silver Rough Estimate");
		    retailCustomer.setText("Customer");
		    
		    menuBar.add(nonGstRetailBillGold);
		    menuBar.add(nonGstRetailBillSilver);
		    menuBar.add(retailCustomer);
		    
		    nonGstPurchaseBillingGold.setText("Gold Rough Estimate");
		    nonGstPurchaseBillingSilver.setText("Silver Rough Estimate");
		    purchaseCustomer.setText("Supplier");
		    
		    menuBar.add(nonGstPurchaseBillingGold);
		    menuBar.add(nonGstPurchaseBillingSilver);
		    menuBar.add(purchaseCustomer);
		    
		    
		    
		    nonGstRetailBillGoldReport.setText("Gold Rough Estimate Report");
		    nonGstRetailBillSilverReport.setText("Silver Rough Estimate Report");
		    nonGstPurchaseBillingGoldReport.setText("Gold Rough Estimate Purchase Report");
		    nonGstPurchaseBillingSilverReport.setText("Silver Rough Estimate PurchaseReport");
		    
		    nonGstGoldStockReport.setText("Gold Rough Stock");
		    nonGstSilverStockReport.setText("Silver Rough Stock");
		    
		    nonGstCashReport.setText("Rough Cash Report");
		    
		    nonGstGoldBalanceSheet.setText("Gold Rough Balance Sheet");
		    nonGstSilverBalanceSheet.setText("Silver Rough Balance Sheet");
		    
		    reports.add(nonGstRetailBillGoldReport);
		    reports.add(nonGstRetailBillSilverReport);
		    reports.add(nonGstPurchaseBillingGoldReport);
		    reports.add(nonGstPurchaseBillingSilverReport);
		    reports.add(nonGstGoldStockReport);
		    reports.add(nonGstSilverStockReport);
		    reports.add(nonGstCashReport);
		    reports.add(nonGstGoldBalanceSheet);
		    reports.add(nonGstSilverBalanceSheet);
		    
		    menuBar.add(reports);
		    //Report Related Menu Items Ends
		    
		    //Payment Menu Items Starts
		    nonGstRetailGoldPayment.setText("Gold Rough Payment");
		    nonGstRetailSilverPayment.setText("Silver Rough Payment");
		    nonGstGoldPurchasePayment.setText("Gold Purchase Rough Payment");
		    nonGstSilverPurchasePayment.setText("Silver Purchase Rough Payment");
		    
		    payment.add(nonGstRetailGoldPayment);
		    payment.add(nonGstRetailSilverPayment);
		    payment.add(nonGstGoldPurchasePayment);
		    payment.add(nonGstSilverPurchasePayment);
		    menuBar.add(payment);
		    
		    //Payment Menu Items Ends
		    
		    //Bank Menu Items Starts
		    bankAccount.setText("Account");
		    bankAccountEntry.setText("Account Entry");
		    bankAccountStatement.setText("Statement");
		    bank.add(bankAccount);
		    bank.add(bankAccountEntry);
		    bank.add(bankAccountStatement);
		    //menuBar.add(bank);
		    //Bank Menu Items Ends
		    
		    //Master Menu Starts
		    //JMenuItem productMaster,backup,licence;
		    productMaster.setText("Ornament");
		    backup.setText("Backup");
		    billnumber.setText("Bill Number");
		    licence.setText("Licence");
		    licenceListView.setText("Licence View");
		    user.setText("User");
		    userList.setText("User Report");
		    
		    master.add(productMaster);
		    master.add(backup);
		    master.add(licence);
		    master.add(licenceListView);
		    master.add(user);
		    master.add(userList);
		    master.add(billnumber);
		    menuBar.add(master);
		    //Master Menu Ends
    	}catch(Exception ex){
    		ex.printStackTrace();
    	}
    }
    
    private void constructPawnbrokerAdminMenu() {
    	try {
    		pawnCustomerMenu.setText("Customer");
    		pawnBillingMenu.setText("Pawn Billing");
    		pawnReportMenu.setText("Reports");
    		master.setText("Master");
    		bank.setText("Bank");
    		
    		pawnCustomer.setText("Customer");
    		pawnCustomerReport.setText("Customer Report");
    		pawnCustomerDetails.setText("Customer Details");
    		
    		pawnBill.setText("Pawn Bill");
    		pawnDelivery.setText("Pawn Delivery");
    		pawnReports.setText("Pledge Report");
    		pawnAuction.setText("Auction Entry");
    		pawnDailyReport.setText("Daily Report");
    		
    		goldOrnamentMaster.setText("Gold Ornament");
    		silverOrnamentMaster.setText("Silver Ornament");
    		ornamentList.setText("Ornament Report");
    		backup.setText("Backup");
    		billnumber.setText("Bill Number");
    		licence.setText("Licence");
    		licenceListView.setText("Licence Report");
    		user.setText("User");
    		userList.setText("User Report");
    		
    		pawnCustomerMenu.add(pawnCustomer);
    		pawnCustomerMenu.add(pawnCustomerReport);
    		pawnCustomerMenu.add(pawnCustomerDetails);
    		
    		pawnBillingMenu.add(pawnBill);
    		pawnBillingMenu.add(pawnDelivery);
    		pawnBillingMenu.add(pawnAuction);
    		
    		pawnReportMenu.add(pawnReports);
    		pawnReportMenu.add(pawnDailyReport);
    		
    		
    		//master.add(productMaster);
    		master.add(goldOrnamentMaster);
		    master.add(silverOrnamentMaster);
		    master.add(ornamentList);
		    master.add(backup);
		    master.add(licence);
		    master.add(licenceListView);
		    master.add(user);
		    master.add(userList);
		    master.add(billnumber);
		    //Master Ends
    		
		  //Bank Menu Items Starts
		    bankAccount.setText("Account");
		    bankAccountEntry.setText("Account Entry");
		    bankAccountStatement.setText("Statement");
		    bank.add(bankAccount);
		    bank.add(bankAccountEntry);
		    bank.add(bankAccountStatement);
		    //Bank Menu Items Ends
    		
    		menuBar.add(pawnCustomerMenu);
    		menuBar.add(pawnBillingMenu);
    		menuBar.add(pawnReportMenu);
    		menuBar.add(bank);
    		menuBar.add(master);
    		//JMenu pawnCustomerMenu,pawnBillingMenu,pawnReportMenu;
    	    //JMenuItem pawnCustomer,pawnCustomerReport,pawnBill,pawnDelivery,pawnReports,pawnCustomerDetails;
    	}catch(Exception ex){
    		ex.printStackTrace();
    	}
    }
    
    private void constructHandLoanAdminMenu() {
    	//handLoanCustomer,handLoanCustomerReport,handLoanCustomerAccount,handLoanEntry,handLoanPayment,handloanReport,handLoanDailyReport,handLoanDashBoard
    	handloan.setText("Hand Loan");
    	master.setText("Master");
    	
    	handLoanCustomer.setText("Customer");
    	handLoanCustomerReport.setText("Customer Report");
    	handLoanCustomerAccount.setText("Customer Account");
    	handLoanEntry.setText("Loan Entry");
    	handLoanPayment.setText("Loan Payment");
    	handloanReport.setText("Loan Report");
    	handLoanDailyReport.setText("Daily Transaction Report");
    	handLoanDashBoard.setText("Dashboard");
    	
    	
    	handloan.add(handLoanDashBoard);
    	
    	handloan.add(handLoanCustomer);
    	handloan.add(handLoanCustomerReport);
    	handloan.add(handLoanCustomerAccount);
    	handloan.add(handLoanEntry);
    	handloan.add(handLoanPayment);
    	handloan.add(handloanReport);
    	handloan.add(handLoanDailyReport);
    	
    	
    	backup.setText("Backup");
		billnumber.setText("Bill Number");
		licence.setText("Licence");
		licenceListView.setText("Licence Report");
		user.setText("User");
		userList.setText("User Report");
		
		master.add(backup);
		master.add(billnumber);
		master.add(licence);
		master.add(licenceListView);
		master.add(user);
		master.add(userList);
		
    	menuBar.add(handloan);
    	menuBar.add(master);
    	
    }
    
    private void constructWholesaleMenu(String userType) {
    	try {
    		
    		wholesaleBilling.setText("Billing");
			purchaseBilling.setText("Purchase");
			reports.setText("Reports");
			payment.setText("Payment");
			bank.setText("Bank");
			master.setText("Master");
			
			gstWholeSaleBilling.setText("Billing");
			nonGstWholeSaleBilling.setText("Rough Estimation");
			
			gstPurchaseBilling.setText("Billing");
			nonGstPurchaseBilling.setText("Rough Estimation");
			
			gstWholeSaleReport.setText("Billing Report");
			nonGstWholeSaleReport.setText("Rough Estimate Report");
			
			gstPurchaseReport.setText("Purchase Report");
			nonGstPurchaseReport.setText("Purchase Rough Estimation");
			
			stockReport.setText("Stock");
		    roughStockReport.setText("Rough Stock");
		    
		    balanceSheet.setText("Balance Sheet");
		    roughBalanceSheet.setText("Rough Balance Sheet");
			
		    returnOrnament.setText("Return Items");
		    
			gstWholeSalePayment.setText("Sale Payment");
			nonGstWholeSalePayment.setText("Sale Rough Estimation Payment");
			
			gstPurchasePayment.setText("Purchase Payment");
			nonGstPurchasePayment.setText("Purchase Rough Estimation Payment");
			
			wholesaleCustomer.setText("Customer");
			wholesaleCustomerReport.setText("Customer List");

			purchaseCustomer.setText("Supplier");    		    
			purchaseCustomerReport.setText("Supplier List");
			
			//Bank Starts
		    bankAccount.setText("Account");
		    bankAccountEntry.setText("Account Entry");
		    bankAccountStatement.setText("Statement");
		    //Bank Ends
		    
		    //Master Starts
		    goldOrnamentMaster.setText("Gold Ornament");
		    silverOrnamentMaster.setText("Silver Ornament");
		    //productMaster.setText("Ornament");
		    ornamentList.setText("Ornament Report");
		    backup.setText("Backup");
		    billnumber.setText("Bill number");
		    licence.setText("Licence");
		    licenceListView.setText("Licence View");
		    user.setText("User");
		    userList.setText("User Report");
		    productMaster.setText("Ornament");
		    
		    //master.add(productMaster);
		    //Master Ends
		    
		    
		    
		    
		    
		    if(userType.equalsIgnoreCase(ConstantUtils.USER_TYPE_ADMIN)) {
		    	wholesaleBilling.add(gstWholeSaleBilling);
				wholesaleBilling.add(nonGstWholeSaleBilling);
				wholesaleBilling.add(wholesaleCustomer);
				wholesaleBilling.add(wholesaleCustomerReport);
				
				purchaseBilling.add(gstPurchaseBilling);
				purchaseBilling.add(nonGstPurchaseBilling);
				purchaseBilling.add(purchaseCustomer);
				purchaseBilling.add(purchaseCustomerReport);
				
				reports.add(gstWholeSaleReport);
				reports.add(nonGstWholeSaleReport);
				reports.add(gstPurchaseReport);
				reports.add(nonGstPurchaseReport);				
				reports.add(stockReport);
			    reports.add(roughStockReport);
			    reports.add(balanceSheet);
			    reports.add(roughBalanceSheet);
			    reports.add(returnOrnament);
				
				payment.add(gstWholeSalePayment);
				payment.add(nonGstWholeSalePayment);
				payment.add(gstPurchasePayment);
				payment.add(nonGstPurchasePayment);
				
				bank.add(bankAccount);
			    bank.add(bankAccountEntry);
			    bank.add(bankAccountStatement);
			    
			    master.add(goldOrnamentMaster);
			    master.add(silverOrnamentMaster);
			    master.add(ornamentList);
			    master.add(backup);
			    master.add(licence);
			    master.add(licenceListView);
			    master.add(user);
			    master.add(userList);
			    master.add(billnumber);
			    
				
				menuBar.add(wholesaleBilling);
			    menuBar.add(purchaseBilling);
			    menuBar.add(reports);
			    menuBar.add(payment);
			    menuBar.add(bank);
			    menuBar.add(master);
				
		    }else if(userType.equalsIgnoreCase(ConstantUtils.USER_TYPE_GST)) {
		    	
		    }else if(userType.equalsIgnoreCase(ConstantUtils.USER_TYPE_NON_GST)) {
				wholesaleBilling.add(nonGstWholeSaleBilling);
				wholesaleBilling.add(wholesaleCustomer);
				wholesaleBilling.add(wholesaleCustomerReport);
				
				purchaseBilling.add(nonGstPurchaseBilling);
				purchaseBilling.add(purchaseCustomer);
				purchaseBilling.add(purchaseCustomerReport);
				
				reports.add(nonGstWholeSaleReport);
				reports.add(nonGstPurchaseReport);				
			    reports.add(roughStockReport);
			    reports.add(roughBalanceSheet);
				
				payment.add(nonGstWholeSalePayment);
				payment.add(nonGstPurchasePayment);
			    master.add(goldOrnamentMaster);
			    master.add(silverOrnamentMaster);
			    master.add(ornamentList);
			    master.add(backup);
			    master.add(licence);
			    master.add(licenceListView);
			    master.add(user);
			    master.add(userList);
			    master.add(billnumber);
			    master.add(wholesaleCustomerReport);
			    master.add(purchaseCustomerReport);
			    
				menuBar.add(wholesaleCustomer);
				menuBar.add(nonGstWholeSaleBilling);
				menuBar.add(nonGstWholeSalePayment);
				menuBar.add(purchaseCustomer);
				menuBar.add(nonGstPurchaseBilling);
				menuBar.add(nonGstPurchasePayment);
				
				
				
			    menuBar.add(reports);
			    menuBar.add(master);
		    	
		    }
		    
		    
		    

    	}catch(Exception ex) {
    		ex.printStackTrace();
    	}
    }
    
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		onClickEvent(e.getSource());
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		onClickEvent(e.getSource());
	}
	
	public void onClickEvent(Object evt){
		try{
			if(evt ==  licence){
				AppController.getInstance().addLicenceFormPage(ConstantUtils.MENU, 0,Session.user.getUserType().getUserType());
			}else if(evt ==  user){
				AppController.getInstance().addUserFormPage(ConstantUtils.MENU, 0,Session.user.getUserType().getUserType());
			}else if(evt == licenceListView){
				AppController.getInstance().addLicenceReportPage(Session.user.getUserType().getUserType());
			}else if(evt == userList){
				AppController.getInstance().addUserReportPage(Session.user.getUserType().getUserType());
			}else if(evt == goldOrnamentMaster){
				AppController.getInstance().addOrnamentFormPage(ConstantUtils.GOLD, 0, ConstantUtils.MENU, Session.user.getUserType().getUserType(), Session.licence.getLicenceId());
			}else if(evt == productMaster){
				AppController.getInstance().addOrnamentFormPage(ConstantUtils.BLANK, 0, ConstantUtils.MENU, Session.user.getUserType().getUserType(), Session.licence.getLicenceId());
			}else if(evt == silverOrnamentMaster){
				AppController.getInstance().addOrnamentFormPage(ConstantUtils.SILVER, 0, ConstantUtils.MENU, Session.user.getUserType().getUserType(), Session.licence.getLicenceId());
			}else if(evt == ornamentList){
				AppController.getInstance().addOrnamentReportPage(Session.user.getUserType().getUserType(),ConstantUtils.MENU);
			}else if(evt == retailCustomer){
				AppController.getInstance().addContactFormPage(0, ConstantUtils.RETAIL_CONTACT_TYPE, ConstantUtils.MENU, Session.licence.getLicenceId());
			}else if(evt == wholesaleCustomer){
				AppController.getInstance().addContactFormPage(0, ConstantUtils.WHOLESALE_CONTACT_TYPE, ConstantUtils.MENU, Session.licence.getLicenceId());
			}else if(evt == purchaseCustomer){
				AppController.getInstance().addContactFormPage(0, ConstantUtils.SUPPLIER_CONTACT_TYPE, ConstantUtils.MENU, Session.licence.getLicenceId());
			}else if(evt == retailCustomerReport){
				AppController.getInstance().addContactReportPage(ConstantUtils.RETAIL_CONTACT_TYPE, ConstantUtils.MENU);
			}else if(evt == wholesaleCustomerReport){
				AppController.getInstance().addContactReportPage(ConstantUtils.WHOLESALE_CONTACT_TYPE, ConstantUtils.MENU);
			}else if(evt == purchaseCustomerReport){
				AppController.getInstance().addContactReportPage(ConstantUtils.SUPPLIER_CONTACT_TYPE, ConstantUtils.MENU);
			}else if(evt == gstRetailBillGold){
				AppController.getInstance().addBillingFormPage(ConstantUtils.MENU, 0, 0, ConstantUtils.GST_ENTRY, ConstantUtils.GOLD, ConstantUtils.GOLD_RETAIL_GST);
			}else if(evt == gstRetailBillSilver){
				AppController.getInstance().addBillingFormPage(ConstantUtils.MENU, 0, 0, ConstantUtils.GST_ENTRY, ConstantUtils.SILVER, ConstantUtils.SILVER_RETAIL_GST);
			}else if(evt == nonGstRetailBillGold){
				AppController.getInstance().addBillingFormPage(ConstantUtils.MENU, 0, 0, ConstantUtils.NON_GST_ENTRY, ConstantUtils.GOLD, ConstantUtils.GOLD_RETAIL_NONGST);
			}else if(evt == nonGstRetailBillSilver){
				AppController.getInstance().addBillingFormPage(ConstantUtils.MENU, 0, 0, ConstantUtils.NON_GST_ENTRY, ConstantUtils.SILVER, ConstantUtils.SILVER_RETAIL_NONGST);
			}else if(evt == gstWholesaleBillingGold){
				AppController.getInstance().addBillingFormPage(ConstantUtils.MENU, 0, 0, ConstantUtils.GST_ENTRY, ConstantUtils.GOLD, ConstantUtils.GOLD_WHOLESALE_GST);
			}else if(evt == gstWholesaleBillingSilver){
				AppController.getInstance().addBillingFormPage(ConstantUtils.MENU, 0, 0, ConstantUtils.GST_ENTRY, ConstantUtils.SILVER, ConstantUtils.SILVER_WHOLESALE_GST);
			}else if(evt == nonGstWholesaleBillingGold){
				AppController.getInstance().addBillingFormPage(ConstantUtils.MENU, 0, 0, ConstantUtils.NON_GST_ENTRY, ConstantUtils.GOLD, ConstantUtils.GOLD_WHOLESALE_NONGST);
			}else if(evt == nonGstWholesaleBillingSilver){
				AppController.getInstance().addBillingFormPage(ConstantUtils.MENU, 0, 0, ConstantUtils.NON_GST_ENTRY, ConstantUtils.SILVER, ConstantUtils.SILVER_WHOLESALE_NONGST);
			}else if(evt == gstPurchaseBillingGold){
				AppController.getInstance().addBillingFormPage(ConstantUtils.MENU, 0, 0, ConstantUtils.GST_ENTRY, ConstantUtils.GOLD, ConstantUtils.GOLD_SUPPLIER_GST);
			}else if(evt == gstPurchaseBillingSilver){
				AppController.getInstance().addBillingFormPage(ConstantUtils.MENU, 0, 0, ConstantUtils.GST_ENTRY, ConstantUtils.SILVER, ConstantUtils.SILVER_SUPPLIER_GST);
			}else if(evt == nonGstPurchaseBillingGold){
				AppController.getInstance().addBillingFormPage(ConstantUtils.MENU, 0, 0, ConstantUtils.NON_GST_ENTRY, ConstantUtils.GOLD, ConstantUtils.GOLD_SUPPLIER_NONGST);
			}else if(evt == nonGstPurchaseBillingSilver){
				AppController.getInstance().addBillingFormPage(ConstantUtils.MENU, 0, 0, ConstantUtils.NON_GST_ENTRY, ConstantUtils.SILVER, ConstantUtils.SILVER_SUPPLIER_NONGST);
			}
			
			else if(evt == gstRetailBilling){
				AppController.getInstance().addBillingFormPage(ConstantUtils.MENU, 0, 0, ConstantUtils.GST_ENTRY, ConstantUtils.BLANK, ConstantUtils.RETAIL_BILLING_GST);
			}else if(evt == nonGstRetailBilling){
				AppController.getInstance().addBillingFormPage(ConstantUtils.MENU, 0, 0, ConstantUtils.NON_GST_ENTRY, ConstantUtils.BLANK, ConstantUtils.RETAIL_BILLING_NONGST);
			}else if(evt == gstWholeSaleBilling){
				AppController.getInstance().addBillingFormPage(ConstantUtils.MENU, 0, 0, ConstantUtils.GST_ENTRY, ConstantUtils.BLANK, ConstantUtils.WHOLESALE_BILLING_GST);
			}else if(evt == nonGstWholeSaleBilling){
				AppController.getInstance().addBillingFormPage(ConstantUtils.MENU, 0, 0, ConstantUtils.NON_GST_ENTRY, ConstantUtils.BLANK, ConstantUtils.WHOLESALE_BILLING_NONGST);
			}else if(evt == gstPurchaseBilling){
				AppController.getInstance().addBillingFormPage(ConstantUtils.MENU, 0, 0, ConstantUtils.GST_ENTRY, ConstantUtils.BLANK, ConstantUtils.PURCHASE_BILLING_GST);
			}else if(evt == nonGstPurchaseBilling){
				AppController.getInstance().addBillingFormPage(ConstantUtils.MENU, 0, 0, ConstantUtils.NON_GST_ENTRY, ConstantUtils.BLANK, ConstantUtils.PURCHASE_BILLING_NONGST);
			}
			
			//New starts
			else if(evt == gstRetailBillGoldReport){
				AppController.getInstance().addBillingReportPage(ConstantUtils.MENU,ConstantUtils.GST_ENTRY, ConstantUtils.GOLD, ConstantUtils.GOLD_RETAIL_GST);
			}else if(evt == gstRetailBillSilverReport){
				AppController.getInstance().addBillingReportPage(ConstantUtils.MENU,ConstantUtils.GST_ENTRY, ConstantUtils.SILVER, ConstantUtils.SILVER_RETAIL_GST);
			}else if(evt == nonGstRetailBillGoldReport){
				AppController.getInstance().addBillingReportPage(ConstantUtils.MENU,ConstantUtils.NON_GST_ENTRY, ConstantUtils.GOLD, ConstantUtils.GOLD_RETAIL_NONGST);
			}else if(evt == nonGstRetailBillSilverReport){
				AppController.getInstance().addBillingReportPage(ConstantUtils.MENU,ConstantUtils.NON_GST_ENTRY, ConstantUtils.SILVER, ConstantUtils.SILVER_RETAIL_NONGST);
			}else if(evt == gstWholesaleBillingGoldReport){
				AppController.getInstance().addBillingReportPage(ConstantUtils.MENU,ConstantUtils.GST_ENTRY, ConstantUtils.GOLD, ConstantUtils.GOLD_WHOLESALE_GST);
			}else if(evt == gstWholesaleBillingSilverReport){
				AppController.getInstance().addBillingReportPage(ConstantUtils.MENU,ConstantUtils.GST_ENTRY, ConstantUtils.SILVER, ConstantUtils.SILVER_WHOLESALE_GST);
			}else if(evt == nonGstWholesaleBillingGoldReport){
				AppController.getInstance().addBillingReportPage(ConstantUtils.MENU,ConstantUtils.NON_GST_ENTRY, ConstantUtils.GOLD, ConstantUtils.GOLD_WHOLESALE_NONGST);
			}else if(evt == nonGstWholesaleBillingSilverReport){
				AppController.getInstance().addBillingReportPage(ConstantUtils.MENU,ConstantUtils.NON_GST_ENTRY, ConstantUtils.SILVER, ConstantUtils.SILVER_WHOLESALE_NONGST);
			}else if(evt == gstPurchaseBillingGoldReport){
				AppController.getInstance().addBillingReportPage(ConstantUtils.MENU,ConstantUtils.GST_ENTRY, ConstantUtils.GOLD, ConstantUtils.GOLD_SUPPLIER_GST);
			}else if(evt == gstPurchaseBillingSilverReport){
				AppController.getInstance().addBillingReportPage(ConstantUtils.MENU,ConstantUtils.GST_ENTRY, ConstantUtils.SILVER, ConstantUtils.SILVER_SUPPLIER_GST);
			}else if(evt == nonGstPurchaseBillingGoldReport){
				AppController.getInstance().addBillingReportPage(ConstantUtils.MENU,ConstantUtils.NON_GST_ENTRY, ConstantUtils.GOLD, ConstantUtils.GOLD_SUPPLIER_NONGST);
			}else if(evt == nonGstPurchaseBillingSilverReport){
				AppController.getInstance().addBillingReportPage(ConstantUtils.MENU,ConstantUtils.NON_GST_ENTRY, ConstantUtils.SILVER, ConstantUtils.SILVER_SUPPLIER_NONGST);
			}
			
			else if(evt == gstRetailReport){
				AppController.getInstance().addBillingReportPage(ConstantUtils.MENU,ConstantUtils.GST_ENTRY, ConstantUtils.BLANK, ConstantUtils.RETAIL_BILLING_GST);
			}else if(evt == nonGstRetailReport){
				AppController.getInstance().addBillingReportPage(ConstantUtils.MENU,ConstantUtils.NON_GST_ENTRY, ConstantUtils.BLANK, ConstantUtils.RETAIL_BILLING_NONGST);
			}else if(evt == gstWholeSaleReport){
				AppController.getInstance().addBillingReportPage(ConstantUtils.MENU,ConstantUtils.GST_ENTRY, ConstantUtils.BLANK, ConstantUtils.WHOLESALE_BILLING_GST);
			}else if(evt == nonGstWholeSaleReport){
				AppController.getInstance().addBillingReportPage(ConstantUtils.MENU,ConstantUtils.NON_GST_ENTRY, ConstantUtils.BLANK, ConstantUtils.WHOLESALE_BILLING_NONGST);
			}else if(evt == gstPurchaseReport){
				AppController.getInstance().addBillingReportPage(ConstantUtils.MENU,ConstantUtils.GST_ENTRY, ConstantUtils.BLANK, ConstantUtils.PURCHASE_BILLING_GST);
			}else if(evt == nonGstPurchaseReport){
				AppController.getInstance().addBillingReportPage(ConstantUtils.MENU,ConstantUtils.NON_GST_ENTRY, ConstantUtils.BLANK, ConstantUtils.PURCHASE_BILLING_NONGST);
			}
			
			
			else if(evt == gstRetailGoldPayment){
				AppController.getInstance().addPaymentFormPage(ConstantUtils.MENU,0,ConstantUtils.GST_ENTRY, ConstantUtils.GOLD, ConstantUtils.GOLD_RETAIL_GST);
			}else if(evt == gstRetailSilverPayment){
				AppController.getInstance().addPaymentFormPage(ConstantUtils.MENU,0,ConstantUtils.GST_ENTRY, ConstantUtils.SILVER, ConstantUtils.SILVER_RETAIL_GST);
			}else if(evt == nonGstRetailGoldPayment){
				AppController.getInstance().addPaymentFormPage(ConstantUtils.MENU,0,ConstantUtils.NON_GST_ENTRY, ConstantUtils.GOLD, ConstantUtils.GOLD_RETAIL_NONGST);
			}else if(evt == nonGstRetailSilverPayment){
				AppController.getInstance().addPaymentFormPage(ConstantUtils.MENU,0,ConstantUtils.NON_GST_ENTRY, ConstantUtils.SILVER, ConstantUtils.SILVER_RETAIL_NONGST);
			}else if(evt == gstWholesaleGoldPayment){
				AppController.getInstance().addPaymentFormPage(ConstantUtils.MENU,0,ConstantUtils.GST_ENTRY, ConstantUtils.GOLD, ConstantUtils.GOLD_WHOLESALE_GST);
			}else if(evt == gstWholesaleSilverPayment){
				AppController.getInstance().addPaymentFormPage(ConstantUtils.MENU,0,ConstantUtils.GST_ENTRY, ConstantUtils.SILVER, ConstantUtils.SILVER_WHOLESALE_GST);
			}else if(evt == nonGstGoldWholesalePayment){
				AppController.getInstance().addPaymentFormPage(ConstantUtils.MENU,0,ConstantUtils.NON_GST_ENTRY, ConstantUtils.GOLD, ConstantUtils.GOLD_WHOLESALE_NONGST);
			}else if(evt == nonGstSilverWholesalePayment){
				AppController.getInstance().addPaymentFormPage(ConstantUtils.MENU,0,ConstantUtils.NON_GST_ENTRY, ConstantUtils.SILVER, ConstantUtils.SILVER_WHOLESALE_NONGST);
			}else if(evt == gstGoldPurchasePayment){
				AppController.getInstance().addPaymentFormPage(ConstantUtils.MENU,0,ConstantUtils.GST_ENTRY, ConstantUtils.GOLD, ConstantUtils.GOLD_SUPPLIER_GST);
			}else if(evt == gstSilverPurchasePayment){
				AppController.getInstance().addPaymentFormPage(ConstantUtils.MENU,0,ConstantUtils.GST_ENTRY, ConstantUtils.SILVER, ConstantUtils.SILVER_SUPPLIER_GST);
			}else if(evt == nonGstGoldPurchasePayment){
				AppController.getInstance().addPaymentFormPage(ConstantUtils.MENU,0,ConstantUtils.NON_GST_ENTRY, ConstantUtils.GOLD, ConstantUtils.GOLD_SUPPLIER_NONGST);
			}else if(evt == nonGstSilverPurchasePayment){
				AppController.getInstance().addPaymentFormPage(ConstantUtils.MENU,0,ConstantUtils.NON_GST_ENTRY, ConstantUtils.SILVER, ConstantUtils.SILVER_SUPPLIER_NONGST);
			}
			
			else if(evt == gstRetailPayment){
				AppController.getInstance().addPaymentFormPage(ConstantUtils.MENU,0,ConstantUtils.GST_ENTRY, ConstantUtils.BLANK, ConstantUtils.RETAIL_BILLING_GST);
			}else if(evt == nonGstRetailPayment){
				AppController.getInstance().addPaymentFormPage(ConstantUtils.MENU,0,ConstantUtils.NON_GST_ENTRY, ConstantUtils.BLANK, ConstantUtils.RETAIL_BILLING_NONGST);
			}else if(evt == gstWholeSalePayment){
				AppController.getInstance().addPaymentFormPage(ConstantUtils.MENU,0,ConstantUtils.GST_ENTRY, ConstantUtils.BLANK, ConstantUtils.WHOLESALE_BILLING_GST);
			}else if(evt == nonGstWholeSalePayment){
				AppController.getInstance().addPaymentFormPage(ConstantUtils.MENU,0,ConstantUtils.NON_GST_ENTRY, ConstantUtils.BLANK, ConstantUtils.WHOLESALE_BILLING_NONGST);
			}else if(evt == gstPurchasePayment){
				AppController.getInstance().addPaymentFormPage(ConstantUtils.MENU,0,ConstantUtils.GST_ENTRY, ConstantUtils.BLANK, ConstantUtils.PURCHASE_BILLING_GST);
			}else if(evt == nonGstPurchasePayment){
				AppController.getInstance().addPaymentFormPage(ConstantUtils.MENU,0,ConstantUtils.NON_GST_ENTRY, ConstantUtils.BLANK, ConstantUtils.PURCHASE_BILLING_NONGST);
			}
			
			//Stock report starts
			//gstGoldStockReport,gstSilverStockReport,nonGstGoldStockReport,nonGstSilverStockReport
			
			else if(evt == gstGoldStockReport) {
				if(Session.licence.getLicenceType().getLicenceTypeName().equalsIgnoreCase(ConstantUtils.LICENCE_TYPE_JEWELLERY)) {
					AppController.getInstance().addStockReport(ConstantUtils.MENU, ConstantUtils.GST_ENTRY, ConstantUtils.GOLD, ConstantUtils.GOLD_RETAIL_GST);
				}else {
					AppController.getInstance().addStockReport(ConstantUtils.MENU, ConstantUtils.GST_ENTRY, ConstantUtils.GOLD, ConstantUtils.GOLD_WHOLESALE_GST);
				}				
			}else if(evt == gstSilverStockReport) {
				if(Session.licence.getLicenceType().getLicenceTypeName().equalsIgnoreCase(ConstantUtils.LICENCE_TYPE_JEWELLERY)) {
					AppController.getInstance().addStockReport(ConstantUtils.MENU, ConstantUtils.GST_ENTRY, ConstantUtils.SILVER, ConstantUtils.SILVER_RETAIL_GST);
				}else {
					AppController.getInstance().addStockReport(ConstantUtils.MENU, ConstantUtils.GST_ENTRY, ConstantUtils.SILVER, ConstantUtils.SILVER_WHOLESALE_GST);
				}				
			}else if(evt == nonGstGoldStockReport) {
				if(Session.licence.getLicenceType().getLicenceTypeName().equalsIgnoreCase(ConstantUtils.LICENCE_TYPE_JEWELLERY)) {
					AppController.getInstance().addStockReport(ConstantUtils.MENU, ConstantUtils.NON_GST_ENTRY, ConstantUtils.GOLD, ConstantUtils.GOLD_RETAIL_NONGST);
				}else {
					AppController.getInstance().addStockReport(ConstantUtils.MENU, ConstantUtils.NON_GST_ENTRY, ConstantUtils.GOLD, ConstantUtils.GOLD_WHOLESALE_NONGST);
				}				
			}else if(evt == nonGstSilverStockReport) {
				if(Session.licence.getLicenceType().getLicenceTypeName().equalsIgnoreCase(ConstantUtils.LICENCE_TYPE_JEWELLERY)) {
					AppController.getInstance().addStockReport(ConstantUtils.MENU, ConstantUtils.NON_GST_ENTRY, ConstantUtils.SILVER, ConstantUtils.SILVER_RETAIL_NONGST);
				}else {
					AppController.getInstance().addStockReport(ConstantUtils.MENU, ConstantUtils.NON_GST_ENTRY, ConstantUtils.SILVER, ConstantUtils.SILVER_WHOLESALE_NONGST);
				}
			}else if(evt == stockReport) {
				AppController.getInstance().addStockReport(ConstantUtils.MENU, ConstantUtils.GST_ENTRY, ConstantUtils.BLANK, ConstantUtils.BLANK);
			}else if(evt == roughStockReport) {
				AppController.getInstance().addStockReport(ConstantUtils.MENU, ConstantUtils.NON_GST_ENTRY, ConstantUtils.BLANK, ConstantUtils.BLANK);
			}
			//Stock report ends
			
			//Cash Report Starts
			else if(evt == gstCashReport) {
				AppController.getInstance().addCashReport(ConstantUtils.MENU, ConstantUtils.GST_ENTRY, "", "");
			}else if(evt == nonGstCashReport) {
				AppController.getInstance().addCashReport(ConstantUtils.MENU, ConstantUtils.NON_GST_ENTRY, "", "");
			}
			//Cash Reports Ends
			
			//Balance Sheet Starts
			//gstGoldBalanceSheet,nonGstGoldBalanceSheet,gstSilverBalanceSheet,nonGstSilverBalanceSheet
			else if(evt == gstGoldBalanceSheet) {
				AppController.getInstance().addBalanceReport(ConstantUtils.MENU, ConstantUtils.GST_ENTRY, "");
			}else if(evt == nonGstGoldBalanceSheet) {
				AppController.getInstance().addBalanceReport(ConstantUtils.MENU, ConstantUtils.NON_GST_ENTRY, "");
			}else if(evt == gstSilverBalanceSheet) {
				AppController.getInstance().addBalanceReport(ConstantUtils.MENU, ConstantUtils.GST_ENTRY, "");
			}else if(evt == nonGstSilverBalanceSheet) {
				AppController.getInstance().addBalanceReport(ConstantUtils.MENU, ConstantUtils.NON_GST_ENTRY, "");
			}else if(evt == balanceSheet) {
				AppController.getInstance().addBalanceReport(ConstantUtils.MENU, ConstantUtils.GST_ENTRY, "");
			}else if(evt == roughBalanceSheet) {
				AppController.getInstance().addBalanceReport(ConstantUtils.MENU, ConstantUtils.NON_GST_ENTRY, "");
			}
			//Balance Sheet Starts
			
			//Retunr item reports starts
			
			else if(evt == returnOrnament) {
				AppController.getInstance().addReturnOrnamentReport(ConstantUtils.MENU, ConstantUtils.NON_GST_ENTRY);
			}
			
			//New ends
			
			//Pawn Starts
			else if(evt == pawnCustomer){
				AppController.getInstance().addContactFormPage(0, ConstantUtils.PAWNCUSTOMER_CONTACT_TYPE, ConstantUtils.MENU, Session.licence.getLicenceId());
			}else if(evt == pawnCustomerReport){
				AppController.getInstance().addContactReportPage(ConstantUtils.PAWNCUSTOMER_CONTACT_TYPE, ConstantUtils.MENU);
			}else if(evt == pawnBill){
				AppController.getInstance().addRetailPawnBillingFormPage(ConstantUtils.MENU, 0, 0, ConstantUtils.PAWN_RETAIL);
			}else if(evt == pawnDelivery){
				AppController.getInstance().addRetailPawnDeliveryFormPage(ConstantUtils.MENU, 0, 0, ConstantUtils.PAWN_RETAIL_DELIVERY);
			}else if(evt == pawnReports){
				AppController.getInstance().addPawnBillingReportPage(ConstantUtils.MENU,ConstantUtils.PAWN_RETAIL);
			}else if(evt == pawnCustomerDetails){
				AppController.getInstance().addRetailPawnCustomerDetails(ConstantUtils.MENU, 0, ConstantUtils.PAWN_RETAIL);;
			}else if(evt == pawnAuction) {
				AppController.getInstance().addRetailPawnAuctionFormPage(ConstantUtils.MENU, 0, 0, ConstantUtils.PAWN_RETAIL_AUCTION);
			}else if(evt ==  pawnDailyReport) {
				AppController.getInstance().addPawnDailyReport(ConstantUtils.MENU, ConstantUtils.PAWN_RETAIL);
			}
			//pawnCustomer,pawnCustomerReport,pawnBill,pawnDelivery,pawnReports,pawnCustomerDetails;
			//Pawn Ends
			
			//Bank Starts
			else if(evt == bankAccount){
				AppController.getInstance().addBankForm(ConstantUtils.MENU, 0);
			}else if(evt == bankAccountEntry){
				AppController.getInstance().addBankEntryForm(ConstantUtils.MENU, 0);
			}else if(evt == bankAccountStatement){
				AppController.getInstance().addBankReport(ConstantUtils.MENU, 0);
			}
			//bankAccount,bankAccountEntry,bankAccountStatement;
			//Bank Ends
			
			//Backup Starts
			else if(evt == backup) {
				AppController.getInstance().addBackupForm();
			}
			//Backup Ends
			else if(evt == billnumber) {
				AppController.getInstance().addBillNumberForm();
			}
			
			//HandLoan starts
			else if(evt == handLoanDashBoard) {
				AppController.getInstance().addHandloanDashboard();
			}else if(evt == handLoanCustomer) {
				AppController.getInstance().addContactFormPage(0, ConstantUtils.CONTACT_TYPE_GENERIC, ConstantUtils.MENU, Session.licence.getLicenceId());
			}else if(evt == handLoanCustomerReport){
				AppController.getInstance().addContactReportPage(ConstantUtils.CONTACT_TYPE_GENERIC, ConstantUtils.MENU);
			}
			//HandLoan Ends
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

}
