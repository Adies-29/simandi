/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package smd.swing;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JToggleButton;
import simandi.ui.panels.Setting;

/**
 *
 * @author Adies
 */
public class SlidingLanguageToggle extends JToggleButton {
    
     // Tema warna dark-mode modern
    private final Color COLOR_BG = new Color(39, 45, 54);             // Background wadah abu-abu gelap
    private final Color COLOR_SLIDER_ACTIVE = new Color(30, 144, 255); // Slider Biru Modern (Dodger Blue)
    
    private final int cornerRadius = 24; // Bentuk kapsul/pil halus
    
    // Indeks Bahasa: 0 = Indonesia, 1 = Inggris, 2 = Malaysia
    private int selectedLanguageIndex = 0; 
    
    // Label teks statis (Bisa juga disesuaikan jika ingin dinamis)
    private final String[] languages = {"Indonesia", "Inggris", "Nederlands", "Español"};
    private final String[] langcodes = {"id", "en", "nl", "es"};
    
    public SlidingLanguageToggle() {
        super();

        // Matikan render bawaan Swing
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(false);

        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setFont(new Font("SansSerif", Font.BOLD, 13)); // Sedikit dikecilkan agar muat 3 teks

        // Logika klik untuk mendeteksi area mana yang ditekan (Bagi 3 area horizontal)
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int width = getWidth();
                int sectionWidth = width / 4;
                int clickX = e.getX();

                // Tentukan indeks berdasarkan koordinat klik X
                if (clickX < sectionWidth) {
                    setSelectedLanguageIndex(0);
                    Setting.prefs.put("LANGUAGE", langcodes[0]);
                } else if (clickX < sectionWidth * 2) {
                    setSelectedLanguageIndex(1);
                    Setting.prefs.put("LANGUAGE", langcodes[1]);
                } else if (clickX < sectionWidth * 3){
                    setSelectedLanguageIndex(2);
                    Setting.prefs.put("LANGUAGE", langcodes[2]);
                }else {
                    setSelectedLanguageIndex(3);
                    Setting.prefs.put("LANGUAGE", langcodes[3]);
                }
                
                
                // Memicu event action listener bawaan JToggleButton agar didengar oleh Form
                //fireActionPerformed(null);
                repaint();
            }
        });
    }
    /**
     * Mendapatkan indeks bahasa saat ini (0 = Indo, 1 = Eng, 2 = nl)
     * @return 
     */
    public int getSelectedLanguageIndex() {
        return selectedLanguageIndex;
    }
    /**
     * Mengubah posisi slider bahasa secara programatis (misal saat load konfigurasi)
     * @param index
     */
    public void setSelectedLanguageIndex(int index) {
        if (index >= 0 && index <= 3) {
            this.selectedLanguageIndex = index;
            repaint();
        }
    }
    public void setSelectedLanguageIndexByString(String lang) {
        switch (lang) {
            case "id":
                this.selectedLanguageIndex = 0;
                break;
            case "en":
                this.selectedLanguageIndex = 1;
                break;
            case "nl":
                this.selectedLanguageIndex = 2;
                break;
            case "es":
                this.selectedLanguageIndex = 3;
                break;    
            default:
                break;
        }
    }
    /**
     * Mengambil kode bahasa berdasarkan string untuk integrasi dengan service Anda
     * @return 
     */
    public String getSelectedLanguageString() {
        return switch (selectedLanguageIndex) {
            case 0 -> "id";
            case 1 -> "en";
            case 2 -> "nl";
            case 3 -> "es";    
            default -> "id";
        };
    }
     @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        

        // Mengaktifkan anti-aliasing
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();
        int margin = 5; 
        
        
        // Lebar slider dibagi 3 bagian dikurangi margin
        int sliderWidth = (w / 4) - (margin + (margin / 2));
        int sliderHeight = h - (margin * 2);

        // 1. GAMBAR BACKGROUND UTAMA
        g2.setColor(COLOR_BG);
        g2.fillRoundRect(0, 0, w, h, cornerRadius, cornerRadius);

        // 2. KALKULASI & GAMBAR SLIDER BERGESER (Posisi X dinamis berdasarkan indeks)
        int sliderX = margin + (selectedLanguageIndex * (w / 4));
        // Penyesuaian mikro posisi X agar presisi di tengah kolomnya
        if (selectedLanguageIndex == 1) sliderX += 2;
        if (selectedLanguageIndex == 3) sliderX = w - sliderWidth - margin;

        g2.setColor(COLOR_SLIDER_ACTIVE);
        g2.fillRoundRect(sliderX, margin, sliderWidth, sliderHeight, cornerRadius - 6, cornerRadius - 6);

        // 3. TATA LETAK TEKS MULTI-KOLOM
        FontMetrics fm = g2.getFontMetrics();
        int textY = (h / 2) + (fm.getAscent() / 2) - 2;

        for (int i = 0; i < languages.length; i++) {
            String text = languages[i];
            
            // Hitung titik tengah X masing-masing sepertiga bagian tombol
            int targetCenterX = (w / 8) + (i * (w / 4));
            int textX = targetCenterX - (fm.stringWidth(text) / 2);

            // Jika teks berada di bawah slider aktif, warnai putih pekat. Jika tidak, redupkan.
            if (i == selectedLanguageIndex) {
                g2.setColor(Color.WHITE);
            } else {
                g2.setColor(new Color(130, 135, 145));
            }
            
            g2.drawString(text, textX, textY);
        }

        g2.dispose();
    }
    
}
