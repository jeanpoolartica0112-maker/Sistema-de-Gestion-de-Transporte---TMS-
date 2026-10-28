package pe.edu.utp.tmspaucars.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.tmspaucars.dtos.ClienteRequestDTO;
import pe.edu.utp.tmspaucars.dtos.ClienteResponseDTO;
import pe.edu.utp.tmspaucars.services.cliente.ClienteService;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody ClienteRequestDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.crearCliente(dto));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> listar() {
        return ResponseEntity.ok(clienteService.listarPrimeros25());
    }

    @GetMapping("/{ruc}")
    public ResponseEntity<?> obtener(@PathVariable String ruc) {
        try {
            return ResponseEntity.ok(clienteService.obtenerPorRuc(ruc));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); // Error 404
        }
    }

    @PutMapping("/{ruc}")
    public ResponseEntity<?> actualizar(@PathVariable String ruc, @RequestBody ClienteRequestDTO dto) {
        try {
            return ResponseEntity.ok(clienteService.actualizar(ruc, dto));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{ruc}")
    public ResponseEntity<?> eliminar(@PathVariable String ruc) {
        try {
            clienteService.eliminar(ruc);
            return ResponseEntity.noContent().build(); // 204
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}