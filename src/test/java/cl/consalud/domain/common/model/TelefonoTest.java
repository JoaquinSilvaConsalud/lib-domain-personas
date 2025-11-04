package cl.consalud.domain.common.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TelefonoTest {

    final String validPhoneString = "+56947390969";


    @Test
    void constructor_DebeCrearObjetoConTelefonoValido() {
        Telefono validPhone = new Telefono(validPhoneString);

        assertEquals(validPhoneString, validPhone.getValor());
        assertEquals(Contacto.Tipo.TELEFONO, validPhone.getTipo());
        assertTrue(validPhone.isActivo());
    }

    @Test
    void constructor_DebeLanzarExcepcionParaTelefonoInvalido() {
        assertThrows(IllegalArgumentException.class, () -> new Telefono("+123"));
        assertThrows(IllegalArgumentException.class, () -> new Telefono(null));
    }
}
