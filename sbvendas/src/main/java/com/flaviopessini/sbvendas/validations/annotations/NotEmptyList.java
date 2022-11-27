package com.flaviopessini.sbvendas.validations.annotations;

import com.flaviopessini.sbvendas.validations.NotEmptyListValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation de configuração customizada para listas.
 * Pode ser inserida somente em classes.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = NotEmptyListValidation.class)
public @interface NotEmptyList {

    // Mensagem por padrão.
    String message() default "A lista não pode ser nulo ou vazia.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
