package cl.consalud.domain.personas.mongo.mappers;

import cl.consalud.domain.common.mongo.mappers.*;
import cl.consalud.domain.personas.model.CuentaIndividual;
import cl.consalud.domain.personas.model.Persona;
import cl.consalud.domain.common.mongo.mappers.EmpleoMongoMapper;
import cl.consalud.domain.personas.mongo.model.PersonaDocument;
import org.mapstruct.BeanMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;

@Mapper(uses = {
        NombreMongoMapper.class,
        IdentificacionMongoMapper.class,
        ConsentimientoMongoMapper.class,
        CertificacionMongoMapper.class,
        BancoMongoMapper.class,
        ContactoMongoMapper.class,
        EmpleoMongoMapper.class,
        SaludMongoMapper.class,
        CuentaIndividualMongoMapper.class,
        NacionalidadMongoMapper.class
},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR

)
public interface PersonaMongoMapper {

    @Mapping(target = "id", source = "persona.id")
    @Mapping(target = "identificaciones", source = "persona.identificaciones")
    @Mapping(target = "nombres", source = "persona.nombres")
    @Mapping(target = "consentimientos", source = "persona.consentimientos")
    @Mapping(target = "certificaciones", source = "persona.certificaciones")
    @Mapping(target = "salud", source = "persona.salud")
    @Mapping(target = "nacionalidades", source = "persona.nacionalidades", qualifiedByName = "toEmbeddedList")
    @Mapping(target = "bancos", source = "persona.bancos")
    @Mapping(target = "estadoCivil", expression = "java(persona.getEstadoCivil().orElse(null))")
    @Mapping(target = "fechaFallecimiento", expression = "java(mapFechaFallecimiento(persona))")
    @Mapping(target = "fechaNacimiento", expression = "java(mapFechaNacimiento(persona))")
    @Mapping(target = "historico", source = "persona.historico")
    @Mapping(target = "empleos", source = "persona.empleos")
    @Mapping(target = "cuentasIndividuales", expression = "java(persona.getCuentasIndividuales().orElse(null))")
    PersonaDocument toMongo(Persona persona);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "estadoCivil", source = "estadoCivil")
    @Mapping(target = "sombreros", ignore = true)
    @Mapping(target = "relaciones", ignore = true)
    @Mapping(target = "documentos", ignore = true)
    @Mapping(target = "direcciones", ignore = true)
    @Mapping(target = "contratos", source = "contratos")
    @Mapping(target = "contactos", source = "contactos")
    @Mapping(target = "id", source = "id")
    @Mapping(target = "identificaciones", source = "identificaciones")
    @Mapping(target = "nombres", source = "nombres")
    @Mapping(target = "consentimientos", source = "consentimientos")
    @Mapping(target = "certificaciones", source = "certificaciones")
    @Mapping(target = "salud", source = "salud")
    @Mapping(target = "nacionalidades", source = "nacionalidades", qualifiedByName = "toDomainList")
    @Mapping(target = "bancos", source = "bancos")
    @Mapping(target = "fechaNacimiento", source = "fechaNacimiento")
    @Mapping(target = "fechaFallecimiento", source = "fechaFallecimiento")
    @Mapping(target = "historico", source = "historico")
    @Mapping(target = "empleos", source = "empleos")
    @Mapping(target = "cuentasIndividuales", source = "cuentasIndividuales")
    Persona toDomain(PersonaDocument document);

    default ZonedDateTime mapFechaNacimiento(Persona persona) {
        return persona.getFechaNacimiento()
                .map(ZonedDateTime::parse)
                .orElse(null);
    }

    default ZonedDateTime mapFechaFallecimiento(Persona persona) {
        return persona.getFechaFallecimiento()
                .map(ZonedDateTime::parse)
                .orElse(null);
    }
}
