package com.example.validation.validators.constraints.method_level_validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;

@SupportedValidationTarget( ValidationTarget.PARAMETERS)   //Показывает, что нам нужно проверять параметры метода, а не респонс
public class MethodLevelValidator implements ConstraintValidator<MethodLevelCheck, Object[]>
{
    private int min;
    private int max;

    public void initialize( MethodLevelCheck constraintAnnotation )
    {
        max = constraintAnnotation.max();
        min = constraintAnnotation.min();
    }

    @Override
    public boolean isValid( Object[] value, ConstraintValidatorContext context )
    {
        if( value.length == 2
            && value[0] instanceof Integer
            && value[1] instanceof Integer )
        {
            return (Integer) value[0] == min && (Integer) value[1] == max;
        }

        return false;
    }
}
