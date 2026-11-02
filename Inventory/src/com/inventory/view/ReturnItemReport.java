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

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
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
import com.inventory.utility.ConstantUtils;

import net.miginfocom.swing.MigLayout;

public class ReturnItemReport implements DocumentListener, ActionListener, KeyListener, MouseListener {
	private static ReturnItemReport myInstance = null;
	
	private ReturnItemReport() {
	}

	public static ReturnItemReport getInstance() {
		if (myInstance == null) {
			myInstance = new ReturnItemReport();
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
	
	JXDatePicker fromDate,toDate;
	MyButton searchBill;
	
	private JPopupMenu popupMenu;
	//private JMenuItem viewOrnamentStockDetails;
	
    public JPanel getPanel(String calledFrom,String billType){
    	this.calledFrom = calledFrom;
    	this.billType = billType;
    	
    	initComponent();
    	constructReturnItemReport();
		fetchReturnItemReportList();
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
			
			searchBill = new MyButton("Search");
			
			MyPanel.setBorder("Return Item Report"+billType, panel);
			MyPanel.setBackground(firstPanel);
			MyPanel.setBackground(secondPanel);
			MyPanel.setBackground(thirdPanel);
			
			reportTable = new JTable();
			
			popupMenu = new JPopupMenu();
			
			searchBill.addActionListener(this);
    		
    	}catch(Exception ex) {
    		ex.printStackTrace();
    	}
    }
    
    private void constructReturnItemReport() {
    	try {
    		MyPanel.setBorder("Return Item Report "+billType, panel);
    		
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
            reportTable.setComponentPopupMenu(popupMenu);
            
            panel.add(firstPanel,"span,pushx,growx,wrap");
            panel.add(secondPanel,"span,pushx,growx,wrap");
            panel.add(thirdPanel,"span,pushx,growx,wrap");
            
    	}catch(Exception ex) {
    		ex.printStackTrace();
    	}
    	
    }
    
    private void fetchReturnItemReportList() {
    	
    }
    
    private void tableModelling(JTable jTable){
		try{
			
			reportModel = new DefaultTableModel() {

				public Class<?> getColumnClass(int column) {
					switch (column) {
					
					case 0:
						return String.class;//Bill Details Id
					case 1:
						return String.class;//Bill No
					case 2:
						return String.class;//Bill Date
					case 3:
						return String.class;//Name
					case 4:
						return String.class;//Gross Weight
					case 5:
						return String.class;//Net Weight
					case 6:
						return String.class;//Amount
					default:
						return String.class;
					}
				}
			};
			jTable.setModel(reportModel);
			reportModel.addColumn("billDetailsId");
			reportModel.addColumn("Bill No");
        	reportModel.addColumn("Date");
        	reportModel.addColumn("Name");
        	reportModel.addColumn("Gross weight");
        	reportModel.addColumn("Net weight");
        	reportModel.addColumn("Amount");

        	jTable.getColumn("billDetailsId").setPreferredWidth(0);
        	jTable.getColumn("billDetailsId").setMinWidth(0);
        	jTable.getColumn("billDetailsId").setMaxWidth(0);

        	
		}catch(Exception ex){
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

}
