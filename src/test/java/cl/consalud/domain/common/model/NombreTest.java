package cl.consalud.domain.common.model;

import cl.consalud.domain.common.exception.NameValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static cl.consalud.domain.common.tools.NombreExamples.*;
import static org.junit.jupiter.api.Assertions.*;

class NombreTest {


    List<String> nombresValidos = List.of("Isaac", "Albert");

    List<Nombre.Apellido> apellidosValidos = List.of(
            new Nombre.Apellido("Schrodinger", Nombre.TipoApellido.PATERNO, 0),
            new Nombre.Apellido("Heisenberg", Nombre.TipoApellido.MATERNO, 1));

    List<String> nombresVacios = List.of("", "", "");

    List<Nombre.Apellido> apellidosVacios = List.of(
            new Nombre.Apellido("", Nombre.TipoApellido.PATERNO, 0),
            new Nombre.Apellido("", Nombre.TipoApellido.FAMILIAR, 1)
    );

    @Test
    @DisplayName("Los constructores deben permitir crear nombres con todos los datos correctos")
    void createNombre() {

        var nombres = List.of("Hector", "Hafthor");
        var nombre = List.of("Hector");
        var apellidos = List.of(
                new Nombre.Apellido("Tazo", Nombre.TipoApellido.PATERNO, 0),
                new Nombre.Apellido("Tilla", Nombre.TipoApellido.MATERNO, 1)
        );

        var prefijos = List.of("Dr.");
        var sufijos = List.of("PhD");
        var pronombres = List.of(Nombre.Pronombre.EL);

        Assertions.assertDoesNotThrow(() -> new Nombre());

        Assertions.assertDoesNotThrow(() -> new Nombre("Hector", "Hafthor", "Tazo", "Tilla"));

        Assertions.assertDoesNotThrow(() -> new Nombre(nombres, apellidos));

        Assertions.assertDoesNotThrow(() -> new Nombre(Nombre.Uso.OFICIAL, nombres, apellidos));

        Assertions.assertDoesNotThrow(() -> new Nombre(Nombre.Uso.ALIAS, nombre, apellidosVacios));

        Assertions.assertDoesNotThrow(() -> new Nombre(Nombre.Uso.SOCIAL, nombre, apellidosVacios,
                prefijos, null));

        Assertions.assertDoesNotThrow(() -> new Nombre(Nombre.Uso.OFICIAL, nombre, apellidos,
                prefijos, sufijos, new Periodo()));

        Assertions.assertDoesNotThrow(() -> new Nombre(Nombre.Uso.OFICIAL, nombre, apellidos,
                prefijos, sufijos, null));

        Assertions.assertDoesNotThrow(() -> new Nombre(null, nombre, apellidos,
                prefijos, sufijos, null));

        Assertions.assertDoesNotThrow(() -> new Nombre(null, nombre, apellidos,
                prefijos, sufijos, new Periodo(), "Hector Tazo", "Hector Tilla", false,pronombres));


    }

