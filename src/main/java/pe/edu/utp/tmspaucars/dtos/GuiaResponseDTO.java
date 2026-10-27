package pe.edu.utp.tmspaucars.dtos;

import pe.edu.utp.tmspaucars.models.Guia;
import java.time.LocalDateTime;

public record GuiaResponseDTO(
        Integer idGuia,
        Integer codigoViaje,
        String tipoGuia,
        String serie,
        String correlativa,
        String rutaImagen,
        String recibida,
        LocalDateTime fechaRecibida,
        Integer usuario
) {
    public static GuiaResponseDTO from(Guia g) {
        return new GuiaResponseDTO(
                g.getIdGuia(), g.getCodigoViaje(), g.getTipoGuia(),
                g.getSerie(), g.getCorrelativa(), g.getRutaImagen(),
                g.getRecibida(), g.getFechaRecibida(), g.getUsuario()
        );
    }
}