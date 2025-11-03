package cl.consalud.domain.personas.model;


import cl.consalud.domain.common.utils.CollectionUtils;
import cl.consalud.domain.common.utils.Default;
import cl.consalud.domain.common.utils.Range;
import cl.consalud.domain.common.utils.StringUtils;
import cl.consalud.domain.common.utils.Tuple;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

@Getter
public class Run extends Identificacion {

    private static final Range<Integer> VALID_RUT_LENGTH = new Range<>(8, 9);

    protected String run;
    protected int numero;
    protected char verificador;
    protected final TipoRun tipoRun;

    Run() {
        super(Tipo.RUN, Genero.NO_ESPECIFICADO);
        tipoRun = TipoRun.PERSONA_NATURAL;
    }

    public Run(String run) {
        this(run, Genero.NO_ESPECIFICADO);
    }

    @Default
    public Run(String run, Genero genero) {
        super(Tipo.RUN, genero);

        update(processRut(run));

        this.genero = genero;

        this.tipoRun = Arrays.stream(TipoRun.values())
                             .filter(tt -> tt.range.isInRangeInclusive(numero))
                             .findFirst().orElse(TipoRun.PERSONA_NATURAL);

    }

    public void setRun(String run) {
        update(processRut(run));
    }

    private void update(Tuple<Integer, Character> processedRut) {
        this.numero = processedRut.first();
        this.verificador = processedRut.second();
        this.run = String.valueOf(this.numero) + this.verificador;
    }

    public String getFormateado() {
        return formatRut(this.run);
    }

    private Tuple<Integer, Character> processRut(String run) {

        if (StringUtils.isEmpty(run))
            throw new IllegalArgumentException("RUN cannot be null or empty.");

        char[] cleanRut = cleanRut(run);

        if (!VALID_RUT_LENGTH.isInRangeInclusive(cleanRut.length))
            throw new IllegalArgumentException("Invalid RUN format: " + run);

        char[] numArray = new char[cleanRut.length - 1];
        System.arraycopy(cleanRut, 0, numArray, 0, cleanRut.length - 1);

        char ver = cleanRut[cleanRut.length - 1];

        if (!isValidRut(numArray, ver))
            throw new IllegalArgumentException(String.format("Incorrect verifier digit %c for RUN %s", ver, run));

        return new Tuple<>(CollectionUtils.charArrayToInt(numArray), ver);
    }

    private char[] cleanRut(String fullRun) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < fullRun.length(); i++) {
            char c = fullRun.charAt(i);
            if (Character.isDigit(c) || c == 'K' || c == 'k') {
                sb.append(Character.toUpperCase(c));
            }
        }
        return sb.toString().toCharArray();
    }

    private String formatRut(String cleanRut) {
        String rutNumber = cleanRut.substring(0, cleanRut.length() - 1);
        char verifier = cleanRut.charAt(cleanRut.length() - 1);
        StringBuilder formatted = new StringBuilder();

        for (int i = 0; i < rutNumber.length(); i++) {
            if (i > 0 && (rutNumber.length() - i) % 3 == 0) {
                formatted.append('.');
            }
            formatted.append(rutNumber.charAt(i));
        }
        formatted.append('-').append(verifier);
        return formatted.toString();
    }

    private boolean isValidRut(char[] body, char expectedVerif) {
        int sum = 0, factor = 2;
        for (int i = body.length - 1; i >= 0; i--) {
            sum += (body[i] - '0') * factor;
            factor = (factor == 7) ? 2 : (factor + 1);
        }
        int modulo = 11 - (sum % 11);
        char calcVerif = (modulo == 11) ? '0' : (modulo == 10) ? 'K' : (char) ('0' + modulo);
        return expectedVerif == calcVerif;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Run run = (Run) obj;
        return Objects.equals(this.run, run.run) &&
                Objects.equals(verificador, run.verificador);
    }

    @Override
    public int hashCode() {
        return Objects.hash(run, verificador);
    }

    @Override
    public String toString() {
        return formatRut(this.run);
    }

    public enum TipoRun {
        PERSONA_NATURAL(new Range<>(1_000_000, 48_999_999)),
        PATRIMONIOS_NO_JURIDICOS(new Range<>(50_000_000, 59_999_999)),
        PERSONA_JURIDICA(new Range<>(60_000_000, 99_999_999));

        final Range<Integer> range;

        TipoRun(Range<Integer> integerRange) {
            this.range = integerRange;
        }
    }
}
