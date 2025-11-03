package cl.consalud.domain.personas.mongo.model;

import cl.consalud.domain.personas.model.Genero;
import cl.consalud.domain.personas.model.Identificacion;
import cl.consalud.domain.personas.model.Run;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.TypeAlias;

@TypeAlias("IdentRun")
public class RunEmbedded extends IdentificacionEmbedded {

    public String run;
    public Integer numero;
    public Character verificador;
    public Run.TipoRun tipoRun;

    @PersistenceCreator
    public RunEmbedded(Identificacion.Tipo tipo, Genero genero, String run, Integer numero, Character verificador, Run.TipoRun tipoRun) {
        super(tipo, genero);
        this.run = run;
        this.numero = numero;
        this.verificador = verificador;
        this.tipoRun = tipoRun;
    }
}
