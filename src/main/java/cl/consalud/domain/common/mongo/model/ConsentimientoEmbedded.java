package cl.consalud.domain.common.mongo.model;



import cl.consalud.domain.common.model.Consentimiento;
import cl.consalud.domain.common.model.Periodo;

public record ConsentimientoEmbedded(
        Periodo periodo,
        boolean estado,
        String version,
        String medio,
        Consentimiento.Tipo tipo
) {


}
