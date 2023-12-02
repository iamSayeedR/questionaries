package com.basirhat.questionaries.service;

import com.basirhat.questionaries.entity.QuestionEntity;
import com.basirhat.questionaries.entity.QuestionOptionEntity;
import com.basirhat.questionaries.mapper.QuestionMapper;
import com.basirhat.questionaries.mapper.QuestionOptionMapper;
import com.basirhat.questionaries.model.Question;
import com.basirhat.questionaries.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class QuestionService {

    @Value("${no_of_questions_per_exam}")
    private int noOfQuestionsPerExam;

    private final QuestionRepository questionRepository;

    private final QuestionMapper questionMapper;

    private final QuestionOptionMapper questionOptionMapper;

    /**
     * This method saves list of questions into database.
     * @param questions - the given list of question
     */
    @Transactional
    public void saveQuestions(List<Question> questions) {

        log.info("saving questions");

        final List<QuestionEntity> questionEntities = questions.stream()
                .map(question -> {
                    final QuestionEntity questionEntity = questionMapper.domainToEntity(question);
                    final List<QuestionOptionEntity> questionOptionEntityList = questionOptionMapper.domainsToEntities(question.options(), questionEntity);
                    questionEntity.setQuestionOptionsList(questionOptionEntityList);
                    return questionEntity;})
                .toList();

        questionRepository.saveAll(questionEntities);

        log.info("saved questions");
    }

    /**
     * This method generates random questions for the given exam type.
     * @param examType - exam type
     * @return - list of random questions
     */
    @Transactional(readOnly = true)
    public List<Question> getRandomQuestions(String examType) {

        final List<QuestionEntity> allQuestions = questionRepository.findByType(examType);

        final List<Integer> randomNumbers = generateRandomNumbers(allQuestions.size());

        final List<QuestionEntity> randomQuestionEntities = randomNumbers.stream()
                .map(allQuestions::get)
                .map(questionEntity -> questionEntity.toBuilder().answers(null).build())
                .toList();

        return questionMapper.entitiesToDomain(randomQuestionEntities);
    }


    /**
     * Generates random numbers
     * @param max - max number
     * @return - list of random integers
     */
    private List<Integer> generateRandomNumbers(int max) {

        final List<Integer> numberList = new ArrayList<>();

        final Random random = new Random();

        while (numberList.size() < noOfQuestionsPerExam) {
            int randomNumber = random.nextInt(max);
            if (!numberList.contains(randomNumber)) {
                numberList.add(randomNumber);
            }
        }

        return numberList;
    }
}


