package com.basirhat.questionaries.mapper;

import com.basirhat.questionaries.entity.QuestionOptionEntity;

import com.basirhat.questionnaires.model.QuestionOption;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuestionOptionMapperTest {


    private final QuestionOptionMapper questionOptionMapper = Mappers.getMapper(QuestionOptionMapper.class);

    @Test
    public void shouldTestDomainsToEntities() {
        List<QuestionOption> questionOptionList = new ArrayList<>();
        questionOptionList.add(QuestionOption.builder()
                .optionId("A")
                .sequence(1)
                .description("The code does not compile.")
                .build());
        List<QuestionOptionEntity> questionOptionEntityList = questionOptionMapper.domainsToEntities(questionOptionList);

       assertEquals(1, questionOptionEntityList.size());
    }



}