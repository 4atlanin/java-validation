package com.example.validation.validators.constraints.constructor_level_validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target( value = { ElementType.CONSTRUCTOR } )
@Retention( RetentionPolicy.RUNTIME )
@Constraint( validatedBy = ConstructorValidator.class )
public @interface ConstructorLevelCheck
{
    String message() default "Constructor parameter must be Integer";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}