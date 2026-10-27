package pe.edu.utp.tmspaucars.services.unidad;

import pe.edu.utp.tmspaucars.dtos.UnidadRequestDTO;
import pe.edu.utp.tmspaucars.dtos.UnidadResponseDTO;
import pe.edu.utp.tmspaucars.models.Unidad;

import java.util.List;

public interface UnidadService {
    Unidad crearUnidad(UnidadRequestDTO dto);
    List<UnidadResponseDTO> listarTodas();
    UnidadResponseDTO obtenerPorPlaca(String placa);
    UnidadResponseDTO actualizar(String placa, UnidadRequestDTO dto);
    void eliminar(String placa);
}
