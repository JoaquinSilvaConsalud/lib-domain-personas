package cl.consalud.domain.personas.mongo.mappers;

import cl.consalud.domain.personas.model.Nacionalidad;
import cl.consalud.domain.personas.mongo.model.NacionalidadEmbedded;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;

@Mapper
public interface NacionalidadMongoMapper {

    @Named("toEmbedded")
    default NacionalidadEmbedded toEmbedded(Nacionalidad nacionalidad) {
        if (nacionalidad == null) return null;
        String codigoIso = nacionalidad.getPais().map(p -> p.getCode()).orElse(null);
        return new NacionalidadEmbedded(
                nacionalidad.getCodigoMinsal(),
                codigoIso
        );
    }

    @Named("toDomain")
    default Nacionalidad toDomain(NacionalidadEmbedded embedded) {
        if (embedded == null) return null;
        // Se mantiene el código MINSAL como fuente de verdad:
        return Nacionalidad.fromCodigoMinsal(embedded.getCodigoMinsal());
    }

    @Named("toEmbeddedList")
    default List<NacionalidadEmbedded> toEmbeddedList(List<Nacionalidad> nacionalidades) {
        if (nacionalidades == null) return List.of();
        return nacionalidades.stream().map(this::toEmbedded).toList();
    }

    @Named("toDomainList")
    default List<Nacionalidad> toDomainList(List<NacionalidadEmbedded> embeddeds) {
        if (embeddeds == null) return List.of();
        return embeddeds.stream().map(this::toDomain).toList();
    }
}