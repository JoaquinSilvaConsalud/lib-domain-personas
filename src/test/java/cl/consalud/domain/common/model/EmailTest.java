package cl.consalud.domain.common.model;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailTest {

    final String validEmailString = "julieta@gmail.com";
    Email validEmail = new Email(validEmailString);

    @Nested
    class ConstructorTests {

        @Test
        void constructorSimple_conEmailValido() {
            Email emailValido = new Email(validEmailString);

            assertEquals(validEmailString, emailValido.getValor());
            assertEquals(Contacto.Tipo.EMAIL, emailValido.getTipo());
        }

        @Test
        void constructorSimple_debeEstablecerValoresPorDefecto() {
            Email email = new Email("default@example.com");

            assertFalse(email.isPreferido(), "Un email nuevo no debería ser preferido por defecto");
            assertTrue(email.isActivo(), "Un email nuevo debería estar activo por defecto");
        }

        @Test
        void constructorCompleto_debeAsignarTodosLosValores() {
            Email email = new Email("trabajo@empresa.cl");

            assertEquals("trabajo@empresa.cl", email.getValor());
            assertFalse(email.isPreferido());
            assertTrue(email.isActivo());
            assertEquals(Contacto.Tipo.EMAIL, email.getTipo());
        }
    }

    @Test
    void constructor_emailvalido(){
        Email emailValido = new Email(validEmailString);

        assertEquals(validEmailString,emailValido.getValor());
        assertEquals(Contacto.Tipo.EMAIL,emailValido.getTipo());
    }

    @Test
    void getTipo() {

        assertNotNull(validEmail.getTipo());
        assertEquals(Contacto.Tipo.EMAIL, validEmail.getTipo());
        assertNotEquals(Contacto.Tipo.TELEFONO, validEmail.getTipo());
    }
}
