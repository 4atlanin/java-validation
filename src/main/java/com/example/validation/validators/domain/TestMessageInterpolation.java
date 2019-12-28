package com.example.validation.validators.domain;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class TestMessageInterpolation
{
    // {} - референс на ключь в проперти файле для сообщения. Выполняется в первую очередь
    // {} - референс на атрибут аннотации (min max у Size  например). Выполняется после пунткта 1
    // ${} - референс на JEL(Jakarta Expression language) выражение. Или на проверяемое значение( доступно по validatedValue ключу)
    // Или на форматтер java.util.Formatter.#format
    // Выполняется после пунткта 2
    @Size( min = 1, max = 2, message = "it's my own validation message min = {min} and max = {max} and validatedValue = ${validatedValue}" )
    private final String testString;

    @NotBlank( message = "Amount of whitespaces in the blank String is ${validatedValue}." +
                         " Formatted part is '${formatter.format('%s', validatedValue)}'." ) // sample of using ${}
    private final String blankString;
}
