package pe.edu.utp.tmspaucars.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.tmspaucars.dtos.ProgramacionRequestDTO;
import pe.edu.utp.tmspaucars.dtos.ProgramacionResponseDTO;
import pe.edu.utp.tmspaucars.dtos.programacion.FiltroMonitoreoDTO;
import pe.edu.utp.tmspaucars.dtos.programacion.FiltroViajeDTO;
import pe.edu.utp.tmspaucars.services.programacion.ProgramacionService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/programaciones")
@RequiredArgsConstructor

public class ProgramacionController {

    private final ProgramacionService programacionService;

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody ProgramacionRequestDTO dto) {
        try {
            ProgramacionResponseDTO response = programacionService.crear(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody ProgramacionRequestDTO dto) {
        try {
            ProgramacionResponseDTO response = programacionService.actualizar(id, dto);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/fletes/filtro")
    public ResponseEntity<Map<String, Object>> listarFletesFiltrados(@RequestBody FiltroViajeDTO filtro) {
        return ResponseEntity.ok(programacionService.listarFletesPaginados(filtro));
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(programacionService.obtenerPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/placa/{placa}")
    public ResponseEntity<?> obtenerPorPlaca(@PathVariable String placa) {
        try {
            return ResponseEntity.ok(programacionService.obtenerPorPlaca(placa));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/conductor/{dni}")
    public ResponseEntity<?> obtenerPorConductor(@PathVariable String dni) {
        try {
            return ResponseEntity.ok(programacionService.obtenerPorConductor(dni));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
            programacionService.eliminarViaje(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}