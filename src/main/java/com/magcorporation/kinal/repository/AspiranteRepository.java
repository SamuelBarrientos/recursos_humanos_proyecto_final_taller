
package main.java.com.magcorporation.kinal.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import main.java.com.magcorporation.kinal.config.DatabaseConnection;
import main.java.com.magcorporation.kinal.model.AspiranteModel;

public class AspiranteRepository {

    public List<AspiranteModel> findAll() throws SQLException {
        String sql = "SELECT id_aspirante, nombre, apellido, correo, telefono, direccion, edad " +
                     "FROM aspirantes ORDER BY apellido, nombre";
        List<AspiranteModel> aspirantes = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                aspirantes.add(new AspiranteModel(
                    rs.getString("id_aspirante"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("correo"),
                    rs.getString("telefono"),
                    rs.getString("direccion"),
                    rs.getInt("edad")
                ));
            }
        }
        return aspirantes;
    }

  
    public boolean existsByCorreo(String correo, String idExcluido) throws SQLException {
        String sql = "SELECT 1 FROM aspirantes WHERE correo = ? AND id_aspirante <> ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, correo);
            stmt.setString(2, idExcluido == null ? "" : idExcluido);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    public boolean save(AspiranteModel aspirante) throws SQLException {
        String sql = "INSERT INTO aspirantes (id_aspirante, nombre, apellido, correo, telefono, direccion, edad) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, UUID.randomUUID().toString());
            stmt.setString(2, aspirante.getNombre());
            stmt.setString(3, aspirante.getApellido());
            stmt.setString(4, aspirante.getCorreo());
            stmt.setString(5, aspirante.getTelefono());
            stmt.setString(6, aspirante.getDireccion());
            stmt.setInt(7, aspirante.getEdad());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean update(AspiranteModel aspirante) throws SQLException {
        String sql = "UPDATE aspirantes SET nombre = ?, apellido = ?, correo = ?, telefono = ?, " +
                     "direccion = ?, edad = ? WHERE id_aspirante = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, aspirante.getNombre());
            stmt.setString(2, aspirante.getApellido());
            stmt.setString(3, aspirante.getCorreo());
            stmt.setString(4, aspirante.getTelefono());
            stmt.setString(5, aspirante.getDireccion());
            stmt.setInt(6, aspirante.getEdad());
            stmt.setString(7, aspirante.getIdAspirante());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean deleteById(String idAspirante) throws SQLException {
        String sql = "DELETE FROM aspirantes WHERE id_aspirante = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, idAspirante);
            return stmt.executeUpdate() > 0;
        }
    }
}
