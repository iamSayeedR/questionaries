package com.basirhat.questionaries.repository;

import com.basirhat.questionaries.entity.QuestionEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface QuestionRepository extends JpaRepository<QuestionEntity,Integer> {

    List<QuestionEntity> findByType(String type);
}
