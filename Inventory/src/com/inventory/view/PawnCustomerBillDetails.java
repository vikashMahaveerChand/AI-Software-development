package com.inventory.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.Window;
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
import javax.swing.WindowConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXDatePicker;

import com.inventory.common.FilterJtable;
import com.inventory.components.MyButton;
import com.inventory.components.MyLabel;
import com.inventory.components.MyPanel;
import com.inventory.components.MyTextField;
import com.inventory.components.TableRendererForPawnReport;
import com.inventory.controller.AppController;
import com.inventory.controller.MasterController;
import com.inventory.controller.PawnBillController;
import com.inventory.dto.PawnBillDTO;
import com.inventory.model.Contact;
import com.inventory.model.PawnBill;
import com.inventory.model.PawnBillStatus;
import com.inventory.utility.Calculator;
import com.inventory.utility.ConstantUtils;
import com.inventory.utility.DateUtils;
import com.inventory.utility.Session;
import com.inventory.utility.StringUtils;

import net.miginfocom.swing.MigLayout;

public class PawnCustomerBillDetails implements DocumentListener, ActionListener, KeyListener, MouseListener  {
	
	private static PawnCustomerBillDetails myInstance=null;
	
	JPanel panel;
	JPanel firstPanel,secondPanel,thirdPanel;
	MyTextField searchTextField = null;
	
	JTable reportTable=null;
    DefaultTableModel reportModel =null;
    JScrollPane scrollPane;
    
    private JPopupMenu popupMenu;
    //private JMenuItem editRecord,deleteRecord,enableDisableRecord;
    private JMenuItem editPawnBillRecord,editRedeemRecord,deletePawnBillRecord,deleteRedeemRecord,editQuickData;
    JDialog dialog = null;
    JXDatePicker fromDate,toDate,paymentDate,pawnBillDate,redeemDate;
    MyButton searchBill;

    String calledFrom;
	Integer customerId;
	String billType;
	String billSource;
	
	List<Contact> contactList = null;
	JComboBox contact;
	JTextField tfContact;
	boolean tfContact_hide_flag = false;
	MyButton save;
	boolean searchButtonClick = false;
	
	JComboBox pawnBillStatusFilter;
    List<PawnBillStatus> pawnBillStatusList = null;
    
    JDialog editDataDialog;
    JPanel editDataPanel;
    MyTextField pawnBillNumber,redeemNumber;
    MyButton saveEditRecord;
    Integer editDataBillId;
    
	
	private PawnCustomerBillDetails(){}
	
	public static PawnCustomerBillDetails getInstance(){
		if(myInstance ==null){
			myInstance = new PawnCustomerBillDetails();
		}
		
		return myInstance;
	}
	
