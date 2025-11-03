package cl.consalud.domain.personas.model;

import lombok.Getter;
import java.time.ZonedDateTime;
import java.util.List;

@Getter
public class Salud {

    private final List<Integer> codigosPatologia;
    private final Double peso;
    private final Double estatura;
    private final ZonedDateTime fechaUltimaActualizacion;
    private final PensionInvalidez pensionInvalidez;

    public Salud(List<Integer> codigosPatologia,
                 Double peso,
                 Double estatura,
                 ZonedDateTime fechaUltimaActualizacion,
                 PensionInvalidez pensionInvalidez) {
        this.codigosPatologia = codigosPatologia;
        this.peso = peso;
        this.estatura = estatura;
        this.fechaUltimaActualizacion = fechaUltimaActualizacion;
        this.pensionInvalidez = pensionInvalidez;
    }

    @Getter
    public static class PensionInvalidez {

        private final boolean estado;
        private final Causal causal;
        private final String diagnostico;

        public PensionInvalidez(boolean estado, Causal causal, String diagnostico) {
            this.estado = estado;
            this.causal = causal;
            this.diagnostico = diagnostico;
        }

        public enum Causal {
            ENFERMEDAD_COMUN,
            ENFERMEDAD_PROFESIONAL
        }
    }
}