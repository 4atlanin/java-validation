package com.example.validation.validators.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Так же само делается и для конструкторов
 */

@Target( value = { ElementType.METHOD, ElementType.CONSTRUCTOR } )
@Retention( RetentionPolicy.RUNTIME )
@Constraint( validatedBy = MethodLevelValidator.class )
@Repeatable( MethodLevelCheck.List.class )
public @interface MethodLevelCheck
{
    int min();

    int max();

    String message() default "validate all parameters of method";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target( { ElementType.METHOD, ElementType.CONSTRUCTOR } )
    @Retention( RUNTIME )
    @Documented
    @interface List
    {
        MethodLevelCheck[] value();
    }
}
