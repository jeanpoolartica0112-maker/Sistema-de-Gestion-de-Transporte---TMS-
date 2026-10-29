package pe.edu.utp.tmspaucars.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;


@Entity
@Table(name = "facturas_programacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FacturasProgramacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_factura")
    private Integer idFactura;

    @Column(name = "codigo_viaje")
    private Integer codigoViaje;

    @Column(name = "importe")
    private BigDecimal importe;

}
