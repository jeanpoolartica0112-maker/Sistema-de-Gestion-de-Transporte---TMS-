package pe.edu.utp.tmspaucars.services.facturas;

import pe.edu.utp.tmspaucars.models.Factura;

import java.util.List;
import java.util.Map;

public interface FacturaService {
    List<Factura> listarTodas();
    Factura guardar(Factura factura);
    List<Map<String, Object>> listarFacturasListaGeneral();
}
