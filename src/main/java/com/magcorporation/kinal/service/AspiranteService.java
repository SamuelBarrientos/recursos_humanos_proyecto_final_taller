
package main.java.com.magcorporation.kinal.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import main.java.com.magcorporation.kinal.dto.AspiranteDTO;
import main.java.com.magcorporation.kinal.exception.AspiranteException;
import main.java.com.magcorporation.kinal.model.AspiranteModel;
import main.java.com.magcorporation.kinal.repository.AspiranteRepository;

public class AspiranteService {

    private static final int EDAD_MINIMA = 16;
    private static final int EDAD_MAXIMA = 100;

    private final AspiranteRepository aspiranteRepository;

    public AspiranteService(AspiranteRepository aspiranteRepository) {
        this.aspiranteRepository = aspiranteRepository;
    }

    public List<AspiranteDTO> listar() throws AspiranteException {
        try {
            List<AspiranteDTO> resultado = new ArrayList<>();
            for (AspiranteModel a : aspiranteRepository.findAll()) {
                resultado.add(new AspiranteDTO(
                    a.getIdAspirante(), a.getNombre(), a.getApellido(), a.getCorreo(),
                    a.getTelefono(), a.getDireccion(), String.valueOf(a.getEdad())
                ));
            }
            return resultado;
        } catch (SQLException e) {
            throw new AspiranteException("No se pudo obtener la lista de aspirantes: " + e.getMessage(), e);
        }
    }

    public void agregar(AspiranteDTO dto) throws AspiranteException {
        AspiranteModel aspirante = validarYConvertir(dto);
        try {
            if (aspiranteRepository.existsByCorreo(aspirante.getCorreo(), null)) {
                throw new AspiranteException("Ya existe un aspirante con ese correo.");
            }
            if (!aspiranteRepository.save(aspirante)) {
                throw new AspiranteException("No se pudo agregar el aspirante.");
            }
        } catch (SQLException e) {
            throw new AspiranteException("Error al agregar el aspirante: " + e.getMessage(), e);
        }
    }

    public void actualizar(AspiranteDTO dto) throws AspiranteException {
        if (dto.getIdAspirante() == null || dto.getIdAspirante().trim().isEmpty()) {
            throw new AspiranteException("Seleccione un aspirante de la tabla para actualizar.");
        }
        AspiranteModel aspirante = validarYConvertir(dto);
        aspirante.setIdAspirante(dto.getIdAspirante());
        try {
            if (aspiranteRepository.existsByCorreo(aspirante.getCorreo(), aspirante.getIdAspirante())) {
                throw new AspiranteException("Ya existe otro aspirante con ese correo.");
            }
            if (!aspiranteRepository.update(aspirante)) {
                throw new AspiranteException("No se encontro el aspirante a actualizar.");
            }
        } catch (SQLException e) {
            throw new AspiranteException("Error al actualizar el aspirante: " + e.getMessage(), e);
        }
    }

    public void eliminar(String idAspirante) throws AspiranteException {
        if (idAspirante == null || idAspirante.trim().isEmpty()) {
            throw new AspiranteException("Seleccione un aspirante de la tabla para eliminar.");
        }
        try {
            if (!aspiranteRepository.deleteById(idAspirante)) {
                throw new AspiranteException("No se encontro el aspirante a eliminar.");
            }
        } catch (SQLException e) {
            throw new AspiranteException("Error al eliminar el aspirante: " + e.getMessage(), e);
        }
    }

    private AspiranteModel validarYConvertir(AspiranteDTO dto) throws AspiranteException {
        if (estaVacio(dto.getNombre()) || estaVacio(dto.getApellido()) || estaVacio(dto.getCorreo()) ||
            estaVacio(dto.getTelefono()) || estaVacio(dto.getDireccion()) || estaVacio(dto.getEdad())) {
            throw new AspiranteException("Todos los campos son obligatorios.");
        }

        String correo = dto.getCorreo().trim();
        if (!correo.matches("^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$")) {
            throw new AspiranteException("El correo no tiene un formato valido.");
        }

        String telefono = dto.getTelefono().trim();
        if (!telefono.matches("^[0-9+\\- ]{8,15}$")) {
            throw new AspiranteException("El telefono debe tener entre 8 y 15 digitos.");
        }

        int edad;
        try {
            edad = Integer.parseInt(dto.getEdad().trim());
        } catch (NumberFormatException e) {
            throw new AspiranteException("La edad debe ser un numero entero.");
        }
        if (edad < EDAD_MINIMA || edad > EDAD_MAXIMA) {
            throw new AspiranteException("La edad debe estar entre " + EDAD_MINIMA + " y " + EDAD_MAXIMA + ".");
        }

        AspiranteModel aspirante = new AspiranteModel();
        aspirante.setNombre(dto.getNombre().trim());
        aspirante.setApellido(dto.getApellido().trim());
        aspirante.setCorreo(correo);
        aspirante.setTelefono(telefono);
        aspirante.setDireccion(dto.getDireccion().trim());
        aspirante.setEdad(edad);
        return aspirante;
    }

    private boolean estaVacio(String valor) {
        return valor == null || valor.trim().isEmpty();
    }
}
