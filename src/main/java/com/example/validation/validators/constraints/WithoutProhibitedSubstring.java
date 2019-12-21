package com.example.validation.validators.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Если в строке есть такая подстрока, то ошибка
 * ElementType.ANNOTATION_TYPE даёт возможность объединять(агрегировать) аннотации.
 * Например @NotBlankAndWithoutProhibitedSubstring создана через агрегирование этой и @NotBlank аннотации
 */

@Target( value = { ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD, ElementType.ANNOTATION_TYPE, TYPE_USE } )
@Retention( RetentionPolicy.RUNTIME )   //обязательно
@Constraint( validatedBy = ProhibitedSubstringValidator.class )
@Repeatable( WithoutProhibitedSubstring.List.class )
public @interface WithoutProhibitedSubstring
{
    String substringNotAllowed();    //no default value == mandatory

    // обязательно для валидационных аннотаций
    // ищет по этому ключу интернационализированное сообщение,
    // если не нашло, печатает сам ключ
    String message() default "the string contains '{substringNotAllowed}'";

    // обязательно для валидационных аннотаций
    Class<?>[] groups() default {};

    // обязательно для валидационных аннотаций
    Class<? extends Payload>[] payload() default {};

    @Target( { METHOD, FIELD, ANNOTATION_TYPE, PARAMETER, TYPE_USE } )   //должны совпадать с основной аннотацией
    @Retention( RUNTIME )
    @Documented
    @interface List
    {
        WithoutProhibitedSubstring[] value();
    }

}
