/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package smd.swing;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author Tya
 */

public class RoundPasword extends JPasswordField {

    private int radius = 25;

    public RoundPasword() {
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        setFont(new Font("Segoe UI", Font.PLAIN, 14));
        setForeground(new Color(50, 50, 50));
        setCaretColor(Color.BLACK);
        setBackground(new Color(0, 0, 0, 0));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // 🔵 WARNA SAMA PERSIS
        g2.setColor(new Color(204, 255, 255));
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

        // 🔥 BORDER HALUS
        g2.setColor(new Color(0, 0, 0, 20));
        g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, radius, radius);

        super.paintComponent(g);
    }
}