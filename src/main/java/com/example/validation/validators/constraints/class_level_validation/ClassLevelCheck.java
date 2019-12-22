package com.example.validation.validators.constraints.class_level_validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target( value = { ElementType.TYPE } )
@Retention( RetentionPolicy.RUNTIME )
@Constraint( validatedBy = ClassLevelValidator.class )
@Repeatable( ClassLevelCheck.List.class )
public @interface ClassLevelCheck
{
    String message() default "{test.validation.message}";   // will take the value from ValidationMessages.properties or

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target( { ElementType.TYPE } )
    @Retention( RUNTIME )
    @Documented
    @interface List
    {
        ClassLevelCheck[] value();
    }
}
