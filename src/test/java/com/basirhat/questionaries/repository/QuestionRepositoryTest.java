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

import java.util.ArrayList;
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
                .qid(1)
                .type("Java 17")
                .question("Which of the following methods compile? (Choose all that apply.)")
                .questionOptionsList(List.of(QuestionOptionEntity.builder()
                        .id(1)
                        .optionId("A")
                        .description("public void january() { return; }")
                        .sequence(1)
                        .build()))
                .answers("A")
                .build();

        List<QuestionOptionEntity> questionOptionsList = questionEntity.getQuestionOptionsList();

        QuestionEntity finalQuestionEntity = questionEntity;

        List<QuestionOptionEntity> questionOptionEntityList = questionOptionsList.stream().map(questionOptionEntity -> {
                    questionOptionEntity.setQuestionId(finalQuestionEntity);
                    return questionOptionEntity;
                }
        ).toList();

        finalQuestionEntity.setQuestionOptionsList(questionOptionEntityList);

        questionEntity = questionRepository.save(finalQuestionEntity);

        List<QuestionOptionEntity> questionOptionList = questionOptionRepository.findAll();

        assertThat(questionOptionList.get(0).getQuestionId()).isNotNull();

        assertThat(questionEntity.getQid()).isNotZero();
        assertThat(questionOptionList.size()).isEqualTo(1);
        assertThat(questionEntity.getAnswers()).isNotNull();
    }


    @Test
    public void shouldTestGetAnswers() {
        QuestionEntity questionEntity1 = QuestionEntity.builder()
                .type("Java 17")
                .question("Which of the following methods compile? (Choose all that apply.)")
                .questionOptionsList(List.of(QuestionOptionEntity.builder()
                        .optionId("A")
                        .description("ssd")
                        .sequence(1)
                        .build()))
                .answers("A")
                .build();

        QuestionEntity questionEntity2 = QuestionEntity.builder()
                .type("Java 17")
                .question("Which of the following methods compile? (Choose all that apply.)")
                .questionOptionsList(List.of(QuestionOptionEntity.builder()
                        .optionId("A")
                        .description("ssd")
                        .sequence(1)
                        .build()))
                .answers("A")
                .build();

        QuestionEntity questionEntity3 = QuestionEntity.builder()
                .type("Java 17")
                .question("Which of the following methods compile? (Choose all that apply.)")
                .questionOptionsList(List.of(QuestionOptionEntity.builder()
                        .optionId("A")
                        .description("ssd")
                        .sequence(1)
                        .build()))
                .answers("A")
                .build();

        List<QuestionEntity> questionEntities = questionRepository.saveAll(List.of(questionEntity1, questionEntity2, questionEntity3));

        assertThat(questionEntities.size()).isEqualTo(3); //3 records inserted.

        List<Integer> questionIdList = questionEntities.stream().map(QuestionEntity::getQid).toList();
        List<QuestionEntity> allById = questionRepository.findAllById(questionIdList);


        assertThat(allById.size()).isEqualTo(3);





    }


}