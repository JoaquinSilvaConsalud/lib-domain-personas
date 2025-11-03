package cl.consalud.domain.personas.model;

import lombok.Getter;

@Getter
public enum Genero {

    MASCULINO('M'), FEMENINO('F'), NONATO('X'), NO_ESPECIFICADO('N');

    private final Character sigla;

    Genero(Character sigla) {
        this.sigla = sigla;

    }

}
