package cl.consalud.domain.personas.model;

import cl.consalud.domain.common.model.Periodo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ContratoTest {

    private String folioValido;
    private Periodo periodoValido;
    private Contrato.Tipo tipoValido;
    private List<Documento> documentosValidos;

    @BeforeEach
    void setUp() {
        folioValido = "123456";
        periodoValido = new Periodo(ZonedDateTime.now());
        tipoValido = Contrato.Tipo.INDIVIDUAL;
        documentosValidos = new ArrayList<>();
    }

    @Test
    @DisplayName("Debe crear un Contrato correctamente con datos válidos")
    void debeCrearContratoConDatosValidos() {
        Contrato contrato = new Contrato(folioValido, true, periodoValido, tipoValido, documentosValidos);

        assertAll("Verificación de propiedades del contrato creado",
                () -> assertNotNull(contrato),
                () -> assertEquals(folioValido, contrato.getFolio()),
                () -> assertTrue(contrato.isEstado()),
                () -> assertEquals(periodoValido, contrato.getPeriodo()),
                () -> assertEquals(tipoValido, contrato.getTipo()),
                () -> assertNotNull(contrato.getDocumentos(), "La lista de documentos no debe ser nula")
        );
    }

    @Test
    @DisplayName("Debe permitir crear un Contrato con estado false")
    void debePermitirEstadoFalse() {
        Contrato contrato = new Contrato(folioValido, false, periodoValido, tipoValido, documentosValidos);

        assertFalse(contrato.isEstado());
    }

    @Test
    @DisplayName("Debe fallar al crear un Contrato con folio nulo")
    void debeFallarConFolioNulo() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                new Contrato(null, true, periodoValido, tipoValido, documentosValidos)
        );
        assertEquals("error id contrato", ex.getMessage());
    }

    @Test
    @DisplayName("Debe fallar al crear un Contrato con folio en blanco")
    void debeFallarConFolioEnBlanco() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                new Contrato("   ", true, periodoValido, tipoValido, documentosValidos)
        );
        assertEquals("error id contrato", ex.getMessage());
    }

    @Test
    @DisplayName("Debe fallar al crear un Contrato con periodo nulo")
    void debeFallarConPeriodoNulo() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                new Contrato(folioValido, true, null, tipoValido, documentosValidos)
        );
        assertEquals("error periodo", ex.getMessage());
    }

    @Test
    @DisplayName("Debe fallar al crear un Contrato con tipo nulo")
    void debeFallarConTipoNulo() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                new Contrato(folioValido, true, periodoValido, null, documentosValidos)
        );
        assertEquals("error tipo", ex.getMessage());
    }

    @Test
    @DisplayName("Debe fallar al crear un Contrato con lista de documentos nula")
    void debeFallarConDocumentosNulos() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                new Contrato(folioValido, true, periodoValido, tipoValido, null)
        );
        assertEquals("error lista documentos", ex.getMessage());
    }


}
