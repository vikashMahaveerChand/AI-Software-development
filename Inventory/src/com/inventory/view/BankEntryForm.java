package com.inventory.view;

import java.awt.Color;
import java.awt.Component;
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

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.jdesktop.swingx.JXDatePicker;

import com.inventory.components.MyButton;
import com.inventory.components.MyLabel;
import com.inventory.components.MyPanel;
import com.inventory.components.MyTextField;
import com.inventory.controller.AppController;
import com.inventory.controller.BankController;
import com.inventory.model.Bank;
import com.inventory.model.BankEntry;
import com.inventory.model.Contact;
import com.inventory.model.State;
import com.inventory.utility.Calculator;
import com.inventory.utility.ConstantUtils;

import net.miginfocom.swing.MigLayout;

public class BankEntryForm implements DocumentListener, ActionListener, KeyListener, MouseListener {
	
private static BankEntryForm myInstance = null;
	
	private BankEntryForm() {
	}

	public static BankEntryForm getInstance() {
		if (myInstance == null) {
			myInstance = new BankEntryForm();
		}

		return myInstance;
	}
	
	JPanel panel;
	JPanel entryPanel,debitFormPanel,creditFormPanel,accountDetailPanel,bankPanel;
	String calledFrom;
	Integer bankId;
	
	JComboBox bankAccount;
	
	JXDatePicker debitDate,creditDate;
	
	MyTextField debitAmount,creditAmount,debitNotes,creditNotes;
	
	MyButton submitDebitEntry,submitCreditEntry;
	
	List<Bank> bankList = null;
	
	public JPanel getPanel(String calledFrom,Integer bankId){
		this.calledFrom = calledFrom;
		this.bankId = bankId;
		System.out.println("Load bankId"+bankId);
		initComponent();
		constructBankEntryForm();
		if(bankId>0) {
			setBankAccountDetails(BankController.getInstace().getBank(bankId));
		}
		return panel;
	}
	
