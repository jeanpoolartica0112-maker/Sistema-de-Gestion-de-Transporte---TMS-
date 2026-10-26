package pe.edu.utp.tmspaucars.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.utp.tmspaucars.models.Unidad;

@Repository
public interface UnidadRepository extends JpaRepository<Unidad, String> {}