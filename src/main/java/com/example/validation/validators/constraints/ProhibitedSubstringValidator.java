package com.example.validation.validators.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ProhibitedSubstringValidator implements ConstraintValidator<WithoutProhibitedSubstring, String>
{
    private String substring;

    @Override
    public void initialize( WithoutProhibitedSubstring constraintAnnotation )
    {
        this.substring = constraintAnnotation.substringNotAllowed();
    }

    /**
     *  Must be tread safe.
     * @param value  Не должно меняться в этом методе. (Тут стринга и так не будет)
      * @param context
     * @return
     */

    @Override
    public boolean isValid( String value, ConstraintValidatorContext context )
    {
        return !value.contains( substring );

    }
}
