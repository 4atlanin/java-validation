package com.example.validation.configs;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorFactory;

//Нигде не используется. Прост пример. Я прост хз что тут можно сделать
public class MyConstraintValidationFactory implements ConstraintValidatorFactory {
    @Override
    public <T extends ConstraintValidator<?, ?>> T getInstance(Class<T> key) {
        return null;
    }

    @Override
    public void releaseInstance(ConstraintValidator<?, ?> instance) {

    }
}
