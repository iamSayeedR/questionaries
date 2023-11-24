package com.basirhat.questionaries.repository;

import com.basirhat.questionaries.entity.QuestionOptionEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
class QuestionOptionRepositoryTest {

    @Autowired
    private QuestionOptionRepository questionOptionRepository;

    @Autowired
    private QuestionRepository questionRepository;




    @Test
    void shouldTestSaveTheQuestion() {

        QuestionOptionEntity questionOptionEntity = QuestionOptionEntity.builder()
                .optionId("A")
                .description("public void january() { return; }")
                .sequence(1).build();

        QuestionOptionEntity actual = questionOptionRepository.save(questionOptionEntity);

        assertThat(actual.getOptionId()).isEqualTo("A");

    }


}