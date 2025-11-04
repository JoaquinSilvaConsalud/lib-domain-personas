package cl.consalud.domain.common.mongo.mappers;

import cl.consalud.domain.common.model.Contacto;
import cl.consalud.domain.common.model.Email;
import cl.consalud.domain.common.model.Telefono;
import cl.consalud.domain.common.mongo.model.ContactoEmbedded;
import org.mapstruct.Mapper;
import org.mapstruct.ObjectFactory;
import org.mapstruct.SubclassMapping;
import org.mapstruct.SubclassMappings;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Mapper
public interface ContactoMongoMapper {

    Email toEmailDomain(ContactoEmbedded contacto);

    Telefono toTelefonoDomain(ContactoEmbedded contacto);

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

    @ObjectFactory
    default Contacto crearInstanciaContacto(ContactoEmbedded embedded) {
        Objects.requireNonNull(embedded, "El ContactoEmbedded no puede ser nulo para instanciar un objeto de dominio.");
        Objects.requireNonNull(embedded.tipo(), "El tipo de ContactoEmbedded no puede ser nulo.");

        return switch (embedded.tipo()) {
            case EMAIL -> new Email();
            case TELEFONO -> new Telefono();
        };
    }

    @SubclassMappings({
            @SubclassMapping(source = Email.class, target = ContactoEmbedded.class),
            @SubclassMapping(source = Telefono.class, target = ContactoEmbedded.class)
    })
    ContactoEmbedded toMongo(Contacto contacto);

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
