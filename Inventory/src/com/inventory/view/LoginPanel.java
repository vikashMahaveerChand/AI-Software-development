package com.inventory.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

import com.inventory.components.MyButton;
import com.inventory.components.MyLabel;
import com.inventory.components.MyPanel;
import com.inventory.components.MyTextField;
import com.inventory.controller.AppController;
import com.inventory.controller.MasterController;
import com.inventory.migration.BillDetailsMigration;
import com.inventory.migration.CustomerDetailsMigration;
import com.inventory.model.Licence;
import com.inventory.model.LicenceType;
import com.inventory.model.User;
import com.inventory.model.pbold.CustomerDetails;
import com.inventory.reports.RunReport;
import com.inventory.utility.Calculator;
import com.inventory.utility.Session;

public class LoginPanel implements ActionListener,KeyListener{
	private static LoginPanel myInstance=null;
    
	MyLabel l_user_name,l_password;
    MyTextField user_name;
    JPasswordField password;
    MyButton login;
    JComboBox Licence;
    MyLabel photoview;
    //Temp starts
    /*
    private final JTextField tf;
    private final JComboBox combo = new JComboBox();
    private final Vector<String> v = new Vector<String>();
    
    String[] countries = {"Afghanistan", "Albania", "Algeria", "Andorra", "Angola","Argentina"
    		,"Armenia","Austria","Bahamas","Bahrain", "Bangladesh","Barbados", "Belarus","Belgium",
    		"Benin","Bhutan","Bolivia","Bosnia & Herzegovina","Botswana","Brazil","Bulgaria",
    		"Burkina Faso","Burma","Burundi","Cambodia","Cameroon","Canada", "China","Colombia",
    		"Comoros","Congo","Croatia","Cuba","Cyprus","Czech Republic","Denmark", "Georgia",
    		"Germany","Ghana","Great Britain","Greece","Hungary","Holland","India","Iran","Iraq",
    		"Italy","Somalia", "Spain", "Sri Lanka", "Sudan","Suriname", "Swaziland","Sweden",
    		"Switzerland", "Syria","Uganda","Ukraine","United Arab Emirates","United Kingdom",
    		"United States","Uruguay","Uzbekistan","Vanuatu","Venezuela","Vietnam",
    		"Yemen","Zaire","Zambia","Zimbabwe"};
    private boolean hide_flag = false;
    */
    //Temp -ends
    private LoginPanel(){
    	/*
    	combo.setEditable(true);//temp
        tf = (JTextField) combo.getEditor().getEditorComponent();//temp
    	*/
    };
    
    public static LoginPanel getInstance(){
        if(myInstance==null){
            myInstance = new LoginPanel();
        }
        
        return myInstance;
    }
    
    JPanel panel;
    
    public JPanel getPanel(){
        initcomponent();
        return panel;
    }
    
    private void initcomponent(){
    	try{
    		panel=new JPanel(new MigLayout());
            MyPanel.setBorder("Login", panel);
            
            JPanel centerpanelMain=new JPanel(new MigLayout());
            JPanel centerpanel=new JPanel(new MigLayout());
            
            l_user_name=new MyLabel("User Name :");
            l_password=new MyLabel("Password :");
            
            user_name=new MyTextField(20);
            password=new JPasswordField(22);
            
            login=new MyButton("Login");
            
            Licence = new JComboBox();
            //Licence.setEditable(true);
            Licence.setSize(300, 10);
            List<Licence> LicenceList = MasterController.getInstace().getLicenceList();
            
            for(Licence obj:LicenceList){
                Licence.addItem(obj);
                //Licence.addItem(new LicenceItem(obj.getLicenceId(),obj.getLicencName(),obj.getAddress(),obj.getLicenceTypeName()));
            }
            
            centerpanel.add(l_user_name);        centerpanel.add(user_name,"wrap");
            centerpanel.add(l_password);        centerpanel.add(password,"wrap");
            centerpanel.add(new MyLabel("Licence :"));   centerpanel.add(Licence,"wrap");
                    
            centerpanel.add(login,"span2,center");
            
            
            
            /*
          //Temp Code starts
            for(int i=0;i<countries.length;i++){
                v.addElement(countries[i]);
            }
            setModel(new DefaultComboBoxModel(v), "");
            tf.addKeyListener(this);
            //combo.setSize(new Dimension(300,150));
            //tf.setSize(new Dimension(300,150));
            Dimension preferredSize = combo.getPreferredSize();
            combo.setPreferredSize(preferredSize);
            tf.setPreferredSize(preferredSize);
            centerpanel.add(combo);
            //Temp Code Ends
          */  
            
            
            centerpanelMain.add(centerpanel,"push, al center, wrap");
            
            photoview   =new MyLabel("");
            ImageIcon imageIcon = new ImageIcon("image/1Logo.jpg");
            photoview.setIcon(imageIcon);
            
            panel.add(photoview,"center,pushx,pushy,wrap");
            panel.add(centerpanelMain,"span,pushx,growx,wrap");
            
            login.addActionListener(this);
            Licence.addActionListener(this);
            password.addActionListener(this);
            
            login.addKeyListener(this);
            Licence.addKeyListener(this);
            password.addKeyListener(this);
    
            
    	}catch(Exception ex){
    		ex.printStackTrace();
    	}
    }

