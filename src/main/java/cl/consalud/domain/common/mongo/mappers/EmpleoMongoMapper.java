package cl.consalud.domain.common.mongo.mappers;

import cl.consalud.domain.common.model.Empleo;
import cl.consalud.domain.common.model.Nombre;
import cl.consalud.domain.common.mongo.model.EmpleoEmbedded;
import cl.consalud.domain.common.mongo.model.NombreEmbedded;
import cl.consalud.domain.personas.mongo.mappers.IdentificacionMongoMapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(uses = {
        IdentificacionMongoMapper.class
})
public interface EmpleoMongoMapper {

    @Mapping(target = "empleador", source = "empleo.empleador")
    @Mapping(target = "identificacion", source = "empleo.identificacion")
    @Mapping(target = "direcciones", source = "empleo.direcciones")
    @Mapping(target = "rentaImponible", source = "empleo.rentaImponible")
    @Mapping(target = "tipoTrabajador", expression = "java(empleo.getTipoTrabajador().name())")
    EmpleoEmbedded toMongo(Empleo empleo);

    List<EmpleoEmbedded> toMongoList(List<Empleo> empleos);

    @Mapping(target = "empleador", source = "embedded.empleador")
    @Mapping(target = "identificacion", source = "embedded.identificacion")
    @Mapping(target = "direcciones", source = "embedded.direcciones")
    @Mapping(target = "rentaImponible", source = "embedded.rentaImponible")
    @Mapping(target = "tipoTrabajador", expression = "java(Empleo.TipoTrabajador.valueOf(embedded.tipoTrabajador()))")
    Empleo toDomain(EmpleoEmbedded embedded);

    List<Empleo> toDomainList(List<EmpleoEmbedded> empleos);
}
