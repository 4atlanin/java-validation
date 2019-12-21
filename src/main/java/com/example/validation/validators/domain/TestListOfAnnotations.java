package com.example.validation.validators.domain;

import com.example.validation.validators.constraints.WithoutProhibitedSubstring;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestListOfAnnotations
{
    @WithoutProhibitedSubstring.List( {
                                          @WithoutProhibitedSubstring( substringNotAllowed = " " ),
                                          @WithoutProhibitedSubstring( substringNotAllowed = "_" )
                                      } )
    public String listOfAnnotations;

    @WithoutProhibitedSubstring( substringNotAllowed = " " )
    @WithoutProhibitedSubstring( substringNotAllowed = "_" )
    public String justTwoAnnotations;
}
