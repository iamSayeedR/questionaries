package com.basirhat.questionaries.model;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record QuestionOption(

        int id,
        @NotBlank
        String optionId,
        @NotBlank
        String description,
        @NotNull
        @Min(value = 1, message = "The sequence should not be lower than {value}.")
        @Max(value = 10, message = "The sequence should not be greater than {value}.")
        int sequence) {
}
