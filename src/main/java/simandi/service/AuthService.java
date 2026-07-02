/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package simandi.service;

import com.mongodb.client.model.Filters;
import java.awt.Frame;
import java.time.LocalDateTime;
import javax.swing.JOptionPane;
import simandi.DAO.GenericDAO;
import simandi.objek.Admin;
import simandi.ui.jframes.dashboardAdmin;
import simandi.ui.jframes.login;
import simandi.util.SecurityUtils;

/**
 *
 * @author Adies
 */
public class AuthService {
    // Inisialisasi DAO untuk koleksi "users" [8]
    private final GenericDAO<Admin> adminDAO = new GenericDAO<>("admin", Admin.class);
    /**
     * Melakukan proses login dengan memvalidasi kredensial (Sub-CPMK 4) [5].
     *
     * @param username
     * @param plainPassword
     * @param Login
     */
    public void login (String username, String plainPassword, login Login){
        // 1. Mengubah password input menjadi hash SHA-256 untuk keamanan [2]
        String hashedInput = SecurityUtils.getHash(plainPassword, SecurityUtils.SHA_256);
        System.out.println("Username dari form UI: " + username);
        System.out.println("Password mentah dari form UI: " + plainPassword);
        System.out.println("Password setelah di-hash: " + hashedInput);
        
        // 2. Mencari user di database berdasarkan username DAN password hash [7, 9]
        Admin admin = adminDAO.findOne(Filters.and(
                Filters.eq("username", username),
                Filters.eq("password", hashedInput)
        ));
        // 3. Validasi hasil pencarian
        if(admin != null){
            // Update waktu login terakhir
            admin.setLastLogin(LocalDateTime.now());
            adminDAO.update(Filters.eq("username", username), admin);
            
            // Berhasil: Masuk ke Halaman Admin
            JOptionPane.showMessageDialog(null, "Selamat Datang, " + admin.getFullname());
            dashboardAdmin DashAdm = new dashboardAdmin();
            DashAdm.setLocationRelativeTo(null); 
            DashAdm.setVisible(true);
            Login.setVisible(false); 
        } else{
            // Gagal: Notifikasi Error
            JOptionPane.showMessageDialog(null,
                    "Username atau Password Salah!",
                    "Login Gagal",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Metode untuk menambahkan user/admin baru ke database. Implementasi sesuai
     * target SPRINT 3 untuk pengamanan kredensial [2].
     *
     * @param fullname Nama lengkap user
     * @param username Username untuk login
     * @param plainPassword Password mentah (akan di-hash otomatis)
     */
    public void registerUser(String fullname, String username, String plainPassword) {
        // 1. Proses Hashing: Mengamankan password mentah menggunakan SHA-256 [1]
        String hashedPassword = SecurityUtils.getHash(plainPassword, SecurityUtils.SHA_256);
        

        // 2. Instansiasi Objek: Membuat objek User baru dengan password yang sudah di-hash
        Admin newAdmin = new Admin(fullname, username, hashedPassword, null);
        // lastLogin disetel null karena user baru belum pernah masuk sistem
        
        System.out.println("Proses pendaftaran selesai! Silakan cek MongoDB.");

        // 3. Operasi Create: Menyimpan dokumen user ke koleksi MongoDB melalui GenericDAO [3], [4]
        try {
            adminDAO.save(newAdmin); // Memanggil insertOne melalui GenericDAO [5]
        } catch (Exception e) {
            // Standar Debugging: Mengidentifikasi error log secara mandiri [6]
            JOptionPane.showMessageDialog(null, "Gagal mendaftarkan user: " + e.getMessage());
        }
    }
}


