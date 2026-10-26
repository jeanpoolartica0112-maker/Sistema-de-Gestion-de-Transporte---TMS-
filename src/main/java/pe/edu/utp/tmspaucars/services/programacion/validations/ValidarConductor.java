package pe.edu.utp.tmspaucars.services.programacion.validations;

import org.springframework.stereotype.Component;
import pe.edu.utp.tmspaucars.dao.ConductorRepository;
import pe.edu.utp.tmspaucars.dtos.ProgramacionRequestDTO;
import pe.edu.utp.tmspaucars.models.Conductor;

import java.time.LocalDate;

@Component
public class ValidarConductor implements ValidacionProgramacion {
    private final ConductorRepository conductorRepository;

    public ValidarConductor(ConductorRepository conductorRepository) {
        this.conductorRepository = conductorRepository;
    }

    @Override
    public void validar(ProgramacionRequestDTO programacion) {
        Conductor conductor = conductorRepository.findById(programacion.idConductor())
                .orElseThrow(() -> new RuntimeException("Error: El conductor con DNI " + programacion.idConductor() + " no existe."));

        if (conductor.getVctoLicencia().isBefore(LocalDate.now())) {
            throw new RuntimeException("Error: La licencia del conductor está vencida.");
        }
        if (!conductor.getEstadoDisponibilidad().equalsIgnoreCase("ACTIVO")) {
            throw new RuntimeException("Error: El conductor no se encuentra ACTIVO.");
        }
    }
}