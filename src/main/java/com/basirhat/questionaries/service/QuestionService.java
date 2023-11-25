package com.basirhat.questionaries.service;

import com.basirhat.questionaries.entity.QuestionEntity;
import com.basirhat.questionaries.entity.QuestionOptionEntity;
import com.basirhat.questionaries.mapper.QuestionOptionMapper;
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

    @Autowired
    private QuestionOptionMapper questionOptionMapper;

    public void saveQuestions(List<Question> questions) {

        List<QuestionEntity> questionEntities = questions.stream()
                .map(question -> {
                    final QuestionEntity questionEntity = questionMapper.domainToEntity(question);
                    final List<QuestionOptionEntity> questionOptionEntityList = questionOptionMapper.domainsToEntities(question.options(), questionEntity);
                    questionEntity.setQuestionOptionsList(questionOptionEntityList);
                    return questionEntity;
                }).toList();

        questionRepository.saveAll(questionEntities);
    }

}


//save list of questions

//find by question id

//write a method which gets random 10 questions - 10 should be configurable value

//check answers

//result


