package com.basirhat.questionaries.controller;


import com.basirhat.questionaries.model.Question;
import com.basirhat.questionaries.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @PostMapping("/questions")
    public ResponseEntity<Void> saveQuestion(@RequestBody List<Question> questionList) {
        questionService.saveQuestions(questionList);
        return ResponseEntity.accepted().build();
    }

}