    @Test
    @DisplayName("El constructor deja el nombre en un estado valido")
    void correctConstructor() {

        var nombres = List.of("Hector", "Hafthor");
        var nombre = List.of("Hector");
        var apellidos = List.of(
                new Nombre.Apellido("Tazo", Nombre.TipoApellido.PATERNO, 0),
                new Nombre.Apellido("Tilla", Nombre.TipoApellido.MATERNO, 1)
        );

        var prefijos = List.of("Dr.");
        var sufijos = List.of("PhD");
        var uso = Nombre.Uso.ALIAS;
        var periodo = new Periodo();
        var texto = "Hector Hafthor Tazo Tilla";
        var textoCompleto = "Dr. Hector Hafthor Tazo Tilla, PhD";
        var textoOriginal = "Doctor Tilla";

        var invertido = false;
        var pronombres = List.of(Nombre.Pronombre.EL);

        var name1 = new Nombre(nombres, apellidos);
        assertConstruction(name1, Nombre.Uso.OFICIAL, nombres, apellidos, null,
                null, null, texto, texto, invertido,null);

        var name2 = new Nombre(uso, nombres, apellidos);
        assertConstruction(name2, uso, nombres, apellidos,null, null, null, texto, texto, invertido,null);

        var name3 = new Nombre(uso, nombres, apellidos, prefijos, sufijos);
        assertConstruction(name3, uso, nombres, apellidos, prefijos, sufijos, null, textoCompleto, textoCompleto, invertido,null);

        var name4 = new Nombre(uso, nombres, apellidos, prefijos, sufijos, periodo);
        assertConstruction(name4, uso, nombres, apellidos, prefijos, sufijos, periodo, textoCompleto, textoCompleto, invertido,null);

        var name5 = new Nombre(uso, nombre, apellidos, prefijos, sufijos, periodo, texto, textoOriginal, invertido,pronombres);
        assertConstruction(name5, uso, nombre, apellidos, prefijos, sufijos, periodo, texto, textoOriginal, invertido,pronombres);

        var name6 = new Nombre(null, nombre, apellidos, prefijos, sufijos, null, texto, textoOriginal, invertido,pronombres);
        assertConstruction(name6, Nombre.Uso.OFICIAL, nombre, apellidos, prefijos, sufijos, null, texto, textoOriginal, invertido,pronombres);

    }

    void assertConstruction(Nombre name, Nombre.Uso uso, List<String> nombres, List<Nombre.Apellido> apellidos, List<String> prefijos,
                            List<String> sufijos, Periodo periodo, String texto, String textoOriginal, Boolean invertido,List<Nombre.Pronombre> pronombres) {


        assertNotNull(name);
        assertNotNull(name.nombres);
        assertNotNull(name.apellidos);
        assertNotNull(name.texto);

        assertEquals(uso, name.uso);
        assertEquals(nombres, name.nombres);
        assertEquals(apellidos, name.apellidos);
        assertEquals(prefijos, name.prefijos);
        assertEquals(sufijos, name.sufijos);
        assertEquals(texto, name.texto);
        assertEquals(invertido, name.invertido);
        assertEquals(pronombres,name.pronombres);

        if (periodo != null) {
            assertEquals(periodo, name.periodo);
        }

    }

    @Test
    @DisplayName("El constructor debe fallar si todos los argumentos son nulos")
    void failOnNulls() {
        assertThrows(NameValidationException.class,
                () -> new Nombre(null, null, null, null, null, null));
    }

    @Test
    @DisplayName("El constructor y el setter deben fallar si no se indican al menos nombres y apellidos")
    void failOnIncompleteData() {
        assertThrows(NameValidationException.class,
                () -> new Nombre(null, List.of(), null, null, null, null));

        assertThrows(NameValidationException.class,
                () -> new Nombre(null, null, List.of(), null, null, null));

        assertThrows(NameValidationException.class,
                () -> new Nombre(null, nombresValidos, List.of(), null, null, null));

        assertThrows(NameValidationException.class,
                () -> new Nombre(null, nombresValidos, null, null, null, null));
    }

    @Test
    @DisplayName("El constructor y el setter deben fallar si todos los nombres carecen de texto")
    void failOnEmptyNames() {
        assertThrows(NameValidationException.class,
                () -> new Nombre(Nombre.Uso.OFICIAL, nombresVacios, apellidosValidos, null, null, null));
        assertThrows(NameValidationException.class,
                () -> oluwaseun.setNombres(nombresVacios));
    }

    @Test
    @DisplayName("El constructor debe fallar si el uso es oficial y todos los apellidos carecen de texto")
    void failOnEmptySurnames() {

        assertThrows(NameValidationException.class,
                () -> new Nombre(null, nombresValidos, apellidosVacios));

        assertThrows(NameValidationException.class,
                () -> new Nombre(Nombre.Uso.OFICIAL, nombresValidos, apellidosVacios));

        assertThrows(NameValidationException.class,
                () -> taro.setApellidos(apellidosVacios));
    }

