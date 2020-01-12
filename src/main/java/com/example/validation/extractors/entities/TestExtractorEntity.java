package com.example.validation.extractors.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.valueextraction.Unwrapping;

@Data
@AllArgsConstructor
public class TestExtractorEntity
{
    @NotNull( message = "Container can't be null" )
    private MyGenericContainer<@NotNull( message = "Key can't be null" ) String, @NotNull( message = "Value can't be null" ) Integer> genericContainer;

    //@NotBlank(payload = Unwrapping.Skip.class ) - показывает, что нужно применить валидацию к самому полю.
    @NotNull( message = "NonGeneric container without unwrapping", payload = Unwrapping.Skip.class )
    private NonGenericContainer nonGenericContainerWithoutUnwrapping;

    // - @NotBlank(payload = Unwrapping.Unwrap.class ) - показывает, что нужно применить валидацию к завёрнутому типу, а не к самому полю.
    //тоже самое, что и @UnwrapByDefault над экстрактором
    // - Если передать null значени, то распаковки не будет
    @NotBlank( message = "NonGeneric container with unwrapping" )
    private NonGenericContainer nonGenericContainer;
}