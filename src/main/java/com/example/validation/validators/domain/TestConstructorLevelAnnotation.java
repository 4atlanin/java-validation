package com.example.validation.validators.domain;

import com.example.validation.validators.constraints.ConstructorLevelCheck;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.executable.ValidateOnExecution;

@Data
@Component
@Validated
public class TestConstructorLevelAnnotation
{
    private String nullAble;

   // @ConstructorLevelCheck
    @Autowired
    public TestConstructorLevelAnnotation( @Qualifier(value = "longString") @Size(max = 1) String nullAble )
    {
        this.nullAble = nullAble;
    }

}
