package pe.edu.utp.tmspaucars.dao;

import org.springframework.stereotype.Repository;
import pe.edu.utp.tmspaucars.models.Conductor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ConductorDao {
    private final List<Conductor> conductores = new ArrayList<>();

    public List<Conductor> obtenerTodos() {
        return new ArrayList<>(conductores);
    }

    public Optional<Conductor> obtenerPorId(String dni) {
        if (dni == null) {
            return Optional.empty();
        }
        return conductores.stream()
                .filter(c -> c.getIdConductor().equals(dni))
                .findFirst();
    }

    public Conductor guardar(Conductor conductor) {
        // Si ya existe un conductor con ese DNI, lo eliminamos para reemplazarlo (Actualización)
        if (obtenerPorId(conductor.getIdConductor()).isPresent()) {
            eliminarPorId(conductor.getIdConductor());
        }
        // Lo agregamos (sea nuevo o actualizado)
        conductores.add(conductor);
        return conductor;
    }

    public boolean eliminarPorId(String dni) {
        return conductores.removeIf(c -> c.getIdConductor().equals(dni));
    }
}