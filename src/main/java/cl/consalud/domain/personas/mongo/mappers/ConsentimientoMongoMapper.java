package cl.consalud.domain.personas.mongo.mappers;

import cl.consalud.domain.common.model.Consentimiento;
import cl.consalud.domain.common.mongo.model.ConsentimientoEmbedded;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface ConsentimientoMongoMapper {

    ConsentimientoEmbedded toMongo(Consentimiento consentimiento);

    List<ConsentimientoEmbedded> toMongoList(List<Consentimiento> consentimiento);

    Consentimiento toDomain(ConsentimientoEmbedded consentimiento);

    List<Consentimiento> toDomainList(List<Consentimiento> consentimiento);
}
