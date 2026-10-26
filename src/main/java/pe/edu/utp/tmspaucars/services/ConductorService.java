package pe.edu.utp.tmspaucars.services;

import org.springframework.stereotype.Service;
import pe.edu.utp.tmspaucars.dao.ConductorRepository;
import pe.edu.utp.tmspaucars.models.Conductor;

import java.util.List;
import java.util.Optional;

@Service
public class ConductorService {

    private final ConductorRepository conductorRepository;

    public ConductorService(ConductorRepository conductorRepository) {
        this.conductorRepository = conductorRepository;
    }

    public List<Conductor> listarConductores() {
        return conductorRepository.findAll();
    }

    public Optional<Conductor> obtenerConductorPorId(String dni) {
        return conductorRepository.findById(dni);
    }

    public Conductor registrarConductor(Conductor conductor) {
        if (conductor.getEstadoDisponibilidad() == null || conductor.getEstadoDisponibilidad().isEmpty()) {
            conductor.setEstadoDisponibilidad("ACTIVO");
        }
        return conductorRepository.save(conductor);
    }


    public Optional<Conductor> actualizarConductor(String dni, Conductor conductorDetalles) {
        return conductorRepository.findById(dni).map(conductor -> {
            conductor.setNombres(conductorDetalles.getNombres());
            conductor.setApellidos(conductorDetalles.getApellidos());
            conductor.setLicencia(conductorDetalles.getLicencia());
            conductor.setCategoriaLicencia(conductorDetalles.getCategoriaLicencia());
            conductor.setVctoLicencia(conductorDetalles.getVctoLicencia());
            conductor.setEstadoDisponibilidad(conductorDetalles.getEstadoDisponibilidad());
            return conductorRepository.save(conductor); // JPA actualiza si el ID ya existe
        });
    }

    public boolean eliminarConductor(String dni) {
        if (conductorRepository.existsById(dni)) {
            conductorRepository.deleteById(dni);
            return true;
        }
        return false;
    }
}