package com.example.validation.validators.domain;

import com.example.validation.validators.constraints.ConstructorLevelCheck;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.executable.ValidateOnExecution;

/// TODO Это не работает
@Data
@Validated
public class TestConstructorLevelAnnotation
{
    private int a;
    private int b;

    private Integer nullAble;

    @ConstructorLevelCheck
    public TestConstructorLevelAnnotation( int a, int b,  Integer nullAble )
    {
        this.a = a;
        this.b = b;
        this.nullAble = nullAble;
    }

}
