package com.inventory.view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

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
import com.inventory.controller.BillController;
import com.inventory.controller.MasterController;
import com.inventory.controller.PawnBillController;
import com.inventory.model.BillNumber;
import com.inventory.model.PawnBill;
import com.inventory.model.PawnBillRedeemDetails;
import com.inventory.utility.Calculator;
import com.inventory.utility.ConstantUtils;
import com.inventory.utility.Session;
import com.inventory.utility.StringUtils;

import net.miginfocom.swing.MigLayout;

public class PawnDeliveryForm implements DocumentListener, ActionListener, KeyListener, MouseListener {
	
	private static PawnDeliveryForm myInstance = null;
	
	private PawnDeliveryForm() {
	}

	public static PawnDeliveryForm getInstance() {
		if (myInstance == null) {
			myInstance = new PawnDeliveryForm();
		}

		return myInstance;
	}
	JPanel panel;
	JPanel firstPanel;
	MyTextField pawnBillNumber,deliveryNumber,receivedAmount;
	JXDatePicker redeemDate;
	MyLabel pledgeDate,totalNumberOfMonths,redeemInterest,redeemTotalAmount,rateOfInterest,customerDetails,pledgeAmount;
	MyTextField redeemAmountReceived,narration;
	MyButton save;
	
	String calledFrom;
	Integer billId;
	Integer customerId;
	String billSource;
	PawnBill pawnBillObj = null;
	BillNumber billNumberObj = null;
	PawnBillRedeemDetails pawnBillRedeemDetailsObj = null;
	public JPanel getPanel(String calledFrom,Integer billId,Integer customerId,String billSource){
		try {
		this.calledFrom = calledFrom;
		this.billId = billId;
		this.customerId = customerId;
		this.billSource = billSource;
		initComponent();
		constructDeliveryForm();
		
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
			
			redeemDate = new JXDatePicker();
			redeemDate.setDate(Calendar.getInstance().getTime());
			//datePicker.setDate(date1);
			redeemDate.setFormats(new SimpleDateFormat("dd/MM/yyyy"));
			
			pawnBillNumber = new MyTextField(10);
			deliveryNumber = new MyTextField(10);
			receivedAmount = new MyTextField(10);
			redeemAmountReceived = new MyTextField(10);
			narration = new MyTextField(10);
			
			pledgeDate = new MyLabel("");
			totalNumberOfMonths = new MyLabel("");
			redeemInterest = new MyLabel("");
			redeemTotalAmount = new MyLabel("");
			rateOfInterest = new MyLabel("");
			customerDetails = new MyLabel("");
			pledgeAmount = new MyLabel("");
			
			save = new MyButton("Redeem");
			
			billNumberObj = BillController.getInstace().getBillNumber(Session.licence.getLicenceId(), MasterController.getInstace().getBillSourceByCode(billSource).getBillSourceId());
			pawnBillRedeemDetailsObj = new PawnBillRedeemDetails();
			
			pawnBillNumber.addActionListener(this);
			deliveryNumber.addActionListener(this);
			receivedAmount.addActionListener(this);
			redeemAmountReceived.addActionListener(this);
			narration.addActionListener(this);
			save.addActionListener(this);
			redeemDate.addActionListener(this);
			
			pawnBillNumber.addKeyListener(this);
			deliveryNumber.addKeyListener(this);
			receivedAmount.addKeyListener(this);
			redeemAmountReceived.addKeyListener(this);
			narration.addKeyListener(this);
			save.addKeyListener(this);
			redeemDate.addKeyListener(this);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void constructDeliveryForm() {
		try {
			MyPanel.setBorder("Retail Pawn Delivery", panel);
			MyPanel.setBackground(firstPanel, Color.white);
			
			firstPanel.add(new MyLabel("Bill Number:"));
			firstPanel.add(pawnBillNumber,"wrap");
			
			firstPanel.add(new MyLabel("Delivery Number:"));
			firstPanel.add(deliveryNumber,"wrap");
			
			firstPanel.add(new MyLabel("Customer Details:"));
			firstPanel.add(customerDetails,"wrap");
			
			firstPanel.add(new MyLabel("Pledge Date:"));
			firstPanel.add(pledgeDate,"wrap");
			
			firstPanel.add(new MyLabel("Pledge Amount:"));
			firstPanel.add(pledgeAmount,"wrap");
			
			firstPanel.add(new MyLabel("Rate Of Interest:"));
			firstPanel.add(rateOfInterest,"wrap");
			
			firstPanel.add(new MyLabel("Redeem Date:"));
			firstPanel.add(redeemDate,"wrap");
			
			firstPanel.add(new MyLabel("# of Months:"));
			firstPanel.add(totalNumberOfMonths,"wrap");
			
			firstPanel.add(new MyLabel("Redeem Interest:"));
			firstPanel.add(redeemInterest,"wrap");
			
			firstPanel.add(new MyLabel("Redeem Amount:"));
			firstPanel.add(redeemTotalAmount,"wrap");
			
			firstPanel.add(new MyLabel("Amount Received:"));
			firstPanel.add(redeemAmountReceived,"wrap");
			
			firstPanel.add(new MyLabel("Narration:"));
			firstPanel.add(narration,"wrap");
			
			//firstPanel.add(new MyLabel(""));
			firstPanel.add(save);
			
			panel.add(firstPanel);			
			
			if(billId==null || billId==0){
				deliveryNumber.setText(StringUtils.getBillNumber(billNumberObj.getBillLetter(),billNumberObj.getBillNumber()));
			}
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
		if(evt.getSource() == redeemDate) {
			if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
				redeemAmountReceived.transferFocus();
			}
		}else if(evt.getSource() == pawnBillNumber) {
			if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
				loadBillByNumber();
			}
		}else if(evt.getSource() == save) {
			if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
				saveDeliveryDetails();
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
		if(evt == redeemDate){
			//redeemDate.transferFocus();	
			//redeemAmountReceived.setCursor(new Cursor(1));
			calculateRedeemAmount();
			redeemAmountReceived.requestFocusInWindow();
		}else if(evt == save) {
			saveDeliveryDetails();
		}
	}
	
	/*private void calculateDateDifference() {
		try {
			LocalDate dateBefore= LocalDate.parse("2016-03-25");
		    LocalDate dateAfter = LocalDate.parse("2016-04-25");
		    int months = Months.monthsBetween(dateBefore, dateAfter).getMonths();
		    //System.out.println("Difference"+months);
		    System.out.println("Difference"+(months+1));
		}catch(Exception ex) {
			
		}
	}*/
	
	
	private void calculateRedeemAmount() {
		try {
			
			String pDateStr = pledgeDate.getText();
			String pAmountStr = pledgeAmount.getText();
			String pInterestStr = rateOfInterest.getText();
			Integer nMonth=0;
			Double interestAmount=0.0,redeemAmount=0.0,pledgeAmount=0.0,rateOfInterest=0.0;
			
			if(!pDateStr.equals("") && !pAmountStr.equals("") && !pInterestStr.equals("")) {
				
				nMonth = Calculator.monthDifference(new SimpleDateFormat("dd/MM/yyyy").parse(pledgeDate.getText()),redeemDate.getDate());
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
					redeemAmount = pledgeAmount;
				}
				
				pawnBillRedeemDetailsObj.setRedeemDate(redeemDate.getDate());
				pawnBillRedeemDetailsObj.setTotalNumberOfMonths(nMonth);
				pawnBillRedeemDetailsObj.setRedeemInterest(interestAmount);
				pawnBillRedeemDetailsObj.setRedeemTotalAmount(redeemAmount);
				redeemAmountReceived.setText(Double.toString(redeemAmount));
				
				
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void calculateDateDifference8() {
		try {
			LocalDate pDate = convertToLocalDateViaInstant(new SimpleDateFormat("dd/MM/yyyy").parse(pledgeDate.getText()));
			LocalDate rDate = convertToLocalDateViaInstant(redeemDate.getDate());
			Period age = Period.between(pDate, rDate);
			
			
			int years = age.getYears(); 
			int months = age.getMonths(); 
			int days = age.getDays();
			System.out.println("number of years: " + years); 
			System.out.println("number of months: " + months);
			System.out.println("number of days: " + days);
			
			if(years>0) {
				months = months + (years*12);
			}
			
			if(days!=0) {
				months = months+1;
			}
			System.out.println("Updated number of months: " + months);
			totalNumberOfMonths.setText(Integer.toString(months));
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
	    return dateToConvert.toInstant()
	      .atZone(ZoneId.systemDefault())
	      .toLocalDate();
	}
	
	private void loadBillByNumber() {
		try {
			String billNumberText = pawnBillNumber.getText();
			
			pawnBillObj = PawnBillController.getInstace().getPawnBillByBillNumber(billNumberText, Session.licence.getLicenceId(), ConstantUtils.PAWN_RETAIL);
			
			if(pawnBillObj!=null && pawnBillObj.getPawnBillStatusId().getPawnBillStatus().equalsIgnoreCase(ConstantUtils.PAWN_BILL_STATUS_PLEDGE)) {
				setBillDetails(pawnBillObj);
			}else if(pawnBillObj!=null && pawnBillObj.getPawnBillStatusId().getPawnBillStatus().equalsIgnoreCase(ConstantUtils.PAWN_BILL_STATUS_REDEEM)){
				billId = pawnBillObj.getPawnBillId();
				loadBill();
			}else if(pawnBillObj!=null && pawnBillObj.getPawnBillStatusId().getPawnBillStatus().equalsIgnoreCase(ConstantUtils.PAWN_BILL_STATUS_AUCTION)){
				JOptionPane.showMessageDialog(null, "This Bill is auctioned on "+StringUtils.getStrDate(pawnBillObj.getPawnBillAuctionDetails().getAuctionDate()));
			}
			
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
				
				pawnBillRedeemDetailsObj.setPawnBill(billObj);
				//For Edit starts
				if(billObj.getPawnBillRedeemDetails()!=null) {
				//if(billObj.getPawnBillRedeemDetailsList()!=null && billObj.getPawnBillRedeemDetailsList().size()>0) {
					//PawnBillRedeemDetails pawnBillRedeemObjTemp = billObj.getPawnBillRedeemDetailsList().get(0);
					PawnBillRedeemDetails pawnBillRedeemObjTemp = billObj.getPawnBillRedeemDetails();
					
					if(pawnBillRedeemObjTemp!=null) {
						pawnBillNumber.setText(pawnBillRedeemObjTemp.getPawnBill().getBillNumber());
						deliveryNumber.setText(pawnBillRedeemObjTemp.getDeliveryNumber());
						redeemDate.setDate(pawnBillRedeemObjTemp.getRedeemDate());
						redeemAmountReceived.setText(pawnBillRedeemObjTemp.getRedeemAmountReceived().toString());
						narration.setText(pawnBillRedeemObjTemp.getNarration());
						totalNumberOfMonths.setText(pawnBillRedeemObjTemp.getTotalNumberOfMonths().toString());
						redeemInterest.setText(pawnBillRedeemObjTemp.getRedeemInterest().toString());
						redeemTotalAmount.setText(pawnBillRedeemObjTemp.getRedeemTotalAmount().toString());
						
						pawnBillRedeemDetailsObj.setPawnBillRedeemDetailsId(pawnBillRedeemObjTemp.getPawnBillRedeemDetailsId());
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
	
	private void saveDeliveryDetails() {
		try {
			pawnBillRedeemDetailsObj.setRedeemAmountReceived(Double.parseDouble(redeemAmountReceived.getText()));
			pawnBillRedeemDetailsObj.setNarration(narration.getText());
			pawnBillRedeemDetailsObj.setDeletedFlag(ConstantUtils.CONSTANT_N);
			pawnBillRedeemDetailsObj.setDeliveryNumber(deliveryNumber.getText());
			
			PawnBillRedeemDetails tempObj = PawnBillController.getInstace().saveOrUpdatePawnBillRedeemDetails(pawnBillRedeemDetailsObj);
			
			if(tempObj!=null) {
				JOptionPane.showMessageDialog(null, "Redeem Success!!");
				
				if(calledFrom.equalsIgnoreCase(ConstantUtils.MENU)) {
					AppController.getInstance().addRetailPawnDeliveryFormPage(ConstantUtils.MENU, 0, 0, ConstantUtils.PAWN_RETAIL_DELIVERY);
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
