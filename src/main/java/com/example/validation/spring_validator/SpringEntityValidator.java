package com.example.validation.spring_validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class SpringEntityValidator implements Validator
{
    @Override
    public boolean supports( Class<?> clazz )
    {
        return EntitySprVal.class.isAssignableFrom( clazz );
    }

    /**
     * Will always fail
     */
    @Override
    public void validate( Object target, Errors errors )
    {
        errors.reject( "errorCode", "errorMessage" );
    }
}
