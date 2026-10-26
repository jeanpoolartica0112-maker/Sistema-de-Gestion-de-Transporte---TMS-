package pe.edu.utp.tmspaucars.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "clientes")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Cliente {

    @Id
    @Column(name = "ruc", length = 11)
    private String ruc;

    @Column(name = "razon_social", nullable = false)
    private String razonSocial;

    @Column(name = "direccion", nullable = false)
    private String direccion;

    @Column(name = "dias_credito", nullable = false)
    private Integer diasCredito;
}