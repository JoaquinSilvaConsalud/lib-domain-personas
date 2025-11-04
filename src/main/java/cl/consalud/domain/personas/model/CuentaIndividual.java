package cl.consalud.domain.personas.model;

import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
public class CuentaIndividual {

    private final String idCuenta;
    private final Tipo tipo;
    private final MedioPago medioPago;
    private final EstadoCuenta estadoCuenta;
    private final ZonedDateTime fechaCompromiso;

    public enum Tipo {
        EXCEDENTE, EXCESO, LEY_CORTA
    }

    public enum MedioPago {
        TRANSFERENCIA,VALE_VISTA
    }

    public enum EstadoCuenta {
        VIGENTE, BLOQUEADA, CERRADA
    }

    public CuentaIndividual(String idCuenta, Tipo tipo, MedioPago medioPago, EstadoCuenta estadoCuenta, ZonedDateTime fechaCompromiso) {
        if (idCuenta == null) {
            throw new IllegalArgumentException("Account Id cannot be null");
        }
        if (tipo == null) {
            throw new IllegalArgumentException("Type cannot be null");
        }
        if (medioPago == null) {
            throw new IllegalArgumentException("Payment method cannot be null");
        }
        if (estadoCuenta == null) {
            throw new IllegalArgumentException("Account state cannot be null");
        }
        this.idCuenta = idCuenta;
        this.tipo = tipo;
        this.medioPago = medioPago;
        this.estadoCuenta = estadoCuenta;
        this.fechaCompromiso = fechaCompromiso;
    }
}
