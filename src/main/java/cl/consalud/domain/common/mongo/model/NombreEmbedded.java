package cl.consalud.domain.common.mongo.model;

import cl.consalud.domain.common.model.Nombre;
import cl.consalud.domain.common.model.Periodo;

import java.util.List;


public record NombreEmbedded(Nombre.Uso uso,
                             List<String> nombres,
                             List<Nombre.Apellido> apellidos,
                             List<String> prefijos,
                             List<String> sufijos,
                             String texto,
                             String textoOriginal,
                             Periodo periodo,
                             Boolean invertido) {

}
