package com.example.validation.validation_groups.domain;

import com.example.validation.configs.groups.BaseGroup;
import com.example.validation.configs.groups.GroupInheritance;
import com.example.validation.configs.groups.MyGroupSequenceProvider;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.group.GroupSequenceProvider;

import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;

/**
 * @GroupSequenceProvider нельзя использовать совместно с @GroupSequence, т.к. они используются для одного по сути
 * @GroupSequenceProvider это чисто хибернейтовская штука
 */
@Data
@AllArgsConstructor
@GroupSequenceProvider(MyGroupSequenceProvider.class)
public class GroupSequenceProviderEntity {

    private boolean groupFlag;

    @NotNull(message = "integer must be not null", groups = BaseGroup.class)
    private Integer integer;

    @NotNull(message = "string must be not null", groups = GroupInheritance.class)
    private String string;
}
