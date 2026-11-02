package com.inventory.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Vector;
import java.util.stream.Collectors;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.jdesktop.swingx.JXDatePicker;

import com.inventory.components.BillingFormTableRenderer;
import com.inventory.components.MyButton;
import com.inventory.components.MyLabel;
import com.inventory.components.MyPanel;
import com.inventory.components.MyTextField;
import com.inventory.controller.AppController;
import com.inventory.controller.BalanceSheetController;
import com.inventory.controller.BillController;
import com.inventory.controller.MasterController;
import com.inventory.dto.BalanceSheetDTO;
import com.inventory.dto.PaymentDTO;
import com.inventory.model.Bill;
import com.inventory.model.BillDetails;
import com.inventory.model.BillNumber;
import com.inventory.model.BillReturnDetails;
import com.inventory.model.BillSource;
import com.inventory.model.Contact;
import com.inventory.model.CoverWeight;
import com.inventory.model.Metal;
import com.inventory.model.OldItemDetails;
import com.inventory.model.Ornament;
import com.inventory.model.PaymentDetails;
import com.inventory.reports.RunReport;
import com.inventory.utility.Calculator;
import com.inventory.utility.ConstantUtils;
import com.inventory.utility.Session;
import com.inventory.utility.StringUtils;

import net.miginfocom.swing.MigLayout;

public class BillingForm implements DocumentListener, ActionListener, KeyListener, MouseListener {
	
	private static BillingForm myInstance = null;
	
	private BillingForm() {
	}

	public static BillingForm getInstance() {
		if (myInstance == null) {
			myInstance = new BillingForm();
		}

		return myInstance;
	}

	JPanel panel;
	JDialog coverWeightDialog;  
	String calledFrom;
	Integer billId;
	Integer customerId;
	String billType;
	String metal;
	String billSource;
	List<Contact> contactList = null;
	List<Metal> metalList = null;
	List<Ornament> ornamentList = null;
	List<CoverWeight> coverWeightList = null;
	
	List<BillDetails> billDetailList = null;
	List<BillReturnDetails> billReturnDetailList = null;
	List<OldItemDetails> oldItemDetailsList = null;
	Bill billObj = null;
	Integer billSno=0,returnSno=0,oldSno=0;
	String billEntryMode="",returnEntryMode="",ornamentEntryMode="";
	Integer loadedSno=0,loadedReturnSno=0,loadedOldSno=0;
	JComboBox contact,ornament,paymentType,metalType;
	JTextField tfContact,tfOrnament,tfMetal;
	JTextField cashReceived,metalReceived;
	boolean tfContact_hide_flag = false,tfOrnament_hide_flag = false,tfMetal_hide_flag = false;
	MyTextField billNo;
	MyTextField grossWeight,lessWeight,netWeight,purity,pureWeight,wastage,rate,taxPercentage,ornamentAmount,taxAmount,mcRate,mcPerPiece,mcAmount,roundOff,amount,
	noOfPiece,directAmount,billdetailId,metalBalance,billReturndetailId,oldItemDetailsId;
	MyButton ornamentMode,saveBill,addContact,saleEntry,returnEntry,oldEntry;
	JPanel contactPanel,contactInfoPanel,contactDetailPanel;
	JPanel billingPanel,billingOrnamentPanel,billingOrnamentTablePanel;
	JPanel billingSummaryPanel,billingSummaryInfoPanel,billingSummaryButtonPanel;
	JXDatePicker billingDate;
	Dimension screenSize = null;

	JTable reportTable=null;
    DefaultTableModel reportModel =null;
    JScrollPane scrollPane;
    Date createDateTime = null;
    Date lastUpdateDateTime = null;
    private JPopupMenu popupMenu;
    private JMenuItem editRecord,deleteRecord;
    BillNumber billNumberObj = null;
    List<Bill> billList = null;
    
    String openingCashBalance;
    String openingMetalBalance;
    
    String closingCashBalance;
    String closingMetalBalance;
    
	public JPanel getPanel(String calledFrom,Integer billId,Integer customerId,String billType,String metal,String billSource){
		this.calledFrom = calledFrom;
		this.billId = billId;
		this.customerId = customerId;
		this.billType = billType;
		this.metal = metal;
		this.billSource = billSource;
	
		
		initComponent();
		constructBillForm();
		enableDisableComponents(billingOrnamentPanel,false);
		
		if(billId!=null && billId>0){
			loadBillObj();
		}
		
		if(customerId!=null && customerId>0) {
			setContact(customerId);
			billingDate.transferFocus();
		}
		return panel;
	}
	
