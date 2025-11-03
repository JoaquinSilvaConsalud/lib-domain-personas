package cl.consalud.domain.personas.model;

import cl.consalud.domain.common.model.Periodo;
import lombok.Data;
import java.util.List;

@Data
public class Contrato {

    private String folio;
    private boolean estado;
    private Periodo periodo;
    private Tipo tipo;
    private List<Documento> documentos;

    public Contrato(String folio,boolean estado,Periodo periodo, Tipo tipo, List<Documento> documentos){
        if(folio==null || folio.isBlank()){
            throw new IllegalArgumentException("error id contrato");
        }
        if (periodo == null || periodo.getInicio() == null) {
            throw new IllegalArgumentException("error periodo");
        }
        if (tipo == null) {
            throw new IllegalArgumentException("error tipo");
        }
        if(documentos == null){
            throw new IllegalArgumentException("error lista documentos");
        }
        this.folio = folio;
        this.estado = estado;
        this.periodo = periodo;
        this.tipo = tipo;
        this.documentos = documentos;
    }


    public enum Tipo{
        INDIVIDUAL,COLECTIVO
    }
}
