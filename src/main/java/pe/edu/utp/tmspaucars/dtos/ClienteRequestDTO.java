package pe.edu.utp.tmspaucars.dtos;

import com.google.common.base.Strings;

public record ClienteRequestDTO(
        String ruc,
        String razonSocial,
        String direccion,
        Integer diasCredito
) {

    public ClienteRequestDTO {
        ruc = Strings.nullToEmpty(ruc).trim();
        razonSocial = Strings.nullToEmpty(razonSocial).trim();
        direccion = Strings.nullToEmpty(direccion).trim();
    }
}