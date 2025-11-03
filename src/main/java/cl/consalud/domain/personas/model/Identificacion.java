package cl.consalud.domain.personas.model;

import lombok.Getter;
import lombok.Setter;

@Getter
public abstract class Identificacion {

    protected final Tipo tipo;
    @Setter
    protected Genero genero;

    protected Identificacion(Tipo tipo, Genero genero) {
        this.tipo = tipo;
        this.genero = genero;
    }

    public enum Tipo {
        RUN, RUN_TEMPORAL, PASAPORTE
    }

}
