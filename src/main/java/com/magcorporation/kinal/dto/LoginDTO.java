
package main.java.com.magcorporation.kinal.dto;

public class LoginDTO {
    private String correo;
    private String password;

    public LoginDTO(String correo, String password) {
        this.correo = correo;
        this.password = password;
    }

    public String getCorreo() { return correo; }
    public String getPassword() { return password; }
}