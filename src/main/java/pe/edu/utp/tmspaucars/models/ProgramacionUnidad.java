package pe.edu.utp.tmspaucars.models;
import pe.edu.utp.tmspaucars.services.EstadoProgramacion;

import jakarta.persistence.*;
import lombok.*;
import pe.edu.utp.tmspaucars.services.EstadoProgramacion;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "programacion_unidades")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProgramacionUnidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_viaje")
    private Integer codigoViaje;

    @Column(name = "ruc", nullable = false)
    private String ruc;

    @Column(name = "fecha_programacion", nullable = false)
    private LocalDate fechaProgramacion;

    @Column(name = "tracto", nullable = false)
    private String tracto;

    @Column(name = "acoplado")
    private String acoplado;

    @Column(name = "id_conductor", nullable = false)
    private String idConductor;

    @Column(name = "origen", nullable = false)
    private String origen;

    @Column(name = "destino", nullable = false)
    private String destino;

    @Column(name = "monto_flete", nullable = false)
    private BigDecimal montoFlete;

    @Enumerated(EnumType.STRING) // Guarda el texto (PROGRAMADO) y no un número
    @Column(name = "estado_programacion", nullable = false)
    private EstadoProgramacion estadoProgramacion;

    @Column(name = "incidencia", nullable = false)
    private String incidencia = ""; // Valor por defecto

    @Column(name = "fecha_registro", updatable = false)
    private LocalDateTime fechaRegistro;

    @Column(name = "id_usuario", nullable = false)
    private int idUsuario;

    // Método para asignar valores por defecto antes de guardar
    @PrePersist
    public void prePersist() {
        if (this.incidencia == null) this.incidencia = "";
        if (this.fechaRegistro == null) this.fechaRegistro = LocalDateTime.now();
        if (this.estadoProgramacion == null) this.estadoProgramacion = EstadoProgramacion.PROGRAMADO;
    }
}