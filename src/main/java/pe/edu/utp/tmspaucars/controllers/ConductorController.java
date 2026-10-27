package pe.edu.utp.tmspaucars.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.tmspaucars.models.Conductor;
import pe.edu.utp.tmspaucars.services.conductor.ConductorService;

import java.util.List;

@RestController
@RequestMapping("/api/conductores")
public class ConductorController {

    private final ConductorService conductorService;

    public ConductorController(ConductorService conductorService) {
        this.conductorService = conductorService;
    }

    @GetMapping
    public List<Conductor> listar() {
        return conductorService.listarConductores();
    }

    @GetMapping("/{dni}")
    public ResponseEntity<Conductor> obtenerPorId(@PathVariable String dni) {
        return conductorService.obtenerConductorPorId(dni)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Conductor conductor) { // <-- Agregamos @Valid
        if (conductorService.obtenerConductorPorId(conductor.getIdConductor()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("El conductor con DNI " + conductor.getIdConductor() + " ya está registrado.");
        }

        Conductor nuevoConductor = conductorService.registrarConductor(conductor);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoConductor);
    }

    @PutMapping("/{dni}") // <-- CAMBIADO a String dni
    public ResponseEntity<Conductor> actualizar(@PathVariable String dni, @RequestBody Conductor conductor) {
        return conductorService.actualizarConductor(dni, conductor)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{dni}")
    public ResponseEntity<Void> eliminar(@PathVariable String dni) {
        if (conductorService.eliminarConductor(dni)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}