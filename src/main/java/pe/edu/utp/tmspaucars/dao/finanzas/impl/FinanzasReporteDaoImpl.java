package pe.edu.utp.tmspaucars.dao.finanzas.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pe.edu.utp.tmspaucars.dao.finanzas.FinanzasReporteDao;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class FinanzasReporteDaoImpl implements FinanzasReporteDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Map<String, Object>> listarFinanzasListaGeneral() {
        String sql = """
            SELECT 
                f.id_finanzas AS idFinanzas,
                fa.numero_factura AS numeroFactura,
                c.ruc AS ruc,
                c.razon_social AS razonSocial,
                pu.origen AS origen,
                pu.destino AS destino,
                f.monto_cobrado AS montoCobrado,
                f.estado_cobro AS estadoCobro,
                f.fecha_registro AS fechaRegistro
            FROM finanzas f
            LEFT JOIN facturas fa ON f.id_factura = fa.id_factura
            LEFT JOIN clientes c ON fa.ruc = c.ruc
            LEFT JOIN facturas_programacion fp ON fa.id_factura = fp.id_factura
            LEFT JOIN programacion_unidades pu ON fp.id_programacion = pu.id_programacion
            """;
        return jdbcTemplate.queryForList(sql);
    }
}
