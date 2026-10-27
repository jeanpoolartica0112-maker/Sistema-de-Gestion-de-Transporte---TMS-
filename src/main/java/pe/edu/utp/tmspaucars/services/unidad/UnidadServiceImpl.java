package pe.edu.utp.tmspaucars.services.unidad;

import com.google.common.base.Preconditions;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.edu.utp.tmspaucars.dao.UnidadRepository;
import pe.edu.utp.tmspaucars.dtos.UnidadRequestDTO;
import pe.edu.utp.tmspaucars.dtos.UnidadResponseDTO;
import pe.edu.utp.tmspaucars.models.Unidad;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UnidadServiceImpl implements UnidadService {

    private final UnidadRepository unidadRepository;

    @Override
    public Unidad crearUnidad(UnidadRequestDTO dto) {
        Preconditions.checkArgument(dto.placa() != null && !dto.placa().isBlank(),
                "La placa es obligatoria");

        Preconditions.checkArgument(dto.capacidadCarga() != null && dto.capacidadCarga() > 0,
                "La capacidad de carga debe ser mayor a 0");

        Preconditions.checkArgument(dto.fechaSoat() != null && dto.fechaSoat().isAfter(LocalDate.now().minusDays(1)),
                "La fecha del SOAT no puede ser anterior a hoy");

        Preconditions.checkArgument(!unidadRepository.existsById(dto.placa()),
                "La unidad con placa %s ya está registrada", dto.placa());

        Unidad unidad = Unidad.builder()
                .placa(dto.placa())
                .tipo(dto.tipo())
                .capacidadCarga(dto.capacidadCarga())
                .configuracionVehicular(dto.configuracionVehicular())
                .certificadoVehicular(dto.certificadoVehicular())
                .fechaSoat(dto.fechaSoat())
                .fechaRevisionTecnica(dto.fechaRevisionTecnica())
                .fechaPermisoMtc(dto.fechaPermisoMtc())
                .build();

        return unidadRepository.save(unidad);
    }

    @Override
    public List<UnidadResponseDTO> listarTodas() {
        return unidadRepository.findAll().stream()
            .map(UnidadResponseDTO::from)
            .collect(Collectors.toList());
    }

    @Override
    public UnidadResponseDTO obtenerPorPlaca(String placa) {
        return unidadRepository.findById(placa)
                .map(UnidadResponseDTO::from)
                .orElseThrow(() -> new RuntimeException("Unidad no encontrada con placa: " + placa));
    }

    @Override
    @Transactional
    public UnidadResponseDTO actualizar(String placa, UnidadRequestDTO dto) {
        Unidad unidad = unidadRepository.findById(placa)
                .orElseThrow(() -> new RuntimeException("Unidad no encontrada con placa: " + placa));

        Preconditions.checkArgument(dto.capacidadCarga() != null && dto.capacidadCarga() > 0, "La capacidad de carga debe ser mayor a 0");

        unidad.setTipo(dto.tipo());
        unidad.setCapacidadCarga(dto.capacidadCarga());
        unidad.setConfiguracionVehicular(dto.configuracionVehicular());
        unidad.setCertificadoVehicular(dto.certificadoVehicular());
        unidad.setFechaSoat(dto.fechaSoat());
        unidad.setFechaRevisionTecnica(dto.fechaRevisionTecnica());
        unidad.setFechaPermisoMtc(dto.fechaPermisoMtc());


        Unidad actualizada = unidadRepository.save(unidad);
        log.info("Unidad {} actualizada", placa);
        return UnidadResponseDTO.from(actualizada);
    }

    @Override
    @Transactional
    public void eliminar(String placa) {
        if (!unidadRepository.existsById(placa)) {
            throw new RuntimeException("No se puede eliminar. Unidad no encontrada con placa: " + placa);
        }
        unidadRepository.deleteById(placa);
        log.info("Unidad {} eliminada", placa);
    }
}