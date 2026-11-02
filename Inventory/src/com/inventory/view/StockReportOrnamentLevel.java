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
import com.inventory.controller.StockController;
import com.inventory.dto.StockDTOList;
import com.inventory.dto.StockDTOOrnamentLevel;
import com.inventory.dto.StockDTOOrnamentLevelDisplay;
import com.inventory.utility.Calculator;
import com.inventory.utility.Session;
import com.inventory.utility.StringUtils;

import net.miginfocom.swing.MigLayout;

public class StockReportOrnamentLevel implements DocumentListener, ActionListener, KeyListener, MouseListener {
	
private static StockReportOrnamentLevel myInstance = null;
	
	private StockReportOrnamentLevel() {
	}

	public static StockReportOrnamentLevel getInstance() {
		if (myInstance == null) {
			myInstance = new StockReportOrnamentLevel();
		}

		return myInstance;
	}
	JPanel panel;
	JPanel firstPanel,secondPanel,thirdPanel;
	MyTextField searchTextField = null;
	
	JTable reportTable=null;
    DefaultTableModel reportModel =null;
    JScrollPane scrollPane;
    
    JXDatePicker fromDate,toDate;
	MyButton searchBill;
	Integer ornamentId;
	Date from,to;
	String billType,billSource;
    
    public JPanel getPanel(Integer ornamentId,Date from,Date to,String billType,String billSource){
		try{
			this.ornamentId = ornamentId;
			this.from = from;
			this.to = to;
			this.billType = billType;
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
			
			MyPanel.setBorder("Ornament Stock Report", panel);
			MyPanel.setBackground(firstPanel);
			MyPanel.setBackground(secondPanel);
			MyPanel.setBackground(thirdPanel);
			
			reportTable = new JTable();
			
			
			searchBill.addActionListener(this);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
	}
    
    private void constructStockReport(){
    	try{
    		MyPanel.setBorder("Ornament Stock Report ", panel);
    		
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
						return String.class;//Name
					case 2:
						return String.class;//Date
					case 3:
						return String.class;//Grams					
					default:
						return String.class;
					}
				}
			};
			jTable.setModel(reportModel);
			reportModel.addColumn("ornamentId");
			reportModel.addColumn("Date");
			reportModel.addColumn("Name");
			reportModel.addColumn("Grams");
			/*reportModel.addColumn("Ornament Name");
        	reportModel.addColumn("GST Opening Stock");
        	reportModel.addColumn("NON GST Opening Stock");
        	reportModel.addColumn("GST Sale");
        	reportModel.addColumn("NON GST Sale");
        	reportModel.addColumn("GST Purchase");
        	reportModel.addColumn("NON GST Purchase");
        	reportModel.addColumn("GST Stock");
        	reportModel.addColumn("NON GST Stock");
        	*/
        	jTable.getColumn("ornamentId").setPreferredWidth(0);
        	jTable.getColumn("ornamentId").setMinWidth(0);
        	jTable.getColumn("ornamentId").setMaxWidth(0);

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
		
		if(evt.getSource() == searchBill) {
			fetchStockReportList();
		}
		
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
	
	private void fetchStockReportList(){
		try {
			
			Date fromDateDt = fromDate.getDate();
    		Date toDateDt = toDate.getDate();
    		Integer licenceId = Session.licence.getLicenceId();
			
			StockDTOOrnamentLevel ornLevel = StockController.getInstace().getStockListOrnamentLevel(fromDateDt, toDateDt, licenceId, billSource, ornamentId,billType);
			
			if(ornLevel!=null) {
				int rowCount = reportModel.getRowCount();
	            for (int i = 0; i < rowCount; i++) {
	            	reportModel.removeRow(0);
	            }
	            
	            List<StockDTOOrnamentLevelDisplay> displayList = ornLevel.getDisplayList();
	            
	            if(displayList!=null && displayList.size()>0) {
	            	for(StockDTOOrnamentLevelDisplay data:displayList){
	            		reportModel.addRow(new Object[]{
	            				"",
	            				StringUtils.getStrDate(data.getDate()),
	            				data.getName(),
	            				data.getGrams()
	            		});
	            	}
	            }
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}

}
