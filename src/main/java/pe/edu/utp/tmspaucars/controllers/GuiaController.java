package pe.edu.utp.tmspaucars.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.tmspaucars.dtos.GuiaRequestDTO;
import pe.edu.utp.tmspaucars.dtos.GuiaResponseDTO;
import pe.edu.utp.tmspaucars.services.guia.GuiaService;

import java.util.List;

@RestController
@RequestMapping("/api/guias")
@RequiredArgsConstructor
public class GuiaController {

    private final GuiaService guiaService;

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody GuiaRequestDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(guiaService.crearGuia(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/viaje/{codigoViaje}")
    public ResponseEntity<List<GuiaResponseDTO>> listarPorViaje(@PathVariable Integer codigoViaje) {
        return ResponseEntity.ok(guiaService.obtenerPorViaje(codigoViaje));
    }
}