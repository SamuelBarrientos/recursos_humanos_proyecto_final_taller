
package main.java.com.magcorporation.kinal.dto;


public class AspiranteDTO {
    private final String idAspirante;
    private final String nombre;
    private final String apellido;
    private final String correo;
    private final String telefono;
    private final String direccion;
    private final String edad;

    public AspiranteDTO(String idAspirante, String nombre, String apellido, String correo,
                        String telefono, String direccion, String edad) {
        this.idAspirante = idAspirante;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.telefono = telefono;
        this.direccion = direccion;
        this.edad = edad;
    }

    public String getIdAspirante() { return idAspirante; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getCorreo() { return correo; }
    public String getTelefono() { return telefono; }
    public String getDireccion() { return direccion; }
    public String getEdad() { return edad; }
}
