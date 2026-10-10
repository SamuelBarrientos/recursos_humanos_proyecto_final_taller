
package main.java.com.magcorporation.kinal.repository;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import main.java.com.magcorporation.kinal.config.DatabaseConnection;
import main.java.com.magcorporation.kinal.model.UsuarioModel;

public class UsuarioRepository {

    public UsuarioModel findByCorreo(String correo) throws SQLException {
        String sql = "SELECT u.id_usuario, u.nombre, u.apellido, u.correo, u.password_hash, u.id_rol, r.nombre_rol " +
                     "FROM usuarios u JOIN roles r ON u.id_rol = r.id_rol WHERE u.correo = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, correo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    UsuarioModel usuario = new UsuarioModel();
                    usuario.setIdUsuario(rs.getString("id_usuario"));
                    usuario.setNombre(rs.getString("nombre"));
                    usuario.setApellido(rs.getString("apellido"));
                    usuario.setCorreo(rs.getString("correo"));
                    usuario.setPasswordHash(rs.getString("password_hash"));
                    usuario.setIdRol(rs.getString("id_rol"));
                    usuario.setNombreRol(rs.getString("nombre_rol"));
                    return usuario;
                }
            }
        }
        return null;
    }

    public boolean existsByCorreo(String correo) throws SQLException {
        String sql = "SELECT 1 FROM usuarios WHERE correo = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, correo);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    public boolean save(UsuarioModel usuario) throws SQLException {
        String sql = "INSERT INTO usuarios (id_usuario, nombre, apellido, correo, password_hash, id_rol) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, UUID.randomUUID().toString());
            stmt.setString(2, usuario.getNombre());
            stmt.setString(3, usuario.getApellido());
            stmt.setString(4, usuario.getCorreo());
            stmt.setString(5, usuario.getPasswordHash());
            stmt.setString(6, usuario.getIdRol());
            return stmt.executeUpdate() > 0;
        }
    }
}