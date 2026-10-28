package pe.edu.utp.tmspaucars.services.finanzas;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.utp.tmspaucars.dao.finanzas.FinanzasReporteDao;
import pe.edu.utp.tmspaucars.services.finanzas.FinanzasService;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FinanzasServiceImpl implements FinanzasService {

    private final FinanzasReporteDao finanzasReporteDao;

    @Override
    public List<Map<String, Object>> listarFinanzasListaGeneral() {
        return finanzasReporteDao.listarFinanzasListaGeneral();
    }
}
