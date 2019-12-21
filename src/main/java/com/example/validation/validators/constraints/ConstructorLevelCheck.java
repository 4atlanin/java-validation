package com.example.validation.validators.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target( value = { ElementType.CONSTRUCTOR } )
@Retention( RetentionPolicy.RUNTIME )
@Constraint( validatedBy = ConstructorValidator.class )
public @interface ConstructorLevelCheck
{
    String message() default "validate all parameters of method";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}