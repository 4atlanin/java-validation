package com.example.validation.validation_groups.domain;

import com.example.validation.configs.groups.BaseGroup;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class BaseEntity {
    /**
     * javax.validation.groups.Default - group which is used by default
     */
    @NotNull            // the same as @NotNull(groups = Default.class)
    private String notNull;

    @AssertTrue(groups = {BaseGroup.class})
    private boolean boolValue;
}
