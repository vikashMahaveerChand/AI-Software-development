package com.inventory.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.inventory.components.MyButton;
import com.inventory.components.MyPanel;
import com.inventory.controller.AppController;
import com.inventory.utility.ConstantUtils;
import com.inventory.utility.Session;

import net.miginfocom.swing.MigLayout;

public class HandLoanDashboard implements DocumentListener, ActionListener, KeyListener, MouseListener {

private static HandLoanDashboard myInstance = null;
	
	private HandLoanDashboard() {
	}

	public static HandLoanDashboard getInstance() {
		if (myInstance == null) {
			myInstance = new HandLoanDashboard();
		}

		return myInstance;
	}
	
	JPanel panel;
	JPanel centerPanel;
	MyButton handLoanCustomer,handLoanCustomerReport,handLoanCustomerAccount,handLoanEntry,handLoanPayment,handloanReport,handLoanDailyReport;
	
	public JPanel getPanel() {
		try {
			initComponent();
			constructHandLoanDashboard();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return panel;
	}
	
	private void initComponent(){
		try {
			panel = new JPanel(new MigLayout());
			centerPanel = new JPanel(new MigLayout());
			
			handLoanCustomer = new MyButton("Customer");
			handLoanCustomerReport = new MyButton("Customer Report");
			handLoanCustomerAccount = new MyButton("Customer Accounts");
			handLoanEntry = new MyButton("Loan Entry");
			handLoanPayment = new MyButton("Loan Payment");
			handloanReport = new MyButton("Loan Report");
			handLoanDailyReport = new MyButton("Daily Report");
			
			handLoanCustomer.setPreferredSize(new Dimension(200, 20));
			handLoanCustomerReport.setPreferredSize(new Dimension(200, 20));
			handLoanCustomerAccount.setPreferredSize(new Dimension(200, 20));
			handLoanEntry.setPreferredSize(new Dimension(200, 20));
			handLoanPayment.setPreferredSize(new Dimension(200, 20));
			handloanReport.setPreferredSize(new Dimension(200, 20));
			handLoanDailyReport.setPreferredSize(new Dimension(200, 20));
			
			handLoanCustomer.addActionListener(this);
			handLoanCustomerReport.addActionListener(this);
			handLoanCustomerAccount.addActionListener(this);
			handLoanEntry.addActionListener(this);
			handLoanPayment.addActionListener(this);
			handloanReport.addActionListener(this);
			handLoanDailyReport.addActionListener(this);
			
			handLoanCustomer.addKeyListener(this);
			handLoanCustomerReport.addKeyListener(this);
			handLoanCustomerAccount.addKeyListener(this);
			handLoanEntry.addKeyListener(this);
			handLoanPayment.addKeyListener(this);
			handloanReport.addKeyListener(this);
			handLoanDailyReport.addKeyListener(this);
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void constructHandLoanDashboard() {
		try {
			MyPanel.setBorder("Hand Loan Dashboard", panel);
			MyPanel.setBackground(centerPanel);
			
			centerPanel.add(handLoanCustomer,"wrap");
			centerPanel.add(handLoanCustomerReport,"wrap");
			centerPanel.add(handLoanCustomerAccount,"wrap");
			centerPanel.add(handLoanEntry,"wrap");
			centerPanel.add(handLoanPayment,"wrap");
			centerPanel.add(handloanReport,"wrap");
			centerPanel.add(handLoanDailyReport,"wrap");
			
			panel.add(centerPanel,"push, al center, wrap");
			
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
	public void keyPressed(KeyEvent evt) {
		// TODO Auto-generated method stub
		if(evt.getKeyCode()==KeyEvent.VK_DOWN) {
			((Component) evt.getSource()).transferFocus();
		}else if(evt.getKeyCode() == KeyEvent.VK_UP) {
			((Component) evt.getSource()).transferFocusBackward();
		}else if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
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
		try {
			//handLoanCustomer,handLoanCustomerReport,handLoanCustomerAccount,handLoanEntry,handLoanPayment,handloanReport,handLoanDailyReport
			if(evt == handLoanCustomer) {
				AppController.getInstance().addContactFormPage(0, ConstantUtils.CONTACT_TYPE_GENERIC, ConstantUtils.MENU, Session.licence.getLicenceId());
			}else if(evt == handLoanCustomerReport){
				AppController.getInstance().addContactReportPage(ConstantUtils.CONTACT_TYPE_GENERIC, ConstantUtils.MENU);
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}

}
