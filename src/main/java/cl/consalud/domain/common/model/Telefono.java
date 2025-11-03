package cl.consalud.domain.common.model;

public class Telefono implements Contacto {

    protected String valor;
    protected final Contacto.Tipo tipo = Tipo.TELEFONO;

    public Telefono(String valor) {
        setValor(valor);
    }

    private boolean validate(String valor) {
        return true;
    }

    public void setValor(String valor) {
        if (validate(valor)) {
            this.valor = valor;
        }
    }

    @Override
    public String getValor() {
        return valor;
    }

    @Override
    public Tipo getTipo() {
        return tipo;
    }
}
