package pe.edu.utp.tmspaucars.dao.finanzas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.utp.tmspaucars.models.Finanzas;

@Repository
public interface FinanzasRepository extends JpaRepository<Finanzas, Integer> {
}