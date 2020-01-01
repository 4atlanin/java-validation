package com.example.validation.validation_groups.domain;

import com.example.validation.configs.groups.GroupInheritance;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Null;

@Data
@AllArgsConstructor
public class DelegateBaseEntity {
    @Valid
    private BaseEntity baseEntity;
    @Null(groups = GroupInheritance.class)
    private Integer nullValue;
}
