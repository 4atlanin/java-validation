package com.example.validation.validators.constraints.constructor_level_validation;

import com.example.validation.validators.constraints.constructor_level_validation.ConstructorLevelCheck;
import lombok.Data;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Size;

@Data
public class TestConstructorLevelAnnotation
{
    @Size( max = 1 )
    private String incorrect;

    @ConstructorLevelCheck  //тут всё также как и в емтод валидации
    public TestConstructorLevelAnnotation(  @Size( max = 1 ) String incorrect )
    {
        this.incorrect = incorrect;
    }

}
