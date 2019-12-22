package com.example.validation.validators.constraints.class_level_validation;

import com.example.validation.validators.constraints.class_level_validation.ClassLevelCheck;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@ClassLevelCheck
public class TestClassLevelAnnotation
{
    public TestClassLevelAnnotation() {}

    private int min = 1;
    private int max = 0;

    @NotBlank
    private String blankString;
}
