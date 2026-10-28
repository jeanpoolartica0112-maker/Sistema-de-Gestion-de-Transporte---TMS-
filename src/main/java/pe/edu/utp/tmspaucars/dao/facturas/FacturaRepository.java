package pe.edu.utp.tmspaucars.dao.facturas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.utp.tmspaucars.models.Factura;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Integer> {
}
