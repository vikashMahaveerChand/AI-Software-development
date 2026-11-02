package com.inventory.view;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

import com.inventory.components.MyButton;
import com.inventory.components.MyLabel;
import com.inventory.components.MyPanel;
import com.inventory.components.MyTextField;
import com.inventory.controller.AppController;
import com.inventory.controller.MasterController;
import com.inventory.dto.StreetDTO;
import com.inventory.model.Area;
import com.inventory.model.City;
import com.inventory.model.Contact;
import com.inventory.model.ContactType;
import com.inventory.model.Licence;
import com.inventory.model.OpeningBalanceDetailsContact;
import com.inventory.model.State;
import com.inventory.model.Street;
import com.inventory.utility.ConstantUtils;
import com.inventory.utility.Session;
import com.inventory.utility.StringUtils;

import net.miginfocom.swing.MigLayout;

public class ContactForm implements DocumentListener, ActionListener, KeyListener, MouseListener{
	
	private static ContactForm myInstance = null;
	Contact loadContactObj = null;
	
	private ContactForm() {
	}

	public static ContactForm getInstance() {
		if (myInstance == null) {
			myInstance = new ContactForm();
		}

		return myInstance;
	}
	JPanel panel;
	JPanel dataPanel,cameraPanel;
	JPanel cameraPanelButton,cameraPanelView;
	String calledFrom;
	Integer contactId,licenceId;
	String contactTypeName;
	JComboBox contactType,state,city,area,street;
	MyButton save,viewContactPhoto;
	MyTextField contactName,secondName,doorNo,mobile,email,gstNumber,pincode;
	MyTextField aadharCard,panCard,voterId,rationCard;
	MyTextField gstCash,nonGSTCash,gstGoldBalance,nonGSTGoldBalance,gstSilverBalance,nonGSTSilverBalance;
	Integer gstCashId,nonGSTCashId,nonGSTGoldBalanceId,nonGSTSilverBalanceId;
	String userType;
	Licence licence;
	List<ContactType> contactTypeList = null;
	List<State> stateList = null;
	List<City> cityList = null;
	List<Area> areaList = null;
	List<StreetDTO> streetDTOList = null;
	
	String licenceTypeStr ="";
	
	
	JTextField tfState,tfCity,tfArea,tfStreet;
	boolean tfState_hide_flag = false,tfCity_hide_flag = false,tfArea_hide_flag = false,tfStreet_hide_flag = false;
	
	//Camera related fields starts
	MyButton startCamera,stopCamera;
	// Camera screen
	private JLabel cameraScreen;

	// Button for image capture
	private MyButton btnCapture;

	// Start camera
	private VideoCapture capture;

	// Store image as 2D matrix
	private Mat image;

	private boolean clicked = false;
	
	private boolean cameraOnOff = false;
	//Camera related fields ends
	
	public JPanel getPanel(Integer contactId,String contactTypeName,String calledFrom,Integer licenceId){
		this.contactId=contactId;
		this.contactTypeName=contactTypeName;
		this.calledFrom=calledFrom;
		this.licenceId=licenceId;
		
		initComponent();
		constructContactForm();
		if(contactId>0){
			loadContactForm();
		}
		return panel;
	}
	
