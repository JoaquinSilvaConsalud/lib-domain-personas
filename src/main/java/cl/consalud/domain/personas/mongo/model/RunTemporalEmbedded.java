package cl.consalud.domain.personas.mongo.model;

import cl.consalud.domain.personas.model.Genero;
import cl.consalud.domain.personas.model.Identificacion;
import org.springframework.data.annotation.TypeAlias;

@TypeAlias("IdentRunTemporal")
public class RunTemporalEmbedded extends IdentificacionEmbedded {
    public final String numero;

    public RunTemporalEmbedded(String numero, Genero genero, Identificacion.Tipo tipo) {
        super(tipo, genero);
        this.numero = numero;
    }
}
