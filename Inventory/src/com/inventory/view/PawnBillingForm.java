package com.inventory.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
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

import org.jdesktop.swingx.JXDatePicker;

import com.inventory.components.BillingFormTableRenderer;
import com.inventory.components.MyButton;
import com.inventory.components.MyLabel;
import com.inventory.components.MyPanel;
import com.inventory.components.MyTextField;
import com.inventory.controller.AppController;
import com.inventory.controller.BillController;
import com.inventory.controller.MasterController;
import com.inventory.controller.PawnBillController;
import com.inventory.dto.PawnBillDTO;
import com.inventory.model.BillNumber;
import com.inventory.model.Contact;
import com.inventory.model.Metal;
import com.inventory.model.Ornament;
import com.inventory.model.PawnBill;
import com.inventory.model.PawnBillDetails;
import com.inventory.reports.RunReport;
import com.inventory.utility.Calculator;
import com.inventory.utility.ConstantUtils;
import com.inventory.utility.DateUtils;
import com.inventory.utility.Session;
import com.inventory.utility.StringUtils;

import net.miginfocom.swing.MigLayout;

public class PawnBillingForm implements DocumentListener, ActionListener, KeyListener, MouseListener  {
	
	private static PawnBillingForm myInstance = null;
	
	private PawnBillingForm() {
	}

	public static PawnBillingForm getInstance() {
		if (myInstance == null) {
			myInstance = new PawnBillingForm();
		}

		return myInstance;
	}

	JPanel panel;
	String calledFrom;
	Integer billId;
	Integer customerId;
	String billSource;
	
	List<Contact> contactList = null;
	List<Ornament> ornamentList = null;
	List<Metal> metalList = null;
	Integer billSno=0;
	Integer loadedSno=0;
	String billEntryMode="";
	JComboBox contact,ornament,metal;
	MyTextField noOfPiece,grossWeight,netWeight,narration,pawnBilldetailId;
	MyTextField pledgeAmount,rateOfInterest,billCharges,payAmount,rateOfInterestPerMonth,totalGrossWeight,totalNetWeight;
	MyLabel amountInWords;
	MyButton saveBill,viewContactPhoto,addContact;
	
	JTextField tfContact,tfOrnament,tfMetal;
	boolean tfContact_hide_flag = false,tfOrnament_hide_flag = false,tfMetal_hide_flag = false;
	MyTextField billNo;
	
	JPanel contactPanel,contactInfoPanel,contactDetailPanel;
	JPanel billingPanel,billingOrnamentPanel,billingOrnamentTablePanel;
	JPanel billingSummaryPanel,billingSummaryInfoPanel,billingSummaryButtonPanel;
	JXDatePicker billingDate;

	JTable reportTable=null;
    DefaultTableModel reportModel =null;
    JScrollPane scrollPane;
    
    private JPopupMenu popupMenu;
    private JMenuItem editRecord,deleteRecord;
    BillNumber billNumberObj = null;
    
    PawnBill pawnBillObj = null;
    List<PawnBillDetails> pawnBillDetailList = null;
    
    public JPanel getPanel(String calledFrom,Integer billId,Integer customerId,String billSource){
    	this.calledFrom = calledFrom;
		this.billId = billId;
		this.customerId = customerId;
		this.billSource = billSource;
		
		initComponent();
		constructBillForm();
		
		if(billId!=null && billId>0){
			loadBillObj();
		}else {
			if(Session.lastPawnBillDate!=null) {
				billingDate.setDate(Session.lastPawnBillDate);
			}
		}
		
		
		if(customerId!=null && customerId>0) {
			setContact(customerId);
			billingDate.transferFocus();
		}
		
		return panel;
    }

    private void initComponent(){
    	try {
    		billSno=0;
			loadedSno=0;
			billEntryMode = ConstantUtils.CREATE;
			panel = new JPanel(new MigLayout());
			
			pawnBillObj = new PawnBill();
			pawnBillDetailList = new ArrayList<PawnBillDetails>();
			
			contactPanel = new JPanel(new MigLayout());
			contactInfoPanel = new JPanel(new MigLayout());
			contactDetailPanel = new JPanel(new MigLayout());
			billingPanel = new JPanel(new MigLayout());
			billingOrnamentPanel = new JPanel(new MigLayout());
			billingOrnamentTablePanel = new JPanel(new MigLayout());
			billingSummaryPanel = new JPanel(new MigLayout());
			billingSummaryInfoPanel = new JPanel(new MigLayout());
			billingSummaryButtonPanel = new JPanel(new MigLayout());
			
			billNo = new MyTextField(10);
			
			contact = new JComboBox();
			contact.setSize(300, 10);
			
			ornament = new JComboBox();
			ornament.setSize(300, 10);
			
			metal = new JComboBox();
			metal.setSize(300, 10);
			
			reportTable = new JTable();
                
            
            popupMenu = new JPopupMenu();
            editRecord = new JMenuItem();
            deleteRecord = new JMenuItem();
            
            noOfPiece = new MyTextField(10);
            grossWeight = new MyTextField(10);
            netWeight = new MyTextField(10);
            narration = new MyTextField(10);
            pawnBilldetailId = new MyTextField(10);
            
            saveBill = new MyButton("Save");
            viewContactPhoto = new MyButton("View Photo");
            addContact = new MyButton("Add(+)");
            
            pledgeAmount = new MyTextField(10);
            rateOfInterest = new MyTextField(10);
            billCharges = new MyTextField(10);
            payAmount = new MyTextField(10);
            amountInWords= new MyLabel("");
            rateOfInterestPerMonth = new MyTextField(10);
            totalGrossWeight = new MyTextField(10);
            totalNetWeight = new MyTextField(10);
            //MyTextField pledgeAmount,rateOfInterest,billCharges,payAmount;
            
			String sDate1="31/12/1998";  
			Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
		    
			billingDate = new JXDatePicker();
			billingDate.setDate(Calendar.getInstance().getTime());
			//datePicker.setDate(date1);
			billingDate.setFormats(new SimpleDateFormat("dd/MM/yyyy"));
			
			if(billSource.equalsIgnoreCase(ConstantUtils.PAWN_RETAIL)
					){
				contactList = MasterController.getInstace().getEnabledContactList(ConstantUtils.PAWNCUSTOMER_CONTACT_TYPE,ConstantUtils.CONSTANT_Y);
			}
			
			metalList = MasterController.getInstace().getMetalList();
			ornamentList = MasterController.getInstace().getOrnamentList(Session.licence.getLicenceId());
			
			billNumberObj = BillController.getInstace().getBillNumber(Session.licence.getLicenceId(), MasterController.getInstace().getBillSourceByCode(billSource).getBillSourceId());
			
			contact.setEditable(true);
			tfContact = (JTextField) contact.getEditor().getEditorComponent();
			
			metal.setEditable(true);
			tfMetal = (JTextField) metal.getEditor().getEditorComponent();
			
			ornament.setEditable(true);
			tfOrnament = (JTextField) ornament.getEditor().getEditorComponent();
			
			
			billNo.addActionListener(this);
			contact.addActionListener(this);
			tfContact.addActionListener(this);
			tfOrnament.addActionListener(this);
			tfMetal.addActionListener(this);
			billingDate.addActionListener(this);
			noOfPiece.addActionListener(this);
			grossWeight.addActionListener(this);
			netWeight.addActionListener(this);
			narration.addActionListener(this);
			saveBill.addActionListener(this);
			viewContactPhoto.addActionListener(this);
			pledgeAmount.addActionListener(this);
			rateOfInterest.addActionListener(this);
			billCharges.addActionListener(this);
			payAmount.addActionListener(this);
			editRecord.addActionListener(this);
			deleteRecord.addActionListener(this);
			totalGrossWeight.addActionListener(this);
			totalNetWeight.addActionListener(this);
			addContact.addActionListener(this);
			
			billNo.addKeyListener(this);
			contact.addKeyListener(this);
			tfContact.addKeyListener(this);
			tfOrnament.addKeyListener(this);
			tfMetal.addKeyListener(this);
			noOfPiece.addKeyListener(this);
			grossWeight.addKeyListener(this);
			netWeight.addKeyListener(this);
			narration.addKeyListener(this);
			saveBill.addKeyListener(this);
			viewContactPhoto.addKeyListener(this);
			pledgeAmount.addKeyListener(this);
			rateOfInterest.addKeyListener(this);
			billCharges.addKeyListener(this);
			payAmount.addKeyListener(this);
			editRecord.addKeyListener(this);
			deleteRecord.addKeyListener(this);
			totalGrossWeight.addKeyListener(this);
			totalNetWeight.addKeyListener(this);
			addContact.addKeyListener(this);
			//editRecord,deleteRecord
    	}catch(Exception ex) {
    		ex.printStackTrace();
    	}
    }
    
