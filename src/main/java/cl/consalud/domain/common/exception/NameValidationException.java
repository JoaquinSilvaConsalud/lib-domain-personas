package cl.consalud.domain.common.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class NameValidationException extends RuntimeException {

    public NameValidationException(String message) {
        super(message);
    }

    public NameValidationException(String message, String internalMessage) {
        super(message, new IllegalArgumentException(internalMessage));
    }
}
