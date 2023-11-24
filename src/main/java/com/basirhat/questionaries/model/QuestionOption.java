package com.basirhat.questionaries.model;


import lombok.Builder;

@Builder

public record QuestionOption(int id, String optionId, String description, int sequence) {
}
