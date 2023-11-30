package com.basirhat.questionaries.controller;


import com.basirhat.questionaries.model.Question;
import com.basirhat.questionaries.service.QuestionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/v1")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @PostMapping("/questions")
    public ResponseEntity<Void> saveQuestion(@RequestBody List<Question> questionList) {
//        validateQuestion(questionList);
        questionService.saveQuestions(questionList);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/question")
    public ResponseEntity<Void> saveQuestion(@Valid @RequestBody Question question) {
        questionService.saveQuestions(List.of(question));
        return ResponseEntity.accepted().build();
    }

//    private void validateQuestion(List<Question> questionList) {
//
//        if (questionList == null || questionList.isEmpty()) {
//            throw new IllegalArgumentException("Question List can not be empty");
//        }
//        questionList.forEach(question -> {
//
//            if (Objects.isNull(question.question())) {
//                throw new NullPointerException("Question is mandatory.");
//            }
//            if (question.question().isBlank()) {
//                throw new IllegalArgumentException("Question can not be null.");
//            }
//            if (Objects.isNull(question.type())) {
//                throw new NullPointerException("Question type is mandatory.");
//            }
//            if (question.type().isEmpty()) {
//                throw new IllegalArgumentException("Question type can not be null");
//            }
//            if (Objects.isNull(question.answers()) || question.answers().isEmpty()) {
//                throw new NullPointerException("Answer can not be empty or null.");
//            }
//            question.answers().forEach(answer -> {
//                if (Objects.isNull(answer)) {
//                    throw new NullPointerException("Answer is mandatory.");
//                }
//                if (answer.isBlank()) {
//                    throw new IllegalArgumentException("Answer can not be null.");
//                }
//            });
//
//            if (question.options().size() < 2) {
//                throw new IllegalArgumentException("Option should be equal to 2");
//            }
//            question.options().forEach(questionOption -> {
//                if (Objects.isNull(questionOption.optionId())) {
//                    throw new IllegalArgumentException("Option Id cant be null");
//                }
//                if (questionOption.optionId().isEmpty()) {
//                    throw new IllegalArgumentException("Option ID can not be null");
//                }
//                if (Objects.isNull(questionOption.description())) {
//                    throw new IllegalArgumentException("description can not be null");
//
//                }
//                if (questionOption.description().isEmpty()) {
//                    throw new IllegalArgumentException("Description can not be empty");
//                }
//            });
//        });
//    }

}

