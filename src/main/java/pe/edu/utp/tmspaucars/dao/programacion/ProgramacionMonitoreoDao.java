package pe.edu.utp.tmspaucars.dao.programacion;

import pe.edu.utp.tmspaucars.dtos.programacion.FiltroMonitoreoDTO;
import pe.edu.utp.tmspaucars.dtos.programacion.FiltroViajeDTO;
import java.util.Map;

public interface ProgramacionMonitoreoDao {
    Map<String, Object> listarMonitoreoPaginados(FiltroMonitoreoDTO filtro);
}
