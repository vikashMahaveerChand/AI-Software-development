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
import com.inventory.controller.CashReportController;
import com.inventory.dto.CashReportDTO;
import com.inventory.dto.CashReportDTOList;
import com.inventory.utility.Calculator;
import com.inventory.utility.ConstantUtils;
import com.inventory.utility.Session;
import com.inventory.utility.StringUtils;

import net.miginfocom.swing.MigLayout;

public class CashReport implements DocumentListener, ActionListener, KeyListener, MouseListener {
	
private static CashReport myInstance = null;
	
	private CashReport() {
	}

	public static CashReport getInstance() {
		if (myInstance == null) {
			myInstance = new CashReport();
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
	String metal;
	String billSource;
	JXDatePicker fromDate,toDate;
	MyButton searchBill;
	
	public JPanel getPanel(String calledFrom,String billType,String metal,String billSource){
		try{
			this.calledFrom = calledFrom;
			this.billType = billType;
			this.metal = metal;
			this.billSource = billSource;
			
			initComponent();
			constructCashReport();
			
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
			
			MyPanel.setBorder("Stock Report"+billType, panel);
			MyPanel.setBackground(firstPanel);
			MyPanel.setBackground(secondPanel);
			MyPanel.setBackground(thirdPanel);
			
			reportTable = new JTable();
			
			searchBill.addActionListener(this);
            
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
	}
	
	private void constructCashReport(){
    	try{
    		MyPanel.setBorder("Cash Report "+billType, panel);
    		
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
            
            
            firstPanel.add(new MyLabel("From Date:"));
            firstPanel.add(fromDate);
            firstPanel.add(new MyLabel("To Date:"));
            firstPanel.add(toDate);
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
						return String.class;//table Id
					case 1:
						return String.class;//record type -Paid or received
					case 2:
						return String.class;//payment type - cash or metal
					case 3:
						return String.class;// Bill Source
					case 4:
						return String.class;//Payment Date
					case 5:
						return String.class;//Description
					case 6:
						return String.class;//Amount
					case 7:
						return String.class;//Gold Metal Received
					case 8:
						return String.class;//Silver Metal Received
					case 9:
						return String.class;//Amount
					case 10:
						return String.class;//Gold Metal Paid
					case 11:
						return String.class;//Silver Metal Paid
					default:
						return String.class;
					}
				}
			};
			jTable.setModel(reportModel);
			reportModel.addColumn("tableId");
			reportModel.addColumn("recordType");
			reportModel.addColumn("paymentType");
			reportModel.addColumn("billSource");
			reportModel.addColumn("Payment Date");
        	reportModel.addColumn("Description");
        	reportModel.addColumn("Amount Received");
    		reportModel.addColumn("Gold Metal Received");
    		reportModel.addColumn("Silver Metal Received");
        	reportModel.addColumn("Amount Paid");	
        	reportModel.addColumn("Gold Metal Paid");
        	reportModel.addColumn("Silver Metal Paid");
        	
        	if(billType.equalsIgnoreCase(ConstantUtils.GST_ENTRY)) {
        		jTable.getColumn("Gold Metal Received").setPreferredWidth(0);
            	jTable.getColumn("Gold Metal Received").setMinWidth(0);
            	jTable.getColumn("Gold Metal Received").setMaxWidth(0);
            	
            	jTable.getColumn("Silver Metal Received").setPreferredWidth(0);
            	jTable.getColumn("Silver Metal Received").setMinWidth(0);
            	jTable.getColumn("Silver Metal Received").setMaxWidth(0);
            	
            	jTable.getColumn("Gold Metal Paid").setPreferredWidth(0);
            	jTable.getColumn("Gold Metal Paid").setMinWidth(0);
            	jTable.getColumn("Gold Metal Paid").setMaxWidth(0);
            	
            	jTable.getColumn("Silver Metal Paid").setPreferredWidth(0);
            	jTable.getColumn("Silver Metal Paid").setMinWidth(0);
            	jTable.getColumn("Silver Metal Paid").setMaxWidth(0);
        	}
        	
        	jTable.getColumn("tableId").setPreferredWidth(0);
        	jTable.getColumn("tableId").setMinWidth(0);
        	jTable.getColumn("tableId").setMaxWidth(0);

        	
        	jTable.getColumn("recordType").setPreferredWidth(0);
        	jTable.getColumn("recordType").setMinWidth(0);
        	jTable.getColumn("recordType").setMaxWidth(0);
        	
        	jTable.getColumn("paymentType").setPreferredWidth(0);
        	jTable.getColumn("paymentType").setMinWidth(0);
        	jTable.getColumn("paymentType").setMaxWidth(0);
        	
        	jTable.getColumn("billSource").setPreferredWidth(0);
        	jTable.getColumn("billSource").setMinWidth(0);
        	jTable.getColumn("billSource").setMaxWidth(0);
        	
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
		if(evt == searchBill) {
			fetchCashReportList();
		}
	}
	
	private void fetchCashReportList(){
		try {
			
			String fromDateStr = StringUtils.getStrDate(fromDate.getDate());
    		String toDateStr = StringUtils.getStrDate(toDate.getDate());
    		System.out.println("fromDateStr->"+fromDateStr);
    		System.out.println("toDateStr->"+toDateStr);
    		
    		System.out.println("fromDate->"+fromDate.getDate());
    		System.out.println("toDate->"+toDate.getDate());
    		
    		Date fromDateDt = fromDate.getDate();
    		Date toDateDt = toDate.getDate();
    		Integer licenceId = Session.licence.getLicenceId();
    		
    		CashReportDTO cashReportDTO = CashReportController.getInstace().getCashReportList(fromDateDt, toDateDt, licenceId, billType);
    		List<CashReportDTOList> cashReportList = null;
    		
    		if(cashReportDTO!=null) {
    			cashReportList = cashReportDTO.getCashReportDTOList();
    		}
    		//System.out.println(stockList);
    		
    		int rowCount = reportModel.getRowCount();
            for (int i = 0; i < rowCount; i++) {
            	reportModel.removeRow(0);
            }
            
            if(cashReportList!=null && cashReportList.size()>0){
            	for(CashReportDTOList data:cashReportList){
            		
            		reportModel.addRow(new Object[]{
            				data.getCashReportId(),
            				data.getRecordType(),
            				data.getPaymentType(),
            				data.getBillSource(),
            				data.getPaymentDate()!=null ? StringUtils.getStrDate(data.getPaymentDate()):"",
            				data.getDescription()!=null? data.getDescription():"",
            				data.getAmountReceived()!=null? Calculator.formatTwoDecimal(data.getAmountReceived()):"",
            				//data.getMetalReceived()!=null? Calculator.formatTwoDecimal(data.getMetalReceived()):"",
            				data.getGoldMetalReceived()!=null? Calculator.formatThreeDecimal(data.getGoldMetalReceived()):"",
            				data.getSilverMetalReceived()!=null? Calculator.formatTwoDecimal(data.getSilverMetalReceived()):"",
            				data.getAmountPaid()!=null ? Calculator.formatTwoDecimal(data.getAmountPaid()):"",
            				//data.getMetalPaid()!=null ? Calculator.formatTwoDecimal(data.getMetalPaid()):""
            				data.getGoldMetalPaid()!=null ? Calculator.formatThreeDecimal(data.getGoldMetalPaid()):"",
            				data.getSilverMetalPaid()!=null ? Calculator.formatTwoDecimal(data.getSilverMetalPaid()):""
            		});
            		
            		
            	}
            }
            
            constuctSummaryPanel(cashReportDTO);
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void constuctSummaryPanel(CashReportDTO cashReportDTO) {
		try {
			thirdPanel.removeAll();
			if(cashReportDTO!=null) {
				thirdPanel.add(new MyLabel("Cash In Hand :")); thirdPanel.add(new MyLabel(Calculator.formatTwoDecimal(cashReportDTO.getAmountAvailable())),"wrap");
				if(billType.equalsIgnoreCase(ConstantUtils.NON_GST_ENTRY)) {
					thirdPanel.add(new MyLabel("Gold Metal :")); thirdPanel.add(new MyLabel(Calculator.formatThreeDecimal(cashReportDTO.getGoldMetalAvailable())),"wrap");
					thirdPanel.add(new MyLabel("Silver Metal :")); thirdPanel.add(new MyLabel(Calculator.formatTwoDecimal(cashReportDTO.getSilverMetalAvailable())),"wrap");
				}
			}
			thirdPanel.revalidate();
			thirdPanel.repaint();
			
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}

}
