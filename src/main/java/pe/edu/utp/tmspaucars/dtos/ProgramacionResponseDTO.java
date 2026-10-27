package pe.edu.utp.tmspaucars.dtos;

import pe.edu.utp.tmspaucars.models.ProgramacionUnidad;
import pe.edu.utp.tmspaucars.models.EstadoProgramacion;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ProgramacionResponseDTO(
        Integer codigoViaje,
        String ruc,
        LocalDate fechaProgramacion,
        String tracto,
        String acoplado,
        String idConductor,
        String origen,
        String destino,
        BigDecimal montoFlete,
        EstadoProgramacion estadoProgramacion,
        String incidencia) {

    public static ProgramacionResponseDTO from(ProgramacionUnidad p) {
        return new ProgramacionResponseDTO(
                p.getCodigoViaje(), p.getRuc(), p.getFechaProgramacion(),
                p.getTracto(), p.getAcoplado(), p.getIdConductor(),
                p.getOrigen(), p.getDestino(), p.getMontoFlete(),
                p.getEstadoProgramacion(), p.getIncidencia());
    }

}