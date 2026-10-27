package pe.edu.utp.tmspaucars.services.usuario;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.utp.tmspaucars.dtos.UsuarioRequestDTO;
import pe.edu.utp.tmspaucars.dtos.UsuarioResponseDTO;
import pe.edu.utp.tmspaucars.models.Usuario;
import pe.edu.utp.tmspaucars.dao.UsuarioRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UsuarioResponseDTO crearUsuario(UsuarioRequestDTO dto) {
        if (dto.username() == null || dto.username().isEmpty()) {
            throw new IllegalArgumentException("El username es obligatorio");
        }

        Usuario usuario = new Usuario();
        usuario.setUsername(dto.username());
        usuario.setPassword(dto.password());
        usuario.setNombreCompleto(dto.nombreCompleto());
        usuario.setRol(dto.rol());
        usuario.setEstado(dto.estado());

        Usuario guardado = usuarioRepository.save(usuario);
        return mapToResponse(guardado);
    }

    @Override
    public List<UsuarioResponseDTO> listarTodos() {
        return usuarioRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UsuarioResponseDTO obtenerPorId(Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
        return mapToResponse(usuario);
    }

    @Override
    public UsuarioResponseDTO actualizar(Integer id, UsuarioRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));

        usuario.setUsername(dto.username());
        usuario.setPassword(dto.password());
        usuario.setNombreCompleto(dto.nombreCompleto());
        usuario.setRol(dto.rol());
        usuario.setEstado(dto.estado());

        Usuario actualizado = usuarioRepository.save(usuario);
        return mapToResponse(actualizado);
    }

    @Override
    public void eliminar(Integer id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado con ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }

    private UsuarioResponseDTO mapToResponse(Usuario usuario) {
        return new UsuarioResponseDTO(
                usuario.getIdUsuario(),
                usuario.getUsername(),
                usuario.getNombreCompleto(),
                usuario.getRol(),
                usuario.getEstado()
        );
    }
}
