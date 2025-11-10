package cl.consalud.domain.personas.utils.test;

import cl.consalud.domain.common.model.*;
import cl.consalud.domain.personas.model.*;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import net.datafaker.Faker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

import cl.consalud.domain.personas.validation.PersonaNacionalidadValidator;

public class PersonaGenerator {

    private static final Faker faker = new Faker();
    private static final ChileFaker chile = new ChileFaker();
    private static final Random random = new Random();

    public static Persona makeValidPersona(int qtyNames) {

        var nombres = new ArrayList<Nombre>();
        var sexo = Sexo.values()[faker.random().nextInt(2)];
        var estadoCivil = EstadoCivil.values()[faker.random().nextInt(EstadoCivil.values().length)];

        if (qtyNames == 0) qtyNames = 1;

        for (int i = 0; i < qtyNames; i++) {
            if (i == 0) {
                nombres.add(makeValidName(Nombre.Uso.OFICIAL,
                        null, null, null, null, sexo.ordinal()));
            } else {
                nombres.add(makeValidName(Nombre.Uso.ALIAS,
                        1, false, null, null, sexo.ordinal()));
            }

        }

        var consentimientos = new ArrayList<Consentimiento>();
        var bancos = new ArrayList<Banco>();

        var tiposUsados = new HashSet<Consentimiento.Tipo>();
        while (consentimientos.size() < random.nextInt(1, 4)) {
            var nuevo = makeValidConsentimiento();
            if (tiposUsados.add(nuevo.getTipo())) {
                consentimientos.add(nuevo);
            }
        }

        var certificaciones = new ArrayList<Certificacion>();
        for (int i = 0; i < random.nextInt(1, 3); i++) {
            certificaciones.add(makeValidCertificacion());
        }

        bancos.add(makeValidBanco(true));
        for (int i = 0; i < random.nextInt(1, 4); i++) {
            bancos.add(makeValidBanco(false));
        }

        var empleos = new ArrayList<Empleo>();
        for (int i = 0; i < random.nextInt(1, 3); i++) {
            empleos.add(makeValidEmpleo());
        }

        var cuentasIndividuales = new ArrayList<CuentaIndividual>();
        CuentaIndividual nueva = makeValidCuentaIndividual();
        cuentasIndividuales.add(nueva);

        var contactos = new ArrayList<Contacto>();
        var primNom = nombres.getFirst();
        String nombre = primNom.getNombres().getFirst();

        contactos.add(new Email(faker.internet().emailAddress()));
        contactos.add(new Telefono(faker.phoneNumber().phoneNumberInternational()));

        var nacionalidades = makeValidNacionalidades();

        var persona = new Persona(
                new PersonaId(),
                List.of(RutGenerator.generarRutAleatorio()),
                nombres,
                consentimientos,
                certificaciones,
                bancos,
                contactos,
                empleos,
                nacionalidades,
                estadoCivil,
                faker.timeAndDate().past().atZone(ZoneId.systemDefault()),
                faker.timeAndDate().past().atZone(ZoneId.systemDefault()),
                cuentasIndividuales
        );


        persona.getDirecciones().add(makeDireccion());
        persona.setSexo(sexo);

        //Validar nacionalidades
        PersonaNacionalidadValidator.validarNacionalidades(persona.getNacionalidades());

        return persona;
    }

    public static Direccion makeDireccion() {

        return new Direccion("Metropolitana",
                faker.eldenRing().location(),
                faker.address().streetName(),
                faker.address().streetAddress());
    }

    public static Consentimiento makeValidConsentimiento() {
        // Selecciona un tipo aleatorio (MARKETING, TERCEROS, etc.)
        var tipos = Consentimiento.Tipo.values();
        var tipo = tipos[random.nextInt(tipos.length)];

        // Genera un período válido (inicio hoy, fin dentro de 1 año)
        var inicio = ZonedDateTime.now().minusDays(random.nextInt(0, 30));
        var fin = inicio.plusMonths(random.nextInt(6, 24));
        var periodo = new Periodo(inicio, fin);

        // Crea el consentimiento y define todos los campos requeridos
        var consentimiento = new Consentimiento(tipo, periodo);
        consentimiento.setEstado(random.nextBoolean());
        consentimiento.setVersion("v" + faker.number().digit());
        consentimiento.setMedio(faker.options().option("WEB", "APP", "CALLCENTER", "PRESENCIAL"));

        return consentimiento;
    }

    public static Banco makeValidBanco(boolean esPreferida) {
        String numeroCuenta = generateAccountNumber();
        String nombreBanco = faker.company().name();
        String tipoCuenta = faker.options().option("Ahorros", "Corriente", "Plazo Fijo");

        return new Banco(numeroCuenta, nombreBanco, tipoCuenta, esPreferida);
    }

    public static String generateAccountNumber() {
        // Genera un número de cuenta aleatorio, por ejemplo, un número de 10 dígitos
        return String.valueOf(1000000000 + random.nextInt(900000000));
    }

