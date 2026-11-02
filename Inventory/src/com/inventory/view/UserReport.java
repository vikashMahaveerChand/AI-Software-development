package com.inventory.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import net.miginfocom.swing.MigLayout;

//import com.ibm.media.content.application.mvr.Master;
import com.inventory.components.EvenOddTableRenderer;
import com.inventory.components.MyPanel;
import com.inventory.controller.AppController;
import com.inventory.controller.MasterController;
import com.inventory.model.Licence;
import com.inventory.model.User;
import com.inventory.utility.ConstantUtils;

public class UserReport implements ActionListener{
	private static UserReport myInstance=null;
	
	private UserReport(){}
	
	public static UserReport getInstance(){
		if(myInstance ==null){
			myInstance = new UserReport();
		}
		
		return myInstance;
	}

	JPanel panel;
	JPanel firstPanel,secondPanel,thirdPanel;
	
	JTable reportTable=null;
    DefaultTableModel reportModel =null;
    JScrollPane scrollPane;
    
    private JPopupMenu popupMenu;
    private JMenuItem editRecord,deleteRecord;
    String userType;
    
    public JPanel getPanel(String userType){
		this.userType = userType;
		initComponent();
		constructUserReport();
		fetchUserList();
		return panel;
	}
    
    private void initComponent(){
    	try{
    		panel = new JPanel(new MigLayout());
			
			firstPanel = new JPanel(new MigLayout());
			secondPanel = new JPanel(new MigLayout());
			thirdPanel = new JPanel(new MigLayout());
			
			MyPanel.setBorder("User", panel);
			MyPanel.setBackground(firstPanel);
			MyPanel.setBackground(secondPanel);
			MyPanel.setBackground(thirdPanel);
			
			reportTable = new JTable();
                
            
            popupMenu = new JPopupMenu();
            editRecord = new JMenuItem();
            deleteRecord = new JMenuItem();
            
            editRecord.addActionListener(this);
            deleteRecord.addActionListener(this);
            
    	}catch(Exception ex){
    		
    	}
    }
    
    private void constructUserReport(){
    	try{
    		tableModelling(reportTable);
			editRecord.setText("Edit User");
			deleteRecord.setText("Delete User");
			
			popupMenu.add(editRecord);
			popupMenu.add(deleteRecord);
			
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
    	}catch(Exception ex){
    		
    	}
    }
    
    private void tableModelling(JTable jTable){
		try{
			
			reportModel = new DefaultTableModel() {

				public Class<?> getColumnClass(int column) {
					switch (column) {
					
					case 0:
						return String.class;//User Id
					case 1:
						return String.class;//User Name
					case 2:
						return String.class;//User Type
					        
					default:
						return String.class;
					}
				}
			};
			jTable.setModel(reportModel);
			reportModel.addColumn("userId");
        	reportModel.addColumn("User Name");
        	reportModel.addColumn("User Type");

        	jTable.getColumn("userId").setPreferredWidth(0);
        	jTable.getColumn("userId").setMinWidth(0);
        	jTable.getColumn("userId").setMaxWidth(0);
        	
        	if(userType.equalsIgnoreCase(ConstantUtils.USER_TYPE_ADMIN)){
				
			}else if (userType.equalsIgnoreCase(ConstantUtils.USER_TYPE_GST)){
				
			}else if(userType.equalsIgnoreCase(ConstantUtils.USER_TYPE_NON_GST)){
				
	        	
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
    private void fetchUserList(){
    	try{
    		int rowCount = reportModel.getRowCount();
            for (int i = 0; i < rowCount; i++) {
            	reportModel.removeRow(0);
            }
            
            List<User> userList = null;
            
            userList = MasterController.getInstace().getUserList();
            String temp;
            
            if(userList!=null && userList.size()>0){
            	for(User data:userList){
            		reportModel.addRow(new Object[]{
            				data.getUserId(),
            				data.getUserName(),
            				data.getUserType().getUserType()            				
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
			if(evt ==  editRecord || evt ==  deleteRecord){
				int selectedRow = reportTable.getSelectedRow();
				int userId=0;
				if(selectedRow>-1){
					String selectedUserId = reportModel.getValueAt(selectedRow, 0).toString();
					userId = Integer.parseInt(selectedUserId);
					
					if(evt ==  editRecord){
						AppController.getInstance().addUserFormPage(ConstantUtils.USER_REPORT, userId,userType);
					}else if(evt ==  deleteRecord){
						deleteUser(userId);
					}
				}
				
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private void deleteUser(int userId){
		try{
			int selected = JOptionPane.showConfirmDialog(null, "Are you sure You want to delete User?");
			if(selected==JOptionPane.YES_OPTION){
				JOptionPane.showMessageDialog(null, "Deleted");
				
			}else if(selected==JOptionPane.NO_OPTION){
				JOptionPane.showMessageDialog(null, "No Deleted");
			}
		}catch(Exception ex){
			
		}
	}

}
