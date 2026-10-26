package pe.edu.utp.tmspaucars.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;
@Entity
@Table(name = "conductores")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Conductor {

    @Id
    @Column(name = "id_conductor", length = 10)
    @NotBlank(message = "El DNI (idConductor) es obligatorio")
    private String idConductor;

    @Column(name = "tipo_documento", nullable = false, length = 2)
    private String tipoDocumento;

    @Column(name = "nombres", nullable = false, length = 100)
    private String nombres;

    @Column(name = "apellidos", nullable = false, length = 100)
    private String apellidos;

    @Column(name = "licencia", nullable = false, length = 15)
    private String licencia;

    @Column(name = "categoria_licencia", nullable = false, length = 15)
    private String categoriaLicencia;

    @Column(name = "vcto_licencia", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd") // <-- AGREGA ESTO
    private LocalDate vctoLicencia;

    @Column(name = "estado_disponibilidad", nullable = false, length = 45)
    private String estadoDisponibilidad;

}