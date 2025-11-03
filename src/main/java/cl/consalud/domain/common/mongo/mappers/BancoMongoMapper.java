package cl.consalud.domain.common.mongo.mappers;

import cl.consalud.domain.common.model.Banco;
import cl.consalud.domain.common.mongo.model.BancoEmbedded;
import org.mapstruct.Mapper;

@Mapper
public interface BancoMongoMapper {

    BancoEmbedded toMongo(Banco domain);

    Banco toDomain(BancoEmbedded embedded);
}
