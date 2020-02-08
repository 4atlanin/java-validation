package com.example.validation.validators.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TestClockConfigurationEntity
{
    @FutureOrPresent
    private LocalDateTime future;

    @PastOrPresent
    private LocalDateTime past;

}
