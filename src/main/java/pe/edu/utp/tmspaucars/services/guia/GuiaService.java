package pe.edu.utp.tmspaucars.services.guia;

import pe.edu.utp.tmspaucars.dtos.GuiaRequestDTO;
import pe.edu.utp.tmspaucars.dtos.GuiaResponseDTO;
import java.util.List;

public interface GuiaService {
    GuiaResponseDTO crearGuia(GuiaRequestDTO dto);
    List<GuiaResponseDTO> obtenerPorViaje(Integer codigoViaje);
}