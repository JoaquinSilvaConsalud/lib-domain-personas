package cl.consalud.domain.personas.model;

import lombok.Data;

import java.net.URL;
import java.util.UUID;

@Data
public class Documento {

    private UUID documentoId;
    private String tipo;
    private URL url;
    private String mediaType;
    private String nombre;
    private String origen;

}
