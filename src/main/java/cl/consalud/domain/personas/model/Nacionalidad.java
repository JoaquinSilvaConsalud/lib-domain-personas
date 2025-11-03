package cl.consalud.domain.personas.model;

import cl.consalud.domain.common.model.Pais;
import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Enum que representa las nacionalidades reconocidas por MINSAL.
 * Cada nacionalidad está asociada a un código MINSAL y a un país (enum Pais).
 *
 * Ejemplo:
 *  Nacionalidad.CHILENA.getPais() → Pais.CHILE
 *  Nacionalidad.ARGENTINA.getPais() → Pais.ARGENTINA
 */
public enum Nacionalidad {

    SIN_INFORMACION(0, null),

    // América
    ARGENTINA(32, Pais.ARGENTINA),
    BOLIVIANA(68, Pais.BOLIVIA),
    BRASILEÑA(76, Pais.BRASIL),
    CANADIENSE(124, Pais.CANADA),
    CHILENA(152, Pais.CHILE),
    COLOMBIANA(170, Pais.COLOMBIA),
    COSTARRICENSE(188, Pais.COSTA_RICA),
    CUBANA(192, Pais.CUBA),
    ECUATORIANA(218, Pais.ECUADOR),
    ESTADOUNIDENSE(840, Pais.ESTADOS_UNIDOS),
    GUATEMALTECA(320, Pais.GUATEMALA),
    HONDUREÑA(340, Pais.HONDURAS),
    MEXICANA(484, Pais.MEXICO),
    NICARAGÜENSE(558, Pais.NICARAGUA),
    PANAMEÑA(591, Pais.PANAMA),
    PARAGUAYA(600, Pais.PARAGUAY),
    PERUANA(604, Pais.PERU),
    SALVADOREÑA(222, Pais.SALVADOR),
    URUGUAYA(858, Pais.URUGUAY),
    VENEZOLANA(862, Pais.VENEZUELA),

    // Europa
    ALEMANA(276, Pais.ALEMANIA),
    AUSTRIACA(40, Pais.AUSTRIA),
    BELGA(56, Pais.BELGICA),
    DANESA(208, Pais.DINAMARCA),
    ESPAÑOLA(724, Pais.ESPANA),
    FINLANDESA(246, Pais.FINLANDIA),
    FRANCESA(250, Pais.FRANCIA),
    GRIEGA(300, Pais.GRECIA),
    HOLANDESA(528, Pais.PAISES_BAJOS),
    HÚNGARA(348, Pais.HUNGRIA),
    IRLANDESA(372, Pais.IRLANDA),
    ITALIANA(380, Pais.ITALIA),
    NORUEGA(578, Pais.NORUEGA),
    POLACA(616, Pais.POLONIA),
    PORTUGUESA(620, Pais.PORTUGAL),
    BRITÁNICA(826, Pais.REINO_UNIDO),
    RUMANA(642, Pais.RUMANIA),
    RUSA(643, Pais.RUSIA),
    SUECA(752, Pais.SUECIA),
    SUIZA(756, Pais.SUIZA),

    // África
    ARGELINA(12, Pais.ARGELIA),
    EGIPCIA(818, Pais.EGIPTO),
    ETÍOPE(231, Pais.ETIOPIA),
    KENIATA(404, Pais.KENIA),
    MARROQUÍ(504, Pais.MARRUECOS),
    NIGERIANA(566, Pais.NIGERIA),
    SUDAFRICANA(710, Pais.SUDAFRICA),

    // Asia
    AFGANA(4, Pais.AFGANISTAN),
    ARABE_SAUDITA(682, Pais.ARABIA_SAUDI),
    CHINA(156, Pais.CHINA),
    COREANA(410, Pais.COREA_DEL_SUR),
    FILIPINA(608, Pais.FILIPINAS),
    INDIA(356, Pais.INDIA),
    INDONESIA(360, Pais.INDONESIA),
    ISRAELÍ(376, Pais.ISRAEL),
    JAPONESA(392, Pais.JAPON),
    PAKISTANÍ(586, Pais.PAKISTAN),
    SIRIA(760, Pais.SIRIA),
    TAILANDESA(764, Pais.TAILANDIA),
    TURCA(792, Pais.TURQUIA),
    VIETNAMITA(704, Pais.VIETNAM),

    // Oceanía
    AUSTRALIANA(36, Pais.AUSTRALIA),
    NEOCELANDESA(554, Pais.NUEVA_ZELANDA);

    @Getter
    private final int codigoMinsal;
    private final Pais pais;

    Nacionalidad(int codigoMinsal, Pais pais) {
        this.codigoMinsal = codigoMinsal;
        this.pais = pais;
    }

    public Optional<Pais> getPais() {
        return Optional.ofNullable(pais);
    }

    private static final Map<Integer, Nacionalidad> MAPA_POR_CODIGO = new HashMap<>();
    static {
        for (Nacionalidad n : values()) {
            MAPA_POR_CODIGO.put(n.codigoMinsal, n);
        }
    }

    public static Nacionalidad fromCodigoMinsal(int codigo) {
        return MAPA_POR_CODIGO.getOrDefault(codigo, SIN_INFORMACION);
    }

    public static Nacionalidad fromPais(Pais pais) {
        if (pais == null) return SIN_INFORMACION;
        return Arrays.stream(values())
                .filter(n -> n.getPais().orElse(null) == pais)
                .findFirst()
                .orElse(SIN_INFORMACION);
    }
}