package com.inventory.view;

import java.awt.Color;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import com.inventory.common.FilterJtable;
import com.inventory.components.EvenOddTableRenderer;
import com.inventory.components.MyButton;
import com.inventory.components.MyLabel;
import com.inventory.components.MyPanel;
import com.inventory.components.MyTextField;
import com.inventory.controller.AppController;
import com.inventory.controller.MasterController;
import com.inventory.dto.LicenceOpeningStockDetailsDTO;
import com.inventory.dto.OpeningStockDetailsLicenceDTO;
import com.inventory.model.LicenceOpeningStockDetails;
import com.inventory.model.OpeningStockDetailsLicence;
import com.inventory.model.Ornament;
import com.inventory.utility.ConstantUtils;
import com.inventory.utility.Session;

import net.miginfocom.swing.MigLayout;

public class OrnamentReport  implements ActionListener{
	
	private static OrnamentReport myInstance=null;
	
	private OrnamentReport(){}
	
	public static OrnamentReport getInstance(){
		if(myInstance ==null){
			myInstance = new OrnamentReport();
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
    private JMenuItem editRecord,deleteRecord,addEditStock,removeStock;
    String userType,calledFrom;
    JDialog dialog = null;
    
    MyTextField stockGSTGrams,stockNONGSTGrams;
	MyButton addStock;
	JPanel licenceStockEntryPanel = null;
	
    public JPanel getPanel(String userType,String calledFrom){
    	try{
		this.userType = userType;
		this.calledFrom = calledFrom;
		initComponent();
		if(calledFrom.equalsIgnoreCase(ConstantUtils.MENU)){
			constructOrnamentReport();
			fetchOrnamentList();
		}else if(calledFrom.equalsIgnoreCase(ConstantUtils.ADD_GST_OPENING_STOCK) || calledFrom.equalsIgnoreCase(ConstantUtils.ADD_NONGST_OPENING_STOCK)){
			constructLicenceOrnamentReport();
			//fetchLicenceOrnamentList();
			fetchOrnamentList();
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
    		licenceStockEntryPanel = new JPanel(new MigLayout());
    		
			firstPanel = new JPanel(new MigLayout());
			secondPanel = new JPanel(new MigLayout());
			thirdPanel = new JPanel(new MigLayout());
			
			searchTextField = new MyTextField(20);
			
			MyPanel.setBorder("Ornament", panel);
			MyPanel.setBackground(firstPanel);
			MyPanel.setBackground(secondPanel);
			MyPanel.setBackground(thirdPanel);
			
			reportTable = new JTable();
                
			dialog = new JDialog();
			Window parentWindow = SwingUtilities.getWindowAncestor(panel);
			dialog = new JDialog(parentWindow, ModalityType.APPLICATION_MODAL);
			
			stockGSTGrams = new MyTextField(10);
			stockNONGSTGrams = new MyTextField(10);
			addStock = new MyButton("Add Stock");
			
			
            popupMenu = new JPopupMenu();
            editRecord = new JMenuItem();
            deleteRecord = new JMenuItem();
            addEditStock = new JMenuItem();
            removeStock = new JMenuItem();
            
            editRecord.addActionListener(this);
            deleteRecord.addActionListener(this);
            addEditStock.addActionListener(this);
            removeStock.addActionListener(this);
            searchTextField.addActionListener(this);
            addStock.addActionListener(this);
            //FilterJtable.setFilterJtable(reportTable, searchTextField);
            
    	}catch(Exception ex){
    		
    	}
    }
    
    private void constructOrnamentReport(){
    	try{
    		MyPanel.setBorder("Ornament", panel);
    		
    		tableModelling(reportTable);
			editRecord.setText("Edit Ornament");
			deleteRecord.setText("Delete Ornament");
			//addEditStock.setText("Add/Edit Stock");
			//removeStock.setText("Remove Stock");
			
			
			popupMenu.add(editRecord);
			popupMenu.add(deleteRecord);
			//popupMenu.add(addEditStock);
			//popupMenu.add(removeStock);
			
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
            
            firstPanel.add(new MyLabel("Search Ornament"),"push,al right");
            firstPanel.add(searchTextField,"al right, wrap");
            
            
            panel.add(firstPanel,"span,pushx,growx,wrap");
            panel.add(secondPanel,"span,pushx,growx,wrap");
            panel.add(thirdPanel,"span,pushx,growx,wrap");
    	}catch(Exception ex){
    		
    	}
    }
    
    private void constructLicenceOrnamentReport(){
    	try{
    		if(calledFrom.equalsIgnoreCase(ConstantUtils.ADD_GST_OPENING_STOCK)){
    			MyPanel.setBorder("Stock Entry -GST", panel);
    		}else if(calledFrom.equalsIgnoreCase(ConstantUtils.ADD_NONGST_OPENING_STOCK)){
    			MyPanel.setBorder("Stock Entry -NONGST", panel);
    		}
    		//licenceStockTableModelling(reportTable);
    		tableModelling(reportTable);
			addEditStock.setText("Add/Edit Stock");
			removeStock.setText("Remove Stock");
			
			popupMenu.add(addEditStock);
			popupMenu.add(removeStock);
			
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
            
            firstPanel.add(new MyLabel("Search Ornament"),"push,al right");
            firstPanel.add(searchTextField,"al right, wrap");
            
            
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
						return String.class;//Ornament Id
					case 1:
						return String.class;//gst stock Id
					case 2:
						return String.class;//nongst stock id
					case 3:
						return String.class;//Ornament Name
					case 4:
						return String.class;//Ornament Code
					case 5:
						return String.class;//GST Percentage
					case 6:
						return String.class;//Purity
					case 7:
						return String.class;//MC Rate
					case 8:
						return String.class;//MC Per Piece
					case 9:
						return String.class;//gst grams
					case 10:
						return String.class;//nongst grams
					default:
						return String.class;
					}
				}
			};
			jTable.setModel(reportModel);
			reportModel.addColumn("ornamentId");
			reportModel.addColumn("gstStockId");
			reportModel.addColumn("nongstStockId");
        	reportModel.addColumn("Ornament Name");
        	reportModel.addColumn("Ornament Code");
        	reportModel.addColumn("GST Percentage");
        	reportModel.addColumn("Purity");
        	reportModel.addColumn("MC Rate");
        	reportModel.addColumn("MC Per Piece");
        	reportModel.addColumn("Opening GST Grams");
        	reportModel.addColumn("Opening NONGST Grams");
        	
        	jTable.getColumn("ornamentId").setPreferredWidth(0);
        	jTable.getColumn("ornamentId").setMinWidth(0);
        	jTable.getColumn("ornamentId").setMaxWidth(0);
        	
        	jTable.getColumn("gstStockId").setPreferredWidth(0);
        	jTable.getColumn("gstStockId").setMinWidth(0);
        	jTable.getColumn("gstStockId").setMaxWidth(0);
        	
        	jTable.getColumn("nongstStockId").setPreferredWidth(0);
        	jTable.getColumn("nongstStockId").setMinWidth(0);
        	jTable.getColumn("nongstStockId").setMaxWidth(0);
        	
        	if(userType.equalsIgnoreCase(ConstantUtils.USER_TYPE_ADMIN)){
				
			}else if (userType.equalsIgnoreCase(ConstantUtils.USER_TYPE_GST)){
				
			}else if(userType.equalsIgnoreCase(ConstantUtils.USER_TYPE_NON_GST)){
				
	        	
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
    
    private void licenceStockTableModelling(JTable jTable){
		try{
			
			reportModel = new DefaultTableModel() {

				public Class<?> getColumnClass(int column) {
					switch (column) {
					case 0:
						return String.class;//Ornament Id
					case 1:
						return String.class;//Licence Opening Stock Id
					case 2:
						return String.class;//Ornament Name
					case 3:
						return String.class;//Ornament Code
					case 4:
						return String.class;//Grams
					default:
						return String.class;
					}
				}
			};
			jTable.setModel(reportModel);
			reportModel.addColumn("ornamentId");
			reportModel.addColumn("licenceOpeningStockId");
        	reportModel.addColumn("Ornament Name");
        	reportModel.addColumn("Ornament Code");
        	//reportModel.addColumn("GST Percentage");
        	reportModel.addColumn("Grams");

        	jTable.getColumn("ornamentId").setPreferredWidth(0);
        	jTable.getColumn("ornamentId").setMinWidth(0);
        	jTable.getColumn("ornamentId").setMaxWidth(0);
        	
        	jTable.getColumn("licenceOpeningStockId").setPreferredWidth(0);
        	jTable.getColumn("licenceOpeningStockId").setMinWidth(0);
        	jTable.getColumn("licenceOpeningStockId").setMaxWidth(0);
        	
        	if(userType.equalsIgnoreCase(ConstantUtils.USER_TYPE_ADMIN)){
				
			}else if (userType.equalsIgnoreCase(ConstantUtils.USER_TYPE_GST)){
				
			}else if(userType.equalsIgnoreCase(ConstantUtils.USER_TYPE_NON_GST)){
				
	        	
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
    /*private void fetchOrnamentList(){
    	try{
    		int rowCount = reportModel.getRowCount();
            for (int i = 0; i < rowCount; i++) {
            	reportModel.removeRow(0);
            }
            
            List<Ornament> ornamentList = null;
            
            ornamentList = MasterController.getInstace().getOrnamentList(Session.licence.getLicenceId());
            String temp;
            
            if(ornamentList!=null && ornamentList.size()>0){
            	for(Ornament data:ornamentList){
            		reportModel.addRow(new Object[]{
            				data.getOrnamentId(),
            				data.getOrnamentName(),
            				data.getOrnamentKeyCode(),
            				data.getGstPercentage()
            		});
            	}
            }
            
            
    	}catch(Exception ex){
			ex.printStackTrace();
		}
    }*/
    
    private void fetchOrnamentList(){
    	try{
    		int rowCount = reportModel.getRowCount();
            for (int i = 0; i < rowCount; i++) {
            	reportModel.removeRow(0);
            }
            
            List<OpeningStockDetailsLicenceDTO> list = null;
            list = MasterController.getInstace().getOpeningStockDetailsLicenceDTOList(Session.licence.getLicenceId(), userType);
            
            if(list!=null && list.size()>0){
            	for(OpeningStockDetailsLicenceDTO data:list){
            		reportModel.addRow(new Object[]{
            				data.getOrnamentId(),
            				data.getGstStockId(),
            				data.getNongstStockId(),
            				data.getOrnamentName(),
            				data.getOrnamentCode(),
            				data.getGstPercentage(),
            				data.getPurity(),
            				data.getMcRate(),
            				data.getMcPerPiece(),
            				data.getGstGrams(),
            				data.getNongstGrams()
            		});
            	}
            }
            
    	}catch(Exception ex){
    		ex.printStackTrace();
    	}
    }
    
    private void fetchLicenceOrnamentList(){
    	try{
    		int rowCount = reportModel.getRowCount();
            for (int i = 0; i < rowCount; i++) {
            	reportModel.removeRow(0);
            }
            
            List<LicenceOpeningStockDetailsDTO> licenceOrnamentListDTO = null;
            if(calledFrom.equalsIgnoreCase(ConstantUtils.ADD_GST_OPENING_STOCK)){
            	licenceOrnamentListDTO = MasterController.getInstace().getLicenceOpeningStockDetailsList(ConstantUtils.GST_ENTRY,Session.licence.getLicenceId());
            }else if(calledFrom.equalsIgnoreCase(ConstantUtils.ADD_NONGST_OPENING_STOCK)){
            	licenceOrnamentListDTO = MasterController.getInstace().getLicenceOpeningStockDetailsList(ConstantUtils.NON_GST_ENTRY,Session.licence.getLicenceId());
            }
            
            if(calledFrom.equalsIgnoreCase(ConstantUtils.ADD_GST_OPENING_STOCK)){
            if(licenceOrnamentListDTO!=null && licenceOrnamentListDTO.size()>0){
            	for(LicenceOpeningStockDetailsDTO data:licenceOrnamentListDTO){
            		reportModel.addRow(new Object[]{
            				data.getOrnamentId(),
            				data.getLicenceOpeningStockDetailsId(),
            				data.getOrnamentName(),
            				data.getOrnamentKeyCode(),
            				data.getGstGrams()
            		});
            	}
            }
            }
            if(calledFrom.equalsIgnoreCase(ConstantUtils.ADD_NONGST_OPENING_STOCK)){
                if(licenceOrnamentListDTO!=null && licenceOrnamentListDTO.size()>0){
                	for(LicenceOpeningStockDetailsDTO data:licenceOrnamentListDTO){
                		reportModel.addRow(new Object[]{
                				data.getOrnamentId(),
                				data.getLicenceOpeningStockDetailsId(),
                				data.getOrnamentName(),
                				data.getOrnamentKeyCode(),
                				data.getNonGstGrams()
                		});
                	}
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
			if(evt ==  editRecord || evt ==  deleteRecord || evt == addEditStock || evt == removeStock){
				int selectedRow = reportTable.getSelectedRow();
				int ornamentId=0;
				int stockId=0;
				Double grams=0.0;
				
				Integer gstStockId = 0;
				Integer nongstStockId = 0;
				Double gstStockGrams =0.0;
				Double nongstStockGrams =0.0;
				
				if(selectedRow>-1){
					String selectedId = reportModel.getValueAt(selectedRow, 0).toString();
					Integer ornamentIdInCell = (Integer)reportTable.getValueAt(selectedRow, 0);
					
					if(calledFrom.equalsIgnoreCase(ConstantUtils.ADD_GST_OPENING_STOCK)){
								if(reportTable.getValueAt(selectedRow, 1)!=null){
									Integer licenceStockIdInCell = (Integer)reportTable.getValueAt(selectedRow, 1);
									stockId = licenceStockIdInCell;
									
								}
								
								if(reportTable.getValueAt(selectedRow, 9)!=null){
									Double licenceStockGramsInCell = (Double)reportTable.getValueAt(selectedRow, 9);
									grams = licenceStockGramsInCell;
									gstStockGrams = grams;
								}
					}else if(calledFrom.equalsIgnoreCase(ConstantUtils.ADD_NONGST_OPENING_STOCK)){
						if(reportTable.getValueAt(selectedRow, 2)!=null){
							Integer licenceStockIdInCell = (Integer)reportTable.getValueAt(selectedRow, 2);
							stockId = licenceStockIdInCell;
						}
						
						if(reportTable.getValueAt(selectedRow, 10)!=null){
							Double licenceStockGramsInCell = (Double)reportTable.getValueAt(selectedRow, 10);
							grams = licenceStockGramsInCell;
							nongstStockGrams = grams;
						}
					}
					//ornamentId = Integer.parseInt(selectedId);
					ornamentId = ornamentIdInCell;
					System.out.println("ornamentId - "+ornamentId);
					System.out.println("licenceStockId - "+stockId);
					
					if(evt ==  editRecord){
						Ornament ornament = MasterController.getInstace().getOrnament(ornamentId);
						if(ornament!=null){
						AppController.getInstance().addOrnamentFormPage(ornament.getMetalId().getMetalCode(), ornamentId, ConstantUtils.ORNAMENT_REPORT, Session.user.getUserType().getUserType(), Session.licence.getLicenceId());
						}
					}else if(evt ==  deleteRecord){
						deleteOrnament(ornamentId);
					}else if(evt == addEditStock){
						addEditLicenceOpeningStock(ornamentId,stockId,grams);
					}else if(evt == removeStock){
						deleteLicenceStock(stockId);
					}
				}
				
			}else if(evt == addStock){
				addStock();
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private void deleteOrnament(int ornamentId){
		try{
			int selected = JOptionPane.showConfirmDialog(null, "Are you sure You want to delete Ornament?");
			if(selected==JOptionPane.YES_OPTION){
				JOptionPane.showMessageDialog(null, "Deleted");
				Ornament deleteOrnament = MasterController.getInstace().getOrnament(ornamentId);
				if(deleteOrnament!=null){
					deleteOrnament.setDeletedFlag(ConstantUtils.CONSTANT_Y);
					Integer deletedFlag = MasterController.getInstace().saveOrUpdateOrnament(deleteOrnament);
					if(deletedFlag!=null && deletedFlag>0){
						JOptionPane.showMessageDialog(null, "Ornament Deleted");
						fetchOrnamentList();
					}else{
						JOptionPane.showMessageDialog(null, "Ornament Deletion Failed");
						fetchOrnamentList();
					}
				}
			}else if(selected==JOptionPane.NO_OPTION){
				JOptionPane.showMessageDialog(null, "Not Deleted");
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	MyLabel licenceOrnamentName;
	Integer licenecStockOrnamentId,licenceOpeningStockDetailsId;
	private void addEditLicenceOpeningStock(Integer ornamentId,Integer stockId,Double grams){
		try{
			System.out.println("ornamentId - "+ornamentId+",licenceStockId - "+stockId+", grams - "+grams);
			
			//int result = JOptionPane.showConfirmDialog(null, addEditLicenceStockPanel(), "Please Enter Grams", JOptionPane.OK_CANCEL_OPTION);
			//dialog.removeAll();
			//dialog.repaint();
			//dialog.pack();
			licenecStockOrnamentId = 0;
			licenceOpeningStockDetailsId = 0;
			dialog.add(getEditLicenceStockPanel(ornamentId,stockId));
			dialog.setTitle("Licence Stock Window");
			
			dialog.setSize(300, 200);
			
			
			final Toolkit toolkit = Toolkit.getDefaultToolkit();
			final Dimension screenSize = toolkit.getScreenSize();
			final int x = (screenSize.width - dialog.getWidth()) / 2;
			final int y = (screenSize.height - dialog.getHeight()) / 2;
			dialog.setLocation(x, y);
			dialog.setVisible(true);
			
			stockGSTGrams.grabFocus();
			stockNONGSTGrams.grabFocus();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
	}
	
	
	
	private JPanel getEditLicenceStockPanel(Integer licenecStockOrnamentId,Integer licenceOpeningStockDetailsId){
		
		try{
			licenceStockEntryPanel.removeAll();
			
			this.licenecStockOrnamentId= licenecStockOrnamentId;
			this.licenceOpeningStockDetailsId = licenceOpeningStockDetailsId;
			
			licenceOrnamentName = new MyLabel("");
			
			
			stockGSTGrams.setText("");
			stockNONGSTGrams.setText("");
			
			
			/*if(licenceOpeningStockDetailsId!=null && licenceOpeningStockDetailsId>0){
				LicenceOpeningStockDetails loadLicenceOpeningStockDetailsObj = MasterController.getInstace().getLicenceOpeningStockDetails(licenceOpeningStockDetailsId);
				if(loadLicenceOpeningStockDetailsObj!=null){
						if(loadLicenceOpeningStockDetailsObj.getGstGrams()!=null){
							stockGSTGrams.setText(loadLicenceOpeningStockDetailsObj.getGstGrams().toString());
						}
						if(loadLicenceOpeningStockDetailsObj.getNonGSTgrams()!=null){
							stockNONGSTGrams.setText(loadLicenceOpeningStockDetailsObj.getNonGSTgrams().toString());
						}
						licenceOrnamentName.setText(loadLicenceOpeningStockDetailsObj.getOrnament().getOrnamentName());
				}
			}else if(licenecStockOrnamentId!=null && licenecStockOrnamentId>0){
				Ornament loadOrnamentStockObj = MasterController.getInstace().getOrnament(licenecStockOrnamentId);
				if(loadOrnamentStockObj!=null){
					licenceOrnamentName.setText(loadOrnamentStockObj.getOrnamentName());
				}
			}*/
			
			if(licenceOpeningStockDetailsId!=null && licenceOpeningStockDetailsId>0){
				OpeningStockDetailsLicence loadOpeningStockDetailsLicenceObj = MasterController.getInstace().getOpeningStockDetailsLicence(licenceOpeningStockDetailsId);
				if(loadOpeningStockDetailsLicenceObj!=null){
					if(loadOpeningStockDetailsLicenceObj.getGrams()!=null){
						if(loadOpeningStockDetailsLicenceObj.getBillType().getBillTypeName().equals(ConstantUtils.GST_ENTRY)){
							stockGSTGrams.setText(loadOpeningStockDetailsLicenceObj.getGrams().toString());
						}else if(loadOpeningStockDetailsLicenceObj.getBillType().getBillTypeName().equals(ConstantUtils.NON_GST_ENTRY)){
							stockNONGSTGrams.setText(loadOpeningStockDetailsLicenceObj.getGrams().toString());
						}
					}
				}
			}else if(licenecStockOrnamentId!=null && licenecStockOrnamentId>0){
				Ornament loadOrnamentStockObj = MasterController.getInstace().getOrnament(licenecStockOrnamentId);
				if(loadOrnamentStockObj!=null){
					licenceOrnamentName.setText(loadOrnamentStockObj.getOrnamentName());
				}
			}
			
			licenceStockEntryPanel.add(new MyLabel("Ornament Name : "));
			licenceStockEntryPanel.add(licenceOrnamentName,"wrap");
			
			licenceStockEntryPanel.add(new MyLabel("Enter Grams :"));
			if(calledFrom.equalsIgnoreCase(ConstantUtils.ADD_GST_OPENING_STOCK)){
				licenceStockEntryPanel.add(stockGSTGrams,"wrap");
				stockGSTGrams.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
			}if(calledFrom.equalsIgnoreCase(ConstantUtils.ADD_NONGST_OPENING_STOCK)){
				licenceStockEntryPanel.add(stockNONGSTGrams,"wrap");
				stockNONGSTGrams.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
			}
			licenceStockEntryPanel.add(addStock);
			
			
			licenceStockEntryPanel.repaint();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return licenceStockEntryPanel;
	}
	
	/*private void addStock(){
		try{
			LicenceOpeningStockDetails openingStockObj = new LicenceOpeningStockDetails();
			
			if(licenceOpeningStockDetailsId!=null && licenceOpeningStockDetailsId>0){
				openingStockObj.setLicenceOpeningStockDetailsId(licenceOpeningStockDetailsId);
			}
			
			if(licenecStockOrnamentId!=null && licenecStockOrnamentId>0){
				openingStockObj.setOrnament(MasterController.getInstace().getOrnament(licenecStockOrnamentId));
			}
			
			if(!stockGSTGrams.getText().trim().equalsIgnoreCase("")){
				openingStockObj.setGstGrams(Double.parseDouble(stockGSTGrams.getText().trim()));
			}
			
			if(!stockNONGSTGrams.getText().trim().equalsIgnoreCase("")){
				openingStockObj.setNonGSTgrams(Double.parseDouble(stockNONGSTGrams.getText().trim()));
			}
			openingStockObj.setDeletedFlag(ConstantUtils.CONSTANT_N);
			
			openingStockObj.setLicenceId(Session.licence.getLicenceId());
			
			if(calledFrom.equalsIgnoreCase(ConstantUtils.ADD_GST_OPENING_STOCK)){
			}else if(calledFrom.equalsIgnoreCase(ConstantUtils.ADD_NONGST_OPENING_STOCK)){
			}
			
			Integer stockId = MasterController.getInstace().saveOrUpdateLicenceOpeningStockDetails(openingStockObj);
			
			if(stockId!=null && stockId>0){
				JOptionPane.showMessageDialog(null, "Stock Added successfully!!");
				fetchLicenceOrnamentList();
				dialog.setVisible(false);
			}else{
				JOptionPane.showMessageDialog(null, "Stock Added Failed");
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}*/
	
	private void addStock(){
		try{
			OpeningStockDetailsLicence openingStockObj = new OpeningStockDetailsLicence();
			
			if(licenceOpeningStockDetailsId!=null && licenceOpeningStockDetailsId>0){
				openingStockObj.setOpeningStockDetailsLicenceId(licenceOpeningStockDetailsId);
			}
			
			if(licenecStockOrnamentId!=null && licenecStockOrnamentId>0){
				openingStockObj.setOrnament(MasterController.getInstace().getOrnament(licenecStockOrnamentId));
			}
			
			if(!stockGSTGrams.getText().trim().equalsIgnoreCase("")){
				openingStockObj.setGrams(Double.parseDouble(stockGSTGrams.getText().trim()));
			}
			
			if(!stockNONGSTGrams.getText().trim().equalsIgnoreCase("")){
				openingStockObj.setGrams(Double.parseDouble(stockNONGSTGrams.getText().trim()));
			}
			openingStockObj.setDeletedFlag(ConstantUtils.CONSTANT_N);
			
			openingStockObj.setLicence(Session.licence);
			
			if(calledFrom.equalsIgnoreCase(ConstantUtils.ADD_GST_OPENING_STOCK)){
				openingStockObj.setBillType(MasterController.getInstace().getBillType(ConstantUtils.GST_ENTRY));	
			}else if(calledFrom.equalsIgnoreCase(ConstantUtils.ADD_NONGST_OPENING_STOCK)){
				openingStockObj.setBillType(MasterController.getInstace().getBillType(ConstantUtils.NON_GST_ENTRY));
			}
			
			openingStockObj = MasterController.getInstace().saveOrUpdateOpeningStockDetailsLicence(openingStockObj);
			
			if(openingStockObj!=null && openingStockObj.getOpeningStockDetailsLicenceId()>0){
				JOptionPane.showMessageDialog(null, "Stock Added successfully!!");
				//fetchLicenceOrnamentList();
				fetchOrnamentList();
				dialog.setVisible(false);
			}else{
				JOptionPane.showMessageDialog(null, "Stock Added Failed");
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private void deleteLicenceStock(int licenceOpeningStockDetailsId){
		try{
			int selected = JOptionPane.showConfirmDialog(null, "Are you sure You want to delete Stock Entry?");
			if(selected==JOptionPane.YES_OPTION){
				//JOptionPane.showMessageDialog(null, "Deleted");
				LicenceOpeningStockDetails deleteLicenceOpeningStock = MasterController.getInstace().getLicenceOpeningStockDetails(licenceOpeningStockDetailsId);
				if(deleteLicenceOpeningStock!=null){
					if(calledFrom.equalsIgnoreCase(ConstantUtils.ADD_GST_OPENING_STOCK)){
						deleteLicenceOpeningStock.setGstGrams(null);
					}else if(calledFrom.equalsIgnoreCase(ConstantUtils.ADD_NONGST_OPENING_STOCK)){
						deleteLicenceOpeningStock.setNonGSTgrams(null);
					}
					Integer deletedFlag = MasterController.getInstace().saveOrUpdateLicenceOpeningStockDetails(deleteLicenceOpeningStock);
					if(deletedFlag!=null && deletedFlag>0){
						JOptionPane.showMessageDialog(null, "Stock Deleted");
						fetchLicenceOrnamentList();
					}else{
						JOptionPane.showMessageDialog(null, "Stock Deletion Failed");
						fetchLicenceOrnamentList();
					}
				}
			}else if(selected==JOptionPane.NO_OPTION){
				JOptionPane.showMessageDialog(null, "Not Deleted");
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

}

