package cl.consalud.domain.common.model;

import lombok.Getter;

@Getter
public class Banco {

    private String numeroCuenta;
    private String nombreBanco;
    private String tipoCuenta;
    private boolean cuentaPreferida;

    public Banco(String numeroCuenta, String nombreBanco, String tipoCuenta, boolean cuentaPreferida) {
        if (numeroCuenta == null) {
            throw new IllegalArgumentException("Account number cannot be null");
        }
        if (nombreBanco == null) {
            throw new IllegalArgumentException("Bank name cannot be null");
        }
        if (tipoCuenta == null) {
            throw new IllegalArgumentException("Account type cannot be null");
        }
        this.numeroCuenta = numeroCuenta;
        this.nombreBanco = nombreBanco;
        this.tipoCuenta = tipoCuenta;
        this.cuentaPreferida = cuentaPreferida;
    }
}
