package com.basirhat.questionaries.repository;

import com.basirhat.questionaries.entity.QuestionOptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface QuestionOptionRepository extends JpaRepository<QuestionOptionEntity,Integer> {

}

