package cl.consalud.domain.common.mongo.model;

import cl.consalud.domain.common.model.Certificacion;
import cl.consalud.domain.common.model.Periodo;

public record CertificacionEmbedded(
        boolean estado,
        Periodo periodo,
        Certificacion.Tipo tipo,
        String nombre
) {

    public CertificacionEmbedded(Certificacion certificacion) {
        this(
                certificacion.isEstado(),
                certificacion.getPeriodo(),
                certificacion.getTipo(),
                certificacion.getNombre()
        );
    }

    public Certificacion toDomain() {
        return Certificacion.of(estado, periodo, tipo, nombre);
    }
}