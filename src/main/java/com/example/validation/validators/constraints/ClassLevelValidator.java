package com.example.validation.validators.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ClassLevelValidator implements ConstraintValidator<ClassLevelCheck, Object[]>
{
    @Override
    public boolean isValid( Object[] params, ConstraintValidatorContext context )
    {
        return (Integer) params[0] <= (Integer) params[1];
    }
}
