package pe.edu.utp.tmspaucars.services.guia;

import com.google.common.base.Preconditions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.tmspaucars.dao.GuiaRepository;
import pe.edu.utp.tmspaucars.dao.programacion.ProgramacionRepository;
import pe.edu.utp.tmspaucars.dtos.GuiaRequestDTO;
import pe.edu.utp.tmspaucars.dtos.GuiaResponseDTO;
import pe.edu.utp.tmspaucars.models.Guia;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class GuiaServiceImpl implements GuiaService {

    private final GuiaRepository guiaRepository;
    private final ProgramacionRepository programacionRepository;

    @Override
    @Transactional
    public GuiaResponseDTO crearGuia(GuiaRequestDTO dto) {
        Preconditions.checkArgument(programacionRepository.existsById(dto.codigoViaje()),
                "El viaje con código %s no existe.", dto.codigoViaje());

        Preconditions.checkArgument(dto.tipoGuia().equals("09") || dto.tipoGuia().equals("31"),
                "El tipo de guía debe ser 09 o 31.");

        Guia guia = Guia.builder()
                .codigoViaje(dto.codigoViaje())
                .tipoGuia(dto.tipoGuia())
                .serie(dto.serie())
                .correlativa(dto.correlativa())
                .rutaImagen(dto.rutaImagen())
                .recibida(dto.recibida() != null ? dto.recibida() : "NO")
                .fechaRecibida(LocalDateTime.now()) // Se registra la hora actual
                .usuario(dto.usuario())
                .build();

        Guia guardada = guiaRepository.save(guia);
        log.info("Guía {} asignada al viaje {}", guardada.getIdGuia(), guardada.getCodigoViaje());
        return GuiaResponseDTO.from(guardada);
    }

    @Override
    public List<GuiaResponseDTO> obtenerPorViaje(Integer codigoViaje) {
        return guiaRepository.findByCodigoViaje(codigoViaje).stream()
                .map(GuiaResponseDTO::from)
                .collect(Collectors.toList());
    }
}