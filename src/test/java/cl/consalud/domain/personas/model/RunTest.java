package cl.consalud.domain.personas.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RunTest {

    String rut1 = "78.486.664-5";
    String rut2 = "54.655.439-2";
    String rut3 = "31.126.530-K";
    String rut4 = "70.259.783-8";
    String rut5 = "53.000.567-4";
    String rut6 = "8.196.161-1";

    @Test
    @DisplayName("Debe crear RUT válido con formato limpio")
    void debeCrearRutValidoConFormatoLimpio() {

        Run run = new Run("439040807");

        assertEquals("439040807", run.run);
        assertEquals('7', run.verificador);
        assertEquals("43.904.080-7", run.getFormateado());
        assertEquals(Genero.NO_ESPECIFICADO, run.genero);
        assertEquals(Identificacion.Tipo.RUN, run.tipo);
    }

    @Test
    @DisplayName("Debe crear RUT válido con formato formateado")
    void debeCrearRutValidoConFormatoFormateado() {
        String rutFormateado = rut1;

        Run run = new Run(rutFormateado);

        assertEquals("784866645", run.run);
        assertEquals('5', run.verificador);
        assertEquals("78.486.664-5", run.getFormateado());
    }

    @Test
    @DisplayName("Debe crear RUT con constructor completo")
    void debeCrearRutConConstructorCompleto() {
        Genero genero = Genero.MASCULINO;

        Run runObj = new Run(rut2, genero);

        assertEquals("546554392", runObj.run);
        assertEquals('2', runObj.verificador);
        assertEquals("54.655.439-2", runObj.getFormateado());
        assertEquals(genero, runObj.genero);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "31.126.530-K", "31126530K", "31126530-K",
            "314532066", "31.453.206-6", "5.613.457-3", "56134573",
            "5613457-3", "5.613.457-3", "56134573"
    })
    @DisplayName("Debe crear RUTs válidos con diferentes formatos")
    void debeCrearRutsValidosConDiferentesFormatos(String rutValido) {

        assertDoesNotThrow(() -> new Run(rutValido));
    }

    @Test
    @DisplayName("Debe lanzar excepción para RUT nulo")
    void debeLanzarExcepcionParaRutNulo() {

        assertThrows(IllegalArgumentException.class, () -> new Run(null));
    }

    @Test
    @DisplayName("Debe lanzar excepción para RUT vacío")
    void debeLanzarExcepcionParaRutVacio() {

        assertThrows(IllegalArgumentException.class, () -> new Run(""));
    }

    @Test
    @DisplayName("Debe lanzar excepción para RUT con formato inválido")
    void debeLanzarExcepcionParaRutConFormatoInvalido() {

        assertThrows(IllegalArgumentException.class, () -> new Run("1234567890"));
    }

    @Test
    @DisplayName("Debe lanzar excepción para RUT con dígito verificador incorrecto")
    void debeLanzarExcepcionParaRutConDigitoVerificadorIncorrecto() {

        assertThrows(IllegalArgumentException.class, () -> new Run("12345678-9"));
    }

    @Test
    @DisplayName("Debe validar RUT con dígito verificador K")
    void debeValidarRutConDigitoVerificadorK() {

        assertDoesNotThrow(() -> new Run("31.126.530-K"));
    }

    @Test
    @DisplayName("Debe validar RUT con dígito verificador k minúscula")
    void debeValidarRutConDigitoVerificadorKMinuscula() {

        Run run = new Run("31.126.530-k");

        assertEquals('K', run.verificador);
        assertEquals("31.126.530-K", run.getFormateado());
    }

    @Test
    @DisplayName("Debe actualizar RUT usando setRut")
    void debeActualizarRutUsandoSetRun() {
        Run run = new Run(rut5);

        run.setRun("70.259.783-8");

        assertEquals("702597838", run.run);
        assertEquals('8', run.verificador);
        assertEquals(70259783, run.numero);
        assertEquals("70.259.783-8", run.getFormateado());
    }


    @Test
    @DisplayName("Debe comparar RUTs usando equals")
    void debeCompararRutsUsandoEquals() {
        Run run1 = new Run(this.rut1);
        Run run2 = new Run(this.rut1);
        Run run3 = new Run(this.rut3);

        assertEquals(run1, run2);
        assertNotEquals(run1, run3);
        assertNotEquals(null, run1);
        assertNotEquals("70.259.783-8", run1);
    }

    @Test
    @DisplayName("Debe generar mismo hashCode para RUTs iguales")
    void debeGenerarMismoHashCodeParaRutsIguales() {
        Run run1 = new Run("702597838");
        Run run2 = new Run("70.259.783-8");

        assertEquals(run1.hashCode(), run2.hashCode());
    }

    @Test
    @DisplayName("Debe retornar formato correcto en toString")
    void debeRetornarFormatoCorrectoEnToString() {
        Run run = new Run(rut4);

        String resultado = run.toString();

        assertEquals("70.259.783-8", resultado);
    }

    @Test
    @DisplayName("Debe manejar RUT de 7 dígitos")
    void debeManejarRutDe7Digitos() {

        Run run = new Run(rut6);

        assertEquals("81961611", run.run);
        assertEquals('1', run.verificador);
        assertEquals(8196161, run.numero);
        assertEquals("8.196.161-1", run.getFormateado());
    }

    @Test
    @DisplayName("Debe manejar RUT de 8 dígitos")
    void debeManejarRutDe8Digitos() {

        Run run = new Run(rut3);

        assertEquals("31126530K", run.run);
        assertEquals('K', run.verificador);
        assertEquals("31.126.530-K", run.getFormateado());
    }

    @Test
    @DisplayName("Debe lanzar excepción para RUT muy corto")
    void debeLanzarExcepcionParaRutMuyCorto() {

        assertThrows(IllegalArgumentException.class, () -> new Run("123456-7"));
    }

    @Test
    @DisplayName("Debe lanzar excepción para RUT muy largo")
    void debeLanzarExcepcionParaRutMuyLargo() {

        assertThrows(IllegalArgumentException.class, () -> new Run("123456789-0"));
    }

    @Test
    @DisplayName("Debe actualizar género y número de documento")
    void debeActualizarGeneroYNumeroDocumento() {
        Run run = new Run(rut1);

        run.setGenero(Genero.FEMENINO);

        assertEquals(Genero.FEMENINO, run.getGenero());
    }

    @Test
    @DisplayName("Debe mantener referencia al mismo objeto después de setRut")
    void debeMantenerReferenciaAlMismoObjetoDespuesDeSetRun() {
        Run run = new Run(rut1);
        run.setGenero(Genero.MASCULINO);

        run.setRun(rut3);

        assertEquals("31126530K", run.run);
        assertEquals(31126530, run.numero);
        assertEquals('K', run.verificador);
        assertEquals(Genero.MASCULINO, run.getGenero());
    }
}
