package cl.consalud.domain.personas.utils.test;

import cl.consalud.domain.personas.model.Genero;
import cl.consalud.domain.personas.model.Run;

import java.security.SecureRandom;
import java.util.Random;

/**
 * Clase utilitaria para generar RUTs válidos de forma aleatoria.
 * Útil para testing, datos de prueba y simulaciones.
 */
public class RutGenerator {

    private static final Random random = new SecureRandom();
    private static final int MIN_RUT_NUMBER = 1_000_000;
    private static final int MAX_RUT_NUMBER = 30_000_000;

    public static void main(String[] args) {
        System.out.println(generarRutAleatorio());
        System.out.println(generarRutAleatorio());
        System.out.println(generarRutAleatorio());
        System.out.println(generarRutAleatorio());
        System.out.println(generarRutAleatorio());
        System.out.println(generarRutAleatorio());
    }

    /**
     * Genera un RUT válido aleatorio con género no especificado
     *
     * @return Objeto Rut válido
     */
    public static Run generarRutAleatorio() {
        return generarRutAleatorio(Genero.NO_ESPECIFICADO);
    }

    /**
     * Genera un RUT válido aleatorio con el género especificado
     *
     * @param genero Género para el RUT
     * @return Objeto Rut válido
     */
    public static Run generarRutAleatorio(Genero genero) {
        int numeroRut = generarNumeroAleatorio();
        char digitoVerificador = calcularDigitoVerificador(numeroRut);
        String rutCompleto = numeroRut + String.valueOf(digitoVerificador);
        return new Run(rutCompleto, genero);
    }

    /**
     * Genera un RUT válido aleatorio como String sin formato
     *
     * @return String con RUT válido (ej: "12345678K")
     */
    public static String generarRutStringAleatorio() {
        int numeroRut = generarNumeroAleatorio();
        char digitoVerificador = calcularDigitoVerificador(numeroRut);
        return numeroRut + String.valueOf(digitoVerificador);
    }

    /**
     * Genera un RUT válido aleatorio como String con formato
     *
     * @return String con RUT válido formateado (ej: "12.345.678-K")
     */
    public static String generarRutFormateadoAleatorio() {
        Run run = generarRutAleatorio();
        return run.getFormateado();
    }

    /**
     * Genera un número de RUT específico con su dígito verificador válido
     *
     * @param numero Número del RUT (sin dígito verificador)
     * @return Objeto Rut válido
     */
    public static Run generarRutConNumero(int numero) {
        if (numero < MIN_RUT_NUMBER || numero > MAX_RUT_NUMBER) {
            throw new IllegalArgumentException("El número debe estar entre " + MIN_RUT_NUMBER + " y " + MAX_RUT_NUMBER);
        }

        char digitoVerificador = calcularDigitoVerificador(numero);
        String rutCompleto = numero + String.valueOf(digitoVerificador);

        return new Run(rutCompleto);
    }

    /**
     * Genera múltiples RUTs válidos aleatorios
     *
     * @param cantidad Cantidad de RUTs a generar
     * @return Array de objetos Rut válidos
     */
    public static Run[] generarMultiplesRuts(int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
        }

        Run[] runs = new Run[cantidad];
        for (int i = 0; i < cantidad; i++) {
            runs[i] = generarRutAleatorio();
        }
        return runs;
    }

    /**
     * Genera múltiples RUTs válidos aleatorios como Strings
     *
     * @param cantidad   Cantidad de RUTs a generar
     * @param formateado Si true, retorna RUTs formateados; si false, sin formato
     * @return Array de Strings con RUTs válidos
     */
    public static String[] generarMultiplesRutsString(int cantidad, boolean formateado) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
        }

        String[] ruts = new String[cantidad];
        for (int i = 0; i < cantidad; i++) {
            if (formateado) {
                ruts[i] = generarRutFormateadoAleatorio();
            } else {
                ruts[i] = generarRutStringAleatorio();
            }
        }
        return ruts;
    }

    /**
     * Genera un RUT válido con un rango específico de números
     *
     * @param minimo Número mínimo (inclusive)
     * @param maximo Número máximo (inclusive)
     * @return Objeto Rut válido
     */
    public static Run generarRutEnRango(int minimo, int maximo) {
        if (minimo < MIN_RUT_NUMBER || maximo > MAX_RUT_NUMBER || minimo > maximo) {
            throw new IllegalArgumentException("Rango inválido. Debe estar entre " + MIN_RUT_NUMBER + " y " + MAX_RUT_NUMBER);
        }

        int numeroRut = random.nextInt(maximo - minimo + 1) + minimo;
        char digitoVerificador = calcularDigitoVerificador(numeroRut);
        String rutCompleto = numeroRut + String.valueOf(digitoVerificador);

        return new Run(rutCompleto);
    }

    /**
     * Genera un número aleatorio para RUT dentro del rango válido
     *
     * @return Número entero válido para RUT
     */
    private static int generarNumeroAleatorio() {
        return random.nextInt(MAX_RUT_NUMBER - MIN_RUT_NUMBER + 1) + MIN_RUT_NUMBER;
    }

    /**
     * Calcula el dígito verificador para un número de RUT dado
     *
     * @param numero Número del RUT
     * @return Dígito verificador ('0'-'9' o 'K')
     */
    private static char calcularDigitoVerificador(int numero) {
        String numeroStr = String.valueOf(numero);
        int suma = 0;
        int factor = 2;

        // Calcular suma ponderada desde el último dígito
        for (int i = numeroStr.length() - 1; i >= 0; i--) {
            suma += (numeroStr.charAt(i) - '0') * factor;
            factor = (factor == 7) ? 2 : (factor + 1);
        }

        int modulo = 11 - (suma % 11);

        if (modulo == 11) {
            return '0';
        } else if (modulo == 10) {
            return 'K';
        } else {
            return (char) ('0' + modulo);
        }
    }

    /**
     * Verifica si un número generaría un RUT válido
     *
     * @param numero Número a verificar
     * @return true si el número está en el rango válido
     */
    public static boolean esNumeroValido(int numero) {
        return numero >= MIN_RUT_NUMBER && numero <= MAX_RUT_NUMBER;
    }

    /**
     * Obtiene el rango mínimo para números de RUT
     *
     * @return Número mínimo válido
     */
    public static int getNumeroMinimo() {
        return MIN_RUT_NUMBER;
    }

    /**
     * Obtiene el rango máximo para números de RUT
     *
     * @return Número máximo válido
     */
    public static int getNumeroMaximo() {
        return MAX_RUT_NUMBER;
    }
}