    @Test
    @DisplayName("Si no se indica uso el valor por defecto es OFICIAL")
    void setDefaultUso() {
        assertEquals(Nombre.Uso.OFICIAL, new Nombre(nombresValidos, apellidosValidos).uso);
        assertEquals(Nombre.Uso.OFICIAL, new Nombre(null, nombresValidos, apellidosValidos).uso);
    }

    @Test
    @DisplayName("Si no se indica periodo el valor por defecto es un nuevo periodo")
    void setDefaultPeriodo() {

        assertNotNull(new Nombre(Nombre.Uso.OFICIAL, nombresValidos, apellidosValidos,null, null, new Periodo()).periodo);

    }

    @Test
    @DisplayName("setTexto() debe cambiar la representación textual del nombre sin cambiar otra cosa.")
    void setTexto() {

        var name = fatimah.copy();
        name.setTexto("Fatima");

        assertEquals("Fatima", name.texto);

    }

    @Test
    @DisplayName("La forma textual del nombre debe reajustarse después de cada cambio de nombres, apellidos, prefijos o sufijos")
    void setTextoAuto() {
        var name = fatimah.copy();

        name.setNombres("Yadira", "Zafiro");

        assertEquals("Yadira Zafiro bint Khalid al-Mansuri", name.texto);

        name.setApellidos("bint Salman", "al-Wahid");

        assertEquals("Yadira Zafiro bint Salman al-Wahid", name.texto);

        name.setPrefijos(List.of("Dr."));

        assertEquals("Dr. Yadira Zafiro bint Salman al-Wahid", name.texto);

        name.setSufijos("OBE");

        assertEquals("Dr. Yadira Zafiro bint Salman al-Wahid, OBE", name.texto);

        name.setSufijos(List.of("OBE", "PhD"));

        assertEquals("Dr. Yadira Zafiro bint Salman al-Wahid, OBE, PhD", name.texto);
    }

    @Test
    @DisplayName("getNombresAsString() debe devolver los nombres como una sola String con los nombres separados por espacios y sin espacios adicionales")
    void getNombresAsString() {
        assertEquals("Ivan Sergeevich", ivan.getNombresAsString());
    }

    @Test
    @DisplayName("getApellidosAsString() debe devolver los apellidos como una sola String con los apellidos separados por espacios, " +
            "sin espacios adicionales y en el orden correcto")
    void getApellidosAsString() {
        assertEquals("Petrov-Sidorov", ivan.getApellidosAsString());
    }

    @Test
    @DisplayName("getPrefijosAsString() debe devolver los prefijos como una sola String con los prefijos separadospor espacios y sin espacios adicionales")
    void getPrefijosAsString() {
        assertEquals("Sir", jonathan.getPrefijosAsString());
    }

    @Test
    @DisplayName("getPrefijosAsString() debe devolver los prefijos como una sola String con los prefijos separados por coma y espacio, sin espacios adicionales")
    void getSufijosAsString() {
        assertEquals("OBE, PhD", jonathan.getSufijosAsString());
    }

    @Test
    @DisplayName("getApellidosAsList() debe devolver los apellidos como una lista de Strings")
    void getApellidosAsList() {
        assertEquals(List.of("da Silva", "Sousa"), jose.getApellidosAsList());

    }

    @Test
    @DisplayName("setNombres() debe permitir usar un arreglo o una lista de strings, y debe ajustar el texto")
    void setNombres() {

        var name = suchada.copy();

        name.setNombres("Saurabh", "Arjun");

        assertEquals(List.of("Saurabh", "Arjun"), name.nombres);
        assertEquals("Saurabh Arjun Phakdee", name.texto);

        name.setNombres(List.of("Vishnu", "Devanand"));

        assertEquals(List.of("Vishnu", "Devanand"), name.nombres);
        assertEquals("Vishnu Devanand Phakdee", name.texto);

    }

