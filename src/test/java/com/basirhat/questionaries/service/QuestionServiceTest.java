package com.basirhat.questionaries.service;

import com.basirhat.questionaries.entity.QuestionEntity;
import com.basirhat.questionaries.entity.QuestionOptionEntity;
import com.basirhat.questionaries.mapper.QuestionMapper;
import com.basirhat.questionaries.mapper.QuestionOptionMapper;
import com.basirhat.questionaries.model.Question;
import com.basirhat.questionaries.model.QuestionOption;
import com.basirhat.questionaries.repository.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.List;

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
        String questionText = "Suppose you have a module named com.vet. Where could you place the following\n" +
                "module-info.\n" +
                "java file to create a valid module?\n" +
                "public module com.vet {\n" +
                "exports com.vet;\n" +
                "}";
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


}