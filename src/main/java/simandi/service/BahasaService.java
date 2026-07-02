/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package simandi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 *
 * @author Adies
 */
public class BahasaService {
    private static ResourceBundle bundle;
    private static Locale currentLocale;
    
    // Interface untuk mendaftarkan UI yang ingin mendengarkan perubahan bahasa
    public interface bahasaChangeListener {
        void onLanguageChanged();
    }
    // Daftar semua form/panel yang sedang aktif mendengarkan perubahan
    private static final List<bahasaChangeListener> listeners = new ArrayList<>();
    
    // Blok inisialisasi default agar tidak NullPointerException di awal aplikasi
    static {
        setLocale(Locale.forLanguageTag("id")); 
    }
    
    public static void setLocale(Locale locale) {
        currentLocale = locale;
        // Membaca file di src/resources/messages_xx.properties
        bundle = ResourceBundle.getBundle("resources.messages", currentLocale);
        
        // PICU PERUBAHAN: Beritahu semua UI untuk update teks mereka secara serentak
        notifyListeners();
    }
    public static String get(String key) {
        try {
            return bundle.getString(key);
        } catch (MissingResourceException | NullPointerException e) {
            return "!" + key + "!"; 
        }
    }
    public static Locale getCurrentLocale() {
        return currentLocale;
    }
     // --- MANAJEMEN LISTENER (OBSERVER) ---
    
    public static synchronized void registerListener(bahasaChangeListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }
    public static synchronized void unregisterListener(bahasaChangeListener listener) {
        listeners.remove(listener);
    }
     private static void notifyListeners() {
        // Jalankan perulangan untuk mengeksekusi fungsi update di setiap form
        for (bahasaChangeListener listener : listeners) {
            if (listener != null) {
                listener.onLanguageChanged();
            }
        }
    }
    
}
