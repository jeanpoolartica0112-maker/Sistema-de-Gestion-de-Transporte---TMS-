package pe.edu.utp.tmspaucars.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "facturas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_factura")
    private Integer idFactura;

    @Column(name = "fecha_emision")
    private LocalDate fechaEmision;

    @Column(name = "fecha_vcto")
    private LocalDate fechaVcto;

    @Column(name = "numero_factura")
    private String numeroFactura;

    @Column(name = "ruc")
    private String ruc;

    @Column(name = "monto_total")
    private BigDecimal montoTotal;

    @Column(name = "detraccion")
    private String detraccion;

    @Column(name = "id_usuario")
    private Integer idUsuario;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;
}
