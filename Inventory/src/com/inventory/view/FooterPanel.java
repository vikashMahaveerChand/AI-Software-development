package com.inventory.view;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import com.inventory.components.MyLabel;
import com.inventory.components.MyPanel;
import com.inventory.utility.Session;

public class FooterPanel {
	private static FooterPanel myInstance=null;
    
    private FooterPanel(){};
    
    public static FooterPanel getInstance(){
        if(myInstance==null){
            myInstance = new FooterPanel();
        }
        
        return myInstance;
    }
    
    JPanel panel;
    
    public JPanel getPanel(){
        initcomponent();
        return panel;
    }
    
    private void initcomponent() {
    	try{
    		panel=new JPanel(new MigLayout());
    		MyPanel.setBackground(panel);
    		if(Session.licence!=null){
    			panel.add(new MyLabel(Session.licence.getLicenceName()),"center,pushx,wrap");
    			//panel.add(new MyLabel(SessionValue.LICENCE_ADDRESS),"center,pushx,wrap");
    		}else{
    			panel.add(new MyLabel("This Software Developed and Maintaned by Dilkush Software. Contact-9941336844"),"center,pushx,wrap");
    		}
    	}catch(Exception ex){
    		ex.printStackTrace();
    	}
    }

}
