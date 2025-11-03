package cl.consalud.domain.common.utils;

public class StringUtils {

    /**
     * Verifica si una string está vacía o contiene solo espacios en blanco
     * Optimizado para performance evitando el uso de trim()
     * @param string la String a verificar
     * @return true si la String es nula o solo contiene espacios en blanco
     */
    public static boolean isEmpty(String string) {
        if (string == null) return true;
        int len = string.length();
        if (len == 0) return true;
        var init = 0;
        var end = len - 1;

        while (init <= end && Character.isWhitespace(string.charAt(init))) init++;
        return init > end;
    }


    /**
     * Verifica si una String No está vacía. Optimizado para performance
     * @param string la String a verificar
     * @return true si la String no es null y tiene caracteres que no son espacios en blanco.
     */
    public static boolean hasText(String string) {
        return !isEmpty(string);
    }

    /**
     * Obtiene la longitud de una string de forma segura
     *
     * @param string la string a medir
     * @return 0 si la string es null, o su longitud real
     */
    public static int safeLength(String string) {
        if (string == null) return 0;
        return string.length();
    }
}
