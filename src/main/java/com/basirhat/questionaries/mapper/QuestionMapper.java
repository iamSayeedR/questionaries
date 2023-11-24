package com.basirhat.questionaries.mapper;


import com.basirhat.questionaries.entity.QuestionEntity;
import com.basirhat.questionaries.model.Question;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface QuestionMapper {

    QuestionEntity domainToEntity(Question question);

    Question entityToDomain(QuestionEntity questionEntity);

    default List<Question> entitiesToDomain(List<QuestionEntity> entities){
        return entities.stream().map(this::entityToDomain).collect(Collectors.toList());
    }
    default List<QuestionEntity> domainToEntities(List<Question> questionList){
        return questionList.stream().map(this::domainToEntity).collect(Collectors.toList());
    }

}
