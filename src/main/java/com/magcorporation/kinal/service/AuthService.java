
package main.java.com.magcorporation.kinal.service;

import java.sql.SQLException;
import main.java.com.magcorporation.kinal.dto.LoginDTO;
import main.java.com.magcorporation.kinal.model.UsuarioModel;
import main.java.com.magcorporation.kinal.repository.UsuarioRepository;
import org.mindrot.jbcrypt.BCrypt;

public class AuthService {

    private final UsuarioRepository usuarioRepository = new UsuarioRepository();
    private static UsuarioModel usuarioAutenticado;

    public UsuarioModel login(LoginDTO dto) throws SQLException, IllegalArgumentException {
        if (dto.getCorreo() == null || dto.getCorreo().trim().isEmpty() ||
            dto.getPassword() == null || dto.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("Debe ingresar todos los campos.");
        }

        UsuarioModel usuario = usuarioRepository.findByCorreo(dto.getCorreo().trim());

        if (usuario == null || !BCrypt.checkpw(dto.getPassword(), usuario.getPasswordHash())) {
            throw new IllegalArgumentException("Credenciales incorrectas.");
        }

        usuarioAutenticado = usuario;
        return usuario;
    }

    public static UsuarioModel getUsuarioAutenticado() {
        return usuarioAutenticado;
    }

    public static void logout() {
        usuarioAutenticado = null;
    }
}