package com.inventory.components;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import com.inventory.utility.ConstantUtils;

public class TableRendererForPawnReport implements TableCellRenderer {
	public static final DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
		DEFAULT_RENDERER.setHorizontalAlignment(JLabel.CENTER);
            
            Component renderer = DEFAULT_RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            ((JLabel) renderer).setOpaque(true);
            Color foreground, background;
            //Color alternate = new Color(0xC0, 0xC0, 0xF0);
            Color lightBlue = new Color(204, 204, 255);
            String statusCode=table.getModel().getValueAt(row,12).toString();
            String months ="";
            if(table.getModel().getValueAt(row, 8)!=null) {
            months = table.getModel().getValueAt(row, 8).toString();
            }
            //System.out.println("Render"+months);
            //jTable.getColumn("isEnabled").setPreferredWidth(0);
            
          //System.err.println("value - "+table.getModel().getValueAt(row, column));
            if (isSelected) {
                foreground = Color.black;
                background = Color.gray;
            }else if(statusCode.equalsIgnoreCase(ConstantUtils.PAWN_BILL_STATUS_PLEDGE)){
            	foreground = Color.black;
            	if(!months.equals("")) {
            		//System.out.println(Integer.parseInt(months)>12);
            		if(Integer.parseInt(months)>12) {
            			background = new Color(242, 145, 138);//255,255,224
            		}else {
            			background = new Color(255,255,153);//255,255,224
            		}
            	}else {
            		background = new Color(255,255,153);//255,255,224
            	}
                
            }else if(statusCode.equalsIgnoreCase(ConstantUtils.PAWN_BILL_STATUS_REDEEM)){
            	foreground = Color.black;
                //background = Color.GREEN;
                background = new Color(152,251,152);
            }else if(statusCode.equalsIgnoreCase(ConstantUtils.PAWN_BILL_STATUS_AUCTION)){
            	foreground = Color.black;
                //background = Color.GREEN;
                background = new Color(236, 51, 255);
            }else {
                if (row % 2 == 0) {
                    foreground = Color.black;
                    background = Color.white;
                } else {
                    foreground = Color.black;
                    background = lightBlue;
                }
            }
            renderer.setForeground(foreground);
            renderer.setBackground(background);
            //renderer.setEnabled(true);
            return renderer;
            }

}
