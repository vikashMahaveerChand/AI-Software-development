package com.inventory.view;

import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXDatePicker;

import com.inventory.common.FilterJtable;
import com.inventory.components.EvenOddTableRenderer;
import com.inventory.components.MyButton;
import com.inventory.components.MyLabel;
import com.inventory.components.MyPanel;
import com.inventory.components.MyTextField;
import com.inventory.controller.BalanceSheetController;
import com.inventory.controller.MasterController;
import com.inventory.dto.BalanceSheetDTO;
import com.inventory.dto.BalanceSheetDTOList;
import com.inventory.dto.BillDTO;
import com.inventory.dto.CashReportDTOList;
import com.inventory.model.ContactType;
import com.inventory.model.Licence;
import com.inventory.utility.Calculator;
import com.inventory.utility.ConstantUtils;
import com.inventory.utility.Session;
import com.inventory.utility.StringUtils;

import net.miginfocom.swing.MigLayout;

public class BalanceSheet implements DocumentListener, ActionListener, KeyListener, MouseListener {
	
	private static BalanceSheet myInstance = null;
	
	private BalanceSheet() {
	}

	public static BalanceSheet getInstance() {
		if (myInstance == null) {
			myInstance = new BalanceSheet();
		}

		return myInstance;
	}
	JPanel panel;
	JPanel firstPanel,secondPanel,thirdPanel;
	MyTextField searchTextField = null;
	
	JTable reportTable=null;
    DefaultTableModel reportModel =null;
    JScrollPane scrollPane;
    
    String calledFrom;
	String billType;
	String contactType;
	String metal;
	String billSource;
	JXDatePicker fromDate,toDate;
	MyButton searchBill;
	Licence licenceObj = null;
	JComboBox contactTypeDropDown;
	List<ContactType> contactTypeList = null;
	
