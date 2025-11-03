package cl.consalud.domain.personas.mongo.model;

import cl.consalud.domain.personas.model.Genero;
import cl.consalud.domain.personas.model.Identificacion;

public abstract class IdentificacionEmbedded {

    public final Identificacion.Tipo tipo;
    public final Genero genero;

    public IdentificacionEmbedded(Identificacion.Tipo tipo, Genero genero) {
        this.tipo = tipo;
        this.genero = genero;
    }
}