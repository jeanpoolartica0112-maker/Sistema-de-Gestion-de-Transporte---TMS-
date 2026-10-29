package pe.edu.utp.tmspaucars.dao.clientes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.utp.tmspaucars.models.Cliente;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, String> {
    List<Cliente> findTop25ByOrderByRazonSocialAsc();
}
