package com.inventory.view;

import java.awt.Color;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXDatePicker;

import com.inventory.common.FilterJtable;
import com.inventory.components.EvenOddTableRenderer;
import com.inventory.components.MyButton;
import com.inventory.components.MyLabel;
import com.inventory.components.MyPanel;
import com.inventory.components.MyTextField;
import com.inventory.controller.AppController;
import com.inventory.controller.BillController;
import com.inventory.controller.MasterController;
import com.inventory.dto.BankEntryDTO;
import com.inventory.dto.BillDTO;
import com.inventory.model.Bill;
import com.inventory.model.BillSource;
import com.inventory.model.Contact;
import com.inventory.reports.RunReport;
import com.inventory.utility.Calculator;
import com.inventory.utility.ConstantUtils;
import com.inventory.utility.Session;
import com.inventory.utility.StringUtils;

import net.miginfocom.swing.MigLayout;

public class BillingReport implements ActionListener{
	
	private static BillingReport myInstance=null;
	
	private BillingReport(){}
	
	public static BillingReport getInstance(){
		if(myInstance ==null){
			myInstance = new BillingReport();
		}
		
		return myInstance;
	}
	
	JPanel panel;
	JPanel firstPanel,secondPanel,thirdPanel;
	MyTextField searchTextField = null;
	
	JTable reportTable=null;
    DefaultTableModel reportModel =null;
    JScrollPane scrollPane;
    
    private JPopupMenu popupMenu;
    private JMenuItem editRecord,deleteRecord,enableDisableRecord,printRecord;
    String billSource,calledFrom,billType,metal;
    JDialog dialog = null;
    JXDatePicker fromDate,toDate;
    MyButton searchBill,printReport;

    public JPanel getPanel(String calledFrom,String billType,String metal,String billSource){
    	try{
    		this.billSource = billSource;
    		this.calledFrom = calledFrom;
    		this.billType = billType;
    		this.metal = metal;
    		initComponent();
    		if(calledFrom.equalsIgnoreCase(ConstantUtils.MENU)){
    			constructBillingReport();
    			//fetchBillingReportList(reportType);
    			fetchBillingReportList();
    		}
    		FilterJtable.setFilterJtable(reportTable, searchTextField);
    	}catch(Exception ex){
    		ex.printStackTrace();
    	}
    	return panel;
    }
    
    private void initComponent(){
    	try{
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
			printReport = new MyButton("Print Report");
			
			MyPanel.setBorder("Billing Reports", panel);
			MyPanel.setBackground(firstPanel);
			MyPanel.setBackground(secondPanel);
			MyPanel.setBackground(thirdPanel);
			
			reportTable = new JTable();
            
			dialog = new JDialog();
			Window parentWindow = SwingUtilities.getWindowAncestor(panel);
			dialog = new JDialog(parentWindow, ModalityType.APPLICATION_MODAL);
			
			popupMenu = new JPopupMenu();
            editRecord = new JMenuItem();
            deleteRecord = new JMenuItem();
            enableDisableRecord = new JMenuItem();
            printRecord = new JMenuItem();
            
            editRecord.addActionListener(this);
            deleteRecord.addActionListener(this);
            enableDisableRecord.addActionListener(this);
            searchBill.addActionListener(this);
            printRecord.addActionListener(this);
            printReport.addActionListener(this);
    	}catch(Exception ex){
    		ex.printStackTrace();
    	}
    }
    
