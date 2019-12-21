package com.example.validation.validators.domain;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Size;

@Data
@Component
@Validated
public class TestConstructorLevelAnnotation
{
    private String incorrect;

   // @ConstructorLevelCheck
    @Autowired
    public TestConstructorLevelAnnotation( @Qualifier(value = "longString") @Size(max = 1) String incorrect )
    {
        this.incorrect = incorrect;
    }

}