	public JPanel getPanel(String calledFrom, Integer customerId,String billSource){
    	try{
    		this.calledFrom = calledFrom;
    		this.customerId = customerId;
    		this.billSource = billSource;
    		
    		initComponent();
   			constructForm();
    		FilterJtable.setFilterJtable(reportTable, searchTextField);
    		System.out.println("customerId - "+customerId);
    		if(customerId!=null && customerId>0){
    			setContactId();
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
			
			searchTextField = new MyTextField(20);
			fromDate = new JXDatePicker();
			fromDate.setDate(Calendar.getInstance().getTime());
			fromDate.setFormats(new SimpleDateFormat("dd/MM/yyyy"));
			
			toDate = new JXDatePicker();
			toDate.setDate(Calendar.getInstance().getTime());
			toDate.setFormats(new SimpleDateFormat("dd/MM/yyyy"));
			
			paymentDate = new JXDatePicker();
			paymentDate.setDate(Calendar.getInstance().getTime());
			paymentDate.setFormats(new SimpleDateFormat("dd/MM/yyyy"));
			
			searchBill = new MyButton("Search");
			save = new MyButton("save");
			contact = new JComboBox();
			contact.setSize(300, 10);
			searchButtonClick = false;
			
			pawnBillStatusFilter = new JComboBox();
			pawnBillStatusFilter.setSize(300, 10);
			
			pawnBillStatusList = PawnBillController.getInstace().getPawnBillStatusList();
			
			pawnBillStatusFilter.addItem("All Record");
			
			for(PawnBillStatus pawnBillStatus: pawnBillStatusList) {
				pawnBillStatusFilter.addItem(pawnBillStatus);
			}
			

			if(billSource.equalsIgnoreCase(ConstantUtils.PAWN_RETAIL)
					){
				contactList = MasterController.getInstace().getEnabledContactList(ConstantUtils.PAWNCUSTOMER_CONTACT_TYPE,ConstantUtils.CONSTANT_Y);
			}
			
			MyPanel.setBorder("Pawn Customer Details", panel);
			MyPanel.setBackground(firstPanel);
			MyPanel.setBackground(secondPanel);
			MyPanel.setBackground(thirdPanel);
			
			reportTable = new JTable();
            
			dialog = new JDialog();
			Window parentWindow = SwingUtilities.getWindowAncestor(panel);
			dialog = new JDialog(parentWindow, ModalityType.APPLICATION_MODAL);
			
			popupMenu = new JPopupMenu();
            /*editRecord = new JMenuItem();
            deleteRecord = new JMenuItem();
            enableDisableRecord = new JMenuItem();
            */
            
            contact.setEditable(true);
			tfContact = (JTextField) contact.getEditor().getEditorComponent();
			
            
			editPawnBillRecord = new JMenuItem();
			editRedeemRecord = new JMenuItem();
			deletePawnBillRecord = new JMenuItem();
			deleteRedeemRecord = new JMenuItem();
			editQuickData = new JMenuItem();
			
            searchBill.addActionListener(this);
            editPawnBillRecord.addActionListener(this);
            editRedeemRecord.addActionListener(this);
            deletePawnBillRecord.addActionListener(this);
            deleteRedeemRecord.addActionListener(this);
            editQuickData.addActionListener(this);
            
            /*editRecord.addActionListener(this);
            deleteRecord.addActionListener(this);
            enableDisableRecord.addActionListener(this);*/
            searchBill.addActionListener(this);
            contact.addActionListener(this);
			tfContact.addActionListener(this);
			save.addActionListener(this);
			
			
			contact.addKeyListener(this);
			tfContact.addKeyListener(this);
    	}catch(Exception ex){
    		ex.printStackTrace();
    	}
    }
	
	private void constructForm(){
    	try{
    		MyPanel.setBorder("Pawn Customer Details "+billSource, panel);
    		
    		tableModelling(reportTable);
			/*editRecord.setText("Edit Entry");
			deleteRecord.setText("Delete Entry");
			enableDisableRecord.setText("Enable/Disable");
			
			popupMenu.add(editRecord);*/
    		
    		editPawnBillRecord.setText("Edit Pawn Bill");
    		editRedeemRecord.setText("Edit Redeem Entry");
    		deletePawnBillRecord.setText("Delete Pawn Bill");
    		deleteRedeemRecord.setText("Delete Redeem Entry");
    		editQuickData.setText("Edit Data");
    		
    		popupMenu.add(editPawnBillRecord);
			popupMenu.add(editRedeemRecord);
			popupMenu.add(deletePawnBillRecord);
			popupMenu.add(deleteRedeemRecord);
			popupMenu.add(editQuickData);
			
			//popupMenu.add(deleteRecord);
			//popupMenu.add(enableDisableRecord);
			
			reportTable.setComponentPopupMenu(popupMenu);
            
            reportTable.setBackground(Color.WHITE);
            scrollPane = new JScrollPane(reportTable);
            Dimension d = reportTable.getPreferredSize();
            int rows=40;
            scrollPane.setPreferredSize(
            new Dimension(d.width,reportTable.getRowHeight()*rows+1));
            secondPanel.add(scrollPane,"span,pushx,growx,wrap");
            
            //PaymentTableRenderer render =new PaymentTableRenderer();
            TableRendererForPawnReport render =new TableRendererForPawnReport();
            //TableRendererForContact render = new TableRendererForContact();
            reportTable.setDefaultRenderer(Object.class,  render);
            reportTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            
            firstPanel.add(new MyLabel("Customer:"));
            firstPanel.add(contact,"span6,wrap");
            firstPanel.add(new MyLabel("From Date:"));
            firstPanel.add(fromDate);
            firstPanel.add(new MyLabel("To Date:"));
            firstPanel.add(toDate);
            firstPanel.add(new MyLabel("Filter :"));
            firstPanel.add(pawnBillStatusFilter);            
            firstPanel.add(searchBill);
            firstPanel.add(new MyLabel("Search Bill"),"push,al right");
            firstPanel.add(searchTextField,"al right, wrap");
            
            setModel(contact,tfContact,new DefaultComboBoxModel(new Vector<>(contactList)), "");
            
            panel.add(firstPanel,"span,pushx,growx,wrap");
            panel.add(secondPanel,"span,pushx,growx,wrap");
            panel.add(thirdPanel,"span,pushx,growx,wrap");
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
						return String.class;//Bill Id
					case 1:
						return Boolean.class;//Check Box
					case 2:
						return String.class;//Bill NO
					case 3:
						return String.class;//Bill Date
					case 4:
						return String.class;//Name
					case 5:
						return String.class;//Ornament Description
					case 6:
						return String.class;//Weight
					case 7:
						return String.class;//Amount					
					case 8:
						return String.class; //No Of Month
					case 9:
						return String.class;// Interest Per Month
					case 10:
						return String.class;// Amount to Pay
					case 11:
						return String.class;// Compound Interest					
					case 12:
						return String.class;//Status
					case 13:
						return String.class;//Delivery No
					case 14:
						return String.class;//Redeem Date
					case 15:
						return String.class;//Redeemed Amount
					default:
						return String.class;
					}
				}
			};
			jTable.setModel(reportModel);
			reportModel.addColumn("billId");
			reportModel.addColumn("ALL");
			reportModel.addColumn("Bill No");
        	reportModel.addColumn("Bill Date");
        	reportModel.addColumn("Name");
        	reportModel.addColumn("Ornament Description");
        	reportModel.addColumn("Weight");
        	reportModel.addColumn("Amount");        	
        	reportModel.addColumn("# Month");
        	reportModel.addColumn("Int / Month");
        	reportModel.addColumn("R Amount SI ");
        	reportModel.addColumn("R Amount CI");
        	reportModel.addColumn("Status");
        	reportModel.addColumn("Delivery No");
        	reportModel.addColumn("Redeem Date");
        	reportModel.addColumn("Redeemed Amount");
        	

