package pe.edu.utp.tmspaucars.services.conductor;

import com.google.common.base.Preconditions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.edu.utp.tmspaucars.dao.ConductorRepository;
import pe.edu.utp.tmspaucars.dao.programacion.ProgramacionRepository;
import pe.edu.utp.tmspaucars.models.Conductor;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConductorServiceImpl implements ConductorService {

    private final ConductorRepository conductorRepository;
    private final ProgramacionRepository programacionRepository;

    @Override
    public List<Conductor> listarConductores() {
        return conductorRepository.findAll();
    }

    @Override
    public Optional<Conductor> obtenerConductorPorId(String dni) {
        return conductorRepository.findById(dni);
    }

    @Override
    public Conductor registrarConductor(Conductor conductor) {
        if (conductor.getEstadoDisponibilidad() == null || conductor.getEstadoDisponibilidad().isEmpty()) {
            conductor.setEstadoDisponibilidad("ACTIVO");
        }
        return conductorRepository.save(conductor);
    }

    @Override
    public Optional<Conductor> actualizarConductor(String dni, Conductor conductorDetalles) {
        return conductorRepository.findById(dni).map(conductor -> {
            conductor.setNombres(conductorDetalles.getNombres());
            conductor.setApellidos(conductorDetalles.getApellidos());
            conductor.setLicencia(conductorDetalles.getLicencia());
            conductor.setCategoriaLicencia(conductorDetalles.getCategoriaLicencia());
            conductor.setVctoLicencia(conductorDetalles.getVctoLicencia());
            conductor.setEstadoDisponibilidad(conductorDetalles.getEstadoDisponibilidad());
            return conductorRepository.save(conductor);
        });
    }

    @Override
    public boolean eliminarConductor(String dni) {
        if (!conductorRepository.existsById(dni)) {
            return false;
        }

        Preconditions.checkState(
                !programacionRepository.existsByIdConductor(dni),
                "No se puede eliminar el conductor porque se encuentra asignado a una programación de viajes."
        );

        conductorRepository.deleteById(dni);
        return true;
    }
}