package pe.edu.utp.tmspaucars.dao.guias;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.utp.tmspaucars.models.Guia;
import java.util.List;

@Repository
public interface GuiaRepository extends JpaRepository<Guia, Integer> {
    List<Guia> findByCodigoViaje(Integer codigoViaje);
    boolean existsByCodigoViajeAndTipoGuia(Integer codigoViaje, String tipoGuia);
}