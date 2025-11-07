package cl.consalud.domain.common.model;

import cl.consalud.domain.common.utils.Default;
import cl.consalud.domain.personas.model.Identificacion;
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

        if(tipoTrabajador.codigo.equals("D")){
            boolean empleadorInvalido  = !StringUtils.hasText(empleador);
            boolean  identificacionInvalida = identificacion.getTipo() != Identificacion.Tipo.RUN;
            boolean direccionesInvalidas = direcciones==null || direcciones.isEmpty();
            if(empleadorInvalido || identificacionInvalida || direccionesInvalidas){
                throw new IllegalArgumentException("Para un trabajador DEPENDIENTE, el nombre del empleador, un RUN y al menos una dirección son obligatorios");
            }
        }

        this.empleador = StringUtils.hasText(empleador) ? empleador.trim() : null;
        this.identificacion = identificacion;
        this.direcciones = direcciones != null ? new ArrayList<>(direcciones) : new ArrayList<>();

        if(rentaImponible!=null){
            if (rentaImponible < 0) {
                throw new IllegalArgumentException("La renta imponible no puede ser negativa");
            }
        }

        this.rentaImponible = rentaImponible;

        this.tipoTrabajador = tipoTrabajador;
    }

    @Getter
    public enum TipoTrabajador {
        DEP("D", "Dependiente"),
        IND("I", "Independiente"),
        PEN("P", "Pensionado"),
        VOL("V", "Voluntario"),
        DOM("C", "Trabajador de casa particular");

        private final String codigo;
        private final String nombre;

        TipoTrabajador(String codigo, String nombre) {
            this.codigo = codigo;
            this.nombre = nombre;
        }
    }

}
