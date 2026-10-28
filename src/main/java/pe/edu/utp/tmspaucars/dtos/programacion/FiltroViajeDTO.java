package pe.edu.utp.tmspaucars.dtos.programacion;

import java.time.LocalDate;

public record FiltroViajeDTO(
        String estado,
        String destino,
        String placa,
        LocalDate fechaInicio,
        LocalDate fechaFin,
        Integer pagina,
        Integer tamanioPagina
) {}