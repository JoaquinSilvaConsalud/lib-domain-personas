package cl.consalud.domain.common.model;

import lombok.Getter;

@Getter
public class Certificacion {

    private final boolean estado;
    private final Periodo periodo;
    private final Tipo tipo;
    private final String nombre;

    public enum Tipo {
        PERSONA,
        SERVICIO
    }

    public Certificacion(boolean estado, Periodo periodo, Tipo tipo, String nombre) {
        this.estado = estado;
        this.periodo = estado ? periodo : null;
        this.tipo = estado ? tipo : null;
        this.nombre = estado ? nombre : null;
    }

    public static Certificacion of(boolean estado, Periodo periodo, Tipo tipo, String nombre) {
        return new Certificacion(estado, periodo, tipo, nombre);
    }

    @Override
    public String toString() {
        if (!estado) {
            return "Certificacion[NO VERIFICADA]";
        }
        return "Certificacion[" + tipo + " por " + nombre + " del " + periodo.getInicio() + " al " + periodo.getFin() + "]";
    }
}