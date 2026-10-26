package pe.edu.utp.tmspaucars.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "unidades")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Unidad {

    @Id
    @Column(name = "placa", length = 15)
    private String placa;

    @Column(name = "tipo", nullable = false, length = 25)
    private String tipo;

    @Column(name = "capacidad_carga", nullable = false)
    private Integer capacidadCarga;

    @Column(name = "configuracion_vehicular", nullable = false, length = 15)
    private String configuracionVehicular;

    @Column(name = "certificado_vehicular", nullable = false, length = 25)
    private String certificadoVehicular;

    @Column(name = "fecha_soat", nullable = false)
    private LocalDate fechaSoat;

    @Column(name = "fecha_revision_tecnica", nullable = false)
    private LocalDate fechaRevisionTecnica;

    @Column(name = "fecha_permiso_mtc", nullable = false)
    private LocalDate fechaPermisoMtc;
}