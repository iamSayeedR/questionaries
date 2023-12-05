package com.basirhat.questionaries.mapper;

import com.basirhat.questionaries.entity.QuestionEntity;

import com.basirhat.questionnaires.model.Question;
import com.basirhat.questionnaires.model.QuestionOption;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class QuestionMapperTest {

    @InjectMocks
    private QuestionMapperImpl questionMapper;

    @Mock
    private QuestionOptionMapperImpl questionOptionMapper;

    @Captor
    private ArgumentCaptor<List<QuestionOption>> argumentCaptor;

    @Test
    public void shouldConvertQuestionModelToEntity() {
        String q = """
                What could be the output of the following?
                var stream = Stream.iterate("", (s) ->
                s + "1");
                System.out.println(stream.limit(2).map(x ->
                x + "2"));""";

        List<String> stringList = List.of("A", "B");

        Question question = Question.builder()
                .question(q)
                .options(List.of(QuestionOption.builder()
                        .optionId("A")
                        .description("12112")
                        .sequence(1)
                        .build()))
                .answers(stringList)
                .build();

        QuestionEntity actual = questionMapper.domainToEntity(question);

        verify(questionOptionMapper).domainsToEntities(argumentCaptor.capture());

        List<QuestionOption> argumentCaptorValue = argumentCaptor.getValue();
        assertThat(argumentCaptorValue.size()).isEqualTo(1);

        assertThat(actual.getAnswers()).isEqualTo(String.join(",", stringList));
        assertThat(actual.getQuestion()).isEqualTo(q);


    }

}