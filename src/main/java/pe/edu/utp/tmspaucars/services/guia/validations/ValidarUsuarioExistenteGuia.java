package pe.edu.utp.tmspaucars.services.guia.validations;

import com.google.common.base.Preconditions;
import org.springframework.stereotype.Component;
import pe.edu.utp.tmspaucars.dao.UsuarioRepository;
import pe.edu.utp.tmspaucars.dtos.GuiaRequestDTO;

@Component
public class ValidarUsuarioExistenteGuia implements ValidacionGuia {
    private final UsuarioRepository usuarioRepository;

    public ValidarUsuarioExistenteGuia(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void validar(GuiaRequestDTO dto) {
        Preconditions.checkArgument(usuarioRepository.existsById(dto.usuario()),
                "Error: El usuario con ID %s no existe.", dto.usuario());
    }
}