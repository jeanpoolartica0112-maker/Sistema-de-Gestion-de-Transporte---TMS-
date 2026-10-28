package pe.edu.utp.tmspaucars.dao.programacion.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;

import pe.edu.utp.tmspaucars.dao.programacion.ProgramacionReporteDao;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class ProgramacionReporteDaoImpl implements ProgramacionReporteDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Map<String, Object>> listarFletesCrudos() {
        String sql = """
            SELECT 
                p.codigo_viaje AS codigoViaje, 
                p.fecha_programacion AS fechaProgramacion, 
                p.ruc AS ruc, 
                c.razon_social AS razonSocial,  
                p.tracto AS tracto, 
                p.acoplado AS acoplado, 
                p.id_conductor AS idConductor, 
                concat(co.nombres , ' ' , co.apellidos) AS nombreConductor,  
                p.origen AS origen, 
                p.destino AS destino, 
                p.monto_flete AS montoFlete, 
                p.estado_programacion AS estadoProgramacion, 
                p.incidencia AS incidencia,
                fp.id_factura as facturado
            FROM programacion_unidades p 
            JOIN clientes c ON p.ruc = c.ruc  
            JOIN conductores co ON p.id_conductor = co.id_conductor
            left join facturas_programacion fp on fp.codigo_viaje = p.codigo_viaje
            """;
        return jdbcTemplate.queryForList(sql);
    }
}