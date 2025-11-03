package cl.consalud.domain.personas.mongo.model;

import cl.consalud.domain.common.model.Direccion;
import cl.consalud.domain.common.mongo.model.*;
import cl.consalud.domain.personas.model.*;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.ZonedDateTime;
import java.util.List;

@Document(collection = "personas")
@CompoundIndexes(
        {
                @CompoundIndex(
                        name = "idx_ident_run",
                        def = "{'identificaciones._class':1, 'identificaciones.run.normalized': 1}"
                ),
                @CompoundIndex(
                        name = "idx_ident_run_unique",
                        def = "{'identificaciones.run': 1}",
                        unique = true
                )
        }
)

public record PersonaDocument(
        @MongoId PersonaId id,
        List<ContactoEmbedded> contactos,
        List<Direccion> direcciones,
        ZonedDateTime fechaFallecimiento,
        Sexo sexo,
        List<Persona.Sombrero> sombreros,
        List<Relacion> relaciones,
        List<Documento> documentos,
        List<Contrato> contratos,
        List<IdentificacionEmbedded> identificaciones,
        List<NombreEmbedded> nombres,
        List<ConsentimientoEmbedded> consentimientos,
        List<CertificacionEmbedded> certificaciones,
        List<BancoEmbedded> bancos,
        @Indexed ZonedDateTime fechaNacimiento,
        EstadoCivil estadoCivil,
        List<Evento<?>> historico,
        List<EmpleoEmbedded> empleos,
        SaludEmbedded salud,
        List<NacionalidadEmbedded> nacionalidades,
        List<CuentaIndividual> cuentasIndividuales

) {
}
