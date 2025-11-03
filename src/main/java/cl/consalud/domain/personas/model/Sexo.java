package cl.consalud.domain.personas.model;

import lombok.Getter;

@Getter
public enum Sexo {

    MASCULINO('M'), FEMENINO('F'), NONATO('X'), NO_ESPECIFICADO('N');

    private final Character sigla;

    Sexo(Character sigla) {
        this.sigla = sigla;
    }

}
