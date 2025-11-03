package cl.consalud.domain.personas.model;

import cl.consalud.domain.common.model.*;
import cl.consalud.domain.common.utils.CollectionUtils;
import cl.consalud.domain.common.utils.Default;
import cl.consalud.domain.personas.validation.PersonaConsentimientoValidator;
import cl.consalud.domain.personas.validation.PersonaNacionalidadValidator;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
public class Persona {


    @Setter
    protected PersonaId id;

    protected final List<Identificacion> identificaciones = new ArrayList<>();
    protected final List<Nombre> nombres = new ArrayList<>();

    @Setter
    protected ZonedDateTime fechaNacimiento;

    protected final List<Sombrero> sombreros = new ArrayList<>();

    @Setter
    protected EstadoCivil estadoCivil;

    protected final List<Contacto> contactos = new ArrayList<>();
    protected final List<Direccion> direcciones = new ArrayList<>();
    protected final List<Consentimiento> consentimientos = new ArrayList<>();
    protected final List<Certificacion> certificaciones = new ArrayList<>();
    protected final List<Banco> bancos = new ArrayList<>();
    @Setter
    protected List<CuentaIndividual> cuentasIndividuales;

    @Setter
    protected ZonedDateTime fechaFallecimiento;

    @Setter
    protected Sexo sexo = Sexo.NO_ESPECIFICADO;

    protected final List<Relacion> relaciones = new ArrayList<>();
    protected final List<Documento> documentos = new ArrayList<>();
    protected final List<Contrato> contratos = new ArrayList<>();

    protected final List<Evento<?>> historico = new ArrayList<>();

    @Setter
    protected Salud salud;

    private final List<Nacionalidad> nacionalidades = new ArrayList<>();

    protected final List<Empleo> empleos = new ArrayList<>();

    //Persona() {this.id = new PersonaId();}

    public Persona(PersonaId id) {
        this.id = id;
    }

    protected Persona() {
        this.id = new PersonaId();
    }

    @Default
    public Persona(PersonaId id,
                   List<Identificacion> identificaciones,
                   List<Nombre> nombres,
                   List<Consentimiento> consentimientos,
                   List<Certificacion> certificaciones,
                   List<Banco> bancos,
                   List<Contacto> contactos,
                   List<Empleo> empleos,
                   List<Nacionalidad> nacionalidades,
                   EstadoCivil estadoCivil,
                   ZonedDateTime fechaNacimiento,
                   ZonedDateTime fechaFallecimiento,
                   List<CuentaIndividual> cuentasIndividuales
                   ) {

        this.fechaNacimiento = fechaNacimiento;

        this.fechaFallecimiento = fechaFallecimiento;

        this.estadoCivil = estadoCivil;

        this.id = id;

        this.empleos.addAll(empleos);

        if (CollectionUtils.nullOrEmpty(contactos)) {
            throw new IllegalArgumentException("Must have at least one contact information.");
        }
        this.contactos.addAll(contactos);

        if (CollectionUtils.nullOrEmpty(identificaciones)) {
            throw new IllegalArgumentException("Must have at least one identification.");
        }
        if (identificaciones.stream().noneMatch(iden ->
                iden.tipo == Identificacion.Tipo.RUN || iden.tipo == Identificacion.Tipo.RUN_TEMPORAL)) {
            throw new IllegalArgumentException("At least one identification must be a Run or a Temporal Run.");
        }
        this.identificaciones.addAll(identificaciones);

        if (CollectionUtils.nullOrEmpty(nombres)) {
            throw new IllegalArgumentException("Must have at least one name");
        }
        if(nombres.stream().filter(n->n.getUso()==Nombre.Uso.OFICIAL).count()>1){
            throw new IllegalArgumentException("Solo puede tener un nombre oficial");
        }
        if(nombres.stream().filter(n->n.getUso()==Nombre.Uso.ALIAS).count()>1){
            throw new IllegalArgumentException("Solo puede tener un alias");
        }
        if(nombres.stream().filter(n->n.getUso()==Nombre.Uso.SOCIAL).count()>1){
            throw new IllegalArgumentException("Solo puede tener un nombre social");
        }

        this.nombres.addAll(nombres);

        if (!CollectionUtils.nullOrEmpty(consentimientos)) {
            PersonaConsentimientoValidator.validarConsentimientos(consentimientos);
            this.consentimientos.addAll(consentimientos);
        }

        if(CollectionUtils.nullOrEmpty(certificaciones)) {
            throw new IllegalArgumentException("Must have at least one certificacion.");
        }

        this.certificaciones.addAll(certificaciones);

        if (CollectionUtils.nullOrEmpty(bancos)) {
            throw new IllegalArgumentException("Must have at least one Bank.");
        }
        this.bancos.addAll(bancos);

        if (nacionalidades != null && !nacionalidades.isEmpty()) {
            PersonaNacionalidadValidator.validarNacionalidades(nacionalidades);
            this.nacionalidades.addAll(nacionalidades);
        }

        if (cuentasIndividuales != null) {
            validarUnicidadCuentasPorTipo(cuentasIndividuales);
            this.cuentasIndividuales = cuentasIndividuales;
        }

        var creacionPersona = Evento.creacion(this, "system", "system");

        this.historico.add(creacionPersona);
    }

