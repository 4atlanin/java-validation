package com.example.validation.validation_groups.domain;

import com.example.validation.configs.groups.BaseGroup;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.GroupSequence;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;

@Data
@AllArgsConstructor
// B @GroupSequence обязательно должен быть упомянут текущий класс - RedefineDefault.class,
// если нет то - HV000054: com.example.validation.validation_groups.domain.RedefineDefault must be part of the redefined default group sequence.
// javax.validation.groups.Default тоже не может появлятся в списке, иначе - HV000053: 'Default.class' cannot appear in default group sequence list.
@GroupSequence({BaseGroup.class, /*Default.class,*/ RedefineDefault.class})
public class RedefineDefault {
    @NotNull(groups = Default.class)
    private String explicitDefault;

    @Max(value = 10)
    private Integer implicitDefault;

    @Size(min = 10, groups = BaseGroup.class)
    private String anotherGroup;

}
