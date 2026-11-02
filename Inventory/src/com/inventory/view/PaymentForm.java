package com.inventory.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Window;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;

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
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXDatePicker;

import com.inventory.common.FilterJtable;
import com.inventory.components.EvenOddTableRenderer;
import com.inventory.components.MyButton;
import com.inventory.components.MyLabel;
import com.inventory.components.MyPanel;
import com.inventory.components.MyTextField;
import com.inventory.components.PaymentTableRenderer;
import com.inventory.controller.AppController;
import com.inventory.controller.BalanceSheetController;
import com.inventory.controller.BillController;
import com.inventory.controller.MasterController;
import com.inventory.dto.BalanceSheetDTO;
import com.inventory.dto.BalanceSheetDTOList;
import com.inventory.dto.ContactTransactionDTO;
import com.inventory.dto.ContactTransactionDTOList;
import com.inventory.dto.PaymentDTO;
import com.inventory.dto.PaymentDTOList;
import com.inventory.model.Bill;
import com.inventory.model.Contact;
import com.inventory.model.Conversion;
import com.inventory.model.Metal;
import com.inventory.model.PaymentDetails;
import com.inventory.reports.RunReport;
import com.inventory.utility.Calculator;
import com.inventory.utility.ConstantUtils;
import com.inventory.utility.DateUtils;
import com.inventory.utility.Session;
import com.inventory.utility.StringUtils;

import net.miginfocom.swing.MigLayout;

public class PaymentForm implements DocumentListener, ActionListener, KeyListener, MouseListener {
	
private static PaymentForm myInstance=null;
	
	private PaymentForm(){}
	
	public static PaymentForm getInstance(){
		if(myInstance ==null){
			myInstance = new PaymentForm();
		}
		
		return myInstance;
	}
	
	JPanel panel;
	JPanel firstPanel,secondPanel,thirdPanel;
	JPanel thirdPanelLeft,thirdPanelRight;
	MyTextField searchTextField = null;
	
	JTable reportTable=null;
    DefaultTableModel reportModel =null;
    JScrollPane scrollPane;
    
    private JPopupMenu popupMenu;
    private JMenuItem editRecord,deleteRecord,enableDisableRecord;
    JDialog dialog = null;
    JXDatePicker fromDate,toDate,paymentDate,conversionDate;
    MyButton searchBill,print;

    String calledFrom;
	Integer customerId;
	String billType;
	String metal;
	String billSource;
	
	List<Contact> contactList = null;
	List<Metal> metalList = null;
	JComboBox contact,paymentType,conversionType,metalType;
	JTextField tfContact,tfMetal;
	boolean tfContact_hide_flag = false,tfMetal_hide_flag = false;
	MyTextField metalReceived,amountReceived;
	MyButton save,conversionSave;
	boolean searchButtonClick = false;
	MyTextField conversionRate,conversionMetal,conversionAmount;
	
    public JPanel getPanel(String calledFrom, Integer customerId,String billType,String metal,String billSource){
    	try{
    		this.calledFrom = calledFrom;
    		this.customerId = customerId;
    		this.billType = billType;
    		this.metal = metal;
    		this.billSource = billSource;
    		
    		initComponent();
    		//if(calledFrom.equalsIgnoreCase(ConstantUtils.MENU)){
    			constructPaymentForm();
    			//fetchBillingReportList(reportType);
    			//fetchBillingReportList();
    		//}
    		FilterJtable.setFilterJtable(reportTable, searchTextField);
    		System.out.println("customerId - "+customerId);
    		if(customerId!=null && customerId>0){
    			loadPaymentForm();
    		}
    	}catch(Exception ex){
    		ex.printStackTrace();
    	}
    	return panel;
    }
    