    @Test
    @DisplayName("Internal setters should not touch the text on false, and should on true")
    void internalSetters() {

        var name = ivan.copy();
        name.texto = "IVAN EL TERRIBLE";

        name.setNombres(List.of("Ivan"), false);
        name.setApellidos(List.of(new Nombre.Apellido("Vasilyevich", Nombre.TipoApellido.PATERNO, 0)), false);
        name.setPrefijos(List.of("Zar"), false);
        name.setSufijos(List.of("IV"), false);

        assertEquals("IVAN EL TERRIBLE", name.texto);

        name.setNombres(List.of("Ivan"), true);
        name.setApellidos(List.of(new Nombre.Apellido("Vasilyevich", Nombre.TipoApellido.PATERNO, 0)), true);
        name.setPrefijos(List.of("Zar"), true);
        name.setSufijos(List.of("IV"), true);

        assertEquals("Zar Ivan Vasilyevich, IV", name.texto);
    }


    @Test
    @DisplayName("setNombres() debe validar excesivos nombres (mas de 12), longitud (max 45), nulidad y blanks")
    void setNombreFail() {
        var name = priyanka.copy();

        var suficientes = new String[]{"Adolph", "Blaine", "Charles", "David", "Earl", "Frederick", "Gerald", "Hubert", "Irvin", "John", "Kenneth", "Lloyd"};
        var suficientesList = List.of("Adolph", "Blaine", "Charles", "David", "Earl", "Frederick", "Gerald", "Hubert", "Irvin", "John", "Kenneth", "Lloyd");

        var muchosNombres = new String[]{"Adolph", "Blaine", "Charles", "David", "Earl", "Frederick", "Gerald", "Hubert", "Irvin", "John", "Kenneth", "Lloyd", "Martin"};
        var muchosNombresList = List.of("Adolph", "Blaine", "Charles", "David", "Earl", "Frederick", "Gerald", "Hubert", "Irvin", "John", "Kenneth", "Lloyd", "Martin");

        var nombreJusto = "AAAAABBBBBCCCCCDDDDDEEEEEFFFFFGGGGGHHHHH";
        var nombreLargo = "AAAAABBBBBCCCCCDDDDDEEEEEFFFFFGGGGGHHHHHI";

        assertDoesNotThrow(() -> name.setNombres(suficientes));
        assertDoesNotThrow(() -> name.setNombres(suficientesList));


        assertThrows(NameValidationException.class, () -> name.setNombres(muchosNombres));
        assertThrows(NameValidationException.class, () -> name.setNombres(muchosNombresList));

        assertDoesNotThrow(() -> name.setNombres(nombreJusto));
        assertDoesNotThrow(() -> name.setNombres(List.of(nombreJusto)));

        assertThrows(NameValidationException.class, () -> name.setNombres(nombreLargo));
        assertThrows(NameValidationException.class, () -> name.setNombres(List.of(nombreLargo)));

        assertThrows(NameValidationException.class, () -> name.setNombres("", "   ", "\n", "\t"));
        assertThrows(NameValidationException.class, () -> name.setNombres(List.of("", "   ", "\n", "\t")));

    }

