package cl.consalud.domain.common.model;

import lombok.Data;

@Data
public class Consentimiento {

    private Periodo periodo;
    private boolean estado;
    private Tipo tipo;
    private String version;
    private String medio;


    public Consentimiento() {}

    public Consentimiento(Tipo tipo, Periodo periodo) {
        if(tipo==null){
            throw new IllegalArgumentException("el propósito no puede ser nulo");
        }
        if(periodo==null){
            throw new IllegalArgumentException("El periodo no puede ser nulo");
        }
        this.periodo = periodo;
    }

    public Consentimiento(Tipo tipo) {
        if (tipo == null) {
            throw new IllegalArgumentException("El propósito no puede ser nulo.");
        }
        this.tipo= tipo;
        this.periodo = null;
    }

    public enum Tipo{
        MARKETING,TERCEROS,POLITICA_PRIVACIDAD,DATOS_SENSIBLES,ANALITICA
    }
}
