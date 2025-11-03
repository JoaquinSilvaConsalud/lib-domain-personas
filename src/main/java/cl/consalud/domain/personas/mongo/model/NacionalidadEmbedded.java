package cl.consalud.domain.personas.mongo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa la nacionalidad embebida en documentos MongoDB.
 * Solo almacena el código MINSAL y el código ISO del país asociado.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NacionalidadEmbedded {
    private int codigoMinsal;
    private String codigoPaisIso; // Ejemplo: "CL", "AR", "US"
}