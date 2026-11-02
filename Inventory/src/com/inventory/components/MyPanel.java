/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.inventory.components;

import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

/**
 *
 * @author vikash
 */
public class MyPanel extends JPanel{
    public static void setBorder(String Title,JPanel panel,Color bgcol,Color bordercol){
        Border lineBorder = BorderFactory.createLineBorder(bordercol);
        bgcol = Color.BLACK;
            int pos=javax.swing.border.TitledBorder.LEFT;
            int titlepostion=TitledBorder.TOP;
            Font font=new Font("Tahoma", 0, 14);
            Color color=Color.red;
            //bgcol = Color.ORANGE;
            Border titledBorder=BorderFactory.createTitledBorder(lineBorder, Title,pos,titlepostion,font,color);
            panel.setBackground(bgcol);
            panel.setBorder(titledBorder);
    }
    
    public static void setBorderAndBackground(JPanel panel,Color bgcol,Color bordercol){
        Border lineBorder = BorderFactory.createLineBorder(bordercol);
        bgcol = Color.BLACK;
        //bgcol = Color.ORANGE;
            int pos=javax.swing.border.TitledBorder.LEFT;
            int titlepostion=TitledBorder.TOP;
            Font font=new Font("Tahoma", 0, 14);
            Color color=Color.red;
            //Border titledBorder=BorderFactory.createTitledBorder(lineBorder, Title,pos,titlepostion,font,color);
            Border titledBorder=BorderFactory.createTitledBorder(lineBorder);
            panel.setBackground(bgcol);
            panel.setBorder(titledBorder);
    }
    
    public static void setBackground(JPanel panel,Color bgcol){
        /*Border lineBorder = BorderFactory.createLineBorder(bordercol);
            int pos=javax.swing.border.TitledBorder.LEFT;
            int titlepostion=TitledBorder.TOP;
            Font font=new Font("Tahoma", 0, 14);
            Color color=Color.red;
            Border titledBorder=BorderFactory.createTitledBorder(lineBorder, Title,pos,titlepostion,font,color);*/
    		//bgcol = Color.BLACK;
    		//bgcol = Color.ORANGE;
            panel.setBackground(bgcol);
            //panel.setBorder(titledBorder);
    }
    
    public static void setBorder(String Title,JPanel panel){
    	Color bordercol = Color.RED;
        Border lineBorder = BorderFactory.createLineBorder(bordercol);
        //Color bgcol = Color.BLACK;
        Color bgcol = Color.WHITE;
            int pos=javax.swing.border.TitledBorder.LEFT;
            int titlepostion=TitledBorder.TOP;
            Font font=new Font("Tahoma", 0, 14);
            Color color=Color.red;
            //bgcol = Color.ORANGE;
            Border titledBorder=BorderFactory.createTitledBorder(lineBorder, Title,pos,titlepostion,font,color);
            panel.setBackground(bgcol);
            panel.setBorder(titledBorder);
    }
    
    public static void setBorderAndBackground(JPanel panel){
    	Color bordercol = Color.RED;
        Border lineBorder = BorderFactory.createLineBorder(bordercol);
        //Color bgcol = Color.BLACK;
        Color bgcol = Color.WHITE;
        //bgcol = Color.ORANGE;
            int pos=javax.swing.border.TitledBorder.LEFT;
            int titlepostion=TitledBorder.TOP;
            Font font=new Font("Tahoma", 0, 14);
            Color color=Color.red;
            //Border titledBorder=BorderFactory.createTitledBorder(lineBorder, Title,pos,titlepostion,font,color);
            Border titledBorder=BorderFactory.createTitledBorder(lineBorder);
            panel.setBackground(bgcol);
            panel.setBorder(titledBorder);
    }
    
    public static void setBackground(JPanel panel){
        /*Border lineBorder = BorderFactory.createLineBorder(bordercol);
            int pos=javax.swing.border.TitledBorder.LEFT;
            int titlepostion=TitledBorder.TOP;
            Font font=new Font("Tahoma", 0, 14);
            Color color=Color.red;
            Border titledBorder=BorderFactory.createTitledBorder(lineBorder, Title,pos,titlepostion,font,color);*/
    		//Color bgcol = Color.BLACK;
    		Color bgcol = Color.WHITE;
    		//bgcol = Color.ORANGE;
            panel.setBackground(bgcol);
            //panel.setBorder(titledBorder);
    }
    
    public MyPanel(){
        super();
        
    }
    
}
