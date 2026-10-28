package pe.edu.utp.tmspaucars.dao.programacion;


import java.util.List;
import java.util.Map;

public interface ProgramacionReporteDao {
    List<Map<String, Object>> listarFletesCrudos();
}