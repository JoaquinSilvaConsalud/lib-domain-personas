package cl.consalud.domain.personas.model;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class NacionalidadTest {

    @Test
    void testFromCodigoMinsalDevuelveEnumCorrecto() {
        assertEquals(Nacionalidad.CHILENA, Nacionalidad.fromCodigoMinsal(152));
        assertEquals(Nacionalidad.MEXICANA, Nacionalidad.fromCodigoMinsal(484));
        assertEquals(Nacionalidad.SUIZA, Nacionalidad.fromCodigoMinsal(756));
    }

    @Test
    void testFromCodigoDevuelveSinInformacionParaCodigoMinsalDesconocido() {
        assertEquals(Nacionalidad.SIN_INFORMACION, Nacionalidad.fromCodigoMinsal(999999));
        assertEquals(Nacionalidad.SIN_INFORMACION, Nacionalidad.fromCodigoMinsal(-1));
    }

    @Test
    void testCodigosMinsalSonUnicos() {
        Set<Integer> codigos = new HashSet<>();
        for (Nacionalidad n : Nacionalidad.values()) {
            assertTrue(codigos.add(n.getCodigoMinsal()),
                    () -> "Código MINSAL duplicado detectado en: " + n.name() + " (" + n.getCodigoMinsal() + ")");
        }
    }

    @Test
    void testMapaPorCodigoContieneTodosLosValores() {
        for (Nacionalidad n : Nacionalidad.values()) {
            Nacionalidad desdeMapa = Nacionalidad.fromCodigoMinsal(n.getCodigoMinsal());
            assertNotNull(desdeMapa, "El mapa no contiene: " + n.name());
            assertEquals(n, desdeMapa, "El mapa devuelve un valor distinto para el código " + n.getCodigoMinsal());
        }
    }

    @Test
    void testFromPaisDevuelveEnumCorrecto() {
        assertEquals(Nacionalidad.CHILENA, Nacionalidad.fromPais(cl.consalud.domain.common.model.Pais.CHILE));
        assertEquals(Nacionalidad.ARGENTINA, Nacionalidad.fromPais(cl.consalud.domain.common.model.Pais.ARGENTINA));
        assertEquals(Nacionalidad.SIN_INFORMACION, Nacionalidad.fromPais(null));
    }

    @Test
    void testPaisNoEsNuloSalvoSinInformacion() {
        for (Nacionalidad n : Nacionalidad.values()) {
            if (n == Nacionalidad.SIN_INFORMACION) continue;
            assertTrue(n.getPais().isPresent(), n.name() + " debe tener un país asociado");
        }
    }

    @Test
    void testCodigoDeSinInformacion() {
        assertEquals(0, Nacionalidad.SIN_INFORMACION.getCodigoMinsal());
        assertTrue(Nacionalidad.SIN_INFORMACION.getPais().isEmpty());
    }
}