	public void initComponent() {
		try {
			panel = new JPanel(new MigLayout());
			entryPanel = new JPanel(new MigLayout());
			debitFormPanel = new JPanel(new MigLayout());
			creditFormPanel = new JPanel(new MigLayout());
			accountDetailPanel = new JPanel(new MigLayout());
			bankPanel = new JPanel(new MigLayout());
			
			debitAmount = new MyTextField(20);
			creditAmount = new MyTextField(20);
			debitNotes = new MyTextField(20);
			creditNotes = new MyTextField(20);
			
			bankList = BankController.getInstace().getBankList();
			bankAccount = new JComboBox();
			bankAccount.setSize(300, 10);
			
			int counter=0;
			int bankIndex=-1;
			for(Bank obj:bankList){
				bankAccount.addItem(obj);
				if(bankId!=null && bankId>0) {
					if(bankId.equals(obj.getBankId())){
						bankIndex = counter;		
						System.out.println(bankId +" "+counter+" "+obj.getBankName());
					}
				}
				counter++;
            }
			
			if(bankIndex>-1) {
				bankAccount.setSelectedIndex(bankIndex);
				
			}
			if(bankList!=null && bankList.size()==1) {
				bankId = bankList.get(0).getBankId();				
			}
			
			debitDate = new JXDatePicker();
			debitDate.setDate(Calendar.getInstance().getTime());
			debitDate.setFormats(new SimpleDateFormat("dd/MM/yyyy"));
			
			
			creditDate = new JXDatePicker();
			creditDate.setDate(Calendar.getInstance().getTime());
			creditDate.setFormats(new SimpleDateFormat("dd/MM/yyyy"));
			
			submitDebitEntry = new MyButton("Save Debit");
			submitCreditEntry = new MyButton("Save Credit");
			
			
			debitAmount.addKeyListener(this);
			creditAmount.addKeyListener(this);
			debitNotes.addKeyListener(this);
			creditNotes.addKeyListener(this);
			submitDebitEntry.addKeyListener(this);
			submitCreditEntry.addKeyListener(this);
			creditDate.addKeyListener(this);
			debitDate.addKeyListener(this);
			
			debitAmount.addActionListener(this);
			creditAmount.addActionListener(this);
			debitNotes.addActionListener(this);
			creditNotes.addActionListener(this);
			submitDebitEntry.addActionListener(this);
			submitCreditEntry.addActionListener(this);
			creditDate.addActionListener(this);
			debitDate.addActionListener(this);
			bankAccount.addActionListener(this);
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void constructBankEntryForm() {
		try {
			MyPanel.setBackground(panel, Color.white);
			
			MyPanel.setBackground(debitFormPanel, Color.white);
			MyPanel.setBackground(creditFormPanel, Color.white);
			MyPanel.setBackground(accountDetailPanel, Color.white);
			MyPanel.setBackground(bankPanel,Color.white);
			
			
			MyPanel.setBorder("Bank Entry", entryPanel);
			MyPanel.setBorder("Debit Entry", debitFormPanel);
			MyPanel.setBorder("Credit Entry", creditFormPanel);
			
			
			bankPanel.add(new MyLabel("Select Bank Account :"));
			bankPanel.add(bankAccount,"wrap");
			
			//Debit starts
			debitFormPanel.add(new MyLabel("Debit Date:"));
			debitFormPanel.add(debitDate,"wrap");
			
			debitFormPanel.add(new MyLabel("Amount:"));
			debitFormPanel.add(debitAmount,"wrap");
			
			debitFormPanel.add(new MyLabel("Notes:"));
			debitFormPanel.add(debitNotes,"wrap");
			
			debitFormPanel.add(submitDebitEntry,"wrap");
			//Debit Ends
			
			//Credit Starts
			creditFormPanel.add(new MyLabel("Credit Date:"));
			creditFormPanel.add(creditDate,"wrap");
			
			creditFormPanel.add(new MyLabel("Amount:"));
			creditFormPanel.add(creditAmount,"wrap");
			
			creditFormPanel.add(new MyLabel("Notes:"));
			creditFormPanel.add(creditNotes,"wrap");
			
			creditFormPanel.add(submitCreditEntry);
			//Credit Ends
			
			entryPanel.add(bankPanel,"wrap");
			entryPanel.add(accountDetailPanel,"wrap");
			entryPanel.add(debitFormPanel);
			entryPanel.add(creditFormPanel);
			
			panel.add(entryPanel);
			//debitFormPanel,creditFormPanel,accountDetailPanel
			
			//JComboBox bankAccount;
			
			//JXDatePicker debitDate,creditDate;
			
			//MyTextField debitAmount,creditAmount,debitNotes,creditNotes;
			
			//MyButton submitDebitEntry,submitCreditEntry;
			
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
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
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
		try {
			if(evt == debitDate){
				debitAmount.transferFocus();
				//((Component) evt).transferFocus();
			}else if(evt == creditDate){
				creditAmount.transferFocus();
				//((Component) evt).transferFocus();
			}else if(evt == submitDebitEntry) {
				saveDebitEntry();
			}else if(evt == submitCreditEntry) {
				saveCreditEntry();
			}else if(evt == bankAccount) {
				Bank bankIdObj = (Bank)bankAccount.getSelectedItem();
				
				setBankAccountDetails(BankController.getInstace().getBank(bankIdObj.getBankId()));
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void saveDebitEntry() {
		try {
			BankEntry debitEntry = new BankEntry();
			
			String validationMessage = validateBankEntry("DEBIT");
			
			if(validationMessage.equalsIgnoreCase(ConstantUtils.SUCCESS)) {
				debitEntry.setBank((Bank)bankAccount.getSelectedItem());
				debitEntry.setDebitAmount((Double.parseDouble(debitAmount.getText().toString())));
				debitEntry.setDeletedFlag(ConstantUtils.CONSTANT_N);
				debitEntry.setEntryDate(debitDate.getDate());
				debitEntry.setEntryNotes(debitNotes.getText().toString());
				
				BankEntry tempEntryObj = BankController.getInstace().saveOrUpdateBankEntry(debitEntry);
				if(tempEntryObj!=null && tempEntryObj.getBankEntryId()>0) {
					JOptionPane.showMessageDialog(null, "Debit Entry Saved!");
					if(calledFrom.equalsIgnoreCase(ConstantUtils.MENU)) {
						AppController.getInstance().addBankEntryForm(ConstantUtils.MENU, tempEntryObj.getBank().getBankId());
					}
				}
			}
			
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void saveCreditEntry() {
		try {
			BankEntry creditEntry = new BankEntry();
			
			String validationMessage = validateBankEntry("CREDIT");
			
			if(validationMessage.equalsIgnoreCase(ConstantUtils.SUCCESS)) {
				creditEntry.setBank((Bank)bankAccount.getSelectedItem());
				creditEntry.setCreditAmount((Double.parseDouble(creditAmount.getText().toString())));
				creditEntry.setDeletedFlag(ConstantUtils.CONSTANT_N);
				creditEntry.setEntryDate(creditDate.getDate());
				creditEntry.setEntryNotes(creditNotes.getText().toString());
				
				BankEntry tempEntryObj = BankController.getInstace().saveOrUpdateBankEntry(creditEntry);
				if(tempEntryObj!=null && tempEntryObj.getBankEntryId()>0) {
					JOptionPane.showMessageDialog(null, "Credit Entry Saved!");
					if(calledFrom.equalsIgnoreCase(ConstantUtils.MENU)) {
						AppController.getInstance().addBankEntryForm(ConstantUtils.MENU, tempEntryObj.getBank().getBankId());
					}
				}
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public String validateBankEntry(String calledFrom) {
		String message = "";
		try {
			
			StringBuffer errorMessage = new StringBuffer();
			Bank selectedBank = (Bank)bankAccount.getSelectedItem();
			
			if(selectedBank==null) {
				errorMessage.append("Bank,");
			}
			
			if(calledFrom.equalsIgnoreCase("DEBIT")) {
				Date debitDateSelected = debitDate.getDate();
				
				if(debitDateSelected==null) {
					errorMessage.append("Debit Date,");
				}
				
				if(debitAmount.getText().equalsIgnoreCase("")) {
					errorMessage.append("Debit Amount,");
				}
				
			}
			
			
			if(calledFrom.equalsIgnoreCase("CREDIT")) {
				Date creditDateSelected = creditDate.getDate();
				
				if(creditDateSelected==null) {
					errorMessage.append("Credit Date,");
				}
				
				if(creditAmount.getText().equalsIgnoreCase("")) {
					errorMessage.append("Credit Amount,");
				}
				
			}
			
			if(!errorMessage.toString().equals(ConstantUtils.BLANK)){
				message = "Please correct the following Validation: \n"+errorMessage.toString().replaceAll(",", "\n");
			}else {
				message = ConstantUtils.SUCCESS;
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return message;
	}
	
	public void setBankAccountDetails(Bank bank) {
		try {
			//accountDetailPanel
			accountDetailPanel.removeAll();
			
			accountDetailPanel.add(new MyLabel("Account Holder Name:"));accountDetailPanel.add(new MyLabel(bank.getAccountHolderName()),"wrap");
			accountDetailPanel.add(new MyLabel("Account Number:"));accountDetailPanel.add(new MyLabel(bank.getAccountNumber()),"wrap");
			accountDetailPanel.add(new MyLabel("Bank:"));accountDetailPanel.add(new MyLabel(bank.getBankName()),"wrap");
			accountDetailPanel.add(new MyLabel("Bank Branch:"));accountDetailPanel.add(new MyLabel(bank.getBankBranch()),"wrap");
			accountDetailPanel.add(new MyLabel("IFSC Code:"));accountDetailPanel.add(new MyLabel(bank.getIfscCode()),"wrap");
			accountDetailPanel.add(new MyLabel("Account Type:"));accountDetailPanel.add(new MyLabel(bank.getAccountType()),"wrap");
			
			if(bank.getBankEntryList()!=null) {
				System.out.println("Entry List Size"+bank.getBankEntryList().size());
			}
			accountDetailPanel.add(new MyLabel("Total Debit:"));accountDetailPanel.add(new MyLabel(Calculator.formatTwoDecimal(bank.getTotalDebitAmount())),"wrap");
			accountDetailPanel.add(new MyLabel("Total Credit:"));accountDetailPanel.add(new MyLabel(Calculator.formatTwoDecimal(bank.getTotalCreditAmount())),"wrap");
			accountDetailPanel.add(new MyLabel("Current Balance:"));accountDetailPanel.add(new MyLabel(Calculator.formatTwoDecimal(bank.getCurrentBalance())),"wrap");
			
			
			accountDetailPanel.revalidate();
			accountDetailPanel.repaint();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}

}
