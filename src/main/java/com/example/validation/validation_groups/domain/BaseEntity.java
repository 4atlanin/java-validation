package com.example.validation.validation_groups.domain;

import com.example.validation.configs.groups.BaseGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseEntity {
    /**
     * javax.validation.groups.Default - group which is used by default
     */
    @NotNull(message = "parent string must not be null")            // the same as @NotNull(groups = Default.class)
    private String notNull;

    @AssertTrue(groups = {BaseGroup.class})
    private boolean boolValue;
}
