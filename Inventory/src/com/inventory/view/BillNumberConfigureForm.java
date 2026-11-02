package com.inventory.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.inventory.components.MyButton;
import com.inventory.components.MyLabel;
import com.inventory.components.MyPanel;
import com.inventory.components.MyTextField;
import com.inventory.controller.AppController;
import com.inventory.controller.BillController;
import com.inventory.controller.MasterController;
import com.inventory.model.BillNumber;
import com.inventory.model.BillResetMaster;
import com.inventory.model.BillSource;
import com.inventory.model.DayMaster;
import com.inventory.model.MonthMaster;
import com.inventory.model.WeekMaster;
import com.inventory.utility.ConstantUtils;
import com.inventory.utility.Session;

import net.miginfocom.swing.MigLayout;

public class BillNumberConfigureForm implements DocumentListener, ActionListener, KeyListener, MouseListener{

private static BillNumberConfigureForm myInstance = null;
	
	private BillNumberConfigureForm() {
	}

	public static BillNumberConfigureForm getInstance() {
		if (myInstance == null) {
			myInstance = new BillNumberConfigureForm();
		}

		return myInstance;
	}
	
	JPanel panel;
	MyButton submit;
	MyTextField letter,number;
	JComboBox billSource,billReset;
	List<BillSource> billSourceList = null;
	List<BillResetMaster> billResetMasterList = null;
	List<DayMaster> dayMasterList = null;
	List<WeekMaster> weekMasterList = null;
	List<MonthMaster> monthMasterList = null;
	
	
	
	public JPanel getPanel(){
		initComponent();
		constructForm();
		return panel;
	}
	
	public void initComponent() {
		try {
			panel = new JPanel(new MigLayout());
			billSourceList = MasterController.getInstace().getBillSourceList();
			
			billResetMasterList = MasterController.getInstace().getBillResetMaster();
			dayMasterList = MasterController.getInstace().getDayMasterList();
			weekMasterList = MasterController.getInstace().getWeekMasterList();
			monthMasterList = MasterController.getInstace().getMonthMasterList();
			
			
			submit = new MyButton("save");
			letter = new MyTextField(10);
			number = new MyTextField(10);
			
			billSource = new JComboBox();
			billSource.setSize(300, 10);
			
			for(BillSource obj:billSourceList) {
				billSource.addItem(obj);
			}
			
			billReset = new JComboBox<>();
			billReset.setSize(300, 10);
			
			for(BillResetMaster obj:billResetMasterList) {
				billReset.addItem(obj);
			}
			
			submit.addKeyListener(this);
			
			submit.addActionListener(this);
			
			
			
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void constructForm() {
		try {
			MyPanel.setBackground(panel, Color.white);
			panel.add(new MyLabel("Letter :"));
			panel.add(letter,"wrap");
			
			panel.add(new MyLabel("Number :"));
			panel.add(number,"wrap");
			
			panel.add(new MyLabel("Type :"));
			panel.add(billSource,"wrap");
			
			panel.add(new MyLabel("Reset Type :"));
			panel.add(billReset,"wrap");
			
			panel.add(submit);
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void constructResetPanel() {
		try {
			
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
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == submit) {
			saveBillnumber();
		}
		
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
	
	private void saveBillnumber() {
		try {
			String letterStr = letter.getText().trim();
			String numberStr = number.getText().trim();
			boolean saveFlag= true;
			
			if(!letterStr.equals("")) {
			if(letterStr.equals("") || letterStr.length()!=1) {
				JOptionPane.showMessageDialog(null, "Letter should not blank and only one character");
				saveFlag = false;
			}else if(letterStr.length()==1) {
				char lt = letterStr.charAt(0);
				if( (lt >= 'a' && lt <= 'z') || (lt >= 'A' && lt <= 'Z')) {
					System.out.println("Fine");
				}else {
					JOptionPane.showMessageDialog(null, "Not a valid Character");
					saveFlag = false;
				}
			}
			}
			
			if(saveFlag) {
			if(numberStr.equals("")) {
				JOptionPane.showMessageDialog(null, "Number should not be blanks");
				saveFlag = false;
			}else {
				Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
				boolean numFlag = pattern.matcher(numberStr).matches();
				if(!numFlag) {
					JOptionPane.showMessageDialog(null, "Not a valid Number");
					saveFlag = false;
				}
				
			}
			}
			if(saveFlag) {
				BillSource billSourceSelected = (BillSource) billSource.getSelectedItem();
				BillNumber billNumberObj = BillController.getInstace().getBillNumber(Session.licence.getLicenceId(), billSourceSelected.getBillSourceId());
				
				if(billNumberObj!=null) {
					//Update
					billNumberObj.setBillLetter(letter.getText().trim());
					billNumberObj.setBillNumber(Integer.parseInt(number.getText().trim()));
					
					BillController.getInstace().saveOrUpdateBillNumber(billNumberObj);
				}else {
					//Save
					BillNumber billNumber = new BillNumber();
					billNumber.setBillLetter(letter.getText().trim());
					billNumber.setBillNumber(Integer.parseInt(number.getText().trim()));
					billNumber.setBillSource(billSourceSelected);
					billNumber.setDeletedFlag(ConstantUtils.CONSTANT_N);
					billNumber.setLicence(Session.licence);
					
					BillController.getInstace().saveOrUpdateBillNumber(billNumber);
				}
				
				JOptionPane.showMessageDialog(null, "Save!");
				AppController.getInstance().addBillNumberForm();
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	

}
