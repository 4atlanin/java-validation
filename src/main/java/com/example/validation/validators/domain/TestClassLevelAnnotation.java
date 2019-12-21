package com.example.validation.validators.domain;

import com.example.validation.validators.constraints.ClassLevelCheck;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@ClassLevelCheck
public class TestClassLevelAnnotation
{
    private int min = 1;
    private int max = 0;

    @NotBlank
    private String blankString;
}
