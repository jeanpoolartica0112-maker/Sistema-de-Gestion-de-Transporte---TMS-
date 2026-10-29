package pe.edu.utp.tmspaucars.dao.clientes;

import java.util.List;
import java.util.Map;

public interface ClienteReporteDao {
    List<Map<String, Object>> listarClientesFiltro(String filtro);
}
