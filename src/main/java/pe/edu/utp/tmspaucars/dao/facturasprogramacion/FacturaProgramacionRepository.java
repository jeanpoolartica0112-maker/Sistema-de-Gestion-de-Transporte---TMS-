package pe.edu.utp.tmspaucars.dao.facturasprogramacion;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.utp.tmspaucars.models.FacturasProgramacion;

public interface FacturaProgramacionRepository extends JpaRepository<FacturasProgramacion, Integer> {
    boolean existsByCodigoViaje(Integer codigoViaje);
}
