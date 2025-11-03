package cl.consalud.domain.common.model;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static cl.consalud.domain.common.utils.DateUtils.SHORT_DATE_TIME_FORMATTER;


@Getter
@Setter
public class Periodo {


    private final ZonedDateTime inicio;
    private ZonedDateTime fin;


    public Periodo() {
        this.inicio = ZonedDateTime.now();
    }

    public Periodo(ZonedDateTime inicio) {
        this.inicio = inicio;
    }

    public Periodo(ZonedDateTime start, ZonedDateTime fin) {
        if (fin.isBefore(start)) {
            throw new IllegalArgumentException("Un periodo no puede terminar antes de empezar.");
        }
        this.inicio = start;
        this.fin = fin;
    }

    public void setFin(ZonedDateTime fin) {
        if (fin.isBefore(inicio)) {
            throw new IllegalArgumentException("Un periodo no puede terminar antes de empezar.");
        }
        this.fin = fin;
    }

    public String getInicio() {
        return inicio.format(DateTimeFormatter.ISO_DATE_TIME);
    }

    public String getFin() {
        return fin != null ? fin.format(DateTimeFormatter.ISO_DATE_TIME) : null;
    }
    @Override
    public String toString() {
        return String.format("Periodo(inicio=%s%s) ",
                SHORT_DATE_TIME_FORMATTER.format(inicio),
                fin != null ? SHORT_DATE_TIME_FORMATTER.format(fin) : "");
    }
}
