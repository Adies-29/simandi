/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package smd.swing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.plaf.basic.BasicScrollBarUI;


/**
 *
 * @author Tya
 */
public class ModerenScrollBarUI extends BasicScrollBarUI {

    @Override
    protected void paintTrack(Graphics g, JComponent c,
            java.awt.Rectangle trackBounds) {
        // transparan
    }

    @Override
    protected void paintThumb(Graphics g, JComponent c,
            java.awt.Rectangle thumbBounds) {

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        int x = thumbBounds.x;
        int y = thumbBounds.y;
        int w = thumbBounds.width;
        int h = thumbBounds.height;

        if (scrollbar.getOrientation() == JScrollBar.VERTICAL) {
            y += 8;
            h -= 16;
        } else {
            x += 8;
            w -= 16;
        }

        g2.setColor(new Color(100, 100, 100, 80));
        g2.fillRoundRect(x, y, w, h, 8, 8);

        g2.dispose();
    }

    @Override
    protected JButton createDecreaseButton(int orientation) {
        return createZeroButton();
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        return createZeroButton();
    }

    private JButton createZeroButton() {

        JButton btn = new JButton();

        btn.setPreferredSize(new java.awt.Dimension(0, 0));
        btn.setMinimumSize(new java.awt.Dimension(0, 0));
        btn.setMaximumSize(new java.awt.Dimension(0, 0));

        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorder(null);

        return btn;
    }
}