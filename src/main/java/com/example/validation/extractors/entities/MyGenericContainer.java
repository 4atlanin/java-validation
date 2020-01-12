package com.example.validation.extractors.entities;

import lombok.Data;

@Data
public class MyGenericContainer<T, Y>
{
    private T key;
    private Y value;
}
