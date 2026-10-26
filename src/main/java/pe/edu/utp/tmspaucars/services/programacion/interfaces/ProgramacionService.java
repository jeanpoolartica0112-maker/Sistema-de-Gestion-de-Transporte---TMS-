package pe.edu.utp.tmspaucars.services.programacion.interfaces;

import pe.edu.utp.tmspaucars.dtos.ProgramacionRequestDTO;
import pe.edu.utp.tmspaucars.dtos.ProgramacionResponseDTO;

import java.util.List;

public interface ProgramacionService {
    ProgramacionResponseDTO crear(ProgramacionRequestDTO dto);
    List<ProgramacionResponseDTO> listarTodas();
    ProgramacionResponseDTO obtenerPorId(Integer id);
    List<ProgramacionResponseDTO> obtenerPorPlaca(String placa);
    List<ProgramacionResponseDTO> obtenerPorConductor(String dni);
    void eliminar(Integer id);
}