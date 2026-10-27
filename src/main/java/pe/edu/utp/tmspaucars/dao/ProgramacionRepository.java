package pe.edu.utp.tmspaucars.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.utp.tmspaucars.models.ProgramacionUnidad;

import java.util.List;

@Repository
public interface ProgramacionRepository extends JpaRepository<ProgramacionUnidad, Integer> {
    List<ProgramacionUnidad> findByTracto(String tracto);
    List<ProgramacionUnidad> findByIdConductor(String idConductor);
    boolean existsByIdConductor(String idConductor);
}