package com.example.validation.validation_groups;

import com.example.validation.validation_groups.domain.BaseEntity;
import com.example.validation.validation_groups.domain.DelegateBaseEntity;
import com.example.validation.validation_groups.domain.RedefineDefault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Service
public class GroupValidationService {
    @Autowired
    private Validator validator;

    public Set<ConstraintViolation<BaseEntity>> simpleExampleGroupValidation(BaseEntity baseEntity, Class... groupsToValidate) {
        return validator.validate(baseEntity, groupsToValidate);
    }

    public Set<ConstraintViolation<DelegateBaseEntity>> inheritanceExampleGroupValidation(DelegateBaseEntity baseEntity, Class... groupsToValidate) {
        return validator.validate(baseEntity, groupsToValidate);
    }

    public Set<ConstraintViolation<DelegateBaseEntity>> orderedChecks(DelegateBaseEntity baseEntity, Class... groupsToValidate) {
        return validator.validate(baseEntity, groupsToValidate);
    }

    public Set<ConstraintViolation<RedefineDefault>> defaultRedefining(RedefineDefault baseEntity, Class... groupsToValidate) {
        return validator.validate(baseEntity, groupsToValidate);
    }

}
