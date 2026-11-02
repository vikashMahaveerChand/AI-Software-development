package com.inventory.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.jdesktop.swingx.JXDatePicker;

import com.inventory.common.FilterJtable;
import com.inventory.components.EvenOddTableRenderer;
import com.inventory.components.MyButton;
import com.inventory.components.MyLabel;
import com.inventory.components.MyPanel;
import com.inventory.components.MyTextField;
import com.inventory.components.TableRendererForPawnReport;
import com.inventory.controller.AppController;
import com.inventory.controller.BillController;
import com.inventory.controller.MasterController;
import com.inventory.controller.PawnBillController;
import com.inventory.dto.PawnBillDTO;
import com.inventory.model.Bill;
import com.inventory.model.BillSource;
import com.inventory.model.Metal;
import com.inventory.model.PawnBill;
import com.inventory.model.PawnBillStatus;
import com.inventory.reports.RunReport;
import com.inventory.utility.Calculator;
import com.inventory.utility.ConstantUtils;
import com.inventory.utility.Session;
import com.inventory.utility.StringUtils;

import net.miginfocom.swing.MigLayout;

public class PawnBillingReport implements ActionListener,KeyListener,TableModelListener {
	
private static PawnBillingReport myInstance=null;
	
	private PawnBillingReport(){}
	
	public static PawnBillingReport getInstance(){
		if(myInstance ==null){
			myInstance = new PawnBillingReport();
		}
		
		return myInstance;
	}
	
	JPanel panel;
	JPanel firstPanel,secondPanel,thirdPanel;
	JPanel thirdPanelFirst,thirdPanelSecond;
	MyTextField searchTextField = null;
	
	JTable reportTable=null;
    DefaultTableModel reportModel =null;
    JScrollPane scrollPane;
    
    private JPopupMenu popupMenu;
    //private JMenuItem editRecord,deleteRecord,enableDisableRecord;
    private JMenuItem editPawnBillRecord,editRedeemRecord,editAuctionRecord,deletePawnBillRecord,deleteRedeemRecord,deleteAuctionRecord,editQuickData,printPawnBill;
    String pawnBillSource,calledFrom,billType,metal;
    JDialog dialog = null;
    JXDatePicker fromDate,toDate,pawnBillDate,redeemDate,bulkAuctionDate;
    MyButton searchBill,remainderReport,auctionReport,pledgeReport,print,selectAll;
    
    JComboBox pawnBillStatusFilter,metalFilter,printOption;
    List<PawnBillStatus> pawnBillStatusList = null;
    List<Metal> metalList = null;
    
    JDialog editDataDialog;
    JPanel editDataPanel;
    MyTextField pawnBillNumber,redeemNumber;
    MyButton saveEditRecord;
    Integer editDataBillId;
    
    MyTextField bulkAuctionNumber,bulkAuctionNarration;
    MyButton bulkAuctionSave;
    
    public JPanel getPanel(String calledFrom,String pawnBillSource){
    	try {
    		this.calledFrom = calledFrom;
    		this.pawnBillSource = pawnBillSource;
    		initComponent();
    		constructBillingReport();
    		fetchPawnBillingReportList();
    		
    		FilterJtable.setFilterJtable(reportTable, searchTextField);
    		
    	}catch(Exception ex) {
    		ex.printStackTrace();
    	}
    	return panel;
    }
    
    private void initComponent() {
    	try {
    		panel = new JPanel(new MigLayout());
    		
    		firstPanel = new JPanel(new MigLayout());
			secondPanel = new JPanel(new MigLayout());
			thirdPanel = new JPanel(new MigLayout());
			
			thirdPanelFirst = new JPanel(new MigLayout());
			thirdPanelSecond = new JPanel(new MigLayout());
			
			searchTextField = new MyTextField(20);
			
			fromDate = new JXDatePicker();
			fromDate.setDate(Calendar.getInstance().getTime());
			fromDate.setFormats(new SimpleDateFormat("dd/MM/yyyy"));
			
			toDate = new JXDatePicker();
			toDate.setDate(Calendar.getInstance().getTime());
			toDate.setFormats(new SimpleDateFormat("dd/MM/yyyy"));
			
			bulkAuctionDate = new JXDatePicker();
			bulkAuctionDate.setDate(Calendar.getInstance().getTime());
			bulkAuctionDate.setFormats(new SimpleDateFormat("dd/MM/yyyy"));
			
			searchBill = new MyButton("Search");
			remainderReport = new MyButton("Remainder Report");
			auctionReport = new MyButton("Auction Report");
			pledgeReport = new MyButton("Pledge Print");
			print = new MyButton("Print");
			selectAll = new MyButton("Select All");
			
			pawnBillStatusFilter = new JComboBox();
			pawnBillStatusFilter.setSize(300, 10);
			
			pawnBillStatusList = PawnBillController.getInstace().getPawnBillStatusList();
			
			pawnBillStatusFilter.addItem("All Record");
			
			for(PawnBillStatus pawnBillStatus: pawnBillStatusList) {
				pawnBillStatusFilter.addItem(pawnBillStatus);
			}
			
			
			metalFilter = new JComboBox();
			metalFilter.setSize(300,10);
			metalList = MasterController.getInstace().getMetalList();
			
			metalFilter.addItem("All");
			for(Metal metal:metalList) {
				metalFilter.addItem(metal);
			}
			
			printOption = new JComboBox();
			printOption.setSize(300,10);
			
			printOption.addItem("Pledge Report");
			printOption.addItem("Remainder Letter");
			printOption.addItem("Auction Letter");
			printOption.addItem("Tag (Chitti)");
			printOption.addItem("Tag (BarCode)");
			
			MyPanel.setBorder("Billing Reports", panel);
			MyPanel.setBackground(firstPanel);
			MyPanel.setBackground(secondPanel);
			MyPanel.setBackground(thirdPanel);
			
			MyPanel.setBackground(thirdPanelFirst);
			MyPanel.setBorder("", thirdPanelSecond);
			
			
			reportTable = new JTable();
            
			dialog = new JDialog();
			Window parentWindow = SwingUtilities.getWindowAncestor(panel);
			dialog = new JDialog(parentWindow, ModalityType.APPLICATION_MODAL);
			
			popupMenu = new JPopupMenu();
            /*editRecord = new JMenuItem();
            deleteRecord = new JMenuItem();
            enableDisableRecord = new JMenuItem();
            
            editRecord.addActionListener(this);
            deleteRecord.addActionListener(this);
            enableDisableRecord.addActionListener(this);*/
			editPawnBillRecord = new JMenuItem();
			editRedeemRecord = new JMenuItem();
			deletePawnBillRecord = new JMenuItem();
			deleteRedeemRecord = new JMenuItem();
			editQuickData = new JMenuItem();
			printPawnBill = new JMenuItem();
			editAuctionRecord = new JMenuItem();
			deleteAuctionRecord = new JMenuItem();
			
			bulkAuctionNumber = new MyTextField(10);
			bulkAuctionNarration = new MyTextField(10);
		    bulkAuctionSave = new MyButton("Save Bulk Auction");

			searchBill.addActionListener(this);
            editPawnBillRecord.addActionListener(this);
            editRedeemRecord.addActionListener(this);
            deletePawnBillRecord.addActionListener(this);
            deleteRedeemRecord.addActionListener(this);
            editQuickData.addActionListener(this);
            printPawnBill.addActionListener(this);
            remainderReport.addActionListener(this);
            auctionReport.addActionListener(this);
            pledgeReport.addActionListener(this);
            print.addActionListener(this);
            editAuctionRecord.addActionListener(this);
            deleteAuctionRecord.addActionListener(this);
            selectAll.addActionListener(this);
            bulkAuctionNumber.addActionListener(this);
            bulkAuctionNarration.addActionListener(this);
            bulkAuctionSave.addActionListener(this);;
            //editPawnBillRecord,editRedeemRecord,deletePawnBillRecord,deleteRedeemRecord,editQuickData
    		
    	}catch(Exception ex) {
    		ex.printStackTrace();
    	}
    }
    
