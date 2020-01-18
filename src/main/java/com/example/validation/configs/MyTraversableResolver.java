package com.example.validation.configs;

import javax.validation.Path;
import javax.validation.TraversableResolver;
import java.lang.annotation.ElementType;

//Штука которая даёт возможность не валиировать какие-то поля, например, чтобы Lazy поля не тянуло из базы.
//PS Hibernate сам прекрасно знает, что ненужно Lazy поля загружать только ради валидации. Стр 117.
//можно в дебаге запустить тест
public class MyTraversableResolver implements TraversableResolver {
    @Override
    public boolean isReachable(Object traversableObject, Path.Node traversableProperty, Class<?> rootBeanType, Path pathToTraversableObject, ElementType elementType) {

        return true;
    }

    @Override
    public boolean isCascadable(Object traversableObject, Path.Node traversableProperty, Class<?> rootBeanType, Path pathToTraversableObject, ElementType elementType) {
        return false;
    }
}
