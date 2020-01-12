package com.example.validation.extractors;

import com.example.validation.extractors.entities.NonGenericContainer;

import javax.validation.valueextraction.ExtractedValue;
import javax.validation.valueextraction.UnwrapByDefault;
import javax.validation.valueextraction.ValueExtractor;

@UnwrapByDefault  //Тоже, что и payload = Unwrapping.Unwrap.class у аннотации
public class NonGenericContainerExtractor implements ValueExtractor<@ExtractedValue( type = String.class ) NonGenericContainer>
{
    @Override
    public void extractValues( NonGenericContainer originalValue, ValueReceiver receiver )
    {
        receiver.value( null, originalValue.getValue() );
    }
}
