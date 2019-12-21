package com.example.validation.validators.constraints;

import com.example.validation.validators.domain.TestConstructorLevelAnnotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;

@SupportedValidationTarget( ValidationTarget.PARAMETERS )
public class ConstructorValidator implements ConstraintValidator<ConstructorLevelCheck, Object[]>
{
    @Override
    public boolean isValid( Object[] value, ConstraintValidatorContext context )
    {
        return value[0] instanceof Integer;
    }
}