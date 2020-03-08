package com.example.validation;

import com.example.validation.spring_validator.EntitySprVal;
import com.example.validation.spring_validator.SpringValidationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.DefaultMessageSourceResolvable;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class SpringValidationServiceTest
{
    @Autowired
    private SpringValidationService springValidationService;

    @Test
    public void testSpringValidator()
    {
        EntitySprVal entitySprVal = new EntitySprVal();
        entitySprVal.setStr( null );

        List<String> list = springValidationService.validateBean( entitySprVal ).getAllErrors().stream()
                                                   .map( DefaultMessageSourceResolvable::getDefaultMessage ).collect( Collectors.toList() );

        assertTrue( list.contains( "must not be null" ) );
    }

    @Test
    public void testCustomSpringValidator()
    {
        EntitySprVal entitySprVal = new EntitySprVal();
        entitySprVal.setStr( "correct string" );

        List<String> list = springValidationService.validateBeanWithCustomValidator( entitySprVal ).getAllErrors().stream()
                                                   .map( DefaultMessageSourceResolvable::getDefaultMessage ).collect( Collectors.toList() );

        assertTrue( list.contains( "errorMessage" ) );
    }

}
