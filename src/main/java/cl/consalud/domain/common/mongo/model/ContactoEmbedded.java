package cl.consalud.domain.common.mongo.model;

import cl.consalud.domain.common.model.Contacto;

public record ContactoEmbedded(
        String valor,
        Contacto.Tipo tipo,
        boolean preferido,
        Boolean activo,
        Contacto.Uso uso
) {
}
