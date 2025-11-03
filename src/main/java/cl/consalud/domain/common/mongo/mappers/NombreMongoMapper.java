package cl.consalud.domain.common.mongo.mappers;

import cl.consalud.domain.common.model.Nombre;
import cl.consalud.domain.common.mongo.model.NombreEmbedded;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface NombreMongoMapper {

    @Mapping(target = "uso", source = "nombre.uso")
    @Mapping(target = "nombres", source = "nombre.nombres")
    /*@Mapping(target = "apellidos", source = "apellidos")
    @Mapping(target = "prefijos", source = "prefijos")
    @Mapping(target = "sufijos", source = "sufijos")
    @Mapping(target = "texto", source = "texto")
    @Mapping(target = "textoOriginal", source = "textoOriginal")
    @Mapping(target = "periodo", source = "periodo")
    @Mapping(target = "invertido", source = "invertido")*/
    NombreEmbedded toMongo(Nombre nombre);

    List<NombreEmbedded> toMongoList(List<Nombre> nombre);

    @Mapping(target = "apellidosAsList", ignore = true)
    @Mapping(target = "uso", source = "embedded.uso")
    @Mapping(target = "nombres", source = "embedded.nombres")
    @Mapping(target = "apellidos", source = "embedded.apellidos")
    @Mapping(target = "prefijos", source = "embedded.prefijos")
    @Mapping(target = "sufijos", source = "embedded.sufijos")
    @Mapping(target = "texto", source = "embedded.texto")
    @Mapping(target = "textoOriginal", source = "embedded.textoOriginal")
    @Mapping(target = "periodo", source = "embedded.periodo")
    @Mapping(target = "invertido", source = "embedded.invertido")
    Nombre toDomain(NombreEmbedded embedded);

    List<Nombre> toDomainList(List<NombreEmbedded> nombres);
}
