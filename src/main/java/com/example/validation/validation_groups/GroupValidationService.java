package com.example.validation.validation_groups;

import com.example.validation.configs.MyTraversableResolver;
import com.example.validation.configs.MyValidationProviderResolver;
import com.example.validation.validation_groups.domain.*;
import org.hibernate.validator.HibernateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

@Service
public class GroupValidationService {

    @Autowired
    //показывает, что заинжектить нужно именно хибернейтовскую имплементацию провайдера
    @org.hibernate.validator.cdi.HibernateValidator
    private Validator validator;

    public Set<ConstraintViolation<BaseEntity>> simpleExampleGroupValidation(BaseEntity baseEntity, Class... groupsToValidate) {
        return validator.validate(baseEntity, groupsToValidate);
    }

    public Set<ConstraintViolation<DelegateBaseEntity>> inheritanceExampleGroupValidation(DelegateBaseEntity baseEntity, Class... groupsToValidate) {
        return validator.validate(baseEntity, groupsToValidate);
    }

    public Set<ConstraintViolation<DelegateBaseEntity>> traversableResolver(DelegateBaseEntity baseEntity, Class... groupsToValidate)
    {
        Validator localValidator = Validation.byProvider(HibernateValidator.class)
                .configure()
                .traversableResolver(new MyTraversableResolver())
                //По умолчанию, обращения к TraversableResolver кешируются на промежуток работы валидатора
                //Кеширование может снижать производительнось, и если ваш резолвер нормальный по скорости
                // то можно отключить кеширование
                .enableTraversableResolverResultCache( false ) //такая проперти есть в HibernateValidatorConfiguration, которую можн получить с помощью byProvider(HibernateValidator.class)
                .buildValidatorFactory()
                .getValidator();
        return localValidator.validate(baseEntity, groupsToValidate);
    }

    public Set<ConstraintViolation<DelegateBaseEntity>> orderedChecks(DelegateBaseEntity baseEntity, Class... groupsToValidate) {
        return validator.validate(baseEntity, groupsToValidate);
    }

    public Set<ConstraintViolation<RedefineDefault>> defaultRedefining(RedefineDefault baseEntity, Class... groupsToValidate) {
        return validator.validate(baseEntity, groupsToValidate);
    }

    public Set<ConstraintViolation<GroupSequenceProviderEntity>>
    groupSequenceProvider(GroupSequenceProviderEntity baseEntity, Class... groupsToValidate) {
        return validator.validate(baseEntity, groupsToValidate);
    }

    public Set<ConstraintViolation<ConvertGroupEntity>>
    groupConversion(ConvertGroupEntity baseEntity, Class... groupsToValidate) {
        return validator.validate(baseEntity, groupsToValidate);
    }

}
