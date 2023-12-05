package com.basirhat.questionaries.service;

import com.basirhat.questionaries.entity.QuestionEntity;
import com.basirhat.questionaries.entity.QuestionOptionEntity;
import com.basirhat.questionaries.mapper.QuestionMapper;
import com.basirhat.questionaries.mapper.QuestionOptionMapper;

import com.basirhat.questionaries.repository.QuestionRepository;
import com.basirhat.questionnaires.model.Question;
import com.basirhat.questionnaires.model.QuestionOption;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuestionServiceTest {

    @InjectMocks
    private QuestionService questionService;

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private QuestionMapper questionMapper;

    @Mock
    private QuestionOptionMapper questionOptionMapper;

    @Captor
    private ArgumentCaptor<List<QuestionEntity>> argumentCaptorQuestionEntity;

    @Test
    public void shouldTestSaveAllTheQuestions() {
        String questionText = """ 
                Suppose you have a module named com.vet. Where could you place the following
                "module-info
                "java file to create a valid module?
                "public module com.vet {
                "exports com.vet;
                "}
                """;
        List<String> answers = List.of("A", "b");
        List<QuestionOption> questionOptions = List.of(QuestionOption.builder()
                .optionId("A")
                .description("fhdsfhdi")
                .sequence(2)
                .build());
        Question question = Question.builder()
                .question(questionText)
                .options(questionOptions)
                .answers(answers)
                .build();
        List<QuestionOptionEntity> questionOptionEntities = List.of(QuestionOptionEntity.builder()
                .optionId("A")
                .description("fhdsfhdi")
                .sequence(2)
                .build());
        List<Question> questions = List.of(question);

        QuestionEntity questionEntity = QuestionEntity.builder()
                .question(questionText)
                .questionOptionsList(questionOptionEntities)
                .answers("A, b")
                .build();


        when(questionMapper.domainToEntity(question)).thenReturn(questionEntity);

        when(questionOptionMapper.domainsToEntities(questionOptions, questionEntity)).thenReturn(questionOptionEntities);

        questionService.saveQuestions(questions);

        verify(questionRepository, times(1)).saveAll(argumentCaptorQuestionEntity.capture());

        List<QuestionEntity> argumentCaptorQuestionEntityValue = argumentCaptorQuestionEntity.getValue();

        QuestionEntity actual = argumentCaptorQuestionEntityValue.get(0);

        assertEquals(1, argumentCaptorQuestionEntityValue.size());
        assertEquals(questionText, actual.getQuestion());
        assertEquals("A, b", actual.getAnswers());


    }

    @Test
    void shouldTestGetRandomQuestions() {

        String examType = "Java 17";

        List<QuestionEntity> questionEntities = new ArrayList<>();

        when(questionRepository.findByType(examType)).thenReturn(questionEntities);

        List<Question> actualQuestions = questionService.getRandomQuestions(examType);
        assertEquals(questionEntities.size(), actualQuestions.size());
        verify(questionRepository).findByType(examType);

    }

    @Test
    public void shouldTestGenerateRandomNumbers() {
        int max = 10;
        int expectedSize = 10;
        Random random = new Random();
        List<Integer> expectedList = new ArrayList<>();
        for (int i = 0; i < expectedSize; i++) {
            int randomNumber = random.nextInt(max);
            expectedList.add(randomNumber);
        }

        List<Integer> actualList = generateRandomNumbers(max);

        assertEquals(expectedSize, actualList.size());
        assertTrue(actualList.containsAll(expectedList));
    }

    List<Integer> generateRandomNumbers(int max) {
        final List<Integer> numberList = new ArrayList<>();
        final Random random = new Random();
        while (numberList.size() < 10) {
            int randomNumber = random.nextInt(max);
            if (!numberList.contains(randomNumber)) {
                numberList.add(randomNumber);
            }
        }
        return numberList;
    }
}


