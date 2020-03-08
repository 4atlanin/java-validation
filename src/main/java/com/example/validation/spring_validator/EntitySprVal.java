package com.example.validation.spring_validator;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class EntitySprVal
{
    @NotNull
    private String str;
}
