
package main.java.com.magcorporation.kinal.service;

import java.sql.SQLException;
import main.java.com.magcorporation.kinal.dto.RegistroDTO;
import main.java.com.magcorporation.kinal.model.UsuarioModel;
import main.java.com.magcorporation.kinal.repository.RolRepository;
import main.java.com.magcorporation.kinal.repository.UsuarioRepository;
import org.mindrot.jbcrypt.BCrypt;

public class UsuarioService {

    private final UsuarioRepository usuarioRepository = new UsuarioRepository();
    private final RolRepository rolRepository = new RolRepository();

    public boolean registrar(RegistroDTO dto) throws SQLException, IllegalArgumentException {
        if (dto.getNombre() == null || dto.getNombre().trim().isEmpty() ||
            dto.getApellido() == null || dto.getApellido().trim().isEmpty() ||
            dto.getCorreo() == null || dto.getCorreo().trim().isEmpty() ||
            dto.getPassword() == null || dto.getPassword().trim().isEmpty() ||
            dto.getNombreRol() == null || dto.getNombreRol().trim().isEmpty()) {
            throw new IllegalArgumentException("Todos los campos son obligatorios.");
        }

        if (usuarioRepository.existsByCorreo(dto.getCorreo().trim())) {
            throw new IllegalArgumentException("El correo ya se encuentra registrado.");
        }

        String idRol = rolRepository.findIdByNombre(dto.getNombreRol());
        if (idRol == null) {
            throw new IllegalArgumentException("El rol seleccionado no es valido.");
        }

        String hash = BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt());

        UsuarioModel usuario = new UsuarioModel();
        usuario.setNombre(dto.getNombre().trim());
        usuario.setApellido(dto.getApellido().trim());
        usuario.setCorreo(dto.getCorreo().trim());
        usuario.setPasswordHash(hash);
        usuario.setIdRol(idRol);

        return usuarioRepository.save(usuario);
    }
}