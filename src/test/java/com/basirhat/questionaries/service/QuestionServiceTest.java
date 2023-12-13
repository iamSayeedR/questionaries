package com.basirhat.questionaries.service;

import com.basirhat.questionaries.entity.QuestionEntity;
import com.basirhat.questionaries.entity.QuestionOptionEntity;
import com.basirhat.questionaries.mapper.QuestionMapper;
import com.basirhat.questionaries.mapper.QuestionOptionMapper;

import com.basirhat.questionaries.repository.QuestionRepository;
import com.basirhat.questionnaires.model.Question;
import com.basirhat.questionnaires.model.QuestionAnswer;
import com.basirhat.questionnaires.model.QuestionAnswerResponse;
import com.basirhat.questionnaires.model.QuestionOption;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

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
    @Captor
    private ArgumentCaptor<List<Integer>> argumentCaptor = ArgumentCaptor.forClass(List.class);

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
    public void shouldTestReturnAllId() {

        List<Integer> qid = Arrays.asList(1,2,3);

       questionService.getQuestionAnswer(qid);
       verify(questionRepository).findAllById(argumentCaptor.capture());

       List<Integer> capturedIds =argumentCaptor.getValue();
       assertEquals(qid,capturedIds);
    }
    @Test
    public void shouldReturnEmptyResponseForEmptyQuestionIds() {
        List<Integer> qid = List.of();
        questionService.getQuestionAnswer(qid);
        verify(questionRepository).findAllById(argumentCaptor.capture());
        List<Integer> capturedIds = argumentCaptor.getValue();
        assertEquals(qid,capturedIds);
    }
    @Test
    public void shouldGetAnswersForValidQuestionsIds() {

        List<Integer> questionIdList = List.of(1,2,3);

        List<QuestionEntity> questionEntities = new ArrayList<>();
        questionEntities.add(QuestionEntity.builder().qid(1).answers("A,B,C").build());
        questionEntities.add(QuestionEntity.builder().qid(2).answers("D").build());
        questionEntities.add(QuestionEntity.builder().qid(3).answers("E,F").build());

        when(questionRepository.findAllById(questionIdList)).thenReturn(questionEntities);


        QuestionAnswerResponse actualResponse = questionService.getQuestionAnswer(questionIdList);

        List<QuestionAnswer> expectedAnswers = new ArrayList<>();
        expectedAnswers.add(QuestionAnswer.builder().questionId(1).answer(List.of("A","B","C")).build());
        expectedAnswers.add(QuestionAnswer.builder().questionId(2).answer(List.of("D")).build());
        expectedAnswers.add(QuestionAnswer.builder().questionId(3).answer(List.of("E","F")).build());
        QuestionAnswerResponse expectedResponse = QuestionAnswerResponse.builder().questionAnswers(expectedAnswers).build();

        assertEquals(expectedResponse, actualResponse);
        verify(questionRepository).findAllById(questionIdList);
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


