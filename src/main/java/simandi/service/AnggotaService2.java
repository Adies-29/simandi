package simandi.service;

import static com.mongodb.BasicDBObjectBuilder.start;
import com.mongodb.client.model.Filters;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import org.bson.conversions.Bson;
import simandi.DAO.GenericDAO;
import simandi.objek.Anggota;
import simandi.ui.panels.MenajemenData2;
import simandi.util.EncryptionUtils;

public class AnggotaService2 {

    private final GenericDAO<Anggota> DAO;

    public AnggotaService2() {
        DAO = new GenericDAO<>("anggota", Anggota.class);
    }

    /**
     * 1. CREATE: Simpan objek Anggota
     */
    public void tambahAnggota(Anggota anggota) {
        Thread saveThread = new Thread(() -> {
            System.out.println("--> [THREAD-SAVE] Menyimpan data mahasiswa baru ke MongoDB di background...");
            DAO.save(anggota);
            System.out.println("--> [THREAD-SAVE] Sukses tersimpan ke database!");
        }, "Thread-Anggota-Save");
        saveThread.setDaemon(true);
        saveThread.start();
    }

    public void tambahAnggota(String uid, String nim, String nama, String kls, String jrs, String sts) {
        Anggota a = new Anggota(uid, nim, nama, kls, jrs, sts);
        tambahAnggota(a);
    }

