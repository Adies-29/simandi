/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package smd.swing;

import java.awt.Dimension;
import javax.swing.JScrollBar;

/**
 *
 * @author Tya
 */
public class ScrollBarCustom extends JScrollBar{
    
    public ScrollBarCustom() {
        setUI(new ModerenScrollBarUI());
        setPreferredSize(new Dimension(8, 8));
        setUnitIncrement(20);
        setOpaque(false);
    }
    
}





    

