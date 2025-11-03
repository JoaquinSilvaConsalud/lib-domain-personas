package cl.consalud.domain.personas.mongo.mappers;

import cl.consalud.domain.personas.model.Salud;
import cl.consalud.domain.personas.mongo.model.SaludEmbedded;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface SaludMongoMapper {

    @Mapping(target = "pensionInvalidez", source = "pensionInvalidez")
    SaludEmbedded toMongo(Salud model);

    @Mapping(target = "pensionInvalidez", source = "embedded.pensionInvalidez")
    Salud toDomain(SaludEmbedded embedded);
}