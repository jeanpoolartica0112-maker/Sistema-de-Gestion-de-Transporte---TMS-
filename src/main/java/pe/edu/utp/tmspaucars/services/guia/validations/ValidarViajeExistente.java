package pe.edu.utp.tmspaucars.services.guia.validations;

import com.google.common.base.Preconditions;
import org.springframework.stereotype.Component;
import pe.edu.utp.tmspaucars.dao.programacion.ProgramacionRepository;
import pe.edu.utp.tmspaucars.dtos.GuiaRequestDTO;
import pe.edu.utp.tmspaucars.models.ProgramacionUnidad;
import pe.edu.utp.tmspaucars.models.EstadoProgramacion;

@Component
public class ValidarViajeExistente implements ValidacionGuia {
    private final ProgramacionRepository programacionRepository;

    public ValidarViajeExistente(ProgramacionRepository programacionRepository) {
        this.programacionRepository = programacionRepository;
    }

    @Override
    public void validar(GuiaRequestDTO dto) {
        ProgramacionUnidad viaje = programacionRepository.findById(dto.codigoViaje())
                .orElseThrow(() -> new RuntimeException("Error: El viaje programado con ID " + dto.codigoViaje() + " no existe."));

        Preconditions.checkArgument(viaje.getEstadoProgramacion() == EstadoProgramacion.PROGRAMADO,
                "Error: Solo se pueden asignar guías a viajes en estado PROGRAMADO. El viaje actual está en estado: %s",
                viaje.getEstadoProgramacion());
    }
}