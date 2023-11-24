package com.basirhat.questionaries.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "question")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Integer questionId;

    //convert into Enum
    @Column(name = "type")
    private String type;

    @Column(name = "question")
    private String question;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "questionId", orphanRemoval = true)
    private List<QuestionOptionEntity> questionOptionsList = new ArrayList<>();

//    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "question", orphanRemoval = true)
//    List<AnswerEntity> answerList = new ArrayList<>();

    @Column(name = "answers")
    private List<String> answers;
}
