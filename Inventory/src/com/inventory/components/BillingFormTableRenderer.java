package com.inventory.components;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public class BillingFormTableRenderer implements TableCellRenderer {
	public static final DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
		DEFAULT_RENDERER.setHorizontalAlignment(JLabel.CENTER);
            
            Component renderer = DEFAULT_RENDERER.getTableCellRendererComponent(
                    table, value, isSelected, hasFocus, row, column);
            ((JLabel) renderer).setOpaque(true);
            Color foreground, background;
            //Color alternate = new Color(0xC0, 0xC0, 0xF0);
            Color lightBlue = new Color(204, 204, 255);
            //String enableFlag=table.getModel().getValueAt(row,6).toString();
            
          //System.err.println("value - "+table.getModel().getValueAt(row, column));
            if (isSelected) {
                foreground = Color.black;
                background = Color.gray;
            } else {
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