        	jTable.getColumn("billId").setPreferredWidth(0);
        	jTable.getColumn("billId").setMinWidth(0);
        	jTable.getColumn("billId").setMaxWidth(0);
        	
        	jTable.getColumn("ALL").setPreferredWidth(0);
        	jTable.getColumn("ALL").setMinWidth(0);
        	jTable.getColumn("ALL").setMaxWidth(0);
        	
        	jTable.getColumn("Ornament Description").setPreferredWidth(300);
        	jTable.getColumn("Ornament Description").setMinWidth(300);
        	jTable.getColumn("Ornament Description").setMaxWidth(300);
        	
		}catch(Exception ex){
			ex.printStackTrace();
		}
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

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent evt) {
		// TODO Auto-generated method stub
		if(evt.getSource() == tfContact){
			contactKeyPressedEvent(evt);
		}else if(evt.getSource() == saveEditRecord) {
			if(evt.getKeyCode() == KeyEvent.VK_ENTER){
				updateEditData();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent evt) {
		// TODO Auto-generated method stub
		if(evt.getSource()==tfContact){
			contactKeyTypedEvent(evt);
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
		        			/*if(createContactFlag && contactobj.getContactName().toUpperCase().equalsIgnoreCase(text.toUpperCase())){
		        				createContactFlag=false;
		        			}*/
		        			
		        			if(createContactFlag && contactobj.toString().replaceAll("\n", " ").toUpperCase().equalsIgnoreCase(text.toUpperCase())){
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
	
	private void setContactInformation(){
		try{
			Date fromDateTemp = fromDate.getDate();
			Date toDateTemp = toDate.getDate();
			Integer billSourceIdTemp=0;
			Integer contactIdTemp = 0;
			Integer licenceIdTemp = 0;
			fromDateTemp = DateUtils.getDate("01/01/2000");
			
			Contact contactObj = (Contact)contact.getSelectedItem();
			contactIdTemp = contactObj.getContactId();
			billSourceIdTemp = MasterController.getInstace().getBillSourceByCode(billSource).getBillSourceId();
			licenceIdTemp = Session.licence.getLicenceId();
			
			String selectedFilter = pawnBillStatusFilter.getSelectedItem().toString();
    		Integer pawnBillStatusFilterId = null;
    		if(!selectedFilter.equalsIgnoreCase("All Record")) {
    			PawnBillStatus billStatus= (PawnBillStatus) pawnBillStatusFilter.getSelectedItem();
    			System.out.println("Id ->"+billStatus.getPawnBillStatusId());
    			pawnBillStatusFilterId = billStatus.getPawnBillStatusId();
    		}
			
			PawnBillDTO pawnBillDTO = PawnBillController.getInstace().getPawnBillList(fromDateTemp, toDateTemp, licenceIdTemp, pawnBillStatusFilterId, contactObj.getContactId(), billSource,null);
			List<PawnBill> pawnBillList = null;
			if(pawnBillDTO!=null) {
				pawnBillList = pawnBillDTO.getPawnBillList();
			}
			constructThirdPanel(pawnBillDTO);
			formTable(pawnBillList);
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
			
			String selectedFilter = pawnBillStatusFilter.getSelectedItem().toString();
    		Integer pawnBillStatusFilterId = null;
    		if(!selectedFilter.equalsIgnoreCase("All Record")) {
    			PawnBillStatus billStatus= (PawnBillStatus) pawnBillStatusFilter.getSelectedItem();
    			System.out.println("Id ->"+billStatus.getPawnBillStatusId());
    			pawnBillStatusFilterId = billStatus.getPawnBillStatusId();
    		}
			
			List<PawnBill> pawnBillList = null;
			PawnBillDTO pawnBillDTO = PawnBillController.getInstace().getPawnBillList(fromDateTemp, toDateTemp, licenceIdTemp, pawnBillStatusFilterId, contactObj.getContactId(), billSource,null); 
    		if(pawnBillDTO!=null) {
    			pawnBillList = pawnBillDTO.getPawnBillList();
    		}
			formTable(pawnBillList);
			searchButtonClick = true;
			constructThirdPanel(pawnBillDTO);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	
	private void onClickEvent(Object evt){
		try{
			//if(evt ==  editRecord || evt ==  deleteRecord || evt == enableDisableRecord){
			if(evt ==  editPawnBillRecord || evt ==  editRedeemRecord || evt == deletePawnBillRecord 
					|| evt == deleteRedeemRecord || evt == editQuickData
					){
				int selectedRow = reportTable.getSelectedRow();
				Integer recordId=0;
				String recordType="";
				int billId=0;
				Contact contactObj = (Contact)contact.getSelectedItem();
				
				if(selectedRow>-1){
					Integer billIdInCell = (Integer)reportTable.getValueAt(selectedRow, 0);
					billId = billIdInCell;
					String billStatus = (String)reportTable.getValueAt(selectedRow, 6);
					/*if(evt ==  editRecord){
						if(billStatus.equalsIgnoreCase(ConstantUtils.PAWN_BILL_STATUS_PLEDGE)) {
							AppController.getInstance().addRetailPawnBillingFormPage(ConstantUtils.PAWN_RETAIL_CUSTOMER_DETAILS, billId, contactObj.getContactId(), billSource);
						}else if(billStatus.equalsIgnoreCase(ConstantUtils.PAWN_BILL_STATUS_REDEEM)) {
							AppController.getInstance().addRetailPawnDeliveryFormPage(ConstantUtils.PAWN_RETAIL_CUSTOMER_DETAILS, billId, contactObj.getContactId(), ConstantUtils.PAWN_RETAIL_DELIVERY);
						}
					}*/
					
					if(evt ==  editPawnBillRecord){
						AppController.getInstance().addRetailPawnBillingFormPage(ConstantUtils.BILLING_REPORT, billId, 0, billSource);						
					}else if(evt == editRedeemRecord) {
						if(billStatus.equalsIgnoreCase(ConstantUtils.PAWN_BILL_STATUS_REDEEM)) {
							AppController.getInstance().addRetailPawnDeliveryFormPage(ConstantUtils.BILLING_REPORT, billId, 0, ConstantUtils.PAWN_RETAIL_DELIVERY);
						}else {
							JOptionPane.showMessageDialog(null, "No Redeem Data Found");
						}
					}else if(evt == deletePawnBillRecord) {
						
					}else if(evt == deleteRedeemRecord) {
						
					}else if(evt == editQuickData) {
						openQuickDataPopup(billId);
					}
				}
			}else if(evt == searchBill){
				searchButtonClick = true;
				fetchBillDetailOnButtonClick();
			}else if(evt == save){
				//savePayment();
			}else if(evt == saveEditRecord) {
				updateEditData();
			}else if(evt == pawnBillDate) {
				redeemNumber.requestFocusInWindow();
			}else if(evt == redeemDate) {
				saveEditRecord.requestFocusInWindow();
			}
		}catch(Exception ex){
    		ex.printStackTrace();
    	}
	}
	
	private void formTable(List<PawnBill> pawnBillList){
		try{
			int rowCount = reportModel.getRowCount();
            for (int i = 0; i < rowCount; i++) {
            	reportModel.removeRow(0);
            }
            
            if(pawnBillList!=null && pawnBillList.size()>0){
            	for(PawnBill data:pawnBillList){
            		reportModel.addRow(new Object[]{
            				data.getPawnBillId(),
            				false,
            				data.getBillNumber(),
            				StringUtils.getStrDate(data.getBillDate()),
            				data.getContact().getContactName(),
            				data.getOrnamentDescription()!=null?StringUtils.capitalize(data.getOrnamentDescription()):"",
            				data.getTotalNetWeight(),
            				//data.getTotalGrossWeight(),
            				data.getPledgeAmount() !=null ? Calculator.formatThreeDecimal(data.getPledgeAmount()):"",            				
            				data.getNoOfMonths(),
            				data.getRateOfInterestPerMonth() !=null ? Calculator.formatThreeDecimal(data.getRateOfInterestPerMonth()):"",
            				data.getRedeemAmount() !=null ? Calculator.formatThreeDecimal(data.getRedeemAmount()):"",
            				data.getCompoundInterestAmount() != null ? Calculator.formatThreeDecimal(data.getCompoundInterestAmount()):"",
            				data.getPawnBillStatusId().getPawnBillStatus(),
            				data.getPawnBillRedeemDetails()!=null ? data.getPawnBillRedeemDetails().getDeliveryNumber():"",
            				data.getPawnBillRedeemDetails()!=null && data.getPawnBillRedeemDetails().getRedeemDate()!=null? StringUtils.getStrDate(data.getPawnBillRedeemDetails().getRedeemDate()):"",
            				data.getPawnBillRedeemDetails()!=null && data.getPawnBillRedeemDetails().getRedeemAmountReceived()!=null? Calculator.formatThreeDecimal(data.getPawnBillRedeemDetails().getRedeemAmountReceived()):""
            				//data.getBillStatusCode()
            	            						
            		});
            	}
            }
            
            //constructSummaryPanel(paymentDTO);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private void setContactId(){
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
	
	private void openQuickDataPopup(int billId) {
		try {
			PawnBill bill = PawnBillController.getInstace().getPawnBill(billId);
			editDataBillId = billId;
			if(bill!=null) {
				editDataDialog = new JDialog(AppController.getInstance().frame,"Edit Bill Data");
				editDataPanel = new JPanel(new MigLayout());
				MyPanel.setBackground(editDataPanel);
				MyPanel.setBorder("Update Record", editDataPanel);
				
				editDataDialog.setSize(300,300);
				
				final Toolkit toolkit = Toolkit.getDefaultToolkit();
				final Dimension screenSize = toolkit.getScreenSize();
				final int x = (screenSize.width - editDataDialog.getWidth()) / 2;
				final int y = (screenSize.height - editDataDialog.getHeight()) / 2;
				editDataDialog.setLocation(x, y);
				
				
				pawnBillDate = new JXDatePicker();
				pawnBillDate.setDate(Calendar.getInstance().getTime());
				pawnBillDate.setFormats(new SimpleDateFormat("dd/MM/yyyy"));
				
				redeemDate = new JXDatePicker();
				redeemDate.setDate(Calendar.getInstance().getTime());
				redeemDate.setFormats(new SimpleDateFormat("dd/MM/yyyy"));
				
				pawnBillNumber = new MyTextField(10);
				redeemNumber = new MyTextField(10);
				
				saveEditRecord = new MyButton("Update");	
								
				pawnBillNumber.addActionListener(this);
				pawnBillDate.addActionListener(this);
				redeemNumber.addActionListener(this);
				redeemDate.addActionListener(this);
				saveEditRecord.addActionListener(this);
				
				pawnBillNumber.addKeyListener(this);
				pawnBillDate.addKeyListener(this);
				redeemNumber.addKeyListener(this);
				redeemDate.addKeyListener(this);
				saveEditRecord.addKeyListener(this);
				
				editDataPanel.add(new MyLabel("Pawn Bill Number :"));
				editDataPanel.add(pawnBillNumber,"wrap");
				
				editDataPanel.add(new MyLabel("Pawn Bill Date :"));
				editDataPanel.add(pawnBillDate,"wrap");
				if(bill.getPawnBillRedeemDetails()!=null) {
				editDataPanel.add(new MyLabel("Delivery Number :"));
				editDataPanel.add(redeemNumber,"wrap");				
				
				editDataPanel.add(new MyLabel("Delivery Date :"));
				editDataPanel.add(redeemDate,"wrap");
				}
				editDataPanel.add(saveEditRecord,"wrap");
				
				editDataDialog.add(editDataPanel);
				
				
				if(bill.getBillNumber()!=null) {
					pawnBillNumber.setText(bill.getBillNumber());
				}
				
				if(bill.getBillDate()!=null) {
					pawnBillDate.setDate(bill.getBillDate());
				}
				
				if(bill.getPawnBillRedeemDetails()!=null) {
					if(bill.getPawnBillRedeemDetails().getDeliveryNumber()!=null) {
						redeemNumber.setText(bill.getPawnBillRedeemDetails().getDeliveryNumber());
					}
					
					if(bill.getPawnBillRedeemDetails().getRedeemDate()!=null) {
						redeemDate.setDate(bill.getPawnBillRedeemDetails().getRedeemDate());
					}else {
						redeemDate.setDate(null);
					}
				}else {
					redeemDate.setDate(null);
				}
				
				
				editDataDialog.setVisible(true);
				editDataDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);				
			}
			
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
	}
	
	private void updateEditData() {		
		try {
			if(editDataBillId!=null && editDataBillId>0) {
				
				String pawnBillNumberStr = "";
				Date pawnBillDateLocal = null;
				String redeemNumberStr="";
				Date redeemDateLocal = null;
				
				if(!pawnBillNumber.getText().equals("")) {
					pawnBillNumberStr = pawnBillNumber.getText().trim();
				}
				
				if(pawnBillDate.getDate()!=null) {
					pawnBillDateLocal = pawnBillDate.getDate();
				}
				
				if(!redeemNumber.getText().trim().equals("")) {
					redeemNumberStr = redeemNumber.getText().trim();
				}
				
				if(redeemDate.getDate()!=null) {
					redeemDateLocal = redeemDate.getDate();
				}
				
				Integer updateResponse = null;
				if(!pawnBillNumberStr.equalsIgnoreCase("") && pawnBillDateLocal!=null) {
					updateResponse = PawnBillController.getInstace().updatePawnBillDetails(editDataBillId, pawnBillNumberStr, pawnBillDateLocal);
				}
				
				if(!redeemNumberStr.equalsIgnoreCase("") && redeemDateLocal!=null) {
					updateResponse = PawnBillController.getInstace().updatePawnBillRedeemDetails(editDataBillId, redeemNumberStr, redeemDateLocal);
				}
				
				if(updateResponse!=null && updateResponse>0) {
					JOptionPane.showMessageDialog(null, "Updated Sucessfully !");
					editDataDialog.dispose();
					fetchBillDetailOnButtonClick();
				}else {
					JOptionPane.showMessageDialog(null, "Updated Failed");
				}
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
	private void constructThirdPanel(PawnBillDTO pawnBillDTO) {
		try {
			
			thirdPanel.removeAll();
			
	        if(pawnBillDTO!=null) {
	        	thirdPanel.add(new MyLabel("Total no of bill : "));
	        	if(pawnBillDTO.getBillCount()!=null) {
	        		thirdPanel.add(new MyLabel(Calculator.formatThreeDecimal(pawnBillDTO.getBillCount())),"wrap");
	        	}else {
	        		thirdPanel.add(new MyLabel(""),"wrap");	
	        	}
	        	thirdPanel.add(new MyLabel("Total pledged amount : "));
	        	if(pawnBillDTO.getTotalAmount()!=null) {
	        		thirdPanel.add(new MyLabel(Calculator.formatThreeDecimal(pawnBillDTO.getTotalAmount())),"wrap");
	        	}else {
	        		thirdPanel.add(new MyLabel(""),"wrap");	
	        	}
	        	thirdPanel.add(new MyLabel("Total un-redeemed amount < 1 year : "));
	        	if(pawnBillDTO.getPledgeBillTotalAmount()!=null) {
	        		thirdPanel.add(new MyLabel(Calculator.formatThreeDecimal(pawnBillDTO.getPledgeBillTotalAmount())),"wrap");
	        	}else {
	        		thirdPanel.add(new MyLabel(""),"wrap");	
	        	}
	        	thirdPanel.add(new MyLabel("Total un-redeemed amount > 1 year : "));
	        	if(pawnBillDTO.getOldPledgeBillTotalAmount()!=null) {
	        		thirdPanel.add(new MyLabel(Calculator.formatThreeDecimal(pawnBillDTO.getOldPledgeBillTotalAmount())),"wrap");
	        	}else {
	        		thirdPanel.add(new MyLabel(""),"wrap");	
	        	}
	        	thirdPanel.add(new MyLabel("Total redeemed amount : "));
	        	if(pawnBillDTO.getRedemeedBillTotalAmount()!=null) {
	        		thirdPanel.add(new MyLabel(Calculator.formatThreeDecimal(pawnBillDTO.getRedemeedBillTotalAmount())),"wrap");
	        	}else {
	        		thirdPanel.add(new MyLabel(""),"wrap");	
	        	}
	        	
	        }
	        
	        thirdPanel.revalidate();
	        thirdPanel.repaint();
			
		}catch(Exception ex) {
			
		}
	}

}
