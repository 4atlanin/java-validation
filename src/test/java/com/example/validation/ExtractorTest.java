package com.example.validation;

import com.example.validation.extractors.ExtractorService;
import com.example.validation.extractors.entities.MyGenericContainer;
import com.example.validation.extractors.entities.NonGenericContainer;
import com.example.validation.extractors.entities.TestExtractorEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ExtractorTest
{
    @Autowired
    private ExtractorService extractorService;

    @Test
    public void testGenericExtractorNullWrapper()
    {
        MyGenericContainer<String, Integer> mgc = new MyGenericContainer<>();
        mgc.setKey( "key" );
        mgc.setValue( 1 );

        NonGenericContainer nonGenericContainer = new NonGenericContainer();
        nonGenericContainer.setValue( "value" );

        List<String> messages = extractorService
            .testExtractor( new TestExtractorEntity( null, nonGenericContainer, nonGenericContainer ) )
            .stream()
            .map( ConstraintViolation::getMessage )
            .collect( Collectors.toList() );

        assertEquals( 1, messages.size() );
        assertTrue( messages.contains( "Container can't be null" ) );
    }

    @Test
    public void testKey()
    {
        MyGenericContainer<String, Integer> mgc = new MyGenericContainer<>();
        mgc.setKey( null );
        mgc.setValue( 1 );

        NonGenericContainer nonGenericContainer = new NonGenericContainer();
        nonGenericContainer.setValue( "value" );

        List<String> messages = extractorService
            .testExtractor( new TestExtractorEntity( mgc, nonGenericContainer, nonGenericContainer ) )
            .stream()
            .map( ConstraintViolation::getMessage )
            .collect( Collectors.toList() );

        assertEquals( 1, messages.size() );
        assertTrue( messages.contains( "Key can't be null" ) );
    }

    @Test
    public void testValue()
    {
        MyGenericContainer<String, Integer> mgc = new MyGenericContainer<>();
        mgc.setKey( "qwerty" );
        mgc.setValue( null );

        NonGenericContainer nonGenericContainer = new NonGenericContainer();
        nonGenericContainer.setValue( "value" );

        List<String> messages = extractorService
            .testExtractor( new TestExtractorEntity( mgc, nonGenericContainer, nonGenericContainer ) )
            .stream()
            .map( ConstraintViolation::getMessage )
            .collect( Collectors.toList() );

        assertEquals( 1, messages.size() );
        assertTrue( messages.contains( "Value can't be null" ) );
    }

    @Test
    public void testUnwrapSkip()
    {
        MyGenericContainer<String, Integer> mgc = new MyGenericContainer<>();
        mgc.setKey( "qwerty" );
        mgc.setValue( 4 );

        NonGenericContainer nonGenericContainer = new NonGenericContainer();
        nonGenericContainer.setValue( "value" );

        List<String> messages = extractorService
            .testExtractor( new TestExtractorEntity( mgc, null, nonGenericContainer ) )
            .stream()
            .map( ConstraintViolation::getMessage )
            .collect( Collectors.toList() );

        assertEquals( 1, messages.size() );
        assertTrue( messages.contains( "NonGeneric container without unwrapping" ) );
    }

    @Test
    public void testUnwrap()
    {
        MyGenericContainer<String, Integer> mgc = new MyGenericContainer<>();
        mgc.setKey( "qwerty" );
        mgc.setValue( 4 );

        NonGenericContainer nonGenericContainer = new NonGenericContainer();
        nonGenericContainer.setValue( null );

        List<String> messages = extractorService
            .testExtractor( new TestExtractorEntity( mgc, nonGenericContainer, nonGenericContainer ) )
            .stream()
            .map( ConstraintViolation::getMessage )
            .collect( Collectors.toList() );

        assertEquals( 1, messages.size() );
        assertTrue( messages.contains( "NonGeneric container with unwrapping" ) );
    }
}