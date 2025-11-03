package cl.consalud.domain.common.model;

import lombok.Data;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data

public class Direccion {

    @Setter
    private Uso uso = Uso.HOGAR;
    @Setter
    private Tipo tipo = Tipo.FISICO;
    @Setter
    private String region;
    @Setter
    private String provincia;
    @Setter
    private String comuna;
    @Setter
    private String ciudad;
    @Setter
    private String poblacion;
    @Setter
    private String calle;
    @Setter
    private String numero;

    private final List<String> lineas = new ArrayList<>();
    @Setter
    private String texto;

    @Setter
    private Calidad calidad = Calidad.FORMA;

    @Setter
    private String codigoPostal;

    @Setter
    private Periodo periodo;


    Direccion() {}

    public Direccion(String region, String comuna, String calle, String numero) {
        this(Tipo.FISICO, Uso.HOGAR, region, comuna,
                "", "", "",
                calle, numero,
                Collections.emptyList(), Calidad.EXISTENCIA,
                "", new Periodo());
    }

    public Direccion(Tipo tipo, Uso uso,
                     String region, String comuna, String ciudad, String provincia,
                     String poblacion, String calle, String numero,
                     List<String> lineas,
                     Calidad calidad, String codigoPostal, Periodo periodo
    ) {

        this.region = region;
        this.comuna = comuna;
        this.ciudad = ciudad;
        this.provincia = provincia;
        this.poblacion = poblacion;
        this.calle = calle;
        this.numero = numero;
        this.calidad = calidad;
        this.codigoPostal = codigoPostal;
        this.periodo = periodo;
        this.lineas.addAll(lineas);
    }


    public enum Tipo {
        FISICO, CORREO
    }

    public enum Uso {
        HOGAR, TRABAJO, OTRO
    }

    public enum Calidad {
        FORMA, EXISTENCIA, DEPENDENCIA
    }
}
