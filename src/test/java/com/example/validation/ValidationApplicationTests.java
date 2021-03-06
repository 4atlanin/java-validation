package com.example.validation;

import com.example.validation.validation_groups.domain.DelegateBaseEntity;
import com.example.validation.validators.constraints.class_level_validation.TestClassLevelAnnotation;
import com.example.validation.validators.constraints.custom_validation.TestCustomAnnotation;
import com.example.validation.validators.constraints.validation_with_inheritance.InheritanceValidationTest;
import com.example.validation.validators.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.metadata.BeanDescriptor;
import java.lang.annotation.ElementType;
import java.time.LocalDateTime;
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
    void testClassLevelAnnotationAndCustomProviderResolver()
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

    @Test
    void testMyOwnMessageInterpolator() {
        List<String> messages = validationService.testMyOwnMessageInterpolator(new TestMessageInterpolation( "q", "   " ) )
                .stream()
                .map( ConstraintViolation::getMessage )
                .collect( Collectors.toList() );

        assertEquals( 1, messages.size() );
        assertTrue( messages.contains( "Annotation - javax.validation.constraints.NotBlank\n" +
                "Message template - Amount of whitespaces in the blank String is ${validatedValue}. Formatted part is '${formatter.format('%s', validatedValue)}'.\n" +
                "Validated value -    \n" ) );
    }

    @Test
    void testMyOwnMessageSource() {
        List<String> messages = validationService
                .testMyOwnMessageSourceFile(new TestCombinedAnnotationWithReportAsSingleViolation( "   " ) )
                .stream()
                .map( ConstraintViolation::getMessage )
                .collect( Collectors.toList() );

        assertEquals( 1, messages.size() );
        assertTrue( messages.contains( "Test message from my own file" ) );
    }


    @Test
    void testReadFromManyMessageSources() {
        List<String> messages = validationService
                .testReadFromManyMessageSources(new TestCombinedAnnotationWithReportAsSingleViolation( "   " ) )
                .stream()
                .map( ConstraintViolation::getMessage )
                .collect( Collectors.toList() );

        assertEquals( 1, messages.size() );
        assertTrue( messages.contains( "Test message from file to show aggregation of message sources" ) );
    }


/*   хз что тут ожидать
    @Test
    public void testParameterNameProvider() {
        List<String> messages = validationService.testParameterNameProvider( 12, 1 )
                .stream()
                .map( ConstraintViolation::getMessage )
                .collect( Collectors.toList() );

        assertTrue( messages.contains( "validate all parameters of method" ) );
    }
*/

    @Test
    public void testClockProvider()
    {
        List<String> messages = validationService.testClockProvider(
            new TestClockConfigurationEntity(
                LocalDateTime.of( 2015, 1, 1, 1, 1 ),
                LocalDateTime.of( 2019, 1, 1, 1, 1 ) ) )
                                                 .stream()
                                                 .map( ConstraintViolation::getMessage )
                                                 .collect( Collectors.toList() );

        assertEquals( 2, messages.size() );
        assertTrue( messages.contains( "must be a date in the past or in the present" ) );
        assertTrue( messages.contains( "must be a date in the present or in the future" ) );
    }

    //Сделали Duration.ofDays( 30 ) и тест показывает, что для +-29 дней от now() валидатор воспринимает как текущий момент времени
    @Test
    public void testTimeValidationTolerance()
    {
        List<String> messages = validationService.testTimeValidationTolerance(
            new TestClockConfigurationEntity(
                LocalDateTime.now().plusDays( 29 ), LocalDateTime.now().minusDays( 29 ) ) )
                                                 .stream()
                                                 .map( ConstraintViolation::getMessage )
                                                 .collect( Collectors.toList() );

        assertEquals( 0, messages.size() );
    }

    @Test
    public void testGetConstraintsForClass()
    {
        //BeanDescriptor содержит метадату по констрэйнтам класса
        //PropertyDescriptor содержит метадату по валидируемой проперти. BeanDescriptor#getConstraintsForProperty( "propName" )
        //ConstraintDescriptors содержит инфу об валидационной аннотации (message, payload и тд). BeanDescriptor#getConstraintDescriptors()
        //ConstructorDescriptor содержит метадату по валидируемому консруктору. BeanDescriptor#getConstrainedConstructors()
        //MethodDescriptor содержит метадату по валидируемому методу. BeanDescriptor#getConstrainedMethods()
        BeanDescriptor descriptor = validationService.getValidator().getConstraintsForClass( DelegateBaseEntity.class );
        //   assertEquals( descriptor.getConstrainedProperties().size(), 2 );

        //findConstraints возвращает ConstraintFinder, который предоставляет fluentAPI для поиска по ConstraintDescriptors
        //у дескрипотра на котором вызывается findConstraints() должны быть


        // !!! не работает, я было разобрался но потом забыл...................
/*        descriptor.getConstrainedProperties()
                  .forEach( propertyDescriptor ->
                      assertTrue( propertyDescriptor.findConstraints().declaredOn( ElementType.FIELD ).hasConstraints() ) );*/
    }

    @Test
    public void testJavaFluentConstraintDefinition() {
        TestListOfAnnotations tloa = new TestListOfAnnotations(null, "a");
        List<String> messages = validationService.testConstraintsFluentApi(tloa)
                .stream()
                .map( ConstraintViolation::getMessage )
                .collect( Collectors.toList() );
        assertEquals(messages.size(), 2);
        assertTrue(messages.contains("must not be null"));
        assertTrue(messages.contains("size must be between 2 and 3"));
    }

    @Test
    public void testCompositionOrLogic() {
        List<String> messages = validationService.testValidateOrComposing(
                new TestCombinedAnnotationWithORLogic(" sdf sd "))
                .stream()
                .map( ConstraintViolation::getMessage )
                .collect( Collectors.toList() );
//ошибок нет, несмотря на то, что в строке есть пробелы.
        //Условие: строка не пустая(true) или не содержит пробелов(false) == true
        assertTrue(messages.isEmpty());

        messages = validationService.testValidateOrComposing(
                new TestCombinedAnnotationWithORLogic(" "))
                .stream()
                .map( ConstraintViolation::getMessage )
                .collect( Collectors.toList() );

        //Появилась ошибка, т.к. все условия не выполняются.
        //Условие: строка не пустая(false) или не содержит пробелов(false) == false
        assertFalse(messages.isEmpty());
        assertTrue(messages.contains("Logical or between composed constraints"));
    }

}