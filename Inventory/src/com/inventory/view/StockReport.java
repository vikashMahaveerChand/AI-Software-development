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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import javax.swing.WindowConstants;
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
import com.inventory.controller.AppController;
import com.inventory.controller.StockController;
import com.inventory.dto.BillDTO;
import com.inventory.dto.StockDTO;
import com.inventory.dto.StockDTOList;
import com.inventory.model.Bill;
import com.inventory.utility.Calculator;
import com.inventory.utility.ConstantUtils;
import com.inventory.utility.Session;
import com.inventory.utility.StringUtils;

import net.miginfocom.swing.MigLayout;

public class StockReport implements DocumentListener, ActionListener, KeyListener, MouseListener {

	private static StockReport myInstance = null;
	
	private StockReport() {
	}

	public static StockReport getInstance() {
		if (myInstance == null) {
			myInstance = new StockReport();
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
	
	private JPopupMenu popupMenu;
	private JMenuItem viewOrnamentStockDetails;
	
	JDialog dialog = null;
	JPanel ornamentViewPanel;
	
	public JPanel getPanel(String calledFrom,String billType,String metal,String billSource){
		try{
			this.calledFrom = calledFrom;
			this.billType = billType;
			this.metal = metal;
			this.billSource = billSource;
			
			initComponent();
			constructStockReport();
			fetchStockReportList();
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
			
			//dialog = new JDialog();
			//Window parentWindow = SwingUtilities.getWindowAncestor(panel);
			//dialog = new JDialog(parentWindow, ModalityType.APPLICATION_MODAL);
			
			MyPanel.setBorder("Stock Report"+billType, panel);
			MyPanel.setBackground(firstPanel);
			MyPanel.setBackground(secondPanel);
			MyPanel.setBackground(thirdPanel);
			
			reportTable = new JTable();
			
			popupMenu = new JPopupMenu();
			viewOrnamentStockDetails = new JMenuItem();
			
			searchBill.addActionListener(this);
			viewOrnamentStockDetails.addActionListener(this);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
	}
	
	private void constructStockReport(){
    	try{
    		MyPanel.setBorder("Stock Report "+billType, panel);
    		
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
            firstPanel.add(new MyLabel("Search Bill"),"push,al right");
            firstPanel.add(searchTextField,"al right, wrap");
            
            //firstPanel.add(new MyLabel("Search Bill"),"push,al right");
            //firstPanel.add(searchTextField,"al right, wrap");
            
            viewOrnamentStockDetails.setText("View Ornament Stock Details");
            
            popupMenu.add(viewOrnamentStockDetails);
            reportTable.setComponentPopupMenu(popupMenu);
            
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
						return String.class;//Ornament Id
					case 1:
						return String.class;//Ornament Name
					case 2:
						return String.class;//GST Opening stock
					case 3:
						return String.class;//NON GST OPENING STOCK
					case 4:
						return String.class;//GST SALE
					case 5:
						return String.class;//NONGST SALE
					case 6:
						return String.class;//GST Purchase
					case 7:
						return String.class;//NONGST Purchase
					case 8:
						return String.class;//GST Stock
					case 9:
						return String.class;//NONGST Stock
					default:
						return String.class;
					}
				}
			};
			jTable.setModel(reportModel);
			reportModel.addColumn("ornamentId");
			reportModel.addColumn("Ornament Name");
        	reportModel.addColumn("GST Opening Stock");
        	reportModel.addColumn("NON GST Opening Stock");
        	reportModel.addColumn("GST Sale");
        	reportModel.addColumn("NON GST Sale");
        	reportModel.addColumn("GST Purchase");
        	reportModel.addColumn("NON GST Purchase");
        	reportModel.addColumn("GST Stock");
        	reportModel.addColumn("NON GST Stock");

        	jTable.getColumn("ornamentId").setPreferredWidth(0);
        	jTable.getColumn("ornamentId").setMinWidth(0);
        	jTable.getColumn("ornamentId").setMaxWidth(0);

        	if(billType.equalsIgnoreCase(ConstantUtils.GST_ENTRY)) {
        		jTable.getColumn("NON GST Opening Stock").setPreferredWidth(0);
            	jTable.getColumn("NON GST Opening Stock").setMinWidth(0);
            	jTable.getColumn("NON GST Opening Stock").setMaxWidth(0);
            	
            	jTable.getColumn("NON GST Sale").setPreferredWidth(0);
            	jTable.getColumn("NON GST Sale").setMinWidth(0);
            	jTable.getColumn("NON GST Sale").setMaxWidth(0);
            	
            	jTable.getColumn("NON GST Purchase").setPreferredWidth(0);
            	jTable.getColumn("NON GST Purchase").setMinWidth(0);
            	jTable.getColumn("NON GST Purchase").setMaxWidth(0);
            	
            	jTable.getColumn("NON GST Stock").setPreferredWidth(0);
            	jTable.getColumn("NON GST Stock").setMinWidth(0);
            	jTable.getColumn("NON GST Stock").setMaxWidth(0);
            	
        	}else if(billType.equalsIgnoreCase(ConstantUtils.NON_GST_ENTRY)) {
        		jTable.getColumn("GST Opening Stock").setPreferredWidth(0);
            	jTable.getColumn("GST Opening Stock").setMinWidth(0);
            	jTable.getColumn("GST Opening Stock").setMaxWidth(0);
            	
            	jTable.getColumn("GST Sale").setPreferredWidth(0);
            	jTable.getColumn("GST Sale").setMinWidth(0);
            	jTable.getColumn("GST Sale").setMaxWidth(0);
            	
            	jTable.getColumn("GST Purchase").setPreferredWidth(0);
            	jTable.getColumn("GST Purchase").setMinWidth(0);
            	jTable.getColumn("GST Purchase").setMaxWidth(0);
            	
            	jTable.getColumn("GST Purchase").setPreferredWidth(0);
            	jTable.getColumn("GST Purchase").setMinWidth(0);
            	jTable.getColumn("GST Purchase").setMaxWidth(0);
        	}
        	
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
			fetchStockReportList();
		}else if(evt == viewOrnamentStockDetails) {
			viewOrnamentStock();
		}
	}
	
