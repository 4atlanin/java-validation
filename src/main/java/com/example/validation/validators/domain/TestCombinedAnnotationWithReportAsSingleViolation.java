package com.example.validation.validators.domain;

import com.example.validation.validators.constraints.NotBlankAndWithoutProhibitedSubstring;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TestCombinedAnnotationWithReportAsSingleViolation
{
    @NotBlankAndWithoutProhibitedSubstring
    public String stringValidate;
}
