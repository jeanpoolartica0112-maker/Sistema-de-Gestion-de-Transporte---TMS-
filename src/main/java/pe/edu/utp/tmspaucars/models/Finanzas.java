package pe.edu.utp.tmspaucars.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "finanzas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Finanzas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_finanzas")
    private Integer idFinanzas;

    @Column(name = "id_factura")
    private Integer idFactura;

    @Column(name = "monto_cobrado")
    private BigDecimal montoCobrado;

    @Column(name = "estado_cobro")
    private String estadoCobro;

    @Column(name = "id_usuario")
    private Integer idUsuario;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;
}
