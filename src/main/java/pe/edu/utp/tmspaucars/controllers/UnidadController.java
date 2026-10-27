package pe.edu.utp.tmspaucars.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.tmspaucars.dtos.UnidadRequestDTO;
import pe.edu.utp.tmspaucars.dtos.UnidadResponseDTO;
import pe.edu.utp.tmspaucars.services.unidad.UnidadService;

import java.util.List;

@RestController
@RequestMapping("/api/unidades")
@RequiredArgsConstructor
public class UnidadController {

    private final UnidadService unidadService;

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody UnidadRequestDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(unidadService.crearUnidad(dto));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<UnidadResponseDTO>> listar() {
        return ResponseEntity.ok(unidadService.listarTodas());
    }

    @GetMapping("/{placa}")
    public ResponseEntity<?> obtener(@PathVariable String placa) {
        try {
            return ResponseEntity.ok(unidadService.obtenerPorPlaca(placa));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{placa}")
    public ResponseEntity<?> actualizar(@PathVariable String placa, @RequestBody UnidadRequestDTO dto) {
        try {
            return ResponseEntity.ok(unidadService.actualizar(placa, dto));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{placa}")
    public ResponseEntity<?> eliminar(@PathVariable String placa) {
        try {
            unidadService.eliminar(placa);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}