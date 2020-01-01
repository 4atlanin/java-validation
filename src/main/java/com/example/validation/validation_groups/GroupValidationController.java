package com.example.validation.validation_groups;

import com.example.validation.ValidationService;
import com.example.validation.configs.groups.BaseGroup;
import com.example.validation.configs.groups.GroupInheritance;
import com.example.validation.validation_groups.domain.DelegateBaseEntity;
import com.example.validation.validators.constraints.custom_validation.TestCustomAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
//@Validated(GroupInheritance.class)
public class GroupValidationController {
    @Autowired
    public GroupValidationService groupValidationService;

    /**
     * @Validated тут используется, чтобы указать группу для валидации.
     * @Validated(BaseGroup.class) можно переместить на уровень клсса, а тут поствить @Valid
     * Тут вызывется валидция только для BaseGroup.class группы
     * Чтобы упавшая валидация позволила попасть в метод, можно добавить BindingResult
     */
    @PostMapping("/group-validated")
    ResponseEntity createrSomething(@RequestBody @Validated(BaseGroup.class) DelegateBaseEntity dto,
                                    BindingResult result ) {

        return ResponseEntity.ok( result.getAllErrors() );
    }
}
