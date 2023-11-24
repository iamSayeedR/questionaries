package com.basirhat.questionaries.model;

import lombok.Builder;

import java.util.List;

@Builder
public record Question(int questionId, String type, String question, List<QuestionOption> options, List<String> answers) {
}
