package com.inventory.view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import net.miginfocom.swing.MigLayout;

import com.inventory.components.MyButton;
import com.inventory.components.MyLabel;
import com.inventory.components.MyPanel;
import com.inventory.components.MyTextField;
import com.inventory.controller.AppController;
import com.inventory.controller.MasterController;
import com.inventory.model.Bank;
import com.inventory.model.User;
import com.inventory.model.UserType;
import com.inventory.utility.ConstantUtils;

public class UserForm  implements DocumentListener, ActionListener, KeyListener, MouseListener{
	
	private static UserForm myInstance = null;
	User userObj = null;

	private UserForm() {
	}

	public static UserForm getInstance() {
		if (myInstance == null) {
			myInstance = new UserForm();
		}

		return myInstance;
	}

	JPanel panel;
	String calledFrom;
	Integer userId;
	MyTextField userName, password;
	JComboBox userType;
	MyButton save;
	String userTypeStr;
	List<UserType> UserTypeList = null;
	
	public JPanel getPanel(String calledFrom,Integer userId,String userTypeStr){
		this.calledFrom = calledFrom;
		this.userId = userId;
		this.userTypeStr = userTypeStr;
		
		initComponent();
		constructUserForm();
		if(userId>0){
			loadUser();
		}
		return panel;
	}
	private void initComponent(){
		try{
			panel = new JPanel(new MigLayout());
			
			userName=new MyTextField(20); 
			password=new MyTextField(20);
			
			save = new MyButton("Save");
			
			userType = new JComboBox();
			userType.setSize(300, 10);
			UserTypeList = MasterController.getInstace().getUserTypeList();
            
            for(UserType obj:UserTypeList){
            	userType.addItem(obj);
            }
			
            save.addActionListener(this);
            userType.addActionListener(this);
            
            save.addKeyListener(this);
            userType.addKeyListener(this);
            
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
	}
	private void constructUserForm(){
		try{
			MyPanel.setBorder("User Form", panel);
			
			panel.add(new MyLabel("User Name : "));
			panel.add(userName, "wrap");

			panel.add(new MyLabel("Password : "));
			panel.add(password, "wrap");
			
			panel.add(new MyLabel("User Type : "));
			panel.add(userType, "wrap");
			
			panel.add(save);
			
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
	public void mousePressed(MouseEvent evt) {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent evt) {
		// TODO Auto-generated method stub
		if(evt.getKeyCode() == KeyEvent.VK_ENTER){
			onClickEvent(evt.getSource());
		}
		
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
		if(evt == userType){
			((Component) evt).transferFocus();
			
		}else{
		//JOptionPane.showMessageDialog(null, "onclcik");
			if(evt == save){
				saveOrUpdateUser();
			}
		}
	}
	
	private void saveOrUpdateUser(){
		try{
			userObj = new User();
			if(userId!=null && userId>0){
				userObj.setUserId(userId);
			}
			
			if(!userName.getText().trim().equalsIgnoreCase("")){
				userObj.setUserName(userName.getText().trim());
			}
			
			if(!password.getText().trim().equalsIgnoreCase("")){
				userObj.setPassword(password.getText().trim());
			}
			
			if(userType.getSelectedItem()!=null){
				userObj.setUserType((UserType)userType.getSelectedItem());
			}
			userObj.setDeletedFlag(ConstantUtils.CONSTANT_N);
			
			userId = MasterController.getInstace().saveOrUpdateUser(userObj);
			if(userId!=null && userId>0){
				JOptionPane.showMessageDialog(null, "User Saved!!");
				if(calledFrom.equalsIgnoreCase(ConstantUtils.MENU)){
					AppController.getInstance().addUserFormPage(ConstantUtils.MENU, 0,userTypeStr);
				}else if(calledFrom.equalsIgnoreCase(ConstantUtils.LOGIN_PAGE)){
					AppController.getInstance().addLoginPage();
				}else if(calledFrom.equalsIgnoreCase(ConstantUtils.USER_REPORT)){
					AppController.getInstance().addUserReportPage(userTypeStr);
				}
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private void loadUser(){
		try{
			User loadUserObj = MasterController.getInstace().getUser(userId);
			//MyTextField userName, password;
			//JComboBox userType;
			if(loadUserObj!=null){
				if(loadUserObj.getUserName()!=null){
					userName.setText(loadUserObj.getUserName());
				}
				
				if(loadUserObj.getPassword()!=null){
					password.setText(loadUserObj.getPassword());
				}
				
				if(loadUserObj.getUserType()!=null){
					int userTypeIndex = -1;
					if(UserTypeList!=null && UserTypeList.size()>0){
						for(UserType userType : UserTypeList){
							userTypeIndex++;
							if(userType.getUserTypeId().equals(loadUserObj.getUserType().getUserTypeId())){
								break;
							}
						}
					}
					if(userTypeIndex>-1){
						userType.setSelectedIndex(userTypeIndex);
					}
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

}
