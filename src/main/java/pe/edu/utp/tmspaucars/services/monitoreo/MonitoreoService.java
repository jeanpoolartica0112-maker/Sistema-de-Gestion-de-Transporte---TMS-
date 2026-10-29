package pe.edu.utp.tmspaucars.services.monitoreo;

import pe.edu.utp.tmspaucars.dtos.ProgramacionResponseDTO;
import pe.edu.utp.tmspaucars.dtos.programacion.FiltroMonitoreoDTO;
import pe.edu.utp.tmspaucars.models.EstadoProgramacion;

import java.util.Map;

public interface MonitoreoService {
    Map<String, Object> listarMonitoreoPaginados(FiltroMonitoreoDTO filtro);
    ProgramacionResponseDTO cambiarEstado(Integer codigoViaje, EstadoProgramacion nuevoEstado);
}
