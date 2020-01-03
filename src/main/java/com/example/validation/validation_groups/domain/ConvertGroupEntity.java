package com.example.validation.validation_groups.domain;

import com.example.validation.configs.groups.BaseGroup;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.GroupSequence;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

@Data
@AllArgsConstructor
@GroupSequence({BaseGroup.class, ConvertGroupEntity.class})
public class ConvertGroupEntity {


    //@ConvertGroup используется только с @Valid
    //Несколько @ConvertGroup с одинаковым аттрибутом from не может быть, кинет ConstraintDeclarationException
    //Нельзя в from указывать группу помеченую аннотацией @GroupSequence, кинет ConstraintDeclarationException
    @Valid
    @ConvertGroup(from = Default.class, to = BaseGroup.class)
    private BaseEntity baseEntity;
    @NotNull(message = "child string must not be null")
    private String notNull;
}
