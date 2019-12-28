package com.example.validation;

import com.example.validation.validators.constraints.class_level_validation.TestClassLevelAnnotation;
import com.example.validation.validators.constraints.custom_validation.TestCustomAnnotation;
import com.example.validation.validators.constraints.validation_with_inheritance.InheritanceValidationTest;
import com.example.validation.validators.domain.TestCombinedAnnotationWithReportAsSingleViolation;
import com.example.validation.validators.domain.TestListOfAnnotations;
import com.example.validation.validators.domain.TestMessageInterpolation;
import com.example.validation.validators.domain.TestOverrideAttributes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ValidationApplicationTests
{

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ValidationService validationService;

    @Test
    void testPost() throws Exception
    {
        mockMvc.perform( post( "/post" ).content( "{\"stringValidate\" : \" \"}" ).contentType( MediaType.APPLICATION_JSON ) )
               .andDo( print() )
               .andExpect( status().isOk() );
    }

    @Test
    void testCustomValidatorAndCombiningTwoAnnotations()
    {
        TestCustomAnnotation testCustomAnnotation = new TestCustomAnnotation( " " );

        Set<ConstraintViolation<TestCustomAnnotation>> errors = validationService.validateCustomAnnotation( testCustomAnnotation );
        assertEquals( 2, errors.size() );
        List<String> messages = errors.stream().map( ConstraintViolation::getMessage ).collect( Collectors.toList() );
        assertTrue( messages.contains( "must not be blank" ) );
        assertTrue( messages.contains( "the string contains ' '" ) );
    }

    @Test
    void testWithReportAsSingleViolation()
    {
        TestCombinedAnnotationWithReportAsSingleViolation dto = new TestCombinedAnnotationWithReportAsSingleViolation( " " );
        Set<ConstraintViolation<TestCombinedAnnotationWithReportAsSingleViolation>> errors = validationService.testReportAsSingleViolation( dto );

        assertEquals( 1, errors.size() );
    }

    @Test
    void testListOfAnnotations()
    {

        //Test  List
        TestListOfAnnotations dto = new TestListOfAnnotations( " _", "sut" );
        Set<ConstraintViolation<TestListOfAnnotations>> errors = validationService.testListOfAnnotations( dto );
        assertEquals( 2, errors.size() );

        List<String> messages = errors.stream().map( ConstraintViolation::getMessage ).collect( Collectors.toList() );
        assertTrue( messages.contains( "the string contains ' '" ) );
        assertTrue( messages.contains( "the string contains '_'" ) );

    }

    @Test
    void testJustTwoTheSameAnnotationsOnOneField()
    {
        TestListOfAnnotations dto = new TestListOfAnnotations( "sut", " _" );

        Set<ConstraintViolation<TestListOfAnnotations>> errors = validationService.testListOfAnnotations( dto );
        assertEquals( 2, errors.size() );

        List<String> messages = errors.stream().map( ConstraintViolation::getMessage ).collect( Collectors.toList() );
        assertTrue( messages.contains( "the string contains ' '" ) );
        assertTrue( messages.contains( "the string contains '_'" ) );
    }

    @Test
    void testOverrideAttributes()
    {
        TestOverrideAttributes testOverrideAttributes = new TestOverrideAttributes( " _" );
        List<String> messages = validationService.testOverrideAttributes( testOverrideAttributes )
                                                 .stream().map( ConstraintViolation::getMessage ).collect( Collectors.toList() );
        assertEquals( 3, messages.size() );

        assertTrue( messages.contains( "message with whitespace" ) );
        assertTrue( messages.contains( "message with underscore" ) );
        assertTrue( messages.contains( "size must be between 3 and 3" ) );
    }

    @Test
    void testClassLevelAnnotation()
    {
        TestClassLevelAnnotation testOverrideAttributes = new TestClassLevelAnnotation( 10, 0, "   " );
        List<String> messages = validationService.testClassLevelAnnotation( testOverrideAttributes )
                                                 .stream().map( ConstraintViolation::getMessage ).collect( Collectors.toList() );
        assertEquals( 2, messages.size() );

        assertTrue( messages.contains( "Test message from file" ) );    //will take this message from ValidationMessages.properties
        assertTrue( messages.contains( "must not be blank" ) );
    }

    @Test
    void testMethodParameterValidation()
    {
        List<String> messages = assertThrows( ConstraintViolationException.class, () -> validationService.testValidateMethodParameter( null ) )
            .getConstraintViolations().stream()
            .map( ConstraintViolation::getMessage )
            .collect( Collectors.toList() );

        assertTrue( messages.contains( "must not be null" ) );
    }

    @Test
    void testMethodLevelValidation()
    {
        List<String> messages = assertThrows( ConstraintViolationException.class, () -> validationService.testValidateAllMethodParameters( 11, 12 ) )
            .getConstraintViolations().stream()
            .map( ConstraintViolation::getMessage )
            .collect( Collectors.toList() );

        assertTrue( messages.contains( "validate all parameters of method" ) );
    }

    @Test
    void testMethodResponseValidation()
    {
        List<String> messages = assertThrows( ConstraintViolationException.class, () -> validationService.testValidateMethodResponse() )
            .getConstraintViolations().stream()
            .map( ConstraintViolation::getMessage )
            .collect( Collectors.toList() );

        assertTrue( messages.contains( "must be true" ) );
    }

    @Test
    void testValidAnnotationInTheService()
    {
        List<String> messages = assertThrows( ConstraintViolationException.class, () -> validationService.testValidAnnotation( new TestClassLevelAnnotation( 10, 5, "asd" ) ) )
            .getConstraintViolations().stream()
            .map( ConstraintViolation::getMessage )
            .collect( Collectors.toList() );

        assertTrue( messages.contains( "Test message from file" ) );

    }

    @Test
    void testConstructorLevelValidationTriggeredManually() throws NoSuchMethodException
    {
        List<String> messages = validationService.testConstructorLevelValidationTriggeredManually().stream().map( ConstraintViolation::getMessage ).collect( Collectors.toList() );
        assertEquals( 2, messages.size() );

        assertTrue( messages.contains( "size must be between 0 and 1" ) );
        assertTrue( messages.contains( "Constructor parameter must be Integer" ) );
    }

    @Test
    void testInheritanceValidationField()
    {
        List<String> messages = assertThrows( ConstraintViolationException.class, () -> validationService.testValidateFieldInheritance( InheritanceValidationTest.getTestObject() ) )
            .getConstraintViolations().stream()
            .map( ConstraintViolation::getMessage )
            .collect( Collectors.toList() );

        assertTrue( messages.contains( "size must be between 0 and 2" ) );   // по сообщению на каждый элемент массива
        assertTrue( messages.contains( "size must be between 0 and 2" ) );
    }

    @Test
    void testParameterScriptAssert()
    {
        List<String> messages = assertThrows( ConstraintViolationException.class, () -> validationService.testParameterScriptAssert( 10, 5 ) )
            .getConstraintViolations().stream()
            .map( ConstraintViolation::getMessage )
            .collect( Collectors.toList() );
        assertTrue( messages.contains( "script expression \"min <= max\" didn't evaluate to true" ) );
    }

    @Test
    void testMessageInterpolation()
    {
        List<String> messages = assertThrows( ConstraintViolationException.class,
            () -> validationService.testMessageInterpolation( new TestMessageInterpolation( "qwe", "   " ) ) )
            .getConstraintViolations().stream()
            .map( ConstraintViolation::getMessage )
            .collect( Collectors.toList() );

        assertEquals( 2, messages.size() );
        assertTrue( messages.contains( "Amount of whitespaces in the blank String is    . Formatted part is '   '." ) );
        assertTrue( messages.contains( "it's my own validation message min = 1 and max = 2 and validatedValue = qwe" ) );
    }
}