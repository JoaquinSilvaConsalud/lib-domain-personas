package cl.consalud.domain.common.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Email extends Contacto {

    private static final String email_regex_formato =
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final Pattern emailpattern = Pattern.compile(email_regex_formato);

    public Email() {
        super();
        this.tipo = Tipo.EMAIL;
    }
    public Email(String valor) {
        super(Tipo.EMAIL, false, true, Uso.PERSONAL, valor);
        setValor(valor);
    }


    @Override
    protected boolean isValid(String s) {
        return !validateEmail(s);
    }

    @Override
    protected String formatValor(String s){
        return s.trim();
    }

    private boolean validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        Matcher matcher = emailpattern.matcher(email);
        return matcher.matches();
    }
}
