package com.basirhat.questionaries.mapper;

import com.basirhat.questionaries.entity.QuestionOptionEntity;
import com.basirhat.questionaries.model.QuestionOption;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface QuestionOptionMapper {

    QuestionOptionEntity domainToEntity(QuestionOption QuestionOption);

    QuestionOption entityToDomain(QuestionOptionEntity QuestionOptionEntity);

//    default List<QuestionOption> entitiesToDomain(List<QuestionOptionEntity> entities){
//        return entities.stream().map(this::entityToDomain).collect(Collectors.toList());
//    }
//
    default List<QuestionOptionEntity> domainsToEntities(List<QuestionOption> QuestionOptionList){
        return QuestionOptionList.stream().map(this::domainToEntity).collect(Collectors.toList());
    }


}
