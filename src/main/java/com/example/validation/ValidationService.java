package com.example.validation;

import com.example.validation.validators.constraints.class_level_validation.TestClassLevelAnnotation;
import com.example.validation.validators.constraints.constructor_level_validation.TestConstructorLevelAnnotation;
import com.example.validation.validators.constraints.custom_validation.TestCustomAnnotation;
import com.example.validation.validators.constraints.method_level_validation.MethodLevelCheck;
import com.example.validation.validators.constraints.validation_with_inheritance.InheritanceValidationTest;
import com.example.validation.validators.domain.TestCombinedAnnotationWithReportAsSingleViolation;
import com.example.validation.validators.domain.TestListOfAnnotations;
import com.example.validation.validators.domain.TestMessageInterpolation;
import com.example.validation.validators.domain.TestOverrideAttributes;
import org.hibernate.validator.constraints.ParameterScriptAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.annotation.Validated;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Service
@Validated//нужно для работы валидаций на параметрах метода. Если небылобы спринг бута, нужно былобы зарегать MethodValidationPostProcessor бин
public class ValidationService
{
    @Autowired
    Validator validator;

    public Set<ConstraintViolation<TestCustomAnnotation>> validateCustomAnnotation( TestCustomAnnotation validationPOSTDTO )
    {
        return validator.validate( validationPOSTDTO );
    }

    public Set<ConstraintViolation<TestCombinedAnnotationWithReportAsSingleViolation>> testReportAsSingleViolation( TestCombinedAnnotationWithReportAsSingleViolation dto )
    {
        return validator.validate( dto );
    }

    public Set<ConstraintViolation<TestListOfAnnotations>> testListOfAnnotations( TestListOfAnnotations dto )
    {
        return validator.validate( dto );
    }

    public void testValidAnnotation( @Valid TestClassLevelAnnotation dto )  //@Valid сработает только если есть @Validated
    // Можно и на респонс вешать
    {
    }

    public Set<ConstraintViolation<TestOverrideAttributes>> testOverrideAttributes( TestOverrideAttributes dto )
    {
        return validator.validate( dto );
    }

    public Set<ConstraintViolation<TestClassLevelAnnotation>> testClassLevelAnnotation( TestClassLevelAnnotation dto )
    {
        return validator.validate( dto );
    }

    public boolean testValidateMethodParameter( @NotNull TestClassLevelAnnotation dto )   //работает благодаря @Validated над классом
    {
        return false;
    }

    @MethodLevelCheck( min = 10, max = 20 )                                 //работает благодаря @Validated над классом
    public boolean testValidateAllMethodParameters( int min, int max )
    {
        return false;
    }

    @ParameterScriptAssert( lang = "javascript", script = "min <= max" )   //только js доступен по дефолту
    public boolean testParameterScriptAssert( int min, int max )
    {
        return false;
    }

    @AssertTrue                                     //работает благодаря @Validated над классом
    public boolean testValidateMethodResponse()
    {
        return false;
    }

    //TODO Валидация параметров консруктора работает только через явное обращение к validator :(
    // @MethodLevelCheck( min = 10, max = 20 )       т.к. метод не принимает параметров, то аннотацию для проверки параметров нельзя вешать на него

    // PS Кажись, только аннотациями неполучится вызвать проверку параметров конструктора
    public Set<ConstraintViolation<TestConstructorLevelAnnotation>> testConstructorLevelValidationTriggeredManually() throws NoSuchMethodException
    {
        return validator.forExecutables().validateConstructorParameters(
            ReflectionUtils.accessibleConstructor( TestConstructorLevelAnnotation.class, String.class ),
            new Object[] { "1234567" } );

    }

    public boolean testValidateFieldInheritance( @Valid InheritanceValidationTest obj )   //работает благодаря @Validated над классом
    {
        return false;
    }

    public void testMessageInterpolation( @Valid TestMessageInterpolation obj )
    {

    }

}