	@Override
	public void actionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
		onClickEvent(evt.getSource());
	}

	@Override
	public void keyPressed(KeyEvent evt) {
		// TODO Auto-generated method stub
		if(evt.getKeyCode() == KeyEvent.VK_ENTER){
			onClickEvent(evt.getSource());
		}/*else if(evt.getSource()==tf){
			String text = tf.getText();
	         int code = evt.getKeyCode();
	             if(code==KeyEvent.VK_ENTER) {
	            if(!v.contains(text)) {
	                v.addElement(text);
	                Collections.sort(v);
	                setModel(getSuggestedModel(v, text), text);
	            }
	            hide_flag = true; 
	        }else if(code==KeyEvent.VK_ESCAPE) {
	            hide_flag = true; 
	        }else if(code==KeyEvent.VK_RIGHT) {
	            for(int i=0;i<v.size();i++) {
	                String str = v.elementAt(i);
	                if(str.startsWith(text)) {
	                    combo.setSelectedIndex(-1);
	                    tf.setText(str);
	                    return;
	                }
	            }
	        }
		}*/
		
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent evt) {
		// TODO Auto-generated method stub
		/*
		if(evt.getSource() == tf){
			EventQueue.invokeLater(new Runnable() {
	            public void run() {
	                String text = tf.getText();
	                        if(text.length()==0) {
	                    combo.hidePopup();
	                    setModel(new DefaultComboBoxModel(v), "");
	                }else{
	                    DefaultComboBoxModel m = getSuggestedModel(v, text);
	                    if(m.getSize()==0 || hide_flag) {
	                          combo.hidePopup();
	                        hide_flag = false;
	                    }else{
	                        setModel(m, text);
	                        combo.showPopup();
	                    }
	                }
	            }
	        });
		}
		*/
	}
	
	private void onClickEvent(Object evt){
		if(evt == Licence || evt == password){
			((Component) evt).transferFocus();
			
		}else{
		//JOptionPane.showMessageDialog(null, "onclcik");
			if(evt == login){
				//RunReport.executeSampleReport();
				//RunReport.executeDemoJasperReport();
				//RunReport.printGSTWholeSaleBill(4);
				validatedUser();
			}
		}
	}
	/*
	 private void setModel(DefaultComboBoxModel mdl, String str) {
	        combo.setModel(mdl);
	        combo.setSelectedIndex(-1);
	        tf.setText(str);
	    }
	private static DefaultComboBoxModel getSuggestedModel(java.util.List<String> list, String text) {
	        DefaultComboBoxModel m = new DefaultComboBoxModel();
	        for(String s: list) {
	            if(s.startsWith(text)) m.addElement(s);
	        }
	        return m;
	    }*/
	
	private void validatedUser(){
		//CustomerDetailsMigration.migrateCustomerDetails();
		//BillDetailsMigration.migrateBillDetails();
		//calculateCI(2000, 5, .08, 12);
		/*calculateCI(1000, 1, .24, 12);
		calculateCI3(1000, 1.17, .24, 12);
		
		double time = 26.0/12.0;
		System.out.println(time);
		System.out.println("-Fomat>"+Calculator.formatTwoDecimal(time));
		time = Double.parseDouble(Calculator.formatTwoDecimal(time));
		System.out.println(time);*/
		/*calculateCI3(1000, 2.18, .24, 12);
		Double CIValue = calculateCI2(100000.0,24.0,2,1);
		
		Double ExtraInt = (CIValue*2*3)/100;
		System.out.println("->"+CIValue);
		System.out.println("**"+ExtraInt);
		System.out.println("-->"+(CIValue+ExtraInt));
		
		Calculator.calculateCompoundInterest(1000.0, 2.0, 26, 1);
		Calculator.calculateCompoundInterest(2500.0, 2.5, 40, 1);
		*/
		try{
			String userName = user_name.getText().trim();
			String psd = password.getText().trim();
			
			if(!userName.equalsIgnoreCase("") && !psd.equalsIgnoreCase("")){
				User userObj = MasterController.getInstace().validatedUser(userName, psd);
				if(userObj!=null){
					Session.user = userObj;
					Session.licence = (Licence)Licence.getSelectedItem();
					JOptionPane.showMessageDialog(null, "Validate User");
					AppController.getInstance().configureMenu();
				}else{
					JOptionPane.showMessageDialog(null, "In-Validate User");
				}
			}else{
				JOptionPane.showMessageDialog(null, "Please Enter username and password!");
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public static void calculateCI(int p, int t, double r, int n) {
        double amount = p * Math.pow(1 + (r / n), n * t);
        double cinterest = amount - p;
        System.out.println("Compound Interest after " + t + " years: "+cinterest);
        System.out.println("Amount after " + t + " years: "+amount);
    }
	
	public static void calculateCI3(int p, double t, double r, int n) {
        double amount = p * Math.pow(1 + (r / n), n * t);
        double cinterest = amount - p;
        System.out.println("Compound Interest after " + t + " years: "+cinterest);
        System.out.println("Amount after " + t + " years: "+amount);
    }
	
	public static Double calculateCI2(Double principal,Double rate,int time,int number) {
		double interest = (principal * (Math.pow((1 + rate/100), (time * number))) - principal);
		
		System.out.println("P+I"+principal+interest);
		System.out.println(Calculator.formatTwoDecimal((principal+interest)));
		double returnValue = Double.parseDouble(Calculator.formatTwoDecimal((principal+interest)).replaceAll(",", ""));
		return returnValue;
	}

}
