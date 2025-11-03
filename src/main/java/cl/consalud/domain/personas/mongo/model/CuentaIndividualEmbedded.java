package cl.consalud.domain.personas.mongo.model;

import cl.consalud.domain.personas.model.CuentaIndividual;

import java.time.ZonedDateTime;

public record CuentaIndividualEmbedded(
        String idCuenta,
        CuentaIndividual.Tipo tipo,
        CuentaIndividual.MedioPago medioPago,
        CuentaIndividual.EstadoCuenta estadoCuenta,
        ZonedDateTime fechaCompromiso
) {

}
