package cl.consalud.domain.personas.mongo.mappers;

import cl.consalud.domain.personas.model.CuentaIndividual;
import cl.consalud.domain.personas.mongo.model.CuentaIndividualEmbedded;
import org.mapstruct.Mapper;

@Mapper
public interface CuentaIndividualMongoMapper {

    CuentaIndividualEmbedded toMongo(CuentaIndividual domain);

    CuentaIndividual toDomain(CuentaIndividualEmbedded embedded);
}
