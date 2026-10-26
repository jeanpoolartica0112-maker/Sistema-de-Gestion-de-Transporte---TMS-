package pe.edu.utp.tmspaucars.services.programacion.validations;

import org.springframework.stereotype.Component;
import pe.edu.utp.tmspaucars.dao.UnidadRepository;
import pe.edu.utp.tmspaucars.dtos.ProgramacionRequestDTO;
import pe.edu.utp.tmspaucars.models.Unidad;
import java.time.LocalDate;

@Component
public class ValidarVigenciasUnidad implements ValidacionProgramacion {
    private final UnidadRepository unidadRepository;

    public ValidarVigenciasUnidad(UnidadRepository unidadRepository) {
        this.unidadRepository = unidadRepository;
    }

    @Override
    public void validar(ProgramacionRequestDTO programacion) {
        Unidad unidad = unidadRepository.findById(programacion.tracto())
                .orElseThrow(() -> new RuntimeException("Error: La unidad " + programacion.tracto() + " no existe."));

        LocalDate hoy = LocalDate.now();
        if (unidad.getFechaSoat().isBefore(hoy)) {
            throw new RuntimeException("Error: El SOAT de la unidad " + unidad.getPlaca() + " está vencido.");
        }
        if (unidad.getFechaRevisionTecnica().isBefore(hoy)) {
            throw new RuntimeException("Error: La Revisión Técnica de la unidad " + unidad.getPlaca() + " está vencida.");
        }
    }
}