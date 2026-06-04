/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package simandi.objek;

import java.time.LocalDateTime;

/**
 *
 * @author Adies
 */
public class Admin {
    
    private String fullname;
    private String username;
    private String password; // Tersimpan dalam format Hash SHA-256
    private LocalDateTime lastLogin;
    
    public Admin(){
    
    }
    public Admin(String fullname, String username, String password, LocalDateTime lastLogin) {
        this.fullname = fullname;
        this.username = username;
        this.password = password;
        this.lastLogin = lastLogin;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }
    
        
    
}
