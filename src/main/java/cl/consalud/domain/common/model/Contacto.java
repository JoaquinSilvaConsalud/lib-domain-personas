package cl.consalud.domain.common.model;

import lombok.Data;


@Data
public abstract class Contacto {

    protected Tipo tipo;
    protected boolean preferido = false;
    protected boolean activo;
    protected Uso uso;
    protected String valor;

    public Contacto() {}

    protected abstract boolean isValid(String s);

    protected abstract String formatValor(String s);

    public Contacto(Tipo tipo, boolean preferido, boolean activo, Uso uso, String valor) {
        if(isValid(valor)){
            throw new IllegalArgumentException("El " + tipo.toString() + " es invalido");
        }
        this.tipo = tipo;
        this.preferido = preferido;
        this.activo = activo;
        this.uso = uso;
        this.valor = valor;
    }

    public void setValor(String s) {
        if (this.isValid(s)) {
            throw new IllegalArgumentException("El valor '" + s + "' no es válido para el tipo " + this.tipo);
        }
        this.valor = this.formatValor(s);
    }

    public enum Tipo {
        TELEFONO, EMAIL
    }

    public enum Uso{
        PERSONAL,TRABAJO,MOVIL,CASA,EMERGENCIA,OTRO
    }

}