    public static Nombre makeValidName(@Nullable Nombre.Uso uso,
                                @Nullable Integer qtyNames, @Nullable Boolean lastNames,
                                @Nullable Boolean preffixes, @Nullable Boolean suffixes,
                                @Nullable Integer sexo) {

        if (qtyNames == null) qtyNames = 2;
        if (lastNames == null) lastNames = true;
        if (preffixes == null) preffixes = false;
        if (suffixes == null) suffixes = false;

        var patMat = List.of(Nombre.TipoApellido.PATERNO, Nombre.TipoApellido.MATERNO);
        var pref = new ArrayList<>(List.of("Dr.", "Prof.", "Ing.", "Lic."));
        var suf = new ArrayList<>(List.of("Phd.", "DDr.", "DDr."));


        var nombres = new ArrayList<String>();
        var apellidos = new ArrayList<Nombre.Apellido>();
        var prefijos = new ArrayList<String>();
        var sufijos = new ArrayList<String>();

        for (int i = 0; i < qtyNames; i++) {
            if (sexo != null && sexo == 0) {
                nombres.add(faker.name().maleFirstName());
            } else if (sexo != null && sexo == 1) {
                nombres.add(faker.name().femaleFirstName());
            } else {
                nombres.add(faker.name().firstName());
            }
        }

        if (lastNames)
            for (int i = 0; i < 2; i++)
                apellidos.add(new Nombre.Apellido(faker.name().lastName(), patMat.get(i), i));

        if (preffixes)
            for (int i = 0; i < random.nextInt(pref.size()); i++)
                prefijos.add(pref.remove(random.nextInt(pref.size())));
        if (suffixes)
            for (int i = 0; i < random.nextInt(suf.size()); i++)
                sufijos.add(suf.remove(random.nextInt(suf.size())));

        return new Nombre(uso, nombres, apellidos, prefijos, sufijos);
    }

    public static Certificacion makeValidCertificacion() {

        var inicio = faker.timeAndDate().past().atZone(ZoneId.systemDefault());
        var fin = inicio.plusYears(random.nextInt(1, 3));

        var periodo = new Periodo(inicio, fin);

        var tipo = Certificacion.Tipo.values()[random.nextInt(Certificacion.Tipo.values().length)];

        boolean estado = random.nextInt(100) > 15; // 85% probabilidad de true

        String nombre = estado
                ? faker.options().option("Equifax", "Registro Civil", "Ejecutivo Juan Pérez", "Gobierno de Chile", "SuperSalud")
                : null;

        return Certificacion.of(estado, periodo, tipo, nombre);
    }

    public static Empleo makeValidEmpleo() {
        String empleador = faker.company().name();
        Identificacion identificacion = RutGenerator.generarRutAleatorio();
        List<Direccion> direcciones = new ArrayList<>();
        direcciones.add(makeDireccion());

        Double rentaImponible = faker.number().randomDouble(2, 400000, 5000000); // Entre 400.000 y 5.000.000

        Empleo.TipoTrabajador tipoTrabajador = Empleo.TipoTrabajador.values()[
                random.nextInt(Empleo.TipoTrabajador.values().length)
                ];

        return new Empleo(
                empleador,
                identificacion,
                direcciones,
                rentaImponible,
                tipoTrabajador
        );
    }

    public static CuentaIndividual makeValidCuentaIndividual() {
        String idCuenta = generateAccountNumber();
        CuentaIndividual.Tipo tipo = CuentaIndividual.Tipo.values()[random.nextInt(CuentaIndividual.Tipo.values().length)];
        CuentaIndividual.MedioPago medioPago = CuentaIndividual.MedioPago.values()[random.nextInt(CuentaIndividual.MedioPago.values().length)];
        CuentaIndividual.EstadoCuenta estadoCuenta = CuentaIndividual.EstadoCuenta.values()[random.nextInt(CuentaIndividual.EstadoCuenta.values().length)];
        ZonedDateTime fechaCompromiso = faker.timeAndDate().future().atZone(ZoneId.systemDefault());
        return new CuentaIndividual(idCuenta, tipo, medioPago, estadoCuenta, fechaCompromiso);
    }

    public static void writeToFile(@Nonnull Persona persona, @Nullable String path) {

        var basePath = "src/test/resources/";
        path = basePath + Objects.requireNonNullElse(path, "personas");

        try {
            Files.createDirectories(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException("No se pudo crear el directorio destino: " + "personas", e);
        }

        try {
            final StringBuilder sb = new StringBuilder();
            persona.getNombres().stream().findFirst()
                   .ifPresent(n -> sb.append(n.getTexto().replaceAll("[^a-zA-Z0-9]", "_")));

            Path file = Path.of(path, sb + ".json");

            var mapper = new ObjectMapper().registerModule(new JavaTimeModule());

            mapper = mapper.configure(JsonParser.Feature.IGNORE_UNDEFINED, true)
                           .setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL);

            mapper.setVisibility(mapper.getSerializationConfig().getDefaultVisibilityChecker()
                                       .withFieldVisibility(JsonAutoDetect.Visibility.ANY));

            Files.writeString(file, mapper
                    .writerWithDefaultPrettyPrinter()
                    .with(JsonGenerator.Feature.IGNORE_UNKNOWN)
                    .writeValueAsString(persona));

            System.out.println("Archivo escrito: " + file.toAbsolutePath());

        } catch (Exception e) {
            System.err.println("Error escribiendo archivo para: " + persona + " -> " + e.getMessage());
        }

    }

    public static List<Nacionalidad> makeValidNacionalidades() {
        var list = new ArrayList<Nacionalidad>();
        var values = Nacionalidad.values();
        int count = random.nextInt(1, 3); // entre 1 y 2 nacionalidades

        while (list.size() < count) {
            var candidate = values[random.nextInt(values.length)];
            if (candidate != Nacionalidad.SIN_INFORMACION && !list.contains(candidate)) {
                list.add(candidate);
            }
        }

        if (list.isEmpty()) {
            list.add(Nacionalidad.CHILENA);
        }

        return list;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            try {
                writeToFile(makeValidPersona(3), null);
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
