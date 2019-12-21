package com.example.validation.validators.domain;

import com.example.validation.validators.constraints.WithoutProhibitedSubstring;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestCustomAnnotation
{

    /**
     * Объединение ограничений. Стандартная + Собственная валидация
     */
    @WithoutProhibitedSubstring( substringNotAllowed = " " )
    @NotBlank
    public String stringValidate;
}
