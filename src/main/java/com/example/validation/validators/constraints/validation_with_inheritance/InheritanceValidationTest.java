package com.example.validation.validators.constraints.validation_with_inheritance;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.List;

@Data
public class InheritanceValidationTest extends BaseClass
{
    @NotNull
    protected List<@Size( max = 2 ) String> id;           //Те. Можно переопределять валидации над полями

    public static InheritanceValidationTest getTestObject()
    {
        InheritanceValidationTest test = new InheritanceValidationTest();
        test.id = Arrays.asList( "qwertyy", "   " );
        return test;
    }

    // @MethodLevelCheck( min = 1, max = 1 )               // Нельзя добавлять или изменять Аннотации валидации.
    public void baseMethod(/* @Size( max = 1 )*/ String a )    // Должно быть или как в базовом методе, или вообще ничего не быть.
    {                                                           // Иначе ConstraintDeclarationException

    }
}

class BaseClass
{
    @Size( min = 100 )
    protected List<@NotBlank String> id;

    public void baseMethod( @Size( max = 1 ) @NotNull String a )
    {

    }
}