    private void constructBillingReport(){
    	try{
    		MyPanel.setBorder("Billing Report"+billSource, panel);
    		
    		tableModelling(reportTable);
			editRecord.setText("Edit Bill");
			deleteRecord.setText("Delete Bill");
			enableDisableRecord.setText("Enable/Disable");
			printRecord.setText("Print Bill");
			
			popupMenu.add(editRecord);
			popupMenu.add(deleteRecord);
			popupMenu.add(printRecord);
			//popupMenu.add(enableDisableRecord);
			
			reportTable.setComponentPopupMenu(popupMenu);
            
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
            
            firstPanel.add(new MyLabel("From Date:"));
            firstPanel.add(fromDate);
            firstPanel.add(new MyLabel("To Date:"));
            firstPanel.add(toDate);
            firstPanel.add(searchBill);
            firstPanel.add(printReport);
            firstPanel.add(new MyLabel("Search Bill"),"push,al right");
            firstPanel.add(searchTextField,"al right, wrap");
            
            
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
						return String.class;//Bill Id
					case 1:
						return String.class;//Bill NO
					case 2:
						return String.class;//Bill Date
					case 3:
						return String.class;//Name
					case 4:
						return String.class;//Weight
					case 5:
						return String.class;//Amount
					default:
						return String.class;
					}
				}
			};
			jTable.setModel(reportModel);
			reportModel.addColumn("billId");
			reportModel.addColumn("Bill No");
        	reportModel.addColumn("Bill Date");
        	reportModel.addColumn("Name");
        	reportModel.addColumn("Weight");
        	reportModel.addColumn("Amount");

        	jTable.getColumn("billId").setPreferredWidth(0);
        	jTable.getColumn("billId").setMinWidth(0);
        	jTable.getColumn("billId").setMaxWidth(0);
        	
        	
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
    
    private void fetchBillingReportList(){
    	try{
    		String fromDateStr = StringUtils.getStrDate(fromDate.getDate());
    		String toDateStr = StringUtils.getStrDate(toDate.getDate());
    		System.out.println("fromDateStr->"+fromDateStr);
    		System.out.println("toDateStr->"+toDateStr);
    		
    		System.out.println("fromDate->"+fromDate.getDate());
    		System.out.println("toDate->"+toDate.getDate());
    		
    		Date fromDateDt = fromDate.getDate();
    		Date toDateDt = toDate.getDate();
    		BillSource billSourceObj = MasterController.getInstace().getBillSourceByCode(billSource);
    		
    		BillDTO billDTO = BillController.getInstace().getBillList(fromDateDt, toDateDt, Session.licence.getLicenceId(), billSourceObj.getBillSourceId(), null,billType);
    		List<Bill> billList = null;
    		
    		if(billDTO!=null) {
    			billList = billDTO.getBillList();
    		}
    		
    		int rowCount = reportModel.getRowCount();
            for (int i = 0; i < rowCount; i++) {
            	reportModel.removeRow(0);
            }
            //System.out.println("billList"+billList.size());
            if(billList!=null && billList.size()>0){
            	for(Bill data:billList){
            		reportModel.addRow(new Object[]{
            				data.getBillId(),
            				data.getBillNumber(),
            				StringUtils.getStrDate(data.getBillDate()),
            				data.getContact().getContactName(),
            				data.getWeight(),
            				data.getTotalAmount()
            		});
            	}
            }
            
            constructSummaryPanel(billDTO);
    		
    	}catch(Exception ex){
    		ex.printStackTrace();
    	}
    }
    
    private void printBillingReportList() {
    	try {
    		
    		Date fromDateDt = fromDate.getDate();
    		Date toDateDt = toDate.getDate();
    		BillSource billSourceObj = MasterController.getInstace().getBillSourceByCode(billSource);
    		
    		BillDTO billDTO = BillController.getInstace().getBillList(fromDateDt, toDateDt, Session.licence.getLicenceId(), billSourceObj.getBillSourceId(), null,billType);
    		List<Bill> billList = null;
    		
    		billDTO.setReportStartDate(StringUtils.getStrDate(fromDateDt));
    		billDTO.setReportEndDate(StringUtils.getStrDate(toDateDt));
    		billDTO.setReportName(billSource);
    		
    		if(billDTO!=null) {
    			billList = billDTO.getBillList(); 			
    			RunReport.printBillingReport(billDTO);
    		}
    		
    	}catch(Exception ex){
    		ex.printStackTrace();
    	}
    }
    
	@Override
	public void actionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
		onClickEvent(evt.getSource());
		
	}
	private void onClickEvent(Object evt){
		try{
			if(evt ==  editRecord || evt ==  deleteRecord || evt == enableDisableRecord || evt == printRecord){
				int selectedRow = reportTable.getSelectedRow();
				int billId=0;
				
				if(selectedRow>-1){
					String selectedId = reportModel.getValueAt(selectedRow, 0).toString();
					Integer billIdInCell = (Integer)reportTable.getValueAt(selectedRow, 0);
					billId = billIdInCell;
					
					if(evt ==  editRecord){
						AppController.getInstance().addBillingFormPage(ConstantUtils.BILLING_REPORT, billId, 0, billType, metal, billSource);
					}else if(evt == printRecord) {
						RunReport.printWholeSaleBill(billId,billSource,billType);
					}
				}
			}else if(evt==searchBill){
				fetchBillingReportList();
			}else if(evt == printReport) {
				printBillingReportList();
			}
		}catch(Exception ex){
    		ex.printStackTrace();
    	}
	}
	
	private void constructSummaryPanel(BillDTO billDTO) {
		try {
			thirdPanel.removeAll();
			if(billDTO!=null) {
				/*
				thirdPanel.add(new MyLabel("Bill Count :")); 
				if(billDTO.getBillCount()!=null) {
				thirdPanel.add(new MyLabel(Calculator.formatTwoDecimal(billDTO.getBillCount())),"wrap");
				}else {
					thirdPanel.add(new MyLabel(""),"wrap");
				}
				
				thirdPanel.add(new MyLabel("Bill Total Weight :")); 
				if(billDTO.getTotalBillWeight()!=null) {
					thirdPanel.add(new MyLabel(Calculator.formatTwoDecimal(billDTO.getTotalBillWeight())),"wrap");
				}else {
					thirdPanel.add(new MyLabel(""),"wrap");
				}
				
				thirdPanel.add(new MyLabel("Total Amount :")); 
				if(billDTO.getTotalBillAmount()!=null) {
					thirdPanel.add(new MyLabel(Calculator.formatTwoDecimal(billDTO.getTotalBillAmount())),"wrap");
				}else {
					thirdPanel.add(new MyLabel(""),"wrap");
				}
				*/
				
				
				thirdPanel.add(new MyLabel("Bill Count :")); 
				if(billDTO.getBillCountStr()!=null) {
				thirdPanel.add(new MyLabel(billDTO.getBillCountStr()),"wrap");
				}else {
					thirdPanel.add(new MyLabel(""),"wrap");
				}
				
				thirdPanel.add(new MyLabel("Bill Total Weight :")); 
				if(billDTO.getTotalBillWeightStr()!=null) {
					thirdPanel.add(new MyLabel(billDTO.getTotalBillWeightStr()),"wrap");
				}else {
					thirdPanel.add(new MyLabel(""),"wrap");
				}
				
				thirdPanel.add(new MyLabel("Total Amount :")); 
				if(billDTO.getTotalBillAmountStr()!=null) {
					thirdPanel.add(new MyLabel(billDTO.getTotalBillAmountStr()),"wrap");
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