	public JPanel getPanel(String calledFrom,String billType,String contactType){
		try{
			this.calledFrom = calledFrom;
			this.billType = billType;
			this.contactType = contactType;
			
			initComponent();
			constructBalanceReport();
			
			FilterJtable.setFilterJtable(reportTable, searchTextField);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return panel;
	}
	
	private void initComponent(){
		try{
			licenceObj = Session.licence;
			panel = new JPanel(new MigLayout());
    		
    		firstPanel = new JPanel(new MigLayout());
			secondPanel = new JPanel(new MigLayout());
			thirdPanel = new JPanel(new MigLayout());
			
			searchTextField = new MyTextField(20);
			
			fromDate = new JXDatePicker();
			fromDate.setDate(Calendar.getInstance().getTime());
			fromDate.setFormats(new SimpleDateFormat("dd/MM/yyyy"));
			
			toDate = new JXDatePicker();
			toDate.setDate(Calendar.getInstance().getTime());
			toDate.setFormats(new SimpleDateFormat("dd/MM/yyyy"));
			
			searchBill = new MyButton("Search");
			
			
			contactTypeDropDown = new JComboBox();
			contactTypeDropDown.setSize(300, 10);
			
			
			contactTypeList = MasterController.getInstace().getContactTypeList();
			
			if(contactTypeList!=null && contactTypeList.size()>0){
				for(ContactType obj:contactTypeList){
					/*if(obj.getContactTypeName().equalsIgnoreCase(ConstantUtils.WHOLESALE_CONTACT_TYPE) ||
							obj.getContactTypeName().equalsIgnoreCase(ConstantUtils.SUPPLIER_CONTACT_TYPE)
							) {
					contactTypeDropDown.addItem(obj);
					}*/
					
					contactTypeDropDown.addItem(obj);
	            }
			}
			
			MyPanel.setBorder("Balance Report"+billType, panel);
			MyPanel.setBackground(firstPanel);
			MyPanel.setBackground(secondPanel);
			MyPanel.setBackground(thirdPanel);
			
			reportTable = new JTable();
			
			
			searchBill.addActionListener(this);
			contactTypeDropDown.addActionListener(this);
			
			contactTypeDropDown.addKeyListener(this);
            
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
	}
	
	private void constructBalanceReport(){
    	try{
    		MyPanel.setBorder("Balance Report "+billType+" "+contactType, panel);
    		
    		tableModelling(reportTable);
            
            reportTable.setBackground(Color.WHITE);
            scrollPane = new JScrollPane(reportTable);
            Dimension d = reportTable.getPreferredSize();
            int rows=30;
            scrollPane.setPreferredSize(
            new Dimension(d.width,reportTable.getRowHeight()*rows+1));
            secondPanel.add(scrollPane,"span,pushx,growx,wrap");
            
            EvenOddTableRenderer render =new EvenOddTableRenderer();
            //TableRendererForContact render = new TableRendererForContact();
            reportTable.setDefaultRenderer(Object.class,  render);
            reportTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            
            
            //firstPanel.add(new MyLabel("From Date:"));
            //firstPanel.add(fromDate);
            firstPanel.add(new MyLabel("To Date:"));
            firstPanel.add(toDate);
            
            //firstPanel.add(new MyLabel("Type : "));
			firstPanel.add(contactTypeDropDown);
            
            
            
            firstPanel.add(searchBill);
            firstPanel.add(new MyLabel("Search"),"push,al right");
            firstPanel.add(searchTextField,"al right, wrap");
            
            //firstPanel.add(new MyLabel("Search Bill"),"push,al right");
            //firstPanel.add(searchTextField,"al right, wrap");
            
            
            panel.add(firstPanel,"span,pushx,growx,wrap");
            panel.add(secondPanel,"span,pushx,growx,wrap");
            panel.add(thirdPanel,"span,pushx,growx,wrap");
    	}catch(Exception ex){
    		ex.printStackTrace();
    	}
    }
	
	private void tableModelling(JTable jTable){
		try{
			
			reportModel = new DefaultTableModel() {

				public Class<?> getColumnClass(int column) {
					switch (column) {
					
					case 0:
						return String.class;//contact Id
					case 1:
						return String.class;//Metal Name
					case 2:
						return String.class;//Customer Name
					case 3:
						return String.class;//Amount Pending
					case 4:
						return String.class;//Metal Pending					
					default:
						return String.class;
					}
				}
			};
			jTable.setModel(reportModel);
			reportModel.addColumn("contactId");
			reportModel.addColumn("metalName");
			reportModel.addColumn("Customer Name");
        	reportModel.addColumn("Amount Pending");
        	reportModel.addColumn("Metal Pending");
        	
        	if(licenceObj.getLicenceType().getLicenceTypeName().equalsIgnoreCase(ConstantUtils.LICENCE_TYPE_GOLD_WHOLESALE)) {
        		if(billType.equalsIgnoreCase(ConstantUtils.GST_ENTRY)) {
        			jTable.getColumn("Metal Pending").setPreferredWidth(0);
                	jTable.getColumn("Metal Pending").setMinWidth(0);
                	jTable.getColumn("Metal Pending").setMaxWidth(0);
            	}
        		
        	}else if(licenceObj.getLicenceType().getLicenceTypeName().equalsIgnoreCase(ConstantUtils.LICENCE_TYPE_SILVER_WHOLESALE)) {
        		if(billType.equalsIgnoreCase(ConstantUtils.GST_ENTRY)) {
        			jTable.getColumn("Metal Pending").setPreferredWidth(0);
                	jTable.getColumn("Metal Pending").setMinWidth(0);
                	jTable.getColumn("Metal Pending").setMaxWidth(0);
            	}
        	}
        	
        	jTable.getColumn("contactId").setPreferredWidth(0);
        	jTable.getColumn("contactId").setMinWidth(0);
        	jTable.getColumn("contactId").setMaxWidth(0);
        	
        	jTable.getColumn("metalName").setPreferredWidth(0);
        	jTable.getColumn("metalName").setMinWidth(0);
        	jTable.getColumn("metalName").setMaxWidth(0);
        	
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
	public void actionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
		onClickEvent(evt.getSource());
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	private void onClickEvent(Object evt){
		if(evt == searchBill) {
			fetchBalanceSheetReportList();
		}
	}
	
	private void fetchBalanceSheetReportList() {
		try {
			Date toDateDt = toDate.getDate();
			Integer licenceId = Session.licence.getLicenceId();
			Licence licenceObj = Session.licence;
			
			int rowCount = reportModel.getRowCount();
            for (int i = 0; i < rowCount; i++) {
            	reportModel.removeRow(0);
            }
			String contactTypeName ="";
			
			ContactType contactTypeObj = (ContactType)contactTypeDropDown.getSelectedItem();
			contactTypeName = contactTypeObj.getContactTypeName();
			
			
			if(licenceObj!=null && licenceObj.getLicenceType().getLicenceTypeName().equalsIgnoreCase(ConstantUtils.LICENCE_TYPE_GOLD_WHOLESALE)) {
				billSource = "";
				if(billType.equalsIgnoreCase(ConstantUtils.GST_ENTRY) && contactTypeName.equalsIgnoreCase(ConstantUtils.CONTACT_TYPE_WHOLESALE)) {
					billSource = ConstantUtils.GOLD_WHOLESALE_GST;
				}else if(billType.equalsIgnoreCase(ConstantUtils.NON_GST_ENTRY)  && contactTypeName.equalsIgnoreCase(ConstantUtils.CONTACT_TYPE_WHOLESALE)) {
					billSource = ConstantUtils.GOLD_WHOLESALE_NONGST;
				}else if(billType.equalsIgnoreCase(ConstantUtils.GST_ENTRY) && contactTypeName.equalsIgnoreCase(ConstantUtils.CONTACT_TYPE_SUPPLIER)) {
					billSource = ConstantUtils.GOLD_SUPPLIER_GST;
				}else if(billType.equalsIgnoreCase(ConstantUtils.NON_GST_ENTRY)  && contactTypeName.equalsIgnoreCase(ConstantUtils.CONTACT_TYPE_SUPPLIER)) {
					billSource = ConstantUtils.GOLD_SUPPLIER_NONGST;
				}
			}else if(licenceObj!=null && licenceObj.getLicenceType().getLicenceTypeName().equalsIgnoreCase(ConstantUtils.LICENCE_TYPE_SILVER_WHOLESALE)) {
				billSource = "";
				if(billType.equalsIgnoreCase(ConstantUtils.GST_ENTRY)) {
					billSource = ConstantUtils.SILVER_WHOLESALE_GST;
				}else if(billType.equalsIgnoreCase(ConstantUtils.NON_GST_ENTRY)) {
					billSource = ConstantUtils.SILVER_WHOLESALE_NONGST;
				}else if(billType.equalsIgnoreCase(ConstantUtils.GST_ENTRY) && contactTypeName.equalsIgnoreCase(ConstantUtils.CONTACT_TYPE_SUPPLIER)) {
					billSource = ConstantUtils.SILVER_SUPPLIER_GST;
				}else if(billType.equalsIgnoreCase(ConstantUtils.NON_GST_ENTRY)  && contactTypeName.equalsIgnoreCase(ConstantUtils.CONTACT_TYPE_SUPPLIER)) {
					billSource = ConstantUtils.SILVER_SUPPLIER_NONGST;
				}
			}
			BalanceSheetDTO balanceSheetDTO = BalanceSheetController.getInstace().getBalanceSheetList(toDateDt, licenceId, contactTypeName, billSource,billType,null);
			
            List<BalanceSheetDTOList> balanceSheetList = balanceSheetDTO.getBalanceSheetDTOList();
            
            if(balanceSheetList!=null && balanceSheetList.size()>0){
            	
            	for(BalanceSheetDTOList data:balanceSheetList){
            		
            		reportModel.addRow(new Object[]{
            				data.getContactId(),
            				data.getMetalName(),
            				data.getContactName(),
            				(data.getAmountBalance()!=null && data.getAmountBalance()>0) ? Calculator.formatTwoDecimal(data.getAmountBalance()):
            				(data.getAmountBalance()!=null && data.getAmountBalance()<0) ?"Extra "+Calculator.formatTwoDecimal(data.getAmountBalance()):"",
                    		/*(data.getMetalBalance()!=null && data.getMetalBalance()>0) ? Calculator.formatTwoDecimal(data.getMetalBalance()):
                    		(data.getMetalBalance()!=null && data.getMetalBalance()<0) ? "Extra "+Calculator.formatTwoDecimal(data.getMetalBalance()):""*/
            				data.getMetalBalanceStr()
            		});
            		
            		
            	}
            	
            }
            constructSummaryPanel(balanceSheetDTO);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void constructSummaryPanel(BalanceSheetDTO balanceDTO) {
		try {
			thirdPanel.removeAll();
			if(balanceDTO!=null) {
				thirdPanel.add(new MyLabel("Total Cash Balance :"));
				
				if(balanceDTO.getTotalAmountBalance()!=null) {
				thirdPanel.add(new MyLabel(Calculator.formatTwoDecimal(balanceDTO.getTotalAmountBalance())),"wrap");
				}else {
					thirdPanel.add(new MyLabel(""),"wrap");
				}
				
				thirdPanel.add(new MyLabel("Total Metal Balance :")); 
				/*if(balanceDTO.getTotalMetalBalance()!=null) {
					thirdPanel.add(new MyLabel(Calculator.formatTwoDecimal(balanceDTO.getTotalMetalBalance())),"wrap");
				}else {
					thirdPanel.add(new MyLabel(""),"wrap");
				}*/
				
				if(balanceDTO.getTotalMetalBalanceStr()!=null) {
					thirdPanel.add(new MyLabel(balanceDTO.getTotalMetalBalanceStr()),"wrap");
				}else {
					thirdPanel.add(new MyLabel(""),"wrap");
				}
				
			}
			thirdPanel.revalidate();
			thirdPanel.repaint();
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
	}

}
