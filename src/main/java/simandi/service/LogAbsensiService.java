/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package simandi.service;

import java.util.List;
import simandi.DAO.GenericDAO;
import simandi.objek.LogAbsensi;

/**
 *
 * @author Adies
 */
public class LogAbsensiService {

    private final GenericDAO<LogAbsensi> DAO;

    public LogAbsensiService() {
        // Menghubungkan otomatis ke koleksi "log_absensi" di MongoDB
        DAO = new GenericDAO<>("log_absensi", LogAbsensi.class);
    }

    /**
     * 1. SIMPAN: Mencatat rekap scan absensi baru ke MongoDB
     */
    public void simpanLog(String uidRfid, String namaLengkap, String status) {
        LogAbsensi log = new LogAbsensi();
        log.setUidRfid(uidRfid);
        log.setNamaLengkap(namaLengkap);
        log.setStatus(status);
        
        // =====================================================================
        // MENGGUNAKAN STRING.FORMAT ANGKA (DIJAMIN TIDAK AKAN ERROR PATTERN!)
        // =====================================================================
        java.time.LocalDateTime now = java.time.LocalDateTime.now();
        String waktuBersih = String.format("%04d-%02d-%02d %02d:%02d:%02d WIB", 
            now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 
            now.getHour(), now.getMinute(), now.getSecond());
            
        log.setLocalDateTimewaktuTap(waktuBersih);
        
        DAO.save(log);
        System.out.println("DEBUG MONGODB: Berhasil mencatat log absensi untuk -> " + namaLengkap);
    }


    /**
     * 2. BACA: Mengambil seluruh riwayat kunjungan dari MongoDB
     */
    public List<LogAbsensi> ambilSemuaLog() {
        return DAO.findAll();
    }
}

