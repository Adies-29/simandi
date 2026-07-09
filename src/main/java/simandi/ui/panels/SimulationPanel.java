/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package simandi.ui.panels;

import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import simandi.objek.Anggota;
import simandi.service.AnggotaService2;

/**
 *
 * @author Adies
 */
public class SimulationPanel extends javax.swing.JPanel {

    private final JPanel targetCardPanel;
    private final AnggotaService2 anggotaService;
    private int scanCounter = 0;
    private JButton btnSimulateScan;

    public SimulationPanel(JPanel targetCardPanel) {
        this.targetCardPanel = targetCardPanel;
        this.anggotaService = new AnggotaService2();
        initComponents();
    }

    private void performSimulatedScan() {
        scanCounter++;
        String simulatedUID;

        // Skenario bergantian untuk testing (Andi -> Budi -> Tidak Dikenal)
        if (scanCounter % 3 == 1) {
            simulatedUID = "04:12:34:56:78"; // Pastikan ada di DB
        } else if (scanCounter % 3 == 2) {
            simulatedUID = "AA:BB:CC:DD:EE"; // Pastikan ada di DB
        } else {
            simulatedUID = "FF:00:11:22:33"; // Kartu tidak dikenal
        }
        // 1. Cari anggota berdasarkan UID di Database (MongoDB)
        Anggota found = anggotaService.cariAnggotaByUid(simulatedUID);

        // 2. Update tampilan kartu di LiveMonitor
        if (found != null) {
            // Tampilkan Popup Sukses
            JOptionPane.showMessageDialog(
                    this,
                    "✅ Scan Berhasil!\nSelamat datang, " + found.getNamaLengkap(),
                    "RFID Terdeteksi",
                    JOptionPane.INFORMATION_MESSAGE
            );
                 anggotaService.tampilAnggota(targetCardPanel, "");
        } else {
            // Tampilkan Popup Gagal
            JOptionPane.showMessageDialog(
                    this,
                    "❌ Kartu dengan UID " + simulatedUID + " tidak terdaftar di sistem.",
                    "Akses Ditolak",
                    JOptionPane.WARNING_MESSAGE
            );

            // Refresh kartu tanpa highlight (menghilangkan efek hijau dari scan sebelumnya)
            anggotaService.tampilAnggota(targetCardPanel, "");
        }
    }

        @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
