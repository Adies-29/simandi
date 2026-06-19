/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package simandi.objek;

/**
 *
 * @author Lenovo
 */
public class Anggota1 {

    private String uidRfid;
    private String Nim;
    private String namaLengkap;
    private String jurusan;
    private String kelas;
    private String status;
    
    public Anggota1(){
        
    }
    
    public Anggota1(String uidRfid, String Nim, String namaLengkap, String jurusan, String kelas, String status) {
    this.uidRfid = uidRfid;
    this.Nim = Nim;
    this.namaLengkap = namaLengkap;
    this.jurusan = jurusan;
    this.kelas = kelas;
    this.status = status;
}

    public String getUidRfid() {
        return uidRfid;
    }

    public void setUidRfid(String uidRfid) {
        this.uidRfid = uidRfid;
    }

    public String getNim() {
        return Nim;
    }

    public void setNim(String Nim) {
        this.Nim = Nim;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

    public String getJurusan() {
        return jurusan;
    }

    public void setJurusan(String jurusan) {
        this.jurusan = jurusan;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}