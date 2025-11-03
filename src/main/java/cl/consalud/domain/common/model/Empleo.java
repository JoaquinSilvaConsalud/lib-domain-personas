package cl.consalud.domain.common.model;

import cl.consalud.domain.common.utils.Default;
import cl.consalud.domain.personas.model.Identificacion;
import cl.consalud.domain.personas.model.Run;
import lombok.Getter;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Empleo {

    private final String empleador;
    private final Identificacion identificacion;
    private final List<Direccion> direcciones;
    private final Double rentaImponible;
    private final TipoTrabajador tipoTrabajador;

    @Default
    public Empleo(String empleador,
                  Identificacion identificacion,
                  List<Direccion> direcciones,
                  Double rentaImponible,
                  TipoTrabajador tipoTrabajador) {

        this.empleador = StringUtils.hasText(empleador) ? empleador.trim() : null;
        this.identificacion = identificacion;
        this.direcciones = direcciones != null ? new ArrayList<>(direcciones) : new ArrayList<>();

        if (rentaImponible == null) {
            throw new IllegalArgumentException("La renta imponible no puede ser nula");
        }
        if (rentaImponible < 0) {
            throw new IllegalArgumentException("La renta imponible no puede ser negativa");
        }
        this.rentaImponible = rentaImponible;

        this.tipoTrabajador = tipoTrabajador;
    }

    @Getter
    public enum TipoTrabajador {
        DEPENDIENTE("D", "Dependiente"),
        INDEPENDIENTE("I", "Independiente"),
        PENSIONADO("P", "Pensionado"),
        VOLUNTARIO("V", "Voluntario"),
        TRABAJADOR_DE_CASA_PARTICULAR("C", "Domestico");

        private final String codigo;
        private final String nombre;

        TipoTrabajador(String codigo, String nombre) {
            this.codigo = codigo;
            this.nombre = nombre;
        }
    }

}
