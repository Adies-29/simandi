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
        // --- HELPER PENERJEMAH JURUSAN ---
    public static String terjemahkanJurusan(String jurusanAsli) {
        if (jurusanAsli == null || jurusanAsli.trim().isEmpty()) return "-";
        
        try {
            if (jurusanAsli.equalsIgnoreCase("Teknik Informatika") || jurusanAsli.equalsIgnoreCase("Computer Science") || jurusanAsli.equalsIgnoreCase("Informatica")) {
                return get("ui.major.ti");
            } else if (jurusanAsli.equalsIgnoreCase("Sistem Informasi") || jurusanAsli.equalsIgnoreCase("Information Systems") || jurusanAsli.equalsIgnoreCase("Informatiekunde")) {
                return get("ui.major.si");
            } else if (jurusanAsli.equalsIgnoreCase("Teknik Komputer") || jurusanAsli.equalsIgnoreCase("Computer Engineering") || jurusanAsli.equalsIgnoreCase("Computertechniek")) {
                return get("ui.major.tk");
            } else if (jurusanAsli.equalsIgnoreCase("Manajemen Informatika") || jurusanAsli.equalsIgnoreCase("Informatics Management") || jurusanAsli.equalsIgnoreCase("Informatiemanagement")) {
                return get("ui.major.mi");
            } else if (jurusanAsli.equalsIgnoreCase("Teknik Elektro") || jurusanAsli.equalsIgnoreCase("Electrical Engineering") || jurusanAsli.equalsIgnoreCase("Elektrotechniek")) {
                return get("ui.major.te");
            } else if (jurusanAsli.equalsIgnoreCase("Teknik Mesin") || jurusanAsli.equalsIgnoreCase("Mechanical Engineering") || jurusanAsli.equalsIgnoreCase("Werktuigbouwkunde")) {
                return get("ui.major.tm");
            } else if (jurusanAsli.equalsIgnoreCase("Akuntansi") || jurusanAsli.equalsIgnoreCase("Accounting") || jurusanAsli.equalsIgnoreCase("Accountancy")) {
                return get("ui.major.ak");
            }
        } catch (Exception e) {
            return jurusanAsli;
        }
        return jurusanAsli;
    }
    
     public static String terjemahkanStatus(String statusAsli) {
        if (statusAsli == null || statusAsli.trim().isEmpty()) return "-";
        
        try {
            if (statusAsli.equalsIgnoreCase("Aktif") || statusAsli.equalsIgnoreCase("Active") || statusAsli.equalsIgnoreCase("Actief")) {
                return get("ui.card.status.active").replace("● ", "");
            } else if (statusAsli.equalsIgnoreCase("No Aktif") || statusAsli.equalsIgnoreCase("Tidak Aktif") || statusAsli.equalsIgnoreCase("Inactive") || statusAsli.equalsIgnoreCase("Inactief")) {
                return get("ui.card.status.inactive").replace("● ", "");
            } else if (statusAsli.equalsIgnoreCase("Cuti") || statusAsli.equalsIgnoreCase("On Leave") || statusAsli.equalsIgnoreCase("Verlof")) {
                return get("ui.status.leave");
            }
        } catch (Exception e) {
            return statusAsli;
        }
        return statusAsli;
    }

}
