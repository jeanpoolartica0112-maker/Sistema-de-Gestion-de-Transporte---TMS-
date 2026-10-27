package pe.edu.utp.tmspaucars.services.programacion;

import com.google.common.base.Preconditions;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.edu.utp.tmspaucars.dao.ProgramacionRepository;
import pe.edu.utp.tmspaucars.dtos.ProgramacionRequestDTO;
import pe.edu.utp.tmspaucars.dtos.ProgramacionResponseDTO;
import pe.edu.utp.tmspaucars.models.EstadoProgramacion;
import pe.edu.utp.tmspaucars.models.ProgramacionUnidad;
import pe.edu.utp.tmspaucars.services.programacion.interfaces.ProgramacionService;
import pe.edu.utp.tmspaucars.services.programacion.validations.ValidacionProgramacion;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProgramacionServiceImpl implements ProgramacionService {

    private final ProgramacionRepository programacionRepository;
    private final List<ValidacionProgramacion> validaciones;

    @Override
    @Transactional
    public ProgramacionResponseDTO crear(ProgramacionRequestDTO dto) {
        validaciones.forEach(validacion -> validacion.validar(dto));

        ProgramacionUnidad programacion = ProgramacionUnidad.builder()
                .ruc(dto.ruc())
                .fechaProgramacion(dto.fechaProgramacion())
                .tracto(dto.tracto())
                .acoplado(dto.acoplado())
                .idConductor(dto.idConductor())
                .origen(dto.origen())
                .destino(dto.destino())
                .montoFlete(dto.montoFlete())
                .estadoProgramacion(EstadoProgramacion.PROGRAMADO)
                .incidencia("")
                .idUsuario(dto.idUsuario())
                .build();

        ProgramacionUnidad guardada = programacionRepository.save(programacion);
        log.info("Viaje {} programado para el cliente {}", guardada.getCodigoViaje(), guardada.getRuc());
        return ProgramacionResponseDTO.from(guardada);
    }


    @Override
    public List<ProgramacionResponseDTO> listarTodas() {
        return programacionRepository.findAll().stream()
                .map(ProgramacionResponseDTO::from)
                .collect(Collectors.toList());
    }

    @Override
    public ProgramacionResponseDTO obtenerPorId(Integer id) {
        return programacionRepository.findById(id)
                .map(ProgramacionResponseDTO::from)
                .orElseThrow(() -> new RuntimeException("No se encontró la programación con ID: " + id));
    }

    @Override
    public List<ProgramacionResponseDTO> obtenerPorPlaca(String placa) {
        List<ProgramacionUnidad> lista = programacionRepository.findByTracto(placa);
        if (lista.isEmpty()) {
            throw new RuntimeException("No hay viajes programados para la placa: " + placa);
        }
        return lista.stream().map(ProgramacionResponseDTO::from).collect(Collectors.toList());
    }

    @Override
    public List<ProgramacionResponseDTO> obtenerPorConductor(String dni) {
        List<ProgramacionUnidad> lista = programacionRepository.findByIdConductor(dni);
        if (lista.isEmpty()) {
            throw new RuntimeException("No hay viajes programados para el conductor con DNI: " + dni);
        }
        return lista.stream().map(ProgramacionResponseDTO::from).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void eliminar(Integer id) {
        if (!programacionRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar. No existe la programación con ID: " + id);
        }
        programacionRepository.deleteById(id);
        log.info("Programación con ID {} eliminada exitosamente", id);
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