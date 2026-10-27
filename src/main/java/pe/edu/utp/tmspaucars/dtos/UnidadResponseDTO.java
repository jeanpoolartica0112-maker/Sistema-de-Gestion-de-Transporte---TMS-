package pe.edu.utp.tmspaucars.dtos;

import pe.edu.utp.tmspaucars.models.Unidad;
import java.time.LocalDate;

public record UnidadResponseDTO(
        String placa, String tipo, Integer capacidadCarga,
        String configuracionVehicular, String certificadoVehicular,
        LocalDate fechaSoat, LocalDate fechaRevisionTecnica, LocalDate fechaPermisoMtc
) {
    public static UnidadResponseDTO from(Unidad u) {
        return new UnidadResponseDTO(
                u.getPlaca(), u.getTipo(), u.getCapacidadCarga(),
                u.getConfiguracionVehicular(), u.getCertificadoVehicular(),
                u.getFechaSoat(), u.getFechaRevisionTecnica(), u.getFechaPermisoMtc()
        );
    }
}