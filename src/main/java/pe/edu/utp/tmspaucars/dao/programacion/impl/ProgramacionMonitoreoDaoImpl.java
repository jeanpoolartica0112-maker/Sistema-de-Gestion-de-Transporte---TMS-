package pe.edu.utp.tmspaucars.dao.programacion.impl;

import com.google.common.base.Preconditions;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Repository;
import pe.edu.utp.tmspaucars.dao.programacion.ProgramacionMonitoreoDao;
import pe.edu.utp.tmspaucars.dtos.programacion.FiltroMonitoreoDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class ProgramacionMonitoreoDaoImpl implements ProgramacionMonitoreoDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Map<String, Object> listarMonitoreoPaginados(FiltroMonitoreoDTO filtro) {
        StringBuilder sqlBase = new StringBuilder("""
            FROM programacion_unidades p
            left join guias g on g.codigo_viaje = p.codigo_viaje and g.tipo_guia = '31'
            inner JOIN clientes c ON p.ruc = c.ruc 
            inner JOIN conductores co ON p.id_conductor = co.id_conductor
            left join facturas_programacion fp on fp.codigo_viaje = p.codigo_viaje
            left join facturas ft on ft.id_factura = fp.id_factura
            where 1=1
            """);

        List<Object> parametros = new ArrayList<>();

        if (filtro.estado() != null && !filtro.estado().isBlank()) {
            sqlBase.append(" AND p.estado_programacion LIKE ? ");
            parametros.add("%" + filtro.estado().trim() + "%");
        }

        if (filtro.placa() != null && !filtro.placa().isBlank()) {
            sqlBase.append(" AND p.tracto LIKE ? ");
            parametros.add("%" + filtro.placa().trim().toUpperCase() + "%");
        }

        // --- 2. Consulta para saber el TOTAL de registros (para la paginación) ---
        String sqlCount = "SELECT COUNT(DISTINCT p.codigo_viaje) " + sqlBase.toString();
        Integer totalRegistros = jdbcTemplate.queryForObject(sqlCount, Integer.class, parametros.toArray());

        // --- 3. Consulta para traer los datos de la página actual ---
        String sqlData = """
            SELECT 
                p.codigo_viaje,
                date_format(p.fecha_programacion, '%d-%m-%Y') as fecha_programacion,
                c.razon_social, 
                group_concat(distinct concat(g.serie, '-', correlativa) separator '\n') as guias,
                p.tracto, p.acoplado,
                concat(co.nombres, ' ', co.apellidos) as conductor, 
                p.origen, p.destino,
                p.estado_programacion, p.incidencia,
                g.recibida
            """ + sqlBase.toString() + """
            group by p.codigo_viaje
            order by p.codigo_viaje desc
            LIMIT ? OFFSET ?
            """;

        Preconditions.checkArgument(filtro.pagina() == null || filtro.pagina() >= 0,
                "La página no puede ser negativa.");
        Preconditions.checkArgument(filtro.tamanioPagina() == null || (filtro.tamanioPagina() > 0 && filtro.tamanioPagina() <= 100),
                "El tamaño de página debe estar entre 1 y 100.");

        // 5. Paginación
        int limite = (filtro.tamanioPagina() != null) ? filtro.tamanioPagina() : 100;
        int pagina = (filtro.pagina() != null) ? filtro.pagina() : 0;
        int offset = pagina * limite;

        List<Object> parametrosData = new ArrayList<>(parametros);
        parametrosData.add(limite);
        parametrosData.add(offset);

        List<Map<String, Object>> datosPagina = jdbcTemplate.queryForList(sqlData, parametrosData.toArray());

        // 6. Estructura de respuesta
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("totalRegistros", totalRegistros);
        respuesta.put("paginaActual", pagina);
        respuesta.put("totalPaginas", (int) Math.ceil((double) totalRegistros / limite));
        respuesta.put("datos", datosPagina);

        return respuesta;
    }
}