package pe.edu.utp.tmspaucars.services.programacion.validations;

import org.springframework.stereotype.Component;
import pe.edu.utp.tmspaucars.dao.clientes.ClienteRepository;
import pe.edu.utp.tmspaucars.dtos.ProgramacionRequestDTO;

@Component
public class ValidarClienteExistente implements ValidacionProgramacion {
    private final ClienteRepository clienteRepository;

    public ValidarClienteExistente(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public void validar(ProgramacionRequestDTO dto) {
        if (!clienteRepository.existsById(dto.ruc())) {
            throw new RuntimeException("Error: El RUC " + dto.ruc() + " no existe.");
        }
    }
}