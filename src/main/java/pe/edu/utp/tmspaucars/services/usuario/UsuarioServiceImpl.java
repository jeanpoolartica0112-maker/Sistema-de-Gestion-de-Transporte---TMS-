package pe.edu.utp.tmspaucars.services.usuario;

import com.google.common.base.Preconditions;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.utp.tmspaucars.dao.UsuarioRepository;
import pe.edu.utp.tmspaucars.dtos.UsuarioRequestDTO;
import pe.edu.utp.tmspaucars.dtos.UsuarioResponseDTO;
import pe.edu.utp.tmspaucars.models.Usuario;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class  UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UsuarioResponseDTO crearUsuario(UsuarioRequestDTO dto) {
        Preconditions.checkArgument(
                dto != null && dto.username() != null && !dto.username().isEmpty(),
                "El DTO y el username son obligatorios"
        );

        Usuario usuario = new Usuario();
        usuario.setUsername(dto.username());
        usuario.setPassword(dto.password());
        usuario.setNombreCompleto(dto.nombreCompleto());
        usuario.setRol(dto.rol());
        usuario.setEstado(dto.estado());

        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        return new UsuarioResponseDTO(
                usuarioGuardado.getIdUsuario(),
                usuarioGuardado.getUsername(),
                usuarioGuardado.getNombreCompleto(),
                usuarioGuardado.getRol(),
                usuarioGuardado.getEstado()
        );
    }

    @Override
    public List<UsuarioResponseDTO> listarTodos() {
        return usuarioRepository.findAll().stream()
                .map(usuario -> new UsuarioResponseDTO(
                        usuario.getIdUsuario(),
                        usuario.getUsername(),
                        usuario.getNombreCompleto(),
                        usuario.getRol(),
                        usuario.getEstado()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public UsuarioResponseDTO obtenerPorId(Integer id) {
        Preconditions.checkArgument(id != null, "El ID del usuario es obligatorio");

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con ID: " + id));

        return new UsuarioResponseDTO(
                usuario.getIdUsuario(),
                usuario.getUsername(),
                usuario.getNombreCompleto(),
                usuario.getRol(),
                usuario.getEstado()
        );
    }

    @Override
    public UsuarioResponseDTO actualizar(Integer id, UsuarioRequestDTO dto) {
        Preconditions.checkArgument(id != null, "El ID del usuario es obligatorio");
        Preconditions.checkArgument(dto != null, "Los datos de actualización son obligatorios");

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con ID: " + id));

        usuario.setUsername(dto.username());
        usuario.setPassword(dto.password());
        usuario.setNombreCompleto(dto.nombreCompleto());
        usuario.setRol(dto.rol());
        usuario.setEstado(dto.estado());

        Usuario usuarioActualizado = usuarioRepository.save(usuario);

        return new UsuarioResponseDTO(
                usuarioActualizado.getIdUsuario(),
                usuarioActualizado.getUsername(),
                usuarioActualizado.getNombreCompleto(),
                usuarioActualizado.getRol(),
                usuarioActualizado.getEstado()
        );
    }

    @Override
    public void eliminar(Integer id) {
        Preconditions.checkArgument(id != null, "El ID del usuario es obligatorio");

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con ID: " + id));

        usuario.setEstado(0);
        usuarioRepository.save(usuario);
    }
}