    public void constructBillForm(){
    	try {
    		if(billSource.equalsIgnoreCase(ConstantUtils.PAWN_RETAIL)){
				//gstGoldWholesaleBillForm();
				MyPanel.setBorder("Retail Pawn Bill", panel);
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
			
			setModel(contact,tfContact,new DefaultComboBoxModel(new Vector<>(contactList)), "");
			setModel(ornament,tfOrnament,new DefaultComboBoxModel(new Vector<>(ornamentList)), "");
			setModel(metal,tfMetal,new DefaultComboBoxModel(new Vector<>(metalList)), "");
			
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
			
			/*contactPanel.setBackground(Color.green);
			contactInfoPanel.setBackground(Color.blue);
			contactDetailPanel.setBackground(Color.red);*/
			//"north,pushx,growx,wrap"
			
			contactInfoPanel.add(new MyLabel("Customer Name : "));
			//contactInfoPanel.add(contact, "wrap,span 10");
			contactInfoPanel.add(contact, "span 10");
			
			contactInfoPanel.add(addContact,"wrap,span 3");
			
			contactInfoPanel.add(new MyLabel("Bill Number : "));
			contactInfoPanel.add(billNo);
			
			contactInfoPanel.add(new MyLabel("Billing Date:"));
			contactInfoPanel.add(billingDate,"wrap");
			
			if(billId==null || billId==0){
				billNo.setText(StringUtils.getBillNumber(billNumberObj.getBillLetter(),billNumberObj.getBillNumber()));
			}
			
			//contactPanel.add(contactInfoPanel,"w "+(getScreenWidth(3)/2)+"!");
			//contactPanel.add(contactDetailPanel,"w "+(getScreenWidth(3)/2)+"!");
			//contactPanel.add(contactInfoPanel,"w "+(getScreenWidth(48))+"!");
			//contactPanel.add(contactDetailPanel,"w "+(getScreenWidth(48))+"!");
			
			contactPanel.add(contactInfoPanel,"west,pushx,growx,wrap");
			contactPanel.add(contactDetailPanel,"center,pushx,growx,wrap");
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

			
			//constructOrnamentPanel();
			
			
			
			billingPanel.add(constructOrnamentPanel(),"west,pushx,pushy,wrap");
			
			billingPanel.add(getOrnamentTable(),"center,pushx,pushy,growx,growy,wrap");
			billingPanel.add(getBillSummaryPanel(),"dock south,pushx,growx,pushy,growy,wrap");
			//ornament ends
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return billingPanel;
    }
    
    private JPanel constructOrnamentPanel(){
    	try{
    		billingOrnamentPanel.removeAll();
    		
    		billingOrnamentPanel.add(new MyLabel("Metal :"));billingOrnamentPanel.add(metal,"wrap");
    		billingOrnamentPanel.add(new MyLabel("Ornament :"));billingOrnamentPanel.add(ornament,"wrap");
    		billingOrnamentPanel.add(new MyLabel("No of piece :"));billingOrnamentPanel.add(noOfPiece,"wrap");
    		//billingOrnamentPanel.add(new MyLabel("Gross Weight :"));billingOrnamentPanel.add(grossWeight,"wrap"); //Only for AmarJi
    		billingOrnamentPanel.add(new MyLabel("Net Weight :"));billingOrnamentPanel.add(netWeight,"wrap");
    		billingOrnamentPanel.add(new MyLabel("Narration :"));billingOrnamentPanel.add(narration,"wrap");
    		
    		billingOrnamentPanel.revalidate();
			billingOrnamentPanel.repaint();
    		
    	}catch(Exception ex){
			ex.printStackTrace();
		}
    	
    	return billingOrnamentPanel;
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
    
    private void tableModelling(JTable jTable){
		try{
			
			reportModel = new DefaultTableModel() {

				public Class<?> getColumnClass(int column) {
					switch (column) {
					
					case 0:
						return String.class;//Record Id -- 
					case 1:
						return String.class;//ornament Id --
					case 2:
						return String.class;//SNO		
						
						
					case 3:
						return String.class;//Display #
					case 4:
						return String.class;//Ornament Name
					case 5:
						return String.class;//No Of Piece
					case 6:
						return String.class;//Gross Weight
					case 7:
						return String.class;//Weight Less
					case 8:
						return String.class;//Narration
					default:
						return String.class;
					}
				}
			};
			jTable.setModel(reportModel);
			reportModel.addColumn("recordId");
			reportModel.addColumn("ornamentId");
			reportModel.addColumn("sno");
						
			
			reportModel.addColumn("#");
        	reportModel.addColumn("Ornament Name");
        	reportModel.addColumn("# piece");
        	reportModel.addColumn("Gross Weight");
        	reportModel.addColumn("Weight Less");
        	reportModel.addColumn("Narration");
        	
        	jTable.getColumn("recordId").setPreferredWidth(0);
        	jTable.getColumn("recordId").setMinWidth(0);
        	jTable.getColumn("recordId").setMaxWidth(0);
        	
        	jTable.getColumn("ornamentId").setPreferredWidth(0);
        	jTable.getColumn("ornamentId").setMinWidth(0);
        	jTable.getColumn("ornamentId").setMaxWidth(0);
        	
        	jTable.getColumn("sno").setPreferredWidth(0);
        	jTable.getColumn("sno").setMinWidth(0);
        	jTable.getColumn("sno").setMaxWidth(0);
        	/**/
        	
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
		}else if(evt.getSource() == tfMetal){
			metalKeyPressedEvent(evt);
		}else if(evt.getSource() == tfOrnament){
			ornamentKeyPressedEvent(evt);
		}else if(evt.getSource() == narration) {
			if(evt.getKeyCode() == KeyEvent.VK_ENTER){
				onClickEvent(evt.getSource());
			}
		}else if(evt.getSource() == pledgeAmount 
				|| evt.getSource() == rateOfInterest
				|| evt.getSource() == billCharges
				) {
			calculateAmountPayable();
		}else if(evt.getSource() == saveBill) {
			if(evt.getKeyCode() == KeyEvent.VK_ENTER){
				savePawnBill();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent evt) {
		// TODO Auto-generated method stub
		if(evt.getSource() == pledgeAmount 
				|| evt.getSource() == rateOfInterest
				|| evt.getSource() == billCharges
				) {
			calculateAmountPayable();
		}
	}

	@Override
	public void keyTyped(KeyEvent evt) {
		// TODO Auto-generated method stub
		if(evt.getSource()==tfContact){
			contactKeyTypedEvent(evt);
		}else if(evt.getSource()==tfMetal){
			metalKeyTypedEvent(evt);
		}else if(evt.getSource()==tfOrnament){
			ornamentKeyTypedEvent(evt);
		}else if(evt.getSource() == pledgeAmount 
				|| evt.getSource() == rateOfInterest
				|| evt.getSource() == billCharges
				) {
			calculateAmountPayable();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		//onClickEvent(e.getSource());
		try {
			if(e.getSource() == saveBill) {
				savePawnBill();
			}else if(e.getSource() == editRecord){
				editOrnament();
			}else if(e.getSource() == deleteRecord){
				deleteOrnament();
			}else if(e.getSource() == billingDate) {
				metal.transferFocus();
			}else if(e.getSource() == viewContactPhoto) {
				viewContactImage();
			}else if(e.getSource() == addContact) {
				addContactWindow();
			}
		}catch(Exception ex) {
			
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
	
	private static DefaultComboBoxModel getContactSuggestedModel(List<Contact> list, String text) {
        DefaultComboBoxModel m = new DefaultComboBoxModel();
        for(Contact s: list) {
            if(s.getContactName().toUpperCase().startsWith(text.toUpperCase())) m.addElement(s);
        }
        return m;
	}
	
	private static DefaultComboBoxModel getMetalSuggestedModel(List<Metal> list, String text) {
        DefaultComboBoxModel m = new DefaultComboBoxModel();
        for(Metal s: list) {
            if(s.getMetalCode().toUpperCase().startsWith(text.toUpperCase())) m.addElement(s);
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
	
	private static DefaultComboBoxModel getOrnamentSuggestedModelWIthMetalCode(List<Ornament> list, String text,String metalCode) {
        DefaultComboBoxModel m = new DefaultComboBoxModel();
        for(Ornament s: list) {
            if(s.getMetalId().getMetalCode().toUpperCase().startsWith(metalCode.toUpperCase())) m.addElement(s);
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
	
	
	private void metalKeyTypedEvent(KeyEvent evt){
		 EventQueue.invokeLater(new Runnable() {
			 public void run() {
				 String text = tfMetal.getText();
				 if(text.length()==0) {
					 metal.hidePopup();
					 setModel(metal,tfMetal,new DefaultComboBoxModel(new Vector<>(metalList)), "");
				 }else{
					 DefaultComboBoxModel m = getMetalSuggestedModel(metalList, text);
					 if(m.getSize()==0 || tfMetal_hide_flag) {
                      metal.hidePopup();
                      tfMetal_hide_flag = false;
					 }else{
                    setModel(metal,tfMetal,m, text);
                    metal.showPopup();
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
			        	/*if(createStateFlag) {
			        		if(!text.equalsIgnoreCase(ConstantUtils.BLANK)){
			        			int selected = JOptionPane.showConfirmDialog(null, "Do you want to add New state?");
			        			if(selected==JOptionPane.YES_OPTION){
			        			State stateObj = createState(text.trim());
			        			if(stateObj!=null){
			        				stateList.add(stateObj);
			        				setModel(state,tfState,getStateSuggestedModel(stateList, text), text);
			        			}
			        			}
			        		}
			        	}*/
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
	
	private void metalKeyPressedEvent(KeyEvent evt){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				String text = tfMetal.getText();
		        int code = evt.getKeyCode();
		        if(code==KeyEvent.VK_ENTER) {
		        	if(!text.equalsIgnoreCase(ConstantUtils.BLANK)){
		        		boolean createMetalFlag=true;
		        		for(Metal metalobj:metalList){
		        			if(createMetalFlag && metalobj.getMetalCode().toUpperCase().equalsIgnoreCase(text.toUpperCase())){
		        				createMetalFlag=false;		        				
		        			}
		        			/*if(metalobj.getMetalCode().toUpperCase().startsWith(text.toUpperCase())) {
		        				metal.setSelectedIndex(-1);
			                    tfMetal.setText(metalobj.getMetalCode());
		        			}*/
		        		}
			        	if(createMetalFlag) {
			        		if(!text.equalsIgnoreCase(ConstantUtils.BLANK)){
			        			int selected = JOptionPane.showConfirmDialog(null, "Do you want to add New Metal?");
			        			if(selected==JOptionPane.YES_OPTION){
			        			Metal metalObj = createMetal(text.trim());
			        			if(metalObj!=null){
			        				metalList.add(metalObj);
			        				setModel(metal,tfMetal,getMetalSuggestedModel(metalList, StringUtils.capitalize(text)), StringUtils.capitalize(text));
			        			}
			        			}
			        		}
			        	}			        	
		        	}
		        	setOrnamentList();
		        	tfMetal_hide_flag=true;
		        	((Component) evt.getSource()).transferFocus();
		        }else if(code==KeyEvent.VK_ESCAPE) {
		        	tfMetal_hide_flag=true;
		        	setOrnamentList();
		        }else if(code==KeyEvent.VK_RIGHT) {
		        	for(Metal obj:metalList) {
		                String str = obj.getMetalCode();
		                if(str.toUpperCase().startsWith(text.toUpperCase())) {
		                	setOrnamentList();
		                    metal.setSelectedIndex(-1);
		                    tfMetal.setText(str);
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
			        	if(createOrnamentFlag) {
			        		if(!text.equalsIgnoreCase(ConstantUtils.BLANK)){
			        			int selected = JOptionPane.showConfirmDialog(null, "Do you want to add New Ornament?");
			        			if(selected==JOptionPane.YES_OPTION){
			        			Ornament ornamnetObj = createOrnament(text.trim());
			        			if(ornamnetObj!=null){
			        				ornamentList.add(ornamnetObj);
			        				setModel(ornament,tfOrnament,getOrnamentSuggestedModel(ornamentList, StringUtils.capitalize(text)), StringUtils.capitalize(text));
			        				//setModel(ornament,tfOrnament,new DefaultComboBoxModel(new Vector<>(ornamentList)), "");
			        			}
			        			}
			        		}
			        	}
		        	}
		        	tfOrnament_hide_flag=true;
		        	((Component) evt.getSource()).transferFocus();
		        }else if(code==KeyEvent.VK_ESCAPE) {
		        	tfOrnament_hide_flag=true;		        	
		        }else if(code==KeyEvent.VK_RIGHT) {
		        	for(Ornament obj:ornamentList) {
		                String str = obj.getOrnamentName();
		                if(str.toUpperCase().startsWith(text.toUpperCase())) {
		                    ornament.setSelectedIndex(-1);
		                    tfOrnament.setText(str);
		                    return;
		                }
		            }
		        }
			}
		});
	}
	
	private void setContactInformation(){
		try{
			//System.out.println(contact.getSelectedItem().toString());
			if(contact.getSelectedIndex()!=-1){
				Contact selectedContact = (Contact)contact.getSelectedItem();
				
				if(selectedContact!=null){
					formContactDetailPanel(selectedContact);
					enableDisableComponents(billingOrnamentPanel,true);
					billingDate.requestFocus();
				}
			}else{
				formContactDetailPanel(null);
				enableDisableComponents(billingOrnamentPanel,false);
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
			billingDate.transferFocus();
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private void setOrnamentList() {
		try {
			String metalCode = tfMetal.getText();
			DefaultComboBoxModel m = getOrnamentSuggestedModelWIthMetalCode(ornamentList, "",metalCode);
			 if(m.getSize()==0 || tfOrnament_hide_flag) {
				 ornament.hidePopup();
				 tfOrnament_hide_flag = false;
			 }else{
				 setModel(ornament,tfOrnament,m, "");
				 //ornament.showPopup();
			 }
			 if(metalCode.equals("")) {
				 pledgeAmount.requestFocus();
				 //totalGrossWeight.requestFocus();
			 }
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private void formContactDetailPanel(Contact contact){
		try{
			JPanel leftContactDetailPanel,rightContactDetailPanel;
			
			leftContactDetailPanel = new JPanel(new MigLayout());
			rightContactDetailPanel = new JPanel(new MigLayout());
			
			MyPanel.setBackground(leftContactDetailPanel, Color.white);
			MyPanel.setBackground(rightContactDetailPanel, Color.white);
			
			contactDetailPanel.removeAll();
			leftContactDetailPanel.removeAll();
			rightContactDetailPanel.removeAll();
			
			if(contact!=null){
				
				PawnBillDTO pawnBillDTO = PawnBillController.getInstace().getPawnBillList(DateUtils.getDate("01/01/2000"), new Date(), Session.licence.getLicenceId(), null, contact.getContactId(), billSource,null);
				
				
				leftContactDetailPanel.add(new MyLabel(contact.getContactName()+" "+contact.getSecondName()),"wrap");
				
				leftContactDetailPanel.add(new MyLabel("Last Bill Date:"));
				if(pawnBillDTO.getLastBillDate()!=null) {
					leftContactDetailPanel.add(new MyLabel(StringUtils.getStrDate(pawnBillDTO.getLastBillDate())),"wrap");
				}else {
					leftContactDetailPanel.add(new MyLabel(""),"wrap");
				}
				
				
				leftContactDetailPanel.add(new MyLabel("Total # of bills:"));
				if(pawnBillDTO.getBillCount()!=null) {
					leftContactDetailPanel.add(new MyLabel(Calculator.formatThreeDecimal(pawnBillDTO.getBillCount())),"wrap");
				}else {
					leftContactDetailPanel.add(new MyLabel(""),"wrap");
				}
				
				leftContactDetailPanel.add(viewContactPhoto);
				
				rightContactDetailPanel.add(new MyLabel("No of Current Bills:"));
				if(pawnBillDTO.getPledgeBillCount()!=null) {
					rightContactDetailPanel.add(new MyLabel(Calculator.formatThreeDecimal(pawnBillDTO.getPledgeBillCount())),"wrap");
				}else {
					rightContactDetailPanel.add(new MyLabel(""),"wrap");
				}
				
				rightContactDetailPanel.add(new MyLabel("No of Redeemed Bills:"));
				if(pawnBillDTO.getRedeemedBillCount()!=null) {
					rightContactDetailPanel.add(new MyLabel(Calculator.formatThreeDecimal(pawnBillDTO.getRedeemedBillCount())),"wrap");
				}else {
					rightContactDetailPanel.add(new MyLabel(""),"wrap");
				}
				rightContactDetailPanel.add(new MyLabel("No of Bill more than 1 year:"));
				if(pawnBillDTO.getOldPledgeBillCount()!=null) {
					rightContactDetailPanel.add(new MyLabel(Calculator.formatThreeDecimal(pawnBillDTO.getOldPledgeBillCount())),"wrap");
				}else {
					rightContactDetailPanel.add(new MyLabel(""),"wrap");
				}
			}
			
			leftContactDetailPanel.revalidate();
			leftContactDetailPanel.repaint();
			
			rightContactDetailPanel.revalidate();
			rightContactDetailPanel.repaint();
			
			contactDetailPanel.add(leftContactDetailPanel);
			contactDetailPanel.add(rightContactDetailPanel);
			
			contactDetailPanel.revalidate();
			contactDetailPanel.repaint();
		}catch(Exception ex){
			ex.printStackTrace();
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
	
	private void onClickEvent(Object source) {
		try {
			if(source == narration) {
				addToOrnamentList();
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private void addToOrnamentList() {
		try {
			boolean validationFlag = validateOrnamentEntry();
			
			if(validationFlag) {
				Ornament ornamentSelect = getSelectedOrnament();
				if(ornamentSelect!=null) {
					System.out.println("ornamentSelect - "+ornamentSelect.getOrnamentId() + " - "+ornamentSelect.getOrnamentName());
					PawnBillDetails obj = new PawnBillDetails();
					
					obj.setDeletedFlag(ConstantUtils.CONSTANT_N);
					
					if(!grossWeight.getText().equalsIgnoreCase(ConstantUtils.BLANK)) {
						obj.setGrossWeight(Double.parseDouble(grossWeight.getText()));
					}
					
					obj.setNarration(narration.getText());
					if(!netWeight.getText().equalsIgnoreCase(ConstantUtils.BLANK)) {
						obj.setNetWeight(Double.parseDouble(netWeight.getText()));
					}
					
					if(!noOfPiece.getText().equalsIgnoreCase(ConstantUtils.BLANK)) {
						obj.setNoOfPiece(Integer.parseInt(noOfPiece.getText()));
					}
					
					if(ornament.getSelectedIndex()!=-1) {
						obj.setOrnament((Ornament)ornament.getSelectedItem());
					}
					
					if(!pawnBilldetailId.getText().equalsIgnoreCase(ConstantUtils.BLANK)){
						obj.setPawnBillDetailsId(Integer.parseInt(pawnBilldetailId.getText()));
					}
					if(billEntryMode.equalsIgnoreCase(ConstantUtils.CREATE)){
						obj.setsNo(++billSno);
					}else if(billEntryMode.equalsIgnoreCase(ConstantUtils.UPDATE)){
						obj.setsNo(loadedSno);
					}
					if(billEntryMode.equalsIgnoreCase(ConstantUtils.UPDATE)){
						pawnBillDetailList.remove(loadedSno-1);
					}
					//noOfPiece,grossWeight,netWeight,narration,pawnBilldetailId;
					pawnBillDetailList.add(obj);
					addOrnamentToTable();
					clearOrnamentFields();
					metal.transferFocus();
				}
				
				
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private boolean validateOrnamentEntry() {
		boolean flag = true;
		StringBuffer errorMessage=new StringBuffer();
		//noOfPiece,grossWeight,netWeight,narration
		try {
			if(tfMetal.getText().equals(ConstantUtils.BLANK)) {
				errorMessage.append("Metal,");
			}
			
			if(tfOrnament.getText().equals(ConstantUtils.BLANK)) {
				errorMessage.append("Ornament,");
			}
			
			/*if(grossWeight.getText().equals(ConstantUtils.BLANK) && netWeight.getText().equals(ConstantUtils.BLANK)) {
				errorMessage.append("Weight,");
			}*/
			
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
	
	private Ornament getSelectedOrnament() {
		Ornament ornamentObj = null;
		Metal metalObj=null;
		boolean createOrnamentFlag=false;
		try {
			if(metal.getSelectedIndex()==-1) {
				//Metal Invalid
				JOptionPane.showMessageDialog(null, "Please select a valid Metal From the list");
			}else if(metal.getSelectedIndex()!=-1 && ornament.getSelectedIndex()==-1) {
				//create Ornament
				ornamentObj = createOrnament();
				createOrnamentFlag = true;
			}else if(ornament.getSelectedIndex()!=-1) {
				ornamentObj = (Ornament)ornament.getSelectedItem();
				metalObj = (Metal) metal.getSelectedItem();
				
				if(!ornamentObj.getMetalId().getMetalCode().toUpperCase().equalsIgnoreCase(metalObj.getMetalCode().toUpperCase())) {
					//createOrnament;
					ornamentObj = createOrnament();
					createOrnamentFlag = true;
				}
			}
			
			if(createOrnamentFlag) {
				//setModel(ornament,tfOrnament,new DefaultComboBoxModel(new Vector<>(MasterController.getInstace().getOrnamentList(Session.licence.getLicenceId()))),"");
				if(ornamentObj!=null){
					ornamentList.add(ornamentObj);
					
					ornamentList.sort(Comparator.comparing(Ornament::getOrnamentName));
					
    				//setModel(ornament,tfOrnament,getOrnamentSuggestedModel(ornamentList, ornamentObj.getOrnamentName()), ornamentObj.getOrnamentName());
					setModel(ornament,tfOrnament,new DefaultComboBoxModel(new Vector<>(ornamentList)), "");
    			}
				
				int tempCounter=-1;
				int tempSetId=-1;
				List<Ornament> ornamentListTemp = MasterController.getInstace().getOrnamentList(Session.licence.getLicenceId());
				for(Ornament obj:ornamentListTemp){
					tempCounter++;
					if(obj.getOrnamentId().equals(ornamentObj.getOrnamentId())){
						tempSetId = tempCounter;
					}
				}
				
				ornament.setSelectedIndex(tempSetId);
				
			}
			
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return ornamentObj;
	}
	
	private Ornament createOrnament() {
		Ornament ornamentObj = null;
		Metal metalObj=null;
		try {
			metalObj = (Metal) metal.getSelectedItem();
			ornamentObj = new Ornament();
			ornamentObj.setDeletedFlag(ConstantUtils.CONSTANT_N);
			ornamentObj.setLicenceId(Session.licence.getLicenceId());
			ornamentObj.setMetalId(metalObj);
			ornamentObj.setOrnamentName(tfOrnament.getText().trim());

			Integer ornId = MasterController.getInstace().saveOrUpdateOrnament(ornamentObj);
			if(ornId!=null && ornId>0) {
				ornamentObj = MasterController.getInstace().getOrnament(ornId);
			}else {
				ornamentObj = null;
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return ornamentObj;
	}
	private void addOrnamentToTable(){
		try {
			int rowCount = reportModel.getRowCount();
            for (int i = 0; i < rowCount; i++) {
            	reportModel.removeRow(0);
            }
            
            int counter=0;
            pawnBillDetailList.sort(Comparator.comparing(PawnBillDetails::getsNo));
            
            if(pawnBillDetailList!=null && pawnBillDetailList.size()>0){
            	for(PawnBillDetails data:pawnBillDetailList){
            		
            		if(data.getDeletedFlag().equalsIgnoreCase(ConstantUtils.CONSTANT_N)){
            			reportModel.addRow(new Object[]{
	            				data.getPawnBillDetailsId(),
	            				data.getOrnament().getOrnamentId(),
	            				data.getsNo(),
	            				
	            				++counter,
	            				data.getOrnament().getOrnamentName(),
	            				data.getNoOfPiece(),
	            				data.getGrossWeight(),
	            				data.getNetWeight(),
	            				data.getNarration()
	            		});
            		}
            	}
            	calculateBillSummary();
            }
            
            
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private void clearOrnamentFields(){
		try {
			noOfPiece.setText("");
			grossWeight.setText("");
			netWeight.setText("");
			narration.setText("");
			pawnBilldetailId.setText("");
			
			constructOrnamentPanel();
			billEntryMode = ConstantUtils.CREATE;
			loadedSno=0;
			
			setModel(ornament,tfOrnament,new DefaultComboBoxModel(new Vector<>(MasterController.getInstace().getOrnamentList(Session.licence.getLicenceId()))), "");
			setModel(metal,tfMetal,new DefaultComboBoxModel(new Vector<>(MasterController.getInstace().getMetalList())), "");
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private JPanel getBillSummaryPanel(){
		try {
			MyPanel.setBackground(billingSummaryPanel, Color.WHITE);
			MyPanel.setBackground(billingSummaryInfoPanel, Color.WHITE);
			MyPanel.setBackground(billingSummaryButtonPanel, Color.WHITE);
			constructBillSummaryInfoPanel();
			constructBillSummaryButtonPanel();
			billingSummaryPanel.add(billingSummaryInfoPanel,"north,pushx,pushy,growx,growy,wrap");
			billingSummaryPanel.add(billingSummaryButtonPanel,"center,pushx,pushy,growx,growy,wrap");
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return billingSummaryPanel;
	}
	
	private void calculateBillSummary(){
		try {
			Double totalGrossWeight=0.0,totalNetWeight=0.0;
			Integer noOfPieces=0;
			
			
			for(PawnBillDetails obj : pawnBillDetailList){
				if(obj.getDeletedFlag().equalsIgnoreCase(ConstantUtils.CONSTANT_N)){
					if(obj.getGrossWeight()!=null){
						totalGrossWeight = totalGrossWeight+obj.getGrossWeight();
					}
					
					if(obj.getNetWeight()!=null){
						totalNetWeight = totalNetWeight+obj.getNetWeight();
					}
					
					if(obj.getNoOfPiece()!=null) {
						noOfPieces = noOfPieces + obj.getNoOfPiece();
					}
				}
			}
			
			totalGrossWeight = Calculator.getThreeDigitDecimal(totalGrossWeight);
			totalNetWeight = Calculator.getThreeDigitDecimal(totalNetWeight);
						
			pawnBillObj.setTotalGrossWeight(totalGrossWeight);
			pawnBillObj.setTotalNetWeight(totalNetWeight);
			
			constructBillSummaryInfoPanel();
			
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private void calculateAmountPayable() {
		try {
			Double amountEntered = 0.0;
			Double rateOfInterestEntered = 0.0;
			Double billChargesEntered = 0.0;
			Double amountPayable = 0.0;
			Double rateOfInterestPM=0.0;
			if(!pledgeAmount.getText().equals(ConstantUtils.BLANK)) {
				amountEntered = Double.parseDouble(pledgeAmount.getText());
			}
			
			if(!rateOfInterest.getText().equals(ConstantUtils.BLANK)) {
				rateOfInterestEntered = Double.parseDouble(rateOfInterest.getText());
			}
			
			if(!billCharges.getText().equals(ConstantUtils.BLANK)) {
				billChargesEntered = Double.parseDouble(billCharges.getText());
			}
			
			if(amountEntered>0.0 && rateOfInterestEntered>0.0) {
				rateOfInterestPM = ((amountEntered * rateOfInterestEntered)/100);
				rateOfInterestPM = Calculator.getTwoDigitDecimal(rateOfInterestPM);
				
				amountPayable = amountEntered - rateOfInterestPM;
				amountPayable = Calculator.getTwoDigitDecimal(amountPayable);
				
				amountPayable = amountPayable - billChargesEntered;
				amountPayable = Calculator.getTwoDigitDecimal(amountPayable);
				
				pawnBillObj.setAmountToPay(amountPayable);
				
				payAmount.setText(Calculator.formatTwoDecimal(amountPayable));
				payAmount.setEditable(false);
				
				String amountInWordsText = Calculator.convert(amountPayable.intValue());
				pawnBillObj.setAmountInWords(amountInWordsText);
				
				//amountInWords.setText(Calculator.convert(amountPayable.intValue()));
				amountInWords.setText(amountInWordsText);
				rateOfInterestPerMonth.setText(rateOfInterestPM.toString());
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private void constructBillSummaryInfoPanel(){
		try {
			billingSummaryInfoPanel.removeAll();
			//totalGrossWeight,totalNetWeight
			billingSummaryInfoPanel.add(new MyLabel("Pledge Amount:"));
			billingSummaryInfoPanel.add(pledgeAmount);
			
			//billingSummaryInfoPanel.add(new MyLabel("Total Gross Weight:"));
			//billingSummaryInfoPanel.add(totalGrossWeight);
			if(pawnBillObj.getTotalGrossWeight()!=null && pawnBillObj.getTotalGrossWeight()>0.0){
				totalGrossWeight.setText(Double.toString(pawnBillObj.getTotalGrossWeight()));
			}
			
			billingSummaryInfoPanel.add(new MyLabel("Total Net Weight:"));
			billingSummaryInfoPanel.add(totalNetWeight,"wrap");
			if(pawnBillObj.getTotalNetWeight()!=null && pawnBillObj.getTotalNetWeight()>0.0){
				totalNetWeight.setText(Double.toString(pawnBillObj.getTotalNetWeight()));
			}
			
			
			
			billingSummaryInfoPanel.add(new MyLabel("Rate Of Interest:"));
			billingSummaryInfoPanel.add(rateOfInterest,"wrap");
			
			billingSummaryInfoPanel.add(new MyLabel("Bill Charges:"));
			billingSummaryInfoPanel.add(billCharges);
			
			billingSummaryInfoPanel.add(new MyLabel("Pay Amount:"));
			billingSummaryInfoPanel.add(payAmount,"wrap");
			
			billingSummaryInfoPanel.add(new MyLabel("Amount In Words:"));
			billingSummaryInfoPanel.add(amountInWords,"span6,wrap");
			//MyTextField pledgeAmount,rateOfInterest,billCharges,payAmount;
			
			billingSummaryInfoPanel.revalidate();
			billingSummaryInfoPanel.repaint();
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private void constructBillSummaryButtonPanel(){
		try{
			billingSummaryButtonPanel.add(saveBill);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private void savePawnBill() {
		try{
			if(pawnBillObj!=null) {
				boolean validationFlag = pawnBillSaveValidationCheck();
				if(validationFlag) {
					//pawnBillObj.setAmountInWords(amountInWords.getText());
					
					if(!billCharges.getText().equals(ConstantUtils.BLANK)) {
						pawnBillObj.setBillCharges(Double.parseDouble(billCharges.getText()));
					}
					
					pawnBillObj.setBillNumber(billNo.getText());					
					pawnBillObj.setBillSource(MasterController.getInstace().getBillSourceByCode(billSource));
					pawnBillObj.setContact((Contact)contact.getSelectedItem());
					pawnBillObj.setDeletedFlag(ConstantUtils.CONSTANT_N);
					pawnBillObj.setLicence(Session.licence);
					pawnBillObj.setBillDate(billingDate.getDate());
					pawnBillObj.setAuctionStatus(ConstantUtils.CONSTANT_Y);
					
					if(!totalGrossWeight.getText().trim().equals("")) {
						pawnBillObj.setTotalGrossWeight(Double.parseDouble(totalGrossWeight.getText().trim()));
					}
					if(!totalNetWeight.getText().trim().equals("")) {
						pawnBillObj.setTotalNetWeight(Double.parseDouble(totalNetWeight.getText().trim()));
					}
					
					
					
					if(billId>0) {
						pawnBillObj.setPawnBillId(billId);
					}else {
						pawnBillObj.setPawnBillStatusId(PawnBillController.getInstace().getPawnBillStatusByCode(ConstantUtils.PAWN_BILL_STATUS_PLEDGE));
					}
					
					//pawnBillObj.setPawnBillStatusId(null);//To be updated
					if(!pledgeAmount.getText().equals(ConstantUtils.BLANK)) {
						pawnBillObj.setPledgeAmount(Double.parseDouble(pledgeAmount.getText()));
					}
					if(!rateOfInterest.getText().equals(ConstantUtils.BLANK)) {
						pawnBillObj.setRateOfInterest(Double.parseDouble(rateOfInterest.getText()));
					}
					
					if(!rateOfInterestPerMonth.getText().equals(ConstantUtils.BLANK)) {
						pawnBillObj.setRateOfInterestPerMonth(Double.parseDouble(rateOfInterestPerMonth.getText()));
					}
					
					pawnBillObj.setPawnBillDetails(pawnBillDetailList);
					
					PawnBill pawnBillObjTemp = PawnBillController.getInstace().saveOrUpdateBill(pawnBillObj);
					
					if(pawnBillObjTemp!=null && pawnBillObjTemp.getPawnBillId()>0){
						JOptionPane.showMessageDialog(null, "Bill Saved!!");
						int ReportOption=JOptionPane.showConfirmDialog(null,"Do You Want To Print (Y/N)", "Confirm", JOptionPane.OK_CANCEL_OPTION);
						if(ReportOption==JOptionPane.OK_OPTION){
							RunReport.printPawnBill(PawnBillController.getInstace().getPawnBill(pawnBillObjTemp.getPawnBillId()));
						}
						//PawnBill pawnBillObjTemp = PawnBillController.getInstace().getPawnBill(billId);
						
						
						Session.lastPawnBillDate = billingDate.getDate();
						//String calledFrom,Integer billId,Integer customerId,String billType,String metal,String billSource
						if(calledFrom.equalsIgnoreCase(ConstantUtils.MENU)){
							AppController.getInstance().addRetailPawnBillingFormPage(ConstantUtils.MENU, 0, 0, billSource);
						}else if(calledFrom.equalsIgnoreCase(ConstantUtils.BILLING_REPORT)){
							//AppController.getInstance().addBillingReportPage(ConstantUtils.MENU,billType, metal, billSource);
							AppController.getInstance().addPawnBillingReportPage(ConstantUtils.MENU,billSource);
						}else if(calledFrom.equalsIgnoreCase(ConstantUtils.PAYMENT_FORM)){
							//customerId = selectedContact.getContactId();
							//AppController.getInstance().addPaymentFormPage(ConstantUtils.MENU,customerId,billType,metal,billSource);
						}else if(calledFrom.equalsIgnoreCase(ConstantUtils.PAWN_RETAIL_CUSTOMER_DETAILS)) {
							AppController.getInstance().addRetailPawnCustomerDetails(ConstantUtils.MENU, customerId, ConstantUtils.PAWN_RETAIL);
						}
					}
					
				}
				
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private boolean pawnBillSaveValidationCheck() {
		boolean flag = true;
		StringBuffer errorMessage=new StringBuffer();
		try {
			if(contact.getSelectedIndex()==-1) {
				errorMessage.append("Customer Name is not filled,");
			}
			
			if(billNo.getText().equals(ConstantUtils.BLANK)) {
				errorMessage.append("Bill Number not filled,");
			}
			
			if(billingDate.getDate()==null) {
				errorMessage.append("Date not filled,");
			}
			
			if(pawnBillDetailList.size()==0) {
				errorMessage.append("Enter atleast one Ornament,");
			}
			
			if(pledgeAmount.getText().equals(ConstantUtils.BLANK)) {
				errorMessage.append("Pledge Amount not filled,");				
			}
			if(!pledgeAmount.getText().equals(ConstantUtils.BLANK)) {
				double test = Double.parseDouble(pledgeAmount.getText().trim());
			}
			if(rateOfInterest.getText().equals(ConstantUtils.BLANK)) {
				errorMessage.append("Rate of interest not filled,");
			}
			
			if(!rateOfInterest.getText().equals(ConstantUtils.BLANK)) {
				double test = Double.parseDouble(rateOfInterest.getText().trim());
			}
			
			if(totalGrossWeight.getText().trim().equals("") && totalNetWeight.getText().trim().equals("")) {
				errorMessage.append("Gross Weight or Net Weight to be filled,");
			}			
			
			if(!totalGrossWeight.getText().trim().equals("")) {
				double test = (Double.parseDouble(totalGrossWeight.getText().trim()));
			}
			if(!totalNetWeight.getText().trim().equals("")) {
				double test = (Double.parseDouble(totalNetWeight.getText().trim()));
			}
			
			
			
			
		}catch(Exception ex){
			flag = false;
			errorMessage.append("Data Issue,");
			ex.printStackTrace();
		}
		
		if(!errorMessage.toString().equals(ConstantUtils.BLANK)){
			flag=false;
			String errorMessageDisplay = "Please correct the following Validation: \n"+errorMessage.toString().replaceAll(",", "\n");
			JOptionPane.showMessageDialog(null, errorMessageDisplay);
		}
		return flag;
	}
	
	private void editOrnament(){
		try{
			int selectedRow = reportTable.getSelectedRow();
			Integer selectedSNO=0;
			
			if(selectedRow>-1){
				selectedSNO = (Integer)reportTable.getValueAt(selectedRow, 2);
				
				
				if(selectedSNO>0){
					PawnBillDetails tempObj = null;
					for(PawnBillDetails obj:pawnBillDetailList){
						if(obj.getsNo().equals(selectedSNO)){
							tempObj = obj;
						}
					}
					
					if(tempObj!=null){
						loadOrnamentPanel(tempObj);
					}
				}
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private void loadOrnamentPanel(PawnBillDetails obj){
		try {
			clearOrnamentFields();
			billEntryMode = ConstantUtils.UPDATE;

			if(obj.getNoOfPiece()!=null) {
				noOfPiece.setText(obj.getNoOfPiece().toString());
			}
			
			if(obj.getGrossWeight()!=null) {
				grossWeight.setText(obj.getGrossWeight().toString());
			}
			
			if(obj.getNetWeight()!=null) {
				netWeight.setText(obj.getNetWeight().toString());
			}
			
			if(obj.getNarration()!=null) {
				narration.setText(obj.getNarration());				
			}
			
			if(obj.getsNo()!=null){
				loadedSno = obj.getsNo();
			}
			
			if(obj.getPawnBillDetailsId()!=null) {
				pawnBilldetailId.setText(obj.getPawnBillDetailsId().toString());
			}
			//MyTextField noOfPiece,grossWeight,netWeight,narration,pawnBilldetailId;
			
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
			
			if(obj.getOrnament()!=null){
				int metalIndex=-1;
				int metalCounter=-1;
				for(Metal metalObj:metalList){
					metalCounter++;
					if(obj.getOrnament().getMetalId().getMetalId().equals(metalObj.getMetalId())){
						metalIndex = metalCounter;
					}
				}
				metal.setSelectedIndex(metalIndex);
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private void deleteOrnament(){
		try{
			int selectedRow = reportTable.getSelectedRow();
			Integer selectedSNO=0;
			if(selectedRow>-1){
				selectedSNO = (Integer)reportTable.getValueAt(selectedRow, 2);
				
				if(selectedSNO>0){
					
					
					int selected = JOptionPane.showConfirmDialog(null, "Are you sure You want to delete Ornament?");
					if(selected==JOptionPane.YES_OPTION){
						PawnBillDetails tempObj = null;
						for(PawnBillDetails obj:pawnBillDetailList){
							if(obj.getsNo().equals(selectedSNO)){
								obj.setDeletedFlag(ConstantUtils.CONSTANT_Y);
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
	
	private void loadBillObj(){
		try{
			pawnBillObj = PawnBillController.getInstace().getPawnBill(billId);
			
			if(pawnBillObj!=null){
				billingDate.setDate(pawnBillObj.getBillDate());
				billNo.setText(pawnBillObj.getBillNumber());

				if(pawnBillObj.getContact()!=null){
					int tempCounter=-1;
					int tempSetId=-1;
					
					for(Contact obj:contactList){
						tempCounter++;
						if(obj.getContactId().equals(pawnBillObj.getContact().getContactId())){
							tempSetId = tempCounter;
						}
					}
					
					contact.setSelectedIndex(tempSetId);
					setContactInformation();
					
				}
				
				pledgeAmount.setText(pawnBillObj.getPledgeAmount().toString());
				rateOfInterest.setText(pawnBillObj.getRateOfInterest().toString());
				rateOfInterestPerMonth.setText(pawnBillObj.getRateOfInterestPerMonth().toString());
				if(pawnBillObj.getBillCharges()!=null) {
					billCharges.setText(pawnBillObj.getBillCharges().toString());
				}
				if(pawnBillObj.getTotalGrossWeight()!=null) {
					totalGrossWeight.setText(Double.toString(pawnBillObj.getTotalGrossWeight()));
				}
				if(pawnBillObj.getTotalNetWeight()!=null) {
					totalNetWeight.setText(Double.toString(pawnBillObj.getTotalNetWeight()));
				}
				calculateAmountPayable();
				pawnBillDetailList = pawnBillObj.getPawnBillDetails();
				
				billSno = (int) pawnBillDetailList
						  .stream()
						  //.filter(c -> c.getDeletedFlag().equalsIgnoreCase(ConstantUtils.CONSTANT_N))
						  .count();
				addOrnamentToTable();
				System.out.println(billSno);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	
	private Ornament createOrnament(String ornamentName) {
		Ornament ornamentObj = null;
		try {
			ornamentObj = new Ornament();
			
			ornamentObj.setMetalId((Metal)metal.getSelectedItem());
			ornamentObj.setOrnamentName(StringUtils.capitalize(ornamentName.trim()));
			ornamentObj.setDeletedFlag(ConstantUtils.CONSTANT_N);
			ornamentObj.setLicenceId(Session.licence.getLicenceId());
			
			Integer ornamentId = MasterController.getInstace().saveOrUpdateOrnament(ornamentObj);
			if(ornamentId>0) {
				ornamentObj = MasterController.getInstace().getOrnament(ornamentId);
			}else {
				ornamentObj = null;
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return ornamentObj;
	}
	
	private Metal createMetal(String metalName) {
		Metal metal = null;
		try {
			metal = new Metal();
			
			metal.setMetalName(StringUtils.capitalize(metalName.trim()));
			metal.setMetalCode(StringUtils.capitalize(metalName.trim()));
			metal.setDeletedFlag(ConstantUtils.CONSTANT_N);
			
			Integer metalId = MasterController.getInstace().saveOrUpdateMetal(metal);
			if(metalId>0) {
				metal = MasterController.getInstace().getMetal(StringUtils.capitalize(metalName.trim()));
			}else {
				metal = null;
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return metal;
	}
	
	private void viewContactImage() {
		try {
			Contact selectedContact = (Contact)contact.getSelectedItem();
			BufferedImage img = ImageIO.read(new File("image/Customer-Photo/"+selectedContact.getContactId()+".jpg"));
			if(img!=null) {
	         ImageIcon icon = new ImageIcon(img);
	         JLabel label = new JLabel(icon);
	         JOptionPane.showMessageDialog(panel, label);
			}else {
				JOptionPane.showMessageDialog(null, "No Photo");	
			}
		}catch(Exception ex) {
			JOptionPane.showMessageDialog(null, "No Photo");
			ex.printStackTrace();
		}
	}
	
	public void addContactWindow() {
		try {
			AppController.getInstance().addContactFormPage(0, ConstantUtils.CONTACT_TYPE_PAWNCUSTOMER, billSource, Session.licence.getLicenceId());
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

}
