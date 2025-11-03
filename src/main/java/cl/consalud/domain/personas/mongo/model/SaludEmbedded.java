package cl.consalud.domain.personas.mongo.model;

import cl.consalud.domain.personas.model.Salud;
import java.time.ZonedDateTime;
import java.util.List;

public record SaludEmbedded(
        List<Integer> codigosPatologia,
        Double peso,
        Double estatura,
        ZonedDateTime fechaUltimaActualizacion,
        PensionInvalidezEmbedded pensionInvalidez
) {
    public record PensionInvalidezEmbedded(
            boolean estado,
            Salud.PensionInvalidez.Causal causal,
            String diagnostico
    ) {}
}