package pe.edu.utp.tmspaucars.services.programacion;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.edu.utp.tmspaucars.dao.facturasprogramacion.FacturaProgramacionRepository;
import pe.edu.utp.tmspaucars.dao.programacion.ProgramacionMonitoreoDao;
import pe.edu.utp.tmspaucars.dao.programacion.ProgramacionReporteDao;
import pe.edu.utp.tmspaucars.dao.programacion.ProgramacionRepository;
import pe.edu.utp.tmspaucars.dtos.ProgramacionRequestDTO;
import pe.edu.utp.tmspaucars.dtos.ProgramacionResponseDTO;
import pe.edu.utp.tmspaucars.models.EstadoProgramacion;
import pe.edu.utp.tmspaucars.models.ProgramacionUnidad;
import pe.edu.utp.tmspaucars.services.programacion.validations.ValidacionProgramacion;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProgramacionServiceImplTest {

    @Mock private ProgramacionRepository programacionRepository;
    @Mock private ProgramacionReporteDao programacionReporteDao;
    @Mock private ProgramacionMonitoreoDao programacionMonitoreoDao;
    @Mock private FacturaProgramacionRepository facturasProgramacionRepository;
    @Mock private ValidacionProgramacion validacion;

    private ProgramacionServiceImpl servicio() {
        return new ProgramacionServiceImpl(programacionRepository, programacionReporteDao,
                programacionMonitoreoDao, facturasProgramacionRepository, List.of(validacion));
    }

    private ProgramacionRequestDTO dto() {
        return new ProgramacionRequestDTO("20123456789", LocalDate.now().plusDays(1),
                "ABC123", "XYZ789", "C001", "Lima", "Arequipa", new BigDecimal("3500.00"), 1);
    }

    @Test
    void crear_siUnaReglaFalla_noSeGuardaElViaje() {
        doThrow(new RuntimeException("licencia vencida")).when(validacion).validar(any());

        assertThrows(RuntimeException.class, () -> servicio().crear(dto()));
        verify(programacionRepository, never()).save(any());
    }

    @Test
    void crear_siTodoEsValido_guardaConEstadoProgramado() {
        when(programacionRepository.save(any(ProgramacionUnidad.class))).thenAnswer(inv -> {
            ProgramacionUnidad p = inv.getArgument(0);
            p.setCodigoViaje(10);
            return p;
        });

        ProgramacionResponseDTO respuesta = servicio().crear(dto());

        assertEquals(EstadoProgramacion.PROGRAMADO, respuesta.estadoProgramacion());
        assertEquals(10, respuesta.codigoViaje());
    }

    @Test
    void actualizar_siElViajeNoEstaProgramado_lanzaExcepcion() {
        ProgramacionUnidad enRuta = ProgramacionUnidad.builder()
                .codigoViaje(1).estadoProgramacion(EstadoProgramacion.EN_RUTA).build();
        when(programacionRepository.findById(1)).thenReturn(Optional.of(enRuta));
        when(facturasProgramacionRepository.existsByCodigoViaje(1)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> servicio().actualizar(1, dto()));
        verify(programacionRepository, never()).save(any());
    }

    @Test
    void eliminar_siEstaFacturado_lanzaExcepcionYNoBorra() {
        when(programacionRepository.existsById(1)).thenReturn(true);
        when(facturasProgramacionRepository.existsByCodigoViaje(1)).thenReturn(true);

        assertThrows(IllegalStateException.class, () -> servicio().eliminarViaje(1));
        verify(programacionRepository, never()).deleteById(any());
    }
}