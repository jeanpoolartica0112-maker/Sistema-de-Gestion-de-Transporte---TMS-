package pe.edu.utp.tmspaucars.services.cliente;

import com.google.common.base.Preconditions;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.edu.utp.tmspaucars.dao.ClienteRepository;
import pe.edu.utp.tmspaucars.dao.programacion.ProgramacionRepository;
import pe.edu.utp.tmspaucars.dtos.ClienteRequestDTO;
import pe.edu.utp.tmspaucars.dtos.ClienteResponseDTO;
import pe.edu.utp.tmspaucars.models.Cliente;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final ProgramacionRepository programacionRepository;

    @Override
    public Cliente crearCliente(ClienteRequestDTO dto) {
        Preconditions.checkArgument(dto.ruc() != null && dto.ruc().length() == 11,
                "El RUC debe tener exactamente 11 dígitos");

        Preconditions.checkArgument(dto.diasCredito() != null && dto.diasCredito() >= 0,
                "Los días de crédito no pueden ser negativos");

        Preconditions.checkArgument(!clienteRepository.existsById(dto.ruc()),
                "El cliente con RUC %s ya está registrado", dto.ruc()); // %s reemplaza el valor

        Cliente cliente = Cliente.builder()
                .ruc(dto.ruc())
                .razonSocial(dto.razonSocial())
                .direccion(dto.direccion())
                .diasCredito(dto.diasCredito())
                .build();

        Cliente guardado = clienteRepository.save(cliente);
        log.info("Cliente {} guardado exitosamente", guardado.getRuc());

        return guardado;
    }

    @Override
    public List<ClienteResponseDTO> listarPrimeros25() {
        return clienteRepository.findTop25ByOrderByRazonSocialAsc().stream()
                .map(ClienteResponseDTO::from)
                .collect(Collectors.toList());
    }
    @Override
    public ClienteResponseDTO obtenerPorRuc(String ruc) {
        return clienteRepository.findById(ruc)
                .map(ClienteResponseDTO::from)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con RUC: " + ruc));
    }

    @Override
    @Transactional
    public ClienteResponseDTO actualizar(String ruc, ClienteRequestDTO dto) {
        Cliente cliente = clienteRepository.findById(ruc)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con RUC: " + ruc));

        Preconditions.checkArgument(dto.diasCredito() != null && dto.diasCredito() >= 0, "Los días de crédito no pueden ser negativos");
        Preconditions.checkArgument(dto.razonSocial() != null && !dto.razonSocial().isBlank(), "La razón social es obligatoria");

        cliente.setRazonSocial(dto.razonSocial());
        cliente.setDireccion(dto.direccion());
        cliente.setDiasCredito(dto.diasCredito());

        Cliente actualizado = clienteRepository.save(cliente);
        log.info("Cliente {} actualizado", ruc);
        return ClienteResponseDTO.from(actualizado);
    }

    @Override
    @Transactional
    public void eliminar(String ruc) {
        if (!clienteRepository.existsById(ruc)) {
            throw new RuntimeException("No se puede eliminar.\n* Cliente no encontrado en la base de datos\n* " +
                    "Ya fue borrado por otro usuario");
        }

        Preconditions.checkState(
                !programacionRepository.existsByRuc(ruc),
                "No se puede eliminar el cliente.\nPorque ya esta asignado a una programación."
        );

        clienteRepository.deleteById(ruc);
        log.info("Cliente {} eliminado", ruc);
    }
}