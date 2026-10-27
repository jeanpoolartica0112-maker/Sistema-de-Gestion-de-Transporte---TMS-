package pe.edu.utp.tmspaucars.services.programacion.validations;

import com.google.common.base.Preconditions;
import org.springframework.stereotype.Component;
import pe.edu.utp.tmspaucars.dao.UsuarioRepository;
import pe.edu.utp.tmspaucars.dtos.ProgramacionRequestDTO;

@Component
public class ValidarUsuarioExistente implements ValidacionProgramacion {

    private final UsuarioRepository usuarioRepository;

    public ValidarUsuarioExistente(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void validar(ProgramacionRequestDTO dto) {
        Preconditions.checkArgument(usuarioRepository.existsById(dto.idUsuario()),
                "Error: El usuario con ID %s no existe en el sistema", dto.idUsuario());
    }
}