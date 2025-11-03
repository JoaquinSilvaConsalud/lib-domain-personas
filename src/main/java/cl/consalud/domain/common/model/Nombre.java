package cl.consalud.domain.common.model;

import cl.consalud.domain.common.exception.NameValidationException;
import cl.consalud.domain.common.utils.CollectionUtils;
import cl.consalud.domain.common.utils.Default;
import cl.consalud.domain.common.utils.StringUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class Nombre {

    protected static final int MAXIMUM_NAME_LENGTH = 40;
    protected static final int MAXIMUM_SURNAME_LENGTH = 60;
    protected static final byte MAXIMUM_NAME_LIST_SIZE = 12;
    protected static final byte MAXIMUM_SURNAME_LIST_SIZE = 8;
    protected static final byte MAXIMUM_PARTICLE_LENGTH = 10;
    protected static final byte MAXIMUM_PARTICLE_LIST_SIZE = 5;
    protected static final int MAXIMUM_TEXTO_ORIGINAL = 256;


    @Setter
    protected Uso uso = Uso.OFICIAL;

    protected final List<String> nombres = new ArrayList<>();
    protected final List<Apellido> apellidos = new ArrayList<>();
    protected List<String> prefijos;
    protected List<String> sufijos;
    protected String texto;
    protected String textoOriginal;
    @Setter
    protected Periodo periodo;
    protected Boolean invertido;

    protected List<Pronombre> pronombres;


    public Nombre(String nombre, String nombre2, String paterno, String materno) {
        this(List.of(nombre, nombre2),
                List.of(new Apellido(paterno, TipoApellido.PATERNO, 0),
                        new Apellido(materno, TipoApellido.MATERNO, 1))
        );
    }


    public Nombre(List<String> nombres, List<Apellido> apellidos) {
        this(Uso.OFICIAL, nombres, apellidos);
    }

    public Nombre(Uso uso, List<String> nombres, List<Apellido> apellidos) {
        this(uso, nombres, apellidos, null, null);
    }

    public Nombre(Uso uso, List<String> nombres, List<Apellido> apellidos, @Nullable List<String> prefijos,
                  @Nullable List<String> sufijos) {
        this(uso, nombres, apellidos, prefijos, sufijos, null);
    }

    @Default
    public Nombre(Uso uso, List<String> nombres, List<Apellido> apellidos, @Nullable List<String> prefijos,
                  @Nullable List<String> sufijos, Periodo periodo) {
        this(uso, nombres, apellidos, prefijos, sufijos, periodo, null, null, false,null);

    }

    public Nombre(Uso uso, List<String> nombres, List<Apellido> apellidos, List<String> prefijos,
                  List<String> sufijos, Periodo periodo, String texto, String textoOriginal, Boolean invertido,List<Pronombre> pronombres) {

        this.uso = uso == null ? Uso.OFICIAL : uso;



        setNombres(nombres, false);
        setApellidos(apellidos, false);
        setPrefijos(prefijos, false);
        setSufijos(sufijos, false);
        setPronombres(pronombres);

        if (StringUtils.isEmpty(texto)) {
            setTexto();
        } else {
            this.texto = texto;
        }

        this.textoOriginal = textoOriginal;
        this.periodo = periodo;
        this.invertido = invertido;
    }

    private void validateNameList(List<String> names) {

        if (CollectionUtils.nullOrEmpty(names)) {
            throw new NameValidationException("There must be at least one name");
        }

        if (names.size() > MAXIMUM_NAME_LIST_SIZE) {
            throw new NameValidationException(String.format("A maximum of %d names are allowed", MAXIMUM_NAME_LIST_SIZE));
        }

        if (names.stream().noneMatch(StringUtils::hasText)) {
            throw new NameValidationException("Names cannot all be blank");
        }

        if (names.stream().anyMatch(s -> s.length() > MAXIMUM_NAME_LENGTH)) {
            throw new NameValidationException(String.format("Los nombres no pueden exceder %d caracteres de longitud", MAXIMUM_NAME_LENGTH));
        }

    }

    private void validateApellidoList(List<Apellido> apellidos) {
        if (uso == Uso.OFICIAL && CollectionUtils.nullOrEmpty(apellidos)) {
            throw new NameValidationException("There must be at least one surname.");
        }

        if(apellidos.stream().anyMatch(a->a.tipo() == TipoApellido.UNICO) && apellidos.size()>1){
            throw new NameValidationException("el apellido es unico, no puede haber otros apellidos en la lista.");
        }

        if(apellidos.stream().anyMatch(a->a.tipo() == TipoApellido.PRIMERO) && apellidos.size()>1){
            throw new NameValidationException("el apellido es primero, no puede haber otros apellidos en la lista.");
        }

        if(apellidos.stream().anyMatch(a->a.tipo() == TipoApellido.SEGUNDO) && apellidos.size()>1){
            throw new NameValidationException("el apellido es segundo, no puede haber otros apellidos segundos en la lista.");
        }

        if (apellidos.size() > MAXIMUM_SURNAME_LIST_SIZE) {
            throw new NameValidationException(String.format("A maximum of %d surnames are allowed", MAXIMUM_SURNAME_LIST_SIZE));
        }

        if (uso == Uso.OFICIAL && apellidos.stream().noneMatch(apellido -> StringUtils.hasText(apellido.texto))) {
            throw new NameValidationException("Surnames cannot be blank.");
        }

        if (apellidos.stream().anyMatch(a -> a.texto.length() > MAXIMUM_SURNAME_LENGTH)) {
            throw new NameValidationException(String.format("Surnames cannot exceed %d characters in length", MAXIMUM_SURNAME_LENGTH));
        }

        if (apellidos.stream().map(Apellido::order).distinct().toList().size() != apellidos.size()) {
            throw new NameValidationException("Surnames must have a different order to each other");
        }

    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public void setInvertido(Boolean inverted) {
        this.invertido = inverted;
        setTexto();
    }

    private void setTexto() {

        List<String> prepare = new ArrayList<>();

        if (this.prefijos != null) {
            prepare.addAll(this.prefijos);
        }


        if (Boolean.TRUE.equals(invertido)) {
            prepare.addAll(getApellidosAsList());
            prepare.addAll(nombres);
        } else {
            prepare.addAll(nombres);
            prepare.addAll(getApellidosAsList());
        }

        this.texto = String.join(" ", prepare) +
                ((sufijos == null || sufijos.isEmpty()) ? "" : ", " + String.join(", ", sufijos));
    }

    @JsonIgnore
    public String getNombresAsString() {
        return String.join(" ", nombres);
    }

    @JsonIgnore
    public String getApellidosAsString() {
        return String.join(" ", apellidos.stream().map(Apellido::texto).toList());
    }

    @JsonIgnore
    public String getPrefijosAsString() {
        if(this.prefijos==null){
            return "";
        }
        return String.join(" ", prefijos);
    }

    @JsonIgnore
    public String getSufijosAsString() {
        if(this.sufijos==null){
            return "";
        }
        return String.join(", ", sufijos);
    }

    public String getTextoOriginal(){
        return this.textoOriginal!=null ? this.textoOriginal : this.texto;
    }

    @JsonIgnore
    public List<String> getApellidosAsList() {
        return apellidos.stream().map(Apellido::texto).toList();
    }

    public void setNombres(String... nombres) {
        setNombres(Arrays.asList(nombres));
    }

    public void setNombres(List<String> nombres) {
        setNombres(nombres, true);
    }

    protected void setNombres(List<String> nombres, boolean setTexto) {
        validateNameList(nombres);
        this.nombres.clear();
        this.nombres.addAll(nombres);
        if (setTexto) setTexto();
    }

    public void setApellidos(Apellido... apellidos) {
        setApellidos(Arrays.asList(apellidos));
    }

    public void setApellidos(String... apellidos) {
        setApellidos(IntStream
                .range(0, apellidos.length)
                .mapToObj(i -> new Apellido(apellidos[i], TipoApellido.FAMILIAR, i))
                .collect(Collectors.toList()));
    }

    public void setApellidos(List<Apellido> apellidos) {
        setApellidos(apellidos, true);
    }

    protected void setApellidos(List<Apellido> apellidos, boolean setTexto) {
        validateApellidoList(apellidos);
        this.apellidos.clear();
        this.apellidos.addAll(apellidos);
        this.apellidos.sort(Apellido::compareTo);
        if (setTexto) setTexto();
    }

    private boolean validateAttachments(List<String> attachments) {
        if (attachments == null) return false;
        if (attachments.size() > MAXIMUM_PARTICLE_LIST_SIZE) {
            throw new NameValidationException(String.format("Only up to %d prefixes are allowed", MAXIMUM_PARTICLE_LIST_SIZE));
        }
        if (attachments.stream().anyMatch(s -> s.length() > MAXIMUM_PARTICLE_LENGTH)) {
            throw new NameValidationException(String.format("Prefixes cannot have more than %d characters", MAXIMUM_PARTICLE_LENGTH));
        }
        return true;
    }

    public void setPrefijos(String... prefijos) {
        setPrefijos(Arrays.asList(prefijos));
    }

    public void setPrefijos(List<String> prefijos) {
        setPrefijos(prefijos, true);
    }

    protected void setPrefijos(List<String> prefijos, boolean setTexto) {
        if(prefijos == null){
            this.prefijos = null;
            if(setTexto) setTexto();
            return;
        }

        if (validateAttachments(prefijos)) {
            this.prefijos = prefijos.stream().filter(StringUtils::hasText).toList();
            if (setTexto) setTexto();
        }
    }

    public void setSufijos(@Nonnull String... sufijos) {
        setSufijos(Arrays.asList(sufijos));
    }

    public void setSufijos(List<String> sufijos) {
        setSufijos(sufijos, true);
    }

    protected void setSufijos(List<String> sufijos, boolean setTexto) {
        if(sufijos == null){
            this.sufijos =null;
            if(setTexto) setTexto();
            return;
        }
        if (validateAttachments(sufijos)) {
            this.sufijos = sufijos.stream().filter(StringUtils::hasText).toList();
            if (setTexto) setTexto();
        }
    }

    public void setPronombres(List<Pronombre> pronombres) {
        this.pronombres = pronombres;
    }

    public void setPronombres(Pronombre... pronombres) {
        setPronombres(Arrays.asList(pronombres));
    }

    public enum Uso {
        OFICIAL, ALIAS, SOCIAL
    }

    public enum TipoApellido {
        MATERNO, PATERNO, UNICO, FAMILIAR, TRADICIONAL, PRIMERO, SEGUNDO, MATRIMONIO, PATRONIMICO, SOLTERO, COMPUESTO, TRIBAL
    }

    public enum Pronombre {
        EL, ELLA, OTRO
    }

    @Override
    public String toString() {
        return texto;
    }

    public record Apellido(String texto, TipoApellido tipo, int order) implements Comparable<Apellido> {
        @Override
        public int compareTo(@Nonnull Apellido o) {
            return Integer.compare(this.order, o.order);
        }
    }

    public boolean hasTextoOriginal() {
        return this.textoOriginal!=null &&  !this.textoOriginal.equals(this.texto);
    }

    public void setTextoOriginal(String textoOriginal) {
       if(!StringUtils.hasText(textoOriginal)){
           this.textoOriginal = null;
           return;
       }
        if (textoOriginal.getBytes(StandardCharsets.UTF_8).length > MAXIMUM_TEXTO_ORIGINAL) return;
        this.textoOriginal = textoOriginal;
    }

    Nombre copy() {
        return new Nombre(uso, nombres, apellidos, prefijos, sufijos, periodo, texto, textoOriginal, invertido,pronombres);
    }
}
