package pe.edu.utp.tmspaucars.services.monitoreo;

import lombok.extern.slf4j.Slf4j;
import com.google.common.base.Preconditions;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.utp.tmspaucars.dao.GuiaRepository;
import pe.edu.utp.tmspaucars.dao.programacion.ProgramacionMonitoreoDao;
import pe.edu.utp.tmspaucars.dao.programacion.ProgramacionRepository;
import pe.edu.utp.tmspaucars.dtos.ProgramacionResponseDTO;
import pe.edu.utp.tmspaucars.dtos.programacion.FiltroMonitoreoDTO;
import pe.edu.utp.tmspaucars.models.EstadoProgramacion;
import pe.edu.utp.tmspaucars.models.ProgramacionUnidad;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class MonitoreoServiceImpl implements MonitoreoService {

    private final ProgramacionMonitoreoDao monitoreoDao;
    private final ProgramacionRepository programacionRepository;
    private final GuiaRepository guiaRepository;

    @Override
    public Map<String, Object> listarMonitoreoPaginados(FiltroMonitoreoDTO filtro) {
        return monitoreoDao.listarMonitoreoPaginados(filtro);
    }

    @Override
    @Transactional
    public ProgramacionResponseDTO cambiarEstado(Integer id, EstadoProgramacion nuevoEstado) {
        ProgramacionUnidad viaje = programacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el viaje con ID: " + id));

        EstadoProgramacion estadoActual = viaje.getEstadoProgramacion();

        Preconditions.checkArgument(estadoActual != EstadoProgramacion.ENTREGADO,
                "Un viaje ENTREGADO ya no puede cambiar de estado.");
        Preconditions.checkArgument(estadoActual != EstadoProgramacion.CANCELADO,
                "Un viaje CANCELADO ya no puede cambiar de estado.");

        if (estadoActual == EstadoProgramacion.PROGRAMADO) {
            Preconditions.checkArgument(nuevoEstado == EstadoProgramacion.EN_RUTA || nuevoEstado == EstadoProgramacion.CANCELADO,
                    "Desde PROGRAMADO solo se puede pasar a EN_RUTA o CANCELADO.");

            if (nuevoEstado == EstadoProgramacion.EN_RUTA) {
                boolean tieneGuia = guiaRepository.existsByCodigoViajeAndTipoGuia(id, "31");
                Preconditions.checkArgument(tieneGuia,
                        "No se puede iniciar la ruta.\nEl viaje no tiene asignada una Guía de Transportista (Tipo 31).");
            }

        } else if (estadoActual == EstadoProgramacion.EN_RUTA) {
            Preconditions.checkArgument(nuevoEstado == EstadoProgramacion.ENTREGADO,
                    "Desde EN_RUTA solo se puede pasar a ENTREGADO.");
        }

        viaje.setEstadoProgramacion(nuevoEstado);
        ProgramacionUnidad actualizado = programacionRepository.save(viaje);

        log.info("El viaje {} cambió de estado de {} a {}", actualizado.getCodigoViaje(), estadoActual, nuevoEstado);
        return ProgramacionResponseDTO.from(actualizado);
    }
}