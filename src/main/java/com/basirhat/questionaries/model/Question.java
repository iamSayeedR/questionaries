package com.basirhat.questionaries.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.List;

@Builder(toBuilder = true)
public record Question(
        int qid,
        @NotBlank(message = "Question type cannot be blank")
        String type,
        @NotBlank
        String question,
        @NotNull
        @Size(min = 2, max = 10)
        List<QuestionOption> options,
        @NotNull
        @Size(min = 1, max = 10)
        List<String> answers) {
}
//@NotNull
//@NotEmpty
//@NotBlank