    @Test
    @DisplayName("setApellidos() debe validar excesivos apellidos (mas de 8), longitud (max 60), nulidad y blanks")
    void setApellidoFail() {

        var name = priyanka.copy();
        var muchosNombres = new String[]{"Adolph", "Blaine", "Charles", "David", "Earl", "Frederick", "Gerald", "Hubert", "Irvin"};
        var suficientesNombres = new String[]{"Adolph", "Blaine", "Charles", "David", "Earl", "Frederick", "Gerald", "Hubert"};
        var apellidoLargo = "AAAAAAAAAABBBBBBBBBBCCCCCCCCCCDDDDDDDDDDEEEEEEEEEEFFFFFFFFFFG";
        var apellidoJusto = "AAAAAAAAAABBBBBBBBBBCCCCCCCCCCDDDDDDDDDDEEEEEEEEEEFFFFFFFFFF";

        List<Nombre.Apellido> muchosApellidos = new ArrayList<>();
        List<Nombre.Apellido> suficientesApellidos = new ArrayList<>();
        for (int i = 0; i < muchosNombres.length; i++) {
            var a = new Nombre.Apellido(muchosNombres[i], Nombre.TipoApellido.FAMILIAR, i);
            muchosApellidos.add(a);
            if(i < muchosNombres.length - 1) suficientesApellidos.add(a);
        }

        ArrayList<Nombre.Apellido> apellidosBlancos = new ArrayList<>();
        var blancos = new String[] {"", "   ", "\n", "\t"};
        for (int i = 0; i < blancos.length; i++) {
            apellidosBlancos.add(new Nombre.Apellido(blancos[i], Nombre.TipoApellido.FAMILIAR, i));
        }

        var apellidoLargoR = new Nombre.Apellido(apellidoLargo, Nombre.TipoApellido.PATERNO, 0);
        var apellidoJustoR = new Nombre.Apellido(apellidoJusto, Nombre.TipoApellido.PATERNO, 0);

        assertThrows(NameValidationException.class, () -> name.setApellidos(muchosNombres));
        assertThrows(NameValidationException.class, () -> name.setApellidos(muchosApellidos));
        assertDoesNotThrow(() -> name.setApellidos(suficientesNombres));
        assertDoesNotThrow(() -> name.setApellidos(suficientesApellidos));

        assertDoesNotThrow(() -> name.setApellidos(apellidoJusto));
        assertThrows(NameValidationException.class, () -> name.setApellidos(apellidoLargo));


        assertThrows(NameValidationException.class, () -> name.setApellidos(List.of(apellidoLargoR)));
        assertDoesNotThrow(() -> name.setApellidos(List.of(apellidoJustoR)));

        assertThrows(NameValidationException.class, () -> name.setApellidos(blancos));
        assertThrows(NameValidationException.class, () -> name.setApellidos(apellidosBlancos));


        List<Nombre.Apellido> sinApellidos = new ArrayList<>();
        name.setUso(Nombre.Uso.OFICIAL);
        assertThrows(NameValidationException.class, () -> name.setApellidos(sinApellidos));

        name.setUso(Nombre.Uso.ALIAS);
        assertDoesNotThrow(() -> name.setApellidos(sinApellidos));
    }

    @Test
    @DisplayName("setApellidos debe permitir usar un arreglo de strings o una lista de Apellido, y debe ajustar el texto")
    void setApellidos() {

        var name = johann.copy();

        name.setApellidos("Schopenhauer");

        assertEquals(List.of(new Nombre.Apellido("Schopenhauer", Nombre.TipoApellido.FAMILIAR, 0)), name.apellidos);

        name.setApellidos(new Nombre.Apellido("Einstein", Nombre.TipoApellido.PATERNO, 0));

        assertEquals(List.of(new Nombre.Apellido("Einstein", Nombre.TipoApellido.PATERNO, 0)), name.apellidos);

        var apellidos = List.of(
                new Nombre.Apellido("van Beethoven", Nombre.TipoApellido.PATERNO, 0),
                new Nombre.Apellido("Kaverich", Nombre.TipoApellido.MATERNO, 1)
        );
        name.setApellidos(apellidos);

        assertEquals(apellidos, name.apellidos);
        assertEquals("van Beethoven Kaverich", name.getApellidosAsString());
        assertEquals("Dr. Prof. Herr Johann Friedrich van Beethoven Kaverich", name.texto);
    }

    @Test
    @DisplayName("setPrefijos() debe permitir usar un arreglo o una lista de Strings, y debe ajustar el texto")
    void setPrefijos() {
        var name = johann.copy();
        name.setPrefijos("Sir");

        assertEquals(List.of("Sir"), name.prefijos);
        assertEquals("Sir Johann Friedrich Müller-Schneider", name.texto);

        name.setPrefijos(List.of());
        assertEquals(List.of(), name.prefijos);
        assertEquals("Johann Friedrich Müller-Schneider", name.texto);
    }

