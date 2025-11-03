package cl.consalud.domain.common.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TelefonoTest {

    String validPhoneString = "+56 9 1234 5678";
    Telefono validPhone;

    @BeforeEach
    void setUp() {
        validPhone = new Telefono(validPhoneString);
    }

    @Test
    void getValor() {

        assertEquals(validPhoneString, validPhone.getValor());
        assertNotEquals("", validPhone.getValor());
        validPhone.setValor("");
        assertEquals("", validPhone.getValor());
    }

    @Test
    void getTipo() {

        assertNotNull(validPhone.getTipo());
        assertEquals(Contacto.Tipo.TELEFONO, validPhone.getTipo());
        assertNotEquals(Contacto.Tipo.EMAIL, validPhone.getTipo());
    }
}
