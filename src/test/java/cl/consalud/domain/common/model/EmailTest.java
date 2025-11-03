package cl.consalud.domain.common.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class EmailTest {

    final String validEmailString = "julieta@gmail.com";
    Email validEmail;

    @BeforeEach
    void setUp() {
        validEmail = new Email(validEmailString);
    }

    @Test
    void getValor() {

        assertEquals(validEmailString, validEmail.getValor());
        assertNotEquals("", validEmail.getValor());
        validEmail.setValor("");
        assertEquals("", validEmail.getValor());
    }

    @Test
    void getTipo() {

        assertNotNull(validEmail.getTipo());
        assertEquals(Contacto.Tipo.EMAIL, validEmail.getTipo());
        assertNotEquals(Contacto.Tipo.TELEFONO, validEmail.getTipo());
    }
}
