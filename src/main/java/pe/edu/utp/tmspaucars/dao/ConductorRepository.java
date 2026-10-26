package pe.edu.utp.tmspaucars.dao;

import pe.edu.utp.tmspaucars.models.Conductor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConductorRepository extends JpaRepository<Conductor, String> {

}