	private void initComponent(){
		try{
			gstCashId=-1;
			nonGSTCashId=-1;
			nonGSTGoldBalanceId=-1;
			nonGSTSilverBalanceId=-1;
			
			licence = Session.licence;
			userType = Session.user.getUserType().getUserType();
			licenceTypeStr = licence.getLicenceType().getLicenceTypeName();
			
			panel = new JPanel(new MigLayout());
			dataPanel = new JPanel(new MigLayout());
			cameraPanel = new JPanel(new MigLayout());
			cameraPanelButton = new JPanel(new MigLayout());
			cameraPanelView = new JPanel(new MigLayout());
			
			MyPanel.setBackground(panel);
			contactName = new MyTextField(20);
			secondName = new MyTextField(20);
			doorNo = new MyTextField(20);
			mobile = new MyTextField(20);
			email = new MyTextField(20);
			gstNumber = new MyTextField(20);
			pincode = new MyTextField(20);
			tfState = new JTextField(20);
			tfCity = new JTextField(20);
			tfArea = new JTextField(20);
			tfStreet = new JTextField(20);
			
			gstCash = new MyTextField(20);
			nonGSTCash = new MyTextField(20);
			gstGoldBalance = new MyTextField(20);
			nonGSTGoldBalance = new MyTextField(20);
			gstSilverBalance = new MyTextField(20);
			nonGSTSilverBalance = new MyTextField(20);
			
			aadharCard = new MyTextField(20);
			panCard = new MyTextField(20);
			voterId = new MyTextField(20);
			rationCard = new MyTextField(20);
			
			save = new MyButton("") ;
			viewContactPhoto = new MyButton("View Photo");
			startCamera = new MyButton("Start Camera");
			stopCamera = new MyButton("Stop Camera");
			
			contactType = new JComboBox();
			contactType.setSize(300, 10);
			
			state = new JComboBox();
			state.setSize(300, 10);
			
			city = new JComboBox();
			city.setSize(300, 10);
			
			area = new JComboBox();
			area.setSize(300, 10);
			
			street = new JComboBox();
			street.setSize(300, 10);
			
			contactTypeList = MasterController.getInstace().getContactTypeList();
			stateList = MasterController.getInstace().getStateList();
			cityList = MasterController.getInstace().getCityList();
			areaList = MasterController.getInstace().getAreaList();
			streetDTOList = MasterController.getInstace().getStreetDTOList();
			
			state.setEditable(true);
			city.setEditable(true);
			area.setEditable(true);
			street.setEditable(true);
			
	        tfState = (JTextField) state.getEditor().getEditorComponent();
	        tfCity = (JTextField) city.getEditor().getEditorComponent();
	        tfArea = (JTextField) area.getEditor().getEditorComponent();
	        tfStreet = (JTextField) street.getEditor().getEditorComponent();
			
			contactName.addActionListener(this);
			secondName.addActionListener(this);
			doorNo.addActionListener(this);
			mobile.addActionListener(this);
			email.addActionListener(this);
			gstNumber.addActionListener(this);
			pincode.addActionListener(this);
			state.addActionListener(this);
			contactType.addActionListener(this);
			tfState.addActionListener(this);
			tfCity.addActionListener(this);
			tfArea.addActionListener(this);
			tfStreet.addActionListener(this);
			save.addActionListener(this);
			viewContactPhoto.addActionListener(this);
			gstCash.addActionListener(this);
			nonGSTCash.addActionListener(this);
			gstGoldBalance.addActionListener(this);
			nonGSTGoldBalance.addActionListener(this);
			gstSilverBalance.addActionListener(this);
			nonGSTSilverBalance.addActionListener(this);
			startCamera.addActionListener(this);
			stopCamera.addActionListener(this);
			aadharCard.addActionListener(this);
			panCard.addActionListener(this);
			voterId.addActionListener(this);
			rationCard.addActionListener(this);

			contactName.addKeyListener(this);
			secondName.addKeyListener(this);
			doorNo.addKeyListener(this);
			mobile.addKeyListener(this);
			email.addKeyListener(this);
			gstNumber.addKeyListener(this);
			pincode.addKeyListener(this);
			state.addKeyListener(this);
			contactType.addKeyListener(this);
			tfState.addKeyListener(this);
			tfCity.addKeyListener(this);
			tfArea.addKeyListener(this);
			tfStreet.addKeyListener(this);
			save.addKeyListener(this);
			viewContactPhoto.addKeyListener(this);
			gstCash.addKeyListener(this);
			nonGSTCash.addKeyListener(this);
			gstGoldBalance.addKeyListener(this);
			nonGSTGoldBalance.addKeyListener(this);
			gstSilverBalance.addKeyListener(this);
			nonGSTSilverBalance.addKeyListener(this);
			aadharCard.addKeyListener(this);
			panCard.addKeyListener(this);
			voterId.addKeyListener(this);
			rationCard.addKeyListener(this);			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void constructContactForm(){
		try{
			if(contactTypeName.equalsIgnoreCase(ConstantUtils.RETAIL_CONTACT_TYPE)){
				constructRetailContactForm();
			}else if(contactTypeName.equalsIgnoreCase(ConstantUtils.WHOLESALE_CONTACT_TYPE)){
				constructWholesaleContactForm();
			}else if(contactTypeName.equalsIgnoreCase(ConstantUtils.SUPPLIER_CONTACT_TYPE)){
				constructSupplierContactForm();
			}else if(contactTypeName.equalsIgnoreCase(ConstantUtils.PAWNCUSTOMER_CONTACT_TYPE)){
				constructPawnCustomerContactForm();
				constructCameraPanel();
			}else if(contactTypeName.equalsIgnoreCase(ConstantUtils.CONTACT_TYPE_GENERIC)){
				constructGenericCustomerContactForm();
				constructCameraPanel();
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void constructRetailContactForm(){
		try{
			MyPanel.setBorder("Retail Customer", dataPanel);
			
			dataPanel.add(new MyLabel("Customer Name : "));
			dataPanel.add(contactName, "wrap");
			
			dataPanel.add(new MyLabel("Door No : "));
			dataPanel.add(doorNo, "wrap");
			
			dataPanel.add(new MyLabel("Mobile : "));
			dataPanel.add(mobile, "wrap");
			
			dataPanel.add(new MyLabel("Email : "));
			dataPanel.add(email, "wrap");
			
			dataPanel.add(new MyLabel("Street : "));
			dataPanel.add(street, "wrap");
			
			dataPanel.add(new MyLabel("Area : "));
			dataPanel.add(area, "wrap");
			
			dataPanel.add(new MyLabel("City : "));
			dataPanel.add(city, "wrap");
			
			dataPanel.add(new MyLabel("Picode : "));
			dataPanel.add(pincode, "wrap");
			
			dataPanel.add(new MyLabel("State : "));
			dataPanel.add(state, "wrap");
			
			//panel.add(new MyLabel("Contact Type : "));
			//panel.add(contactType, "wrap");
			
			dataPanel.add(save);
			
			int defaultSelectIndex=-1;
			int counter=-1;
			if(contactTypeList!=null && contactTypeList.size()>0){
				for(ContactType obj:contactTypeList){
					counter++;
					if(contactTypeName.equalsIgnoreCase(obj.getContactTypeName())){
						defaultSelectIndex = counter;
					}
					contactType.addItem(obj);
	            }
			}
			
			if(defaultSelectIndex>0){
				contactType.setSelectedIndex(defaultSelectIndex);
			}
			
			setModel(state,tfState,new DefaultComboBoxModel(new Vector<>(stateList)), "");
			setModel(city,tfCity,new DefaultComboBoxModel(new Vector<>(cityList)), "");
			setModel(area,tfArea,new DefaultComboBoxModel(new Vector<>(areaList)), "");
			setModel(street,tfStreet,new DefaultComboBoxModel(new Vector<>(streetDTOList)), "");
			
			save.setText("Create Retail Customer");
			
			//panel.add(dataPanel);
			panel.add(dataPanel,"pushy,growy");
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void constructWholesaleContactForm(){
		try{
			MyPanel.setBorder("Wholesale Customer", dataPanel);
			
			dataPanel.add(new MyLabel("Customer Name : "));
			dataPanel.add(contactName, "wrap");
			
			dataPanel.add(new MyLabel("Door No : "));
			dataPanel.add(doorNo, "wrap");
			
			dataPanel.add(new MyLabel("Mobile : "));
			dataPanel.add(mobile, "wrap");
			
			dataPanel.add(new MyLabel("Email : "));
			dataPanel.add(email, "wrap");
			
			if(userType.equals(ConstantUtils.USER_TYPE_GST) || userType.equals(ConstantUtils.USER_TYPE_ADMIN)) {
				dataPanel.add(new MyLabel("GST Number : "));
				dataPanel.add(gstNumber, "wrap");
			}
			
			dataPanel.add(new MyLabel("Street : "));
			dataPanel.add(street, "wrap");
			
			dataPanel.add(new MyLabel("Area : "));
			dataPanel.add(area, "wrap");
			
			dataPanel.add(new MyLabel("City : "));
			dataPanel.add(city, "wrap");
			
			dataPanel.add(new MyLabel("Picode : "));
			dataPanel.add(pincode, "wrap");
			
			dataPanel.add(new MyLabel("State : "));
			dataPanel.add(state, "wrap");
			
			//panel.add(new MyLabel("Contact Type : "));
			//panel.add(contactType, "wrap");
			
			if(userType.equalsIgnoreCase(ConstantUtils.USER_TYPE_GST) || userType.equalsIgnoreCase(ConstantUtils.USER_TYPE_ADMIN)) {
				dataPanel.add(new MyLabel("GST Cash Balance : "));
				dataPanel.add(gstCash, "wrap");
			}
			
			if(userType.equalsIgnoreCase(ConstantUtils.USER_TYPE_NON_GST) || userType.equalsIgnoreCase(ConstantUtils.USER_TYPE_ADMIN)) {
				dataPanel.add(new MyLabel("NONGST Cash Balance : "));
				dataPanel.add(nonGSTCash, "wrap");
			}
			
			if((userType.equalsIgnoreCase(ConstantUtils.USER_TYPE_NON_GST) || userType.equalsIgnoreCase(ConstantUtils.USER_TYPE_ADMIN)) && 
					(licence.getLicenceType().getLicenceTypeName().equals(ConstantUtils.LICENCE_TYPE_GOLD_WHOLESALE)
							|| licence.getLicenceType().getLicenceTypeName().equals(ConstantUtils.LICENCE_TYPE_WHOLESALE)
							)
					) {
				dataPanel.add(new MyLabel("NONGST Gold Metal Balance : "));
				dataPanel.add(nonGSTGoldBalance, "wrap");
			}
			
			if((userType.equalsIgnoreCase(ConstantUtils.USER_TYPE_NON_GST) || userType.equalsIgnoreCase(ConstantUtils.USER_TYPE_ADMIN)) && 
					(licence.getLicenceType().getLicenceTypeName().equals(ConstantUtils.LICENCE_TYPE_SILVER_WHOLESALE))
					|| licence.getLicenceType().getLicenceTypeName().equals(ConstantUtils.LICENCE_TYPE_WHOLESALE)
					) {
				dataPanel.add(new MyLabel("NONGST Silver Metal Balance : "));
				dataPanel.add(nonGSTSilverBalance, "wrap");
			}
			
			if((userType.equalsIgnoreCase(ConstantUtils.USER_TYPE_NON_GST) || userType.equalsIgnoreCase(ConstantUtils.USER_TYPE_ADMIN)) && 
					licence.getLicenceType().getLicenceTypeName().equals(ConstantUtils.LICENCE_TYPE_JEWELLERY)) {
				dataPanel.add(new MyLabel("NONGST Gold Metal Balance : "));
				dataPanel.add(nonGSTGoldBalance, "wrap");
				
				dataPanel.add(new MyLabel("NONGST Silver Metal Balance : "));
				dataPanel.add(nonGSTSilverBalance, "wrap");				
				
			}
			
			
			
			dataPanel.add(save);
			
			int defaultSelectIndex=-1;
			int counter=-1;
			if(contactTypeList!=null && contactTypeList.size()>0){
				for(ContactType obj:contactTypeList){
					counter++;
					if(contactTypeName.equalsIgnoreCase(obj.getContactTypeName())){
						defaultSelectIndex = counter;
					}
					contactType.addItem(obj);
	            }
			}
			
			if(defaultSelectIndex>0){
				contactType.setSelectedIndex(defaultSelectIndex);
			}
			
			setModel(state,tfState,new DefaultComboBoxModel(new Vector<>(stateList)), "");
			setModel(city,tfCity,new DefaultComboBoxModel(new Vector<>(cityList)), "");
			setModel(area,tfArea,new DefaultComboBoxModel(new Vector<>(areaList)), "");
			setModel(street,tfStreet,new DefaultComboBoxModel(new Vector<>(streetDTOList)), "");
			
			save.setText("Create Wholesale Customer");
			
			//panel.add(dataPanel);
			panel.add(dataPanel,"pushy,growy");
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void constructSupplierContactForm(){
		try{
			MyPanel.setBorder("Supplier", dataPanel);
			
			dataPanel.add(new MyLabel("Supplier Name : "));
			dataPanel.add(contactName, "wrap");
			
			dataPanel.add(new MyLabel("Door No : "));
			dataPanel.add(doorNo, "wrap");
			
			dataPanel.add(new MyLabel("Mobile : "));
			dataPanel.add(mobile, "wrap");
			
			dataPanel.add(new MyLabel("Email : "));
			dataPanel.add(email, "wrap");
			
			if(userType.equalsIgnoreCase(ConstantUtils.USER_TYPE_GST) || userType.equalsIgnoreCase(ConstantUtils.USER_TYPE_ADMIN)) {
				dataPanel.add(new MyLabel("GST Number : "));
				dataPanel.add(gstNumber, "wrap");
			}
			
			dataPanel.add(new MyLabel("Street : "));
			dataPanel.add(street, "wrap");
			
			dataPanel.add(new MyLabel("Area : "));
			dataPanel.add(area, "wrap");
			
			dataPanel.add(new MyLabel("City : "));
			dataPanel.add(city, "wrap");
			
			dataPanel.add(new MyLabel("Picode : "));
			dataPanel.add(pincode, "wrap");
			
			dataPanel.add(new MyLabel("State : "));
			dataPanel.add(state, "wrap");
			
			//panel.add(new MyLabel("Contact Type : "));
			//panel.add(contactType, "wrap");
			
			if(userType.equalsIgnoreCase(ConstantUtils.USER_TYPE_GST) || userType.equalsIgnoreCase(ConstantUtils.USER_TYPE_ADMIN)) {
				dataPanel.add(new MyLabel("GST Cash Balance : "));
				dataPanel.add(gstCash, "wrap");
			}
			
			if(userType.equalsIgnoreCase(ConstantUtils.USER_TYPE_NON_GST) || userType.equalsIgnoreCase(ConstantUtils.USER_TYPE_ADMIN)) {
				dataPanel.add(new MyLabel("NONGST Cash Balance : "));
				dataPanel.add(nonGSTCash, "wrap");
			}
			
			if((userType.equalsIgnoreCase(ConstantUtils.USER_TYPE_NON_GST) || userType.equalsIgnoreCase(ConstantUtils.USER_TYPE_ADMIN)) 
					&& (licence.getLicenceType().getLicenceTypeName().equals(ConstantUtils.LICENCE_TYPE_GOLD_WHOLESALE)
					||  licence.getLicenceType().getLicenceTypeName().equals(ConstantUtils.LICENCE_TYPE_WHOLESALE))
					) {
				dataPanel.add(new MyLabel("NONGST Gold Metal Balance : "));
				dataPanel.add(nonGSTGoldBalance, "wrap");
			}
			
			if((userType.equalsIgnoreCase(ConstantUtils.USER_TYPE_NON_GST) || userType.equalsIgnoreCase(ConstantUtils.USER_TYPE_ADMIN)) 
					&& (licence.getLicenceType().getLicenceTypeName().equals(ConstantUtils.LICENCE_TYPE_SILVER_WHOLESALE)
							|| licence.getLicenceType().getLicenceTypeName().equals(ConstantUtils.LICENCE_TYPE_WHOLESALE)
							)) {
				dataPanel.add(new MyLabel("NONGST Silver Metal Balance : "));
				dataPanel.add(nonGSTSilverBalance, "wrap");
			}
			
			if((userType.equalsIgnoreCase(ConstantUtils.USER_TYPE_NON_GST) || userType.equalsIgnoreCase(ConstantUtils.USER_TYPE_ADMIN)) 
					&& licence.getLicenceType().getLicenceTypeName().equals(ConstantUtils.LICENCE_TYPE_JEWELLERY)) {
				dataPanel.add(new MyLabel("NONGST Gold Metal Balance : "));
				dataPanel.add(nonGSTGoldBalance, "wrap");
				
				dataPanel.add(new MyLabel("NONGST Silver Metal Balance : "));
				dataPanel.add(nonGSTSilverBalance, "wrap");				
				
			}
			
			dataPanel.add(save);
			
			int defaultSelectIndex=-1;
			int counter=-1;
			if(contactTypeList!=null && contactTypeList.size()>0){
				for(ContactType obj:contactTypeList){
					counter++;
					if(contactTypeName.equalsIgnoreCase(obj.getContactTypeName())){
						defaultSelectIndex = counter;
					}
					contactType.addItem(obj);
	            }
			}
			
			if(defaultSelectIndex>0){
				contactType.setSelectedIndex(defaultSelectIndex);
			}
			
			setModel(state,tfState,new DefaultComboBoxModel(new Vector<>(stateList)), "");
			setModel(city,tfCity,new DefaultComboBoxModel(new Vector<>(cityList)), "");
			setModel(area,tfArea,new DefaultComboBoxModel(new Vector<>(areaList)), "");
			setModel(street,tfStreet,new DefaultComboBoxModel(new Vector<>(streetDTOList)), "");
			
			save.setText("Create Supplier");
			
			//panel.add(dataPanel);
			panel.add(dataPanel,"pushy,growy");
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private void constructPawnCustomerContactForm() {
		try {
			MyPanel.setBorder("Pawn Customer", dataPanel);
			
			dataPanel.add(new MyLabel("Customer Full Name : "));
			dataPanel.add(contactName, "wrap");
			
			dataPanel.add(new MyLabel("Second Name(S/O, D/O, W/O,C/O) : "));
			dataPanel.add(secondName, "wrap");
			
			dataPanel.add(new MyLabel("Mobile : "));
			dataPanel.add(mobile, "wrap");
			
			dataPanel.add(new MyLabel("Email : "));
			dataPanel.add(email, "wrap");

			dataPanel.add(new MyLabel("Door No : "));
			dataPanel.add(doorNo, "wrap");
			
			//panel.add(new MyLabel("GST Number : "));
			//panel.add(gstNumber, "wrap");
			
			dataPanel.add(new MyLabel("Street : "));
			dataPanel.add(street, "wrap");
			
			dataPanel.add(new MyLabel("Area : "));
			dataPanel.add(area, "wrap");
			
			dataPanel.add(new MyLabel("City : "));
			dataPanel.add(city, "wrap");
			
			dataPanel.add(new MyLabel("Picode : "));
			dataPanel.add(pincode, "wrap");
			
			dataPanel.add(new MyLabel("State : "));
			dataPanel.add(state, "wrap");
			
			dataPanel.add(new MyLabel("Aadhar Card : "));
			dataPanel.add(aadharCard, "wrap");
			
			dataPanel.add(new MyLabel("Pan Card : "));
			dataPanel.add(panCard, "wrap");
			
			dataPanel.add(new MyLabel("Voter Id : "));
			dataPanel.add(voterId, "wrap");
			
			dataPanel.add(new MyLabel("Ration Card : "));
			dataPanel.add(rationCard, "wrap");
			//aadharCard,panCard,voterId,rationCard
			
			//panel.add(new MyLabel("Contact Type : "));
			//panel.add(contactType, "wrap");
			
			dataPanel.add(save);
			if(contactId!=null && contactId>0) {
				dataPanel.add(viewContactPhoto);
			}
			
			int defaultSelectIndex=-1;
			int counter=-1;
			if(contactTypeList!=null && contactTypeList.size()>0){
				for(ContactType obj:contactTypeList){
					counter++;
					if(contactTypeName.equalsIgnoreCase(obj.getContactTypeName())){
						defaultSelectIndex = counter;
					}
					contactType.addItem(obj);
	            }
			}
			
			if(defaultSelectIndex>0){
				contactType.setSelectedIndex(defaultSelectIndex);
			}
			
			setModel(state,tfState,new DefaultComboBoxModel(new Vector<>(stateList)), "");
			setModel(city,tfCity,new DefaultComboBoxModel(new Vector<>(cityList)), "");
			setModel(area,tfArea,new DefaultComboBoxModel(new Vector<>(areaList)), "");
			setModel(street,tfStreet,new DefaultComboBoxModel(new Vector<>(streetDTOList)), "");
			
			save.setText("Create Pawn Customer");
			
			panel.add(dataPanel,"pushy,growy");
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private void constructGenericCustomerContactForm() {
		try {
			MyPanel.setBorder("Generic Customer", dataPanel);
			
			dataPanel.add(new MyLabel("Customer Full Name : "));
			dataPanel.add(contactName, "wrap");
			
			dataPanel.add(new MyLabel("Second Name(S/O, D/O, W/O,C/O) : "));
			dataPanel.add(secondName, "wrap");
			
			dataPanel.add(new MyLabel("Mobile : "));
			dataPanel.add(mobile, "wrap");
			
			dataPanel.add(new MyLabel("Email : "));
			dataPanel.add(email, "wrap");

			dataPanel.add(new MyLabel("Door No : "));
			dataPanel.add(doorNo, "wrap");
			
			//panel.add(new MyLabel("GST Number : "));
			//panel.add(gstNumber, "wrap");
			
			dataPanel.add(new MyLabel("Street : "));
			dataPanel.add(street, "wrap");
			
			dataPanel.add(new MyLabel("Area : "));
			dataPanel.add(area, "wrap");
			
			dataPanel.add(new MyLabel("City : "));
			dataPanel.add(city, "wrap");
			
			dataPanel.add(new MyLabel("Picode : "));
			dataPanel.add(pincode, "wrap");
			
			dataPanel.add(new MyLabel("State : "));
			dataPanel.add(state, "wrap");
			
			dataPanel.add(new MyLabel("Aadhar Card : "));
			dataPanel.add(aadharCard, "wrap");
			
			dataPanel.add(new MyLabel("Pan Card : "));
			dataPanel.add(panCard, "wrap");
			
			dataPanel.add(new MyLabel("Voter Id : "));
			dataPanel.add(voterId, "wrap");
			
			dataPanel.add(new MyLabel("Ration Card : "));
			dataPanel.add(rationCard, "wrap");
			//aadharCard,panCard,voterId,rationCard
			
			//panel.add(new MyLabel("Contact Type : "));
			//panel.add(contactType, "wrap");
			
			dataPanel.add(save);
			if(contactId!=null && contactId>0) {
				dataPanel.add(viewContactPhoto);
			}
			
			int defaultSelectIndex=-1;
			int counter=-1;
			if(contactTypeList!=null && contactTypeList.size()>0){
				for(ContactType obj:contactTypeList){
					counter++;
					if(contactTypeName.equalsIgnoreCase(obj.getContactTypeName())){
						defaultSelectIndex = counter;
					}
					contactType.addItem(obj);
	            }
			}
			
			if(defaultSelectIndex>0){
				contactType.setSelectedIndex(defaultSelectIndex);
			}
			
			setModel(state,tfState,new DefaultComboBoxModel(new Vector<>(stateList)), "");
			setModel(city,tfCity,new DefaultComboBoxModel(new Vector<>(cityList)), "");
			setModel(area,tfArea,new DefaultComboBoxModel(new Vector<>(areaList)), "");
			setModel(street,tfStreet,new DefaultComboBoxModel(new Vector<>(streetDTOList)), "");
			
			save.setText("Save");
			
			panel.add(dataPanel,"pushy,growy");
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private void constructCameraPanel() {
		try {
			MyPanel.setBorder("Camera Picture", cameraPanel);
			//cameraPanelButton,cameraPanelView
			
			cameraPanelButton.add(startCamera);
			cameraPanelButton.add(stopCamera);
			
			
			
			
			
			cameraPanel.add(cameraPanelButton,"wrap");
			cameraPanel.add(cameraPanelView,"pushx,pushy,growx,growy");
			
			panel.add(cameraPanel,"pushx,pushy,growx,growy");
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private void constructAndStartCamera() {
		try {
			System.out.println("constructAndStartCamera - Start");
			cameraPanelView.removeAll();
			//cameraPanelView.removeAll();
			//Create Camera Starts
			cameraScreen = new JLabel();
			cameraOnOff = true;
			//cameraScreen.setBounds(0, 0, 640, 480);
			//cameraPanelView.add(cameraScreen,"pushx,pushy,growx,growy,wrap");
			cameraPanelView.add(cameraScreen,"wrap");
			
			btnCapture = new MyButton("capture");
			//btnCapture.setBounds(300, 480, 80, 40);
			cameraPanelView.add(btnCapture,"wrap");
			btnCapture.addActionListener(this);
			
			//cameraPanelView.revalidate();
			//cameraPanelView.repaint();
	    
			cameraPanelView.revalidate();
			cameraPanelView.repaint();
			//Create Camera Ends
			
			//Start Camera Starts
			try {
			capture = new VideoCapture(0);
			image = new Mat();
			byte[] imageData;

			ImageIcon icon;
			System.out.println("Before While");
			while (cameraOnOff) {
				//System.out.println("In While");
				capture.read(image);

				// convert matrix to byte
				final MatOfByte buf = new MatOfByte();
				Imgcodecs.imencode(".jpg", image, buf);

				imageData = buf.toArray();

				// Add to JLabel
				icon = new ImageIcon(imageData);
				cameraScreen.setIcon(icon);
				//cameraPanelView.revalidate();
				//cameraPanelView.repaint();
			}
			}catch(Exception ex) {
				ex.printStackTrace();
			}
			//Start Camera Ends
			
		}catch(Exception ex) {
			System.out.println("Exception construct and start camera");
			ex.printStackTrace();
		}
		System.out.println("constructAndStartCamera - Ends");
	}
	
	private void saveContactPhoto() {
		try {			
			if(contactId!=null && contactId>0) {
				
				Imgcodecs.imwrite("image/Customer-Photo/" + contactId + ".jpg",
						image);	
			}else {
			String name="contact";
			
			Imgcodecs.imwrite("image/" + name + ".jpg",
					image);
			}
			cameraOnOff = false;
			capture.release();
			capture = null;
			
		}catch(Exception ex) {
			System.out.println("Exception construct and start camera");
			ex.printStackTrace();
		}
	}
	
	private void stopCamera() {
		try {
			cameraOnOff = false;
			capture.release();
			capture = null;
			cameraPanelView.removeAll();
			cameraPanelView.revalidate();
			cameraPanelView.repaint();
		}catch(Exception ex) {
			
		}
	}
	
	private void transferImage(Integer custId) {
		try {
			System.out.println("Transfer Files Starts");
			Optional<Path> sourcepathop = Optional.ofNullable(Paths.get("/image/contact.jpg"));
			
			//Path sourcepath = Paths.get("image/contact.jpg");
			System.out.println("Is contact image present : "+sourcepathop.isPresent());
			if(sourcepathop.isPresent()) {
				Path sourcepath = sourcepathop.get();
				Path destinationepath = Paths.get("image/Customer-Photo/"+custId+".jpg");
				if(sourcepath!=null) {
					Files.move(sourcepath, destinationepath, StandardCopyOption.REPLACE_EXISTING);
				}
			}
			System.out.println("Transfer Files Ends");
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void viewContactImage() {
		try {
			BufferedImage img = ImageIO.read(new File("image/Customer-Photo/"+contactId+".jpg"));
			if(img!=null) {
	         ImageIcon icon = new ImageIcon(img);
	         JLabel label = new JLabel(icon);
	         JOptionPane.showMessageDialog(null, label);
			}else {
				JOptionPane.showMessageDialog(null, "No Photo");	
			}
		}catch(Exception ex) {
			JOptionPane.showMessageDialog(null, "No Photo");
			ex.printStackTrace();
		}
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
		if(evt.getSource() == tfState){
			stateKeyPressedEvent(evt);
		}else if(evt.getSource() == tfCity){
			cityKeyPressedEvent(evt);
		}else if(evt.getSource() == tfArea){
			areaKeyPressedEvent(evt);
		}else if(evt.getSource() == tfStreet){
			streetKeyPressedEvent(evt);
		}else if(evt.getKeyCode() == KeyEvent.VK_ENTER){
			onClickEvent(evt.getSource());
		}
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent evt) {
		// TODO Auto-generated method stub
		if(evt.getSource()==tfState){
			stateKeyTypedEvent(evt);
		}else if(evt.getSource()==tfCity){
			cityKeyTypedEvent(evt);
		}else if(evt.getSource()==tfArea){
			areaKeyTypedEvent(evt);
		}else if(evt.getSource()==tfStreet){
			streetKeyTypedEvent(evt);
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
		if(evt == contactType || evt == state || evt == city || evt == area || evt == street){
			((Component) evt).transferFocus();
			
		}else if(evt == startCamera) {
			EventQueue.invokeLater(new Runnable() {
				// Overriding existing run() method
				@Override public void run()
				{

					// Start camera in thread
					new Thread(new Runnable() {
						@Override public void run()
						{
							constructAndStartCamera();
						}
					}).start();
				}
			});
		}else if(evt == stopCamera) {
			stopCamera();
		}else if(evt == btnCapture) {
			saveContactPhoto();
		}else if(evt == viewContactPhoto) {
			viewContactImage();
		}else{
		//JOptionPane.showMessageDialog(null, "onclcik");
			if(evt == save){
				saveOrUpdateContact();
			}
		}
	}
	
	private void saveOrUpdateContact(){
		try{
			
			System.out.println("StreetId->"+street.getSelectedIndex());
			System.out.println("AreaId->"+area.getSelectedIndex());
			System.out.println("cityId->"+city.getSelectedIndex());
			System.out.println("State Id"+state.getSelectedIndex());
			Integer streetId=null;
			
			boolean contactValidationFlag=contactValidation();
			if(contactValidationFlag){
				streetId = getAddressStreetId();
				
				if(streetId!=null && streetId>0){
					Contact contactObj = new Contact();
					
					if(contactId!=null && contactId>0){
						contactObj.setContactId(contactId);
						if(loadContactObj!=null){
							contactObj.setCreatedDate(loadContactObj.getCreatedDate());
						}
					}else{
						contactObj.setCreatedDate(new Timestamp(System.currentTimeMillis()));
					}
					
					contactObj.setContactName(StringUtils.capitalize(contactName.getText().trim()));
					if(!secondName.getText().equals("")) {
						contactObj.setSecondName(StringUtils.capitalize(secondName.getText().trim()));
					}
					contactObj.setContactType((ContactType)contactType.getSelectedItem());
					contactObj.setDeletedFlag(ConstantUtils.CONSTANT_N);
					contactObj.setEmail(email.getText().trim());
					contactObj.setGstNumber(gstNumber.getText().trim());
					contactObj.setIsEnabled(ConstantUtils.CONSTANT_Y);
					contactObj.setMobile(mobile.getText().trim());
					contactObj.setStreet(MasterController.getInstace().getStreet(streetId));
					contactObj.setDoorNo(doorNo.getText().trim());
					
					contactObj.setAadharCard(aadharCard.getText());
					contactObj.setPanCard(panCard.getText());
					contactObj.setVoterId(voterId.getText());
					contactObj.setRationCard(rationCard.getText());
					
					//Opening Balance Save Starts
					//gstCash,nonGSTCash,gstGoldBalance,nonGSTGoldBalance,gstSilverBalance,nonGSTSilverBalance;
					//gstCashId,nonGSTCashId,nonGSTGoldBalanceId,nonGSTSilverBalanceId;
					List<OpeningBalanceDetailsContact> openingBalanceDetailsList = new ArrayList<OpeningBalanceDetailsContact>();
					
					if(!gstCash.getText().equals("")) {
						OpeningBalanceDetailsContact openingBalance = new OpeningBalanceDetailsContact();
						openingBalance.setAmount(Double.parseDouble(gstCash.getText()));
						openingBalance.setBillType(MasterController.getInstace().getBillType(ConstantUtils.GST_ENTRY));
						openingBalance.setDeletedFlag(ConstantUtils.CONSTANT_N);
						openingBalance.setLicence(Session.licence);
						if(gstCashId>0) {
							openingBalance.setOpeningBalanceDetailsContactId(gstCashId);
						}
						openingBalanceDetailsList.add(openingBalance);
					}
					
					if(!nonGSTCash.getText().equals("")) {
						OpeningBalanceDetailsContact openingBalance = new OpeningBalanceDetailsContact();
						openingBalance.setAmount(Double.parseDouble(nonGSTCash.getText()));
						openingBalance.setBillType(MasterController.getInstace().getBillType(ConstantUtils.NON_GST_ENTRY));
						openingBalance.setDeletedFlag(ConstantUtils.CONSTANT_N);
						openingBalance.setLicence(Session.licence);
						if(nonGSTCashId>0) {
							openingBalance.setOpeningBalanceDetailsContactId(nonGSTCashId);
						}
						openingBalanceDetailsList.add(openingBalance);
					}
					
					if(!nonGSTGoldBalance.getText().equals("")) {
						OpeningBalanceDetailsContact openingBalance = new OpeningBalanceDetailsContact();
						openingBalance.setGrams(Double.parseDouble(nonGSTGoldBalance.getText()));
						openingBalance.setMetal(MasterController.getInstace().getMetal(ConstantUtils.GOLD));
						openingBalance.setBillType(MasterController.getInstace().getBillType(ConstantUtils.NON_GST_ENTRY));
						openingBalance.setDeletedFlag(ConstantUtils.CONSTANT_N);
						openingBalance.setLicence(Session.licence);
						if(nonGSTGoldBalanceId>0) {
							openingBalance.setOpeningBalanceDetailsContactId(nonGSTGoldBalanceId);
						}
						openingBalanceDetailsList.add(openingBalance);
					}
					if(!nonGSTSilverBalance.getText().equals("")) {
						OpeningBalanceDetailsContact openingBalance = new OpeningBalanceDetailsContact();
						openingBalance.setGrams(Double.parseDouble(nonGSTSilverBalance.getText()));
						openingBalance.setMetal(MasterController.getInstace().getMetal(ConstantUtils.SILVER));
						openingBalance.setBillType(MasterController.getInstace().getBillType(ConstantUtils.NON_GST_ENTRY));
						openingBalance.setDeletedFlag(ConstantUtils.CONSTANT_N);
						openingBalance.setLicence(Session.licence);
						if(nonGSTSilverBalanceId>0) {
							openingBalance.setOpeningBalanceDetailsContactId(nonGSTSilverBalanceId);
						}
						openingBalanceDetailsList.add(openingBalance);
					}
					contactObj.setOpeningBalanceDetailsList(openingBalanceDetailsList);
					//Opening Balance Save Ends
					
					
					Contact contactObjTemp = MasterController.getInstace().saveOrUpdateContact(contactObj);
					if(contactObjTemp!=null && contactObjTemp.getContactId()>0){
						//Transfer photo -starts
						try {
						transferImage(contactObjTemp.getContactId());
						}catch(Exception ex) {
							ex.printStackTrace();
						}
						//Transfer photo -ends
						
						JOptionPane.showMessageDialog(null, contactObjTemp.getContactName()+" - saved Successfully!!");
						Integer contactIdTemp = contactObjTemp.getContactId();
						
						if(calledFrom.equalsIgnoreCase(ConstantUtils.MENU)){
							AppController.getInstance().addContactFormPage(0, contactTypeName, ConstantUtils.MENU, Session.licence.getLicenceId());
						}else if(calledFrom.equalsIgnoreCase(ConstantUtils.CONTACT_REPORT)){
							AppController.getInstance().addContactReportPage(contactTypeName, ConstantUtils.MENU);
						}else if(calledFrom.equalsIgnoreCase(ConstantUtils.GOLD_RETAIL_GST)){
							AppController.getInstance().addBillingFormPage(ConstantUtils.MENU, 0, contactIdTemp, ConstantUtils.GST_ENTRY, ConstantUtils.GOLD, ConstantUtils.GOLD_RETAIL_GST);
						}else if(calledFrom.equalsIgnoreCase(ConstantUtils.SILVER_RETAIL_GST)){
							AppController.getInstance().addBillingFormPage(ConstantUtils.MENU, 0, contactIdTemp, ConstantUtils.GST_ENTRY, ConstantUtils.SILVER, ConstantUtils.SILVER_RETAIL_GST);
						}else if(calledFrom.equalsIgnoreCase(ConstantUtils.GOLD_RETAIL_NONGST)){
							AppController.getInstance().addBillingFormPage(ConstantUtils.MENU, 0, contactIdTemp, ConstantUtils.NON_GST_ENTRY, ConstantUtils.GOLD, ConstantUtils.GOLD_RETAIL_NONGST);
						}else if(calledFrom.equalsIgnoreCase(ConstantUtils.SILVER_RETAIL_NONGST)){
							AppController.getInstance().addBillingFormPage(ConstantUtils.MENU, 0, contactIdTemp, ConstantUtils.NON_GST_ENTRY, ConstantUtils.SILVER, ConstantUtils.SILVER_RETAIL_NONGST);
						}else if(calledFrom.equalsIgnoreCase(ConstantUtils.GOLD_WHOLESALE_GST)){
							AppController.getInstance().addBillingFormPage(ConstantUtils.MENU, 0, contactIdTemp, ConstantUtils.GST_ENTRY, ConstantUtils.GOLD, ConstantUtils.GOLD_WHOLESALE_GST);
						}else if(calledFrom.equalsIgnoreCase(ConstantUtils.SILVER_WHOLESALE_GST)){
							AppController.getInstance().addBillingFormPage(ConstantUtils.MENU, 0, contactIdTemp, ConstantUtils.GST_ENTRY, ConstantUtils.SILVER, ConstantUtils.SILVER_WHOLESALE_GST);
						}else if(calledFrom.equalsIgnoreCase(ConstantUtils.GOLD_WHOLESALE_NONGST)){
							AppController.getInstance().addBillingFormPage(ConstantUtils.MENU, 0, contactIdTemp, ConstantUtils.NON_GST_ENTRY, ConstantUtils.GOLD, ConstantUtils.GOLD_WHOLESALE_NONGST);
						}else if(calledFrom.equalsIgnoreCase(ConstantUtils.SILVER_WHOLESALE_NONGST)){
							AppController.getInstance().addBillingFormPage(ConstantUtils.MENU, 0, contactIdTemp, ConstantUtils.NON_GST_ENTRY, ConstantUtils.SILVER, ConstantUtils.SILVER_WHOLESALE_NONGST);
						}else if(calledFrom.equalsIgnoreCase(ConstantUtils.GOLD_SUPPLIER_GST)){
							AppController.getInstance().addBillingFormPage(ConstantUtils.MENU, 0, contactIdTemp, ConstantUtils.GST_ENTRY, ConstantUtils.GOLD, ConstantUtils.GOLD_SUPPLIER_GST);
						}else if(calledFrom.equalsIgnoreCase(ConstantUtils.SILVER_SUPPLIER_GST)){
							AppController.getInstance().addBillingFormPage(ConstantUtils.MENU, 0, contactIdTemp, ConstantUtils.GST_ENTRY, ConstantUtils.SILVER, ConstantUtils.SILVER_SUPPLIER_GST);
						}else if(calledFrom.equalsIgnoreCase(ConstantUtils.GOLD_SUPPLIER_NONGST)){
							AppController.getInstance().addBillingFormPage(ConstantUtils.MENU, 0, contactIdTemp, ConstantUtils.NON_GST_ENTRY, ConstantUtils.GOLD, ConstantUtils.GOLD_SUPPLIER_NONGST);
						}else if(calledFrom.equalsIgnoreCase(ConstantUtils.SILVER_SUPPLIER_NONGST)){
							AppController.getInstance().addBillingFormPage(ConstantUtils.MENU, 0, contactIdTemp, ConstantUtils.NON_GST_ENTRY, ConstantUtils.SILVER, ConstantUtils.SILVER_SUPPLIER_NONGST);
						}else if(calledFrom.equalsIgnoreCase(ConstantUtils.PAWN_RETAIL)){
							AppController.getInstance().addRetailPawnBillingFormPage(ConstantUtils.MENU, 0, contactIdTemp, ConstantUtils.PAWN_RETAIL);
						}else if(calledFrom.equalsIgnoreCase(ConstantUtils.RETAIL_BILLING_GST)) {
							AppController.getInstance().addBillingFormPage(ConstantUtils.MENU, 0, 0, ConstantUtils.GST_ENTRY, ConstantUtils.BLANK, ConstantUtils.RETAIL_BILLING_GST);
						}else if(calledFrom.equalsIgnoreCase(ConstantUtils.RETAIL_BILLING_NONGST)) {
							AppController.getInstance().addBillingFormPage(ConstantUtils.MENU, 0, 0, ConstantUtils.NON_GST_ENTRY, ConstantUtils.BLANK, ConstantUtils.RETAIL_BILLING_NONGST);
						}else if(calledFrom.equalsIgnoreCase(ConstantUtils.WHOLESALE_BILLING_GST)) {
							AppController.getInstance().addBillingFormPage(ConstantUtils.MENU, 0, 0, ConstantUtils.GST_ENTRY, ConstantUtils.BLANK, ConstantUtils.WHOLESALE_BILLING_GST);
						}else if(calledFrom.equalsIgnoreCase(ConstantUtils.WHOLESALE_BILLING_NONGST)) {
							AppController.getInstance().addBillingFormPage(ConstantUtils.MENU, 0, 0, ConstantUtils.NON_GST_ENTRY, ConstantUtils.BLANK, ConstantUtils.WHOLESALE_BILLING_NONGST);
						}else if(calledFrom.equalsIgnoreCase(ConstantUtils.PURCHASE_BILLING_GST)) {
							AppController.getInstance().addBillingFormPage(ConstantUtils.MENU, 0, 0, ConstantUtils.GST_ENTRY, ConstantUtils.BLANK, ConstantUtils.PURCHASE_BILLING_GST);
						}else if(calledFrom.equalsIgnoreCase(ConstantUtils.PURCHASE_BILLING_NONGST)) {
							AppController.getInstance().addBillingFormPage(ConstantUtils.MENU, 0, 0, ConstantUtils.NON_GST_ENTRY, ConstantUtils.BLANK, ConstantUtils.PURCHASE_BILLING_NONGST);
						}
					}
				}
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
	 
	 private static DefaultComboBoxModel getStateSuggestedModel(List<State> list, String text) {
	        DefaultComboBoxModel m = new DefaultComboBoxModel();
	        for(State s: list) {
	            if(s.getStateName().toUpperCase().startsWith(text.toUpperCase())) m.addElement(s);
	        }
	        return m;
	 }
	 private static DefaultComboBoxModel getCitySuggestedModel(List<City> list, String text) {
	        DefaultComboBoxModel m = new DefaultComboBoxModel();
	        for(City s: list) {
	            if(s.getCityName().toUpperCase().startsWith(text.toUpperCase())) m.addElement(s);
	        }
	        return m;
	 }
	 private static DefaultComboBoxModel getAreaSuggestedModel(List<Area> list, String text) {
	        DefaultComboBoxModel m = new DefaultComboBoxModel();
	        for(Area s: list) {
	            if(s.getAreaName().toUpperCase().startsWith(text.toUpperCase())) m.addElement(s);
	        }
	        return m;
	 }
	 
	 private static DefaultComboBoxModel getStreetSuggestedModel(List<StreetDTO> list, String text) {
	        DefaultComboBoxModel m = new DefaultComboBoxModel();
	        for(StreetDTO s: list) {
	            if(s.getStreetName().toUpperCase().startsWith(text.toUpperCase())) m.addElement(s);
	        }
	        return m;
	 }
	 
	 private void stateKeyTypedEvent(KeyEvent evt){
		 EventQueue.invokeLater(new Runnable() {
			 public void run() {
				 String text = tfState.getText();
				 if(text.length()==0) {
					 state.hidePopup();
					 setModel(state,tfState,new DefaultComboBoxModel(new Vector<>(stateList)), "");
				 }else{
					 DefaultComboBoxModel m = getStateSuggestedModel(stateList, text);
					 if(m.getSize()==0 || tfState_hide_flag) {
                         state.hidePopup();
                         tfState_hide_flag = false;
					 }else{
                       setModel(state,tfState,m, text);
                       state.showPopup();
					 }
				 }
			 }
		 });
		 
	 }
	 
	 private void cityKeyTypedEvent(KeyEvent evt){
		 EventQueue.invokeLater(new Runnable() {
			 public void run() {
				 String text = tfCity.getText();
				 if(text.length()==0) {
					 city.hidePopup();
					 setModel(city,tfCity,new DefaultComboBoxModel(new Vector<>(cityList)), "");
				 }else{
					 DefaultComboBoxModel m = getCitySuggestedModel(cityList, text);
					 if(m.getSize()==0 || tfCity_hide_flag) {
                         city.hidePopup();
                         tfCity_hide_flag = false;
					 }else{
                       setModel(city,tfCity,m, text);
                       city.showPopup();
					 }
				 }
			 }
		 });
		 
	 }
	 
	 private void areaKeyTypedEvent(KeyEvent evt){
		 EventQueue.invokeLater(new Runnable() {
			 public void run() {
				 String text = tfArea.getText();
				 if(text.length()==0) {
					 area.hidePopup();
					 setModel(area,tfArea,new DefaultComboBoxModel(new Vector<>(areaList)), "");
				 }else{
					 DefaultComboBoxModel m = getAreaSuggestedModel(areaList, text);
					 if(m.getSize()==0 || tfArea_hide_flag) {
                         area.hidePopup();
                         tfArea_hide_flag = false;
					 }else{
                       setModel(area,tfArea,m, text);
                       area.showPopup();
					 }
				 }
			 }
		 });
		 
	 }
	 
	 private void streetKeyTypedEvent(KeyEvent evt){
		 EventQueue.invokeLater(new Runnable() {
			 public void run() {
				 String text = tfStreet.getText();
				 if(text.length()==0) {
					 street.hidePopup();
					 setModel(street,tfStreet,new DefaultComboBoxModel(new Vector<>(streetDTOList)), "");
				 }else{
					 DefaultComboBoxModel m = getStreetSuggestedModel(streetDTOList, text);
					 if(m.getSize()==0 || tfStreet_hide_flag) {
                         street.hidePopup();
                         tfStreet_hide_flag = false;
					 }else{
                       setModel(street,tfStreet,m, text);
                       street.showPopup();
					 }
				 }
			 }
		 });
		 
	 }

	private void stateKeyPressedEvent(KeyEvent evt){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				String text = tfState.getText();
		        int code = evt.getKeyCode();
		        if(code==KeyEvent.VK_ENTER) {
		        	if(!text.equalsIgnoreCase(ConstantUtils.BLANK)){
		        		boolean createStateFlag=true;
		        		for(State stateobj:stateList){
		        			if(createStateFlag && stateobj.getStateName().toUpperCase().equalsIgnoreCase(text.toUpperCase())){
		        				createStateFlag=false;
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
		        	tfState_hide_flag=true;
		        	((Component) evt.getSource()).transferFocus();
		        }else if(code==KeyEvent.VK_ESCAPE) {
		        	tfState_hide_flag=true;
		        }else if(code==KeyEvent.VK_RIGHT) {
		        	for(State obj:stateList) {
		                String str = obj.getStateName();
		                if(str.toUpperCase().startsWith(text.toUpperCase())) {
		                    state.setSelectedIndex(-1);
		                    tfState.setText(str);
		                    return;
		                }
		            }
		        }
			}
		});
	}
	
	private void cityKeyPressedEvent(KeyEvent evt){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				String text = tfCity.getText();
		        int code = evt.getKeyCode();
		        if(code==KeyEvent.VK_ENTER) {
		        	if(!text.equalsIgnoreCase(ConstantUtils.BLANK)){
		        		boolean isCityAvailable=false;
		        		for(City cityobj:cityList){
		        			if(!isCityAvailable && cityobj.getCityName().toUpperCase().equalsIgnoreCase(text.toUpperCase())){
		        				isCityAvailable=true;
		        			}
		        		}
			        	if(isCityAvailable) {
			        		City selectedCity = (City)city.getSelectedItem();
			        		int counter=-1;
			        		int setStateId=-1;
			        		for(State obj:stateList){
			        			counter++;
			        			if(obj.getStateId().equals(selectedCity.getState().getStateId())){
			        				setStateId = counter;
			        			}
			        		}
			        		if(setStateId>-1){
			        			state.setSelectedIndex(setStateId);
			        		}
			        	}else{
			        		setModel(state,tfState,new DefaultComboBoxModel(new Vector<>(stateList)), "");
			        	}
		        	}else{
		        		setModel(state,tfState,new DefaultComboBoxModel(new Vector<>(stateList)), "");
		        	}
		        	tfCity_hide_flag=true;
		        	((Component) evt.getSource()).transferFocus();
		        }else if(code==KeyEvent.VK_ESCAPE) {
		        	tfCity_hide_flag=true;
		        }else if(code==KeyEvent.VK_RIGHT) {
		        	for(City obj:cityList) {
		                String str = obj.getCityName();
		                if(str.toUpperCase().startsWith(text.toUpperCase())) {
		                    city.setSelectedIndex(-1);
		                    tfCity.setText(str);
		                    return;
		                }
		            }
		        }
			}
		});
	}
	
	private void areaKeyPressedEvent(KeyEvent evt){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				String text = tfArea.getText();
		        int code = evt.getKeyCode();
		        if(code==KeyEvent.VK_ENTER) {
		        	if(!text.equalsIgnoreCase(ConstantUtils.BLANK)){
		        		boolean isAreaAvailable=false;
		        		for(Area areaobj:areaList){
		        			if(!isAreaAvailable && areaobj.getAreaName().toUpperCase().equalsIgnoreCase(text.toUpperCase())){
		        				isAreaAvailable=true;
		        			}
		        		}
			        	if(isAreaAvailable) {
			        		Area selectedArea = (Area)area.getSelectedItem();
			        		int counter=-1;
			        		int setId=-1;
			        		pincode.setText(selectedArea.getPincode());
			        		for(City obj:cityList){
			        			counter++;
			        			if(obj.getCityId().equals(selectedArea.getCity().getCityId())){
			        				setId=counter;
			        			}
			        		}
			        		if(setId>-1){
			        			city.setSelectedIndex(setId);
			        		}
			        		
			        		counter=-1;
			        		setId=-1;
			        		City selectedCity = (City)city.getSelectedItem();
			        		for(State obj:stateList){
			        			counter++;
			        			if(obj.getStateId().equals(selectedCity.getState().getStateId())){
			        				setId = counter;
			        			}
			        		}
			        		if(setId>-1){
			        			state.setSelectedIndex(setId);
			        		}
			        	}else{
			        		setModel(state,tfState,new DefaultComboBoxModel(new Vector<>(stateList)), "");
			        		setModel(city,tfCity,new DefaultComboBoxModel(new Vector<>(cityList)), "");
			        	}
		        	}else{
		        		setModel(state,tfState,new DefaultComboBoxModel(new Vector<>(stateList)), "");
		        		setModel(city,tfCity,new DefaultComboBoxModel(new Vector<>(cityList)), "");
		        	}
		        	tfArea_hide_flag=true;
		        	((Component) evt.getSource()).transferFocus();
		        }else if(code==KeyEvent.VK_ESCAPE) {
		        	tfArea_hide_flag=true;
		        }else if(code==KeyEvent.VK_RIGHT) {
		        	for(Area obj:areaList) {
		                String str = obj.getAreaName();
		                if(str.toUpperCase().startsWith(text.toUpperCase())) {
		                    area.setSelectedIndex(-1);
		                    tfArea.setText(str);
		                    return;
		                }
		            }
		        }
			}
		});
	}
	
	
	private void streetKeyPressedEvent(KeyEvent evt){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				String text = tfStreet.getText();
		        int code = evt.getKeyCode();
		        if(code==KeyEvent.VK_ENTER) {
		        	if(!text.equalsIgnoreCase(ConstantUtils.BLANK)){
		        		boolean isStreetAvailable=false;
		        		for(StreetDTO streetobj:streetDTOList){
		        			if(!isStreetAvailable && streetobj.toString().toUpperCase().equalsIgnoreCase(text.toUpperCase())){
		        				isStreetAvailable=true;
		        			}
		        		}
			        	if(isStreetAvailable) {
			        		StreetDTO selectedStreet = (StreetDTO)street.getSelectedItem();
			        		int counter=-1;
			        		int setId=-1;
			        		Area selectedArea = null;
			        		for(Area obj:areaList){
			        			counter++;
			        			if(obj.getAreaId().equals(selectedStreet.getArea().getAreaId())){
			        				setId = counter;
			        				selectedArea = obj;
			        			}
			        		}
			        		if(setId>-1){
			        			area.setSelectedIndex(setId);
			        			pincode.setText(selectedArea.getPincode());
			        		}
			        		counter=-1;
			        		setId=-1;			        		
			        		for(City obj:cityList){
			        			counter++;
			        			if(obj.getCityId().equals(selectedArea.getCity().getCityId())){
			        				setId=counter;
			        			}
			        		}
			        		if(setId>-1){
			        			city.setSelectedIndex(setId);
			        		}
			        		
			        		counter=-1;
			        		setId=-1;
			        		City selectedCity = (City)city.getSelectedItem();
			        		for(State obj:stateList){
			        			counter++;
			        			if(obj.getStateId().equals(selectedCity.getState().getStateId())){
			        				setId = counter;
			        			}
			        		}
			        		if(setId>-1){
			        			state.setSelectedIndex(setId);
			        		}
			        	}else{
			        		setModel(state,tfState,new DefaultComboBoxModel(new Vector<>(stateList)), "");
			        		setModel(city,tfCity,new DefaultComboBoxModel(new Vector<>(cityList)), "");
			        		setModel(area,tfArea,new DefaultComboBoxModel(new Vector<>(areaList)), "");
			        	}
		        	}else{
		        		setModel(state,tfState,new DefaultComboBoxModel(new Vector<>(stateList)), "");
		        		setModel(city,tfCity,new DefaultComboBoxModel(new Vector<>(cityList)), "");
		        		setModel(area,tfArea,new DefaultComboBoxModel(new Vector<>(areaList)), "");
		        	}
		        	tfStreet_hide_flag=true;
		        	((Component) evt.getSource()).transferFocus();
		        }else if(code==KeyEvent.VK_ESCAPE) {
		        	tfStreet_hide_flag=true;
		        }else if(code==KeyEvent.VK_RIGHT) {
		        	for(StreetDTO obj:streetDTOList) {
		                String str = obj.getStreetName();
		                if(str.toUpperCase().startsWith(text.toUpperCase())) {
		                    street.setSelectedIndex(-1);
		                    tfStreet.setText(str);
		                    return;
		                }
		            }
		        }
			}
		});
	}
	
	private State createState(String state){
		State obj = null;
		try{
			obj = new State();
			obj.setStateName(state);
			obj.setDeletedFlag(ConstantUtils.CONSTANT_N);
			Integer stateIdTemp=MasterController.getInstace().saveOrUpdateState(obj);
			if(stateIdTemp>0){
				obj = MasterController.getInstace().getState(stateIdTemp);
				System.out.println("State Created!!");
			}else{
				obj=null;
				System.out.println("State creation Failed!!");
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return obj;
	}
	
	private City createCity(State stateObj,String cityName){
		City obj=null;
		try{
			obj = new City();
			obj.setCityName(cityName);
			obj.setDeletedFlag(ConstantUtils.CONSTANT_N);
			obj.setState(stateObj);
			Integer cityIdTemp = MasterController.getInstace().saveOrUpdateCity(obj);
			if(cityIdTemp>0){
				obj = MasterController.getInstace().getCity(cityIdTemp);
				System.out.println("City created..");
			}else{
				obj = null;
				System.out.println("City creation failed..");
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return obj;				
				
	}
	
	private Area createArea(City cityObj,String areaName,String pincode){
		Area obj=null;
		try{
			obj = new Area();
			obj.setAreaName(areaName);
			obj.setCity(cityObj);
			obj.setDeletedFlag(ConstantUtils.CONSTANT_N);
			obj.setPincode(pincode);
			Integer areaIdTemp = MasterController.getInstace().saveOrUpdateArea(obj);
			if(areaIdTemp>0){
				obj = MasterController.getInstace().getArea(areaIdTemp);
				System.out.println("Area Created!!");
			}else{
				obj = null;
				System.out.println("Area Creation Failed..");
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return obj;				
				
	}
	
	private Street createStreet(Area areaObj,String streetName){
		Street obj=null;
		try{
			obj = new Street();
			
			obj.setArea(areaObj);
			obj.setDeletedFlag(ConstantUtils.CONSTANT_N);
			obj.setStreetName(streetName);
			Integer streetTempId = MasterController.getInstace().saveOrUpdateStreet(obj);
			if(streetTempId>0){
				obj = MasterController.getInstace().getStreet(streetTempId);
				System.out.println("Street Created..");
			}else{
				obj = null;
				System.out.println("Street Creation Failed..");
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return obj;				
				
	}
	
	private Integer getAddressStreetId(){
		Integer streetId=null;
		try{
			State stateObj = null;
			City cityObj = null;
			Area areaObj = null;
			Street streetObj = null;
			
			System.out.println("addressValidation");
			System.out.println("StreetId->"+street.getSelectedIndex());
			System.out.println("AreaId->"+area.getSelectedIndex());
			System.out.println("cityId->"+city.getSelectedIndex());
			System.out.println("State Id"+state.getSelectedIndex());
			boolean addressCheck=true;
			if(state.getSelectedIndex()==-1){
				//create state,city,area,street
				System.out.println("State to create"+tfState.getText());
				stateObj = createState(tfState.getText().trim());
				
				if(stateObj!=null){
					String cityName = tfCity.getText().trim();
					cityObj = createCity(stateObj,cityName);
					
					if(cityObj!=null){
						String areaName = tfArea.getText().trim();
						String pinCode = pincode.getText().trim();
						
						areaObj = createArea(cityObj, areaName, pinCode);
						
						if(areaObj!=null){
							String streetName="";
							if(street.getSelectedIndex()==-1){
								streetName = tfStreet.getText().trim();
							}else{
								streetName = ((StreetDTO)street.getSelectedItem()).getStreetName();
							}
							
							streetObj = createStreet(areaObj, streetName);
							if(streetObj!=null){
								streetId = streetObj.getStreetId();
								addressCheck=false;
							}
							
						}
					}
				}
			}
			
			if(city.getSelectedIndex()==-1 && addressCheck){
				//create city,area,street
				System.out.println("City to create"+tfCity.getText());
				
				if(stateObj==null){
					stateObj = (State)state.getSelectedItem();
				}
				
				if(stateObj!=null){
					String cityName = tfCity.getText().trim();
					cityObj = createCity(stateObj,cityName);
					
					if(cityObj!=null){
						String areaName = tfArea.getText().trim();
						String pinCode = pincode.getText().trim();
						
						areaObj = createArea(cityObj, areaName, pinCode);
						
						if(areaObj!=null){
							String streetName="";
							if(street.getSelectedIndex()==-1){
								streetName = tfStreet.getText().trim();
							}else{
								streetName = ((StreetDTO)street.getSelectedItem()).getStreetName();
							}
							
							streetObj = createStreet(areaObj, streetName);
							if(streetObj!=null){
								streetId = streetObj.getStreetId();
								addressCheck=false;
							}
							
						}
					}
				}
				
			}
			
			if(area.getSelectedIndex()==-1 && addressCheck){
				//create area,street
				System.out.println("Area to create"+tfArea.getText());
				if(cityObj==null){
					cityObj = (City)city.getSelectedItem();
				}
				
				if(cityObj!=null){
					String areaName = tfArea.getText().trim();
					String pinCode = pincode.getText().trim();
					
					areaObj = createArea(cityObj, areaName, pinCode);
					
					if(areaObj!=null){
						String streetName="";
						if(street.getSelectedIndex()==-1){
							streetName = tfStreet.getText().trim();
						}else{
							streetName = ((StreetDTO)street.getSelectedItem()).getStreetName();
						}
						
						streetObj = createStreet(areaObj, streetName);
						if(streetObj!=null){
							streetId = streetObj.getStreetId();
							addressCheck=false;
						}
						
					}
				}
				
			}
			if(street.getSelectedIndex()==-1 && addressCheck){
				//create street
				System.out.println("Street to create ->"+tfStreet.getText());
				
				if(areaObj==null){
					areaObj = (Area)area.getSelectedItem();
				}
				
				if(areaObj!=null){
					String streetName="";
					if(street.getSelectedIndex()==-1){
						streetName = tfStreet.getText().trim();
					}else{
						streetName = ((StreetDTO)street.getSelectedItem()).getStreetName();
					}
					
					streetObj = createStreet(areaObj, streetName);
					if(streetObj!=null){
						streetId = streetObj.getStreetId();
						addressCheck=false;
					}
					
				}
				
				
			}
			
			System.out.println("streetId ->"+streetId);
			if(streetId==null){
				System.out.println("streetId is already available->"+street.getSelectedIndex());
				StreetDTO streetDTOObj = (StreetDTO)street.getSelectedItem();
				System.out.println("->StreetId ->"+streetDTOObj.getStreetId());
				streetId = streetDTOObj.getStreetId();
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return streetId;
	}
	
	private boolean contactValidation(){
		boolean contactValidationFlag=true;
		StringBuffer errorMessage=new StringBuffer();
		try{
			//contactName,doorNo,mobile,email,gstNumber
			if(contactName.getText().trim().equals(ConstantUtils.BLANK)){
				errorMessage.append("Name,");
			}
			
			if(tfStreet.getText().trim().equals(ConstantUtils.BLANK)){
				errorMessage.append("Street,");
			}
			if(tfArea.getText().trim().equals(ConstantUtils.BLANK)){
				errorMessage.append("Area,");
			}
			if(tfCity.getText().trim().equals(ConstantUtils.BLANK)){
				errorMessage.append("City,");
			}
			if(pincode.getText().trim().equals(ConstantUtils.BLANK)){
				errorMessage.append("Pincode,");
			}
			if(tfState.getText().trim().equals(ConstantUtils.BLANK)){
				errorMessage.append("State,");
			}
			
			
			if(!errorMessage.toString().equals(ConstantUtils.BLANK)){
				contactValidationFlag=false;
				
				String errorMessageDisplay = "Please correct the following Validation: \n"+errorMessage.toString().replaceAll(",", "\n");
				JOptionPane.showMessageDialog(null, errorMessageDisplay);
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return contactValidationFlag;
	}

	private void loadContactForm(){
		try{
			loadContactObj = MasterController.getInstace().getContact(contactId);
			if(loadContactObj!=null){
				contactName.setText(loadContactObj.getContactName().trim());
				if(loadContactObj.getSecondName()!=null) {
					secondName.setText(loadContactObj.getSecondName().trim());
				}
				doorNo.setText(loadContactObj.getDoorNo());
				mobile.setText(loadContactObj.getMobile());
				gstNumber.setText(loadContactObj.getGstNumber());
				email.setText(loadContactObj.getEmail());
				
				
				aadharCard.setText(loadContactObj.getAadharCard());
				panCard.setText(loadContactObj.getPanCard());
				voterId.setText(loadContactObj.getVoterId());
				rationCard.setText(loadContactObj.getRationCard());
				
				if(loadContactObj.getStreet()!=null){
					int counter=-1;
					int setId=-1;
					
					for(StreetDTO obj:streetDTOList){
						counter++;
						if(obj.getStreetId().equals(loadContactObj.getStreet().getStreetId())){
							setId = counter;
						}
					}
					
					street.setSelectedIndex(setId);
					
					counter=-1;
					setId=-1;
					
					for(Area obj:areaList){
						counter++;
						if(obj.getAreaId().equals(loadContactObj.getStreet().getArea().getAreaId())){
							setId = counter;
						}
					}
					area.setSelectedIndex(setId);
					pincode.setText(loadContactObj.getStreet().getArea().getPincode());
					
					counter=-1;
					setId=-1;
					
					for(City obj:cityList){
						counter++;
						if(obj.getCityId().equals(loadContactObj.getStreet().getArea().getCity().getCityId())){
							setId = counter;
						}
					}
					city.setSelectedIndex(setId);
					counter=-1;
					setId=-1;
					for(State obj:stateList){
						counter++;
						if(obj.getStateId().equals(loadContactObj.getStreet().getArea().getCity().getState().getStateId())){
							setId = counter;
						}
					}
					state.setSelectedIndex(setId);
					
					//Opening Balance Starts
					if(loadContactObj.getOpeningBalanceDetailsList()!=null && loadContactObj.getOpeningBalanceDetailsList().size()>0) {
						for(OpeningBalanceDetailsContact balance : loadContactObj.getOpeningBalanceDetailsList()) {
							if(balance.getBillType().getBillTypeName().equalsIgnoreCase(ConstantUtils.GST_ENTRY)) {
								if(balance.getAmount()!=null) {
									gstCash.setText(balance.getAmount().toString());
									gstCashId = balance.getOpeningBalanceDetailsContactId();
								}
							}
							
							if(balance.getBillType().getBillTypeName().equalsIgnoreCase(ConstantUtils.NON_GST_ENTRY)) {
								if(balance.getAmount()!=null) {
									nonGSTCash.setText(balance.getAmount().toString());
									nonGSTCashId = balance.getOpeningBalanceDetailsContactId();
								}
								
								if(balance.getMetal()!=null && balance.getMetal().getMetalName().equalsIgnoreCase(ConstantUtils.GOLD)) {
									if(balance.getGrams()!=null) {
										nonGSTGoldBalance.setText(balance.getGrams().toString());
										nonGSTGoldBalanceId = balance.getOpeningBalanceDetailsContactId();
									}
								}
								
								if(balance.getMetal()!=null && balance.getMetal().getMetalName().equalsIgnoreCase(ConstantUtils.SILVER)) {
									if(balance.getGrams()!=null) {
										nonGSTSilverBalance.setText(balance.getGrams().toString());
										nonGSTSilverBalanceId = balance.getOpeningBalanceDetailsContactId();
									}
								}
							}
						}
					}
					//Opening Balance Ends
					
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
