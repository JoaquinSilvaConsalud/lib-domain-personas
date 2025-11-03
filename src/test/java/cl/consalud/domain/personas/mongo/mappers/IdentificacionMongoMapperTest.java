package cl.consalud.domain.personas.mongo.mappers;

import cl.consalud.domain.personas.model.Genero;
import cl.consalud.domain.personas.model.Identificacion;
import cl.consalud.domain.personas.model.Run;
import cl.consalud.domain.personas.model.RunTemporal;
import cl.consalud.domain.personas.mongo.model.RunEmbedded;
import cl.consalud.domain.personas.utils.test.RutGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IdentificacionMongoMapperTest {

    final IdentificacionMongoMapper unit = Mappers.getMapper(IdentificacionMongoMapper.class);

    Run runValido;
    RunTemporal runTemporalValido;

    RunEmbedded runEmbeddedValido;
    RunTemporal runTemporalEmbeddedValido;

    @BeforeEach
    void setUp() {
        runValido = RutGenerator.generarRutAleatorio();
        runTemporalValido = new RunTemporal("123456", Genero.FEMENINO);

        /*runEmbeddedValido = new RunEmbedded("70.259.783-8", 70259783, '8',
                Run.TipoRun.PERSONA_JURIDICA, Genero.MASCULINO, Identificacion.Tipo.RUN_TEMPORAL);*/

        runTemporalEmbeddedValido = new RunTemporal("654321", Genero.MASCULINO);
    }

   /* @Test
    void toDomainRun() {

        Run actual = unit.toDomainRun(runEmbeddedValido);

        assertEquals("70.259.783-8", actual.getFormateado());

    }*/

    @Test
    void toDomainRunTemporal() {
    }

    @Test
    void toDomain() {
    }

    @Test
    void toDomainList() {
    }
}