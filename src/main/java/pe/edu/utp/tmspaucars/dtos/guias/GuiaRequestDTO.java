package pe.edu.utp.tmspaucars.dtos.guias;

public record GuiaRequestDTO(
        Integer codigoViaje,
        String tipoGuia,
        String serie,
        String correlativa,
        String rutaImagen,
        String recibida,
        Integer usuario
) {}