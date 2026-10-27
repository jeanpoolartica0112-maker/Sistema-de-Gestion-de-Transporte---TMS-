package pe.edu.utp.tmspaucars.services.guia;

import pe.edu.utp.tmspaucars.dtos.GuiaRequestDTO;
import pe.edu.utp.tmspaucars.dtos.GuiaResponseDTO;
import java.util.List;

public interface GuiaService {
    GuiaResponseDTO crear(GuiaRequestDTO dto);
    List<GuiaResponseDTO> listarPorViaje(Integer codigoViaje);
}