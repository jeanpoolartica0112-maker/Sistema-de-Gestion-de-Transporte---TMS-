package pe.edu.utp.tmspaucars.dao.clientes.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pe.edu.utp.tmspaucars.dao.clientes.ClienteListaDao;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class ClienteListaDaoImpl implements ClienteListaDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Map<String, Object>> listarClientesFiltro(String filtro) {
        String sql = "SELECT " +
                "ruc AS ruc, " +
                "razon_social AS razonSocial, " +
                "direccion AS direccion, " +
                "dias_credito AS diasCredito " +
                "FROM clientes " +
                "WHERE ruc LIKE ? OR razon_social LIKE ?";

        String paramLike = "%" + (filtro != null ? filtro : " ") + "%";

        return jdbcTemplate.queryForList(sql, paramLike, paramLike);
    }

    @Override
    public Object existsById(String number) {
        return null;
    }
}
