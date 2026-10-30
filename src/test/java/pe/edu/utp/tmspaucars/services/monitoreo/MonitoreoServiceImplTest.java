package pe.edu.utp.tmspaucars.services.monitoreo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import pe.edu.utp.tmspaucars.dao.GuiaRepository;
import pe.edu.utp.tmspaucars.dao.programacion.ProgramacionMonitoreoDao;
import pe.edu.utp.tmspaucars.dao.programacion.ProgramacionRepository;
import pe.edu.utp.tmspaucars.dtos.ProgramacionResponseDTO;
import pe.edu.utp.tmspaucars.models.EstadoProgramacion;
import pe.edu.utp.tmspaucars.models.ProgramacionUnidad;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class MonitoreoServiceImplTest {

    @Mock private ProgramacionMonitoreoDao monitoreoDao;
    @Mock private ProgramacionRepository programacionRepository;
    @Mock private GuiaRepository guiaRepository;

    private MonitoreoServiceImpl servicio() {
        return new MonitoreoServiceImpl(monitoreoDao, programacionRepository, guiaRepository);
    }

    private void viajeEnEstado(EstadoProgramacion estado) {
        ProgramacionUnidad viaje = ProgramacionUnidad.builder()
                .codigoViaje(1).estadoProgramacion(estado).build();
        when(programacionRepository.findById(1)).thenReturn(Optional.of(viaje));
        when(programacionRepository.save(any(ProgramacionUnidad.class)))
                .thenAnswer(inv -> inv.getArgument(0));
    }

    @Test
    void viajeInexistente_lanzaExcepcion() {
        when(programacionRepository.findById(1)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> servicio().cambiarEstado(1, EstadoProgramacion.EN_RUTA));
        assertTrue(ex.getMessage().contains("No se encontró"));
    }

    @Test
    void programado_aEnRuta_conGuiaTipo31_cambiaEstado() {
        viajeEnEstado(EstadoProgramacion.PROGRAMADO);
        when(guiaRepository.existsByCodigoViajeAndTipoGuia(1, "31")).thenReturn(true);

        ProgramacionResponseDTO r = servicio().cambiarEstado(1, EstadoProgramacion.EN_RUTA);

        assertEquals(EstadoProgramacion.EN_RUTA, r.estadoProgramacion());
    }

    @Test
    void programado_aEnRuta_sinGuia_noCambiaNiGuarda() {
        viajeEnEstado(EstadoProgramacion.PROGRAMADO);
        when(guiaRepository.existsByCodigoViajeAndTipoGuia(1, "31")).thenReturn(false);

        assertThrows(IllegalArgumentException.class,
                () -> servicio().cambiarEstado(1, EstadoProgramacion.EN_RUTA));
        verify(programacionRepository, never()).save(any());
    }

    @Test
    void programado_aCancelado_cambiaEstado() {
        viajeEnEstado(EstadoProgramacion.PROGRAMADO);

        ProgramacionResponseDTO r = servicio().cambiarEstado(1, EstadoProgramacion.CANCELADO);

        assertEquals(EstadoProgramacion.CANCELADO, r.estadoProgramacion());
    }

    @Test
    void programado_aEntregado_noPermitido() {
        viajeEnEstado(EstadoProgramacion.PROGRAMADO);

        assertThrows(IllegalArgumentException.class,
                () -> servicio().cambiarEstado(1, EstadoProgramacion.ENTREGADO));
        verify(programacionRepository, never()).save(any());
    }

    @Test
    void enRuta_aEntregado_cambiaEstado() {
        viajeEnEstado(EstadoProgramacion.EN_RUTA);

        ProgramacionResponseDTO r = servicio().cambiarEstado(1, EstadoProgramacion.ENTREGADO);

        assertEquals(EstadoProgramacion.ENTREGADO, r.estadoProgramacion());
    }

    @Test
    void enRuta_aCancelado_noPermitido() {
        viajeEnEstado(EstadoProgramacion.EN_RUTA);

        assertThrows(IllegalArgumentException.class,
                () -> servicio().cambiarEstado(1, EstadoProgramacion.CANCELADO));
    }

    @ParameterizedTest
    @EnumSource(EstadoProgramacion.class)
    void entregado_noPuedeCambiarANingunEstado(EstadoProgramacion nuevo) {
        viajeEnEstado(EstadoProgramacion.ENTREGADO);

        assertThrows(IllegalArgumentException.class, () -> servicio().cambiarEstado(1, nuevo));
        verify(programacionRepository, never()).save(any());
    }

    @ParameterizedTest
    @EnumSource(EstadoProgramacion.class)
    void cancelado_noPuedeCambiarANingunEstado(EstadoProgramacion nuevo) {
        viajeEnEstado(EstadoProgramacion.CANCELADO);

        assertThrows(IllegalArgumentException.class, () -> servicio().cambiarEstado(1, nuevo));
        verify(programacionRepository, never()).save(any());
    }
}