package pe.edu.utp.tmspaucars.dao.programacion;

import pe.edu.utp.tmspaucars.dtos.programacion.FiltroViajeDTO;

import java.util.List;
import java.util.Map;

public interface ProgramacionReporteDao {
    Map<String, Object> listarFletesPaginados(FiltroViajeDTO filtro);
}