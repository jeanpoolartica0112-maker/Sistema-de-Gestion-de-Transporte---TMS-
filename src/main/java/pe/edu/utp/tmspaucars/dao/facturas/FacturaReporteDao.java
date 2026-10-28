package pe.edu.utp.tmspaucars.dao.facturas;

import java.util.List;
import java.util.Map;

public interface FacturaReporteDao {
    List<Map<String, Object>> listarFacturasListaGeneral();
}