package com.basirhat.questionaries.repository;

import com.basirhat.questionaries.entity.QuestionEntity;
import com.basirhat.questionaries.entity.QuestionOptionEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionOptionRepository questionOptionRepository;


    @AfterEach
    public void tearDown() throws Exception {
        questionRepository.deleteAll();
    }

    @Test
    void shouldTestSaveTheQuestion() {

        QuestionEntity questionEntity = QuestionEntity.builder()
                .question("Which of the following methods compile? (Choose all that apply.)")
                .type("Java 17")
                .questionOptionsList(List.of(QuestionOptionEntity.builder()
                        .optionId("A")
                        .description("public void january() { return; }")
                        .sequence(1)
                        .build()))
                .answers(List.of("A"))
                .build();

        questionEntity = questionRepository.save(questionEntity);

        List<QuestionOptionEntity> questionOptionList = questionOptionRepository.findAll();


        assertThat(questionEntity.getQuestionId()).isNotZero();
        assertThat(questionOptionList.size()).isEqualTo(1);
        assertThat(questionEntity.getAnswers().size()).isNotZero();
    }


}