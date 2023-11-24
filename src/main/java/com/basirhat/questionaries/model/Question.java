package com.basirhat.questionaries.model;

import java.util.List;

public record   Question(int questionId, String type, String question, List<QuestionOption> options, List<String> answers) {
}
