package com.example.validation.validators.domain;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Size;
import javax.validation.executable.ExecutableType;
import javax.validation.executable.ValidateOnExecution;

@Data
@Component
@Validated
public class TestConstructorLevelAnnotation
{
    @Size( max = 1 )
    private String incorrect;

    // @ConstructorLevelCheck
    @Autowired
  //  @ValidateOnExecution(type = ExecutableType.CONSTRUCTORS)
    public TestConstructorLevelAnnotation( @Qualifier( value = "longString" ) @Size( max = 1 ) String incorrect )
    {
        this.incorrect = incorrect;
    }

}
