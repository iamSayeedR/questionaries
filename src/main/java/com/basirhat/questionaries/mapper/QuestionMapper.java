package com.basirhat.questionaries.mapper;


import com.basirhat.questionaries.entity.QuestionEntity;

import com.basirhat.questionnaires.model.Question;
import org.mapstruct.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {QuestionOptionMapper.class},
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface QuestionMapper {

    @Mapping(source = "options", target = "questionOptionsList")
    @Mapping(source = "answers", target = "answers", qualifiedByName = "setToString")
    QuestionEntity domainToEntity(Question question);

    @Mapping(source = "questionOptionsList", target = "options")
    @Mapping(source = "answers", target = "answers", qualifiedByName = "stringToSet")
    Question entityToDomain(QuestionEntity questionEntity);

    default List<Question> entitiesToDomain(List<QuestionEntity> entities){
        return entities.stream().map(this::entityToDomain).collect(Collectors.toList());
    }
    default List<QuestionEntity> domainToEntities(List<Question> questionList){
        return questionList.stream().map(this::domainToEntity).collect(Collectors.toList());
    }

    @Named("stringToSet")
    static List<String> stringToSet(String answers) {
        return nonNull(answers) ? Arrays.stream(answers.split(",")).toList() : null;
    }

    @Named("setToString")
    static String setToString(List<String> answers) {
        return answers.stream().map(String::valueOf).collect(Collectors.joining(","));
    }

}
