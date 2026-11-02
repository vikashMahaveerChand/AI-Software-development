package com.inventory.view;

import java.awt.Color;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXDatePicker;

import com.inventory.common.FilterJtable;
import com.inventory.components.MyButton;
import com.inventory.components.MyLabel;
import com.inventory.components.MyPanel;
import com.inventory.components.MyTextField;
import com.inventory.components.TableRendererForPawnReport;
import com.inventory.controller.PawnBillController;
import com.inventory.dto.PawnDailyReportDTO;
import com.inventory.dto.PawnDailyReportDTOList;
import com.inventory.utility.Session;
import com.inventory.utility.StringUtils;

import net.miginfocom.swing.MigLayout;

public class PawnDailyReport implements ActionListener,KeyListener,TableModelListener {
	
private static PawnDailyReport myInstance=null;
	
	private PawnDailyReport(){}
	
	public static PawnDailyReport getInstance(){
		if(myInstance ==null){
			myInstance = new PawnDailyReport();
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
    
    JXDatePicker fromDate,toDate;
    MyButton searchBill;
    String pawnBillSource,calledFrom,billType,metal;
    JDialog dialog = null;
    
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
			
			searchBill = new MyButton("Search");
			
			MyPanel.setBorder("Daily Reports", panel);
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

			searchBill.addActionListener(this);
            //editPawnBillRecord,editRedeemRecord,deletePawnBillRecord,deleteRedeemRecord,editQuickData
    		
    	}catch(Exception ex) {
    		ex.printStackTrace();
    	}
    }
    
    private void constructBillingReport(){
    	try{
    		MyPanel.setBorder("Pawn Daily Report"+pawnBillSource, panel);
    		
    		tableModelling(reportTable);
			/*editRecord.setText("Edit Bill");
			deleteRecord.setText("Delete Bill");
			enableDisableRecord.setText("Enable/Disable");
			
			popupMenu.add(editRecord);
			popupMenu.add(deleteRecord);*/
			//popupMenu.add(enableDisableRecord);
			

			reportTable.setComponentPopupMenu(popupMenu);
            
            reportTable.setBackground(Color.WHITE);
            scrollPane = new JScrollPane(reportTable);
            Dimension d = reportTable.getPreferredSize();
            int rows=40;
            scrollPane.setPreferredSize(
            new Dimension(d.width,reportTable.getRowHeight()*rows+1));
            secondPanel.add(scrollPane,"span,pushx,growx,wrap");
            
            //TableRendererForPawnReport render =new TableRendererForPawnReport();
            //reportTable.setDefaultRenderer(Object.class,  render);
            reportTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            
            firstPanel.add(new MyLabel("From Date:"));
            firstPanel.add(fromDate);
            firstPanel.add(new MyLabel("To Date:"));
            firstPanel.add(toDate);
            firstPanel.add(searchBill);
            
            /*firstPanel.add(pledgeReport);
            firstPanel.add(remainderReport);
            firstPanel.add(auctionReport);*/
            firstPanel.add(new MyLabel("Search Bill"),"push,al right");
            firstPanel.add(searchTextField,"al right, wrap");
            
            
            
            
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
						return String.class;//Date
					case 2:
						return String.class;//Description
					case 3:
						return String.class;//Pledge Amount
					case 4:
						return String.class;//Bill Charge
					case 5:
						return String.class;//Interest
					case 6:
						return String.class;//Redeem Bill Amount
					case 7:
						return String.class;//Redeem Interest Received	
					case 8:
						return String.class;//Redeem Amount Received					
					default:
						return String.class;
					}
				}
			};
			jTable.setModel(reportModel);
			reportModel.addColumn("billId");
			reportModel.addColumn("Date");
        	reportModel.addColumn("Description");
        	reportModel.addColumn("Pledge Amount");
        	reportModel.addColumn("Bill Charges");
        	reportModel.addColumn("Pledge Interest");
        	reportModel.addColumn("Redeem Bill Amount");        	
        	reportModel.addColumn("Redeem Interest Received");
        	reportModel.addColumn("Redeem Amount Received");

        	jTable.getColumn("billId").setPreferredWidth(0);
        	jTable.getColumn("billId").setMinWidth(0);
        	jTable.getColumn("billId").setMaxWidth(0);
        	
        	
        	        	
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
    
    private void fetchPawnBillingReportList(){
    	try {
    		String fromDateStr = StringUtils.getStrDate(fromDate.getDate());
    		String toDateStr = StringUtils.getStrDate(toDate.getDate());
    		
    		PawnDailyReportDTO pawnDailyReportDTO = PawnBillController.getInstace().getPawnDailyReport(fromDate.getDate(), toDate.getDate(), Session.licence.getLicenceId());
    		
    		int rowCount = reportModel.getRowCount();
            for (int i = 0; i < rowCount; i++) {
            	reportModel.removeRow(0);
            }
            
            if(pawnDailyReportDTO!=null && pawnDailyReportDTO.getPawnDailyReportDTOList()!=null && pawnDailyReportDTO.getPawnDailyReportDTOList().size()>0) {
            	List<PawnDailyReportDTOList> pawnDailyReportDTOList = pawnDailyReportDTO.getPawnDailyReportDTOList();
            	
            	pawnDailyReportDTOList.forEach(data ->{
            		
            		reportModel.addRow(new Object[]{
            				data.getPawnBillId(),
            				data.getDate(),
                    		data.getDescription(),
                    		data.getPledgeAmount(),
                    		data.getBillCharges(),
                    		data.getInterest(),
                    		data.getRedeemPledgeAmount(),
                    		data.getRedeemInterestAmount(),
                    		data.getRedeemAmountReceived()
                    		
            		});
            		
            	});
            }
            
            
    		
    	}catch(Exception ex) {
    		ex.printStackTrace();
    	}
    }

	@Override
	public void tableChanged(TableModelEvent e) {
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
	
	private void onClickEvent(Object evt){
		try {
			if(evt==searchBill){
				fetchPawnBillingReportList();
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}

}
