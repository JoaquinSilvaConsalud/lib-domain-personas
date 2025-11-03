package cl.consalud.domain.common.model;

public class Email implements Contacto {


    protected String valor;
    protected final Contacto.Tipo tipo = Tipo.EMAIL;


    public Email(String valor) {
        setValor(valor);
    }

    public void setValor(String valor) {
        if (validateEmail(valor)) this.valor = valor;

    }

    private boolean validateEmail(String email) {
        return true;
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
