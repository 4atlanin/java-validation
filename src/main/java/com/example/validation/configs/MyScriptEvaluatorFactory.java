package com.example.validation.configs;

import org.hibernate.validator.spi.scripting.ScriptEvaluator;
import org.hibernate.validator.spi.scripting.ScriptEvaluatorFactory;


//Тип понятненько. Оно должно возвращать ScriptEvaluator, который будет определять тру или не тру.
//Пример имплементации есть в доке
//Сейчас нигде не используется
public class MyScriptEvaluatorFactory implements ScriptEvaluatorFactory
{
    @Override
    public ScriptEvaluator getScriptEvaluatorByLanguageName( String languageName )
    {
        return null;
    }

    @Override
    public void clear()
    {

    }
}
