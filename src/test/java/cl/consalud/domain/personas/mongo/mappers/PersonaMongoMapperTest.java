package cl.consalud.domain.personas.mongo.mappers;

import cl.consalud.domain.common.model.*;
import cl.consalud.domain.common.mongo.mappers.*;
import cl.consalud.domain.personas.model.*;
import cl.consalud.domain.personas.mongo.model.PersonaDocument;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PersonaMongoMapperTest {

    IdentificacionMongoMapper identificacionMapper = Mappers.getMapper(IdentificacionMongoMapper.class);
    NombreMongoMapper nombreMapper = Mappers.getMapper(NombreMongoMapper.class);
    ConsentimientoMongoMapper consentimientoMapper = Mappers.getMapper(ConsentimientoMongoMapper.class);
    CertificacionMongoMapper certificacionMapper = Mappers.getMapper(CertificacionMongoMapper.class);
    BancoMongoMapper bancoMapper = Mappers.getMapper(BancoMongoMapper.class);
    ContactoMongoMapper contactoMapper = Mappers.getMapper(ContactoMongoMapper.class);
    EmpleoMongoMapper empleoMapper = Mappers.getMapper(EmpleoMongoMapper.class);
    SaludMongoMapper saludMapper = Mappers.getMapper(SaludMongoMapper.class);
    CuentaIndividualMongoMapper cuentaIndividualMapper = Mappers.getMapper(CuentaIndividualMongoMapper.class);
    NacionalidadMongoMapper nacionalidadMapper = Mappers.getMapper(NacionalidadMongoMapper.class);

    PersonaMongoMapper unit;

    Persona personaValida;
    PersonaDocument personaDocumentValida;

    @BeforeEach
    void setUp() {
        unit = new PersonaMongoMapperImpl(
                nombreMapper,
                identificacionMapper,
                consentimientoMapper,
                certificacionMapper,
                bancoMapper,
                contactoMapper,
                empleoMapper,
                saludMapper,
                nacionalidadMapper
        );

        var id = new PersonaId();
        List<Identificacion> identificaciones = List.of(new Run("98943652-K"));        var nombres = List.of(new Nombre("Juan", "Pedro", "Perez", "Soto"));
        var consentimientos = List.of(crearConsentimientoValido());
        var salud = new Salud(
                List.of(67),
                67.0,
                1.70,
                ZonedDateTime.now(),
                new Salud.PensionInvalidez(true, Salud.PensionInvalidez.Causal.ENFERMEDAD_COMUN, "Diagnóstico")
        );

        personaValida = new Persona(
                id,
                identificaciones,
                nombres,
                consentimientos,
                List.of(new Certificacion(true, new Periodo(), Certificacion.Tipo.PERSONA, "Test")),
                List.of(new Banco("12456", "galicia", "Corriente", true)),
                List.of(new Email("test@test.com")),
                List.of(),
                List.of(Nacionalidad.CHILENA, Nacionalidad.ARGENTINA),
                EstadoCivil.CASADO,
                ZonedDateTime.now(),
                null,
                List.of(new CuentaIndividual(
                        "123",
                        CuentaIndividual.Tipo.EXCEDENTE,
                        CuentaIndividual.MedioPago.TRANSFERENCIA,
                        CuentaIndividual.EstadoCuenta.VIGENTE,
                        ZonedDateTime.now()
                ))
        );

        personaValida.setSalud(salud);

        personaDocumentValida = new PersonaDocument(
                id,
                contactoMapper.toMongoList(List.of(new Email("test@test.com"))),
                List.of(),
                null,
                Sexo.NO_ESPECIFICADO,
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                identificacionMapper.toMongoList(identificaciones),
                nombreMapper.toMongoList(nombres),
                consentimientoMapper.toMongoList(consentimientos),
                List.of(certificacionMapper.toMongo(new Certificacion(true, new Periodo(), Certificacion.Tipo.PERSONA, "Test"))),
                List.of(bancoMapper.toMongo(new Banco("12456", "galicia", "Corriente", true))),  // Agregar banco
                ZonedDateTime.now(),
                EstadoCivil.CASADO,
                List.of(),
                List.of(),
                saludMapper.toMongo(salud),
                nacionalidadMapper.toEmbeddedList(List.of(Nacionalidad.CHILENA, Nacionalidad.ARGENTINA)),
                List.of(new CuentaIndividual(
                        "123",
                        CuentaIndividual.Tipo.EXCEDENTE,
                        CuentaIndividual.MedioPago.TRANSFERENCIA,
                        CuentaIndividual.EstadoCuenta.VIGENTE,
                        ZonedDateTime.now()
                ))
        );
    }

    @Test
    void toMongo() {
        PersonaDocument result = unit.toMongo(personaValida);

        assertNotNull(result);
        assertEquals(personaValida.getId(), result.id());
        assertEquals(personaValida.getEstadoCivil().orElse(null), result.estadoCivil());
        assertEquals(personaValida.getIdentificaciones().size(), result.identificaciones().size());
        assertEquals(personaValida.getNombres().size(), result.nombres().size());
        assertEquals(personaValida.getConsentimientos().size(), result.consentimientos().size());
        assertEquals(personaValida.getCertificaciones().size(), result.certificaciones().size());
        assertEquals(personaValida.getBancos().size(), result.bancos().size());
        assertEquals(personaValida.getContactos().size(), result.contactos().size());
        assertEquals(personaValida.getEmpleos().size(), result.empleos().size());
        assertEquals(personaValida.getCuentasIndividuales().orElse(List.of()).size(), result.cuentasIndividuales().size());
    }

    @Test
    void toDomain() {
        Persona result = unit.toDomain(personaDocumentValida);

        assertNotNull(result);
        assertEquals(personaDocumentValida.id(), result.getId());
        assertEquals(personaDocumentValida.estadoCivil(), result.getEstadoCivil().orElse(null));
        assertEquals(personaDocumentValida.identificaciones().size(), result.getIdentificaciones().size());
        assertEquals(personaDocumentValida.nombres().size(), result.getNombres().size());
        assertEquals(personaDocumentValida.consentimientos().size(), result.getConsentimientos().size());
        assertEquals(personaDocumentValida.certificaciones().size(), result.getCertificaciones().size());
        assertEquals(personaDocumentValida.bancos().size(), result.getBancos().size());
        assertEquals(personaDocumentValida.contactos().size(), result.getContactos().size());
        assertEquals(personaDocumentValida.empleos().size(), result.getEmpleos().size());
        assertEquals(personaDocumentValida.cuentasIndividuales().size(), result.getCuentasIndividuales().orElse(List.of()).size());
    }

    private Consentimiento crearConsentimientoValido() {
        Consentimiento consentimiento = new Consentimiento(
                Consentimiento.Tipo.MARKETING,
                new Periodo(ZonedDateTime.now(), ZonedDateTime.now().plusDays(30))
        );
        consentimiento.setMedio("WEB");
        consentimiento.setVersion("v1.0");
        consentimiento.setEstado(true);
        return consentimiento;
    }
}
