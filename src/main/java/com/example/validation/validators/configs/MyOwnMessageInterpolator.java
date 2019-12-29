package com.example.validation.validators.configs;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.validation.MessageInterpolator;
import java.util.Locale;

/**
 * Он просто будет заменять все валидционные собщения на то, что реализовано в методе
 */
public class MyOwnMessageInterpolator implements MessageInterpolator {
    @Override
    public String interpolate(String messageTemplate, Context context) {
        StringBuilder sb = new StringBuilder(1000);
        sb.append("Annotation - ").append(context.getConstraintDescriptor()
                .getAnnotation().annotationType().getCanonicalName()).append('\n');
        sb.append("Message template - ").append(messageTemplate).append('\n');
        sb.append("Validated value - ").append(context.getValidatedValue()).append('\n');
        return sb.toString();
    }

    @Override
    public String interpolate(String messageTemplate, Context context, Locale locale) {
        throw new NotImplementedException();
    }
}
