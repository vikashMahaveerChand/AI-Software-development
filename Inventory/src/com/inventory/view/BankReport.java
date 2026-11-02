package com.inventory.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXDatePicker;

import com.inventory.common.FilterJtable;
import com.inventory.components.EvenOddTableRenderer;
import com.inventory.components.MyButton;
import com.inventory.components.MyLabel;
import com.inventory.components.MyPanel;
import com.inventory.components.MyTextField;
import com.inventory.controller.BankController;
import com.inventory.dto.BankEntryDTO;
import com.inventory.model.Bank;
import com.inventory.model.BankEntry;
import com.inventory.model.Bill;
import com.inventory.utility.Calculator;
import com.inventory.utility.StringUtils;

import net.miginfocom.swing.MigLayout;

public class BankReport implements ActionListener {
	
private static BankReport myInstance=null;
	
	private BankReport(){}
	
	public static BankReport getInstance(){
		if(myInstance ==null){
			myInstance = new BankReport();
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
    
    JXDatePicker fromDate,toDate;
    MyButton searchRecords;
    
    String calledFrom;
    Integer bankId;
    
    JComboBox bankAccount;
    
    List<Bank> bankList = null;
    
    public JPanel getPanel(String calledFrom, Integer bankId){
    	this.calledFrom = calledFrom;
    	this.bankId = bankId;
    	
    	initComponent();
    	constructBankReport();
    	
    	FilterJtable.setFilterJtable(reportTable, searchTextField);
    	
    	return panel;
    }
    
    private void initComponent(){
    	try {
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
			
			searchRecords = new MyButton("Search");
			
			MyPanel.setBorder("Billing Reports", panel);
			MyPanel.setBackground(firstPanel);
			MyPanel.setBackground(secondPanel);
			MyPanel.setBackground(thirdPanel);
			
			reportTable = new JTable();
			
			popupMenu = new JPopupMenu();
            editRecord = new JMenuItem();
            deleteRecord = new JMenuItem();
            enableDisableRecord = new JMenuItem();
            printRecord = new JMenuItem();
            
            bankAccount = new JComboBox();
			bankAccount.setSize(300, 10);
			
			int counter=0;
			int bankIndex=-1;
			bankList = BankController.getInstace().getBankList();
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
            
            editRecord.addActionListener(this);
            deleteRecord.addActionListener(this);
            enableDisableRecord.addActionListener(this);
            searchRecords.addActionListener(this);
            printRecord.addActionListener(this);
    		
    	}catch(Exception ex) {
    		ex.printStackTrace();
    	}
    }
    
    private void constructBankReport(){
    	try {
    		MyPanel.setBorder("Bank Report", panel);
    		
    		tableModelling(reportTable);
			editRecord.setText("Edit Bill");
			deleteRecord.setText("Delete Entry");
			enableDisableRecord.setText("Enable/Disable");
			printRecord.setText("Print Bill");
			
			//popupMenu.add(editRecord);
			popupMenu.add(deleteRecord);
			//popupMenu.add(printRecord);
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
            
            firstPanel.add(new MyLabel("Bank Account:"));
            firstPanel.add(bankAccount);
            firstPanel.add(new MyLabel("From Date:"));
            firstPanel.add(fromDate);
            firstPanel.add(new MyLabel("To Date:"));
            firstPanel.add(toDate);
            firstPanel.add(searchRecords);
            firstPanel.add(new MyLabel("Search Bank Entry"),"push,al right");
            firstPanel.add(searchTextField,"al right, wrap");
            
            
            panel.add(firstPanel,"span,pushx,growx,wrap");
            panel.add(secondPanel,"span,pushx,growx,wrap");
            panel.add(thirdPanel,"span,pushx,growx,wrap");
    	}catch(Exception ex) {
    		ex.printStackTrace();
    	}
    }
    
    private void tableModelling(JTable jTable){
		try{
			
			reportModel = new DefaultTableModel() {

				public Class<?> getColumnClass(int column) {
					switch (column) {
					
					case 0:
						return String.class;//Bank Entry Id
					case 1:
						return String.class;//Entry type Bank or from payment
					case 2:
						return String.class;//Entry Date
					case 3:
						return String.class;//Debit Amount
					case 4:
						return String.class;//Credit Amount
					case 5:
						return String.class;//Notes
					default:
						return String.class;
					}
				}
			};
			jTable.setModel(reportModel);
			reportModel.addColumn("bankEntryId");
			reportModel.addColumn("entryType");
			reportModel.addColumn("Entry Date");
        	reportModel.addColumn("Debit Amount");
        	reportModel.addColumn("Credit Amount");
        	reportModel.addColumn("Notes");

        	jTable.getColumn("bankEntryId").setPreferredWidth(0);
        	jTable.getColumn("bankEntryId").setMinWidth(0);
        	jTable.getColumn("bankEntryId").setMaxWidth(0);
        	
        	jTable.getColumn("entryType").setPreferredWidth(0);
        	jTable.getColumn("entryType").setMinWidth(0);
        	jTable.getColumn("entryType").setMaxWidth(0);
        	
        	
        	
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
		try {
			if(evt==searchRecords){
				fetchBankReportList();
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void fetchBankReportList() {
		try {
			Date fromDateDt = fromDate.getDate();
    		Date toDateDt = toDate.getDate();
    		Bank bankObjSelected = (Bank) bankAccount.getSelectedItem();
    		
    		BankEntryDTO bankEntryDTO = BankController.getInstace().getBankEntryList(fromDateDt, toDateDt, bankObjSelected.getBankId());
    		
    		List<BankEntry> bankEntryList = null;
    		if(bankEntryDTO!=null) {
    			bankEntryList = bankEntryDTO.getBankEntryList();
    		}
    		
    		int rowCount = reportModel.getRowCount();
            for (int i = 0; i < rowCount; i++) {
            	reportModel.removeRow(0);
            }
            
    		if(bankEntryList!=null && bankEntryList.size()>0) {
    			if(bankEntryList!=null && bankEntryList.size()>0){
    				for(BankEntry data:bankEntryList){
                		reportModel.addRow(new Object[]{
                				data.getBankEntryId(),
                				"",
                				data.getEntryDate()!=null?StringUtils.getStrDate(data.getEntryDate()):"",
                				data.getDebitAmount()!=null? Calculator.formatTwoDecimal(data.getDebitAmount()):"",
                				data.getCreditAmount()!=null? Calculator.formatTwoDecimal(data.getCreditAmount()):"",
                				data.getEntryNotes()
                		});
                	}
    			}
    		}
    		
    		formSummaryPanel(bankEntryDTO);
    		
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void formSummaryPanel(BankEntryDTO bankEntryDTO) {
		try {
			thirdPanel.removeAll();
			if(bankEntryDTO!=null) {
				thirdPanel.add(new MyLabel("Total Debit Amount :")); thirdPanel.add(new MyLabel(Calculator.formatTwoDecimal(bankEntryDTO.getTotalDebit())),"wrap");
				thirdPanel.add(new MyLabel("Total Credit Amount :")); thirdPanel.add(new MyLabel(Calculator.formatTwoDecimal(bankEntryDTO.getTotalCredit())),"wrap");
				thirdPanel.add(new MyLabel("Total Balance :")); thirdPanel.add(new MyLabel(Calculator.formatTwoDecimal(bankEntryDTO.getCurrentBalance())),"wrap");
			}
			thirdPanel.revalidate();
			thirdPanel.repaint();
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
	}
}
