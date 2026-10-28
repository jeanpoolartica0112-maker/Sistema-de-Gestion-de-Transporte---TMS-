package pe.edu.utp.tmspaucars.dao.facturas.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pe.edu.utp.tmspaucars.dao.facturas.FacturaReporteDao;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class FacturaReporteDaoImpl implements FacturaReporteDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Map<String, Object>> listarFacturasListaGeneral() {
        String sql = "SELECT " +
                "f.id_factura AS idFactura, " +
                "f.fecha_emision AS fechaEmision, " +
                "f.fecha_vcto AS fechaVcto, " +
                "f.numero_factura AS numeroFactura, " +
                "f.ruc AS ruc, " +
                "c.razon_social AS razonSocial, " +
                "f.monto_total AS montoTotal, " +
                "f.detraccion AS detraccion, " +
                "f.fecha_registro AS fechaRegistro " +
                "FROM facturas f " +
                "LEFT JOIN clientes c ON f.ruc = c.ruc";

        return jdbcTemplate.queryForList(sql);
    }
}
