package com.example.validation;

import com.example.validation.validators.constraints.ConstructorLevelCheck;
import com.example.validation.validators.constraints.MethodLevelCheck;
import com.example.validation.validators.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

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

    @Autowired
    @Valid
    private TestConstructorLevelAnnotation testConstructorLevelAnnotation;

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

    public Set<ConstraintViolation<TestListOfAnnotations>> testValidAnnotation( @Valid TestClassLevelAnnotation dto )  //@Valid сработает только если есть @Validated
    // Можно и на респонс вешать
    {
        return null;
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

    /*Если валидатор уровня метода проверяет респонс (т.е. нет аннотации @SupportedValidationTarget( ValidationTarget.PARAMETERS))
     *  на методе который возвращает void, мы получим ConstraintDeclarationException: HV000132: Void methods must not be constrained or marked for cascaded validation
     */
    @MethodLevelCheck( min = 10, max = 20 )
    public void testValidateAllMethodParametersFail( int min, int max )
    {
        /*return false;*/
    }

    @AssertTrue                                     //работает благодаря @Validated над классом
    public boolean testValidateMethodResponse()
    {
        return false;
    }

    //TODO Валидация параметров консруктора работает только через явное обращение к validator :(
    // @MethodLevelCheck( min = 10, max = 20 )       т.к. метод не принимает параметров, то аннотацию для проверки параметров нельзя вешать на него
    public Set<ConstraintViolation<TestConstructorLevelAnnotation>> testConstructorLevelValidationTriggeredManually() throws NoSuchMethodException
    {
        return validator.forExecutables().validateConstructorParameters(
            ReflectionUtils.accessibleConstructor( TestConstructorLevelAnnotation.class, int.class, int.class, Integer.class ),
            new Object[] { null } );

    }

    public Set<ConstraintViolation<TestConstructorLevelAnnotation>> testConstructorLevelValidationTriggeredByAnnotation() throws NoSuchMethodException
    {
        return validator.forExecutables().validateConstructorParameters(
            ReflectionUtils.accessibleConstructor( TestConstructorLevelAnnotation.class, int.class, int.class, Integer.class ),
            new Object[] { 2,1, null } );

    }



    public int testBean() {
        return testConstructorLevelAnnotation.getB();
    }
}
