package com.example.validation.validators.constraints;

import com.example.validation.validators.domain.TestConstructorLevelAnnotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;

@SupportedValidationTarget( ValidationTarget.PARAMETERS )
public class ConstructorValidator implements ConstraintValidator<ConstructorLevelCheck, TestConstructorLevelAnnotation>
{
    @Override
    public boolean isValid( TestConstructorLevelAnnotation value, ConstraintValidatorContext context )
    {
        return value.getA() <= value.getB();
    }
}