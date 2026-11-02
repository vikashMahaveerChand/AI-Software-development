package com.inventory.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.inventory.components.MyButton;
import com.inventory.components.MyLabel;
import com.inventory.components.MyPanel;
import com.inventory.components.MyTextField;
import com.inventory.controller.AppController;
import com.inventory.controller.MasterController;
import com.inventory.model.OpeningStockDetailsLicence;
import com.inventory.model.Ornament;
import com.inventory.utility.ConstantUtils;
import com.inventory.utility.Session;

import net.miginfocom.swing.MigLayout;

public class OrnamentForm  implements DocumentListener, ActionListener, KeyListener, MouseListener{
	private static OrnamentForm myInstance = null;
	Ornament ornamentFormObj = null;

	private OrnamentForm() {
	}

	public static OrnamentForm getInstance() {
		if (myInstance == null) {
			myInstance = new OrnamentForm();
		}

		return myInstance;
	}

	JPanel panel;
	String calledFrom,metalCode;
	Integer ornamentId,licenceId;
	Integer gstStockId,nonGSTStockId;
	String userType;
	MyTextField ornamentGroup,ornamentSubGroup,ornamentName,ornamentKeyCode,gstPercentage,purity,mcRate,mcPerPiece,hsncode,gstStock,nonGSTStock;
	MyButton save;
	
	public JPanel getPanel(String metalCode,Integer ornamentId,String calledFrom,String userType,Integer licenceId){
		this.calledFrom = calledFrom;
		this.ornamentId = ornamentId;
		this.userType = userType;
		this.metalCode = metalCode;
		this.licenceId =licenceId;
		initComponent();
		constructOrnamentForm();
		if(ornamentId>0){
			loadOrnament();
		}
		return panel;
	}
	
