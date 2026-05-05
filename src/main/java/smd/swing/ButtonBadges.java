/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package smd.swing;


import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import javax.swing.JButton;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;


/**
 *
 * @author Tya
 */
public class ButtonBadges extends JButton {
     private int badges;
    private Color effectColor = new Color(173, 173, 173);

    // Ripple animation
    private Timer timer;
    private float alpha;
    private int rippleSize;
    private Point pressedPoint;

    public ButtonBadges() {
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setBackground(Color.WHITE);
        setForeground(new Color(220, 0, 0));
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        timer = new Timer(15, e -> {
            rippleSize += 20;
            alpha -= 0.03f;
            if (alpha <= 0) {
                timer.stop();
            }
            repaint();
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                pressedPoint = e.getPoint();
                rippleSize = 0;
                alpha = 0.4f;
                timer.restart();
            }
        });
    }

    // ================= BADGES =================

    public int getBadges() {
        return badges;
    }

    public void setBadges(int badges) {
        this.badges = badges;
        repaint();
    }

    public Color getEffectColor() {
        return effectColor;
    }

    public void setEffectColor(Color effectColor) {
        this.effectColor = effectColor;
    }

    // ================= PAINT =================

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int size = Math.min(getWidth(), getHeight()) - 8;
        int x = (getWidth() - size) / 2;
        int y = (getHeight() - size) / 2;

        // Background circle
        g2.setColor(getBackground());
        g2.fillOval(x, y, size, size);

        // Ripple effect
        if (pressedPoint != null && alpha > 0) {
            g2.setComposite(AlphaComposite.getInstance(
                    AlphaComposite.SRC_ATOP, alpha));
            g2.setColor(effectColor);
            g2.fillOval(
                    pressedPoint.x - rippleSize / 2,
                    pressedPoint.y - rippleSize / 2,
                    rippleSize,
                    rippleSize
            );
        }

        g2.dispose();
        super.paintComponent(g);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        if (badges > 0) {
            String value = badges > 9 ? "+9" : String.valueOf(badges);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            FontMetrics fm = g2.getFontMetrics();
            Rectangle2D r = fm.getStringBounds(value, g2);

            int size = Math.max((int) r.getWidth(), (int) r.getHeight()) + 6;
            int x = getWidth() - size - 4;
            int y = 4;

            // Badge background
            g2.setColor(getForeground());
            g2.fillOval(x, y, size, size);

            // Badge text
            g2.setColor(Color.WHITE);
            g2.drawString(
                    value,
                    x + (size - (int) r.getWidth()) / 2,
                    y + fm.getAscent()
            );

            g2.dispose();
        }
    }
    
}
