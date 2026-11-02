package com.inventory.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import com.inventory.components.EvenOddTableRenderer;
import com.inventory.components.MyPanel;
import com.inventory.controller.AppController;
import com.inventory.controller.MasterController;
import com.inventory.model.Licence;
import com.inventory.utility.ConstantUtils;
import com.inventory.utility.Session;

import net.miginfocom.swing.MigLayout;

public class LicenceReport implements ActionListener{
	private static LicenceReport myInstance=null;
	
	private LicenceReport(){}
	
	public static LicenceReport getInstance(){
		if(myInstance ==null){
			myInstance = new LicenceReport();
		}
		
		return myInstance;
	}

	JPanel panel;
	JPanel firstPanel,secondPanel,thirdPanel;
	
	JTable reportTable=null;
    DefaultTableModel reportModel =null;
    
    private JPopupMenu popupMenu;
    private JMenuItem editRecord,addGSTOpeningStock,addNONGSTOpeningStock,addGSTOpeningBalance,addNONGSTOpeningBalance;
    JScrollPane scrollPane;
    String userType;
	public JPanel getPanel(String userType){
		this.userType = userType;
		initComponent();
		constructReport();
		return panel;
	}
	
	private void initComponent(){
		try{
			panel = new JPanel(new MigLayout());
			
			firstPanel = new JPanel(new MigLayout());
			secondPanel = new JPanel(new MigLayout());
			thirdPanel = new JPanel(new MigLayout());
			
			MyPanel.setBorder("Licence", panel);
			MyPanel.setBackground(firstPanel);
			MyPanel.setBackground(secondPanel);
			MyPanel.setBackground(thirdPanel);
			
			reportTable = new JTable();
                
            
            popupMenu = new JPopupMenu();
            
            editRecord = new JMenuItem();
            addGSTOpeningStock = new JMenuItem();
            addNONGSTOpeningStock = new JMenuItem();
            addGSTOpeningBalance = new JMenuItem();
            addNONGSTOpeningBalance = new JMenuItem();
            
            editRecord.addActionListener(this);
            addGSTOpeningStock.addActionListener(this);
            addNONGSTOpeningStock.addActionListener(this);
            addGSTOpeningBalance.addActionListener(this);
            addNONGSTOpeningBalance.addActionListener(this);
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	private void constructReport(){
		try{
			tableModelling(reportTable);
			editRecord.setText("Edit Licence");
			
			if(userType.equalsIgnoreCase(ConstantUtils.USER_TYPE_ADMIN)){
				constructAdminLicenceReport();
			}else if (userType.equalsIgnoreCase(ConstantUtils.USER_TYPE_GST)){
				constructGSTLicenceReport();
			}else if(userType.equalsIgnoreCase(ConstantUtils.USER_TYPE_NON_GST)){
				constructNONGSTLicenceReport();
			}
			
			reportTable.setComponentPopupMenu(popupMenu);
            
            reportTable.setBackground(Color.WHITE);
            scrollPane = new JScrollPane(reportTable);
            Dimension d = reportTable.getPreferredSize();
            int rows=30;
            scrollPane.setPreferredSize(
            new Dimension(d.width,reportTable.getRowHeight()*rows+1));
            secondPanel.add(scrollPane,"span,pushx,growx,wrap");
            
            EvenOddTableRenderer render =new EvenOddTableRenderer();
            reportTable.setDefaultRenderer(Object.class,  render);
            reportTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            
            panel.add(firstPanel,"span,pushx,growx,wrap");
            panel.add(secondPanel,"span,pushx,growx,wrap");
            panel.add(thirdPanel,"span,pushx,growx,wrap");
            
            fetchLicenceList();
            
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	private void constructAdminLicenceReport(){
		try{
			
			
			
			addGSTOpeningStock.setText("Add/Edit GST Opening Stock");
			addNONGSTOpeningStock.setText("Add/Edit NONGST Opening Stock");
			addGSTOpeningBalance.setText("Add/Edit GST Opening Balance");
			addNONGSTOpeningBalance.setText("Add/Edit NONGST Opening Balance");
			
			popupMenu.add(editRecord);
            //popupMenu.add(addGSTOpeningStock);
            //popupMenu.add(addNONGSTOpeningStock);
            //popupMenu.add(addGSTOpeningBalance);
            //popupMenu.add(addNONGSTOpeningBalance);
            
            
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	private void constructGSTLicenceReport(){
		try{
			addGSTOpeningStock.setText("Add/Edit Opening Stock");
			addGSTOpeningBalance.setText("Add/Edit Opening Balance");
			
			popupMenu.add(editRecord);
            //popupMenu.add(addGSTOpeningStock);
            //popupMenu.add(addGSTOpeningBalance);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
	}
	private void constructNONGSTLicenceReport(){
		try{
			addNONGSTOpeningStock.setText("Add/Edit Opening Stock");
			addNONGSTOpeningBalance.setText("Add/Edit Opening Balance");
			
			popupMenu.add(editRecord);
            //popupMenu.add(addNONGSTOpeningStock);
            //popupMenu.add(addNONGSTOpeningBalance);
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
						return String.class;//Licence Id
					case 1:
						return String.class;//Licence Name
					case 2:
						return String.class;//Address
					case 3:
						return String.class;//LAnd Line
					case 4:
						return String.class;//Email
					case 5:
						return String.class;//GST
					case 6:
						return String.class;//Bank
					        
					default:
						return String.class;
					}
				}
			};
			jTable.setModel(reportModel);
			reportModel.addColumn("LicenceId");
        	reportModel.addColumn("Licence Name");
        	reportModel.addColumn("Address");
        	reportModel.addColumn("Land Line");
        	reportModel.addColumn("Email");
        	reportModel.addColumn("GST Number");
        	reportModel.addColumn("Bank Details");
        	
        	jTable.getColumn("LicenceId").setPreferredWidth(0);
        	jTable.getColumn("LicenceId").setMinWidth(0);
        	jTable.getColumn("LicenceId").setMaxWidth(0);
        	
        	if(userType.equalsIgnoreCase(ConstantUtils.USER_TYPE_ADMIN)){
				
			}else if (userType.equalsIgnoreCase(ConstantUtils.USER_TYPE_GST)){
				
			}else if(userType.equalsIgnoreCase(ConstantUtils.USER_TYPE_NON_GST)){
				jTable.getColumn("Address").setPreferredWidth(0);
	        	jTable.getColumn("Address").setMinWidth(0);
	        	jTable.getColumn("Address").setMaxWidth(0);
	        	
	        	jTable.getColumn("Land Line").setPreferredWidth(0);
	        	jTable.getColumn("Land Line").setMinWidth(0);
	        	jTable.getColumn("Land Line").setMaxWidth(0);
	        	
	        	jTable.getColumn("Email").setPreferredWidth(0);
	        	jTable.getColumn("Email").setMinWidth(0);
	        	jTable.getColumn("Email").setMaxWidth(0);
	        	
	        	jTable.getColumn("GST Number").setPreferredWidth(0);
	        	jTable.getColumn("GST Number").setMinWidth(0);
	        	jTable.getColumn("GST Number").setMaxWidth(0);
	        	
	        	jTable.getColumn("Bank Details").setPreferredWidth(0);
	        	jTable.getColumn("Bank Details").setMinWidth(0);
	        	jTable.getColumn("Bank Details").setMaxWidth(0);
	        	
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private void fetchLicenceList(){
		try{
			int rowCount = reportModel.getRowCount();
            for (int i = 0; i < rowCount; i++) {
            	reportModel.removeRow(0);
            }
            
            List<Licence> licence = null;
            
            licence = MasterController.getInstace().getLicenceList();
            String temp;
            
            if(licence!=null && licence.size()>0){
            	for(Licence data:licence){
            		reportModel.addRow(new Object[]{
            				data.getLicenceId(),
            				data.getLicenceName(),
            				data.getAddress(),
            				data.getLandLine(),
            				data.getEmail(),
            				data.getGstNumber(),            				
            				(data.getBank()!=null ? data.getBank().getAccountHolderName() + " - " + data.getBank().getAccountNumber() : "")
            		});
            	}
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
	
	public void onClickEvent(Object evt){
		try{
			if(evt ==  editRecord || evt ==  addGSTOpeningStock || evt ==  addNONGSTOpeningStock || evt ==  addGSTOpeningBalance || evt ==  addNONGSTOpeningBalance){
				int selectedRow = reportTable.getSelectedRow();
				int licenceId=0;
				if(selectedRow>-1){
					String selectedLicenceId = reportModel.getValueAt(selectedRow, 0).toString();
					licenceId = Integer.parseInt(selectedLicenceId);
					
					if(evt ==  editRecord){
						AppController.getInstance().addLicenceFormPage(ConstantUtils.LICENCE_REPORT, licenceId,userType);
					}else if(evt == addGSTOpeningStock){
						AppController.getInstance().addOrnamentReportPage(Session.user.getUserType().getUserType(),ConstantUtils.ADD_GST_OPENING_STOCK);
					}else if(evt == addNONGSTOpeningStock){
						AppController.getInstance().addOrnamentReportPage(Session.user.getUserType().getUserType(),ConstantUtils.ADD_NONGST_OPENING_STOCK);
					}
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

}

