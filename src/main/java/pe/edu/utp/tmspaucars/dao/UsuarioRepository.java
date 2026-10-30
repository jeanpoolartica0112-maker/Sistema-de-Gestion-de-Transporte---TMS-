package pe.edu.utp.tmspaucars.dao;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.utp.tmspaucars.models.Usuario;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{

    Optional<Usuario> findByUsername(@NotBlank String username);
}
