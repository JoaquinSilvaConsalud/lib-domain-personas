package cl.consalud.domain.personas.validation;

import cl.consalud.domain.personas.model.Nacionalidad;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class PersonaNacionalidadValidatorTest {

    @Test
    void validaListaCorrectaSinDuplicados() {
        var nacionalidades = List.of(Nacionalidad.CHILENA, Nacionalidad.ARGENTINA);
        assertDoesNotThrow(() -> PersonaNacionalidadValidator.validarNacionalidades(nacionalidades));
    }

    @Test
    void lanzaErrorSiHayDuplicados() {
        var nacionalidades = List.of(Nacionalidad.CHILENA, Nacionalidad.CHILENA);
        var ex = assertThrows(IllegalArgumentException.class,
                () -> PersonaNacionalidadValidator.validarNacionalidades(nacionalidades));
        assertTrue(ex.getMessage().contains("repetida") || ex.getMessage().contains("repetido"));
    }

    @Test
    void ignoraListaVacia() {
        assertDoesNotThrow(() -> PersonaNacionalidadValidator.validarNacionalidades(List.of()));
    }

    @Test
    void ignoraListaNula() {
        assertDoesNotThrow(() -> PersonaNacionalidadValidator.validarNacionalidades(null));
    }

    @Test
    void lanzaErrorSiContieneNull() {
        var nacionalidades = new ArrayList<Nacionalidad>();
        nacionalidades.add(Nacionalidad.CHILENA);
        nacionalidades.add(null);

        var ex = assertThrows(IllegalArgumentException.class,
                () -> PersonaNacionalidadValidator.validarNacionalidades(nacionalidades));

        assertTrue(ex.getMessage().toLowerCase().contains("nulo"));
    }
}