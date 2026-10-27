package pe.edu.utp.tmspaucars.dtos;

public record UsuarioResponseDTO(
        Integer idUsuario,
        String username,
        String nombreCompleto,
        String rol,
        Integer estado
) {
}
