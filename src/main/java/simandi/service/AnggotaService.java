package simandi.service;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.bson.conversions.Bson;
import simandi.DAO.GenericDAO;
import simandi.objek.Anggota;

public class AnggotaService {

    private final GenericDAO<Anggota> DAO;

    public AnggotaService() {
        DAO = new GenericDAO<>("anggota", Anggota.class);
    }

    // 1. CREATE
    public void tambahAnggota(Anggota a) {

        // cek NIM sudah ada atau belum
        Bson filter = Filters.eq("nim", a.getNim());

        Anggota cek = DAO.findOne(filter);

        if (cek != null) {

            JOptionPane.showMessageDialog(
                    null,
                    "NIM sudah terdaftar!"
            );

            return;
        }

        DAO.save(a);

        JOptionPane.showMessageDialog(
                null,
                "Data berhasil disimpan"
        );
    }

    // 2. READ ALL
    public List<Anggota> tampilSemuaAnggota() {
        return DAO.findAll();
    }

    // 3. READ ONE
    public Anggota cariAnggotaByNim(String nim) {

        Bson filter = Filters.eq("nim", nim);

        return DAO.findOne(filter);
    }

    // 4. SEARCH
    public List<Anggota> searchAnggota(String key) {

        List<Bson> filters = new ArrayList<>();

        for (Field field : Anggota.class.getDeclaredFields()) {

            // skip field serialVersionUID kalau ada
            if (field.getName().equalsIgnoreCase("serialVersionUID")) {
                continue;
            }

            filters.add(
                    Filters.regex(
                            field.getName(),
                            key,
                            "i"
                    )
            );
        }

        return DAO.findMany(
                Filters.or(filters)
        );
    }

    // 5. UPDATE
    public void updateAnggota(Anggota a) {

        // cari data lama berdasarkan NIM
        Bson filter = Filters.eq("nim", a.getNim());

        Anggota old = DAO.findOne(filter);

        if (old == null) {

            JOptionPane.showMessageDialog(
                    null,
                    "Data tidak ditemukan"
            );

            return;
        }

        // UPDATE PAKE COMBINE
        Bson update = Updates.combine(

                Updates.set("uidRfid", a.getUidRfid()),
                Updates.set("namaLengkap", a.getNamaLengkap()),
                Updates.set("kelas", a.getKelas()),
                Updates.set("jurusan", a.getJurusan()),
                Updates.set("status", a.getStatus())

        );

        DAO.getCollection().updateOne(filter, update);

        JOptionPane.showMessageDialog(
                null,
                "Data berhasil diupdate"
        );
    }

    // 6. DELETE
    public void hapusAnggota(String nim) {

        Bson filter = Filters.eq("nim", nim);

        DAO.delete(filter);

        JOptionPane.showMessageDialog(
                null,
                "Data berhasil dihapus"
        );
    }

    // 7. UI RENDER
    public void tampilAnggota(JPanel panelTarget, String key) {

        List<Anggota> list;

        if (key == null || key.isEmpty()) {

            list = DAO.findAll();

        } else {

            list = searchAnggota(key);
        }

        panelTarget.removeAll();

        for (Anggota a : list) {

            JPanel card = new JPanel(
                    new java.awt.GridLayout(4, 1)
            );

            card.setBackground(
                    new java.awt.Color(52, 152, 219)
            );

            javax.swing.JLabel nama =
                    new javax.swing.JLabel(
                            "Nama: " + a.getNamaLengkap()
                    );

            javax.swing.JLabel nim =
                    new javax.swing.JLabel(
                            "NIM: " + a.getNim()
                    );

            javax.swing.JLabel jurusan =
                    new javax.swing.JLabel(
                            "Jurusan: " + a.getJurusan()
                    );

            nama.setForeground(java.awt.Color.WHITE);
            nim.setForeground(java.awt.Color.WHITE);
            jurusan.setForeground(java.awt.Color.WHITE);

            JPanel btnPanel =
                    new JPanel(
                            new java.awt.GridLayout(1, 2)
                    );

            javax.swing.JButton edit =
                    new javax.swing.JButton("Edit");

            javax.swing.JButton delete =
                    new javax.swing.JButton("Delete");

            delete.addActionListener(e -> {

                int confirm = JOptionPane.showConfirmDialog(
                        null,
                        "Hapus data " + a.getNamaLengkap() + "?",
                        "Konfirmasi",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {

                    hapusAnggota(a.getNim());

                    tampilAnggota(panelTarget, "");
                }
            });

            btnPanel.add(edit);
            btnPanel.add(delete);

            card.add(nama);
            card.add(nim);
            card.add(jurusan);
            card.add(btnPanel);

            panelTarget.add(card);
        }

        panelTarget.revalidate();
        panelTarget.repaint();
    }
}