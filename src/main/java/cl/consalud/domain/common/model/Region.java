package cl.consalud.domain.common.model;

public enum Region {

    ARICA_PARINACOTA("Arica y Parinacota"),
    TARAPACA("Tarapacá"),
    ANTOFAGASTA("Antofagasta"),
    ATACAMA("Atacama"),
    COQUIMBO("Coquimbo"),
    VALPARAISO("Valparaíso"),
    METROPOLITANA("Región Metropolitana de Santiago"),
    O_HIGGINS("Libertador General Bernardo O’Higgins"),
    MAULE("Maule"),
    ÑUBLE("Ñuble"),
    BIOBIO("Biobío"),
    ARAUCANIA("La Araucanía"),
    LOS_RIOS("Los Ríos"),
    LOS_LAGOS("Los Lagos"),
    AYSEN("Aysén del General Carlos Ibáñez del Campo"),
    MAGALLANES("Magallanes y de la Antártica Chilena");

    private final String nombre;

    Region(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}

