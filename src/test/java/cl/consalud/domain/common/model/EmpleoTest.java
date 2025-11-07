package cl.consalud.domain.common.model;

import cl.consalud.domain.personas.model.Identificacion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EmpleoTest {
    private Identificacion identificacionRun;
    private Identificacion identificacionPasaporte;
    private List<Direccion> unaDireccion;
    private final String nombreEmpleador = "Consalud";
    private final Double rentaPositiva = 1000000.0;
    private final Double rentaNegativa = -500.0;

    static class Run extends Identificacion {
        public Run() {
            super(Tipo.RUN, null);
        }
    }

    static class Pasaporte extends Identificacion {
        public Pasaporte() {
            super(Tipo.PASAPORTE, null);
        }
    }

    @BeforeEach
    void setUp() {
        identificacionRun = new Run();
        identificacionPasaporte = new Pasaporte();
        unaDireccion = List.of(new Direccion("Metropolitana", "Las Condes", "Apoquindo", "5555"));
    }

    @Test
    @DisplayName("Debe crear un Empleo exitosamente con todos los campos válidos")
    void constructor_DebeCrearDependienteExitosamente() {
        Empleo empleo = assertDoesNotThrow(() -> new Empleo(
                nombreEmpleador,
                identificacionRun,
                unaDireccion,
                rentaPositiva,
                Empleo.TipoTrabajador.DEP
        ));

        assertEquals(nombreEmpleador, empleo.getEmpleador());
        assertEquals(identificacionRun, empleo.getIdentificacion());
        assertEquals(1, empleo.getDirecciones().size());
        assertEquals(rentaPositiva, empleo.getRentaImponible());
        assertEquals(Empleo.TipoTrabajador.DEP, empleo.getTipoTrabajador());
    }

    @Test
    @DisplayName("Debe lanzar excepción si el empleador es nulo o vacío")
    void constructor_DebeLanzarExcepcionPorEmpleadorInvalido() {
        Exception exNulo = assertThrows(IllegalArgumentException.class, () -> new Empleo(
                null, identificacionRun, unaDireccion, rentaPositiva, Empleo.TipoTrabajador.DEP
        ));
        assertEquals("Para un trabajador DEPENDIENTE, el nombre del empleador, un RUN y al menos una dirección son obligatorios", exNulo.getMessage());

        Exception exVacio = assertThrows(IllegalArgumentException.class, () -> new Empleo(
                " ", identificacionRun, unaDireccion, rentaPositiva, Empleo.TipoTrabajador.DEP
        ));
        assertEquals("Para un trabajador DEPENDIENTE, el nombre del empleador, un RUN y al menos una dirección son obligatorios", exVacio.getMessage());
    }

    @Test
    @DisplayName("Debe lanzar excepción si la identificación no es de tipo RUN")
    void constructor_DebeLanzarExcepcionPorIdentificacionNoRun() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> new Empleo(
                nombreEmpleador, identificacionPasaporte, unaDireccion, rentaPositiva, Empleo.TipoTrabajador.DEP
        ));
        assertEquals("Para un trabajador DEPENDIENTE, el nombre del empleador, un RUN y al menos una dirección son obligatorios", ex.getMessage());
    }

    @Test
    @DisplayName("Debe lanzar excepción si la lista de direcciones es nula o vacía")
    void constructor_DebeLanzarExcepcionPorDireccionInvalida() {
        Exception exNula = assertThrows(IllegalArgumentException.class, () -> new Empleo(
                nombreEmpleador, identificacionRun, null, rentaPositiva, Empleo.TipoTrabajador.DEP
        ));
        assertEquals("Para un trabajador DEPENDIENTE, el nombre del empleador, un RUN y al menos una dirección son obligatorios", exNula.getMessage());

        Exception exVacia = assertThrows(IllegalArgumentException.class, () -> new Empleo(
                nombreEmpleador, identificacionRun, Collections.emptyList(), rentaPositiva, Empleo.TipoTrabajador.DEP
        ));
        assertEquals("Para un trabajador DEPENDIENTE, el nombre del empleador, un RUN y al menos una dirección son obligatorios", exVacia.getMessage());
    }

    @Test
    @DisplayName("Debe crear Empleo INDEPENDIENTE con campos opcionales nulos")
    void constructor_DebeCrearIndependienteConNulos() {
        Empleo empleo = assertDoesNotThrow(() -> new Empleo(
                null, null, null, null, Empleo.TipoTrabajador.IND
        ));

        assertNull(empleo.getEmpleador());
        assertNull(empleo.getIdentificacion());
        assertTrue(empleo.getDirecciones().isEmpty());
        assertNull(empleo.getRentaImponible());
        assertEquals(Empleo.TipoTrabajador.IND, empleo.getTipoTrabajador());
    }

    @Test
    @DisplayName("Debe funcionar para todos los tipos de trabajador no dependientes")
    void constructor_FuncionaParaTodosLosTiposOpcionales() {
        assertDoesNotThrow(() -> new Empleo(null, null, null, null, Empleo.TipoTrabajador.PEN));
        assertDoesNotThrow(() -> new Empleo(null, null, null, null, Empleo.TipoTrabajador.VOL));
        assertDoesNotThrow(() -> new Empleo(null, null, null, null, Empleo.TipoTrabajador.DOM));
    }

    @Test
    @DisplayName("Debe lanzar excepción si la renta imponible es negativa")
    void constructor_DebeLanzarExcepcionPorRentaNegativa() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> new Empleo(
                null, null, null, rentaNegativa, Empleo.TipoTrabajador.IND
        ));
        assertEquals("La renta imponible no puede ser negativa", ex.getMessage());
    }

    @Test
    @DisplayName("Debe permitir una renta imponible nula")
    void constructor_DebePermitirRentaNula() {
        Empleo empleo = assertDoesNotThrow(() -> new Empleo(
                null, null, null, null, Empleo.TipoTrabajador.PEN
        ));
        assertNull(empleo.getRentaImponible());
    }

    @Test
    @DisplayName("Debe permitir una renta imponible de cero")
    void constructor_DebePermitirRentaCero() {
        Empleo empleo = assertDoesNotThrow(() -> new Empleo(
                null, null, null, 0.0, Empleo.TipoTrabajador.VOL
        ));
        assertEquals(0.0, empleo.getRentaImponible());
    }
}
