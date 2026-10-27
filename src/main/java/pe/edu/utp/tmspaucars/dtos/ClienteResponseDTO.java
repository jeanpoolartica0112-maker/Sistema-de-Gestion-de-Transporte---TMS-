package pe.edu.utp.tmspaucars.dtos;

import pe.edu.utp.tmspaucars.models.Cliente;

public record ClienteResponseDTO(
        String ruc,
        String razonSocial,
        String direccion,
        Integer diasCredito
) {
    public static ClienteResponseDTO from(Cliente c) {
        return new ClienteResponseDTO(c.getRuc(), c.getRazonSocial(), c.getDireccion(), c.getDiasCredito());
    }
}