	private void initComponent(){
		try{
			billEntryMode = ConstantUtils.CREATE;
			returnEntryMode = ConstantUtils.CREATE;
			billSno=0;returnSno=0;oldSno=0;
			loadedSno=0;loadedReturnSno=0;loadedOldSno=0;
			openingCashBalance="";
		    openingMetalBalance="";
		    closingCashBalance="";
		    closingMetalBalance="";
		    
			panel = new JPanel(new MigLayout());
			
			contactPanel = new JPanel(new MigLayout());
			contactInfoPanel = new JPanel(new MigLayout());
			contactDetailPanel = new JPanel(new MigLayout());
			billingPanel = new JPanel(new MigLayout());
			billingOrnamentPanel = new JPanel(new MigLayout());
			billingOrnamentTablePanel = new JPanel(new MigLayout());
			billingSummaryPanel = new JPanel(new MigLayout());
			billingSummaryInfoPanel = new JPanel(new MigLayout());
			billingSummaryButtonPanel = new JPanel(new MigLayout());
			
			screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			
			billNo = new MyTextField(10);
			billdetailId = new MyTextField(10);
			billReturndetailId = new MyTextField(10);
			oldItemDetailsId = new MyTextField(10);
			metalBalance = new MyTextField(10);
			grossWeight = new MyTextField(10);
			lessWeight = new MyTextField(10);
			netWeight = new MyTextField(10);
			purity = new MyTextField(10);
			pureWeight = new MyTextField(10);
			wastage = new MyTextField(10);
			rate = new MyTextField(10);
			taxPercentage = new MyTextField(10);
			ornamentAmount = new MyTextField(10);
			taxAmount = new MyTextField(10);
			mcRate = new MyTextField(10);
			mcPerPiece = new MyTextField(10);
			mcAmount = new MyTextField(10);
			roundOff = new MyTextField(10);
			amount = new MyTextField(10);
			noOfPiece = new MyTextField(10);
			directAmount = new MyTextField(10);
			cashReceived = new MyTextField(10);
			metalReceived = new MyTextField(10);
			
			ornamentMode = new MyButton(ConstantUtils.ORNAMENT_MODE_SALE);
			
			saleEntry = new MyButton(ConstantUtils.ORNAMENT_MODE_SALE);
			returnEntry = new MyButton(ConstantUtils.ORNAMENT_MODE_RETURN);
			oldEntry = new MyButton(ConstantUtils.ORNAMENT_MODE_OLD);
			ornamentEntryMode = ConstantUtils.ORNAMENT_MODE_SALE;//Default Value
			
			saveBill = new MyButton("Save");
			addContact = new MyButton("Add(+)");
			contact = new JComboBox();
			contact.setSize(300, 10);
			
			ornament = new JComboBox();
			ornament.setSize(300, 10);
			
			paymentType = new JComboBox();
			paymentType.setSize(300, 10);
			
			metalType = new JComboBox();
			metalType.setSize(300, 10);
			
			reportTable = new JTable();
            
            popupMenu = new JPopupMenu();
            editRecord = new JMenuItem();
            deleteRecord = new JMenuItem();
            
            
			String sDate1="31/12/1998";  
			Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
		    
			billingDate = new JXDatePicker();
			billingDate.setDate(Calendar.getInstance().getTime());
			//datePicker.setDate(date1);
			billingDate.setFormats(new SimpleDateFormat("dd/MM/yyyy"));
			
			billObj = new Bill();
			billDetailList = new ArrayList<BillDetails>();
			billReturnDetailList = new ArrayList<BillReturnDetails>();
			oldItemDetailsList = new ArrayList<OldItemDetails>();
			
			if(billSource.equalsIgnoreCase(ConstantUtils.GOLD_WHOLESALE_GST) ||
					billSource.equalsIgnoreCase(ConstantUtils.GOLD_WHOLESALE_NONGST) ||
					billSource.equalsIgnoreCase(ConstantUtils.SILVER_WHOLESALE_GST) ||
					billSource.equalsIgnoreCase(ConstantUtils.SILVER_WHOLESALE_NONGST) ||
					billSource.equalsIgnoreCase(ConstantUtils.WHOLESALE_BILLING_GST) ||
					billSource.equalsIgnoreCase(ConstantUtils.WHOLESALE_BILLING_NONGST)
					){
				contactList = MasterController.getInstace().getEnabledContactList(ConstantUtils.WHOLESALE_CONTACT_TYPE,ConstantUtils.CONSTANT_Y);
			}else if(billSource.equalsIgnoreCase(ConstantUtils.GOLD_SUPPLIER_GST) ||
					billSource.equalsIgnoreCase(ConstantUtils.GOLD_SUPPLIER_NONGST) ||
					billSource.equalsIgnoreCase(ConstantUtils.SILVER_SUPPLIER_GST) ||
					billSource.equalsIgnoreCase(ConstantUtils.SILVER_SUPPLIER_NONGST) ||
					billSource.equalsIgnoreCase(ConstantUtils.PURCHASE_BILLING_GST) ||
					billSource.equalsIgnoreCase(ConstantUtils.PURCHASE_BILLING_NONGST)
					){
				contactList = MasterController.getInstace().getEnabledContactList(ConstantUtils.SUPPLIER_CONTACT_TYPE,ConstantUtils.CONSTANT_Y);
			}else if(billSource.equalsIgnoreCase(ConstantUtils.GOLD_RETAIL_GST) ||
					billSource.equalsIgnoreCase(ConstantUtils.GOLD_RETAIL_NONGST) ||
					billSource.equalsIgnoreCase(ConstantUtils.SILVER_RETAIL_GST) ||
					billSource.equalsIgnoreCase(ConstantUtils.SILVER_RETAIL_NONGST) ||
					billSource.equalsIgnoreCase(ConstantUtils.RETAIL_BILLING_GST) ||
					billSource.equalsIgnoreCase(ConstantUtils.RETAIL_BILLING_NONGST)
					){
				contactList = MasterController.getInstace().getEnabledContactList(ConstantUtils.RETAIL_CONTACT_TYPE,ConstantUtils.CONSTANT_Y);
			}
			
			metalList = MasterController.getInstace().getMetalList();
			if(metalList!=null && metalList.size()==1) {
				metal = metalList.get(0).getMetalName();
			}
			
			if(!metal.equalsIgnoreCase(ConstantUtils.BLANK)) {
				ornamentList = MasterController.getInstace().getOrnamentListByMetalCode(Session.licence.getLicenceId(), metal);
			}
			billNumberObj = BillController.getInstace().getBillNumber(Session.licence.getLicenceId(), MasterController.getInstace().getBillSourceByCode(billSource).getBillSourceId());
			
			coverWeightList = MasterController.getInstace().coverWeightList();
			
			contact.setEditable(true);
			tfContact = (JTextField) contact.getEditor().getEditorComponent();
			
			ornament.setEditable(true);
			tfOrnament = (JTextField) ornament.getEditor().getEditorComponent();
			
			paymentType.setModel(new DefaultComboBoxModel(new Vector<>(ConstantUtils.PAYMENT_TYPE)));
			
			metalType.setEditable(true);
			tfMetal = (JTextField) metalType.getEditor().getEditorComponent();
			
			
			billNo.addActionListener(this);
			contact.addActionListener(this);
			tfContact.addActionListener(this);
			tfOrnament.addActionListener(this);
			tfMetal.addActionListener(this);
			billingDate.addActionListener(this);
			ornamentMode.addActionListener(this);
			saveBill.addActionListener(this);
			addContact.addActionListener(this);
			
			grossWeight.addActionListener(this);
			lessWeight.addActionListener(this);
			netWeight.addActionListener(this);
			purity.addActionListener(this);
			pureWeight.addActionListener(this);
			wastage.addActionListener(this);
			rate.addActionListener(this);
			taxPercentage.addActionListener(this);
			ornamentAmount.addActionListener(this);
			taxAmount.addActionListener(this);
			mcRate.addActionListener(this);
			mcPerPiece.addActionListener(this);
			mcAmount.addActionListener(this);
			roundOff.addActionListener(this);
			amount.addActionListener(this);
			noOfPiece.addActionListener(this);
			directAmount.addActionListener(this);
			editRecord.addActionListener(this);
            deleteRecord.addActionListener(this);
            cashReceived.addActionListener(this);
            metalReceived.addActionListener(this);
            saleEntry.addActionListener(this);
            returnEntry.addActionListener(this);
            oldEntry.addActionListener(this);
            
			billNo.addKeyListener(this);
			contact.addKeyListener(this);
			tfContact.addKeyListener(this);
			tfMetal.addKeyListener(this);
			tfOrnament.addKeyListener(this);
			ornamentMode.addKeyListener(this);
			saveBill.addKeyListener(this);
			addContact.addKeyListener(this);
			grossWeight.addKeyListener(this);
			lessWeight.addKeyListener(this);
			netWeight.addKeyListener(this);
			purity.addKeyListener(this);
			pureWeight.addKeyListener(this);
			wastage.addKeyListener(this);
			rate.addKeyListener(this);
			taxPercentage.addKeyListener(this);
			ornamentAmount.addKeyListener(this);
			taxAmount.addKeyListener(this);
			mcRate.addKeyListener(this);
			mcPerPiece.addKeyListener(this);
			mcAmount.addKeyListener(this);
			roundOff.addKeyListener(this);
			amount.addKeyListener(this);
			noOfPiece.addKeyListener(this);
			directAmount.addKeyListener(this);
			cashReceived.addKeyListener(this);
			metalReceived.addKeyListener(this);
			saleEntry.addKeyListener(this);
			returnEntry.addKeyListener(this);
			oldEntry.addKeyListener(this);
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	public void constructBillForm(){
		try{
			if(billSource.equalsIgnoreCase(ConstantUtils.GOLD_WHOLESALE_GST)){
				MyPanel.setBorder("GST Gold WholeSale Bill", panel);
			}else if(billSource.equalsIgnoreCase(ConstantUtils.GOLD_WHOLESALE_NONGST)){
				MyPanel.setBorder("NONGST Gold WholeSale Bill", panel);
			}else if(billSource.equalsIgnoreCase(ConstantUtils.SILVER_WHOLESALE_GST)){
				MyPanel.setBorder("GST Silver WholeSale Bill", panel);
			}else if(billSource.equalsIgnoreCase(ConstantUtils.SILVER_WHOLESALE_NONGST)){
				MyPanel.setBorder("NONGST Silver WholeSale Bill", panel);
			}else if(billSource.equalsIgnoreCase(ConstantUtils.GOLD_SUPPLIER_GST)){
				MyPanel.setBorder("GST Gold Supplier Bill", panel);
			}else if(billSource.equalsIgnoreCase(ConstantUtils.GOLD_SUPPLIER_NONGST)){
				MyPanel.setBorder("NONGST Gold Supplier Bill", panel);
			}else if(billSource.equalsIgnoreCase(ConstantUtils.SILVER_SUPPLIER_GST)){
				MyPanel.setBorder("GST Silver Supplier Bill", panel);
			}else if(billSource.equalsIgnoreCase(ConstantUtils.SILVER_SUPPLIER_NONGST)){
				MyPanel.setBorder("NONGST Silver Supplier Bill", panel);
			}else if(billSource.equalsIgnoreCase(ConstantUtils.GOLD_RETAIL_GST)){
				MyPanel.setBorder("GST Gold Retail Bill", panel);
			}else if(billSource.equalsIgnoreCase(ConstantUtils.GOLD_RETAIL_NONGST)){
				MyPanel.setBorder("NONGST Gold Retail Bill", panel);
			}else if(billSource.equalsIgnoreCase(ConstantUtils.SILVER_RETAIL_GST)){
				MyPanel.setBorder("GST Silver Retail Bill", panel);
			}else if(billSource.equalsIgnoreCase(ConstantUtils.SILVER_RETAIL_NONGST)){
				MyPanel.setBorder("NONGST Silver Retail Bill", panel);
			}else if(billSource.equalsIgnoreCase(ConstantUtils.RETAIL_BILLING_GST)){
				MyPanel.setBorder("Retail Bill", panel);
			}else if(billSource.equalsIgnoreCase(ConstantUtils.RETAIL_BILLING_NONGST)){
				MyPanel.setBorder("Rough Retail Bill", panel);
			}else if(billSource.equalsIgnoreCase(ConstantUtils.WHOLESALE_BILLING_GST)) {
				MyPanel.setBorder("WholeSale Bill", panel);
			}else if(billSource.equalsIgnoreCase(ConstantUtils.WHOLESALE_BILLING_NONGST)) {
				MyPanel.setBorder("Rough Wholesale Bill", panel);
			}else if(billSource.equalsIgnoreCase(ConstantUtils.PURCHASE_BILLING_GST)) {
				MyPanel.setBorder("Purchase Bill", panel);
			}else if(billSource.equalsIgnoreCase(ConstantUtils.PURCHASE_BILLING_NONGST)) {
				MyPanel.setBorder("Rough Purchase Bill", panel);
			}
				
			getBillForm();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private void getBillForm(){
		try{
			panel.add(getContactPanel(),"north,pushx,growx,wrap");
			panel.add(getOrnamentPanel(),"center,pushx,growx,pushy,growy");
			if(contactList!=null && contactList.size()>0) {
				setModel(contact,tfContact,new DefaultComboBoxModel(new Vector<>(contactList)), "");
			}
			if(ornamentList!=null && ornamentList.size()>0) {
				setModel(ornament,tfOrnament,new DefaultComboBoxModel(new Vector<>(ornamentList)), "");
			}
			
			if(metalList!=null && metalList.size()>0) {
				setModel(metalType,tfMetal,new DefaultComboBoxModel(new Vector<>(metalList)),"");
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private JPanel getContactPanel(){
		try{
			//Contact starts
			MyPanel.setBackground(contactPanel, Color.white);
			MyPanel.setBackground(contactInfoPanel, Color.white);
			MyPanel.setBackground(contactDetailPanel, Color.white);
			
			contactInfoPanel.add(new MyLabel("Customer Name : "));
			contactInfoPanel.add(contact);
			contactInfoPanel.add(addContact,"wrap,span 3");
			
			contactInfoPanel.add(new MyLabel("Bill Number : "));
			contactInfoPanel.add(billNo);
			
			contactInfoPanel.add(new MyLabel("Billing Date:"));
			contactInfoPanel.add(billingDate,"wrap");
			if(!(billSource.equalsIgnoreCase(ConstantUtils.GOLD_SUPPLIER_GST) ||
					billSource.equalsIgnoreCase(ConstantUtils.GOLD_SUPPLIER_NONGST) ||
					billSource.equalsIgnoreCase(ConstantUtils.SILVER_SUPPLIER_GST) ||
					billSource.equalsIgnoreCase(ConstantUtils.SILVER_SUPPLIER_NONGST) ||
					
					billSource.equalsIgnoreCase(ConstantUtils.PURCHASE_BILLING_GST) ||
					billSource.equalsIgnoreCase(ConstantUtils.PURCHASE_BILLING_NONGST)
					)) {
			if(billId==null || billId==0){
				billNo.setText(StringUtils.getBillNumber(billNumberObj.getBillLetter(),billNumberObj.getBillNumber()));
			}
			}
			contactPanel.add(contactInfoPanel,"w "+(getScreenWidth(48))+"!");
			contactPanel.add(contactDetailPanel,"w "+(getScreenWidth(48))+"!");
			//Contact ends
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return contactPanel;
	}
	
	private JPanel getOrnamentPanel(){
		try{
			//ornament starts
			MyPanel.setBorder("Billing Details", billingPanel);
			MyPanel.setBorder("", billingOrnamentPanel);
			MyPanel.setBackground(billingOrnamentTablePanel, Color.white);
			
			constructOrnamentPanel();

			billingPanel.add(billingOrnamentPanel,"west,pushx,pushy,wrap");
			billingPanel.add(getOrnamentTable(),"center,pushx,pushy,growx,growy,wrap");
			billingPanel.add(getBillSummaryPanel(),"dock south,pushx,growx,pushy,growy,wrap");
			//ornament ends
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return billingPanel;
	}
	
	private void constructOrnamentPanel(){
		try{
			billingOrnamentPanel.removeAll();
			
			if(billType.equalsIgnoreCase(ConstantUtils.GST_ENTRY)){
				if(metalList!=null && metalList.size()>1) {
					billingOrnamentPanel.add(new MyLabel("Metal :"));billingOrnamentPanel.add(metalType,"wrap");	
				}
				billingOrnamentPanel.add(new MyLabel("Ornament :"));billingOrnamentPanel.add(ornament,"wrap");			
				billingOrnamentPanel.add(new MyLabel("DtAmt :"));billingOrnamentPanel.add(directAmount,"wrap");
				billingOrnamentPanel.add(new MyLabel("Tax% :"));billingOrnamentPanel.add(taxPercentage,"wrap");
				billingOrnamentPanel.add(new MyLabel("NO/Piece :"));billingOrnamentPanel.add(noOfPiece,"wrap");
				billingOrnamentPanel.add(new MyLabel("NetWt :"));billingOrnamentPanel.add(netWeight,"wrap");
				
				if(billSource.equalsIgnoreCase(ConstantUtils.GOLD_RETAIL_GST) || billSource.equalsIgnoreCase(ConstantUtils.SILVER_RETAIL_GST) ||
						billSource.equalsIgnoreCase(ConstantUtils.RETAIL_BILLING_GST)
						) {
					billingOrnamentPanel.add(new MyLabel("Wastage :"));billingOrnamentPanel.add(wastage,"wrap");
					billingOrnamentPanel.add(new MyLabel("MC :"));billingOrnamentPanel.add(mcPerPiece,"wrap");
				}
				billingOrnamentPanel.add(new MyLabel("Rate :"));billingOrnamentPanel.add(rate,"wrap");
				if(billSource.equalsIgnoreCase(ConstantUtils.GOLD_RETAIL_GST) || billSource.equalsIgnoreCase(ConstantUtils.SILVER_RETAIL_GST) ||
						billSource.equalsIgnoreCase(ConstantUtils.RETAIL_BILLING_GST)
						) {
					billingOrnamentPanel.add(new MyLabel("MC Amt :"));billingOrnamentPanel.add(mcAmount,"wrap");
				}
				billingOrnamentPanel.add(new MyLabel("OrnAmt :"));billingOrnamentPanel.add(ornamentAmount,"wrap");
				billingOrnamentPanel.add(new MyLabel("TaxAmt :"));billingOrnamentPanel.add(taxAmount,"wrap");
				billingOrnamentPanel.add(new MyLabel("Rd/off :"));billingOrnamentPanel.add(roundOff,"wrap");
				billingOrnamentPanel.add(new MyLabel("Amount :"));billingOrnamentPanel.add(amount,"wrap");
				
			}else{
				//billingOrnamentPanel.add(ornamentMode,"wrap");
				if(metalList!=null && metalList.size()>1) {
					billingOrnamentPanel.add(new MyLabel("Metal :"));billingOrnamentPanel.add(metalType,"wrap");	
				}
				billingOrnamentPanel.add(new MyLabel("Ornament :"));billingOrnamentPanel.add(ornament,"wrap");			
				billingOrnamentPanel.add(new MyLabel("NO/Piece :"));billingOrnamentPanel.add(noOfPiece,"wrap");
				billingOrnamentPanel.add(new MyLabel("GrossWt :"));billingOrnamentPanel.add(grossWeight,"wrap");
				billingOrnamentPanel.add(new MyLabel("LessWt :"));billingOrnamentPanel.add(lessWeight,"wrap");
				billingOrnamentPanel.add(new MyLabel("NetWt :"));billingOrnamentPanel.add(netWeight,"wrap");
				
				if(!(billSource.equalsIgnoreCase(ConstantUtils.GOLD_RETAIL_NONGST) ||	billSource.equalsIgnoreCase(ConstantUtils.SILVER_RETAIL_NONGST) ||
						billSource.equalsIgnoreCase(ConstantUtils.RETAIL_BILLING_NONGST)
						)){
					billingOrnamentPanel.add(new MyLabel("Purity% :"));billingOrnamentPanel.add(purity,"wrap");
				}
				
				billingOrnamentPanel.add(new MyLabel("PureWt :"));billingOrnamentPanel.add(pureWeight,"wrap");
				billingOrnamentPanel.add(new MyLabel("Wastage :"));billingOrnamentPanel.add(wastage,"wrap");
				billingOrnamentPanel.add(new MyLabel("MC :"));billingOrnamentPanel.add(mcRate,"wrap");
				billingOrnamentPanel.add(new MyLabel("MC/Piece :"));billingOrnamentPanel.add(mcPerPiece,"wrap");
				billingOrnamentPanel.add(new MyLabel("Rate :"));billingOrnamentPanel.add(rate,"wrap");
				billingOrnamentPanel.add(new MyLabel("MC Amt :"));billingOrnamentPanel.add(mcAmount,"wrap");
				billingOrnamentPanel.add(new MyLabel("OrnAmt :"));billingOrnamentPanel.add(ornamentAmount,"wrap");
				billingOrnamentPanel.add(new MyLabel("Rd/off :"));billingOrnamentPanel.add(roundOff,"wrap");
				billingOrnamentPanel.add(new MyLabel("Amount :"));billingOrnamentPanel.add(amount,"wrap");
				
				billingOrnamentPanel.add(saleEntry);billingOrnamentPanel.add(returnEntry);//billingOrnamentPanel.add(oldEntry,"wrap");
			}
			
			if(billSource.equalsIgnoreCase(ConstantUtils.GOLD_RETAIL_NONGST) ||				
					billSource.equalsIgnoreCase(ConstantUtils.SILVER_RETAIL_NONGST) ||
					billSource.equalsIgnoreCase(ConstantUtils.GOLD_RETAIL_GST) || 
					billSource.equalsIgnoreCase(ConstantUtils.SILVER_RETAIL_GST) ||
					billSource.equalsIgnoreCase(ConstantUtils.GOLD_SUPPLIER_GST) ||
					billSource.equalsIgnoreCase(ConstantUtils.SILVER_SUPPLIER_GST) ||
					
					billSource.equalsIgnoreCase(ConstantUtils.RETAIL_BILLING_GST) ||
					billSource.equalsIgnoreCase(ConstantUtils.RETAIL_BILLING_NONGST) ||
					billSource.equalsIgnoreCase(ConstantUtils.WHOLESALE_BILLING_GST) ||
					billSource.equalsIgnoreCase(ConstantUtils.PURCHASE_BILLING_GST)
					){
				purity.setText("100");
				purity.setVisible(false);
			}
			
			billingOrnamentPanel.revalidate();
			billingOrnamentPanel.repaint();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private JPanel getOrnamentTable(){
		try{
			tableModelling(reportTable);
			
			editRecord.setText("Edit Ornament");
			deleteRecord.setText("Remove Ornament");
			
			popupMenu.add(editRecord);
			popupMenu.add(deleteRecord);
			
			reportTable.setComponentPopupMenu(popupMenu);
            
            reportTable.setBackground(Color.WHITE);
            scrollPane = new JScrollPane(reportTable,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            //reportTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            Dimension d = reportTable.getPreferredSize();
            int rows=20;
            scrollPane.setPreferredSize(
            new Dimension(d.width,reportTable.getRowHeight()*rows+1));
            
            billingOrnamentTablePanel.add(scrollPane,"span,pushx,growx,wrap");
            
            BillingFormTableRenderer render =new BillingFormTableRenderer();
            reportTable.setDefaultRenderer(Object.class,  render);
            reportTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            
            
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return billingOrnamentTablePanel;
	}
	
	private JPanel getBillSummaryPanel(){
		try{
			//Summary Starts
			MyPanel.setBackground(billingSummaryPanel, Color.WHITE);
			MyPanel.setBackground(billingSummaryInfoPanel, Color.WHITE);
			MyPanel.setBackground(billingSummaryButtonPanel, Color.WHITE);
			constructBillSummaryInfoPanel();
			constructBillSummaryButtonPanel();
			billingSummaryPanel.add(billingSummaryInfoPanel,"north,pushx,pushy,growx,growy,wrap");
			billingSummaryPanel.add(billingSummaryButtonPanel,"center,pushx,pushy,growx,growy,wrap");
			//Summary Ends
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return billingSummaryPanel;
	}
	
	private void constructBillSummaryInfoPanel(){
		try{
			billingSummaryInfoPanel.removeAll();
			Double payment = 199800.89;
			Locale locale = new Locale("en","in");
		    DecimalFormat decimalFormat = (DecimalFormat) DecimalFormat.getCurrencyInstance(locale);
		    DecimalFormatSymbols dfs = DecimalFormatSymbols.getInstance(locale);
		    dfs.setCurrencySymbol("\u20B9");
		    decimalFormat.setDecimalFormatSymbols(dfs);
		    System.out.println("payment->"+decimalFormat.format(payment));
		    
		    NumberFormat formatter = NumberFormat.getCurrencyInstance(locale);
		    String moneyString = formatter.format(Double.valueOf("2000125.25"));
		    
		    Double amount = 53566735531.235; //amount is an example ,can be used with any double value
		    DecimalFormat IndianCurrencyFormat = new DecimalFormat("##,##,###.00");
		    String formattedAmount = IndianCurrencyFormat.format(amount);
		    
			billingSummaryInfoPanel.add(new MyLabel("Gross Weight:"));
			if(metal.equalsIgnoreCase(ConstantUtils.GOLD)){
				if(billObj.getGrossWeight()!=null){
					billingSummaryInfoPanel.add(new MyLabel(Calculator.formatThreeDecimal(billObj.getGrossWeight()),Color.RED));
				}
				billingSummaryInfoPanel.add(new MyLabel("Weight Less:"));
				if(billObj.getWeightLess()!=null){
					billingSummaryInfoPanel.add(new MyLabel(Calculator.formatThreeDecimal(billObj.getWeightLess()),Color.RED));
				}
				billingSummaryInfoPanel.add(new MyLabel("Net Weight:"));
				if(billObj.getWeight()!=null){
					billingSummaryInfoPanel.add(new MyLabel(Calculator.formatThreeDecimal(billObj.getWeight()),Color.RED));
				}
				billingSummaryInfoPanel.add(new MyLabel("Pure Weight:"));
				if(billObj.getPureWeight()!=null){
					billingSummaryInfoPanel.add(new MyLabel(Calculator.formatThreeDecimal(billObj.getPureWeight()),Color.RED),"wrap");
				}
			}else if(metal.equalsIgnoreCase(ConstantUtils.SILVER)){
				if(billObj.getGrossWeight()!=null){
					billingSummaryInfoPanel.add(new MyLabel(Calculator.formatTwoDecimal(billObj.getGrossWeight()),Color.RED));
				}
				billingSummaryInfoPanel.add(new MyLabel("Weight Less:"));
				if(billObj.getWeightLess()!=null){
					billingSummaryInfoPanel.add(new MyLabel(Calculator.formatTwoDecimal(billObj.getWeightLess()),Color.RED));
				}
				billingSummaryInfoPanel.add(new MyLabel("Net Weight:"));
				if(billObj.getWeight()!=null){
					billingSummaryInfoPanel.add(new MyLabel(Calculator.formatTwoDecimal(billObj.getWeight()),Color.RED));
				}
				billingSummaryInfoPanel.add(new MyLabel("Pure Weight:"));
				if(billObj.getPureWeight()!=null){
					billingSummaryInfoPanel.add(new MyLabel(Calculator.formatTwoDecimal(billObj.getPureWeight()),Color.RED),"wrap");
				}
			}
			
			billingSummaryInfoPanel.add(new MyLabel(""),"wrap");
			billingSummaryInfoPanel.add(new MyLabel("Amount:"));
			if(billObj.getAmount()!=null){
				billingSummaryInfoPanel.add(new MyLabel(Calculator.formatTwoDecimal(billObj.getAmount()),Color.RED));
			}
			
			billingSummaryInfoPanel.add(new MyLabel("Tax Amount:"));
			if(billObj.getTaxAmount()!=null){
				billingSummaryInfoPanel.add(new MyLabel(Calculator.formatTwoDecimal(billObj.getTaxAmount()),Color.RED));
			}
			billingSummaryInfoPanel.add(new MyLabel("Roundoff:"));
			if(billObj.getRoundOff()!=null){
				billingSummaryInfoPanel.add(new MyLabel(Calculator.formatTwoDecimal(billObj.getRoundOff()),Color.RED));
			}
			billingSummaryInfoPanel.add(new MyLabel("Total Amount"));
			if(billObj.getTotalAmount()!=null){
				billingSummaryInfoPanel.add(new MyLabel(Calculator.formatTwoDecimal(billObj.getTotalAmount()),Color.RED));
				
				if(billSource.equalsIgnoreCase(ConstantUtils.GOLD_RETAIL_GST) ||
						billSource.equalsIgnoreCase(ConstantUtils.GOLD_RETAIL_NONGST) ||
						billSource.equalsIgnoreCase(ConstantUtils.SILVER_RETAIL_GST) ||
						billSource.equalsIgnoreCase(ConstantUtils.SILVER_RETAIL_NONGST) ||
						
						billSource.equalsIgnoreCase(ConstantUtils.RETAIL_BILLING_GST) ||
						billSource.equalsIgnoreCase(ConstantUtils.RETAIL_BILLING_NONGST)
						) {
					cashReceived.setText(billObj.getTotalAmount().toString());
				}				
			}
			billingSummaryInfoPanel.add(new MyLabel("Metal Balance:"));
			if(billObj.getMetalBalance()!=null){
			billingSummaryInfoPanel.add(new MyLabel(Calculator.formatThreeDecimal(billObj.getMetalBalance()),Color.RED),"wrap");
				if(billSource.equalsIgnoreCase(ConstantUtils.GOLD_RETAIL_GST) ||
						billSource.equalsIgnoreCase(ConstantUtils.GOLD_RETAIL_NONGST) ||
						billSource.equalsIgnoreCase(ConstantUtils.SILVER_RETAIL_GST) ||
						billSource.equalsIgnoreCase(ConstantUtils.SILVER_RETAIL_NONGST) ||
						
						billSource.equalsIgnoreCase(ConstantUtils.RETAIL_BILLING_GST) ||
						billSource.equalsIgnoreCase(ConstantUtils.RETAIL_BILLING_NONGST)
						) {
					metalReceived.setText(billObj.getMetalBalance().toString());
				}
			}
			billingSummaryInfoPanel.add(new MyLabel(""),"wrap");
			billingSummaryInfoPanel.add(new MyLabel("Amount in words:"));
			billingSummaryInfoPanel.add(new MyLabel(billObj.getAmountInWords(),Color.RED),"span 6");
			
			billingSummaryInfoPanel.revalidate();
			billingSummaryInfoPanel.repaint();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	private void constructBillSummaryButtonPanel(){
		try{
			if(billSource.equalsIgnoreCase(ConstantUtils.GOLD_WHOLESALE_GST) ||
					billSource.equalsIgnoreCase(ConstantUtils.SILVER_WHOLESALE_GST) ||
					billSource.equalsIgnoreCase(ConstantUtils.GOLD_RETAIL_GST) ||
					billSource.equalsIgnoreCase(ConstantUtils.SILVER_RETAIL_GST) ||
					billSource.equalsIgnoreCase(ConstantUtils.GOLD_RETAIL_NONGST) ||
					billSource.equalsIgnoreCase(ConstantUtils.SILVER_RETAIL_NONGST) ||
					
					billSource.equalsIgnoreCase(ConstantUtils.RETAIL_BILLING_GST) ||
					billSource.equalsIgnoreCase(ConstantUtils.RETAIL_BILLING_NONGST) ||
					billSource.equalsIgnoreCase(ConstantUtils.WHOLESALE_BILLING_GST)
					) {
				billingSummaryButtonPanel.add(new MyLabel("Cash Received :"));
				billingSummaryButtonPanel.add(cashReceived);
				
				billingSummaryButtonPanel.add(new MyLabel("Payment Type :"));
				billingSummaryButtonPanel.add(paymentType,"wrap");
				
				if(billSource.equalsIgnoreCase(ConstantUtils.GOLD_RETAIL_NONGST) || 
						billSource.equalsIgnoreCase(ConstantUtils.SILVER_RETAIL_NONGST) ||
						
						billSource.equalsIgnoreCase(ConstantUtils.RETAIL_BILLING_NONGST)
						) {
					paymentType.setSelectedIndex(0);//Cash
				}else {
					paymentType.setSelectedIndex(1);//Bank
				}
				
			}else if(		
					billSource.equalsIgnoreCase(ConstantUtils.GOLD_SUPPLIER_GST) ||
					billSource.equalsIgnoreCase(ConstantUtils.SILVER_SUPPLIER_GST) ||
					
					billSource.equalsIgnoreCase(ConstantUtils.PURCHASE_BILLING_GST)
					) {
				billingSummaryButtonPanel.add(new MyLabel("Cash Paid :"));
				billingSummaryButtonPanel.add(cashReceived);
				billingSummaryButtonPanel.add(new MyLabel("Payment Type :"));
				billingSummaryButtonPanel.add(paymentType,"wrap");
				paymentType.setSelectedIndex(1);//Bank
			}
			
			if(billSource.equalsIgnoreCase(ConstantUtils.GOLD_WHOLESALE_NONGST) ||				
					billSource.equalsIgnoreCase(ConstantUtils.SILVER_WHOLESALE_NONGST) ||
					
					billSource.equalsIgnoreCase(ConstantUtils.WHOLESALE_BILLING_NONGST)
					){
				billingSummaryButtonPanel.add(new MyLabel("Cash Received :"));
				billingSummaryButtonPanel.add(cashReceived);
				billingSummaryButtonPanel.add(new MyLabel("Payment Type :"));
				billingSummaryButtonPanel.add(paymentType,"wrap");
				billingSummaryButtonPanel.add(new MyLabel("Metal Received :"));
				billingSummaryButtonPanel.add(metalReceived,"wrap");
				paymentType.setSelectedIndex(0);//Cash
				
			}else if(billSource.equalsIgnoreCase(ConstantUtils.GOLD_SUPPLIER_NONGST) ||					
					billSource.equalsIgnoreCase(ConstantUtils.SILVER_SUPPLIER_NONGST) ||
					
					billSource.equalsIgnoreCase(ConstantUtils.PURCHASE_BILLING_NONGST)
					){
				billingSummaryButtonPanel.add(new MyLabel("Cash Paid :"));
				billingSummaryButtonPanel.add(cashReceived);
				billingSummaryButtonPanel.add(new MyLabel("Payment Type :"));
				billingSummaryButtonPanel.add(paymentType,"wrap");
				billingSummaryButtonPanel.add(new MyLabel("Metal Paid :"));
				billingSummaryButtonPanel.add(metalReceived,"wrap");
				paymentType.setSelectedIndex(0);//Cash
			}
			
			billingSummaryButtonPanel.add(saveBill);
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private void tableModelling(JTable jTable){
		try{
			
			reportModel = new DefaultTableModel() {

				public Class<?> getColumnClass(int column) {
					switch (column) {
					
					case 0:
						return String.class;//Record Id -- BillDetailId
					case 1:
						return String.class;//ornament Id --
					case 2:
						return String.class;//SNO
					case 3:
						return String.class;//Bill or Return flag
					case 4:
						return String.class;//Display #
					case 5:
						return String.class;//Ornament Name
					case 6:
						return String.class;//No Of Piece
					case 7:
						return String.class;//Gross Weight
					case 8:
						return String.class;//Weight Less
					case 9:
						return String.class;//Net Weight --Weight
					case 10:
						return String.class;//purity
					case 11:
						return String.class;//pure weight
					case 12:
						return String.class;//wastage
					case 13:
						return String.class;//MC Rate
					case 14:
						return String.class;//MC Per Piece
					case 15:
						return String.class;//MC Amount
					case 16:
						return String.class;//Rate
					case 17:
						return String.class;//Tax Percentage
					case 18:
						return String.class;//Ornament Amt
					case 19:
						return String.class;//Tax Amt
					case 20:
						return String.class;//Round off
					case 21:
						return String.class;//Amount
					default:
						return String.class;
					}
				}
			};
			jTable.setModel(reportModel);
			reportModel.addColumn("recordId");
			reportModel.addColumn("ornamentId");
			reportModel.addColumn("sno");
			reportModel.addColumn("SOURCE");
			
			
			reportModel.addColumn("#");
        	reportModel.addColumn("Ornament Name");
        	reportModel.addColumn("# piece");
        	reportModel.addColumn("Gross Weight");
        	reportModel.addColumn("Weight Less");
        	reportModel.addColumn("Net Weight");
        	reportModel.addColumn("Purity");
        	reportModel.addColumn("Pure Weight");
        	reportModel.addColumn("Wastage");
        	reportModel.addColumn("MC Rate");
        	reportModel.addColumn("MC Per Piece");
        	reportModel.addColumn("MC Amount");
        	reportModel.addColumn("Rate");
        	reportModel.addColumn("Tax Percentage");
        	reportModel.addColumn("Ornament Amount");
        	reportModel.addColumn("Tax Amount");
        	reportModel.addColumn("Round off");
        	reportModel.addColumn("Amount");
        	
        	jTable.getColumn("recordId").setPreferredWidth(0);
        	jTable.getColumn("recordId").setMinWidth(0);
        	jTable.getColumn("recordId").setMaxWidth(0);
        	
        	jTable.getColumn("ornamentId").setPreferredWidth(0);
        	jTable.getColumn("ornamentId").setMinWidth(0);
        	jTable.getColumn("ornamentId").setMaxWidth(0);
        	
        	jTable.getColumn("sno").setPreferredWidth(0);
        	jTable.getColumn("sno").setMinWidth(0);
        	jTable.getColumn("sno").setMaxWidth(0);
        	
        	jTable.getColumn("SOURCE").setPreferredWidth(0);
        	jTable.getColumn("SOURCE").setMinWidth(0);
        	jTable.getColumn("SOURCE").setMaxWidth(0);
        	
        	if(billSource.equalsIgnoreCase(ConstantUtils.GOLD_WHOLESALE_NONGST) ||
        			billSource.equalsIgnoreCase(ConstantUtils.GOLD_SUPPLIER_NONGST) ||
        			
        			billSource.equalsIgnoreCase(ConstantUtils.WHOLESALE_BILLING_NONGST) ||
        			billSource.equalsIgnoreCase(ConstantUtils.PURCHASE_BILLING_NONGST)
        			){
        		jTable.getColumn("Wastage").setPreferredWidth(0);
            	jTable.getColumn("Wastage").setMinWidth(0);
            	jTable.getColumn("Wastage").setMaxWidth(0);
            	
            	jTable.getColumn("MC Per Piece").setPreferredWidth(0);
            	jTable.getColumn("MC Per Piece").setMinWidth(0);
            	jTable.getColumn("MC Per Piece").setMaxWidth(0);
            
            	jTable.getColumn("Tax Percentage").setPreferredWidth(0);
            	jTable.getColumn("Tax Percentage").setMinWidth(0);
            	jTable.getColumn("Tax Percentage").setMaxWidth(0);
            	
            
            	jTable.getColumn("Tax Amount").setPreferredWidth(0);
            	jTable.getColumn("Tax Amount").setMinWidth(0);
            	jTable.getColumn("Tax Amount").setMaxWidth(0);
            	
        	}else if(billSource.equalsIgnoreCase(ConstantUtils.SILVER_WHOLESALE_NONGST) ||
        			billSource.equalsIgnoreCase(ConstantUtils.SILVER_SUPPLIER_NONGST) ||
        			
        			billSource.equalsIgnoreCase(ConstantUtils.WHOLESALE_BILLING_NONGST) ||
        			billSource.equalsIgnoreCase(ConstantUtils.PURCHASE_BILLING_NONGST)
        			){
        		jTable.getColumn("Wastage").setPreferredWidth(0);
            	jTable.getColumn("Wastage").setMinWidth(0);
            	jTable.getColumn("Wastage").setMaxWidth(0);
            	
            	jTable.getColumn("Tax Percentage").setPreferredWidth(0);
            	jTable.getColumn("Tax Percentage").setMinWidth(0);
            	jTable.getColumn("Tax Percentage").setMaxWidth(0);
            	
            	jTable.getColumn("Tax Amount").setPreferredWidth(0);
            	jTable.getColumn("Tax Amount").setMinWidth(0);
            	jTable.getColumn("Tax Amount").setMaxWidth(0);
        	}else if(billSource.equalsIgnoreCase(ConstantUtils.SILVER_WHOLESALE_GST) ||
        			billSource.equalsIgnoreCase(ConstantUtils.GOLD_WHOLESALE_GST) ||
        			billSource.equalsIgnoreCase(ConstantUtils.GOLD_SUPPLIER_GST) ||
        			billSource.equalsIgnoreCase(ConstantUtils.SILVER_SUPPLIER_GST) ||
        			
        			billSource.equalsIgnoreCase(ConstantUtils.WHOLESALE_BILLING_GST) ||
        			billSource.equalsIgnoreCase(ConstantUtils.PURCHASE_BILLING_GST)
        			){
        		jTable.getColumn("Gross Weight").setPreferredWidth(0);
            	jTable.getColumn("Gross Weight").setMinWidth(0);
            	jTable.getColumn("Gross Weight").setMaxWidth(0);
            	
            	jTable.getColumn("Weight Less").setPreferredWidth(0);
            	jTable.getColumn("Weight Less").setMinWidth(0);
            	jTable.getColumn("Weight Less").setMaxWidth(0);
            	
            	jTable.getColumn("Purity").setPreferredWidth(0);
            	jTable.getColumn("Purity").setMinWidth(0);
            	jTable.getColumn("Purity").setMaxWidth(0);
            	
            	jTable.getColumn("Pure Weight").setPreferredWidth(0);
            	jTable.getColumn("Pure Weight").setMinWidth(0);
            	jTable.getColumn("Pure Weight").setMaxWidth(0);
            	
            	jTable.getColumn("Wastage").setPreferredWidth(0);
            	jTable.getColumn("Wastage").setMinWidth(0);
            	jTable.getColumn("Wastage").setMaxWidth(0);
            
            	jTable.getColumn("MC Rate").setPreferredWidth(0);
            	jTable.getColumn("MC Rate").setMinWidth(0);
            	jTable.getColumn("MC Rate").setMaxWidth(0);
            	
            	jTable.getColumn("MC Per Piece").setPreferredWidth(0);
            	jTable.getColumn("MC Per Piece").setMinWidth(0);
            	jTable.getColumn("MC Per Piece").setMaxWidth(0);
            	
            	jTable.getColumn("MC Amount").setPreferredWidth(0);
            	jTable.getColumn("MC Amount").setMinWidth(0);
            	jTable.getColumn("MC Amount").setMaxWidth(0);
        	}
        	
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private void setModel(JComboBox combo,JTextField tf,DefaultComboBoxModel mdl, String str) {
        combo.setModel(mdl);
        combo.setSelectedIndex(-1);
        tf.setText(str);
	}
	
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
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
	public void keyPressed(KeyEvent evt) {
		// TODO Auto-generated method stub
		if(evt.getSource() == tfContact){
			contactKeyPressedEvent(evt);
		}else if(evt.getSource() == tfOrnament){
			ornamentKeyPressedEvent(evt);
		}else if(evt.getSource() == tfMetal) {
			metalKeyPressedEvent(evt);
		}else if(evt.getSource() == directAmount){
			if(billType.equalsIgnoreCase(ConstantUtils.GST_ENTRY)){
				directAmountChangedGST();
			}
		}else if(evt.getSource() == taxPercentage){
			if(billType.equalsIgnoreCase(ConstantUtils.GST_ENTRY)){
				taxPercenatageChangedGST();
			}
		}else if(evt.getSource() == netWeight){
			if(billType.equalsIgnoreCase(ConstantUtils.GST_ENTRY)){
				netWeightChangedGST();
			}else if(billType.equalsIgnoreCase(ConstantUtils.NON_GST_ENTRY) && metal.equalsIgnoreCase(ConstantUtils.GOLD)){
				netWeightChangedNONGSTGold();
			}else if(billType.equalsIgnoreCase(ConstantUtils.NON_GST_ENTRY) && metal.equalsIgnoreCase(ConstantUtils.SILVER)){
				netWeightChangedNONGSTSilver();
			}
		}else if(evt.getSource() == rate){
			if(billType.equalsIgnoreCase(ConstantUtils.GST_ENTRY)){
				rateChangedGST();
			}else if(billType.equalsIgnoreCase(ConstantUtils.NON_GST_ENTRY) && metal.equalsIgnoreCase(ConstantUtils.GOLD)){
				rateChangedNONGSTGold();
			}else if(billType.equalsIgnoreCase(ConstantUtils.NON_GST_ENTRY) && metal.equalsIgnoreCase(ConstantUtils.SILVER)){
				rateChangedNONGSTSilver();
			}
		}else if(evt.getSource() == amount){
			System.out.println("keyPressed");
			if(evt.getKeyCode() == KeyEvent.VK_ENTER){
				addOrnamentToList();
			}
		}else if(evt.getSource() == grossWeight){
			if(billType.equalsIgnoreCase(ConstantUtils.NON_GST_ENTRY) && metal.equalsIgnoreCase(ConstantUtils.GOLD)){
				grossWeightChangedNONGSTGold();
			}else if(billType.equalsIgnoreCase(ConstantUtils.NON_GST_ENTRY) && metal.equalsIgnoreCase(ConstantUtils.SILVER)){
				grossWeightChangedNONGSTSilver();
			}
		}else if(evt.getSource() == lessWeight){
			if(billType.equalsIgnoreCase(ConstantUtils.NON_GST_ENTRY) && metal.equalsIgnoreCase(ConstantUtils.GOLD)){
				lessWeightChangedNONGSTGold();
			}else if(billType.equalsIgnoreCase(ConstantUtils.NON_GST_ENTRY) && metal.equalsIgnoreCase(ConstantUtils.SILVER)){
				lessWeightChangedNONGSTSilver();
			}
		}else if(evt.getSource() ==purity){
			if(billType.equalsIgnoreCase(ConstantUtils.NON_GST_ENTRY) && metal.equalsIgnoreCase(ConstantUtils.GOLD)){
				purityChangedNONGSTGold();
			}else if(billType.equalsIgnoreCase(ConstantUtils.NON_GST_ENTRY) && metal.equalsIgnoreCase(ConstantUtils.SILVER)){
				purityChangedNONGSTSilver();
			}
		}else if(evt.getSource() == pureWeight){
			if(billType.equalsIgnoreCase(ConstantUtils.NON_GST_ENTRY) && metal.equalsIgnoreCase(ConstantUtils.GOLD)){
				//pureWeightChangedNONGSTGold();
			}else if(billType.equalsIgnoreCase(ConstantUtils.NON_GST_ENTRY) && metal.equalsIgnoreCase(ConstantUtils.SILVER)){
				//pureWeightChangedNONGSTSilver();
			}
			
		}else if(evt.getSource() == wastage){
			if(billType.equalsIgnoreCase(ConstantUtils.NON_GST_ENTRY) && metal.equalsIgnoreCase(ConstantUtils.GOLD)){
				wastageChangedNONGSTGold();
			}else if(billType.equalsIgnoreCase(ConstantUtils.NON_GST_ENTRY) && metal.equalsIgnoreCase(ConstantUtils.SILVER)){
				wastageChangedNONGSTSilver();
			}else if(billSource.equalsIgnoreCase(ConstantUtils.GOLD_RETAIL_GST) || billSource.equalsIgnoreCase(ConstantUtils.SILVER_RETAIL_GST)) {
				calculateAmountChangesGST();
			}
		}else if(evt.getSource() == ornamentAmount){
			if(billType.equalsIgnoreCase(ConstantUtils.NON_GST_ENTRY) && metal.equalsIgnoreCase(ConstantUtils.GOLD)){
				
			}else if(billType.equalsIgnoreCase(ConstantUtils.NON_GST_ENTRY) && metal.equalsIgnoreCase(ConstantUtils.SILVER)){
				
			}
		}else if(evt.getSource() == taxAmount){
			
		}else if(evt.getSource() == mcRate){
			if(billType.equalsIgnoreCase(ConstantUtils.NON_GST_ENTRY) && metal.equalsIgnoreCase(ConstantUtils.GOLD)){
				mcRateChangedNONGSTGold();
			}else if(billType.equalsIgnoreCase(ConstantUtils.NON_GST_ENTRY) && metal.equalsIgnoreCase(ConstantUtils.SILVER)){
				mcRateChangedNONGSTSilver();
			}
		}else if(evt.getSource() == mcPerPiece){
			if(billType.equalsIgnoreCase(ConstantUtils.NON_GST_ENTRY) && metal.equalsIgnoreCase(ConstantUtils.GOLD)){
				mcPerPieceChangedNONGSTGold();
			}else if(billType.equalsIgnoreCase(ConstantUtils.NON_GST_ENTRY) && metal.equalsIgnoreCase(ConstantUtils.SILVER)){
				mcPerPieceChangedNONGSTSilver();
			}else if(billSource.equalsIgnoreCase(ConstantUtils.GOLD_RETAIL_GST) || billSource.equalsIgnoreCase(ConstantUtils.SILVER_RETAIL_GST)) {
				calculateAmountChangesGST();
			}
		}else if(evt.getSource() == mcAmount){
			
		}else if(evt.getSource() == roundOff){
			
		}else if(evt.getSource() == amount){
			
		}else if(evt.getSource() == noOfPiece){
			if(billType.equalsIgnoreCase(ConstantUtils.NON_GST_ENTRY) && metal.equalsIgnoreCase(ConstantUtils.GOLD)){
				noofPieceChangedNONGSTGold();
			}else if(billType.equalsIgnoreCase(ConstantUtils.NON_GST_ENTRY) && metal.equalsIgnoreCase(ConstantUtils.SILVER)){
				noofPieceChangedNONGSTSilver();
			}
		}
		
		//MyTextField grossWeight,lessWeight,netWeight,purity,pureWeight,wastage,rate,taxPercentage,ornamentAmount,taxAmount,mcRate,mcPerPiece,mcAmount,roundOff,amount,
				//noOfPiece,directAmount,billdetailId;
	}

	@Override
	public void keyReleased(KeyEvent evt) {
		// TODO Auto-generated method stub
		if(evt.getSource() == directAmount){
			if(billType.equalsIgnoreCase(ConstantUtils.GST_ENTRY)){
				directAmountChangedGST();
			}
		}else if(evt.getSource() == taxPercentage){
			if(billType.equalsIgnoreCase(ConstantUtils.GST_ENTRY)){
				taxPercenatageChangedGST();
			}
		}else if(evt.getSource() == netWeight){
			if(billType.equalsIgnoreCase(ConstantUtils.GST_ENTRY)){
				netWeightChangedGST();
			}
		}else if(evt.getSource() == grossWeight){
			if(billType.equalsIgnoreCase(ConstantUtils.NON_GST_ENTRY) && metal.equalsIgnoreCase(ConstantUtils.GOLD)){
				grossWeightChangedNONGSTGold();
			}else if(billType.equalsIgnoreCase(ConstantUtils.NON_GST_ENTRY) && metal.equalsIgnoreCase(ConstantUtils.SILVER)){
				grossWeightChangedNONGSTSilver();
			}
		}else if(evt.getSource() == lessWeight){
			if(billType.equalsIgnoreCase(ConstantUtils.NON_GST_ENTRY) && metal.equalsIgnoreCase(ConstantUtils.GOLD)){
				lessWeightChangedNONGSTGold();
			}else if(billType.equalsIgnoreCase(ConstantUtils.NON_GST_ENTRY) && metal.equalsIgnoreCase(ConstantUtils.SILVER)){
				lessWeightChangedNONGSTSilver();
			}
		}else if(evt.getSource() ==purity){
			if(billType.equalsIgnoreCase(ConstantUtils.NON_GST_ENTRY) && metal.equalsIgnoreCase(ConstantUtils.GOLD)){
				purityChangedNONGSTGold();
			}else if(billType.equalsIgnoreCase(ConstantUtils.NON_GST_ENTRY) && metal.equalsIgnoreCase(ConstantUtils.SILVER)){
				purityChangedNONGSTSilver();
			}
		}else if(evt.getSource() == pureWeight){
			if(billType.equalsIgnoreCase(ConstantUtils.NON_GST_ENTRY) && metal.equalsIgnoreCase(ConstantUtils.GOLD)){
				//pureWeightChangedNONGSTGold();
			}else if(billType.equalsIgnoreCase(ConstantUtils.NON_GST_ENTRY) && metal.equalsIgnoreCase(ConstantUtils.SILVER)){
				//pureWeightChangedNONGSTSilver();
			}
			
		}else if(evt.getSource() == wastage){
			if(billType.equalsIgnoreCase(ConstantUtils.NON_GST_ENTRY) && metal.equalsIgnoreCase(ConstantUtils.GOLD)){
				wastageChangedNONGSTGold();
			}else if(billType.equalsIgnoreCase(ConstantUtils.NON_GST_ENTRY) && metal.equalsIgnoreCase(ConstantUtils.SILVER)){
				wastageChangedNONGSTSilver();
			}
		}else if(evt.getSource() == ornamentAmount){
			if(billType.equalsIgnoreCase(ConstantUtils.NON_GST_ENTRY) && metal.equalsIgnoreCase(ConstantUtils.GOLD)){
				
			}else if(billType.equalsIgnoreCase(ConstantUtils.NON_GST_ENTRY) && metal.equalsIgnoreCase(ConstantUtils.SILVER)){
				
			}
		}else if(evt.getSource() == taxAmount){
			
		}else if(evt.getSource() == mcRate){
			if(billType.equalsIgnoreCase(ConstantUtils.NON_GST_ENTRY) && metal.equalsIgnoreCase(ConstantUtils.GOLD)){
				mcRateChangedNONGSTGold();
			}else if(billType.equalsIgnoreCase(ConstantUtils.NON_GST_ENTRY) && metal.equalsIgnoreCase(ConstantUtils.SILVER)){
				mcRateChangedNONGSTSilver();
			}
		}else if(evt.getSource() == mcPerPiece){
			if(billType.equalsIgnoreCase(ConstantUtils.NON_GST_ENTRY) && metal.equalsIgnoreCase(ConstantUtils.GOLD)){
				mcPerPieceChangedNONGSTGold();
			}else if(billType.equalsIgnoreCase(ConstantUtils.NON_GST_ENTRY) && metal.equalsIgnoreCase(ConstantUtils.SILVER)){
				mcPerPieceChangedNONGSTSilver();
			}
		}else if(evt.getSource() == mcAmount){
			
		}else if(evt.getSource() == roundOff){
			
		}else if(evt.getSource() == amount){
			
		}else if(evt.getSource() == noOfPiece){
			if(billType.equalsIgnoreCase(ConstantUtils.NON_GST_ENTRY) && metal.equalsIgnoreCase(ConstantUtils.GOLD)){
				noofPieceChangedNONGSTGold();
			}else if(billType.equalsIgnoreCase(ConstantUtils.NON_GST_ENTRY) && metal.equalsIgnoreCase(ConstantUtils.SILVER)){
				noofPieceChangedNONGSTSilver();
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent evt) {
		// TODO Auto-generated method stub
		if(evt.getSource()==tfContact){
			contactKeyTypedEvent(evt);
		}else if(evt.getSource()==tfOrnament){
			ornamentKeyTypedEvent(evt);
		}else if(evt.getSource() == directAmount){
			if(billType.equalsIgnoreCase(ConstantUtils.GST_ENTRY)){
				directAmountChangedGST();
			}
		}else if(evt.getSource() == taxPercentage){
			if(billType.equalsIgnoreCase(ConstantUtils.GST_ENTRY)){
				taxPercenatageChangedGST();
			}
		}else if(evt.getSource() == netWeight){
			if(billType.equalsIgnoreCase(ConstantUtils.GST_ENTRY)){
				netWeightChangedGST();
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
		onClickEvent(evt.getSource());
	}

	@Override
	public void changedUpdate(DocumentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insertUpdate(DocumentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeUpdate(DocumentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	private void onClickEvent(Object evt){
		if(evt == contact){
			((Component) evt).transferFocus();
		}else if(evt == billingDate){
			if(metalList!=null && metalList.size()>1) {
				metalType.transferFocus();
			}else {
				ornament.transferFocus();
			}
		}else if(evt == ornamentMode){
			changeOrnmentEntryMode();			
		}else if(evt == editRecord){
			editOrnament();
		}else if(evt == deleteRecord){
			deleteOrnament();
		}else if(evt == saveBill){
			saveOrUpdateBill();
		}else if(evt == addContact) {
			addContactWindow();
		}else if(evt == saleEntry) {
			changeOrnmentEntryMode(ConstantUtils.ORNAMENT_MODE_SALE);
		}else if(evt == returnEntry) {
			changeOrnmentEntryMode(ConstantUtils.ORNAMENT_MODE_RETURN);
		}else if(evt == oldEntry) {
			changeOrnmentEntryMode(ConstantUtils.ORNAMENT_MODE_OLD);
		}else if(evt == grossWeight) {
			//weightLessPopup();
		}
		
		System.out.println(evt);
	}
	
	private static DefaultComboBoxModel getContactSuggestedModel(List<Contact> list, String text) {
        DefaultComboBoxModel m = new DefaultComboBoxModel();
        for(Contact s: list) {
            if(s.getContactName().toUpperCase().startsWith(text.toUpperCase())) m.addElement(s);
        }
        return m;
	}
	
	private static DefaultComboBoxModel getOrnamentSuggestedModel(List<Ornament> list, String text) {
        DefaultComboBoxModel m = new DefaultComboBoxModel();
        for(Ornament s: list) {
            if(s.getOrnamentName().toUpperCase().startsWith(text.toUpperCase())) m.addElement(s);
        }
        return m;
	}
	
	private static DefaultComboBoxModel getMetalSuggestedModel(List<Metal> list, String text) {
        DefaultComboBoxModel m = new DefaultComboBoxModel();
        for(Metal s: list) {
            if(s.getMetalName().toUpperCase().startsWith(text.toUpperCase())) m.addElement(s);
        }
        return m;
	}
	
	private void contactKeyTypedEvent(KeyEvent evt){
		 EventQueue.invokeLater(new Runnable() {
			 public void run() {
				 String text = tfContact.getText();
				 if(text.length()==0) {
					 contact.hidePopup();
					 setModel(contact,tfContact,new DefaultComboBoxModel(new Vector<>(contactList)), "");
				 }else{
					 DefaultComboBoxModel m = getContactSuggestedModel(contactList, text);
					 if(m.getSize()==0 || tfContact_hide_flag) {
                        contact.hidePopup();
                        tfContact_hide_flag = false;
					 }else{
                      setModel(contact,tfContact,m, text);
                      contact.showPopup();
					 }
				 }
			 }
		 });
		 
	 }
	
	private void ornamentKeyTypedEvent(KeyEvent evt){
		 EventQueue.invokeLater(new Runnable() {
			 public void run() {
				 String text = tfOrnament.getText();
				 if(text.length()==0) {
					 ornament.hidePopup();
					 setModel(ornament,tfOrnament,new DefaultComboBoxModel(new Vector<>(ornamentList)), "");
				 }else{
					 DefaultComboBoxModel m = getOrnamentSuggestedModel(ornamentList, text);
					 if(m.getSize()==0 || tfOrnament_hide_flag) {
                       ornament.hidePopup();
                       tfOrnament_hide_flag = false;
					 }else{
                     setModel(ornament,tfOrnament,m, text);
                     ornament.showPopup();
					 }
				 }
			 }
		 });
		 
	 }
	
	private void metalKeyTypedEvent(KeyEvent evt){
		 EventQueue.invokeLater(new Runnable() {
			 public void run() {
				 String text = tfMetal.getText();
				 if(text.length()==0) {
					 metalType.hidePopup();
					 setModel(metalType,tfMetal,new DefaultComboBoxModel(new Vector<>(metalList)), "");
				 }else{
					 DefaultComboBoxModel m = getMetalSuggestedModel(metalList, text);
					 if(m.getSize()==0 || tfMetal_hide_flag) {
                      metalType.hidePopup();
                      tfMetal_hide_flag = false;
					 }else{
                    setModel(metalType,tfMetal,m, text);
                    metalType.showPopup();
					 }
				 }
			 }
		 });
		 
	 }
	
	private void contactKeyPressedEvent(KeyEvent evt){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				String text = tfContact.getText();
		        int code = evt.getKeyCode();
		        if(code==KeyEvent.VK_ENTER) {
		        	if(!text.equalsIgnoreCase(ConstantUtils.BLANK)){
		        		boolean createContactFlag=true;
		        		for(Contact contactobj:contactList){
		        			if(createContactFlag && contactobj.getContactName().toUpperCase().equalsIgnoreCase(text.toUpperCase())){
		        				createContactFlag=false;
		        			}
		        		}
		        	}
		        	setContactInformation();
		        	tfContact_hide_flag=true;
		        	//((Component) evt.getSource()).transferFocus();
		        }else if(code==KeyEvent.VK_ESCAPE) {
		        	tfContact_hide_flag=true;
		        	setContactInformation();
		        }else if(code==KeyEvent.VK_RIGHT) {
		        	for(Contact obj:contactList) {
		                String str = obj.getContactName();
		                if(str.toUpperCase().startsWith(text.toUpperCase())) {
		                	setContactInformation();
		                    contact.setSelectedIndex(-1);
		                    tfContact.setText(str);
		                    return;
		                }
		            }
		        }
			}
		});
	}
	
	private void ornamentKeyPressedEvent(KeyEvent evt){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				String text = tfOrnament.getText();
		        int code = evt.getKeyCode();
		        if(code==KeyEvent.VK_ENTER) {
		        	if(!text.equalsIgnoreCase(ConstantUtils.BLANK)){
		        		boolean createOrnamentFlag=true;
		        		for(Ornament ornamentobj:ornamentList){
		        			if(createOrnamentFlag && ornamentobj.getOrnamentName().toUpperCase().equalsIgnoreCase(text.toUpperCase())){
		        				createOrnamentFlag=false;
		        			}
		        		}
		        	}
		        	setOrnamentInformation();
		        	tfOrnament_hide_flag=true;
		        	((Component) evt.getSource()).transferFocus();
		        }else if(code==KeyEvent.VK_ESCAPE) {
		        	tfOrnament_hide_flag=true;
		        	setOrnamentInformation();
		        }else if(code==KeyEvent.VK_RIGHT) {
		        	for(Ornament obj:ornamentList) {
		                String str = obj.getOrnamentName();
		                if(str.toUpperCase().startsWith(text.toUpperCase())) {
		                	setOrnamentInformation();
		                    ornament.setSelectedIndex(-1);
		                    tfOrnament.setText(str);
		                    ornament.hidePopup();
		                    return;
		                }
		            }
		        }
			}
		});
	}
	
	private void metalKeyPressedEvent(KeyEvent evt){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				String text = tfMetal.getText();
		        int code = evt.getKeyCode();
		        if(code==KeyEvent.VK_ENTER) {
		        	if(!text.equalsIgnoreCase(ConstantUtils.BLANK)){
		        		boolean createMetalFlag=true;
		        		for(Metal metalobj:metalList){
		        			if(createMetalFlag && metalobj.getMetalName().toUpperCase().equalsIgnoreCase(text.toUpperCase())){
		        				createMetalFlag=false;
		        			}
		        		}
		        	}
		        	setMetalInformation();
		        	tfMetal_hide_flag=true;
		        	((Component) evt.getSource()).transferFocus();
		        }else if(code==KeyEvent.VK_ESCAPE) {
		        	tfMetal_hide_flag=true;
		        	setMetalInformation();
		        }else if(code==KeyEvent.VK_RIGHT) {
		        	for(Metal obj:metalList) {
		                String str = obj.getMetalName();
		                if(str.toUpperCase().startsWith(text.toUpperCase())) {
		                	setMetalInformation();
		                    metalType.setSelectedIndex(-1);
		                    tfMetal.setText(str);
		                    return;
		                }
		            }
		        }
			}
		});
	}
	
	private void setContactInformation(){
		try{
			if(contact.getSelectedIndex()!=-1){
				Contact selectedContact = (Contact)contact.getSelectedItem();
				
				if(selectedContact!=null){
					formContactDetailPanel(selectedContact);
					enableDisableComponents(billingOrnamentPanel,true);
					billingDate.transferFocus();
				}
			}else{
				formContactDetailPanel(null);
				enableDisableComponents(billingOrnamentPanel,false);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private void setOrnamentInformation(){
		try{
			if(ornament.getSelectedIndex()!=-1){
				Ornament selectedOrnament = (Ornament)ornament.getSelectedItem();
				
				if(selectedOrnament!=null){
					if(selectedOrnament.getGstPercentage()!=null){
						taxPercentage.setText(selectedOrnament.getGstPercentage().toString());
					}
					
					//Get the last saved purity for the selected customer
					if(billList!=null && billList.size()>0) {
						List<BillDetails> billDetailsList = billList.stream().flatMap(s-> s.getBillDetailsList().stream())
								.collect(Collectors.toList())
								;
						if(billDetailsList!=null) {
						
						//list.sort(Comparator.comparing(o -> o.getItem().getValue()));
						//paymentDTOList.sort(Comparator.comparing(PaymentDTOList::getDateForSort).thenComparing(PaymentDTOList::getBillNumber,Comparator.nullsLast(Comparator.naturalOrder())));
						billDetailsList = billDetailsList
						.stream()
						.sorted(Comparator.comparing(BillDetails::getBillDetailsId).reversed())
						.collect(Collectors.toList())
						;
						
						BillDetails billDetailsObj = billDetailsList.stream().filter(s -> s.getOrnament().getOrnamentId().equals(selectedOrnament.getOrnamentId())).findFirst().orElse(null);
						
						if(billDetailsObj!=null) {
							//System.out.println("billDetailsObj -"+billDetailsObj.getOrnament().getOrnamentName()+" = "+billDetailsObj.getPurity());
							purity.setText(Calculator.formatThreeDecimal(billDetailsObj.getPurity()));
						}else {
							System.out.println("Null billDetailsObj");
						}
						
						}
					}
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private void setMetalInformation(){
		try{
			if(metalType.getSelectedIndex()!=-1){
				Metal selectedMetal = (Metal)metalType.getSelectedItem();
				
				if(selectedMetal!=null){
					//Load OrnamentList -Starts
					metal = selectedMetal.getMetalName();
					ornamentList = MasterController.getInstace().getOrnamentListByMetalCode(Session.licence.getLicenceId(), selectedMetal.getMetalName());
					if(ornamentList!=null && ornamentList.size()>0) {
						setModel(ornament,tfOrnament,new DefaultComboBoxModel(new Vector<>(ornamentList)), "");
						ornament.transferFocus();
					}
					//Load Ornament List Ends
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private void formContactDetailPanel(Contact contact){
		try{
			BillSource billSourceObj = MasterController.getInstace().getBillSourceByCode(billSource);
			PaymentDTO paymentDTO = BillController.getInstace().getPaymentDTODetails(null, null, billSourceObj.getBillSourceId(), contact.getContactId(), Session.licence.getLicenceId(),billType);
			
			BalanceSheetDTO balanceSheetDTO = BalanceSheetController.getInstace().getBalanceSheetList(new Date(), Session.licence.getLicenceId(), contact.getContactType().getContactTypeName(), billSource, billType, contact.getContactId());

			System.out.println("balanceSheetDAO: "+balanceSheetDTO);
			//toDate, licenceId, contactTypeName, billSource,billType,contactId
			
			if(paymentDTO!=null && paymentDTO.getBillList()!=null && paymentDTO.getBillList().size()>0) {
				billList = paymentDTO.getBillList();
			}
			
			contactDetailPanel.removeAll();
			if(contact!=null){
				contactDetailPanel.add(new MyLabel(contact.getContactName()),"wrap");
				contactDetailPanel.add(new MyLabel("Last Bill Date:"));
				if(paymentDTO.getLastBilledDate()!=null) {
					contactDetailPanel.add(new MyLabel(StringUtils.getStrDate(paymentDTO.getLastBilledDate())));
				}
				contactDetailPanel.add(new MyLabel("Last Payment Date:"));
				if(paymentDTO.getLastPaymentDate()!=null) {
					contactDetailPanel.add(new MyLabel(StringUtils.getStrDate(paymentDTO.getLastPaymentDate())),"wrap");
				}else {
					contactDetailPanel.add(new MyLabel(""),"wrap");
				}
				contactDetailPanel.add(new MyLabel("Total Balance:"));
				/*
				 * if(paymentDTO.getAmountBalance()!=null) { contactDetailPanel.add(new
				 * MyLabel(Calculator.formatThreeDecimal(paymentDTO.getAmountBalance()))); }
				 * contactDetailPanel.add(new MyLabel("Metal Balance:"));
				 * if(paymentDTO.getMetalBalanceStr()!=null) { contactDetailPanel.add(new
				 * MyLabel(paymentDTO.getMetalBalanceStr())); }
				 */
				
				if(balanceSheetDTO.getTotalAmountBalance()!=null) {
					contactDetailPanel.add(new MyLabel(Calculator.formatThreeDecimal(balanceSheetDTO.getTotalAmountBalance())));
					openingCashBalance = Calculator.formatThreeDecimal(balanceSheetDTO.getTotalAmountBalance());
				}
				contactDetailPanel.add(new MyLabel("Metal Balance:"));
				if(balanceSheetDTO.getTotalMetalBalanceStr()!=null) {
					contactDetailPanel.add(new MyLabel(balanceSheetDTO.getTotalMetalBalanceStr()));
					openingMetalBalance = balanceSheetDTO.getTotalMetalBalanceStr();
				}
			}
			contactDetailPanel.revalidate();
			contactDetailPanel.repaint();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private int getScreenWidth(int percentage){
		int screenWidth=0;
		try{
			System.out.println("Screen width ->"+screenSize.width);
			if(percentage>0){
				screenWidth = ((screenSize.width*percentage)/100);
			}else{
				screenWidth = screenSize.width;
			}
		}catch(Exception ex){
			
		}
		System.out.println("screenWidth return ->"+screenWidth);
		return screenWidth;
	}
	
	private int getPanelWidth(JPanel panel,int percentage){
		int screenWidth = 0;
		try{
			System.out.println(panel.getName()+"<=panel widht =>"+panel.getWidth());
			System.out.println(panel.getName()+"<=panel widht =>"+panel.getHeight());
			
			if(percentage>0){
				screenWidth = ((panel.getWidth()*percentage)/100);
			}
		}catch(Exception ex){
			
		}
		System.out.println("screenWidth ->"+screenWidth);
		return screenWidth;	
	}
	
	private int getPanelHeight(JPanel panel,int percentage){
		int screenHeight = 0;
		try{
			System.out.println(panel.getName()+"<=panel height =>"+panel.getWidth());
			System.out.println(panel.getName()+"<=panel height =>"+panel.getHeight());
			
			if(percentage>0){
				screenHeight = ((panel.getWidth()*percentage)/100);
			}
		}catch(Exception ex){
			
		}
		System.out.println("screenHeight ->"+screenHeight);
		return screenHeight;	
	}
	
	private void changeOrnmentEntryMode(){
		try{
			String buttonText = ornamentMode.getText();
			if(buttonText.equalsIgnoreCase(ConstantUtils.ORNAMENT_MODE_SALE)){
				ornamentMode.setText(ConstantUtils.ORNAMENT_MODE_RETURN);
				MyPanel.setBackground(billingOrnamentPanel, Color.red);
			}else if(buttonText.equalsIgnoreCase(ConstantUtils.ORNAMENT_MODE_RETURN)){
				ornamentMode.setText(ConstantUtils.ORNAMENT_MODE_SALE);
				MyPanel.setBackground(billingOrnamentPanel, Color.WHITE);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private void changeOrnmentEntryMode(String mode){
		
		if(mode.equalsIgnoreCase(ConstantUtils.ORNAMENT_MODE_SALE)) {
			MyPanel.setBackground(billingOrnamentPanel, Color.WHITE);
			ornamentEntryMode = ConstantUtils.ORNAMENT_MODE_SALE;
		}else if(mode.equalsIgnoreCase(ConstantUtils.ORNAMENT_MODE_RETURN)) {
			MyPanel.setBackground(billingOrnamentPanel, Color.magenta);
			ornamentEntryMode = ConstantUtils.ORNAMENT_MODE_RETURN;
		}else if(mode.equalsIgnoreCase(ConstantUtils.ORNAMENT_MODE_OLD)) {
			MyPanel.setBackground(billingOrnamentPanel, Color.orange);
			ornamentEntryMode = ConstantUtils.ORNAMENT_MODE_OLD;
		}
		
	}
	
	private void enableDisableComponents(JPanel panel,boolean flag){
		try{
			for (Component cp : panel.getComponents() ){
		        cp.setEnabled(flag);
		 }
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private void directAmountChangedGST(){
		try{
			Double directAmountFilled = 0.0;
			Double taxPercentageFilled = 0.0;
			Integer getCostPrice =0;
			Double netWeightFilled =0.0;
			Double setRate =0.0,setOrnAmt=0.0,setTaxAmt =0.0,setRoundOff=0.0,setAmt=0.0;
			if(!directAmount.getText().trim().equalsIgnoreCase(ConstantUtils.BLANK)){
				directAmountFilled = Double.parseDouble(directAmount.getText().trim().toString());
			}
			
			if(!taxPercentage.getText().trim().equalsIgnoreCase(ConstantUtils.BLANK)){
				taxPercentageFilled = Double.parseDouble(taxPercentage.getText().trim().toString());
			}
			
			if(!netWeight.getText().trim().equalsIgnoreCase(ConstantUtils.BLANK)){
				netWeightFilled = Double.parseDouble(netWeight.getText().trim().toString());
			}
			
			if(directAmountFilled>0.0 && taxPercentageFilled>0.0){
				getCostPrice = Calculator.getCostPrice(directAmountFilled, taxPercentageFilled);
				
				if(getCostPrice>0.0){
					
					if(netWeightFilled>0.0){
						setRate = Calculator.getTwoDigitDecimal(getCostPrice/netWeightFilled);
						rate.setText(setRate.toString());
						
						setOrnAmt = Calculator.getTwoDigitDecimal(netWeightFilled * setRate);
						ornamentAmount.setText(setOrnAmt.toString());
						
						setTaxAmt = Calculator.getTwoDigitDecimal((setOrnAmt * taxPercentageFilled)/100);
						taxAmount.setText(setTaxAmt.toString());
						
						setAmt = Calculator.getTwoDigitDecimal(setOrnAmt+setTaxAmt);
						if(setAmt!=directAmountFilled){
							setRoundOff = Calculator.getTwoDigitDecimal(directAmountFilled - setAmt);
						}
						
						roundOff.setText(setRoundOff.toString());
						amount.setText(directAmountFilled.toString());
					}
					
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	private void calculateAmountChangesGST(){
		try{
			Double taxPercentageFilled=0.0,netWeightFilled=0.0,rateFilled=0.0,mcAmountFilled=0.0;
			Double setOrnAmt=0.0,setTaxAmt =0.0,setRoundOff=0.0,setAmt=0.0,setNetWeight=0.0;
			Double purityFilled=0.0,setPureWeight=0.0;
			Double wastageFilled =0.0;
			
			if(!taxPercentage.getText().trim().equalsIgnoreCase(ConstantUtils.BLANK)){
				taxPercentageFilled = Double.parseDouble(taxPercentage.getText().toString());
			}
			
			if(!netWeight.getText().trim().equalsIgnoreCase(ConstantUtils.BLANK)){
				netWeightFilled = Double.parseDouble(netWeight.getText().toString());
			}
			
			if(!rate.getText().trim().equalsIgnoreCase(ConstantUtils.BLANK)){
				rateFilled = Double.parseDouble(rate.getText().toString());
			}
			
			if(!mcPerPiece.getText().trim().equalsIgnoreCase(ConstantUtils.BLANK)) {
				mcAmountFilled = Double.parseDouble(mcPerPiece.getText().trim());
			}
			
			if(!purity.getText().equalsIgnoreCase(ConstantUtils.BLANK)){
				purityFilled = Double.parseDouble(purity.getText().toString());
			}if(!wastage.getText().equalsIgnoreCase(ConstantUtils.BLANK)){
				wastageFilled = Double.parseDouble(wastage.getText().toString());
				purityFilled = purityFilled+wastageFilled;
			}
			setNetWeight = netWeightFilled;
			setPureWeight = Calculator.getThreeDigitDecimal(((setNetWeight * purityFilled)/100));
			
			if(taxPercentageFilled>0.0 && netWeightFilled>0.0 && rateFilled>0.0){
				setOrnAmt = Calculator.getTwoDigitDecimal((setPureWeight * rateFilled)+mcAmountFilled);
				setTaxAmt = Calculator.getTwoDigitDecimal((setOrnAmt * taxPercentageFilled)/100);
				setAmt = (double) Math.round(setOrnAmt+setTaxAmt);
				Double setAmtTemp = Calculator.getTwoDigitDecimal(setOrnAmt+setTaxAmt);
				
				if(setAmt!=setAmtTemp){
					setRoundOff =Calculator.getTwoDigitDecimal(setAmt - setAmtTemp);
				}
				
				ornamentAmount.setText(setOrnAmt.toString());
				taxAmount.setText(setTaxAmt.toString());
				roundOff.setText(setRoundOff.toString());
				amount.setText(setAmt.toString());
				mcAmount.setText(mcAmountFilled.toString());
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	private void taxPercenatageChangedGST(){
		try{
			if(!taxPercentage.getText().trim().equalsIgnoreCase(ConstantUtils.BLANK)){
				if(!directAmount.getText().trim().equalsIgnoreCase(ConstantUtils.BLANK)){
					directAmountChangedGST();
				}else{
					calculateAmountChangesGST();
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private void netWeightChangedGST(){
		try{
			if(!netWeight.getText().trim().equalsIgnoreCase(ConstantUtils.BLANK)){
				if(!directAmount.getText().trim().equalsIgnoreCase(ConstantUtils.BLANK)){
					directAmountChangedGST();
				}else{
					calculateAmountChangesGST();
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private void rateChangedGST(){
		try{
			if(!rate.getText().trim().equalsIgnoreCase(ConstantUtils.BLANK)){
				if(!directAmount.getText().trim().equalsIgnoreCase(ConstantUtils.BLANK)){
					directAmountChangedGST();
				}else{
					calculateAmountChangesGST();
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private void addOrnamentToList(){
		try{
			boolean validFlag = validateOrnamentDetails();
			System.out.println("ValidFlag"+validFlag);
			
			if(validFlag){
				String ornamentModeText = ornamentMode.getText();
				System.out.println("ornamentModeText"+ornamentModeText);
				//if(ornamentModeText.equalsIgnoreCase(ConstantUtils.ORNAMENT_MODE_SALE)){
				if(ornamentEntryMode.equalsIgnoreCase(ConstantUtils.ORNAMENT_MODE_SALE)) {
					BillDetails obj = new BillDetails();
					if(!billdetailId.getText().equalsIgnoreCase(ConstantUtils.BLANK)){
						obj.setBillDetailsId(Integer.parseInt(billdetailId.getText().toString()));
					}
					if(!amount.getText().equalsIgnoreCase(ConstantUtils.BLANK)){
						obj.setAmount(Double.parseDouble(amount.getText().toString()));
					}
					obj.setDeletedFlag(ConstantUtils.CONSTANT_N);
					if(!directAmount.getText().equalsIgnoreCase(ConstantUtils.BLANK)){
						obj.setDirectAmount(Double.parseDouble(directAmount.getText().toString()));
					}
					if(!grossWeight.getText().equalsIgnoreCase(ConstantUtils.BLANK)){
						obj.setGrossWeight(Double.parseDouble(grossWeight.getText().toString()));
					}
					if(!mcAmount.getText().equalsIgnoreCase(ConstantUtils.BLANK)){
						obj.setMcAmount(Double.parseDouble(mcAmount.getText().toString()));
					}
					if(!mcPerPiece.getText().equalsIgnoreCase(ConstantUtils.BLANK)){
						obj.setMcPerPiece(Double.parseDouble(mcPerPiece.getText().toString()));
					}
					if(!mcRate.getText().equalsIgnoreCase(ConstantUtils.BLANK)){
						obj.setMcRate(Double.parseDouble(mcRate.getText().toString()));
					}
					if(!noOfPiece.getText().equalsIgnoreCase(ConstantUtils.BLANK)){
						obj.setNoOfPiece(Integer.parseInt(noOfPiece.getText().toString()));
					}
					if(ornament.getSelectedIndex()!=-1){
						obj.setOrnament((Ornament)ornament.getSelectedItem());
					}
					if(!ornamentAmount.getText().equalsIgnoreCase(ConstantUtils.BLANK)){
						obj.setOrnamentAmount(Double.parseDouble(ornamentAmount.getText().toString()));
					}
					if(!pureWeight.getText().equalsIgnoreCase(ConstantUtils.BLANK)){
						obj.setPureWeight(Double.parseDouble(pureWeight.getText().toString()));
					}
					if(!purity.getText().equalsIgnoreCase(ConstantUtils.BLANK)){
						obj.setPurity(Double.parseDouble(purity.getText().toString()));
					}
					if(!rate.getText().equalsIgnoreCase(ConstantUtils.BLANK)){
						obj.setRate(Double.parseDouble(rate.getText().toString()));
					}
					if(!metalBalance.getText().equalsIgnoreCase(ConstantUtils.BLANK)){
						obj.setMetalBalance(Double.parseDouble(metalBalance.getText().toString()));
					}
					if(!roundOff.getText().equalsIgnoreCase(ConstantUtils.BLANK)){
						obj.setRoundOff(Double.parseDouble(roundOff.getText().toString()));
					}
					if(billEntryMode.equalsIgnoreCase(ConstantUtils.CREATE)){
						obj.setsNo(++billSno);
					}else if(billEntryMode.equalsIgnoreCase(ConstantUtils.UPDATE)){
						obj.setsNo(loadedSno);
					}
					if(!taxAmount.getText().equalsIgnoreCase(ConstantUtils.BLANK)){
						obj.setTaxAmount(Double.parseDouble(taxAmount.getText().toString()));
					}
					if(!taxPercentage.getText().equalsIgnoreCase(ConstantUtils.BLANK)){
						obj.setTaxPercentage(Double.parseDouble(taxPercentage.getText().toString()));
					}
					if(!wastage.getText().equalsIgnoreCase(ConstantUtils.BLANK)){
						obj.setWastage(Double.parseDouble(wastage.getText().toString()));
					}
					if(!netWeight.getText().equalsIgnoreCase(ConstantUtils.BLANK)){
						obj.setWeight(Double.parseDouble(netWeight.getText().toString()));
					}
					if(!lessWeight.getText().equalsIgnoreCase(ConstantUtils.BLANK)){
						obj.setWeightLess(Double.parseDouble(lessWeight.getText().toString()));
					}
					if(billEntryMode.equalsIgnoreCase(ConstantUtils.UPDATE)){
						billDetailList.remove(loadedSno-1);
					}
					billDetailList.add(obj);
					addOrnamentToTable();
					clearOrnamentFields();
				}else if(ornamentEntryMode.equalsIgnoreCase(ConstantUtils.ORNAMENT_MODE_RETURN)){
					BillReturnDetails obj = new BillReturnDetails();
					if(!billReturndetailId.getText().equalsIgnoreCase(ConstantUtils.BLANK)){
						obj.setBillReturnDetailsId(Integer.parseInt(billReturndetailId.getText().toString()));
					}
					if(!amount.getText().equalsIgnoreCase(ConstantUtils.BLANK)){
						obj.setAmount(Double.parseDouble(amount.getText().toString()));
					}
					obj.setDeletedFlag(ConstantUtils.CONSTANT_N);
					if(!directAmount.getText().equalsIgnoreCase(ConstantUtils.BLANK)){
						obj.setDirectAmount(Double.parseDouble(directAmount.getText().toString()));
					}
					if(!grossWeight.getText().equalsIgnoreCase(ConstantUtils.BLANK)){
						obj.setGrossWeight(Double.parseDouble(grossWeight.getText().toString()));
					}
					if(!mcAmount.getText().equalsIgnoreCase(ConstantUtils.BLANK)){
						obj.setMcAmount(Double.parseDouble(mcAmount.getText().toString()));
					}
					if(!mcPerPiece.getText().equalsIgnoreCase(ConstantUtils.BLANK)){
						obj.setMcPerPiece(Double.parseDouble(mcPerPiece.getText().toString()));
					}
					if(!mcRate.getText().equalsIgnoreCase(ConstantUtils.BLANK)){
						obj.setMcRate(Double.parseDouble(mcRate.getText().toString()));
					}
					if(!noOfPiece.getText().equalsIgnoreCase(ConstantUtils.BLANK)){
						obj.setNoOfPiece(Integer.parseInt(noOfPiece.getText().toString()));
					}
					if(ornament.getSelectedIndex()!=-1){
						obj.setOrnament((Ornament)ornament.getSelectedItem());
					}
					if(!ornamentAmount.getText().equalsIgnoreCase(ConstantUtils.BLANK)){
						obj.setOrnamentAmount(Double.parseDouble(ornamentAmount.getText().toString()));
					}
					if(!pureWeight.getText().equalsIgnoreCase(ConstantUtils.BLANK)){
						obj.setPureWeight(Double.parseDouble(pureWeight.getText().toString()));
					}
					if(!purity.getText().equalsIgnoreCase(ConstantUtils.BLANK)){
						obj.setPurity(Double.parseDouble(purity.getText().toString()));
					}
					if(!rate.getText().equalsIgnoreCase(ConstantUtils.BLANK)){
						obj.setRate(Double.parseDouble(rate.getText().toString()));
					}
					if(!metalBalance.getText().equalsIgnoreCase(ConstantUtils.BLANK)){
						obj.setMetalBalance(Double.parseDouble(metalBalance.getText().toString()));
					}
					if(!roundOff.getText().equalsIgnoreCase(ConstantUtils.BLANK)){
						obj.setRoundOff(Double.parseDouble(roundOff.getText().toString()));
					}
					if(billEntryMode.equalsIgnoreCase(ConstantUtils.CREATE)){
						obj.setsNo(++returnSno);
					}else if(billEntryMode.equalsIgnoreCase(ConstantUtils.UPDATE)){
						obj.setsNo(loadedReturnSno);
					}
					if(!taxAmount.getText().equalsIgnoreCase(ConstantUtils.BLANK)){
						obj.setTaxAmount(Double.parseDouble(taxAmount.getText().toString()));
					}
					if(!taxPercentage.getText().equalsIgnoreCase(ConstantUtils.BLANK)){
						obj.setTaxPercentage(Double.parseDouble(taxPercentage.getText().toString()));
					}
					if(!wastage.getText().equalsIgnoreCase(ConstantUtils.BLANK)){
						obj.setWastage(Double.parseDouble(wastage.getText().toString()));
					}
					if(!netWeight.getText().equalsIgnoreCase(ConstantUtils.BLANK)){
						obj.setWeight(Double.parseDouble(netWeight.getText().toString()));
					}
					if(!lessWeight.getText().equalsIgnoreCase(ConstantUtils.BLANK)){
						obj.setWeightLess(Double.parseDouble(lessWeight.getText().toString()));
					}
					if(billEntryMode.equalsIgnoreCase(ConstantUtils.UPDATE)){
						billReturnDetailList.remove(loadedReturnSno-1);
					}
					if(Session.licence.getReturnItemConfig()!=null) {
						obj.setAddedToStock(Session.licence.getReturnItemConfig());
					}
					billReturnDetailList.add(obj);
					addOrnamentToTable();
					clearOrnamentFields();
				}else if(ornamentEntryMode.equalsIgnoreCase(ConstantUtils.ORNAMENT_MODE_OLD)){
					OldItemDetails obj = new OldItemDetails();
					if(!oldItemDetailsId.getText().equalsIgnoreCase(ConstantUtils.BLANK)){
						obj.setOldDetailsId(Integer.parseInt(oldItemDetailsId.getText().toString()));
					}
					if(!amount.getText().equalsIgnoreCase(ConstantUtils.BLANK)){
						obj.setAmount(Double.parseDouble(amount.getText().toString()));
					}
					obj.setDeletedFlag(ConstantUtils.CONSTANT_N);
					if(!directAmount.getText().equalsIgnoreCase(ConstantUtils.BLANK)){
						obj.setDirectAmount(Double.parseDouble(directAmount.getText().toString()));
					}
					if(!grossWeight.getText().equalsIgnoreCase(ConstantUtils.BLANK)){
						obj.setGrossWeight(Double.parseDouble(grossWeight.getText().toString()));
					}
					if(!mcAmount.getText().equalsIgnoreCase(ConstantUtils.BLANK)){
						obj.setMcAmount(Double.parseDouble(mcAmount.getText().toString()));
					}
					if(!mcPerPiece.getText().equalsIgnoreCase(ConstantUtils.BLANK)){
						obj.setMcPerPiece(Double.parseDouble(mcPerPiece.getText().toString()));
					}
					if(!mcRate.getText().equalsIgnoreCase(ConstantUtils.BLANK)){
						obj.setMcRate(Double.parseDouble(mcRate.getText().toString()));
					}
					if(!noOfPiece.getText().equalsIgnoreCase(ConstantUtils.BLANK)){
						obj.setNoOfPiece(Integer.parseInt(noOfPiece.getText().toString()));
					}
					if(ornament.getSelectedIndex()!=-1){
						obj.setOrnament((Ornament)ornament.getSelectedItem());
					}
					if(!ornamentAmount.getText().equalsIgnoreCase(ConstantUtils.BLANK)){
						obj.setOrnamentAmount(Double.parseDouble(ornamentAmount.getText().toString()));
					}
					if(!pureWeight.getText().equalsIgnoreCase(ConstantUtils.BLANK)){
						obj.setPureWeight(Double.parseDouble(pureWeight.getText().toString()));
					}
					if(!purity.getText().equalsIgnoreCase(ConstantUtils.BLANK)){
						obj.setPurity(Double.parseDouble(purity.getText().toString()));
					}
					if(!rate.getText().equalsIgnoreCase(ConstantUtils.BLANK)){
						obj.setRate(Double.parseDouble(rate.getText().toString()));
					}
					if(!metalBalance.getText().equalsIgnoreCase(ConstantUtils.BLANK)){
						obj.setMetalBalance(Double.parseDouble(metalBalance.getText().toString()));
					}
					if(!roundOff.getText().equalsIgnoreCase(ConstantUtils.BLANK)){
						obj.setRoundOff(Double.parseDouble(roundOff.getText().toString()));
					}
					if(billEntryMode.equalsIgnoreCase(ConstantUtils.CREATE)){
						obj.setsNo(++oldSno);
					}else if(billEntryMode.equalsIgnoreCase(ConstantUtils.UPDATE)){
						obj.setsNo(loadedOldSno);
					}
					if(!taxAmount.getText().equalsIgnoreCase(ConstantUtils.BLANK)){
						obj.setTaxAmount(Double.parseDouble(taxAmount.getText().toString()));
					}
					if(!taxPercentage.getText().equalsIgnoreCase(ConstantUtils.BLANK)){
						obj.setTaxPercentage(Double.parseDouble(taxPercentage.getText().toString()));
					}
					if(!wastage.getText().equalsIgnoreCase(ConstantUtils.BLANK)){
						obj.setWastage(Double.parseDouble(wastage.getText().toString()));
					}
					if(!netWeight.getText().equalsIgnoreCase(ConstantUtils.BLANK)){
						obj.setWeight(Double.parseDouble(netWeight.getText().toString()));
					}
					if(!lessWeight.getText().equalsIgnoreCase(ConstantUtils.BLANK)){
						obj.setWeightLess(Double.parseDouble(lessWeight.getText().toString()));
					}
					if(billEntryMode.equalsIgnoreCase(ConstantUtils.UPDATE)){
						oldItemDetailsList.remove(loadedOldSno-1);
					}
					oldItemDetailsList.add(obj);
					addOrnamentToTable();
					clearOrnamentFields();
				}
			
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private boolean validateOrnamentDetails(){
		boolean flag=true;
		StringBuffer errorMessage=new StringBuffer();
		try{
			if(billType.equalsIgnoreCase(ConstantUtils.GST_ENTRY)){
				
				if(ornament.getSelectedIndex()==-1){
					errorMessage.append("Ornament,");
				}
				
				if(netWeight.getText().equalsIgnoreCase(ConstantUtils.BLANK)){
					errorMessage.append("NetWeight,");
				}
				
				if(rate.getText().equalsIgnoreCase(ConstantUtils.BLANK)){
					errorMessage.append("Rate,");
				}
				
				if(ornamentAmount.getText().equalsIgnoreCase(ConstantUtils.BLANK)){
					errorMessage.append("Ornament Amount,");
				}
				
				if(amount.getText().equalsIgnoreCase(ConstantUtils.BLANK)){
					errorMessage.append("Amount,");
				}
			}else{
				
			}
			
			Integer size = getBillDetailListSize();
			System.out.println("BillDetail Size"+size);
			if(size>=12){
				flag = false;
				errorMessage.append("Maximum 12 Ornament can be added in single Bill,");
			}
			
			if(!errorMessage.toString().equals(ConstantUtils.BLANK)){
				flag=false;
				
				String errorMessageDisplay = "Please correct the following Validation: \n"+errorMessage.toString().replaceAll(",", "\n");
				JOptionPane.showMessageDialog(null, errorMessageDisplay);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return flag;
	}
	private void addOrnamentToTable(){
		try{
			int rowCount = reportModel.getRowCount();
            for (int i = 0; i < rowCount; i++) {
            	reportModel.removeRow(0);
            }

            int counter=0;
            billDetailList.sort(Comparator.comparing(BillDetails::getsNo));
            if(billDetailList!=null && billDetailList.size()>0){
            	for(BillDetails data:billDetailList){
            		if(data.getDeletedFlag().equalsIgnoreCase(ConstantUtils.CONSTANT_N)){
	            		reportModel.addRow(new Object[]{
	            				data.getBillDetailsId(),
	            				data.getOrnament().getOrnamentId(),
	            				data.getsNo(),
	            				ConstantUtils.ORNAMENT_MODE_SALE,	            				
	            				++counter,
	            				data.getOrnament().getOrnamentName(),
	            				data.getNoOfPiece(),
	            				data.getGrossWeight(),
	            				data.getWeightLess(),
	            				data.getWeight(),
	            				data.getPurity(),
	            				data.getPureWeight(),
	            				data.getWastage(),
	            				data.getMcRate(),
	            				data.getMcPerPiece(),
	            				data.getMcAmount(),
	            				data.getRate(),
	            				data.getTaxPercentage(),
	            				data.getOrnamentAmount(),
	            				data.getTaxAmount(),
	            				data.getRoundOff(),
	            				data.getAmount()
	            		});
            		}
            	}
            	//calculateBillSummary();
            }
            counter=0;
            if(billReturnDetailList!=null && billReturnDetailList.size()>0){
            	reportModel.addRow(new Object[] {
            			"","","","","","RETURN"
            	});
            	for(BillReturnDetails data:billReturnDetailList){
            		if(data.getDeletedFlag().equalsIgnoreCase(ConstantUtils.CONSTANT_N)){
	            		reportModel.addRow(new Object[]{
	            				data.getBillReturnDetailsId(),
	            				data.getOrnament().getOrnamentId(),
	            				data.getsNo(),
	            				ConstantUtils.ORNAMENT_MODE_RETURN,	            				
	            				++counter,
	            				data.getOrnament().getOrnamentName(),
	            				data.getNoOfPiece(),
	            				data.getGrossWeight(),
	            				data.getWeightLess(),
	            				data.getWeight(),
	            				data.getPurity(),
	            				data.getPureWeight(),
	            				data.getWastage(),
	            				data.getMcRate(),
	            				data.getMcPerPiece(),
	            				data.getMcAmount(),
	            				data.getRate(),
	            				data.getTaxPercentage(),
	            				data.getOrnamentAmount(),
	            				data.getTaxAmount(),
	            				data.getRoundOff(),
	            				data.getAmount()
	            		});
            		}
            	}
            	//calculateBillSummary();
            }
            
            if(oldItemDetailsList!=null && oldItemDetailsList.size()>0){
            	reportModel.addRow(new Object[] {
            			"","","","","","Old"
            	});
            	for(OldItemDetails data:oldItemDetailsList){
            		if(data.getDeletedFlag().equalsIgnoreCase(ConstantUtils.CONSTANT_N)){
	            		reportModel.addRow(new Object[]{
	            				data.getOldDetailsId(),
	            				data.getOrnament().getOrnamentId(),
	            				data.getsNo(),
	            				ConstantUtils.ORNAMENT_MODE_OLD,	            				
	            				++counter,
	            				data.getOrnament().getOrnamentName(),
	            				data.getNoOfPiece(),
	            				data.getGrossWeight(),
	            				data.getWeightLess(),
	            				data.getWeight(),
	            				data.getPurity(),
	            				data.getPureWeight(),
	            				data.getWastage(),
	            				data.getMcRate(),
	            				data.getMcPerPiece(),
	            				data.getMcAmount(),
	            				data.getRate(),
	            				data.getTaxPercentage(),
	            				data.getOrnamentAmount(),
	            				data.getTaxAmount(),
	            				data.getRoundOff(),
	            				data.getAmount()
	            		});
            		}
            	}
            	//calculateBillSummary();
            }
            calculateBillSummary();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private void clearOrnamentFields(){
		try{
			billdetailId.setText("");
			billReturndetailId.setText("");
			metalBalance.setText("");
			grossWeight.setText("");
			lessWeight.setText("");
			netWeight.setText("");
			purity.setText("");
			pureWeight.setText("");
			wastage.setText("");
			rate.setText("");
			taxPercentage.setText("");
			ornamentAmount.setText("");
			taxAmount.setText("");
			mcRate.setText("");
			mcPerPiece.setText("");
			mcAmount.setText("");
			roundOff.setText("");
			amount.setText("");
			noOfPiece.setText("");
			directAmount.setText("");
			
			constructOrnamentPanel();
			ornament.hidePopup();
			setModel(ornament,tfOrnament,new DefaultComboBoxModel(new Vector<>(ornamentList)), "");
			ornament.transferFocus();
			billEntryMode = ConstantUtils.CREATE;
			loadedSno=0;
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private void editOrnament(){
		try{
			int selectedRow = reportTable.getSelectedRow();
			Integer selectedSNO=0;
			String source ="";
			if(selectedRow>-1){
				selectedSNO = (Integer)reportTable.getValueAt(selectedRow, 2);
				source = (String)reportTable.getValueAt(selectedRow, 3);
				
				if(selectedSNO>0){
					if(source.equalsIgnoreCase(ConstantUtils.ORNAMENT_MODE_SALE)){
						BillDetails tempObj = null;
						for(BillDetails obj:billDetailList){
							if(obj.getsNo().equals(selectedSNO)){
								tempObj = obj;
							}
						}
						
						if(tempObj!=null){
							loadOrnamentPanel(tempObj,null);
						}
					}
					
					if(source.equalsIgnoreCase(ConstantUtils.ORNAMENT_MODE_RETURN)){
						BillReturnDetails tempObj = null;
						for(BillReturnDetails obj:billReturnDetailList){
							if(obj.getsNo().equals(selectedSNO)){
								tempObj = obj;
							}
						}
						
						if(tempObj!=null){
							loadOrnamentPanel(null,tempObj);
						}
					}
					
					
				}
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private void loadOrnamentPanel(BillDetails obj,BillReturnDetails returnObj){
		try{
			clearOrnamentFields();
			billEntryMode = ConstantUtils.UPDATE;
			if(obj!=null) {
				if(obj.getAmount()!=null){
					amount.setText(obj.getAmount().toString());
				}
				if(obj.getBarCode()!=null){
					
				}
				if(obj.getBillDetailsId()!=null){
					billdetailId.setText(obj.getBillDetailsId().toString());
				}
				if(obj.getDirectAmount()!=null){
					directAmount.setText(obj.getDirectAmount().toString());
				}
				if(obj.getGrossWeight()!=null){
					grossWeight.setText(obj.getGrossWeight().toString());
				}
				if(obj.getMcAmount()!=null){
					mcAmount.setText(obj.getMcAmount().toString());
				}
				if(obj.getMcPerPiece()!=null){
					mcPerPiece.setText(obj.getMcPerPiece().toString());
				}
				if(obj.getMcRate()!=null){
					mcRate.setText(obj.getMcRate().toString());
				}
				if(obj.getNoOfPiece()!=null){
					noOfPiece.setText(obj.getNoOfPiece().toString());
				}
				if(obj.getOrnament()!=null){
					int ornamentIndex=-1;
					int ornamentCounter=-1;
					for(Ornament ornObj:ornamentList){
						ornamentCounter++;
						if(ornObj.getOrnamentId().equals(obj.getOrnament().getOrnamentId())){
							ornamentIndex = ornamentCounter;
						}
					}
					ornament.setSelectedIndex(ornamentIndex);
				}
				if(obj.getOrnamentAmount()!=null){
					ornamentAmount.setText(obj.getOrnamentAmount().toString());
				}
				if(obj.getPureWeight()!=null){
					pureWeight.setText(obj.getPureWeight().toString());
				}
				if(obj.getPurity()!=null){
					purity.setText(obj.getPurity().toString());
				}
				if(obj.getRate()!=null){
					rate.setText(obj.getRate().toString());
				}
				if(obj.getRoundOff()!=null){
					roundOff.setText(obj.getRoundOff().toString());
				}
				if(obj.getsNo()!=null){
					loadedSno = obj.getsNo();
				}
				if(obj.getTaxAmount()!=null){
					taxAmount.setText(obj.getTaxAmount().toString());
				}
				if(obj.getTaxPercentage()!=null){
					taxPercentage.setText(obj.getTaxPercentage().toString());
				}
				if(obj.getWastage()!=null){
					wastage.setText(obj.getWastage().toString());
				}
				if(obj.getWeight()!=null){
					netWeight.setText(obj.getWeight().toString());
				}
				if(obj.getWeightLess()!=null){
					lessWeight.setText(obj.getWeightLess().toString());
				}
				if(obj.getMetalBalance()!=null){
					metalBalance.setText(obj.getMetalBalance().toString());
				}
			}else if(returnObj!=null) {
				if(returnObj.getAmount()!=null){
					amount.setText(returnObj.getAmount().toString());
				}
				if(returnObj.getBarCode()!=null){
					
				}
				if(returnObj.getBillReturnDetailsId()!=null){
					billReturndetailId.setText(returnObj.getBillReturnDetailsId().toString());
				}
				if(returnObj.getDirectAmount()!=null){
					directAmount.setText(returnObj.getDirectAmount().toString());
				}
				if(returnObj.getGrossWeight()!=null){
					grossWeight.setText(returnObj.getGrossWeight().toString());
				}
				if(returnObj.getMcAmount()!=null){
					mcAmount.setText(returnObj.getMcAmount().toString());
				}
				if(returnObj.getMcPerPiece()!=null){
					mcPerPiece.setText(returnObj.getMcPerPiece().toString());
				}
				if(returnObj.getMcRate()!=null){
					mcRate.setText(returnObj.getMcRate().toString());
				}
				if(returnObj.getNoOfPiece()!=null){
					noOfPiece.setText(returnObj.getNoOfPiece().toString());
				}
				if(returnObj.getOrnament()!=null){
					int ornamentIndex=-1;
					int ornamentCounter=-1;
					for(Ornament ornObj:ornamentList){
						ornamentCounter++;
						if(ornObj.getOrnamentId().equals(returnObj.getOrnament().getOrnamentId())){
							ornamentIndex = ornamentCounter;
						}
					}
					ornament.setSelectedIndex(ornamentIndex);
				}
				if(returnObj.getOrnamentAmount()!=null){
					ornamentAmount.setText(returnObj.getOrnamentAmount().toString());
				}
				if(returnObj.getPureWeight()!=null){
					pureWeight.setText(returnObj.getPureWeight().toString());
				}
				if(returnObj.getPurity()!=null){
					purity.setText(returnObj.getPurity().toString());
				}
				if(returnObj.getRate()!=null){
					rate.setText(returnObj.getRate().toString());
				}
				if(returnObj.getRoundOff()!=null){
					roundOff.setText(returnObj.getRoundOff().toString());
				}
				if(returnObj.getsNo()!=null){
					loadedReturnSno = returnObj.getsNo();
				}
				if(returnObj.getTaxAmount()!=null){
					taxAmount.setText(returnObj.getTaxAmount().toString());
				}
				if(returnObj.getTaxPercentage()!=null){
					taxPercentage.setText(returnObj.getTaxPercentage().toString());
				}
				if(returnObj.getWastage()!=null){
					wastage.setText(returnObj.getWastage().toString());
				}
				if(returnObj.getWeight()!=null){
					netWeight.setText(returnObj.getWeight().toString());
				}
				if(returnObj.getWeightLess()!=null){
					lessWeight.setText(returnObj.getWeightLess().toString());
				}
				if(returnObj.getMetalBalance()!=null){
					metalBalance.setText(returnObj.getMetalBalance().toString());
				}
				
				ornamentMode.setText(ConstantUtils.ORNAMENT_MODE_SALE);
				changeOrnmentEntryMode();
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private void deleteOrnament(){
		try{
			int selectedRow = reportTable.getSelectedRow();
			Integer selectedSNO=0;
			String source="";
			if(selectedRow>-1){
				selectedSNO = (Integer)reportTable.getValueAt(selectedRow, 2);
				source = (String)reportTable.getValueAt(selectedRow, 3);
				if(selectedSNO>0){
					
					
					int selected = JOptionPane.showConfirmDialog(null, "Are you sure You want to delete Ornament?");
					if(selected==JOptionPane.YES_OPTION){
						if(source.equalsIgnoreCase(ConstantUtils.ORNAMENT_MODE_SALE)) {
							BillDetails tempObj = null;
							for(BillDetails obj:billDetailList){
								if(obj.getsNo().equals(selectedSNO)){
									obj.setDeletedFlag(ConstantUtils.CONSTANT_Y);
								}
							}
						}else if(source.equalsIgnoreCase(ConstantUtils.ORNAMENT_MODE_RETURN)) {
							BillReturnDetails tempObj = null;
							for(BillReturnDetails obj:billReturnDetailList){
								if(obj.getsNo().equals(selectedSNO)){
									obj.setDeletedFlag(ConstantUtils.CONSTANT_Y);
								}
							}
						}
						addOrnamentToTable();
					}
				}
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private Integer getBillDetailListSize(){
		long count=0;
		try{
			count = billDetailList
					  .stream()
					  .filter(c -> c.getDeletedFlag().equalsIgnoreCase(ConstantUtils.CONSTANT_N))
					  .count();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return (int) count;
	}
	
	private void calculateBillSummary(){
		try{
			Double number1= 999999.99;
			Double number2 = 999999.99;
			Double number3 = number1+number2;
			System.out.println("number1"+number1);
			System.out.println("number2"+number2);
			System.out.println("number3"+number3);

			
			Double billGrossWeight=0.0,returnGrossWeight=0.0,grossWeight=0.0,oldGrossWeight=0.0;
			Double billWeightLess=0.0,returnWeightLess=0.0,weightLess=0.0,oldWeightLess=0.0;
			Double billNetWeight=0.0,returnNetWeight=0.0,netWeight=0.0,oldNetWeight=0.0;
			Double billPureWeight=0.0,returnPureWeight=0.0,pureWeight=0.0,oldPureWeight=0.0;
			
			Double billOrnamentAmount=0.0,returnOrnamentAmount=0.0,ornamentAmount=0.0,oldOrnamentAmount=0.0;
			Double billTaxAmount=0.0,returnTaxAmount=0.0,taxAmount=0.0,oldTaxAmount=0.0;
			Double billMCAmount=0.0,returnMCAmount=0.0,mcAmount=0.0,oldMCAmount=0.0;
			
			Double billAmount=0.0,returnAmount=0.0,amount=0.0,oldAmount=0.0;
			Double billRoundOff=0.0,returnRoundOff=0.0,roundOff=0.0,oldRoundOff=0.0;
			Double billMetalBalance = 0.0,returnMetalBalance=0.0,metalBalance=0.0,oldMetalBalance=0.0;
			
			Integer noOfPiece=0;
			String amountInWords="";
			
			for(BillDetails obj : billDetailList){
				if(obj.getDeletedFlag().equalsIgnoreCase(ConstantUtils.CONSTANT_N)){
					if(obj.getGrossWeight()!=null){
						billGrossWeight = billGrossWeight+obj.getGrossWeight();
					}
					
					if(obj.getWeightLess()!=null){
						billWeightLess = billWeightLess+obj.getWeightLess();
					}
					
					if(obj.getWeight()!=null){
						billNetWeight = billNetWeight + obj.getWeight();
					}
					
					if(obj.getPureWeight()!=null){
						billPureWeight = billPureWeight+obj.getPureWeight();
					}
					
					if(obj.getOrnamentAmount()!=null){
						billOrnamentAmount = billOrnamentAmount + obj.getOrnamentAmount();
					}
					
					if(obj.getTaxAmount()!=null){
						billTaxAmount = billTaxAmount + obj.getTaxAmount();
					}
					
					if(obj.getMcAmount()!=null){
						billMCAmount = billMCAmount + obj.getMcAmount();
					}
					
					if(obj.getAmount()!=null){
						billAmount = billAmount + obj.getAmount();
					}
					
					if(obj.getRate() == null && obj.getPureWeight()!=null){
						billMetalBalance = billMetalBalance+obj.getPureWeight();
					}
					
					if(obj.getRoundOff()!=null){
						billRoundOff = billRoundOff + obj.getRoundOff();
					}
					
					if(obj.getNoOfPiece()!=null){
						noOfPiece = noOfPiece + obj.getNoOfPiece();
					}
				}
			}
			//For loop for return items starts
				if(billReturnDetailList!=null && billReturnDetailList.size()>0){
				for(BillReturnDetails obj : billReturnDetailList){
					if(obj.getDeletedFlag().equalsIgnoreCase(ConstantUtils.CONSTANT_N)){
						if(obj.getGrossWeight()!=null){
							returnGrossWeight = returnGrossWeight+obj.getGrossWeight();
						}
						
						if(obj.getWeightLess()!=null){
							returnWeightLess = returnWeightLess+obj.getWeightLess();
						}
						
						if(obj.getWeight()!=null){
							returnNetWeight = returnNetWeight + obj.getWeight();
						}
						
						if(obj.getPureWeight()!=null){
							returnPureWeight = returnPureWeight+obj.getPureWeight();
						}
						
						if(obj.getOrnamentAmount()!=null){
							returnOrnamentAmount = returnOrnamentAmount + obj.getOrnamentAmount();
						}
						
						if(obj.getTaxAmount()!=null){
							returnTaxAmount = returnTaxAmount + obj.getTaxAmount();
						}
						
						if(obj.getMcAmount()!=null){
							returnMCAmount = returnMCAmount + obj.getMcAmount();
						}
						
						if(obj.getAmount()!=null){
							returnAmount = returnAmount + obj.getAmount();
						}
						
						if(obj.getRate() == null && obj.getPureWeight()!=null){
							returnMetalBalance = returnMetalBalance+obj.getPureWeight();
						}
						
						if(obj.getRoundOff()!=null){
							returnRoundOff = returnRoundOff + obj.getRoundOff();
						}
						
					}
				}
			}
			//For loop for return items ends
				
			//For loop for Old items starts
			if(oldItemDetailsList!=null && oldItemDetailsList.size()>0){
			for(OldItemDetails obj : oldItemDetailsList){
				if(obj.getDeletedFlag().equalsIgnoreCase(ConstantUtils.CONSTANT_N)){
					if(obj.getGrossWeight()!=null){
						oldGrossWeight = oldGrossWeight+obj.getGrossWeight();
					}
					
					if(obj.getWeightLess()!=null){
						oldWeightLess = oldWeightLess+obj.getWeightLess();
					}
					
					if(obj.getWeight()!=null){
						oldNetWeight = oldNetWeight + obj.getWeight();
					}
					
					if(obj.getPureWeight()!=null){
						oldPureWeight = oldPureWeight+obj.getPureWeight();
					}
					
					if(obj.getOrnamentAmount()!=null){
						oldOrnamentAmount = oldOrnamentAmount + obj.getOrnamentAmount();
					}
					
					if(obj.getTaxAmount()!=null){
						oldTaxAmount = oldTaxAmount + obj.getTaxAmount();
					}
					
					if(obj.getMcAmount()!=null){
						oldMCAmount = oldMCAmount + obj.getMcAmount();
					}
					
					if(obj.getAmount()!=null){
						oldAmount = oldAmount + obj.getAmount();
					}
					
					if(obj.getRate() == null && obj.getPureWeight()!=null){
						oldMetalBalance = oldMetalBalance+obj.getPureWeight();
					}
					
					if(obj.getRoundOff()!=null){
						oldRoundOff = oldRoundOff + obj.getRoundOff();
					}
				}
			}
		}
		//For loop for Old items ends
			
			grossWeight = billGrossWeight-returnGrossWeight-oldGrossWeight;
			weightLess = billWeightLess-returnWeightLess-oldWeightLess;
			netWeight = billNetWeight-returnNetWeight-oldNetWeight;
			pureWeight = billPureWeight-returnPureWeight-oldPureWeight;
			
			ornamentAmount = billOrnamentAmount-returnOrnamentAmount-oldOrnamentAmount;
			taxAmount = billTaxAmount-returnTaxAmount-oldTaxAmount;
			mcAmount = billMCAmount-returnMCAmount-oldMCAmount;
			roundOff = billRoundOff - returnRoundOff-oldRoundOff;
			amount = billAmount-returnAmount-oldAmount;
			metalBalance = billMetalBalance-returnMetalBalance-oldMetalBalance;
			
			
			
			//round off digits starts
			if(metal.equalsIgnoreCase(ConstantUtils.GOLD)){
				billGrossWeight = Calculator.getThreeDigitDecimal(billGrossWeight);
				returnGrossWeight = Calculator.getThreeDigitDecimal(returnGrossWeight);
				grossWeight = Calculator.getThreeDigitDecimal(grossWeight);
				billWeightLess = Calculator.getThreeDigitDecimal(billWeightLess);
				returnWeightLess = Calculator.getThreeDigitDecimal(returnWeightLess);
				weightLess = Calculator.getThreeDigitDecimal(weightLess);
				billNetWeight = Calculator.getThreeDigitDecimal(billNetWeight);
				returnNetWeight = Calculator.getThreeDigitDecimal(returnNetWeight);
				netWeight = Calculator.getThreeDigitDecimal(netWeight);
				billPureWeight = Calculator.getThreeDigitDecimal(billPureWeight);
				returnPureWeight = Calculator.getThreeDigitDecimal(returnPureWeight);
				pureWeight = Calculator.getThreeDigitDecimal(pureWeight);
				
				billOrnamentAmount = Calculator.getThreeDigitDecimal(billOrnamentAmount);
				returnOrnamentAmount = Calculator.getThreeDigitDecimal(returnOrnamentAmount);
				ornamentAmount = Calculator.getThreeDigitDecimal(ornamentAmount);
				billTaxAmount = Calculator.getThreeDigitDecimal(billTaxAmount);
				returnTaxAmount = Calculator.getThreeDigitDecimal(returnTaxAmount);
				taxAmount = Calculator.getThreeDigitDecimal(taxAmount);
				billMCAmount = Calculator.getThreeDigitDecimal(billMCAmount);
				returnMCAmount = Calculator.getThreeDigitDecimal(returnMCAmount);
				mcAmount = Calculator.getThreeDigitDecimal(mcAmount);
				
				billAmount = Calculator.getThreeDigitDecimal(billAmount);
				returnAmount = Calculator.getThreeDigitDecimal(returnAmount);
				amount = Calculator.getThreeDigitDecimal(amount);
				billRoundOff = Calculator.getThreeDigitDecimal(billRoundOff);
				returnRoundOff = Calculator.getThreeDigitDecimal(returnRoundOff);
				roundOff = Calculator.getThreeDigitDecimal(roundOff);
				billMetalBalance = Calculator.getThreeDigitDecimal(billMetalBalance);
				returnMetalBalance = Calculator.getThreeDigitDecimal(returnMetalBalance);
				metalBalance = Calculator.getThreeDigitDecimal(metalBalance);
			}else if(metal.equalsIgnoreCase(ConstantUtils.SILVER)){
				billGrossWeight = Calculator.getTwoDigitDecimal(billGrossWeight);
				returnGrossWeight = Calculator.getTwoDigitDecimal(returnGrossWeight);
				grossWeight = Calculator.getTwoDigitDecimal(grossWeight);
				billWeightLess = Calculator.getTwoDigitDecimal(billWeightLess);
				returnWeightLess = Calculator.getTwoDigitDecimal(returnWeightLess);
				weightLess = Calculator.getTwoDigitDecimal(weightLess);
				billNetWeight = Calculator.getTwoDigitDecimal(billNetWeight);
				returnNetWeight = Calculator.getTwoDigitDecimal(returnNetWeight);
				netWeight = Calculator.getTwoDigitDecimal(netWeight);
				billPureWeight = Calculator.getTwoDigitDecimal(billPureWeight);
				returnPureWeight = Calculator.getTwoDigitDecimal(returnPureWeight);
				pureWeight = Calculator.getTwoDigitDecimal(pureWeight);
				
				billOrnamentAmount = Calculator.getTwoDigitDecimal(billOrnamentAmount);
				returnOrnamentAmount = Calculator.getTwoDigitDecimal(returnOrnamentAmount);
				ornamentAmount = Calculator.getTwoDigitDecimal(ornamentAmount);
				billTaxAmount = Calculator.getTwoDigitDecimal(billTaxAmount);
				returnTaxAmount = Calculator.getTwoDigitDecimal(returnTaxAmount);
				taxAmount = Calculator.getTwoDigitDecimal(taxAmount);
				billMCAmount = Calculator.getTwoDigitDecimal(billMCAmount);
				returnMCAmount = Calculator.getTwoDigitDecimal(returnMCAmount);
				mcAmount = Calculator.getTwoDigitDecimal(mcAmount);
				
				billAmount = Calculator.getTwoDigitDecimal(billAmount);
				returnAmount = Calculator.getTwoDigitDecimal(returnAmount);
				amount = Calculator.getTwoDigitDecimal(amount);
				billRoundOff = Calculator.getTwoDigitDecimal(billRoundOff);
				returnRoundOff = Calculator.getTwoDigitDecimal(returnRoundOff);
				roundOff = Calculator.getTwoDigitDecimal(roundOff);
				billMetalBalance = Calculator.getTwoDigitDecimal(billMetalBalance);
				returnMetalBalance = Calculator.getTwoDigitDecimal(returnMetalBalance);
				metalBalance = Calculator.getTwoDigitDecimal(metalBalance);
			}
			//round off digits ends
			
			amountInWords = Calculator.convert(amount.intValue());
			
			billObj.setBillGrossWeight(billGrossWeight);
			billObj.setWeightLess(billWeightLess);
			billObj.setBillWeight(billNetWeight);
			billObj.setBillPureWeight(billPureWeight);
			
			billObj.setReturnGrossWeight(returnGrossWeight);
			billObj.setReturnWeightLess(returnWeightLess);
			billObj.setReturnWeight(returnNetWeight);
			billObj.setReturnPureWeight(returnPureWeight);
			
			billObj.setGrossWeight(grossWeight);
			billObj.setWeightLess(weightLess);
			billObj.setWeight(netWeight);
			billObj.setPureWeight(pureWeight);
			
			
			billObj.setBillAmount(billOrnamentAmount);
			billObj.setBillTaxAmount(billTaxAmount);
			billObj.setBillRoundOff(billRoundOff);
			billObj.setBillTotalAmount(billAmount);
			
			billObj.setReturnAmount(returnOrnamentAmount);
			billObj.setReturnRoundOff(returnRoundOff);
			billObj.setReturnTotalAmount(returnAmount);
			
			billObj.setAmount(ornamentAmount);
			billObj.setTaxAmount(taxAmount);
			billObj.setRoundOff(roundOff);
			billObj.setLessAmount(0.0);
			billObj.setTotalAmount(amount);
			billObj.setAmountInWords(amountInWords);
			
			billObj.setBillMetalBalance(billMetalBalance);
			billObj.setReturnMetal(returnMetalBalance);
			billObj.setMetalBalance(metalBalance);
			billObj.setNoOfPiece(noOfPiece);
			constructBillSummaryInfoPanel();
			
			
		    
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private void adjustTableWidth(){
		try{
			for (int column = 0; column < reportTable.getColumnCount(); column++)
			{
			    TableColumn tableColumn = reportTable.getColumnModel().getColumn(column);
			    int preferredWidth = tableColumn.getMinWidth();
			    int maxWidth = tableColumn.getMaxWidth();
			 
			    for (int row = 0; row < reportTable.getRowCount(); row++)
			    {
			        TableCellRenderer cellRenderer = reportTable.getCellRenderer(row, column);
			        Component c = reportTable.prepareRenderer(cellRenderer, row, column);
			        int width = c.getPreferredSize().width + reportTable.getIntercellSpacing().width;
			        preferredWidth = Math.max(preferredWidth, width);
			 
			        //  We've exceeded the maximum width, no need to check other rows
			 
			        if (preferredWidth >= maxWidth)
			        {
			            preferredWidth = maxWidth;
			            break;
			        }
			    }
			 
			    tableColumn.setPreferredWidth( preferredWidth );
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private void saveOrUpdateBill(){
		try{
			Contact selectedContact = (Contact)contact.getSelectedItem();
			
			billObj.setContact(selectedContact);
			billObj.setBillDetails(billDetailList);
			billObj.setBillReturnDetails(billReturnDetailList);
			billObj.setBillDate(billingDate.getDate());
			billObj.setBillNumber(billNo.getText());
			billObj.setLicence(Session.licence);
			billObj.setDeletedFlag(ConstantUtils.CONSTANT_N);
			billObj.setBillType(MasterController.getInstace().getBillType(billType));
			billObj.setMetal(MasterController.getInstace().getMetal(metal));
			billObj.setBillSource(MasterController.getInstace().getBillSourceByCode(billSource));
			
			billObj.setOpeningCashBalance(openingCashBalance);
			billObj.setOpeningMetalBalance(openingMetalBalance);
			
			if(billId!=null && billId>0){
				billObj.setBillId(billId);
				billObj.setCreatedDateTime(createDateTime);
				billObj.setLastUpdatedDateTime(new Date());
			}else {
				billObj.setCreatedDateTime(new Date());
			}
			
			if(!cashReceived.getText().equals("") || !metalReceived.getText().equals("")) {
				savePaymentDetails();
			}
			
			
			Bill tempObj = BillController.getInstace().saveOrUpdateBill(billObj);
			/*if(tempObj!=null) {
				if(!cashReceived.getText().equals("") || !metalReceived.getText().equals("")) {
					savePaymentDetails();
				}
			}*/
			if(tempObj!=null && tempObj.getBillId()>0){
				int ReportOption=JOptionPane.showConfirmDialog(null,"Bill Saved!! \n Do You Want To Print (Y/N)", "Confirm", JOptionPane.OK_CANCEL_OPTION);
				
				if(ReportOption==JOptionPane.OK_OPTION){
					if(billSource.equalsIgnoreCase(ConstantUtils.GOLD_WHOLESALE_GST) || 
							billSource.equalsIgnoreCase(ConstantUtils.GOLD_WHOLESALE_NONGST) ||
							billSource.equalsIgnoreCase(ConstantUtils.SILVER_WHOLESALE_GST)
							
							) {
						RunReport.printWholeSaleBill(tempObj.getBillId(),billSource,billType);
					}else {
						RunReport.printWholeSaleBill(tempObj.getBillId(),billSource,billType);
					}
				}
				
				//String calledFrom,Integer billId,Integer customerId,String billType,String metal,String billSource
				if(calledFrom.equalsIgnoreCase(ConstantUtils.MENU)){
					AppController.getInstance().addBillingFormPage(calledFrom, 0, 0, billType, metal, billSource);
				}else if(calledFrom.equalsIgnoreCase(ConstantUtils.BILLING_REPORT)){
					AppController.getInstance().addBillingReportPage(ConstantUtils.MENU,billType, metal, billSource);
				}else if(calledFrom.equalsIgnoreCase(ConstantUtils.PAYMENT_FORM)){
					customerId = selectedContact.getContactId();
					AppController.getInstance().addPaymentFormPage(ConstantUtils.MENU,customerId,billType,metal,billSource);
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private void grossWeightChangedNONGSTGold(){
		calculateNONGSTGoldAmount();
	}
	
	private void grossWeightChangedNONGSTSilver(){
		calculateNONGSTSilverAmount();
	}
	
	private void lessWeightChangedNONGSTGold(){
		calculateNONGSTGoldAmount();
	}
	
	private void lessWeightChangedNONGSTSilver(){
		calculateNONGSTSilverAmount();
	}
	
	private void purityChangedNONGSTGold(){
		calculateNONGSTGoldAmount();
	}
	
	private void purityChangedNONGSTSilver(){
		calculateNONGSTSilverAmount();
	}
	
	private void wastageChangedNONGSTGold(){
		calculateNONGSTGoldAmount();
	}
	
	private void wastageChangedNONGSTSilver(){
		calculateNONGSTSilverAmount();
	}
	
	private void mcRateChangedNONGSTGold(){
		calculateNONGSTGoldAmount();
	}
	private void mcRateChangedNONGSTSilver(){
		calculateNONGSTSilverAmount();
	}
	
	private void mcPerPieceChangedNONGSTGold(){
		calculateNONGSTGoldAmount();
	}
	
	private void mcPerPieceChangedNONGSTSilver(){
		calculateNONGSTSilverAmount();
	}
	
	private void noofPieceChangedNONGSTGold(){
		calculateNONGSTGoldAmount();
	}
	
	private void noofPieceChangedNONGSTSilver(){
		calculateNONGSTSilverAmount();
	}
	
	private void netWeightChangedNONGSTGold(){
		calculateNONGSTGoldAmount();
	}
	
	private void netWeightChangedNONGSTSilver(){
		calculateNONGSTSilverAmount();
	}

	private void rateChangedNONGSTGold(){
		calculateNONGSTGoldAmount();
	}
	
	private void rateChangedNONGSTSilver(){
		calculateNONGSTSilverAmount();
	}
	private void calculateNONGSTGoldAmount(){
		try{
			System.out.println("== calculateNONGSTGoldAmount Starts==");
			Double grossWeightFilled=0.0,lessWeightFilled=0.0,setNetWeight=0.0;
			Double purityFilled=0.0,setPureWeight=0.0;
			Double wastageFilled =0.0;
			Double rateFilled=0.0;
			Double setOrnamentAmount=0.0;
			Double mcRateFilled=0.0,mcPerPieceFilled=0.0,setMCAmount=0.0,setRoundOff=0.0,setAmount=0.0;
			Double noofPieceFilled=0.0;
			Double setMetalBalance =0.0;
			
			if(!grossWeight.getText().equalsIgnoreCase(ConstantUtils.BLANK)){
				grossWeightFilled = Double.parseDouble(grossWeight.getText().toString());
			}if(!lessWeight.getText().equalsIgnoreCase(ConstantUtils.BLANK)){
				lessWeightFilled = Double.parseDouble(lessWeight.getText().toString());
			}if(!purity.getText().equalsIgnoreCase(ConstantUtils.BLANK)){
				purityFilled = Double.parseDouble(purity.getText().toString());
			}if(!wastage.getText().equalsIgnoreCase(ConstantUtils.BLANK)){
				wastageFilled = Double.parseDouble(wastage.getText().toString());
				purityFilled = purityFilled+wastageFilled;
			}if(!rate.getText().equalsIgnoreCase(ConstantUtils.BLANK)){
				rateFilled = Double.parseDouble(rate.getText().toString());
			}if(!mcRate.getText().equalsIgnoreCase(ConstantUtils.BLANK)){
				mcRateFilled = Double.parseDouble(mcRate.getText().toString());
			}if(!mcPerPiece.getText().equalsIgnoreCase(ConstantUtils.BLANK)){
				mcPerPieceFilled = Double.parseDouble(mcPerPiece.getText().toString());
			}if(!noOfPiece.getText().equalsIgnoreCase(ConstantUtils.BLANK)){
				noofPieceFilled = Double.parseDouble(noOfPiece.getText().toString());
			}
			
			setNetWeight = Calculator.getThreeDigitDecimal(grossWeightFilled - lessWeightFilled);
			setPureWeight = Calculator.getThreeDigitDecimal(((setNetWeight * purityFilled)/100));
			
			if(mcRateFilled>0.0){
				setMCAmount = Calculator.getTwoDigitDecimal(((setPureWeight/1000) * (mcRateFilled))); 
			}
			
			if(mcPerPieceFilled>0.0){
				setMCAmount = Calculator.getTwoDigitDecimal(noofPieceFilled * mcPerPieceFilled);
			}
			
			if(rateFilled>0.0){
				System.out.println("rateFilled->"+rateFilled);
				System.out.println("setPureWeight->"+setPureWeight);
				setOrnamentAmount = Calculator.getTwoDigitDecimal(rateFilled * setPureWeight);
			}else{
				setMetalBalance = setPureWeight;
			}
			System.out.println("setOrnamentAmount=>"+setOrnamentAmount);
			setAmount = Calculator.getTwoDigitDecimal(setOrnamentAmount + setMCAmount);
			System.out.println("setAmount=>"+setAmount);
			setRoundOff = Calculator.getTwoDigitDecimal(((double)Math.round(setAmount) - setAmount));
			
			setAmount = (double)Math.round(setAmount);
			
			
			netWeight.setText(setNetWeight.toString());
			pureWeight.setText(setPureWeight.toString());
			ornamentAmount.setText(setOrnamentAmount.toString());
			mcAmount.setText(setMCAmount.toString());
			roundOff.setText(setRoundOff.toString());
			amount.setText(setAmount.toString());
			
			if(setMetalBalance>0.0){
				metalBalance.setText(setMetalBalance.toString());
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		//MyTextField grossWeight,lessWeight,netWeight,purity,pureWeight,wastage,rate,taxPercentage,ornamentAmount,taxAmount,mcRate,mcPerPiece,mcAmount,roundOff,amount,
		//noOfPiece,directAmount,billdetailId;
		
		System.out.println("== calculateNONGSTGoldAmount Ends==");
	}
	
	private void calculateNONGSTSilverAmount(){
		try{
			System.out.println("== calculateNONGSTSilverAmount Starts==");
			Double grossWeightFilled=0.0,lessWeightFilled=0.0,setNetWeight=0.0;
			Double purityFilled=0.0,setPureWeight=0.0;
			Double wastageFilled =0.0;
			Double rateFilled=0.0;
			Double setOrnamentAmount=0.0;
			Double mcRateFilled=0.0,mcPerPieceFilled=0.0,setMCAmount=0.0,setRoundOff=0.0,setAmount=0.0;
			Double noofPieceFilled=0.0;
			Double setMetalBalance =0.0;
			
			if(!grossWeight.getText().equalsIgnoreCase(ConstantUtils.BLANK)){
				grossWeightFilled = Double.parseDouble(grossWeight.getText().toString());
			}if(!lessWeight.getText().equalsIgnoreCase(ConstantUtils.BLANK)){
				lessWeightFilled = Double.parseDouble(lessWeight.getText().toString());
			}if(!purity.getText().equalsIgnoreCase(ConstantUtils.BLANK)){
				purityFilled = Double.parseDouble(purity.getText().toString());
			}if(!wastage.getText().equalsIgnoreCase(ConstantUtils.BLANK)){
				wastageFilled = Double.parseDouble(wastage.getText().toString());
				purityFilled = purityFilled+wastageFilled;
			}if(!rate.getText().equalsIgnoreCase(ConstantUtils.BLANK)){
				rateFilled = Double.parseDouble(rate.getText().toString());
			}if(!mcRate.getText().equalsIgnoreCase(ConstantUtils.BLANK)){
				mcRateFilled = Double.parseDouble(mcRate.getText().toString());
			}if(!mcPerPiece.getText().equalsIgnoreCase(ConstantUtils.BLANK)){
				mcPerPieceFilled = Double.parseDouble(mcPerPiece.getText().toString());
			}if(!noOfPiece.getText().equalsIgnoreCase(ConstantUtils.BLANK)){
				noofPieceFilled = Double.parseDouble(noOfPiece.getText().toString());
			}
			
			setNetWeight = Calculator.getTwoDigitDecimal(grossWeightFilled - lessWeightFilled);
			setPureWeight = Calculator.getTwoDigitDecimal(((setNetWeight * purityFilled)/100));
			
			if(mcRateFilled>0.0){
				setMCAmount = Calculator.getTwoDigitDecimal(((setPureWeight/1000) * (mcRateFilled))); 
			}
			
			if(mcPerPieceFilled>0.0){
				setMCAmount = Calculator.getTwoDigitDecimal(noofPieceFilled * mcPerPieceFilled);
			}
			
			if(rateFilled>0.0){
				System.out.println("rateFilled->"+rateFilled);
				System.out.println("setPureWeight->"+setPureWeight);
				setOrnamentAmount = Calculator.getTwoDigitDecimal(rateFilled * setPureWeight);
			}else{
				setMetalBalance = setPureWeight;
			}
			System.out.println("setOrnamentAmount=>"+setOrnamentAmount);
			setAmount = Calculator.getTwoDigitDecimal(setOrnamentAmount + setMCAmount);
			System.out.println("setAmount=>"+setAmount);
			setRoundOff = Calculator.getTwoDigitDecimal(((double)Math.round(setAmount) - setAmount));
			
			setAmount = (double)Math.round(setAmount);
			
			
			netWeight.setText(setNetWeight.toString());
			pureWeight.setText(setPureWeight.toString());
			ornamentAmount.setText(setOrnamentAmount.toString());
			mcAmount.setText(setMCAmount.toString());
			roundOff.setText(setRoundOff.toString());
			amount.setText(setAmount.toString());
			
			if(setMetalBalance>0.0){
				metalBalance.setText(setMetalBalance.toString());
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		//MyTextField grossWeight,lessWeight,netWeight,purity,pureWeight,wastage,rate,taxPercentage,ornamentAmount,taxAmount,mcRate,mcPerPiece,mcAmount,roundOff,amount,
		//noOfPiece,directAmount,billdetailId;
		
		System.out.println("== calculateNONGSTSilverAmount Ends==");
	}
	
	private void loadBillObj(){
		try{
			billObj = BillController.getInstace().getBill(billId);
			
			if(billObj!=null){
				billingDate.setDate(billObj.getBillDate());
				billNo.setText(billObj.getBillNumber());
				createDateTime = billObj.getCreatedDateTime();
				metal = billObj.getMetal().getMetalName();
				
				if(!metal.equalsIgnoreCase(ConstantUtils.BLANK)) {
					ornamentList = MasterController.getInstace().getOrnamentListByMetalCode(Session.licence.getLicenceId(), metal);
				}
				
				if(billObj.getContact()!=null){
					
					setContact(billObj.getContact().getContactId());
				}
				
				
				billDetailList = billObj.getBillDetails();
				billReturnDetailList = billObj.getBillReturnDetails();
				billSno = (int) billDetailList
						  .stream()
						  //.filter(c -> c.getDeletedFlag().equalsIgnoreCase(ConstantUtils.CONSTANT_N))
						  .count();
				if(billReturnDetailList!=null && billReturnDetailList.size()>0) {
				returnSno = (int) billReturnDetailList
						  .stream()
						  //.filter(c -> c.getDeletedFlag().equalsIgnoreCase(ConstantUtils.CONSTANT_N))
						  .count();
				}else {
					billReturnDetailList = new ArrayList<BillReturnDetails>();
				}
				addOrnamentToTable();
				System.out.println(billSno);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void setContact(Integer contactId) {
		try {
			
			int tempCounter=-1;
			int tempSetId=-1;
			
			for(Contact obj:contactList){
				tempCounter++;
				if(obj.getContactId().equals(contactId)){
					tempSetId = tempCounter;
				}
			}
			
			contact.setSelectedIndex(tempSetId);
			setContactInformation();
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void addContactWindow() {
		try {
			String contactTypeNameStr = "";
			if(billSource.equalsIgnoreCase(ConstantUtils.GOLD_WHOLESALE_GST) ||
					billSource.equalsIgnoreCase(ConstantUtils.GOLD_WHOLESALE_NONGST) ||
					billSource.equalsIgnoreCase(ConstantUtils.SILVER_WHOLESALE_GST) ||
					billSource.equalsIgnoreCase(ConstantUtils.SILVER_WHOLESALE_NONGST) ||
					
					billSource.equalsIgnoreCase(ConstantUtils.WHOLESALE_BILLING_GST) ||
					billSource.equalsIgnoreCase(ConstantUtils.WHOLESALE_BILLING_NONGST)
					){
				contactTypeNameStr = ConstantUtils.WHOLESALE_CONTACT_TYPE;
			}else if(billSource.equalsIgnoreCase(ConstantUtils.GOLD_SUPPLIER_GST) ||
					billSource.equalsIgnoreCase(ConstantUtils.GOLD_SUPPLIER_NONGST) ||
					billSource.equalsIgnoreCase(ConstantUtils.SILVER_SUPPLIER_GST) ||
					billSource.equalsIgnoreCase(ConstantUtils.SILVER_SUPPLIER_NONGST) ||
					
					billSource.equalsIgnoreCase(ConstantUtils.PURCHASE_BILLING_GST) ||
					billSource.equalsIgnoreCase(ConstantUtils.PURCHASE_BILLING_NONGST)
					){
				contactTypeNameStr = ConstantUtils.SUPPLIER_CONTACT_TYPE;
			}else if(billSource.equalsIgnoreCase(ConstantUtils.GOLD_RETAIL_GST) ||
					billSource.equalsIgnoreCase(ConstantUtils.GOLD_RETAIL_NONGST) ||
					billSource.equalsIgnoreCase(ConstantUtils.SILVER_RETAIL_GST) ||
					billSource.equalsIgnoreCase(ConstantUtils.SILVER_RETAIL_NONGST) ||
					
					billSource.equalsIgnoreCase(ConstantUtils.RETAIL_BILLING_GST) ||
					billSource.equalsIgnoreCase(ConstantUtils.RETAIL_BILLING_NONGST)
					){
				contactTypeNameStr = ConstantUtils.RETAIL_CONTACT_TYPE;
			}
			
			AppController.getInstance().addContactFormPage(0, contactTypeNameStr, billSource, Session.licence.getLicenceId());
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private void savePaymentDetails() {
		try {
			if(!cashReceived.getText().equals("")) {
				PaymentDetails payment = new PaymentDetails();
				
				payment.setAmountPaid(Double.parseDouble(cashReceived.getText()));
				payment.setBillSource(MasterController.getInstace().getBillSourceByCode(billSource));
				payment.setBillType(MasterController.getInstace().getBillType(billType));
				payment.setContact((Contact)contact.getSelectedItem());
				payment.setDeletedFlag(ConstantUtils.CONSTANT_N);
				payment.setLicence(Session.licence);
				payment.setPaymentDate(billingDate.getDate());
				
				if(paymentType.getSelectedItem().toString().equalsIgnoreCase(ConstantUtils.BANK)){
					payment.setBank(Session.licence.getBank());
				}
				
				BillController.getInstace().saveOrUpdatePaymentDetails(payment);
			}
			if(!metalReceived.getText().equals("")) {
				PaymentDetails payment = new PaymentDetails();
				
				payment.setMetalPaid(Double.parseDouble(metalReceived.getText()));
				payment.setMetal(MasterController.getInstace().getMetal(metal));
				payment.setBillSource(MasterController.getInstace().getBillSourceByCode(billSource));
				payment.setBillType(MasterController.getInstace().getBillType(billType));
				payment.setContact((Contact)contact.getSelectedItem());
				payment.setDeletedFlag(ConstantUtils.CONSTANT_N);
				payment.setLicence(Session.licence);
				payment.setPaymentDate(billingDate.getDate());
				
				BillController.getInstace().saveOrUpdatePaymentDetails(payment);
			}
			
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private void weightLessPopup() {
		try {
			coverWeightDialog = new JDialog();
			JPanel weightLessPanel = new JPanel(new MigLayout());
			Double totalWeightLess=0.0;
			HashMap<Double,Double> weightMap = new HashMap<>();
			
			MyPanel.setBorder("", weightLessPanel);
			
			//Add Component to the Panel starts
			if(coverWeightList!=null && coverWeightList.size()>0) {
				for(CoverWeight coverWeight:coverWeightList) {
					double weight = coverWeight.getCoverWeight();
					weightLessPanel.add(new MyLabel(Calculator.formatThreeDecimal(coverWeight.getCoverWeight())));
					
					MyTextField field = new MyTextField(10);
					MyLabel label = new MyLabel("");
					field.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							double iWeight = weight;
							//System.out.println(field.getText()+" - "+iWeight);
							Double total=0.0;
							if(!field.getText().equals("")) {
								total = iWeight*Integer.parseInt(field.getText());
								total = Calculator.getThreeDigitDecimal(total);
								label.setText(Double.toString(total));
							}else {
								label.setText("");
							}
							
							weightMap.put(iWeight, total);
							
							System.out.println(weightMap);
						}
					});
					
					weightLessPanel.add(field);
					weightLessPanel.add(label,"wrap");
					
				}
			}
			//Add Component to the Panel Ends
			
			
			
			
			coverWeightDialog.add(weightLessPanel,BorderLayout.CENTER);
			
			coverWeightDialog.setTitle("Cover Weight");
			coverWeightDialog.setSize(500, 500);
			coverWeightDialog.setLocationRelativeTo(null);
			coverWeightDialog.setVisible(true);
			
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
