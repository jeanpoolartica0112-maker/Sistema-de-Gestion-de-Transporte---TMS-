package pe.edu.utp.tmspaucars.services.programacion;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import pe.edu.utp.tmspaucars.dao.ConductorRepository;
import pe.edu.utp.tmspaucars.dao.UnidadRepository;
import pe.edu.utp.tmspaucars.dao.UsuarioRepository;
import pe.edu.utp.tmspaucars.dao.clientes.ClienteRepository;
import pe.edu.utp.tmspaucars.dtos.ProgramacionRequestDTO;
import pe.edu.utp.tmspaucars.models.Conductor;
import pe.edu.utp.tmspaucars.models.Unidad;
import pe.edu.utp.tmspaucars.services.programacion.validations.ValidarClienteExistente;
import pe.edu.utp.tmspaucars.services.programacion.validations.ValidarConductor;
import pe.edu.utp.tmspaucars.services.programacion.validations.ValidarUsuarioExistente;
import pe.edu.utp.tmspaucars.services.programacion.validations.ValidarVigenciasUnidad;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = org.mockito.quality.Strictness.LENIENT)
class ValidacionesProgramacionTest {

    @Mock private ClienteRepository clienteRepository;
    @Mock private ConductorRepository conductorRepository;
    @Mock private UnidadRepository unidadRepository;
    @Mock private UsuarioRepository usuarioRepository;

    private final LocalDate vigente = LocalDate.now().plusMonths(6);
    private final LocalDate vencido = LocalDate.now().minusDays(1);

    private ProgramacionRequestDTO dto() {
        return new ProgramacionRequestDTO("20123456789", LocalDate.now().plusDays(1),
                "ABC123", "XYZ789", "C001", "Lima", "Arequipa", new BigDecimal("3500.00"), 1);
    }

    private Conductor conductor(LocalDate vcto, String estado) {
        Conductor c = new Conductor();
        c.setVctoLicencia(vcto);
        c.setEstadoDisponibilidad(estado);
        return c;
    }

    private Unidad unidad(String placa, LocalDate soat, LocalDate revision) {
        Unidad u = new Unidad();
        u.setPlaca(placa);
        u.setFechaSoat(soat);
        u.setFechaRevisionTecnica(revision);
        u.setFechaPermisoMtc(vigente);
        return u;
    }

    private void stubUnidades(Unidad tracto, Unidad acoplado) {
        when(unidadRepository.findById("ABC123")).thenReturn(Optional.of(tracto));
        when(unidadRepository.findById("XYZ789")).thenReturn(Optional.of(acoplado));
    }

    // ---------- Cliente ----------
    @Test
    void cliente_inexistente_lanzaExcepcion() {
        when(clienteRepository.existsById("20123456789")).thenReturn(false);
        var v = new ValidarClienteExistente(clienteRepository);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> v.validar(dto()));
        assertTrue(ex.getMessage().contains("no existe"));
    }

    @Test
    void cliente_existente_noLanzaExcepcion() {
        when(clienteRepository.existsById("20123456789")).thenReturn(true);
        assertDoesNotThrow(() -> new ValidarClienteExistente(clienteRepository).validar(dto()));
    }

    // ---------- Conductor ----------
    @Test
    void conductor_inexistente_lanzaExcepcion() {
        when(conductorRepository.findById("C001")).thenReturn(Optional.empty());
        var v = new ValidarConductor(conductorRepository);

        assertThrows(RuntimeException.class, () -> v.validar(dto()));
    }

    @Test
    void conductor_conLicenciaVencida_lanzaExcepcion() {
        when(conductorRepository.findById("C001")).thenReturn(Optional.of(conductor(vencido, "ACTIVO")));
        var v = new ValidarConductor(conductorRepository);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> v.validar(dto()));
        assertTrue(ex.getMessage().contains("licencia"));
    }

    @Test
    void conductor_noActivo_lanzaExcepcion() {
        when(conductorRepository.findById("C001")).thenReturn(Optional.of(conductor(vigente, "INACTIVO")));
        var v = new ValidarConductor(conductorRepository);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> v.validar(dto()));
        assertTrue(ex.getMessage().contains("ACTIVO"));
    }

    @Test
    void conductor_valido_noLanzaExcepcion() {
        when(conductorRepository.findById("C001")).thenReturn(Optional.of(conductor(vigente, "ACTIVO")));
        assertDoesNotThrow(() -> new ValidarConductor(conductorRepository).validar(dto()));
    }

    // ---------- Usuario ----------
    @Test
    void usuario_inexistente_lanzaExcepcion() {
        when(usuarioRepository.existsById(1)).thenReturn(false);
        var v = new ValidarUsuarioExistente(usuarioRepository);

        assertThrows(IllegalArgumentException.class, () -> v.validar(dto()));
    }

    @Test
    void usuario_existente_noLanzaExcepcion() {
        when(usuarioRepository.existsById(1)).thenReturn(true);
        assertDoesNotThrow(() -> new ValidarUsuarioExistente(usuarioRepository).validar(dto()));
    }

    // ---------- Unidades ----------
    @Test
    void tracto_inexistente_lanzaExcepcion() {
        when(unidadRepository.findById("ABC123")).thenReturn(Optional.empty());
        var v = new ValidarVigenciasUnidad(unidadRepository);

        assertThrows(RuntimeException.class, () -> v.validar(dto()));
    }

    @Test
    void tracto_conSoatVencido_lanzaExcepcion() {
        stubUnidades(unidad("ABC123", vencido, vigente), unidad("XYZ789", vigente, vigente));
        var v = new ValidarVigenciasUnidad(unidadRepository);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> v.validar(dto()));
        assertTrue(ex.getMessage().contains("SOAT"));
    }

    @Test
    void tracto_conRevisionTecnicaVencida_lanzaExcepcion() {
        stubUnidades(unidad("ABC123", vigente, vencido), unidad("XYZ789", vigente, vigente));
        var v = new ValidarVigenciasUnidad(unidadRepository);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> v.validar(dto()));
        assertTrue(ex.getMessage().contains("Revisión Técnica"));
    }


    @Test
    void unidades_vigentes_noLanzaExcepcion() {
        stubUnidades(unidad("ABC123", vigente, vigente), unidad("XYZ789", vigente, vigente));
        assertDoesNotThrow(() -> new ValidarVigenciasUnidad(unidadRepository).validar(dto()));
    }
}