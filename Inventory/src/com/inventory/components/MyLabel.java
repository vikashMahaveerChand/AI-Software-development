/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.inventory.components;

import java.awt.Color;

import javax.swing.JLabel;

/**
 *
 * @author vikash
 */
public class MyLabel extends JLabel{
	public MyLabel() {
		super();
	}
    public MyLabel(String Name){
        super(Name);
        //setBackground(Color.red);
        setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        //setFont(new j);
        setForeground(Color.BLACK);
    }
    
    public MyLabel(String Name,Color color){
        super(Name);
        //setBackground(Color.red);
        setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        //setFont(new j);
        setForeground(color);
    }
    public MyLabel(String Name,int len){
        super(Name,len);
        setBackground(Color.red);
    }

    
    
}
