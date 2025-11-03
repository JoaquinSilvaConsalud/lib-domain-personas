package cl.consalud.domain.personas.model;

import cl.consalud.domain.common.model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class PersonaTest {
    private PersonaId crearPersonaId() {
        return new PersonaId();
    }

    private List<Identificacion> crearIdentificaciones() {
        return List.of(new Run("12345678-5"));
    }

    private List<Identificacion> crearIdentificacionesSinRun() {
        return List.of(new Run("253181583"));
    }

    private List<Nombre> crearNombres() {
        return List.of(new Nombre("Juan", "Pedro", "Perez", "Soto"));
    }

    private List<Consentimiento> crearConsentimientos() {
        return List.of(new Consentimiento(Consentimiento.Tipo.MARKETING));
    }

    private List<Consentimiento> crearConsentimientosConPeriodo() {
        return List.of(new Consentimiento(Consentimiento.Tipo.MARKETING,new Periodo(ZonedDateTime.now(),null)));
    }

    private List<Certificacion> crearCertificaciones() {
        return List.of(new Certificacion(true, new Periodo(), Certificacion.Tipo.PERSONA, "Test"));
    }

    private List<Banco> crearBancos() {
        return List.of(new Banco("12456", "galicia", "Corriente"));
    }

    private List<Contacto> crearContactos() {
        return List.of(new Email("email@email.com"));
    }

    private List<CuentaIndividual> crearCuentasIndividuales() {
        return List.of(new CuentaIndividual("123", CuentaIndividual.Tipo.EXCEDENTE,
                CuentaIndividual.MedioPago.TRANSFERENCIA,
                CuentaIndividual.EstadoCuenta.VIGENTE, ZonedDateTime.now()));
    }

    private List<Nacionalidad> crearNacionalidades() {
        return List.of(Nacionalidad.CHILENA, Nacionalidad.ARGENTINA);
    }

    private Persona crearPersonaCompleta() {
        return new Persona(
                crearPersonaId(),
                crearIdentificaciones(),
                crearNombres(),
                crearConsentimientos(),
                crearCertificaciones(),
                crearBancos(),
                crearContactos(),
                List.of(),
                crearNacionalidades(),
                EstadoCivil.CASADO,
                ZonedDateTime.now(),
                null,
                crearCuentasIndividuales()
        );
    }

    private List<CuentaIndividual> crearCuentasIndividualesDuplicadas() {
        ZonedDateTime now = ZonedDateTime.now();
        return List.of(
                new CuentaIndividual("123", CuentaIndividual.Tipo.EXCEDENTE,
                        CuentaIndividual.MedioPago.TRANSFERENCIA,
                        CuentaIndividual.EstadoCuenta.VIGENTE, now),
                new CuentaIndividual("456", CuentaIndividual.Tipo.EXCEDENTE,
                        CuentaIndividual.MedioPago.VALE_VISTA,
                        CuentaIndividual.EstadoCuenta.VIGENTE, now)
        );
    }

    @Test
    @DisplayName("Debe crear Persona correctamente con datos válidos")
    void debeCrearPersonaValida() {
        Persona persona = crearPersonaCompleta();

        assertAll(
                () -> assertNotNull(persona),
                () -> assertEquals(1, persona.getIdentificaciones().size()),
                () -> assertEquals(1, persona.getNombres().size()),
                () -> assertEquals(1, persona.getConsentimientos().size()),
                () -> assertEquals(1, persona.getCertificaciones().size()),
                () -> assertEquals(1, persona.getBancos().size()),
                () -> assertEquals(1, persona.getContactos().size()),
                () -> assertEquals(1, persona.getCuentasIndividuales().orElse(List.of()).size()),
                () -> assertTrue(persona.getEstadoCivil().isPresent()),
                () -> assertEquals(EstadoCivil.CASADO, persona.getEstadoCivil().get()),
                () -> assertEquals(2, persona.getNacionalidades().size())
        );
    }

    @Test
    @DisplayName("Debe permitir setear nacionalidades válidas sin duplicados")
    void debeSetearNacionalidadesValidas() {
        Persona persona = new Persona(crearPersonaId());
        persona.setNacionalidades(List.of(Nacionalidad.CHILENA, Nacionalidad.ARGENTINA));

        assertEquals(2, persona.getNacionalidades().size());
        assertTrue(persona.getNacionalidades().contains(Nacionalidad.CHILENA));
        assertTrue(persona.getNacionalidades().contains(Nacionalidad.ARGENTINA));
    }

    @Test
    @DisplayName("Debe lanzar excepción al asignar nacionalidades duplicadas")
    void debeFallarSiHayNacionalidadesDuplicadas() {
        Persona persona = new Persona(crearPersonaId());
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> persona.setNacionalidades(List.of(Nacionalidad.CHILENA, Nacionalidad.CHILENA)));
        assertTrue(ex.getMessage().toLowerCase().contains("repetida"));
    }

    @Test
    @DisplayName("Debe permitir limpiar nacionalidades con lista vacía o null")
    void debePermitirLimpiarNacionalidades() {
        Persona persona = new Persona(crearPersonaId());
        persona.setNacionalidades(List.of(Nacionalidad.CHILENA));
        assertFalse(persona.getNacionalidades().isEmpty());

        persona.setNacionalidades(List.of());
        assertTrue(persona.getNacionalidades().isEmpty());

        persona.setNacionalidades(null);
        assertTrue(persona.getNacionalidades().isEmpty());
    }

    @Test
    @DisplayName("Debe crear Persona con evento histórico con tipo CREACION")
    void debeCrearEventoHistoricoConTipoCreacion() {
        Persona persona = crearPersonaCompleta();
        Evento<?> evento = persona.getHistorico().get(0);
        assertTrue(evento.tipo().name().contains("CREACION"));
    }

    @Test
    @DisplayName("Debe retornar Optional vacío en getters de fecha si no están seteadas")
    void debeRetornarOptionalVacioEnFechasSiNoSeteadas() {
        Persona persona = new Persona(crearPersonaId());
        assertTrue(persona.getFechaNacimiento().isEmpty());
        assertTrue(persona.getFechaFallecimiento().isEmpty());
    }

    @Test
    @DisplayName("Debe formatear fecha de nacimiento en formato ISO_DATE_TIME")
    void debeFormatearFechaNacimientoEnISO() {
        Persona persona = new Persona(crearPersonaId());
        ZonedDateTime fecha = ZonedDateTime.now();
        persona.setFechaNacimiento(fecha);

        Optional<String> fechaNacimiento = persona.getFechaNacimiento();
        assertTrue(fechaNacimiento.isPresent());
        assertTrue(fechaNacimiento.get().matches("\\d{4}-\\d{2}-\\d{2}T.*"), "Debe estar en formato ISO");
    }

    @Test
    @DisplayName("Debe mantener referencias independientes de colecciones externas")
    void debeMantenerColeccionesIndependientes() {
        List<Consentimiento> consentimientos = new ArrayList<>(crearConsentimientos());
        Persona persona = new Persona(crearPersonaId(), crearIdentificaciones(), crearNombres(),
                consentimientos, crearCertificaciones(), crearBancos(), crearContactos(),
                List.of(), crearNacionalidades(), EstadoCivil.CASADO, ZonedDateTime.now(), null, crearCuentasIndividuales());
        consentimientos.clear();
        assertEquals(1, persona.getConsentimientos().size());
    }

    @Test
    @DisplayName("Debe inicializar listas vacías al usar constructor mínimo")
    void debeInicializarListasVacias() {
        Persona persona = new Persona(crearPersonaId());
        assertAll(
                () -> assertNotNull(persona.getIdentificaciones()),
                () -> assertNotNull(persona.getNombres()),
                () -> assertNotNull(persona.getContactos()),
                () -> assertTrue(persona.getIdentificaciones().isEmpty()),
                () -> assertTrue(persona.getNombres().isEmpty())
        );
    }

    @Test
    @DisplayName("Debe permitir cambiar fecha de fallecimiento")
    void debePermitirCambiarFechaFallecimiento() {
        Persona persona = new Persona(crearPersonaId());
        ZonedDateTime fallecimiento = ZonedDateTime.now();
        persona.setFechaFallecimiento(fallecimiento);

        Optional<String> fechaFallecimiento = persona.getFechaFallecimiento();
        assertTrue(fechaFallecimiento.isPresent());
        assertTrue(fechaFallecimiento.get().contains(String.valueOf(fallecimiento.getYear())));
    }

    @Test
    @DisplayName("Debe permitir asignar y recuperar Salud")
    void debePermitirAsignarSalud() {
        Persona persona = new Persona(crearPersonaId());
        Salud salud = new Salud(
                List.of(67),
                67.0,
                1.70,
                ZonedDateTime.parse("2025-01-01T00:00:00-03:00[America/Buenos_Aires]"),
                new Salud.PensionInvalidez(true, Salud.PensionInvalidez.Causal.ENFERMEDAD_COMUN, "Diagnóstico")
        );        persona.setSalud(salud);
        assertEquals(salud, persona.getSalud());
    }

    @Test
    @DisplayName("Debe permitir crear Persona sin estado civil")
    void debePermitirCrearPersonaSinEstadoCivil() {
        Persona persona = new Persona(
                crearPersonaId(),
                crearIdentificaciones(),
                crearNombres(),
                crearConsentimientos(),
                crearCertificaciones(),
                crearBancos(),
                crearContactos(),
                List.of(),
                List.of(),
                null,
                ZonedDateTime.now(),
                null,
                crearCuentasIndividuales()
        );

        assertTrue(persona.getEstadoCivil().isEmpty());
    }

    @Test
    @DisplayName("Debe permitir crear Persona sin fecha de nacimiento")
    void debePermitirCrearPersonaSinFechaNacimiento() {
        Persona persona = new Persona(
                crearPersonaId(),
                crearIdentificaciones(),
                crearNombres(),
                crearConsentimientos(),
                crearCertificaciones(),
                crearBancos(),
                crearContactos(),
                List.of(),
                crearNacionalidades(),
                EstadoCivil.CASADO,
                null,  // fecha nacimiento null
                null,
                crearCuentasIndividuales()
        );

        assertTrue(persona.getFechaNacimiento().isEmpty());
    }

    @Test
    @DisplayName("Debe permitir crear Persona sin fecha de fallecimiento")
    void debePermitirCrearPersonaSinFechaFallecimiento() {
        Persona persona = crearPersonaCompleta(); // Ya viene con fecha fallecimiento null
        assertTrue(persona.getFechaFallecimiento().isEmpty());
    }

    @Test
    @DisplayName("Debe retornar Optional vacío cuando no hay estado civil")
    void debeRetornarOptionalVacioSinEstadoCivil() {
        Persona persona = new Persona(crearPersonaId());
        assertTrue(persona.getEstadoCivil().isEmpty());
    }

    @Test
    @DisplayName("Debe retornar Optional con valor cuando hay estado civil")
    void debeRetornarOptionalConValorConEstadoCivil() {
        Persona persona = crearPersonaCompleta();
        assertTrue(persona.getEstadoCivil().isPresent());
        assertEquals(EstadoCivil.CASADO, persona.getEstadoCivil().get());
    }

    @Test
    @DisplayName("Debe tener sombrero vacío al crear nueva Persona")
    void debeTenerSombreroVacioInicialmente() {
        Persona persona = new Persona(crearPersonaId());
        assertTrue(persona.getSombreros().isEmpty());
    }

    @Test
    @DisplayName("Debe inicializar empleos como lista vacía")
    void debeInicializarEmpleosVacio() {
        Persona persona = new Persona(crearPersonaId());
        assertNotNull(persona.getEmpleos());
        assertTrue(persona.getEmpleos().isEmpty());
    }
    @Test
    @DisplayName("Debe fallar al crear Persona sin contactos")
    void falloSinContactos() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                new Persona(crearPersonaId(), crearIdentificaciones(), crearNombres(),
                        crearConsentimientos(), crearCertificaciones(), crearBancos(),
                        new ArrayList<>(), List.of(), crearNacionalidades(), EstadoCivil.CASADO, ZonedDateTime.now(), null,
                        crearCuentasIndividuales())
        );
        assertEquals("Must have at least one contact information.", ex.getMessage());
    }

    @Test
    @DisplayName("Debe fallar al crear Persona sin identificaciones")
    void falloSinIdentificaciones() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                new Persona(crearPersonaId(), new ArrayList<>(), crearNombres(),
                        crearConsentimientos(), crearCertificaciones(), crearBancos(),
                        crearContactos(), List.of(), crearNacionalidades(), EstadoCivil.CASADO, ZonedDateTime.now(), null,
                        crearCuentasIndividuales())
        );
        assertEquals("Must have at least one identification.", ex.getMessage());
    }

    @Test
    @DisplayName("Debe fallar al crear Persona sin nombres")
    void falloSinNombres() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                new Persona(crearPersonaId(), crearIdentificaciones(), new ArrayList<>(),
                        crearConsentimientos(), crearCertificaciones(), crearBancos(),
                        crearContactos(), List.of(), crearNacionalidades(), EstadoCivil.CASADO, ZonedDateTime.now(), null,
                        crearCuentasIndividuales())
        );
        assertEquals("Must have at least one name", ex.getMessage());
    }

    @Test
    @DisplayName("Debe fallar al crear Persona sin consentimientos")
    void falloSinConsentimientos() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                new Persona(crearPersonaId(), crearIdentificaciones(), crearNombres(),
                        new ArrayList<>(), crearCertificaciones(), crearBancos(),
                        crearContactos(), List.of(), crearNacionalidades(), EstadoCivil.CASADO, ZonedDateTime.now(), null,
                        crearCuentasIndividuales())
        );
        assertEquals("Must have at least one consent.", ex.getMessage());
    }

    @Test
    @DisplayName("Debe fallar al crear Persona sin certificaciones")
    void falloSinCertificaciones() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                new Persona(crearPersonaId(), crearIdentificaciones(), crearNombres(),
                        crearConsentimientos(), new ArrayList<>(), crearBancos(),
                        crearContactos(), List.of(), crearNacionalidades(), EstadoCivil.CASADO, ZonedDateTime.now(), null,
                        crearCuentasIndividuales())
        );
        assertEquals("Must have at least one certificacion.", ex.getMessage());
    }

    @Test
    @DisplayName("Debe fallar al crear Persona sin bancos")
    void falloSinBancos() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                new Persona(crearPersonaId(), crearIdentificaciones(), crearNombres(),
                        crearConsentimientos(), crearCertificaciones(), new ArrayList<>(),
                        crearContactos(), List.of(), crearNacionalidades(), EstadoCivil.CASADO, ZonedDateTime.now(), null,
                        crearCuentasIndividuales())
        );
        assertEquals("Must have at least one Bank.", ex.getMessage());
    }

    @Test
    @DisplayName("Debe crear Persona correctamente con colecciones copiadas y evento histórico")
    void creacionValida() {
        List<Identificacion> idens = new ArrayList<>(crearIdentificaciones());
        List<Nombre> nombres = new ArrayList<>(crearNombres());
        Persona persona = new Persona(crearPersonaId(), idens, nombres,
                crearConsentimientos(), crearCertificaciones(), crearBancos(),
                crearContactos(), List.of(), crearNacionalidades(), EstadoCivil.CASADO, ZonedDateTime.now(), null,
                crearCuentasIndividuales());

        idens.clear();
        nombres.clear();

        assertEquals(1, persona.getIdentificaciones().size());
        assertEquals(1, persona.getNombres().size());
        assertNotNull(persona.getHistorico());
        assertEquals(1, persona.getHistorico().size());
        assertTrue(persona.getHistorico().get(0).tipo().name().contains("CREACION"));
    }

    @Test
    @DisplayName("Debe lanzar excepción al construir con cuentas individuales de tipo duplicado")
    void debeLanzarExcepcionAlConstruirConCuentasIndividualesDuplicadas() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                new Persona(
                        crearPersonaId(),
                        crearIdentificaciones(),
                        crearNombres(),
                        crearConsentimientos(),
                        crearCertificaciones(),
                        crearBancos(),
                        crearContactos(),
                        List.of(),
                        crearNacionalidades(),
                        EstadoCivil.CASADO,
                        ZonedDateTime.now(),
                        null,
                        crearCuentasIndividualesDuplicadas()
                )
        );

        assertTrue(ex.getMessage().contains("No puede haber más de una cuenta individual del mismo tipo."));
    }

    @Test
    @DisplayName("Debe construir Persona con 'cuentasIndividuales' null y getter debe retornar Optional vacío")
    void debeConstruirConCuentasIndividualesNullYGetterRetornaEmpty() {
        Persona persona = new Persona(
                crearPersonaId(),
                crearIdentificaciones(),
                crearNombres(),
                crearConsentimientos(),
                crearCertificaciones(),
                crearBancos(),
                crearContactos(),
                List.of(),
                crearNacionalidades(),
                EstadoCivil.CASADO,
                ZonedDateTime.now(),
                null,
                null
        );

        assertNotNull(persona);
        assertTrue(persona.getCuentasIndividuales().isEmpty(), "El getter debe retornar Optional.empty si la lista interna es null");
    }

    @Test
    @DisplayName("getCuentasIndividuales debe retornar una lista inmutable")
    void debeRetornarListaInmutableDeCuentasIndividuales() {
        Persona persona = crearPersonaCompleta();

        Optional<List<CuentaIndividual>> cuentasOpt = persona.getCuentasIndividuales();
        assertTrue(cuentasOpt.isPresent());

        List<CuentaIndividual> cuentas = cuentasOpt.get();

        assertThrows(UnsupportedOperationException.class, () -> {
            cuentas.add(new CuentaIndividual("999", CuentaIndividual.Tipo.EXCESO,
                    CuentaIndividual.MedioPago.VALE_VISTA,
                    CuentaIndividual.EstadoCuenta.VIGENTE, ZonedDateTime.now()));
        }, "La lista retornada por getCuentasIndividuales no debe ser modificable");
    }

}
