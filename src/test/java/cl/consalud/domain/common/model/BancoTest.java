package cl.consalud.domain.common.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BancoTest {

    private String numeroCuenta;
    private String nombreBanco;
    private String tipoCuenta;

    @BeforeEach
    void setUp() {
        numeroCuenta = "123456";
        nombreBanco = "Galicia";
        tipoCuenta = "Cuenta Corriente";
    }

    @Test
    @DisplayName("Debe crear un Banco con datos válidos")
    void testCrearBancoValido() {
        Banco banco = new Banco(numeroCuenta, nombreBanco, tipoCuenta, true);
        assertNotNull(banco);
        assertEquals(numeroCuenta, banco.getNumeroCuenta());
        assertEquals(nombreBanco, banco.getNombreBanco());
        assertEquals(tipoCuenta, banco.getTipoCuenta());
        assertTrue(banco.isCuentaPreferida());
    }

    @Test
    @DisplayName("Debe crear un Banco con el campo cuentaPreferida en true")
    void testCrearBancoConCuentaPreferida() {
        Banco banco = new Banco(numeroCuenta, nombreBanco, tipoCuenta, true);
        assertTrue(banco.isCuentaPreferida());
    }

    @Test
    @DisplayName("Debe crear un Banco con el campo cuentaPreferida en false")
    void testCrearBancoConCuentaNoPreferida() {
        Banco banco = new Banco(numeroCuenta, nombreBanco, tipoCuenta, false);
        assertFalse(banco.isCuentaPreferida());
    }

    @Test
    @DisplayName("Debe lanzar una excepción si numeroCuenta es null")
    void testCrearBancoConNumeroCuentaNulo() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Banco(null, nombreBanco, tipoCuenta, true);
        });
        assertEquals("Account number cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Debe lanzar una excepción si nombreBanco es null")
    void testCrearBancoConNombreBancoNulo() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Banco(numeroCuenta, null, tipoCuenta, true);
        });
        assertEquals("Bank name cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Debe lanzar una excepción si tipoCuenta es null")
    void testCrearBancoConTipoCuentaNulo() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Banco(numeroCuenta, nombreBanco, null, true);
        });
        assertEquals("Account type cannot be null", exception.getMessage());
    }
}
