package cl.consalud.domain.common.mongo.mappers;

import cl.consalud.domain.common.model.Contacto;
import cl.consalud.domain.common.model.Email;
import cl.consalud.domain.common.model.Telefono;
import cl.consalud.domain.common.mongo.model.ContactoEmbedded;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface ContactoMongoMapper {

    Email toEmailDomain(ContactoEmbedded contacto);

    Telefono toTelefonoDomain(ContactoEmbedded contacto);

  /*  Contacto toDomain(Contacto contacto);

    ContactoEmbedded toMongo(ContactoEmbedded contacto);*/

    default Contacto toDomain(ContactoEmbedded embedded) {
        if (embedded == null) {
            return null;
        }

        return switch (embedded.tipo()) {
            case EMAIL -> toEmailDomain(embedded);
            case TELEFONO -> toTelefonoDomain(embedded);
            default -> throw new IllegalArgumentException("Tipo de contacto no soportado: " + embedded.tipo());
        };
    }

    default ContactoEmbedded toMongo(Contacto contacto) {
        if (contacto == null) {
            return null;
        }
        return new ContactoEmbedded(contacto.getValor(), contacto.getTipo());
    }

    default List<Contacto> toDomainList(List<ContactoEmbedded> embeddedList) {
        if (embeddedList == null) {
            return null;
        }
        return embeddedList.stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }
    default List<ContactoEmbedded> toMongoList(List<Contacto> contactoList) {
        if (contactoList == null) {
            return null;
        }
        return contactoList.stream()
                .map(this::toMongo)
                .collect(Collectors.toList());
    }

}
