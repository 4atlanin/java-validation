package com.example.validation.validators.constraints;

import com.example.validation.validators.domain.TestClassLevelAnnotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ClassLevelValidator implements ConstraintValidator<ClassLevelCheck, TestClassLevelAnnotation>
{
    @Override
    public boolean isValid( TestClassLevelAnnotation value, ConstraintValidatorContext context )
    {
        return value.getMin() <= value.getMax();
    }
}