    private void initComponent(){
    	try{
    		panel = new JPanel(new MigLayout());
    		
    		firstPanel = new JPanel(new MigLayout());
			secondPanel = new JPanel(new MigLayout());
			thirdPanel = new JPanel(new MigLayout());
			thirdPanelLeft = new JPanel(new MigLayout());
			thirdPanelRight = new JPanel(new MigLayout());
			
			searchTextField = new MyTextField(20);
			metalReceived = new MyTextField(20);
			amountReceived = new MyTextField(20);
			fromDate = new JXDatePicker();
			fromDate.setDate(Calendar.getInstance().getTime());
			fromDate.setFormats(new SimpleDateFormat("dd/MM/yyyy"));
			
			toDate = new JXDatePicker();
			toDate.setDate(Calendar.getInstance().getTime());
			toDate.setFormats(new SimpleDateFormat("dd/MM/yyyy"));
			
			paymentDate = new JXDatePicker();
			paymentDate.setDate(Calendar.getInstance().getTime());
			paymentDate.setFormats(new SimpleDateFormat("dd/MM/yyyy"));
			
			conversionDate = new JXDatePicker();
			conversionDate.setDate(Calendar.getInstance().getTime());
			conversionDate.setFormats(new SimpleDateFormat("dd/MM/yyyy"));
			
			
			searchBill = new MyButton("Search");
			print = new MyButton("Print");
			save = new MyButton("save");
			conversionSave = new MyButton("Save Convert");
			
			contact = new JComboBox();
			contact.setSize(300, 10);
			
			paymentType = new JComboBox();
			paymentType.setSize(300, 10);
			
			metalType = new JComboBox();
			metalType.setSize(300, 10);
			
			conversionType = new JComboBox();
			conversionType.setSize(300, 10);
			
			searchButtonClick = false;
			
			conversionRate = new MyTextField(10);
			conversionAmount = new MyTextField(10);
			conversionMetal = new MyTextField(10);
			
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
			
			MyPanel.setBorder("Billing Reports", panel);
			MyPanel.setBackground(firstPanel);
			MyPanel.setBackground(secondPanel);
			MyPanel.setBackground(thirdPanel);
			MyPanel.setBackground(thirdPanelLeft);
			MyPanel.setBackground(thirdPanelRight);
			
			reportTable = new JTable();
            /*
			dialog = new JDialog();
			Window parentWindow = SwingUtilities.getWindowAncestor(panel);
			dialog = new JDialog(parentWindow, ModalityType.APPLICATION_MODAL);
			*/
			popupMenu = new JPopupMenu();
            editRecord = new JMenuItem();
            deleteRecord = new JMenuItem();
            enableDisableRecord = new JMenuItem();
            
            contact.setEditable(true);
			tfContact = (JTextField) contact.getEditor().getEditorComponent();
			
			metalType.setEditable(true);
			tfMetal = (JTextField) metalType.getEditor().getEditorComponent();
            
            editRecord.addActionListener(this);
            deleteRecord.addActionListener(this);
            enableDisableRecord.addActionListener(this);
            searchBill.addActionListener(this);
            print.addActionListener(this);
            contact.addActionListener(this);
			tfContact.addActionListener(this);
			tfMetal.addActionListener(this);
			metalReceived.addActionListener(this);
			amountReceived.addActionListener(this);
			save.addActionListener(this);
			conversionDate.addActionListener(this);
			conversionType.addActionListener(this);
			conversionRate.addActionListener(this);
			conversionAmount.addActionListener(this);
			conversionMetal.addActionListener(this);
			conversionSave.addActionListener(this);
			
			contact.addKeyListener(this);
			tfContact.addKeyListener(this);
			tfMetal.addKeyListener(this);
			metalReceived.addKeyListener(this);
			amountReceived.addKeyListener(this);
			conversionDate.addKeyListener(this);
			conversionType.addKeyListener(this);
			conversionRate.addKeyListener(this);
			conversionAmount.addKeyListener(this);
			conversionMetal.addKeyListener(this);
			conversionSave.addKeyListener(this);
			
    	}catch(Exception ex){
    		ex.printStackTrace();
    	}
    }
    private void constructPaymentForm(){
    	try{
    		MyPanel.setBorder("Payment "+billSource, panel);
    		
    		tableModelling(reportTable);
			editRecord.setText("Edit Entry");
			deleteRecord.setText("Delete Entry");
			enableDisableRecord.setText("Enable/Disable");
			
			popupMenu.add(editRecord);
			popupMenu.add(deleteRecord);
			//popupMenu.add(enableDisableRecord);
			
			reportTable.setComponentPopupMenu(popupMenu);
            
            reportTable.setBackground(Color.WHITE);
            scrollPane = new JScrollPane(reportTable);
            Dimension d = reportTable.getPreferredSize();
            int rows=25;
            scrollPane.setPreferredSize(
            new Dimension(d.width,reportTable.getRowHeight()*rows+1));
            secondPanel.add(scrollPane,"span,pushx,growx,wrap");
            
            PaymentTableRenderer render =new PaymentTableRenderer();
            //TableRendererForContact render = new TableRendererForContact();
            reportTable.setDefaultRenderer(Object.class,  render);
            reportTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            
            firstPanel.add(new MyLabel("Customer:"));
            firstPanel.add(contact);
            firstPanel.add(new MyLabel("From Date:"));
            firstPanel.add(fromDate);
            firstPanel.add(new MyLabel("To Date:"));
            firstPanel.add(toDate);
            firstPanel.add(searchBill);
            firstPanel.add(print);
            firstPanel.add(new MyLabel("Search Bill"),"push,al right");
            firstPanel.add(searchTextField,"al right, wrap");
            setModel(contact,tfContact,new DefaultComboBoxModel(new Vector<>(contactList)), "");
            setModel(metalType,tfMetal,new DefaultComboBoxModel(new Vector<>(metalList)), "");
            paymentType.setModel(new DefaultComboBoxModel(new Vector<>(ConstantUtils.PAYMENT_TYPE)));
            
            panel.add(firstPanel,"span,pushx,growx,wrap");
            panel.add(secondPanel,"span,pushx,growx,wrap");
            panel.add(thirdPanel,"span,pushx,growx,wrap");
    	}catch(Exception ex){
    		ex.printStackTrace();
    	}
    }
	@Override
	public void actionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
		onClickEvent(evt.getSource());
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
		}else if(evt.getSource() == conversionRate || evt.getSource() == conversionMetal || evt.getSource()==conversionAmount) {
			conversionTypeCalculation();
		}
	}

	@Override
	public void keyReleased(KeyEvent evt) {
		// TODO Auto-generated method stub
		if(evt.getSource() == conversionRate || evt.getSource() == conversionMetal || evt.getSource()==conversionAmount) {
			conversionTypeCalculation();
		}
	}

	@Override
	public void keyTyped(KeyEvent evt) {
		// TODO Auto-generated method stub
		if(evt.getSource()==tfContact){
			contactKeyTypedEvent(evt);
		}else if(evt.getSource() == conversionRate || evt.getSource() == conversionMetal || evt.getSource()==conversionAmount) {
			conversionTypeCalculation();
		}
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
	
	private void setModel(JComboBox combo,JTextField tf,DefaultComboBoxModel mdl, String str) {
        combo.setModel(mdl);
        combo.setSelectedIndex(-1);
        tf.setText(str);
	}
	
	private static DefaultComboBoxModel getContactSuggestedModel(List<Contact> list, String text) {
        DefaultComboBoxModel m = new DefaultComboBoxModel();
        for(Contact s: list) {
            if(s.getContactName().toUpperCase().startsWith(text.toUpperCase())) m.addElement(s);
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
		        	((Component) evt.getSource()).transferFocus();
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
	
	private void tableModelling(JTable jTable){
		try{
			
			reportModel = new DefaultTableModel() {

				public Class<?> getColumnClass(int column) {
					switch (column) {
					
					case 0:
						return String.class;//record Id
					case 1:
						return String.class;//Record Type(bill or payment)
					case 2:
						return String.class;//Bill Date
					case 3:
						return String.class;//Bill Number
					case 4:
						return String.class;//Netweight
					case 5:
						return String.class;//Metal Balance (Netweight for GST metal Balance for nongst)
					case 6:
						return String.class;//Amount
					case 7:
						return String.class;//Payment Date
					case 8:
						return String.class;//Payment Metal
					case 9:
						return String.class;//Payment Amount
					default:
						return String.class;
					}
				}
			};
			jTable.setModel(reportModel);
			reportModel.addColumn("recordId");
			reportModel.addColumn("recordType");
        	reportModel.addColumn("Bill Date");
        	reportModel.addColumn("Bill No");
        	reportModel.addColumn("Weight");
        	reportModel.addColumn("Metal Balance");
        	reportModel.addColumn("Amount");
        	reportModel.addColumn("Payment Date");
        	reportModel.addColumn("Metal Paid");
        	reportModel.addColumn("Payment Amount");
        	

        	jTable.getColumn("recordId").setPreferredWidth(0);
        	jTable.getColumn("recordId").setMinWidth(0);
        	jTable.getColumn("recordId").setMaxWidth(0);
        	
        	jTable.getColumn("recordType").setPreferredWidth(0);
        	jTable.getColumn("recordType").setMinWidth(0);
        	jTable.getColumn("recordType").setMaxWidth(0);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private void setContactInformation(){
		try{
			//Date fromDate,Date toDate,Integer billSourceId,Integer contactId,Integer licenceId
			Date fromDateTemp = fromDate.getDate();
			Date toDateTemp = toDate.getDate();
			Integer billSourceIdTemp=0;
			Integer contactIdTemp = 0;
			Integer licenceIdTemp = 0;
			
			Contact contactObj = (Contact)contact.getSelectedItem();
			contactIdTemp = contactObj.getContactId();
			billSourceIdTemp = MasterController.getInstace().getBillSourceByCode(billSource).getBillSourceId();
			licenceIdTemp = Session.licence.getLicenceId();
			
			PaymentDTO paymentDTO = null;
			paymentDTO = BillController.getInstace().getPaymentDTODetails(null, null, billSourceIdTemp, contactIdTemp, licenceIdTemp,billType);
			
			ContactTransactionDTO contactTransactionDTO = BillController.getInstace().getContactTransactionDetails(DateUtils.getDate("01/01/1970"), toDate.getDate(), billSource, contactIdTemp, licenceIdTemp, billType);
			
			//formTable(paymentDTO);
			formTableFromContactTransactionDate(contactTransactionDTO);
			searchButtonClick = false;
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private void fetchBillDetailOnButtonClick(){
		try{
			//Date fromDate,Date toDate,Integer billSourceId,Integer contactId,Integer licenceId
			Date fromDateTemp = fromDate.getDate();
			Date toDateTemp = toDate.getDate();
			Integer billSourceIdTemp=0;
			Integer contactIdTemp = 0;
			Integer licenceIdTemp = 0;
			
			Contact contactObj = (Contact)contact.getSelectedItem();
			contactIdTemp = contactObj.getContactId();
			billSourceIdTemp = MasterController.getInstace().getBillSourceByCode(billSource).getBillSourceId();
			licenceIdTemp = Session.licence.getLicenceId();
			
			PaymentDTO paymentDTO = null;
			paymentDTO = BillController.getInstace().getPaymentDTODetails(fromDateTemp, toDateTemp, billSourceIdTemp, contactIdTemp, licenceIdTemp,billType);
			
			
			//BalanceSheetDTO balanceSheetDTO = BalanceSheetController.getInstace().getBalanceSheetList(toDateTemp, licenceIdTemp, contactObj.getContactType().getContactTypeName(), billSource,billType,contactIdTemp);
			
			ContactTransactionDTO contactTransactionDTO = BillController.getInstace().getContactTransactionDetails(fromDateTemp, toDateTemp, billSource, contactIdTemp, licenceIdTemp, billType);
			
			//formTable(paymentDTO);
			//formTableNew(balanceSheetDTO);
			formTableFromContactTransactionDate(contactTransactionDTO);
			searchButtonClick = true;
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private void formTable(PaymentDTO paymentDTO){
		try{
			int rowCount = reportModel.getRowCount();
            for (int i = 0; i < rowCount; i++) {
            	reportModel.removeRow(0);
            }
            
            if(paymentDTO!=null && paymentDTO.getPaymentDTOList()!=null && paymentDTO.getPaymentDTOList().size()>0){
            	for(PaymentDTOList data:paymentDTO.getPaymentDTOList()){
            		if(!data.getRecordType().equalsIgnoreCase("Total")) {
	            		reportModel.addRow(new Object[]{
	            				data.getRecordId(),
	            				data.getRecordType(),
	            				(data.getBillDate()!=null)?StringUtils.getStrDate(data.getBillDate()):"",
	            				data.getBillNumber(),
	            				(data.getWeight() != null)?Calculator.formatThreeDecimal(data.getWeight()):"",
	            				(data.getMetalBalance() != null)?Calculator.formatThreeDecimal(data.getMetalBalance()):"",
	            				(data.getBillingAmount()!=null)?Calculator.formatThreeDecimal(data.getBillingAmount()):"",
	            				(data.getPaymentDate()!=null)?StringUtils.getStrDate(data.getPaymentDate()):"",
	            				(data.getPaymentMetal() != null)?Calculator.formatThreeDecimal(data.getPaymentMetal()):"",
	            	            (data.getPaymentAmount()!=null)?Calculator.formatThreeDecimal(data.getPaymentAmount()):""
	            	            						
	            		});
            		}else if(data.getRecordType().equalsIgnoreCase("Total")) {
            			reportModel.addRow(new Object[]{
	            				data.getRecordId(),
	            				data.getRecordType(),
	            				(data.getBillDate()!=null)?StringUtils.getStrDate(data.getBillDate()):"",
	            				data.getBillNumber(),
	            				(data.getWeight() != null)?Calculator.formatThreeDecimal(data.getWeight()):"",
	            				//(data.getMetalBalance() != null)?Calculator.formatThreeDecimal(data.getMetalBalance()):"",
	            				//data.getMetalBalanceStr(),
	            				data.getTotalMetalBalanceStr(),
	            				(data.getBillingAmount()!=null)?Calculator.formatThreeDecimal(data.getBillingAmount()):"",
	            				(data.getPaymentDate()!=null)?StringUtils.getStrDate(data.getPaymentDate()):"",
	            				//(data.getPaymentMetal() != null)?Calculator.formatThreeDecimal(data.getPaymentMetal()):"",
	            				data.getTotalMetalPaidStr(),
	            	            (data.getPaymentAmount()!=null)?Calculator.formatThreeDecimal(data.getPaymentAmount()):""
	            	            						
	            		});
            		}
            	}
            }
            
            constructSummaryPanel(paymentDTO);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private void formTableFromContactTransactionDate(ContactTransactionDTO contactTransactionDTO){
		try{
			int rowCount = reportModel.getRowCount();
            for (int i = 0; i < rowCount; i++) {
            	reportModel.removeRow(0);
            }
            //Display opening Balance Starts
            if(contactTransactionDTO!=null && (contactTransactionDTO.getOpeningBalance()!=null || contactTransactionDTO.getOpeningMetalBlance()!=null)) {
            	reportModel.addRow(new Object[]{
        				"-1",
        				ConstantUtils.OPENING,
        				"",
        				"Opening Balance",
        				"",
        				contactTransactionDTO.getOpeningMetalBlance(),
        				contactTransactionDTO.getOpeningBalance(),        				
        				"","",""
        		});
            }
            //Display opening Balance Ends
            
            //Display Bill and Payment Details Starts
            if(contactTransactionDTO!=null && contactTransactionDTO.getContactTransactionList()!=null) {
            	for(ContactTransactionDTOList data: contactTransactionDTO.getContactTransactionList()) {
            		if(data.gettType().equalsIgnoreCase(ConstantUtils.BILL)) {
            			reportModel.addRow(new Object[]{
	            				data.getBillId(),
	            				data.gettType(),
	            				(data.getBillDate()!=null)?StringUtils.getStrDate(data.getBillDate()):"",
	            				data.getBillNumber(),
	            				(data.getBillWeight() != null)?Calculator.formatThreeDecimal(data.getBillWeight()):"",
	            				(data.getBillMetalBalance() != null)?data.getMetalName().charAt(0)+": "+ Calculator.formatThreeDecimal(data.getBillMetalBalance()):"",
	            				(data.getBillAmount()!=null)?Calculator.formatThreeDecimal(data.getBillAmount()):"",
	            				"","",""
	            		});
            		}else if(data.gettType().equalsIgnoreCase(ConstantUtils.PAYMENT)) {
            			reportModel.addRow(new Object[]{
	            				data.getPaymentId(),
	            				data.gettType(),
	            				"",
	            				"",
	            				"",
	            				"",
	            				"",
	            				(data.getPaymentDate()!=null)?StringUtils.getStrDate(data.getPaymentDate()):"",
	            				(data.getPaymentMetalPaid() != null)?data.getMetalName().charAt(0)+": "+ Calculator.formatThreeDecimal(data.getPaymentMetalPaid()):"",
	            	            (data.getPaymentAmountPaid()!=null)?Calculator.formatThreeDecimal(data.getPaymentAmountPaid()):""
	            				
	            		});
            		}
            	}
            }
            //Display Bill and Payment Details Ends
            
            //Display Current Balance Details Starts
            if(contactTransactionDTO!=null && (contactTransactionDTO.getCurrentMetalBalance()!=null || contactTransactionDTO.getCurrentAmountBalance()!=null)) {
            	reportModel.addRow(new Object[]{
        				"-1",
        				ConstantUtils.TOTAL,
        				"",
        				"Current Total",
        				"",
        				contactTransactionDTO.getCurrentMetalBalance(),
        				contactTransactionDTO.getCurrentAmountBalance(),        				
        				"","",""
        		});
            }
            //Display Current Balance Details Ends
            constructSummaryPanelUsingContactDTO(contactTransactionDTO);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private void formTableNew(BalanceSheetDTO balanceSheetDTO){
		try{
			int rowCount = reportModel.getRowCount();
            for (int i = 0; i < rowCount; i++) {
            	reportModel.removeRow(0);
            }
            
            if(balanceSheetDTO!=null && balanceSheetDTO.getBalanceSheetDTOList()!=null && balanceSheetDTO.getBalanceSheetDTOList().size()>0){
            	for(BalanceSheetDTOList tempData:balanceSheetDTO.getBalanceSheetDTOList()){
            		for(BalanceSheetDTOList data: tempData.getBalanceSheetDTOList()) {
            		if(data.getType().equalsIgnoreCase("BILL")) {
	            		reportModel.addRow(new Object[]{
	            				data.gettID(),
	            				data.getType(),
	            				(data.gettDate()!=null)?StringUtils.getStrDate(data.gettDate()):"",
	            				data.gettNumber(),
	            				(data.getWeight() != null)?Calculator.formatThreeDecimal(data.getMetalBalance()):"",
	            				(data.getMetalBalance() != null)?Calculator.formatThreeDecimal(data.getMetalBalance()):"",
	            				(data.getAmountBalance()!=null)?Calculator.formatThreeDecimal(data.getAmountBalance()):"",
	            				"","",""
	            		});
            		}else if(data.getType().equalsIgnoreCase("PAYMENT")) {
            			reportModel.addRow(new Object[]{
	            				data.gettID(),
	            				data.getType(),
	            				"",
	            				"",
	            				"",
	            				"",
	            				"",
	            				(data.gettDate()!=null)?StringUtils.getStrDate(data.gettDate()):"",
	            				(data.getMetalBalance() != null)?Calculator.formatThreeDecimal(data.getMetalBalance()):"",
	            				(data.getAmountBalance()!=null)?Calculator.formatThreeDecimal(data.getAmountBalance()):""
	            		});
            			
            		}else if(data.getType().equalsIgnoreCase("OPENING_BALANCE")) {
            			reportModel.addRow(new Object[]{
	            				data.gettID(),
	            				data.getType(),
	            				"",
	            				(data.getAmountBalance() != null && data.getAmountBalance()>0)?"Opening Cash ":(data.getMetalBalance()!=null)?"Opening Metal "+data.getMetalName():"",
	            				"",
	            				(data.getMetalBalance() != null)?Calculator.formatThreeDecimal(data.getMetalBalance()):"",
	    	            		(data.getAmountBalance()!=null)?Calculator.formatThreeDecimal(data.getAmountBalance()):"",
	            				"",
	            				"",
	            				""
	            		});
            			
            		}
            		/*}else if(data.getRecordType().equalsIgnoreCase("Total")) {
            			reportModel.addRow(new Object[]{
	            				data.getRecordId(),
	            				data.getRecordType(),
	            				(data.getBillDate()!=null)?StringUtils.getStrDate(data.getBillDate()):"",
	            				data.getBillNumber(),
	            				(data.getWeight() != null)?Calculator.formatThreeDecimal(data.getWeight()):"",
	            				//(data.getMetalBalance() != null)?Calculator.formatThreeDecimal(data.getMetalBalance()):"",
	            				//data.getMetalBalanceStr(),
	            				data.getTotalMetalBalanceStr(),
	            				(data.getBillingAmount()!=null)?Calculator.formatThreeDecimal(data.getBillingAmount()):"",
	            				(data.getPaymentDate()!=null)?StringUtils.getStrDate(data.getPaymentDate()):"",
	            				//(data.getPaymentMetal() != null)?Calculator.formatThreeDecimal(data.getPaymentMetal()):"",
	            				data.getTotalMetalPaidStr(),
	            	            (data.getPaymentAmount()!=null)?Calculator.formatThreeDecimal(data.getPaymentAmount()):""
	            	            						
	            		});
            		}*/
            	}
            	}
            	
            	//Show Total in the last row -Start
            	/*reportModel.addRow(new Object[]{
            			"",//balanceSheetDTO.gettID(),
        				"Total",
        				"",
        				"Total",
        				"",
        				balanceSheetDTO.getTotalMetalBalanceStr(),
        				balanceSheetDTO.getTotalAmountBalance(),
        				"",
        				"",
        				""
        		});*/
            	//Show total in the last row ends
            }
            
            //constructSummaryPanel(paymentDTO);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private void constructSummaryPanel(PaymentDTO paymentDTO){
		try{
			thirdPanel.removeAll();
			thirdPanelLeft.removeAll();
			thirdPanelRight.removeAll();
			
			
			thirdPanelLeft.add(new MyLabel("Payment Date:"));
			thirdPanelLeft.add(paymentDate,"wrap");
			
			if(billType.equalsIgnoreCase(ConstantUtils.NON_GST_ENTRY)) {
				if(paymentDTO.getMetalBalance()!=null){
					thirdPanelLeft.add(new MyLabel("Metal Balance:"));
					//thirdPanelLeft.add(new MyLabel(Calculator.formatThreeDecimal(paymentDTO.getMetalBalance())),"wrap");
					thirdPanelLeft.add(new MyLabel(paymentDTO.getMetalBalanceStr()),"wrap");
				}else{
					thirdPanelLeft.add(new MyLabel("Metal Balance:"),"wrap");
				}
			}
			if(paymentDTO.getAmountBalance()!=null){
				thirdPanelLeft.add(new MyLabel("Amount Balance:"));
				thirdPanelLeft.add(new MyLabel(Calculator.formatThreeDecimal(paymentDTO.getAmountBalance())),"wrap");
			}else{
				thirdPanelLeft.add(new MyLabel("Amount Balance:"),"wrap");
			}
            metalReceived.setText("");
            amountReceived.setText("");
            
            if(billType.equalsIgnoreCase(ConstantUtils.NON_GST_ENTRY)) {
            	thirdPanelLeft.add(new MyLabel("Metal Type:"));
            	thirdPanelLeft.add(metalType,"wrap");
            	
            	if(billSource.contains("SUPPLIER")) {
            		thirdPanelLeft.add(new MyLabel("Metal Paid:"));
            	}else {
            		thirdPanelLeft.add(new MyLabel("Metal Received:"));
            	}
	            
            	thirdPanelLeft.add(metalReceived,"wrap");
            }
            
            if(billSource.contains("SUPPLIER")) {
            	thirdPanelLeft.add(new MyLabel("Amount Paid:"));
            }else {
            	thirdPanelLeft.add(new MyLabel("Amount Received:"));
            }
            
            thirdPanelLeft.add(amountReceived);
            
            thirdPanelLeft.add(new MyLabel("Payment Type:"));
            thirdPanelLeft.add(paymentType,"wrap");
            
            if(billSource.contains("NONGST")) {
            	paymentType.setSelectedIndex(0);
            }else {
            	paymentType.setSelectedIndex(1);
            }
            
            
            thirdPanelLeft.add(save);
            
            
            
            //thirdPanelRight starts
            conversionType.setModel(new DefaultComboBoxModel(new Vector<>(ConstantUtils.CONVERSION_TYPE)));
            thirdPanelRight.add(new MyLabel("Conversion Date :"));
            thirdPanelRight.add(conversionDate,"wrap");
            
            thirdPanelRight.add(new MyLabel("Conversion Type:"));
            thirdPanelRight.add(conversionType,"wrap");
            
            thirdPanelRight.add(new MyLabel("Conversion Rate:"));
            thirdPanelRight.add(conversionRate,"wrap");
            
            thirdPanelRight.add(new MyLabel("Conversion Amt:"));
            thirdPanelRight.add(conversionAmount,"wrap");
            
            thirdPanelRight.add(new MyLabel("Conversion Metal:"));
            thirdPanelRight.add(conversionMetal,"wrap");
            
            thirdPanelRight.add(conversionSave);
            //thirdPanelRight ends
            
            
            thirdPanel.add(thirdPanelLeft);
            thirdPanel.add(thirdPanelRight);
            
            
			thirdPanel.revalidate();
			thirdPanel.repaint();
			
			thirdPanelLeft.revalidate();
			thirdPanelLeft.repaint();
			
			thirdPanelRight.revalidate();
			thirdPanelRight.repaint();
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private void constructSummaryPanelUsingContactDTO(ContactTransactionDTO contactTransactionDTO){
		try{
			thirdPanel.removeAll();
			thirdPanelLeft.removeAll();
			thirdPanelRight.removeAll();
			
			
			thirdPanelLeft.add(new MyLabel("Payment Date:"));
			thirdPanelLeft.add(paymentDate,"wrap");
			
			if(billType.equalsIgnoreCase(ConstantUtils.NON_GST_ENTRY)) {
				if(contactTransactionDTO.getCurrentMetalBalance()!=null){
					thirdPanelLeft.add(new MyLabel("Metal Balance:"));
					//thirdPanelLeft.add(new MyLabel(Calculator.formatThreeDecimal(paymentDTO.getMetalBalance())),"wrap");
					thirdPanelLeft.add(new MyLabel(contactTransactionDTO.getCurrentMetalBalance()),"wrap");
				}else{
					thirdPanelLeft.add(new MyLabel("Metal Balance:"),"wrap");
				}
			}
			if(contactTransactionDTO.getCurrentAmountBalance()!=null){
				thirdPanelLeft.add(new MyLabel("Amount Balance:"));
				thirdPanelLeft.add(new MyLabel(contactTransactionDTO.getCurrentAmountBalance()),"wrap");
			}else{
				thirdPanelLeft.add(new MyLabel("Amount Balance:"),"wrap");
			}
            metalReceived.setText("");
            amountReceived.setText("");
            
            if(billType.equalsIgnoreCase(ConstantUtils.NON_GST_ENTRY)) {
            	thirdPanelLeft.add(new MyLabel("Metal Type:"));
            	thirdPanelLeft.add(metalType,"wrap");
            	metalType.setSelectedIndex(0);
            	
            	if(billSource.contains("SUPPLIER") || billSource.contains("PURCHASE")) {
            		thirdPanelLeft.add(new MyLabel("Metal Paid:"));
            	}else {
            		thirdPanelLeft.add(new MyLabel("Metal Received:"));
            	}
	            
            	thirdPanelLeft.add(metalReceived,"wrap");
            }
            
            if(billSource.contains("SUPPLIER")) {
            	thirdPanelLeft.add(new MyLabel("Amount Paid:"));
            }else {
            	thirdPanelLeft.add(new MyLabel("Amount Received:"));
            }
            
            thirdPanelLeft.add(amountReceived);
            
            thirdPanelLeft.add(new MyLabel("Payment Type:"));
            thirdPanelLeft.add(paymentType,"wrap");
            
            if(billSource.contains("NONGST")) {
            	paymentType.setSelectedIndex(0);
            }else {
            	paymentType.setSelectedIndex(1);
            }
            
            
            thirdPanelLeft.add(save);
            
            
            
            //thirdPanelRight starts
            conversionType.setModel(new DefaultComboBoxModel(new Vector<>(ConstantUtils.CONVERSION_TYPE)));
            thirdPanelRight.add(new MyLabel("Conversion Date :"));
            thirdPanelRight.add(conversionDate,"wrap");
            
            thirdPanelRight.add(new MyLabel("Conversion Type:"));
            thirdPanelRight.add(conversionType,"wrap");
            
            thirdPanelRight.add(new MyLabel("Conversion Rate:"));
            thirdPanelRight.add(conversionRate,"wrap");
            
            thirdPanelRight.add(new MyLabel("Conversion Amt:"));
            thirdPanelRight.add(conversionAmount,"wrap");
            
            thirdPanelRight.add(new MyLabel("Conversion Metal:"));
            thirdPanelRight.add(conversionMetal,"wrap");
            
            thirdPanelRight.add(conversionSave);
            //thirdPanelRight ends
            
            
            thirdPanel.add(thirdPanelLeft);
            thirdPanel.add(thirdPanelRight);
            
            
			thirdPanel.revalidate();
			thirdPanel.repaint();
			
			thirdPanelLeft.revalidate();
			thirdPanelLeft.repaint();
			
			thirdPanelRight.revalidate();
			thirdPanelRight.repaint();
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private void savePayment(){
		try{
			Double metalPaid = 0.0;
			Double amountPaid = 0.0;
			
			if(!metalReceived.getText().equalsIgnoreCase(ConstantUtils.BLANK)){
				metalPaid = Double.parseDouble(metalReceived.getText().toString());
			}
			if(!amountReceived.getText().equalsIgnoreCase(ConstantUtils.BLANK)){
				amountPaid = Double.parseDouble(amountReceived.getText().toString());
			}
			boolean validationFlag = paymentValidation();
			boolean saveFlagMetal = true;
			boolean saveFlagAmount = true;
			if(validationFlag){
				
				if(metalPaid > 0.0){
					saveFlagMetal = saveMetalPaid(metalPaid);
				}
				
				if(amountPaid>0.0){
					saveFlagAmount = saveAmountPaid(amountPaid);
				}
				
				
				if(saveFlagAmount && saveFlagMetal){
				JOptionPane.showMessageDialog(null, "Payment Saved!!");
				if(calledFrom.equalsIgnoreCase(ConstantUtils.MENU)){
					if(searchButtonClick){
						fetchBillDetailOnButtonClick();
					}else{
						setContactInformation();
					}
				}
				}else{
					JOptionPane.showMessageDialog(null, "Error in Payment save");
				}
				
			}
			
			
			
			
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private boolean saveMetalPaid(Double metalPaid){
		boolean saveFlag = true;
		try{
			PaymentDetails payment = new PaymentDetails();
			Contact contactObj = (Contact) contact.getSelectedItem();
			Metal metalObj = (Metal)metalType.getSelectedItem();
			
			payment.setBillType(MasterController.getInstace().getBillType(billType));
			//payment.setMetal(MasterController.getInstace().getMetal(metal));
			payment.setMetal(metalObj);
			payment.setBillSource(MasterController.getInstace().getBillSourceByCode(billSource));

			payment.setContact(contactObj);
			payment.setDeletedFlag(ConstantUtils.CONSTANT_N);
			payment.setLicence(Session.licence);
			payment.setMetalPaid(metalPaid);
			payment.setPaymentDate(new Date());
			
			saveFlag = savePaymentDetails(payment);
			
		}catch(Exception ex){
			
		}
		return saveFlag;
	}
	private boolean saveAmountPaid(Double amountPaid){
		boolean saveFlag = true;
		try{
			PaymentDetails payment = new PaymentDetails();
			Contact contactObj = (Contact) contact.getSelectedItem();
			
			
			payment.setBillType(MasterController.getInstace().getBillType(billType));
			//payment.setMetal(MasterController.getInstace().getMetal(metal));
			payment.setBillSource(MasterController.getInstace().getBillSourceByCode(billSource));

			payment.setContact(contactObj);
			payment.setDeletedFlag(ConstantUtils.CONSTANT_N);
			payment.setLicence(Session.licence);
			//payment.setMetalPaid(metalPaid);
			payment.setPaymentDate(new Date());
			payment.setAmountPaid(amountPaid);
			
			if(paymentType.getSelectedItem().toString().equalsIgnoreCase(ConstantUtils.BANK)){
				payment.setBank(Session.licence.getBank());
			}
			
			saveFlag = savePaymentDetails(payment);
		}catch(Exception ex){
			
		}
		return saveFlag;
	}
	
	private boolean savePaymentDetails(PaymentDetails payment){
		boolean saveFlag = false;
		try{
			payment.setPaymentDate(paymentDate.getDate());
			PaymentDetails obj = BillController.getInstace().saveOrUpdatePaymentDetails(payment);
			
			if(obj!=null && obj.getPaymentDetailsId()>0){
				saveFlag = true;
			}
		}catch(Exception ex){
			
		}
		return saveFlag;
	}
	
	private boolean paymentValidation(){
		boolean flag=true;
		
		return flag;
	}
	
	
	private void onClickEvent(Object evt){
		try{
			if(evt ==  editRecord || evt ==  deleteRecord || evt == enableDisableRecord){
				int selectedRow = reportTable.getSelectedRow();
				Integer recordId=0;
				String recordType="";
				
				if(selectedRow>-1){
					String selectedId = reportModel.getValueAt(selectedRow, 0).toString();
					recordId = (Integer)reportTable.getValueAt(selectedRow, 0);
					recordType = (String)reportTable.getValueAt(selectedRow, 1);
					System.out.println("recordId->"+recordId+"recordType->"+recordType);
					
					if(recordId>0 && (recordType.equalsIgnoreCase(ConstantUtils.BILL) || recordType.equalsIgnoreCase(ConstantUtils.PAYMENT))){
						if(recordType.equalsIgnoreCase(ConstantUtils.BILL)){
							if(evt ==  editRecord){
								AppController.getInstance().addBillingFormPage(ConstantUtils.PAYMENT_FORM, recordId, 0, billType, metal, billSource);
							}else if(evt ==  deleteRecord){
								int selected = JOptionPane.showConfirmDialog(null, "Are you sure You want to delete Bill?");
								if(selected==JOptionPane.YES_OPTION){
									Integer deleteFlag = BillController.getInstace().deleteBill(recordId);
									if(deleteFlag!=null && deleteFlag>0){
										JOptionPane.showMessageDialog(null, "Bill Deleted!!");
										if(searchButtonClick){
											fetchBillDetailOnButtonClick();
										}else{
											setContactInformation();
										}
									}else{
										JOptionPane.showMessageDialog(null, "Bill Delete failed");
									}
								}
							}
						}else if(recordType.equalsIgnoreCase(ConstantUtils.PAYMENT)){
							if(evt ==  editRecord){
								JOptionPane.showMessageDialog(null, "Payment Can't be edited");
								//Show edit popup starts
								/*String metalPaidStr = (String)reportTable.getValueAt(selectedRow, 8);
								Integer cashPaidStr = (Integer)reportTable.getValueAt(selectedRow, 0);
								editPayment(recordId,metalPaidStr,""+cashPaidStr);*/
								//Show Edit Popup ends
							}else if(evt ==  deleteRecord){
								int selected = JOptionPane.showConfirmDialog(null, "Are you sure You want to delete the payment?");
								if(selected==JOptionPane.YES_OPTION){
									PaymentDetails deleteObj = BillController.getInstace().getPaymentDetails(recordId);
									if(deleteObj!=null){
										deleteObj.setDeletedFlag(ConstantUtils.CONSTANT_Y);
										deleteObj = BillController.getInstace().saveOrUpdatePaymentDetails(deleteObj);
										if(deleteObj!=null && deleteObj.getDeletedFlag().equalsIgnoreCase(ConstantUtils.CONSTANT_Y)){
											JOptionPane.showMessageDialog(null, "Payment Deleted!!");
											if(searchButtonClick){
												fetchBillDetailOnButtonClick();
											}else{
												setContactInformation();
											}
										}
									}
								}
							}
						}
					}else{
						JOptionPane.showMessageDialog(null, "No Action can be performed");
					}
					
					
				}
			}else if(evt == searchBill){
				searchButtonClick = true;
				fetchBillDetailOnButtonClick();
			}else if(evt == print) {
				printData();
			}else if(evt == save){
				savePayment();
			}else if(evt == conversionType) {
				conversionTypeChanged();
			}else if(evt == conversionRate || evt == conversionMetal || evt == conversionAmount) {
				conversionTypeCalculation();
			}else if(evt == conversionSave) {
				saveConversion();
			}
		}catch(Exception ex){
    		ex.printStackTrace();
    	}
	}
	
	private void loadPaymentForm(){
		try{
			int tempCounter=-1;
			int tempSetId=-1;
			
			for(Contact obj:contactList){
				tempCounter++;
				if(obj.getContactId().equals(customerId)){
					tempSetId = tempCounter;
				}
			}
			
			contact.setSelectedIndex(tempSetId);
			setContactInformation();
		}catch(Exception ex){
    		ex.printStackTrace();
    	}
	}
	
	private void printData() {
		try {
			//Date fromDate,Date toDate,Integer billSourceId,Integer contactId,Integer licenceId
			Date fromDateTemp = fromDate.getDate();
			Date toDateTemp = toDate.getDate();
			Integer billSourceIdTemp=0;
			Integer contactIdTemp = 0;
			Integer licenceIdTemp = 0;
			
			Contact contactObj = (Contact)contact.getSelectedItem();
			contactIdTemp = contactObj.getContactId();
			billSourceIdTemp = MasterController.getInstace().getBillSourceByCode(billSource).getBillSourceId();
			licenceIdTemp = Session.licence.getLicenceId();
			
			PaymentDTO paymentDTO = null;
			if(searchButtonClick) {
				paymentDTO = BillController.getInstace().getPaymentDTODetails(fromDateTemp, toDateTemp, billSourceIdTemp, contactIdTemp, licenceIdTemp,billType);
			}else {
				paymentDTO = BillController.getInstace().getPaymentDTODetails(null, null, billSourceIdTemp, contactIdTemp, licenceIdTemp,billType);
			}
			
			RunReport.printContactBillAndPayment(paymentDTO,contactObj);
		}catch(Exception ex){
    		ex.printStackTrace();
    	}
	}
	
	private void conversionTypeChanged() {
		try {
			String conversionTypeStr = (String)conversionType.getSelectedItem();
			
			if(conversionTypeStr.startsWith("Cash")) {
				conversionMetal.setEditable(false);
				conversionAmount.setEditable(true);
			}else if(conversionTypeStr.startsWith("Metal")) {
				conversionAmount.setEditable(false);
				conversionMetal.setEditable(true);
			}
			
		}catch(Exception ex){
    		ex.printStackTrace();
    	}
	}
	
	
	private void conversionTypeCalculation() {
		try {
			String conversionTypeStr = (String)conversionType.getSelectedItem();
			double rate=0.0;
			double amount=0.0;
			double metal = 0.0;
			if(!conversionRate.getText().equals(ConstantUtils.BLANK)) {
				rate = Double.parseDouble(conversionRate.getText());
			}
			if(conversionTypeStr.startsWith("Cash")) {
				if(!conversionAmount.getText().equalsIgnoreCase(ConstantUtils.BLANK)) {
					amount = Double.parseDouble(conversionAmount.getText());
					metal = Calculator.getThreeDigitDecimal(amount/rate);
					
					conversionMetal.setText(Double.toString(metal));
				}
			}else if(conversionTypeStr.startsWith("Metal")) {
				if(!conversionMetal.getText().equalsIgnoreCase(ConstantUtils.BLANK)) {
					metal = Double.parseDouble(conversionMetal.getText());
					amount = Math.round(metal*rate);
					
					conversionAmount.setText(Double.toString(amount));
				}
			}
		}catch(Exception ex){
    		ex.printStackTrace();
    	}
	}
	
	private void saveConversion() {
		try {
			String conversionTypeStr = (String)conversionType.getSelectedItem();
			double rate=0.0;
			double amount=0.0;
			double metalTemp = 0.0;
			
			if(!conversionRate.getText().equals(ConstantUtils.BLANK)) {
				rate = Double.parseDouble(conversionRate.getText());
			}
			
			if(!conversionAmount.getText().equalsIgnoreCase(ConstantUtils.BLANK)) {
				amount = Double.parseDouble(conversionAmount.getText());
			}
			
			if(!conversionMetal.getText().equalsIgnoreCase(ConstantUtils.BLANK)) {
				metalTemp = Double.parseDouble(conversionMetal.getText());
			}
			Contact contactObj = (Contact) contact.getSelectedItem();
			
			Conversion conversion = new Conversion();
			conversion.setAmount(amount);
			conversion.setBillType(MasterController.getInstace().getBillType(billType));
			conversion.setMetal(MasterController.getInstace().getMetal(metal));
			conversion.setBillSource(MasterController.getInstace().getBillSourceByCode(billSource));
			conversion.setContact(contactObj);
			conversion.setConversionDate(conversionDate.getDate());
			conversion.setConversionType(conversionTypeStr);
			conversion.setDeletedFlag(ConstantUtils.CONSTANT_N);
			conversion.setGrams(metalTemp);
			conversion.setLicence(Session.licence);
			conversion.setRate(rate);
			
			
			
			
			
		}catch(Exception ex){
    		ex.printStackTrace();
    	}
	}
	
	private void editPayment(Integer recordId,String metalPaidStr,String cashPaidStr) {
		try {
			dialog = new JDialog();
			Window parentWindow = SwingUtilities.getWindowAncestor(panel);
			dialog = new JDialog(parentWindow, ModalityType.APPLICATION_MODAL);
			
			JPanel dialogPanel = new JPanel();
			
			dialogPanel.add(new MyLabel("Cash:"));dialogPanel.add(new MyLabel(cashPaidStr),"wrap");
			dialogPanel.add(new MyLabel("Metal:"));dialogPanel.add(new MyLabel(metalPaidStr),"wrap");
			dialogPanel.add(new MyButton("Update"));
			dialogPanel.setSize(100, 100);
			dialogPanel.setVisible(true);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
}
