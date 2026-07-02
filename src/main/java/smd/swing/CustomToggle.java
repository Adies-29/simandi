/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package smd.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author Adies
 */
public class CustomToggle extends JToggleButton {
    
    public CustomToggle() {
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
    }
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Warna saat ON (Biru) / OFF (Abu-abu)
        if (isSelected()) {
            g2.setColor(new Color(52, 152, 219)); // Warna Biru
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            g2.setColor(Color.WHITE);
            g2.fillOval(getWidth() - getHeight() + 2, 2, getHeight() - 4, getHeight() - 4); // Bulatan
        } else {
            g2.setColor(Color.LIGHT_GRAY); // Warna Abu
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            g2.setColor(Color.WHITE);
            g2.fillOval(2, 2, getHeight() - 4, getHeight() - 4); // Bulatan
        }
        g2.dispose();
    }
    
}
