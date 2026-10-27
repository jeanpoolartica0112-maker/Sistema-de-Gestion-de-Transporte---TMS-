package pe.edu.utp.tmspaucars.models;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "guias")
@Getter
@Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Guia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_guia")
    private Integer idGuia;

    @Column(name = "codigo_viaje", nullable = false)
    private Integer codigoViaje;

    @Column(name = "tipo_guia", nullable = false, length = 2) // '09' o '31'
    private String tipoGuia;

    @Column(name = "serie", length = 10)
    private String serie;

    @Column(name = "correlativa", length = 15)
    private String correlativa;

    @Column(name = "ruta_imagen", length = 255)
    private String rutaImagen;

    @Column(name = "recibida", length = 2)
    private String recibida;

    @Column(name = "fecha_recibida")
    private LocalDateTime fechaRecibida;

    @Column(name = "usuario", nullable = false)
    private Integer usuario;
}