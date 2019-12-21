package com.example.validation;

import com.example.validation.validators.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

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
        final ResultActions resultActions;
        resultActions = mockMvc.perform( post( "/post" ).content( "{\"stringValidate\" : \" \"}" ).contentType( MediaType.APPLICATION_JSON ) )
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
        assertThrows( ConstraintViolationException.class, () -> validationService.testValidateMethodParameter( null ) );
    }

    @Test
    void testMethodLevelValidation()
    {
        assertThrows( ConstraintViolationException.class, () -> validationService.testValidateAllMethodParameters( 11, 12 ) );
    }

    @Test
    void testMethodResponseValidation()
    {
        assertThrows( ConstraintViolationException.class, () -> validationService.testValidateMethodResponse() );
    }

    @Test
    void testValidAnnotationInTheService()
    {
        assertThrows( ConstraintViolationException.class, () -> validationService.testValidAnnotation( new TestClassLevelAnnotation( 10, 5, "asd" ) ) );
    }

    @Test
    void testConstructorLevelAnnotation()
    {
        assertThrows( ConstraintViolationException.class, () -> validationService.testConstructorLevelValidation() );
    }
}
