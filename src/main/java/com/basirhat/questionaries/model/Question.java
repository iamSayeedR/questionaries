package com.basirhat.questionaries.model;

import lombok.Builder;

import java.util.List;

@Builder
public record Question(int qid, String type, String question, List<QuestionOption> options, List<String> answers) {
}