    /**
     * 2. UPDATE: Ubah data berdasarkan NIM
     */
    public void updateAnggota(Anggota newA) {
        Thread updateThread = new Thread(() -> {
            System.out.println("--> [THREAD-UPDATE] Memperbarui data NIM [" + newA.getNim() + "] di background...");
            Bson filter = Filters.eq("nim", newA.getNim());
            Anggota a = DAO.findOne(filter);
            if (a != null) {
                DAO.update(filter, newA);
                System.out.println("--> [THREAD-UPDATE] Sukses diperbarui!");
            } else {
                javax.swing.SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(null, "Data dengan NIM " + newA.getNim() + " tidak ditemukan!");
                });
            }
        }, "Thread-Anggota-Update");
        updateThread.setDaemon(true);
        updateThread.start();
    }

    /**
     * 3. READ: Menampilkan kartu mahasiswa (FIX SIZE & SCROLL)
     */
    public void tampilAnggota(JPanel panelTarget, String key) {
        // Ambil data dari database

        panelTarget.removeAll();
        panelTarget.setLayout(new BorderLayout());
        panelTarget.setBackground(new Color(230, 242, 255));

        JLabel lblLoading = new JLabel("⏳ Memuat Data Mahasiswa dari MongoDB...", SwingConstants.CENTER);
        lblLoading.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblLoading.setForeground(new Color(52, 152, 219));
        panelTarget.add(lblLoading, BorderLayout.CENTER);
        panelTarget.revalidate();
        panelTarget.repaint();

        Thread loaderThread = new Thread(() -> {
            System.out.println("--> [THREAD-START] Membaca data Anggota dari MongoDB di background...");
            long start = System.currentTimeMillis();

            List<Anggota> daftarAnggota = (key == null || key.isEmpty()) ? DAO.findAll() : cariAnggota(key);

            long durasi = System.currentTimeMillis() - start;
            System.out.println("--> [THREAD-DONE] Sukses memuat " + daftarAnggota.size() + " data dalam " + durasi + " ms!");

            // 3. SEBERANGKAN KE THREAD UI UNTUK MENGGAMBAR KARTU DI NETBEANS
            javax.swing.SwingUtilities.invokeLater(() -> {
                panelTarget.removeAll();

                // Panel Utama dengan GridLayout (6 kolom)
                JPanel gridPanel = new JPanel(new GridLayout(0, 6, 12, 12));
                gridPanel.setOpaque(false);
                gridPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

                for (Anggota a : daftarAnggota) {
                    // --- CUSTOM CARD MODERN ---
                    JPanel card = new JPanel() {
                        @Override
                        protected void paintComponent(Graphics g) {
                            Graphics2D g2 = (Graphics2D) g.create();
                            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                            // Shadow
                            g2.setColor(new Color(0, 0, 0, 12));
                            g2.fillRoundRect(5, 5, getWidth() - 10, getHeight() - 10, 25, 25);

                            // Body
                            g2.setColor(Color.WHITE);
                            g2.fillRoundRect(0, 0, getWidth() - 10, getHeight() - 10, 25, 25);

                            // Header Accent
                            g2.setColor(new Color(52, 152, 219, 40));
                            g2.fill((Shape) new RoundRectangle2D.Double(0, 0, getWidth() - 10, 10, 25, 25));
                            g2.fillRect(0, 5, getWidth() - 10, 5);

                            g2.dispose();
                            super.paintComponent(g);
                        }
                    };

                    card.setOpaque(false);
                    card.setLayout(new BorderLayout(0, 15));
                    // KUNCI UKURAN KARTU DI SINI
                    card.setPreferredSize(new Dimension(239, 190));
                    card.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

                    // -- Bagian Atas (Nama & Status) --
                    JPanel topPanel = new JPanel(new BorderLayout());
                    topPanel.setOpaque(false);
                    JLabel lblNama = new JLabel("<html><body style='width: 100px'>" + a.getNamaLengkap().toUpperCase() + "</body></html>");
                    lblNama.setFont(new Font("SansSerif", Font.BOLD, 12));
                    lblNama.setForeground(new Color(44, 62, 80));

                    String statusKey = a.getStatus(); // Sekarang dari DB isinya "ui.status.active"
                    JLabel lblBadge = new JLabel(simandi.service.BahasaService.get(statusKey));
                    lblBadge.setFont(new Font("SansSerif", Font.BOLD, 8));
                    lblBadge.setOpaque(true);
                    lblBadge.setHorizontalAlignment(SwingConstants.CENTER);
                    lblBadge.setBorder(BorderFactory.createEmptyBorder(2, 6, 2, 6));
                    // Cek warna hijau/merah:
                    if (statusKey.equals("ui.status.active") || statusKey.equalsIgnoreCase("Aktif")) {
                        lblBadge.setBackground(new Color(232, 247, 235));
                        lblBadge.setForeground(new Color(40, 167, 69));
                    } else {
                        lblBadge.setBackground(new Color(253, 237, 236));
                        lblBadge.setForeground(new Color(231, 76, 60));
                    }
                    topPanel.add(lblNama, BorderLayout.NORTH);
                    topPanel.add(lblBadge, BorderLayout.WEST);

                    // -- Bagian Tengah (Detail NIM, Kelas, Jurusan) --
                    JPanel midPanel = new JPanel(new GridLayout(3, 1, 0, 3));
                    midPanel.setOpaque(false);
                    midPanel.add(createInfoLabel(simandi.service.BahasaService.get("ui.student.nim") + ": " + EncryptionUtils.decrypt(a.getNim())));
                    midPanel.add(createInfoLabel(simandi.service.BahasaService.get("ui.student.class") + ": " + a.getKelas()));
                    midPanel.add(createInfoLabel(simandi.service.BahasaService.get("ui.student.major") + ": " + simandi.service.BahasaService.get(a.getJurusan())));

                    // -- Bagian Bawah (Aksi) --
                    JPanel botPanel = new JPanel(new GridLayout(1, 2, 8, 0));
                    botPanel.setOpaque(false);

                    JButton btnEdit = createStyledButton(simandi.service.BahasaService.get("ui.btn.update").toUpperCase(), new Color(52, 152, 219));
                    btnEdit.addActionListener(e -> {
                        MenajemenData2.txtUid.setText(a.getUidRfid());

                        String nimAsli = simandi.util.EncryptionUtils.decrypt(a.getNim());
                        javax.swing.JLabel lblNim = new javax.swing.JLabel(simandi.service.BahasaService.get("ui.student.nim") + ": " + nimAsli);
                        MenajemenData2.txtNim.setText(nimAsli);
                        MenajemenData2.txtNim.setEnabled(false);

                        MenajemenData2.txtNama.setText(a.getNamaLengkap());
                        MenajemenData2.txtKelas.setSelectedItem(a.getKelas());
                        MenajemenData2.txtJurusan.setSelectedItem(a.getJurusan());
                        MenajemenData2.comboStatus.setSelectedItem(a.getStatus());

                        MenajemenData2.btnSimpan.getParent().getParent().setVisible(false);
                        MenajemenData2.btnUpdate.getParent().getParent().setVisible(true);
                    });

                    JButton btnHapus = createStyledButton(simandi.service.BahasaService.get("ui.btn.delete").toUpperCase(), new Color(231, 76, 60));
                    btnHapus.addActionListener(e -> {
                        int confirm = JOptionPane.showConfirmDialog(null, "Hapus data " + a.getNamaLengkap() + "?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                        if (confirm == JOptionPane.YES_OPTION) {
                            hapusAnggota(a.getNim());
                            tampilAnggota(panelTarget, "");
                        }
                    });
                    botPanel.add(btnEdit);
                    botPanel.add(btnHapus);

                    card.add(topPanel, BorderLayout.NORTH);
                    card.add(midPanel, BorderLayout.CENTER);
                    card.add(botPanel, BorderLayout.SOUTH);

                    gridPanel.add(card);
                }
                // --- WRAPPER PANEL: MENCEGAH KARTU MELEBAR ---
                JPanel wrapperPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
                wrapperPanel.setOpaque(false);
                wrapperPanel.add(gridPanel);

                // --- SCROLLPANE ---
                JScrollPane scroll = new JScrollPane(wrapperPanel);
                scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
                scroll.setBorder(null);
                scroll.setOpaque(false);
                scroll.getViewport().setOpaque(false);
                scroll.getVerticalScrollBar().setUnitIncrement(18);
                panelTarget.add(scroll, BorderLayout.CENTER);
                panelTarget.revalidate();
                panelTarget.repaint();
            });
        }, "Thread-Anggota-Loader");
        // Set Daemon agar thread otomatis mati jika aplikasi ditutup
        loaderThread.setDaemon(true);
        loaderThread.start();
    }

    private JLabel createInfoLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.PLAIN, 10));
        label.setForeground(new Color(127, 140, 141));
        return label;
    }

    private JButton createStyledButton(String text, Color baseColor) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("SansSerif", Font.BOLD, 9));
        btn.setForeground(Color.WHITE);
        btn.setBackground(baseColor);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(6, 0, 6, 0));
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(baseColor.brighter());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(baseColor);
            }
        });
        return btn;
    }

    /**
     * 4. SEARCH: Cari berdasarkan Nama, NIM, atau Jurusan
     */
    public List<Anggota> cariAnggota(String key) {
        List<Bson> filters = new ArrayList<>();
        filters.add(Filters.regex("namaLengkap", key, "i"));
        filters.add(Filters.regex("nim", key, "i"));
        filters.add(Filters.regex("jurusan", key, "i"));
        return DAO.findMany(Filters.or(filters));
    }

    /**
     * 5. DELETE: Hapus data permanen
     */
    public void hapusAnggota(String nim) {
        Thread deleteThread = new Thread(() -> {
            System.out.println("--> [THREAD-DELETE] Menghapus data NIM [" + nim + "] di background...");
            Bson filter = Filters.eq("nim", nim);
            DAO.delete(filter);
            System.out.println("--> [THREAD-DELETE] Sukses dihapus dari MongoDB!");
        }, "Thread-Anggota-Delete");
        deleteThread.setDaemon(true);
        deleteThread.start();
    }

    public Anggota cariAnggotaByUid(String uidRfid) {
        Bson filter = Filters.eq("uidRfid", uidRfid);
        return DAO.findOne(filter);
    }
}
