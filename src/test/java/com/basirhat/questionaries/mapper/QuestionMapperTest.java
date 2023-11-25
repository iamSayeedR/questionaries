package com.basirhat.questionaries.mapper;

import com.basirhat.questionaries.entity.QuestionEntity;
import com.basirhat.questionaries.entity.QuestionOptionEntity;
import com.basirhat.questionaries.model.Question;
import com.basirhat.questionaries.model.QuestionOption;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class QuestionMapperTest {

    private QuestionMapper questionMapper = new QuestionMapperImpl(new QuestionOptionMapperImpl());

    @Test
    public void shouldConvertQuestionModelToEntity() {
        String q = "What could be the output of the following?\n" +
                "var stream = Stream.iterate(\"\", (s) ->\n" +
                "s + \"1\");\n" +
                "System.out.println(stream.limit(2).map(x ->\n" +
                "x + \"2\"));";

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

        assertThat(actual.getAnswers()).isEqualTo(String.join(",", stringList));
        assertThat(actual.getQuestion()).isEqualTo(q);
        assertThat(actual.getQuestionOptionsList()).isEqualTo(List.of(QuestionOptionEntity.builder()
                .optionId("A")
                .description("12112")
                .sequence(1)
                .build()));
    }

}