	private void fetchStockReportList(){
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
    		
    		//StockDTO stockDTO = StockController.getInstace().getStockList(fromDateDt, toDateDt, licenceId, billSource,billType);
    		StockDTO stockDTO = StockController.getInstace().getStockListNew(fromDateDt, toDateDt, licenceId, billSource,billType);
    		List<StockDTOList> stockList = stockDTO.getStockDTOList();
    		
    		System.out.println(stockList);
    		
    		int rowCount = reportModel.getRowCount();
            for (int i = 0; i < rowCount; i++) {
            	reportModel.removeRow(0);
            }
            
            if(stockList!=null && stockList.size()>0){
            	for(StockDTOList data:stockList){
            		reportModel.addRow(new Object[]{
            				data.getOrnamentId(),
            				data.getOrnamentName(),
            				data.getGstOpeningStock()!=null?Calculator.formatThreeDecimal(data.getGstOpeningStock()):"",
    						data.getNonGSTOpeningStock()!=null?Calculator.formatThreeDecimal(data.getNonGSTOpeningStock()):"",
							data.getGstSale()!=null?Calculator.formatThreeDecimal(data.getGstSale()):"",
							data.getNonGstSale()!=null?Calculator.formatThreeDecimal(data.getNonGstSale()):"",
							data.getGstPurchase()!=null?Calculator.formatThreeDecimal(data.getGstPurchase()):"",
							data.getNonGstPurchase()!=null?Calculator.formatThreeDecimal(data.getNonGstPurchase()):"",
							data.getGstStock()!=null?Calculator.formatThreeDecimal(data.getGstStock()):"",
							data.getNonGstStock()!=null?Calculator.formatThreeDecimal(data.getNonGstStock()):""
            		});
            	}
            	constructSummaryPanel(stockDTO);
            }
            
    		
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void viewOrnamentStock() {
		try {
			
			int selectedRow = reportTable.getSelectedRow();
			Date fromDateDt = fromDate.getDate();
    		Date toDateDt = toDate.getDate();
    		
			if(selectedRow>-1){
				Integer ornamentIdSelected = (Integer)reportTable.getValueAt(selectedRow, 0);
				
				dialog = new JDialog(AppController.getInstance().frame,"Oranment Stock Details");
				ornamentViewPanel = new JPanel(new MigLayout());
				dialog.setSize(1000,600);
				
				final Toolkit toolkit = Toolkit.getDefaultToolkit();
				final Dimension screenSize = toolkit.getScreenSize();
				final int x = (screenSize.width - dialog.getWidth()) / 2;
				final int y = (screenSize.height - dialog.getHeight()) / 2;
				dialog.setLocation(x, y);	
				
				dialog.add(StockReportOrnamentLevel.getInstance().getPanel(ornamentIdSelected, fromDateDt, toDateDt, billType,billSource));
				
				dialog.setVisible(true);
				dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);		
				
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void constructSummaryPanel(StockDTO stockDTO) {
		try {
			thirdPanel.removeAll();
			if(stockDTO!=null) {
				if(billType.equalsIgnoreCase(ConstantUtils.GST_ENTRY)) {
					thirdPanel.add(new MyLabel("Total GST Stock :"));
					thirdPanel.add(new MyLabel(stockDTO.getTotalGSTStockStr()),"wrap");
					
					/*if(stockDTO.getGstStock()!=null) {
						//thirdPanel.add(new MyLabel(Calculator.formatTwoDecimal(stockDTO.getGstStock())),"wrap");
						thirdPanel.add(new MyLabel(stockDTO.getGstStockStr()),"wrap");
					}else {
						thirdPanel.add(new MyLabel(""),"wrap");
					}*/
				}
				if(billType.equalsIgnoreCase(ConstantUtils.NON_GST_ENTRY)) {
					thirdPanel.add(new MyLabel("Total NON GST Stock :"));
					thirdPanel.add(new MyLabel(stockDTO.getTotalNONGSTStockStr()),"wrap");
					
					/*if(stockDTO.getNonGstStock()!=null) {
						//thirdPanel.add(new MyLabel(Calculator.formatTwoDecimal(stockDTO.getNonGstStock())),"wrap");
						thirdPanel.add(new MyLabel(stockDTO.getNonGstStockStr()),"wrap");
					}else {
						thirdPanel.add(new MyLabel(""),"wrap");
					}*/
				}
				
			}
			thirdPanel.revalidate();
			thirdPanel.repaint();
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
	}

}
