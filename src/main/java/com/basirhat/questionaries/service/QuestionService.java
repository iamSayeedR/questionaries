package com.basirhat.questionaries.service;

import com.basirhat.questionaries.entity.QuestionEntity;
import com.basirhat.questionaries.model.Question;
import com.basirhat.questionaries.mapper.QuestionMapper;
import com.basirhat.questionaries.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionMapper questionMapper;

    public void saveQuestions(List<Question> questions) {
        List<QuestionEntity> questionEntities = questionMapper.domainToEntities(questions);
        questionRepository.saveAll(questionEntities);
    }


//    public Question findById(int id) {
//        QuestionEntity questionEntity = questionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Question not found with ID: " + id));
//        return new Question(questionEntity.getQuestionId(), questionEntity.getType(), questionEntity.getQuestion(), questionEntity.getQuestionOptionsList(), questionEntity.getAnswers());
//    }

}


//save list of questions

//find by question id

//write a method which gets random 10 questions - 10 should be configurable value

//check answers

//result


