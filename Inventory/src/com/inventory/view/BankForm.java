package com.inventory.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.inventory.components.MyButton;
import com.inventory.components.MyLabel;
import com.inventory.components.MyPanel;
import com.inventory.components.MyTextField;
import com.inventory.controller.AppController;
import com.inventory.controller.BankController;
import com.inventory.model.Bank;
import com.inventory.utility.ConstantUtils;

import net.miginfocom.swing.MigLayout;

public class BankForm implements DocumentListener, ActionListener, KeyListener, MouseListener {
	
	private static BankForm myInstance = null;
	
	private BankForm() {
	}

	public static BankForm getInstance() {
		if (myInstance == null) {
			myInstance = new BankForm();
		}

		return myInstance;
	}
	
	JPanel panel;
	JPanel bankFormPanel;
	String calledFrom;
	Integer bankId;
	
	MyTextField accountNumber,accountName,bankName,ifscCode,branchName,accountType;
	MyButton submit;
	
	
	public JPanel getPanel(String calledFrom,Integer bankId){
		this.calledFrom = calledFrom;
		this.bankId = bankId;
		
		initComponent();
		constructBankForm();
		return panel;
	}
	
	public void initComponent() {
		try {
			panel = new JPanel(new MigLayout());
			bankFormPanel = new JPanel(new MigLayout());
			
			accountNumber = new MyTextField(20);
			accountName = new MyTextField(20);
			bankName = new MyTextField(20);
			ifscCode = new MyTextField(20);
			branchName = new MyTextField(20);
			accountType = new MyTextField(20);
			
			submit = new MyButton("Save");
			
			submit.addKeyListener(this);
			
			submit.addActionListener(this);
			
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void constructBankForm() {
		try {
			MyPanel.setBackground(panel, Color.white);
			MyPanel.setBackground(bankFormPanel, Color.white);
			MyPanel.setBorder("Bank Account", bankFormPanel);
			
			
			bankFormPanel.add(new MyLabel("Account Number :"));
			bankFormPanel.add(accountNumber,"wrap");
			
			bankFormPanel.add(new MyLabel("Account Name :"));
			bankFormPanel.add(accountName,"wrap");
			
			bankFormPanel.add(new MyLabel("Bank Name :"));
			bankFormPanel.add(bankName,"wrap");
			
			bankFormPanel.add(new MyLabel("IFSC Code :"));
			bankFormPanel.add(ifscCode,"wrap");
			
			bankFormPanel.add(new MyLabel("Branch Name :"));
			bankFormPanel.add(branchName,"wrap");
			
			bankFormPanel.add(new MyLabel("Account Type :"));
			bankFormPanel.add(accountType,"wrap");
			
			bankFormPanel.add(submit,"wrap");
			
			//accountNumber,accountName,bankName,ifscCode,branchName,accountType
			
			panel.add(bankFormPanel,"center,pushx,growx,pushy,growy");
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
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
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
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
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
		onClickEvent(evt.getSource());
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void onClickEvent(Object evt) {
		if(evt == submit){
			saveOrUpdateBankDetails();
		}
	}
	
	public void saveOrUpdateBankDetails() {
		try {
			
			String validationMessage = validateBankFormData();
			
			if(validationMessage.equalsIgnoreCase(ConstantUtils.SUCCESS)) {
				Bank obj = new Bank();
				
				if(bankId!=null && bankId>0) {
					obj.setBankId(bankId);
				}
				
				obj.setAccountHolderName(accountName.getText());
				obj.setAccountNumber(accountNumber.getText());
				obj.setAccountType(accountType.getText());
				obj.setBankBranch(branchName.getText());
				obj.setBankName(bankName.getText());
				obj.setDeletedFlag(ConstantUtils.CONSTANT_N);
				obj.setIfscCode(ifscCode.getText());
				
				obj = BankController.getInstace().saveOrUpdateBank(obj);
				
				if(obj!=null && obj.getBankId()>0) {
					JOptionPane.showMessageDialog(null, "Bank Account Saved!!");
					
					if(calledFrom.equalsIgnoreCase(ConstantUtils.MENU)){
						AppController.getInstance().addBankForm(ConstantUtils.MENU, 0);
					}
				}
				
				//accountNumber,accountName,bankName,ifscCode,branchName,accountType
				
				
			}else {
				JOptionPane.showMessageDialog(null, validationMessage);
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public String validateBankFormData() {
		String message="";
		try {
			StringBuffer errorMessage = new StringBuffer();
			
			if(accountNumber.getText().equals(ConstantUtils.BLANK)) {
				errorMessage.append("Bank Account Number,");
			}
			
			if(accountName.getText().equals(ConstantUtils.BLANK)) {
				errorMessage.append("Bank Account Name,");
			}
			
			if(bankName.getText().equals(ConstantUtils.BLANK)) {
				errorMessage.append("Bank Name,");
			}
			
			if(ifscCode.getText().equals(ConstantUtils.BLANK)) {
				errorMessage.append("Bank IFSC Code,");
			}
			
			if(branchName.getText().equals(ConstantUtils.BLANK)) {
				errorMessage.append("Bank Branch,");
			}
			
			if(accountType.getText().equals(ConstantUtils.BLANK)) {
				errorMessage.append("Account Type,");
			}
			
			if(!errorMessage.toString().equals(ConstantUtils.BLANK)){
				message = "Please correct the following Validation: \n"+errorMessage.toString().replaceAll(",", "\n");
			}else {
				message = ConstantUtils.SUCCESS;
			}
			//accountNumber,accountName,bankName,ifscCode,branchName,accountType
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return message;
	}

}
