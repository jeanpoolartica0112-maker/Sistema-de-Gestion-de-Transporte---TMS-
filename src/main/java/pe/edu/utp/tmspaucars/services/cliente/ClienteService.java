package pe.edu.utp.tmspaucars.services.cliente;


import pe.edu.utp.tmspaucars.dtos.ClienteRequestDTO;
import pe.edu.utp.tmspaucars.dtos.ClienteResponseDTO;
import pe.edu.utp.tmspaucars.models.Cliente;

import java.util.List;

public interface ClienteService {
    Cliente crearCliente(ClienteRequestDTO dto);
    List<ClienteResponseDTO> listarTodos();
    ClienteResponseDTO obtenerPorRuc(String ruc);
    ClienteResponseDTO actualizar(String ruc, ClienteRequestDTO dto);
    void eliminar(String ruc);
}

