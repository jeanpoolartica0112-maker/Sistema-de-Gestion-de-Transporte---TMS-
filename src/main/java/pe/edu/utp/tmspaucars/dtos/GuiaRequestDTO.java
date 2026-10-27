package pe.edu.utp.tmspaucars.dtos;

import org.apache.commons.lang3.StringUtils;

public record GuiaRequestDTO(
        Integer codigoViaje,
        String tipoGuia,
        String serie,
        String correlativa,
        String rutaImagen,
        String recibida,
        Integer usuario
) {
    public GuiaRequestDTO {
        tipoGuia = StringUtils.trim(tipoGuia);
        serie = StringUtils.upperCase(StringUtils.trim(serie));
        correlativa = StringUtils.trim(correlativa);
        recibida = StringUtils.upperCase(StringUtils.trim(recibida));
    }
}