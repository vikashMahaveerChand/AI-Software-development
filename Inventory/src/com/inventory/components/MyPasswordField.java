/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.inventory.components;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPasswordField;

/**
 *
 * @author vikash
 */
public class MyPasswordField extends JPasswordField{
    public MyPasswordField(int len){
        super(len);
        addKeyListener(new KeyAdapter() {
   public void keyPressed(KeyEvent evt) {
    int key = evt.getKeyCode();
    if (key == KeyEvent.VK_ENTER)
     transferFocus();}});
  
    }
    
}
