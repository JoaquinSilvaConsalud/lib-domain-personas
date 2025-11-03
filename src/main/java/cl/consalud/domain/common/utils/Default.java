package cl.consalud.domain.common.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
    Annotation for MapStruct.
    This annotation can be used on constructors for Mapstruct to use them as default.
    No functionality needed.
 */
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.CONSTRUCTOR})
public @interface Default {
}
