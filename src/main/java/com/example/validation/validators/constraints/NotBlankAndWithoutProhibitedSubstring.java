package com.example.validation.validators.constraints;

import com.example.validation.validators.constraints.custom_validation.WithoutProhibitedSubstring;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.NotBlank;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 1. Благодаря @ReportAsSingleViolation проверка остановится на первом constraint violation. Т.е. код последующих проверок выполняться не будет.
 *
 * 2. Если бы небыло аннотации @ReportAsSingleViolation, валидация вернула бы 2 отдельные ошибки(для @NotNull and @WithoutProhibitedSubstring).
 * Но благодаря @ReportAsSingleViolation, вернётся 1 ошибка. Её сообщение возьмётся из этой аннотации
 */
@ReportAsSingleViolation
@Target( value = { ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD } )
@Retention( RetentionPolicy.RUNTIME )
@NotBlank
@WithoutProhibitedSubstring( substringNotAllowed = " " )
@Constraint( validatedBy = {} )
public @interface NotBlankAndWithoutProhibitedSubstring
{
    String message() default "{test.constraint.violation.NotBlankAndWithoutProhibitedSubstring}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
