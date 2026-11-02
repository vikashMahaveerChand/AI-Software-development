package com.inventory.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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
import com.inventory.controller.PawnBillController;
import com.inventory.model.PawnBill;
import com.inventory.model.PawnBillAuctionDetails;
import com.inventory.model.PawnBillRedeemDetails;
import com.inventory.utility.Calculator;
import com.inventory.utility.ConstantUtils;
import com.inventory.utility.Session;
import com.inventory.utility.StringUtils;

import net.miginfocom.swing.MigLayout;

public class PawnAuctionForm implements DocumentListener, ActionListener, KeyListener, MouseListener {
	
private static PawnAuctionForm myInstance = null;
	
	private PawnAuctionForm() {
	}

	public static PawnAuctionForm getInstance() {
		if (myInstance == null) {
			myInstance = new PawnAuctionForm();
		}

		return myInstance;
	}
	JPanel panel;
	JPanel firstPanel;
	MyTextField pawnBillNumber,auctionNumber,receivedAmount;
	JXDatePicker auctionDate;
	MyLabel pledgeDate,totalNumberOfMonths,redeemInterest,redeemTotalAmount,rateOfInterest,customerDetails,pledgeAmount;
	MyTextField auctionAmountReceived,narration;
	MyButton save;
	
	String calledFrom;
	Integer billId;
	Integer customerId;
	String billSource;
	PawnBill pawnBillObj = null;
	PawnBillAuctionDetails pawnBillAuctionDetailsObj = null;
	
