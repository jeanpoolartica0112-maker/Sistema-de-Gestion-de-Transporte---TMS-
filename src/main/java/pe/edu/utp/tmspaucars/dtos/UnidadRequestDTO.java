package pe.edu.utp.tmspaucars.dtos;

import java.time.LocalDate;

public record UnidadRequestDTO(
        String placa,
        String tipo,
        Integer capacidadCarga,
        String configuracionVehicular,
        String certificadoVehicular,
        LocalDate fechaSoat,
        LocalDate fechaRevisionTecnica,
        LocalDate fechaPermisoMtc
) {}