package cl.consalud.domain.common.model;

public interface Contacto {
    String getValor();
    Tipo getTipo();

    enum Tipo {
        TELEFONO, EMAIL
    }
}
