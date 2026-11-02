/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.inventory.components;

/**
 *
 * @author vikash
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.JTextField;

public class MyTextField extends JTextField {
 public MyTextField(int len) {
  super(len);
  setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
  addKeyListener(new KeyAdapter() {
   public void keyPressed(KeyEvent evt) {
    int key = evt.getKeyCode();
    if (key == KeyEvent.VK_ENTER)
     transferFocus();}});
  }
}
