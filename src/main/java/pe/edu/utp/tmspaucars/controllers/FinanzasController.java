package pe.edu.utp.tmspaucars.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.utp.tmspaucars.services.finanzas.FinanzasService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/finanzas")
@RequiredArgsConstructor
public class FinanzasController {

    private final FinanzasService finanzasService;

    @GetMapping("/lista-general")
    public ResponseEntity<List<Map<String, Object>>> listarFinanzasListaGeneral() {
        List<Map<String, Object>> resultado = finanzasService.listarFinanzasListaGeneral();
        return ResponseEntity.ok(resultado);
    }
}
