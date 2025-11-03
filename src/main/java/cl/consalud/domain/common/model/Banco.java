package cl.consalud.domain.common.model;

import lombok.Getter;

@Getter
public class Banco {

    private String numeroCuenta;
    private String nombreBanco;
    private String tipoCuenta;

    public Banco(String numeroCuenta, String nombreBanco, String tipoCuenta) {
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
    }
}
