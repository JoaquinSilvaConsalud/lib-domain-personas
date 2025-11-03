package cl.consalud.domain.personas.model;

import java.util.List;

/**
 * TODO: Se está haciendo un spike de negocio para definir los conceptos adecuados
 * **/
public class Relacion {

    private PersonaId idPersona;
    private List<Tipo> tipos;

    enum Familiar {
        PADRE, MADRE, HIJO
    }

    enum Tipo {
        CARGA_LEGAL,
        CARGA_MEDICA,
        REPRESENTANTE_LEGAL,
        CONTACTO_EMERGENCIA,
        CONYUGE,
        FAMILIAR,
        CONVIVENCIA_CIVIL,
        APODERADO,
        HEREDERO
    }
}
