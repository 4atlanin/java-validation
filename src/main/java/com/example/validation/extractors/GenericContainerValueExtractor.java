package com.example.validation.extractors;

import com.example.validation.extractors.entities.MyGenericContainer;

import javax.validation.valueextraction.ExtractedValue;
import javax.validation.valueextraction.ValueExtractor;

public class GenericContainerValueExtractor implements ValueExtractor<MyGenericContainer<?, @ExtractedValue ?>>
{
    @Override
    public void extractValues( MyGenericContainer<?, ?> originalValue, ValueReceiver receiver )
    {
        //У ValueReceiver есть разные методы, для разных типов контейнеров. Они добавляют разную метаинформацию в респонс.
        //value() - for a simple wrapping container - it is used for Optional s
        //iterableValue() - for an iterable container - it is used for Set s
        //indexedValue() - for a container containing indexed values - it is used for List s
        //keyedValue() - for a container containing keyed values - it is used for Map s. It is used for both the keys and the
        //values. In the case of keys, the key is also passed as the validated value.

        //"<my container value>" - это имя узла которое будет добавлено в ошибку. Можно указать null, чтобы она не добавлялась
        receiver.keyedValue( "<my container value>", originalValue.getValue(), originalValue.getValue() );
    }
}