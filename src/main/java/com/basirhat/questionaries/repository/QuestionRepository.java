package com.basirhat.questionaries.repository;

import com.basirhat.questionaries.entity.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface QuestionRepository extends JpaRepository<QuestionEntity,Integer> {
}
