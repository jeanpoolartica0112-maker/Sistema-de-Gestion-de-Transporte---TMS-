package pe.edu.utp.tmspaucars.services.programacion;

import com.google.common.base.Preconditions;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.edu.utp.tmspaucars.dao.facturasprogramacion.FacturaProgramacionRepository;
import pe.edu.utp.tmspaucars.dao.programacion.ProgramacionMonitoreoDao;
import pe.edu.utp.tmspaucars.dao.programacion.ProgramacionRepository;
import pe.edu.utp.tmspaucars.dao.programacion.ProgramacionReporteDao;
import pe.edu.utp.tmspaucars.dtos.ProgramacionRequestDTO;
import pe.edu.utp.tmspaucars.dtos.ProgramacionResponseDTO;
import pe.edu.utp.tmspaucars.dtos.programacion.FiltroMonitoreoDTO;
import pe.edu.utp.tmspaucars.dtos.programacion.FiltroViajeDTO;
import pe.edu.utp.tmspaucars.models.EstadoProgramacion;
import pe.edu.utp.tmspaucars.models.ProgramacionUnidad;
import pe.edu.utp.tmspaucars.services.programacion.validations.ValidacionProgramacion;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

    @Slf4j
    @Service
    @RequiredArgsConstructor
    public class ProgramacionServiceImpl implements ProgramacionService {

        private final ProgramacionRepository programacionRepository;
        private final ProgramacionReporteDao programacionReporteDao;
        private final ProgramacionMonitoreoDao programacionMonitoreoDao;
        private final FacturaProgramacionRepository facturasProgramacionRepository;
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
    @Transactional
    public ProgramacionResponseDTO actualizar(Integer codigoViaje, ProgramacionRequestDTO dto) {
        ProgramacionUnidad viajeExistente = programacionRepository.findById(codigoViaje)
                .orElseThrow(() -> new RuntimeException("No se encontró el viaje con ID: " + codigoViaje));

        validaciones.forEach(validacion -> validacion.validar(dto));

        Preconditions.checkState(
            !facturasProgramacionRepository.existsByCodigoViaje(codigoViaje),
            "No se puede actualizar el viaje, porque ya se encuentra facturado."
        );

        // Validar que solo se pueda editar si está PROGRAMADO
        Preconditions.checkArgument(
            viajeExistente.getEstadoProgramacion() == EstadoProgramacion.PROGRAMADO,
            "Solo se pueden editar viajes en estado PROGRAMADO.\nEl viaje actual está %s",
            viajeExistente.getEstadoProgramacion()
        );

        viajeExistente.setRuc(dto.ruc());
        viajeExistente.setFechaProgramacion(dto.fechaProgramacion());
        viajeExistente.setTracto(dto.tracto());
        viajeExistente.setAcoplado(dto.acoplado());
        viajeExistente.setIdConductor(dto.idConductor());
        viajeExistente.setOrigen(dto.origen());
        viajeExistente.setDestino(dto.destino());
        viajeExistente.setMontoFlete(dto.montoFlete());
//        viajeExistente.setIdUsuario(dto.idUsuario());

        ProgramacionUnidad actualizado = programacionRepository.save(viajeExistente);
        log.info("Viaje {} actualizado correctamente", actualizado.getCodigoViaje());

        return ProgramacionResponseDTO.from(actualizado);
    }

//
//    @Override
//    public List<ProgramacionResponseDTO> listarTodas() {
//        return programacionRepository.findAll().stream()
//                .map(ProgramacionResponseDTO::from)
//                .collect(Collectors.toList());
//    }
//

    @Override
    public Map<String, Object> listarFletesPaginados(FiltroViajeDTO filtro) {
        return programacionReporteDao.listarFletesPaginados(filtro);
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
    public void eliminarViaje(Integer id) {
        if (!programacionRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar. No existe la programación con ID: " + id);
        }

        Preconditions.checkState(
                !facturasProgramacionRepository.existsByCodigoViaje(id),
                "No se puede eliminar el viaje.\nPorque ya esta asociado a una Factura."
        );

        programacionRepository.deleteById(id);
        log.info("Programación con ID {} eliminada exitosamente", id);
    }

}