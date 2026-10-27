package pe.edu.utp.tmspaucars.services.conductor;

import pe.edu.utp.tmspaucars.models.Conductor;

import java.util.List;
import java.util.Optional;

public interface ConductorService {
    List<Conductor> listarConductores();
    Optional<Conductor> obtenerConductorPorId(String dni);
    Conductor registrarConductor(Conductor conductor);
    Optional<Conductor> actualizarConductor(String dni, Conductor conductorDetalles);
    boolean eliminarConductor(String dni);
}