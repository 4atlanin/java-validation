package com.example.validation.spring_validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

@Service
public class SpringValidationService
{
    @Autowired
    @Qualifier( "defaultValidator" )
    private Validator validator;

    @Autowired
    @Qualifier( "springEntityValidator" )
    private Validator customValidator;

    public BindingResult validateBean( EntitySprVal obj )
    {
        BeanPropertyBindingResult validationResult = new BeanPropertyBindingResult( obj, StringUtils.uncapitalize( obj.getClass().getSimpleName() ) );
        validator.validate( obj, validationResult );

        return validationResult;
    }

    public BindingResult validateBeanWithCustomValidator( EntitySprVal obj )
    {
        BeanPropertyBindingResult validationResult = new BeanPropertyBindingResult( obj, StringUtils.uncapitalize( obj.getClass().getSimpleName() ) );
        customValidator.validate( obj, validationResult );

        return validationResult;
    }

}
