package cl.consalud.domain.common.mongo.model;

public record BancoEmbedded(
        String numeroCuenta,
        String nombreBanco,
        String tipoCuenta
) {
}