    @Test
    @DisplayName("setPrefijos() debe validar cantidad (maximo 5), longitud(maximo 10) y nulidad")
    void prefijosValid() {
        var name = johann.copy();

        assertThrows(NullPointerException.class, () -> name.setPrefijos((String) null));
        assertDoesNotThrow(() -> name.setPrefijos((List<String>) null));

        assertDoesNotThrow(() -> name.setPrefijos("1", "2", "3", "4", "5"));
        assertDoesNotThrow(() -> name.setPrefijos("12345", "1234", "123", "12", "1"));

        assertThrows(NameValidationException.class, () -> name.setPrefijos("1", "2", "3", "4", "5", "6"));
        assertThrows(NameValidationException.class, () -> name.setPrefijos(List.of("1", "2", "3", "4", "5", "6")));
        assertThrows(NameValidationException.class, () -> name.setPrefijos(List.of("prefijoMuyLargo")));
    }

    @Test
    @DisplayName("setSufijos() debe permitir usar un arreglo o una lista de Strings, y debe ajustar el texto")
    void setSufijos() {

        var name = jonathan.copy();

        name.setSufijos("Col");

        assertEquals(List.of("Col"), name.sufijos);
        assertEquals("Sir Jonathan Edward Harrington, Col", name.texto);

        name.setSufijos(List.of());
        assertEquals(List.of(), name.sufijos);
        assertEquals("Sir Jonathan Edward Harrington", name.texto);

        name.setSufijos("OBE");
        name.setSufijos("", "", "");
        assertEquals("Sir Jonathan Edward Harrington", name.texto);

    }

    @Test
    @DisplayName("setSufijos() debe validar cantidad (maximo 5), longitud(maximo 10) y nulidad")
    void sufijosValid() {

        var name = johann.copy();

        assertThrows(NullPointerException.class, () -> name.setSufijos((String) null));
        assertDoesNotThrow(() -> name.setSufijos((List<String>) null));

        assertDoesNotThrow(() -> name.setSufijos("1", "2", "3", "4", "5"));
        assertDoesNotThrow(() -> name.setSufijos("12345", "1234", "123", "12", "1"));

        assertThrows(NameValidationException.class, () -> name.setSufijos("1", "2", "3", "4", "5", "6"));
        assertThrows(NameValidationException.class, () -> name.setSufijos(List.of("1", "2", "3", "4", "5", "6")));

        assertDoesNotThrow(() -> name.setSufijos("1234567890"));
        assertDoesNotThrow(() -> name.setSufijos("123456789"));
        assertDoesNotThrow(() -> name.setSufijos("12345678"));
        assertDoesNotThrow(() -> name.setSufijos("1234567"));
        assertDoesNotThrow(() -> name.setSufijos("123456"));

        assertThrows(NameValidationException.class, () -> name.setSufijos(List.of("1234567890a")));
        assertThrows(NameValidationException.class, () -> name.setSufijos(List.of("1234567890ab")));
        assertThrows(NameValidationException.class, () -> name.setSufijos(List.of("1234567890abc")));
    }


    @Test
    @DisplayName("getUso() debe devolver el uso")
    void getUso() {

        assertEquals(anastasia.uso, anastasia.getUso());
        assertEquals(anastasia2.uso, anastasia2.getUso());
        assertEquals(anastasia3.uso, anastasia3.getUso());
    }

    @Test
    @DisplayName("getNombres() debe devolver la misma lista de nombres")
    void getNombres() {

        assertEquals(anil.nombres, anil.getNombres());
    }

    @Test
    @DisplayName("getApellidos() debe devolver la misma lista de apellidos")
    void getApellidos() {

        assertEquals(anil.apellidos, anil.getApellidos());
    }

    @Test
    @DisplayName("getPrefijos() debe devolver la misma lista de prefijos")
    void getPrefijos() {

        assertEquals(jonathan.prefijos, jonathan.getPrefijos());
        assertEquals(lucia.prefijos, lucia.getPrefijos());
        assertEquals(piotr.prefijos, piotr.getPrefijos());
    }

    @Test
    @DisplayName("getSufijos() debe devolver la misma lista de sufijos")
    void getSufijos() {

        assertEquals(jonathan.sufijos, jonathan.getSufijos());
    }

