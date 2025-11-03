package cl.consalud.domain.personas.model;

import cl.consalud.domain.common.utils.Default;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RunTemporal extends Identificacion {

    private String numero;

    public RunTemporal(String numero) {
        super(Tipo.RUN_TEMPORAL, Genero.NO_ESPECIFICADO);
        this.numero = numero;
    }

    @Default
    public RunTemporal(String numero, Genero genero) {
        super(Tipo.RUN_TEMPORAL, genero);
        this.numero = numero;
    }
}