    public void setNacionalidades(List<Nacionalidad> nuevas) {
        if (nuevas == null || nuevas.isEmpty()) {
            this.nacionalidades.clear();
            return;
        }

        PersonaNacionalidadValidator.validarNacionalidades(nuevas);

        this.nacionalidades.clear();
        this.nacionalidades.addAll(nuevas);
    }

    private void validarUnicidadCuentasPorTipo(List<CuentaIndividual> cuentas) {
        long tiposUnicos = cuentas.stream()
                .map(CuentaIndividual::getTipo)
                .distinct()
                .count();
        if (tiposUnicos != cuentas.size()) {
            throw new IllegalArgumentException("No puede haber más de una cuenta individual del mismo tipo.");
        }
    }

    public List<Nacionalidad> getNacionalidades() {
        return List.copyOf(nacionalidades);
    }

    public void setConsentimientos(List<Consentimiento> nuevos) {
        this.consentimientos.clear();
        if (nuevos == null || nuevos.isEmpty()) return;

        PersonaConsentimientoValidator.validarConsentimientos(nuevos);
        this.consentimientos.addAll(nuevos);
    }

    public void agregarOReemplazarConsentimiento(Consentimiento nuevo) {
        List<Consentimiento> actualizados =
                PersonaConsentimientoValidator.agregarOReemplazar(this.consentimientos, nuevo);

        this.consentimientos.clear();
        this.consentimientos.addAll(actualizados);
    }

    public List<Consentimiento> getConsentimientos() {
        return List.copyOf(consentimientos);
    }

    public Optional<List<CuentaIndividual>> getCuentasIndividuales() {
        return Optional.ofNullable(cuentasIndividuales)
                .filter(list -> !list.isEmpty())
                .map(List::copyOf);
    }

    public Optional<String> getFechaNacimiento() {
        return Optional.ofNullable(fechaNacimiento)
                .map(fecha -> fecha.format(java.time.format.DateTimeFormatter.ISO_DATE_TIME));
    }

    public Optional<String> getFechaFallecimiento() {
        return Optional.ofNullable(fechaFallecimiento)
                .map(fecha -> fecha.format(java.time.format.DateTimeFormatter.ISO_DATE_TIME));
    }

    public Optional<EstadoCivil> getEstadoCivil() {
        return Optional.ofNullable(this.estadoCivil);
    }

    public boolean isAfiliado() {
        return sombreros.contains(Sombrero.AFILIADO);
    }

    public enum Sombrero {
        //TODO: Titular en vez de afiliado
        AFILIADO, PROSPECTO, PROVEEDOR, EMPLEADO, BENEFICIARIO, CARGA
    }
}
