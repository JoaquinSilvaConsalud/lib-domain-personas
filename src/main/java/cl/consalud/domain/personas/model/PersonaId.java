package cl.consalud.domain.personas.model;

import java.util.UUID;

public record PersonaId(UUID uuid) {

    private static final char[] alphabet = new char[]{
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z', '1', '2', '3', '4',
            '5', '6', '7', '8', '9'
    };

    public PersonaId() {
        this(UUID.randomUUID());
    }
}
