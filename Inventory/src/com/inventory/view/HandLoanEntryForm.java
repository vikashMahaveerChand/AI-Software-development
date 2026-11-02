package com.inventory.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.jdesktop.swingx.JXDatePicker;

import com.inventory.components.MyButton;
import com.inventory.components.MyTextField;
import com.inventory.model.Bill;
import com.inventory.model.Contact;

public class HandLoanEntryForm implements DocumentListener, ActionListener, KeyListener, MouseListener {

	private static HandLoanEntryForm myInstance = null;


	private HandLoanEntryForm() {
	}

	public static HandLoanEntryForm getInstance() {
		if (myInstance == null) {
			myInstance = new HandLoanEntryForm();
		}

		return myInstance;
	}
	
	JPanel panel;
	JPanel loanEntryPanel,customerPanel,loanDetailPanel;
	String calledFrom;
	Integer loanId,customerId;
	
	List<Contact> contactList = null;
	Bill billObj = null;
	JComboBox contact,inerestType;
	JXDatePicker billingDate;
	MyTextField loanNo,loanAmount,rateOfInterest;
	MyButton save,addContact;
	
	public JPanel getPanel(String calledFrom,Integer loanId,Integer customerId) {
		this.calledFrom = calledFrom;
		this.loanId = loanId;
		this.customerId = customerId;
		try {
			initComponent();
			constructHandLoanEntryForm();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return panel;
	}
	
	private void initComponent(){
		try {
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void constructHandLoanEntryForm() {
		try {
		}catch(Exception ex) {
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
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
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
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
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

}
