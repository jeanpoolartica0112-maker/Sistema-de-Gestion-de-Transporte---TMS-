package pe.edu.utp.tmspaucars.services.usuario;

import pe.edu.utp.tmspaucars.dtos.UsuarioRequestDTO;
import pe.edu.utp.tmspaucars.dtos.UsuarioResponseDTO;

import java.util.List;

public interface UsuarioService {
    UsuarioResponseDTO crearUsuario(UsuarioRequestDTO dto);
    List<UsuarioResponseDTO> listarTodos();
    UsuarioResponseDTO obtenerPorId(Integer id);
    UsuarioResponseDTO actualizar(Integer id, UsuarioRequestDTO dto);
    void eliminar(Integer id);
}
