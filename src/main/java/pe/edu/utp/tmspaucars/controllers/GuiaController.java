package pe.edu.utp.tmspaucars.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.tmspaucars.dtos.GuiaRequestDTO;
import pe.edu.utp.tmspaucars.models.EstadoProgramacion;
import pe.edu.utp.tmspaucars.services.guia.GuiaService;
import pe.edu.utp.tmspaucars.services.programacion.ProgramacionService;

@RestController
@RequestMapping("/api/guias")
@RequiredArgsConstructor
public class GuiaController {

    private final GuiaService guiaService;
    private final ProgramacionService programacionService;

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody GuiaRequestDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(guiaService.crear(dto));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/viaje/{codigoViaje}")
    public ResponseEntity<?> listarPorViaje(@PathVariable Integer codigoViaje) {
        try {
            return ResponseEntity.ok(guiaService.listarPorViaje(codigoViaje));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<?> cambiarEstado(@PathVariable Integer id, @RequestParam EstadoProgramacion nuevoEstado) {
        try {
            return ResponseEntity.ok(programacionService.cambiarEstado(id, nuevoEstado));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}