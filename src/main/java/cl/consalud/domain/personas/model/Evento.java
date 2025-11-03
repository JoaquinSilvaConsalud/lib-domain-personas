package cl.consalud.domain.personas.model;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public record Evento<T>(
        ZonedDateTime timestamp,
        String atributo, //nombres[0].nombre[1]
        T valorAnterior, // Critian
        T valorFinal, // Cristian
        String usuario,
        String origen,
        Tipo tipo) implements Comparable<Evento<?>> {


    @Override
    public int compareTo(Evento o) {
        return this.timestamp.compareTo(o.timestamp);
    }

    public enum Tipo {
        CREACION, MODIFICACION, ELIMINACION
    }

    public String getTimestamp() {
        return timestamp != null ? timestamp.format(DateTimeFormatter.ISO_DATE_TIME) : null;
    }
    public static Evento<PersonaId> creacion(Persona p, String usuario, String origen) {
        return new Evento<>(ZonedDateTime.now(), p.getClass().getSimpleName(), null,
                p.getId(), usuario, origen, Tipo.CREACION);
    }
}
