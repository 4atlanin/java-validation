package com.example.validation.validators.domain;

import com.example.validation.validators.constraints.NotBlankOrWithoutProhibitedSubString;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestCombinedAnnotationWithORLogic {

    @NotBlankOrWithoutProhibitedSubString
    public String stringValidate;
}