    @Test
    @DisplayName("getTexto() debe devolver la forma textual exacta")
    void getTexto() {

        assertEquals(ivan.texto, ivan.getTexto());
        assertEquals(misaki.texto, misaki.getTexto());
        assertEquals(wei.texto, wei.getTexto());
        assertEquals(odysseas.texto, odysseas.getTexto());
        assertEquals(minJun.texto, minJun.getTexto());
    }

    @Test
    @DisplayName("setTextoOriginal debe no hacer nada si la cadena es nula, vacía o arrojar un error si es mayor a 256 bytes")
    void setTextoOriginalFail() {

        var name = johann.copy();
        
        var string256bytes = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
                "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
                "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
                "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";

        var string256BytesArabic = "عععععععععععععععععععععععععععععععععععععععععععععععععععععععععععععععع" +
                "عععععععععععععععععععععععععععععععععععععععععععععععععععععععععععععععع";

        var string255BytesKanji = "日日日日日日日日日日日日日日日日日日日日日日日日日日日日日日日日日" +
                "日日日日日日日日日日日日日日日日日日日日日日日日日日日日日日日日日日日日日日日日日日" +
                "日日日日日日日日日日";

        var string256BytesCirilic = "ББББББББББББББББББББББББББББББББББББББББББББББББББББББББББББББББ" +
                "ББББББББББББББББББББББББББББББББББББББББББББББББББББББББББББББББ";

        var string256BytesThai = "กกกกกAกกกกกAกกกกกAกกกกกAกกกกกAกกกกกAกกกกกAกกกกกAกกกกกAกกกกกAกกกกกAกกกกกAกกกกกAกกกกกAกกกกกAกกกกกA";

        var strings = List.of(string256bytes, string255BytesKanji, string256BytesCirilic, string256BytesArabic, string256BytesThai);
        var overflows = List.of("A", "日", "Б", "ع", "ก");


        name.setTextoOriginal(null);
        assertNull(johann.textoOriginal);

        for (int i = 0; i < strings.size(); i++) {

            var longString = strings.get(i);
            var overflow = overflows.get(i);

            name.setTextoOriginal(longString);
            assertEquals(longString, name.getTextoOriginal());

            name.setTextoOriginal("Shorty");
            name.setTextoOriginal(longString + overflow);
            assertEquals("Shorty", name.getTextoOriginal());
        }

    }

    @Test
    @DisplayName("getTextoOriginal() debe devolver el nombre en su forma escrita original si fue especificada, o una cadena igual al texto si no")
    void getTextoOriginal() {

        assertEquals("Иван Сергеевич Петров-Сидоров", ivan.getTextoOriginal());
        assertNotEquals(ivan.texto, ivan.getTextoOriginal());
        assertTrue(ivan.hasTextoOriginal());

        assertEquals("佐藤 美咲", misaki.getTextoOriginal());
        assertNotEquals(misaki.texto, misaki.getTextoOriginal());
        assertTrue(misaki.hasTextoOriginal());

        assertEquals("王伟", wei.getTextoOriginal());
        assertNotEquals(wei.texto, wei.getTextoOriginal());
        assertTrue(wei.hasTextoOriginal());

        assertEquals("Οδυσσέας Παπαδόπουλος Καραγιάννης", odysseas.getTextoOriginal());
        assertNotEquals(odysseas.texto, odysseas.getTextoOriginal());
        assertTrue(odysseas.hasTextoOriginal());

        assertEquals("김민준", minJun.getTextoOriginal());
        assertNotEquals(minJun.texto, minJun.getTextoOriginal());
        assertTrue(minJun.hasTextoOriginal());

        assertEquals(jonathan.texto, jonathan.getTextoOriginal());
        assertFalse(jonathan.hasTextoOriginal());

        assertEquals(johann.texto, johann.getTextoOriginal());
        assertFalse(johann.hasTextoOriginal());
    }

