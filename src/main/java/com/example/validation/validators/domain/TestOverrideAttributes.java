package com.example.validation.validators.domain;

import com.example.validation.validators.constraints.OverridesAttributesExample;
import lombok.AllArgsConstructor;
import lombok.Setter;

@Setter
@AllArgsConstructor
public class TestOverrideAttributes
{

    /**
     * Constraint над геттером - тоже самое что и над полем
     */
    private String testString;

    @OverridesAttributesExample( messageUndescore = "message with underscore", messageWhitespace = "message with whitespace", size = 3 )
    public String getTestSubstring()
    {
        return testString;
    }
}

