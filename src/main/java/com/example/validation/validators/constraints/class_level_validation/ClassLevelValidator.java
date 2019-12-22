package com.example.validation.validators.constraints.class_level_validation;

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
