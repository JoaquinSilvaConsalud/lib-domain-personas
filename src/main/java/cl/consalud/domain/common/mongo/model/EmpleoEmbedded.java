package cl.consalud.domain.common.mongo.model;

import cl.consalud.domain.common.model.Direccion;
import cl.consalud.domain.common.model.Empleo;
import cl.consalud.domain.personas.model.Run;
import cl.consalud.domain.personas.mongo.model.IdentificacionEmbedded;
import cl.consalud.domain.personas.mongo.model.RunEmbedded;

import java.util.List;

public record EmpleoEmbedded(
        String empleador,
        IdentificacionEmbedded identificacion,
        List<Direccion> direcciones,
        Double rentaImponible,
        String tipoTrabajador
) {
}
