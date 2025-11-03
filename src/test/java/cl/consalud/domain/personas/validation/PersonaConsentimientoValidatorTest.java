package cl.consalud.domain.personas.validation;

import cl.consalud.domain.common.model.Consentimiento;
import cl.consalud.domain.common.model.Periodo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersonaConsentimientoValidatorTest {

    private Consentimiento consentimientoBasico(Consentimiento.Tipo tipo) {
        return new Consentimiento(tipo,
                new Periodo(ZonedDateTime.now(), ZonedDateTime.now().plusDays(30)));
    }

    private Consentimiento consentimientoCompleto(Consentimiento.Tipo tipo, String medio, String version) {
        Consentimiento c = consentimientoBasico(tipo);
        c.setMedio(medio);
        c.setVersion(version);
        c.setEstado(true);
        return c;
    }

    @Test
    @DisplayName("validarConsentimientos: permite null o lista vacía")
    void validarConsentimientos_permiteNullOVacio() {
        assertDoesNotThrow(() -> PersonaConsentimientoValidator.validarConsentimientos(null));
        assertDoesNotThrow(() -> PersonaConsentimientoValidator.validarConsentimientos(List.of()));
    }

    @Test
    @DisplayName("validarConsentimientos: válido con tipo y periodo")
    void validarConsentimientos_validoMinimo() {
        List<Consentimiento> lista = List.of(consentimientoBasico(Consentimiento.Tipo.MARKETING));
        assertDoesNotThrow(() -> PersonaConsentimientoValidator.validarConsentimientos(lista));
    }

    @Test
    @DisplayName("validarConsentimientos: válido con medio y versión definidos")
    void validarConsentimientos_validoConMedioYVersion() {
        List<Consentimiento> lista = List.of(consentimientoCompleto(Consentimiento.Tipo.MARKETING, "WEB", "v1.0"));
        assertDoesNotThrow(() -> PersonaConsentimientoValidator.validarConsentimientos(lista));
    }

    @Test
    @DisplayName("validarConsentimientos: falla si falta tipo")
    void validarConsentimientos_fallaSinTipo() {
        Consentimiento c = new Consentimiento();
        c.setPeriodo(new Periodo(ZonedDateTime.now(), ZonedDateTime.now().plusDays(1)));
        c.setTipo(null); // tipo ausente

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> PersonaConsentimientoValidator.validarConsentimientos(List.of(c)));

        assertTrue(ex.getMessage().contains("El consentimiento debe tener un tipo definido."));
    }

    @Test
    @DisplayName("validarConsentimientos: falla si falta periodo")
    void validarConsentimientos_fallaSinPeriodo() {
        Consentimiento c = new Consentimiento(Consentimiento.Tipo.MARKETING);
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> PersonaConsentimientoValidator.validarConsentimientos(List.of(c)));
        assertTrue(ex.getMessage().toLowerCase().contains("periodo"));
    }

    @Test
    @DisplayName("validarConsentimientos: falla si hay medio y falta versión")
    void validarConsentimientos_fallaMedioSinVersion() {
        Consentimiento c = consentimientoBasico(Consentimiento.Tipo.MARKETING);
        c.setMedio("APP");
        c.setVersion(null);
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> PersonaConsentimientoValidator.validarConsentimientos(List.of(c)));
        assertTrue(ex.getMessage().contains("versión cuando se define un medio"));
    }

    @Test
    @DisplayName("validarConsentimientos: falla si hay versión y falta medio")
    void validarConsentimientos_fallaVersionSinMedio() {
        Consentimiento c = consentimientoBasico(Consentimiento.Tipo.MARKETING);
        c.setVersion("v1.0");
        c.setMedio(null);
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> PersonaConsentimientoValidator.validarConsentimientos(List.of(c)));
        assertTrue(ex.getMessage().contains("medio cuando se define una versión"));
    }

    @Test
    @DisplayName("validarConsentimientos: falla por duplicados de tipo")
    void validarConsentimientos_fallaDuplicados() {
        Consentimiento c1 = consentimientoBasico(Consentimiento.Tipo.MARKETING);
        Consentimiento c2 = consentimientoBasico(Consentimiento.Tipo.MARKETING); // mismo tipo
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> PersonaConsentimientoValidator.validarConsentimientos(List.of(c1, c2)));
        assertTrue(ex.getMessage().contains("Solo puede existir un consentimiento por tipo"));
    }

    @Test
    @DisplayName("agregarOReemplazar: crea lista cuando actuales es null")
    void agregarOReemplazar_creaListaSiNull() {
        Consentimiento nuevo = consentimientoBasico(Consentimiento.Tipo.MARKETING);
        List<Consentimiento> result = PersonaConsentimientoValidator.agregarOReemplazar(null, nuevo);
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(Consentimiento.Tipo.MARKETING, result.get(0).getTipo());
    }

    @Test
    @DisplayName("agregarOReemplazar: agrega si no existe tipo")
    void agregarOReemplazar_agregaSiNoExisteTipo() {
        List<Consentimiento> actuales = new ArrayList<>();
        actuales.add(consentimientoBasico(Consentimiento.Tipo.ANALITICA));

        Consentimiento nuevo = consentimientoBasico(Consentimiento.Tipo.MARKETING);
        List<Consentimiento> result = PersonaConsentimientoValidator.agregarOReemplazar(actuales, nuevo);

        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(c -> c.getTipo() == Consentimiento.Tipo.ANALITICA));
        assertTrue(result.stream().anyMatch(c -> c.getTipo() == Consentimiento.Tipo.MARKETING));
    }

    @Test
    @DisplayName("agregarOReemplazar: reemplaza si ya existe el tipo")
    void agregarOReemplazar_reemplazaSiExisteTipo() {
        Consentimiento original = consentimientoCompleto(Consentimiento.Tipo.MARKETING, "WEB", "v1.0");
        List<Consentimiento> actuales = new ArrayList<>(List.of(original));

        Consentimiento reemplazo = consentimientoCompleto(Consentimiento.Tipo.MARKETING, "APP", "v2.0");
        List<Consentimiento> result = PersonaConsentimientoValidator.agregarOReemplazar(actuales, reemplazo);

        assertEquals(1, result.size());
        Consentimiento unico = result.get(0);
        assertEquals("APP", unico.getMedio());
        assertEquals("v2.0", unico.getVersion());
    }

    @Test
    @DisplayName("agregarOReemplazar: falla si 'nuevo' es nulo")
    void agregarOReemplazar_fallaNuevoNulo() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> PersonaConsentimientoValidator.agregarOReemplazar(new ArrayList<>(), null));
        assertTrue(ex.getMessage().toLowerCase().contains("no puede ser nulo"));
    }

    @Test
    @DisplayName("agregarOReemplazar: falla por reglas de datos obligatorios")
    void agregarOReemplazar_fallaDatosObligatorios() {
        // tipo OK, y periodo falta entonces debe fallar
        Consentimiento invalido = new Consentimiento(Consentimiento.Tipo.MARKETING);
        assertThrows(IllegalArgumentException.class,
                () -> PersonaConsentimientoValidator.agregarOReemplazar(new ArrayList<>(), invalido));
    }
}