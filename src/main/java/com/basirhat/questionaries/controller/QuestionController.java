package com.basirhat.questionaries.controller;


import com.basirhat.questionaries.service.QuestionService;
import com.basirhat.questionnaires.model.Question;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
@Slf4j
@Validated
@RequiredArgsConstructor
public class QuestionController {


    private final QuestionService questionService;

    @PostMapping("/questions")
    public ResponseEntity<Void> saveQuestion(@RequestBody List<@Valid Question> questionList) {
        questionService.saveQuestions(questionList);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/question")
    public ResponseEntity<Void> saveQuestion(@Valid @RequestBody Question question) {
        questionService.saveQuestions(List.of(question));
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/questions")
    public ResponseEntity<List<Question>> startExam(@RequestParam String examType) {
        log.info("examType selected - {} ", examType);
        List<Question> questions = questionService.getRandomQuestions(examType);
        return ResponseEntity.ok(questions);

    }



}

