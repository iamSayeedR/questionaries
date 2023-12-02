package com.basirhat.questionaries.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "question")
@Table(name = "question")
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class QuestionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private int qid;

    //convert into Enum
    @Column(name = "type")
    private String type;

    @Column(name = "question")
    private String question;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "questionId")
    private List<QuestionOptionEntity> questionOptionsList = new ArrayList<>();

    @Column(name = "answers")
    private String answers;
}
