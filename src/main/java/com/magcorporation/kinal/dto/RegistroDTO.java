
package main.java.com.magcorporation.kinal.dto;

public class RegistroDTO {
    private String nombre;
    private String apellido;
    private String correo;
    private String password;
    private String nombreRol;

    public RegistroDTO(String nombre, String apellido, String correo, String password, String nombreRol) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.password = password;
        this.nombreRol = nombreRol;
    }

    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getCorreo() { return correo; }
    public String getPassword() { return password; }
    public String getNombreRol() { return nombreRol; }
}