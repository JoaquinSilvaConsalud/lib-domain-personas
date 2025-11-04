package cl.consalud.domain.common.model;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import java.util.Objects;
import static com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat.E164;

public final class Telefono extends Contacto {

    private static final PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
    private static final String DEFAULT_REGION = "CL";

    public Telefono() {
        super();
        this.tipo = Tipo.TELEFONO;
    }

    public Telefono(String valor) {
        super(Tipo.TELEFONO, false, true, Uso.PERSONAL, valor);
    }

    @Override
    protected boolean isValid(String s) {
        if (s == null || s.isBlank()) {
            return true;
        }
        try {
            Phonenumber.PhoneNumber numeroParse = parseInternal(s);
            return !phoneUtil.isValidNumber(numeroParse);
        } catch (NumberParseException e) {
            return true;
        }
    }

    @Override
    protected String formatValor(String s){
        try {
            Phonenumber.PhoneNumber numeroParse = parseInternal(s);
            return phoneUtil.format(numeroParse, E164);
        } catch (NumberParseException e) {
            throw new IllegalStateException("Error al formatear un número que debería ser válido: " + s, e);
        }
    }

    private Phonenumber.PhoneNumber parseInternal(String s) throws NumberParseException {
        if (s.startsWith("+")) {
            return phoneUtil.parse(s, null);
        } else {
            return phoneUtil.parse(s, DEFAULT_REGION);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode());
    }
}
