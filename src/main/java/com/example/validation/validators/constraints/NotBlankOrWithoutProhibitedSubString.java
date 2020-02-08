package com.example.validation.validators.constraints;

import com.example.validation.validators.constraints.custom_validation.WithoutProhibitedSubstring;
import org.hibernate.validator.constraints.ConstraintComposition;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.NotBlank;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static org.hibernate.validator.constraints.CompositionType.OR;

//По умолчанию, если есть композиция аннотаций, то сежду ними применяется логическое И, т.е. все условия должны
// выполняться. Поведение можно поменять на OR?, как тут.
@ConstraintComposition(OR)
@ReportAsSingleViolation
@Target( value = { ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD } )
@Retention( RetentionPolicy.RUNTIME )
@NotBlank
@WithoutProhibitedSubstring( substringNotAllowed = " " )
@Constraint( validatedBy = {} )
public @interface NotBlankOrWithoutProhibitedSubString
{
    String message() default "Logical or between composed constraints";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

