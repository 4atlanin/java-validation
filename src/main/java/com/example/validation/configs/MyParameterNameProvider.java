package com.example.validation.configs;

import javax.validation.ParameterNameProvider;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// так и не понял зачем это нужно стр 119
public class MyParameterNameProvider implements ParameterNameProvider {
    @Override
    public List<String> getParameterNames(Constructor<?> constructor) {
        return null;
    }

    @Override
    public List<String> getParameterNames(Method method) {
        return Stream.of(method.getParameters()).map(p -> p.getName() + "lyl" ).collect(Collectors.toList());
    }
}
