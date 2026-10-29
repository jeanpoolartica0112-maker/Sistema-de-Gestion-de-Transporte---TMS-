package pe.edu.utp.tmspaucars.services.programacion;

import pe.edu.utp.tmspaucars.dtos.ProgramacionRequestDTO;
import pe.edu.utp.tmspaucars.dtos.ProgramacionResponseDTO;
import pe.edu.utp.tmspaucars.dtos.programacion.FiltroViajeDTO;
import pe.edu.utp.tmspaucars.models.EstadoProgramacion;

import java.util.List;
import java.util.Map;

public interface ProgramacionService {
        ProgramacionResponseDTO crear(ProgramacionRequestDTO dto);
        ProgramacionResponseDTO actualizar(Integer codigoViaje, ProgramacionRequestDTO dto);
        Map<String, Object> listarFletesPaginados(FiltroViajeDTO filtro);

        ProgramacionResponseDTO obtenerPorId(Integer id);
        List<ProgramacionResponseDTO> obtenerPorPlaca(String placa);
        List<ProgramacionResponseDTO> obtenerPorConductor(String dni);
//        ProgramacionResponseDTO cambiarEstado(Integer id, EstadoProgramacion nuevoEstado);
        void eliminarViaje(Integer id);
    }