    @Test
    @DisplayName("Los apellidos se ordenan de acuerdo a la propiedad \"order\"")
    void ordering() {

        var first = new Nombre.Apellido("Apellido 1", Nombre.TipoApellido.FAMILIAR, 0);
        var second = new Nombre.Apellido("Apellido 2", Nombre.TipoApellido.FAMILIAR, 1);
        var third = new Nombre.Apellido("Apellido 3", Nombre.TipoApellido.FAMILIAR, 2);
        var fourth = new Nombre.Apellido("Apellido 4", Nombre.TipoApellido.FAMILIAR, 3);
        var unordered = List.of(fourth, second, third, first);


        var name = new Nombre(nombresValidos, unordered);

        assertEquals("Isaac Albert Apellido 1 Apellido 2 Apellido 3 Apellido 4", name.texto);

    }

    @Test
    @DisplayName("Los apellidos deben tener diferente orden y no repetirse")
    void noRepeat() {
        var first = new Nombre.Apellido("Apellido 1", Nombre.TipoApellido.FAMILIAR, 0);
        assertThrows(NameValidationException.class, () -> new Nombre(nombresValidos, List.of(first, first)));
    }

    @Test
    @DisplayName("Nombres que llevan los apellidos primero se muestran como deben ser con invertir = true")
    void inverted() {
        var name = duncan.copy();
        name.setInvertido(true);
        assertEquals("MacLeod Duncan", name.texto);

    }

    @Test
    @DisplayName("Los apellidos deben ser Comparable, y deben implementar comparación")
    void comparable() {
        var first = new Nombre.Apellido("Apellido 1", Nombre.TipoApellido.FAMILIAR, 0);
        var second = new Nombre.Apellido("Apellido 2", Nombre.TipoApellido.FAMILIAR, 1);
        var third = new Nombre.Apellido("Apellido 3", Nombre.TipoApellido.FAMILIAR, 2);
        var another = new Nombre.Apellido("Apellido 0", Nombre.TipoApellido.FAMILIAR, 0);

        assertInstanceOf(Comparable.class, first);

        assertEquals(1, second.compareTo(first));
        assertNotEquals(0, second.compareTo(first));
        assertNotEquals(-1, second.compareTo(first));

        assertEquals(-1, second.compareTo(third));
        assertNotEquals(0, second.compareTo(third));
        assertNotEquals(1, second.compareTo(third));

        assertEquals(0, first.compareTo(another));
        assertNotEquals(1, first.compareTo(another));
        assertNotEquals(-1, first.compareTo(another));

    }

    @Test
    @DisplayName("toString() devuelve el nombre completo como aparece en texto")
    void testToString() {
        assertEquals(johann.texto, johann.toString());
    }

    @Test
    @DisplayName("Copy produces a complete copy of the name")
    void copy() {
        var name = misaki.copy();
        assertEquals(misaki.texto, name.texto);
        assertEquals(misaki.nombres, name.nombres);
        assertEquals(misaki.apellidos, name.apellidos);
        assertEquals(misaki.sufijos, name.sufijos);
        assertEquals(misaki.prefijos, name.prefijos);
        assertEquals(misaki.periodo, name.periodo);
        assertEquals(misaki.uso, name.uso);
        assertEquals(misaki.getApellidosAsString(), name.getApellidosAsString());
        assertEquals(misaki.getNombresAsString(), name.getNombresAsString());
        assertEquals(misaki.getPrefijosAsString(), name.getPrefijosAsString());
        assertEquals(misaki.getSufijosAsString(), name.getSufijosAsString());
        assertEquals(misaki.hasTextoOriginal(), name.hasTextoOriginal());
        assertEquals(misaki.getApellidosAsList(), name.getApellidosAsList());
    }

    @Test
    @DisplayName("Verify setters")
    void settersOfCatan() {
        var name = jose.copy();

        name.setInvertido(true);
        name.setInvertido(false);


        name.setPeriodo(new Periodo());
        assertNotNull(name.getPeriodo());

        name.setUso(Nombre.Uso.ALIAS);
        assertNotNull(name.uso);
        assertEquals(Nombre.Uso.ALIAS, name.uso);
    }
}