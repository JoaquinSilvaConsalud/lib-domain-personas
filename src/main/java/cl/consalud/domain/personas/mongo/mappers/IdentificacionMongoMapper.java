package cl.consalud.domain.personas.mongo.mappers;

import cl.consalud.domain.personas.model.Identificacion;
import cl.consalud.domain.personas.model.Run;
import cl.consalud.domain.personas.model.RunTemporal;
import cl.consalud.domain.personas.mongo.model.IdentificacionEmbedded;
import cl.consalud.domain.personas.mongo.model.RunEmbedded;
import cl.consalud.domain.personas.mongo.model.RunTemporalEmbedded;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.SubclassExhaustiveStrategy;
import org.mapstruct.SubclassMapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface IdentificacionMongoMapper {


    RunEmbedded toRunEmbedded(Run run);

    RunTemporalEmbedded toRunTemporalEmbedded(RunTemporal runTemporal);

    @BeanMapping(subclassExhaustiveStrategy = SubclassExhaustiveStrategy.RUNTIME_EXCEPTION)
    @SubclassMapping(target = RunEmbedded.class, source = Run.class)
    @SubclassMapping(target = RunTemporalEmbedded.class, source = RunTemporal.class)
    IdentificacionEmbedded toMongo(Identificacion identificacion);

    List<IdentificacionEmbedded> toMongoList(List<Identificacion> identificaciones);


    Run toDomainRun(RunEmbedded embedded);

    RunTemporal toDomainRunTemporal(RunTemporalEmbedded embedded);

    @BeanMapping(subclassExhaustiveStrategy = SubclassExhaustiveStrategy.RUNTIME_EXCEPTION)
    @SubclassMapping(target = Run.class, source = RunEmbedded.class)
    @SubclassMapping(target = RunTemporal.class, source = RunTemporalEmbedded.class)
    Identificacion toDomain(IdentificacionEmbedded doc);

    default List<Identificacion> toDomainList(List<IdentificacionEmbedded> identificaciones){
        if(identificaciones==null){
            return null;
        }
        return identificaciones.stream().map(this::toDomain).collect(Collectors.toList());
    }
}
