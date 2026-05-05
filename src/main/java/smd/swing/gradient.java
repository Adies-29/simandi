/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package smd.swing;

import javax.swing.*;
import java.awt.*;



public class gradient extends JPanel {

    public gradient() {
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        float[] dist = {0.0f, 0.2f, 0.4f, 0.7f, 1.0f};

        Color[] colors = {
            new Color(200, 240, 248),   
            new Color(235, 250, 252),   
            new Color(180, 235, 245),   
            new Color(140, 225, 240),   
            new Color(90, 200, 230)    
        };

        LinearGradientPaint gradient = new LinearGradientPaint(
                0, 0,
                0, height,
                dist,
                colors
        );

        g2.setPaint(gradient);
        g2.fillRect(0, 0, width, height);

        g2.dispose();
    }
}