	public JPanel getPanel(String calledFrom,Integer billId,Integer customerId,String billSource){
		try {
		this.calledFrom = calledFrom;
		this.billId = billId;
		this.customerId = customerId;
		this.billSource = billSource;
		initComponent();
		constructAuctionForm();
		
		if(billId>0) {
			loadBill();
		}
		
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return panel;
	}
	
	private void initComponent(){
		try {
			panel = new JPanel(new MigLayout());
			firstPanel = new JPanel(new MigLayout());
			
			auctionDate = new JXDatePicker();
			auctionDate.setDate(Calendar.getInstance().getTime());
			//datePicker.setDate(date1);
			auctionDate.setFormats(new SimpleDateFormat("dd/MM/yyyy"));
			
			pawnBillNumber = new MyTextField(10);
			auctionNumber = new MyTextField(10);
			receivedAmount = new MyTextField(10);
			auctionAmountReceived = new MyTextField(10);
			narration = new MyTextField(10);
			
			pledgeDate = new MyLabel("");
			totalNumberOfMonths = new MyLabel("");
			redeemInterest = new MyLabel("");
			redeemTotalAmount = new MyLabel("");
			rateOfInterest = new MyLabel("");
			customerDetails = new MyLabel("");
			pledgeAmount = new MyLabel("");
			
			save = new MyButton("Redeem");
			pawnBillAuctionDetailsObj = new PawnBillAuctionDetails();
			
			pawnBillNumber.addActionListener(this);
			auctionNumber.addActionListener(this);
			receivedAmount.addActionListener(this);
			auctionAmountReceived.addActionListener(this);
			narration.addActionListener(this);
			save.addActionListener(this);
			auctionDate.addActionListener(this);
			
			pawnBillNumber.addKeyListener(this);
			auctionNumber.addKeyListener(this);
			receivedAmount.addKeyListener(this);
			auctionAmountReceived.addKeyListener(this);
			narration.addKeyListener(this);
			save.addKeyListener(this);
			auctionDate.addKeyListener(this);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void constructAuctionForm() {
		try {
			MyPanel.setBorder("Retail Auction Form", panel);
			MyPanel.setBackground(firstPanel, Color.white);
			
			firstPanel.add(new MyLabel("Bill Number:"));
			firstPanel.add(pawnBillNumber,"wrap");
			
			firstPanel.add(new MyLabel("Auction Number:"));
			firstPanel.add(auctionNumber,"wrap");
			
			firstPanel.add(new MyLabel("Customer Details:"));
			firstPanel.add(customerDetails,"wrap");
			
			firstPanel.add(new MyLabel("Pledge Date:"));
			firstPanel.add(pledgeDate,"wrap");
			
			firstPanel.add(new MyLabel("Pledge Amount:"));
			firstPanel.add(pledgeAmount,"wrap");
			
			firstPanel.add(new MyLabel("Rate Of Interest:"));
			firstPanel.add(rateOfInterest,"wrap");
			
			firstPanel.add(new MyLabel("Auction Date:"));
			firstPanel.add(auctionDate,"wrap");
			
			firstPanel.add(new MyLabel("# of Months:"));
			firstPanel.add(totalNumberOfMonths,"wrap");
			
			firstPanel.add(new MyLabel("Redeem Interest:"));
			firstPanel.add(redeemInterest,"wrap");
			
			firstPanel.add(new MyLabel("Redeem Amount:"));
			firstPanel.add(redeemTotalAmount,"wrap");
			
			firstPanel.add(new MyLabel("Auction Amount Received:"));
			firstPanel.add(auctionAmountReceived,"wrap");
			
			firstPanel.add(new MyLabel("Narration:"));
			firstPanel.add(narration,"wrap");
			
			//firstPanel.add(new MyLabel(""));
			firstPanel.add(save);
			
			panel.add(firstPanel);			
			
			if(billId==null || billId==0){
				//auctionNumber.setText(StringUtils.getBillNumber(billNumberObj.getBillLetter(),billNumberObj.getBillNumber()));
			}
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
		
		if(evt.getSource() == auctionDate) {
			if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
				auctionAmountReceived.transferFocus();
			}
		}else if(evt.getSource() == pawnBillNumber) {
			if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
				loadBillByNumber();
			}
		}else if(evt.getSource() == save) {
			if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
				saveAuctionDetails();
			}
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
	
	private void onClickEvent(Object evt){
		if(evt == auctionDate){
			//redeemDate.transferFocus();	
			//redeemAmountReceived.setCursor(new Cursor(1));
			calculateRedeemAmount();
			auctionAmountReceived.requestFocusInWindow();
		}else if(evt == save) {
			saveAuctionDetails();
		}
	}
	
	private void calculateRedeemAmount() {
		try {
			
			String pDateStr = pledgeDate.getText();
			String pAmountStr = pledgeAmount.getText();
			String pInterestStr = rateOfInterest.getText();
			Integer nMonth=0;
			Double interestAmount=0.0,redeemAmount=0.0,pledgeAmount=0.0,rateOfInterest=0.0;
			
			if(!pDateStr.equals("") && !pAmountStr.equals("") && !pInterestStr.equals("")) {
				
				nMonth = Calculator.monthDifference(new SimpleDateFormat("dd/MM/yyyy").parse(pledgeDate.getText()),auctionDate.getDate());
				totalNumberOfMonths.setText(Integer.toString(nMonth));
				pledgeAmount=pawnBillObj.getPledgeAmount();
				rateOfInterest=pawnBillObj.getRateOfInterest();
				
				if(nMonth>1) {
					//Double interestAmount=0.0,redeemAmount=0.0,
					
					interestAmount = ((pledgeAmount*(nMonth-1)*rateOfInterest)/100);
					interestAmount = Calculator.getTwoDigitDecimal(interestAmount);
					
					redeemAmount = pledgeAmount+interestAmount;
					redeemAmount = Calculator.getTwoDigitDecimal(redeemAmount);
					
					redeemInterest.setText(Calculator.formatTwoDecimal(interestAmount));
					redeemTotalAmount.setText(Calculator.formatTwoDecimal(redeemAmount));
				}else {
					redeemInterest.setText(Calculator.formatTwoDecimal(0.0));
					redeemTotalAmount.setText(Calculator.formatTwoDecimal(pledgeAmount));
				}
				
				pawnBillAuctionDetailsObj.setAuctionDate(auctionDate.getDate());
				pawnBillAuctionDetailsObj.setTotalNumberOfMonths(nMonth);
				pawnBillAuctionDetailsObj.setRedeemInterest(interestAmount);
				pawnBillAuctionDetailsObj.setRedeemTotalAmount(redeemAmount);
				
				
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void loadBillByNumber() {
		try {
			String billNumberText = pawnBillNumber.getText();
			
			pawnBillObj = PawnBillController.getInstace().getPawnBillByBillNumber(billNumberText, Session.licence.getLicenceId(), ConstantUtils.PAWN_RETAIL);
			
			setBillDetails(pawnBillObj);
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void loadBill() {
		try {
			pawnBillObj = PawnBillController.getInstace().getPawnBill(billId);
			if(pawnBillObj!=null){
				setBillDetails(pawnBillObj);
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void setBillDetails(PawnBill billObj) {
		try {
			if(billObj!=null) {
				pledgeDate.setText(StringUtils.getStrDate(billObj.getBillDate()));
				rateOfInterest.setText(billObj.getRateOfInterest().toString());
				customerDetails.setText(billObj.getContact().getContactName()+" "+billObj.getContact().getSecondName());
				pledgeAmount.setText(Calculator.formatTwoDecimal(billObj.getPledgeAmount()));
				//MyLabel pledgeDate,totalNumberOfMonths,redeemInterest,redeemTotalAmount,rateOfInterest,customerDetails,pledgeAmount;
				
				pawnBillAuctionDetailsObj.setPawnBill(billObj);
				//For Edit starts
				if(billObj.getPawnBillAuctionDetails()!=null) {
				//if(billObj.getPawnBillRedeemDetailsList()!=null && billObj.getPawnBillRedeemDetailsList().size()>0) {
					//PawnBillRedeemDetails pawnBillRedeemObjTemp = billObj.getPawnBillRedeemDetailsList().get(0);
					PawnBillAuctionDetails pawnBillAuctionObjTemp = billObj.getPawnBillAuctionDetails();
					
					if(pawnBillAuctionObjTemp!=null) {
						pawnBillNumber.setText(pawnBillAuctionObjTemp.getPawnBill().getBillNumber());
						auctionNumber.setText(pawnBillAuctionObjTemp.getAuctionNumber());
						auctionDate.setDate(pawnBillAuctionObjTemp.getAuctionDate());
						auctionAmountReceived.setText(pawnBillAuctionObjTemp.getAuctionAmountReceived().toString());
						narration.setText(pawnBillAuctionObjTemp.getNarration());
						totalNumberOfMonths.setText(pawnBillAuctionObjTemp.getTotalNumberOfMonths().toString());
						redeemInterest.setText(pawnBillAuctionObjTemp.getRedeemInterest().toString());
						redeemTotalAmount.setText(pawnBillAuctionObjTemp.getRedeemTotalAmount().toString());
						
						pawnBillAuctionDetailsObj.setPawnBillAuctionDetailsId(pawnBillAuctionObjTemp.getPawnBillAuctionDetailsId());
					}
				}
				//For Edit Ends
				
				
			}else {
				JOptionPane.showMessageDialog(null, "Invalid Bill Number!");
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void saveAuctionDetails() {
		try {
			pawnBillAuctionDetailsObj.setAuctionAmountReceived(Double.parseDouble(auctionAmountReceived.getText()));
			pawnBillAuctionDetailsObj.setNarration(narration.getText());
			pawnBillAuctionDetailsObj.setDeletedFlag(ConstantUtils.CONSTANT_N);
			pawnBillAuctionDetailsObj.setAuctionNumber(auctionNumber.getText());
			
			PawnBillAuctionDetails tempObj = PawnBillController.getInstace().saveOrUpdatePawnBillAuctionDetails(pawnBillAuctionDetailsObj);
			
			if(tempObj!=null) {
				JOptionPane.showMessageDialog(null, "Auction Success!!");
				
				if(calledFrom.equalsIgnoreCase(ConstantUtils.MENU)) {
					AppController.getInstance().addRetailPawnAuctionFormPage(ConstantUtils.MENU, 0, 0, ConstantUtils.PAWN_RETAIL_DELIVERY);
				}else if(calledFrom.equalsIgnoreCase(ConstantUtils.BILLING_REPORT)) {
					AppController.getInstance().addPawnBillingReportPage(ConstantUtils.MENU,ConstantUtils.PAWN_RETAIL);
				}else if(calledFrom.equalsIgnoreCase(ConstantUtils.PAWN_RETAIL_CUSTOMER_DETAILS)) {
					AppController.getInstance().addRetailPawnCustomerDetails(ConstantUtils.MENU, customerId, ConstantUtils.PAWN_RETAIL);
				}
				
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}

}
