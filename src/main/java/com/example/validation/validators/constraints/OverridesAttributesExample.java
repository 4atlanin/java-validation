package com.example.validation.validators.constraints;

import javax.validation.Constraint;
import javax.validation.OverridesAttribute;
import javax.validation.Payload;
import javax.validation.constraints.Size;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target( value = { ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD } )
@Retention( RetentionPolicy.RUNTIME )
@Size
@WithoutProhibitedSubstring( substringNotAllowed = " " )
@WithoutProhibitedSubstring( substringNotAllowed = "_" )
@Constraint( validatedBy = {} )
public @interface OverridesAttributesExample
{
    @OverridesAttribute( constraint = WithoutProhibitedSubstring.class, name = "message", constraintIndex = 0 )
    String messageWhitespace();

    @OverridesAttribute( constraint = WithoutProhibitedSubstring.class, name = "message", constraintIndex = 1 )
    String messageUndescore();

    @OverridesAttribute( constraint = Size.class, name = "min" )
    @OverridesAttribute( constraint = Size.class, name = "max" )
    int size();

    String message() default "Override size attributes";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
