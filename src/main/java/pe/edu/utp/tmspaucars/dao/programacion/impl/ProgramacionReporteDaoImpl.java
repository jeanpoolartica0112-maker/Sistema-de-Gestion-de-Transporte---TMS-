package pe.edu.utp.tmspaucars.dao.programacion.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Repository;
import pe.edu.utp.tmspaucars.dao.programacion.ProgramacionReporteDao;
import pe.edu.utp.tmspaucars.dtos.programacion.FiltroViajeDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class ProgramacionReporteDaoImpl implements ProgramacionReporteDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Map<String, Object> listarFletesPaginados(FiltroViajeDTO filtro) {

        // --- 1. Armamos la base de la consulta y los filtros ---
        StringBuilder sqlBase = new StringBuilder("""
            FROM programacion_unidades p 
            JOIN clientes c ON p.ruc = c.ruc  
            JOIN conductores co ON p.id_conductor = co.id_conductor
            LEFT JOIN facturas_programacion fp on fp.codigo_viaje = p.codigo_viaje
            WHERE 1=1
            """);

        List<Object> parametros = new ArrayList<>();

        if (filtro.estado() != null && !filtro.estado().isBlank()) {
            sqlBase.append(" AND p.estado_programacion LIKE ? ");
            parametros.add("%" + filtro.estado().trim() + "%");
        }
        if (filtro.destino() != null && !filtro.destino().isBlank()) {
            sqlBase.append(" AND p.destino LIKE ? ");
            parametros.add("%" + filtro.destino().trim() + "%");
        }
        if (filtro.placa() != null && !filtro.placa().isBlank()) {
            sqlBase.append(" AND p.tracto LIKE ? ");
            parametros.add("%" + filtro.placa().trim().toUpperCase() + "%");
        }

        // --- 2. Consulta para saber el TOTAL de registros (para la paginación) ---
        String sqlCount = "SELECT COUNT(*) " + sqlBase.toString();
        Integer totalRegistros = jdbcTemplate.queryForObject(sqlCount, Integer.class, parametros.toArray());

        // --- 3. Consulta para traer los datos de la página actual ---
        String sqlData = """
            SELECT 
                p.codigo_viaje, 
                DATE_FORMAT(p.fecha_programacion, '%d-%m-%Y') AS fecha_programacion, 
                c.razon_social, p.tracto, p.acoplado, p.ruc,
                concat(co.nombres, ' ', co.apellidos) AS nombre_conductor,
                co.id_conductor, 
                p.origen, p.destino, p.estado_programacion,
                p.monto_flete, p.incidencia, fp.id_factura as facturado
            """ + sqlBase.toString();

        // Calculamos el límite y el offset
        int limite = (filtro.tamanioPagina() != null) ? filtro.tamanioPagina() : 50;
        int pagina = (filtro.pagina() != null) ? filtro.pagina() : 0;
        int offset = pagina * limite;

        sqlData += " LIMIT ? OFFSET ? ";

        // Copiamos los parámetros de los filtros y agregamos los de paginación
        List<Object> parametrosData = new ArrayList<>(parametros);
        parametrosData.add(limite);
        parametrosData.add(offset);

        List<Map<String, Object>> datosPagina = jdbcTemplate.queryForList(sqlData, parametrosData.toArray());

        // --- 4. Devolvemos todo en un solo JSON estructurado ---
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("totalRegistros", totalRegistros);
        respuesta.put("paginaActual", pagina);
        respuesta.put("totalPaginas", (int) Math.ceil((double) totalRegistros / limite));
        respuesta.put("datos", datosPagina);

        return respuesta;
    }
}