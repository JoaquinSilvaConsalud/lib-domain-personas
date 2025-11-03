package cl.consalud.domain.personas.validation;

import cl.consalud.domain.personas.model.Nacionalidad;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class PersonaNacionalidadValidator {

    private PersonaNacionalidadValidator() {}

    public static void validarNacionalidades(List<Nacionalidad> nacionalidades) {
        // Permitir personas sin nacionalidades informadas
        if (nacionalidades == null || nacionalidades.isEmpty()) return;

        Set<Nacionalidad> tipos = new HashSet<>();
        Set<Integer> codigos = new HashSet<>();

        for (Nacionalidad n : nacionalidades) {
            if (n == null) {
                throw new IllegalArgumentException("La lista de nacionalidades contiene un valor nulo.");
            }

            // Valida duplicado de tipo (enum)
            if (!tipos.add(n)) {
                throw new IllegalArgumentException("Nacionalidad repetida: " + n.name());
            }

            // Valida duplicado de código MINSAL
            if (!codigos.add(n.getCodigoMinsal())) {
                throw new IllegalArgumentException("Código MINSAL duplicado: " + n.getCodigoMinsal() +
                        " (" + n.name() + ")");
            }
        }
    }
}