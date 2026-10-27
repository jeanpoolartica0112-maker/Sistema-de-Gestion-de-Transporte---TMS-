package pe.edu.utp.tmspaucars.dtos;

import com.google.common.base.Strings;

public record UsuarioRequestDTO(
        String username,
        String password,
        String nombreCompleto,
        String rol,
        Integer estado
) {
    public UsuarioRequestDTO {
        username = Strings.nullToEmpty(username).trim();
        password = Strings.nullToEmpty(password).trim();
        nombreCompleto = Strings.nullToEmpty(nombreCompleto).trim();
        rol = Strings.nullToEmpty(rol).trim();
    }
}
