package pe.edu.utp.tmspaucars.dtos;

import jakarta.validation.constraints.*;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ProgramacionRequestDTO(
        @NotBlank @Pattern(regexp = "\\d{11}", message = "El RUC debe tener 11 dígitos") String ruc,
        @NotNull @FutureOrPresent LocalDate fechaProgramacion,
        @NotBlank @Size(max = 15) String tracto,
        @NotBlank @Size(max = 15) String acoplado,
        @NotBlank @Size(max = 10) String idConductor,
        @NotBlank @Size(max = 100) String origen,
        @NotBlank @Size(max = 100) String destino,
        @NotNull @Positive @Digits(integer = 8, fraction = 2) BigDecimal montoFlete,
        @NotNull Integer idUsuario) {

    public ProgramacionRequestDTO {
        ruc = StringUtils.trim(ruc);
        tracto = StringUtils.upperCase(StringUtils.trim(tracto));
        acoplado = StringUtils.upperCase(StringUtils.trim(acoplado));
        idConductor = StringUtils.trim(idConductor);
    }
}