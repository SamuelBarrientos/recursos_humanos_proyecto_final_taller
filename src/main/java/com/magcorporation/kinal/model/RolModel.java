
package main.java.com.magcorporation.kinal.model;

public class RolModel {
    private String idRol;
    private String nombreRol;

    public RolModel() {}

    public RolModel(String idRol, String nombreRol) {
        this.idRol = idRol;
        this.nombreRol = nombreRol;
    }

    public String getIdRol() { return idRol; }
    public void setIdRol(String idRol) { this.idRol = idRol; }

    public String getNombreRol() { return nombreRol; }
    public void setNombreRol(String nombreRol) { this.nombreRol = nombreRol; }
}