	private void initComponent(){
		try{
			panel = new JPanel(new MigLayout());
			
			ornamentGroup = new MyTextField(10);
			ornamentSubGroup = new MyTextField(10);
			ornamentName = new MyTextField(10);
			ornamentKeyCode = new MyTextField(10);
			gstPercentage = new MyTextField(10);
			purity = new MyTextField(10);
			mcRate = new MyTextField(10);
			mcPerPiece = new MyTextField(10);
			hsncode = new MyTextField(10);
			gstStock = new MyTextField(10);
			nonGSTStock = new MyTextField(10);
			
			save = new MyButton("") ;
					
			ornamentGroup.addActionListener(this);
			ornamentSubGroup.addActionListener(this);
			ornamentName.addActionListener(this);
			ornamentKeyCode.addActionListener(this);
			gstPercentage.addActionListener(this);
			save.addActionListener(this);
			purity.addActionListener(this);
			mcRate.addActionListener(this);
			mcPerPiece.addActionListener(this);
			hsncode.addActionListener(this);
			gstStock.addActionListener(this);
			nonGSTStock.addActionListener(this);
			
			ornamentGroup.addKeyListener(this);
			ornamentSubGroup.addKeyListener(this);
			ornamentName.addKeyListener(this);
			ornamentKeyCode.addKeyListener(this);
			gstPercentage.addKeyListener(this);
			save.addKeyListener(this);
			purity.addKeyListener(this);
			mcRate.addKeyListener(this);
			mcPerPiece.addKeyListener(this);
			hsncode.addKeyListener(this);
			gstStock.addKeyListener(this);
			nonGSTStock.addKeyListener(this);
			
			gstStockId=-1;
			nonGSTStockId=-1;
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private void constructOrnamentForm(){
		try{
			MyPanel.setBorder("Ornament Form", panel);
			
			panel.add(new MyLabel("Ornament Name : "));
			panel.add(ornamentName, "wrap");
			
			panel.add(new MyLabel("Ornament Code : "));
			panel.add(ornamentKeyCode, "wrap");

			if(userType.equalsIgnoreCase(ConstantUtils.USER_TYPE_GST) || userType.equalsIgnoreCase(ConstantUtils.USER_TYPE_ADMIN)){
				panel.add(new MyLabel("GST Percentage : "));
				panel.add(gstPercentage, "wrap");
				
				panel.add(new MyLabel("HSN Code :"));
				panel.add(hsncode,"wrap");
				
				
			}
			if(userType.equalsIgnoreCase(ConstantUtils.USER_TYPE_NON_GST) || userType.equalsIgnoreCase(ConstantUtils.USER_TYPE_ADMIN)){
				panel.add(new MyLabel("Purity : "));
				panel.add(purity, "wrap");
				
				panel.add(new MyLabel("M.C. Rate : "));
				panel.add(mcRate, "wrap");
				
				panel.add(new MyLabel("M.C. Per Piece : "));
				panel.add(mcPerPiece, "wrap");
				
				
			}
			
			if(userType.equalsIgnoreCase(ConstantUtils.USER_TYPE_GST) || userType.equalsIgnoreCase(ConstantUtils.USER_TYPE_ADMIN)){
				panel.add(new MyLabel("GST Opening Stock :"));
				panel.add(gstStock,"wrap");
			}
			
			if(userType.equalsIgnoreCase(ConstantUtils.USER_TYPE_NON_GST) || userType.equalsIgnoreCase(ConstantUtils.USER_TYPE_ADMIN)){
				panel.add(new MyLabel("NON GST Opening Stock:"));
				panel.add(nonGSTStock,"wrap");
			}
			
			save.setText("Save");
			panel.add(save);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	private void loadOrnament() {
		// TODO Auto-generated method stub
		try{
			Ornament loadOrnamentObj = MasterController.getInstace().getOrnament(ornamentId);
			
			if(loadOrnamentObj!=null){
				//ornamentGroup,ornamentSubGroup,ornamentName,ornamentKeyCode,gstPercentage
				if(loadOrnamentObj.getOrnamentName()!=null){
					ornamentName.setText(loadOrnamentObj.getOrnamentName());
				}
				
				if(loadOrnamentObj.getOrnamentKeyCode()!=null){
					ornamentKeyCode.setText(loadOrnamentObj.getOrnamentKeyCode());
				}
				
				if(loadOrnamentObj.getGstPercentage()!=null){
					gstPercentage.setText(loadOrnamentObj.getGstPercentage().toString());
				}
				
				if(loadOrnamentObj.getPurity()!=null){
					purity.setText(loadOrnamentObj.getPurity().toString());
				}
				
				if(loadOrnamentObj.getMcRate()!=null){
					mcRate.setText(loadOrnamentObj.getMcRate().toString());
				}
				
				if(loadOrnamentObj.getMcPerPiece()!=null){
					mcPerPiece.setText(loadOrnamentObj.getMcPerPiece().toString());
				}
				
				if(loadOrnamentObj.getHsncode()!=null) {
					hsncode.setText(loadOrnamentObj.getHsncode());
				}
				
				if(loadOrnamentObj.getOpeningStockDetails()!=null && loadOrnamentObj.getOpeningStockDetails().size()>0) {
					for(OpeningStockDetailsLicence stock:loadOrnamentObj.getOpeningStockDetails()) {
						if(stock.getBillType().getBillTypeName().equals(ConstantUtils.GST_ENTRY)) {
							gstStock.setText(stock.getGrams().toString());
							gstStockId = stock.getOpeningStockDetailsLicenceId();
						}
						
						if(stock.getBillType().getBillTypeName().equals(ConstantUtils.NON_GST_ENTRY)) {
							nonGSTStock.setText(stock.getGrams().toString());
							nonGSTStockId=stock.getOpeningStockDetailsLicenceId();
						}
					}
				}
			}
		}catch(Exception ex){
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
		if(evt.getKeyCode() == KeyEvent.VK_ENTER){
			onClickEvent(evt.getSource());
		}
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
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
		try{
			//JOptionPane.showMessageDialog(null, "onclcik");
			if(evt == save){
				saveOrUpdateOrnament();
			}
			
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private void saveOrUpdateOrnament(){
		try{
			ornamentFormObj = new Ornament();
			
			if(ornamentId!=null && ornamentId>0){
				ornamentFormObj.setOrnamentId(ornamentId);
			}
			
			if(!metalCode.equalsIgnoreCase(ConstantUtils.BLANK)){
				ornamentFormObj.setMetalId(MasterController.getInstace().getMetal(metalCode));
			}
			
			ornamentFormObj.setOrnamentGroupId(null);
			ornamentFormObj.setOrnamentSubGroupId(null);
			
			if(!ornamentName.getText().trim().equalsIgnoreCase(ConstantUtils.BLANK)){
				ornamentFormObj.setOrnamentName(ornamentName.getText().trim());
			}
			
			if(!ornamentKeyCode.getText().trim().equalsIgnoreCase(ConstantUtils.BLANK)){
				ornamentFormObj.setOrnamentKeyCode(ornamentKeyCode.getText().trim());
			}
			
			if(!hsncode.getText().trim().equalsIgnoreCase(ConstantUtils.BLANK)) {
				ornamentFormObj.setHsncode(hsncode.getText().trim());
			}
			if(!gstPercentage.getText().trim().equalsIgnoreCase(ConstantUtils.BLANK)){
				try{
					ornamentFormObj.setGstPercentage(Double.parseDouble(gstPercentage.getText().trim()));
				}catch(Exception ex){
					ornamentFormObj.setGstPercentage(null);
				}
			}
			
			if(!purity.getText().trim().equalsIgnoreCase(ConstantUtils.BLANK)){
				try{
					ornamentFormObj.setPurity(Double.parseDouble(purity.getText().trim()));
				}catch(Exception ex){
					ornamentFormObj.setPurity(null);
				}
			}
			
			if(!mcRate.getText().trim().equalsIgnoreCase(ConstantUtils.BLANK)){
				try{
					ornamentFormObj.setMcRate(Double.parseDouble(mcRate.getText().trim()));
				}catch(Exception ex){
					ornamentFormObj.setMcRate(null);
				}
			}
			
			if(!mcPerPiece.getText().trim().equalsIgnoreCase(ConstantUtils.BLANK)){
				try{
					ornamentFormObj.setMcPerPiece(Double.parseDouble(mcPerPiece.getText().trim()));
				}catch(Exception ex){
					ornamentFormObj.setMcPerPiece(null);
				}
			}
			
			ornamentFormObj.setDeletedFlag(ConstantUtils.CONSTANT_N);
			ornamentFormObj.setLicenceId(licenceId);
			
			List<OpeningStockDetailsLicence> openingStockDetailsList = new ArrayList<OpeningStockDetailsLicence>();
			
			if(!gstStock.getText().equals(ConstantUtils.BLANK)) {
				OpeningStockDetailsLicence obj = new OpeningStockDetailsLicence();
				obj.setBillType(MasterController.getInstace().getBillType(ConstantUtils.GST_ENTRY));
				obj.setDeletedFlag(ConstantUtils.CONSTANT_N);
				obj.setGrams(Double.parseDouble(gstStock.getText()));
				obj.setLicence(Session.licence);
				if(gstStockId>0) {
					obj.setOpeningStockDetailsLicenceId(gstStockId);
				}
				openingStockDetailsList.add(obj);
			}
			
			if(!nonGSTStock.getText().equals(ConstantUtils.BLANK)) {
				OpeningStockDetailsLicence obj = new OpeningStockDetailsLicence();
				obj.setBillType(MasterController.getInstace().getBillType(ConstantUtils.NON_GST_ENTRY));
				obj.setDeletedFlag(ConstantUtils.CONSTANT_N);
				obj.setGrams(Double.parseDouble(nonGSTStock.getText()));
				obj.setLicence(Session.licence);
				if(nonGSTStockId>0) {
					obj.setOpeningStockDetailsLicenceId(nonGSTStockId);
				}
				openingStockDetailsList.add(obj);
			}
			
			ornamentFormObj.setOpeningStockDetails(openingStockDetailsList);
			
			if(ornamentFormObj!=null){
				ornamentId = MasterController.getInstace().saveOrUpdateOrnament(ornamentFormObj);
				
				if(ornamentId!=null && ornamentId>0){
					JOptionPane.showMessageDialog(null, "Ornament Saved!!");
					if(calledFrom.equalsIgnoreCase(ConstantUtils.MENU)){
						AppController.getInstance().addOrnamentFormPage(metalCode, 0, calledFrom, userType, licenceId);
					}else if(calledFrom.equalsIgnoreCase(ConstantUtils.ORNAMENT_REPORT)){
						AppController.getInstance().addOrnamentReportPage(userType,ConstantUtils.MENU);
					}
				}
				
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

}
