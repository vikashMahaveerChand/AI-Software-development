package com.inventory.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import com.inventory.common.FilterJtable;
import com.inventory.components.EvenOddTableRenderer;
import com.inventory.components.MyLabel;
import com.inventory.components.MyPanel;
import com.inventory.components.MyTextField;
import com.inventory.components.TableRendererForContact;
import com.inventory.controller.AppController;
import com.inventory.controller.MasterController;
import com.inventory.dto.LicenceOpeningStockDetailsDTO;
import com.inventory.model.Contact;
import com.inventory.utility.ConstantUtils;
import com.inventory.utility.Session;

import net.miginfocom.swing.MigLayout;

public class ContactReport  implements ActionListener {
	
	private static ContactReport myInstance=null;
	
	private ContactReport(){}
	
	public static ContactReport getInstance(){
		if(myInstance ==null){
			myInstance = new ContactReport();
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
    private JMenuItem editRecord,deleteRecord,enableDisableRecord;
    String contactType,calledFrom;
    JDialog dialog = null;
    
    public JPanel getPanel(String contactType,String calledFrom){
    	try{
    		this.contactType = contactType;
    		this.calledFrom = calledFrom;
    		initComponent();
    		if(calledFrom.equalsIgnoreCase(ConstantUtils.MENU)){
    			constructContactReport();
    			fetchContactReportList(contactType);
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
			
			MyPanel.setBorder("Contact Reports", panel);
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
            
            editRecord.addActionListener(this);
            deleteRecord.addActionListener(this);
            enableDisableRecord.addActionListener(this);
            
    	}catch(Exception ex){
    		ex.printStackTrace();
    	}
    }
    
    private void constructContactReport(){
    	try{
    		MyPanel.setBorder("Contact Report", panel);
    		
    		tableModelling(reportTable);
			editRecord.setText("Edit Contact");
			deleteRecord.setText("Delete Contact");
			enableDisableRecord.setText("Enable/Disable");
			
			popupMenu.add(editRecord);
			//popupMenu.add(deleteRecord);
			//popupMenu.add(enableDisableRecord);
			
			reportTable.setComponentPopupMenu(popupMenu);
            
            reportTable.setBackground(Color.WHITE);
            scrollPane = new JScrollPane(reportTable);
            Dimension d = reportTable.getPreferredSize();
            int rows=30;
            scrollPane.setPreferredSize(
            new Dimension(d.width,reportTable.getRowHeight()*rows+1));
            secondPanel.add(scrollPane,"span,pushx,growx,wrap");
            
            //EvenOddTableRenderer render =new EvenOddTableRenderer();
            TableRendererForContact render = new TableRendererForContact();
            reportTable.setDefaultRenderer(Object.class,  render);
            reportTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            
            firstPanel.add(new MyLabel("Search Contacts"),"push,al right");
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
						return String.class;//Contact Id
					case 1:
						return String.class;//Is Enabled
					case 2:
						return String.class;//Contact Name
					case 3:
						return String.class;//Address
					case 4:
						return String.class;//Mobile        
					default:
						return String.class;
					}
				}
			};
			jTable.setModel(reportModel);
			reportModel.addColumn("contactId");
			reportModel.addColumn("isEnabled");
        	reportModel.addColumn("Name");
        	reportModel.addColumn("Address");
        	reportModel.addColumn("Mobile");

        	jTable.getColumn("contactId").setPreferredWidth(0);
        	jTable.getColumn("contactId").setMinWidth(0);
        	jTable.getColumn("contactId").setMaxWidth(0);
        	
        	jTable.getColumn("isEnabled").setPreferredWidth(0);
        	jTable.getColumn("isEnabled").setMinWidth(0);
        	jTable.getColumn("isEnabled").setMaxWidth(0);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
    
    private void fetchContactReportList(String contactType){
    	try{
    		List<Contact> contactList = MasterController.getInstace().getContactList(contactType);
    		
    		int rowCount = reportModel.getRowCount();
            for (int i = 0; i < rowCount; i++) {
            	reportModel.removeRow(0);
            }
            
            if(contactList!=null && contactList.size()>0){
            	for(Contact data:contactList){
            		reportModel.addRow(new Object[]{
            				data.getContactId(),
            				data.getIsEnabled(),
            				data.getContactName(),
            				data.getDoorNo()+" "+data.getStreet().getStreetName()+" "+data.getStreet().getArea().getAreaName()+" "+data.getStreet().getArea().getCity().getCityName()+" "
            				+data.getStreet().getArea().getPincode()+" "+data.getStreet().getArea().getCity().getState().getStateName(),
            				data.getMobile()
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
	
	private void onClickEvent(Object evt){
		try{
			if(evt ==  editRecord || evt ==  deleteRecord || evt == enableDisableRecord){
				int selectedRow = reportTable.getSelectedRow();
				int contactId=0;
				
				if(selectedRow>-1){
					String selectedId = reportModel.getValueAt(selectedRow, 0).toString();
					Integer contactIdInCell = (Integer)reportTable.getValueAt(selectedRow, 0);
					contactId = contactIdInCell;
					
					if(evt ==  editRecord){
						AppController.getInstance().addContactFormPage(contactId, contactType, ConstantUtils.CONTACT_REPORT, Session.licence.getLicenceId());
					}
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

}
