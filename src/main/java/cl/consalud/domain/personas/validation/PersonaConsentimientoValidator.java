package cl.consalud.domain.personas.validation;

import cl.consalud.domain.common.model.Consentimiento;
import java.util.*;

public final class PersonaConsentimientoValidator {

    private PersonaConsentimientoValidator() {}

    public static void validarConsentimientos(List<Consentimiento> consentimientos) {
        if (consentimientos == null || consentimientos.isEmpty()) {
            return; // permitido: sin consentimientos
        }

        Set<Consentimiento.Tipo> tiposVistos = new HashSet<>();
        for (Consentimiento c : consentimientos) {
            validarDatosObligatorios(c);

            if (!tiposVistos.add(c.getTipo())) {
                throw new IllegalArgumentException("Solo puede existir un consentimiento por tipo: " + c.getTipo());
            }
        }
    }

    public static List<Consentimiento> agregarOReemplazar(List<Consentimiento> actuales, Consentimiento nuevo) {
        if (nuevo == null) {
            throw new IllegalArgumentException("El consentimiento no puede ser nulo");
        }

        validarDatosObligatorios(nuevo);

        if (actuales == null) {
            actuales = new ArrayList<>();
        }

        List<Consentimiento> resultado = new ArrayList<>();
        boolean reemplazado = false;

        for (Consentimiento existente : actuales) {
            if (existente.getTipo() == nuevo.getTipo()) {
                resultado.add(nuevo);
                reemplazado = true;
            } else {
                resultado.add(existente);
            }
        }

        if (!reemplazado) {
            resultado.add(nuevo);
        }

        validarConsentimientos(resultado);
        return resultado;
    }

    private static void validarDatosObligatorios(Consentimiento c) {
        if (c.getTipo() == null) {
            throw new IllegalArgumentException("El consentimiento debe tener un tipo definido.");
        }
        if (c.getPeriodo() == null) {
            throw new IllegalArgumentException("El consentimiento debe tener un periodo definido.");
        }

        if ((c.getVersion() == null || c.getVersion().isBlank()) && c.getMedio() != null) {
            throw new IllegalArgumentException("El consentimiento debe especificar la versión cuando se define un medio.");
        }
        if ((c.getMedio() == null || c.getMedio().isBlank()) && c.getVersion() != null) {
            throw new IllegalArgumentException("El consentimiento debe especificar el medio cuando se define una versión.");
        }
    }
}