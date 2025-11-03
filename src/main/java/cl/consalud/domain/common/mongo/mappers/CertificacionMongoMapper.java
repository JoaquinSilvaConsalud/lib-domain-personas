package cl.consalud.domain.common.mongo.mappers;

import cl.consalud.domain.common.model.Certificacion;
import cl.consalud.domain.common.mongo.model.CertificacionEmbedded;
import org.mapstruct.Mapper;

@Mapper
public interface CertificacionMongoMapper {

    CertificacionEmbedded toMongo(Certificacion domain);

    Certificacion toDomain(CertificacionEmbedded embedded);
}