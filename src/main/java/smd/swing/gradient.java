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

        int width = getWidth();
        int height = getHeight();

        GradientPaint gp = new GradientPaint(
                0, 0, new Color(138, 232, 243),
                0, height, new Color(202, 240, 244)
        );

        g2.setPaint(gp);
        g2.fillRect(0, 0, width, height);

        g2.dispose();
    }
}