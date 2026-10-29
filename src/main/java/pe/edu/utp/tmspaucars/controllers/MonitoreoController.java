package pe.edu.utp.tmspaucars.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.tmspaucars.dtos.ProgramacionResponseDTO;
import pe.edu.utp.tmspaucars.dtos.programacion.FiltroMonitoreoDTO;
import pe.edu.utp.tmspaucars.models.EstadoProgramacion;
import pe.edu.utp.tmspaucars.services.monitoreo.MonitoreoService;

import java.util.Map;

@RestController
@RequestMapping("/api/monitoreo")
@RequiredArgsConstructor
public class MonitoreoController {

    private final MonitoreoService monitoreoService;

    @PostMapping("/listar")
    public ResponseEntity<Map<String, Object>> listar(@RequestBody FiltroMonitoreoDTO filtro) {
        return ResponseEntity.ok(monitoreoService.listarMonitoreoPaginados(filtro));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<?> cambiarEstado(@PathVariable Integer id, @RequestParam EstadoProgramacion nuevoEstado) {
        try {
            ProgramacionResponseDTO response = monitoreoService.cambiarEstado(id, nuevoEstado);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}