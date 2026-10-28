package pe.edu.utp.tmspaucars.services.facturas;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.utp.tmspaucars.dao.facturas.FacturaReporteDao;
import pe.edu.utp.tmspaucars.dao.facturas.FacturaRepository;
import pe.edu.utp.tmspaucars.models.Factura;
import pe.edu.utp.tmspaucars.services.facturas.FacturaService;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FacturaServiceImpl implements FacturaService {

    private final FacturaRepository facturaRepository;
    private final FacturaReporteDao facturaReporteDao;

    @Override
    public List<Factura> listarTodas() {
        return facturaRepository.findAll();
    }

    @Override
    public Factura guardar(Factura factura) {
        return facturaRepository.save(factura);
    }

    @Override
    public List<Map<String, Object>> listarFacturasListaGeneral() {
        return facturaReporteDao.listarFacturasListaGeneral();
    }
}