    private void constructBillingReport(){
    	try{
    		MyPanel.setBorder("Pawn Billing Report"+pawnBillSource, panel);
    		
    		tableModelling(reportTable);
			/*editRecord.setText("Edit Bill");
			deleteRecord.setText("Delete Bill");
			enableDisableRecord.setText("Enable/Disable");
			
			popupMenu.add(editRecord);
			popupMenu.add(deleteRecord);*/
			//popupMenu.add(enableDisableRecord);
			
    		editPawnBillRecord.setText("Edit Pawn Bill");
    		editRedeemRecord.setText("Edit Redeem Entry");
    		deletePawnBillRecord.setText("Delete Pawn Bill");
    		deleteRedeemRecord.setText("Delete Redeem Entry");
    		editQuickData.setText("Edit Data");
    		printPawnBill.setText("Print Pawn Bill");
    		editAuctionRecord.setText("Edit Auction Entry");
    		deleteAuctionRecord.setText("Delete Auction Entry");

    		popupMenu.add(editPawnBillRecord);
			popupMenu.add(editRedeemRecord);
			popupMenu.add(editAuctionRecord);
			popupMenu.add(deletePawnBillRecord);
			popupMenu.add(deleteRedeemRecord);
			popupMenu.add(deleteAuctionRecord);
			popupMenu.add(editQuickData);
			popupMenu.add(printPawnBill);

			reportTable.setComponentPopupMenu(popupMenu);
            
            reportTable.setBackground(Color.WHITE);
            scrollPane = new JScrollPane(reportTable);
            Dimension d = reportTable.getPreferredSize();
            int rows=40;
            scrollPane.setPreferredSize(
            new Dimension(d.width,reportTable.getRowHeight()*rows+1));
            secondPanel.add(scrollPane,"span,pushx,growx,wrap");
            
            TableRendererForPawnReport render =new TableRendererForPawnReport();
            //TableRendererForContact render = new TableRendererForContact();
            reportTable.setDefaultRenderer(Object.class,  render);
            reportTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            
            firstPanel.add(new MyLabel("From Date:"));
            firstPanel.add(fromDate);
            firstPanel.add(new MyLabel("To Date:"));
            firstPanel.add(toDate);
            firstPanel.add(new MyLabel("Filter :"));
            firstPanel.add(pawnBillStatusFilter);
            firstPanel.add(new MyLabel("Metal Filter :"));
            firstPanel.add(metalFilter);
            firstPanel.add(searchBill);
            firstPanel.add(printOption);
            firstPanel.add(print);
            
            
            /*firstPanel.add(pledgeReport);
            firstPanel.add(remainderReport);
            firstPanel.add(auctionReport);*/
            firstPanel.add(new MyLabel("Search Bill"),"push,al right");
            firstPanel.add(searchTextField,"al right, wrap");
            firstPanel.add(selectAll);
            
            
            thirdPanelSecond.add(new MyLabel("Bulk Auction Date:"));
            thirdPanelSecond.add(bulkAuctionDate,"wrap");
            
            thirdPanelSecond.add(new MyLabel("Bulk Auction Number:"));
            thirdPanelSecond.add(bulkAuctionNumber,"wrap");
            
            thirdPanelSecond.add(new MyLabel("Bulk Auction Narration:"));
            thirdPanelSecond.add(bulkAuctionNarration,"wrap");
            
            
            thirdPanelSecond.add(bulkAuctionSave,"span 2,wrap");
            
            
            thirdPanel.add(thirdPanelFirst,"pushy,growy");
            thirdPanel.add(thirdPanelSecond,"pushy,growy");
            
            panel.add(firstPanel,"span,pushx,growx,wrap");
            panel.add(secondPanel,"span,pushx,growx,wrap");
            panel.add(thirdPanel,"span,pushx,growx,wrap");
            
            reportTable.getModel().addTableModelListener(this);
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
						return Boolean.class;//Check Box
					case 2:
						return String.class;//Bill NO
					case 3:
						return String.class;//Bill Date
					case 4:
						return String.class;//Name
					case 5:
						return String.class;//Ornament Description
					case 6:
						return String.class;//Weight
					case 7:
						return String.class;//Amount					
					case 8:
						return String.class; //No Of Month
					case 9:
						return String.class;// Interest Per Month
					case 10:
						return String.class;// Amount to Pay
					case 11:
						return String.class;// Compound Interest					
					case 12:
						return String.class;//Status
					case 13:
						return String.class;//Delivery No
					case 14:
						return String.class;//Redeem Date
					case 15:
						return String.class;//Redeemed Amount
					case 16:
						return Boolean.class;//Check box
					default:
						return String.class;
					}
				}
			};
			jTable.setModel(reportModel);
			reportModel.addColumn("billId");
			reportModel.addColumn("ALL");
			reportModel.addColumn("Bill No");
        	reportModel.addColumn("Bill Date");
        	reportModel.addColumn("Name");
        	reportModel.addColumn("Ornament Description");
        	reportModel.addColumn("Weight");
        	reportModel.addColumn("Amount");        	
        	reportModel.addColumn("# Month");
        	reportModel.addColumn("Int / Month");
        	reportModel.addColumn("R Amount SI ");
        	reportModel.addColumn("R Amount CI");
        	reportModel.addColumn("Status");
        	reportModel.addColumn("Delivery No");
        	reportModel.addColumn("Redeem Date");
        	reportModel.addColumn("Redeemed Amount");
        	reportModel.addColumn("Auction Y/N");

        	jTable.getColumn("billId").setPreferredWidth(0);
        	jTable.getColumn("billId").setMinWidth(0);
        	jTable.getColumn("billId").setMaxWidth(0);
        	
        	jTable.getColumn("Ornament Description").setPreferredWidth(300);
        	jTable.getColumn("Ornament Description").setMinWidth(300);
        	jTable.getColumn("Ornament Description").setMaxWidth(300);
        	
        	        	
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
    
    private void fetchPawnBillingReportList(){
    	try{
    		String fromDateStr = StringUtils.getStrDate(fromDate.getDate());
    		String toDateStr = StringUtils.getStrDate(toDate.getDate());
    		System.out.println("fromDateStr->"+fromDateStr);
    		System.out.println("toDateStr->"+toDateStr);
    		
    		System.out.println("fromDate->"+fromDate.getDate());
    		System.out.println("toDate->"+toDate.getDate());
    		
    		System.out.println("Selected Filter : "+pawnBillStatusFilter.getSelectedItem().toString());
    		
    		String selectedFilter = pawnBillStatusFilter.getSelectedItem().toString();
    		Integer pawnBillStatusFilterId = null;
    		if(!selectedFilter.equalsIgnoreCase("All Record")) {
    			PawnBillStatus billStatus= (PawnBillStatus) pawnBillStatusFilter.getSelectedItem();
    			System.out.println("Id ->"+billStatus.getPawnBillStatusId());
    			pawnBillStatusFilterId = billStatus.getPawnBillStatusId();
    		}
    		
    		Integer metalId = null;
    		String selectedMetalFilter = metalFilter.getSelectedItem().toString();
    		if(!selectedMetalFilter.equalsIgnoreCase("All")) {
    			Metal selectMetal = (Metal) metalFilter.getSelectedItem();
    			metalId = selectMetal.getMetalId();
    		}
    		
    		Date fromDateDt = fromDate.getDate();
    		Date toDateDt = toDate.getDate();
    		BillSource billSourceObj = MasterController.getInstace().getBillSourceByCode(pawnBillSource);
    		//PawnBillDTO pawnBillDTO = PawnBillController.getInstace().getPawnBillList(fromDateDt, toDateDt, Session.licence.getLicenceId(), billSourceObj.getBillSourceId(), null,pawnBillSource);
    		PawnBillDTO pawnBillDTO = PawnBillController.getInstace().getPawnBillList(fromDateDt, toDateDt, Session.licence.getLicenceId(), pawnBillStatusFilterId, null,pawnBillSource,metalId);
    		
    		List<PawnBill> pawnBillList = null;
    		
    		if(pawnBillDTO!=null) {
    			pawnBillList = pawnBillDTO.getPawnBillList();
    		}
    		
    		int rowCount = reportModel.getRowCount();
            for (int i = 0; i < rowCount; i++) {
            	reportModel.removeRow(0);
            }
            System.out.println("pawnBillList"+pawnBillList.size());
            if(pawnBillList!=null && pawnBillList.size()>0){
            	for(PawnBill data:pawnBillList){
            		reportModel.addRow(new Object[]{
            				data.getPawnBillId(),
            				false,
            				data.getBillNumber(),
            				StringUtils.getStrDate(data.getBillDate()),
            				data.getContact().getContactName(),
            				data.getOrnamentDescription()!=null?StringUtils.capitalize(data.getOrnamentDescription()):"",
            				data.getTotalNetWeight(),
            				//data.getTotalGrossWeight(),
            				data.getPledgeAmount() !=null ? Calculator.formatThreeDecimal(data.getPledgeAmount()):"",            				
            				data.getNoOfMonths(),
            				data.getRateOfInterestPerMonth() !=null ? Calculator.formatThreeDecimal(data.getRateOfInterestPerMonth()):"",
            				data.getRedeemAmount() !=null ? Calculator.formatThreeDecimal(data.getRedeemAmount()):"",
            				data.getCompoundInterestAmount() != null ? Calculator.formatThreeDecimal(data.getCompoundInterestAmount()):"",
            				data.getPawnBillStatusId().getPawnBillStatus(),
            				data.getPawnBillRedeemDetails()!=null ? data.getPawnBillRedeemDetails().getDeliveryNumber():"",
            				data.getPawnBillRedeemDetails()!=null && data.getPawnBillRedeemDetails().getRedeemDate()!=null? StringUtils.getStrDate(data.getPawnBillRedeemDetails().getRedeemDate()):"",
            				data.getPawnBillRedeemDetails()!=null && data.getPawnBillRedeemDetails().getRedeemAmountReceived()!=null? Calculator.formatThreeDecimal(data.getPawnBillRedeemDetails().getRedeemAmountReceived()):"",
            				data.getAuctionStatus()!=null && data.getAuctionStatus().equalsIgnoreCase("Y") ? true:false
            				//data.getBillStatusCode()
            						
            		});
            	}
            }
            constructThirdPanel(pawnBillDTO);
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
			//editPawnBillRecord,editRedeemRecord,deletePawnBillRecord,deleteRedeemRecord,editQuickData
			if(evt ==  editPawnBillRecord || evt ==  editRedeemRecord || evt == deletePawnBillRecord 
					|| evt == deleteRedeemRecord || evt == editQuickData || evt ==printPawnBill
					|| evt == editAuctionRecord || evt == deleteAuctionRecord
					){
				int selectedRow = reportTable.getSelectedRow();
				int billId=0;
				
				if(selectedRow>-1){
					Integer billIdInCell = (Integer)reportTable.getValueAt(selectedRow, 0);
					billId = billIdInCell;
					String billStatus = (String)reportTable.getValueAt(selectedRow, 12);
					if(evt ==  editPawnBillRecord){
						AppController.getInstance().addRetailPawnBillingFormPage(ConstantUtils.BILLING_REPORT, billId, 0, pawnBillSource);						
					}else if(evt == editRedeemRecord) {
						if(billStatus.equalsIgnoreCase(ConstantUtils.PAWN_BILL_STATUS_REDEEM)) {
							AppController.getInstance().addRetailPawnDeliveryFormPage(ConstantUtils.BILLING_REPORT, billId, 0, ConstantUtils.PAWN_RETAIL_DELIVERY);
						}else {
							JOptionPane.showMessageDialog(null, "No Redeem Data Found");
						}
					}else if(evt == editAuctionRecord) {
						if(billStatus.equalsIgnoreCase(ConstantUtils.PAWN_BILL_STATUS_AUCTION)) {
							AppController.getInstance().addRetailPawnAuctionFormPage(ConstantUtils.BILLING_REPORT, billId, 0, ConstantUtils.PAWN_RETAIL_AUCTION);
						}else {
							JOptionPane.showMessageDialog(null, "No Auction Data Found");
						}
					}else if(evt == deletePawnBillRecord) {
						deleteBill(billId, ConstantUtils.PAWN_BILL_STATUS_PLEDGE,billStatus);
					}else if(evt == deleteRedeemRecord) {
						deleteBill(billId, ConstantUtils.PAWN_BILL_STATUS_REDEEM,billStatus);
					}else if(evt == deleteAuctionRecord) {
						deleteBill(billId, ConstantUtils.PAWN_BILL_STATUS_AUCTION,billStatus);
					}else if(evt == editQuickData) {
						openQuickDataPopup(billId);
					}else if(evt ==printPawnBill) {
						PawnBill pawnBillObjTemp = PawnBillController.getInstace().getPawnBill(billId);
						RunReport.printPawnBill(pawnBillObjTemp);
					}
				}
			}else if(evt==searchBill){
				fetchPawnBillingReportList();
			}else if(evt == saveEditRecord) {
				updateEditData();
			}else if(evt == pawnBillDate) {
				redeemNumber.requestFocusInWindow();
			}else if(evt == redeemDate) {
				saveEditRecord.requestFocusInWindow();
			}else if(evt == remainderReport) {
				remainderReport();
			}else if(evt == auctionReport) {
				auctionReport();
			}else if(evt == pledgeReport) {
				pledgeReportPrint();
			}else if(evt == print) {
				print();
			}else if(evt == selectAll) {
				selectAllCheckBox();
			}else if(evt == bulkAuctionSave) {
				bulkAuctionSave();
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	
	
	private void openQuickDataPopup(int billId) {
		try {
			PawnBill bill = PawnBillController.getInstace().getPawnBill(billId);
			editDataBillId = billId;
			if(bill!=null) {
				editDataDialog = new JDialog(AppController.getInstance().frame,"Edit Bill Data");
				editDataPanel = new JPanel(new MigLayout());
				MyPanel.setBackground(editDataPanel);
				MyPanel.setBorder("Update Record", editDataPanel);
				
				editDataDialog.setSize(300,300);
				
				final Toolkit toolkit = Toolkit.getDefaultToolkit();
				final Dimension screenSize = toolkit.getScreenSize();
				final int x = (screenSize.width - editDataDialog.getWidth()) / 2;
				final int y = (screenSize.height - editDataDialog.getHeight()) / 2;
				editDataDialog.setLocation(x, y);
				
				
				pawnBillDate = new JXDatePicker();
				pawnBillDate.setDate(Calendar.getInstance().getTime());
				pawnBillDate.setFormats(new SimpleDateFormat("dd/MM/yyyy"));
				
				redeemDate = new JXDatePicker();
				redeemDate.setDate(Calendar.getInstance().getTime());
				redeemDate.setFormats(new SimpleDateFormat("dd/MM/yyyy"));
				
				pawnBillNumber = new MyTextField(10);
				redeemNumber = new MyTextField(10);
				
				saveEditRecord = new MyButton("Update");	
								
				pawnBillNumber.addActionListener(this);
				pawnBillDate.addActionListener(this);
				redeemNumber.addActionListener(this);
				redeemDate.addActionListener(this);
				saveEditRecord.addActionListener(this);
				
				pawnBillNumber.addKeyListener(this);
				pawnBillDate.addKeyListener(this);
				redeemNumber.addKeyListener(this);
				redeemDate.addKeyListener(this);
				saveEditRecord.addKeyListener(this);
				
				editDataPanel.add(new MyLabel("Pawn Bill Number :"));
				editDataPanel.add(pawnBillNumber,"wrap");
				
				editDataPanel.add(new MyLabel("Pawn Bill Date :"));
				editDataPanel.add(pawnBillDate,"wrap");
				if(bill.getPawnBillRedeemDetails()!=null) {
				editDataPanel.add(new MyLabel("Delivery Number :"));
				editDataPanel.add(redeemNumber,"wrap");				
				
				editDataPanel.add(new MyLabel("Delivery Date :"));
				editDataPanel.add(redeemDate,"wrap");
				}
				editDataPanel.add(saveEditRecord,"wrap");
				
				editDataDialog.add(editDataPanel);
				
				
				if(bill.getBillNumber()!=null) {
					pawnBillNumber.setText(bill.getBillNumber());
				}
				
				if(bill.getBillDate()!=null) {
					pawnBillDate.setDate(bill.getBillDate());
				}
				
				if(bill.getPawnBillRedeemDetails()!=null) {
					if(bill.getPawnBillRedeemDetails().getDeliveryNumber()!=null) {
						redeemNumber.setText(bill.getPawnBillRedeemDetails().getDeliveryNumber());
					}
					
					if(bill.getPawnBillRedeemDetails().getRedeemDate()!=null) {
						redeemDate.setDate(bill.getPawnBillRedeemDetails().getRedeemDate());
					}else {
						redeemDate.setDate(null);
					}
				}else {
					redeemDate.setDate(null);
				}
				
				
				editDataDialog.setVisible(true);
				editDataDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);				
			}
			
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent evt) {
		// TODO Auto-generated method stub
		if(evt.getSource() == saveEditRecord) {
			if(evt.getKeyCode() == KeyEvent.VK_ENTER){
				updateEditData();
			}
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	private void updateEditData() {		
		try {
			if(editDataBillId!=null && editDataBillId>0) {
				
				String pawnBillNumberStr = "";
				Date pawnBillDateLocal = null;
				String redeemNumberStr="";
				Date redeemDateLocal = null;
				
				if(!pawnBillNumber.getText().equals("")) {
					pawnBillNumberStr = pawnBillNumber.getText().trim();
				}
				
				if(pawnBillDate.getDate()!=null) {
					pawnBillDateLocal = pawnBillDate.getDate();
				}
				
				if(!redeemNumber.getText().trim().equals("")) {
					redeemNumberStr = redeemNumber.getText().trim();
				}
				
				if(redeemDate.getDate()!=null) {
					redeemDateLocal = redeemDate.getDate();
				}
				
				Integer updateResponse = null;
				if(!pawnBillNumberStr.equalsIgnoreCase("") && pawnBillDateLocal!=null) {
					updateResponse = PawnBillController.getInstace().updatePawnBillDetails(editDataBillId, pawnBillNumberStr, pawnBillDateLocal);
				}
				
				if(!redeemNumberStr.equalsIgnoreCase("") && redeemDateLocal!=null) {
					updateResponse = PawnBillController.getInstace().updatePawnBillRedeemDetails(editDataBillId, redeemNumberStr, redeemDateLocal);
				}
				
				if(updateResponse!=null && updateResponse>0) {
					JOptionPane.showMessageDialog(null, "Updated Sucessfully !");
					editDataDialog.dispose();
					fetchPawnBillingReportList();
				}else {
					JOptionPane.showMessageDialog(null, "Updated Failed");
				}
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
	private void remainderReport() {
		try {
			
			String fromDateStr = StringUtils.getStrDate(fromDate.getDate());
    		String toDateStr = StringUtils.getStrDate(toDate.getDate());
    		System.out.println("fromDateStr->"+fromDateStr);
    		System.out.println("toDateStr->"+toDateStr);
    		
    		System.out.println("fromDate->"+fromDate.getDate());
    		System.out.println("toDate->"+toDate.getDate());
    		
    		System.out.println("Selected Filter : "+pawnBillStatusFilter.getSelectedItem().toString());
    		
    		String selectedFilter = pawnBillStatusFilter.getSelectedItem().toString();
    		Integer pawnBillStatusFilterId = null;
    		if(!selectedFilter.equalsIgnoreCase("All Record")) {
    			PawnBillStatus billStatus= (PawnBillStatus) pawnBillStatusFilter.getSelectedItem();
    			System.out.println("Id ->"+billStatus.getPawnBillStatusId());
    			pawnBillStatusFilterId = billStatus.getPawnBillStatusId();
    		}
    		
    		Integer metalId = null;
    		String selectedMetalFilter = metalFilter.getSelectedItem().toString();
    		if(!selectedMetalFilter.equalsIgnoreCase("All")) {
    			Metal selectMetal = (Metal) metalFilter.getSelectedItem();
    			metalId = selectMetal.getMetalId();
    		}
    		
    		Date fromDateDt = fromDate.getDate();
    		Date toDateDt = toDate.getDate();
    		BillSource billSourceObj = MasterController.getInstace().getBillSourceByCode(pawnBillSource);
    		//PawnBillDTO pawnBillDTO = PawnBillController.getInstace().getPawnBillList(fromDateDt, toDateDt, Session.licence.getLicenceId(), billSourceObj.getBillSourceId(), null,pawnBillSource);
    		PawnBillDTO pawnBillDTO = PawnBillController.getInstace().getPawnBillList(fromDateDt, toDateDt, Session.licence.getLicenceId(), pawnBillStatusFilterId, null,pawnBillSource,metalId);
    		
    		List<PawnBill> pawnBillList = null;
    		
    		if(pawnBillDTO!=null) {
    			pawnBillList = pawnBillDTO.getPawnBillList();
    		}
    		
    		RunReport.printRemainderReport(pawnBillList);
			
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void auctionReport() {
		try {
			
			String fromDateStr = StringUtils.getStrDate(fromDate.getDate());
    		String toDateStr = StringUtils.getStrDate(toDate.getDate());
    		System.out.println("fromDateStr->"+fromDateStr);
    		System.out.println("toDateStr->"+toDateStr);
    		
    		System.out.println("fromDate->"+fromDate.getDate());
    		System.out.println("toDate->"+toDate.getDate());
    		
    		System.out.println("Selected Filter : "+pawnBillStatusFilter.getSelectedItem().toString());
    		
    		String selectedFilter = pawnBillStatusFilter.getSelectedItem().toString();
    		Integer pawnBillStatusFilterId = null;
    		if(!selectedFilter.equalsIgnoreCase("All Record")) {
    			PawnBillStatus billStatus= (PawnBillStatus) pawnBillStatusFilter.getSelectedItem();
    			System.out.println("Id ->"+billStatus.getPawnBillStatusId());
    			pawnBillStatusFilterId = billStatus.getPawnBillStatusId();
    		}
    		
    		Integer metalId = null;
    		String selectedMetalFilter = metalFilter.getSelectedItem().toString();
    		if(!selectedMetalFilter.equalsIgnoreCase("All")) {
    			Metal selectMetal = (Metal) metalFilter.getSelectedItem();
    			metalId = selectMetal.getMetalId();
    		}
    		
    		Date fromDateDt = fromDate.getDate();
    		Date toDateDt = toDate.getDate();
    		BillSource billSourceObj = MasterController.getInstace().getBillSourceByCode(pawnBillSource);
    		//PawnBillDTO pawnBillDTO = PawnBillController.getInstace().getPawnBillList(fromDateDt, toDateDt, Session.licence.getLicenceId(), billSourceObj.getBillSourceId(), null,pawnBillSource);
    		PawnBillDTO pawnBillDTO = PawnBillController.getInstace().getPawnBillList(fromDateDt, toDateDt, Session.licence.getLicenceId(), pawnBillStatusFilterId, null,pawnBillSource,metalId);
    		
    		List<PawnBill> pawnBillList = null;
    		
    		if(pawnBillDTO!=null) {
    			pawnBillList = pawnBillDTO.getPawnBillList();
    		}
    		
    		RunReport.printAuctionReport(pawnBillList,fromDateDt,toDateDt);
			
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	private void pledgeReportPrint() {
		try {
			
			String fromDateStr = StringUtils.getStrDate(fromDate.getDate());
    		String toDateStr = StringUtils.getStrDate(toDate.getDate());
    		
    		String selectedFilter = pawnBillStatusFilter.getSelectedItem().toString();
    		Integer pawnBillStatusFilterId = null;
    		if(!selectedFilter.equalsIgnoreCase("All Record")) {
    			PawnBillStatus billStatus= (PawnBillStatus) pawnBillStatusFilter.getSelectedItem();
    			System.out.println("Id ->"+billStatus.getPawnBillStatusId());
    			pawnBillStatusFilterId = billStatus.getPawnBillStatusId();
    		}
    		
    		Integer metalId = null;
    		String selectedMetalFilter = metalFilter.getSelectedItem().toString();
    		if(!selectedMetalFilter.equalsIgnoreCase("All")) {
    			Metal selectMetal = (Metal) metalFilter.getSelectedItem();
    			metalId = selectMetal.getMetalId();
    		}
    		
    		Date fromDateDt = fromDate.getDate();
    		Date toDateDt = toDate.getDate();
    		BillSource billSourceObj = MasterController.getInstace().getBillSourceByCode(pawnBillSource);
    		PawnBillDTO pawnBillDTO = PawnBillController.getInstace().getPawnBillList(fromDateDt, toDateDt, Session.licence.getLicenceId(), pawnBillStatusFilterId, null,pawnBillSource,metalId);
    		
    		List<PawnBill> pawnBillList = null;
    		
    		if(pawnBillDTO!=null) {
    			pawnBillList = pawnBillDTO.getPawnBillList();
    		}
    		
    		RunReport.printPawnPledgeReport(pawnBillList,fromDateDt,toDateDt);
			
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void tableChanged(TableModelEvent e) {
		// TODO Auto-generated method stub
		//System.out.println("tableChanged");
		int row = e.getFirstRow();
	    int column = e.getColumn();
	    if (column == 16) {
	        TableModel model = (TableModel) e.getSource();
	        String columnName = model.getColumnName(column);
	        Boolean checked = (Boolean) model.getValueAt(row, column);
	        Integer billId = (Integer)model.getValueAt(row, 0);
            //System.out.println("BillId ->"+billId);
	        if (checked) {
	            //System.out.println(columnName + ": " + true);
	            PawnBillController.getInstace().updatePawnBillAuctionStatus(billId, "Y");
	        } else {
	            //System.out.println(columnName + ": " + false);
	            PawnBillController.getInstace().updatePawnBillAuctionStatus(billId, "N");
	        }
	        
	        
	        
	    }
	}
	
	private void constructThirdPanel(PawnBillDTO pawnBillDTO) {
		try {
			
			thirdPanelFirst.removeAll();
			
	        //if(pawnBillDTO!=null) {
				thirdPanelFirst.add(new MyLabel("Total no of bill : "));
	        	if(pawnBillDTO!=null && pawnBillDTO.getBillCount()!=null) {
	        		thirdPanelFirst.add(new MyLabel(Calculator.formatThreeDecimal(pawnBillDTO.getBillCount())),"wrap");
	        	}else {
	        		thirdPanelFirst.add(new MyLabel(""),"wrap");	
	        	}
	        	thirdPanelFirst.add(new MyLabel("Total pledged amount : "));
	        	if(pawnBillDTO!=null && pawnBillDTO.getTotalAmount()!=null) {
	        		thirdPanelFirst.add(new MyLabel(Calculator.formatThreeDecimal(pawnBillDTO.getTotalAmount())),"wrap");
	        	}else {
	        		thirdPanelFirst.add(new MyLabel(""),"wrap");	
	        	}
	        	thirdPanelFirst.add(new MyLabel("Total un-redeemed amount < 1 year : "));
	        	if(pawnBillDTO!=null && pawnBillDTO.getPledgeBillTotalAmount()!=null) {
	        		thirdPanelFirst.add(new MyLabel(Calculator.formatThreeDecimal(pawnBillDTO.getPledgeBillTotalAmount())),"wrap");
	        	}else {
	        		thirdPanelFirst.add(new MyLabel(""),"wrap");	
	        	}
	        	thirdPanelFirst.add(new MyLabel("Total un-redeemed amount > 1 year : "));
	        	if(pawnBillDTO!=null && pawnBillDTO.getOldPledgeBillTotalAmount()!=null) {
	        		thirdPanelFirst.add(new MyLabel(Calculator.formatThreeDecimal(pawnBillDTO.getOldPledgeBillTotalAmount())),"wrap");
	        	}else {
	        		thirdPanelFirst.add(new MyLabel(""),"wrap");	
	        	}
	        	thirdPanelFirst.add(new MyLabel("Total redeemed amount : "));
	        	if(pawnBillDTO!=null && pawnBillDTO.getRedemeedBillTotalAmount()!=null) {
	        		thirdPanelFirst.add(new MyLabel(Calculator.formatThreeDecimal(pawnBillDTO.getRedemeedBillTotalAmount())),"wrap");
	        	}else {
	        		thirdPanelFirst.add(new MyLabel(""),"wrap");	
	        	}
	        	
	        //}
	        
	        	thirdPanelFirst.revalidate();
	        	thirdPanelFirst.repaint();
			
		}catch(Exception ex) {
			
		}
	}
	
	private void tagPrint() {
		try {
			
			String fromDateStr = StringUtils.getStrDate(fromDate.getDate());
    		String toDateStr = StringUtils.getStrDate(toDate.getDate());
    		System.out.println("fromDateStr->"+fromDateStr);
    		System.out.println("toDateStr->"+toDateStr);
    		
    		System.out.println("fromDate->"+fromDate.getDate());
    		System.out.println("toDate->"+toDate.getDate());
    		
    		System.out.println("Selected Filter : "+pawnBillStatusFilter.getSelectedItem().toString());
    		
    		String selectedFilter = pawnBillStatusFilter.getSelectedItem().toString();
    		Integer pawnBillStatusFilterId = null;
    		if(!selectedFilter.equalsIgnoreCase("All Record")) {
    			PawnBillStatus billStatus= (PawnBillStatus) pawnBillStatusFilter.getSelectedItem();
    			System.out.println("Id ->"+billStatus.getPawnBillStatusId());
    			pawnBillStatusFilterId = billStatus.getPawnBillStatusId();
    		}
    		
    		Integer metalId = null;
    		String selectedMetalFilter = metalFilter.getSelectedItem().toString();
    		if(!selectedMetalFilter.equalsIgnoreCase("All")) {
    			Metal selectMetal = (Metal) metalFilter.getSelectedItem();
    			metalId = selectMetal.getMetalId();
    		}
    		
    		Date fromDateDt = fromDate.getDate();
    		Date toDateDt = toDate.getDate();
    		BillSource billSourceObj = MasterController.getInstace().getBillSourceByCode(pawnBillSource);
    		PawnBillDTO pawnBillDTO = PawnBillController.getInstace().getPawnBillList(fromDateDt, toDateDt, Session.licence.getLicenceId(), pawnBillStatusFilterId, null,pawnBillSource,metalId);
    		
    		List<PawnBill> pawnBillList = null;
    		
    		if(pawnBillDTO!=null) {
    			pawnBillList = pawnBillDTO.getPawnBillList();
    		}
    		
    		//RunReport.printPawnPledgeReport(pawnBillList,fromDateDt,toDateDt);
    		RunReport.tagPrintReport(pawnBillList);
			
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void barcodePrint() {
		try {
			
			String fromDateStr = StringUtils.getStrDate(fromDate.getDate());
    		String toDateStr = StringUtils.getStrDate(toDate.getDate());
    		System.out.println("fromDateStr->"+fromDateStr);
    		System.out.println("toDateStr->"+toDateStr);
    		
    		System.out.println("fromDate->"+fromDate.getDate());
    		System.out.println("toDate->"+toDate.getDate());
    		
    		System.out.println("Selected Filter : "+pawnBillStatusFilter.getSelectedItem().toString());
    		
    		String selectedFilter = pawnBillStatusFilter.getSelectedItem().toString();
    		Integer pawnBillStatusFilterId = null;
    		if(!selectedFilter.equalsIgnoreCase("All Record")) {
    			PawnBillStatus billStatus= (PawnBillStatus) pawnBillStatusFilter.getSelectedItem();
    			System.out.println("Id ->"+billStatus.getPawnBillStatusId());
    			pawnBillStatusFilterId = billStatus.getPawnBillStatusId();
    		}
    		
    		Integer metalId = null;
    		String selectedMetalFilter = metalFilter.getSelectedItem().toString();
    		if(!selectedMetalFilter.equalsIgnoreCase("All")) {
    			Metal selectMetal = (Metal) metalFilter.getSelectedItem();
    			metalId = selectMetal.getMetalId();
    		}
    		
    		Date fromDateDt = fromDate.getDate();
    		Date toDateDt = toDate.getDate();
    		BillSource billSourceObj = MasterController.getInstace().getBillSourceByCode(pawnBillSource);
    		PawnBillDTO pawnBillDTO = PawnBillController.getInstace().getPawnBillList(fromDateDt, toDateDt, Session.licence.getLicenceId(), pawnBillStatusFilterId, null,pawnBillSource,metalId);
    		
    		List<PawnBill> pawnBillList = null;
    		
    		if(pawnBillDTO!=null) {
    			pawnBillList = pawnBillDTO.getPawnBillList();
    		}
    		
    		//RunReport.printPawnPledgeReport(pawnBillList,fromDateDt,toDateDt);
    		RunReport.pawnBillTagPrintBarcode(pawnBillList);
			
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void print() {
		try {
			String selectedPrintType = printOption.getSelectedItem().toString();
			
			if(selectedPrintType.equalsIgnoreCase("Pledge Report")) {
				pledgeReportPrint();
			}else if(selectedPrintType.equalsIgnoreCase("Remainder Letter")) {
				remainderReport();
			}else if(selectedPrintType.equalsIgnoreCase("Auction Letter")) {
				auctionReport();
			}else if(selectedPrintType.equalsIgnoreCase("Tag (Chitti)")) {
				tagPrint();
			}else if(selectedPrintType.equalsIgnoreCase("Tag (BarCode)")) {
				barcodePrint();
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void selectAllCheckBox() {
		try {
			
			for(int i=0;i<reportModel.getRowCount();i++) {
				//Boolean objectValue = (Boolean)reportTable.getValueAt(i, 1);
				//System.out.println(objectValue);
				if(!(Boolean)reportTable.getValueAt(i, 1)) {
					reportModel.setValueAt(true, i, 1);
				}else {
					reportModel.setValueAt(false, i, 1);
				}
				
				/*if((Boolean)reportModel.getValueAt(i, i)) {
					reportModel.setValueAt(false, i, 1);
				}else {
					reportModel.setValueAt(true, i, 1);
				}*/
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void bulkAuctionSave() {
		try {
			StringBuffer errorMessage = new StringBuffer();
			Boolean dataFlag= false;
			List<Integer> billIdList = new ArrayList<Integer>();
			Date bulkAuctionDateSelected = bulkAuctionDate.getDate();
			String auctionNumberEntered = bulkAuctionNarration.getText().trim();
			String auctionNarractionEntered = bulkAuctionNarration.getText().trim();
			
			for(int i=0;i<reportModel.getRowCount();i++) {
				Boolean objectValue = (Boolean)reportTable.getValueAt(i, 1);
				String status = (String) reportTable.getValueAt(i, 12);
				
				if(objectValue) {
					if(status.equalsIgnoreCase(ConstantUtils.PAWN_BILL_STATUS_PLEDGE)) {
						dataFlag = true;
						billIdList.add((Integer)reportTable.getValueAt(i, 0));
					}else {
						errorMessage.append("One of Selected Row is not in pledge status");
						billIdList.clear();
						break;
					}
				}
				
				/*if(status.equalsIgnoreCase(ConstantUtils.PAWN_BILL_STATUS_PLEDGE)) {
					if(objectValue) {
						dataFlag = true;
					}
				}else {
					errorMessage.append("One of Selected Row is not in pledge status");
					break;
				}*/
			}
			System.out.println("->"+errorMessage+"<-");
			if(!errorMessage.equals("") && !dataFlag) {
				errorMessage.append("\n").append("Atleast select one checkbox in Pledge status");
				JOptionPane.showMessageDialog(null, errorMessage.toString());
			}else if(!errorMessage.toString().equals("")) {
				JOptionPane.showMessageDialog(null, errorMessage.toString());
			}else if(!dataFlag) {
				JOptionPane.showMessageDialog(null, "Atleast select one checkbox");
			}
			
			System.out.println(billIdList);
			if(billIdList.size()>0) {
				Integer saveFlag = PawnBillController.getInstace().savePawnBulkAuction(billIdList,bulkAuctionDateSelected,auctionNumberEntered,auctionNarractionEntered);
				if(saveFlag>0) {
					JOptionPane.showMessageDialog(null, "Bulk Auction Saved!");
					fetchPawnBillingReportList();
				}
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void deleteBill(Integer billId,String deleteStatus,String billStatus) {
		try {
			if(deleteStatus.equalsIgnoreCase(ConstantUtils.PAWN_BILL_STATUS_PLEDGE)) {
				if(billStatus.equalsIgnoreCase(ConstantUtils.PAWN_BILL_STATUS_REDEEM)) {
					int ReportOption=JOptionPane.showConfirmDialog(null,"Both Pledge and Redeem entry will be deleted. Do you want to delete?", "Confirm", JOptionPane.OK_CANCEL_OPTION);
					if(ReportOption==JOptionPane.OK_OPTION){
						deletePledgeEntry(billId);
						deleteRedeemEntry(billId);
						JOptionPane.showMessageDialog(null, "Deleted");
					}
				}else if(billStatus.equalsIgnoreCase(ConstantUtils.PAWN_BILL_STATUS_AUCTION)) {
					int ReportOption=JOptionPane.showConfirmDialog(null,"Both Pledge and Auction entry will be deleted. Do you want to delete?", "Confirm", JOptionPane.OK_CANCEL_OPTION);
					if(ReportOption==JOptionPane.OK_OPTION){
						deletePledgeEntry(billId);
						deleteAuctionEntry(billId);
						JOptionPane.showMessageDialog(null, "Deleted");
					}
				}else {
					deletePledgeEntry(billId);
					JOptionPane.showMessageDialog(null, "Deleted");
				}
			}
			
			if(deleteStatus.equalsIgnoreCase(ConstantUtils.PAWN_BILL_STATUS_REDEEM)) {
				if(billStatus.equalsIgnoreCase(ConstantUtils.PAWN_BILL_STATUS_REDEEM)) {
					int ReportOption=JOptionPane.showConfirmDialog(null,"Do you want to delete?", "Confirm", JOptionPane.OK_CANCEL_OPTION);
					if(ReportOption==JOptionPane.OK_OPTION){
						deleteRedeemEntry(billId);
						updatePledgeStatus(billId, ConstantUtils.PAWN_BILL_STATUS_PLEDGE);
						JOptionPane.showMessageDialog(null, "Deleted");
					}
				}else {
					//Error Message
					JOptionPane.showMessageDialog(null, "The Bill Status is not in Redeem. So unable to delete");
				}
			}
			
			if(deleteStatus.equalsIgnoreCase(ConstantUtils.PAWN_BILL_STATUS_AUCTION)) {
				if(billStatus.equalsIgnoreCase(ConstantUtils.PAWN_BILL_STATUS_AUCTION)) {
					int ReportOption=JOptionPane.showConfirmDialog(null,"Do you want to delete?", "Confirm", JOptionPane.OK_CANCEL_OPTION);
					if(ReportOption==JOptionPane.OK_OPTION){
						deleteAuctionEntry(billId);
						updatePledgeStatus(billId, ConstantUtils.PAWN_BILL_STATUS_PLEDGE);
						JOptionPane.showMessageDialog(null, "Deleted");
					}
				}else {
					//Error Message
					JOptionPane.showMessageDialog(null, "The Bill Status is not in Auction. So unable to delete");
				}
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		fetchPawnBillingReportList();
	}
	
	
	private void deletePledgeEntry(Integer pawnBillId) {
		PawnBillController.getInstace().deletePawnBillEntry(pawnBillId);
	}
	private void deleteRedeemEntry(Integer pawnBillId) {
		PawnBillController.getInstace().deletePawnRedeemEntry(pawnBillId);
	}
	private void deleteAuctionEntry(Integer pawnBillId) {
		PawnBillController.getInstace().deletePawnAuctionEntry(pawnBillId);
	}
	private void updatePledgeStatus(Integer pawnBillId,String billStatus) {
		try {
			PawnBillController.getInstace().updatePawnBillStatus(pawnBillId, PawnBillController.getInstace().getPawnBillStatusByCode(billStatus).getPawnBillStatusId());
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}

}
