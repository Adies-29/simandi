/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package simandi.objek;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import simandi.util.MongoManager;

/**
 *
 * @author Tya
 */
public class TesKoneksi {

    public static void main(String[] args) {

        try {

            System.out.println("Sedang mencoba menghubungkan ke database...");

            // koneksi database
            MongoDatabase database = MongoManager.getDatabase();

            // test koneksi
            Document ping = new Document("ping", 1);
            database.runCommand(ping);

            System.out.println("=========================================");
            System.out.println("STATUS: KONEKSI BERHASIL!");
            System.out.println("Nama Database : " + database.getName());
            System.out.println("=========================================");

            // tampil collection
            System.out.println("Daftar Collection:");

            boolean adaAnggota = false;

            for (String name : database.listCollectionNames()) {

                System.out.println("- " + name);

                if (name.equals("anggota")) {
                    adaAnggota = true;
                }
            }

            System.out.println("=========================================");

            if (adaAnggota) {

                System.out.println("Collection 'anggota' DITEMUKAN");

                // =========================
                // TEST INSERT DATA
                // =========================

                MongoCollection<Document> col =
                        database.getCollection("anggota");

                Document doc = new Document("nama", "Tiya")
                        .append("alamat", "Tegal");

                col.insertOne(doc);

                System.out.println("DATA TEST BERHASIL DITAMBAHKAN");

            } else {

                System.out.println("Collection 'anggota' TIDAK ADA");
            }

        } catch (Exception e) {

            System.err.println("=========================================");
            System.err.println("STATUS: KONEKSI GAGAL!");
            System.err.println("Pesan Error: " + e.getMessage());
            System.err.println("=========================================");

            e.printStackTrace();
        }
    }
}