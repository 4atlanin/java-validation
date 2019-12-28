package com.example.validation.validators.constraints.constructor_level_validation;

import lombok.Data;

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
