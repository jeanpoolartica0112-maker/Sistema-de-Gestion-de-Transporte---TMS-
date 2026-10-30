package pe.edu.utp.tmspaucars.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.utp.tmspaucars.dao.UsuarioRepository;
import pe.edu.utp.tmspaucars.dtos.LoginRequestDTO;
import pe.edu.utp.tmspaucars.dtos.LoginResponseDTO;
import pe.edu.utp.tmspaucars.models.Usuario;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO dto) {
        Optional<Usuario> encontrado = usuarioRepository.findByUsername(dto.username());

        boolean valido = encontrado.isPresent()
                && Integer.valueOf(1).equals(encontrado.get().getEstado())
                && passwordEncoder.matches(dto.password(), encontrado.get().getPassword());

        if (!valido) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Usuario o contraseña incorrectos");
        }

        Usuario u = encontrado.get();
        return ResponseEntity.ok(new LoginResponseDTO(
                u.getIdUsuario(), u.getUsername(), u.getNombreCompleto(), u.getRol()));
    }
}